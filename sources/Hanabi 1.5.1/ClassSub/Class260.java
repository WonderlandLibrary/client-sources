package ClassSub;

import java.util.*;
import org.lwjgl.*;

public class Class260 implements Class250
{
    private ArrayList frames;
    private int currentFrame;
    private long nextChange;
    private boolean stopped;
    private long timeLeft;
    private float speed;
    private int stopAt;
    private long lastUpdate;
    private boolean firstUpdate;
    private boolean autoUpdate;
    private int direction;
    private boolean pingPong;
    private boolean loop;
    private Class265 spriteSheet;
    
    
    public Class260() {
        this(true);
    }
    
    public Class260(final Class220[] array, final int n) {
        this(array, n, true);
    }
    
    public Class260(final Class220[] array, final int[] array2) {
        this(array, array2, true);
    }
    
    public Class260(final boolean autoUpdate) {
        this.frames = new ArrayList();
        this.currentFrame = -1;
        this.nextChange = 0L;
        this.stopped = false;
        this.speed = 1.0f;
        this.stopAt = -2;
        this.firstUpdate = true;
        this.autoUpdate = true;
        this.direction = 1;
        this.loop = true;
        this.spriteSheet = null;
        this.currentFrame = 0;
        this.autoUpdate = autoUpdate;
    }
    
    public Class260(final Class220[] array, final int n, final boolean autoUpdate) {
        this.frames = new ArrayList();
        this.currentFrame = -1;
        this.nextChange = 0L;
        this.stopped = false;
        this.speed = 1.0f;
        this.stopAt = -2;
        this.firstUpdate = true;
        this.autoUpdate = true;
        this.direction = 1;
        this.loop = true;
        this.spriteSheet = null;
        for (int i = 0; i < array.length; ++i) {
            this.addFrame(array[i], n);
        }
        this.currentFrame = 0;
        this.autoUpdate = autoUpdate;
    }
    
    public Class260(final Class220[] array, final int[] array2, final boolean autoUpdate) {
        this.frames = new ArrayList();
        this.currentFrame = -1;
        this.nextChange = 0L;
        this.stopped = false;
        this.speed = 1.0f;
        this.stopAt = -2;
        this.firstUpdate = true;
        this.autoUpdate = true;
        this.direction = 1;
        this.loop = true;
        this.spriteSheet = null;
        this.autoUpdate = autoUpdate;
        if (array.length != array2.length) {
            throw new RuntimeException("There must be one duration per frame");
        }
        for (int i = 0; i < array.length; ++i) {
            this.addFrame(array[i], array2[i]);
        }
        this.currentFrame = 0;
    }
    
    public Class260(final Class265 class265, final int n) {
        this(class265, 0, 0, class265.getHorizontalCount() - 1, class265.getVerticalCount() - 1, true, n, true);
    }
    
    public Class260(final Class265 class265, final int n, final int n2, final int n3, final int n4, final boolean b, final int n5, final boolean autoUpdate) {
        this.frames = new ArrayList();
        this.currentFrame = -1;
        this.nextChange = 0L;
        this.stopped = false;
        this.speed = 1.0f;
        this.stopAt = -2;
        this.firstUpdate = true;
        this.autoUpdate = true;
        this.direction = 1;
        this.loop = true;
        this.spriteSheet = null;
        this.autoUpdate = autoUpdate;
        if (!b) {
            for (int i = n; i <= n3; ++i) {
                for (int j = n2; j <= n4; ++j) {
                    this.addFrame(class265.getSprite(i, j), n5);
                }
            }
        }
        else {
            for (int k = n2; k <= n4; ++k) {
                for (int l = n; l <= n3; ++l) {
                    this.addFrame(class265.getSprite(l, k), n5);
                }
            }
        }
    }
    
    public Class260(final Class265 spriteSheet, final int[] array, final int[] array2) {
        this.frames = new ArrayList();
        this.currentFrame = -1;
        this.nextChange = 0L;
        this.stopped = false;
        this.speed = 1.0f;
        this.stopAt = -2;
        this.firstUpdate = true;
        this.autoUpdate = true;
        this.direction = 1;
        this.loop = true;
        this.spriteSheet = null;
        this.spriteSheet = spriteSheet;
        for (int i = 0; i < array.length / 2; ++i) {
            this.addFrame(array2[i], array[i * 2], array[i * 2 + 1]);
        }
    }
    
    public void addFrame(final int n, final int n2, final int n3) {
        if (n == 0) {
            Class301.error("Invalid duration: " + n);
            throw new RuntimeException("Invalid duration: " + n);
        }
        if (this.frames.isEmpty()) {
            this.nextChange = (int)(n / this.speed);
        }
        this.frames.add(new Class319(n, n2, n3));
        this.currentFrame = 0;
    }
    
    public void setAutoUpdate(final boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
    
    public void setPingPong(final boolean pingPong) {
        this.pingPong = pingPong;
    }
    
    public boolean isStopped() {
        return this.stopped;
    }
    
    public void setSpeed(final float speed) {
        if (speed > 0.0f) {
            this.nextChange = (long)(this.nextChange * this.speed / speed);
            this.speed = speed;
        }
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public void stop() {
        if (this.frames.size() == 0) {
            return;
        }
        this.timeLeft = this.nextChange;
        this.stopped = true;
    }
    
    public void start() {
        if (!this.stopped) {
            return;
        }
        if (this.frames.size() == 0) {
            return;
        }
        this.stopped = false;
        this.nextChange = this.timeLeft;
    }
    
    public void restart() {
        if (this.frames.size() == 0) {
            return;
        }
        this.stopped = false;
        this.currentFrame = 0;
        this.nextChange = (int)(this.frames.get(0).duration / this.speed);
        this.firstUpdate = true;
        this.lastUpdate = 0L;
    }
    
    public void addFrame(final Class220 class220, final int n) {
        if (n == 0) {
            Class301.error("Invalid duration: " + n);
            throw new RuntimeException("Invalid duration: " + n);
        }
        if (this.frames.isEmpty()) {
            this.nextChange = (int)(n / this.speed);
        }
        this.frames.add(new Class319(class220, n));
        this.currentFrame = 0;
    }
    
    public void draw() {
        this.draw(0.0f, 0.0f);
    }
    
    @Override
    public void draw(final float n, final float n2) {
        this.draw(n, n2, this.getWidth(), this.getHeight());
    }
    
    public void draw(final float n, final float n2, final Class26 class26) {
        this.draw(n, n2, this.getWidth(), this.getHeight(), class26);
    }
    
    public void draw(final float n, final float n2, final float n3, final float n4) {
        this.draw(n, n2, n3, n4, Class26.white);
    }
    
    public void draw(final float n, final float n2, final float n3, final float n4, final Class26 class26) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            final long time = this.getTime();
            long n5 = time - this.lastUpdate;
            if (this.firstUpdate) {
                n5 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = time;
            this.nextFrame(n5);
        }
        this.frames.get(this.currentFrame).image.draw(n, n2, n3, n4, class26);
    }
    
    public void renderInUse(final int n, final int n2) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            final long time = this.getTime();
            long n3 = time - this.lastUpdate;
            if (this.firstUpdate) {
                n3 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = time;
            this.nextFrame(n3);
        }
        final Class319 class319 = this.frames.get(this.currentFrame);
        this.spriteSheet.renderInUse(n, n2, class319.x, class319.y);
    }
    
    public int getWidth() {
        return this.frames.get(this.currentFrame).image.getWidth();
    }
    
    public int getHeight() {
        return this.frames.get(this.currentFrame).image.getHeight();
    }
    
    public void drawFlash(final float n, final float n2, final float n3, final float n4) {
        this.drawFlash(n, n2, n3, n4, Class26.white);
    }
    
    public void drawFlash(final float n, final float n2, final float n3, final float n4, final Class26 class26) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            final long time = this.getTime();
            long n5 = time - this.lastUpdate;
            if (this.firstUpdate) {
                n5 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = time;
            this.nextFrame(n5);
        }
        this.frames.get(this.currentFrame).image.drawFlash(n, n2, n3, n4, class26);
    }
    
    public void updateNoDraw() {
        if (this.autoUpdate) {
            final long time = this.getTime();
            long n = time - this.lastUpdate;
            if (this.firstUpdate) {
                n = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = time;
            this.nextFrame(n);
        }
    }
    
    public void update(final long n) {
        this.nextFrame(n);
    }
    
    public int getFrame() {
        return this.currentFrame;
    }
    
    public void setCurrentFrame(final int currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public Class220 getImage(final int n) {
        return this.frames.get(n).image;
    }
    
    public int getFrameCount() {
        return this.frames.size();
    }
    
    public Class220 getCurrentFrame() {
        return this.frames.get(this.currentFrame).image;
    }
    
    private void nextFrame(final long n) {
        if (this.stopped) {
            return;
        }
        if (this.frames.size() == 0) {
            return;
        }
        this.nextChange -= n;
        while (this.nextChange < 0L && !this.stopped) {
            if (this.currentFrame == this.stopAt) {
                this.stopped = true;
                break;
            }
            if (this.currentFrame == this.frames.size() - 1 && !this.loop && !this.pingPong) {
                this.stopped = true;
                break;
            }
            this.currentFrame = (this.currentFrame + this.direction) % this.frames.size();
            if (this.pingPong) {
                if (this.currentFrame <= 0) {
                    this.currentFrame = 0;
                    this.direction = 1;
                    if (!this.loop) {
                        this.stopped = true;
                        break;
                    }
                }
                else if (this.currentFrame >= this.frames.size() - 1) {
                    this.currentFrame = this.frames.size() - 1;
                    this.direction = -1;
                }
            }
            this.nextChange += (int)(this.frames.get(this.currentFrame).duration / this.speed);
        }
    }
    
    public void setLooping(final boolean loop) {
        this.loop = loop;
    }
    
    private long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public void stopAt(final int stopAt) {
        this.stopAt = stopAt;
    }
    
    public int getDuration(final int n) {
        return this.frames.get(n).duration;
    }
    
    public void setDuration(final int n, final int duration) {
        this.frames.get(n).duration = duration;
    }
    
    public int[] getDurations() {
        final int[] array = new int[this.frames.size()];
        for (int i = 0; i < this.frames.size(); ++i) {
            array[i] = this.getDuration(i);
        }
        return array;
    }
    
    @Override
    public String toString() {
        String s = "[Animation (" + this.frames.size() + ") ";
        for (int i = 0; i < this.frames.size(); ++i) {
            s = s + ((Class319)this.frames.get(i)).duration + ",";
        }
        return s + "]";
    }
    
    public Class260 copy() {
        final Class260 class260 = new Class260();
        class260.spriteSheet = this.spriteSheet;
        class260.frames = this.frames;
        class260.autoUpdate = this.autoUpdate;
        class260.direction = this.direction;
        class260.loop = this.loop;
        class260.pingPong = this.pingPong;
        class260.speed = this.speed;
        return class260;
    }
    
    static Class265 access$000(final Class260 class260) {
        return class260.spriteSheet;
    }
    
    private class Class319
    {
        public Class220 image;
        public int duration;
        public int x;
        public int y;
        final Class260 this$0;
        
        
        public Class319(final Class260 this$0, final Class220 image, final int duration) {
            this.this$0 = this$0;
            this.x = -1;
            this.y = -1;
            this.image = image;
            this.duration = duration;
        }
        
        public Class319(final Class260 this$0, final int duration, final int x, final int y) {
            this.this$0 = this$0;
            this.x = -1;
            this.y = -1;
            this.image = Class260.access$000(this$0).getSubImage(x, y);
            this.duration = duration;
            this.x = x;
            this.y = y;
        }
    }
}
