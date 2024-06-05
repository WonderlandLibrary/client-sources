package net;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import java.io.Reader;

public class atq {
   public static Config a(Reader var0, ConfigParseOptions var1) {
      return ConfigFactory.parseReader(var0, var1);
   }
}
