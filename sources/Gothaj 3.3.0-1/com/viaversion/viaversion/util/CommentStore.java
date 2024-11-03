package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommentStore {
   private final Map<String, List<String>> headers = new HashMap<>();
   private final String pathSeparator;
   private final String pathSeparatorQuoted;
   private final int indents;
   private List<String> mainHeader = new ArrayList<>();

   public CommentStore(char pathSeparator, int indents) {
      this.pathSeparator = Character.toString(pathSeparator);
      this.pathSeparatorQuoted = Pattern.quote(this.pathSeparator);
      this.indents = indents;
   }

   public void mainHeader(String... header) {
      this.mainHeader = Arrays.asList(header);
   }

   public List<String> mainHeader() {
      return this.mainHeader;
   }

   public void header(String key, String... header) {
      this.headers.put(key, Arrays.asList(header));
   }

   public List<String> header(String key) {
      return this.headers.get(key);
   }

   public void storeComments(InputStream inputStream) throws IOException {
      this.mainHeader.clear();
      this.headers.clear();

      String data;
      try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
         data = CharStreams.toString(reader);
      }

      List<String> currentComments = new ArrayList<>();
      boolean header = true;
      boolean multiLineValue = false;
      int currentIndents = 0;
      String key = "";

      for (String line : data.split("\n")) {
         String s = line.trim();
         if (s.startsWith("#")) {
            currentComments.add(s);
         } else {
            if (header) {
               if (!currentComments.isEmpty()) {
                  currentComments.add("");
                  this.mainHeader.addAll(currentComments);
                  currentComments.clear();
               }

               header = false;
            }

            if (s.isEmpty()) {
               currentComments.add(s);
            } else if (s.startsWith("- |")) {
               multiLineValue = true;
            } else {
               int indent = this.getIndents(line);
               int indents = indent / this.indents;
               if (multiLineValue) {
                  if (indents > currentIndents) {
                     continue;
                  }

                  multiLineValue = false;
               }

               if (indents <= currentIndents) {
                  String[] array = key.split(this.pathSeparatorQuoted);
                  int backspace = currentIndents - indents + 1;
                  int delta = array.length - backspace;
                  key = delta >= 0 ? this.join(array, delta) : key;
               }

               String separator = key.isEmpty() ? "" : this.pathSeparator;
               String lineKey = line.indexOf(58) != -1 ? line.split(Pattern.quote(":"))[0] : line;
               key = key + separator + lineKey.substring(indent);
               currentIndents = indents;
               if (!currentComments.isEmpty()) {
                  this.headers.put(key, new ArrayList<>(currentComments));
                  currentComments.clear();
               }
            }
         }
      }
   }

   public void writeComments(String rawYaml, File output) throws IOException {
      StringBuilder fileData = new StringBuilder();

      for (String mainHeaderLine : this.mainHeader) {
         fileData.append(mainHeaderLine).append('\n');
      }

      fileData.deleteCharAt(fileData.length() - 1);
      int currentKeyIndents = 0;
      String key = "";

      for (String line : rawYaml.split("\n")) {
         if (!line.isEmpty()) {
            int indent = this.getIndents(line);
            int indents = indent / this.indents;
            String substring = line.substring(indent);
            boolean keyLine;
            if (substring.trim().isEmpty() || substring.charAt(0) == '-') {
               keyLine = false;
            } else if (indents <= currentKeyIndents) {
               String[] array = key.split(this.pathSeparatorQuoted);
               int backspace = currentKeyIndents - indents + 1;
               key = this.join(array, array.length - backspace);
               keyLine = true;
            } else {
               keyLine = line.indexOf(58) != -1;
            }

            if (!keyLine) {
               fileData.append(line).append('\n');
            } else {
               String newKey = substring.split(Pattern.quote(":"))[0];
               if (!key.isEmpty()) {
                  key = key + this.pathSeparator;
               }

               key = key + newKey;
               List<String> strings = this.headers.get(key);
               if (strings != null && !strings.isEmpty()) {
                  String indentText = indent > 0 ? line.substring(0, indent) : "";

                  for (String comment : strings) {
                     if (comment.isEmpty()) {
                        fileData.append('\n');
                     } else {
                        fileData.append(indentText).append(comment).append('\n');
                     }
                  }
               }

               currentKeyIndents = indents;
               fileData.append(line).append('\n');
            }
         }
      }

      Files.write(fileData.toString(), output, StandardCharsets.UTF_8);
   }

   private int getIndents(String line) {
      int count = 0;

      for (int i = 0; i < line.length() && line.charAt(i) == ' '; i++) {
         count++;
      }

      return count;
   }

   private String join(String[] array, int length) {
      String[] copy = new String[length];
      System.arraycopy(array, 0, copy, 0, length);
      return String.join(this.pathSeparator, copy);
   }
}
