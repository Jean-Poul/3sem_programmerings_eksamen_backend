
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HotelDTO;
import errorhandling.NotFoundException;
import facades.HotelFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

//    @Path("/all")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getNextLaunches() throws IOException, NotFoundException {
//        HotelDTO hDTO = FACADE.getHotel();
//        return Response.ok(hDTO.getId()).build();
//    }
}
