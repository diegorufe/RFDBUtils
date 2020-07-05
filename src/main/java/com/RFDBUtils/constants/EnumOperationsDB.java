package com.RFDBUtils.constants;

/**
 * Enum class for operations db
 * 
 * <p>
 * 
 * Operations
 * 
 * <ul>
 * 
 * <li>{@link #BACKUP}</li>
 * <li>{@link #EXECUTE_SCRIPTS}</li>
 * <li>{@link #HELP}</li>
 * <li>{@link #OPTIONS}</li>
 * 
 * </ul>
 * 
 * @author diego
 *
 */
public enum EnumOperationsDB {

	BACKUP("backup"),

	EXECUTE_SCRIPTS("executeScripts"),

	HELP("help"),

	OPTIONS("options"),

	UNDEFINED("");

	private String value;

	private EnumOperationsDB(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Method for convert by value
	 * 
	 * @param value from the enum
	 * @return EnumOperationsDB is found UNDEFINED if not
	 */
	public static EnumOperationsDB convert(String value) {
		EnumOperationsDB enumOperationsDB = EnumOperationsDB.UNDEFINED;
		if (value != null) {
			for (EnumOperationsDB enumOperationsDBCompare : values()) {
				if (enumOperationsDBCompare.getValue().equalsIgnoreCase(value)) {
					enumOperationsDB = enumOperationsDBCompare;
					break;
				}
			}
		}
		return enumOperationsDB;
	}
}
