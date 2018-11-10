package br.com.vinicius.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.vinicius.util.FileBuilder;

public class SiteReaderService {

	private static final String URL = "https://www.checkpoint.com";
	private static String URL_COMPLEMENT = "/advisories/";

	private static final String THEAD_TAG = "thead";
	private static final String TBODY_TAG = "tbody";
	private static final String TH_TAG = "th";
	private static final String TR_TAG = "tr";
	private static final String TD_TAG = "td";
	private static final String A_TAG = "a";

	private static final String PAGINATION_TAG = "pagination";
	private static final String MESSAGE_NEXT_PAGE = "Next Page";
	private static final String ID_ELEMENT_TABLE = "cp_advisory_table_sorter";

	private static final String FIELD_SEPARATOR = ";";
	private static final String LINE_SEPARATOR = "\n";

	private static final String HREF_PROPERTIE = "href";
	
	private static final int CONNECTION_TIMEOUT = 60000;

	private boolean canContinue;
	
	private StringBuilder dataBuilder;
	private StringBuilder headerBuilder;	
	private FileBuilder fileBuilder;

	private int cont;
	
	public SiteReaderService() {
		this.dataBuilder = new StringBuilder();
		this.headerBuilder = new StringBuilder();
		this.fileBuilder = new FileBuilder();
		this.cont = 1;
		this.canContinue = false;
	}
	
	public void getDataFromDom() throws IOException {	
		
		startInfos();
				
		do {			
			printConsole("Reading page: " + cont);
			
			Document domDocument = getDom();

			Element fullTableElement = domDocument.getElementById( ID_ELEMENT_TABLE );

			getFields(fullTableElement);

			getUrlNextPage(domDocument);
			
			if (!canContinue) {
				getHeadTitles(fullTableElement);
				
				printConsole("The last page was read");
			}
			
			cont++;
			
		} while ( canContinue );
		
		generateOutputFile(headerBuilder, dataBuilder);
	}

	private void startInfos() {
		printConsole("------------------------------------------");
		printConsole("----------------- Setup ------------------");
		printConsole("URL: " + URL.concat(URL_COMPLEMENT));
		printConsole("TIMEOUT: " + CONNECTION_TIMEOUT / 1000 + " seconds");
		printConsole("OUTPUT FILE: " + fileBuilder.file.getAbsolutePath());
		printConsole("---- WARNING ----");
		printConsole("Sometimes it may take some time to process.");
		printConsole("------------------------------------------");
		
	} 
	
	private void printConsole(String message){
		System.out.println(message);
		System.out.println();	
	}

	private void generateOutputFile(StringBuilder headerBuilder, StringBuilder dataBuilder) {
		printConsole("Generating output file");
		
		StringBuilder fullDataBuilder = new StringBuilder();
		
		fullDataBuilder.append( headerBuilder.toString() );
		fullDataBuilder.append( LINE_SEPARATOR );
		fullDataBuilder.append( dataBuilder.toString() );
		
		fileBuilder.fileWriter(fullDataBuilder.toString());
	
		printConsole("Output File was generated..");
	}

	private void getUrlNextPage(Document domDocument) {
		Elements paginationElements = domDocument.getElementsByClass( PAGINATION_TAG );

		for (Element pagination : paginationElements) {

			for (Element element : pagination.getElementsByTag( A_TAG )) {

				if (element.text().contains( MESSAGE_NEXT_PAGE )) {

					URL_COMPLEMENT = element.attr( HREF_PROPERTIE );
					canContinue = true;
				} else {
					canContinue = false;
				}
			}
		}
	}

	private void getFields(Element fullTableElement) {
		Elements tBodyElements = fullTableElement.getElementsByTag( TBODY_TAG );

		for (Element bodyElement : tBodyElements) {

			getFieldsData(bodyElement);
		}
	}

	private void getFieldsData(Element bodyElement) {
		Elements trElements = bodyElement.getElementsByTag( TR_TAG );

		for (Element trEle : trElements) {
			addTextToBuilder( LINE_SEPARATOR );

			Elements thElements = trEle.getElementsByTag( TD_TAG );

			for (Element thEle : thElements) {

				addTextToBuilder( getValueFromElement(thEle) );
				addTextToBuilder( FIELD_SEPARATOR );
			}
		}
	}

	private void getHeadTitles(Element fullTableElement) {
		Elements tHeadElements = fullTableElement.getElementsByTag( THEAD_TAG );

		for (Element tHead : tHeadElements) {

			Elements thElements = tHead.getElementsByTag( TH_TAG );

			for (Element thEle : thElements) {

				headerBuilder.append( getValueFromElement(thEle) );
				headerBuilder.append( FIELD_SEPARATOR );
			}
		}
	}

	private void addTextToBuilder(String text) {
		dataBuilder.append(text);
	}

	private String getValueFromElement(Element element) {
		return element.text();
	}

	private Document getDom() throws IOException {
		return Jsoup.connect( URL.concat(URL_COMPLEMENT) ).timeout( CONNECTION_TIMEOUT ).get();
	}
}
