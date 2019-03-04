package pl.sda.mlr.miniblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentDateTimeService implements MessageService{

//  @Autowired
    private CurrentDateTimeProvider currentDateTimeProvider;

    @Autowired
    public CurrentDateTimeService(CurrentDateTimeProvider currentDateTimeProvider) {
        this.currentDateTimeProvider = currentDateTimeProvider;
    }

    public String getMessage(){
       return currentDateTimeProvider.getProvider().toString();
    }

}
