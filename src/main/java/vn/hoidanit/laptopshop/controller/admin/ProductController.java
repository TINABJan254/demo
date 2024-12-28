package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService){
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model){
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model){
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(Model model, @ModelAttribute("newProduct") @Valid Product product, BindingResult newProductBindingResult, @RequestParam("thienFile") MultipartFile file){
        //validate
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors){
            System.out.println(">>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()){
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");

        product.setImage(image);
        this.productService.handleSaveProduct(product);
   
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id){
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}") 
    public String getUpdateProductPage(Model model, @PathVariable long id){
        Product product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }   

    @PostMapping("/admin/product/update")
    public String postUpdateUser(Model model, @ModelAttribute("newProduct") @Valid Product updatedProduct, BindingResult updatedProductBindingResult, @RequestParam("thienFile") MultipartFile file){
        List<FieldError> errors = updatedProductBindingResult.getFieldErrors();
        for (FieldError error : errors){
            System.out.println(">>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (updatedProductBindingResult.hasErrors()){
            return "/admin/product/update";
        }


        Product currentProduct = this.productService.getProductById(updatedProduct.getId());
        if (currentProduct != null){
            if (!file.isEmpty()){
                String image = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(image);
            }

            currentProduct.setName(updatedProduct.getName());
            currentProduct.setPrice(updatedProduct.getPrice());
            currentProduct.setDetailDesc(updatedProduct.getDetailDesc());
            currentProduct.setShortDesc(updatedProduct.getShortDesc());
            currentProduct.setQuantity(updatedProduct.getQuantity());
            currentProduct.setFactory(updatedProduct.getFactory());
            currentProduct.setTarget(updatedProduct.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }


        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id){
        model.addAttribute("id", id);
        model.addAttribute("currentProduct", new Product());
        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("currentProduct") Product product){
        this.productService.handleDeleteProductById(product.getId());
        System.out.println(">>>>>>>>>>>" + product.getId());
        return "redirect:/admin/product";
    }
    
}
