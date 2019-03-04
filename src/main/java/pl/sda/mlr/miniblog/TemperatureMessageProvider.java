package pl.sda.mlr.miniblog;

import org.springframework.stereotype.Component;

@Component
public class TemperatureMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "22";
    }
}
