package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.time.LocalDateTime;

import fun.ellant.utils.drag.Dragging;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.ITextComponent;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;

public class Information implements ElementRenderer, ElementUpdater {

    final Dragging dragging;

    float width;
    float height;



    public void render(EventDisplay eventDisplay) {
        float x = dragging.getX();
        float y = dragging.getY();

        MatrixStack ms = eventDisplay.getMatrixStack();
        float FontSize = 8.0F;
        ServerData serverData = Minecraft.getInstance().getCurrentServerData();
        String serverIP = serverData != null ? serverData.serverIP : " ";
        ITextComponent serv = GradientUtil.gradient(" " + serverIP, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        String playerName = Minecraft.getInstance().player.getName().getString();
        ITextComponent nazv = GradientUtil.gradient("Информация", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        ITextComponent ss = GradientUtil.white("Сервер:");
        ITextComponent ss2 = GradientUtil.white("Ник: ");
        ITextComponent playernames = GradientUtil.gradient(playerName, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        LocalDateTime currentTime = LocalDateTime.now();
        int currentHour = currentTime.getHour();
        int currentMinute = currentTime.getMinute();
        int currentSecond = currentTime.getSecond();
        ITextComponent vremya = GradientUtil.gradient(" " + currentHour + ":" + currentMinute + ":" + currentSecond, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        ITextComponent vremya2 = GradientUtil.white("Время:");
        DisplayUtils.drawShadow(x + 1.0F, y + 40.0F - 8.0F, width, height, 10, ColorUtils.rgb(30, 30, 30));

        DisplayUtils.drawRoundedRect(x + 1.0F, y + 40.0F - 8.0F, width, height, 5.0F, ColorUtils.rgba(25, 25, 25, 160));
        Fonts.sfui.drawText(ms, nazv, x + 17.0F + 3.0F + 7.0F, y + 42.0F - 7.0F, FontSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, ss, x + 17.0F - 11.0F, y + 46.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, serv, x + 17.0F + 20.0F, y + 46.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, ss2, x + 17.0F - 11.0F, y + 46.0F + 10.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, playernames, x + 17.0F + 20.0F - 10.0F, y + 46.0F + 10.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, vremya, x + 17.0F + 20.0F - 10.0F + 5.0F, y + 46.0F + 10.0F + 10.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));
        Fonts.sfbold.drawText(ms, vremya2, x + 17.0F - 11.0F, y + 46.0F + 10.0F + 10.0F + 3.0F, 6.5F, ColorUtils.rgb(255, 255, 255));

        width = width = 100;
        height = height = 50;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    public void update(EventUpdate e) {
    }
    public Information(Dragging dragging) {
        this.dragging = dragging;
    }

}
