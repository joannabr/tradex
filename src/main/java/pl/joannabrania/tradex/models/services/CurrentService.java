package pl.joannabrania.tradex.models.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrentService {

    @Bean
    public List<String> getActualCurrencies(){

        return Arrays.asList("EUR","USD","CHF","GBP","AUD","HKD");
    }
}
