package org.yaml.snakeyaml.constructor;

public class CustomClassLoaderConstructor extends Constructor {
   private ClassLoader loader;

   public CustomClassLoaderConstructor(ClassLoader var1) {
      this(Object.class, var1);
   }

   public CustomClassLoaderConstructor(Class var1, ClassLoader var2) {
      super(var1);
      this.loader = CustomClassLoaderConstructor.class.getClassLoader();
      if (var2 == null) {
         throw new NullPointerException("Loader must be provided.");
      } else {
         this.loader = var2;
      }
   }

   protected Class getClassForName(String var1) throws ClassNotFoundException {
      return Class.forName(var1, true, this.loader);
   }
}
