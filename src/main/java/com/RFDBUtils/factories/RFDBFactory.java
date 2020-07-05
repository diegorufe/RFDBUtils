package com.RFDBUtils.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import com.RFDBUtils.comparators.FileNameComparator;
import com.RFDBUtils.config.ConfigDB;
import com.RFDBUtils.config.ConfigDBUtils;
import com.RFDBUtils.config.LogUtils;
import com.RFDBUtils.constants.IConstantsRFDBUtils;

/**
 * Class for manage actions for app
 * 
 * <p>
 * Operations
 * 
 * <ul>
 * 
 * <li>backup : execute backup for database</li>
 * <li>executeScripts : execute scripts in folder and restore to save point if
 * any of script fail</li>
 * 
 * </ul>
 * 
 * @author diego
 *
 */
public final class RFDBFactory {

	private static final Logger LOG = LogUtils.getLogger(RFDBFactory.class.getSimpleName());
	private ConfigDB configDB;
	private Connection connection;

	/**
	 * Method for star app
	 * 
	 * @param args pass for command line
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void start(String[] args) throws ClassNotFoundException, SQLException {

		// Init log
		LogUtils.initLog();

		// Init config
		this.initConfig(args);

		switch (this.configDB.getEnumOperationsDB()) {
		case BACKUP:
			this.backup();
			break;

		case EXECUTE_SCRIPTS:
			this.executeScripts();
			break;

		default:
			break;
		}
	}

	/**
	 * Method for generate backup
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public final void backup() throws ClassNotFoundException, SQLException {
		long start = System.currentTimeMillis();
		LOG.info("Start backup ...");

		if (this.connectMysql()) {

			// Dump database
			final String fileName = (this.configDB.getPathBackup().isEmpty() ? ""
					: this.configDB.getPathBackup().concat(File.separator))
							.concat(IConstantsRFDBUtils.MYSQL_DUMP_EXE_DATE_FORMAT.format(new Date()).concat("_")
									.concat(IConstantsRFDBUtils.MYSQL_DUMP_EXE_FILENAME)
									.concat(IConstantsRFDBUtils.MYSQL_DUMP_EXE_FILENAME_EXT));
			Process process = null;

			try {
				StringBuilder builderCommand = new StringBuilder();
				builderCommand.append(IConstantsRFDBUtils.MYSQL_DUMP_EXE).append(" ")
						.append(IConstantsRFDBUtils.MYSQL_EXE_USER).append(this.configDB.getUser()).append(" ")
						.append(IConstantsRFDBUtils.MYSQL_EXE_PASSWORD).append(this.configDB.getPassword()).append(" ");

				if (this.configDB.isCreateDropDatabase()) {
					builderCommand.append(" ").append(IConstantsRFDBUtils.MYSQL_DUMP_EXE_CREATE_DROP_DATABASE);
				}

				builderCommand.append(" ").append(this.configDB.getDatabase());

				process = Runtime.getRuntime().exec(builderCommand.toString());

				File file = new File(fileName);

				if (!file.createNewFile()) {
					throw new IOException("Unable to create file " + fileName);
				}

				InputStream is = process.getInputStream();
				FileOutputStream fos = new FileOutputStream(fileName);
				byte[] buffer = new byte[1000];

				int readed = is.read(buffer);
				while (readed > 0) {
					LOG.info("Backup is on fire ...");
					fos.write(buffer, 0, readed);
					readed = is.read(buffer);
				}

				fos.close();

				LOG.info("Backup database is finished");

			} catch (IOException e) {
				if (process != null && process.getErrorStream() != null) {
					LOG.severe("Reading error stream backup");
					try {
						byte[] buffer = new byte[1000];
						int readed = process.getErrorStream().read(buffer);
						while (readed > 0) {
							String text = new String(buffer, 0, readed);
							LOG.severe(text);
							readed = process.getErrorStream().read(buffer);
						}
					} catch (IOException e1) {
						LOG.severe(e1.getLocalizedMessage());
					}

				}
				LOG.severe(e.getLocalizedMessage());
			}

			if (!this.closeMySql()) {
				LOG.severe("Unable to close database");
			}
		} else {
			LOG.severe("Unable to connect to database");
		}

		LOG.info("End backup");
		LOG.info("Time execute process: " + (System.currentTimeMillis() - start));
	}

	/**
	 * Method for execute scripts
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void executeScripts() throws ClassNotFoundException, SQLException {
		long start = System.currentTimeMillis();
		LOG.info("Start execute scripts ...");

		if (this.connectMysql()) {
			// Set autocommit to false
			this.connection.setAutoCommit(false);

			Statement currentStatement = this.connection.createStatement();
			// Read folder for files
			File folderFile = new File(this.configDB.getFolderExecuteScripts());

			if (folderFile.isDirectory()) {
				File[] arrScriptsExecute = folderFile.listFiles();
				// Sort by name
				Arrays.sort(arrScriptsExecute, new FileNameComparator());

				String sqlQuery = null;

				boolean scriptFailed = false;

				for (File script : arrScriptsExecute) {
					sqlQuery = this.readFile(script);
					try {
						currentStatement.execute(sqlQuery);
					} catch (Exception e) {
						LOG.severe("Error exeucte script " + script.getName());
						scriptFailed = true;
						break;
					}
				}

				if (!scriptFailed) {
					LOG.info("Commiting changes ...");
					try {
						this.connection.commit();
						LOG.info("Changes commited");
					} catch (Exception e) {
						LOG.severe("Error exeucte commit changes");
					}
				} else {
					LOG.info("Not commit changes beacause any script failed");
				}

			} else {
				LOG.severe("Folder script not found");
			}

			if (!this.closeMySql()) {
				LOG.severe("Unable to close database");
			}

		} else {
			LOG.severe("Unable to connect to database");
		}

		LOG.info("End execute scripts");
		LOG.info("Time execute process: " + (System.currentTimeMillis() - start));
	}

	/**
	 * Method for close mysql
	 * 
	 * @return true if close false if not
	 * @throws CloneNotSupportedException
	 * @throws SQLException
	 */
	private boolean closeMySql() throws SQLException {
		boolean valid = false;
		LOG.info("Start close database ...");
		if (!this.connection.isClosed()) {
			this.connection.close();
		}
		valid = this.connection.isClosed();
		LOG.info("End close database");
		return valid;
	}

	/**
	 * Method for connect to mysql
	 * 
	 * @return true if connect false if not
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private boolean connectMysql() throws ClassNotFoundException, SQLException {
		boolean valid = false;
		LOG.info("Start connect to database ...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.connection = DriverManager.getConnection(
				"jdbc:mysql://" + this.configDB.getHost() + ":" + this.configDB.getPort() + "/"
						+ this.configDB.getDatabase() + "?serverTimezone=UTC",
				this.configDB.getUser(), this.configDB.getPassword());
		valid = this.connection.isValid(5000);
		LOG.info("End connect to database");
		return valid;
	}

	/**
	 * Method for init config
	 * 
	 * @param args pass for command line
	 */
	public final void initConfig(String[] args) {
		LOG.info("Start config ...");
		this.configDB = ConfigDBUtils.initConfig(args);
		LOG.info("End config.");
	}

	/**
	 * Method for read file
	 * 
	 * @param file is file to read
	 * @return content for file
	 */
	private String readFile(File file) {
		String fileContent = null;
		try {
			byte[] bytes = Files.readAllBytes(file.toPath());
			fileContent = new String(bytes, "UTF-8");
		} catch (FileNotFoundException e) {
			LOG.severe(e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.severe(e.getLocalizedMessage());
		}
		return fileContent;
	}

	/**
	 * Method for get config db
	 * 
	 * @return config db
	 */
	public ConfigDB getConfigDB() {
		return configDB;
	}

}
