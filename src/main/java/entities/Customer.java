
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;


@Entity
@Table(name = "customer")
@NamedQuery(name = "Customer.deleteAllRows", query = "DELETE from Customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(name = "fullname", length = 50)
    private String name;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "email", length = 20)
    private String email;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    
    @Column(name = "phone", length = 11)
    private int phone;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private List<Booking> bookingList = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private List<Creditcard> cardList = new ArrayList<>();
    
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "email", referencedColumnName = "email")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String email, String password, int phone) {
        this.name = name;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.phone = phone;
    }
    
    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, this.password));
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
    
    public void addBooking(Booking booking) {
        if (booking != null) {
            bookingList.add(booking);
            booking.setCustomer(this);
        }
    }
    
    public void addCreditcard(Creditcard creditcard) {
        if (creditcard != null) {
            cardList.add(creditcard);
            creditcard.setCustomer(this);
        }
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Creditcard> getCardList() {
        return cardList;
    }

    public void setCardList(List<Creditcard> cardList) {
        this.cardList = cardList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
    
    public void addRole(Role userRole) {
        roleList.add(userRole);
    }
    
    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }
    
}
