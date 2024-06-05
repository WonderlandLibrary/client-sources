package net;

import cc.novoline.utils.messages.IRCMessage;
import cc.novoline.utils.messages.TextMessage;
import net.minecraft.util.EnumChatFormatting;
import net.skidunion.F;
import org.jetbrains.annotations.NotNull;

public final class Uk extends TextMessage {
   private Uk(@NotNull F var1) {
      super(IRCMessage.access$000());
      this.append(var1.a().c(), IRCMessage.access$100(var1.a().g().d()));
      this.append(" " + var1.b(), EnumChatFormatting.WHITE);
   }

   @NotNull
   public static Uk a(@NotNull F var0) {
      return new Uk(var0);
   }
}
