package cc.novoline.commands;

import cc.novoline.Novoline;
import cc.novoline.utils.java.Lazy;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import cc.novoline.utils.notifications.NotificationType;
import com.google.common.collect.Lists;
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
import net.Uj;
import net.acE;
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
import viaversion.viaversion.api.type.Type;

public abstract class NovoCommand extends CommandBase {
   public static final TextMessage PREFIX = MessageFactory.text("Novoline", EnumChatFormatting.LIGHT_PURPLE).append(" » ", EnumChatFormatting.GRAY);
   public static final IChatComponent EMPTY_COMPONENT = new ChatComponentText("");
   protected final Lazy player;
   protected final Novoline novoline;
   protected final String name;
   protected final String description;
   protected final List aliases;
   private static String g;

   protected NovoCommand(@NotNull Novoline var1, @NotNull String var2, @Nullable String var3, @Nullable Iterable var4) {
      this.player = Lazy.create(NovoCommand::lambda$new$0);
      this.novoline = var1;
      this.name = var2;
      this.description = var3;
      this.aliases = Lists.newArrayList(var4);
   }

   protected NovoCommand(@NotNull Novoline var1, @NotNull String var2, @Nullable String var3, @Nullable String var4) {
      this(var1, var2, var3, (Iterable)Collections.singleton(var4));
   }

   protected NovoCommand(@NotNull Novoline var1, @NotNull String var2, @Nullable String var3) {
      this(var1, var2, (String)null, (Iterable)Collections.singleton(var3));
   }

   protected NovoCommand(@NotNull Novoline var1, @NotNull String var2, @Nullable Iterable var3) {
      this(var1, var2, (String)null, (Iterable)var3);
   }

   protected NovoCommand(@NotNull Novoline var1, @NotNull String var2) {
      this(var1, var2, (String)null, (Iterable)((Iterable)null));
   }

   public abstract void process(String[] var1) throws CommandException, IOException;

   @Nullable
   public List completeTabOptions(String[] var1) {
      return null;
   }

   protected int getInt(String var1) throws NumberInvalidException {
      String var10000 = var1;

      try {
         return Integer.parseInt(var10000);
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException(var1 + " is not a valid number", new Object[0]);
      }
   }

   protected double getDouble(String var1) throws NumberInvalidException {
      String var10000 = var1;

      try {
         return Double.parseDouble(var10000);
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException(var1 + " is not a valid number", new Object[0]);
      }
   }

   protected void send(@Nullable TextMessage var1, boolean var2) {
      Type.b();
      EntityPlayerSP var4 = (EntityPlayerSP)this.player.get();
      if(var1 != null) {
         TextMessage var5 = var1.prefix(PREFIX);
      }

      IChatComponent var6 = EMPTY_COMPONENT;
      var4.addChatComponentMessage(var6);
   }

   protected void send(@Nullable TextMessage var1) {
      this.send(var1, false);
   }

   protected void send(@Nullable String var1, boolean var2) {
      this.send(MessageFactory.text(var1), var2);
   }

   protected void send(@Nullable String var1) {
      this.send(MessageFactory.text(var1));
   }

   protected void send(@Nullable String var1, @NotNull EnumChatFormatting var2) {
      TextMessage var3 = MessageFactory.text(var1);
      var3.setColor(var2);
      this.send(var3);
   }

   protected void send(@Nullable Object var1) {
      acE[] var2 = Type.b();
      this.send(var1 != null?var1.toString():null);
   }

   protected void sendEmpty() {
      ((EntityPlayerSP)this.player.get()).addChatComponentMessage(MessageFactory.a());
   }

   protected void sendUsage(@NotNull String var1, @NotNull String var2) {
      this.send((TextMessage)MessageFactory.a(var1, var2));
   }

   protected void a(@NotNull String var1, @NotNull String var2, @NotNull Uj... var3) {
      this.send((TextMessage)MessageFactory.a(var1, var2, var3), true);
   }

   protected void notify(@NotNull String var1, int var2, @Nullable NotificationType var3) {
      Novoline.getInstance().getNotificationManager().pop(var1, var2, var3);
   }

   protected void notify(@NotNull String var1, int var2) {
      this.notify(var1, var2, (NotificationType)null);
   }

   protected void notify(@NotNull String var1, @Nullable NotificationType var2) {
      this.notify(var1, 2000, var2);
   }

   protected void notify(@NotNull String var1) {
      this.notify(var1, 2000, NotificationType.INFO);
   }

   protected void notifyClient(@NotNull String var1, @NotNull String var2, int var3, NotificationType var4) {
      this.novoline.getNotificationManager().pop(var1, var2, var3, var4);
   }

   protected void notifyWarning(@NotNull String var1, int var2) {
      this.notify(var1, var2, NotificationType.WARNING);
   }

   protected void notifyWarning(@NotNull String var1) {
      this.notifyWarning(var1, 2000);
   }

   protected void notifyError(@NotNull String var1, int var2) {
      this.notify(var1, var2, NotificationType.ERROR);
   }

   protected void notifyError(@NotNull String var1) {
      this.notifyError(var1, 2000);
   }

   protected List completeTab(@NotNull Stream var1, @NotNull String var2, boolean var3) {
      acE[] var4 = Type.b();
      String var5 = var2.toLowerCase(Locale.ROOT);
      return (List)var1.map(NovoCommand::lambda$completeTab$1).filter(NovoCommand::lambda$completeTab$2).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   protected List completeTab(@NotNull Collection var1, @NotNull String var2, boolean var3) {
      return this.completeTab(var1.stream(), var2, var3);
   }

   protected Logger getLogger() {
      return Novoline.getLogger();
   }

   public final void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      NovoCommand var10000 = this;
      String[] var10001 = var2;

      try {
         var10000.process(var10001);
      } catch (CommandException var4) {
         this.getLogger().warn(var4);
         throw var4;
      } catch (Throwable var5) {
         this.getLogger().warn(var5);
         throw new CommandException("Unexpected error occurred while executing command", var5, new Object[0]);
      }
   }

   public final String getCommandName() {
      return this.name;
   }

   public final List getCommandAliases() {
      return this.aliases;
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
      return this.completeTabOptions(var2);
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
      return Minecraft.getInstance().player;
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
