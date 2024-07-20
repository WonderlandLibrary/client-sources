/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.world.GameType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiIngameMenu
extends GuiScreen {
    private int saveStep;
    private int visibleTime;
    public static boolean respawnKey = false;
    public static GameType gamemode = GameType.SURVIVAL;
    private final HashMap<GuiIngameMenu, Float> map = new HashMap();

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(false);
        this.saveStep = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + -16, I18n.format("menu.returnToMenu", new Object[0])));
        if (!Panic.stop) {
            if (respawnKey) {
                this.buttonList.add(new GuiButton(1337, width / 2 - 100, height / 4 + 120 + -16 + 24 + 24, I18n.format("deathScreen.respawn", new Object[0])));
            }
            this.buttonList.add(new GuiButton(8, width / 2 - 100, height / 4 + 120 + -16 + 24, this.mc.isSingleplayer() ? "\u0421\u0435\u0440\u0432\u0435\u0440\u0430" : "\u041f\u0435\u0440\u0435\u0437\u0430\u0439\u0442\u0438"));
        }
        if (!this.mc.isIntegratedServerRunning()) {
            ((GuiButton)this.buttonList.get((int)0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + -16, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + -16, 98, 20, I18n.format("menu.options", new Object[0])));
        GuiButton guibutton = this.addButton(new GuiButton(7, width / 2 + 2, height / 4 + 96 + -16, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
        this.buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + -16, 98, 20, I18n.format("gui.advancements", new Object[0])));
        this.buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + -16, 98, 20, I18n.format("gui.stats", new Object[0])));
        super.initGui();
        this.map.put(this, Float.valueOf(1.1f));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                if (!Panic.stop) {
                    Client.configManager.saveConfig("Default");
                }
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.world.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                if (flag) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                if (flag1) {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                    break;
                }
                this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                break;
            }
            case 1337: {
                if (Minecraft.player == null) {
                    return;
                }
                Minecraft.player.respawnPlayer();
                respawnKey = false;
                Minecraft.player.closeScreen();
                this.mc.playerController.setGameType(gamemode);
                Minecraft.player.capabilities.isFlying = false;
                break;
            }
            default: {
                break;
            }
            case 4: {
                if (!Panic.stop) {
                    Client.configManager.saveConfig("Default");
                }
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiScreenAdvancements(Minecraft.player.connection.func_191982_f()));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, Minecraft.player.getStatFileWriter()));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            }
            case 8: {
                if (!Panic.stop) {
                    Client.configManager.saveConfig("Default");
                }
                if (this.mc.isSingleplayer()) {
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                    break;
                }
                GuiDisconnected.lastServer = this.mc.getCurrentServerData().serverIP;
                this.mc.world.sendQuittingDisconnectingPacket("\u041f\u0435\u0440\u0435\u0437\u0430\u0445\u043e\u0434 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440 " + GuiDisconnected.lastServer);
                GuiDisconnected.does = true;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.visibleTime;
    }

    public float lerp(float start, float end, float step) {
        return start + step * (end - start);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float scaled = 0.0f;
        if (this.map.containsKey(this)) {
            this.map.entrySet().forEach(m -> m.setValue(Float.valueOf(this.lerp(((Float)m.getValue()).floatValue(), 1.0f, (float)Minecraft.frameTime * 0.015f))));
            if (!Panic.stop) {
                scaled = -this.map.get(this).floatValue();
            }
        }
        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(this.mc);
        float scaledWidth = sr.getScaledWidth();
        float scaledHeight = sr.getScaledHeight();
        if (Panic.stop || !ComfortUi.get.isAnimPauseScreen()) {
            this.drawDefaultBackground();
        } else {
            RenderUtils.customScaledObject2D(0.0f, 0.0f, scaledWidth, scaledHeight * 0.75f, -scaled);
        }
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), width / 2, 40, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }
}

