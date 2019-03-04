package pl.sda.mlr.miniblog.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.sda.mlr.miniblog.entity.User;
import pl.sda.mlr.miniblog.repository.UserRepository;

import java.util.Optional;

@Service
//@Scope(value = "session")     -> robi to samo, co na dole:
@Scope(value=WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)       //wstrzyknięty zostanie dopiero gdy wywołamy na nim metodę w innej klasi; normalnie, po uruchomieniu aplikacji zamiast tego beana wstawiany jest pusty bean proxy (tylko taka wywoływaczka)
public class UserSessionService {

    private UserRepository userRepository;

    @Getter
    private boolean logged;

    @Getter
    private User user;      //to nie jest dobra praktyka!!! nie powinno się trzymać encji!!! tutaj jest tylko po to, żeby pokazać, że można :)

    @Autowired
    public UserSessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean loginUser(String username, String password){
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(!userOptional.isPresent()){
            this.logged=false;
            return false;
        }
        if(userOptional.get().getPassword().equals(password)){
            this.logged=true;
            this.user = userOptional.get();
            return true;
        }
        this.logged=false;
        return false;
    }

    public void logout(){
        this.logged=false;
        this.user=null;
    }

}
