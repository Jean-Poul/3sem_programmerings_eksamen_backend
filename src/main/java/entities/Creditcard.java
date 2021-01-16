
package entities;

import java.io.Serializable;
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


@Entity
@Table(name = "creditcard")
@NamedQuery(name = "Creditcard.deleteAllRows", query = "DELETE from Creditcard")
public class Creditcard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type", length = 20)
    private String type;
    
    @Column(name = "card_number", length = 20)
    private String cardNumber;
    
    @Column(name = "end_date", length = 10)
    private String expirationDate;
    
    @Column(name = "name_on_card", length = 50)
    private String nameOnCard;

    @JoinColumn(name = "customer_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    public Creditcard() {
    }

    public Creditcard(String type, String cardNumber, String expirationDate, String nameOnCard) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.nameOnCard = nameOnCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
