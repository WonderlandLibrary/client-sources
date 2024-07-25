package club.bluezenith.module.modules.render.hud.elements;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.draggables.Draggable;
import net.minecraft.client.gui.FontRenderer;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.BlueZenith.isVirtueTheme;
import static club.bluezenith.util.MinecraftInstance.mc;
import static java.lang.Float.MIN_VALUE;
import static java.lang.String.format;

public class BuildInfo implements Draggable {
    private float x = MIN_VALUE, y = MIN_VALUE, width, height = mc.fontRendererObj.FONT_HEIGHT;

    @Override
    public boolean shouldBeRendered() {
        return HUD.module.getState() && HUD.elements.getOptionState("Build Info");
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return checkMouseBounds(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void draw(Render2DEvent event) {
        resetPosition();
        String buildInfo = format(
                "Build - §7%s §f| UID - §7%s",
                getBlueZenith().version(),
                format(
                        "%03d",
                        getBlueZenith().getClientUser().getUID()
                )
        );

        if(isVirtueTheme) {
            final boolean isBeta = false; //todo fetch from server later?

            buildInfo = format(
                    "§7%s - §f§l%s §7- §lUID %s",
                    (isBeta ? "Beta " : "Release ") + "build",
                    BlueZenith.version,
                    format("%03d", getBlueZenith().getClientUser().getUID())
            );
        }
        final FontRenderer font = HUD.module.font.get();
        final float screenWidth = (float) event.getWidth(),
                    screenHeight = (float) event.getHeight();

        final boolean setXY = x == MIN_VALUE || y == MIN_VALUE;

        font.drawString(
                buildInfo,
                setXY ? x = (screenWidth - font.getStringWidthF(buildInfo) - 1) : x,
                setXY ? y = screenHeight - font.FONT_HEIGHT - 3 : y,
                -1,
                true
        );

        width = font.getStringWidthF(buildInfo);
        height = font.FONT_HEIGHT;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public String getIdentifier() {
        return "BuildInfo";
    }

    @Override
    public void resetPosition() {
        x = y = MIN_VALUE;
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
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
