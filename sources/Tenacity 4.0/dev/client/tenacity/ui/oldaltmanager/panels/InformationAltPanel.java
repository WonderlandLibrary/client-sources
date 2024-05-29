package dev.client.tenacity.ui.oldaltmanager.panels;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.ui.oldaltmanager.AltPanel;
import dev.client.tenacity.ui.oldaltmanager.backend.AltManagerUtils;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.animations.impl.EaseInOutQuad;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class InformationAltPanel implements AltPanel {

    KingAltsPanel kingAltsPanel;
    private Animation hoverInfoScreen;
    private Animation infoScreenRect;
    private boolean clickedInfoScreen = false;

    @Override
    public void initGui() {
        //kingAltsPanel = (KingAltsPanel) Tenacity.INSTANCE.altPanels.getPanel(KingAltsPanel.class);
        clickedInfoScreen = false;
        //Hover Animations
        hoverInfoScreen = new DecelerateAnimation(200, 1);
        infoScreenRect = new EaseInOutQuad(700, 1, Direction.FORWARDS);
        infoScreenRect.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        ScaledResolution sr = new ScaledResolution(mc);
        Color interpolate2 =
                ColorUtil.interpolateColorsBackAndForth(
                        1,
                        9,
                        Tenacity.INSTANCE.getAlternateClientColor(),
                        Tenacity.INSTANCE.getClientColor(),
                        false);
        float mainRectAnimation = (float) (105 + (45 * kingAltsPanel.hasKingAltKey.getOutput()) + (30 * kingAltsPanel.errorAnimation.getOutput()));
        float yVal = 195 + mainRectAnimation;
        float xVal = (float) ((30 - 290) + (290 * initAnimation.getOutput()));
        Gui.drawRect2(xVal - 10, yVal + 10, 250, 110, rectColorInt);
        FontUtil.tenacityFont22.drawString("Do you want your alt shop or alt generator", xVal, yVal + 20, -1);
        FontUtil.tenacityFont22.drawString("sponsored here?", xVal, yVal + 30, -1);
        FontUtil.tenacityFont22.drawString("Contact cedo#0001 on Discord with information", xVal, yVal + 50, -1);
        FontUtil.tenacityFont22.drawString("regarding your alt shop!", xVal, yVal + 60, -1);

        FontUtil.tenacityFont22.drawString("For more information regarding sponsorships,", xVal, yVal + 90, -1);

        boolean hoveringInfoScreen = HoveringUtil.isHovering((float) (30 +
                        FontUtil.tenacityFont22.getStringWidth("see the ")), yVal + 100,
                (float) FontUtil.tenacityFont22.getStringWidth("info screen"), FontUtil.tenacityFont22.getHeight(), mouseX, mouseY);

        hoverInfoScreen.setDirection(hoveringInfoScreen ? Direction.FORWARDS : Direction.BACKWARDS);
        FontUtil.tenacityFont22.drawString("§fsee the §r§ninfo screen", xVal, yVal + 100,
                ColorUtil.interpolateColor(-1, interpolate2.getRGB(), (float) hoverInfoScreen.getOutput()));

        infoScreenRect.setDirection(clickedInfoScreen ? Direction.FORWARDS : Direction.BACKWARDS);
        Gui.drawRect2(0, 0, sr.getScaledWidth() * infoScreenRect.getOutput(), sr.getScaledHeight(),
                Tenacity.INSTANCE.getAlternateClientColor().getRGB());
        if (clickedInfoScreen && infoScreenRect.isDone()) {
//            mc.displayGuiScreen(new InfoScreen());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        float mainRectAnimation = (float) (105 + (45 * kingAltsPanel.hasKingAltKey.getOutput()) + (30 * kingAltsPanel.errorAnimation.getOutput()));

        float yVal = 160 + mainRectAnimation;
        if (button != 0) return;

        boolean hoveringInfoScreen = HoveringUtil.isHovering((float) (30 +
                        FontUtil.tenacityFont22.getStringWidth("see the ")), yVal + 100,
                (float) FontUtil.tenacityFont22.getStringWidth("infoscreen"), FontUtil.tenacityFont22.getHeight(), mouseX, mouseY);

        if (hoveringInfoScreen) {
            clickedInfoScreen = true;
        }
    }
}
