package com.speech4j.apigw.control.tts;

import com.speech4j.apigw.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController("free_tts")
public class FreeTTSController {

    @GetMapping("/generate")
    public ResponseEntity<File> generateSpeechFromText(@RequestParam String text, @RequestParam String lang){
        File responseBody;
        HttpStatus responseStatus;

        if(!text.isEmpty() && ValidationService.validateLanguageCode(lang)) {
            responseBody = new File("");
            responseStatus =  HttpStatus.OK;
        } else {
            responseBody = new File("");
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseBody, responseStatus);
    }
}
