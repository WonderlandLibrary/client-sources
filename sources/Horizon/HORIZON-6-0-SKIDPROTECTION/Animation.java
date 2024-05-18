package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.Sys;
import java.util.ArrayList;

public class Animation implements Renderable
{
    private ArrayList HorizonCode_Horizon_È;
    private int Â;
    private long Ý;
    private boolean Ø­áŒŠá;
    private long Âµá€;
    private float Ó;
    private int à;
    private long Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private int ÂµÈ;
    private boolean á;
    private boolean ˆÏ­;
    private SpriteSheet £á;
    
    public Animation() {
        this(true);
    }
    
    public Animation(final Image[] frames, final int duration) {
        this(frames, duration, true);
    }
    
    public Animation(final Image[] frames, final int[] durations) {
        this(frames, durations, true);
    }
    
    public Animation(final boolean autoUpdate) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = -1;
        this.Ý = 0L;
        this.Ø­áŒŠá = false;
        this.Ó = 1.0f;
        this.à = -2;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = 1;
        this.ˆÏ­ = true;
        this.£á = null;
        this.Â = 0;
        this.áˆºÑ¢Õ = autoUpdate;
    }
    
    public Animation(final Image[] frames, final int duration, final boolean autoUpdate) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = -1;
        this.Ý = 0L;
        this.Ø­áŒŠá = false;
        this.Ó = 1.0f;
        this.à = -2;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = 1;
        this.ˆÏ­ = true;
        this.£á = null;
        for (int i = 0; i < frames.length; ++i) {
            this.HorizonCode_Horizon_È(frames[i], duration);
        }
        this.Â = 0;
        this.áˆºÑ¢Õ = autoUpdate;
    }
    
    public Animation(final Image[] frames, final int[] durations, final boolean autoUpdate) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = -1;
        this.Ý = 0L;
        this.Ø­áŒŠá = false;
        this.Ó = 1.0f;
        this.à = -2;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = 1;
        this.ˆÏ­ = true;
        this.£á = null;
        this.áˆºÑ¢Õ = autoUpdate;
        if (frames.length != durations.length) {
            throw new RuntimeException("There must be one duration per frame");
        }
        for (int i = 0; i < frames.length; ++i) {
            this.HorizonCode_Horizon_È(frames[i], durations[i]);
        }
        this.Â = 0;
    }
    
    public Animation(final SpriteSheet frames, final int duration) {
        this(frames, 0, 0, frames.HorizonCode_Horizon_È() - 1, frames.á() - 1, true, duration, true);
    }
    
    public Animation(final SpriteSheet frames, final int x1, final int y1, final int x2, final int y2, final boolean horizontalScan, final int duration, final boolean autoUpdate) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = -1;
        this.Ý = 0L;
        this.Ø­áŒŠá = false;
        this.Ó = 1.0f;
        this.à = -2;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = 1;
        this.ˆÏ­ = true;
        this.£á = null;
        this.áˆºÑ¢Õ = autoUpdate;
        if (!horizontalScan) {
            for (int x3 = x1; x3 <= x2; ++x3) {
                for (int y3 = y1; y3 <= y2; ++y3) {
                    this.HorizonCode_Horizon_È(frames.Ø­áŒŠá(x3, y3), duration);
                }
            }
        }
        else {
            for (int y4 = y1; y4 <= y2; ++y4) {
                for (int x4 = x1; x4 <= x2; ++x4) {
                    this.HorizonCode_Horizon_È(frames.Ø­áŒŠá(x4, y4), duration);
                }
            }
        }
    }
    
    public Animation(final SpriteSheet ss, final int[] frames, final int[] duration) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = -1;
        this.Ý = 0L;
        this.Ø­áŒŠá = false;
        this.Ó = 1.0f;
        this.à = -2;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = 1;
        this.ˆÏ­ = true;
        this.£á = null;
        this.£á = ss;
        int x = -1;
        int y = -1;
        for (int i = 0; i < frames.length / 2; ++i) {
            x = frames[i * 2];
            y = frames[i * 2 + 1];
            this.HorizonCode_Horizon_È(duration[i], x, y);
        }
    }
    
    public void HorizonCode_Horizon_È(final int duration, final int x, final int y) {
        if (duration == 0) {
            Log.HorizonCode_Horizon_È("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }
        if (this.HorizonCode_Horizon_È.isEmpty()) {
            this.Ý = (int)(duration / this.Ó);
        }
        this.HorizonCode_Horizon_È.add(new HorizonCode_Horizon_È(duration, x, y));
        this.Â = 0;
    }
    
    public void HorizonCode_Horizon_È(final boolean auto) {
        this.áˆºÑ¢Õ = auto;
    }
    
    public void Â(final boolean pingPong) {
        this.á = pingPong;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final float spd) {
        if (spd > 0.0f) {
            this.Ý = (long)(this.Ý * this.Ó / spd);
            this.Ó = spd;
        }
    }
    
    public float Â() {
        return this.Ó;
    }
    
    public void Ý() {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        this.Âµá€ = this.Ý;
        this.Ø­áŒŠá = true;
    }
    
    public void Ø­áŒŠá() {
        if (!this.Ø­áŒŠá) {
            return;
        }
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        this.Ø­áŒŠá = false;
        this.Ý = this.Âµá€;
    }
    
    public void Âµá€() {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        this.Ø­áŒŠá = false;
        this.Â = 0;
        this.Ý = (int)(this.HorizonCode_Horizon_È.get(0).Â / this.Ó);
        this.áŒŠÆ = true;
        this.Ø = 0L;
    }
    
    public void HorizonCode_Horizon_È(final Image frame, final int duration) {
        if (duration == 0) {
            Log.HorizonCode_Horizon_È("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }
        if (this.HorizonCode_Horizon_È.isEmpty()) {
            this.Ý = (int)(duration / this.Ó);
        }
        this.HorizonCode_Horizon_È.add(new HorizonCode_Horizon_È(frame, duration));
        this.Â = 0;
    }
    
    public void Ó() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È(x, y, this.à(), this.Ø());
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final Color filter) {
        this.HorizonCode_Horizon_È(x, y, this.à(), this.Ø(), filter);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height) {
        this.HorizonCode_Horizon_È(x, y, width, height, Color.Ý);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height, final Color col) {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        if (this.áˆºÑ¢Õ) {
            final long now = this.Å();
            long delta = now - this.Ø;
            if (this.áŒŠÆ) {
                delta = 0L;
                this.áŒŠÆ = false;
            }
            this.Ø = now;
            this.Â(delta);
        }
        final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(this.Â);
        frame.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, y, width, height, col);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y) {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        if (this.áˆºÑ¢Õ) {
            final long now = this.Å();
            long delta = now - this.Ø;
            if (this.áŒŠÆ) {
                delta = 0L;
                this.áŒŠÆ = false;
            }
            this.Ø = now;
            this.Â(delta);
        }
        final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(this.Â);
        this.£á.Â(x, y, frame.Ý, frame.Ø­áŒŠá);
    }
    
    public int à() {
        return this.HorizonCode_Horizon_È.get(this.Â).HorizonCode_Horizon_È.ŒÏ();
    }
    
    public int Ø() {
        return this.HorizonCode_Horizon_È.get(this.Â).HorizonCode_Horizon_È.Çªà¢();
    }
    
    public void Â(final float x, final float y, final float width, final float height) {
        this.Â(x, y, width, height, Color.Ý);
    }
    
    public void Â(final float x, final float y, final float width, final float height, final Color col) {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        if (this.áˆºÑ¢Õ) {
            final long now = this.Å();
            long delta = now - this.Ø;
            if (this.áŒŠÆ) {
                delta = 0L;
                this.áŒŠÆ = false;
            }
            this.Ø = now;
            this.Â(delta);
        }
        final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(this.Â);
        frame.HorizonCode_Horizon_È.Â(x, y, width, height, col);
    }
    
    public void áŒŠÆ() {
        if (this.áˆºÑ¢Õ) {
            final long now = this.Å();
            long delta = now - this.Ø;
            if (this.áŒŠÆ) {
                delta = 0L;
                this.áŒŠÆ = false;
            }
            this.Ø = now;
            this.Â(delta);
        }
    }
    
    public void HorizonCode_Horizon_È(final long delta) {
        this.Â(delta);
    }
    
    public int áˆºÑ¢Õ() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final int index) {
        this.Â = index;
    }
    
    public Image Â(final int index) {
        final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(index);
        return frame.HorizonCode_Horizon_È;
    }
    
    public int ÂµÈ() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public Image á() {
        final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(this.Â);
        return frame.HorizonCode_Horizon_È;
    }
    
    private void Â(final long delta) {
        if (this.Ø­áŒŠá) {
            return;
        }
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return;
        }
        this.Ý -= delta;
        while (this.Ý < 0L && !this.Ø­áŒŠá) {
            if (this.Â == this.à) {
                this.Ø­áŒŠá = true;
                break;
            }
            if (this.Â == this.HorizonCode_Horizon_È.size() - 1 && !this.ˆÏ­ && !this.á) {
                this.Ø­áŒŠá = true;
                break;
            }
            this.Â = (this.Â + this.ÂµÈ) % this.HorizonCode_Horizon_È.size();
            if (this.á) {
                if (this.Â <= 0) {
                    this.Â = 0;
                    this.ÂµÈ = 1;
                    if (!this.ˆÏ­) {
                        this.Ø­áŒŠá = true;
                        break;
                    }
                }
                else if (this.Â >= this.HorizonCode_Horizon_È.size() - 1) {
                    this.Â = this.HorizonCode_Horizon_È.size() - 1;
                    this.ÂµÈ = -1;
                }
            }
            final int realDuration = (int)(this.HorizonCode_Horizon_È.get(this.Â).Â / this.Ó);
            this.Ý += realDuration;
        }
    }
    
    public void Ý(final boolean loop) {
        this.ˆÏ­ = loop;
    }
    
    private long Å() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public void Ý(final int frameIndex) {
        this.à = frameIndex;
    }
    
    public int Ø­áŒŠá(final int index) {
        return this.HorizonCode_Horizon_È.get(index).Â;
    }
    
    public void Â(final int index, final int duration) {
        this.HorizonCode_Horizon_È.get(index).Â = duration;
    }
    
    public int[] ˆÏ­() {
        final int[] durations = new int[this.HorizonCode_Horizon_È.size()];
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            durations[i] = this.Ø­áŒŠá(i);
        }
        return durations;
    }
    
    @Override
    public String toString() {
        String res = "[Animation (" + this.HorizonCode_Horizon_È.size() + ") ";
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            final HorizonCode_Horizon_È frame = this.HorizonCode_Horizon_È.get(i);
            res = String.valueOf(res) + frame.Â + ",";
        }
        res = String.valueOf(res) + "]";
        return res;
    }
    
    public Animation £á() {
        final Animation copy = new Animation();
        copy.£á = this.£á;
        copy.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        copy.áˆºÑ¢Õ = this.áˆºÑ¢Õ;
        copy.ÂµÈ = this.ÂµÈ;
        copy.ˆÏ­ = this.ˆÏ­;
        copy.á = this.á;
        copy.Ó = this.Ó;
        return copy;
    }
    
    private class HorizonCode_Horizon_È
    {
        public Image HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        
        public HorizonCode_Horizon_È(final Image image, final int duration) {
            this.Ý = -1;
            this.Ø­áŒŠá = -1;
            this.HorizonCode_Horizon_È = image;
            this.Â = duration;
        }
        
        public HorizonCode_Horizon_È(final int duration, final int x, final int y) {
            this.Ý = -1;
            this.Ø­áŒŠá = -1;
            this.HorizonCode_Horizon_È = Animation.this.£á.HorizonCode_Horizon_È(x, y);
            this.Â = duration;
            this.Ý = x;
            this.Ø­áŒŠá = y;
        }
    }
}
