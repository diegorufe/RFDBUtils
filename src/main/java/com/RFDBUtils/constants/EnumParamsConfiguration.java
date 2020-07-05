package com.RFDBUtils.constants;

/**
 * Enum params configuration
 * 
 * <p>
 * 
 * Params
 * 
 * <ul>
 * 
 * <li>{@link #DATABASE}</li>
 * <li>{@link #FOLDER_SCRIPTS}</li>
 * <li>{@link #HOST}</li>
 * <li>{@link #PASSWORD}</li>
 * <li>{@link #PORT}</li>
 * <li>{@link #USER}</li>
 * 
 * </ul>
 * 
 * @author diego
 *
 */
public enum EnumParamsConfiguration {

	DATABASE("database"),

	FOLDER_SCRIPTS("folderScripts"),

	HOST("host"),

	PASSWORD("password"),

	PORT("port"),

	USER("user"),

	UNDEFINED("");

	private String value;

	private EnumParamsConfiguration(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Method for convert by value
	 * 
	 * @param value from the enum
	 * @return EnumParamsConfiguration is found UNDEFINED if not
	 */
	public static EnumParamsConfiguration convert(String value) {
		EnumParamsConfiguration enumParams = EnumParamsConfiguration.UNDEFINED;
		if (value != null) {
			for (EnumParamsConfiguration enumParamsCompare : values()) {
				if (enumParamsCompare.getValue().equalsIgnoreCase(value)) {
					enumParams = enumParamsCompare;
					break;
				}
			}
		}
		return enumParams;
	}
}
