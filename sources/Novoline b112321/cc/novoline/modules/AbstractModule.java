package cc.novoline.modules;

import cc.novoline.Initializer;
import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule$1;
import cc.novoline.modules.Config;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.KeyBindProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.exceptions.LoadConfigException;
import cc.novoline.modules.serializers.ConfigSerializer;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import cc.novoline.utils.notifications.NotificationType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.reflect.TypeToken;
import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import net.Ea;
import net.J8;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.skidunion.J;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@J8
public class AbstractModule {
   Initializer o;
   protected final Logger logger;
   protected final Novoline novoline;
   protected final ModuleManager moduleManager;
   protected final Minecraft mc;
   protected Config config;
   protected final EnumModuleType type;
   protected final String name;
   protected String displayName;
   protected final String description;
   protected final int g;
   protected boolean a;
   protected String suffix;
   @Property("enabled")
   protected final BooleanProperty enabled;
   @Property("hidden")
   protected final BooleanProperty hidden;
   @Property("display-name")
   protected final StringProperty displayNameProperty;
   protected final KeyBindProperty bind;
   public float offsetX;
   public float offsetY;
   public Color b;
   public Color c;
   private final Cache moduleCache;
   private static int[] q;

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull String var3, int var4, @NotNull EnumModuleType var5, @Nullable String var6, @Nullable String var7) {
      this.o = Initializer.getInstance();
      this.logger = LogManager.getLogger();
      this.a = false;
      this.enabled = PropertyFactory.booleanFalse();
      this.hidden = PropertyFactory.booleanFalse();
      d();
      this.displayNameProperty = PropertyFactory.createString("");
      this.bind = PropertyFactory.keyBinding(KeyboardKeybind.of(0));
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.b = new Color(255, 0, 0);
      this.c = new Color(0, 255, 255);
      this.moduleCache = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.MINUTES).build();
      this.moduleManager = var1;
      this.novoline = var1.getNovoline();
      this.mc = this.novoline.getMinecraft();
      this.name = var2;
      this.type = var5;
      this.g = var4;
      this.description = var6;
      this.displayName = var3;
      Manager.put(new Setting("MODULE_BIND", "Bind", SettingType.BINDABLE, this, this.bind));
      this.config = Config.fromPath(this.novoline.getDataFolder().resolve(var7 + ".novo"));
      Ea var9 = this.config.getLoader().getDefaultOptions().f();
      var9.a((Predicate)(AbstractModule::lambda$new$0), new ConfigSerializer(var1));
      this.a(var9);

      try {
         this.config.load();
      } catch (LoadConfigException var11) {
         var11.printStackTrace();
      }

   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull String var3, @NotNull EnumModuleType var4, @Nullable String var5, @Nullable String var6) {
      this(var1, var2, var3, 0, var4, var5, var6);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull String var3, int var4, @NotNull EnumModuleType var5, @Nullable String var6) {
      this(var1, var2, var3, var4, var5, var6, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull EnumModuleType var3, @Nullable String var4, @Nullable String var5) {
      this(var1, var2, var2, 0, var3, var4, var5);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull String var3, @NotNull EnumModuleType var4, @Nullable String var5) {
      this(var1, var2, var3, 0, var4, var5, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull String var3, @NotNull int var4, @NotNull EnumModuleType var5) {
      this(var1, var2, var3, var4, var5, (String)null, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull EnumModuleType var3, @Nullable String var4) {
      this(var1, var2, var2, 0, var3, var4, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull String var2, @NotNull EnumModuleType var3) {
      this(var1, var2, var2, 0, var3, (String)null, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull EnumModuleType var2, @NotNull String var3) {
      this(var1, var3, var3, 0, var2, (String)null, (String)null);
   }

   protected AbstractModule(@NotNull ModuleManager var1, @NotNull EnumModuleType var2, @NotNull String var3, @NotNull String var4) {
      this(var1, var3, var4, 0, var2, (String)null, (String)null);
   }

   protected void a(@NotNull Ea var1) {
   }

   @NotNull
   public AbstractModule getModule(Class var1) {
      try {
         return (AbstractModule)this.moduleCache.get(var1, this::lambda$getModule$1);
      } catch (ExecutionException var3) {
         throw new RuntimeException(var3);
      }
   }

   protected void send(@Nullable TextMessage var1, boolean var2) {
      int[] var3 = d();
      if(var1 != null) {
         TextMessage var4 = var1.prefix(NovoCommand.PREFIX);
      }

      IChatComponent var5 = NovoCommand.EMPTY_COMPONENT;
      this.mc.player.addChatComponentMessage(var5);
   }

   protected void send(@Nullable TextMessage var1) {
      this.send(var1, false);
   }

   protected void send(@NotNull String var1, boolean var2) {
      this.send(MessageFactory.text(var1), var2);
   }

   protected void send(@NotNull String var1) {
      this.send(MessageFactory.text(var1));
   }

   protected void sendEmpty() {
      this.mc.player.addChatComponentMessage(MessageFactory.a());
   }

   public float categoryColor() {
      switch(AbstractModule$1.$SwitchMap$cc$novoline$modules$EnumModuleType[this.type.ordinal()]) {
      case 1:
         return 0.9F;
      case 2:
         return 0.55F;
      case 3:
         return 0.45F;
      case 4:
         return 0.1F;
      default:
         return 0.0F;
      }
   }

   protected void autoDisable(@NotNull PacketEvent var1) {
      int[] var2 = d();
      if(var1.getDirection() == PacketDirection.INCOMING) {
         if(var1.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle var3 = (S45PacketTitle)var1.getPacket();
            if(var3.getType() == S45PacketTitle$Type.TITLE) {
               String var4 = var3.getMessage().getUnformattedText();
               if(var4.equals("VICTORY!")) {
                  this.toggle();
                  this.novoline.getNotificationManager().pop(this.name + " was disabled, because game has ended", 1000, NotificationType.WARNING);
               }
            }
         }

         if(var1.getPacket() instanceof S02PacketChat) {
            S02PacketChat var5 = (S02PacketChat)var1.getPacket();
            String var7 = var5.getChatComponent().getUnformattedText();
            if(var7.equalsIgnoreCase("You died! Want to play again? Click here! ")) {
               this.novoline.getNotificationManager().pop(this.name + " was disabled, because game has ended", 1000, NotificationType.WARNING);
               this.toggle();
            }
         }

         if((ServerUtils.serverIs(Servers.SW) || ServerUtils.serverIs(Servers.BW)) && var1.getPacket() instanceof S39PacketPlayerAbilities) {
            S39PacketPlayerAbilities var6 = (S39PacketPlayerAbilities)var1.getPacket();
            if(var6.isAllowFlying()) {
               this.novoline.getNotificationManager().pop(this.name + " was disabled, because game has ended", 1000, NotificationType.WARNING);
               this.toggle();
            }
         }
      }

   }

   protected void checkModule(@NotNull Class var1) {
      d();
      AbstractModule var3 = this.getModule(var1);
      if(var3.isEnabled()) {
         this.novoline.getNotificationManager().pop(var3.getName() + " was disabled to prevent flags/errors", 1000, NotificationType.WARNING);
         var3.toggle();
      }

   }

   protected void checkModule(@NotNull Class... var1) {
      int[] var2 = d();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         Class var6 = var1[var5];
         AbstractModule var7 = this.getModule(var6);
         if(var7.isEnabled()) {
            var7.toggle();
            this.novoline.getNotificationManager().pop(this.getDisplayName(), var7.name + " was disabled to prevent flags/errors", 1000, NotificationType.WARNING);
         }

         ++var5;
      }

   }

   protected boolean isEnabled(@NotNull Class var1) {
      return this.getModule(var1).isEnabled();
   }

   public void sendPacket(@NotNull Packet var1) {
      int[] var2 = d();
      if(this.mc.player != null) {
         this.mc.player.connection.b(var1);
      }

   }

   public void sendPacketNoEvent(@NotNull Packet var1) {
      int[] var2 = d();
      if(this.mc.player != null) {
         this.mc.player.connection.sendPacketNoEvent(var1);
      }

   }

   public void a(Object... var1) {
      if(this.o.a()) {
         String var2 = Arrays.toString(var1);
         this.mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var2)));
      }

   }

   public void c(boolean var1) {
      d();
      this.a = var1;
      if(this.a && this.isEnabled()) {
         this.toggle();
         this.novoline.getNotificationManager().pop(this.getDisplayName() + " is currently detected!", "Can\'t turn that on right now, however, it will be usable again soon!", 3000, NotificationType.WARNING);
      }

   }

   public boolean h() {
      return this.a;
   }

   public boolean toggle() {
      int[] var1 = d();
      if(((Boolean)this.enabled.get()).booleanValue()) {
         this.setEnabled(false);
         return false;
      } else {
         this.setEnabled(true);
         return true;
      }
   }

   public void u() {
      int[] var1 = d();
      if(this.isEnabled()) {
         this.toggle();
      }

   }

   public void setSuffix(String var1) {
      int[] var2 = d();
      if(var1 == null || !var1.isEmpty()) {
         String var3 = ((String)((HUD)this.getModule(HUD.class)).getSuffixType().get()).toLowerCase();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -902286926:
            if(!var3.equals("simple")) {
               break;
            }

            var4 = 0;
         case 3075986:
            if(!var3.equals("dash")) {
               break;
            }

            var4 = 1;
         case 137407656:
            if(var3.equals("bracket")) {
               var4 = 2;
            }
         }

         switch(var4) {
         case 0:
            this.suffix = "§7 " + var1;
         case 1:
            this.suffix = "§7 - " + var1;
         case 2:
            this.suffix = "§7 [" + var1 + "]";
         default:
            this.suffix = "";
         }
      }

      this.suffix = "";
   }

   public final boolean isEnabled() {
      return ((Boolean)this.enabled.get()).booleanValue();
   }

   public void setEnabled(boolean var1) {
      int[] var2 = d();
      if(((Boolean)this.enabled.get()).booleanValue() != var1) {
         this.enabled.set(Boolean.valueOf(var1));
         if(var1) {
            this.q();
         }

         this.disable();
      }

   }

   public final void q() {
      d();
      EventManager.register(this);
      this.onEnable();
      if(this.a) {
         this.toggle();
         this.novoline.getNotificationManager().pop(this.getDisplayName() + " is currently detected!", "Can\'t turn that on right now, however, it will be usable again soon!", 3000, NotificationType.WARNING);
      }

      if(!this.novoline.getModuleManager().getAbstractModules().contains(this) && this != this.novoline.getModuleManager().getModule(ClickGUI.class)) {
         this.novoline.getModuleManager().getAbstractModules().add(this);
      }

      this.offsetY = ((HUD)this.novoline.getModuleManager().getModule(HUD.class)).getHeight(this) - 1.0F;
      this.offsetX = 0.0F;
   }

   public final void disable() {
      EventManager.unregister(this);
      this.onDisable();
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   @NotNull
   public String getDisplayName() {
      d();
      String var2 = (String)this.displayNameProperty.get();
      return var2 != null && !var2.isEmpty()?var2:this.displayName;
   }

   public final boolean isHidden() {
      return ((Boolean)this.hidden.get()).booleanValue();
   }

   public void setHidden(boolean var1) {
      this.hidden.set(Boolean.valueOf(var1));
   }

   @NotNull
   public String getSuffix() {
      int[] var1 = d();
      return this.suffix == null?"":this.suffix;
   }

   @NotNull
   public String getFinalDisplayName() {
      return this.getDisplayName() + this.getSuffix();
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   public KeyBindProperty getKeybind() {
      return this.bind;
   }

   @Nullable
   public String getDescription() {
      return this.description;
   }

   @NotNull
   public EnumModuleType getType() {
      return this.type;
   }

   public Config getConfig() {
      return this.config;
   }

   @NotNull
   public Logger getLogger() {
      return this.logger;
   }

   @NotNull
   public String getVanillaDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(@NotNull String var1) {
      this.displayName = var1;
   }

   public void setKeyBind(ModuleKeybind var1) {
      this.bind.set(var1);
   }

   public int p() {
      return this.g;
   }

   public void setDisplayNameProperty(String var1) {
      this.displayNameProperty.set(var1);
   }

   @NotNull
   public Novoline getNovoline() {
      return this.novoline;
   }

   public boolean i() {
      int[] var1 = d();
      return Integer.parseInt(J.aK) < 3;
   }

   private AbstractModule lambda$getModule$1(Class var1) throws Exception {
      return this.moduleManager.getModule(var1);
   }

   private static boolean lambda$new$0(TypeToken var0) {
      d();
      Class var2 = var0.getRawType();

      boolean var3;
      while(true) {
         if(!(var3 = var2.isAnnotationPresent(J8.class))) {
            var2 = var2.getSuperclass();
         }

         if(var3 || var2 == null || var2.getSuperclass() == null) {
            break;
         }
      }

      return var3;
   }

   public static void b(int[] var0) {
      q = var0;
   }

   public static int[] d() {
      return q;
   }

   private static LoadConfigException a(LoadConfigException var0) {
      return var0;
   }

   static {
      b(new int[2]);
   }
}
