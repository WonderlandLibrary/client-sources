package net.minecraft.command.server;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent$Serializer;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CommandMessageRaw extends CommandBase {
   public String getCommandName() {
      return "tellraw";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.tellraw.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 2) {
         throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
      } else {
         EntityPlayerMP var3 = getPlayer(var1, var2[0]);
         String var4 = buildString(var2, 1);
         String var10000 = var4;

         try {
            IChatComponent var5 = IChatComponent$Serializer.jsonToComponent(var10000);
            var3.addChatMessage(ChatComponentProcessor.processComponent(var1, var5, var3));
         } catch (JsonParseException var7) {
            Throwable var6 = ExceptionUtils.getRootCause(var7);
            throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[]{""});
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()):null;
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
