package com.speech4j.gcs.control;

import com.speech4j.gcs.service.GoogleSpeechRecognitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleSpeechRecognitionController {

    @GetMapping("/convert")
    public ResponseEntity<String> convert(@RequestParam String uri, @RequestParam String lang) {
        String responseBody;
        HttpStatus responseStatus;
        try{
            responseBody = GoogleSpeechRecognitionService.convert(uri, lang);
            responseStatus = HttpStatus.OK;
        } catch (RuntimeException e){
            responseBody = e.getMessage();
            if(responseBody.equals("Invalid file location")) responseStatus = HttpStatus.NOT_FOUND;
            else if(responseBody.equals("Upload failed")) responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
            else responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(responseBody, responseStatus);
    }

}
