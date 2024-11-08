package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;

    public UserController(UserService userService, UploadService uploadService){
        this.userService = userService;
        this.uploadService = uploadService;
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
        model.addAttribute("newUser", new User());

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
    public String handleCreateUser(Model model, @ModelAttribute("newUser") User user, @RequestParam("thienFile") MultipartFile file) {
        //this.userService.handleSaveUser(user);
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update") //PostMapping = RequestMapping + method post
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User updatedUser) {
        User currentUser = this.userService.getUserById(updatedUser.getId());
        if (currentUser != null){
            currentUser.setPhone(updatedUser.getPhone());
            currentUser.setAddress(updatedUser.getAddress());
            currentUser.setFullName(updatedUser.getFullName());
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
        return "/admin/user/delete";
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