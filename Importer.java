package src;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Importer
{
    File [] files;
    private static final float SIZE_CONSTANT = 1024;
    private static final int FILES_BEFORE_COMMIT = 10;

    
    public static void main(String[] args){
    	Importer importer = new Importer();
    	
    	File folder = new File("C:/Users/Luis/Downloads/nyt/nyt/data/2000/01/01"); //folder where to search, just change here to search somewhere else
    	try{
    		importer.importDirectory(folder);
    		System.out.println("Finished ! ");
    	}catch(NullPointerException e){
    		System.out.println("Exception : " + e + " with file");
    	}
    }
    	
    
    public void importDirectory(File folder){ 
    	Parser parser = new Parser();
    	Database db = new Database();
    	
    	
    	try{
    	Class.forName("org.sqlite.JDBC");
    	}catch(ClassNotFoundException e){
    		System.out.println("Class not found : " + e);
    	}
    	
    	PreparedStatement preparedStatementTfs = null;
    	PreparedStatement preparedStatementDocs = null;
    	Connection connection = null;
    	
    	
    	try{
    		connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Luis/nyt.sqlite.db"); //address of sqlite database, change if somewhere else.
    		
	    	preparedStatementTfs = connection.prepareStatement("INSERT INTO TFS VALUES(?, ?, ?)"); //id, term, frequency
	    	preparedStatementDocs = connection.prepareStatement("INSERT INTO DOCS VALUES(?, ?, ?)"); //id, title, url, content
	    	
	    	connection.setAutoCommit(false); //commit manually, not automatic
	    	
    		int docsCount = 1;
        	File[] files = folder.listFiles();
        	

        	    for (File file : files) {
        	    	
        	      if (file.isFile() && file.getName().endsWith(".xml")) {      	   
        	    	  
        	    	  Document doc = parser.parseAndStem(file, docsCount);
        	    	  
        	    	  preparedStatementTfs = db.addStatementsOfDocument(doc, preparedStatementTfs); // count Terms and add statements to preparedStatement
        	    	  preparedStatementDocs = db.addDocsStatements(doc, preparedStatementDocs);
        	    	  
        	    	  System.out.println("Document " + doc.getId() + " (" + file.getName() +  ", length : " + file.length() / SIZE_CONSTANT + ") parsed and added to statement"); // to remove when doing research
        	    	  
        	    	  if(docsCount % FILES_BEFORE_COMMIT == 0){ // files size to commit
          	    		  try{
      	    	    		 preparedStatementTfs.executeBatch();
      	    	    		 preparedStatementDocs.executeBatch();
      	    	    		
      	    	    		  connection.commit();
      	    	    		  System.out.println(docsCount + " documents successfully commited to database.");
      	    	    		  
      	    	    		  preparedStatementTfs.clearBatch();
      	    	    		preparedStatementDocs.clearBatch();
      	    	    		
          	    		  }catch(SQLException e){
          	    			  System.out.println("SQL Exception : " + e);
          	    		  }
          	    	  }
        	    	  
        	    	  docsCount++; //increment document count

        	    	  
        	      }else if(file.isDirectory()){
        	    	  importDirectory(file);
        	      	}
        	      
        	    }
    	
    	}catch(SQLException e){
    		System.out.println("exception : " + e);
    	}

    }
    
}