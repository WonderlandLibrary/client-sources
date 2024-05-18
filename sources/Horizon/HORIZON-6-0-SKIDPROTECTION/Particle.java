package HORIZON-6-0-SKIDPROTECTION;

public class Particle
{
    protected static SGL HorizonCode_Horizon_È;
    public static final int Â = 1;
    public static final int Ý = 2;
    public static final int Ø­áŒŠá = 3;
    protected float Âµá€;
    protected float Ó;
    protected float à;
    protected float Ø;
    protected float áŒŠÆ;
    protected Color áˆºÑ¢Õ;
    protected float ÂµÈ;
    protected float á;
    private ParticleSystem ˆà;
    private ParticleEmitter ¥Æ;
    protected Image ˆÏ­;
    protected int £á;
    protected int Å;
    protected boolean £à;
    protected float µà;
    
    static {
        Particle.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public Particle(final ParticleSystem engine) {
        this.áŒŠÆ = 10.0f;
        this.áˆºÑ¢Õ = Color.Ý;
        this.Å = 1;
        this.£à = false;
        this.µà = 1.0f;
        this.ˆà = engine;
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public float Â() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.Âµá€ += x;
        this.Ó += y;
    }
    
    public float Ý() {
        return this.áŒŠÆ;
    }
    
    public Color Ø­áŒŠá() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final Image image) {
        this.ˆÏ­ = image;
    }
    
    public float Âµá€() {
        return this.á;
    }
    
    public float Ó() {
        return this.ÂµÈ;
    }
    
    public boolean à() {
        return this.ÂµÈ > 0.0f;
    }
    
    public void Ø() {
        if ((this.ˆà.Ý() && this.Å == 1) || this.Å == 2) {
            TextureImpl.£à();
            Particle.HorizonCode_Horizon_È.Âµá€(2832);
            Particle.HorizonCode_Horizon_È.Â(this.áŒŠÆ / 2.0f);
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
            Particle.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0);
            Particle.HorizonCode_Horizon_È.Â(this.Âµá€, this.Ó);
            Particle.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        }
        else if (this.£à || this.µà != 1.0f) {
            Particle.HorizonCode_Horizon_È.Âµá€();
            Particle.HorizonCode_Horizon_È.Â(this.Âµá€, this.Ó, 0.0f);
            if (this.£à) {
                final float angle = (float)(Math.atan2(this.Ó, this.Âµá€) * 180.0 / 3.141592653589793);
                Particle.HorizonCode_Horizon_È.Ý(angle, 0.0f, 0.0f, 1.0f);
            }
            Particle.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1.0f, this.µà, 1.0f);
            this.ˆÏ­.HorizonCode_Horizon_È((int)(-(this.áŒŠÆ / 2.0f)), (int)(-(this.áŒŠÆ / 2.0f)), (int)this.áŒŠÆ, (int)this.áŒŠÆ, this.áˆºÑ¢Õ);
            Particle.HorizonCode_Horizon_È.Ø­áŒŠá();
        }
        else {
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
            this.ˆÏ­.Â((int)(this.Âµá€ - this.áŒŠÆ / 2.0f), (int)(this.Ó - this.áŒŠÆ / 2.0f), (int)this.áŒŠÆ, (int)this.áŒŠÆ);
        }
    }
    
    public void HorizonCode_Horizon_È(final int delta) {
        this.¥Æ.HorizonCode_Horizon_È(this, delta);
        this.ÂµÈ -= delta;
        if (this.ÂµÈ > 0.0f) {
            this.Âµá€ += delta * this.à;
            this.Ó += delta * this.Ø;
        }
        else {
            this.ˆà.HorizonCode_Horizon_È(this);
        }
    }
    
    public void HorizonCode_Horizon_È(final ParticleEmitter emitter, final float life) {
        this.Âµá€ = 0.0f;
        this.¥Æ = emitter;
        this.Ó = 0.0f;
        this.à = 0.0f;
        this.Ø = 0.0f;
        this.áŒŠÆ = 10.0f;
        this.£á = 0;
        this.ÂµÈ = life;
        this.á = life;
        this.£à = false;
        this.µà = 1.0f;
    }
    
    public void Â(final int type) {
        this.£á = type;
    }
    
    public void Ý(final int usePoints) {
        this.Å = usePoints;
    }
    
    public int áŒŠÆ() {
        return this.£á;
    }
    
    public void HorizonCode_Horizon_È(final float size) {
        this.áŒŠÆ = size;
    }
    
    public void Â(final float delta) {
        this.áŒŠÆ += delta;
        this.áŒŠÆ = Math.max(0.0f, this.áŒŠÆ);
    }
    
    public void Ý(final float life) {
        this.ÂµÈ = life;
    }
    
    public void Ø­áŒŠá(final float delta) {
        this.ÂµÈ += delta;
    }
    
    public void áˆºÑ¢Õ() {
        this.ÂµÈ = 1.0f;
    }
    
    public void HorizonCode_Horizon_È(final float r, final float g, final float b, final float a) {
        if (this.áˆºÑ¢Õ == Color.Ý) {
            this.áˆºÑ¢Õ = new Color(r, g, b, a);
        }
        else {
            this.áˆºÑ¢Õ.£à = r;
            this.áˆºÑ¢Õ.µà = g;
            this.áˆºÑ¢Õ.ˆà = b;
            this.áˆºÑ¢Õ.¥Æ = a;
        }
    }
    
    public void Â(final float x, final float y) {
        this.Âµá€ = x;
        this.Ó = y;
    }
    
    public void HorizonCode_Horizon_È(final float dirx, final float diry, final float speed) {
        this.à = dirx * speed;
        this.Ø = diry * speed;
    }
    
    public void Âµá€(final float speed) {
        final float currentSpeed = (float)Math.sqrt(this.à * this.à + this.Ø * this.Ø);
        this.à *= speed;
        this.Ø *= speed;
        this.à /= currentSpeed;
        this.Ø /= currentSpeed;
    }
    
    public void Ý(final float velx, final float vely) {
        this.HorizonCode_Horizon_È(velx, vely, 1.0f);
    }
    
    public void Ø­áŒŠá(final float dx, final float dy) {
        this.Âµá€ += dx;
        this.Ó += dy;
    }
    
    public void Â(final float r, final float g, final float b, final float a) {
        if (this.áˆºÑ¢Õ == Color.Ý) {
            this.áˆºÑ¢Õ = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        final Color áˆºÑ¢Õ = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ.£à += r;
        final Color áˆºÑ¢Õ2 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ2.µà += g;
        final Color áˆºÑ¢Õ3 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ3.ˆà += b;
        final Color áˆºÑ¢Õ4 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ4.¥Æ += a;
    }
    
    public void HorizonCode_Horizon_È(final int r, final int g, final int b, final int a) {
        if (this.áˆºÑ¢Õ == Color.Ý) {
            this.áˆºÑ¢Õ = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        final Color áˆºÑ¢Õ = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ.£à += r / 255.0f;
        final Color áˆºÑ¢Õ2 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ2.µà += g / 255.0f;
        final Color áˆºÑ¢Õ3 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ3.ˆà += b / 255.0f;
        final Color áˆºÑ¢Õ4 = this.áˆºÑ¢Õ;
        áˆºÑ¢Õ4.¥Æ += a / 255.0f;
    }
    
    public void Âµá€(final float dx, final float dy) {
        this.à += dx;
        this.Ø += dy;
    }
    
    public ParticleEmitter ÂµÈ() {
        return this.¥Æ;
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " : " + this.ÂµÈ;
    }
    
    public boolean á() {
        return this.£à;
    }
    
    public void HorizonCode_Horizon_È(final boolean oriented) {
        this.£à = oriented;
    }
    
    public float ˆÏ­() {
        return this.µà;
    }
    
    public void Ó(final float scaleY) {
        this.µà = scaleY;
    }
}
