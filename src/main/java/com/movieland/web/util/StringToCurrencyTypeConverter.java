package com.movieland.web.util;

import com.movieland.entity.CurrencyType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCurrencyTypeConverter implements Converter<String, CurrencyType> {
    @Override
    public CurrencyType convert(String source) {
        try {
            return CurrencyType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
