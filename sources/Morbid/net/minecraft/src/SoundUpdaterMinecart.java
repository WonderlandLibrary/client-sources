package net.minecraft.src;

public class SoundUpdaterMinecart implements IUpdatePlayerListBox
{
    private final SoundManager theSoundManager;
    private final EntityMinecart theMinecart;
    private final EntityPlayerSP thePlayer;
    private boolean playerSPRidingMinecart;
    private boolean minecartIsDead;
    private boolean minecartIsMoving;
    private boolean silent;
    private float minecartSoundPitch;
    private float minecartMoveSoundVolume;
    private float minecartRideSoundVolume;
    private double minecartSpeed;
    
    public SoundUpdaterMinecart(final SoundManager par1SoundManager, final EntityMinecart par2EntityMinecart, final EntityPlayerSP par3EntityPlayerSP) {
        this.playerSPRidingMinecart = false;
        this.minecartIsDead = false;
        this.minecartIsMoving = false;
        this.silent = false;
        this.minecartSoundPitch = 0.0f;
        this.minecartMoveSoundVolume = 0.0f;
        this.minecartRideSoundVolume = 0.0f;
        this.minecartSpeed = 0.0;
        this.theSoundManager = par1SoundManager;
        this.theMinecart = par2EntityMinecart;
        this.thePlayer = par3EntityPlayerSP;
    }
    
    @Override
    public void update() {
        boolean var1 = false;
        final boolean var2 = this.playerSPRidingMinecart;
        final boolean var3 = this.minecartIsDead;
        final boolean var4 = this.minecartIsMoving;
        final float var5 = this.minecartMoveSoundVolume;
        final float var6 = this.minecartSoundPitch;
        final float var7 = this.minecartRideSoundVolume;
        final double var8 = this.minecartSpeed;
        this.playerSPRidingMinecart = (this.thePlayer != null && this.theMinecart.riddenByEntity == this.thePlayer);
        this.minecartIsDead = this.theMinecart.isDead;
        this.minecartSpeed = MathHelper.sqrt_double(this.theMinecart.motionX * this.theMinecart.motionX + this.theMinecart.motionZ * this.theMinecart.motionZ);
        this.minecartIsMoving = (this.minecartSpeed >= 0.01);
        if (var2 && !this.playerSPRidingMinecart) {
            this.theSoundManager.stopEntitySound(this.thePlayer);
        }
        if (this.minecartIsDead || (!this.silent && this.minecartMoveSoundVolume == 0.0f && this.minecartRideSoundVolume == 0.0f)) {
            if (!var3) {
                this.theSoundManager.stopEntitySound(this.theMinecart);
                if (var2 || this.playerSPRidingMinecart) {
                    this.theSoundManager.stopEntitySound(this.thePlayer);
                }
            }
            this.silent = true;
            if (this.minecartIsDead) {
                return;
            }
        }
        if (!this.theSoundManager.isEntitySoundPlaying(this.theMinecart) && this.minecartMoveSoundVolume > 0.0f) {
            this.theSoundManager.playEntitySound("minecart.base", this.theMinecart, this.minecartMoveSoundVolume, this.minecartSoundPitch, false);
            this.silent = false;
            var1 = true;
        }
        if (this.playerSPRidingMinecart && !this.theSoundManager.isEntitySoundPlaying(this.thePlayer) && this.minecartRideSoundVolume > 0.0f) {
            this.theSoundManager.playEntitySound("minecart.inside", this.thePlayer, this.minecartRideSoundVolume, 1.0f, true);
            this.silent = false;
            var1 = true;
        }
        if (this.minecartIsMoving) {
            if (this.minecartSoundPitch < 1.0f) {
                this.minecartSoundPitch += 0.0025f;
            }
            if (this.minecartSoundPitch > 1.0f) {
                this.minecartSoundPitch = 1.0f;
            }
            float var9 = MathHelper.clamp_float((float)this.minecartSpeed, 0.0f, 4.0f) / 4.0f;
            this.minecartRideSoundVolume = 0.0f + var9 * 0.75f;
            var9 = MathHelper.clamp_float(var9 * 2.0f, 0.0f, 1.0f);
            this.minecartMoveSoundVolume = 0.0f + var9 * 0.7f;
        }
        else if (var4) {
            this.minecartMoveSoundVolume = 0.0f;
            this.minecartSoundPitch = 0.0f;
            this.minecartRideSoundVolume = 0.0f;
        }
        if (!this.silent) {
            if (this.minecartSoundPitch != var6) {
                this.theSoundManager.setEntitySoundPitch(this.theMinecart, this.minecartSoundPitch);
            }
            if (this.minecartMoveSoundVolume != var5) {
                this.theSoundManager.setEntitySoundVolume(this.theMinecart, this.minecartMoveSoundVolume);
            }
            if (this.minecartRideSoundVolume != var7) {
                this.theSoundManager.setEntitySoundVolume(this.thePlayer, this.minecartRideSoundVolume);
            }
        }
        if (!var1 && (this.minecartMoveSoundVolume > 0.0f || this.minecartRideSoundVolume > 0.0f)) {
            this.theSoundManager.updateSoundLocation(this.theMinecart);
            if (this.playerSPRidingMinecart) {
                this.theSoundManager.updateSoundLocation(this.thePlayer, this.theMinecart);
            }
        }
        else {
            if (this.theSoundManager.isEntitySoundPlaying(this.theMinecart)) {
                this.theSoundManager.stopEntitySound(this.theMinecart);
            }
            if (this.playerSPRidingMinecart && this.theSoundManager.isEntitySoundPlaying(this.thePlayer)) {
                this.theSoundManager.stopEntitySound(this.thePlayer);
            }
        }
    }
}
