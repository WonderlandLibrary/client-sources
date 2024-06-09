package dev.vertic.ui.altmanager;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.ui.MenuBackground;
import dev.vertic.util.animation.impl.EaseBackIn;
import dev.vertic.util.animation.impl.SmoothStepAnimation;
import dev.vertic.util.render.BlurUtil;
import dev.vertic.util.render.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class AltManager extends GuiScreen implements Utils {

    private final Minecraft mc = Utils.mc;
    private final GuiTextField textField;
    private EaseBackIn anim;

    public AltManager() {
        textField = new GuiTextField(0, mc.fontRendererObj, 5, 5, 100, 100);
    }

    @Override
    public void initGui() {
        anim = new EaseBackIn(1000, 200, 1);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        double sW = sr.getScaledWidth_double();
        double sH = sr.getScaledHeight_double();
        double xCenter = sW / 2;
        double yCenter = sH / 2;
        double boxW = 200;
        double boxH = 35;
        int op = (int) anim.getOutput();
        if (op <= 0 || op >= 200) {
            anim.changeDirection();
        }
        MenuBackground.run(partialTicks, null);
        textField.setXPosition((int) (xCenter - boxW / 2));
        textField.setYPosition((int) (yCenter - (boxH*0.75)) );
        textField.setWidth((int) boxW);
        textField.setHeight((int) boxH);

        BlurUtil.doBlur(
                25,
                () -> {
                    textField.drawAltManagerBox(10, new Color(0, 0, 0, 100), new Color(255, 255, 255), 0);
                },
                () -> DrawUtil.roundedRect(xCenter - (boxW / 2), yCenter + (boxH*0.75), boxW, boxH, 10, new Color(0, 0, 0, 100))
        );

        textField.drawAltManagerBox(10, new Color(0, 0, 0, 100), new Color(255, 255, 255), op);
        DrawUtil.roundedRect(xCenter - (boxW / 2), yCenter + (boxH*0.75), boxW, boxH, 10, new Color(0, 0, 0, 100));
        if (mouseX > xCenter - (boxW/2) && mouseX < xCenter + (boxW/2) && mouseY > yCenter + (boxH*0.75) && mouseY < yCenter + boxH + (boxH*0.75)) {
            DrawUtil.roundedOutline(xCenter - (boxW / 2), yCenter + (boxH*0.75), boxW, boxH, 10, 1, new Color(200, 200, 200));
        }
        font24.drawCenteredStringWithShadow("Login", xCenter, yCenter + (boxH * 0.75) + (font24.getHeight()), -1);
        font32.drawCenteredStringWithShadow("Alt Manager", xCenter, yCenter - (boxH * 3), -1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        double sW = sr.getScaledWidth_double();
        double sH = sr.getScaledHeight_double();
        double xCenter = sW / 2;
        double yCenter = sH / 2;
        double boxW = 200;
        double boxH = 35;
        if (mouseX > xCenter - (boxW/2) && mouseX < xCenter + (boxW/2) && mouseY > yCenter + (boxH*0.75) && mouseY < yCenter + boxH + (boxH*0.75)) {
            mc.setSession(new Session(textField.getText(), "none", "none", "mojang"));
        }
        textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(Client.instance.getMainMenu());
        }
        if (textField.isFocused() && !textField.getText().isEmpty() && keyCode == Keyboard.KEY_RETURN) {
            mc.setSession(new Session(textField.getText(), "none", "none", "mojang"));
        } else {
            textField.textboxKeyTyped(typedChar, keyCode);
        }
    }
}
