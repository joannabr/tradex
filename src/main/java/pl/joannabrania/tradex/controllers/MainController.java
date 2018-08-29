package pl.joannabrania.tradex.controllers;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.joannabrania.tradex.models.CurrencyModel;
import pl.joannabrania.tradex.models.HttpErrorHandler;

import java.util.stream.Collectors;

@Controller
public class MainController {

    @GetMapping("/{currencyOne}/{currencyTwo}")
     public String index( @PathVariable("currencyOne") String currencyOne,
                          @PathVariable("currencyTwo") String currencyTwo,
                            Model model){

        ResponseEntity<CurrencyModel> currencyModel;
        try {
            currencyModel = getRestTemplate().getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/" + currencyOne + "/last/10/?format=json", CurrencyModel.class);
        }catch (IllegalStateException e){
            return "redirect:/";
        }

        model.addAttribute("labels", currencyModel.getBody().getRates().stream()
                            .map( s-> s.getBidData())
                            .collect(Collectors.toList()));
        model.addAttribute("data1",currencyModel.getBody().getRates().stream()
                            .map( s-> s.getBid())
                            .collect(Collectors.toList()));
        model.addAttribute("currencyNameOne", currencyModel.getBody().getCurrency() );

        ResponseEntity<CurrencyModel> currencyModel2;
        try {
            currencyModel2 = getRestTemplate().getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/" + currencyTwo + "/last/10/?format=json", CurrencyModel.class);
        }catch (IllegalStateException e){
            return "redirect:/";
        }

        model.addAttribute("currencyNameTwo", currencyModel2.getBody().getCurrency());
        model.addAttribute("dataTwo", currencyModel2.getBody().getRates().stream().map(s -> s.getBid()).collect(Collectors.toList()));

        return "index";
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplateBuilder().errorHandler(new HttpErrorHandler()).build();
    }

    @GetMapping("/*")
    @ResponseBody
    public String error404(){
        return "blad nie znaleziono";
    }

//    @ResponseStatus(value= HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public String notFoundCheck(){
//        return "Nie znaleziono takiej strony";
//    }
}
