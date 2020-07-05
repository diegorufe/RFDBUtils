package com.RFDBUtils.config;

import com.RFDBUtils.constants.EnumOperationsDB;
import com.RFDBUtils.constants.EnumParamsConfiguration;
import com.RFDBUtils.constants.IConstantsRFDBUtils;

/**
 * Utils for load configuration for db utils
 * 
 * @author diego
 *
 */
public final class ConfigDBUtils {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 3306;
	private static final String DEFAULT_USER = "root";
	private static final String DEFAULT_PASSWORD = "root";
	private static final String DEFAULT_DATABASE = "test";
	private static final EnumOperationsDB DEFAULT_OPERATION = EnumOperationsDB.BACKUP;
	private static final String DEFAULT_PATH_BACKUP = "";
	private static final boolean DEFAULT_CREATE_DROP_DATABASE = true;

	/**
	 * Mhetod for create default config
	 * 
	 * @return default config
	 */
	public static ConfigDB defaultConfig() {
		ConfigDB configDB = new ConfigDB();
		configDB.setHost(DEFAULT_HOST);
		configDB.setPort(DEFAULT_PORT);
		configDB.setUser(DEFAULT_USER);
		configDB.setPassword(DEFAULT_PASSWORD);
		configDB.setDatabase(DEFAULT_DATABASE);
		configDB.setEnumOperationsDB(DEFAULT_OPERATION);
		configDB.setPathBackup(DEFAULT_PATH_BACKUP);
		configDB.setCreateDropDatabase(DEFAULT_CREATE_DROP_DATABASE);
		return configDB;
	}

	/**
	 * Method for parse configuration line commands
	 * 
	 * @param configDB to set data
	 * @param args     to be parsed
	 */
	private static void parseConfig(ConfigDB configDB, String[] args) {

		EnumOperationsDB enumOperationsDB = EnumOperationsDB.UNDEFINED;
		EnumParamsConfiguration enumParamsConfiguration = EnumParamsConfiguration.UNDEFINED;
		String[] argDbParsed = null;

		if (args != null) {
			for (String argDb : args) {

				// Find operation
				if (enumOperationsDB.equals(EnumOperationsDB.UNDEFINED)) {
					enumOperationsDB = EnumOperationsDB.convert(argDb);
				}

				// Find params
				argDbParsed = argDb.split(IConstantsRFDBUtils.EQUAL_SEPARATOR);
				enumParamsConfiguration = EnumParamsConfiguration.convert(argDbParsed[0]);

				if (!enumParamsConfiguration.equals(EnumParamsConfiguration.UNDEFINED)) {
					switch (enumParamsConfiguration) {

					case DATABASE:
						configDB.setDatabase(argDbParsed[1]);
						break;

					case FOLDER_SCRIPTS:
						configDB.setFolderExecuteScripts(argDbParsed[1]);
						break;

					case HOST:
						configDB.setHost(argDbParsed[1]);
						break;

					case PASSWORD:
						configDB.setPassword(argDbParsed[1]);
						break;

					case PORT:
						configDB.setPort(Integer.parseInt(argDbParsed[1]));
						break;

					case USER:
						configDB.setUser(argDbParsed[1]);
						break;

					default:
						break;
					}
				}

			}

		}

		configDB.setEnumOperationsDB(enumOperationsDB);
	}

	/**
	 * Method for create configuratión
	 * 
	 * @param args pass command line for create configuratión
	 * @return config with command line args applied
	 */
	public static ConfigDB initConfig(String[] args) {
		ConfigDB configDB = ConfigDBUtils.defaultConfig();
		ConfigDBUtils.parseConfig(configDB, args);
		return configDB;
	}

}
