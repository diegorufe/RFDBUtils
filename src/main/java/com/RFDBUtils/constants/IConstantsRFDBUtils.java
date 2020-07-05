package com.RFDBUtils.constants;

import java.text.SimpleDateFormat;

/**
 * Interfaces constans for app
 * 
 * <p>
 * 
 * Constants
 * 
 * <ul>
 * 
 * <li>{@link #MYSQL_DUMP_EXE}</li>
 * <li>{@link #MYSQL_EXE_USER}</li>
 * <li>{@link #MYSQL_EXE_PASSWORD}</li>
 * <li>{@link #MYSQL_DUMP_EXE_CREATE_DROP_DATABASE}</li>
 * <li>{@link #MYSQL_DUMP_EXE_DATABASES}</li>
 * <li>{@link #MYSQL_DUMP_EXE_FILENAME}</li>
 * <li>{@link #MYSQL_DUMP_EXE_FILENAME_EXT}</li>
 * <li>{@link #MYSQL_DUMP_EXE_DATE_FORMAT}</li>
 * <li>{@link #EQUAL_SEPARATOR}</li>
 * <li>{@link #MYSQL_SAVEPOINT_NAME}</li>
 * 
 * </ul>
 * 
 * @author diego
 *
 */
public interface IConstantsRFDBUtils {
	public static final String MYSQL_DUMP_EXE = "mysqldump";
	public static final String MYSQL_EXE_USER = "-u";
	public static final String MYSQL_EXE_PASSWORD = "-p";
	public static final String MYSQL_DUMP_EXE_CREATE_DROP_DATABASE = "--add-drop-database";
	public static final String MYSQL_DUMP_EXE_DATABASES = "--databases";
	public static final String MYSQL_DUMP_EXE_FILENAME = "RFDBUtilsDump";
	public static final String MYSQL_DUMP_EXE_FILENAME_EXT = ".sql";
	public static final SimpleDateFormat MYSQL_DUMP_EXE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
	public static final String EQUAL_SEPARATOR = "=";
	public static final String MYSQL_SAVEPOINT_NAME = "savePointExecuteScripts";
}
