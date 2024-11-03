package me.jDev.xenza.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager<T> {
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
   public static final Gson NORMALGSON = new GsonBuilder().setPrettyPrinting().create();

   public void saveFile(String filePath, String fileName, List<T> list) {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
         try {
            Files.createDirectories(path);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      try {
         String s = GSON.toJson(list);
         BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
         bufferedWriter.write(s);
         bufferedWriter.flush();
         bufferedWriter.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void saveAllFile(String filePath, String fileName, List<T> list) {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
         try {
            Files.createDirectories(path);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      try {
         String s = NORMALGSON.toJson(list);
         BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
         bufferedWriter.write(s);
         bufferedWriter.flush();
         bufferedWriter.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void saveAllFile(String filePath, String fileName, T object) {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
         try {
            Files.createDirectories(path);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      try {
         String s = GSON.toJson(object);
         BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
         bufferedWriter.write(s);
         bufferedWriter.flush();
         bufferedWriter.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void saveFile(String filePath, String fileName, T object) {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
         try {
            Files.createDirectories(path);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      try {
         String s = GSON.toJson(object);
         BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath, fileName)));
         bufferedWriter.write(s);
         bufferedWriter.flush();
         bufferedWriter.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public ArrayList<T> readFile(String filePath, String fileName) {
      ArrayList<T> list = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(filePath + "/" + fileName));
         list = GSON.fromJson(reader, (new TypeToken<List<T>>() {
         }).getType());
         reader.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return list;
   }

   public ArrayList<T> readFileAll(String filePath, String fileName) {
      ArrayList<T> list = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(filePath + "/" + fileName));
         list = NORMALGSON.fromJson(reader, (new TypeToken<List<T>>() {
         }).getType());
         reader.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return list;
   }

   public T readSingleFile(String filePath, String fileName) {
      T list = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(filePath + "/" + fileName));
         list = GSON.fromJson(reader, (new TypeToken<T>() {
         }).getType());
         reader.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return list;
   }

   public T readSingleFileAll(String filePath, String fileName) {
      T list = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(filePath + "/" + fileName));
         list = NORMALGSON.fromJson(reader, (new TypeToken<T>() {
         }).getType());
         reader.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return list;
   }
}
