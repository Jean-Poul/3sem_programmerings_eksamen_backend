//package fetcher;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.nimbusds.jose.shaded.json.JSONObject;
//import entities.Hotel;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.Charset;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import org.eclipse.persistence.exceptions.JSONException;
//import utils.EMF_Creator;
//import utils.HttpUtils;
//
//public class HotelFetcher {
//
//    List jsonString;
//    String newJson;
//
//    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//    EntityManager em = emf.createEntityManager();
//
//
//    public HotelFetcher() {
//        
//    }
//    
//    public static String getAllHotelsJson() throws MalformedURLException, IOException {
//    String sURL = "http://exam.cphdat.dk:8000/hotel/all/"; //just a string
//
//    // Connect to the URL using java's native library
//    URL url = new URL(sURL);
//    URLConnection request = url.openConnection();
//    request.connect();
//
//    // Convert to a JSON object to print data
//    JsonParser jp = new JsonParser(); //from gson
//    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
//    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
//    String id = rootobj.get("id").getAsString(); //just grab the zipcode
//    return id;
//    }
//    
//    public static void main(String[] args) throws IOException {
//        System.out.println("ID: "+ getAllHotelsJson());
//    }
//    
////    private static String readAll(Reader rd) throws IOException {
////    StringBuilder sb = new StringBuilder();
////    int cp;
////    while ((cp = rd.read()) != -1) {
////      sb.append((char) cp);
////    }
////    return sb.toString();
////  }
////    
////    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
////    InputStream is = new URL(url).openStream();
////    try {
////      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
////      String jsonText = readAll(rd);
////      JSONObject json = new JSONObject(jsonText);
////      return json;
////    } finally {
////      is.close();
////    }
////  }
//
////    public String getAllHotelsJson() throws IOException {
////
////        try {
////            System.out.println("\n### Trying to fetch new hotel data");
////            String APIData = HttpUtils.fetchData("http://exam.cphdat.dk:8000/hotel/all");
////            
////            Hotel h1 = new Hotel(APIData);
////            em.getTransaction().begin();
////            em.createNamedQuery("Hotel.deleteAllRows").executeUpdate();
////            em.persist(h1);
////            jsonString = em.createQuery("SELECT d.data FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
////            newJson = (String) jsonString.get(0);
////            em.getTransaction().commit();
////            System.out.println("\n### Fetched new data! - comming up!");
////            return newJson;
////        } finally {
////            em.close();
////        }
////
////    }
//    
////    public static void main(String[] args) throws IOException, JSONException {
////    JSONObject json = readJsonFromUrl("http://exam.cphdat.dk:8000/hotel/all");
////    System.out.println(json.toString());
////    System.out.println(json.get("id"));
////  }
//
//}
