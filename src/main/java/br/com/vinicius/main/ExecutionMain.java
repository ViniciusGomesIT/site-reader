package br.com.vinicius.main;

import java.io.IOException;

import br.com.vinicius.enums.MessagesEnum;
import br.com.vinicius.service.SiteReaderService;

public class ExecutionMain {
	
	private static SiteReaderService service = new SiteReaderService();
	
	public static void main(String[] args) {
		
		try {
			service.getDataFromDom();
			
		} catch (IOException e) {
			System.out.println( MessagesEnum.EXCEPTION_PROCESSING_MESSAGE.getValue() + e.getMessage() + e );
		}
	}
}
