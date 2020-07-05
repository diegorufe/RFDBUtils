package com.RFDBUtils.config;

import com.RFDBUtils.constants.EnumOperationsDB;

/**
 * Class for configuration for db utils
 * 
 * @author diego
 *
 */
public class ConfigDB {

	private String host;
	private int port;
	private String user;
	private String password;
	private String database;
	private EnumOperationsDB enumOperationsDB;
	private String pathBackup;
	private boolean createDropDatabase;
	private String folderExecuteScripts;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public EnumOperationsDB getEnumOperationsDB() {
		return enumOperationsDB;
	}

	public void setEnumOperationsDB(EnumOperationsDB enumOperationsDB) {
		this.enumOperationsDB = enumOperationsDB;
	}

	public String getPathBackup() {
		return pathBackup;
	}

	public void setPathBackup(String pathBackup) {
		this.pathBackup = pathBackup;
	}

	public boolean isCreateDropDatabase() {
		return createDropDatabase;
	}

	public void setCreateDropDatabase(boolean createDropDatabase) {
		this.createDropDatabase = createDropDatabase;
	}

	public String getFolderExecuteScripts() {
		return folderExecuteScripts;
	}

	public void setFolderExecuteScripts(String folderExecuteScripts) {
		this.folderExecuteScripts = folderExecuteScripts;
	}

}
