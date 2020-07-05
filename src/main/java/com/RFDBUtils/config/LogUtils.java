package com.RFDBUtils.config;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class utilities for log
 * 
 * @author diego
 *
 */
public final class LogUtils {

	/**
	 * Method for set log for application
	 */
	public static void initLog() {
		String path = LogUtils.class.getClassLoader().getResource("logging.properties").getFile();
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			LogManager.getLogManager().readConfiguration(fis);
		} catch (Exception e) {
		}
		// System.setProperty("java.util.logging.config.file", path);
		LogUtils.getLogger(LogUtils.class.getSimpleName()).info("Log is started");
	}

	/**
	 * Method for get logger wiht tag
	 * 
	 * @param tag is tag for logger
	 * @return logger for tag
	 */
	public static Logger getLogger(String tag) {
		return Logger.getLogger(tag);
	}
}
