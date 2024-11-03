package net.silentclient.client.gui.resourcepacks;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.elements.TooltipIconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.*;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SilentResourcePacksGui extends SilentScreen {
    private final GuiScreen parentScreen;
    private List<ResourcePackRepository.Entry> availableResourcePacks;
    private List<ResourcePackRepository.Entry> selectedResourcePacks;
    private boolean changed = false;
    private int blockX = 0;
    private int blockY = 0;
    private int blockWidth = 0;
    private int blockHeight = 0;
    private ScrollHelper scrollHelper = new ScrollHelper();
    private ScrollHelper scrollHelper2 = new ScrollHelper();
    private Random random = new Random();

    public SilentResourcePacksGui(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.defaultCursor = false;
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
        this.buttonList.clear();
        this.silentInputs.clear();
        blockWidth = 400;
        blockHeight = height - 20;
        blockX = (width / 2) - (blockWidth / 2);
        blockY = 10;
        this.buttonList.add(new IconButton(1, blockX + blockWidth - 14 - 5, blockY + 5, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
        if(mc.thePlayer != null) {
            this.buttonList.add(new TooltipIconButton(2, blockX + blockWidth - 14 - 5, blockY + blockHeight - 5 - 14, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/lightoverlay.png"), "Toggle Background Panorama"));
        }
        this.buttonList.add(new TooltipIconButton(6, blockX + 5, blockY + blockHeight - 5 - 14, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/dice.png"), "Random Resource Pack"));
        this.buttonList.add(new Button(3, blockX + 40, blockY + blockHeight - 5 - 14, 100, 14, "Open Pack Folder"));
        this.buttonList.add(new Button(4, blockX + 40 + 100 + 5, blockY + blockHeight - 5 - 14, 100, 14, "Apply"));
        this.buttonList.add(new Button(5, blockX + 40 + 100 + 5 + 100 + 5, blockY + blockHeight - 5 - 14, 100, 14, "Done"));
        this.silentInputs.add(new Input("Search"));
        if(!this.changed) {
            this.availableResourcePacks = Lists.newArrayList();
            this.selectedResourcePacks = Lists.newArrayList();
            ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
            resourcepackrepository.updateRepositoryEntriesAll();
            List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
            list.removeAll(resourcepackrepository.getRepositoryEntries());
            for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
            {
                this.availableResourcePacks.add(resourcepackrepository$entry);
            }

            for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
            {
                this.selectedResourcePacks.add(resourcepackrepository$entry1);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        if(mc.thePlayer == null || Client.getInstance().getGlobalSettings().isPacksPanoramaEnabled()) {
            GlStateManager.disableAlpha();
            Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
            if(Client.getInstance().getGlobalSettings().isLite()) {
                this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
            } else {
                this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
            }
        } else {
            MenuBlurUtils.renderBackground(this);
        }
        RenderUtils.drawRect(blockX, blockY, blockWidth, blockHeight, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("Resource Packs", blockX + 5, blockY + 5, 14, SilentFontRenderer.FontType.TITLE);

        MouseCursorHandler.CursorType cursorType1 = this.drawAvailableResourcePacks(blockX + 5, blockY + 14 + 5 + 2, mouseX, mouseY);
        if(cursorType1 != null) {
            cursorType = cursorType1;
        }
        MouseCursorHandler.CursorType cursorType2 = this.drawActiveResourcePacks(blockX + blockWidth - 193 - 5, blockY + 14 + 5 + 2, mouseX, mouseY);
        if(cursorType2 != null) {
            cursorType = cursorType2;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
    }

    private MouseCursorHandler.CursorType drawAvailableResourcePacks(float x, float y, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        RenderUtil.drawRoundedOutline(x, y, 193, blockHeight - 45, 3, 1, Theme.borderColor().getRGB());
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight(availableResourcePacks.size() * 37 + 19);
        scrollHelper.setMaxScroll(blockHeight - 45);
        scrollHelper.setSpeed(200);

        if(MouseUtils.isInside(mouseX, mouseY, x, y, 193, blockHeight - 45)) {
            scrollHelper.setFlag(true);
        } else {
            scrollHelper.setFlag(false);
        }

        float scrollY = scrollHelper.getScroll();

        float itemY = y + 19 + scrollY;

        Scissor.start((int) x, (int) y, 193, blockHeight - 45);
        Client.getInstance().getSilentFontRenderer().drawString("Available", x + 3, y + 3 + scrollY, 14, SilentFontRenderer.FontType.TITLE);
        this.silentInputs.get(0).render(mouseX, mouseY, x + 193 - 3 - 100, y + 2 + scrollY, 100, true);

        for(ResourcePackRepository.Entry entry : availableResourcePacks) {
            if((!this.silentInputs.get(0).getValue().trim().equals("") && !entry.getResourcePackName().toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()))) {
                continue;
            }
            if(GuiUtils.blockInOtherBlock(x + 3, itemY, 187, 35, x, y, 193, blockHeight - 45)) {
                int i = entry.func_183027_f();
                boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, itemY, 187, 35);
                RenderUtil.drawRoundedOutline(x + 3, itemY, 187, 35, 3, 1, i != 1 ? Color.RED.getRGB() : Theme.borderColor().getRGB());
                if(isHovered) {
                    RenderUtil.drawRoundedRect(x + 3, itemY, 187, 35, 3, new Color(255, 255, 255,  30).getRGB());
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                entry.bindTexturePackIcon(mc.getTextureManager());
                RenderUtil.drawImage(null, x + 5, itemY + 2, 31, 31, false);
                String s = FilenameUtils.getBaseName(entry.getResourcePackName());
                int i1 = this.mc.fontRendererObj.getStringWidth(s);

                if (i1 > 153)
                {
                    s = this.mc.fontRendererObj.trimStringToWidth(s, 153 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
                }

                this.mc.fontRendererObj.drawStringWithShadow(s, x + 5 + 31 + 3, itemY + 3, 16777215);
                List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(entry.getTexturePackDescription(), 153);

                for (int l = 0; l < 2 && l < list.size(); ++l)
                {
                    this.mc.fontRendererObj.drawStringWithShadow(list.get(l), x + 5 + 31 + 3, itemY + 3 + 12 + 10 * l, 8421504);
                }
                if(!Client.getInstance().getGlobalSettings().getUsedResourcePacks().contains(entry.getResourcePackName())) {
                    RenderUtil.drawRoundedRect(x + 187 - 23, itemY + 35 - 12, 23, 10, 8, new Color(255, 255, 255).getRGB());
                    ColorUtils.setColor(new Color(0, 0, 0).getRGB());
                    Client.getInstance().getSilentFontRenderer().drawCenteredString("NEW", (int) x + 187 - 23 + (23 / 2), (int) (itemY + 35 - 12 + 10 - 9), 8, SilentFontRenderer.FontType.HEADER);
                    ColorUtils.resetColor();
                }
            }
            itemY += 37;
        }
        Scissor.end();

        return cursorType;
    }

    private void availableResourcePacksClick(float x, float y, int mouseX, int mouseY) {
        float itemY = y + 19 + scrollHelper.getScroll();
        if(!GuiUtils.blockInOtherBlock(mouseX, mouseY, 2, 2, x, y, 193, blockHeight - 45)) {
            return;
        }

        for(ResourcePackRepository.Entry entry : availableResourcePacks) {
            if((!this.silentInputs.get(0).getValue().trim().equals("") && !entry.getResourcePackName().toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()))) {
                continue;
            }
            if(GuiUtils.blockInOtherBlock(x + 3, itemY, 187, 35, x, y, 193, blockHeight - 45)) {
                boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, itemY, 187, 35);
                if(isHovered) {
                    availableResourcePacks.remove(entry);
                    selectedResourcePacks.add(0, entry);
                    this.markChanged();
                    break;
                }
            }
            itemY += 37;
        }
    }

    private MouseCursorHandler.CursorType drawActiveResourcePacks(float x, float y, int mouseX, int mouseY) {
        MouseCursorHandler.CursorType cursorType = null;
        RenderUtil.drawRoundedOutline(x, y, 193, blockHeight - 45, 3, 1, Theme.borderColor().getRGB());
        scrollHelper2.setStep(5);
        scrollHelper2.setElementsHeight(selectedResourcePacks.size() * 37 + 19);
        scrollHelper2.setMaxScroll(blockHeight - 45);
        scrollHelper.setSpeed(200);

        if(MouseUtils.isInside(mouseX, mouseY, x, y, 193, blockHeight - 45)) {
            scrollHelper2.setFlag(true);
        } else {
            scrollHelper2.setFlag(false);
        }
        float scrollY = scrollHelper2.getScroll();
        Scissor.start((int) x, (int) y, 193, blockHeight - 45);
        Client.getInstance().getSilentFontRenderer().drawString("Active", x + 3, y + 3 + scrollY, 14, SilentFontRenderer.FontType.TITLE);

        float itemY = y + 19 + scrollY;
        int packIndex = 0;

        for(ResourcePackRepository.Entry entry : selectedResourcePacks) {
            if(GuiUtils.blockInOtherBlock(x + 3, itemY, 187, 35, x, y, 193, blockHeight - 45)) {
                int i = entry.func_183027_f();
                RenderUtil.drawRoundedOutline(x + 3, itemY, 187, 35, 3, 1, i != 1 ? Color.RED.getRGB() : Theme.borderColor().getRGB());
                boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, itemY, 187, 35);
                if(isHovered) {
                    RenderUtil.drawRoundedRect(x + 3, itemY, 187, 35, 3, new Color(255, 255, 255,  30).getRGB());
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                entry.bindTexturePackIcon(mc.getTextureManager());
                RenderUtil.drawImage(null, x + 5, itemY + 2, 31, 31, false);
                if(MouseUtils.isInside(mouseX, mouseY, x + 5, itemY + 2, 31, 31)) {
                    if(this.canUpSwap(packIndex)) {
                        RenderUtil.drawImage(new ResourceLocation("silentclient/icons/page-up.png"), x + 5 + (31 / 2) - (15 / 2), itemY + 2, 15, 15, true, MouseUtils.isInside(mouseX, mouseY, x + 5 + (31 / 2) - (15 / 2), itemY + 2, 15, 15) ? new Color(255, 255, 255).getRGB() : Theme.borderColor().getRGB());
                    }

                    if(this.canDownSwap(packIndex)) {
                        RenderUtil.drawImage(new ResourceLocation("silentclient/icons/page-down.png"), x + 5 + (31 / 2) - (15 / 2), itemY + 2 + 31 - 16, 15, 15, true, MouseUtils.isInside(mouseX, mouseY, x + 5 + (31 / 2) - (15 / 2), itemY + 2 + 31 - 16, 15, 15) ? new Color(255, 255, 255).getRGB() : Theme.borderColor().getRGB());
                    }
                }
                String s = FilenameUtils.getBaseName(entry.getResourcePackName());
                int i1 = this.mc.fontRendererObj.getStringWidth(s);

                if (i1 > 153)
                {
                    s = this.mc.fontRendererObj.trimStringToWidth(s, 153 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
                }

                this.mc.fontRendererObj.drawStringWithShadow(s, x + 5 + 31 + 3, itemY + 3, 16777215);
                List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(entry.getTexturePackDescription(), 153);

                for (int l = 0; l < 2 && l < list.size(); ++l)
                {
                    this.mc.fontRendererObj.drawStringWithShadow(list.get(l), x + 5 + 31 + 3, itemY + 3 + 12 + 10 * l, 8421504);
                }
                if(!Client.getInstance().getGlobalSettings().getUsedResourcePacks().contains(entry.getResourcePackName())) {
                    RenderUtil.drawRoundedRect(x + 187 - 23, itemY + 35 - 12, 23, 10, 8, new Color(255, 255, 255).getRGB());
                    ColorUtils.setColor(new Color(0, 0, 0).getRGB());
                    Client.getInstance().getSilentFontRenderer().drawCenteredString("NEW", (int) x + 187 - 23 + (23 / 2), (int) (itemY + 35 - 12 + 10 - 9), 8, SilentFontRenderer.FontType.HEADER);
                    ColorUtils.resetColor();
                }
            }
            itemY += 37;
            packIndex++;
        }

        Scissor.end();
        return cursorType;
    }

    public void activeResourcePacksClick(float x, float y, int mouseX, int mouseY) {
        float itemY = y + 19 + scrollHelper2.getScroll();
        int packIndex = 0;

        if(!GuiUtils.blockInOtherBlock(mouseX, mouseY, 2, 2, x, y, 193, blockHeight - 45)) {
            return;
        }

        for(ResourcePackRepository.Entry entry : selectedResourcePacks) {
            if(GuiUtils.blockInOtherBlock(x + 3, itemY, 187, 35, x, y, 193, blockHeight - 45)) {
                int i = entry.func_183027_f();
                RenderUtil.drawRoundedOutline(x + 3, itemY, 187, 35, 3, 1, i != 1 ? Color.RED.getRGB() : Theme.borderColor().getRGB());
                boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, itemY, 187, 35);
                if(isHovered) {
                    if(MouseUtils.isInside(mouseX, mouseY, x + 5, itemY + 2, 31, 31)) {
                        if(this.canUpSwap(packIndex) && MouseUtils.isInside(mouseX, mouseY, x + 5 + (31 / 2) - (15 / 2), itemY + 2, 15, 15)) {
                            List<ResourcePackRepository.Entry> list1 = this.selectedResourcePacks;
                            int k = list1.indexOf(entry);
                            list1.remove(entry);
                            list1.add(k - 1, entry);
                            this.markChanged();
                            break;
                        }

                        if(this.canDownSwap(packIndex) && MouseUtils.isInside(mouseX, mouseY, x + 5 + (31 / 2) - (15 / 2), itemY + 2 + 31 - 16, 15, 15)) {
                            List<ResourcePackRepository.Entry> list = this.selectedResourcePacks;
                            int i2 = list.indexOf(entry);
                            list.remove(entry);
                            list.add(i2 + 1, entry);
                            this.markChanged();
                            break;
                        }
                    }
                    selectedResourcePacks.remove(entry);
                    availableResourcePacks.add(0, entry);
                    this.markChanged();
                    break;
                }
            }
            itemY += 37;
            packIndex++;
        }
    }

    public void apply() {
        if (this.changed)
        {
            this.scrollHelper2.resetScroll();
            this.scrollHelper.resetScroll();
            List<ResourcePackRepository.Entry> list = Lists.<ResourcePackRepository.Entry>newArrayList();

            for (ResourcePackRepository.Entry resourcepacklistentry : this.selectedResourcePacks)
            {
                list.add(resourcepacklistentry);
            }

            Collections.reverse(list);
            this.mc.getResourcePackRepository().setRepositories(list);
            this.mc.gameSettings.resourcePacks.clear();
            this.mc.gameSettings.incompatibleResourcePacks.clear();

            for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
            {
                this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
                Client.getInstance().getGlobalSettings().addToUsedResourcePacks(resourcepackrepository$entry.getResourcePackName());
                if (resourcepackrepository$entry.func_183027_f() != 1)
                {
                    this.mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
                }
            }

            this.mc.gameSettings.saveOptions();
            this.mc.refreshResources();
            this.initGui();
            this.scrollHelper2.resetScroll();
            this.scrollHelper.resetScroll();
            Client.getInstance().getGlobalSettings().save();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
            case 2:
                Client.getInstance().getGlobalSettings().setPacksPanoramaEnabled(!Client.getInstance().getGlobalSettings().isPacksPanoramaEnabled());
                Client.getInstance().getGlobalSettings().save();
                break;
            case 3:
                File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
                String s = file1.getAbsolutePath();

                if (Util.getOSType() == Util.EnumOS.OSX)
                {
                    try
                    {
                        Client.logger.info(s);
                        Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                        return;
                    }
                    catch (IOException ioexception1)
                    {
                        Client.logger.error("Couldn't open file", ioexception1);
                    }
                }
                else if (Util.getOSType() == Util.EnumOS.WINDOWS)
                {
                    String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);

                    try
                    {
                        Runtime.getRuntime().exec(s1);
                        return;
                    }
                    catch (IOException ioexception)
                    {
                        Client.logger.error("Couldn't open file", ioexception);
                    }
                }

                boolean flag = false;

                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, file1.toURI());
                }
                catch (Throwable throwable)
                {
                    Client.logger.error("Couldn't open link", throwable);
                    flag = true;
                }

                if (flag)
                {
                    Client.logger.info("Opening via system class!");
                    Sys.openURL("file://" + s);
                }
                break;
            case 4:
                this.apply();
                break;
            case 5:
                this.apply();
                mc.displayGuiScreen(parentScreen);
                break;
            case 6:
                this.randomPack();
                break;
        }
    }

    private void randomPack() {
        if(!this.selectedResourcePacks.isEmpty()) {
            for(ResourcePackRepository.Entry entry : this.selectedResourcePacks) {
                this.availableResourcePacks.add(0, entry);
            }
            this.selectedResourcePacks.clear();
        }

        ArrayList<ResourcePackRepository.Entry> newPacks = new ArrayList<>();
        for(ResourcePackRepository.Entry entry : this.availableResourcePacks) {
            if(!Client.getInstance().getGlobalSettings().getUsedResourcePacks().contains(entry.getResourcePackName())) {
                newPacks.add(entry);
            }
        }
        ResourcePackRepository.Entry randomPack;

        if(!newPacks.isEmpty()) {
            randomPack = newPacks.get(random.nextInt(newPacks.size()));
        } else {
            randomPack = this.availableResourcePacks.get(random.nextInt(this.availableResourcePacks.size()));
        }

        if(randomPack != null) {
            this.selectedResourcePacks.add(0, randomPack);
            this.availableResourcePacks.remove(randomPack);
        }

        this.markChanged();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.silentInputs.get(0).onClick(mouseX, mouseY, blockX + 5 + 193 - 3 - 100, blockY + 14 + 5 + 2 + 2, 100, true);
        this.availableResourcePacksClick(blockX + 5, blockY + 14 + 5 + 2, mouseX, mouseY);
        this.activeResourcePacksClick(blockX + blockWidth - 193 - 5, blockY + 14 + 5 + 2, mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
        if(this.silentInputs.get(0).isFocused()) {
            this.scrollHelper.resetScroll();
        }
    }

    public void markChanged()
    {
        this.changed = true;
    }

    public boolean canUpSwap(int index)
    {
        return index > 0;
    }

    public boolean canDownSwap(int index)
    {
        return index < this.selectedResourcePacks.size() - 1;
    }
}
