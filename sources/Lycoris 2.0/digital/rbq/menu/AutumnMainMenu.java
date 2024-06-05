/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;
import digital.rbq.alt.gui.GuiAltManager;
import digital.rbq.menu.buttons.SimpleButton;
import digital.rbq.utils.render.RenderUtils;

public final class AutumnMainMenu
extends GuiScreen {
    private ResourceLocation background = new ResourceLocation("autumn/menu/background.png");

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new SimpleButton(0, this.width / 2, this.height / 2 - 40, "Singleplayer"));
        this.buttonList.add(new SimpleButton(1, this.width / 2, this.height / 2 - 20, "Multiplayer"));
        this.buttonList.add(new SimpleButton(2, this.width / 2, this.height / 2, "Alt Manager"));
        this.buttonList.add(new SimpleButton(3, this.width / 2, this.height / 2 + 20, "Settings"));
        this.buttonList.add(new SimpleButton(4, this.width / 2, this.height / 2 + 40, "Exit"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiAltManager(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                System.exit(0);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawImg(this.background, 0.0, 0.0, this.width, this.height);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

