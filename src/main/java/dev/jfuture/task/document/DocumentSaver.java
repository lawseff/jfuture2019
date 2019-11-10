package dev.jfuture.task.document;

import dev.jfuture.task.exception.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DocumentSaver {

    private static final String ENCODING = "UTF-8";

    public void save(String file, String content) throws DocumentException {
        try {
            PrintWriter writer = new PrintWriter(file, ENCODING);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            throw new DocumentException(e.getMessage(), e);
        }
    }

}
