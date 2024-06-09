package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;

public class CommandExecuteAt extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002344";
    
    @Override
    public String Ý() {
        return "execute";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.execute.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 5) {
            throw new WrongUsageException("commands.execute.usage", new Object[0]);
        }
        final Entity var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0], Entity.class);
        final double var4 = CommandBase.Â(var3.ŒÏ, args[1], false);
        final double var5 = CommandBase.Â(var3.Çªà¢, args[2], false);
        final double var6 = CommandBase.Â(var3.Ê, args[3], false);
        final BlockPos var7 = new BlockPos(var4, var5, var6);
        byte var8 = 4;
        if ("detect".equals(args[4]) && args.length > 10) {
            final World var9 = sender.k_();
            final double var10 = CommandBase.Â(var4, args[5], false);
            final double var11 = CommandBase.Â(var5, args[6], false);
            final double var12 = CommandBase.Â(var6, args[7], false);
            final Block var13 = CommandBase.à(sender, args[8]);
            final int var14 = CommandBase.HorizonCode_Horizon_È(args[9], -1, 15);
            final BlockPos var15 = new BlockPos(var10, var11, var12);
            final IBlockState var16 = var9.Â(var15);
            if (var16.Ý() != var13 || (var14 >= 0 && var16.Ý().Ý(var16) != var14)) {
                throw new CommandException("commands.execute.failed", new Object[] { "detect", var3.v_() });
            }
            var8 = 10;
        }
        final String var17 = CommandBase.HorizonCode_Horizon_È(args, var8);
        final ICommandSender var18 = new ICommandSender() {
            private static final String Â = "CL_00002343";
            
            @Override
            public String v_() {
                return var3.v_();
            }
            
            @Override
            public IChatComponent Ý() {
                return var3.Ý();
            }
            
            @Override
            public void HorizonCode_Horizon_È(final IChatComponent message) {
                sender.HorizonCode_Horizon_È(message);
            }
            
            @Override
            public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
                return sender.HorizonCode_Horizon_È(permissionLevel, command);
            }
            
            @Override
            public BlockPos £á() {
                return var7;
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(var4, var5, var6);
            }
            
            @Override
            public World k_() {
                return var3.Ï­Ðƒà;
            }
            
            @Override
            public Entity l_() {
                return var3;
            }
            
            @Override
            public boolean g_() {
                final MinecraftServer var1 = MinecraftServer.áƒ();
                return var1 == null || var1.Ý[0].Çªà¢().Â("commandBlockOutput");
            }
            
            @Override
            public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
                var3.HorizonCode_Horizon_È(p_174794_1_, p_174794_2_);
            }
        };
        final ICommandManager var19 = MinecraftServer.áƒ().Õ();
        try {
            final int var20 = var19.HorizonCode_Horizon_È(var18, var17);
            if (var20 < 1) {
                throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { var17 });
            }
        }
        catch (Throwable var21) {
            throw new CommandException("commands.execute.failed", new Object[] { var17, var3.v_() });
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : ((args.length > 1 && args.length <= 4) ? CommandBase.HorizonCode_Horizon_È(args, 1, pos) : ((args.length > 5 && args.length <= 8 && "detect".equals(args[4])) ? CommandBase.HorizonCode_Horizon_È(args, 5, pos) : ((args.length == 9 && "detect".equals(args[4])) ? CommandBase.HorizonCode_Horizon_È(args, Block.HorizonCode_Horizon_È.Ý()) : null)));
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
