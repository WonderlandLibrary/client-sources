package net.silentclient.client.gui.silentmainmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.LiteMainMenu;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.silentmainmenu.components.AccountPicker;
import net.silentclient.client.gui.silentmainmenu.components.MenuOption;
import net.silentclient.client.gui.theme.button.IButtonTheme;
import net.silentclient.client.gui.util.GlUtil;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.PromoController;
import net.silentclient.client.utils.SCTextureManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class MainMenuConcept extends SilentScreen {
    public static ResourceLocation imageLocation = null;
    public static BufferedImage image = null;
    public static boolean loading = false;
    public static boolean initSkin = false;
    private AccountPicker accountPicker;
    private final SimpleAnimation scaleAnimation = new SimpleAnimation(0f);

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.buttonList.clear();
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);

        this.buttonList.add(new MenuOption(1, 10, this.height / 2 - (14 * 3), 73, 14, "Singleplayer"));
        this.buttonList.add(new MenuOption(2, 10, this.height / 2 - (14 * 2), 73, 14, "Multiplayer"));
        this.buttonList.add(new MenuOption(3, 10, this.height / 2 - 14, 73, 14, "Options"));
        this.buttonList.add(new MenuOption(4, 10, this.height / 2, 73, 14, "Cosmetics"));
        this.buttonList.add(new MenuOption(5, 10, this.height / 2 + 14, 73, 14, "Lite Edition"));
        this.buttonList.add(new MenuOption(6, 10, this.height / 2 + 28, 73, 14, "Store"));
        this.buttonList.add(new IconButton(7, this.width - 25, 3, new ResourceLocation("silentclient/icons/cross.png")));
        if(this.buttonList.get(6) instanceof IconButton) {
            ((IconButton) this.buttonList.get(6)).setTheme(new IButtonTheme() {
                @Override
                public Color getBorderColor() {
                    return new Color(214, 213, 210, 0);
                }

                @Override
                public Color getBackgroundColor() {
                    return new Color(0, 0, 0, 0);
                }

                @Override
                public Color getTextColor() {
                    return new Color(255, 255, 255);
                }

                @Override
                public Color getHoveredBackgroundColor(int opacity) {
                    return new Color(255, 255, 255, opacity);
                }
            });
        }
        this.accountPicker = new AccountPicker(width - 120 + (62 / 2), height / 2 - 75 + 155);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        this.loadSkin();
        GlStateManager.disableAlpha();
        Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);

        if(imageLocation != null) {
            RenderUtil.drawImage(imageLocation, width - 120, height / 2 - (75), 62, 150, false);
        } else {
            RenderUtil.drawImage(new ResourceLocation("silentclient/images/steve.png"), width - 120, height / 2 - (75), 62, 150, false);
        }

        PromoController.getResponse().update();

        if(PromoController.getResponse().getCurrentPanel() != null) {
            if(PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
                if(MouseUtils.isInside(mouseX, mouseY, 10, 10, 109, 63)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                RenderUtils.drawRect(11, 11, 107, 61, -1);
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                GlUtil.scissor(12, 12, 12 + 105, 12 + 59);
                RenderUtil.drawImage(PromoController.getResponse().getCurrentPanel().getImageLocation(), 12 - scaleAnimation.getValue(), 12 - scaleAnimation.getValue(), 105 + (scaleAnimation.getValue() * 2), 59 + (scaleAnimation.getValue() * 2), false);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);

                if(MouseUtils.isInside(mouseX, mouseY, 12, 12, 105, 59)) {
                    scaleAnimation.setAnimation(10f, 15f);
                } else {
                    scaleAnimation.setAnimation(0f, 15f);
                }
            } else {
                PromoController.getResponse().getCurrentPanel().loadImage();
            }
        }


        Client.getInstance().getSilentFontRenderer().drawString(3, height - 14, "Silent Client 1.8.9", 12, SilentFontRenderer.FontType.TITLE);

        super.drawScreen(mouseX, mouseY, partialTicks);

        MouseCursorHandler.CursorType accountCursor = this.accountPicker.draw(mc, mouseX, mouseY);

        if(accountCursor != null) {
            cursorType = accountCursor;
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 4:
                mc.displayGuiScreen(new CosmeticsGui());
                break;
            case 5:
                Client.getInstance().getGlobalSettings().setLite(true);
                Client.getInstance().getGlobalSettings().save();
                mc.displayGuiScreen(new LiteMainMenu());
                break;
            case 6:
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/")});
                } catch (Throwable err) {
                    err.printStackTrace();
                }
                break;
            case 7:
                mc.shutdown();
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(MouseUtils.isInside(mouseX, mouseY, 10, 10, 109, 63) && PromoController.getResponse().getCurrentPanel() != null) {
            try {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(PromoController.getResponse().getCurrentPanel().getRedirectUrl())});
            } catch (Throwable err) {
                err.printStackTrace();
            }
            return;
        }

        this.accountPicker.onClick(mc, mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }

    // Functions
    private void loadSkin() {
        if(image == null && !loading && !initSkin) {
            loading = true;
            (new Thread("SkinThread") {
                public void run() {
                    BufferedImage skin = SCTextureManager.getImage(String.format("https://cache.silentclient.net/body/%s/left/1000.png", Client.getInstance().getAccount().getUsername()));
                    if(skin != null) {
                        MainMenuConcept.image = skin;
                    } else {
                        MainMenuConcept.initSkin = true;
                    }
                    MainMenuConcept.loading = false;
                }
            }).start();
        }
        if(image != null && !initSkin) {
            imageLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("player_skin_" + new Random().nextLong(), new DynamicTexture(image));
            initSkin = true;
        }
    }
}
