package xyz.cucumber.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import java.io.File;

public class FileUtils {
   public static Gson gson = new Gson();
   public static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
   public static JsonParser jsonParser = new JsonParser();
   private File file;
   private File directory;

   public FileUtils(String directory, String name) {
      this.directory = new File(directory);
      this.file = new File(directory, name);
      if (!this.directory.exists()) {
         this.directory.mkdirs();
      }
   }

   public void save() {
   }

   public void load() {
   }

   public File getFile() {
      return this.file;
   }

   public void setFile(File file) {
      this.file = file;
   }

   public File getDirectory() {
      return this.directory;
   }

   public void setDirectory(File directory) {
      this.directory = directory;
   }
}
