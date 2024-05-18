package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import com.google.common.collect.Lists;

public class CommandSpreadPlayers extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001080";
    
    @Override
    public String Ý() {
        return "spreadplayers";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.spreadplayers.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }
        final byte var3 = 0;
        final BlockPos var4 = sender.£á();
        final double var5 = var4.HorizonCode_Horizon_È();
        int var6 = var3 + 1;
        final double var7 = CommandBase.Â(var5, args[var3], true);
        final double var8 = CommandBase.Â(var4.Ý(), args[var6++], true);
        final double var9 = CommandBase.HorizonCode_Horizon_È(args[var6++], 0.0);
        final double var10 = CommandBase.HorizonCode_Horizon_È(args[var6++], var9 + 1.0);
        final boolean var11 = CommandBase.Ø­áŒŠá(args[var6++]);
        final ArrayList var12 = Lists.newArrayList();
        while (var6 < args.length) {
            final String var13 = args[var6++];
            if (PlayerSelector.Â(var13)) {
                final List var14 = PlayerSelector.Â(sender, var13, Entity.class);
                if (var14.size() == 0) {
                    throw new EntityNotFoundException();
                }
                var12.addAll(var14);
            }
            else {
                final EntityPlayerMP var15 = MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(var13);
                if (var15 == null) {
                    throw new PlayerNotFoundException();
                }
                var12.add(var15);
            }
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ý, var12.size());
        if (var12.isEmpty()) {
            throw new EntityNotFoundException();
        }
        sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.spreadplayers.spreading." + (var11 ? "teams" : "players"), new Object[] { var12.size(), var10, var7, var8, var9 }));
        this.HorizonCode_Horizon_È(sender, var12, new HorizonCode_Horizon_È(var7, var8), var9, var10, var12.get(0).Ï­Ðƒà, var11);
    }
    
    private void HorizonCode_Horizon_È(final ICommandSender p_110669_1_, final List p_110669_2_, final HorizonCode_Horizon_È p_110669_3_, final double p_110669_4_, final double p_110669_6_, final World worldIn, final boolean p_110669_9_) throws CommandException {
        final Random var10 = new Random();
        final double var11 = p_110669_3_.HorizonCode_Horizon_È - p_110669_6_;
        final double var12 = p_110669_3_.Â - p_110669_6_;
        final double var13 = p_110669_3_.HorizonCode_Horizon_È + p_110669_6_;
        final double var14 = p_110669_3_.Â + p_110669_6_;
        final HorizonCode_Horizon_È[] var15 = this.HorizonCode_Horizon_È(var10, p_110669_9_ ? this.Â(p_110669_2_) : p_110669_2_.size(), var11, var12, var13, var14);
        final int var16 = this.HorizonCode_Horizon_È(p_110669_3_, p_110669_4_, worldIn, var10, var11, var12, var13, var14, var15, p_110669_9_);
        final double var17 = this.HorizonCode_Horizon_È(p_110669_2_, worldIn, var15, p_110669_9_);
        CommandBase.HorizonCode_Horizon_È(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"), var15.length, p_110669_3_.HorizonCode_Horizon_È, p_110669_3_.Â);
        if (var15.length > 1) {
            p_110669_1_.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[] { String.format("%.2f", var17), var16 }));
        }
    }
    
    private int Â(final List p_110667_1_) {
        final HashSet var2 = Sets.newHashSet();
        for (final Entity var4 : p_110667_1_) {
            if (var4 instanceof EntityPlayer) {
                var2.add(((EntityPlayer)var4).Çªáˆºá());
            }
            else {
                var2.add(null);
            }
        }
        return var2.size();
    }
    
    private int HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_110668_1_, final double p_110668_2_, final World worldIn, final Random p_110668_5_, final double p_110668_6_, final double p_110668_8_, final double p_110668_10_, final double p_110668_12_, final HorizonCode_Horizon_È[] p_110668_14_, final boolean p_110668_15_) throws CommandException {
        boolean var16 = true;
        double var17 = 3.4028234663852886E38;
        int var18;
        for (var18 = 0; var18 < 10000 && var16; ++var18) {
            var16 = false;
            var17 = 3.4028234663852886E38;
            for (int var19 = 0; var19 < p_110668_14_.length; ++var19) {
                final HorizonCode_Horizon_È var20 = p_110668_14_[var19];
                int var21 = 0;
                final HorizonCode_Horizon_È var22 = new HorizonCode_Horizon_È();
                for (int var23 = 0; var23 < p_110668_14_.length; ++var23) {
                    if (var19 != var23) {
                        final HorizonCode_Horizon_È var24 = p_110668_14_[var23];
                        final double var25 = var20.HorizonCode_Horizon_È(var24);
                        var17 = Math.min(var25, var17);
                        if (var25 < p_110668_2_) {
                            ++var21;
                            final HorizonCode_Horizon_È horizonCode_Horizon_È = var22;
                            horizonCode_Horizon_È.HorizonCode_Horizon_È += var24.HorizonCode_Horizon_È - var20.HorizonCode_Horizon_È;
                            final HorizonCode_Horizon_È horizonCode_Horizon_È2 = var22;
                            horizonCode_Horizon_È2.Â += var24.Â - var20.Â;
                        }
                    }
                }
                if (var21 > 0) {
                    final HorizonCode_Horizon_È horizonCode_Horizon_È3 = var22;
                    horizonCode_Horizon_È3.HorizonCode_Horizon_È /= var21;
                    final HorizonCode_Horizon_È horizonCode_Horizon_È4 = var22;
                    horizonCode_Horizon_È4.Â /= var21;
                    final double var26 = var22.Â();
                    if (var26 > 0.0) {
                        var22.HorizonCode_Horizon_È();
                        var20.Â(var22);
                    }
                    else {
                        var20.HorizonCode_Horizon_È(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
                    }
                    var16 = true;
                }
                if (var20.HorizonCode_Horizon_È(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_)) {
                    var16 = true;
                }
            }
            if (!var16) {
                for (final HorizonCode_Horizon_È var22 : p_110668_14_) {
                    if (!var22.Â(worldIn)) {
                        var22.HorizonCode_Horizon_È(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
                        var16 = true;
                    }
                }
            }
        }
        if (var18 >= 10000) {
            throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"), new Object[] { p_110668_14_.length, p_110668_1_.HorizonCode_Horizon_È, p_110668_1_.Â, String.format("%.2f", var17) });
        }
        return var18;
    }
    
    private double HorizonCode_Horizon_È(final List p_110671_1_, final World worldIn, final HorizonCode_Horizon_È[] p_110671_3_, final boolean p_110671_4_) {
        double var5 = 0.0;
        int var6 = 0;
        final HashMap var7 = Maps.newHashMap();
        for (int var8 = 0; var8 < p_110671_1_.size(); ++var8) {
            final Entity var9 = p_110671_1_.get(var8);
            HorizonCode_Horizon_È var11;
            if (p_110671_4_) {
                final Team var10 = (var9 instanceof EntityPlayer) ? ((EntityPlayer)var9).Çªáˆºá() : null;
                if (!var7.containsKey(var10)) {
                    var7.put(var10, p_110671_3_[var6++]);
                }
                var11 = var7.get(var10);
            }
            else {
                var11 = p_110671_3_[var6++];
            }
            var9.áˆºÑ¢Õ(MathHelper.Ý(var11.HorizonCode_Horizon_È) + 0.5f, var11.HorizonCode_Horizon_È(worldIn), MathHelper.Ý(var11.Â) + 0.5);
            double var12 = Double.MAX_VALUE;
            for (int var13 = 0; var13 < p_110671_3_.length; ++var13) {
                if (var11 != p_110671_3_[var13]) {
                    final double var14 = var11.HorizonCode_Horizon_È(p_110671_3_[var13]);
                    var12 = Math.min(var14, var12);
                }
            }
            var5 += var12;
        }
        var5 /= p_110671_1_.size();
        return var5;
    }
    
    private HorizonCode_Horizon_È[] HorizonCode_Horizon_È(final Random p_110670_1_, final int p_110670_2_, final double p_110670_3_, final double p_110670_5_, final double p_110670_7_, final double p_110670_9_) {
        final HorizonCode_Horizon_È[] var11 = new HorizonCode_Horizon_È[p_110670_2_];
        for (int var12 = 0; var12 < var11.length; ++var12) {
            final HorizonCode_Horizon_È var13 = new HorizonCode_Horizon_È();
            var13.HorizonCode_Horizon_È(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
            var11[var12] = var13;
        }
        return var11;
    }
    
    static class HorizonCode_Horizon_È
    {
        double HorizonCode_Horizon_È;
        double Â;
        private static final String Ý = "CL_00001105";
        
        HorizonCode_Horizon_È() {
        }
        
        HorizonCode_Horizon_È(final double p_i1358_1_, final double p_i1358_3_) {
            this.HorizonCode_Horizon_È = p_i1358_1_;
            this.Â = p_i1358_3_;
        }
        
        double HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_111099_1_) {
            final double var2 = this.HorizonCode_Horizon_È - p_111099_1_.HorizonCode_Horizon_È;
            final double var3 = this.Â - p_111099_1_.Â;
            return Math.sqrt(var2 * var2 + var3 * var3);
        }
        
        void HorizonCode_Horizon_È() {
            final double var1 = this.Â();
            this.HorizonCode_Horizon_È /= var1;
            this.Â /= var1;
        }
        
        float Â() {
            return MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â);
        }
        
        public void Â(final HorizonCode_Horizon_È p_111094_1_) {
            this.HorizonCode_Horizon_È -= p_111094_1_.HorizonCode_Horizon_È;
            this.Â -= p_111094_1_.Â;
        }
        
        public boolean HorizonCode_Horizon_È(final double p_111093_1_, final double p_111093_3_, final double p_111093_5_, final double p_111093_7_) {
            boolean var9 = false;
            if (this.HorizonCode_Horizon_È < p_111093_1_) {
                this.HorizonCode_Horizon_È = p_111093_1_;
                var9 = true;
            }
            else if (this.HorizonCode_Horizon_È > p_111093_5_) {
                this.HorizonCode_Horizon_È = p_111093_5_;
                var9 = true;
            }
            if (this.Â < p_111093_3_) {
                this.Â = p_111093_3_;
                var9 = true;
            }
            else if (this.Â > p_111093_7_) {
                this.Â = p_111093_7_;
                var9 = true;
            }
            return var9;
        }
        
        public int HorizonCode_Horizon_È(final World worldIn) {
            BlockPos var2 = new BlockPos(this.HorizonCode_Horizon_È, 256.0, this.Â);
            while (var2.Â() > 0) {
                var2 = var2.Âµá€();
                if (worldIn.Â(var2).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                    return var2.Â() + 1;
                }
            }
            return 257;
        }
        
        public boolean Â(final World worldIn) {
            BlockPos var2 = new BlockPos(this.HorizonCode_Horizon_È, 256.0, this.Â);
            while (var2.Â() > 0) {
                var2 = var2.Âµá€();
                final Material var3 = worldIn.Â(var2).Ý().Ó();
                if (var3 != Material.HorizonCode_Horizon_È) {
                    return !var3.HorizonCode_Horizon_È() && var3 != Material.Å;
                }
            }
            return false;
        }
        
        public void HorizonCode_Horizon_È(final Random p_111097_1_, final double p_111097_2_, final double p_111097_4_, final double p_111097_6_, final double p_111097_8_) {
            this.HorizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(p_111097_1_, p_111097_2_, p_111097_6_);
            this.Â = MathHelper.HorizonCode_Horizon_È(p_111097_1_, p_111097_4_, p_111097_8_);
        }
    }
}
