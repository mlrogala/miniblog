package pl.sda.mlr.miniblog.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CurrentDateTimeProvider {

    public LocalDateTime getProvider(){
        return LocalDateTime.now();
    }
}
