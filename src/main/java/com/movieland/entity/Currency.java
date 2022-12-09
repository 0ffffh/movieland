package com.movieland.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Currency {
    @JsonProperty("r030")
    private int magicNumber;
    @JsonProperty("txt")
    private String name;
    @JsonProperty("rate")
    private double rate;
    @JsonProperty("cc")
    private String currency;
    @JsonProperty("exchangedate") @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate exchangedate;

}
