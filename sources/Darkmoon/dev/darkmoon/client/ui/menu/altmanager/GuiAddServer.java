package dev.darkmoon.client.ui.menu.altmanager;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.manager.quickjoin.ServerInstance;
import dev.darkmoon.client.ui.menu.widgets.CustomButton;
import dev.darkmoon.client.ui.menu.widgets.TextField;
import dev.darkmoon.client.utility.math.Vec2i;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import dev.darkmoon.client.utility.render.particle.Particle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiAddServer extends GuiScreen {
    private TextField serverNameField;
    private TextField ipField;

    private final ArrayList<Particle> particles = new ArrayList<>();

    private final GuiAltManager guiAltManager;

    protected GuiAddServer() {
        this.guiAltManager = new GuiAltManager();
    }

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());

        particles.clear();
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle());
        }

        Keyboard.enableRepeatEvents(true);

        this.serverNameField = new TextField(this.eventButton, Fonts.mntsb17, scaledWidth / 2 - 100, scaledHeight / 2 - 40, 200, 20);
        this.ipField = new TextField(this.eventButton, Fonts.mntsb17, scaledWidth / 2 - 100, scaledHeight / 2 + 5, 200, 20);

        this.addButton(new CustomButton(0, scaledWidth / 2 - 100, scaledHeight / 2 + 40, 98, 20, "Добавить"));
        this.addButton(new CustomButton(1, scaledWidth / 2 + 2, scaledHeight / 2 + 40, 98, 20, "Вернуться"));

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse(mouseX, mouseY, scaledResolution);
        DarkMoon.getInstance().getScaleMath().pushScale();

        RenderUtility.drawRect(0, 0, scaledWidth, scaledHeight, new Color(20, 20, 20).getRGB());

        particles.forEach(particle -> {
            particle.update(mouse.getX(), mouse.getY(), scaledWidth, scaledHeight);
            particle.draw(particles, mouse.getX(), mouse.getY());
        });

        Fonts.mntsb17.drawStringWithShadow("Название", scaledWidth / 2f - 100, scaledHeight / 2f - 52, -7829368);
        this.serverNameField.drawTextBox();

        Fonts.mntsb17.drawStringWithShadow("IP", scaledWidth / 2f - 100, scaledHeight / 2f - 7, -7829368);
        this.ipField.drawTextBox();

        super.drawScreen(mouse.getX(), mouse.getY(), partialTicks);
        DarkMoon.getInstance().getScaleMath().popScale();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                if (!(StringUtils.deleteWhitespace(serverNameField.getText()).equalsIgnoreCase("") || StringUtils.deleteWhitespace(ipField.getText()).equalsIgnoreCase(""))) {
                    DarkMoon.getInstance().getServerManager().addServer(new ServerInstance(serverNameField.getText(), ipField.getText()));
                    mc.displayGuiScreen(guiAltManager);
                }
                break;
            case 1:
                this.mc.displayGuiScreen(guiAltManager);
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new GuiAltManager());
            return;
        }

        this.serverNameField.textboxKeyTyped(typedChar, keyCode);
        this.ipField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t' && (this.serverNameField.isFocused() || this.ipField.isFocused())) {
            this.serverNameField.setFocused(!this.serverNameField.isFocused());
            this.ipField.setFocused(!this.ipField.isFocused());
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse(mouseX, mouseY, scaledResolution);

        this.serverNameField.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);
        this.ipField.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);

        super.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);
    }
}
