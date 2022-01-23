package org.test.piano.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PathServiceImplTest {

    @InjectMocks
    private PathServiceImpl service;

    private String path = this.getClass().getClassLoader().getResource(".").getPath();


    @Test
    public void directoryExist() {
        boolean result = service.isDirectoryExist(path + "/123");
        assertTrue(result);
    }

    @Test
    public void directoryNotExist() {
        boolean result = service.isDirectoryExist(path + "/456");
        assertFalse(result);
    }

    @Test
    public void fileNotDirectory() {
        boolean result = service.isDirectoryExist(path + "/123/123.txt");
        assertFalse(result);
    }

    @Test
    public void fileMatches() {
        boolean result = service.isPathMatches(path + "/123/stats_1.txt");
        assertTrue(result);
    }

    @Test
    public void fileNotMatches() {
        boolean result = service.isPathMatches(path + "/123/123.txt");
        assertFalse(result);
    }

    @Test
    public void getFilesFromDirectory() throws IOException {
        List<Path> result = service.getFilesFromDirectory(path + "/123");
        assertEquals(1, result.size());
        assertEquals("stats_1.txt", result.get(0).getFileName().toString());
    }
}
