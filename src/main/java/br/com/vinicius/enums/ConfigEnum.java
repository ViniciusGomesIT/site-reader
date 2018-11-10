package br.com.vinicius.enums;

public enum ConfigEnum {

	BASE_URL("https://www.checkpoint.com"),
	URL_COMPLEMENT("/advisories/"),
	CONNECTION_TIMEOUT("60000");
	
	public String value;
	
	ConfigEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
