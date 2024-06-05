package cc.novoline.events.events;

import cc.novoline.events.events.Event;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;

public class SettingEvent implements Event {
   private BooleanProperty booleanProperty;
   private StringProperty stringProperty;
   private AbstractNumberProperty numberProperty;
   private ListProperty listProperty;
   private final AbstractModule module;
   private final String settingName;
   private String displayName;
   private final SettingType settingType;

   public SettingEvent(AbstractModule var1, String var2, BooleanProperty var3) {
      this.module = var1;
      this.settingName = var2;
      this.settingType = SettingType.CHECKBOX;
      this.booleanProperty = var3;
   }

   public SettingEvent(AbstractModule var1, String var2, String var3, BooleanProperty var4) {
      this.module = var1;
      this.settingName = var2;
      this.displayName = var3;
      this.settingType = SettingType.CHECKBOX;
      this.booleanProperty = var4;
   }

   public SettingEvent(AbstractModule var1, String var2, StringProperty var3) {
      this.module = var1;
      this.settingName = var2;
      this.settingType = SettingType.COMBOBOX;
      this.stringProperty = var3;
   }

   public SettingEvent(AbstractModule var1, String var2, AbstractNumberProperty var3) {
      this.module = var1;
      this.settingName = var2;
      this.settingType = SettingType.SLIDER;
      this.numberProperty = var3;
   }

   public SettingEvent(AbstractModule var1, String var2, ListProperty var3) {
      this.module = var1;
      this.settingName = var2;
      this.settingType = SettingType.SELECTBOX;
      this.listProperty = var3;
   }

   public BooleanProperty getBooleanProperty() {
      return this.booleanProperty;
   }

   public StringProperty getStringProperty() {
      return this.stringProperty;
   }

   public AbstractNumberProperty getNumberProperty() {
      return this.numberProperty;
   }

   public ListProperty getListProperty() {
      return this.listProperty;
   }

   public String getSettingName() {
      return this.settingName;
   }

   public SettingType getSettingType() {
      return this.settingType;
   }

   public AbstractModule getModule() {
      return this.module;
   }

   public String getDisplayName() {
      return this.displayName;
   }
}
