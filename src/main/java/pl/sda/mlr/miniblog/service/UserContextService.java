package pl.sda.mlr.miniblog.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {

    public String getLoggedAs(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     //ten obiekt jest zawze, niezależnie czy użytkownik jest zalogowany, czy nie; instancją autentykacji jest użytkownik anonimowy(niezalogowany) i użytkownik zalogowany

        if(authentication instanceof AnonymousAuthenticationToken){ //jeśli jest instancją, to mamy niezalogowanego użytkownika
            return null;
        }

        return authentication.getName();        //zwraca login zalogowanego użytkownika (to, co zdefiniowaliśmy jako login w konfiguracji security)

    }

    //TODO: hasRole (String... roleNames)
    public boolean hasRole(String roleName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(a->a.getAuthority())
                .anyMatch(s->s.equals(roleName));
    }
}
