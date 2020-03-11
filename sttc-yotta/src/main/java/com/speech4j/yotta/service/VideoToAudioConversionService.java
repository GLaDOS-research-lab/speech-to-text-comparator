package com.speech4j.yotta.service;

import org.springframework.stereotype.Service;
import ws.schild.jave.*;

import java.io.File;

@Service
public class VideoToAudioConversionService {

    public static String convert(String localVideoURI){
        File source = new File(localVideoURI);
        // name target file with name of source but with .flac extension
        File target = new File(String.format("%s.flac", localVideoURI.substring(0, localVideoURI.lastIndexOf("."))));

        /* Step 2. Set Audio Attrributes for conversion*/
        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("aac");
// here 64kbit/s is 64000
        audio.setBitRate(64000);
        audio.setChannels(1);
        audio.setSamplingRate(44100);

//        /* Step 3. Set Video Attributes for conversion*/
//        VideoAttributes video = new VideoAttributes();
//        video.setCodec("h264");
//        video.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
//// Here 160 kbps video is 160000
//        video.setBitRate(160000);
//// More the frames more quality and size, but keep it low based on devices like mobile
//        video.setFrameRate(15);
//        video.setSize(new VideoSize(400, 300));

        /* Step 4. Set Encoding Attributes*/
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("flac");
        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);

        /* Step 5. Do the Encoding*/
        try {
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (Exception e) {
            /*Handle here the video failure*/
            e.printStackTrace();
        }

        return source.getPath();

    }
}
