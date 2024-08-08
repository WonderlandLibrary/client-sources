package com.example.editme.settings;

public interface ISettingSafe {
   String getName();

   void setValueFromString(String var1);

   Class getValueClass();

   boolean isVisible();

   String getValueAsString();
}
