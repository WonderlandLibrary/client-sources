package net;

import cc.novoline.gui.screen.setting.Setting;
import java.util.List;
import net.aSv;

public class ay6 {
   public Setting a;

   public ay6(Setting var1) {
      this.a = var1;
   }

   @aSv
   public boolean getBoolean() {
      return this.a.getCheckBoxValue().booleanValue();
   }

   @aSv
   public String getString() {
      return this.a.getComboBoxValue();
   }

   @aSv
   public List getList() {
      return this.a.getSelectBox();
   }

   @aSv
   public String getText() {
      return this.a.getTextBoxValue();
   }

   @aSv
   public double getDouble() {
      return this.a.getDouble();
   }

   @aSv
   public float getFloat() {
      return this.a.getFloat();
   }

   @aSv
   public int getInteger() {
      return this.a.getInt();
   }

   @aSv
   public long getLong() {
      return this.a.getLong();
   }
}
