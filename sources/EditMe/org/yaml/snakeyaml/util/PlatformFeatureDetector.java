package org.yaml.snakeyaml.util;

public class PlatformFeatureDetector {
   private Boolean isRunningOnAndroid = null;

   public boolean isRunningOnAndroid() {
      if (this.isRunningOnAndroid == null) {
         this.isRunningOnAndroid = System.getProperty("java.runtime.name").startsWith("Android Runtime");
      }

      return this.isRunningOnAndroid;
   }
}
