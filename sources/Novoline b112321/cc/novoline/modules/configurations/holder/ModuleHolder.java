package cc.novoline.modules.configurations.holder;

import cc.novoline.modules.AbstractModule;
import com.google.common.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

public abstract class ModuleHolder implements Cloneable {
   protected final String name;
   protected AbstractModule module;
   protected TypeToken typeToken;
   private static String[] b;

   public ModuleHolder(@NotNull String var1, @NotNull AbstractModule var2) {
      this.name = var1;
      this.module = var2;
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   @NotNull
   public AbstractModule getModule() {
      return this.module;
   }

   @NotNull
   public TypeToken getTypeToken() {
      String[] var1 = d();
      return this.typeToken != null?this.typeToken:(this.typeToken = TypeToken.of(this.module.getClass()));
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public String toString() {
      return "ModuleHolder{name=\'" + this.name + '\'' + ", module=" + this.module + '}';
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] d() {
      return b;
   }

   static {
      b(new String[1]);
   }
}
