package facades;

import dto.CustomerDTO;
import dto.CustomersDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Booking;
import entities.Customer;
import entities.Hobby;
import entities.Person;
import entities.Role;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Customer user;
        try {
            user = em.find(Customer.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public long getCustomerCount() {
        EntityManager em = emf.createEntityManager();

        try {
            Long userCount = (Long) em.createQuery("SELECT COUNT(c) FROM Customer u").getSingleResult();
            return userCount;
        } finally {
            em.close();
        }
    }

    public CustomerDTO getCustomerByEmail(String email) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Customer customer;
        CustomerDTO cDTO;

        try {
            customer = em.find(Customer.class, email);
            if (customer == null) {
                throw new NotFoundException("Invalid user name or email");
            }
        } finally {
            em.close();
        }
        return new CustomerDTO(customer);

    }

    public CustomersDTO getAllCustomers() throws NotFoundException {

        EntityManager em = emf.createEntityManager();
        CustomersDTO csDTO;

        try {
            csDTO = new CustomersDTO(em.createQuery("SELECT c FROM Customer c").getResultList());
            if (csDTO == null) {
                throw new NotFoundException("No customers were found");
            }
        } finally {
            em.close();
        }
        return csDTO;

    }
    
    public CustomerDTO addCustomer(String name, String email, String password, int phone) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Customer customer;

        try {
            customer = em.find(Customer.class, email);
            if (customer == null && email.length() > 0 && password.length() > 0) {
                customer = new Customer(name, email, password, phone);
                Role userRole = em.find(Role.class, "user");
                customer.addRole(userRole);
                em.getTransaction().begin();
                em.persist(customer);
                em.getTransaction().commit();
            } else {
                if ((email.length() == 0 || password.length() == 0)) {
                    throw new NotFoundException("Missing input");
                }
                if (customer.getEmail().equalsIgnoreCase(email)) {
                    throw new NotFoundException("Customer exist");
                }
            }
        } finally {
            em.close();
        }
        return new CustomerDTO(customer);
    }
    
    public CustomerDTO deleteCustomer(String email) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Customer customer = em.find(Customer.class, email);
        
        if (customer == null) {
            throw new NotFoundException("Could not delete, provided id does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(customer);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new CustomerDTO(customer);
        }
    }

}
