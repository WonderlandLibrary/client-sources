// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.client.gui;

import java.util.Iterator;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gg.childtrafficking.smokex.utils.player.HypixelUtils;
import net.minecraft.client.resources.I18n;
import java.util.List;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen
{
    private String banType;
    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    
    public GuiDisconnected(final GuiScreen screen, final String reasonLocalizationKey, final IChatComponent chatComp) {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
        if (this.message.getUnformattedText().contains("www.hypixel.net/appeal")) {
            this.banType = "Loading...";
            final Thread hypixelCheckingThread = new Thread(() -> {
                try {
                    final String banID = this.message.getUnformattedText().split("Ban ID: #")[1].split("\n")[0];
                    final String uuid = this.mc.session.getProfile().getId().toString().replaceAll("-", "");
                    final String banInformation = HypixelUtils.getHypixelBanInformation(banID, uuid);
                    final JsonElement jsonElement = new JsonParser().parse(banInformation);
                    if (jsonElement.isJsonObject()) {
                        final JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.get("success") != null && jsonObject.get("success").getAsBoolean()) {
                            if (jsonObject.get("punishmentCategory").getAsString().equals("hacks")) {
                                this.banType = "Watchdog (wtfrick?)";
                            }
                            else if (jsonObject.get("punishmentCategory").getAsString().equals("other")) {
                                this.banType = "Staff";
                            }
                            else {
                                this.banType = "Other";
                            }
                        }
                        else if (jsonObject.getAsJsonArray("errors") != null) {
                            this.banType = jsonObject.getAsJsonArray("errors").get(0).getAsJsonObject().get("code").getAsString();
                        }
                    }
                    else {
                        this.banType = "ERROR";
                    }
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
                return;
            });
            hypixelCheckingThread.setDaemon(true);
            hypixelCheckingThread.start();
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        if (this.banType != null) {
            this.drawCenteredString(this.fontRendererObj, "BAN TYPE: " + this.banType, this.width / 2, this.height / 2 - this.fontRendererObj.FONT_HEIGHT * 2 - 100, 16777215);
        }
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            for (final String s : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
