package dev.client.tenacity.ui.oldaltmanager;

import dev.client.tenacity.ui.oldaltmanager.backend.AltManagerUtils;
import dev.utils.animations.Animation;
import net.minecraft.client.Minecraft;

import java.awt.*;

public interface AltPanel {

    int rectColorInt = new Color(20, 20, 29, 120).getRGB();
    Minecraft mc = Minecraft.getMinecraft();

    void initGui();

    void keyTyped(char typedChar, int keyCode);

    void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation);

    void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils);

}
