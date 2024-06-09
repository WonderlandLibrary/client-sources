package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ConfigurableEmitter implements ParticleEmitter
{
    private static String ÇŽÕ;
    public Ø­áŒŠá HorizonCode_Horizon_È;
    public Ø­áŒŠá Â;
    public Ø­áŒŠá Ý;
    public Ø­áŒŠá Ø­áŒŠá;
    public Ø­áŒŠá Âµá€;
    public Ø­áŒŠá Ó;
    public Ý à;
    public Âµá€ Ø;
    public Ø­áŒŠá áŒŠÆ;
    public Ø­áŒŠá áˆºÑ¢Õ;
    public Âµá€ ÂµÈ;
    public Âµá€ á;
    public Âµá€ ˆÏ­;
    public Ø­áŒŠá £á;
    public ArrayList Å;
    public Âµá€ £à;
    public Âµá€ µà;
    public Â ˆà;
    public Â ¥Æ;
    public Â Ø­à;
    public Â µÕ;
    public Ø­áŒŠá Æ;
    public int Šáƒ;
    public boolean Ï­Ðƒà;
    public boolean áŒŠà;
    public String ŠÄ;
    public String Ñ¢á;
    private Image É;
    private boolean áƒ;
    private boolean á€;
    private float Õ;
    private float à¢;
    private int ŠÂµà;
    private int ¥à;
    private int Âµà;
    private ParticleSystem Ç;
    private int È;
    protected boolean ŒÏ;
    protected boolean Çªà¢;
    protected boolean Ê;
    protected float ÇŽÉ;
    protected float ˆá;
    
    static {
        ConfigurableEmitter.ÇŽÕ = "";
    }
    
    public static void HorizonCode_Horizon_È(String path) {
        if (!path.endsWith("/")) {
            path = String.valueOf(path) + "/";
        }
        ConfigurableEmitter.ÇŽÕ = path;
    }
    
    public ConfigurableEmitter(final String name) {
        this.HorizonCode_Horizon_È = new Ø­áŒŠá(100.0f, 100.0f, (Ø­áŒŠá)null);
        this.Â = new Ø­áŒŠá(5.0f, 5.0f, (Ø­áŒŠá)null);
        this.Ý = new Ø­áŒŠá(1000.0f, 1000.0f, (Ø­áŒŠá)null);
        this.Ø­áŒŠá = new Ø­áŒŠá(10.0f, 10.0f, (Ø­áŒŠá)null);
        this.Âµá€ = new Ø­áŒŠá(0.0f, 0.0f, (Ø­áŒŠá)null);
        this.Ó = new Ø­áŒŠá(0.0f, 0.0f, (Ø­áŒŠá)null);
        this.à = new Ý(360.0f, (Ý)null);
        this.Ø = new Âµá€(0.0f, (Âµá€)null);
        this.áŒŠÆ = new Ø­áŒŠá(0.0f, 0.0f, (Ø­áŒŠá)null);
        this.áˆºÑ¢Õ = new Ø­áŒŠá(50.0f, 50.0f, (Ø­áŒŠá)null);
        this.ÂµÈ = new Âµá€(0.0f, (Âµá€)null);
        this.á = new Âµá€(0.0f, (Âµá€)null);
        this.ˆÏ­ = new Âµá€(0.0f, (Âµá€)null);
        this.£á = new Ø­áŒŠá(1000.0f, 1000.0f, (Ø­áŒŠá)null);
        this.Å = new ArrayList();
        this.£à = new Âµá€(255.0f, (Âµá€)null);
        this.µà = new Âµá€(0.0f, (Âµá€)null);
        this.Æ = new Ø­áŒŠá(1000.0f, 1000.0f, (Ø­áŒŠá)null);
        this.Šáƒ = 1;
        this.Ï­Ðƒà = false;
        this.áŒŠà = false;
        this.Ñ¢á = "";
        this.á€ = true;
        this.ŠÂµà = 0;
        this.ŒÏ = false;
        this.Çªà¢ = false;
        this.ŠÄ = name;
        this.È = (int)this.Æ.HorizonCode_Horizon_È();
        this.¥à = (int)this.£á.HorizonCode_Horizon_È();
        this.Å.add(new HorizonCode_Horizon_È(0.0f, Color.Ý));
        this.Å.add(new HorizonCode_Horizon_È(1.0f, Color.Âµá€));
        ArrayList curve = new ArrayList();
        curve.add(new Vector2f(0.0f, 0.0f));
        curve.add(new Vector2f(1.0f, 255.0f));
        this.ˆà = new Â(curve, 0, 255);
        curve = new ArrayList();
        curve.add(new Vector2f(0.0f, 0.0f));
        curve.add(new Vector2f(1.0f, 255.0f));
        this.¥Æ = new Â(curve, 0, 255);
        curve = new ArrayList();
        curve.add(new Vector2f(0.0f, 0.0f));
        curve.add(new Vector2f(1.0f, 1.0f));
        this.Ø­à = new Â(curve, 0, 1);
        curve = new ArrayList();
        curve.add(new Vector2f(0.0f, 0.0f));
        curve.add(new Vector2f(1.0f, 1.0f));
        this.µÕ = new Â(curve, 0, 1);
    }
    
    public void Â(String imageName) {
        if (imageName.length() == 0) {
            imageName = null;
        }
        if ((this.Ñ¢á = imageName) == null) {
            this.É = null;
        }
        else {
            this.áƒ = true;
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Ñ¢á;
    }
    
    @Override
    public String toString() {
        return "[" + this.ŠÄ + "]";
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È(x, y, true);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final boolean moveParticles) {
        if (moveParticles) {
            this.Ê = true;
            this.ÇŽÉ -= this.Õ - x;
            this.ˆá -= this.à¢ - y;
        }
        this.Õ = x;
        this.à¢ = y;
    }
    
    public float Â() {
        return this.Õ;
    }
    
    public float Ý() {
        return this.à¢;
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return this.á€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean enabled) {
        this.á€ = enabled;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ParticleSystem system, final int delta) {
        this.Ç = system;
        if (!this.Ê) {
            this.ÇŽÉ = 0.0f;
            this.ˆá = 0.0f;
        }
        else {
            this.Ê = false;
        }
        if (this.áƒ) {
            this.áƒ = false;
            try {
                this.É = new Image(String.valueOf(ConfigurableEmitter.ÇŽÕ) + this.Ñ¢á);
            }
            catch (SlickException e) {
                this.É = null;
                Log.HorizonCode_Horizon_È(e);
            }
        }
        if ((this.ŒÏ || (this.£á.Â() && this.¥à < 0) || (this.Æ.Â() && this.È <= 0)) && this.Âµà == 0) {
            this.Çªà¢ = true;
        }
        this.Âµà = 0;
        if (this.ŒÏ) {
            return;
        }
        if (this.£á.Â()) {
            if (this.¥à < 0) {
                return;
            }
            this.¥à -= delta;
        }
        if (this.Æ.Â() && this.È <= 0) {
            return;
        }
        this.ŠÂµà -= delta;
        if (this.ŠÂµà < 0) {
            this.ŠÂµà = (int)this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
            for (int count = (int)this.Â.HorizonCode_Horizon_È(), i = 0; i < count; ++i) {
                final Particle p = system.HorizonCode_Horizon_È(this, this.Ý.HorizonCode_Horizon_È());
                p.HorizonCode_Horizon_È(this.Ø­áŒŠá.HorizonCode_Horizon_È());
                p.Â(this.Õ + this.Âµá€.HorizonCode_Horizon_È(), this.à¢ + this.Ó.HorizonCode_Horizon_È());
                p.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f);
                final float dist = this.áŒŠÆ.HorizonCode_Horizon_È();
                final float power = this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
                if (dist != 0.0f || power != 0.0f) {
                    final float s = this.à.HorizonCode_Horizon_È(0.0f);
                    final float ang = s + this.Ø.HorizonCode_Horizon_È(0.0f) - this.à.HorizonCode_Horizon_È() / 2.0f - 90.0f;
                    final float xa = (float)FastTrig.Â(Math.toRadians(ang)) * dist;
                    final float ya = (float)FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang)) * dist;
                    p.Ø­áŒŠá(xa, ya);
                    final float xv = (float)FastTrig.Â(Math.toRadians(ang));
                    final float yv = (float)FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang));
                    p.HorizonCode_Horizon_È(xv, yv, power * 0.001f);
                }
                if (this.É != null) {
                    p.HorizonCode_Horizon_È(this.É);
                }
                final HorizonCode_Horizon_È start = this.Å.get(0);
                p.HorizonCode_Horizon_È(start.Â.£à, start.Â.µà, start.Â.ˆà, this.£à.HorizonCode_Horizon_È(0.0f) / 255.0f);
                p.Ý(this.Šáƒ);
                p.HorizonCode_Horizon_È(this.Ï­Ðƒà);
                if (this.Æ.Â()) {
                    --this.È;
                    if (this.È <= 0) {
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Particle particle, final int delta) {
        ++this.Âµà;
        particle.Âµá€ += this.ÇŽÉ;
        particle.Ó += this.ˆá;
        particle.Âµá€(this.ˆÏ­.HorizonCode_Horizon_È(0.0f) * 5.0E-5f * delta, this.á.HorizonCode_Horizon_È(0.0f) * 5.0E-5f * delta);
        final float offset = particle.Ó() / particle.Âµá€();
        final float inv = 1.0f - offset;
        float colOffset = 0.0f;
        float colInv = 1.0f;
        Color startColor = null;
        Color endColor = null;
        for (int i = 0; i < this.Å.size() - 1; ++i) {
            final HorizonCode_Horizon_È rec1 = this.Å.get(i);
            final HorizonCode_Horizon_È rec2 = this.Å.get(i + 1);
            if (inv >= rec1.HorizonCode_Horizon_È && inv <= rec2.HorizonCode_Horizon_È) {
                startColor = rec1.Â;
                endColor = rec2.Â;
                final float step = rec2.HorizonCode_Horizon_È - rec1.HorizonCode_Horizon_È;
                colOffset = inv - rec1.HorizonCode_Horizon_È;
                colOffset /= step;
                colOffset = 1.0f - colOffset;
                colInv = 1.0f - colOffset;
            }
        }
        if (startColor != null) {
            final float r = startColor.£à * colOffset + endColor.£à * colInv;
            final float g = startColor.µà * colOffset + endColor.µà * colInv;
            final float b = startColor.ˆà * colOffset + endColor.ˆà * colInv;
            float a;
            if (this.ˆà.Â()) {
                a = this.ˆà.HorizonCode_Horizon_È(inv) / 255.0f;
            }
            else {
                a = this.£à.HorizonCode_Horizon_È(0.0f) / 255.0f * offset + this.µà.HorizonCode_Horizon_È(0.0f) / 255.0f * inv;
            }
            particle.HorizonCode_Horizon_È(r, g, b, a);
        }
        if (this.¥Æ.Â()) {
            final float s = this.¥Æ.HorizonCode_Horizon_È(inv);
            particle.HorizonCode_Horizon_È(s);
        }
        else {
            particle.Â(delta * this.ÂµÈ.HorizonCode_Horizon_È(0.0f) * 0.001f);
        }
        if (this.Ø­à.Â()) {
            particle.Âµá€(this.Ø­à.HorizonCode_Horizon_È(inv));
        }
        if (this.µÕ.Â()) {
            particle.Ó(this.µÕ.HorizonCode_Horizon_È(inv));
        }
    }
    
    @Override
    public boolean Âµá€() {
        if (this.Ç == null) {
            return false;
        }
        if (this.£á.Â()) {
            return this.¥à <= 0 && this.Çªà¢;
        }
        if (this.Æ.Â()) {
            return this.È <= 0 && this.Çªà¢;
        }
        return this.ŒÏ && this.Çªà¢;
    }
    
    public void Ó() {
        this.à();
        this.ŠÂµà = 0;
        this.È = (int)this.Æ.HorizonCode_Horizon_È();
        this.¥à = (int)this.£á.HorizonCode_Horizon_È();
    }
    
    public void à() {
        this.Çªà¢ = false;
        if (this.Ç != null) {
            this.Ç.Ý(this);
        }
    }
    
    public void Ø() {
        if (this.Âµá€() && this.Ç != null && this.Ç.áˆºÑ¢Õ() == 0) {
            this.Ó();
        }
    }
    
    public ConfigurableEmitter áŒŠÆ() {
        ConfigurableEmitter theCopy = null;
        try {
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ParticleIO.HorizonCode_Horizon_È(bout, this);
            final ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            theCopy = ParticleIO.Â(bin);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È("Slick: ConfigurableEmitter.duplicate(): caught exception " + e.toString());
            return null;
        }
        return theCopy;
    }
    
    public void HorizonCode_Horizon_È(final float pos, final Color col) {
        this.Å.add(new HorizonCode_Horizon_È(pos, col));
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return this.áŒŠà;
    }
    
    @Override
    public boolean ÂµÈ() {
        return this.Ï­Ðƒà;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ParticleSystem system) {
        return (this.Šáƒ == 1 && system.Ý()) || this.Šáƒ == 2;
    }
    
    @Override
    public Image á() {
        return this.É;
    }
    
    @Override
    public void ˆÏ­() {
        this.ŒÏ = true;
    }
    
    @Override
    public void £á() {
        this.ŒÏ = false;
        this.Ó();
    }
    
    public class Âµá€ implements Ó
    {
        private float Â;
        private float Ý;
        
        private Âµá€(final float value) {
            this.Â = value;
        }
        
        @Override
        public float HorizonCode_Horizon_È(final float time) {
            return this.Â;
        }
        
        public void Â(final float value) {
            this.Â = value;
        }
    }
    
    public class Ý implements Ó
    {
        private float Â;
        
        private Ý(final float value) {
            this.Â = value;
        }
        
        @Override
        public float HorizonCode_Horizon_È(final float time) {
            return (float)(Math.random() * this.Â);
        }
        
        public void Â(final float value) {
            this.Â = value;
        }
        
        public float HorizonCode_Horizon_È() {
            return this.Â;
        }
    }
    
    public class Â implements Ó
    {
        private ArrayList Â;
        private boolean Ý;
        private int Ø­áŒŠá;
        private int Âµá€;
        
        public Â(final ArrayList curve, final int min, final int max) {
            this.Â = curve;
            this.Ø­áŒŠá = min;
            this.Âµá€ = max;
            this.Ý = false;
        }
        
        public void HorizonCode_Horizon_È(final ArrayList curve) {
            this.Â = curve;
        }
        
        public ArrayList HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        @Override
        public float HorizonCode_Horizon_È(final float t) {
            Vector2f p0 = this.Â.get(0);
            for (int i = 1; i < this.Â.size(); ++i) {
                final Vector2f p2 = this.Â.get(i);
                if (t >= p0.Â() && t <= p2.Â()) {
                    final float st = (t - p0.Â()) / (p2.Â() - p0.Â());
                    final float r = p0.Ý() + st * (p2.Ý() - p0.Ý());
                    return r;
                }
                p0 = p2;
            }
            return 0.0f;
        }
        
        public boolean Â() {
            return this.Ý;
        }
        
        public void HorizonCode_Horizon_È(final boolean active) {
            this.Ý = active;
        }
        
        public int Ý() {
            return this.Âµá€;
        }
        
        public void HorizonCode_Horizon_È(final int max) {
            this.Âµá€ = max;
        }
        
        public int Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
        
        public void Â(final int min) {
            this.Ø­áŒŠá = min;
        }
    }
    
    public class HorizonCode_Horizon_È
    {
        public float HorizonCode_Horizon_È;
        public Color Â;
        
        public HorizonCode_Horizon_È(final float pos, final Color col) {
            this.HorizonCode_Horizon_È = pos;
            this.Â = col;
        }
    }
    
    public class Ø­áŒŠá
    {
        private float Â;
        private float Ý;
        private boolean Ø­áŒŠá;
        
        private Ø­áŒŠá(final float min, final float max) {
            this.Ø­áŒŠá = false;
            this.Ý = min;
            this.Â = max;
        }
        
        public float HorizonCode_Horizon_È() {
            return (float)(this.Ý + Math.random() * (this.Â - this.Ý));
        }
        
        public boolean Â() {
            return this.Ø­áŒŠá;
        }
        
        public void HorizonCode_Horizon_È(final boolean enabled) {
            this.Ø­áŒŠá = enabled;
        }
        
        public float Ý() {
            return this.Â;
        }
        
        public void HorizonCode_Horizon_È(final float max) {
            this.Â = max;
        }
        
        public float Ø­áŒŠá() {
            return this.Ý;
        }
        
        public void Â(final float min) {
            this.Ý = min;
        }
    }
    
    public interface Ó
    {
        float HorizonCode_Horizon_È(final float p0);
    }
}
