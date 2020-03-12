package org.speech4j;

import java.io.InputStream;

public interface MediaConverter {

    String convertToText(InputStream audio);
}
