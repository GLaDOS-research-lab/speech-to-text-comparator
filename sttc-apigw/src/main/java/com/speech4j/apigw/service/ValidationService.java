package com.speech4j.apigw.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ValidationService {

    public static boolean validateYouTubeLink(String link){
        return link.startsWith("https://www.youtube.com/watch?v=");
    }

    public static boolean validateLanguageCode(String lang){
        List<String> isoLanguages = Arrays.asList(Locale.getISOLanguages());
        return isoLanguages.contains(lang);
    }

}
