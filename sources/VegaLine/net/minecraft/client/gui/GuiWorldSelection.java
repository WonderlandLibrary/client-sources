/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import dev.intave.viamcp.ViaMCP;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.impl.Panic;

public class GuiWorldSelection
extends GuiScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    protected GuiScreen prevScreen;
    protected String title = "Select world";
    private String worldVersTooltip;
    private GuiButton deleteButton;
    private GuiButton selectButton;
    private GuiButton renameButton;
    private GuiButton copyButton;
    private GuiListWorldSelection selectionList;

    public GuiWorldSelection(GuiScreen screenIn) {
        this.prevScreen = screenIn;
    }

    @Override
    public void initGui() {
        ClientRP.getInstance().getDiscordRP().update("\u0412 \u043c\u0435\u043d\u044e \u0432\u044b\u0431\u043e\u0440\u0430 \u043e\u0434\u0438\u043d\u043e\u0447\u043d\u043e\u0433\u043e \u043c\u0438\u0440\u0430", "\u0412\u044b\u0431\u0438\u0440\u0430\u0435\u0442 \u043c\u0438\u0440");
        this.title = I18n.format("selectWorld.title", new Object[0]);
        this.selectionList = new GuiListWorldSelection(this, this.mc, width, height, 32, height - 64, 36);
        this.postInit();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.selectionList.handleMouseInput();
    }

    public void postInit() {
        this.selectButton = this.addButton(new GuiButton(1, width / 2 - 154, height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
        this.addButton(new GuiButton(3, width / 2 + 4, height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
        this.renameButton = this.addButton(new GuiButton(4, width / 2 - 154, height - 28, 72, 20, I18n.format("selectWorld.edit", new Object[0])));
        this.deleteButton = this.addButton(new GuiButton(2, width / 2 - 76, height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
        this.copyButton = this.addButton(new GuiButton(5, width / 2 + 4, height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
        this.addButton(new GuiButton(0, width / 2 + 82, height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
        this.renameButton.enabled = false;
        this.copyButton.enabled = false;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            GuiListWorldSelectionEntry guilistworldselectionentry = this.selectionList.getSelectedWorld();
            if (button.id == 2) {
                if (guilistworldselectionentry != null) {
                    guilistworldselectionentry.deleteWorld();
                }
            } else if (button.id == 1) {
                if (guilistworldselectionentry != null) {
                    guilistworldselectionentry.joinWorld();
                }
            } else if (button.id == 3) {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
            } else if (button.id == 4) {
                if (guilistworldselectionentry != null) {
                    guilistworldselectionentry.editWorld();
                }
            } else if (button.id == 0) {
                this.mc.displayGuiScreen(this.prevScreen);
            } else if (button.id == 5 && guilistworldselectionentry != null) {
                guilistworldselectionentry.recreateWorld();
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.worldVersTooltip = null;
        this.selectionList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!Panic.stop) {
            ViaMCP.INSTANCE.getViaPanel().drawPanel(1.0f, mouseX, mouseY);
        }
        if (this.worldVersTooltip != null) {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.worldVersTooltip)), mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!Panic.stop) {
            ViaMCP.INSTANCE.getViaPanel().mouseClick(mouseX, mouseY, mouseButton);
        }
        this.selectionList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.selectionList.mouseReleased(mouseX, mouseY, state);
    }

    public void setVersionTooltip(String p_184861_1_) {
        this.worldVersTooltip = p_184861_1_;
    }

    public void selectWorld(@Nullable GuiListWorldSelectionEntry entry) {
        boolean flag;
        this.selectButton.enabled = flag = entry != null;
        this.deleteButton.enabled = flag;
        this.renameButton.enabled = flag;
        this.copyButton.enabled = flag;
    }
}

