package br.com.vinicius.enums;

public enum TagElementsEnum {

	THEAD_TAG("thead"),
	TBODY_TAG("tbody"),
	TH_TAG("th"),
	TR_TAG("tr"),
	TD_TAG("td"),
	A_TAG("a"),
	PAGINATION_TAG("pagination"),
	NEXT_PAGE_TAG("Next Page");
	
	public String value;

	TagElementsEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
