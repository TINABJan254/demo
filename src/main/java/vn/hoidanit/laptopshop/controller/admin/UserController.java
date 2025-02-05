package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;


@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model){
        List<User> arrUsers = this.userService.getAllUserByEmail("t@gmail.com");
        for (User x : arrUsers){
            System.out.println(x);
        }

        model.addAttribute("thien", "test");
        model.addAttribute("ban", "Hello from controller");
        return "hello";
    }

    @RequestMapping("/admin/user") // URL
    public String getUserPage(Model model) {
        // model.addAttribute("newUser", new User());

        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/user/show"; //Dia chi vao file jsp, chỉ là địa chỉ để vào file jsp render ra giao diện
        // nhưng ko thể đổi url
    }

    @RequestMapping("/admin/user/{id}") //Không khai báo method thì mặc định là GET
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create") //Không khai báo method thì mặc định là GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(Model model, @ModelAttribute("newUser") @Valid User user, BindingResult newUserBindingResult, @RequestParam("thienFile") MultipartFile file) {
        //Validate
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors){
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        
        if (newUserBindingResult.hasErrors()){
            return "admin/user/create";
        }

        //Nếu user có chọn file
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        Role role = this.userService.getRoleByName(user.getRole().getName());

        user.setRole(role);
        user.setAvatar(avatar);
        user.setPassword(hashedPassword);
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update") //PostMapping = RequestMapping + method post
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User updatedUser,
            @RequestParam("thienFile") MultipartFile file) {

        User currentUser = this.userService.getUserById(updatedUser.getId());

        if (currentUser != null){
            currentUser.setPhone(updatedUser.getPhone());
            currentUser.setAddress(updatedUser.getAddress());
            currentUser.setFullName(updatedUser.getFullName());
            currentUser.setRole(this.userService.getRoleByName(updatedUser.getRole().getName()));

            if (!file.isEmpty()) {
                // Xử lý upload file và cập nhật avatar
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatar); // Cập nhật avatar mới
            }

            this.userService.handleSaveUser(currentUser);
        }

        return "redirect:/admin/user"; //redirect là chuyển hướng
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @RequestMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("currentUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("currentUser") User user) {
        this.userService.handleDeleteUserById(user.getId());
        return "redirect:/admin/user";
    }
    
    
}

// @RestController
// public class UserController {

//     private UserService userService;
    
//     public UserController(UserService userService){
//         this.userService = userService;
//     }

//     @GetMapping("")
//     public String getHomePage(){
//         return this.userService.handleHello();
//     }
    
// }