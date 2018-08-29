package pl.joannabrania.tradex.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.joannabrania.tradex.models.CurrencyModel;

import java.util.stream.Collectors;

@Controller
public class MainController {

    @GetMapping("/{currencyOne}/{currencyTwo}")
     public String index( @PathVariable("currencyOne") String currencyOne,
                          @PathVariable("currencyTwo") String currencyTwo,
                            Model model){
        RestTemplate restTemplate = getRestTemplate();

        CurrencyModel currencyModelOne = restTemplate
                .getForObject("http://api.nbp.pl/api/exchangerates/rates/a/"+currencyOne+ "/last/10/?format=json", CurrencyModel.class);
        model.addAttribute("labels", currencyModelOne.getRates().stream()
                            .map( s-> s.getBidData())
                            .collect(Collectors.toList()));
        model.addAttribute("data1",currencyModelOne.getRates().stream()
                            .map( s-> s.getBid())
                            .collect(Collectors.toList()));
        model.addAttribute("currencyNameOne", currencyModelOne.getCurrency() );

        CurrencyModel currencyModelTwo = restTemplate
                .getForObject("http://api.nbp.pl/api/exchangerates/rates/a/"+currencyTwo+ "/last/10/?format=json", CurrencyModel.class);
        model.addAttribute("data2",currencyModelTwo.getRates().stream()
                .map( s-> s.getBid())
                .collect(Collectors.toList()));
        model.addAttribute("currencyNameTwo", currencyModelTwo.getCurrency() );

        return "index";
    }

    @Bean
    public RestTemplate getRestTemplate(){

        return new RestTemplate();
    }
}
