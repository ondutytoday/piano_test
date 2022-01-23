package org.test.piano.service;

import java.io.IOException;

public interface FileWatchingService {

    void startWatching(String pathString) throws IOException;
}
