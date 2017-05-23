package src;

public class Document
{
    private long id;
    private String titel;
    private String url;
    private String [] content;
    private static final String MSG_WRONG_ID = "";
    private static final String MSG_TITEL = "";
    private static final String MSG_URL = "";
    private static final String MSG_CONTENT = "";

    
    
    public Document(long id, String titel, String url, String[] content)
    {
       check(id > 0, MSG_WRONG_ID);
       check(titel.trim().length() > 0, MSG_TITEL);
       check(url.trim().length() > 15, MSG_URL);
       check(content.length > 0, MSG_CONTENT);
       this.id = id;
       this.titel = titel;
       this.url = url;
       this.content = content;
       
    }

    public long getId(){
        return id;
    }
    
    public String getTitel(){
        return titel;
    }
    
    public String getUrl(){
        return url;
    }
    
    public String [] getContent(){
        return content;
    }
    
    public void setId(long newId){
        this.id = newId;
    }
    
    public void setTitel(String newTitel){
        this.titel = newTitel;
    }
    
    public void setUrl(String newUrl){
        this.url = newUrl;
    }
    
    public void setContent(String [] newContent){
        this.content = newContent;
    }
    
    public void check(boolean condition, String msg){
        if (!condition){
            System.out.println(msg);
        }
    }
}