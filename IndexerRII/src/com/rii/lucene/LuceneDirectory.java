package com.rii.lucene;
//test

public enum LuceneDirectory {

	INDEX_DIR("C:\\lucene-6.6.1\\Index"),
	DATA_DIR("C:\\lucene-6.6.1\\Data");
	
	public String valor;
	
	LuceneDirectory(String valor){
		this.valor = valor;
	}
}
