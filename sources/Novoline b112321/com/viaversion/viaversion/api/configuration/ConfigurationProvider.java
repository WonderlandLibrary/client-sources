package com.viaversion.viaversion.api.configuration;

import java.util.Map;

public interface ConfigurationProvider {
   void set(String var1, Object var2);

   void saveConfig();

   void reloadConfig();

   Map getValues();
}
