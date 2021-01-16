package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Hotel;
import java.io.IOException;
import utils.HttpUtils;

public class FetchTester {                  //Testing file to check the return from a fetch url
private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        
//        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
//        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
        
         String hotelString = HttpUtils.fetchData("http://exam.cphdat.dk:8000/hotel/all");
         

        Hotel hotel = GSON.fromJson(hotelString, Hotel.class);
        
        System.out.println("HOTEL");
        System.out.println(hotel);
    }
}
