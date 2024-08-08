package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.modules.Module;
import com.example.editme.settings.BooleanSetting;
import com.example.editme.settings.EnumSetting;
import com.example.editme.settings.NumberSetting;
import com.example.editme.settings.Setting;
import com.example.editme.settings.StringSetting;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ModuleSettingsComponent extends Component {
   private final Module module;
   private final ArrayList components = new ArrayList();

   private static void lambda$onKeyTyped$4(char var0, int var1, Component var2) {
      var2.onKeyTyped(var0, var1);
   }

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
      if (this.isExtended()) {
         this.getComponents().forEach(ModuleSettingsComponent::lambda$onKeyTyped$4);
      }

   }

   private static String[] lambda$initialize$1(int var0) {
      return new String[var0];
   }

   private static String lambda$initialize$0(Object var0) {
      return var0.toString().toUpperCase();
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      if (this.getModule().isEnabled()) {
         RenderUtil.drawRect(this.getFinishedX() + 2.0F, this.getFinishedY() + 1.0F, this.getWidth() - 4.0F, this.getHeight() - 2.0F, (new Color(0, 102, 204, 204)).getRGB());
      }

      fontRenderer.drawStringWithShadow(this.getName(), (double)(this.getFinishedX() + 4.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), this.getModule().isEnabled() ? -1 : -5592406);
      if (this.isExtended()) {
         this.getComponents().forEach(ModuleSettingsComponent::lambda$drawComponent$3);
      }

   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      boolean var4 = mouseWithinBounds(var1, var2, (double)this.getFinishedX(), (double)this.getFinishedY(), (double)this.getWidth(), (double)this.getHeight());
      if (var4) {
         switch(var3) {
         case 0:
            if (this.getModule() instanceof Module) {
               Module var5 = this.getModule();
               var5.toggle();
            }
            break;
         case 1:
            if (!this.getComponents().isEmpty()) {
               this.setExtended(!this.isExtended());
            }
         }
      }

      if (this.isExtended()) {
         this.getComponents().forEach(ModuleSettingsComponent::lambda$onMouseClicked$5);
      }

   }

   public ModuleSettingsComponent(Module var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.module = var1;
   }

   private static void lambda$onMouseClicked$5(int var0, int var1, int var2, Component var3) {
      var3.onMouseClicked(var0, var1, var2);
   }

   private void lambda$onMove$2(Component var1) {
      var1.onMove(this.getFinishedX(), this.getFinishedY());
   }

   private static void lambda$onMouseReleased$6(int var0, int var1, int var2, Component var3) {
      var3.onMouseReleased(var0, var1, var2);
   }

   private static void lambda$drawComponent$3(int var0, int var1, float var2, Component var3) {
      var3.drawComponent(var0, var1, var2);
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
      this.getComponents().forEach(this::lambda$onMove$2);
   }

   public ArrayList getComponents() {
      return this.components;
   }

   public Module getModule() {
      return this.module;
   }

   public void initialize() {
      float var1 = this.getHeight();
      Iterator var2 = this.module.settingList.iterator();

      while(var2.hasNext()) {
         Setting var3 = (Setting)var2.next();
         if (var3.getName().equals("Bind")) {
            this.getComponents().add(new KeybindSettingComponent(this.getModule(), this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
            var1 += 14.0F;
         }

         if (var3 instanceof BooleanSetting && !var3.getName().equals("Enabled")) {
            this.getComponents().add(new BooleanSettingComponent((BooleanSetting)var3, this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
            var1 += 14.0F;
         }

         if (var3 instanceof NumberSetting) {
            if (((NumberSetting)var3).isBound()) {
               this.getComponents().add(new SliderSettingComponent((NumberSetting)var3, this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
               var1 += 14.0F;
            } else {
               this.getComponents().add(new NumberSettingComponent((NumberSetting)var3, this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
               var1 += 14.0F;
            }
         }

         if (var3 instanceof StringSetting) {
            this.getComponents().add(new StringSettingComponent((StringSetting)var3, this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
            var1 += 14.0F;
         }

         if (var3 instanceof EnumSetting) {
            Class var4 = ((EnumSetting)var3).clazz;
            Object[] var5 = var4.getEnumConstants();
            String[] var6 = (String[])Arrays.stream(var5).map(ModuleSettingsComponent::lambda$initialize$0).toArray(ModuleSettingsComponent::lambda$initialize$1);
            this.getComponents().add(new EnumSettingComponent((EnumSetting)var3, var6, this.getFinishedX(), this.getFinishedY(), 0.0F, var1, this.getWidth(), 14.0F));
            var1 += 14.0F;
         }
      }

      this.getComponents().forEach(Component::initialize);
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
      if (this.isExtended()) {
         this.getComponents().forEach(ModuleSettingsComponent::lambda$onMouseReleased$6);
      }

   }
}
