package net.minecraft.src;

public class EntitySplashFX extends EntityRainFX
{
    public EntitySplashFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6);
        this.particleGravity = 0.04f;
        this.nextTextureIndexX();
        if (par10 == 0.0 && (par8 != 0.0 || par12 != 0.0)) {
            this.motionX = par8;
            this.motionY = par10 + 0.1;
            this.motionZ = par12;
        }
    }
}
