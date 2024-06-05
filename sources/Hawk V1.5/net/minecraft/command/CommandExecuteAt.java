package net.minecraft.command;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandExecuteAt extends CommandBase {
   private static final String __OBFID = "CL_00002344";

   public String getCommandUsage(ICommandSender var1) {
      return "commands.execute.usage";
   }

   public String getCommandName() {
      return "execute";
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()) : (var2.length > 1 && var2.length <= 4 ? func_175771_a(var2, 1, var3) : (var2.length > 5 && var2.length <= 8 && "detect".equals(var2[4]) ? func_175771_a(var2, 5, var3) : (var2.length == 9 && "detect".equals(var2[4]) ? func_175762_a(var2, Block.blockRegistry.getKeys()) : null)));
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 5) {
         throw new WrongUsageException("commands.execute.usage", new Object[0]);
      } else {
         Entity var3 = func_175759_a(var1, var2[0], Entity.class);
         double var4 = func_175761_b(var3.posX, var2[1], false);
         double var6 = func_175761_b(var3.posY, var2[2], false);
         double var8 = func_175761_b(var3.posZ, var2[3], false);
         BlockPos var10 = new BlockPos(var4, var6, var8);
         byte var11 = 4;
         if ("detect".equals(var2[4]) && var2.length > 10) {
            World var12 = var1.getEntityWorld();
            double var13 = func_175761_b(var4, var2[5], false);
            double var15 = func_175761_b(var6, var2[6], false);
            double var17 = func_175761_b(var8, var2[7], false);
            Block var19 = getBlockByText(var1, var2[8]);
            int var20 = parseInt(var2[9], -1, 15);
            BlockPos var21 = new BlockPos(var13, var15, var17);
            IBlockState var22 = var12.getBlockState(var21);
            if (var22.getBlock() != var19 || var20 >= 0 && var22.getBlock().getMetaFromState(var22) != var20) {
               throw new CommandException("commands.execute.failed", new Object[]{"detect", var3.getName()});
            }

            var11 = 10;
         }

         String var24 = func_180529_a(var2, var11);
         ICommandSender var25 = new ICommandSender(this, var3, var1, var10, var4, var6, var8) {
            private final ICommandSender val$sender;
            private static final String __OBFID = "CL_00002343";
            private final Entity val$var3;
            private final BlockPos val$var10;
            private final double val$var4;
            private final double val$var8;
            private final double val$var6;
            final CommandExecuteAt this$0;

            public String getName() {
               return this.val$var3.getName();
            }

            public Vec3 getPositionVector() {
               return new Vec3(this.val$var4, this.val$var6, this.val$var8);
            }

            public IChatComponent getDisplayName() {
               return this.val$var3.getDisplayName();
            }

            public boolean canCommandSenderUseCommand(int var1, String var2) {
               return this.val$sender.canCommandSenderUseCommand(var1, var2);
            }

            {
               this.this$0 = var1;
               this.val$var3 = var2;
               this.val$sender = var3;
               this.val$var10 = var4;
               this.val$var4 = var5;
               this.val$var6 = var7;
               this.val$var8 = var9;
            }

            public void addChatMessage(IChatComponent var1) {
               this.val$sender.addChatMessage(var1);
            }

            public boolean sendCommandFeedback() {
               MinecraftServer var1 = MinecraftServer.getServer();
               return var1 == null || var1.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
            }

            public BlockPos getPosition() {
               return this.val$var10;
            }

            public void func_174794_a(CommandResultStats.Type var1, int var2) {
               this.val$var3.func_174794_a(var1, var2);
            }

            public World getEntityWorld() {
               return this.val$var3.worldObj;
            }

            public Entity getCommandSenderEntity() {
               return this.val$var3;
            }
         };
         ICommandManager var14 = MinecraftServer.getServer().getCommandManager();

         try {
            int var26 = var14.executeCommand(var25, var24);
            if (var26 < 1) {
               throw new CommandException("commands.execute.allInvocationsFailed", new Object[]{var24});
            }
         } catch (Throwable var23) {
            throw new CommandException("commands.execute.failed", new Object[]{var24, var3.getName()});
         }
      }
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var2 == 0;
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }
}
