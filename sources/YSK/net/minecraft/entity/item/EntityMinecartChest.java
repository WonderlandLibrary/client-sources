package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class EntityMinecartChest extends EntityMinecartContainer
{
    private static final String[] I;
    
    public EntityMinecartChest(final World world) {
        super(world);
    }
    
    static {
        I();
    }
    
    @Override
    public String getGuiID() {
        return EntityMinecartChest.I[" ".length()];
    }
    
    public EntityMinecartChest(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.CHEST;
    }
    
    @Override
    public int getSizeInventory() {
        return 0x67 ^ 0x7C;
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.worldObj.getGameRules().getBoolean(EntityMinecartChest.I["".length()])) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), " ".length(), 0.0f);
        }
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 0x6C ^ 0x64;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("/\u0003$\u001c2\"\u0018\u001864$\u001c\u0012", "KlarF");
        EntityMinecartChest.I[" ".length()] = I("83\u0018\u0001\u0005';\u0010\u0010\\62\u0013\u0017\u0012", "UZvdf");
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.chest.getDefaultState().withProperty((IProperty<Comparable>)BlockChest.FACING, EnumFacing.NORTH);
    }
}
