package br.com.vinicius.enums;

public enum MessagesEnum {

	GENERATING_FILE_MESSAGE("Generating output file.."),
	FILE_GENERATED_MESSAGE("Output File was generated.."),
	WARNING_TIME_PROCESS_MESSAGE("Sometimes it may take some time to process."),
	LAST_PAGE_REACHED_MESSAGE("The last page was read."),
	READING_PAGE_MESSAGE("Reading page: "),
	EXCEPTION_PROCESSING_MESSAGE("There had a problem while processing page: "),
	EXCEPTION_WRITE_FILE_MESSAGE("There had a problem while write in output file: ");
	
	public String value;
	
	MessagesEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
