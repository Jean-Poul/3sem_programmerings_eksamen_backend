
package dto;


import entities.Hotel;
import java.util.ArrayList;
import java.util.List;


public class HotelsDTO {
     List<HotelDTO> all = new ArrayList();

    public HotelsDTO(List<Hotel> hotelEntities) {
        hotelEntities.forEach((h) -> {
            all.add(new HotelDTO(h));
        });
    }

    public List<HotelDTO> getAll() {
        return all;
    }

    public void setAll(List<HotelDTO> all) {
        this.all = all;
    }
}
