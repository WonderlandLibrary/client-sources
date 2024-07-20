/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.intave.vialoadingbase.ViaLoadingBase;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiDisconnected
extends GuiScreen {
    private final long initTime = System.currentTimeMillis();
    private final String reason;
    private final ITextComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int textHeight;
    public static String lastServer;
    public static boolean autoReconnect;
    int ticks = 0;
    String reVersion;
    public NetworkManager networkManager;
    public static boolean does;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp) {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        if (!Panic.stop) {
            Client.configManager.saveConfig("Default");
        }
        this.ticks = 0;
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), width - 50);
        this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, Math.min(height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, height - 30), I18n.format("gui.toMenu", new Object[0])));
        if (lastServer != null && !Panic.stop) {
            this.buttonList.add(new GuiButton(1, width / 2 - 100, Math.min(height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 24, height - 30), "\u041f\u0435\u0440\u0435\u0437\u0430\u0439\u0442\u0438"));
            this.buttonList.add(new GuiButton(2, width / 2 - 100, Math.min(height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 24 + 24, height - 30), "\u0410\u0432\u0442\u043e\u043f\u0435\u0440\u0435\u0437\u0430\u0445\u043e\u0434 " + (autoReconnect ? TextFormatting.GREEN + "\u0432\u043a\u043b\u044e\u0447\u0435\u043d" : TextFormatting.RED + "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d")));
            String reason2 = this.message.getUnformattedText().toLowerCase();
            if (reason2.contains("\u0443\u0441\u0442\u0430\u0440\u0435\u0432\u0448\u0438\u0439 \u043a\u043b\u0438\u0435\u043d\u0442") || reason2.contains("\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0435") || reason2.contains("\u0432\u0435\u0440\u0441\u0438\u044e") || reason2.contains("\u0432\u0435\u0440\u0441\u0438\u0438") || reason2.contains("outdated")) {
                this.reVersion = reason2.contains("1.16") ? "1.16.5" : (reason2.contains("1.17") ? "1.17.1" : (reason2.contains("1.18") ? "1.18.2" : (reason2.contains("1.19") ? "1.19.4" : (reason2.contains("1.20") ? "1.20.2" : (reason2.contains("1.7") ? "1.7.10" : (reason2.contains("1.8") ? "1.8.9" : (reason2.contains("1.9") ? "1.9.4" : (reason2.contains("1.10") ? "1.10" : (reason2.contains("1.11") ? "1.11.2" : (reason2.contains("1.12") ? "1.12.2" : (reason2.contains("1.13") ? "1.13.2" : (reason2.contains("1.14") ? "1.14.4" : (reason2.contains("1.15") ? "1.15.2" : "1.16.5")))))))))))));
                this.buttonList.add(new GuiButton(1001, width / 2 - 100, Math.min(height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 24 + 24 + 24, height - 30), "\u041f\u0435\u0440\u0435\u0437\u0430\u0439\u0442\u0438 c \u0432\u0435\u0440\u0441\u0438\u0438 " + this.reVersion));
            }
        }
    }

    void connectToIp(String Ip) {
        this.mc.displayGuiScreen(new GuiConnecting(this.parentScreen, this.mc, new ServerData(lastServer, lastServer, true)));
        System.out.println("GuiDisconnected > connecting to " + lastServer);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
            autoReconnect = false;
        }
        if (button.id == 1) {
            this.connectToIp(lastServer);
        }
        if (button.id == 2) {
            autoReconnect = !autoReconnect;
            this.buttonList.remove(2);
            this.buttonList.add(new GuiButton(2, width / 2 - 100, Math.min(height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 24 + 24, height - 30), "\u0410\u0432\u0442\u043e\u043f\u0435\u0440\u0435\u0437\u0430\u0445\u043e\u0434 " + (autoReconnect ? TextFormatting.GREEN + "\u0432\u043a\u043b\u044e\u0447\u0435\u043d" : TextFormatting.RED + "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d")));
        }
        if (button.id == 1001 && !this.reVersion.isEmpty()) {
            ProtocolVersion version = switch (this.reVersion) {
                case "1.7.10" -> ProtocolVersion.v1_7_6;
                case "1.8.9" -> ProtocolVersion.v1_8;
                case "1.9.4" -> ProtocolVersion.v1_9_3;
                case "1.10" -> ProtocolVersion.v1_10;
                case "1.11.2" -> ProtocolVersion.v1_11_1;
                case "1.13.2" -> ProtocolVersion.v1_13_2;
                case "1.14.4" -> ProtocolVersion.v1_14_4;
                case "1.15.2" -> ProtocolVersion.v1_15_2;
                case "1.16.5" -> ProtocolVersion.v1_16_4;
                case "1.17.1" -> ProtocolVersion.v1_17_1;
                case "1.18.2" -> ProtocolVersion.v1_18_2;
                case "1.19.4" -> ProtocolVersion.v1_19_4;
                case "1.20.2" -> ProtocolVersion.v1_20_2;
                default -> ProtocolVersion.v1_12_2;
            };
            if (version != null && !this.reVersion.isEmpty()) {
                ViaLoadingBase.getInstance().reload(version);
                this.connectToIp(lastServer);
                this.reVersion = "";
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (autoReconnect && this.ticks > 78) {
            this.connectToIp(lastServer);
            this.ticks = 0;
        } else if (!Panic.stop) {
            ++this.ticks;
        }
        if (does && this.ticks > 90) {
            this.connectToIp(lastServer);
            does = false;
        }
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (!Panic.stop) {
            RenderUtils.drawScreenShaderBackground(sr, mouseX, mouseY);
        } else {
            this.drawDefaultBackground();
        }
        this.drawCenteredString(this.fontRendererObj, this.reason, width / 2, height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 0xAAAAAA);
        int i = height / 2 - this.textHeight / 2;
        if (this.multilineMessage != null) {
            for (String s : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, s, width / 2, i, 0xFFFFFF);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    static {
        autoReconnect = false;
        does = false;
    }
}

