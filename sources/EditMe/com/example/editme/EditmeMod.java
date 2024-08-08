package com.example.editme;

import com.example.editme.commands.Command;
import com.example.editme.events.ForgeEventProcessor;
import com.example.editme.gui.EditmeGUI;
import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.client.LagCompensator;
import com.example.editme.util.client.ReflectionFields;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.command.CommandManager;
import com.example.editme.util.config.ConfigurationUtil;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import com.example.editme.util.setting.SettingsRegister;
import com.example.editme.util.tooltips.TooltipsUtil;
import com.google.common.base.Converter;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
   modid = "editme",
   name = "EDITME",
   version = "r5"
)
public class EditmeMod {
   public EditmeGUI guiManager;
   public static final String MODNAME = "EDITME";
   public static final EventBus EVENT_BUS = new EventManager();
   public static final List KEYS = new ArrayList();
   private static String initId;
   public static final String MODVER = "r5";
   public static final String EDITME_CONFIG_NAME_DEFAULT = "EDITMEConfig.json";
   public static final String hwidUrl = "https://www.youtube.com/watch?v=CQZYzGO8QlM";
   public EditmeHUDEditor hudEditor;
   public Setting guiStateSetting = SettingsManager.custom("gui", new JsonObject(), new Converter(this) {
      final EditmeMod this$0;

      protected JsonObject doForward(JsonObject var1) {
         return var1;
      }

      {
         this.this$0 = var1;
      }

      protected Object doBackward(Object var1) {
         return this.doBackward((JsonObject)var1);
      }

      protected Object doForward(Object var1) {
         return this.doForward((JsonObject)var1);
      }

      protected JsonObject doBackward(JsonObject var1) {
         return var1;
      }
   }).buildAndRegister("");
   public static final String MODID = "editme";
   public static final char colour = 167;
   public static final Logger log = LogManager.getLogger("EDITME");
   @Instance
   private static EditmeMod INSTANCE;
   public CommandManager commandManager;

   @EventHandler
   public void init(FMLInitializationEvent var1) {
      log.info("\n\nInitializing EDITME r5");
      ModuleManager.initialize();
      Stream var10000 = ModuleManager.getModules().stream().filter(EditmeMod::lambda$init$0);
      EventBus var10001 = EVENT_BUS;
      var10000.forEach(var10001::subscribe);
      MinecraftForge.EVENT_BUS.register(new ForgeEventProcessor());
      LagCompensator.INSTANCE = new LagCompensator();
      ReflectionFields.init(Minecraft.func_71410_x());
      Wrapper.init();
      this.commandManager = new CommandManager();
      Friends.initFriends();
      SettingsRegister.register("commandPrefix", Command.commandPrefix);
      ConfigurationUtil.loadConfiguration();
      log.info("Settings loaded");
      ModuleManager.updateLookup();
      ModuleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);
      ModuleManager.disableModule("Spectate");
      ModuleManager.disableModule("PacketCanceller");
      this.guiManager = new EditmeGUI();
      this.guiManager.initialize();
      this.hudEditor = new EditmeHUDEditor();
      this.hudEditor.initialize();
      log.info("EDITME Mod initialized!\n");
   }

   private static boolean lIIIIIIlIllI(Object var0) {
      return var0 != null;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public void resetGuiManager() {
      this.guiManager = null;
      this.guiManager = new EditmeGUI();
      this.guiManager.initialize();
   }

   public void preInit(FMLPreInitializationEvent var1) {
   }

   private static boolean lIIIIIIlIlll(int var0) {
      return var0 == 0;
   }

   public static EditmeMod getInstance() {
      return INSTANCE;
   }

   public void postInit(FMLPostInitializationEvent var1) {
      TooltipsUtil.post();
   }

   private static boolean lIIIIIIlIlIl(int var0, int var1) {
      return var0 == var1;
   }

   public EditmeHUDEditor getHudEditor() {
      return this.hudEditor;
   }

   private static boolean lambda$init$0(Module var0) {
      return var0.alwaysListening;
   }

   public EditmeGUI getGuiManager() {
      return this.guiManager;
   }
}
