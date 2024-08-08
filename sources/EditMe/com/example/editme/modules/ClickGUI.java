package com.example.editme.modules;

import com.example.editme.gui.EditmeGUI;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;

@Module.Info(
   name = "clickGUI",
   description = "Opens the Click GUI",
   category = Module.Category.HIDDEN
)
public class ClickGUI extends Module {
   private Setting MISCx = this.register(SettingsManager.integerBuilder("MISCx").withValue((int)362).withVisibility(ClickGUI::lambda$new$6).build());
   private Setting MISCy = this.register(SettingsManager.integerBuilder("MISCy").withValue((int)2).withVisibility(ClickGUI::lambda$new$7).build());
   private Setting PLAYERx = this.register(SettingsManager.integerBuilder("PLAYERx").withValue((int)482).withVisibility(ClickGUI::lambda$new$8).build());
   private Setting MOVEMENTe = this.register(SettingsManager.booleanBuilder("MOVEMENTe").withValue(false).withVisibility(ClickGUI::lambda$new$21).build());
   private Setting EXPLOITSe = this.register(SettingsManager.booleanBuilder("EXPLOITSe").withValue(false).withVisibility(ClickGUI::lambda$new$17).build());
   private Setting OLDFAGe = this.register(SettingsManager.booleanBuilder("OLDFAGe").withValue(false).withVisibility(ClickGUI::lambda$new$23).build());
   private Setting COMBATx = this.register(SettingsManager.integerBuilder("COMBATx").withValue((int)2).withVisibility(ClickGUI::lambda$new$0).build());
   private EditmeGUI editmeGUI;
   private Setting EXPLOITSx = this.register(SettingsManager.integerBuilder("EXPLOITSx").withValue((int)122).withVisibility(ClickGUI::lambda$new$2).build());
   private Setting CLIENTy = this.register(SettingsManager.integerBuilder("CLIENTy").withValue((int)2).withVisibility(ClickGUI::lambda$new$13).build());
   private Setting COMBATy = this.register(SettingsManager.integerBuilder("COMBATy").withValue((int)2).withVisibility(ClickGUI::lambda$new$1).build());
   private Setting MOVEMENTx = this.register(SettingsManager.integerBuilder("MOVEMENTx").withValue((int)602).withVisibility(ClickGUI::lambda$new$10).build());
   private Setting CLIENTe = this.register(SettingsManager.booleanBuilder("CLIENTe").withValue(false).withVisibility(ClickGUI::lambda$new$22).build());
   private Setting OLDFAGy = this.register(SettingsManager.integerBuilder("OLDFAGy").withValue((int)102).withVisibility(ClickGUI::lambda$new$15).build());
   private Setting OLDFAGx = this.register(SettingsManager.integerBuilder("OLDFAGx").withValue((int)722).withVisibility(ClickGUI::lambda$new$14).build());
   private Setting RENDERx = this.register(SettingsManager.integerBuilder("RENDERx").withValue((int)242).withVisibility(ClickGUI::lambda$new$4).build());
   private Setting PLAYERe = this.register(SettingsManager.booleanBuilder("PLAYERe").withValue(false).withVisibility(ClickGUI::lambda$new$20).build());
   private Setting PLAYERy = this.register(SettingsManager.integerBuilder("PLAYERy").withValue((int)2).withVisibility(ClickGUI::lambda$new$9).build());
   private Setting COMBATe = this.register(SettingsManager.booleanBuilder("COMBATe").withValue(false).withVisibility(ClickGUI::lambda$new$16).build());
   private Setting EXPLOIYSy = this.register(SettingsManager.integerBuilder("EXPLOITSy").withValue((int)2).withVisibility(ClickGUI::lambda$new$3).build());
   private Setting RENDERy = this.register(SettingsManager.integerBuilder("RENDERy").withValue((int)2).withVisibility(ClickGUI::lambda$new$5).build());
   private Setting MISCe = this.register(SettingsManager.booleanBuilder("MISCe").withValue(false).withVisibility(ClickGUI::lambda$new$19).build());
   private Setting CLIENTx = this.register(SettingsManager.integerBuilder("CLIENTx").withValue((int)722).withVisibility(ClickGUI::lambda$new$12).build());
   private Setting MOVEMENTy = this.register(SettingsManager.integerBuilder("MOVEMENTy").withValue((int)2).withVisibility(ClickGUI::lambda$new$11).build());
   private Setting RENDERe = this.register(SettingsManager.booleanBuilder("RENDERe").withValue(false).withVisibility(ClickGUI::lambda$new$18).build());

   private static boolean lambda$new$19(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$5(Integer var0) {
      return false;
   }

   private static boolean lambda$new$9(Integer var0) {
      return false;
   }

   private static boolean lambda$new$15(Integer var0) {
      return false;
   }

   private static boolean lambda$new$22(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$4(Integer var0) {
      return false;
   }

   private static boolean lambda$new$14(Integer var0) {
      return false;
   }

   private static boolean lambda$new$23(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$13(Integer var0) {
      return false;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   public void onEnable() {
      if (this.editmeGUI == null) {
         this.editmeGUI = new EditmeGUI();
         this.editmeGUI.initialize();
      }

      mc.func_147108_a(this.editmeGUI);
      this.toggle();
   }

   private static boolean lambda$new$12(Integer var0) {
      return false;
   }

   private static boolean lambda$new$11(Integer var0) {
      return false;
   }

   private static boolean lambda$new$17(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$16(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$20(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private static boolean lambda$new$21(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$3(Integer var0) {
      return false;
   }

   private static boolean lambda$new$8(Integer var0) {
      return false;
   }

   private static boolean lambda$new$6(Integer var0) {
      return false;
   }

   private static boolean lambda$new$2(Integer var0) {
      return false;
   }

   private static boolean lambda$new$10(Integer var0) {
      return false;
   }

   private static boolean lambda$new$18(Boolean var0) {
      return false;
   }

   private static boolean lambda$new$7(Integer var0) {
      return false;
   }
}
