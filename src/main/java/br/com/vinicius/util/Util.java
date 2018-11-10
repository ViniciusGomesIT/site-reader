package br.com.vinicius.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
}
