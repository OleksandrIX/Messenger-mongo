package com.himera.Messengermongo.controllers;

import com.himera.Messengermongo.models.User;
import com.himera.Messengermongo.models.UserTransporter;
import com.himera.Messengermongo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String addUser(@RequestParam("email") String email) {
        if (!userService.searchEmailInDB(email)) {
            userService.addUser(email);
        }
        return "redirect:/activate";
    }

    @GetMapping("/activate")
    public String activate() {
        return "activate";
    }

    @PostMapping("/activate")
    public String sendActivateCode(@RequestParam("code") String code, Model model) {
        User user = userService.activeUser(code);
        if(user != null){
            if (user.getActivationCode() == null) {
                UserTransporter.setUser(user);
                if (user.getUsername()==null && user.getPassword()==null) {
                    return "redirect:/registration";
                }else {
                    return "redirect:/login";
                }
            }else {
                model.addAttribute("message","Confirmation code entered incorrectly");
            }
        }else {
            model.addAttribute("message","Confirmation code entered incorrectly");

        }
        return "activate";
    }

    @GetMapping("/registration")
    public String pageRegistration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String pageRegistration(@RequestParam("username")String username,
                                   @RequestParam("password")String password){
        User user = UserTransporter.getUser();
        if(userService.saveUser(username,password,user)){
            return "redirect:/login";
        }
        return "registration";
    }

    @GetMapping("/login")
    public String pageLogin(Model model){
        User user = UserTransporter.getUser();
        model.addAttribute("user", user);
        return "login";
    }
}
