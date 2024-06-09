package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandEffect extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000323";
    
    @Override
    public String Ý() {
        return "effect";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.effect.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        final EntityLivingBase var3 = (EntityLivingBase)CommandBase.HorizonCode_Horizon_È(sender, args[0], EntityLivingBase.class);
        if (args[1].equals("clear")) {
            if (var3.ÇŽÈ().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", new Object[] { var3.v_() });
            }
            var3.¥Âµá€();
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.effect.success.removed.all", var3.v_());
        }
        else {
            int var4;
            try {
                var4 = CommandBase.HorizonCode_Horizon_È(args[1], 1);
            }
            catch (NumberInvalidException var6) {
                final Potion var5 = Potion.HorizonCode_Horizon_È(args[1]);
                if (var5 == null) {
                    throw var6;
                }
                var4 = var5.É;
            }
            int var7 = 600;
            int var8 = 30;
            int var9 = 0;
            if (var4 < 0 || var4 >= Potion.HorizonCode_Horizon_È.length || Potion.HorizonCode_Horizon_È[var4] == null) {
                throw new NumberInvalidException("commands.effect.notFound", new Object[] { var4 });
            }
            final Potion var10 = Potion.HorizonCode_Horizon_È[var4];
            if (args.length >= 3) {
                var8 = CommandBase.HorizonCode_Horizon_È(args[2], 0, 1000000);
                if (var10.Ý()) {
                    var7 = var8;
                }
                else {
                    var7 = var8 * 20;
                }
            }
            else if (var10.Ý()) {
                var7 = 1;
            }
            if (args.length >= 4) {
                var9 = CommandBase.HorizonCode_Horizon_È(args[3], 0, 255);
            }
            boolean var11 = true;
            if (args.length >= 5 && "true".equalsIgnoreCase(args[4])) {
                var11 = false;
            }
            if (var8 > 0) {
                final PotionEffect var12 = new PotionEffect(var4, var7, var9, false, var11);
                var3.HorizonCode_Horizon_È(var12);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.effect.success", new ChatComponentTranslation(var12.Ó(), new Object[0]), var4, var9, var3.v_(), var8);
            }
            else {
                if (!var3.ˆÏ­(var4)) {
                    throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(var10.Ø­áŒŠá(), new Object[0]), var3.v_() });
                }
                var3.Å(var4);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.effect.success.removed", new ChatComponentTranslation(var10.Ø­áŒŠá(), new Object[0]), var3.v_());
            }
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : ((args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, Potion.HorizonCode_Horizon_È()) : ((args.length == 5) ? CommandBase.HorizonCode_Horizon_È(args, "true", "false") : null));
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
