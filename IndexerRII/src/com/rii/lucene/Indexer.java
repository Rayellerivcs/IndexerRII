package com.rii.lucene;
//test

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

   private IndexWriter indexWriter;

   public Indexer (String indexDirectoryPath) throws IOException {
	   
	   Path path = Paths.get(indexDirectoryPath);
	   
	   //this directory will contain the indexes
	   Directory directory = FSDirectory.open(path); 
       
	   //create the indexer
	   indexWriter = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
	  
   }

   private Document getDocument(File file) throws IOException{
      Document document = new Document();
      
      //index file name
      FieldType fileNameType = new FieldType();
      fileNameType.setStored(true);
      fileNameType.setTokenized(true);

      //index file contents
      FieldType contentFieldType = new FieldType();
      contentFieldType.setStored(true);
      contentFieldType.setTokenized(true);
      contentFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
      
     // Field contentField = new Field (LuceneConstants.CONTENT.valor, FileUtils.readFileToString(file), contentFieldType);
      
      //index file path
      Field filePathField = new Field(LuceneConstants.FILE_PATH.valor, file.getCanonicalPath(), fileNameType);
      
      //document.add(contentField);
     // document.add(fileNameType);
      document.add(filePathField);

      return document;
   }   

   private void indexFile(File file) throws IOException{
      System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
      indexWriter.addDocument(document);
   }

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws IOException{
      //get all files in the data directory
      File[] files = new File(dataDirPath).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
         ){
            indexFile(file);
         }
      }
      return indexWriter.numDocs();
   }
}