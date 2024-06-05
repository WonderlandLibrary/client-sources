package net.minecraft.command;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

public class CommandGameRule extends CommandBase {
   private static final String __OBFID = "CL_00000475";

   public static void func_175773_a(GameRules var0, String var1) {
      if ("reducedDebugInfo".equals(var1)) {
         int var2 = var0.getGameRuleBooleanValue(var1) ? 22 : 23;
         Iterator var3 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

         while(var3.hasNext()) {
            EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
            var4.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(var4, (byte)var2));
         }
      }

   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      GameRules var3 = this.getGameRules();
      String var4 = var2.length > 0 ? var2[0] : "";
      String var5 = var2.length > 1 ? func_180529_a(var2, 1) : "";
      switch(var2.length) {
      case 0:
         var1.addChatMessage(new ChatComponentText(joinNiceString(var3.getRules())));
         break;
      case 1:
         if (!var3.hasRule(var4)) {
            throw new CommandException("commands.gamerule.norule", new Object[]{var4});
         }

         String var6 = var3.getGameRuleStringValue(var4);
         var1.addChatMessage((new ChatComponentText(var4)).appendText(" = ").appendText(var6));
         var1.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3.getInt(var4));
         break;
      default:
         if (var3.areSameType(var4, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(var5) && !"false".equals(var5)) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[]{var5});
         }

         var3.setOrCreateGameRule(var4, var5);
         func_175773_a(var3, var4);
         notifyOperators(var1, this, "commands.gamerule.success", new Object[0]);
      }

   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return getListOfStringsMatchingLastWord(var2, this.getGameRules().getRules());
      } else {
         if (var2.length == 2) {
            GameRules var4 = this.getGameRules();
            if (var4.areSameType(var2[0], GameRules.ValueType.BOOLEAN_VALUE)) {
               return getListOfStringsMatchingLastWord(var2, new String[]{"true", "false"});
            }
         }

         return null;
      }
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.gamerule.usage";
   }

   private GameRules getGameRules() {
      return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
   }

   public String getCommandName() {
      return "gamerule";
   }
}
