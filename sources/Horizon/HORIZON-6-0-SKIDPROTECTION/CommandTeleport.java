package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Set;
import java.util.EnumSet;

public class CommandTeleport extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001180";
    
    @Override
    public String Ý() {
        return "tp";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.tp.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        byte var3 = 0;
        Object var4;
        if (args.length != 2 && args.length != 4 && args.length != 6) {
            var4 = CommandBase.Â(sender);
        }
        else {
            var4 = CommandBase.Â(sender, args[0]);
            var3 = 1;
        }
        if (args.length != 1 && args.length != 2) {
            if (args.length < var3 + 3) {
                throw new WrongUsageException("commands.tp.usage", new Object[0]);
            }
            if (((Entity)var4).Ï­Ðƒà != null) {
                int var5 = var3 + 1;
                final HorizonCode_Horizon_È var6 = CommandBase.HorizonCode_Horizon_È(((Entity)var4).ŒÏ, args[var3], true);
                final HorizonCode_Horizon_È var7 = CommandBase.HorizonCode_Horizon_È(((Entity)var4).Çªà¢, args[var5++], 0, 0, false);
                final HorizonCode_Horizon_È var8 = CommandBase.HorizonCode_Horizon_È(((Entity)var4).Ê, args[var5++], true);
                final HorizonCode_Horizon_È var9 = CommandBase.HorizonCode_Horizon_È(((Entity)var4).É, (args.length > var5) ? args[var5++] : "~", false);
                final HorizonCode_Horizon_È var10 = CommandBase.HorizonCode_Horizon_È(((Entity)var4).áƒ, (args.length > var5) ? args[var5] : "~", false);
                if (var4 instanceof EntityPlayerMP) {
                    final EnumSet var11 = EnumSet.noneOf(S08PacketPlayerPosLook.HorizonCode_Horizon_È.class);
                    if (var6.Ý()) {
                        var11.add(S08PacketPlayerPosLook.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                    }
                    if (var7.Ý()) {
                        var11.add(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Â);
                    }
                    if (var8.Ý()) {
                        var11.add(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ý);
                    }
                    if (var10.Ý()) {
                        var11.add(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Âµá€);
                    }
                    if (var9.Ý()) {
                        var11.add(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ø­áŒŠá);
                    }
                    float var12 = (float)var9.Â();
                    if (!var9.Ý()) {
                        var12 = MathHelper.à(var12);
                    }
                    float var13 = (float)var10.Â();
                    if (!var10.Ý()) {
                        var13 = MathHelper.à(var13);
                    }
                    if (var13 > 90.0f || var13 < -90.0f) {
                        var13 = MathHelper.à(180.0f - var13);
                        var12 = MathHelper.à(var12 + 180.0f);
                    }
                    ((Entity)var4).HorizonCode_Horizon_È((Entity)null);
                    ((EntityPlayerMP)var4).HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6.Â(), var7.Â(), var8.Â(), var12, var13, var11);
                    ((Entity)var4).Ø(var12);
                }
                else {
                    float var14 = (float)MathHelper.à(var9.HorizonCode_Horizon_È());
                    float var12 = (float)MathHelper.à(var10.HorizonCode_Horizon_È());
                    if (var12 > 90.0f || var12 < -90.0f) {
                        var12 = MathHelper.à(180.0f - var12);
                        var14 = MathHelper.à(var14 + 180.0f);
                    }
                    ((Entity)var4).Â(var6.HorizonCode_Horizon_È(), var7.HorizonCode_Horizon_È(), var8.HorizonCode_Horizon_È(), var14, var12);
                    ((Entity)var4).Ø(var14);
                }
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.tp.success.coordinates", ((Entity)var4).v_(), var6.HorizonCode_Horizon_È(), var7.HorizonCode_Horizon_È(), var8.HorizonCode_Horizon_È());
            }
        }
        else {
            final Entity var15 = CommandBase.Â(sender, args[args.length - 1]);
            if (var15.Ï­Ðƒà != ((Entity)var4).Ï­Ðƒà) {
                throw new CommandException("commands.tp.notSameDimension", new Object[0]);
            }
            ((Entity)var4).HorizonCode_Horizon_È((Entity)null);
            if (var4 instanceof EntityPlayerMP) {
                ((EntityPlayerMP)var4).HorizonCode_Horizon_È.HorizonCode_Horizon_È(var15.ŒÏ, var15.Çªà¢, var15.Ê, var15.É, var15.áƒ);
            }
            else {
                ((Entity)var4).Â(var15.ŒÏ, var15.Çªà¢, var15.Ê, var15.É, var15.áƒ);
            }
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.tp.success", ((Entity)var4).v_(), var15.v_());
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length != 1 && args.length != 2) ? null : CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
