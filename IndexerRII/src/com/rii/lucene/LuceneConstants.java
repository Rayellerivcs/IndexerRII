package com.rii.lucene;
//test

/*
 Classe usada para fornecer várias contantes
 a serem usadas em toda a aplicação
 */
public enum LuceneConstants {

	FILE_PATH ("filepath"),
	FILE_NAME ("filename"),
	CONTENT ("content");
	
	
	//public static final int MAX_SEARCH = 100;
	
	public String valor;
	
	private LuceneConstants(String valor) {
		
		this.valor = valor;
	}
}
