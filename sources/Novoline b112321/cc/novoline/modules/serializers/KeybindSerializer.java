package cc.novoline.modules.serializers;

import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.binds.MouseKeybind;
import cc.novoline.modules.serializers.PropertySerializer;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public final class KeybindSerializer implements TypeSerializer {
   public void serialize(TypeToken var1, ModuleKeybind var2, ConfigurationNode var3) {
      int[] var4 = PropertySerializer.b();
      if(var2 == null) {
         var3.setValue((Object)null);
      } else if(var2.getKey() != 0) {
         if(var2 instanceof KeyboardKeybind) {
            String var5 = "keyboard";
         }

         if(var2 instanceof MouseKeybind) {
            String var6 = "mouse";
         }

         var3.setValue((Object)null);
      }
   }

   public ModuleKeybind deserialize(TypeToken var1, ConfigurationNode var2) {
      int[] var3 = PropertySerializer.b();
      if(var2.getValue() == null) {
         return null;
      } else {
         String var4 = var2.getNode(new Object[]{"type"}).getString();
         return null;
      }
   }
}
