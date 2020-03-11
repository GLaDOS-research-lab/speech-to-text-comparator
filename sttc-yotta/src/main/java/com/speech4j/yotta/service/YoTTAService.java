package com.speech4j.yotta.service;

public class YoTTAService {

    private static void validateURL(String youtubeURL){
        if (!youtubeURL.startsWith("https://www.youtube.com/")) throw new RuntimeException("Invalid youtube link");
    }

    public static String uploadToGoogleStorageFromYouTube(String youtubeURL){
        validateURL(youtubeURL);
        String localVideoURL = UploadingToGoogleStorageService.upload(youtubeURL);
        String localAudioURL = VideoToAudioConversionService.convert(localVideoURL);
        String googleStorageURI = YouTubeDownloadService.download(localAudioURL);

        return googleStorageURI;
    }

}
