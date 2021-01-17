package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CustomerDTO;
import entities.Booking;
import entities.Creditcard;
import entities.Customer;
import entities.Hotel;
import entities.Role;
import facades.CustomerFacade;
import facades.PersonFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.enterprise.inject.New;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import static rest.PersonResourceTest.BASE_URI;
import static rest.PersonResourceTest.startServer;
import utils.EMF_Creator;

//@Disabled
public class CustomerResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    
    private Customer c1, c2, c3;
    private Role r1, r2;
    private Booking b1, b2, b3;
    private Creditcard card1, card2;
    private Hotel h1, h2, h3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final CustomerFacade FACADE = CustomerFacade.getCustomerFacade(emf);

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    public CustomerResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void tearDownClass() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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


            h1 = new Hotel("sleep", "Cwm Cadlan, Penderyn, CF44 0YJ", "alt", "checkin", "checkout",
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
            h2 = new Hotel("sleep", "Harbour Island, North Eleuthera, Bahamas", "alt", "checkin", "checkout",
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
            h3 = new Hotel("sleep", "address", "alt", "checkin", "checkout", "Small, friendly and relaxed hostel, offering both shared and private rooms. In a very nice, tranquil and green part of town, only 300mts. from the bus terminal. Good kitchen. Swimming pool.",
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
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given()
                .when()
                .get("/customer")
                .then().statusCode(200);
    }

    /**
     * Test of getInfoForAll method, of class CustomerResource.
     */
    @Test
    public void testGetInfoForAll() {
        given()
                .contentType("application/json")
                .get("/customer").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("{\"msg\":\"Hello from customer\"}"));
    }

    /**
     * Test of getFromUser method, of class CustomerResource.
     */
    @Test
    public void testGetFromUser() {
        given()
                .when()
                .get("customer/user/")
                .then().statusCode(403);
    }

    /**
     * Test of getFromAdmin method, of class CustomerResource.
     */
    @Test
    public void testGetFromAdmin() {
        given()
                .when()
                .get("customer/admin/")
                .then().statusCode(403);
    }

    /**
     * Test of countCustomers method, of class CustomerResource.
     */
    @Test
    public void testCountCustomers() {
        System.out.println("countCustomers");
        given()
                .contentType("application/json")
                .get("customer/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("[" + 3 + "]"));
    }

    /**
     * Test of getCustomerByEmail method, of class CustomerResource.
     */
    @Disabled
    public void testGetCustomerByEmail() throws Exception {
        System.out.println("getCustomerByEmail");
        String email = "";
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.getCustomerByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllCustomers method, of class CustomerResource.
     */
    @Disabled
    public void testGetAllCustomers() throws Exception {
        System.out.println("getAllCustomers");
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.getAllCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCustomer method, of class CustomerResource.
     */
    @Disabled
    public void testAddCustomer() throws Exception {
        System.out.println("addCustomer");
        String name = "Netbeans";
        String email = "netbeans@email.com";
        String password = "tester";
        int phone = 12345678;
        
        Customer c4 = new Customer(name, email, password, phone);
        CustomerDTO dto = new CustomerDTO(c4);
        
        given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post("customer")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Netbeans"));
    }

    /**
     * Test of deleteCustomer method, of class CustomerResource.
     */
    @Disabled
    public void testDeleteCustomer() throws Exception {
        System.out.println("deleteCustomer");
        String email = "";
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.deleteCustomer(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateCustomer method, of class CustomerResource.
     */
    @Disabled
    public void testUpdateCustomer() throws Exception {
        System.out.println("updateCustomer");
        String email = "";
        String customer = "";
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.updateCustomer(email, customer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
