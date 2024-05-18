package net.minecraft.src;

public class DamageSource
{
    public static DamageSource inFire;
    public static DamageSource onFire;
    public static DamageSource lava;
    public static DamageSource inWall;
    public static DamageSource drown;
    public static DamageSource starve;
    public static DamageSource cactus;
    public static DamageSource fall;
    public static DamageSource outOfWorld;
    public static DamageSource generic;
    public static DamageSource magic;
    public static DamageSource wither;
    public static DamageSource anvil;
    public static DamageSource fallingBlock;
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private float hungerDamage;
    private boolean fireDamage;
    private boolean projectile;
    private boolean difficultyScaled;
    private boolean magicDamage;
    private boolean explosion;
    public String damageType;
    
    static {
        DamageSource.inFire = new DamageSource("inFire").setFireDamage();
        DamageSource.onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
        DamageSource.lava = new DamageSource("lava").setFireDamage();
        DamageSource.inWall = new DamageSource("inWall").setDamageBypassesArmor();
        DamageSource.drown = new DamageSource("drown").setDamageBypassesArmor();
        DamageSource.starve = new DamageSource("starve").setDamageBypassesArmor();
        DamageSource.cactus = new DamageSource("cactus");
        DamageSource.fall = new DamageSource("fall").setDamageBypassesArmor();
        DamageSource.outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
        DamageSource.generic = new DamageSource("generic").setDamageBypassesArmor();
        DamageSource.magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
        DamageSource.wither = new DamageSource("wither").setDamageBypassesArmor();
        DamageSource.anvil = new DamageSource("anvil");
        DamageSource.fallingBlock = new DamageSource("fallingBlock");
    }
    
    public static DamageSource causeMobDamage(final EntityLiving par0EntityLiving) {
        return new EntityDamageSource("mob", par0EntityLiving);
    }
    
    public static DamageSource causePlayerDamage(final EntityPlayer par0EntityPlayer) {
        return new EntityDamageSource("player", par0EntityPlayer);
    }
    
    public static DamageSource causeArrowDamage(final EntityArrow par0EntityArrow, final Entity par1Entity) {
        return new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity).setProjectile();
    }
    
    public static DamageSource causeFireballDamage(final EntityFireball par0EntityFireball, final Entity par1Entity) {
        return (par1Entity == null) ? new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity).setFireDamage().setProjectile();
    }
    
    public static DamageSource causeThrownDamage(final Entity par0Entity, final Entity par1Entity) {
        return new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity).setProjectile();
    }
    
    public static DamageSource causeIndirectMagicDamage(final Entity par0Entity, final Entity par1Entity) {
        return new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity).setDamageBypassesArmor().setMagicDamage();
    }
    
    public static DamageSource causeThornsDamage(final Entity par0Entity) {
        return new EntityDamageSource("thorns", par0Entity).setMagicDamage();
    }
    
    public static DamageSource setExplosionSource(final Explosion par0Explosion) {
        return (par0Explosion != null && par0Explosion.func_94613_c() != null) ? new EntityDamageSource("explosion.player", par0Explosion.func_94613_c()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }
    
    public boolean isProjectile() {
        return this.projectile;
    }
    
    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }
    
    public boolean isExplosion() {
        return this.explosion;
    }
    
    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }
    
    public boolean isUnblockable() {
        return this.isUnblockable;
    }
    
    public float getHungerDamage() {
        return this.hungerDamage;
    }
    
    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }
    
    protected DamageSource(final String par1Str) {
        this.isUnblockable = false;
        this.isDamageAllowedInCreativeMode = false;
        this.hungerDamage = 0.3f;
        this.magicDamage = false;
        this.explosion = false;
        this.damageType = par1Str;
    }
    
    public Entity getSourceOfDamage() {
        return this.getEntity();
    }
    
    public Entity getEntity() {
        return null;
    }
    
    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0f;
        return this;
    }
    
    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }
    
    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }
    
    public String getDeathMessage(final EntityLiving par1EntityLiving) {
        final EntityLiving var2 = par1EntityLiving.func_94060_bK();
        final String var3 = "death.attack." + this.damageType;
        final String var4 = String.valueOf(var3) + ".player";
        return (var2 != null && StatCollector.func_94522_b(var4)) ? StatCollector.translateToLocalFormatted(var4, par1EntityLiving.getTranslatedEntityName(), var2.getTranslatedEntityName()) : StatCollector.translateToLocalFormatted(var3, par1EntityLiving.getTranslatedEntityName());
    }
    
    public boolean isFireDamage() {
        return this.fireDamage;
    }
    
    public String getDamageType() {
        return this.damageType;
    }
    
    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }
    
    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }
    
    public boolean isMagicDamage() {
        return this.magicDamage;
    }
    
    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }
}
