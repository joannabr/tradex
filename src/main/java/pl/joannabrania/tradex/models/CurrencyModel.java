package pl.joannabrania.tradex.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyModel {

    @JsonProperty(value = "currency")
    private String currency;
    @JsonProperty(value = "code")
    private String currencyShortCode;

    private List<BidModel> rates;

    //klasa zagnieżdżona
    @Data
    public static class BidModel{
        @JsonProperty(value = "effectiveDate")
        private LocalDate bidData;
        @JsonProperty(value = "mid")
        private double bid;
    }
}
