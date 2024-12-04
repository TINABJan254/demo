package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name must be not empty")    
    private String name;
    private String image;

    @NotEmpty(message = "Detail description must be not empty")
    private String detailDesc;

    @NotEmpty(message = "Short description must be not empty")
    private String shortDesc;
    private String factory;
    private String target;

    @NotNull
    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @NotNull
    @Min(value = 1, message = "Quantiy must be greater than or equal to 1")
    private long quantity;
    private long sold;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "Prodcut [id=" + id + ", name=" + name + ", image=" + image + ", detailDesc=" + detailDesc
                + ", shortDesc=" + shortDesc + ", factory=" + factory + ", target=" + target + ", price=" + price
                + ", quantity=" + quantity + ", sold=" + sold + "]";
    }

}
