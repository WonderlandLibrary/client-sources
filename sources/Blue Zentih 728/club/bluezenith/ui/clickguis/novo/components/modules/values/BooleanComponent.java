package club.bluezenith.ui.clickguis.novo.components.modules.values;

import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.modules.ModuleComponent;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.Color;

import static club.bluezenith.util.render.RenderUtil.*;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class BooleanComponent extends Component {
    private static final TFontRenderer font = FontUtil.createFont("helvetica", 33);

    private final BooleanValue parent;
    private final ModuleComponent owner;

    private float alpha;

    public BooleanComponent(ModuleComponent owner, BooleanValue parent, float x, float y) {
        super(x, y);
        this.parent = parent;
        this.owner = owner;

        this.identifier = parent.name;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height + 6F;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        x += 4;

        RenderUtil.hollowRect(x, y, x + width, y + height, 1, Color.WHITE.getRGB());

        if (parent.get()) {
            alpha = animate(1, alpha, 0.1F);
            if(1 - alpha < 0.03) alpha = 1; //normalizing values a bit (when the anim is close to target it'll get slower which looks bad)
        } else {
            if(alpha < 0.03) alpha = 0;
            alpha = animate(0, alpha, 0.12F);
        }

        pushMatrix();
        translate(x, y, 0);
        translate(width/2D, width/2D, 0);
        scale(alpha, alpha, 1);
        translate(.8, .8, 0);
        translate(-width/2D, -width/2D, 0);
        glLineWidth(2);
        glColor4d(1, 1, 1, alpha);
        start2D(GL_LINE_STRIP);
        glVertex2d(1.5, 4);
        glVertex2d(3.5, 6);
        glVertex2d(7, 2);
        end2D();
        popMatrix();

        font.drawString(identifier, x + width + 3, y + (height/2F - font.getHeight(parent.name)/2F), -1, true);
        x -= 4;
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        parent.next();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, getX() + 3, getY(), getX() + 3 + (owner != null ? owner.getWidth() : 100), getY() + height + 2);
    }
}
