package facades;

import dto.CustomerDTO;
import dto.CustomersDTO;
import entities.Booking;
import entities.Creditcard;
import entities.Customer;
import entities.Hotel;
import entities.Role;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

//@Disabled
public class CustomerFacadeTest {

    private static EntityManagerFactory emf;
    private static CustomerFacade facade;
    private Customer c1, c2, c3;
    private Role r1, r2;
    private Booking b1, b2, b3;
    private Creditcard card1, card2;
    private Hotel h1, h2, h3;

    public CustomerFacadeTest() {

    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CustomerFacade.getCustomerFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            c1 = new Customer("full name 1", "email@test.dk", "3sem", 12345678);
            c2 = new Customer("full name 2", "email2@test.dk", "3sem", 87654321);
            c3 = new Customer("full name 3", "email3@test.dk", "3sem", 11223344);

            b1 = new Booking("16/1-2021", 9, 3000);
            b2 = new Booking("9/1-2021", 3, 1500);
            b3 = new Booking("10/1-2021", 1, 500);

            card1 = new Creditcard("Visa", "12345678", "1/1-2022", "card name 1");
            card2 = new Creditcard("Master card", "87654321", "9/9-2023", "card name 2");


            Hotel h1 = new Hotel("sleep", "Cwm Cadlan, Penderyn, CF44 0YJ", "alt", "checkin", "checkout",
                    "Fantastic little B&B with 3 rooms, all en-suite. Very comfortable beds, friendly hosts, delicious breakfasts. Ideally placed for waterfalls and mountains, Walkers and cyclists welcome.",
                    "From Penderyn, follow Cwm Cadlan road from Lamb Hotel for 1.5 miles, you'll see sign and yellow grit bin at the gate on your left.",
                    "willow.walks@hotmail.co.uk",
                    "fax",
                    "geo",
                    "hours",
                    4042,
                    "image",
                    "Beili Helyg Guest House",
                    "+44 0 1685 813609",
                    "£80 per double room per night",
                    "Brecon Beacons National Park",
                    "tollfree",
                    "landmark",
                    "http://www.beilihelygguesthouse.co.uk"
            );
            Hotel h2 = new Hotel("sleep", "Harbour Island, North Eleuthera, Bahamas", "alt", "checkin", "checkout",
                    "Valentines is the most vibrant and exciting luxury resort in Harbour Island, Bahamas.  The resort offers 40 spacious, tropically appointed luxury suites with fully equipped kitchens and a 50 slip state of the art Yacht Marina.",
                    "directions",
                    "email",
                    "fax",
                    "geo",
                    "hours",
                    8848,
                    "image",
                    "Valentines Resort & Marina",
                    "(866) 389-6864",
                    "price",
                    "Eleuthera",
                    "tollfree",
                    "landmark",
                    "http://www.valentinesresort.com/"
            );
            Hotel h3 = new Hotel("sleep", "address", "alt", "checkin", "checkout", "Small, friendly and relaxed hostel, offering both shared and private rooms. In a very nice, tranquil and green part of town, only 300mts. from the bus terminal. Good kitchen. Swimming pool.",
                    "El Urú 120, Puerto Iguazú,",
                    "info@porambahostel.com",
                    "fax",
                    "geo",
                    "hours",
                    23350,
                    "image",
                    "Poramba Hostel",
                    "+54 3757 423041",
                    "price",
                    "Puerto Iguazú",
                    "tollfree",
                    "landmark",
                    "http://porambahostel.com"
            );

            em.getTransaction().begin();

            
            
            em.createQuery("DELETE FROM Booking").executeUpdate();
            em.createQuery("DELETE FROM Creditcard").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createQuery("DELETE FROM Customer").executeUpdate();

            System.out.println("HOTEL: " + h1);
            System.out.println("CREDITCARD: " + card1);
            System.out.println("CUSTOMER: " + c1);
            System.out.println("BOOKING: " + b1);

            c1.addRole(adminRole);
            c2.addRole(userRole);
            c3.addRole(userRole);
            c3.addRole(adminRole);

            h1.addBooking(b1);
            h2.addBooking(b2);
            h3.addBooking(b3);

            c1.addBooking(b1);
            c2.addBooking(b2);
            c2.addBooking(b3);

            c1.addCreditcard(card1);
            c2.addCreditcard(card2);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getVeryfiedUser method, of class CustomerFacade.
     */
    @Test
    public void testGetVeryfiedUser() throws Exception {
        System.out.println("Verify user");
        String pass = c1.getPassword();

        assertEquals(c1.getEmail(), "email@test.dk");
        assertEquals(c1.getPassword(), pass);
        assertThat(c1.getEmail(), is(not("hotel@test.dk")));
        assertThat(c1.getPassword(), is(not("lilleTomat")));
    }

    /**
     * Test of getCustomerCount method, of class CustomerFacade.
     */
    @Test
    public void testGetCustomerCount() {
        System.out.println("Customer count");
        assertEquals(3, facade.getCustomerCount(), "Expects three rows in the database");
    }

    /**
     * Test of getCustomerByEmail method, of class CustomerFacade.
     */
    @Test
    public void testGetCustomerByEmail() throws NotFoundException {
        System.out.println("Customer by email");
        CustomerDTO customerDTO = facade.getCustomerByEmail(c1.getEmail());

        assertEquals("email@test.dk", customerDTO.getEmail());
    }

    /**
     * Test of getAllCustomers method, of class CustomerFacade.
     */
    @Test
    public void testGetAllCustomers() throws NotFoundException {
        CustomersDTO customersDTO = facade.getAllCustomers();
        List<CustomerDTO> list = customersDTO.getAll();
        System.out.println("Liste af personer: " + list);
        assertThat(list, everyItem(Matchers.hasProperty("email")));
        assertThat(list, Matchers.hasItems(Matchers.<CustomerDTO>hasProperty("name", is("full name 1")),
                Matchers.<CustomerDTO>hasProperty("name", is("full name 2"))
        ));

    }

    /**
     * Test of addCustomer method, of class CustomerFacade.
     */
    @Test
    public void testAddCustomer() throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        String name = "jplm";
        String email = "3em@cph.dk";
        String password = "3sem";
        int phone = 12345678;

        Customer c = new Customer(name, email, password, phone);

        System.out.println("Customer: " + c);

        CustomerDTO cDTO = new CustomerDTO(c);

        System.out.println("PersonDTO" + cDTO);

        facade.addCustomer(name, email, password, phone);

        try {
            em.getTransaction().begin();

            em.find(Customer.class, email);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Test of deleteCustomer method, of class CustomerFacade.
     */
//    @Test
//    public void testDeleteCustomer() throws Exception {
//        System.out.println("Delete Customer");
//        CustomerDTO customerDTO = facade.deleteCustomer(c1.getEmail());
//        //assertThat(p1.getEmail(), is(not(personDTO.getEmail())));
//        assertEquals(2, facade.getCustomerCount());
//    }

    /**
     * Test of editCustomer method, of class CustomerFacade.
     */
//    @Test
//    public void testEditCustomer() throws Exception {
//        System.out.println("Edit Customer");
//        EntityManager em = emf.createEntityManager();
//
//        String name = "J-P";
//        String email = "jp@test.dk";
//        String password = "tester";
//        int phone = 12345678;
//
//        c1.setName(name);
//        c1.setEmail(email);
//        c1.setPassword(password);
//        c1.setPhone(phone);
//
//        System.out.println("Customer: " + c1);
//
//        CustomerDTO cDTO = new CustomerDTO(c1);
//
//        System.out.println("CustomerDTO: " + cDTO);
//
//        facade.editCustomer(cDTO);
//
//        try {
//            em.getTransaction().begin();
//            em.merge(c1);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//
//        assertEquals(c1.getName(), "J-P");
//    }

}
