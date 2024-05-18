package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.HashMultimap;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;

public class ItemPotion extends Item_1028566121
{
    private Map à;
    private static final Map Ø;
    private static final String áŒŠÆ = "CL_00000055";
    
    static {
        Ø = Maps.newLinkedHashMap();
    }
    
    public ItemPotion() {
        this.à = Maps.newHashMap();
        this.Â(1);
        this.HorizonCode_Horizon_È(true);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(CreativeTabs.ÂµÈ);
    }
    
    public List ÂµÈ(final ItemStack p_77832_1_) {
        if (p_77832_1_.£á() && p_77832_1_.Å().Â("CustomPotionEffects", 9)) {
            final ArrayList var7 = Lists.newArrayList();
            final NBTTagList var8 = p_77832_1_.Å().Ý("CustomPotionEffects", 10);
            for (int var9 = 0; var9 < var8.Âµá€(); ++var9) {
                final NBTTagCompound var10 = var8.Â(var9);
                final PotionEffect var11 = PotionEffect.Â(var10);
                if (var11 != null) {
                    var7.add(var11);
                }
            }
            return var7;
        }
        List var12 = this.à.get(p_77832_1_.Ø());
        if (var12 == null) {
            var12 = PotionHelper.Â(p_77832_1_.Ø(), false);
            this.à.put(p_77832_1_.Ø(), var12);
        }
        return var12;
    }
    
    public List Âµá€(final int p_77834_1_) {
        List var2 = this.à.get(p_77834_1_);
        if (var2 == null) {
            var2 = PotionHelper.Â(p_77834_1_, false);
            this.à.put(p_77834_1_, var2);
        }
        return var2;
    }
    
    @Override
    public ItemStack Â(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            --stack.Â;
        }
        if (!worldIn.ŠÄ) {
            final List var4 = this.ÂµÈ(stack);
            if (var4 != null) {
                for (final PotionEffect var6 : var4) {
                    playerIn.HorizonCode_Horizon_È(new PotionEffect(var6));
                }
            }
        }
        playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            if (stack.Â <= 0) {
                return new ItemStack(Items.Ñ¢ÇŽÏ);
            }
            playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(Items.Ñ¢ÇŽÏ));
        }
        return stack;
    }
    
    @Override
    public int Ø­áŒŠá(final ItemStack stack) {
        return 32;
    }
    
    @Override
    public EnumAction Ý(final ItemStack stack) {
        return EnumAction.Ý;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (Ó(itemStackIn.Ø())) {
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                --itemStackIn.Â;
            }
            worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 0.5f, 0.4f / (ItemPotion.Ý.nextFloat() * 0.4f + 0.8f));
            if (!worldIn.ŠÄ) {
                worldIn.HorizonCode_Horizon_È(new EntityPotion(worldIn, playerIn, itemStackIn));
            }
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
            return itemStackIn;
        }
        playerIn.Â(itemStackIn, this.Ø­áŒŠá(itemStackIn));
        return itemStackIn;
    }
    
    public static boolean Ó(final int p_77831_0_) {
        return (p_77831_0_ & 0x4000) != 0x0;
    }
    
    public int à(final int p_77620_1_) {
        return PotionHelper.HorizonCode_Horizon_È(p_77620_1_, false);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        return (renderPass > 0) ? 16777215 : this.à(stack.Ø());
    }
    
    public boolean Ø(final int p_77833_1_) {
        final List var2 = this.Âµá€(p_77833_1_);
        if (var2 != null && !var2.isEmpty()) {
            for (final PotionEffect var4 : var2) {
                if (Potion.HorizonCode_Horizon_È[var4.HorizonCode_Horizon_È()].Ý()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public String à(final ItemStack stack) {
        if (stack.Ø() == 0) {
            return StatCollector.HorizonCode_Horizon_È("item.emptyPotion.name").trim();
        }
        String var2 = "";
        if (Ó(stack.Ø())) {
            var2 = String.valueOf(StatCollector.HorizonCode_Horizon_È("potion.prefix.grenade").trim()) + " ";
        }
        final List var3 = Items.µÂ.ÂµÈ(stack);
        if (var3 != null && !var3.isEmpty()) {
            String var4 = var3.get(0).Ó();
            var4 = String.valueOf(var4) + ".postfix";
            return String.valueOf(var2) + StatCollector.HorizonCode_Horizon_È(var4).trim();
        }
        String var4 = PotionHelper.Â(stack.Ø());
        return String.valueOf(StatCollector.HorizonCode_Horizon_È(var4).trim()) + " " + super.à(stack);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        if (stack.Ø() != 0) {
            final List var5 = Items.µÂ.ÂµÈ(stack);
            final HashMultimap var6 = HashMultimap.create();
            if (var5 != null && !var5.isEmpty()) {
                for (final PotionEffect var8 : var5) {
                    String var9 = StatCollector.HorizonCode_Horizon_È(var8.Ó()).trim();
                    final Potion var10 = Potion.HorizonCode_Horizon_È[var8.HorizonCode_Horizon_È()];
                    final Map var11 = var10.ÂµÈ();
                    if (var11 != null && var11.size() > 0) {
                        for (final Map.Entry var13 : var11.entrySet()) {
                            final AttributeModifier var14 = var13.getValue();
                            final AttributeModifier var15 = new AttributeModifier(var14.Â(), var10.HorizonCode_Horizon_È(var8.Ý(), var14), var14.Ý());
                            var6.put((Object)var13.getKey().HorizonCode_Horizon_È(), (Object)var15);
                        }
                    }
                    if (var8.Ý() > 0) {
                        var9 = String.valueOf(var9) + " " + StatCollector.HorizonCode_Horizon_È("potion.potency." + var8.Ý()).trim();
                    }
                    if (var8.Â() > 20) {
                        var9 = String.valueOf(var9) + " (" + Potion.HorizonCode_Horizon_È(var8) + ")";
                    }
                    if (var10.à()) {
                        tooltip.add(EnumChatFormatting.ˆÏ­ + var9);
                    }
                    else {
                        tooltip.add(EnumChatFormatting.Ø + var9);
                    }
                }
            }
            else {
                final String var16 = StatCollector.HorizonCode_Horizon_È("potion.empty").trim();
                tooltip.add(EnumChatFormatting.Ø + var16);
            }
            if (!var6.isEmpty()) {
                tooltip.add("");
                tooltip.add(EnumChatFormatting.Ó + StatCollector.HorizonCode_Horizon_È("potion.effects.whenDrank"));
                for (final Map.Entry var17 : var6.entries()) {
                    final AttributeModifier var18 = var17.getValue();
                    final double var19 = var18.Ø­áŒŠá();
                    double var20;
                    if (var18.Ý() != 1 && var18.Ý() != 2) {
                        var20 = var18.Ø­áŒŠá();
                    }
                    else {
                        var20 = var18.Ø­áŒŠá() * 100.0;
                    }
                    if (var19 > 0.0) {
                        tooltip.add(EnumChatFormatting.áˆºÑ¢Õ + StatCollector.HorizonCode_Horizon_È("attribute.modifier.plus." + var18.Ý(), ItemStack.HorizonCode_Horizon_È.format(var20), StatCollector.HorizonCode_Horizon_È("attribute.name." + var17.getKey())));
                    }
                    else {
                        if (var19 >= 0.0) {
                            continue;
                        }
                        var20 *= -1.0;
                        tooltip.add(EnumChatFormatting.ˆÏ­ + StatCollector.HorizonCode_Horizon_È("attribute.modifier.take." + var18.Ý(), ItemStack.HorizonCode_Horizon_È.format(var20), StatCollector.HorizonCode_Horizon_È("attribute.name." + var17.getKey())));
                    }
                }
            }
        }
    }
    
    @Override
    public boolean Ø(final ItemStack stack) {
        final List var2 = this.ÂµÈ(stack);
        return var2 != null && !var2.isEmpty();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        super.HorizonCode_Horizon_È(itemIn, tab, subItems);
        if (ItemPotion.Ø.isEmpty()) {
            for (int var4 = 0; var4 <= 15; ++var4) {
                for (int var5 = 0; var5 <= 1; ++var5) {
                    int var6;
                    if (var5 == 0) {
                        var6 = (var4 | 0x2000);
                    }
                    else {
                        var6 = (var4 | 0x4000);
                    }
                    for (int var7 = 0; var7 <= 2; ++var7) {
                        int var8 = var6;
                        if (var7 != 0) {
                            if (var7 == 1) {
                                var8 = (var6 | 0x20);
                            }
                            else if (var7 == 2) {
                                var8 = (var6 | 0x40);
                            }
                        }
                        final List var9 = PotionHelper.Â(var8, false);
                        if (var9 != null && !var9.isEmpty()) {
                            ItemPotion.Ø.put(var9, var8);
                        }
                    }
                }
            }
        }
        for (final int var5 : ItemPotion.Ø.values()) {
            subItems.add(new ItemStack(itemIn, 1, var5));
        }
    }
}
