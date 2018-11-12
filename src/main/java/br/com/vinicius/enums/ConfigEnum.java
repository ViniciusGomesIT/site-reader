package br.com.vinicius.enums;

public enum ConfigEnum {

	BASE_URL("https://www.XXXX.com"),
	URL_COMPLEMENT("/XXXX/"),
	CONNECTION_TIMEOUT("60000");
	
	public String value;
	
	ConfigEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
