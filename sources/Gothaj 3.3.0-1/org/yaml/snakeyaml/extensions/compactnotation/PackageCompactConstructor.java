package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.LoaderOptions;

public class PackageCompactConstructor extends CompactConstructor {
   private final String packageName;

   public PackageCompactConstructor(String packageName) {
      super(new LoaderOptions());
      this.packageName = packageName;
   }

   @Override
   protected Class<?> getClassForName(String name) throws ClassNotFoundException {
      if (name.indexOf(46) < 0) {
         try {
            return Class.forName(this.packageName + "." + name);
         } catch (ClassNotFoundException var3) {
         }
      }

      return super.getClassForName(name);
   }
}
