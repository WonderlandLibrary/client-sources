package xyz.cucumber.base.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import xyz.cucumber.base.file.files.AccountsFile;
import xyz.cucumber.base.file.files.BindFile;
import xyz.cucumber.base.file.files.ClientSettingsFile;
import xyz.cucumber.base.utils.FileUtils;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;

public class FileManager {
   private ArrayList<FileUtils> files = new ArrayList<>();

   public FileManager() {
      File dir = new File("Gothaj");
      File directory = new File("Gothaj/configs");
      File file = new File(directory, "default.json");
      if (!dir.exists()) {
         dir.mkdir();
      }

      if (!directory.exists()) {
         directory.mkdir();
      }

      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var5) {
            var5.printStackTrace();
         }

         ConfigFileUtils.save(file, false);
      }

      this.files.add(new BindFile());
      this.files.add(new AccountsFile());
      this.files.add(new ClientSettingsFile());
   }

   public void load() {
      try {
         for (FileUtils file : this.files) {
            file.load();
         }
      } catch (Exception var3) {
      }
   }

   public void save() {
      try {
         for (FileUtils file : this.files) {
            file.save();
         }
      } catch (Exception var3) {
      }
   }

   public FileUtils getFile(Class file) {
      for (FileUtils f : this.files) {
         if (f.getClass() == file) {
            return f;
         }
      }

      return null;
   }
}
