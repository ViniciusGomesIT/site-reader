package br.com.vinicius.service;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.vinicius.enums.ConfigEnum;
import br.com.vinicius.enums.MessagesEnum;
import br.com.vinicius.enums.TagElementsEnum;
import br.com.vinicius.util.FileBuilder;
import br.com.vinicius.util.Util;

public class SiteReaderService {
	
	private String urlComplement;

	private static final String ID_ELEMENT_TABLE = "cp_advisory_table_sorter";
	private static final String FIELD_SEPARATOR = ";";
	private static final String LINE_SEPARATOR = "\n";
	private static final String HREF_PROPERTIE = "href";
	
	private boolean canContinue;
	private boolean isFirstPage;
	
	private StringBuilder dataBuilder;
	private FileBuilder fileBuilder;	
	private Util util;

	private int cont;
	
	public SiteReaderService() {
		this.dataBuilder = new StringBuilder();
		this.fileBuilder = new FileBuilder();
		this.util = new Util();
		this.cont = 1;
		this.canContinue = false;
		this.isFirstPage = true;
		this.urlComplement = ConfigEnum.URL_COMPLEMENT.getValue();
	}
	
	public void getDataFromDom() throws IOException {			
		
		util.printStartInfos( fileBuilder.getFilePath() );
				
		do {			
			util.printConsole( MessagesEnum.READING_PAGE_MESSAGE.getValue() + cont );
			
			Document domDocument = util.getDom( ConfigEnum.BASE_URL.getValue(), urlComplement, ConfigEnum.CONNECTION_TIMEOUT.getValue() );
			
			Element fullTableElement = domDocument.getElementById( ID_ELEMENT_TABLE );
			
			if (isFirstPage) {				
				getHeadTitles(fullTableElement);
				isFirstPage = false;
			}

			getFields(fullTableElement);

			getUrlNextPage(domDocument);
			
			if (!canContinue) {					
				util.printConsole( MessagesEnum.LAST_PAGE_REACHED_MESSAGE.getValue() );
			}
			
			cont++;
			
		} while ( canContinue );
		
		util.generateOutputFile(dataBuilder, this.fileBuilder);
	}

	private void getUrlNextPage(Document domDocument) {
		Elements paginationElements = domDocument.getElementsByClass( TagElementsEnum.PAGINATION_TAG.getValue() );

		for (Element pagination : paginationElements) {

			for (Element element : pagination.getElementsByTag( TagElementsEnum.A_TAG.getValue() )) {

				if (element.text().contains( TagElementsEnum.NEXT_PAGE_TAG.getValue() )) {

					urlComplement = element.attr( HREF_PROPERTIE );
					canContinue = true;
					
				} else {
					canContinue = false;
				}
			}
		}
	}

	private void getFields(Element fullTableElement) {
		Elements tBodyElements = fullTableElement.getElementsByTag( TagElementsEnum.TBODY_TAG.getValue() );

		for (Element bodyElement : tBodyElements) {

			getFieldsData(bodyElement);
		}
	}

	private void getFieldsData(Element bodyElement) {
		Elements trElements = bodyElement.getElementsByTag( TagElementsEnum.TR_TAG.getValue() );

		for (Element trEle : trElements) {
			addTextToBuilder( LINE_SEPARATOR );

			Elements thElements = trEle.getElementsByTag( TagElementsEnum.TD_TAG.getValue() );

			for (Element thEle : thElements) {

				addTextToBuilder( util.getValueFromElement(thEle).trim() );
				addTextToBuilder( FIELD_SEPARATOR );
			}
		}
	}

	private void getHeadTitles(Element fullTableElement) {
		Elements tHeadElements = fullTableElement.getElementsByTag( TagElementsEnum.THEAD_TAG.getValue() );

		for (Element tHead : tHeadElements) {

			Elements thElements = tHead.getElementsByTag( TagElementsEnum.TH_TAG.getValue() );

			for (Element thEle : thElements) {

				dataBuilder.append( util.getValueFromElement(thEle) );
				dataBuilder.append( FIELD_SEPARATOR );
			}
		}
		
		dataBuilder.append( LINE_SEPARATOR );
	}

	private void addTextToBuilder(String text) {
		dataBuilder.append(text);
	}
}
