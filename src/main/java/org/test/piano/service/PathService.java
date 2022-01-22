package org.test.piano.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PathService {

    boolean isDirectoryExist (String pathString);

    List<Path> getFilesFromDirectory(String pathString) throws IOException;

}
