package cc.novoline.modules.serializers;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.configurations.holder.StoringModuleHolder;
import cc.novoline.modules.serializers.PropertySerializer;
import cc.novoline.utils.java.Checks;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import java.util.Iterator;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ValueType;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ModuleMapSerializer implements TypeSerializer {
   private final Logger logger = LogManager.getLogger();
   private final ModuleManager moduleManager;

   public ModuleMapSerializer(ModuleManager var1) {
      Checks.notNull(var1, "module manager");
      this.moduleManager = var1;
   }

   public void serialize(TypeToken var1, ModuleArrayMap var2, ConfigurationNode var3) throws X9 {
      int[] var4 = PropertySerializer.b();
      if(var2 == null) {
         var3.setValue((Object)null);
      } else {
         ObjectIterator var5 = var2.object2ObjectEntrySet().iterator();
         if(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            String var7 = (String)var6.getKey();
            ModuleHolder var8 = (ModuleHolder)var6.getValue();
            if(var8 != null) {
               TypeToken var9 = ((ModuleHolder)var6.getValue()).getTypeToken();
               AbstractModule var10 = ((ModuleHolder)var6.getValue()).getModule();
               var3.getNode(new Object[]{var7}).a(var9, var10);
            }
         }

      }
   }

   public ModuleArrayMap deserialize(TypeToken var1, ConfigurationNode var2) {
      PropertySerializer.b();
      ModuleArrayMap var4 = this.moduleManager.getModuleManager();
      ModuleArrayMap var5 = new ModuleArrayMap();
      Iterator var7 = var2.getChildrenMap().entrySet().iterator();
      if(var7.hasNext()) {
         java.util.Map.Entry var8 = (java.util.Map.Entry)var7.next();
         String var9 = var8.getKey().toString();
         ConfigurationNode var10 = (ConfigurationNode)var8.getValue();
         ModuleHolder var6;
         if(var10.getValueType() != ValueType.NULL && (var6 = (ModuleHolder)var4.get(var9)) != null) {
            AbstractModule var11 = null;
            X9 var12 = null;

            try {
               var11 = (AbstractModule)var10.getValue(var6.getTypeToken());
            } catch (X9 var14) {
               var12 = var14;
            }

            if(var11 == null) {
               String var13 = "Cannot initiate " + var9 + " module. Skipping it...";
               this.logger.warn(var13, var12);
               this.logger.warn(var13);
            }

            var5.put(var9, StoringModuleHolder.of(var9, var11));
         }
      }

      if(acE.b() == null) {
         PropertySerializer.b(new int[1]);
      }

      return var5;
   }

   private static X9 a(X9 var0) {
      return var0;
   }
}
