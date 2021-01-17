
package rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;


@Disabled
public class CustomerResourceTest {
    
    public CustomerResourceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getInfoForAll method, of class CustomerResource.
     */
    @Test
    public void testGetInfoForAll() {
        System.out.println("getInfoForAll");
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.getInfoForAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFromUser method, of class CustomerResource.
     */
    @Test
    public void testGetFromUser() {
        System.out.println("getFromUser");
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.getFromUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFromAdmin method, of class CustomerResource.
     */
    @Test
    public void testGetFromAdmin() {
        System.out.println("getFromAdmin");
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.getFromAdmin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countCustomers method, of class CustomerResource.
     */
    @Test
    public void testCountCustomers() {
        System.out.println("countCustomers");
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.countCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerByEmail method, of class CustomerResource.
     */
    @Test
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
    @Test
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
    @Test
    public void testAddCustomer() throws Exception {
        System.out.println("addCustomer");
        String user = "";
        CustomerResource instance = new CustomerResource();
        String expResult = "";
        String result = instance.addCustomer(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCustomer method, of class CustomerResource.
     */
    @Test
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
    @Test
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
