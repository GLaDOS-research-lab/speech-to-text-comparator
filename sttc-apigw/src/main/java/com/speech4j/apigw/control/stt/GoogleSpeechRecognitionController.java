package com.speech4j.apigw.control.stt;

import com.speech4j.apigw.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/google_speech")
public class GoogleSpeechRecognitionController {

    @GetMapping("/youtube")
    public ResponseEntity<String> recognizeSpeechByYouTubeLink(@RequestParam String youtubeLink,
                                                               @RequestParam String lang){
        String responseBody;
        HttpStatus responseStatus;
        if(ValidationService.validateYouTubeLink(youtubeLink) && ValidationService.validateLanguageCode(lang)){
            responseBody = "Some text";
            responseStatus = HttpStatus.OK;
        } else {
            responseBody = "Some error message";
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseBody, responseStatus);
    }

    @PostMapping("/upload_video")
    public ResponseEntity<String> uploadVideoToRecognizeSpeech(@RequestParam MultipartFile file,
                                                              @RequestParam String lang){
        String responseBody;
        HttpStatus responseStatus;
        if(!file.isEmpty() && ValidationService.validateLanguageCode(lang)){
            responseBody = "Some text";
            responseStatus = HttpStatus.OK;
        } else {
            responseBody = "Some error message";
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseBody, responseStatus);
    }

    @PostMapping("/upload_audio")
    public ResponseEntity<String> uploadAudioToRecognizeSpeech(@RequestParam MultipartFile file,
                                                               @RequestParam String lang){
        String responseBody;
        HttpStatus responseStatus;
        if(!file.isEmpty() && ValidationService.validateLanguageCode(lang)){
            responseBody = "Some text";
            responseStatus = HttpStatus.OK;
        } else {
            responseBody = "Some error message";
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseBody, responseStatus);
    }

}
