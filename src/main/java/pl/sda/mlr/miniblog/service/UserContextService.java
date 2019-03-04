package pl.sda.mlr.miniblog.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserContextService {

    public String getLoggedAs() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     //ten obiekt jest zawze, niezależnie czy użytkownik jest zalogowany, czy nie; instancją autentykacji jest użytkownik anonimowy(niezalogowany) i użytkownik zalogowany

        if (authentication instanceof AnonymousAuthenticationToken) { //jeśli jest instancją, to mamy niezalogowanego użytkownika
            return null;
        }

        return authentication.getName();        //zwraca login zalogowanego użytkownika (to, co zdefiniowaliśmy jako login w konfiguracji security)

    }

    public boolean hasAnyRole(String... roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        } else {
            List<Boolean> booleans = getBooleans(authentication, roleNames);
            if (booleans.contains(true)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllRoles(String... roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        } else {
            List<Boolean> booleans = getBooleans(authentication, roleNames);
            if (booleans.contains(false)) {
                return false;
            }
        }
        return true;
    }

    private List<Boolean> getBooleans(Authentication authentication, String[] roleNames) {
        List<Boolean> booleans = new ArrayList<>();
        for (String roleName : roleNames) {
            booleans.add(authentication.getAuthorities().stream()
                    .map(a -> (a.getAuthority()))
                    .anyMatch(s -> s.equals(roleName)));
        }
        return booleans;
    }

    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .anyMatch(s -> s.equals(roleName));
    }
}
