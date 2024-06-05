package ClassSub;

import java.util.*;
import org.lwjgl.*;
import java.nio.*;

public class Class225
{
    protected static Class311 GL;
    private static Class127 LSR;
    public static int MODE_NORMAL;
    public static int MODE_ALPHA_MAP;
    public static int MODE_ALPHA_BLEND;
    public static int MODE_COLOR_MULTIPLY;
    public static int MODE_ADD;
    public static int MODE_SCREEN;
    private static final int DEFAULT_SEGMENTS = 50;
    protected static Class225 currentGraphics;
    protected static Class297 DEFAULT_FONT;
    private float sx;
    private float sy;
    private Class297 font;
    private Class26 currentColor;
    protected int screenWidth;
    protected int screenHeight;
    private boolean pushed;
    private Class324 clip;
    private DoubleBuffer worldClip;
    private ByteBuffer readBuffer;
    private boolean antialias;
    private Class324 worldClipRecord;
    private int currentDrawingMode;
    private float lineWidth;
    private ArrayList stack;
    private int stackIndex;
    
    
    public static void setCurrent(final Class225 currentGraphics) {
        if (Class225.currentGraphics != currentGraphics) {
            if (Class225.currentGraphics != null) {
                Class225.currentGraphics.disable();
            }
            (Class225.currentGraphics = currentGraphics).enable();
        }
    }
    
    public Class225() {
        this.sx = 1.0f;
        this.sy = 1.0f;
        this.currentColor = Class26.white;
        this.worldClip = BufferUtils.createDoubleBuffer(4);
        this.readBuffer = BufferUtils.createByteBuffer(4);
        this.currentDrawingMode = Class225.MODE_NORMAL;
        this.lineWidth = 1.0f;
        this.stack = new ArrayList();
    }
    
    public Class225(final int screenWidth, final int screenHeight) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokespecial   java/lang/Object.<init>:()V
        //     8: aload_0        
        //     9: fconst_1       
        //    10: putfield        ClassSub/Class225.sx:F
        //    13: aload_0        
        //    14: fconst_1       
        //    15: putfield        ClassSub/Class225.sy:F
        //    18: aload_0        
        //    19: getstatic       ClassSub/Class26.white:LClassSub/Class26;
        //    22: putfield        ClassSub/Class225.currentColor:LClassSub/Class26;
        //    25: aload_0        
        //    26: ldc             4
        //    28: invokestatic    org/lwjgl/BufferUtils.createDoubleBuffer:(I)Ljava/nio/DoubleBuffer;
        //    31: putfield        ClassSub/Class225.worldClip:Ljava/nio/DoubleBuffer;
        //    34: aload_0        
        //    35: ldc             4
        //    37: invokestatic    org/lwjgl/BufferUtils.createByteBuffer:(I)Ljava/nio/ByteBuffer;
        //    40: putfield        ClassSub/Class225.readBuffer:Ljava/nio/ByteBuffer;
        //    43: aload_0        
        //    44: getstatic       ClassSub/Class225.MODE_NORMAL:I
        //    47: putfield        ClassSub/Class225.currentDrawingMode:I
        //    50: aload_0        
        //    51: fconst_1       
        //    52: putfield        ClassSub/Class225.lineWidth:F
        //    55: aload_0        
        //    56: new             Ljava/util/ArrayList;
        //    59: dup            
        //    60: invokespecial   java/util/ArrayList.<init>:()V
        //    63: putfield        ClassSub/Class225.stack:Ljava/util/ArrayList;
        //    66: getstatic       ClassSub/Class225.DEFAULT_FONT:LClassSub/Class297;
        //    69: ifnonnull       84
        //    72: new             LClassSub/Class221;
        //    75: dup            
        //    76: aload_0        
        //    77: invokespecial   ClassSub/Class221.<init>:(LClassSub/Class225;)V
        //    80: invokestatic    java/security/AccessController.doPrivileged:(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
        //    83: pop            
        //    84: aload_0        
        //    85: getstatic       ClassSub/Class225.DEFAULT_FONT:LClassSub/Class297;
        //    88: putfield        ClassSub/Class225.font:LClassSub/Class297;
        //    91: aload_0        
        //    92: iload_1        
        //    93: putfield        ClassSub/Class225.screenWidth:I
        //    96: aload_0        
        //    97: iload_2        
        //    98: putfield        ClassSub/Class225.screenHeight:I
        //   101: return         
        //   102: nop            
        //   103: nop            
        //   104: nop            
        //   105: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void setDimensions(final int screenWidth, final int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public void setDrawMode(final int currentDrawingMode) {
        this.predraw();
        this.currentDrawingMode = currentDrawingMode;
        if (this.currentDrawingMode == Class225.MODE_NORMAL) {
            Class225.GL.glEnable(3042);
            Class225.GL.glColorMask(true, true, true, true);
            Class225.GL.glBlendFunc(770, 771);
        }
        if (this.currentDrawingMode == Class225.MODE_ALPHA_MAP) {
            Class225.GL.glDisable(3042);
            Class225.GL.glColorMask(false, false, false, true);
        }
        if (this.currentDrawingMode == Class225.MODE_ALPHA_BLEND) {
            Class225.GL.glEnable(3042);
            Class225.GL.glColorMask(true, true, true, false);
            Class225.GL.glBlendFunc(772, 773);
        }
        if (this.currentDrawingMode == Class225.MODE_COLOR_MULTIPLY) {
            Class225.GL.glEnable(3042);
            Class225.GL.glColorMask(true, true, true, true);
            Class225.GL.glBlendFunc(769, 768);
        }
        if (this.currentDrawingMode == Class225.MODE_ADD) {
            Class225.GL.glEnable(3042);
            Class225.GL.glColorMask(true, true, true, true);
            Class225.GL.glBlendFunc(1, 1);
        }
        if (this.currentDrawingMode == Class225.MODE_SCREEN) {
            Class225.GL.glEnable(3042);
            Class225.GL.glColorMask(true, true, true, true);
            Class225.GL.glBlendFunc(1, 769);
        }
        this.postdraw();
    }
    
    public void clearAlphaMap() {
        this.pushTransform();
        Class225.GL.glLoadIdentity();
        final int currentDrawingMode = this.currentDrawingMode;
        this.setDrawMode(Class225.MODE_ALPHA_MAP);
        this.setColor(new Class26(0, 0, 0, 0));
        this.fillRect(0.0f, 0.0f, this.screenWidth, this.screenHeight);
        this.setColor(this.currentColor);
        this.setDrawMode(currentDrawingMode);
        this.popTransform();
    }
    
    private void predraw() {
        setCurrent(this);
    }
    
    private void postdraw() {
    }
    
    protected void enable() {
    }
    
    public void flush() {
        if (Class225.currentGraphics == this) {
            Class225.currentGraphics.disable();
            Class225.currentGraphics = null;
        }
    }
    
    protected void disable() {
    }
    
    public Class297 getFont() {
        return this.font;
    }
    
    public void setBackground(final Class26 class26) {
        this.predraw();
        Class225.GL.glClearColor(class26.r, class26.g, class26.b, class26.a);
        this.postdraw();
    }
    
    public Class26 getBackground() {
        this.predraw();
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        Class225.GL.glGetFloat(3106, floatBuffer);
        this.postdraw();
        return new Class26(floatBuffer);
    }
    
    public void clear() {
        this.predraw();
        Class225.GL.glClear(16384);
        this.postdraw();
    }
    
    public void resetTransform() {
        this.sx = 1.0f;
        this.sy = 1.0f;
        if (this.pushed) {
            this.predraw();
            Class225.GL.glPopMatrix();
            this.pushed = false;
            this.postdraw();
        }
    }
    
    private void checkPush() {
        if (!this.pushed) {
            this.predraw();
            Class225.GL.glPushMatrix();
            this.pushed = true;
            this.postdraw();
        }
    }
    
    public void scale(final float n, final float n2) {
        this.sx *= n;
        this.sy *= n2;
        this.checkPush();
        this.predraw();
        Class225.GL.glScalef(n, n2, 1.0f);
        this.postdraw();
    }
    
    public void rotate(final float n, final float n2, final float n3) {
        this.checkPush();
        this.predraw();
        this.translate(n, n2);
        Class225.GL.glRotatef(n3, 0.0f, 0.0f, 1.0f);
        this.translate(-n, -n2);
        this.postdraw();
    }
    
    public void translate(final float n, final float n2) {
        this.checkPush();
        this.predraw();
        Class225.GL.glTranslatef(n, n2, 0.0f);
        this.postdraw();
    }
    
    public void setFont(final Class297 font) {
        this.font = font;
    }
    
    public void resetFont() {
        this.font = Class225.DEFAULT_FONT;
    }
    
    public void setColor(final Class26 class26) {
        if (class26 == null) {
            return;
        }
        this.currentColor = new Class26(class26);
        this.predraw();
        this.currentColor.bind();
        this.postdraw();
    }
    
    public Class26 getColor() {
        return new Class26(this.currentColor);
    }
    
    public void drawLine(float n, float n2, float n3, float n4) {
        final float n5 = this.lineWidth - 1.0f;
        if (Class225.LSR.applyGLLineFixes()) {
            if (n == n3) {
                if (n2 > n4) {
                    final float n6 = n4;
                    n4 = n2;
                    n2 = n6;
                }
                final float n7 = 1.0f / this.sy;
                final float n8 = n5 / this.sy;
                this.fillRect(n - n8 / 2.0f, n2 - n8 / 2.0f, n8 + n7, n4 - n2 + n8 + n7);
                return;
            }
            if (n2 == n4) {
                if (n > n3) {
                    final float n9 = n3;
                    n3 = n;
                    n = n9;
                }
                final float n10 = 1.0f / this.sx;
                final float n11 = n5 / this.sx;
                this.fillRect(n - n11 / 2.0f, n2 - n11 / 2.0f, n3 - n + n11 + n10, n11 + n10);
                return;
            }
        }
        this.predraw();
        this.currentColor.bind();
        Class237.bindNone();
        Class225.LSR.start();
        Class225.LSR.vertex(n, n2);
        Class225.LSR.vertex(n3, n4);
        Class225.LSR.end();
        this.postdraw();
    }
    
    public void draw(final Class186 class186, final Class173 class187) {
        this.predraw();
        Class237.bindNone();
        Class236.draw(class186, class187);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void fill(final Class186 class186, final Class173 class187) {
        this.predraw();
        Class237.bindNone();
        Class236.fill(class186, class187);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void draw(final Class186 class186) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        Class236.draw(class186);
        this.postdraw();
    }
    
    public void fill(final Class186 class186) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        Class236.fill(class186);
        this.postdraw();
    }
    
    public void texture(final Class186 class186, final Class220 class187) {
        this.texture(class186, class187, 0.01f, 0.01f, false);
    }
    
    public void texture(final Class186 class186, final Class220 class187, final Class173 class188) {
        this.texture(class186, class187, 0.01f, 0.01f, class188);
    }
    
    public void texture(final Class186 class186, final Class220 class187, final boolean b) {
        if (b) {
            this.texture(class186, class187, 1.0f, 1.0f, true);
        }
        else {
            this.texture(class186, class187, 0.01f, 0.01f, false);
        }
    }
    
    public void texture(final Class186 class186, final Class220 class187, final float n, final float n2) {
        this.texture(class186, class187, n, n2, false);
    }
    
    public void texture(final Class186 class186, final Class220 class187, final float n, final float n2, final boolean b) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        if (b) {
            Class236.textureFit(class186, class187, n, n2);
        }
        else {
            Class236.texture(class186, class187, n, n2);
        }
        this.postdraw();
    }
    
    public void texture(final Class186 class186, final Class220 class187, final float n, final float n2, final Class173 class188) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        Class236.texture(class186, class187, n, n2, class188);
        this.postdraw();
    }
    
    public void drawRect(final float n, final float n2, final float n3, final float n4) {
        this.getLineWidth();
        this.drawLine(n, n2, n + n3, n2);
        this.drawLine(n + n3, n2, n + n3, n2 + n4);
        this.drawLine(n + n3, n2 + n4, n, n2 + n4);
        this.drawLine(n, n2 + n4, n, n2);
    }
    
    public void clearClip() {
        this.clip = null;
        this.predraw();
        Class225.GL.glDisable(3089);
        this.postdraw();
    }
    
    public void setWorldClip(final float n, final float n2, final float n3, final float n4) {
        this.predraw();
        this.worldClipRecord = new Class324(n, n2, n3, n4);
        Class225.GL.glEnable(12288);
        this.worldClip.put(1.0).put(0.0).put(0.0).put(-n).flip();
        Class225.GL.glClipPlane(12288, this.worldClip);
        Class225.GL.glEnable(12289);
        this.worldClip.put(-1.0).put(0.0).put(0.0).put(n + n3).flip();
        Class225.GL.glClipPlane(12289, this.worldClip);
        Class225.GL.glEnable(12290);
        this.worldClip.put(0.0).put(1.0).put(0.0).put(-n2).flip();
        Class225.GL.glClipPlane(12290, this.worldClip);
        Class225.GL.glEnable(12291);
        this.worldClip.put(0.0).put(-1.0).put(0.0).put(n2 + n4).flip();
        Class225.GL.glClipPlane(12291, this.worldClip);
        this.postdraw();
    }
    
    public void clearWorldClip() {
        this.predraw();
        this.worldClipRecord = null;
        Class225.GL.glDisable(12288);
        Class225.GL.glDisable(12289);
        Class225.GL.glDisable(12290);
        Class225.GL.glDisable(12291);
        this.postdraw();
    }
    
    public void setWorldClip(final Class324 class324) {
        if (class324 == null) {
            this.clearWorldClip();
        }
        else {
            this.setWorldClip(class324.getX(), class324.getY(), class324.getWidth(), class324.getHeight());
        }
    }
    
    public Class324 getWorldClip() {
        return this.worldClipRecord;
    }
    
    public void setClip(final int n, final int n2, final int n3, final int n4) {
        this.predraw();
        if (this.clip == null) {
            Class225.GL.glEnable(3089);
            this.clip = new Class324(n, n2, n3, n4);
        }
        else {
            this.clip.setBounds(n, n2, n3, n4);
        }
        Class225.GL.glScissor(n, this.screenHeight - n2 - n4, n3, n4);
        this.postdraw();
    }
    
    public void setClip(final Class324 class324) {
        if (class324 == null) {
            this.clearClip();
            return;
        }
        this.setClip((int)class324.getX(), (int)class324.getY(), (int)class324.getWidth(), (int)class324.getHeight());
    }
    
    public Class324 getClip() {
        return this.clip;
    }
    
    public void fillRect(final float n, final float n2, final float n3, final float n4, final Class220 class220, final float n5, final float n6) {
        final int n7 = (int)Math.ceil(n3 / class220.getWidth()) + 2;
        final int n8 = (int)Math.ceil(n4 / class220.getHeight()) + 2;
        final Class324 worldClip = this.getWorldClip();
        this.setWorldClip(n, n2, n3, n4);
        this.predraw();
        for (int i = 0; i < n7; ++i) {
            for (int j = 0; j < n8; ++j) {
                class220.draw(i * class220.getWidth() + n - n5, j * class220.getHeight() + n2 - n6);
            }
        }
        this.postdraw();
        this.setWorldClip(worldClip);
    }
    
    public void fillRect(final float n, final float n2, final float n3, final float n4) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        Class225.GL.glBegin(7);
        Class225.GL.glVertex2f(n, n2);
        Class225.GL.glVertex2f(n + n3, n2);
        Class225.GL.glVertex2f(n + n3, n2 + n4);
        Class225.GL.glVertex2f(n, n2 + n4);
        Class225.GL.glEnd();
        this.postdraw();
    }
    
    public void drawOval(final float n, final float n2, final float n3, final float n4) {
        this.drawOval(n, n2, n3, n4, 50);
    }
    
    public void drawOval(final float n, final float n2, final float n3, final float n4, final int n5) {
        this.drawArc(n, n2, n3, n4, n5, 0.0f, 360.0f);
    }
    
    public void drawArc(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.drawArc(n, n2, n3, n4, 50, n5, n6);
    }
    
    public void drawArc(final float n, final float n2, final float n3, final float n4, final int n5, final float n6, float n7) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        while (n7 < n6) {
            n7 += 360.0f;
        }
        final float n8 = n + n3 / 2.0f;
        final float n9 = n2 + n4 / 2.0f;
        Class225.LSR.start();
        for (int n10 = 360 / n5, i = (int)n6; i < (int)(n7 + n10); i += n10) {
            float n11 = i;
            if (n11 > n7) {
                n11 = n7;
            }
            Class225.LSR.vertex((float)(n8 + Class161.cos(Math.toRadians(n11)) * n3 / 2.0), (float)(n9 + Class161.sin(Math.toRadians(n11)) * n4 / 2.0));
        }
        Class225.LSR.end();
        this.postdraw();
    }
    
    public void fillOval(final float n, final float n2, final float n3, final float n4) {
        this.fillOval(n, n2, n3, n4, 50);
    }
    
    public void fillOval(final float n, final float n2, final float n3, final float n4, final int n5) {
        this.fillArc(n, n2, n3, n4, n5, 0.0f, 360.0f);
    }
    
    public void fillArc(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.fillArc(n, n2, n3, n4, 50, n5, n6);
    }
    
    public void fillArc(final float n, final float n2, final float n3, final float n4, final int n5, final float n6, float n7) {
        this.predraw();
        Class237.bindNone();
        this.currentColor.bind();
        while (n7 < n6) {
            n7 += 360.0f;
        }
        final float n8 = n + n3 / 2.0f;
        final float n9 = n2 + n4 / 2.0f;
        Class225.GL.glBegin(6);
        final int n10 = 360 / n5;
        Class225.GL.glVertex2f(n8, n9);
        for (int i = (int)n6; i < (int)(n7 + n10); i += n10) {
            float n11 = i;
            if (n11 > n7) {
                n11 = n7;
            }
            Class225.GL.glVertex2f((float)(n8 + Class161.cos(Math.toRadians(n11)) * n3 / 2.0), (float)(n9 + Class161.sin(Math.toRadians(n11)) * n4 / 2.0));
        }
        Class225.GL.glEnd();
        if (this.antialias) {
            Class225.GL.glBegin(6);
            Class225.GL.glVertex2f(n8, n9);
            if (n7 != 360.0f) {
                n7 -= 10.0f;
            }
            for (int j = (int)n6; j < (int)(n7 + n10); j += n10) {
                float n12 = j;
                if (n12 > n7) {
                    n12 = n7;
                }
                Class225.GL.glVertex2f((float)(n8 + Class161.cos(Math.toRadians(n12 + 10.0f)) * n3 / 2.0), (float)(n9 + Class161.sin(Math.toRadians(n12 + 10.0f)) * n4 / 2.0));
            }
            Class225.GL.glEnd();
        }
        this.postdraw();
    }
    
    public void drawRoundRect(final float n, final float n2, final float n3, final float n4, final int n5) {
        this.drawRoundRect(n, n2, n3, n4, n5, 50);
    }
    
    public void drawRoundRect(final float n, final float n2, final float n3, final float n4, int n5, final int n6) {
        if (n5 < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (n5 == 0) {
            this.drawRect(n, n2, n3, n4);
            return;
        }
        final int n7 = (int)Math.min(n3, n4) / 2;
        if (n5 > n7) {
            n5 = n7;
        }
        this.drawLine(n + n5, n2, n + n3 - n5, n2);
        this.drawLine(n, n2 + n5, n, n2 + n4 - n5);
        this.drawLine(n + n3, n2 + n5, n + n3, n2 + n4 - n5);
        this.drawLine(n + n5, n2 + n4, n + n3 - n5, n2 + n4);
        final float n8 = n5 * 2;
        this.drawArc(n + n3 - n8, n2 + n4 - n8, n8, n8, n6, 0.0f, 90.0f);
        this.drawArc(n, n2 + n4 - n8, n8, n8, n6, 90.0f, 180.0f);
        this.drawArc(n + n3 - n8, n2, n8, n8, n6, 270.0f, 360.0f);
        this.drawArc(n, n2, n8, n8, n6, 180.0f, 270.0f);
    }
    
    public void fillRoundRect(final float n, final float n2, final float n3, final float n4, final int n5) {
        this.fillRoundRect(n, n2, n3, n4, n5, 50);
    }
    
    public void fillRoundRect(final float n, final float n2, final float n3, final float n4, int n5, final int n6) {
        if (n5 < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (n5 == 0) {
            this.fillRect(n, n2, n3, n4);
            return;
        }
        final int n7 = (int)Math.min(n3, n4) / 2;
        if (n5 > n7) {
            n5 = n7;
        }
        final float n8 = n5 * 2;
        this.fillRect(n + n5, n2, n3 - n8, n5);
        this.fillRect(n, n2 + n5, n5, n4 - n8);
        this.fillRect(n + n3 - n5, n2 + n5, n5, n4 - n8);
        this.fillRect(n + n5, n2 + n4 - n5, n3 - n8, n5);
        this.fillRect(n + n5, n2 + n5, n3 - n8, n4 - n8);
        this.fillArc(n + n3 - n8, n2 + n4 - n8, n8, n8, n6, 0.0f, 90.0f);
        this.fillArc(n, n2 + n4 - n8, n8, n8, n6, 90.0f, 180.0f);
        this.fillArc(n + n3 - n8, n2, n8, n8, n6, 270.0f, 360.0f);
        this.fillArc(n, n2, n8, n8, n6, 180.0f, 270.0f);
    }
    
    public void setLineWidth(final float n) {
        this.predraw();
        this.lineWidth = n;
        Class225.LSR.setWidth(n);
        Class225.GL.glPointSize(n);
        this.postdraw();
    }
    
    public float getLineWidth() {
        return this.lineWidth;
    }
    
    public void resetLineWidth() {
        this.predraw();
        Class197.getLineStripRenderer().setWidth(1.0f);
        Class225.GL.glLineWidth(1.0f);
        Class225.GL.glPointSize(1.0f);
        this.postdraw();
    }
    
    public void setAntiAlias(final boolean b) {
        this.predraw();
        this.antialias = b;
        Class225.LSR.setAntiAlias(b);
        if (b) {
            Class225.GL.glEnable(2881);
        }
        else {
            Class225.GL.glDisable(2881);
        }
        this.postdraw();
    }
    
    public boolean isAntiAlias() {
        return this.antialias;
    }
    
    public void drawString(final String s, final float n, final float n2) {
        this.predraw();
        this.font.drawString(n, n2, s, this.currentColor);
        this.postdraw();
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2, final Class26 class221) {
        this.predraw();
        class220.draw(n, n2, class221);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void drawAnimation(final Class260 class260, final float n, final float n2) {
        this.drawAnimation(class260, n, n2, Class26.white);
    }
    
    public void drawAnimation(final Class260 class260, final float n, final float n2, final Class26 class261) {
        this.predraw();
        class260.draw(n, n2, class261);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2) {
        this.drawImage(class220, n, n2, Class26.white);
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.predraw();
        class220.draw(n, n2, n3, n4, n5, n6, n7, n8);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.drawImage(class220, n, n2, n + class220.getWidth(), n2 + class220.getHeight(), n3, n4, n5, n6);
    }
    
    public void copyArea(final Class220 class220, final int n, final int n2) {
        final int n3 = class220.getTexture().hasAlpha() ? 6408 : 6407;
        class220.bind();
        Class225.GL.glCopyTexImage2D(3553, 0, n3, n, this.screenHeight - (n2 + class220.getHeight()), class220.getTexture().getTextureWidth(), class220.getTexture().getTextureHeight(), 0);
        class220.ensureInverted();
    }
    
    private int translate(final byte b) {
        if (b < 0) {
            return 256 + b;
        }
        return b;
    }
    
    public Class26 getPixel(final int n, final int n2) {
        this.predraw();
        Class225.GL.glReadPixels(n, this.screenHeight - n2, 1, 1, 6408, 5121, this.readBuffer);
        this.postdraw();
        return new Class26(this.translate(this.readBuffer.get(0)), this.translate(this.readBuffer.get(1)), this.translate(this.readBuffer.get(2)), this.translate(this.readBuffer.get(3)));
    }
    
    public void getArea(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        if (byteBuffer.capacity() < n3 * n4 * 4) {
            throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
        }
        this.predraw();
        Class225.GL.glReadPixels(n, this.screenHeight - n2 - n4, n3, n4, 6408, 5121, byteBuffer);
        this.postdraw();
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final Class26 class221) {
        this.predraw();
        class220.draw(n, n2, n3, n4, n5, n6, n7, n8, class221);
        this.currentColor.bind();
        this.postdraw();
    }
    
    public void drawImage(final Class220 class220, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Class26 class221) {
        this.drawImage(class220, n, n2, n + class220.getWidth(), n2 + class220.getHeight(), n3, n4, n5, n6, class221);
    }
    
    public void drawGradientLine(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12) {
        this.predraw();
        Class237.bindNone();
        Class225.GL.glBegin(1);
        Class225.GL.glColor4f(n3, n4, n5, n6);
        Class225.GL.glVertex2f(n, n2);
        Class225.GL.glColor4f(n9, n10, n11, n12);
        Class225.GL.glVertex2f(n7, n8);
        Class225.GL.glEnd();
        this.postdraw();
    }
    
    public void drawGradientLine(final float n, final float n2, final Class26 class26, final float n3, final float n4, final Class26 class27) {
        this.predraw();
        Class237.bindNone();
        Class225.GL.glBegin(1);
        class26.bind();
        Class225.GL.glVertex2f(n, n2);
        class27.bind();
        Class225.GL.glVertex2f(n3, n4);
        Class225.GL.glEnd();
        this.postdraw();
    }
    
    public void pushTransform() {
        this.predraw();
        FloatBuffer floatBuffer;
        if (this.stackIndex >= this.stack.size()) {
            floatBuffer = BufferUtils.createFloatBuffer(18);
            this.stack.add(floatBuffer);
        }
        else {
            floatBuffer = this.stack.get(this.stackIndex);
        }
        Class225.GL.glGetFloat(2982, floatBuffer);
        floatBuffer.put(16, this.sx);
        floatBuffer.put(17, this.sy);
        ++this.stackIndex;
        this.postdraw();
    }
    
    public void popTransform() {
        if (this.stackIndex == 0) {
            throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
        }
        this.predraw();
        --this.stackIndex;
        final FloatBuffer floatBuffer = this.stack.get(this.stackIndex);
        Class225.GL.glLoadMatrix(floatBuffer);
        this.sx = floatBuffer.get(16);
        this.sy = floatBuffer.get(17);
        this.postdraw();
    }
    
    public void destroy() {
    }
    
    static {
        Class225.GL = Class197.get();
        Class225.LSR = Class197.getLineStripRenderer();
        Class225.MODE_NORMAL = 1;
        Class225.MODE_ALPHA_MAP = 2;
        Class225.MODE_ALPHA_BLEND = 3;
        Class225.MODE_COLOR_MULTIPLY = 4;
        Class225.MODE_ADD = 5;
        Class225.MODE_SCREEN = 6;
        Class225.currentGraphics = null;
    }
}
