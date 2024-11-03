package com.viaversion.viaversion.libs.gson.internal;

public final class JavaVersion {
   private static final int majorJavaVersion = determineMajorJavaVersion();

   private static int determineMajorJavaVersion() {
      String javaVersion = System.getProperty("java.version");
      return getMajorJavaVersion(javaVersion);
   }

   static int getMajorJavaVersion(String javaVersion) {
      int version = parseDotted(javaVersion);
      if (version == -1) {
         version = extractBeginningInt(javaVersion);
      }

      return version == -1 ? 6 : version;
   }

   private static int parseDotted(String javaVersion) {
      try {
         String[] parts = javaVersion.split("[._]");
         int firstVer = Integer.parseInt(parts[0]);
         return firstVer == 1 && parts.length > 1 ? Integer.parseInt(parts[1]) : firstVer;
      } catch (NumberFormatException var3) {
         return -1;
      }
   }

   private static int extractBeginningInt(String javaVersion) {
      try {
         StringBuilder num = new StringBuilder();

         for (int i = 0; i < javaVersion.length(); i++) {
            char c = javaVersion.charAt(i);
            if (!Character.isDigit(c)) {
               break;
            }

            num.append(c);
         }

         return Integer.parseInt(num.toString());
      } catch (NumberFormatException var4) {
         return -1;
      }
   }

   public static int getMajorJavaVersion() {
      return majorJavaVersion;
   }

   public static boolean isJava9OrLater() {
      return majorJavaVersion >= 9;
   }

   private JavaVersion() {
   }
}
