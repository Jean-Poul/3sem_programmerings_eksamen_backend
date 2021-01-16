package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CustomerDTO;
import dto.CustomersDTO;
import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Customer;
import entities.Person;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.CustomerFacade;
import facades.PersonFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

@Path("customer")
public class CustomerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CustomerFacade FACADE = CustomerFacade.getCustomerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    public CustomerResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from customer\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String countCustomers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            List<Customer> customer = query.getResultList();
            return "Count of customers: " + "[" + customer.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public String getCustomerByEmail(@PathParam("email") String email) throws NotFoundException {

        return GSON.toJson(FACADE.getCustomerByEmail(email));

    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAllCustomers() throws NotFoundException {
        CustomersDTO psDTO = FACADE.getAllCustomers();
        return GSON.toJson(psDTO);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addCustomer(String user) throws NotFoundException {
        CustomerDTO cDTO = GSON.fromJson(user, CustomerDTO.class);
        CustomerDTO addCustomer = FACADE.addCustomer(cDTO.getName(), cDTO.getEmail(), cDTO.getPassword(), cDTO.getPhone());
        return GSON.toJson(addCustomer);
    }
    
    @DELETE
    @Path("delete/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteCustomer(@PathParam("email") String email) throws NotFoundException {
        CustomerDTO customerDelete = FACADE.deleteCustomer(email);
        return GSON.toJson(customerDelete);
    }
    
    @PUT
    @Path("update/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateCustomer(@PathParam("email") String email, String customer) throws  MissingInputException, NotFoundException {
        CustomerDTO customerDTO = GSON.fromJson(customer, CustomerDTO.class);
        customerDTO.setEmail(email);
        CustomerDTO customerNew = FACADE.editCustomer(customerDTO);
        return GSON.toJson(customerNew);
    }

}
