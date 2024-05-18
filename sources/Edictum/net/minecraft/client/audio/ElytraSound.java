package net.minecraft.client.audio;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class ElytraSound extends MovingSound
{
    private final EntityPlayerSP field_189405_m;
    private int field_189406_n;

    public ElytraSound(EntityPlayerSP p_i47113_1_)
    {
        super(SoundEvents.field_189426_aK, SoundCategory.PLAYERS);
        this.field_189405_m = p_i47113_1_;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.1F;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        ++this.field_189406_n;

        if (!this.field_189405_m.isDead && (this.field_189406_n <= 20 || this.field_189405_m.isElytraFlying()))
        {
            this.xPosF = (float)this.field_189405_m.posX;
            this.yPosF = (float)this.field_189405_m.posY;
            this.zPosF = (float)this.field_189405_m.posZ;
            float f = MathHelper.sqrt_double(this.field_189405_m.motionX * this.field_189405_m.motionX + this.field_189405_m.motionZ * this.field_189405_m.motionZ + this.field_189405_m.motionY * this.field_189405_m.motionY);
            float f1 = f / 2.0F;

            if ((double)f >= 0.01D)
            {
                this.volume = MathHelper.clamp_float(f1 * f1, 0.0F, 1.0F);
            }
            else
            {
                this.volume = 0.0F;
            }

            if (this.field_189406_n < 20)
            {
                this.volume = 0.0F;
            }
            else if (this.field_189406_n < 40)
            {
                this.volume = (float)((double)this.volume * ((double)(this.field_189406_n - 20) / 20.0D));
            }

            float f2 = 0.8F;

            if (this.volume > 0.8F)
            {
                this.pitch = 1.0F + (this.volume - 0.8F);
            }
            else
            {
                this.pitch = 1.0F;
            }
        }
        else
        {
            this.donePlaying = true;
        }
    }
}
