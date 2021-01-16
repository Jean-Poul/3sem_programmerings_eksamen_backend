
package facades;

import dto.HotelDTO;
import entities.Hotel;
import errorhandling.NotFoundException;
import fetcher.HotelFetcher;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;


public class HotelFacade {
    
    private static EntityManagerFactory emf;
    private static HotelFacade instance;

    public HotelFacade() {
    }
    
    public static HotelFacade getHotelFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HotelFacade();
        }
        return instance;
    }
    
//    public HotelDTO getHotel() throws NotFoundException, IOException{
//        EntityManager em = emf.createEntityManager();
//        
//        try {
//            HotelFetcher hotelFetcher = new HotelFetcher();
//            String json = hotelFetcher.getAllHotelsJson();
//            HotelDTO hDTO = new HotelDTO(new Hotel(json));
//            if (json == null) {
//                throw new NotFoundException("No hotels were found");
//            }
//            
//            return hDTO;
//        } finally {
//            em.close();
//        }
//    }
    
}
