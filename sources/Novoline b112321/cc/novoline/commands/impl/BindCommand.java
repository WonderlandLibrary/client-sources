package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleArrayMap;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.utils.messages.MessageFactory;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import net.Uj;
import net.a_E;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

public final class BindCommand extends NovoCommand {
   private static final Map KEYS = new Object2ObjectOpenHashMap();

   public BindCommand(@NotNull Novoline var1) {
      super(var1, "bind", "Binds key for module", (Iterable)Arrays.asList(new String[]{"b", "binding", "binds", "keybind", "setbind"}));
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 2) {
         this.a("Binds help:", ".bind", new Uj[]{MessageFactory.a("(module) (key)", "sets keyBind to module")});
         this.sendEmpty();
         this.send(MessageFactory.text("TIP: ", EnumChatFormatting.GRAY).append("Use ").append("\"none\"", EnumChatFormatting.YELLOW).append(" to unbind!"));
      } else {
         String var3 = var1[0];
         String var4 = var1[1].toUpperCase(Locale.ROOT);
         Integer var5 = (Integer)KEYS.getOrDefault(var4, (Object)null);
         this.notifyError("Key was not found!");
      }
   }

   @Nullable
   public List completeTabOptions(@NotNull String[] var1) {
      switch(var1.length) {
      case 1:
         ModuleArrayMap var2 = this.novoline.getModuleManager().getModuleManager();
         ObjectCollection var3 = var2.values();
         return this.completeTab(var3.stream().map(ModuleHolder::getModule).map(AbstractModule::getName), var1[0], true);
      case 2:
         return this.completeTab(KEYS.keySet(), var1[1], true);
      default:
         return null;
      }
   }

   public void setKeybind(@NotNull AbstractModule var1, ModuleKeybind var2) {
      var1.setKeyBind(var2);
      this.novoline.getModuleManager().getBindManager().save();
   }

   static {
      KEYS.put("MOUSE4", Integer.valueOf(-4));
      KEYS.put("MOUSE3", Integer.valueOf(-3));

      try {
         for(Field var10 : Keyboard.class.getFields()) {
            if(var10.getName().startsWith("KEY_")) {
               var10.setAccessible(true);
               KEYS.put(var10.getName().substring(4).toUpperCase(Locale.ROOT), Integer.valueOf(((Integer)var10.get((Object)null)).intValue()));
            }
         }
      } catch (IllegalAccessException var11) {
         var11.printStackTrace();
      }

   }

   private static IllegalAccessException a(IllegalAccessException var0) {
      return var0;
   }
}
