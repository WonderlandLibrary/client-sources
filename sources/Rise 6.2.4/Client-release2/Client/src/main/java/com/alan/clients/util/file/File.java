package com.alan.clients.util.file;

import com.alan.clients.util.Accessor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
@Getter
@AllArgsConstructor
public abstract class File implements Accessor {

    private final java.io.File file;
    private final FileType fileType;

    public abstract boolean read();

    public abstract boolean write();

    public void forceWrite(String name, ArrayList<String> lines) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(name, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    public ArrayList<String> getString() {
        ArrayList<String> lines = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(getFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }
}