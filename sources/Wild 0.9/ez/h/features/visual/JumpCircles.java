package ez.h.features.visual;

import ez.h.event.*;
import org.lwjgl.opengl.*;
import java.util.*;
import ez.h.event.events.*;
import java.awt.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;

public class JumpCircles extends Feature
{
    ArrayList<Circle> circles;
    OptionColor endColor;
    OptionSlider speed;
    static ArrayList<Circle2> circles2;
    OptionSlider fadeSpeed;
    static final int TYPE = 0;
    static final byte MAX_JC_TIME = 20;
    OptionMode mode;
    OptionSlider maxRadius;
    
    @Override
    public void updateElements() {
        this.speed.display = this.mode.isMode("Default");
        this.fadeSpeed.display = this.mode.isMode("Default");
        this.maxRadius.display = this.mode.isMode("Default");
        this.endColor.display = this.mode.isMode("Default");
        super.updateElements();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        JumpCircles.circles2.removeIf(Circle2::update);
    }
    
    @EventTarget
    public void onRenderWorld(final EventRender3D eventRender3D) {
        if (this.mode.isMode("Default")) {
            return;
        }
        final bud h = bib.z().h;
        bib.z();
        final double n = -(h.M + (h.p - h.M) * eventRender3D.getPartialTicks());
        final double n2 = -(h.N + (h.q - h.N) * eventRender3D.getPartialTicks());
        final double n3 = -(h.O + (h.r - h.O) * eventRender3D.getPartialTicks());
        GL11.glPushMatrix();
        GL11.glTranslated(n, n2, n3);
        GL11.glDisable(1530 + 2151 - 3081 + 2284);
        GL11.glEnable(1890 + 208 - 1472 + 2416);
        GL11.glDisable(1889 + 350 + 217 + 1097);
        GL11.glDisable(2655 + 751 - 2619 + 2221);
        GL11.glDisable(2238 + 539 - 1372 + 1524);
        GL11.glBlendFunc(629 + 678 - 846 + 309, 633 + 423 - 625 + 340);
        GL11.glShadeModel(1124 + 6361 - 1966 + 1906);
        Collections.reverse(JumpCircles.circles2);
        try {
            for (final Circle2 circle2 : JumpCircles.circles2) {
                final float n4 = circle2.existed / 20.0f;
                final double x = circle2.position().x;
                final double n5 = circle2.position().y - n4 * 0.5;
                final double z = circle2.position().z;
                final float n6 = n4;
                final float n7 = n6 + 1.0f - n4;
                GL11.glBegin(8);
                for (int i = 0; i <= 282 + 193 - 409 + 294; i += 5) {
                    GL11.glColor4f((float)circle2.color().x, (float)circle2.color().y, (float)circle2.color().z, 0.2f * (1.0f - circle2.existed / 20.0f));
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(i * 4)) * n6, n5, z + Math.sin(Math.toRadians(i * 4)) * n6);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.01f * (1.0f - circle2.existed / 20.0f));
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * n7, n5 + Math.sin(n4 * 8.0f) * 0.5, z + Math.sin(Math.toRadians(i) * n7));
                }
                GL11.glEnd();
            }
        }
        catch (Exception ex) {}
        Collections.reverse(JumpCircles.circles2);
        GL11.glEnable(2161 + 1086 - 1558 + 1864);
        GL11.glDisable(1802 + 539 - 1124 + 1825);
        GL11.glShadeModel(2749 + 5127 - 2821 + 2369);
        GL11.glEnable(495 + 1590 - 720 + 1519);
        GL11.glEnable(1982 + 1451 - 2636 + 2132);
        GL11.glEnable(2371 + 2760 - 4084 + 1961);
        GL11.glPopMatrix();
    }
    
    static {
        JumpCircles.circles2 = new ArrayList<Circle2>();
    }
    
    @EventTarget
    public void onJump2(final EventJump eventJump) {
        if (this.mode.isMode("Default")) {
            return;
        }
        JumpCircles.circles2.add(new Circle2(new bhe(JumpCircles.mc.h.p, JumpCircles.mc.h.q, JumpCircles.mc.h.r), new bhe((double)(Color.BLUE.getRed() / 255.0f), (double)(Color.BLUE.getGreen() / 255.0f), (double)(Color.BLUE.getRed() / 255.0f))));
    }
    
    @Override
    public void onDisable() {
        this.circles.clear();
        JumpCircles.circles2.clear();
        super.onDisable();
    }
    
    @EventTarget
    public void onJump(final EventJump eventJump) {
        if (this.mode.isMode("Sinus")) {
            return;
        }
        this.circles.add(new Circle((float)JumpCircles.mc.h.p, (float)JumpCircles.mc.h.q, (float)JumpCircles.mc.h.r, (float)this.endColor.alpha));
    }
    
    public JumpCircles() {
        super("JumpCircles", "\u0410\u043d\u0438\u043c\u0438\u0440\u0443\u0435\u0442 \u043f\u0440\u044b\u0436\u043e\u043a \u0438\u0433\u0440\u043e\u043a\u0430.", Category.VISUAL);
        this.circles = new ArrayList<Circle>();
        this.mode = new OptionMode(this, "Mode", "Sinus", new String[] { "Sinus", "Default" }, 0);
        this.speed = new OptionSlider(this, "Speed", 0.05f, 0.01f, 1.0f, OptionSlider.SliderType.NULL);
        this.fadeSpeed = new OptionSlider(this, "Fade Speed", 0.05f, 0.01f, 1.0f, OptionSlider.SliderType.NULL);
        this.maxRadius = new OptionSlider(this, "Max Radius", 3.0f, 0.01f, 5.0f, OptionSlider.SliderType.M);
        this.endColor = new OptionColor(this, "End Color", new Color(-1), true);
        this.addOptions(this.mode, this.speed, this.fadeSpeed, this.maxRadius, this.endColor);
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.isMode("Sinus")) {
            return;
        }
        if (this.circles.size() == 0) {
            return;
        }
        this.circles.removeIf(circle -> circle.radius > this.maxRadius.getNum());
        for (final Circle circle2 : this.circles) {
            RenderUtils.drawJumpCircle(circle2.x, circle2.y, circle2.z, circle2.radius, 0, RenderUtils.injectAlpha(this.endColor.getColor().getRGB(), (int)circle2.alpha).getRGB());
            circle2.radius = MathUtils.lerp(circle2.radius, this.maxRadius.getNum() + 0.5f, this.speed.getNum() / 10.0f);
            if (circle2.radius > this.maxRadius.getNum() / 2.0f) {
                circle2.alpha = MathUtils.lerp(circle2.alpha, 0.0f, this.fadeSpeed.getNum() / 4.0f);
            }
        }
    }
    
    static class Circle2
    {
        byte existed;
        private final bhe color;
        private final bhe vec;
        
        bhe color() {
            return this.color;
        }
        
        boolean update() {
            final byte existed = (byte)(this.existed + 1);
            this.existed = existed;
            return existed > (0xA7 ^ 0xB3);
        }
        
        bhe position() {
            return this.vec;
        }
        
        Circle2(final bhe vec, final bhe color) {
            this.vec = vec;
            this.color = color;
        }
    }
    
    static class Circle
    {
        float radius;
        float y;
        float alpha;
        float x;
        float z;
        
        public Circle(final float x, final float y, final float z, final float alpha) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.alpha = alpha;
        }
    }
}
