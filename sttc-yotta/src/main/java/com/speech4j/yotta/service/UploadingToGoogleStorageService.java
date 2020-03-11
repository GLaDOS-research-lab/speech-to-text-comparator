package com.speech4j.yotta.service;

import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class UploadingToGoogleStorageService {

    private final static String GOOGLE_STORAGE_BUCKET_NAME = "staging.stoftecon-1580375927522.appspot.com";

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

    public static String upload(String uri) {
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

}
