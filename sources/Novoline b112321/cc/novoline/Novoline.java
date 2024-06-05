package cc.novoline;

import cc.novoline.commands.NovoCommandHandler;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.dropdown.DropdownGUI;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager;
import cc.novoline.utils.fonts.api.FontManager;
import cc.novoline.utils.fonts.impl.SimpleFontManager;
import cc.novoline.utils.notifications.NotificationManager;
import cc.novoline.utils.tasks.TaskManager;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import net.Pa;
import net.X9;
import net.aHK;
import net.aHW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.skidunion.J;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import store.intent.c;

public final class Novoline {
   private final boolean BETA = false;
   private final boolean HOTFIX = false;
   private c c;
   private static Novoline INSTANCE;
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft mc = Minecraft.getInstance();
   private final Path dataFolder;
   public String version = "112321";
   private final NovoCommandHandler novoCommandHandler = new NovoCommandHandler(this);
   private ModuleManager moduleManager;
   public TaskManager taskManager;
   public PlayerManager playerManager;
   private final AsynchronousDataRetriever dataRetriever = new AsynchronousDataRetriever((String)null);
   public NotificationManager notificationManager;
   public FontManager fontManager = SimpleFontManager.create();
   public Pa r;
   public DiscordGUI discordGUI;
   public DropdownGUI dropDownGUI;
   public AltRepositoryGUI altRepositoryGUI;
   public aHW q;
   public aHK d;
   private J p;
   private ServerData lastConnectedServer;
   private static int l;

   public Novoline() {
      this.dataFolder = Paths.get(this.mc.mcDataDir.getAbsolutePath(), new String[0]).resolve("Novoline");
      if(INSTANCE != null) {
         throw new IllegalStateException("Cannot instantiate " + this.getClass().getCanonicalName() + " twice");
      }
   }

   public void onStart() {
   }

   public void onLoaded() {
   }

   public void onDisable() {
      Novoline var10000 = this;

      try {
         var10000.getModuleManager().getConfigManager().save("default");
      } catch (IOException var2) {
         getLogger().warn("An I/O error occurred while " + var2.getMessage() + "!", var2);
      } catch (X9 var3) {
         getLogger().warn("An I/O error occurred while serializing config!", var3);
      }

   }

   public NovoCommandHandler getNovoCommandHandler() {
      return this.novoCommandHandler;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public PlayerManager getPlayerManager() {
      return this.playerManager;
   }

   public TaskManager getTaskManager() {
      return this.taskManager;
   }

   public AsynchronousDataRetriever getDataRetriever() {
      return this.dataRetriever;
   }

   public DiscordGUI getDiscordGUI() {
      return this.discordGUI;
   }

   public DropdownGUI getDropDownGUI() {
      return this.dropDownGUI;
   }

   public AltRepositoryGUI getAltRepositoryGUI() {
      return this.altRepositoryGUI;
   }

   public Pa u() {
      return this.r;
   }

   public aHK h() {
      return this.d;
   }

   public String getVersion() {
      int var1 = E();
      String var2 = this.version.toCharArray()[0] != 64?this.version:(new SimpleDateFormat("MMddyy")).format(new Date());
      return var2 + "" + "";
   }

   public Path getDataFolder() {
      return this.dataFolder;
   }

   public String getPathString() {
      return this.mc.mcDataDir.getAbsolutePath() + "\\Novoline\\";
   }

   public CompletableFuture generateAlteningAlt() {
      return this.dataRetriever.getAccountDataAsync();
   }

   public ServerData getLastConnectedServer() {
      return this.lastConnectedServer;
   }

   public void setLastConnectedServer(@NotNull ServerData var1) {
      this.lastConnectedServer = var1;
   }

   public void init() {
   }

   public String Xor(Object var1, String var2) {
      StringBuilder var4 = new StringBuilder();
      char[] var5 = var2.toCharArray();
      int var6 = 0;
      C();
      char[] var7 = var1.toString().toCharArray();
      int var8 = var7.length;
      int var9 = 0;
      if(var9 < var8) {
         char var10 = var7[var9];
         var4.append((char)(var10 ^ var5[var6 % var5.length]));
         ++var6;
         ++var9;
      }

      return var4.toString();
   }

   public boolean isAnythingNull() {
      int var1 = C();
      return this.moduleManager == null || this.playerManager == null || this.notificationManager == null;
   }

   public NotificationManager getNotificationManager() {
      return this.notificationManager;
   }

   public FontManager getFontManager() {
      return this.fontManager;
   }

   public Minecraft getMinecraft() {
      return this.mc;
   }

   public static Logger getLogger() {
      return LOGGER;
   }

   public static Novoline getInstance() {
      try {
         if(INSTANCE == null) {
            INSTANCE = new Novoline();
         }

         return INSTANCE;
      } catch (Throwable var1) {
         LOGGER.warn(var1);
         throw var1;
      }
   }

   public void setModuleManager(ModuleManager var1) {
      this.moduleManager = var1;
   }

   public J A() {
      return this.p;
   }

   public void a(J var1) {
      this.p = var1;
   }

   public int viaVersion() {
      return 340;
   }

   public c e() {
      return this.c;
   }

   public void a(c var1) {
      this.c = var1;
   }

   public aHW D() {
      return this.q;
   }

   static {
      b(55);
   }

   public static void b(int var0) {
      l = var0;
   }

   public static int C() {
      return l;
   }

   public static int E() {
      int var0 = C();
      return 102;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
