package net.minecraft.src;

import java.util.*;

public final class ItemStack
{
    public int stackSize;
    public int animationsToGo;
    public int itemID;
    public NBTTagCompound stackTagCompound;
    private int itemDamage;
    private EntityItemFrame itemFrame;
    
    public ItemStack(final Block par1Block) {
        this(par1Block, 1);
    }
    
    public ItemStack(final Block par1Block, final int par2) {
        this(par1Block.blockID, par2, 0);
    }
    
    public ItemStack(final Block par1Block, final int par2, final int par3) {
        this(par1Block.blockID, par2, par3);
    }
    
    public ItemStack(final Item par1Item) {
        this(par1Item.itemID, 1, 0);
    }
    
    public ItemStack(final Item par1Item, final int par2) {
        this(par1Item.itemID, par2, 0);
    }
    
    public ItemStack(final Item par1Item, final int par2, final int par3) {
        this(par1Item.itemID, par2, par3);
    }
    
    public ItemStack(final int par1, final int par2, final int par3) {
        this.stackSize = 0;
        this.itemFrame = null;
        this.itemID = par1;
        this.stackSize = par2;
        this.itemDamage = par3;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public static ItemStack loadItemStackFromNBT(final NBTTagCompound par0NBTTagCompound) {
        final ItemStack var1 = new ItemStack();
        var1.readFromNBT(par0NBTTagCompound);
        return (var1.getItem() != null) ? var1 : null;
    }
    
    private ItemStack() {
        this.stackSize = 0;
        this.itemFrame = null;
    }
    
    public ItemStack splitStack(final int par1) {
        final ItemStack var2 = new ItemStack(this.itemID, par1, this.itemDamage);
        if (this.stackTagCompound != null) {
            var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= par1;
        return var2;
    }
    
    public Item getItem() {
        return Item.itemsList[this.itemID];
    }
    
    public Icon getIconIndex() {
        return this.getItem().getIconIndex(this);
    }
    
    public int getItemSpriteNumber() {
        return this.getItem().getSpriteNumber();
    }
    
    public boolean tryPlaceItemIntoWorld(final EntityPlayer par1EntityPlayer, final World par2World, final int par3, final int par4, final int par5, final int par6, final float par7, final float par8, final float par9) {
        final boolean var10 = this.getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);
        if (var10) {
            par1EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }
        return var10;
    }
    
    public float getStrVsBlock(final Block par1Block) {
        return this.getItem().getStrVsBlock(this, par1Block);
    }
    
    public ItemStack useItemRightClick(final World par1World, final EntityPlayer par2EntityPlayer) {
        return this.getItem().onItemRightClick(this, par1World, par2EntityPlayer);
    }
    
    public ItemStack onFoodEaten(final World par1World, final EntityPlayer par2EntityPlayer) {
        return this.getItem().onEaten(this, par1World, par2EntityPlayer);
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("id", (short)this.itemID);
        par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
        par1NBTTagCompound.setShort("Damage", (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            par1NBTTagCompound.setTag("tag", this.stackTagCompound);
        }
        return par1NBTTagCompound;
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.itemID = par1NBTTagCompound.getShort("id");
        this.stackSize = par1NBTTagCompound.getByte("Count");
        this.itemDamage = par1NBTTagCompound.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
        if (par1NBTTagCompound.hasKey("tag")) {
            this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
        }
    }
    
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }
    
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }
    
    public boolean isItemStackDamageable() {
        return Item.itemsList[this.itemID].getMaxDamage() > 0;
    }
    
    public boolean getHasSubtypes() {
        return Item.itemsList[this.itemID].getHasSubtypes();
    }
    
    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }
    
    public int getItemDamageForDisplay() {
        return this.itemDamage;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public void setItemDamage(final int par1) {
        this.itemDamage = par1;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public int getMaxDamage() {
        return Item.itemsList[this.itemID].getMaxDamage();
    }
    
    public boolean attemptDamageItem(int par1, final Random par2Random) {
        if (!this.isItemStackDamageable()) {
            return false;
        }
        if (par1 > 0) {
            final int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int var4 = 0;
            for (int var5 = 0; var3 > 0 && var5 < par1; ++var5) {
                if (EnchantmentDurability.negateDamage(this, var3, par2Random)) {
                    ++var4;
                }
            }
            par1 -= var4;
            if (par1 <= 0) {
                return false;
            }
        }
        this.itemDamage += par1;
        return this.itemDamage > this.getMaxDamage();
    }
    
    public void damageItem(final int par1, final EntityLiving par2EntityLiving) {
        if ((!(par2EntityLiving instanceof EntityPlayer) || !((EntityPlayer)par2EntityLiving).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(par1, par2EntityLiving.getRNG())) {
            par2EntityLiving.renderBrokenItemStack(this);
            if (par2EntityLiving instanceof EntityPlayer) {
                ((EntityPlayer)par2EntityLiving).addStat(StatList.objectBreakStats[this.itemID], 1);
            }
            --this.stackSize;
            if (this.stackSize < 0) {
                this.stackSize = 0;
            }
            this.itemDamage = 0;
        }
    }
    
    public void hitEntity(final EntityLiving par1EntityLiving, final EntityPlayer par2EntityPlayer) {
        final boolean var3 = Item.itemsList[this.itemID].hitEntity(this, par1EntityLiving, par2EntityPlayer);
        if (var3) {
            par2EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }
    }
    
    public void onBlockDestroyed(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
        final boolean var7 = Item.itemsList[this.itemID].onBlockDestroyed(this, par1World, par2, par3, par4, par5, par6EntityPlayer);
        if (var7) {
            par6EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }
    }
    
    public int getDamageVsEntity(final Entity par1Entity) {
        return Item.itemsList[this.itemID].getDamageVsEntity(par1Entity);
    }
    
    public boolean canHarvestBlock(final Block par1Block) {
        return Item.itemsList[this.itemID].canHarvestBlock(par1Block);
    }
    
    public boolean interactWith(final EntityLiving par1EntityLiving) {
        return Item.itemsList[this.itemID].itemInteractionForEntity(this, par1EntityLiving);
    }
    
    public ItemStack copy() {
        final ItemStack var1 = new ItemStack(this.itemID, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return var1;
    }
    
    public static boolean areItemStackTagsEqual(final ItemStack par0ItemStack, final ItemStack par1ItemStack) {
        return (par0ItemStack == null && par1ItemStack == null) || (par0ItemStack != null && par1ItemStack != null && (par0ItemStack.stackTagCompound != null || par1ItemStack.stackTagCompound == null) && (par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound)));
    }
    
    public static boolean areItemStacksEqual(final ItemStack par0ItemStack, final ItemStack par1ItemStack) {
        return (par0ItemStack == null && par1ItemStack == null) || (par0ItemStack != null && par1ItemStack != null && par0ItemStack.isItemStackEqual(par1ItemStack));
    }
    
    private boolean isItemStackEqual(final ItemStack par1ItemStack) {
        return this.stackSize == par1ItemStack.stackSize && this.itemID == par1ItemStack.itemID && this.itemDamage == par1ItemStack.itemDamage && (this.stackTagCompound != null || par1ItemStack.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(par1ItemStack.stackTagCompound));
    }
    
    public boolean isItemEqual(final ItemStack par1ItemStack) {
        return this.itemID == par1ItemStack.itemID && this.itemDamage == par1ItemStack.itemDamage;
    }
    
    public String getItemName() {
        return Item.itemsList[this.itemID].getUnlocalizedName(this);
    }
    
    public static ItemStack copyItemStack(final ItemStack par0ItemStack) {
        return (par0ItemStack == null) ? null : par0ItemStack.copy();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.stackSize) + "x" + Item.itemsList[this.itemID].getUnlocalizedName() + "@" + this.itemDamage;
    }
    
    public void updateAnimation(final World par1World, final Entity par2Entity, final int par3, final boolean par4) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        Item.itemsList[this.itemID].onUpdate(this, par1World, par2Entity, par3, par4);
    }
    
    public void onCrafting(final World par1World, final EntityPlayer par2EntityPlayer, final int par3) {
        par2EntityPlayer.addStat(StatList.objectCraftStats[this.itemID], par3);
        Item.itemsList[this.itemID].onCreated(this, par1World, par2EntityPlayer);
    }
    
    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }
    
    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }
    
    public void onPlayerStoppedUsing(final World par1World, final EntityPlayer par2EntityPlayer, final int par3) {
        this.getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
    }
    
    public boolean hasTagCompound() {
        return this.stackTagCompound != null;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }
    
    public NBTTagList getEnchantmentTagList() {
        return (this.stackTagCompound == null) ? null : ((NBTTagList)this.stackTagCompound.getTag("ench"));
    }
    
    public void setTagCompound(final NBTTagCompound par1NBTTagCompound) {
        this.stackTagCompound = par1NBTTagCompound;
    }
    
    public String getDisplayName() {
        String var1 = this.getItem().getItemDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display")) {
            final NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
            if (var2.hasKey("Name")) {
                var1 = var2.getString("Name");
            }
        }
        return var1;
    }
    
    public void setItemName(final String par1Str) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound("tag");
        }
        if (!this.stackTagCompound.hasKey("display")) {
            this.stackTagCompound.setCompoundTag("display", new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag("display").setString("Name", par1Str);
    }
    
    public boolean hasDisplayName() {
        return this.stackTagCompound != null && this.stackTagCompound.hasKey("display") && this.stackTagCompound.getCompoundTag("display").hasKey("Name");
    }
    
    public List getTooltip(final EntityPlayer par1EntityPlayer, final boolean par2) {
        final ArrayList var3 = new ArrayList();
        final Item var4 = Item.itemsList[this.itemID];
        String var5 = this.getDisplayName();
        if (this.hasDisplayName()) {
            var5 = EnumChatFormatting.ITALIC + var5 + EnumChatFormatting.RESET;
        }
        if (par2) {
            String var6 = "";
            if (var5.length() > 0) {
                var5 = String.valueOf(var5) + " (";
                var6 = ")";
            }
            if (this.getHasSubtypes()) {
                var5 = String.valueOf(var5) + String.format("#%04d/%d%s", this.itemID, this.itemDamage, var6);
            }
            else {
                var5 = String.valueOf(var5) + String.format("#%04d%s", this.itemID, var6);
            }
        }
        else if (!this.hasDisplayName() && this.itemID == Item.map.itemID) {
            var5 = String.valueOf(var5) + " #" + this.itemDamage;
        }
        var3.add(var5);
        var4.addInformation(this, par1EntityPlayer, var3, par2);
        if (this.hasTagCompound()) {
            final NBTTagList var7 = this.getEnchantmentTagList();
            if (var7 != null) {
                for (int var8 = 0; var8 < var7.tagCount(); ++var8) {
                    final short var9 = ((NBTTagCompound)var7.tagAt(var8)).getShort("id");
                    final short var10 = ((NBTTagCompound)var7.tagAt(var8)).getShort("lvl");
                    if (Enchantment.enchantmentsList[var9] != null) {
                        var3.add(Enchantment.enchantmentsList[var9].getTranslatedName(var10));
                    }
                }
            }
            if (this.stackTagCompound.hasKey("display")) {
                final NBTTagCompound var11 = this.stackTagCompound.getCompoundTag("display");
                if (var11.hasKey("color")) {
                    if (par2) {
                        var3.add("Color: #" + Integer.toHexString(var11.getInteger("color")).toUpperCase());
                    }
                    else {
                        var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }
                if (var11.hasKey("Lore")) {
                    final NBTTagList var12 = var11.getTagList("Lore");
                    if (var12.tagCount() > 0) {
                        for (int var13 = 0; var13 < var12.tagCount(); ++var13) {
                            var3.add(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(EnumChatFormatting.ITALIC).append(((NBTTagString)var12.tagAt(var13)).data).toString());
                        }
                    }
                }
            }
        }
        if (par2 && this.isItemDamaged()) {
            var3.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
        }
        return var3;
    }
    
    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }
    
    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }
    
    public boolean isItemEnchantable() {
        return this.getItem().isItemTool(this) && !this.isItemEnchanted();
    }
    
    public void addEnchantment(final Enchantment par1Enchantment, final int par2) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.hasKey("ench")) {
            this.stackTagCompound.setTag("ench", new NBTTagList("ench"));
        }
        final NBTTagList var3 = (NBTTagList)this.stackTagCompound.getTag("ench");
        final NBTTagCompound var4 = new NBTTagCompound();
        var4.setShort("id", (short)par1Enchantment.effectId);
        var4.setShort("lvl", (byte)par2);
        var3.appendTag(var4);
    }
    
    public boolean isItemEnchanted() {
        return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench");
    }
    
    public void setTagInfo(final String par1Str, final NBTBase par2NBTBase) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(par1Str, par2NBTBase);
    }
    
    public boolean func_82835_x() {
        return this.getItem().func_82788_x();
    }
    
    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }
    
    public void setItemFrame(final EntityItemFrame par1EntityItemFrame) {
        this.itemFrame = par1EntityItemFrame;
    }
    
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }
    
    public int getRepairCost() {
        return (this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost")) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }
    
    public void setRepairCost(final int par1) {
        if (!this.hasTagCompound()) {
            this.stackTagCompound = new NBTTagCompound("tag");
        }
        this.stackTagCompound.setInteger("RepairCost", par1);
    }
}
