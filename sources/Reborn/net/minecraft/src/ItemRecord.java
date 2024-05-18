package net.minecraft.src;

import java.util.*;

public class ItemRecord extends Item
{
    private static final Map records;
    public final String recordName;
    
    static {
        records = new HashMap();
    }
    
    protected ItemRecord(final int par1, final String par2Str) {
        super(par1);
        this.recordName = par2Str;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        ItemRecord.records.put(par2Str, this);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return this.itemIcon;
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par3World.getBlockId(par4, par5, par6) != Block.jukebox.blockID || par3World.getBlockMetadata(par4, par5, par6) != 0) {
            return false;
        }
        if (par3World.isRemote) {
            return true;
        }
        ((BlockJukeBox)Block.jukebox).insertRecord(par3World, par4, par5, par6, par1ItemStack);
        par3World.playAuxSFXAtEntity(null, 1005, par4, par5, par6, this.itemID);
        --par1ItemStack.stackSize;
        return true;
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        par3List.add(this.getRecordTitle());
    }
    
    public String getRecordTitle() {
        return "C418 - " + this.recordName;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }
    
    public static ItemRecord getRecord(final String par0Str) {
        return ItemRecord.records.get(par0Str);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("record_" + this.recordName);
    }
}
