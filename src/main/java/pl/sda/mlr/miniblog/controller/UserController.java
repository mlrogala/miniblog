package pl.sda.mlr.miniblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.mlr.miniblog.form.UserRegisterForm;
import pl.sda.mlr.miniblog.service.UserService;
import pl.sda.mlr.miniblog.service.UserSessionService;

import javax.validation.Valid;

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
    public String showRegisterForm(Model model){
        model.addAttribute("userRegisterForm",new UserRegisterForm());
        return "user/registerForm";
    }

    @PostMapping("/register")
    public String handleRegisterForm(
            @ModelAttribute @Valid UserRegisterForm userRegisterForm,
            BindingResult bindingResult
    ){

        if(bindingResult.hasErrors()){
            return "user/registerForm";
        }
        //System.out.println(firstName + ", " + lastName + ", " + email + ", " + password);
        //System.out.println(userRegisterForm);

        userService.registerUser(userRegisterForm);

        return "redirect:/home";
    }


    @GetMapping("/login-by-spring")
    public String showLoginFormBySpringSecurity(){
        return "user/loginFormBySpring";
    }

    @GetMapping("/loginForm")
    public String showLoginForm(){
        return "user/loginForm";
    }

    @PostMapping("/loginForm")
    public String handleLoginForm(@RequestParam String username,
                                  @RequestParam String password){

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