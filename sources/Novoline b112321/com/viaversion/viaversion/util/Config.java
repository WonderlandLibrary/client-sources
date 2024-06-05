package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.util.CommentStore;
import com.viaversion.viaversion.util.YamlConstructor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.representer.Representer;

public abstract class Config implements ConfigurationProvider {
   private static final ThreadLocal YAML = ThreadLocal.withInitial(Config::lambda$static$0);
   private final CommentStore commentStore = new CommentStore('.', 2);
   private final File configFile;
   private Map config;
   private static String b;

   public Config(File var1) {
      this.configFile = var1;
   }

   public abstract URL getDefaultConfigURL();

   public synchronized Map loadConfig(File param1) {
      // $FF: Couldn't be decompiled
   }

   protected abstract void handleConfig(Map var1);

   public synchronized void saveConfig(File var1, Map var2) {
      try {
         this.commentStore.writeComments(((Yaml)YAML.get()).dump(var2), var1);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public abstract List getUnsupportedOptions();

   public void set(String var1, Object var2) {
      this.config.put(var1, var2);
   }

   public void saveConfig() {
      this.configFile.getParentFile().mkdirs();
      this.saveConfig(this.configFile, this.config);
   }

   public void reloadConfig() {
      this.configFile.getParentFile().mkdirs();
      this.config = new ConcurrentSkipListMap(this.loadConfig(this.configFile));
   }

   public Map getValues() {
      return this.config;
   }

   @Nullable
   public Object a(String var1, Class var2, Object var3) {
      c();
      Object var5 = this.config.get(var1);
      return var5 != null?var5:var3;
   }

   public boolean getBoolean(String var1, boolean var2) {
      c();
      Object var4 = this.config.get(var1);
      return var4 != null?((Boolean)var4).booleanValue():var2;
   }

   @Nullable
   public String getString(String var1, @Nullable String var2) {
      c();
      Object var4 = this.config.get(var1);
      return var4 != null?(String)var4:var2;
   }

   public int getInt(String var1, int var2) {
      c();
      Object var4 = this.config.get(var1);
      return var4 != null?(var4 instanceof Number?((Number)var4).intValue():var2):var2;
   }

   public double getDouble(String var1, double var2) {
      c();
      Object var5 = this.config.get(var1);
      return var5 != null?(var5 instanceof Number?((Number)var5).doubleValue():var2):var2;
   }

   public List getIntegerList(String var1) {
      c();
      Object var3 = this.config.get(var1);
      return (List)(var3 != null?(List)var3:new ArrayList());
   }

   private static Yaml lambda$static$0() {
      DumperOptions var0 = new DumperOptions();
      var0.setDefaultFlowStyle(FlowStyle.BLOCK);
      var0.setPrettyFlow(false);
      var0.setIndent(2);
      return new Yaml(new YamlConstructor(), new Representer(), var0);
   }

   static {
      b((String)null);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String c() {
      return b;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
