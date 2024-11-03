package net.silentclient.client.cosmetics.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.Outfits;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.ScrollHelper;
import net.silentclient.client.utils.types.PlayerResponse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class OutfitsGui extends SilentScreen {
    private final GuiScreen parentScreen;
    private int outfitIndex = 0;
    private ScrollHelper scrollHelper = new ScrollHelper();

    public OutfitsGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }


    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        if(mc.thePlayer == null) {
            Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
        } else {
            MenuBlurUtils.loadBlur();
        }
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        this.buttonList.add(new IconButton(0, x + width - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        if(mc.thePlayer == null) {
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
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight((float) Math.ceil((Outfits.getOutfits().size() + 3) / 3) * 85);
        scrollHelper.setMaxScroll(height - 20);
        scrollHelper.setSpeed(200);
        scrollHelper.setFlag(true);
        float scrollY = scrollHelper.getScroll();
        RenderUtil.drawRoundedRect(x, y, width, height, 4, Theme.backgroundColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(x + 3, y + 3, "Outfits", 14, SilentFontRenderer.FontType.TITLE);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = height - 20;
        int translatedY = r.getScaledHeight() - y - 20 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, this.width * s, listHeight * s);
        int outfitX = x + 3;
        float outfitY = y + 20 + scrollY;
        int outfitIndex = 0;
        boolean isCreateHovered = MouseUtils.isInside(mouseX, mouseY, outfitX, outfitY, 80, 80);
        if(isCreateHovered) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
            RenderUtil.drawRoundedRect(outfitX, outfitY, 80, 80, 3, new Color(255, 255, 255,  30).getRGB());
        }
        RenderUtil.drawRoundedOutline(outfitX, outfitY, 80, 80, 3, 1, Theme.borderColor().getRGB());
        Client.getInstance().getSilentFontRenderer().drawCenteredString("Create New Outfit", outfitX + 40, (int) (outfitY + 40 - 6), 12, SilentFontRenderer.FontType.TITLE);
        outfitX += 83;
        outfitIndex += 1;
        this.outfitIndex = 1;
        for(Outfits.Outfit outfit : Outfits.getOutfits()) {
            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, outfitX, outfitY, 80, 80) && !MouseUtils.isInside(mouseX, mouseY, outfitX + 80 - 3 - 10, outfitY + 3, 10, 10);
            if(isHovered) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
                RenderUtil.drawRoundedRect(outfitX, outfitY, 80, 80, 3, new Color(255, 255, 255,  30).getRGB());
            }
            if(MouseUtils.isInside(mouseX, mouseY, outfitX + 80 - 3 - 10, outfitY + 3, 10, 10)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
            RenderUtil.drawRoundedOutline(outfitX, outfitY, 80, 80, 3, 1, Theme.borderColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawString(outfit.name, outfitX + 3, (int) (outfitY + 3), 12, SilentFontRenderer.FontType.TITLE, 64);
            RenderUtil.drawImage(new ResourceLocation("silentclient/icons/trash-icon.png"), outfitX + 80 - 3 - 10, outfitY + 3, 10, 10);
            int cosmeticY = (int) (outfitY + 18);

            if(outfit.selected_cape != 0 && Client.getInstance().getCosmetics().getMyCapes().stream().filter((c) -> c.id == outfit.selected_cape).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyCapes().stream().filter((c) -> c.id == outfit.selected_cape).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_wings != 0 && Client.getInstance().getCosmetics().getMyWings().stream().filter((c) -> c.id == outfit.selected_wings).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyWings().stream().filter((c) -> c.id == outfit.selected_wings).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_bandana != 0 && Client.getInstance().getCosmetics().getMyBandanas().stream().filter((c) -> c.id == outfit.selected_bandana).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyBandanas().stream().filter((c) -> c.id == outfit.selected_bandana).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_hat != 0 && Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_hat).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_hat).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_neck != 0 && Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_neck).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_neck).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_mask != 0 && Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_mask).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyHats().stream().filter((c) -> c.id == outfit.selected_mask).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_shield != 0 && Client.getInstance().getCosmetics().getMyShields().stream().filter((c) -> c.id == outfit.selected_shield).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyShields().stream().filter((c) -> c.id == outfit.selected_shield).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
                cosmeticY += 10;
            }

            if(outfit.selected_icon != 0 && Client.getInstance().getCosmetics().getMyIcons().stream().filter((c) -> c.id == outfit.selected_icon).findFirst().isPresent() && (cosmeticY - outfitY) <= 62) {
                PlayerResponse.Account.Cosmetics.CosmeticItem item = Client.getInstance().getCosmetics().getMyIcons().stream().filter((c) -> c.id == outfit.selected_icon).findFirst().get();

                Client.getInstance().getSilentFontRenderer().drawString(item.name, outfitX + 3, cosmeticY, 10, SilentFontRenderer.FontType.TITLE, 75);
            }

            this.outfitIndex += 1;
            outfitIndex += 1;
            if(outfitIndex == 3) {
                outfitIndex = 0;
                outfitX = x + 3;
                outfitY += 85;
            } else {
                outfitX += 83;
            }
        }
        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 0) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int width = 255;
        int height = 200;
        int x = this.width / 2 - (width / 2);
        int y = this.height / 2 - (height / 2);
        int outfitX = x + 3;
        float outfitY = (int) (y + 20 + scrollHelper.getScroll());
        int outfitIndex = 0;
        if(MouseUtils.isInside(mouseX, mouseY, outfitX, outfitY, 80, 80)) {
            mc.displayGuiScreen(new NewOutfitModal(this));
            return;
        }
        outfitX += 83;
        outfitIndex += 1;
        for(Outfits.Outfit outfit : Outfits.getOutfits()) {
            boolean isHovered = MouseUtils.isInside(mouseX, mouseY, outfitX, outfitY, 80, 80) && !MouseUtils.isInside(mouseX, mouseY, outfitX + 80 - 3 - 10, outfitY + 3, 10, 10);

            if(isHovered) {
                Outfits.loadOutfit(outfit);
                mc.displayGuiScreen(parentScreen);
                break;
            }

            if(MouseUtils.isInside(mouseX, mouseY, outfitX + 80 - 3 - 10, outfitY + 3, 10, 10)) {
                Outfits.deleteOutfit(outfit);
                break;
            }

            outfitIndex += 1;
            if(outfitIndex == 3) {
                outfitIndex = 0;
                outfitX = x + 3;
                outfitY += 85;
            } else {
                outfitX += 83;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(mc.thePlayer == null) {
            Client.backgroundPanorama.tickPanorama();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        MenuBlurUtils.unloadBlur();
    }
}
