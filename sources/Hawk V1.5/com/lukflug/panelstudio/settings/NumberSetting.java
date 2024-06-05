package com.lukflug.panelstudio.settings;

public interface NumberSetting {
   double getNumber();

   void setNumber(double var1);

   double getMinimumValue();

   int getPrecision();

   double getMaximumValue();
}
