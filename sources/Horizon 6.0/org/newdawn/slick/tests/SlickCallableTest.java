package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import HORIZON-6-0-SKIDPROTECTION.SlickCallable;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.SpriteSheet;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Animation;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SlickCallableTest extends BasicGame
{
    private Image Ó;
    private Image à;
    private float Ø;
    private AngelCodeFont áŒŠÆ;
    private Animation áˆºÑ¢Õ;
    
    public SlickCallableTest() {
        super("Slick Callable Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Image("testdata/rocket.png");
        this.à = new Image("testdata/sky.jpg");
        this.áŒŠÆ = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        final SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
        this.áˆºÑ¢Õ = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È(2.0f, 2.0f);
        g.HorizonCode_Horizon_È(0.0f, 0.0f, 800.0f, 600.0f, this.à, 0.0f, 0.0f);
        g.Ø();
        g.HorizonCode_Horizon_È(this.Ó, 100.0f, 100.0f);
        this.Ó.HorizonCode_Horizon_È(100.0f, 200.0f, 80.0f, 200.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(100.0f, 200.0f, "Text Drawn before the callable");
        final SlickCallable callable = new SlickCallable() {
            @Override
            protected void Ø­áŒŠá() throws SlickException {
                SlickCallableTest.this.Ó();
            }
        };
        callable.Ý();
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(450.0f, 250.0f, 80.0f, 200.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(150.0f, 300.0f, "Text Drawn after the callable");
    }
    
    public void Ó() {
        final FloatBuffer pos = BufferUtils.createFloatBuffer(4);
        pos.put(new float[] { 5.0f, 5.0f, 10.0f, 0.0f }).flip();
        final FloatBuffer red = BufferUtils.createFloatBuffer(4);
        red.put(new float[] { 0.8f, 0.1f, 0.0f, 1.0f }).flip();
        GL11.glLight(16384, 4611, pos);
        GL11.glEnable(16384);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        final float h = 0.75f;
        GL11.glFrustum(-1.0, 1.0, (double)(-h), (double)h, 5.0, 60.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -40.0f);
        GL11.glRotatef(this.Ø, 0.0f, 1.0f, 1.0f);
        GL11.glMaterial(1028, 5634, red);
        this.HorizonCode_Horizon_È(0.5f, 2.0f, 2.0f, 10, 0.7f);
    }
    
    private void HorizonCode_Horizon_È(final float inner_radius, final float outer_radius, final float width, final int teeth, final float tooth_depth) {
        final float r1 = outer_radius - tooth_depth / 2.0f;
        final float r2 = outer_radius + tooth_depth / 2.0f;
        final float da = 6.2831855f / teeth / 4.0f;
        GL11.glShadeModel(7424);
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glBegin(8);
        for (int i = 0; i <= teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
            if (i < teeth) {
                GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), width * 0.5f);
                GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
            }
        }
        GL11.glEnd();
        GL11.glBegin(7);
        for (int i = 0; i < teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0f * da), r2 * (float)Math.sin(angle + 2.0f * da), width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
        }
        GL11.glEnd();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        GL11.glBegin(8);
        for (int i = 0; i <= teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
            GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), -width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), -width * 0.5f);
            GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), -width * 0.5f);
        }
        GL11.glEnd();
        GL11.glBegin(7);
        for (int i = 0; i < teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), -width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0f * da), r2 * (float)Math.sin(angle + 2.0f * da), -width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
        }
        GL11.glEnd();
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glBegin(8);
        for (int i = 0; i < teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
            float u = r2 * (float)Math.cos(angle + da) - r1 * (float)Math.cos(angle);
            float v = r2 * (float)Math.sin(angle + da) - r1 * (float)Math.sin(angle);
            final float len = (float)Math.sqrt(u * u + v * v);
            u /= len;
            v /= len;
            GL11.glNormal3f(v, -u, 0.0f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5f);
            GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0f * da), r2 * (float)Math.sin(angle + 2.0f * da), width * 0.5f);
            GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0f * da), r2 * (float)Math.sin(angle + 2.0f * da), -width * 0.5f);
            u = r1 * (float)Math.cos(angle + 3.0f * da) - r2 * (float)Math.cos(angle + 2.0f * da);
            v = r1 * (float)Math.sin(angle + 3.0f * da) - r2 * (float)Math.sin(angle + 2.0f * da);
            GL11.glNormal3f(v, -u, 0.0f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
            GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), -width * 0.5f);
            GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0f);
        }
        GL11.glVertex3f(r1 * (float)Math.cos(0.0), r1 * (float)Math.sin(0.0), width * 0.5f);
        GL11.glVertex3f(r1 * (float)Math.cos(0.0), r1 * (float)Math.sin(0.0), -width * 0.5f);
        GL11.glEnd();
        GL11.glShadeModel(7425);
        GL11.glBegin(8);
        for (int i = 0; i <= teeth; ++i) {
            final float angle = i * 2.0f * 3.1415927f / teeth;
            GL11.glNormal3f(-(float)Math.cos(angle), -(float)Math.sin(angle), 0.0f);
            GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), -width * 0.5f);
            GL11.glVertex3f(inner_radius * (float)Math.cos(angle), inner_radius * (float)Math.sin(angle), width * 0.5f);
        }
        GL11.glEnd();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.Ø += delta * 0.1f;
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new SlickCallableTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
