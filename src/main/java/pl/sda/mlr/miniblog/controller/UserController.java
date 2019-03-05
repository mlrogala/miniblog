package pl.sda.mlr.miniblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.mlr.miniblog.entity.Post;
import pl.sda.mlr.miniblog.entity.Role;
import pl.sda.mlr.miniblog.entity.User;
import pl.sda.mlr.miniblog.form.UserEditForm;
import pl.sda.mlr.miniblog.form.UserRegisterForm;
import pl.sda.mlr.miniblog.service.UserService;
import pl.sda.mlr.miniblog.service.UserSessionService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;
    private UserSessionService userSessionService;

    @Autowired
    public UserController(UserService userService, UserSessionService userSessionService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterForm", new UserRegisterForm());
        return "user/registerForm";
    }

    @PostMapping("/register")
    public String handleRegisterForm(
            @ModelAttribute @Valid UserRegisterForm userRegisterForm,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "user/registerForm";
        }
        //System.out.println(firstName + ", " + lastName + ", " + email + ", " + password);
        //System.out.println(userRegisterForm);

        userService.registerUser(userRegisterForm);

        return "redirect:/home";
    }


    @GetMapping("/login-by-spring")
    public String showLoginFormBySpringSecurity() {
        return "user/loginFormBySpring";
    }

    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "user/loginForm";
    }

    @PostMapping("/loginForm")
    public String handleLoginForm(@RequestParam String username,
                                  @RequestParam String password) {

        boolean userLogged = userSessionService.loginUser(username, password);

        if (!userLogged) {
            return "user/loginForm";
        }
        return "redirect:/";
    }

    @GetMapping("/logoutForm")
    public String logoutForm() {
        userSessionService.logout();
        return "user/logoutForm";
    }

    @GetMapping("/users")
    public String showAllPosts(Model model, Model modelForRoles) {
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "user/showUsers";
    }

    @GetMapping("/user/{userId}")
    public String showSingleUser(@PathVariable String userId, Model model, Model modelForEdition) {

        Optional<User> userOptional = userService.getSingleUser(Long.valueOf(userId));
        if (userOptional.isPresent() == false) {
            return "user/userNotFound";
        }
        model.addAttribute("user", userOptional.get());
        modelForEdition.addAttribute("userEditForm", new UserEditForm());

        return "user/showSingleUser";
    }

    @GetMapping("/user/{userId}/editFirstName")
    public String editUserFirstName(@PathVariable String userId) {

        return "user/editFirstName";
    }

    @PostMapping("/user/{userId}/editFirstName")
    public String handleFirstNameEdition(@RequestParam String userId, @RequestParam String firstName) {

        userService.changeFirstName(userId, firstName);
        return "redirect:/users";
    }

    @GetMapping("/user/{userId}/editLastName")
    public String editUserLastName(@PathVariable String userId) {

        return "user/editLastName";
    }

    @PostMapping("/user/{userId}/editLastName")
    public String handleLastNameEdition(@RequestParam String userId, @RequestParam String lastName) {

        userService.changeLastName(userId, lastName);
        return "redirect:/users";
    }

}

/*
@Controller
public class UserController {

    private UserService userService;
    private UserSessionService userSessionService;

    @Autowired
    public UserController(UserService userService, UserSessionService userSessionService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterForm", new UserRegisterForm());
        return "user/registerForm";     //podajemy ścieżkę do pliku (z pominięciem nadrzędnego folderu templates)
    }

    @PostMapping("/register")
    public String handleRegisterForm(
            @ModelAttribute @Valid UserRegisterForm userRegisterForm,    //@ModelAttribute -> String mapuje pola formularza na odpowiednie pola klasy i wrzuca obiekt do modelu; @Valid/@Validated -> określa, że ma to walidować (według reguł określonych w samej klasie formularza)
            BindingResult bindingResult) {                               //BindingResult MUSI być za walidowanym obiektem! Brak obsługi tego obiektu spowoduje, że nie wyświetlą się informacje o ewentualnych błędach

        if (bindingResult.hasErrors()) {
            return "user/registerForm";
        }
        userService.registerUser(userRegisterForm);
        return "redirect:/home";     //przekierowuje użytkownika na podanego bezwzględnego urla (nie są to nazwy widoków z templates!!!)
    }

    @GetMapping("/login-by-spring")
    public String showLoginFormBySpringSecurity(){
        return "user/loginFormBySpring";
    }

//  powyższym przy Spring Security można zastąpić cały @Get i @Post Mapping na "/loginForm" i "/logoutForm"

    @GetMapping("/loginForm")
    public String showLoginForm(){
        return "user/loginForm";
    }

    @PostMapping("/loginForm")
    public String handleLoginForm(@RequestParam String username, @RequestParam String password){
        boolean userLogged = userSessionService.loginUser(username, password);
        if(!userLogged){
            return "user/loginForm";
        }
        return "redirect:/";
    }

    @GetMapping("/logoutForm")
    public String logoutForm(){
        userSessionService.logout();
        return "user/logoutForm";
    }
}
*/