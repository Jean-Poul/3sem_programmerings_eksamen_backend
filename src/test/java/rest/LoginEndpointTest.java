package rest;

import entities.Booking;
import entities.Creditcard;
import entities.Customer;
import entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

@Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private Customer c1, c2, c3;
    private Booking b1, b2;
    private Creditcard card1, card2;

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
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            
            em.createQuery("DELETE FROM Booking").executeUpdate();
            em.createQuery("DELETE FROM Creditcard").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Customer").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            c1 = new Customer("full name 1", "email@test.dk", "3sem", 12345678);
            c2 = new Customer("full name 2", "email2@test.dk", "3sem", 87654321);
            c3 = new Customer("full name 3", "email3@test.dk", "3sem", 11223344);

            b1 = new Booking("16/1-2021", 9, 1000);
            b2 = new Booking("9/1-2021", 3, 1000);

            card1 = new Creditcard("Visa", "12345678", "1/1-2022", "card name 1");
            card2 = new Creditcard("Master card", "87654321", "9/9-2023", "card name 2");

            c1.addRole(adminRole);
            c2.addRole(userRole);
            c3.addRole(userRole);
            c3.addRole(adminRole);

            c1.addBooking(b1);
            c2.addBooking(b2);

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

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/customer").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/customer/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello from customer"));
    }

    @Test
    public void testRestForAdmin() {
        login("email@test.dk", "3sem");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/customer/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: " + c1.getEmail()));
    }

    @Test
    public void testRestForUser() {
        login("email2@test.dk", "3sem");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/customer/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: " + c2.getEmail()));
    }

    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        login("email2@test.dk", "3sem");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/customer/admin").then() //Call Admin endpoint as user
                .statusCode(401);
    }

    @Disabled
    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        login("email1@test.dk", "3sem");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/customer/user").then() //Call Person endpoint as Admin
                .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("email3@test.dk", "3sem");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: " + c3.getEmail()));
    }

    @Disabled
    @Test
    public void testRestForMultiRole2() {
        login("email3@test.dk", "secretpassword");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: " + c3.getEmail()));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/customer/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/customer/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

}
