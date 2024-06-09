package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;

public class CommandReplaceItem extends CommandBase
{
    private static final Map HorizonCode_Horizon_È;
    private static final String Â = "CL_00002340";
    
    static {
        HorizonCode_Horizon_È = Maps.newHashMap();
        for (int var0 = 0; var0 < 54; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.container." + var0, var0);
        }
        for (int var0 = 0; var0 < 9; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.hotbar." + var0, var0);
        }
        for (int var0 = 0; var0 < 27; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.inventory." + var0, 9 + var0);
        }
        for (int var0 = 0; var0 < 27; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.enderchest." + var0, 200 + var0);
        }
        for (int var0 = 0; var0 < 8; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.villager." + var0, 300 + var0);
        }
        for (int var0 = 0; var0 < 15; ++var0) {
            CommandReplaceItem.HorizonCode_Horizon_È.put("slot.horse." + var0, 500 + var0);
        }
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.weapon", 99);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.armor.head", 103);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.armor.chest", 102);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.armor.legs", 101);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.armor.feet", 100);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.horse.saddle", 400);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.horse.armor", 401);
        CommandReplaceItem.HorizonCode_Horizon_È.put("slot.horse.chest", 499);
    }
    
    @Override
    public String Ý() {
        return "replaceitem";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.replaceitem.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }
        boolean var3;
        if (args[0].equals("entity")) {
            var3 = false;
        }
        else {
            if (!args[0].equals("block")) {
                throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }
            var3 = true;
        }
        byte var4;
        if (var3) {
            if (args.length < 6) {
                throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }
            var4 = 4;
        }
        else {
            if (args.length < 4) {
                throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
            }
            var4 = 2;
        }
        int var5 = var4 + 1;
        final int var6 = this.Âµá€(args[var4]);
        Item_1028566121 var7;
        try {
            var7 = CommandBase.Ó(sender, args[var5]);
        }
        catch (NumberInvalidException var8) {
            if (Block.HorizonCode_Horizon_È(args[var5]) != Blocks.Â) {
                throw var8;
            }
            var7 = null;
        }
        ++var5;
        final int var9 = (args.length > var5) ? CommandBase.HorizonCode_Horizon_È(args[var5++], 1, 64) : 1;
        final int var10 = (args.length > var5) ? CommandBase.HorizonCode_Horizon_È(args[var5++]) : 0;
        ItemStack var11 = new ItemStack(var7, var9, var10);
        if (args.length > var5) {
            final String var12 = CommandBase.HorizonCode_Horizon_È(sender, args, var5).Ø();
            try {
                var11.Ø­áŒŠá(JsonToNBT.HorizonCode_Horizon_È(var12));
            }
            catch (NBTException var13) {
                throw new CommandException("commands.replaceitem.tagError", new Object[] { var13.getMessage() });
            }
        }
        if (var11.HorizonCode_Horizon_È() == null) {
            var11 = null;
        }
        if (var3) {
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, 0);
            final BlockPos var14 = CommandBase.HorizonCode_Horizon_È(sender, args, 1, false);
            final World var15 = sender.k_();
            final TileEntity var16 = var15.HorizonCode_Horizon_È(var14);
            if (var16 == null || !(var16 instanceof IInventory)) {
                throw new CommandException("commands.replaceitem.noContainer", new Object[] { var14.HorizonCode_Horizon_È(), var14.Â(), var14.Ý() });
            }
            final IInventory var17 = (IInventory)var16;
            if (var6 >= 0 && var6 < var17.áŒŠÆ()) {
                var17.Ý(var6, var11);
            }
        }
        else {
            final Entity var18 = CommandBase.Â(sender, args[1]);
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, 0);
            if (var18 instanceof EntityPlayer) {
                ((EntityPlayer)var18).ŒÂ.Ý();
            }
            if (!var18.Â(var6, var11)) {
                throw new CommandException("commands.replaceitem.failed", new Object[] { var6, var9, (var11 == null) ? "Air" : var11.Çªà¢() });
            }
            if (var18 instanceof EntityPlayer) {
                ((EntityPlayer)var18).ŒÂ.Ý();
            }
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, var9);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.replaceitem.success", var6, var9, (var11 == null) ? "Air" : var11.Çªà¢());
    }
    
    private int Âµá€(final String p_175783_1_) throws CommandException {
        if (!CommandReplaceItem.HorizonCode_Horizon_È.containsKey(p_175783_1_)) {
            throw new CommandException("commands.generic.parameter.invalid", new Object[] { p_175783_1_ });
        }
        return CommandReplaceItem.HorizonCode_Horizon_È.get(p_175783_1_);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "entity", "block") : ((args.length == 2 && args[0].equals("entity")) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? null : CommandBase.HorizonCode_Horizon_È(args, Item_1028566121.HorizonCode_Horizon_È.Ý())) : CommandBase.HorizonCode_Horizon_È(args, CommandReplaceItem.HorizonCode_Horizon_È.keySet())));
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return args.length > 0 && args[0].equals("entity") && index == 1;
    }
}
