package src;

import java.util.Hashtable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database{

 private void prepareStatement(long docId, String k, int v, PreparedStatement p){	 
	 try{
		 p.setLong(1, docId);
		 p.setString(2, k);
		 p.setInt(3, v);
		 p.addBatch();
	 }catch(SQLException e){
		 System.out.println("SQL Error : " + e);
	 }
 }
 
 
 public PreparedStatement addDocsStatements(Document doc, PreparedStatement p){
	//Prepared statement like id, title, url, content
	 try{
		 p.setLong(1, doc.getId());
		 p.setString(2, doc.getTitel());
		 p.setString(3, doc.getUrl());
		 p.addBatch();

	 }catch(SQLException e){
		 System.out.println("SQL Error : " + e);
	 }
	 
	 return p;
 }
 
 
 
 public PreparedStatement addStatementsOfDocument(Document doc, PreparedStatement p){ 
	 String[] terms = doc.getContent(); // get all terms from document in String[] terms.

	 Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
	 
	 for(String c : terms){
		 if(ht.containsKey(c)){ //if term already exists, increment 1
			 ;
			 ht.replace(c, ht.get(c) + 1);
		 }else{ //otherwise create term with value 1
			 ht.put(c, 1);
		 }
	 }
	 
	 
	 ht.forEach((k, v) -> this.prepareStatement(doc.getId(), k, v, p)); // put all term with their frequencies of the hashtable in the preparedStatement.
	 
	 return p; //return statement
 }
 
 
 
}