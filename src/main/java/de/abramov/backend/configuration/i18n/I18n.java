package de.abramov.backend.configuration.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Samuel Abramov created on 20.06.2020
 */
@Service
public class I18n {


    private final MessageSource messageSource;

    private Locale locale;

    @Autowired
    public I18n(MessageSource messageSource){
        this.messageSource = messageSource;
        locale = Locale.getDefault();
    }


    public String getLocaleString(String key){
        return  messageSource.getMessage(key, null, locale);
    }

    public String getLocaleString(String key, Locale locale){
        return  messageSource.getMessage(key, null, locale);
    }
}
