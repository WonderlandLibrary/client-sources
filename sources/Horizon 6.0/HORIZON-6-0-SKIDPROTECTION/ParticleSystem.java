package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

public class ParticleSystem
{
    protected SGL HorizonCode_Horizon_È;
    public static final int Â = 1;
    public static final int Ý = 2;
    private static final int Ø = 100;
    private ArrayList áŒŠÆ;
    protected HashMap Ø­áŒŠá;
    protected int Âµá€;
    protected ArrayList Ó;
    protected Particle à;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private boolean á;
    private float ˆÏ­;
    private float £á;
    private boolean Å;
    private Image £à;
    private boolean µà;
    private String ˆà;
    private Color ¥Æ;
    
    public static void HorizonCode_Horizon_È(final String path) {
        ConfigurableEmitter.HorizonCode_Horizon_È(path);
    }
    
    public ParticleSystem(final Image defaultSprite) {
        this(defaultSprite, 100);
    }
    
    public ParticleSystem(final String defaultSpriteRef) {
        this(defaultSpriteRef, 100);
    }
    
    public void HorizonCode_Horizon_È() {
        for (final HorizonCode_Horizon_È pool : this.Ø­áŒŠá.values()) {
            pool.HorizonCode_Horizon_È(this);
        }
        for (int i = 0; i < this.Ó.size(); ++i) {
            final ParticleEmitter emitter = this.Ó.get(i);
            emitter.£á();
        }
    }
    
    public boolean Â() {
        return this.µà;
    }
    
    public void HorizonCode_Horizon_È(final boolean visible) {
        this.µà = visible;
    }
    
    public void Â(final boolean remove) {
        this.Å = remove;
    }
    
    public void Ý(final boolean usePoints) {
        this.á = usePoints;
    }
    
    public boolean Ý() {
        return this.á;
    }
    
    public ParticleSystem(final String defaultSpriteRef, final int maxParticles) {
        this(defaultSpriteRef, maxParticles, null);
    }
    
    public ParticleSystem(final String defaultSpriteRef, final int maxParticles, final Color mask) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.áŒŠÆ = new ArrayList();
        this.Ø­áŒŠá = new HashMap();
        this.Ó = new ArrayList();
        this.áˆºÑ¢Õ = 2;
        this.Å = true;
        this.µà = true;
        this.Âµá€ = maxParticles;
        this.¥Æ = mask;
        this.Â(defaultSpriteRef);
        this.à = this.HorizonCode_Horizon_È(this);
    }
    
    public ParticleSystem(final Image defaultSprite, final int maxParticles) {
        this.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        this.áŒŠÆ = new ArrayList();
        this.Ø­áŒŠá = new HashMap();
        this.Ó = new ArrayList();
        this.áˆºÑ¢Õ = 2;
        this.Å = true;
        this.µà = true;
        this.Âµá€ = maxParticles;
        this.£à = defaultSprite;
        this.à = this.HorizonCode_Horizon_È(this);
    }
    
    public void Â(final String ref) {
        this.ˆà = ref;
        this.£à = null;
    }
    
    public int Ø­áŒŠá() {
        return this.áˆºÑ¢Õ;
    }
    
    protected Particle HorizonCode_Horizon_È(final ParticleSystem system) {
        return new Particle(system);
    }
    
    public void HorizonCode_Horizon_È(final int mode) {
        this.áˆºÑ¢Õ = mode;
    }
    
    public int Âµá€() {
        return this.Ó.size();
    }
    
    public ParticleEmitter Â(final int index) {
        return this.Ó.get(index);
    }
    
    public void HorizonCode_Horizon_È(final ParticleEmitter emitter) {
        this.Ó.add(emitter);
        final HorizonCode_Horizon_È pool = new HorizonCode_Horizon_È(this, this.Âµá€);
        this.Ø­áŒŠá.put(emitter, pool);
    }
    
    public void Â(final ParticleEmitter emitter) {
        this.Ó.remove(emitter);
        this.Ø­áŒŠá.remove(emitter);
    }
    
    public void Ó() {
        for (int i = 0; i < this.Ó.size(); --i, ++i) {
            this.Â(this.Ó.get(i));
        }
    }
    
    public float à() {
        return this.ˆÏ­;
    }
    
    public float Ø() {
        return this.£á;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.ˆÏ­ = x;
        this.£á = y;
    }
    
    public void áŒŠÆ() {
        this.Â(this.ˆÏ­, this.£á);
    }
    
    public void Â(final float x, final float y) {
        if (this.£à == null && this.ˆà != null) {
            this.á();
        }
        if (!this.µà) {
            return;
        }
        this.HorizonCode_Horizon_È.Â(x, y, 0.0f);
        if (this.áˆºÑ¢Õ == 1) {
            this.HorizonCode_Horizon_È.Ø­áŒŠá(770, 1);
        }
        if (this.Ý()) {
            this.HorizonCode_Horizon_È.Âµá€(2832);
            TextureImpl.£à();
        }
        for (int emitterIdx = 0; emitterIdx < this.Ó.size(); ++emitterIdx) {
            final ParticleEmitter emitter = this.Ó.get(emitterIdx);
            if (emitter.Ø­áŒŠá()) {
                if (emitter.áˆºÑ¢Õ()) {
                    this.HorizonCode_Horizon_È.Ø­áŒŠá(770, 1);
                }
                final HorizonCode_Horizon_È pool = this.Ø­áŒŠá.get(emitter);
                Image image = emitter.á();
                if (image == null) {
                    image = this.£à;
                }
                if (!emitter.ÂµÈ() && !emitter.HorizonCode_Horizon_È(this)) {
                    image.Ó();
                }
                for (int i = 0; i < pool.HorizonCode_Horizon_È.length; ++i) {
                    if (pool.HorizonCode_Horizon_È[i].à()) {
                        pool.HorizonCode_Horizon_È[i].Ø();
                    }
                }
                if (!emitter.ÂµÈ() && !emitter.HorizonCode_Horizon_È(this)) {
                    image.Âµá€();
                }
                if (emitter.áˆºÑ¢Õ()) {
                    this.HorizonCode_Horizon_È.Ø­áŒŠá(770, 771);
                }
            }
        }
        if (this.Ý()) {
            this.HorizonCode_Horizon_È.Ø­áŒŠá(2832);
        }
        if (this.áˆºÑ¢Õ == 1) {
            this.HorizonCode_Horizon_È.Ø­áŒŠá(770, 771);
        }
        Color.Ý.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È.Â(-x, -y, 0.0f);
    }
    
    private void á() {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    if (ParticleSystem.this.¥Æ != null) {
                        ParticleSystem.HorizonCode_Horizon_È(ParticleSystem.this, new Image(ParticleSystem.this.ˆà, ParticleSystem.this.¥Æ));
                    }
                    else {
                        ParticleSystem.HorizonCode_Horizon_È(ParticleSystem.this, new Image(ParticleSystem.this.ˆà));
                    }
                }
                catch (SlickException e) {
                    Log.HorizonCode_Horizon_È(e);
                    ParticleSystem.HorizonCode_Horizon_È(ParticleSystem.this, (String)null);
                }
                return null;
            }
        });
    }
    
    public void Ý(final int delta) {
        if (this.£à == null && this.ˆà != null) {
            this.á();
        }
        this.áŒŠÆ.clear();
        final ArrayList emitters = new ArrayList(this.Ó);
        for (int i = 0; i < emitters.size(); ++i) {
            final ParticleEmitter emitter = emitters.get(i);
            if (emitter.Ø­áŒŠá()) {
                emitter.HorizonCode_Horizon_È(this, delta);
                if (this.Å && emitter.Âµá€()) {
                    this.áŒŠÆ.add(emitter);
                    this.Ø­áŒŠá.remove(emitter);
                }
            }
        }
        this.Ó.removeAll(this.áŒŠÆ);
        this.ÂµÈ = 0;
        if (!this.Ø­áŒŠá.isEmpty()) {
            for (final ParticleEmitter emitter : this.Ø­áŒŠá.keySet()) {
                if (emitter.Ø­áŒŠá()) {
                    final HorizonCode_Horizon_È pool = this.Ø­áŒŠá.get(emitter);
                    for (int j = 0; j < pool.HorizonCode_Horizon_È.length; ++j) {
                        if (pool.HorizonCode_Horizon_È[j].ÂµÈ > 0.0f) {
                            pool.HorizonCode_Horizon_È[j].HorizonCode_Horizon_È(delta);
                            ++this.ÂµÈ;
                        }
                    }
                }
            }
        }
    }
    
    public int áˆºÑ¢Õ() {
        return this.ÂµÈ;
    }
    
    public Particle HorizonCode_Horizon_È(final ParticleEmitter emitter, final float life) {
        final HorizonCode_Horizon_È pool = this.Ø­áŒŠá.get(emitter);
        final ArrayList available = pool.Â;
        if (available.size() > 0) {
            final Particle p = available.remove(available.size() - 1);
            p.HorizonCode_Horizon_È(emitter, life);
            p.HorizonCode_Horizon_È(this.£à);
            return p;
        }
        Log.Â("Ran out of particles (increase the limit)!");
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final Particle particle) {
        if (particle != this.à) {
            final HorizonCode_Horizon_È pool = this.Ø­áŒŠá.get(particle.ÂµÈ());
            pool.Â.add(particle);
        }
    }
    
    public void Ý(final ParticleEmitter emitter) {
        if (!this.Ø­áŒŠá.isEmpty()) {
            for (final HorizonCode_Horizon_È pool : this.Ø­áŒŠá.values()) {
                for (int i = 0; i < pool.HorizonCode_Horizon_È.length; ++i) {
                    if (pool.HorizonCode_Horizon_È[i].à() && pool.HorizonCode_Horizon_È[i].ÂµÈ() == emitter) {
                        pool.HorizonCode_Horizon_È[i].Ý(-1.0f);
                        this.HorizonCode_Horizon_È(pool.HorizonCode_Horizon_È[i]);
                    }
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final ParticleEmitter emitter, final float x, final float y) {
        final HorizonCode_Horizon_È pool = this.Ø­áŒŠá.get(emitter);
        for (int i = 0; i < pool.HorizonCode_Horizon_È.length; ++i) {
            if (pool.HorizonCode_Horizon_È[i].à()) {
                pool.HorizonCode_Horizon_È[i].HorizonCode_Horizon_È(x, y);
            }
        }
    }
    
    public ParticleSystem ÂµÈ() throws SlickException {
        for (int i = 0; i < this.Ó.size(); ++i) {
            if (!(this.Ó.get(i) instanceof ConfigurableEmitter)) {
                throw new SlickException("Only systems contianing configurable emitters can be duplicated");
            }
        }
        ParticleSystem theCopy = null;
        try {
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ParticleIO.HorizonCode_Horizon_È(bout, this);
            final ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            theCopy = ParticleIO.HorizonCode_Horizon_È(bin);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È("Failed to duplicate particle system");
            throw new SlickException("Unable to duplicated particle system", e);
        }
        return theCopy;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ParticleSystem particleSystem, final Image £à) {
        particleSystem.£à = £à;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ParticleSystem particleSystem, final String ˆà) {
        particleSystem.ˆà = ˆà;
    }
    
    private class HorizonCode_Horizon_È
    {
        public Particle[] HorizonCode_Horizon_È;
        public ArrayList Â;
        
        public HorizonCode_Horizon_È(final ParticleSystem system, final int maxParticles) {
            this.HorizonCode_Horizon_È = new Particle[maxParticles];
            this.Â = new ArrayList();
            for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
                this.HorizonCode_Horizon_È[i] = ParticleSystem.this.HorizonCode_Horizon_È(system);
            }
            this.HorizonCode_Horizon_È(system);
        }
        
        public void HorizonCode_Horizon_È(final ParticleSystem system) {
            this.Â.clear();
            for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
                this.Â.add(this.HorizonCode_Horizon_È[i]);
            }
        }
    }
}
