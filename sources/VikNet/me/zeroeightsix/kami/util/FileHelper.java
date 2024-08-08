package me.zeroeightsix.kami.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * Created by hub on 16 June 2019
 * Last Updated 16 June 2019 by hub
 * <p>
 * Testfile: https://pastebin.com/raw/tkAu5gM6
 */
public class FileHelper {

    /**
     * Writes UTF-8 text file.
     *
     * @param data Line to append
     * @param file File Name
     * @return true on success, otherwise false
     */
    public static boolean appendTextFile(String data, String file) {
        try {
            final Path path = Paths.get(file);
            Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        } catch (final IOException e) {
            System.out.println("WARNING: Unable to write file: " + file);
            return false;
        }
        return true;
    }

    /**
     * Reads UTF-8 text file, creates when not exists.
     * <p>
     * (Use Iterator<String> in concurrent situations!)
     * <pre>{@code
     * List<String> data = readTextFileAllLines(fileName);
     * Iterator<String> i = data.iterator();
     * while (i.hasNext()) {
     *   String s = i.next().replaceAll("\\s", "");
     *   if (!s.isEmpty()) {
     *     ...
     * }</pre>
     *
     * @param file File Name
     * @return List containing File contents or empty List
     */
    public static List<String> readTextFileAllLines(String file) {
        try {
            final Path path = Paths.get(file);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            System.out.println("WARNING: Unable to read file, creating new file: " + file);
            appendTextFile("", file);
        }
        return Collections.emptyList();
    }

}
