
package dto;

import entities.Customer;


public class CustomerDTO {
    
    private String name;
    private String email;
    private String password;
    private int phone;

    public CustomerDTO(Customer customer) {
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.phone = customer.getPhone();
    }

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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
