package com.speech4j.gcs.service;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class GoogleSpeechRecognitionService {

    private final static String GOOGLE_STORAGE_BUCKET_NAME = "staging.stoftecon-1580375927522.appspot.com";
    private final static int MOST_PROBABLE_VERSION_INDEX = 0;
    private final static int FLAC_SAMPLE_RATE_HERTZ = 44100;

    private static String getFileName(String uri) {
        return new File(uri).getName();
    }

    private static void validateURI(String uri) {
        File file = new File(uri);
        if (!file.isFile()) {
            String errorMess = "Invalid file location";
            System.out.println(errorMess);
            throw new RuntimeException(errorMess);
        }
    }

    private static byte[] getBitesFromURI(String uri) {
        try {
            File file = new File(uri);
            return Files.readAllBytes(file.toPath());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException");
        }
    }

    // TODO: move this logic to YoTTA Service
    private static String uploadFileToGoogleDrive(String uri) {
        String googleDriveURI;
        validateURI(uri);
        googleDriveURI = String.format("audio/%s", getFileName(uri));
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(GOOGLE_STORAGE_BUCKET_NAME, googleDriveURI);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("audio/mpeg").build();
        Blob blob = storage.create(blobInfo, getBitesFromURI(uri));
        if (blob == null) {
            throw new RuntimeException("Upload failed");
        }

        return googleDriveURI;
    }


    public static String convert(String googleDriveURI, String languageCode) throws RuntimeException {
        StringBuilder googleSpeechOutput = new StringBuilder();
//        String googleDriveURI = uploadFileToGoogleDrive(uri);
        googleDriveURI = GOOGLE_STORAGE_BUCKET_NAME + googleDriveURI;

        try (SpeechClient speechClient = SpeechClient.create()) {

            // Encoding of audio data sent. This sample sets this explicitly.
            // This field is optional for FLAC and WAV audio formats.
            RecognitionConfig.AudioEncoding encoding = RecognitionConfig.AudioEncoding.LINEAR16;
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setSampleRateHertz(FLAC_SAMPLE_RATE_HERTZ)
                            .setLanguageCode(languageCode)
                            .setEncoding(encoding)
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(googleDriveURI).build();
            LongRunningRecognizeRequest request =
                    LongRunningRecognizeRequest.newBuilder().setConfig(config).setAudio(audio).build();
            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> future =
                    speechClient.longRunningRecognizeAsync(request);

            System.out.println("Waiting for operation to complete...");
            LongRunningRecognizeResponse response = future.get();
            for (SpeechRecognitionResult result : response.getResultsList()) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(MOST_PROBABLE_VERSION_INDEX);
                googleSpeechOutput.append(alternative.getTranscript());

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return googleSpeechOutput.toString();
    }

}
