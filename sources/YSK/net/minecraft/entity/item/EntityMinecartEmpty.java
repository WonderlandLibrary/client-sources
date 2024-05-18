package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class EntityMinecartEmpty extends EntityMinecart
{
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.RIDEABLE;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(null);
            }
            if (this.getRollingAmplitude() == 0) {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(0xB ^ 0x1);
                this.setDamage(50.0f);
                this.setBeenAttacked();
            }
        }
    }
    
    public EntityMinecartEmpty(final World world) {
        super(world);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
            return " ".length() != 0;
        }
        if (this.riddenByEntity != null && this.riddenByEntity != entityPlayer) {
            return "".length() != 0;
        }
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
        return " ".length() != 0;
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityMinecartEmpty(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
}
