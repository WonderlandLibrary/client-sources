package dev.darkmoon.client.ui.menu.altmanager;

import dev.darkmoon.client.ui.menu.main.GuiMainMenuElement;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.manager.quickjoin.ServerInstance;
import dev.darkmoon.client.ui.menu.altmanager.alt.Alt;
import dev.darkmoon.client.ui.menu.altmanager.alt.AltLoginThread;
import dev.darkmoon.client.ui.menu.altmanager.alt.AltManager;
import dev.darkmoon.client.ui.menu.widgets.CustomButton;
import dev.darkmoon.client.utility.math.RandomUtility;
import dev.darkmoon.client.utility.math.Vec2i;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.StencilUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiAltManager extends GuiScreen {
    public Alt selectedAlt = null;
    private AltLoginThread loginThread;

    public int scrollY = 0;
    private ResourceLocation resourceLocation;

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());

        this.addButton(new CustomButton(0, scaledWidth / 2 - 121, scaledHeight - 48, 57, 20, "Добавить"));
        this.addButton(new CustomButton(1, scaledWidth / 2 - 59, scaledHeight - 48, 57, 20, "Случайный"));
        this.addButton(new CustomButton(2, scaledWidth / 2 + 3, scaledHeight - 48, 57, 20, "Удалить"));
        this.addButton(new CustomButton(3, scaledWidth / 2 + 65, scaledHeight - 48, 57, 20, "Очистить"));
        this.addButton(new CustomButton(4, scaledWidth / 2 - 121, scaledHeight - 24, 243, 20, "Вернуться"));
        this.addButton(new CustomButton(5, scaledWidth / 2 - 208, scaledHeight - 24, 82, 20, "Добавить сервер"));

        super.initGui();
    }

    private void getDownloadImageSkin(Alt alt, String username) {
        TextureManager textureManager = mc.getTextureManager();
        textureManager.getTexture(alt.getSkin());
        ThreadDownloadImageData textureObject = new ThreadDownloadImageData(null, String.format("https://minotar.net/skin/%s", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(AbstractClientPlayer.getOfflineUUID(username)), new ImageBufferDownload());
        textureManager.loadTexture(alt.getSkin(), textureObject);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiAddAlt());
                break;
            case 1:
                String randomName = RandomUtility.randomString();
                AltManager.addAccount(new Alt(randomName, "", Alt.getCurrentDate()));
                mc.session = new Session(randomName, "", "", "mojang");
                break;
            case 2:
                if (loginThread != null) {
                    loginThread = null;
                }

                AltManager.removeAccount(selectedAlt);
                selectedAlt = null;
                break;
            case 3:
                AltManager.clearAccounts();
                break;
            case 4:
                mc.displayGuiScreen(new GuiMainMenuElement());
                break;
            case 5:
                mc.displayGuiScreen(new GuiAddServer());
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());
        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse(mouseX, mouseY, scaledResolution);
        DarkMoon.getInstance().getScaleMath().pushScale();
        RenderUtility.drawRect(0, 0, scaledWidth, scaledHeight, new Color(20, 20, 20).getRGB());

        // Quick Join
        int joinRectHeight = 20 + Fonts.tenacityBold28.getStringHeight("Quick Join") + 12 * getServers().size();
        int joinRectWidth = Fonts.mntsb16.getStringWidth(getServers().get(0).getName()) + 20;
        int joinRectWidthTitle = Fonts.tenacityBold28.getStringWidth("Quick Join") + 20;
        if (joinRectWidth < joinRectWidthTitle) {
            joinRectWidth = joinRectWidthTitle;
        }

        RenderUtility.drawLeftSideRoundedGradientRect(0.25f, scaledHeight / 2f - joinRectHeight / 2f - 1, joinRectWidth + 0.75f, joinRectHeight - 2 + 2, 6, 1, color, color, color2, color2);
        RenderUtility.drawLeftSideRoundedRect(0, scaledHeight / 2f - joinRectHeight / 2f, joinRectWidth, joinRectHeight - 2, 5, new Color(28, 28, 28).getRGB());

        Fonts.tenacityBold28.drawCenteredString("Quick Join", joinRectWidth / 2f - 1, scaledHeight / 2f - joinRectHeight / 2f + 5, -1);
        RenderUtility.drawHeader(0, scaledHeight / 2f - joinRectHeight / 2f + 22, joinRectWidth, 1);
        int joinOffset = 8;

        for (ServerInstance server : getServers()) {
            String name = server.getName();
            float joinServerY = scaledHeight / 2f - joinRectHeight / 2f + 22 + joinOffset;
            if (RenderUtility.isHovered(mouse.getX(), mouse.getY(), joinRectWidth / 2f - Fonts.mntsb16.getStringWidth(name) / 2f, joinServerY, Fonts.mntsb16.getStringWidth(name), Fonts.mntsb16.getStringHeight(name))) {
                name = TextFormatting.GOLD + name;
            }
            Fonts.mntsb16.drawCenteredString(name, joinRectWidth / 2f, joinServerY, -1);
            joinOffset += 12;
        }
        //

        scrollY += Mouse.getDWheel() / 10f;

        int offset = scrollY;

        StencilUtility.initStencilToWrite();
        RenderUtility.drawRect(scaledWidth / 2f - 100.5f, 0, 201, scaledHeight - 50, Color.TRANSLUCENT);
        StencilUtility.readStencilBuffer(1);

        for (Alt alt : AltManager.registry) {
            if (alt.equals(selectedAlt)) {
                RenderUtility.drawRoundedGradientRect(scaledWidth / 2f - 100.5f, 20 + offset - 0.5f, 201, 36, 7, 1, color, color, color2, color2);
            }

            RenderUtility.drawRoundedRect(scaledWidth / 2f - 100, 20 + offset, 200, 35, 6, new Color(0, 0, 0).getRGB());

            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

            if (alt.getSkin() == null) {
                alt.setSkin(AbstractClientPlayer.getLocationSkin(alt.getUsername()));
                getDownloadImageSkin(alt, alt.getUsername());
            } else {
                mc.getTextureManager().bindTexture(alt.getSkin());
                GlStateManager.enableTexture2D();
                Gui.drawScaledCustomSizeModalRect(scaledWidth / 2 - 95, 25 + offset, 8, 8, 8, 8, 25, 25, 64, 64);
            }

            GlStateManager.popMatrix();

            String name = alt.getUsername();
            if (name.equalsIgnoreCase(mc.session.getUsername())) {
                name = TextFormatting.GREEN + name;
            }

            Fonts.mntsb16.drawString(name, scaledWidth / 2f - 65, 30 + offset, -1);
            Fonts.mntsb13.drawString(alt.getDate(), scaledWidth / 2f - 65, 42.5f + offset, -1);
            offset += 35 + 5;
        }
        StencilUtility.uninitStencilBuffer();

        super.drawScreen(mouse.getX(), mouse.getY(), partialTicks);
        DarkMoon.getInstance().getScaleMath().popScale();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        Vec2i mouse = DarkMoon.getInstance().getScaleMath().getMouse((int) mouseX, (int) mouseY, scaledResolution);

        // QuickJoin
        int joinOffset = 8;
        int joinRectHeight = 20 + Fonts.tenacityBold28.getStringHeight("Quick Join") + 12 * getServers().size();
        int joinRectWidth = Fonts.mntsb16.getStringWidth(getServers().get(0).getName()) + 20;
        for (ServerInstance server : getServers()) {
            String name = server.getName();
            float joinServerY = scaledHeight / 2f - joinRectHeight / 2f + 22 + joinOffset;
            if (RenderUtility.isHovered(mouse.getX(), mouse.getY(), joinRectWidth / 2f - Fonts.mntsb16.getStringWidth(name) / 2f + 8, joinServerY, Fonts.mntsb16.getStringWidth(name), Fonts.mntsb16.getStringHeight(name))) {
                mc.displayGuiScreen(new GuiConnecting(this, mc, new ServerData(server.getName(), server.getIp(), false)));
            }
            joinOffset += 12;
        }
        //

        scrollY += Mouse.getDWheel() / 10f;
        int offset = scrollY;
        for (Alt alt : AltManager.registry) {
            if (RenderUtility.isHovered(mouse.getX(), mouse.getY(), scaledWidth / 2f - 100, 20 + offset, 200, 35)) {
                if (selectedAlt == alt) {
                    (loginThread = new AltLoginThread(selectedAlt)).start();
                    return;
                }
                selectedAlt = alt;
            }
            offset += 35 + 5;
        }

        super.mouseClicked(mouse.getX(), mouse.getY(), mouseButton);
    }

    private List<ServerInstance> getServers() {
        return DarkMoon.getInstance().getServerManager().getServers().stream().sorted(Comparator.comparingInt(server -> Fonts.mntsb16.getStringWidth(((ServerInstance)server).getName())).reversed()).collect(Collectors.toList());
    }
}
