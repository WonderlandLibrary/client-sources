package exhibition.management;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exhibition.Client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Saveable {
   private final Gson gson = (new GsonBuilder()).setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
   private SubFolder folderType;
   private File folder;
   private File file;

   public boolean save() {
      String data = this.gson.toJson(this);

      try {
         this.checkFile();
         FileWriter writer = new FileWriter(this.getFile());
         writer.write(data);
         writer.close();
         return true;
      } catch (IOException var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public Saveable load() {
      try {
         this.checkFile();
         BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
         return (Saveable)this.gson.fromJson(br, this.getClass());
      } catch (IOException | RuntimeException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   protected void checkFile() throws IOException {
      File file = this.getFile();
      if (!file.exists()) {
         File folder = this.getFolder();
         if (!folder.exists()) {
            folder.mkdirs();
         }

         file.createNewFile();
      }

   }

   public void setupFolder() {
      if (this.folderType == null) {
         this.folderType = SubFolder.Other;
      }

      String basePath = Client.getDataDir().getAbsolutePath();
      String newPath = basePath + (basePath.endsWith(File.separator) ? this.folderType.getFolderName() : File.separator + this.folderType.getFolderName());
      this.folder = new File(newPath);
   }

   public void setupFile() {
      if (this.folder == null) {
         this.setupFolder();
      }

      String fileName = this.getFileName();
      String basePath = this.folder.getAbsolutePath();
      String newPath = basePath + (basePath.endsWith(File.separator) ? fileName : File.separator + fileName);
      this.file = new File(newPath);
   }

   public File getFolder() {
      if (this.folder == null) {
         this.setupFolder();
      }

      return this.folder;
   }

   public File getFile() {
      if (this.file == null) {
         this.setupFile();
      }

      return this.file;
   }

   public SubFolder getFolderType() {
      return this.folderType;
   }

   public void setFolderType(SubFolder folderType) {
      this.folderType = folderType;
   }

   public String getFileName() {
      return this.getClass().getSimpleName() + ".json";
   }
}
