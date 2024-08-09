package ez.h.ui.hudeditor.registry;

import ez.h.ui.hudeditor.*;
import java.util.*;
import com.google.common.collect.*;
import java.text.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.ui.fonts.*;

public class MotionGraph extends DraggableElement
{
    Counter counter;
    ArrayList<MovementNode> movementNodes;
    
    public float getAverageHeight() {
        float n = 0.0f;
        for (int i = this.movementNodes.size() - 1; i > 0; --i) {
            final MovementNode movementNode = this.movementNodes.get(i);
            if (this.movementNodes.size() > (0xAD ^ 0xA6) && movementNode != null && i > this.movementNodes.size() - (0x6 ^ 0xC)) {
                n += movementNode.speed;
            }
        }
        return n / 10.0f;
    }
    
    public MotionGraph() {
        super("MotionGraph", 180.0f, 20.0f);
        this.movementNodes = (ArrayList<MovementNode>)Lists.newArrayList();
        this.counter = new Counter();
        this.x = RenderUtils.getScaledScreen()[0] / 2.0f;
        this.y = RenderUtils.getScaledScreen()[1] - RenderUtils.getScaledScreen()[1] / 4.0f;
    }
    
    @Override
    public void render(final float n, final float n2, final float n3, final boolean b) {
        this.width = 180.0f;
        this.height = 20.0f;
        if (b) {
            super.render(n, n2, n3, b);
            return;
        }
        final double n4 = 180.0;
        final double n5 = this.x;
        final double n6 = this.y;
        final double n7 = 20.0;
        if (this.mc.h != null && this.mc.f != null) {
            final bit bit = new bit(this.mc);
            final DecimalFormat decimalFormat = new DecimalFormat("###.##");
            if (this.movementNodes.size() > n4 / 2.0) {
                this.movementNodes.clear();
            }
            if (this.counter.hasReached(15.0f)) {
                if (this.movementNodes.size() > n4 / 2.0 - 1.0) {
                    this.movementNodes.remove(0);
                }
                this.movementNodes.add(new MovementNode((float)(Math.hypot(this.mc.h.p - this.mc.h.m, this.mc.h.r - this.mc.h.o) / 0.07000000029802322)));
                this.counter.reset();
            }
            GL11.glPushMatrix();
            ScaleUtils.scale_pre();
            GL11.glAlphaFunc(197 + 17 + 153 + 149, 0.01f);
            RenderUtils.drawBlurredShadow((float)n5, (float)n6, 180.0f, 20.0f, 0x5F ^ 0x55, new Color(1, 1, 1, 0x77 ^ 0x13));
            RenderUtils.drawRoundedRect((int)n5, (float)n6, (int)n4, (float)n7, 5.0, new Color(0, 0, 0, 0xFA ^ 0xBC).getRGB());
            GL11.glEnable(974 + 2143 - 1109 + 1081);
            RenderUtils.getScaleFactor();
            RenderUtils.setupScissor((float)n5, (float)n6, (float)n4, (float)n7);
            MovementNode movementNode = null;
            for (int i = 0; i < this.movementNodes.size(); ++i) {
                final MovementNode movementNode2 = this.movementNodes.get(i);
                final float mappedX = (float)MathUtils.map(n4 / 2.0 - 1.0 - i, 0.0, n4 / 2.0 - 1.0, n5 + n4 - 1.0, n5 + 1.0);
                final float mappedY = (float)((float)MathUtils.map(movementNode2.speed, -2.0, this.getAverageHeight(), n6 + n7 - 1.0, n6 + 1.0) + n7 / 2.0);
                movementNode2.mappedX = mappedX;
                movementNode2.mappedY = mappedY;
                if (movementNode != null) {
                    final Color color = ez.h.features.visual.MotionGraph.color.getColor();
                    RenderUtils.drawBlurredShadow(movementNode2.mappedX - 3.0f, movementNode2.mappedY - 2.5f, 5.0f, 5.0f, 3, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0x2D ^ 0x1F));
                    RenderUtils.drawLine(movementNode2.mappedX, movementNode2.mappedY, movementNode.mappedX, movementNode.mappedY, 2.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 63 + 131 - 183 + 239).getRGB());
                }
                final float n8 = 0.0f;
                final float n9 = 0.0f;
                if (n8 >= movementNode2.mappedX && n8 <= movementNode2.mappedX + movementNode2.size && n9 >= n6 && n9 <= n6 + n7) {
                    final Color color2 = ez.h.features.visual.MotionGraph.color.getColor();
                    RenderUtils.drawRect(movementNode2.mappedX - movementNode2.size, n6, movementNode2.mappedX + movementNode2.size, n6 + n7, new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), 66 + 179 - 233 + 238).getRGB());
                    RenderUtils.drawRect(movementNode2.mappedX - movementNode2.size, movementNode2.mappedY - movementNode2.size, movementNode2.mappedX + movementNode2.size, movementNode2.mappedY + movementNode2.size, new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), 35 + 248 - 278 + 245).getRGB());
                    String.format("Speed: %s", decimalFormat.format(movementNode2.speed));
                }
                movementNode = movementNode2;
            }
            GL11.glDisable(632 + 2208 - 652 + 901);
            final String string = decimalFormat.format(this.movementNodes.get(this.movementNodes.size() - 1).speed) + "bps";
            CFontManager.comfortaa.drawString(string, (float)(n5 + n4 - this.mc.k.a(string)), (float)(n6 + n7 + 3.0), new Color(-1).getRGB());
            ScaleUtils.scale_post();
            GL11.glPopMatrix();
        }
    }
    
    private static class MovementNode
    {
        public float mappedY;
        public float speed;
        public float mappedX;
        public float size;
        public Color color;
        
        public MovementNode(final float speed) {
            this.size = 0.5f;
            this.speed = 0.0f;
            this.speed = speed;
            this.color = new Color(43 + 166 - 46 + 92, 185 + 196 - 180 + 54, 209 + 249 - 395 + 192);
        }
    }
}
