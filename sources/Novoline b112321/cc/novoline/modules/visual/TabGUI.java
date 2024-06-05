package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.KeyPressEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.TabGUI$1;
import cc.novoline.modules.visual.tabgui.TabModule;
import cc.novoline.modules.visual.tabgui.TabSetting;
import cc.novoline.modules.visual.tabgui.TabType;
import cc.novoline.modules.visual.tabgui.TabValue;
import cc.novoline.utils.ScaleUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.lwjgl.opengl.GL11;

public class TabGUI extends AbstractModule {
   public List types = new ObjectArrayList();
   private int typeN = 0;
   private int moduleN = 0;
   private int settingN = 0;
   private int valueN = 0;

   public TabGUI(ModuleManager var1) {
      super(var1, "TabGui", "Tab Gui", 0, EnumModuleType.VISUALS);
   }

   public void onEnable() {
      HUD.h();
      EnumModuleType[] var2 = EnumModuleType.values();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         EnumModuleType var5 = var2[var4];
         this.types.add(new TabType(this, var5));
         ++var4;
      }

   }

   public int getColor() {
      return ((HUD)this.getModule(HUD.class)).getHUDColor();
   }

   public void onDisable() {
      this.types.clear();
   }

   @EventTarget
   public void onRender2D(Render2DEvent var1) {
      GL11.glPushMatrix();
      ScaleUtils.scale(this.mc);
      this.types.forEach(TabType::e);
      GL11.glPopMatrix();
   }

   @EventTarget
   public void onKeyPress(KeyPressEvent var1) {
      int var3 = var1.getKey();
      TabType var4 = this.getSelectedType();
      boolean var5 = ((TabType)Objects.requireNonNull(var4)).isOpened();
      HUD.h();
      TabModule var6 = var4.getSelectedModule();
      boolean var7 = var6.isOpened();
      TabSetting var8 = var6.getSelectedSetting();
      if(var8 != null) {
         Setting var9 = var8.getSetting();
      }

      Object var11 = null;
      switch(var3) {
      case 28:
         if(!var5) {
            break;
         }

         if(var7) {
            if(((Setting)Objects.requireNonNull(var11)).getSettingType() == SettingType.CHECKBOX) {
               ((Setting)var11).getCheckBoxProperty().invert();
            }

            TabValue var10 = var8.getSelectedValue();
            switch(TabGUI$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[((Setting)var11).getSettingType().ordinal()]) {
            case 1:
               ((Setting)var11).setComboBoxValue(var10.getValue());
            case 2:
               if(((Setting)var11).getSelectBox().contains(var10.getValue())) {
                  ((Setting)var11).getSelectBox().remove(var10.getValue());
               }

               ((Setting)var11).getSelectBox().add(var10.getValue());
            }
         }

         var6.getMod().toggle();
      case 200:
         if(!var5) {
            if(this.typeN == 0) {
               this.typeN = this.types.size() - 1;
            }

            --this.typeN;
         }

         if(!var7) {
            if(this.moduleN == 0) {
               this.moduleN = var4.getModules().size() - 1;
            }

            --this.moduleN;
         }

         if(!((TabSetting)Objects.requireNonNull(var8)).isOpened()) {
            if(this.settingN == 0) {
               this.settingN = var6.getSettings().size() - 1;
            }

            --this.settingN;
         }

         if(((Setting)Objects.requireNonNull(var11)).getSettingType() == SettingType.SLIDER) {
            ((Setting)var11).setSliderValue(Double.valueOf(((Setting)var11).getDouble() + ((Setting)var11).getIncrement()));
         }

         if(this.valueN == 0) {
            this.valueN = var8.getValues().size() - 1;
         }

         --this.valueN;
      case 208:
         if(!var5) {
            if(this.typeN == this.types.size() - 1) {
               this.typeN = 0;
            }

            ++this.typeN;
         }

         if(!var7) {
            if(this.moduleN == var4.getModules().size() - 1) {
               this.moduleN = 0;
            }

            ++this.moduleN;
         }

         if(!((TabSetting)Objects.requireNonNull(var8)).isOpened()) {
            if(this.settingN == var6.getSettings().size() - 1) {
               this.settingN = 0;
            }

            ++this.settingN;
         }

         if(((Setting)var11).getSettingType() == SettingType.SLIDER) {
            ((Setting)var11).setSliderValue(Double.valueOf(((Setting)var11).getDouble() - ((Setting)var11).getIncrement()));
         }

         if(this.valueN == var8.getValues().size() - 1) {
            this.valueN = 0;
         }

         ++this.valueN;
      case 205:
         if(!var5) {
            this.moduleN = 0;
            var4.setOpened(true);
         }

         if(!var7 && !var6.areSettingsEmpty()) {
            this.settingN = 0;
            var6.setOpened(true);
         }

         if(((TabSetting)Objects.requireNonNull(var8)).isOpened() || ((Setting)var11).getSettingType() != SettingType.SELECTBOX && ((Setting)var11).getSettingType() != SettingType.COMBOBOX && ((Setting)var11).getSettingType() != SettingType.SLIDER) {
            break;
         }

         var8.setOpened(true);
      case 203:
         if(var4.isOpened()) {
            if(var6.isOpened()) {
               if(((TabSetting)Objects.requireNonNull(var8)).isOpened()) {
                  this.valueN = 0;
                  var8.setOpened(false);
               }

               this.settingN = 0;
               var6.setOpened(false);
            }

            this.moduleN = 0;
            var4.setOpened(false);
         }
      }

   }

   private TabType getSelectedType() {
      return (TabType)this.types.stream().filter(TabType::isSelected).findFirst().orElse((Object)null);
   }

   public List getTypes() {
      return this.types;
   }

   public int getTypeN() {
      return this.typeN;
   }

   public int getModuleN() {
      return this.moduleN;
   }

   public int getSettingN() {
      return this.settingN;
   }

   public int getValueN() {
      return this.valueN;
   }
}
