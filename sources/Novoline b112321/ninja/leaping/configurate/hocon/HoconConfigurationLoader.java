package ninja.leaping.configurate.hocon;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigOriginFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import net.a6t;
import net.aSh;
import net.acE;
import net.af_;
import net.awr;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader$1;
import ninja.leaping.configurate.loader.CommentHandler;
import ninja.leaping.configurate.loader.CommentHandlers;
import org.jetbrains.annotations.NotNull;

public class HoconConfigurationLoader extends awr {
   public static final Pattern CRLF_MATCH = Pattern.compile("\r?");
   private static final ConfigRenderOptions DEFAULT_RENDER_OPTIONS = ConfigRenderOptions.defaults().setOriginComments(false).setJson(false);
   private static final ConfigOrigin CONFIGURATE_ORIGIN = ConfigOriginFactory.newSimple("configurate-hocon");
   private final ConfigRenderOptions render;
   private final ConfigParseOptions parse;
   private static final Constructor CONFIG_OBJECT_CONSTRUCTOR;
   private static final Constructor CONFIG_LIST_CONSTRUCTOR;

   public static ConfigRenderOptions defaultRenderOptions() {
      return DEFAULT_RENDER_OPTIONS;
   }

   public static ConfigParseOptions defaultParseOptions() {
      return ConfigParseOptions.defaults();
   }

   @NotNull
   public static a6t b() {
      return new a6t();
   }

   private HoconConfigurationLoader(a6t var1) {
      super(var1, new CommentHandler[]{CommentHandlers.HASH, CommentHandlers.DOUBLE_SLASH});
      this.render = var1.f();
      this.parse = var1.d();
   }

   public void loadInternal(CommentedConfigurationNode var1, BufferedReader var2) throws IOException {
      Config var4 = ConfigFactory.parseReader(var2, this.parse);
      int var10000 = a6t.e();
      var4 = var4.resolve();
      int var3 = var10000;
      Iterator var5 = var4.root().entrySet().iterator();
      if(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         readConfigValue((ConfigValue)var6.getValue(), var1.getNode(new Object[]{var6.getKey()}));
      }

      if(acE.b() == null) {
         ++var3;
         a6t.b(var3);
      }

   }

   private static void readConfigValue(ConfigValue var0, CommentedConfigurationNode var1) {
      int var2 = a6t.e();
      if(!var0.origin().comments().isEmpty()) {
         var1.setComment(CRLF_MATCH.matcher(Joiner.on('\n').join(var0.origin().comments())).replaceAll(""));
      }

      switch(HoconConfigurationLoader$1.$SwitchMap$com$typesafe$config$ConfigValueType[var0.valueType().ordinal()]) {
      case 1:
         if(((ConfigObject)var0).isEmpty()) {
            var1.setValue(ImmutableMap.of());
         }

         Iterator var3 = ((ConfigObject)var0).entrySet().iterator();
         if(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            readConfigValue((ConfigValue)var4.getValue(), var1.getNode(new Object[]{var4.getKey()}));
         }
      case 2:
         if(((ConfigList)var0).isEmpty()) {
            var1.setValue(ImmutableList.of());
         }

         ConfigList var5 = (ConfigList)var0;
         int var6 = 0;
         if(var6 < var5.size()) {
            readConfigValue((ConfigValue)var5.get(var6), var1.getNode(new Object[]{Integer.valueOf(var6)}));
            ++var6;
         }
      case 3:
         return;
      default:
         var1.setValue(var0.unwrapped());
      }
   }

   protected void saveInternal(ConfigurationNode var1, Writer var2) throws IOException {
      int var3 = a6t.e();
      if(!var1.hasMapChildren()) {
         if(var1.getValue() == null) {
            var2.write(c);
         } else {
            throw new IOException("HOCON cannot write nodes not in map format!");
         }
      } else {
         ConfigValue var4 = fromValue(var1);
         String var5 = var4.render(this.render);
         var2.write(var5);
      }
   }

   private static ConfigValue fromValue(ConfigurationNode var0) {
      int var1 = a6t.c();
      if(var0.hasMapChildren()) {
         ConcurrentMap var3 = var0.getOptions().getMapFactory().create();
         Iterator var4 = var0.getChildrenMap().entrySet().iterator();
         if(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            var3.put(String.valueOf(var5.getKey()), fromValue((ConfigurationNode)var5.getValue()));
         }

         ConfigValue var2 = newConfigObject(var3);
      }

      if(var0.hasListChildren()) {
         ArrayList var8 = new ArrayList();
         Iterator var10 = var0.getChildrenList().iterator();
         if(var10.hasNext()) {
            ConfigurationNode var11 = (ConfigurationNode)var10.next();
            var8.add(fromValue(var11));
         }

         ConfigValue var6 = newConfigList(var8);
      }

      ConfigValue var7 = ConfigValueFactory.fromAnyRef(var0.getValue(), "configurate-hocon");
      if(var0 instanceof CommentedConfigurationNode) {
         CommentedConfigurationNode var9 = (CommentedConfigurationNode)var0;
         var7 = (ConfigValue)var9.getComment().map(HoconConfigurationLoader::lambda$fromValue$0).orElse(var7);
      }

      return var7;
   }

   static ConfigValue newConfigObject(Map var0) {
      try {
         return (ConfigValue)CONFIG_OBJECT_CONSTRUCTOR.newInstance(new Object[]{CONFIGURATE_ORIGIN, var0});
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var2) {
         throw new RuntimeException(var2);
      }
   }

   static ConfigValue newConfigList(List var0) {
      try {
         return (ConfigValue)CONFIG_LIST_CONSTRUCTOR.newInstance(new Object[]{CONFIGURATE_ORIGIN, var0});
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var2) {
         throw new RuntimeException(var2);
      }
   }

   @NotNull
   public CommentedConfigurationNode createEmptyNode(@NotNull ConfigurationOptions var1) {
      a6t.e();
      var1 = var1.setAcceptedTypes(aSh.a(Map.class, List.class, Double.class, Long.class, Integer.class, Boolean.class, new Class[]{String.class, Number.class}));
      return SimpleCommentedConfigurationNode.root(var1);
   }

   private static ConfigValue lambda$fromValue$0(ConfigValue var0, String var1) {
      return var0.withOrigin(var0.origin().withComments(d.splitToList(var1)));
   }

   HoconConfigurationLoader(a6t var1, HoconConfigurationLoader$1 var2) {
      this(var1);
   }

   static {
      String var10000 = "com.typesafe.config.impl.SimpleConfigObject";

      Class var7;
      Class var8;
      try {
         var7 = af_.a(var10000).asSubclass(ConfigValue.class);
         var8 = af_.a("com.typesafe.config.impl.SimpleConfigList").asSubclass(ConfigValue.class);
      } catch (ClassNotFoundException var11) {
         throw new ExceptionInInitializerError(var11);
      }

      try {
         CONFIG_OBJECT_CONSTRUCTOR = var7.getDeclaredConstructor(new Class[]{ConfigOrigin.class, Map.class});
         CONFIG_OBJECT_CONSTRUCTOR.setAccessible(true);
         CONFIG_LIST_CONSTRUCTOR = var8.getDeclaredConstructor(new Class[]{ConfigOrigin.class, List.class});
         CONFIG_LIST_CONSTRUCTOR.setAccessible(true);
      } catch (NoSuchMethodException var10) {
         throw new ExceptionInInitializerError(var10);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
