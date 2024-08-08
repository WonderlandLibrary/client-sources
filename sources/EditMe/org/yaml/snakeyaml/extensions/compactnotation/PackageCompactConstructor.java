package org.yaml.snakeyaml.extensions.compactnotation;

public class PackageCompactConstructor extends CompactConstructor {
   private String packageName;

   public PackageCompactConstructor(String var1) {
      this.packageName = var1;
   }

   protected Class getClassForName(String var1) throws ClassNotFoundException {
      if (var1.indexOf(46) < 0) {
         try {
            Class var2 = Class.forName(this.packageName + "." + var1);
            return var2;
         } catch (ClassNotFoundException var3) {
         }
      }

      return super.getClassForName(var1);
   }
}
