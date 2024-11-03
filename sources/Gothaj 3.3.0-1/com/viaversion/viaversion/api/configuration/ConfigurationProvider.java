package com.viaversion.viaversion.api.configuration;

import java.util.Collection;

public interface ConfigurationProvider {
   void register(Config var1);

   Collection<Config> configs();

   void reloadConfigs();
}
