package br.com.vinicius.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.vinicius.enums.ConfigEnum;
import br.com.vinicius.enums.MessagesEnum;

public class Util {

	public String getValueFromElement(Element element) {
		return element.text();
	}
	
	public Document getDom(String url, String complement, String timeout) throws IOException {
		return Jsoup.connect( url.concat(complement) )
				.timeout( Integer.parseInt(timeout) )
				.get();
	}
	
	public Integer getTimeOutInSecondsFromString(String timeout) {
		return Integer.parseInt(timeout) / 1000;
	}
	
	public void printConsole(String message){
		System.out.println(message);
		System.out.println();	
	}
	
	public void printStartInfos(String path) {
		printConsole("------------------------------------------");
		printConsole("----------------- Setup ------------------");
		printConsole("URL: " + ConfigEnum.BASE_URL.getValue().concat(ConfigEnum.URL_COMPLEMENT.getValue()));
		printConsole("TIMEOUT: " + getTimeOutInSecondsFromString( ConfigEnum.CONNECTION_TIMEOUT.getValue() ) + " seconds");
		printConsole("OUTPUT FILE: " + path);
		printConsole("---- WARNING ----");
		printConsole( MessagesEnum.WARNING_TIME_PROCESS_MESSAGE.getValue() );
		printConsole("------------------------------------------");
	} 
	
	public void generateOutputFile(StringBuilder dataBuilder, FileBuilder fileBuilder) {
		printConsole( MessagesEnum.GENERATING_FILE_MESSAGE.getValue() );
		
		fileBuilder.fileWriter(dataBuilder.toString());
	
		printConsole( MessagesEnum.FILE_GENERATED_MESSAGE.getValue() );
	}

}
