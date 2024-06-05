package cc.novoline.utils.messages;

import cc.novoline.utils.messages.EmptyMessage;
import cc.novoline.utils.messages.IRCMessage;
import cc.novoline.utils.messages.TextMessage;
import java.util.List;
import net.U_;
import net.Uj;
import net.Uk;
import net.Ux;
import net.VA;
import net.VJ;
import net.Vg;
import net.minecraft.util.EnumChatFormatting;
import net.skidunion.F;
import net.skidunion.b;
import net.skidunion.m;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MessageFactory {
   @NotNull
   public static TextMessage text(@Nullable String var0) {
      return TextMessage.of(var0);
   }

   @NotNull
   public static TextMessage text(@Nullable String var0, @Nullable EnumChatFormatting var1) {
      return TextMessage.of(var0, var1);
   }

   @NotNull
   public static U_ a(@NotNull String var0, @NotNull String var1, @NotNull Uj... var2) {
      return U_.a(var0, var1, var2);
   }

   @NotNull
   public static Uj a(@NotNull String var0, @NotNull String var1) {
      return Uj.a(var0, var1);
   }

   @NotNull
   public static IRCMessage broadcast(@NotNull String var0) {
      return IRCMessage.of(var0);
   }

   @NotNull
   public static Uk a(@NotNull F var0) {
      return Uk.a(var0);
   }

   @NotNull
   public static Vg a(@NotNull b var0) {
      return Vg.a(var0);
   }

   @NotNull
   public static VJ a(@NotNull List var0, boolean var1) {
      return VJ.a(var0, var1);
   }

   @NotNull
   public static VA a(@NotNull m var0, @NotNull String var1) {
      return VA.a(var0, var1);
   }

   @NotNull
   public static VA b(@NotNull m var0, @NotNull String var1) {
      return VA.b(var0, var1);
   }

   @NotNull
   public static Ux a() {
      return EmptyMessage.get();
   }

   @Contract(
      value = "-> fail",
      pure = true
   )
   private MessageFactory() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
