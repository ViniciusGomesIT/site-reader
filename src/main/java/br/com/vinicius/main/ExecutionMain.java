package br.com.vinicius.main;

import java.io.IOException;

import br.com.vinicius.service.SiteReaderService;

public class ExecutionMain {
	
	private static SiteReaderService service = new SiteReaderService();
	
	public static void main(String[] args) {
		
		try {
			service.getDataFromDom();
			
		} catch (IOException e) {
			System.out.println("There had a problem while processing page: " + e.getMessage() + e);
		}
	}
}
