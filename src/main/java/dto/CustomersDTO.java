
package dto;

import entities.Customer;
import java.util.ArrayList;
import java.util.List;


public class CustomersDTO {
    
    List<CustomerDTO> all = new ArrayList();

    public CustomersDTO(List<Customer> customerEntities) {
        customerEntities.forEach((c) -> {
            all.add(new CustomerDTO(c));
        });
    }

    public List<CustomerDTO> getAll() {
        return all;
    }

    public void setAll(List<CustomerDTO> all) {
        this.all = all;
    }
    
}
