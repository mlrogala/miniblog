package pl.sda.mlr.miniblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.mlr.miniblog.service.MessageService;
import pl.sda.mlr.miniblog.service.UserSessionService;

import java.util.Set;

//@Component
@Controller
public class MainController {

    @Autowired
    private MessageService currentDateTimeService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private Set<MessageProvider> messageProvider;

    @RequestMapping(value = {"/", "/home"})
    public String home(Model model){
        model.addAttribute("userLogged", userSessionService.isLogged());
        messageProvider.forEach(mp -> System.out.println(mp.getMessage()));
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "homePage";
    }

    //@RequestMapping(name = "/hello", method = RequestMethod.GET)
    @GetMapping("/hello")
    public String hello(Model model, @RequestParam(required = false) String nameParam){
        if(nameParam != null){
            model.addAttribute("name", nameParam);
        } else {
            model.addAttribute("name", "Anonymous");
        }
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "hello";
    }


    @RequestMapping("/params")
    public String params(@RequestParam(required = false, name = "test") String testParam){
        System.out.println("Param test: " + testParam);
        return "params";
    }
}


/*
//@Component        -> ta adnotacja jest niepotrzebna, bo @Controller "dziedziczy" po Componencie
@Controller()
public class MainController {

    @Autowired
    private MessageService currentDateTimeService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private Set<MessageProvider> messageProvider;       //set, bo mamy kilka klas, które implementują dany interfejs, a chcemy korzystać ze wszystkich, nie z jednej konkretnej





    @RequestMapping({"/home", "/"})
    public String home(Model model) {
        model.addAttribute("userLogged", userSessionService.isLogged());
        messageProvider.forEach(mp-> System.out.println(mp.getMessage()));  // wypluje wynik getMessage dla każdej z implementacji interfejsu MessageProvider
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "home";
    }

    //    @RequestMapping(name="/hello", method = RequestMethod.GET)  //method określi, na jaką metodę działa request; wysłanie danych z formularza inną metodą uniemożliwi wyświetleie strony; jeśli metoda ma nasłuchiwać na kilka metod, podajemy je w tablicy, ale robi się to bardzo, bardzo rzadko)
    @GetMapping("/hello")
    //robi dokładnie to samo, co powyższa adnotacja z tymi atrybutami (są jeszcze odpowiednie dla pozostałych metod, nie tylko GET); można stworzyć dwie rózne metody mapujące ten sam url, ale np. jedna na GetMapping, druga na PostMapping
    public String hello(Model model, @RequestParam(required = false) String name) {
        if (name != null) {
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "Anonymous");
        }
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "helloParam";
    }

    /*
     @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam (required=false)String name){
        if(name!=null) {
            model.addAttribute("name", name);
        } else{
            model.addAttribute("name", "Anonymous");
        }
        return "helloParam";
    }

    @RequestMapping("/params")
    public String params(@RequestParam(required = false, name = "test") String testParam) {  //name określa jak parametr ma być wpisany w urlu (test=...), testParam to nazwa tego samego parametru, ale wykorzystywana przeze mnie w ciele metody
        System.out.println("Param test: " + testParam);
        return "params";
    }

     @RequestMapping("/params")
    public String params(@RequestParam(required = false) String test) {  //z takim parametrem (required) przy @RequestParam aplikacja nie sypnie błędem; jeśli nie występuje, to wpadnie jako null, jeśli będzie w url: "test=", to wpadnie jako pusty String
        System.out.println("Param test: " + test);
        return "params";
    }

      @RequestMapping("/params")
    public String params(@RequestParam String test) {    //@RequestParam oznacza, że test to parametr podany w url; bez podania parametru w urlu, to strona sypnie błędem, bez wchodzenia do ponizszej metody
        System.out.println("Param test: " + test);
        return "params";
    }

    @RequestMapping("/hello")
    public String hello(Model model) {                                   // Spring wstrzykuje obiekt Model, którego możemy dalej używać
        model.addAttribute("name", "Adam");     //(klucz, wartość)
        return "helloPage";
    }

    @RequestMapping("/")    //w " " jest podany adres, po wejściu na który wywołana zostanie poniższa metoda; mappingi na kilka urli można podać jako: value = [" ", " " ...]
    public String home(){

        return "homePage";      //zwracamy nazwę widoku, który ma być wyświetlony po wejściu na powyższy url (w mappingu);
                                // podany widok jest plikiem html w templates (muszą mieć taką samą nazwę)
    }
    }
    */

