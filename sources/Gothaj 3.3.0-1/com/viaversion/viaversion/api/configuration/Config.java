package com.viaversion.viaversion.api.configuration;

import java.util.Map;

public interface Config {
   void reload();

   void save();

   void set(String var1, Object var2);

   Map<String, Object> getValues();
}
