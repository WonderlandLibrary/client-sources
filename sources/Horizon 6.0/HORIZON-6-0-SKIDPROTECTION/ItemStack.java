package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.HashMultimap;
import java.util.Iterator;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

public final class ItemStack
{
    public static final DecimalFormat HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    private Item_1028566121 Âµá€;
    public NBTTagCompound Ø­áŒŠá;
    private int Ó;
    private EntityItemFrame à;
    private Block Ø;
    private boolean áŒŠÆ;
    private Block áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private static final String á = "CL_00000043";
    
    static {
        HorizonCode_Horizon_È = new DecimalFormat("#.###");
    }
    
    public ItemStack(final Block blockIn) {
        this(blockIn, 1);
    }
    
    public ItemStack(final Block blockIn, final int amount) {
        this(blockIn, amount, 0);
    }
    
    public ItemStack(final Block blockIn, final int amount, final int meta) {
        this(Item_1028566121.HorizonCode_Horizon_È(blockIn), amount, meta);
    }
    
    public ItemStack(final Item_1028566121 itemIn) {
        this(itemIn, 1);
    }
    
    public ItemStack(final Item_1028566121 itemIn, final int amount) {
        this(itemIn, amount, 0);
    }
    
    public ItemStack(final Item_1028566121 itemIn, final int amount, final int meta) {
        this.Ø = null;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = null;
        this.ÂµÈ = false;
        this.Âµá€ = itemIn;
        this.Â = amount;
        this.Ó = meta;
        if (this.Ó < 0) {
            this.Ó = 0;
        }
    }
    
    public static ItemStack HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        final ItemStack var1 = new ItemStack();
        var1.Ý(nbt);
        return (var1.HorizonCode_Horizon_È() != null) ? var1 : null;
    }
    
    private ItemStack() {
        this.Ø = null;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = null;
        this.ÂµÈ = false;
    }
    
    public ItemStack HorizonCode_Horizon_È(final int amount) {
        final ItemStack var2 = new ItemStack(this.Âµá€, amount, this.Ó);
        if (this.Ø­áŒŠá != null) {
            var2.Ø­áŒŠá = (NBTTagCompound)this.Ø­áŒŠá.Â();
        }
        this.Â -= amount;
        return var2;
    }
    
    public Item_1028566121 HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final boolean var8 = this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
        if (var8) {
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this.Âµá€)]);
        }
        return var8;
    }
    
    public float HorizonCode_Horizon_È(final Block p_150997_1_) {
        return this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this, p_150997_1_);
    }
    
    public ItemStack HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this, worldIn, playerIn);
    }
    
    public ItemStack Â(final World worldIn, final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È().Â(this, worldIn, playerIn);
    }
    
    public NBTTagCompound Â(final NBTTagCompound nbt) {
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Item_1028566121.HorizonCode_Horizon_È.Â(this.Âµá€);
        nbt.HorizonCode_Horizon_È("id", (var2 == null) ? "minecraft:air" : var2.toString());
        nbt.HorizonCode_Horizon_È("Count", (byte)this.Â);
        nbt.HorizonCode_Horizon_È("Damage", (short)this.Ó);
        if (this.Ø­áŒŠá != null) {
            nbt.HorizonCode_Horizon_È("tag", this.Ø­áŒŠá);
        }
        return nbt;
    }
    
    public void Ý(final NBTTagCompound nbt) {
        if (nbt.Â("id", 8)) {
            this.Âµá€ = Item_1028566121.HorizonCode_Horizon_È(nbt.áˆºÑ¢Õ("id"));
        }
        else {
            this.Âµá€ = Item_1028566121.HorizonCode_Horizon_È(nbt.Âµá€("id"));
        }
        this.Â = nbt.Ø­áŒŠá("Count");
        this.Ó = nbt.Âµá€("Damage");
        if (this.Ó < 0) {
            this.Ó = 0;
        }
        if (nbt.Â("tag", 10)) {
            this.Ø­áŒŠá = nbt.ˆÏ­("tag");
            if (this.Âµá€ != null) {
                this.Âµá€.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            }
        }
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È().HorizonCode_Horizon_È();
    }
    
    public boolean Ý() {
        return this.Â() > 1 && (!this.Ø­áŒŠá() || !this.Ó());
    }
    
    public boolean Ø­áŒŠá() {
        return this.Âµá€ != null && this.Âµá€.Ý() > 0 && (!this.£á() || !this.Å().£á("Unbreakable"));
    }
    
    public boolean Âµá€() {
        return this.Âµá€.Â();
    }
    
    public boolean Ó() {
        return this.Ø­áŒŠá() && this.Ó > 0;
    }
    
    public int à() {
        return this.Ó;
    }
    
    public int Ø() {
        return this.Ó;
    }
    
    public void Â(final int meta) {
        this.Ó = meta;
        if (this.Ó < 0) {
            this.Ó = 0;
        }
    }
    
    public int áŒŠÆ() {
        return this.Âµá€.Ý();
    }
    
    public boolean HorizonCode_Horizon_È(int amount, final Random rand) {
        if (!this.Ø­áŒŠá()) {
            return false;
        }
        if (amount > 0) {
            final int var3 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ø­à.ŒÏ, this);
            int var4 = 0;
            for (int var5 = 0; var3 > 0 && var5 < amount; ++var5) {
                if (EnchantmentDurability.HorizonCode_Horizon_È(this, var3, rand)) {
                    ++var4;
                }
            }
            amount -= var4;
            if (amount <= 0) {
                return false;
            }
        }
        this.Ó += amount;
        return this.Ó > this.áŒŠÆ();
    }
    
    public void HorizonCode_Horizon_È(final int amount, final EntityLivingBase entityIn) {
        if ((!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).áˆºáˆºáŠ.Ø­áŒŠá) && this.Ø­áŒŠá() && this.HorizonCode_Horizon_È(amount, entityIn.ˆÐƒØ())) {
            entityIn.Ý(this);
            --this.Â;
            if (entityIn instanceof EntityPlayer) {
                final EntityPlayer var3 = (EntityPlayer)entityIn;
                var3.HorizonCode_Horizon_È(StatList.Õ[Item_1028566121.HorizonCode_Horizon_È(this.Âµá€)]);
                if (this.Â == 0 && this.HorizonCode_Horizon_È() instanceof ItemBow) {
                    var3.ˆØ();
                }
            }
            if (this.Â < 0) {
                this.Â = 0;
            }
            this.Ó = 0;
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase entityIn, final EntityPlayer playerIn) {
        final boolean var3 = this.Âµá€.HorizonCode_Horizon_È(this, entityIn, playerIn);
        if (var3) {
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this.Âµá€)]);
        }
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Block blockIn, final BlockPos pos, final EntityPlayer playerIn) {
        final boolean var5 = this.Âµá€.HorizonCode_Horizon_È(this, worldIn, blockIn, pos, playerIn);
        if (var5) {
            playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this.Âµá€)]);
        }
    }
    
    public boolean Â(final Block p_150998_1_) {
        return this.Âµá€.Â(p_150998_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn, final EntityLivingBase entityIn) {
        return this.Âµá€.HorizonCode_Horizon_È(this, playerIn, entityIn);
    }
    
    public ItemStack áˆºÑ¢Õ() {
        final ItemStack var1 = new ItemStack(this.Âµá€, this.Â, this.Ó);
        if (this.Ø­áŒŠá != null) {
            var1.Ø­áŒŠá = (NBTTagCompound)this.Ø­áŒŠá.Â();
        }
        return var1;
    }
    
    public static boolean HorizonCode_Horizon_È(final ItemStack stackA, final ItemStack stackB) {
        return (stackA == null && stackB == null) || (stackA != null && stackB != null && (stackA.Ø­áŒŠá != null || stackB.Ø­áŒŠá == null) && (stackA.Ø­áŒŠá == null || stackA.Ø­áŒŠá.equals(stackB.Ø­áŒŠá)));
    }
    
    public static boolean Â(final ItemStack stackA, final ItemStack stackB) {
        return (stackA == null && stackB == null) || (stackA != null && stackB != null && stackA.Ø­áŒŠá(stackB));
    }
    
    private boolean Ø­áŒŠá(final ItemStack other) {
        return this.Â == other.Â && this.Âµá€ == other.Âµá€ && this.Ó == other.Ó && (this.Ø­áŒŠá != null || other.Ø­áŒŠá == null) && (this.Ø­áŒŠá == null || this.Ø­áŒŠá.equals(other.Ø­áŒŠá));
    }
    
    public static boolean Ý(final ItemStack stackA, final ItemStack stackB) {
        return (stackA == null && stackB == null) || (stackA != null && stackB != null && stackA.HorizonCode_Horizon_È(stackB));
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack other) {
        return other != null && this.Âµá€ == other.Âµá€ && this.Ó == other.Ó;
    }
    
    public String ÂµÈ() {
        return this.Âµá€.Â(this);
    }
    
    public static ItemStack Â(final ItemStack stack) {
        return (stack == null) ? null : stack.áˆºÑ¢Õ();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.Â) + "x" + this.Âµá€.Ø() + "@" + this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Entity entityIn, final int inventorySlot, final boolean isCurrentItem) {
        if (this.Ý > 0) {
            --this.Ý;
        }
        this.Âµá€.HorizonCode_Horizon_È(this, worldIn, entityIn, inventorySlot, isCurrentItem);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final int amount) {
        playerIn.HorizonCode_Horizon_È(StatList.áƒ[Item_1028566121.HorizonCode_Horizon_È(this.Âµá€)], amount);
        this.Âµá€.Ý(this, worldIn, playerIn);
    }
    
    public boolean Ý(final ItemStack p_179549_1_) {
        return this.Ø­áŒŠá(p_179549_1_);
    }
    
    public int á() {
        return this.HorizonCode_Horizon_È().Ø­áŒŠá(this);
    }
    
    public EnumAction ˆÏ­() {
        return this.HorizonCode_Horizon_È().Ý(this);
    }
    
    public void Â(final World worldIn, final EntityPlayer playerIn, final int timeLeft) {
        this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this, worldIn, playerIn, timeLeft);
    }
    
    public boolean £á() {
        return this.Ø­áŒŠá != null;
    }
    
    public NBTTagCompound Å() {
        return this.Ø­áŒŠá;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È(final String key, final boolean create) {
        if (this.Ø­áŒŠá != null && this.Ø­áŒŠá.Â(key, 10)) {
            return this.Ø­áŒŠá.ˆÏ­(key);
        }
        if (create) {
            final NBTTagCompound var3 = new NBTTagCompound();
            this.HorizonCode_Horizon_È(key, var3);
            return var3;
        }
        return null;
    }
    
    public NBTTagList £à() {
        return (this.Ø­áŒŠá == null) ? null : this.Ø­áŒŠá.Ý("ench", 10);
    }
    
    public void Ø­áŒŠá(final NBTTagCompound nbt) {
        this.Ø­áŒŠá = nbt;
    }
    
    public String µà() {
        String var1 = this.HorizonCode_Horizon_È().à(this);
        if (this.Ø­áŒŠá != null && this.Ø­áŒŠá.Â("display", 10)) {
            final NBTTagCompound var2 = this.Ø­áŒŠá.ˆÏ­("display");
            if (var2.Â("Name", 8)) {
                var1 = var2.áˆºÑ¢Õ("Name");
            }
        }
        return var1;
    }
    
    public ItemStack HorizonCode_Horizon_È(final String p_151001_1_) {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá = new NBTTagCompound();
        }
        if (!this.Ø­áŒŠá.Â("display", 10)) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È("display", new NBTTagCompound());
        }
        this.Ø­áŒŠá.ˆÏ­("display").HorizonCode_Horizon_È("Name", p_151001_1_);
        return this;
    }
    
    public void ˆà() {
        if (this.Ø­áŒŠá != null && this.Ø­áŒŠá.Â("display", 10)) {
            final NBTTagCompound var1 = this.Ø­áŒŠá.ˆÏ­("display");
            var1.Å("Name");
            if (var1.Ý()) {
                this.Ø­áŒŠá.Å("display");
                if (this.Ø­áŒŠá.Ý()) {
                    this.Ø­áŒŠá((NBTTagCompound)null);
                }
            }
        }
    }
    
    public boolean ¥Æ() {
        return this.Ø­áŒŠá != null && this.Ø­áŒŠá.Â("display", 10) && this.Ø­áŒŠá.ˆÏ­("display").Â("Name", 8);
    }
    
    public List HorizonCode_Horizon_È(final EntityPlayer playerIn, final boolean advanced) {
        final ArrayList var3 = Lists.newArrayList();
        String var4 = this.µà();
        if (this.¥Æ()) {
            var4 = EnumChatFormatting.µÕ + var4;
        }
        var4 = String.valueOf(var4) + EnumChatFormatting.Æ;
        if (advanced) {
            String var5 = "";
            if (var4.length() > 0) {
                var4 = String.valueOf(var4) + " (";
                var5 = ")";
            }
            final int var6 = Item_1028566121.HorizonCode_Horizon_È(this.Âµá€);
            if (this.Âµá€()) {
                var4 = String.valueOf(var4) + String.format("#%04d/%d%s", var6, this.Ó, var5);
            }
            else {
                var4 = String.valueOf(var4) + String.format("#%04d%s", var6, var5);
            }
        }
        else if (!this.¥Æ() && this.Âµá€ == Items.ˆØ) {
            var4 = String.valueOf(var4) + " #" + this.Ó;
        }
        var3.add(var4);
        int var7 = 0;
        if (this.£á() && this.Ø­áŒŠá.Â("HideFlags", 99)) {
            var7 = this.Ø­áŒŠá.Ó("HideFlags");
        }
        if ((var7 & 0x20) == 0x0) {
            this.Âµá€.HorizonCode_Horizon_È(this, playerIn, var3, advanced);
        }
        if (this.£á()) {
            if ((var7 & 0x1) == 0x0) {
                final NBTTagList var8 = this.£à();
                if (var8 != null) {
                    for (int var9 = 0; var9 < var8.Âµá€(); ++var9) {
                        final short var10 = var8.Â(var9).Âµá€("id");
                        final short var11 = var8.Â(var9).Âµá€("lvl");
                        if (Enchantment.HorizonCode_Horizon_È(var10) != null) {
                            var3.add(Enchantment.HorizonCode_Horizon_È(var10).Ø­áŒŠá(var11));
                        }
                    }
                }
            }
            if (this.Ø­áŒŠá.Â("display", 10)) {
                final NBTTagCompound var12 = this.Ø­áŒŠá.ˆÏ­("display");
                if (var12.Â("color", 3)) {
                    if (advanced) {
                        var3.add("Color: #" + Integer.toHexString(var12.Ó("color")).toUpperCase());
                    }
                    else {
                        var3.add(EnumChatFormatting.µÕ + StatCollector.HorizonCode_Horizon_È("item.dyed"));
                    }
                }
                if (var12.Â("Lore") == 9) {
                    final NBTTagList var13 = var12.Ý("Lore", 8);
                    if (var13.Âµá€() > 0) {
                        for (int var14 = 0; var14 < var13.Âµá€(); ++var14) {
                            var3.add(new StringBuilder().append(EnumChatFormatting.Ó).append(EnumChatFormatting.µÕ).append(var13.Ó(var14)).toString());
                        }
                    }
                }
            }
        }
        final Multimap var15 = this.ŒÏ();
        if (!var15.isEmpty() && (var7 & 0x2) == 0x0) {
            var3.add("");
            for (final Map.Entry var17 : var15.entries()) {
                final AttributeModifier var18 = var17.getValue();
                double var19 = var18.Ø­áŒŠá();
                if (var18.HorizonCode_Horizon_È() == Item_1028566121.Â) {
                    var19 += EnchantmentHelper.HorizonCode_Horizon_È(this, EnumCreatureAttribute.HorizonCode_Horizon_È);
                }
                double var20;
                if (var18.Ý() != 1 && var18.Ý() != 2) {
                    var20 = var19;
                }
                else {
                    var20 = var19 * 100.0;
                }
                if (var19 > 0.0) {
                    var3.add(EnumChatFormatting.áˆºÑ¢Õ + StatCollector.HorizonCode_Horizon_È("attribute.modifier.plus." + var18.Ý(), ItemStack.HorizonCode_Horizon_È.format(var20), StatCollector.HorizonCode_Horizon_È("attribute.name." + var17.getKey())));
                }
                else {
                    if (var19 >= 0.0) {
                        continue;
                    }
                    var20 *= -1.0;
                    var3.add(EnumChatFormatting.ˆÏ­ + StatCollector.HorizonCode_Horizon_È("attribute.modifier.take." + var18.Ý(), ItemStack.HorizonCode_Horizon_È.format(var20), StatCollector.HorizonCode_Horizon_È("attribute.name." + var17.getKey())));
                }
            }
        }
        if (this.£á() && this.Å().£á("Unbreakable") && (var7 & 0x4) == 0x0) {
            var3.add(EnumChatFormatting.áˆºÑ¢Õ + StatCollector.HorizonCode_Horizon_È("item.unbreakable"));
        }
        if (this.£á() && this.Ø­áŒŠá.Â("CanDestroy", 9) && (var7 & 0x8) == 0x0) {
            final NBTTagList var13 = this.Ø­áŒŠá.Ý("CanDestroy", 8);
            if (var13.Âµá€() > 0) {
                var3.add("");
                var3.add(EnumChatFormatting.Ø + StatCollector.HorizonCode_Horizon_È("item.canBreak"));
                for (int var14 = 0; var14 < var13.Âµá€(); ++var14) {
                    final Block var21 = Block.HorizonCode_Horizon_È(var13.Ó(var14));
                    if (var21 != null) {
                        var3.add(EnumChatFormatting.áŒŠÆ + var21.ŒÏ());
                    }
                    else {
                        var3.add(EnumChatFormatting.áŒŠÆ + "missingno");
                    }
                }
            }
        }
        if (this.£á() && this.Ø­áŒŠá.Â("CanPlaceOn", 9) && (var7 & 0x10) == 0x0) {
            final NBTTagList var13 = this.Ø­áŒŠá.Ý("CanPlaceOn", 8);
            if (var13.Âµá€() > 0) {
                var3.add("");
                var3.add(EnumChatFormatting.Ø + StatCollector.HorizonCode_Horizon_È("item.canPlace"));
                for (int var14 = 0; var14 < var13.Âµá€(); ++var14) {
                    final Block var21 = Block.HorizonCode_Horizon_È(var13.Ó(var14));
                    if (var21 != null) {
                        var3.add(EnumChatFormatting.áŒŠÆ + var21.ŒÏ());
                    }
                    else {
                        var3.add(EnumChatFormatting.áŒŠÆ + "missingno");
                    }
                }
            }
        }
        if (advanced) {
            if (this.Ó()) {
                var3.add("Durability: " + (this.áŒŠÆ() - this.à()) + " / " + this.áŒŠÆ());
            }
            var3.add(EnumChatFormatting.áŒŠÆ + ((ResourceLocation_1975012498)Item_1028566121.HorizonCode_Horizon_È.Â(this.Âµá€)).toString());
            if (this.£á()) {
                var3.add(EnumChatFormatting.áŒŠÆ + "NBT: " + this.Å().Âµá€().size() + " tag(s)");
            }
        }
        return var3;
    }
    
    public boolean Ø­à() {
        return this.HorizonCode_Horizon_È().Ø(this);
    }
    
    public EnumRarity µÕ() {
        return this.HorizonCode_Horizon_È().áŒŠÆ(this);
    }
    
    public boolean Æ() {
        return this.HorizonCode_Horizon_È().áˆºÑ¢Õ(this) && !this.Šáƒ();
    }
    
    public void HorizonCode_Horizon_È(final Enchantment ench, final int level) {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá(new NBTTagCompound());
        }
        if (!this.Ø­áŒŠá.Â("ench", 9)) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È("ench", new NBTTagList());
        }
        final NBTTagList var3 = this.Ø­áŒŠá.Ý("ench", 10);
        final NBTTagCompound var4 = new NBTTagCompound();
        var4.HorizonCode_Horizon_È("id", (short)ench.ŒÏ);
        var4.HorizonCode_Horizon_È("lvl", (short)(byte)level);
        var3.HorizonCode_Horizon_È(var4);
    }
    
    public boolean Šáƒ() {
        return this.Ø­áŒŠá != null && this.Ø­áŒŠá.Â("ench", 9);
    }
    
    public void HorizonCode_Horizon_È(final String key, final NBTBase value) {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá(new NBTTagCompound());
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(key, value);
    }
    
    public boolean Ï­Ðƒà() {
        return this.HorizonCode_Horizon_È().Å();
    }
    
    public boolean áŒŠà() {
        return this.à != null;
    }
    
    public void HorizonCode_Horizon_È(final EntityItemFrame frame) {
        this.à = frame;
    }
    
    public EntityItemFrame ŠÄ() {
        return this.à;
    }
    
    public int Ñ¢á() {
        return (this.£á() && this.Ø­áŒŠá.Â("RepairCost", 3)) ? this.Ø­áŒŠá.Ó("RepairCost") : 0;
    }
    
    public void Ý(final int cost) {
        if (!this.£á()) {
            this.Ø­áŒŠá = new NBTTagCompound();
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È("RepairCost", cost);
    }
    
    public Multimap ŒÏ() {
        Object var1;
        if (this.£á() && this.Ø­áŒŠá.Â("AttributeModifiers", 9)) {
            var1 = HashMultimap.create();
            final NBTTagList var2 = this.Ø­áŒŠá.Ý("AttributeModifiers", 10);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final NBTTagCompound var4 = var2.Â(var3);
                final AttributeModifier var5 = SharedMonsterAttributes.HorizonCode_Horizon_È(var4);
                if (var5 != null && var5.HorizonCode_Horizon_È().getLeastSignificantBits() != 0L && var5.HorizonCode_Horizon_È().getMostSignificantBits() != 0L) {
                    ((Multimap)var1).put((Object)var4.áˆºÑ¢Õ("AttributeName"), (Object)var5);
                }
            }
        }
        else {
            var1 = this.HorizonCode_Horizon_È().£à();
        }
        return (Multimap)var1;
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_150996_1_) {
        this.Âµá€ = p_150996_1_;
    }
    
    public IChatComponent Çªà¢() {
        final ChatComponentText var1 = new ChatComponentText(this.µà());
        if (this.¥Æ()) {
            var1.à().Â(Boolean.valueOf(true));
        }
        final IChatComponent var2 = new ChatComponentText("[").HorizonCode_Horizon_È(var1).Â("]");
        if (this.Âµá€ != null) {
            final NBTTagCompound var3 = new NBTTagCompound();
            this.Â(var3);
            var2.à().HorizonCode_Horizon_È(new HoverEvent(HoverEvent.HorizonCode_Horizon_È.Ý, new ChatComponentText(var3.toString())));
            var2.à().HorizonCode_Horizon_È(this.µÕ().Âµá€);
        }
        return var2;
    }
    
    public boolean Ý(final Block blockIn) {
        if (blockIn == this.Ø) {
            return this.áŒŠÆ;
        }
        this.Ø = blockIn;
        if (this.£á() && this.Ø­áŒŠá.Â("CanDestroy", 9)) {
            final NBTTagList var2 = this.Ø­áŒŠá.Ý("CanDestroy", 8);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final Block var4 = Block.HorizonCode_Horizon_È(var2.Ó(var3));
                if (var4 == blockIn) {
                    return this.áŒŠÆ = true;
                }
            }
        }
        return this.áŒŠÆ = false;
    }
    
    public boolean Ø­áŒŠá(final Block blockIn) {
        if (blockIn == this.áˆºÑ¢Õ) {
            return this.ÂµÈ;
        }
        this.áˆºÑ¢Õ = blockIn;
        if (this.£á() && this.Ø­áŒŠá.Â("CanPlaceOn", 9)) {
            final NBTTagList var2 = this.Ø­áŒŠá.Ý("CanPlaceOn", 8);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final Block var4 = Block.HorizonCode_Horizon_È(var2.Ó(var3));
                if (var4 == blockIn) {
                    return this.ÂµÈ = true;
                }
            }
        }
        return this.ÂµÈ = false;
    }
}
