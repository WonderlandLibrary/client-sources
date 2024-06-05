package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aQl;
import net.af_;
import optifine.Config;
import optifine.MatchBlock;
import optifine.ReflectorField;

public class ReflectorClass {
   private String targetClassName;
   private boolean checked;
   private Class targetClass;

   public ReflectorClass(String var1) {
      this(var1, false);
   }

   public ReflectorClass(String var1, boolean var2) {
      this.targetClassName = null;
      this.checked = false;
      this.targetClass = null;
      this.targetClassName = var1;
      Class var3 = this.getTargetClass();
   }

   public ReflectorClass(Class var1) {
      this.targetClassName = null;
      this.checked = false;
      this.targetClass = null;
      this.targetClass = var1;
      this.targetClassName = var1.getName();
      this.checked = true;
   }

   public Class getTargetClass() {
      if(this.checked) {
         return this.targetClass;
      } else {
         this.checked = true;

         Throwable var1;
         try {
            ReflectorClass var10000 = this;
            ReflectorClass var10001 = this;

            try {
               var10000.targetClass = af_.a(var10001.targetClassName);
               return this.targetClass;
            } catch (Throwable var2) {
               var1 = var2;
            }
         } catch (ClassNotFoundException var3) {
            Config.log("(Reflector) Class not present: " + this.targetClassName);
            return this.targetClass;
         }

         var1.printStackTrace();
         return this.targetClass;
      }
   }

   public boolean exists() {
      return this.getTargetClass() != null;
   }

   public String getTargetClassName() {
      return this.targetClassName;
   }

   public boolean isInstance(Object var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return this.getTargetClass() == null?false:this.getTargetClass().isInstance(var1);
   }

   public ReflectorField makeField(String var1) {
      return new ReflectorField(this, var1);
   }

   public aQl b(String var1) {
      return new aQl(this, var1);
   }

   public aQl a(String var1, Class[] var2) {
      return new aQl(this, var1, var2);
   }

   public aQl a(String var1, Class[] var2, boolean var3) {
      return new aQl(this, var1, var2, var3);
   }

   private static ClassNotFoundException a(ClassNotFoundException var0) {
      return var0;
   }
}
