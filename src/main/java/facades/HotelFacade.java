
package facades;

import dto.CustomerDTO;
import dto.HotelDTO;
import dto.HotelsDTO;
import entities.Customer;
import entities.Hotel;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


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
    
    public HotelDTO getHotelById(int id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Hotel hotel;
        HotelDTO cDTO;

        try {
            hotel = em.find(Hotel.class, id);
            if (hotel == null) {
                throw new NotFoundException("Invalid user name or email");
            }
        } finally {
            em.close();
        }
        return new HotelDTO(hotel);

    }
    
    public HotelsDTO getAllHotels() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        HotelsDTO hsDTO;
        try {
            hsDTO = new HotelsDTO(em.createQuery("SELECT h FROM Hotel h").getResultList());
            if (hsDTO == null) {
                throw new NotFoundException("No hotels were found");
            }
        } finally {
            em.close();
        }
        return hsDTO;

    }
    
//    public HotelDTO getHotel() throws NotFoundException, IOException{
//        EntityManager em = emf.createEntityManager();
//        
//        String hotelString = HttpUtils.fetchData("http://exam.cphdat.dk:8000/hotel/all");
//        
//        
//         
//        Hotel hotel;
//        HotelDTO hDTO;
//        
//        try {
//           
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
