package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class CommandPlaySound extends CommandBase {
   private static final String __OBFID = "CL_00000774";

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException(this.getCommandUsage(var1), new Object[0]);
      } else {
         byte var3 = 0;
         int var4 = var3 + 1;
         String var5 = var2[var3];
         EntityPlayerMP var6 = getPlayer(var1, var2[var4++]);
         Vec3 var7 = var1.getPositionVector();
         double var8 = var7.xCoord;
         if (var2.length > var4) {
            var8 = func_175761_b(var8, var2[var4++], true);
         }

         double var10 = var7.yCoord;
         if (var2.length > var4) {
            var10 = func_175769_b(var10, var2[var4++], 0, 0, false);
         }

         double var12 = var7.zCoord;
         if (var2.length > var4) {
            var12 = func_175761_b(var12, var2[var4++], true);
         }

         double var14 = 1.0D;
         if (var2.length > var4) {
            var14 = parseDouble(var2[var4++], 0.0D, 3.4028234663852886E38D);
         }

         double var16 = 1.0D;
         if (var2.length > var4) {
            var16 = parseDouble(var2[var4++], 0.0D, 2.0D);
         }

         double var18 = 0.0D;
         if (var2.length > var4) {
            var18 = parseDouble(var2[var4], 0.0D, 1.0D);
         }

         double var20 = var14 > 1.0D ? var14 * 16.0D : 16.0D;
         double var22 = var6.getDistance(var8, var10, var12);
         if (var22 > var20) {
            if (var18 <= 0.0D) {
               throw new CommandException("commands.playsound.playerTooFar", new Object[]{var6.getName()});
            }

            double var24 = var8 - var6.posX;
            double var26 = var10 - var6.posY;
            double var28 = var12 - var6.posZ;
            double var30 = Math.sqrt(var24 * var24 + var26 * var26 + var28 * var28);
            if (var30 > 0.0D) {
               var8 = var6.posX + var24 / var30 * 2.0D;
               var10 = var6.posY + var26 / var30 * 2.0D;
               var12 = var6.posZ + var28 / var30 * 2.0D;
            }

            var14 = var18;
         }

         var6.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var5, var8, var10, var12, (float)var14, (float)var16));
         notifyOperators(var1, this, "commands.playsound.success", new Object[]{var5, var6.getName()});
      }
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.playsound.usage";
   }

   public String getCommandName() {
      return "playsound";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 2 ? getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()) : (var2.length > 2 && var2.length <= 5 ? func_175771_a(var2, 2, var3) : null);
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var2 == 1;
   }
}
