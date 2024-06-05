package cc.novoline.modules.configurations.holder;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.ModuleManager$ModuleCreator;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.configurations.property.mapper.PropertyMapper;
import cc.novoline.utils.java.Checks;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiFunction;
import net.ahS;
import net.ahu;

public final class CreatingModuleHolder extends ModuleHolder {
   private final ModuleManager moduleManager;
   private final ModuleManager$ModuleCreator creator;
   private final Map fields;

   private CreatingModuleHolder(ModuleManager var1, String var2, ModuleManager$ModuleCreator var3) {
      super(var2, var3.create(var1));
      this.moduleManager = var1;
      this.creator = var3;
      this.fields = this.collectFields();
   }

   public static CreatingModuleHolder of(ModuleManager var0, String var1, ModuleManager$ModuleCreator var2) {
      Checks.notNull(var0, "module manager");
      Checks.notBlank(var1, "name");
      Checks.notNull(var2, "module creator");
      return new CreatingModuleHolder(var0, var1, var2);
   }

   private Map collectFields() {
      Object2ObjectArrayMap var2 = new Object2ObjectArrayMap();
      ModuleHolder.d();
      Class var3 = this.module.getClass();
      Field[] var4 = AbstractModule.class.getDeclaredFields();
      int var5 = var4.length;
      int var6 = 0;
      if(var6 < var5) {
         Field var7 = var4[var6];
         this.collectField(var2, var7, CreatingModuleHolder::lambda$collectFields$0);
         ++var6;
      }

      var4 = var3.getDeclaredFields();
      var5 = var4.length;
      var6 = 0;
      if(var6 < var5) {
         Field var13 = var4[var6];
         this.collectField(var2, var13, CreatingModuleHolder::lambda$collectFields$1);
         ++var6;
      }

      return var2;
   }

   private void collectField(Map var1, Field var2, BiFunction var3) {
      ModuleHolder.d();
      Property var5 = (Property)var2.getAnnotation(Property.class);
      var2.setAccessible(true);
      String var6 = PropertyMapper.mapPropertyPath(var2, var5);
      ahu var7 = (ahu)var3.apply(var6, var2);
      var1.put(var6, var7);
   }

   public AbstractModule createNew() {
      return this.creator.create(this.moduleManager);
   }

   public Map getFields() {
      return this.fields;
   }

   private static ahu lambda$collectFields$1(String var0, Field var1) {
      return new ahu(var0, var1);
   }

   private static ahu lambda$collectFields$0(String var0, Field var1) {
      return (ahu)(var0.equalsIgnoreCase("enabled")?new ahS(var0, var1):new ahu(var0, var1));
   }
}
