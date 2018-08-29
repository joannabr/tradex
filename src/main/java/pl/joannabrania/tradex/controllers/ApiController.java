package pl.joannabrania.tradex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.joannabrania.tradex.models.services.CurrentService;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    final CurrentService currentService;

    @Autowired
    public ApiController(CurrentService currentService) {

        this.currentService = currentService;
    }

    @GetMapping("/api/currency/{typed}")
    public ResponseEntity getCurrency(@PathVariable("typed") String typed){
        return ResponseEntity
                .ok()
                .body(currentService.getActualCurrencies()
                .stream()
                .filter(s -> s.contains(typed.toUpperCase()))
                .collect(Collectors.toList()));
    }
}
