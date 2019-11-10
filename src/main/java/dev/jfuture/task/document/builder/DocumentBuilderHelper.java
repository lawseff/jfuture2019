package dev.jfuture.task.document.builder;

import dev.jfuture.task.exception.DocumentException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DocumentBuilderHelper {

    public String getLinesFromResourceFile(String fileName) throws DocumentException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        try {
            if (url == null) {
                throw new DocumentException("File " + fileName + " not found in resources");
            }
            URI uri = url.toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri));
            StringBuilder builder = new StringBuilder();
            lines.forEach(builder::append);
            return builder.toString();
        } catch (URISyntaxException | IOException e) {
            throw new DocumentException(e.getMessage(), e);
        }
    }

}
