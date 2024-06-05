package optifine;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import net.minecraft.client.Minecraft;
import optifine.Config;
import optifine.IFieldLocator;
import optifine.MatchBlock;
import optifine.ReflectorRaw;

public class FieldLocatorActionKeyF3 implements IFieldLocator {
   public Field getField() {
      Class var2 = Minecraft.class;
      MatchBlock.b();
      Field var3 = this.getFieldRenderChunksMany();
      if(var3 == null) {
         Config.log("(Reflector) Field not present: " + var2.getName() + ".actionKeyF3 (field renderChunksMany not found)");
         return null;
      } else {
         Field var4 = ReflectorRaw.getFieldAfter(Minecraft.class, var3, Boolean.TYPE, 0);
         if(var4 == null) {
            Config.log("(Reflector) Field not present: " + var2.getName() + ".actionKeyF3");
            return null;
         } else {
            return var4;
         }
      }
   }

   private Field getFieldRenderChunksMany() {
      Minecraft var2 = Minecraft.getMinecraft();
      boolean var3 = var2.renderChunksMany;
      Field[] var4 = Minecraft.class.getDeclaredFields();
      var2.renderChunksMany = true;
      Field[] var5 = ReflectorRaw.getFields(var2, var4, Boolean.TYPE, Boolean.TRUE);
      var2.renderChunksMany = false;
      Field[] var6 = ReflectorRaw.getFields(var2, var4, Boolean.TYPE, Boolean.FALSE);
      var2.renderChunksMany = var3;
      HashSet var7 = new HashSet(Arrays.asList(var5));
      MatchBlock.b();
      HashSet var8 = new HashSet(Arrays.asList(var6));
      HashSet var9 = new HashSet(var7);
      var9.retainAll(var8);
      Field[] var10 = (Field[])((Field[])((Field[])var9.toArray(new Field[var9.size()])));
      return var10.length != 1?null:var10[0];
   }
}
