package com.speech4j.yotta.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;

public class VideoToAudioConversionServiceTest {
    @Test
    public void convertTest(){
        File file = new File(VideoToAudioConversionService.convert("src/test/resources/videoplayback.mp4"));
        Assert.isTrue(file.isFile());
    }
}
