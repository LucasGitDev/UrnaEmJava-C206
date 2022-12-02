package br.com.tucaleli.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class FileController {

    public static void createFileIfNotExists(String path) {
        try {
            new File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String path) {
        createFileIfNotExists(path);
        Path filePath = Paths.get(path);

        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeAllLines(String path, List<String> lines) {
        createFileIfNotExists(path);
        Path filePath = Paths.get(path);

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendLine(String path, String line) {
        createFileIfNotExists(path);
        Path filePath = Paths.get(path);

        try {
            Files.writeString(filePath, line + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getFilesInFolder(String path) {
        createFileIfNotExists(path);
        try {
            Path filePath = Paths.get(path);
            Stream<Path> files = Files.list(filePath);
            return files.map(Path::toString).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
