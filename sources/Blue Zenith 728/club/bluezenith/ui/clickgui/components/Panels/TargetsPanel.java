package club.bluezenith.ui.clickgui.components.Panels;

import club.bluezenith.ui.clickgui.components.Panel;
import club.bluezenith.util.player.TargetHelper;
import club.bluezenith.util.font.FontUtil;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static club.bluezenith.util.render.RenderUtil.rect;

public class TargetsPanel extends Panel {
    private final ArrayList<TargetHelper.Targets> targets = new ArrayList<>();
    private boolean wasButtonDown = false;
    private final FontRenderer bigCheckbox = FontUtil.ICON_testFont3;
    public TargetsPanel(float x, float y) {
        super(x, y, "Targets");
        f = FontUtil.inter28;
        mHeight = f.FONT_HEIGHT + 9;
        originalMheight = mHeight;
        targets.addAll(Arrays.asList(TargetHelper.Targets.values()));
        width = 120 - 10;
    }
    public void drawPanel(int mouseX, int mouseY, float partialTicks, boolean handleClicks) {
        float borderWidth = 1.5F;
        Color mainColor = click.primaryColor.get();
        Color backgroundColor = click.oldDropdownBackground;
        rect(x - borderWidth, y, x + width + borderWidth, y + mHeight, new Color(21, 21, 21));
        float div = mHeight / 2f - f.FONT_HEIGHT / 2f;
        f.drawString("Targets", x + 4, y + div, Color.WHITE.getRGB());
        if(!showContent) return;
        float y = this.y + mHeight;
        for (TargetHelper.Targets tar : targets) {
            rect(x - borderWidth, y, x, y + mHeight, backgroundColor.darker());
            rect(x + width, y, x + width + borderWidth, y + mHeight, backgroundColor.darker());
            if(targets.indexOf(tar) == targets.size() - 1) {
                rect(x - borderWidth, y, x + width + borderWidth, y + mHeight + borderWidth, backgroundColor.darker());
            }
            rect(x, y, x + width, y + mHeight, backgroundColor);
            f.drawString(tar.displayName, x + 5, y + (div), tar.on ? Color.WHITE.getRGB() : Color.WHITE.darker().getRGB());
            if(tar.on)
                bigCheckbox.drawString("F", x + width - 14, y + (div), mainColor.getRGB());
            else bigCheckbox.drawString("D", x + width - 14, y + (div), mainColor.getRGB());
            if(i(mouseX, mouseY, x, y, x + width, y + mHeight) && Mouse.isButtonDown(0) && !wasButtonDown && handleClicks){
                tar.on = !tar.on;
                toggleSound();
            }
            y += mHeight;
        }
        wasButtonDown = Mouse.isButtonDown(0);
    }
}
