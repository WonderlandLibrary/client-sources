package club.bluezenith.ui.clickguis.novo.components.modules.values;

import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static java.lang.Math.max;
import static net.minecraft.util.EnumChatFormatting.GRAY;

public class StringComponent extends Component {

    private final StringValue parent;

    private boolean acceptingKeypresses, animateForwards;

    private float typingAlpha;

    public StringComponent(StringValue parent, float x, float y) {
        super(x, y);
        this.parent = parent;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height + 6;
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
        Keyboard.enableRepeatEvents(true);
        typingAlpha = RenderUtil.animate(animateForwards ? 0.9F : 0F, typingAlpha, 0.08F);

        if(typingAlpha >= 0.88F && animateForwards)
            animateForwards = false;
        else if(typingAlpha <= 0.04F && !animateForwards)
            animateForwards = true;

        final TFontRenderer font = FontUtil.createFont("helvetica", 30);

        float y = this.y;

        final String the = parent.get().isEmpty() ? "t" : parent.get();

        font.drawString(GRAY + parent.name, x + 3, y, -1, true);

        String toDraw = parent.get();

        while (font.getStringWidthF(toDraw + "_") > width - 3) {
            toDraw = toDraw.substring(2);
        }

        font.drawString(toDraw, x + 3, y += (4.5F + font.getHeight(parent.name)), -1, true);

        if(acceptingKeypresses)
          font.drawString("_", x + 3 + font.getStringWidthF(toDraw), y, new Color(1, 1, 1, typingAlpha).getRGB(), true);

        RenderUtil.rect(x + 3, y += 1 + font.getHeight(the), x + width - 3, y + .5F, new Color(126,126,126,255));
    }

    public String getText() {
        return parent.get();
    }

    public void resetText() {
        parent.set("");
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        if(acceptingKeypresses) {
            if(keyCode == Keyboard.KEY_BACK) {
                if(parent.get().length() > 0)
                  parent.set(parent.get().substring(0, max(0, parent.get().length() - 1)));
            } else if(keyCode == Keyboard.KEY_RETURN)
                acceptingKeypresses = false;
            else if(!Character.isISOControl(key))
                parent.set(parent.get() + key);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final TFontRenderer font = FontUtil.createFont("helvetica", 30);
        float y = this.y + 1.5F + font.getHeight(parent.name) + 1;

        if(ClickGui.i(mouseX, mouseY, x + 3, y, x + width - 3, y + font.getHeight(parent.get().isEmpty() ? "t" : parent.get()) + .5F)) {
            acceptingKeypresses = true;
        } else {
            acceptingKeypresses = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return true;
    }

    @Override
    public boolean isAcceptingKeypresses() {
        return acceptingKeypresses;
    }

    @Override
    public boolean shouldUpdateWidth() {
        return true;
    }

    @Override
    public void onGuiClosed() {
        this.acceptingKeypresses = false;
        Keyboard.enableRepeatEvents(false);
    }
}
