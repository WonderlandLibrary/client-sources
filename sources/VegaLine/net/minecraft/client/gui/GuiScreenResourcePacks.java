/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackListEntryServer;
import net.minecraft.client.resources.ResourcePackRepository;
import org.lwjgl.Sys;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class GuiScreenResourcePacks
extends GuiScreen {
    private final GuiScreen parentScreen;
    private List<ResourcePackListEntry> availableResourcePacks;
    private List<ResourcePackListEntry> selectedResourcePacks;
    private GuiResourcePackAvailable availableResourcePacksList;
    private GuiResourcePackSelected selectedResourcePacksList;
    private boolean changed;

    public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done", new Object[0])));
        if (!this.changed) {
            this.availableResourcePacks = Lists.newArrayList();
            this.selectedResourcePacks = Lists.newArrayList();
            ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
            resourcepackrepository.updateRepositoryEntriesAll();
            ArrayList<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
            list.removeAll(resourcepackrepository.getRepositoryEntries());
            for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
                this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
            }
            ResourcePackRepository.Entry resourcepackrepository$entry2 = resourcepackrepository.getResourcePackEntry();
            if (resourcepackrepository$entry2 != null) {
                this.selectedResourcePacks.add(new ResourcePackListEntryServer(this, resourcepackrepository.getResourcePackInstance()));
            }
            for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries())) {
                this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
            }
            this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
        }
        this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, height, this.availableResourcePacks);
        this.availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
        this.availableResourcePacksList.registerScrollButtons(7, 8);
        this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, height, this.selectedResourcePacks);
        this.selectedResourcePacksList.setSlotXBoundsFromLeft(width / 2 + 4);
        this.selectedResourcePacksList.registerScrollButtons(7, 8);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.selectedResourcePacksList.handleMouseInput();
        this.availableResourcePacksList.handleMouseInput();
    }

    public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
        return this.selectedResourcePacks.contains(p_146961_1_);
    }

    public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
        return this.hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
    }

    public List<ResourcePackListEntry> getAvailableResourcePacks() {
        return this.availableResourcePacks;
    }

    public List<ResourcePackListEntry> getSelectedResourcePacks() {
        return this.selectedResourcePacks;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 2) {
                if (Panic.stop) {
                    Sys.openURL(new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft", "resourcepacks").getAbsolutePath());
                } else {
                    OpenGlHelper.openFile(this.mc.getResourcePackRepository().getDirResourcepacks());
                }
            } else if (button.id == 1) {
                if (this.changed) {
                    ArrayList<ResourcePackRepository.Entry> list = Lists.newArrayList();
                    for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
                        if (!(resourcepacklistentry instanceof ResourcePackListEntryFound)) continue;
                        list.add(((ResourcePackListEntryFound)resourcepacklistentry).getResourcePackEntry());
                    }
                    Collections.reverse(list);
                    this.mc.getResourcePackRepository().setRepositories(list);
                    this.mc.gameSettings.resourcePacks.clear();
                    this.mc.gameSettings.incompatibleResourcePacks.clear();
                    for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
                        this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
                        if (resourcepackrepository$entry.getPackFormat() == 3) continue;
                        this.mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
                    }
                    this.mc.gameSettings.saveOptions();
                    this.mc.refreshResources();
                }
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
        this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiScreen guiScreen;
        if (Panic.stop || Client.moduleManager == null || !ComfortUi.get.actived || this.mc.world == null) {
            this.drawBackground(0);
        }
        if (!Panic.stop && (guiScreen = this.parentScreen) instanceof GuiOptions) {
            GuiOptions op = (GuiOptions)guiScreen;
            if (op.lastScreen instanceof GuiMainMenu) {
                RenderUtils.drawScreenShaderBackground(new ScaledResolution(this.mc), mouseX, mouseY);
            }
        }
        if (Client.moduleManager != null && ComfortUi.get.actived && this.mc.world != null) {
            ScaledResolution sr = new ScaledResolution(this.mc);
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect(sr.getScaledWidth() / 2 - 204, 32.0, sr.getScaledWidth() / 2 + 204, sr.getScaledHeight() - 52, -1);
            StencilUtil.readStencilBuffer(1);
        }
        this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), width / 2, 16, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), width / 2 - 77, height - 26, 0x808080);
        if (Client.moduleManager != null && ComfortUi.get.actived && this.mc.world != null) {
            StencilUtil.uninitStencilBuffer();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void markChanged() {
        this.changed = true;
    }
}

