package vn.hoidanit.laptopshop.domain;

public class User {
    private String email, password, fullName, address, phone;
    private long id;

    public User() {

    }

    public User(long id, String email, String password, String fullName, String address, String phone){
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", password=" + password + ", fullName=" + fullName + ", address=" + address
                + ", phone=" + phone + ", id=" + id + "]";
    }


}
