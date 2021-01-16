
package dto;

import entities.Hotel;


public class HotelDTO {
    
    
    private String address;
    private String email;
    private int id;
    private String name;
    private String phone;
    private String price;
    private String url;

    public HotelDTO(Hotel hotel) {
        this.address = hotel.getAddress();
        this.email = hotel.getEmail();
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.phone = hotel.getPhone();
        this.price = hotel.getPrice();
        this.url = hotel.getUrl();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
