package net;

import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.bukkit.commands.BukkitCommandHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;
import net.NG;
import net.aq9;
import net.bgY;
import net.i0;
import net.iU;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import us.myles.ViaVersion.api.ViaAPI;
import viamcp.platform.VRViaConfig;
import viamcp.utils.FutureTaskId;
import viamcp.utils.JLoggerToLog4j;

public class jo implements ViaPlatform {
   private final Logger e = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
   private final VRViaConfig c;
   private final File a;
   private final iU b;
   private final ViaAPI d;

   public jo() {
      bgY.b();
      Path var2 = Minecraft.getMinecraft().mcDataDir.toPath().resolve("ViaFabric");
      this.c = new VRViaConfig(var2.resolve("viaversion.yml").toFile());
      this.a = var2.toFile();
      this.b = new BukkitCommandHandler();
      this.d = new i0();
   }

   public static MinecraftServer b() {
      return !Minecraft.getMinecraft().isIntegratedServerRunning()?null:MinecraftServer.getServer();
   }

   public Logger getLogger() {
      return this.e;
   }

   public String a() {
      return "ViaFabric";
   }

   public String j() {
      return aq9.b();
   }

   public String f() {
      return "3.3.0";
   }

   public NG b(Runnable var1) {
      return new FutureTaskId(CompletableFuture.runAsync(var1, aq9.f).exceptionally(jo::lambda$runAsync$0));
   }

   public NG a(Runnable var1) {
      return b() != null?this.c(var1):this.d(var1);
   }

   private NG c(Runnable var1) {
      return new FutureTaskId(CompletableFuture.runAsync(var1, jo::lambda$runServerSync$2));
   }

   private NG d(Runnable var1) {
      return new FutureTaskId(aq9.c.submit(var1).addListener(this.a()));
   }

   public NG a(Runnable var1, Long var2) {
      return new FutureTaskId(aq9.c.schedule(this::lambda$runSync$3, var2.longValue() * 50L, TimeUnit.MILLISECONDS).addListener(this.a()));
   }

   public NG b(Runnable var1, Long var2) {
      PacketRemapper[] var3 = bgY.b();
      FutureTaskId var10000 = new FutureTaskId(aq9.c.scheduleAtFixedRate(this::lambda$runRepeatingSync$4, 0L, var2.longValue() * 50L, TimeUnit.MILLISECONDS).addListener(this.a()));
      if(PacketRemapper.b() == null) {
         bgY.b(new PacketRemapper[1]);
      }

      return var10000;
   }

   private GenericFutureListener a() {
      return jo::lambda$errorLogger$5;
   }

   public void a(NG var1) {
      PacketRemapper[] var2 = bgY.b();
      if(var1 instanceof FutureTaskId) {
         ((FutureTaskId)var1).getObject().cancel(false);
      }

   }

   public ViaCommandSender[] getOnlinePlayers() {
      return new ViaCommandSender[0];
   }

   public void sendMessage(UUID var1, String var2) {
   }

   public boolean kickPlayer(UUID var1, String var2) {
      return this.a(var1, var2);
   }

   private boolean a(UUID var1, String var2) {
      return false;
   }

   public boolean h() {
      return true;
   }

   public ViaAPI o() {
      return this.d;
   }

   public ViaVersionConfig getConf() {
      return this.c;
   }

   public ConfigurationProvider getConfigurationProvider() {
      return this.c;
   }

   public File getDataFolder() {
      return this.a;
   }

   public void onReload() {
   }

   public JsonObject b() {
      return new JsonObject();
   }

   public boolean m() {
      return true;
   }

   public iU i() {
      return this.b;
   }

   private String a(String var1) {
      return ComponentSerializer.toString(TextComponent.fromLegacyText(var1));
   }

   private static void lambda$errorLogger$5(Future var0) throws Exception {
      PacketRemapper[] var1 = bgY.b();
      if(!var0.isCancelled() && var0.cause() != null) {
         var0.cause().printStackTrace();
      }

   }

   private void lambda$runRepeatingSync$4(Runnable var1) {
      this.a(var1);
   }

   private NG lambda$runSync$3(Runnable var1) throws Exception {
      return this.a(var1);
   }

   private static void lambda$runServerSync$2(Runnable var0) {
      b().callFromMainThread(jo::lambda$null$1);
   }

   private static Void lambda$null$1(Runnable var0) throws Exception {
      var0.run();
      return null;
   }

   private static Void lambda$runAsync$0(Throwable var0) {
      PacketRemapper[] var1 = bgY.b();
      if(!(var0 instanceof CancellationException)) {
         var0.printStackTrace();
      }

      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
