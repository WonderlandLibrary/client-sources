package exhibition.gui.altmanager;

import exhibition.Client;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;

public class FileManager {
   public static ArrayList Files = new ArrayList();
   private static File directory;

   public FileManager() {
      this.makeDirectories();
      Files.add(new Alts("alts", false, true));
   }

   public void loadFiles() {
      Iterator var1 = Files.iterator();

      while(var1.hasNext()) {
         FileManager.CustomFile f = (FileManager.CustomFile)var1.next();

         try {
            if (f.loadOnStart()) {
               f.loadFile();
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public void saveFiles() {
      Iterator var1 = Files.iterator();

      while(var1.hasNext()) {
         FileManager.CustomFile f = (FileManager.CustomFile)var1.next();

         try {
            f.saveFile();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public FileManager.CustomFile getFile(Class clazz) {
      Iterator var2 = Files.iterator();

      FileManager.CustomFile file;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         file = (FileManager.CustomFile)var2.next();
      } while(file.getClass() != clazz);

      return file;
   }

   public void makeDirectories() {
      if (!directory.exists()) {
         if (directory.mkdir()) {
            System.out.println("Directory is created!");
         } else {
            System.out.println("Failed to create directory!");
         }
      }

   }

   static {
      directory = new File(Minecraft.getMinecraft().mcDataDir.toString() + "\\" + Client.clientName);
   }

   public abstract static class CustomFile {
      private final File file;
      private final String name;
      private boolean load;

      public CustomFile(String name, boolean Module2, boolean loadOnStart) {
         this.name = name;
         this.load = loadOnStart;
         this.file = new File(FileManager.directory, name + ".txt");
         if (!this.file.exists()) {
            try {
               this.saveFile();
            } catch (Exception var5) {
               var5.printStackTrace();
            }
         }

      }

      public final File getFile() {
         return this.file;
      }

      private boolean loadOnStart() {
         return this.load;
      }

      public final String getName() {
         return this.name;
      }

      public abstract void loadFile() throws IOException;

      public abstract void saveFile() throws IOException;
   }
}
