package wtf.diablo.client.gui.altmanager2;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.gui.altmanager2.impl.AltManagerButton;
import wtf.diablo.client.gui.altmanager2.login.AccountType;
import wtf.diablo.client.gui.altmanager2.login.Alt;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;
import wtf.diablo.client.util.mc.alt.AltUtil;
import wtf.diablo.client.util.mc.player.skin.SkinUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.system.alts.CookieAltsUtil;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

public class GuiAltManager extends GuiScreen {
    private final GuiScreen parentScreen;
    private int selectedIndex = -1;
    private boolean showingButtons = false;
    private float extraY = 0;

    public GuiAltManager(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    public void onGuiClosed() {

    }

    @Override
    public void initGui() {
        AltUtil.getAltList().sort(Comparator.comparingInt((alt) -> {
            if (alt.isPinned()) return 0;
            else return 1;
        }));
        updateButtons();
        if (selectedIndex >= 0) {
            this.buttonList.add(new AltManagerButton(5, 5, this.height - 80, 100, 20, "Login"));
            this.buttonList.add(new AltManagerButton(6, 5, this.height - 55, 100, 20, "Edit"));
            this.buttonList.add(new AltManagerButton(7, 5, this.height - 30, 100, 20, "Delete"));
            showingButtons = true;
        } else {
            if (showingButtons) {
                updateButtons();
                showingButtons = false;
            }
        }
    }

    public void updateButtons() {
        this.buttonList.clear();
        createButtons();
    }

    void createButtons() {
        this.buttonList.add(new AltManagerButton(1, 5, 10, 100, 20, "Back"));
        this.buttonList.add(new AltManagerButton(2, 5, 35, 100, 20, "Direct Login"));
        this.buttonList.add(new AltManagerButton(3, 5, 60, 100, 20, "Add Alt"));
        this.buttonList.add(new AltManagerButton(4, 5, 85, 100, 20, "Random Cracked"));
        this.buttonList.add(new AltManagerButton(8, 5, 110, 100, 20, "Cookie Login"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float thisIdk = Mouse.getDWheel() / 120f;
        addExtraY(thisIdk * 15);
        Gui.drawRect(0, 0, this.width, this.height, 0xFF151515);
        super.drawScreen(mouseX, mouseY, partialTicks);
        float baseAltX = 115;
        float baseAltY = 10;

        float altsOnPage = ((height - baseAltY - 15) / 55);


        float maxExtraY = Math.min(0, -(AltUtil.getAltList().size() * 55) + (55 * (altsOnPage)));
        float height = this.height - baseAltY - 10;
        float width = 350;

        if (extraY > 0) {
            setExtraY(0);
        } else if (extraY < maxExtraY) {
            setExtraY(maxExtraY);
        }

        drawAltList(baseAltX, baseAltY, extraY);

        if (selectedIndex >= 0) {
            Alt currentAlt = AltUtil.getAltList().get(this.selectedIndex);
            drawAltInfo(baseAltX + 375, baseAltY, currentAlt);
        }


        // Scroll bar
        float scrollBarX = (baseAltX + width);
        float altsNotOnPage = AltUtil.getAltList().size() - altsOnPage;
        float totalPages = (altsNotOnPage / altsOnPage) + 1;
        float pagesNotShown = totalPages - 1;
        float percentageShowing = 1 - (pagesNotShown / totalPages);
        float scrollBarBottom = baseAltY + height;
        float scrollBarHeight = (scrollBarBottom - baseAltY) * Math.max(1, (maxExtraY / 100));
        float sliderHeight = Math.min(scrollBarBottom - 10, scrollBarHeight * percentageShowing);
        float percentageToEnd = 1 - (((maxExtraY - extraY) / 10) / maxExtraY) * 10;
        float scrollBarY = baseAltY + ((baseAltY + height - sliderHeight - 10) * percentageToEnd);

        Gui.drawRect(scrollBarX, scrollBarY, scrollBarX + 2, scrollBarY + sliderHeight, 0xFFFFFFFF);

        if (shouldPinAlt(mouseX, mouseY)) {
            mc.fontRendererObj.drawString("Click to Pin Alt", mouseX + 1f, mouseY - 10f, 0xFFFFFFFF);
        } else if (shouldDeleteAlt(mouseX, mouseY)) {
            mc.fontRendererObj.drawString("Click to Delete Alt", mouseX + 1f, mouseY - 10f, 0xFFFFFFFF);
        }

    }

    private void drawAltInfo(float posX, float posY, Alt selectedAlt) {
        float nameWidth = mc.fontRendererObj.getStringWidth(selectedAlt.getUsername());
        if (nameWidth > 60) {
            posX += (nameWidth - 60) / 2f;
        }
        drawCenteredString(mc.fontRendererObj, "Account Details", posX + 40, posY, 0xFFFFFFFF);
        drawCenteredString(mc.fontRendererObj, "Username: " + selectedAlt.getUsername(), posX + 40, posY + 12, 0xFFFFFFFF);
        String accountType = selectedAlt.getAccountType().toString();
        drawCenteredString(mc.fontRendererObj, "Account Type: " +
                        accountType.substring(0, 1).toUpperCase() +
                        accountType.substring(1).toLowerCase(),
                posX + 40, posY + 24, 0xFFFFFFFF);
        drawCenteredString(mc.fontRendererObj, "Ban Details", posX + 40, posY + 48, 0xFFFFFFFF);
        drawCenteredString(mc.fontRendererObj, "----------", posX + 40, posY + 53, 0xFFFFFFFF);
        if (!selectedAlt.isBanned()) {
            drawCenteredString(mc.fontRendererObj, "No Bans Recorded", posX + 40, posY + 64, 0xFFFFFFFF);
        } else {
            float banPosY = posY + 64;
            for (Map.Entry<String, Long> entry : selectedAlt.getBanData().entrySet()) {
                String bannedServer = entry.getKey();
                long unbanTime = entry.getValue() - System.currentTimeMillis();
                if (unbanTime <= 0) {
                    selectedAlt.updateBan(bannedServer, 0);
                    continue;
                }
                long totalSecs = unbanTime / 1000L;
                long hours = totalSecs / 3600;
                long minutes = (totalSecs % 3600) / 60;
                long seconds = totalSecs % 60;
                mc.fontRendererObj.drawStringWithShadow("IP: " + bannedServer, 475, banPosY, 0xFFDADADA);
                mc.fontRendererObj.drawStringWithShadow("Unbanned In: §f" + String.format("%02dd %02dm %02ds", hours, minutes, seconds), 475, banPosY + 12, 0xFFDADADA);
                banPosY += 28;
            }
        }
    }

    private void drawAltList(float posX, float posY, float extraY) {
        float bottom = this.height - 10;
        float right = Math.min((this.width) - 10, posX + 350);
        float altPosX = posX + 5;
        float altPosY = posY + 5;

        int currentAltIndex = 0;

        //TODO: Stencil.write(false);
        Gui.drawRect(posX, posY, right, bottom, 0xFF000000);
        //TODO: Stencil.erase(true);
        Gui.drawRect(posX, posY, right, bottom, 0xFF101010);
        int renderedAlts = 0;
        for (Alt alt : AltUtil.getAltList()) {
            if (currentAltIndex > extraY + 110) {
                currentAltIndex++;
                extraY += 55;
                continue;
            } else if ((renderedAlts - 1) * 55 > this.height) {
                break;
            }
            renderedAlts++;
            drawAlt(altPosX, altPosY + extraY, alt, currentAltIndex);
            extraY += 55;
            currentAltIndex++;
        }

        //TODO: Stencil.dispose();
    }

    private void drawAlt(float posX, float posY, Alt alt, int currentIndex) {
        float right = Math.min((this.width) - 10, posX + 345) - 5;
        RenderUtil.drawRectWidth(posX, posY, right - posX, 48, 0xFF202020);
        RenderUtil.drawRectOutlineWidth(posX, posY, right - posX, 48,
                currentIndex == this.selectedIndex ? 0xFF8C61B2 : 0xFF303030, 1);
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, 1);
        GlStateManager.scale(1.5, 1.5, 1);
        GlStateManager.translate(-posX, -posY, -1);

        if (!alt.getUUID().equals("")) {
            ResourceLocation skinLocation = new ResourceLocation("skins/" + alt.getUsername());
            try {
                SkinUtil.getDownloadImageSkin(skinLocation, alt.getUUID(), alt.getUsername()).loadTexture(mc.getResourceManager());
                mc.getTextureManager().bindTexture(skinLocation);
            } catch (IOException e) {
                LogManager.getLogger().info("Failed to get Skin for user " + alt.getUsername());
            }
        } else {
            mc.getTextureManager().bindTexture(DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(alt.getUsername())));
        }

        RenderUtil.drawBoundFaceWithLayer(posX + 2, posY + 2, 28, 28);

        GlStateManager.popMatrix();

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        if (alt.isPinned()) {
            GlStateManager.color(237 / 255f, 212 / 255f, 54 / 255f);
        }
        mc.getTextureManager().bindTexture(new ResourceLocation("diablo/altmanager/pinned.png"));
        drawScaledCustomSizeModalRect((int) (right - 15), (int) (posY + 2), 0, 0, 16, 16, 12, 12, 16, 16);

        GlStateManager.color(1, 1, 1);
        mc.getTextureManager().bindTexture(alt.getAccountType().getIconLocation());
        drawScaledCustomSizeModalRect((int) (right - 15), (int) (posY + 16), 0, 0, 16, 16, 12, 12, 16, 16);

        GlStateManager.disableBlend();

        mc.fontRendererObj.drawStringWithShadow(alt.getUsername(), posX + 55, posY + 8, alt.isInvalid() ? 0xFFFF4444 : 0xFFDDDDDD);
        if (!(alt.getAccountType() == AccountType.CRACKED))
            mc.fontRendererObj.drawStringWithShadow(alt.getPassword().replaceAll("[^*]", "*"), posX + 55, posY + 22, 0xFFAAAAAA);

    }

    protected void actionPerformed(GuiButton button) {
        int currentIndex = selectedIndex;
        switch (button.id) {
            case 1:
                new Thread("AltManagerThread") {
                    @Override
                    public void run() {
                        try {
                            AltUtil.saveAlts();
                            LogManager.getLogger().info("Successfully saved alts");
                        } catch (Exception e) {
                            LogManager.getLogger().error("Failed Saving Alts");
                        }
                    }
                }.start();
                mc.displayGuiScreen(this.parentScreen);
                break;
            case 2:
                mc.displayGuiScreen(new GuiDirectLogin(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                new Alt("DiabloUser" + RandomUtils.nextInt(1000, 10000), "", AccountType.CRACKED).login();
                break;
            case 5:
                AltUtil.getAltList().get(currentIndex).login();
                break;
            case 6:
                mc.displayGuiScreen(new GuiEditAlt(this, currentIndex));
                break;
            case 7:
                AltUtil.getAltList().remove(currentIndex);
                setAltIndex(-1);
                break;
            case 8:
                try {
                    CookieAltsUtil.getAccessToken();
                    Diablo.getInstance().getNotificationManager().addNotification(new Notification("Cookie Alts", "Copied OAuth link to clipboard", 5000, NotificationType.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
               break;
        }
    }

    public int getClickedIndex(int mouseY) {
        float wholeY = (int) (mouseY - extraY) - 10;
        wholeY /= 55;
        return (int) Math.floor(wholeY);
    }

    private boolean shouldPinAlt(int mouseX, int mouseY) {
        if (mouseX >= 120 && mouseX <= 120 + 290) {
            if (mouseY >= 10 && mouseY <= this.height - 10) {
                int clickedIndex = getClickedIndex(mouseY);
                if (clickedIndex <= AltUtil.getAltList().size() - 1 && clickedIndex >= 0) {
                    return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
                }
            }
        }

        return false;
    }

    private boolean shouldDeleteAlt(int mouseX, int mouseY) {
        if (mouseX >= 120 && mouseX <= 120 + 290) {
            if (mouseY >= 10 && mouseY <= this.height - 10) {
                int clickedIndex = getClickedIndex(mouseY);
                if (clickedIndex <= AltUtil.getAltList().size() - 1 && clickedIndex >= 0) {
                    return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
                }
            }
        }

        return false;
    }

    public void setExtraY(float value) {
        this.extraY = value;
    }

    public void addExtraY(float value) {
        this.extraY += value;
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_V && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            String[] clipboardSplit = getClipboardString().split("\n");
            for (String string : clipboardSplit) {
                if (string.contains(":")) {
                    string = string.replaceAll(" ", "");
                    String[] alt = string.split(":");
                    if (alt.length == 1) {
                        String username = alt[0];
                        Alt currentAlt = new Alt(username, "", AccountType.CRACKED);
                        AltUtil.getAltList().add(currentAlt);
                    } else if (alt.length > 1) {
                        String username = alt[0];
                        String password = alt[1];
                        Alt currentAlt = new Alt(username, password, AccountType.MICROSOFT);
                        AltUtil.getAltList().add(currentAlt);
                    }
                }
            }
        } else if (keyCode == 1) {
            this.mc.displayGuiScreen(parentScreen);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= 120 && mouseX <= 120 + 290) {
                if (mouseY >= 10 && mouseY <= this.height - 10) {
                    int clickedIndex = getClickedIndex(mouseY);
                    if (shouldPinAlt(mouseX, mouseY)) {
                        Alt clickedAlt = AltUtil.getAltList().get(clickedIndex);
                        clickedAlt.setPinned(!clickedAlt.isPinned());
                        setAltIndex(-1);
                        AltUtil.getAltList().sort(Comparator.comparingInt((alt) -> {
                            if (alt.isPinned()) return 0;
                            else return 1;
                        }));
                    } else if (shouldDeleteAlt(mouseX, mouseY)) {
                        AltUtil.getAltList().remove(clickedIndex);
                        setAltIndex(-1);
                    } else if (clickedIndex <= AltUtil.getAltList().size() - 1 && clickedIndex >= 0) {
                        setAltIndex(clickedIndex);
                    } else {
                        setAltIndex(-1);
                    }
                }
            }
        }
    }

    private void setAltIndex(int index) {
        this.selectedIndex = index;
        initGui();
    }
}
