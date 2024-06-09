package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;

public class CommandSummon extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001158";
    
    @Override
    public String Ý() {
        return "summon";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.summon.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        final String var3 = args[0];
        BlockPos var4 = sender.£á();
        final Vec3 var5 = sender.z_();
        double var6 = var5.HorizonCode_Horizon_È;
        double var7 = var5.Â;
        double var8 = var5.Ý;
        if (args.length >= 4) {
            var6 = CommandBase.Â(var6, args[1], true);
            var7 = CommandBase.Â(var7, args[2], false);
            var8 = CommandBase.Â(var8, args[3], true);
            var4 = new BlockPos(var6, var7, var8);
        }
        final World var9 = sender.k_();
        if (!var9.Ó(var4)) {
            throw new CommandException("commands.summon.outOfWorld", new Object[0]);
        }
        if ("LightningBolt".equals(var3)) {
            var9.Âµá€(new EntityLightningBolt(var9, var6, var7, var8));
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.summon.success", new Object[0]);
        }
        else {
            NBTTagCompound var10 = new NBTTagCompound();
            boolean var11 = false;
            if (args.length >= 5) {
                final IChatComponent var12 = CommandBase.HorizonCode_Horizon_È(sender, args, 4);
                try {
                    var10 = JsonToNBT.HorizonCode_Horizon_È(var12.Ø());
                    var11 = true;
                }
                catch (NBTException var13) {
                    throw new CommandException("commands.summon.tagError", new Object[] { var13.getMessage() });
                }
            }
            var10.HorizonCode_Horizon_È("id", var3);
            Entity var14;
            try {
                var14 = EntityList.HorizonCode_Horizon_È(var10, var9);
            }
            catch (RuntimeException var18) {
                throw new CommandException("commands.summon.failed", new Object[0]);
            }
            if (var14 == null) {
                throw new CommandException("commands.summon.failed", new Object[0]);
            }
            var14.Â(var6, var7, var8, var14.É, var14.áƒ);
            if (!var11 && var14 instanceof EntityLiving) {
                ((EntityLiving)var14).HorizonCode_Horizon_È(var9.Ê(new BlockPos(var14)), null);
            }
            var9.HorizonCode_Horizon_È(var14);
            Entity var15 = var14;
            Entity var17;
            for (NBTTagCompound var16 = var10; var15 != null && var16.Â("Riding", 10); var15 = var17, var16 = var16.ˆÏ­("Riding")) {
                var17 = EntityList.HorizonCode_Horizon_È(var16.ˆÏ­("Riding"), var9);
                if (var17 != null) {
                    var17.Â(var6, var7, var8, var17.É, var17.áƒ);
                    var9.HorizonCode_Horizon_È(var17);
                    var15.HorizonCode_Horizon_È(var17);
                }
            }
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.summon.success", new Object[0]);
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, EntityList.Â()) : ((args.length > 1 && args.length <= 4) ? CommandBase.HorizonCode_Horizon_È(args, 1, pos) : null);
    }
}
