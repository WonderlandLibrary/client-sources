package cc.novoline.gui.screen.click;

import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.gui.screen.setting.SettingType;

// $FF: synthetic class
class Tab$1 {
   static final int[] $SwitchMap$cc$novoline$gui$screen$click$Scroll;
   static final int[] $SwitchMap$cc$novoline$gui$screen$setting$SettingType = new int[SettingType.values().length];

   static {
      try {
         $SwitchMap$cc$novoline$gui$screen$setting$SettingType[SettingType.COMBOBOX.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$cc$novoline$gui$screen$setting$SettingType[SettingType.SELECTBOX.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$cc$novoline$gui$screen$setting$SettingType[SettingType.TEXTBOX.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      $SwitchMap$cc$novoline$gui$screen$click$Scroll = new int[Scroll.values().length];

      try {
         $SwitchMap$cc$novoline$gui$screen$click$Scroll[Scroll.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$cc$novoline$gui$screen$click$Scroll[Scroll.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
