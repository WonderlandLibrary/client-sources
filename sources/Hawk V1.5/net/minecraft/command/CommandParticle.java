package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandParticle extends CommandBase {
   private static final String __OBFID = "CL_00002341";

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? getListOfStringsMatchingLastWord(var2, EnumParticleTypes.func_179349_a()) : (var2.length > 1 && var2.length <= 4 ? func_175771_a(var2, 1, var3) : (var2.length == 9 ? getListOfStringsMatchingLastWord(var2, new String[]{"normal", "force"}) : null));
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.particle.usage";
   }

   public String getCommandName() {
      return "particle";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 8) {
         throw new WrongUsageException("commands.particle.usage", new Object[0]);
      } else {
         boolean var3 = false;
         EnumParticleTypes var4 = null;
         EnumParticleTypes[] var5 = EnumParticleTypes.values();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            EnumParticleTypes var8 = var5[var7];
            if (var8.func_179343_f()) {
               if (var2[0].startsWith(var8.func_179346_b())) {
                  var3 = true;
                  var4 = var8;
                  break;
               }
            } else if (var2[0].equals(var8.func_179346_b())) {
               var3 = true;
               var4 = var8;
               break;
            }
         }

         if (!var3) {
            throw new CommandException("commands.particle.notFound", new Object[]{var2[0]});
         } else {
            String var32 = var2[0];
            Vec3 var33 = var1.getPositionVector();
            double var9 = (double)((float)func_175761_b(var33.xCoord, var2[1], true));
            double var11 = (double)((float)func_175761_b(var33.yCoord, var2[2], true));
            double var13 = (double)((float)func_175761_b(var33.zCoord, var2[3], true));
            double var15 = (double)((float)parseDouble(var2[4]));
            double var17 = (double)((float)parseDouble(var2[5]));
            double var19 = (double)((float)parseDouble(var2[6]));
            double var21 = (double)((float)parseDouble(var2[7]));
            int var23 = 0;
            if (var2.length > 8) {
               var23 = parseInt(var2[8], 0);
            }

            boolean var24 = false;
            if (var2.length > 9 && "force".equals(var2[9])) {
               var24 = true;
            }

            World var25 = var1.getEntityWorld();
            if (var25 instanceof WorldServer) {
               WorldServer var26 = (WorldServer)var25;
               int[] var27 = new int[var4.func_179345_d()];
               if (var4.func_179343_f()) {
                  String[] var28 = var2[0].split("_", 3);

                  for(int var29 = 1; var29 < var28.length; ++var29) {
                     try {
                        var27[var29 - 1] = Integer.parseInt(var28[var29]);
                     } catch (NumberFormatException var31) {
                        throw new CommandException("commands.particle.notFound", new Object[]{var2[0]});
                     }
                  }
               }

               var26.func_180505_a(var4, var24, var9, var11, var13, var23, var15, var17, var19, var21, var27);
               notifyOperators(var1, this, "commands.particle.success", new Object[]{var32, Math.max(var23, 1)});
            }

         }
      }
   }
}
