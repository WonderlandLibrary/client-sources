package net;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.Ua;
import net.Uj;
import net.aJ1;
import net.aiy;
import net.azi;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FJ extends CommandBase {
   public static final Ua c = aJ1.a("Novoline", EnumChatFormatting.LIGHT_PURPLE).b(" » ", EnumChatFormatting.GRAY);
   public static final IChatComponent i = new ChatComponentText("");
   protected final aiy f;
   protected final gZ h;
   protected final String d;
   protected final String b;
   protected final List e;
   private static String g;

   protected FJ(@NotNull gZ var1, @NotNull String var2, @Nullable String var3, @Nullable Iterable var4) {
      this.f = aiy.d(FJ::lambda$new$0);
      this.h = var1;
      this.d = var2;
      this.b = var3;
      this.e = Lists.newArrayList(var4);
   }

   protected FJ(@NotNull gZ var1, @NotNull String var2, @Nullable String var3, @Nullable String var4) {
      this(var1, var2, var3, (Iterable)Collections.singleton(var4));
   }

   protected FJ(@NotNull gZ var1, @NotNull String var2, @Nullable String var3) {
      this(var1, var2, (String)null, (Iterable)Collections.singleton(var3));
   }

   protected FJ(@NotNull gZ var1, @NotNull String var2, @Nullable Iterable var3) {
      this(var1, var2, (String)null, (Iterable)var3);
   }

   protected FJ(@NotNull gZ var1, @NotNull String var2) {
      this(var1, var2, (String)null, (Iterable)((Iterable)null));
   }

   public abstract void b(String[] var1) throws CommandException, IOException;

   @Nullable
   public List a(String[] var1) {
      return null;
   }

   protected int d(String var1) throws NumberInvalidException {
      String var10000 = var1;

      try {
         return Integer.parseInt(var10000);
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException(var1 + " is not a valid number", new Object[0]);
      }
   }

   protected double b(String var1) throws NumberInvalidException {
      String var10000 = var1;

      try {
         return Double.parseDouble(var10000);
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException(var1 + " is not a valid number", new Object[0]);
      }
   }

   protected void a(@Nullable Ua var1, boolean var2) {
      Type.b();
      EntityPlayerSP var4 = (EntityPlayerSP)this.f.get();
      if(var1 != null) {
         Ua var5 = var1.a(c);
      }

      IChatComponent var6 = i;
      var4.addChatComponentMessage(var6);
   }

   protected void a(@Nullable Ua var1) {
      this.a(var1, false);
   }

   protected void a(@Nullable String var1, boolean var2) {
      this.a(aJ1.b(var1), var2);
   }

   protected void c(@Nullable String var1) {
      this.a(aJ1.b(var1));
   }

   protected void a(@Nullable String var1, @NotNull EnumChatFormatting var2) {
      Ua var3 = aJ1.b(var1);
      var3.a((EnumChatFormatting)var2);
      this.a(var3);
   }

   protected void a(@Nullable Object var1) {
      PacketRemapper[] var2 = Type.b();
      this.c(var1 != null?var1.toString():null);
   }

   protected void a() {
      ((EntityPlayerSP)this.f.get()).addChatComponentMessage(aJ1.a());
   }

   protected void a(@NotNull String var1, @NotNull String var2) {
      this.a((Ua)aJ1.a(var1, var2));
   }

   protected void a(@NotNull String var1, @NotNull String var2, @NotNull Uj... var3) {
      this.a((Ua)aJ1.a(var1, var2, var3), true);
   }

   protected void a(@NotNull String var1, int var2, @Nullable azi var3) {
      gZ.g().t().a(var1, var2, var3);
   }

   protected void c(@NotNull String var1, int var2) {
      this.a(var1, var2, (azi)null);
   }

   protected void a(@NotNull String var1, @Nullable azi var2) {
      this.a(var1, 2000, var2);
   }

   protected void e(@NotNull String var1) {
      this.a(var1, 2000, azi.INFO);
   }

   protected void a(@NotNull String var1, @NotNull String var2, int var3, azi var4) {
      this.h.t().a(var1, var2, var3, var4);
   }

   protected void a(@NotNull String var1, int var2) {
      this.a(var1, var2, azi.WARNING);
   }

   protected void h(@NotNull String var1) {
      this.a(var1, 2000);
   }

   protected void b(@NotNull String var1, int var2) {
      this.a(var1, var2, azi.ERROR);
   }

   protected void f(@NotNull String var1) {
      this.b(var1, 2000);
   }

   protected List a(@NotNull Stream var1, @NotNull String var2, boolean var3) {
      PacketRemapper[] var4 = Type.b();
      String var5 = var2.toLowerCase(Locale.ROOT);
      return (List)var1.map(FJ::lambda$completeTab$1).filter(FJ::lambda$completeTab$2).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   protected List a(@NotNull Collection var1, @NotNull String var2, boolean var3) {
      return this.a(var1.stream(), var2, var3);
   }

   protected Logger d() {
      return gZ.v();
   }

   public final void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      FJ var10000 = this;
      String[] var10001 = var2;

      try {
         var10000.b(var10001);
      } catch (CommandException var4) {
         this.d().warn(var4);
         throw var4;
      } catch (Throwable var5) {
         this.d().warn(var5);
         throw new CommandException("Unexpected error occurred while executing command", var5, new Object[0]);
      }
   }

   public final String getCommandName() {
      return this.d;
   }

   public final List getCommandAliases() {
      return this.e;
   }

   @Nullable
   public final String getCommandUsage(ICommandSender var1) {
      return null;
   }

   public final int getRequiredPermissionLevel() {
      return 0;
   }

   public final boolean canCommandSenderUseCommand(ICommandSender var1) {
      return true;
   }

   @Nullable
   public final List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return this.a(var2);
   }

   public final boolean isUsernameIndex(String[] var1, int var2) {
      return false;
   }

   private static boolean lambda$completeTab$3(String var0, String var1) {
      return var1.startsWith(var0);
   }

   private static boolean lambda$completeTab$2(String var0, String var1) {
      return var1.startsWith(var0);
   }

   private static String lambda$completeTab$1(String var0) {
      return var0.toLowerCase(Locale.ROOT);
   }

   private static EntityPlayerSP lambda$new$0() {
      return Minecraft.getMinecraft().thePlayer;
   }

   static {
      g((String)null);
   }

   public static void g(String var0) {
      g = var0;
   }

   public static String c() {
      return g;
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
