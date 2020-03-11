package com.speech4j.yotta.control;

import com.speech4j.yotta.service.YoTTAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoTTAController {

    @GetMapping("/load")
    public ResponseEntity<String> loadToGoogleStorageFromYouTube(@RequestParam String youtubeURL) {
        String responseBody;
        HttpStatus responseStatus;
        try{
            responseBody = YoTTAService.uploadToGoogleStorageFromYouTube(youtubeURL);
            responseStatus = HttpStatus.OK;
        } catch (RuntimeException e){
            responseBody = e.getMessage();
            if(responseBody.equals("Invalid youtube url")) responseStatus = HttpStatus.NOT_FOUND;
            else if(responseBody.equals("Upload failed")) responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
            else responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(responseBody, responseStatus);
    }

}
