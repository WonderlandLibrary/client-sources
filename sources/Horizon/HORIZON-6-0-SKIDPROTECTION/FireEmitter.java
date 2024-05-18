package HORIZON-6-0-SKIDPROTECTION;

public class FireEmitter implements ParticleEmitter
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private float Âµá€;
    
    public FireEmitter() {
        this.Ý = 50;
        this.Âµá€ = 40.0f;
    }
    
    public FireEmitter(final int x, final int y) {
        this.Ý = 50;
        this.Âµá€ = 40.0f;
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
    }
    
    public FireEmitter(final int x, final int y, final float size) {
        this.Ý = 50;
        this.Âµá€ = 40.0f;
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        this.Âµá€ = size;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ParticleSystem system, final int delta) {
        this.Ø­áŒŠá -= delta;
        if (this.Ø­áŒŠá <= 0) {
            this.Ø­áŒŠá = this.Ý;
            final Particle p = system.HorizonCode_Horizon_È(this, 1000.0f);
            p.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f, 0.5f);
            p.Â(this.HorizonCode_Horizon_È, this.Â);
            p.HorizonCode_Horizon_È(this.Âµá€);
            final float vx = (float)(-0.019999999552965164 + Math.random() * 0.03999999910593033);
            final float vy = (float)(-(Math.random() * 0.15000000596046448));
            p.HorizonCode_Horizon_È(vx, vy, 1.1f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Particle particle, final int delta) {
        if (particle.Ó() > 600.0f) {
            particle.Â(0.07f * delta);
        }
        else {
            particle.Â(-0.04f * delta * (this.Âµá€ / 40.0f));
        }
        final float c = 0.002f * delta;
        particle.Â(0.0f, -c / 2.0f, -c * 2.0f, -c / 4.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean enabled) {
    }
    
    @Override
    public boolean Âµá€() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public Image á() {
        return null;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ParticleSystem system) {
        return false;
    }
    
    @Override
    public boolean ÂµÈ() {
        return false;
    }
    
    @Override
    public void ˆÏ­() {
    }
    
    @Override
    public void £á() {
    }
}
