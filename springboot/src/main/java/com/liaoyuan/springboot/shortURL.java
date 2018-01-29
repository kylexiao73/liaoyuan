package com.liaoyuan.springboot;

import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException; 
import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



public class shortURL {
	 MongoClient mongoClient;
	 MongoDatabase database;
	 MongoCollection<Document> collection;
	 JSONArray jsonArray = new JSONArray();
	 
	 String longURL;
	 String shorterURL = "";
	 
	public void connect() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		database = mongoClient.getDatabase("urlDB");
        collection = database.getCollection("test") ;
		
		
	}
	
	 public static String  setupURL(String currentURL){
		 long countOfCurrentURL = collection.count();
		 String shortURL = "";
		 if(countOfCurrentURL<=26){
	      	String s = "";
            s = (char) ('a' + countOfCurrentURL -1) + s;
            return "11111"+s;
		 }
	        
		 if(countOfCurrentURL>26 && countOfCurrentURL<=52){

			String s = "";
            s = (char) ('A' + countOfCurrentURL -27) + s;
            return "11111"+s;
         }
		 else{
			String s = "Z";
            String str = String.format("%5d", countOfCurrentURL-53).replace(" ", "0");
            return str+s;
            
		 }
		 
	 }
	public void run() throws IOException, InterruptedException {
		String longURL = "http://www.liaoyuan.io/okokokokokokok";

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        database = mongoClient.getDatabase("urlDB");
        collection = database.getCollection("tests");
        
        System.out.println("In database: "+collection.count() + "number of urls in database");

        Document testDoc = collection.find(eq("longURL", longURL)).first();

        if (testDoc!=null) {
            System.out.println("URL already exists");
            JSONObject jsonObj = new JSONObject(testDoc.toJson());
            
            System.out.println("shortURL is :" + jsonObj.get("shortURL"));
            shorterURL = jsonObj.get("shortURL").toString();

        } else {

            //create a new short URL and insert into collection
            String shortURL = setupURL(longURL);
            Document doc = new Document("longURL", longURL).append("shortURL", shortURL);
            collection.insertOne(doc);
            
            Document myDoc = collection.find(eq("longURL", longURL)).first();

            if (myDoc != null) System.out.println("result : " + myDoc.toJson());

            JSONObject jsonObj = new JSONObject(myDoc.toJson());
            System.out.println("shortURL is :" + jsonObj.get("shortURL"));
            shorterURL = jsonObj.get("shortURL").toString();
            
        }
            

		
	}
	
	@RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/");

    }

	    
	 /*
	  * 以下为另一种用MD5生成shortURL的加密方法：
	public static String getKeyedDigest(String strSrc, String key) {  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            md5.update(strSrc.getBytes("UTF8"));    
            String result="";  
            byte[] temp;  
            temp=md5.digest(key.getBytes("UTF8"));  
            for (int i=0; i<temp.length; i++){  
                result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);  
                }             
            return result;  
        } 
        catch (NoSuchAlgorithmException e) {   
            e.printStackTrace();  
        }
        catch(Exception e){  
          e.printStackTrace();  
        }  
        return null;  
    } 
	
	public static String[] ShortText(String string){ 
        String key = "Liaoyuan";                 //生成MD5加密字符串的KEY 
        String[] chars = new String[]{          
            "a","b","c","d","e","f","g","h", 
            "i","j","k","l","m","n","o","p", 
            "q","r","s","t","u","v","w","x", 
            "y","z","0","1","2","3","4","5", 
            "6","7","8","9","A","B","C","D", 
            "E","F","G","H","I","J","K","L", 
            "M","N","O","P","Q","R","S","T", 
            "U","V","W","X","Y","Z" 
        }; 
        String hex = getKeyedDigest(string,key);
        int hexLen = hex.length(); 
        int subHexLen = hexLen / 8; 
        String[] ShortStr = new String[4]; 
         
        for (int i = 0; i < subHexLen; i++) { 
            String outChars = ""; 
            int j = i + 1; 
            String subHex = hex.substring(i * 8, j * 8); 
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16); 
             
            for (int k = 0; k < 6; k++) { 
                int index = (int) (Long.valueOf("0000003D", 16) & idx); 
                outChars += chars[index]; 
                idx = idx >> 5; 
            } 
            ShortStr[i] = outChars; 
        } 
         
        return ShortStr; 
    } 
    */
     
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDBJDBC ss = new MongoDBJDBC();
        ss.run();

		
		
	}

}
