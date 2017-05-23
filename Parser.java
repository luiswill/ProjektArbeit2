package src;

import java.io.File;
import java.util.ArrayList;

import parserSrc.NYTCorpusDocument;

import stem.ext.englishStemmer;



public class Parser extends parserSrc.NYTCorpusDocumentParser
{
//	public static void main(String[] args){
//		File folder = new File("C:/Users/Luis/Downloads/nyt/nyt/data/2000/02/01/1172968.xml");
//		Parser p = new Parser();
//		p.parseAndStem(folder);
//	}

    public Document parseAndStem(File file, int docId){
    	
    	
    	
    	 englishStemmer stemmer = new englishStemmer();

         
         NYTCorpusDocument doc = parseNYTCorpusDocumentFromFile(file, false);
         
         String headline = doc.getHeadline(); //get headline
         String url = doc.getUrl().toString();

         
         String content = removeNotChar(doc.getBody()).toLowerCase(); //first get body, then remove everything which is not char and then lowercase()
         String[] words = content.split(" ");
         
         
         for(int i = 0; i < words.length; i++){
        	 stemmer.setCurrent(words[i]); //stem each word
             if (stemmer.stem()){ 
            	 words[i] = stemmer.getCurrent();
             }
         }
         
		return new Document(docId, headline, url, words); // create a new document with properties
    }
    


	private String removeNotChar(String content){
    	String newContent = "";
    	for (char ch: content.toCharArray()) {
    		if(!Character.isLetterOrDigit(ch) && !Character.isSpaceChar(ch)){
    			ch = ' ';
    			
    		}else{
    			newContent += ch;
    		}
    	}
    	return newContent;
    }
}