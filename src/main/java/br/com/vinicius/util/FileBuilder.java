package br.com.vinicius.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.vinicius.enums.MessagesEnum;

public class FileBuilder {	
	private static final String DIRECTORY_SAPARATOR = "\\";
	private static final String FILE_EXTENSION = ".csv";
	private static final String NAME_SEPARATOR = "_";
	private static final String FOLDER_NAME = "checkpoint-report";
	
	private String data = null;
	public File file = null;
	private DirectoryBuilder directoryBuilder;	
	
	public FileBuilder() {
		this.directoryBuilder = new DirectoryBuilder();
		this.data = new SimpleDateFormat("dd-MM-yyyy").format( new Date(System.currentTimeMillis()) );
		this.file = new File( directoryBuilder.createDirectory( FOLDER_NAME )
				.concat( DIRECTORY_SAPARATOR )
				.concat( FOLDER_NAME )
				.concat( NAME_SEPARATOR )
				.concat( data )
				.concat( FILE_EXTENSION ) );
	}

	public void fileWriter(String message) {			
		try {		
			
			BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(file, false) );
			
			bufferedWriter.write(message);
			bufferedWriter.flush();
			bufferedWriter.close();					
			
		} catch (IOException e) {
			throw new RuntimeException( MessagesEnum.EXCEPTION_WRITE_FILE_MESSAGE.getValue() + e.getMessage() );			
		} 
	}
}