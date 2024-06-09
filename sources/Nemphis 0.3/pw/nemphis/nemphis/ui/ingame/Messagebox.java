/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.ui.ingame;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import pw.vertexcode.nemphis.ui.ingame.render.RenderComponent;
import pw.vertexcode.util.lwjgl.LWJGLUtil;
import pw.vertexcode.util.management.TimeHelper;

public class Messagebox
extends RenderComponent {
    private static List<String> message = new ArrayList<String>();
    private TimeHelper timeHelper = new TimeHelper();

    @Override
    public void render() {
        this.renderBox();
        super.render();
    }

    public static void addMessage(String msg) {
        message.add(msg);
    }

    public void renderBox() {
        int width = this.sr.getScaledWidth();
        int height = this.sr.getScaledHeight();
        int buffer = height - 20;
        int counter = 0;
        if (message.size() != 0) {
            LWJGLUtil.drawRect(width - this.fr.getStringWidth(message.get(counter)) - 1, height - (this.fr.FONT_HEIGHT + 2), width, height, -16777216);
            this.fr.drawString(message.get(counter), width - this.fr.getStringWidth(message.get(counter)), height - (this.fr.FONT_HEIGHT + 1), -1);
        }
        ++counter;
        buffer -= 20;
    }
}

