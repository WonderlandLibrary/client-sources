package HORIZON-6-0-SKIDPROTECTION;

public class DamageSource
{
    public static DamageSource HorizonCode_Horizon_È;
    public static DamageSource Â;
    public static DamageSource Ý;
    public static DamageSource Ø­áŒŠá;
    public static DamageSource Âµá€;
    public static DamageSource Ó;
    public static DamageSource à;
    public static DamageSource Ø;
    public static DamageSource áŒŠÆ;
    public static DamageSource áˆºÑ¢Õ;
    public static DamageSource ÂµÈ;
    public static DamageSource á;
    public static DamageSource ˆÏ­;
    public static DamageSource £á;
    public static DamageSource Å;
    private boolean µà;
    private boolean ˆà;
    private boolean ¥Æ;
    private float Ø­à;
    private boolean µÕ;
    private boolean Æ;
    private boolean Šáƒ;
    private boolean Ï­Ðƒà;
    private boolean áŒŠà;
    public String £à;
    private static final String ŠÄ = "CL_00001521";
    
    static {
        DamageSource.HorizonCode_Horizon_È = new DamageSource("inFire").£á();
        DamageSource.Â = new DamageSource("lightningBolt");
        DamageSource.Ý = new DamageSource("onFire").ÂµÈ().£á();
        DamageSource.Ø­áŒŠá = new DamageSource("lava").£á();
        DamageSource.Âµá€ = new DamageSource("inWall").ÂµÈ();
        DamageSource.Ó = new DamageSource("drown").ÂµÈ();
        DamageSource.à = new DamageSource("starve").ÂµÈ().ˆÏ­();
        DamageSource.Ø = new DamageSource("cactus");
        DamageSource.áŒŠÆ = new DamageSource("fall").ÂµÈ();
        DamageSource.áˆºÑ¢Õ = new DamageSource("outOfWorld").ÂµÈ().á();
        DamageSource.ÂµÈ = new DamageSource("generic").ÂµÈ();
        DamageSource.á = new DamageSource("magic").ÂµÈ().Ø­à();
        DamageSource.ˆÏ­ = new DamageSource("wither").ÂµÈ();
        DamageSource.£á = new DamageSource("anvil");
        DamageSource.Å = new DamageSource("fallingBlock");
    }
    
    public static DamageSource HorizonCode_Horizon_È(final EntityLivingBase p_76358_0_) {
        return new EntityDamageSource("mob", p_76358_0_);
    }
    
    public static DamageSource HorizonCode_Horizon_È(final EntityPlayer p_76365_0_) {
        return new EntityDamageSource("player", p_76365_0_);
    }
    
    public static DamageSource HorizonCode_Horizon_È(final EntityArrow p_76353_0_, final Entity p_76353_1_) {
        return new EntityDamageSourceIndirect("arrow", p_76353_0_, p_76353_1_).Â();
    }
    
    public static DamageSource HorizonCode_Horizon_È(final EntityFireball p_76362_0_, final Entity p_76362_1_) {
        return (p_76362_1_ == null) ? new EntityDamageSourceIndirect("onFire", p_76362_0_, p_76362_0_).£á().Â() : new EntityDamageSourceIndirect("fireball", p_76362_0_, p_76362_1_).£á().Â();
    }
    
    public static DamageSource HorizonCode_Horizon_È(final Entity p_76356_0_, final Entity p_76356_1_) {
        return new EntityDamageSourceIndirect("thrown", p_76356_0_, p_76356_1_).Â();
    }
    
    public static DamageSource Â(final Entity p_76354_0_, final Entity p_76354_1_) {
        return new EntityDamageSourceIndirect("indirectMagic", p_76354_0_, p_76354_1_).ÂµÈ().Ø­à();
    }
    
    public static DamageSource HorizonCode_Horizon_È(final Entity p_92087_0_) {
        return new EntityDamageSource("thorns", p_92087_0_).Æ().Ø­à();
    }
    
    public static DamageSource HorizonCode_Horizon_È(final Explosion p_94539_0_) {
        return (p_94539_0_ != null && p_94539_0_.Ý() != null) ? new EntityDamageSource("explosion.player", p_94539_0_.Ý()).µà().Ø­áŒŠá() : new DamageSource("explosion").µà().Ø­áŒŠá();
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Æ;
    }
    
    public DamageSource Â() {
        this.Æ = true;
        return this;
    }
    
    public boolean Ý() {
        return this.áŒŠà;
    }
    
    public DamageSource Ø­áŒŠá() {
        this.áŒŠà = true;
        return this;
    }
    
    public boolean Âµá€() {
        return this.µà;
    }
    
    public float Ó() {
        return this.Ø­à;
    }
    
    public boolean à() {
        return this.ˆà;
    }
    
    public boolean Ø() {
        return this.¥Æ;
    }
    
    protected DamageSource(final String p_i1566_1_) {
        this.Ø­à = 0.3f;
        this.£à = p_i1566_1_;
    }
    
    public Entity áŒŠÆ() {
        return this.áˆºÑ¢Õ();
    }
    
    public Entity áˆºÑ¢Õ() {
        return null;
    }
    
    protected DamageSource ÂµÈ() {
        this.µà = true;
        this.Ø­à = 0.0f;
        return this;
    }
    
    protected DamageSource á() {
        this.ˆà = true;
        return this;
    }
    
    protected DamageSource ˆÏ­() {
        this.¥Æ = true;
        this.Ø­à = 0.0f;
        return this;
    }
    
    protected DamageSource £á() {
        this.µÕ = true;
        return this;
    }
    
    public IChatComponent Â(final EntityLivingBase p_151519_1_) {
        final EntityLivingBase var2 = p_151519_1_.ŒÓ();
        final String var3 = "death.attack." + this.£à;
        final String var4 = String.valueOf(var3) + ".player";
        return (var2 != null && StatCollector.Ý(var4)) ? new ChatComponentTranslation(var4, new Object[] { p_151519_1_.Ý(), var2.Ý() }) : new ChatComponentTranslation(var3, new Object[] { p_151519_1_.Ý() });
    }
    
    public boolean Å() {
        return this.µÕ;
    }
    
    public String £à() {
        return this.£à;
    }
    
    public DamageSource µà() {
        this.Šáƒ = true;
        return this;
    }
    
    public boolean ˆà() {
        return this.Šáƒ;
    }
    
    public boolean ¥Æ() {
        return this.Ï­Ðƒà;
    }
    
    public DamageSource Ø­à() {
        this.Ï­Ðƒà = true;
        return this;
    }
    
    public boolean µÕ() {
        final Entity var1 = this.áˆºÑ¢Õ();
        return var1 instanceof EntityPlayer && ((EntityPlayer)var1).áˆºáˆºáŠ.Ø­áŒŠá;
    }
}
