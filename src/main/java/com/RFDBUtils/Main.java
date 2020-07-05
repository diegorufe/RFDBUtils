package com.RFDBUtils;

import java.sql.SQLException;

import com.RFDBUtils.factories.RFDBFactory;

/**
 * 
 * @author diego
 *
 */
public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		RFDBFactory rfdbFactory = new RFDBFactory();
		// Star app
		rfdbFactory.start(args);
	}
}
