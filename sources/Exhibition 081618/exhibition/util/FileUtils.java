package exhibition.util;

import exhibition.Client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;

public final class FileUtils {
   public static List read(File inputFile) {
      ArrayList readContent = new ArrayList();

      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));

         String str;
         while((str = in.readLine()) != null) {
            readContent.add(str);
         }

         in.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return readContent;
   }

   public static void write(File outputFile, List writeContent, boolean overrideContent) {
      try {
         Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
         Iterator var4 = writeContent.iterator();

         while(var4.hasNext()) {
            String outputLine = (String)var4.next();
            out.write(outputLine + System.getProperty("line.separator"));
         }

         out.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static File getConfigDir() {
      File file = new File(Minecraft.getMinecraft().mcDataDir, Client.clientName);
      if (!file.exists()) {
         file.mkdir();
      }

      return file;
   }

   public static File getConfigFile(String name) {
      File file = new File(getConfigDir(), String.format("%s.txt", name));
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return file;
   }
}
