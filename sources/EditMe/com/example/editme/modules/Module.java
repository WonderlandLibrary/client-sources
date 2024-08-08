package com.example.editme.modules;

import com.example.editme.EditmeMod;
import com.example.editme.commands.Command;
import com.example.editme.events.RenderEvent;
import com.example.editme.gui.font.CFontRenderer;
import com.example.editme.settings.Setting;
import com.example.editme.util.builders.SettingBuilder;
import com.example.editme.util.client.Bind;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Font;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Module {
   public List settingList;
   private Setting enabled;
   public boolean alwaysListening;
   private final Setting name;
   private final Module.Category category;
   protected CFontRenderer fontRenderer;
   private final String description;
   protected static final Minecraft mc = Minecraft.func_71410_x();
   private Setting visible;
   private final String originalName = this.getAnnotation().name();
   private Setting bind;

   public int getWidth() {
      return 0;
   }

   public Bind getBind() {
      return (Bind)this.bind.getValue();
   }

   protected void onEnable() {
   }

   public String getName() {
      return (String)this.name.getValue();
   }

   public String getHudInfo() {
      return null;
   }

   public void destroy() {
   }

   public boolean isEnabled() {
      return (Boolean)this.enabled.getValue();
   }

   protected Setting register(Setting var1) {
      if (this.settingList == null) {
         this.settingList = new ArrayList();
      }

      this.settingList.add(var1);
      return SettingBuilder.register(var1, String.valueOf((new StringBuilder()).append("modules.").append(this.originalName)));
   }

   public Module.Category getCategory() {
      return this.category;
   }

   protected void onDisable() {
   }

   public String getDescription() {
      return this.description;
   }

   public String getOriginalName() {
      return this.originalName;
   }

   private Module.Info getAnnotation() {
      if (this.getClass().isAnnotationPresent(Module.Info.class)) {
         return (Module.Info)this.getClass().getAnnotation(Module.Info.class);
      } else {
         throw new IllegalStateException(String.valueOf((new StringBuilder()).append("No Annotation on class ").append(this.getClass().getCanonicalName()).append("!")));
      }
   }

   protected Setting register(SettingBuilder var1) {
      if (this.settingList == null) {
         this.settingList = new ArrayList();
      }

      Setting var2 = var1.buildAndRegister(String.valueOf((new StringBuilder()).append("modules.").append(this.name)));
      this.settingList.add(var2);
      return var2;
   }

   public void disable() {
      this.enabled.setValue(false);
      this.onDisable();
      if (!this.alwaysListening) {
         EditmeMod.EVENT_BUS.unsubscribe((Object)this);
      }

   }

   public void onRender() {
   }

   public String getBindName() {
      return ((Bind)this.bind.getValue()).toString();
   }

   public void setBind(Bind var1) {
      this.bind.setValue(var1);
   }

   public void enable() {
      this.enabled.setValue(true);
      this.onEnable();
      if (!this.alwaysListening) {
         EditmeMod.EVENT_BUS.subscribe((Object)this);
      }

   }

   public boolean isDisabled() {
      return !this.isEnabled();
   }

   public void onWorldRender(RenderEvent var1) {
   }

   public void onUpdate() {
   }

   public void setName(String var1) {
      this.name.setValue(var1);
      ModuleManager.updateLookup();
   }

   public void setEnabled(boolean var1) {
      boolean var2 = (Boolean)this.enabled.getValue();
      if (var2 != var1) {
         if (var1) {
            this.enable();
         } else {
            this.disable();
         }
      }

   }

   private static boolean lambda$new$0(Boolean var0) {
      return false;
   }

   public int getHeight() {
      return 0;
   }

   protected final void setAlwaysListening(boolean var1) {
      this.alwaysListening = var1;
      if (var1) {
         EditmeMod.EVENT_BUS.subscribe((Object)this);
      }

      if (!var1 && this.isDisabled()) {
         EditmeMod.EVENT_BUS.unsubscribe((Object)this);
      }

   }

   public void toggle() {
      this.setEnabled(!this.isEnabled());
   }

   public Module() {
      this.name = this.register(SettingsManager.s("Name", this.originalName));
      this.description = this.getAnnotation().description();
      this.category = this.getAnnotation().category();
      this.bind = this.register(SettingsManager.custom("Bind", Bind.none(), new Module.BindConverter(this)).build());
      this.enabled = this.register(SettingsManager.booleanBuilder("Enabled").withVisibility(Module::lambda$new$0).withValue(false).build());
      this.visible = this.register(SettingsManager.booleanBuilder("Visible").withValue(true).build());
      this.fontRenderer = new CFontRenderer(new Font("Arial", 1, 16), true, true);
      this.settingList = new ArrayList();
      this.alwaysListening = this.getAnnotation().alwaysListening();
      this.registerAll(this.bind, this.enabled, this.visible);
   }

   protected void registerAll(Setting... var1) {
      Setting[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Setting var5 = var2[var4];
         this.register(var5);
      }

   }

   protected void sendNotification(String var1) {
      Command.sendChatMessage(String.valueOf((new StringBuilder()).append("[").append(ChatFormatting.LIGHT_PURPLE.toString()).append(this.getName()).append(ChatFormatting.RESET.toString()).append("]  ").append(var1)));
   }

   @Retention(RetentionPolicy.RUNTIME)
   public @interface Info {
      boolean alwaysListening() default false;

      String name();

      Module.Category category();

      String description() default "Descriptionless";
   }

   private class BindConverter extends Converter {
      final Module this$0;

      protected Object doForward(Object var1) {
         return this.doForward((Bind)var1);
      }

      protected Object doBackward(Object var1) {
         return this.doBackward((JsonElement)var1);
      }

      protected JsonElement doForward(Bind var1) {
         return new JsonPrimitive(var1.toString());
      }

      protected Bind doBackward(JsonElement var1) {
         String var2 = var1.getAsString();
         if (var2.equalsIgnoreCase("None")) {
            return Bind.none();
         } else {
            boolean var3 = false;
            boolean var4 = false;
            boolean var5 = false;
            if (var2.startsWith("Ctrl+")) {
               var3 = true;
               var2 = var2.substring(5);
            }

            if (var2.startsWith("Alt+")) {
               var4 = true;
               var2 = var2.substring(4);
            }

            if (var2.startsWith("Shift+")) {
               var5 = true;
               var2 = var2.substring(6);
            }

            int var6 = -1;

            try {
               var6 = Keyboard.getKeyIndex(var2.toUpperCase());
            } catch (Exception var8) {
            }

            return var6 == 0 ? Bind.none() : new Bind(var3, var4, var5, var6);
         }
      }

      BindConverter(Module var1, Object var2) {
         this(var1);
      }

      private BindConverter(Module var1) {
         this.this$0 = var1;
      }
   }

   public static enum Category {
      COMBAT("Combat", false);

      private static final Module.Category[] $VALUES = new Module.Category[]{COMBAT, EXPLOITS, RENDER, MISC, PLAYER, MOVEMENT, CLIENT, OLDFAG, HUD, HIDDEN};
      PLAYER("Player", false),
      EXPLOITS("Exploits", false),
      CLIENT("Client", false),
      MOVEMENT("Movement", false);

      boolean hidden;
      String name;
      HIDDEN("Hidden", true),
      MISC("Misc", false),
      RENDER("Render", false),
      HUD("Hud", true),
      OLDFAG("Oldfag", false);

      public String getName() {
         return this.name;
      }

      public boolean isHidden() {
         return this.hidden;
      }

      private Category(String var3, boolean var4) {
         this.name = var3;
         this.hidden = var4;
      }
   }
}
