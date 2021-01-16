
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "hotel")
@NamedQuery(name = "Hotel.deleteAllRows", query = "DELETE FROM Hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    
    private String activity;
    private String address;
    private String alt;
    private String checkin;
    private String checkout;
    private String content;
    private String directions;
    private String email;
    private String fax;
    private String geo;
    private String hours;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String image;
    private String name;
    private String phone;
    private String price;
    private String title;
    private String tollfree;
    private String type;
    private String url;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "hotel")
    private List<Booking> bookingList = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(String activity, String address, String alt, String checkin, String checkout, String content, String directions, String email, String fax, String geo, String hours, int id, String image, String name, String phone, String price, String title, String tollfree, String type, String url) {
        this.activity = activity;
        this.address = address;
        this.alt = alt;
        this.checkin = checkin;
        this.checkout = checkout;
        this.content = content;
        this.directions = directions;
        this.email = email;
        this.fax = fax;
        this.geo = geo;
        this.hours = hours;
        this.id = id;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.title = title;
        this.tollfree = tollfree;
        this.type = type;
        this.url = url;
    }
    
    public void addBooking(Booking booking) {
        if (booking != null) {
            bookingList.add(booking);
            booking.setHotel(this);
        }
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTollfree() {
        return tollfree;
    }

    public void setTollfree(String tollfree) {
        this.tollfree = tollfree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    
    
}
