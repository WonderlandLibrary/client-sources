package club.bluezenith.module.modules.render.hud.elements;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.listeners.PlaytimeMeter;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.draggables.Draggable;
import net.minecraft.client.gui.GuiIngameMenu;
import net.optifine.gui.GuiChatOF;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.MinecraftInstance.mc;
import static java.lang.Float.MIN_VALUE;
import static net.minecraft.client.gui.GuiPlayerTabOverlay.playtimeYOffset;

public class Playtime implements Draggable {
    private static final String UNSUPPORTED = "Playtime is not supported in singleplayer!",
                                RECONNECT = "Reconnect to the server.";

    private float x = MIN_VALUE, y = MIN_VALUE, width, height;

    @Override
    public boolean shouldBeRendered() {
        return HUD.module.getState()
                && (HUD.elements.getOptionState("Playtime")
                && ( !mc.isSingleplayer()
                   || mc.currentScreen instanceof GuiChatOF
                ));
    }

    @Override
    public void draw(Render2DEvent event) {
        final float width = (float) event.resolution.getScaledWidth_double(),
                     height = (float) event.resolution.getScaledHeight_double();

        if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiIngameMenu) && !(mc.currentScreen instanceof GuiChatOF))
            return;

        final boolean setXY = x == MIN_VALUE || y == MIN_VALUE;

        final float offset = !mc.gameSettings.keyBindPlayerList.pressed ? height / 20F
                             : height / 100F + playtimeYOffset;

        if (mc.isSingleplayer()) {
            mc.fontRendererObj.drawString(
                    UNSUPPORTED,
                    setXY ? x = (width / 2F - mc.fontRendererObj.getStringWidthF(UNSUPPORTED) / 2F) : x,
                    setXY ? y = offset : y,
                    -1,
                    true
            );
            this.width = mc.fontRendererObj.getStringWidthF(UNSUPPORTED);
        } else {
            final PlaytimeMeter meter = getBlueZenith().getPlaytimeMeter();
            if (meter == null) {
                mc.fontRendererObj.drawString(
                        RECONNECT,
                        setXY ? x = (width / 2F - mc.fontRendererObj.getStringWidthF(RECONNECT) / 2F) : x,
                        setXY ? y = offset : y,
                        -1,
                        true
                );
                this.width = mc.fontRendererObj.getStringWidthF(RECONNECT);
            } else {
                final String playtime = meter.timePlayedString;
                mc.fontRendererObj.drawString(
                        playtime,
                        setXY ? x = (width / 2F - mc.fontRendererObj.getStringWidthF(playtime) / 2F) : x,
                        setXY ? y = offset : y,
                        -1,
                        true
                );

                this.width = mc.fontRendererObj.getStringWidthF(playtime);
            }
        }
        this.height = mc.fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

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

    @Override
    public String getIdentifier() {
        return "Playtime";
    }

    @Override
    public void resetPosition() {
        x = MIN_VALUE;
        y = MIN_VALUE;
    }
}
