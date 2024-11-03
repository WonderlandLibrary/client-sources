package xyz.cucumber.base.utils.cfgs;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class PublicConfigUtils {
   private static final String configs = "Configs.txt";
   private static final String repo = "https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/";
   private static HashMap<String, String> publicConfigs = new HashMap<>();

   public static void reload() {
      String[] files = loadNames();

      for (String file : files) {
         publicConfigs.put(file, scrapeUrl("https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/" + file + ".json"));
      }

      System.out.println(publicConfigs.size());
   }

   private static String[] loadNames() {
      try {
         System.out.println(scrapeUrl("https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/Configs.txt"));
         return scrapeUrl("https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/Configs.txt").split("-");
      } catch (Exception var1) {
         return new String[]{"no internet"};
      }
   }

   private static String scrapeUrl(String ur) {
      try {
         URL url = new URL(ur);
         Scanner scan = new Scanner(url.openStream());
         String currContent = new String();

         while (scan.hasNext()) {
            currContent = currContent + scan.nextLine();
         }

         scan.close();
         return currContent;
      } catch (IOException var4) {
         return null;
      }
   }

   public static String getConfigs() {
      return "Configs.txt";
   }

   public static String getRepo() {
      return "https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/";
   }

   public static HashMap<String, String> getPublicConfigs() {
      return publicConfigs;
   }

   public static void setPublicConfigs(HashMap<String, String> publicConfigs) {
      PublicConfigUtils.publicConfigs = publicConfigs;
   }
}
