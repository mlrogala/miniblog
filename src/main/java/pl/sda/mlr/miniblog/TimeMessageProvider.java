package pl.sda.mlr.miniblog;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return LocalTime.now().toString();
    }
}
