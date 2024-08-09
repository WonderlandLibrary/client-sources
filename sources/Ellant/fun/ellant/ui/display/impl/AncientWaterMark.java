package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AncientWaterMark implements ElementRenderer {



    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 5;
        float posY = 2.5f;
        float padding = 5;
        float fontSize = 6.5f;
        float iconSize = 10;
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        NetworkPlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(Minecraft.getInstance().player.getUniqueID());
        int ping = (playerInfo != null) ? playerInfo.getResponseTime() : 0;

        ITextComponent logo = GradientUtil.gradient("expensive");
        ITextComponent username = GradientUtil.gradient(" | " + mc.player.getName().getString() + " | " + mc.debugFPS + " fps | " + ping + " ms");

        float textWidth = Fonts.sfbold.getWidth(logo, fontSize);

        float localPosX = posX + iconSize + padding * 10;
        drawStyledRect(localPosX, posY + 3, iconSize + 8 + padding * 2.8f + Fonts.sfbold.getWidth(logo, 6.5f) + Fonts.sfbold.getWidth(username, 6.5f) - 3, iconSize - 8 + padding * 2.17f, 3.50f);
        //Fonts.sfbold.drawText(ms, logo, 6 + 1.5f, posY - 0.5f + iconSize / 1.90f + 1.5f + 0.5f, fontSize + 1, 255);
        Fonts.sfbold.drawText(ms, "expensive", 12, 9.3f, -1, 7.5f);
        //Fonts.sfbold.drawText(ms, username, localPosX + iconSize + padding * -6.5f - 1 + 11.8f, posY + iconSize / 2 + 1.5f + 0.3f, fontSize + 1, ColorUtils.rgba(255, 255, 255, 255));
        Fonts.sfbold.drawText(ms, " | " + mc.player.getName().getString() + " | " + mc.debugFPS + "fps | " + ping + " ms", 57.5f, 9.3f, -1, 7.5f);
        DisplayUtils.drawShadow(6.2f, 6f, 3, 14.3f, 7, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(8f, 5.8f, 2.5f, 14.3f, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        NetworkPlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(Minecraft.getInstance().player.getUniqueID());
        int ping = (playerInfo != null) ? playerInfo.getResponseTime() : 0;
        ITextComponent logo = GradientUtil.gradient("expensive");
        ITextComponent username = GradientUtil.gradient(" | " + mc.player.getName().getString() + " | " + mc.debugFPS + " fps | " + ping + " ms");
        DisplayUtils.drawRoundedRect(9.7f, 6f, width, 14f, radius - 2.5f, ColorUtils.rgba(0, 0, 0, 255));
        //DisplayUtils.drawRoundedRect(5, y, width + 19 + Fonts.sfbold.getWidth(logo, 6.5f) + Fonts.sfbold.getWidth(username, 6.5f) - 107f, height + 2.5f, radius, ColorUtils.rgba(34, 34, 34, 255));
    }
}