// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui;

import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import xyz.niggfaclient.font.Fonts;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiOptions;
import xyz.niggfaclient.gui.login.GuiAltLogin;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCustomMainMenu extends GuiScreen
{
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        final int sHeight = this.height / 4 + 90;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 73, sHeight + 5, 150, 20, "Singleplayer"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 73, sHeight + 27, 150, 20, "Multiplayer"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 73, sHeight + 49, 150, 20, "AltManager"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 73, sHeight + 78, 70, 20, "Options.."));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 7, sHeight + 78, 70, 20, "Exit Game"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                this.mc.shutdownMinecraftApplet();
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtils.drawGradient((float)this.width, (float)this.height);
        Client.getInstance().getDiscordRP().update("https://discord.gg/Bah29YMQG6", "Main Menu");
        final ScaledResolution sr = new ScaledResolution(this.mc);
        for (final GuiButton g : this.buttonList) {
            g.drawButton(this.mc, mouseX, mouseY);
        }
        final String currentDayOfWeek = Calendar.getInstance().getDisplayName(7, 2, Locale.getDefault());
        final String currentMonth = new SimpleDateFormat("MMMM").format(new Date());
        final String currentDay = new SimpleDateFormat("dd").format(new Date());
        final String totalText = currentDayOfWeek + ", " + currentMonth + " " + currentDay;
        Fonts.sf23.drawCenteredStringWithShadow(totalText, sr.getScaledWidth() / 2.0f, sr.getScaledHeight() / 4.0f + 70.0f, -1);
        Fonts.sf21.drawStringWithShadow(Client.getInstance().clientName + " Client " + Client.getInstance().version + " (#" + Client.getInstance().getBuildDate() + ")", (sr.getScaledWidth() - Fonts.sf21.getStringWidth(Client.getInstance().clientName + " Client " + Client.getInstance().version + " (#" + Client.getInstance().getBuildDate() + ")")) / 115.0f, (float)(sr.getScaledHeight() - 13), -1);
        RenderUtils.drawLogo(new ResourceLocation("minecraft", "logo.png"), sr.getScaledWidth() / 2.0f - 82.0f, sr.getScaledHeight() / 4.0f - 55.0f, 165.0f, 165.0f);
    }
}
