package de.abramov.backend.configuration.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Samuel Abramov created on 20.06.2020
 */
@Service
public class I18n {

    @Autowired
    MessageSource messageSource;

    private Locale locale;
    private Logger logger = LoggerFactory.getLogger(I18n.class);

    public I18n(){
        locale = Locale.getDefault();
        logger.info(String.format("Running with Locale: %s", locale.getDisplayLanguage()));
    }


    public String getLocaleString(String key){
        return  messageSource.getMessage(key, null, locale);
    }

    public String getLocaleString(String key, Locale locale){
        return  messageSource.getMessage(key, null, locale);
    }
}
