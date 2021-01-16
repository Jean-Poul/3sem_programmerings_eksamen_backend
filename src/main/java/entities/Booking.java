package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "booking")
@NamedQuery(name = "Booking.deleteAllRows", query = "DELETE from Booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", length = 10)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;

    @Column(name = "number_of_nights", length = 365)
    private int numberOfNights;

    @Column(name = "price_pr_night", length = 5)
    private double pricePrNight;

    @JoinColumn(name = "customer_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    public Booking() {
    }

    public Booking(String startDate, int numberOfNights, double pricePrNight) {
        this.startDate = new Date();;
        this.numberOfNights = numberOfNights;
        this.pricePrNight = pricePrNight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public double getPricePrNight() {
        return pricePrNight;
    }

    public void setPricePrNight(double pricePrNight) {
        this.pricePrNight = pricePrNight;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
