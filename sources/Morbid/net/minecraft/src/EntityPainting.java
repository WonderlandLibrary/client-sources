package net.minecraft.src;

import java.util.*;

public class EntityPainting extends EntityHanging
{
    public EnumArt art;
    
    public EntityPainting(final World par1World) {
        super(par1World);
    }
    
    public EntityPainting(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        super(par1World, par2, par3, par4, par5);
        final ArrayList var6 = new ArrayList();
        for (final EnumArt var10 : EnumArt.values()) {
            this.art = var10;
            this.setDirection(par5);
            if (this.onValidSurface()) {
                var6.add(var10);
            }
        }
        if (!var6.isEmpty()) {
            this.art = var6.get(this.rand.nextInt(var6.size()));
        }
        this.setDirection(par5);
    }
    
    public EntityPainting(final World par1World, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        this(par1World, par2, par3, par4, par5);
        for (final EnumArt var10 : EnumArt.values()) {
            if (var10.title.equals(par6Str)) {
                this.art = var10;
                break;
            }
        }
        this.setDirection(par5);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final String var2 = par1NBTTagCompound.getString("Motive");
        for (final EnumArt var6 : EnumArt.values()) {
            if (var6.title.equals(var2)) {
                this.art = var6;
            }
        }
        if (this.art == null) {
            this.art = EnumArt.Kebab;
        }
        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public int func_82329_d() {
        return this.art.sizeX;
    }
    
    @Override
    public int func_82330_g() {
        return this.art.sizeY;
    }
    
    @Override
    public void dropItemStack() {
        this.entityDropItem(new ItemStack(Item.painting), 0.0f);
    }
}
