package net.silentclient.client.gui.modmenu;

import com.google.common.io.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.silentclient.client.Client;
import net.silentclient.client.config.AddConfigModal;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Checkbox;
import net.silentclient.client.gui.elements.*;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.SelectedButtonTheme;
import net.silentclient.client.gui.theme.switches.DefaultSwitchTheme;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ModMenu extends SilentScreen {
    private ModCategory modCategory;
    public static SimpleAnimation introAnimation;
    private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private float scrollY = 0;
    public static boolean loaded;
    private float scrollHeight = 0;

    public ModMenu() {
        this(ModCategory.MODS);
    }

    public ModMenu(ModCategory category) {
        this.modCategory = category;
        this.loaded = false;
    }

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        if(!loaded) {
            introAnimation = new SimpleAnimation(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Animations").getValBoolean() ? -150 : 0);
        }
        Client.getInstance().configManager.updateConfigs();
        MenuBlurUtils.loadBlur();
        this.buttonList.clear();
        this.silentInputs.clear();

        initBaseButtons(this.buttonList);

        this.buttonList.add(new Button(1, 3, 26, 46, 15, "Mods", false, modCategory == ModCategory.MODS ? new SelectedButtonTheme() : new DefaultButtonTheme()));
        this.buttonList.add(new Button(2, 52, 26, 46, 15, "Settings", false, modCategory == ModCategory.SETTINGS ? new SelectedButtonTheme() : new DefaultButtonTheme()));
        this.buttonList.add(new Button(3, 101, 26, 46, 15, "Configs", false, modCategory == ModCategory.CONFIGS ? new SelectedButtonTheme() : new DefaultButtonTheme()));
        this.buttonList.add(new Button(4, 3, this.height - 18, 144, 15, "Premium", false, modCategory == ModCategory.PLUS ? new SelectedButtonTheme() : new DefaultButtonTheme()));

        this.buttonList.add(new Button(5, 3, 46, 61, 15, "New Config"));
        this.buttonList.add(new Button(6, 67, 46, 61, 15, "Folder"));

        this.silentInputs.add(new Input("Search"));
        this.silentInputs.add(new Input("Nametag Message", Pattern
                .compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 40));

        this.silentInputs.get(1).setValue(Client.getInstance().getAccount().getNametagMessage());
    }

    public static void initBaseButtons(List<GuiButton> buttonList) {
        buttonList.add(new IconButton(91, 132, 5, 15, 15, 9, 9, new ResourceLocation("silentclient/icons/exit.png")));
        buttonList.add(new TooltipIconButton(92, 132, 46, 15, 15, 9, 9, new ResourceLocation("silentclient/icons/pencil.png"), "Edit HUD"));
    }

    public static void clickBaseButtons(GuiButton button, GuiScreen screen) {
        switch (button.id) {
            case 91:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 92:
                ModMenu.loaded = false;
                Minecraft.getMinecraft().displayGuiScreen(new HUDConfigScreen(screen));
                break;
        }
    }

    public static void drawOverlayListBase(float height) {
        drawOverlayListBase(height, null);
    }

    public static void drawOverlayListBase(float height, String header) {
        ModMenu.introAnimation.setValue(0);
        GlStateManager.translate(ModMenu.introAnimation.getValue(), 0, 0);
        RenderUtils.drawRect(0, 0, 150, height, Theme.backgroundColor().getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), 3, 3, 97.7F, 19);
        if(header != null) {
            Client.getInstance().getSilentFontRenderer().drawString(header, 3, 46, 14, SilentFontRenderer.FontType.TITLE);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        ModMenu.drawOverlayListBase(height, modCategory == ModCategory.PLUS ? "Premium" : null);

        if(modCategory != ModCategory.CONFIGS && modCategory != ModCategory.PLUS) {
            this.silentInputs.get(0).render(mouseX, mouseY, 3, 46, 127, true);
            this.buttonList.get(6).visible = false;
            this.buttonList.get(7).visible = false;
        } else if(modCategory == ModCategory.CONFIGS) {
            this.buttonList.get(6).visible = true;
            this.buttonList.get(7).visible = true;
        } else if(modCategory == ModCategory.PLUS) {
            this.buttonList.get(6).visible = false;
            this.buttonList.get(7).visible = false;
        }

        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);

        trimContentStart(width, height);
        if(modCategory != ModCategory.CONFIGS && modCategory != ModCategory.PLUS) {
            float modY = 66 - scrollAnimation.getValue();

            for(Mod mod : getMods()) {
                if(mouseInContent(0, (int) modY, height) || mouseInContent(0, (int) (modY + 20), height)) {
                    boolean isHovered = mouseInContent(mouseX, mouseY, height) && MouseUtils.isInside(mouseX, mouseY, 3, modY, 144, 20) && !(modCategory == ModCategory.MODS && Switch.isHovered(mouseX, mouseY, 129, modY + 10 - 4)) && (Client.getInstance().getSettingsManager().getSettingByMod(mod).size() != 0 || mod.getName() == "Auto Text");
                    if(isHovered) {
                        cursorType = MouseCursorHandler.CursorType.POINTER;
                        RenderUtil.drawRoundedRect(3, modY, 144, 20, 3, new Color(255, 255, 255,  30).getRGB());
                    }
                    RenderUtil.drawRoundedOutline(3, modY, 144, 20, 3, 1, Theme.borderColor().getRGB());
                    int modX = 6;
                    if(mod.getIcon() != null) {
                        RenderUtil.drawImage(new ResourceLocation(mod.getIcon()), modX, modY + 10 - 7, 14, 14);
                        modX += 19;
                    }

                    Client.getInstance().getSilentFontRenderer().drawString(mod.getName(), modX, modY + 10 - 6, 12, SilentFontRenderer.FontType.TITLE);

                    if(mod.isUpdated() || mod.isNew()) {
                        String status = "UPDATED";
                        if(mod.isNew()) {
                            status = "NEW";
                        }
                        float badgeX = modX + Client.getInstance().getSilentFontRenderer().getStringWidth(mod.getName(), 12, SilentFontRenderer.FontType.TITLE) + 2;
                        float badgeY = modY + 6;
                        RenderUtil.drawRoundedRect(badgeX, badgeY, Client.getInstance().getSilentFontRenderer().getStringWidth(status, 6, SilentFontRenderer.FontType.HEADER) + 4, 8, 8, Color.RED.getRGB());
                        Client.getInstance().getSilentFontRenderer().drawString(status, badgeX + 2, badgeY + 1, 6, SilentFontRenderer.FontType.HEADER);
                    }

                    if(modCategory == ModCategory.MODS) {
                        Switch.render(mouseX, mouseY, 129, modY + 10 - 4, mod.simpleAnimation, mod.isEnabled(), mod.isForceDisabled(), mod.isForceDisabled() ? "Force disabled" : null);
                        if(Switch.isHovered(mouseX, mouseY, 129, modY + 10 - 4)) {
                            cursorType = MouseCursorHandler.CursorType.POINTER;
                        }
                    }
                }

                modY += 23;
            }
            this.scrollHeight = 66 + modY + scrollAnimation.getValue();
        } else if(modCategory == ModCategory.CONFIGS) {
            float configY = 66 - scrollAnimation.getValue();
            RenderUtil.drawRoundedOutline(3, configY, 144, 20, 3, 2, new DefaultSwitchTheme().getSelectedBackgroundColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawString(Files.getNameWithoutExtension(Client.getInstance().configManager.configFile.getName()), 6, (int) configY + 10 - 6, 12, SilentFontRenderer.FontType.TITLE, 100);
            configY += 23;
            for(String config : Client.getInstance().getConfigManager().getConfigFiles()) {
                if(config.equals(".config-settings.txt") || config.equals(".DS_Store") || Client.getInstance().configManager.configFile.getName().equals(config)) {
                    continue;
                }

                boolean isHovered = mouseInContent(mouseX, mouseY, height) && MouseUtils.isInside(mouseX, mouseY, 3, configY, 144, 20);
                if(isHovered) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                    if(!MouseUtils.isInside(mouseX, mouseY, 132, configY + 10 - 6, 12, 12)) {
                        RenderUtil.drawRoundedRect(3, configY, 144, 20, 3, new Color(255, 255, 255,  30).getRGB());
                    }
                }
                RenderUtil.drawRoundedOutline(3, configY, 144, 20, 3, 1, Theme.borderColor().getRGB());

                Client.getInstance().getSilentFontRenderer().drawString(Files.getNameWithoutExtension(config), 6, (int) configY + 10 - 6, 12, SilentFontRenderer.FontType.TITLE, 90);

                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/trash-icon.png"), 132, configY + 10 - 6, 12, 12);

                configY += 23;
            }
            this.scrollHeight = 66 + configY + scrollAnimation.getValue();
        } else if(modCategory == ModCategory.PLUS) {
            float premiumY = 66 - scrollAnimation.getValue();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if(!Client.getInstance().getAccount().isPlus()) {
                Client.getInstance().getSilentFontRenderer().drawCenteredString("You're currently not Premium!", 150 / 2, (int) premiumY, 14, SilentFontRenderer.FontType.TITLE);
            } else {
                int days = Client.getInstance().getAccount().getPlusExpiration();
                Client.getInstance().getSilentFontRenderer().drawString(days != -1 ? days + " days left" : "Unknown Time Remaining", 3, (int) premiumY, 12, SilentFontRenderer.FontType.TITLE);
                premiumY += 15;
                RegularColorPicker.render(3, (int) premiumY, 144, "Chroma Bandana Color", Client.getInstance().getAccount().getBandanaColor() == 50 ? ColorUtils.getChromaColor(0, 0, 1).getRGB() : Client.getInstance().getAccount().getBandanaColor());
                if(RegularColorPicker.isHovered(mouseX, mouseY, 3, (int) premiumY, 144)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                premiumY += 15;
                Client.getInstance().getSilentFontRenderer().drawString("Custom Capes", 3, premiumY, 12, SilentFontRenderer.FontType.TITLE);
                StaticButton.render(150 - 3 - 65, (int) premiumY, 65, 12, Client.getInstance().getAccount().isPremiumPlus() ? "OPEN MENU" : "BUY PREMIUM+");
                if(StaticButton.isHovered(mouseX, mouseY, 150 - 3 - 65, premiumY, 65, 12)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                premiumY += 15;
                ColorUtils.setColor(new Color(255, 255, 255, 127).getRGB());
                Client.getInstance().getSilentFontRenderer().drawString("Nametag Message Settings:", 3, premiumY, 12, SilentFontRenderer.FontType.TITLE);
                premiumY += 15;
                ColorUtils.setColor(new Color(255, 255, 255, 255).getRGB());
                if(Client.getInstance().getAccount().isPremiumPlus()) {
                    Checkbox.render(mouseX, mouseY, 3, (int) premiumY, "Show Nametag Message", Client.getInstance().getAccount().showNametagMessage());
                    if(Checkbox.isHovered(mouseX, mouseY, 3, premiumY)) {
                        cursorType = MouseCursorHandler.CursorType.POINTER;
                    }
                    premiumY += 15;
                    Client.getInstance().getSilentFontRenderer().drawString("Nametag Message:", 3, premiumY + 1, 12, SilentFontRenderer.FontType.TITLE);
                    premiumY += 15;
                    this.silentInputs.get(1).render(mouseX, mouseY, 3, (int) premiumY, 144, true);
                    premiumY += 20;
                    StaticButton.render(150 - 3 - 50, (int) premiumY, 50, 12, "Save");
                    if(StaticButton.isHovered(mouseX, mouseY, 150 - 3 - 50, premiumY, 50, 12)) {
                        cursorType = MouseCursorHandler.CursorType.POINTER;
                    }
                } else {
                    Client.getInstance().getSilentFontRenderer().drawString("You're currently not Premium+", 3, (int) premiumY, 12, SilentFontRenderer.FontType.TITLE);
                }
            }
            this.scrollHeight = 66 + premiumY + scrollAnimation.getValue();
        }

        trimContentEnd();

        super.drawScreen(mouseX, mouseY, partialTicks);

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

        scrollAnimation.setAnimation(scrollY, 12);
    }

    public static void trimContentStart(int width, int height) {
        trimContentStart(width, height, false);
    }

    public static void trimContentStart(int width, int height, boolean withoutFooter) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = height - 66 - (withoutFooter ? 0 : 21);
        int translatedY = r.getScaledHeight() - 66 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, width * s, listHeight * s);
    }

    public static void trimContentEnd() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = this.scrollY;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            if (newScrollY + amountScrolled > 0)
                newScrollY += amountScrolled;
            else
                newScrollY = 0;
            if((newScrollY < scrollHeight && scrollHeight > height - 25) || amountScrolled < 0) {
                this.scrollY = (float) newScrollY;
                if(this.scrollY < 0) {
                    this.scrollY = 0;
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        clickBaseButtons(button, this);
        if(button instanceof Button) {
            switch (button.id) {
                case 1:
                    resetTabs((Button) button);
                    modCategory = ModCategory.MODS;
                    break;
                case 2:
                    resetTabs((Button) button);
                    modCategory = ModCategory.SETTINGS;
                    break;
                case 3:
                    resetTabs((Button) button);
                    modCategory = ModCategory.CONFIGS;
                    break;
                case 4:
                    resetTabs((Button) button);
                    modCategory = ModCategory.PLUS;
                    break;
                case 5:
                    mc.displayGuiScreen(new AddConfigModal(this));
                    break;
                case 6:
                    File file1 = Client.getInstance().configDir;
                    String s = file1.getAbsolutePath();

                    if (Util.getOSType() == Util.EnumOS.OSX) {
                        try {
                            Client.logger.info(s);
                            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
                            return;
                        } catch (IOException ioexception1) {
                            Client.logger.error((String) "Couldn\'t open file", (Throwable) ioexception1);
                        }
                    } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                        String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{s});

                        try {
                            Runtime.getRuntime().exec(s1);
                            return;
                        } catch (IOException ioexception) {
                            Client.logger.error((String) "Couldn\'t open file", (Throwable) ioexception);
                        }
                    }

                    boolean flag = false;

                    try {
                        Class<?> oclass = Class.forName("java.awt.Desktop");
                        Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
                        oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{file1.toURI()});
                    } catch (Throwable throwable) {
                        Client.logger.error("Couldn\'t open link", throwable);
                        flag = true;
                    }

                    if (flag) {
                        Client.logger.info("Opening via system class!");
                        Sys.openURL("file://" + s);
                    }
                    break;
            }
        }
    }

    private void resetTabs(Button button) {
        this.buttonList.forEach(oldButton -> {
            if(oldButton instanceof Button) {
                ((Button) oldButton).setTheme(new DefaultButtonTheme());
            }
        });
        button.setTheme(new SelectedButtonTheme());
        this.scrollY = 0;
        scrollAnimation.setValue(0);
    }

    public static boolean mouseInContent(int mouseX, int mouseY, float height, boolean mainMenu) {
        return MouseUtils.isInside(mouseX, mouseY, 0, 66, 150, height - 66 - (mainMenu ? 21 : 0));
    }

    public static boolean mouseInContent(int mouseX, int mouseY, float height) {
        return mouseInContent(mouseX, mouseY, height, true);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(modCategory != ModCategory.CONFIGS && modCategory != ModCategory.PLUS) {
            this.silentInputs.get(0).onClick(mouseX, mouseY, 3, 46, 127, true);
        }

        if(!mouseInContent(mouseX, mouseY, height)) {
            return;
        }

        if(modCategory != ModCategory.CONFIGS && modCategory != ModCategory.PLUS) {
            float modY = 66 - scrollAnimation.getValue();

            for(Mod mod : getMods()) {
                if(modCategory == ModCategory.MODS && Switch.isHovered(mouseX, mouseY, 129, modY + 10 - 4)) {
                    if(!mod.isForceDisabled()) {
                        Sounds.playButtonSound();
                        mod.toggle();
                    }
                    break;
                }

                if(MouseUtils.isInside(mouseX, mouseY, 3, modY, 144, 20) && (Client.getInstance().getSettingsManager().getSettingByMod(mod).size() != 0 || mod.getName() == "Auto Text")) {
                    Sounds.playButtonSound();
                    mc.displayGuiScreen(new ModSettings(mod, this));
                }

                modY += 23;
            }
        } else if(modCategory == ModCategory.CONFIGS) {
            float configY = 66 - scrollAnimation.getValue();
            configY += 23;
            this.scrollHeight += 20;
            for(String config : Client.getInstance().getConfigManager().getConfigFiles()) {
                if(config.equals(".config-settings.txt") || config.equals(".DS_Store") || Client.getInstance().configManager.configFile.getName().equals(config)) {
                    continue;
                }

                if(MouseUtils.isInside(mouseX, mouseY, 132, configY + 10 - 6, 12, 12)) {
                    Client.getInstance().configManager.deleteConfig(config);
                    Sounds.playButtonSound();
                    break;
                }

                boolean isHovered = mouseInContent(mouseX, mouseY, height) && MouseUtils.isInside(mouseX, mouseY, 3, configY, 144, 20);
                if(isHovered) {
                    Client.getInstance().configManager.loadConfig(config);
                    Sounds.playButtonSound();
                    break;
                }

                configY += 23;
                this.scrollHeight += 20;
            }
        } else if(modCategory == ModCategory.PLUS && Client.getInstance().getAccount().isPlus()) {
            float premiumY = 66 - scrollAnimation.getValue();
            premiumY += 15;
            if(RegularColorPicker.isHovered(mouseX, mouseY, 3, (int) premiumY, 144)) {
                // Color Picker
                mc.displayGuiScreen(new ColorPicker(Client.getInstance().getAccount().getBandanaColor() == 50 ? new Color(255, 255, 255) : new Color(Client.getInstance().getAccount().getBandanaColor()), Client.getInstance().getAccount().getBandanaColor() == 50, false, 255, new ColorPickerAction() {
                    @Override
                    public void onChange(Color color, boolean chroma, int opacity) {
                        int colorInt = 0;
                        colorInt = color.getRGB();
                        if(chroma) {
                            colorInt = 50;
                        }
                        if(Client.getInstance().getAccount().getBandanaColor() != colorInt) {
                            Client.getInstance().getAccount().setBandanaColor(colorInt);
                        }
                    }

                    @Override
                    public void onClose(Color color, boolean chroma, int opacity) {
                        Client.getInstance().getAccount().saveBandanaColor();
                    }
                }, this));
                return;
            }
            premiumY += 15;
            if(StaticButton.isHovered(mouseX, mouseY, 150 - 3 - 65, (int) premiumY, 65, 12)) {
                Sounds.playButtonSound();
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(Client.getInstance().getAccount().isPremiumPlus() ? "https://store.silentclient.net/premium/custom_cape" : "https://store.silentclient.net/premium")});
                } catch (Throwable err) {
                    err.printStackTrace();
                }
                return;
            }
            premiumY += 30;
            if(Client.getInstance().getAccount().isPremiumPlus()) {
                if(Checkbox.isHovered(mouseX, mouseY, 3, (int) premiumY)) {
                    Client.getInstance().getAccount().setShowNametagMessage(!Client.getInstance().getAccount().showNametagMessage());
                    return;
                }
                premiumY += 30;
                this.silentInputs.get(1).onClick(mouseX, mouseY, 3, (int) premiumY, 144, true);
                premiumY += 20;
                if(StaticButton.isHovered(mouseX, mouseY, 150 - 3 - 50, (int) premiumY, 50, 12)) {
                    Client.getInstance().getAccount().setNametagMessage(this.silentInputs.get(1).getValue());
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(modCategory != ModCategory.CONFIGS && modCategory != ModCategory.PLUS) {
            this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
            if(this.silentInputs.get(0).isFocused()) {
                this.scrollY = 0;
                scrollAnimation.setValue(0);
            }
            return;
        }

        if(modCategory == ModCategory.PLUS) {
            this.silentInputs.get(1).onKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return !(modCategory.equals(ModCategory.CONFIGS));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Client.getInstance().configManager.save();
        MenuBlurUtils.unloadBlur();
    }

    private ArrayList<Mod> getMods() {
        if(this.silentInputs.get(0).getValue().trim().equals("") || !modCategory.equals(ModCategory.MODS)) {
            return Client.getInstance().getModInstances().getModByCategory(modCategory);
        }

        return Client.getInstance().getModInstances().searchMods(this.silentInputs.get(0).getValue());
    }
}
