package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HotelsDTO;
import errorhandling.NotFoundException;
import facades.HotelFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("hotel")
public class HotelResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HotelFacade FACADE = HotelFacade.getHotelFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    public HotelResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello from hotel\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String getAllHotels() throws NotFoundException {
//        CustomersDTO psDTO = FACADE.getAllCustomers();

//        String hotelString = HttpUtils.fetchData("http://exam.cphdat.dk:8000/hotel/all");
//        Hotel hotel = GSON.fromJson(hotelString, Hotel.class);
//        
//        HotelDTO hotelDTO = new HotelDTO(hotel);
//
//        String hotelJSON = GSON.toJson(hotelDTO);
//        return hotelJSON;
        HotelsDTO psDTO = FACADE.getAllHotels();
        return GSON.toJson(psDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getCustomerById(@PathParam("id") int id) throws NotFoundException {

        return GSON.toJson(FACADE.getHotelById(id));

    }
}
