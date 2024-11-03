package net.silentclient.client.gui.minecraft;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.admin.AdminRender;
import net.silentclient.client.cosmetics.Cosmetics;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.event.impl.ServerLeaveEvent;
import net.silentclient.client.gui.GuiMultiplayerInGame;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.TooltipIconButton;
import net.silentclient.client.gui.friends.FriendsListOverlay;
import net.silentclient.client.gui.hud.Watermark;
import net.silentclient.client.gui.lite.clickgui.ClickGUI;
import net.silentclient.client.gui.modmenu.ModMenu;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.utils.MenuBlurUtils;

import java.io.IOException;

public class GuiIngameMenu extends SilentScreen
{
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
//        MenuBlurUtils.loadBlur();
        this.buttonList.clear();
        int i = -16;
        this.buttonList.add(new Button(1, this.width / 2 - 100, this.height / 4 + 120 + i,  "Save and Quit to Title", true));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = "Disconnect";
        }

        this.buttonList.add(new Button(4, this.width / 2 - 100, this.height / 4 + 24 + i, "Back to Game", true));
        this.buttonList.add(new Button(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, "MC Options", true));
        this.buttonList.add(new Button(8, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, "Silent Options", true));
        this.buttonList.add(new Button(11, this.width / 2 - 100, this.height / 4 + 72 + i, "Mods", true));
        this.buttonList.add(new Button(7, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, "Server List", true));
        this.buttonList.add(new Button(10, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, "Cosmetics", true));
        
        this.buttonList.add(new Button(9, 2, this.height - 10 + i, 95, 20, "Reload Resources", true));
        int iconX = 4;
        this.buttonList.add(new TooltipIconButton(5, iconX, 5, 15, 15, 7, 7, new ResourceLocation("silentclient/icons/trophy.png"), "Achievements"));
        iconX += 18;
        this.buttonList.add(new TooltipIconButton(6, iconX, 5, 15, 15, 7, 7, new ResourceLocation("silentclient/icons/graph.png"), "Statistics"));
        iconX += 18;
        if(mc.isSingleplayer() && !mc.getIntegratedServer().getPublic()) {
            this.buttonList.add(new TooltipIconButton(12, iconX, 5, 15, 15, 7, 7, new ResourceLocation("silentclient/icons/lan.png"), "Open to LAN"));
            iconX += 18;
        }
        this.buttonList.add(new TooltipIconButton(13, iconX, 5, 15, 15, 7, 7, new ResourceLocation("silentclient/icons/friends.png"), "Friends"));
        iconX += 18;
        if(Client.getInstance().getAccount().isStaff()) {
        	this.buttonList.add(new TooltipIconButton(14, iconX, 5, 15, 15, 7, 7, new ResourceLocation("silentclient/icons/store_icon.png"), "Admin Render"));
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isSingleplayer();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(Client.getInstance().getMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(Client.getInstance().getMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(Client.getInstance().getMainMenu()));
                }
                new ServerLeaveEvent().call();

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiMultiplayerInGame(this));
                break;
            case 8:
            	this.mc.displayGuiScreen(Client.getInstance().getGlobalSettings().isLite() ? new ClickGUI(ModCategory.SETTINGS) : new ModMenu(ModCategory.SETTINGS));
            	break;
            case 9:
            	Cosmetics.reload(this.mc.thePlayer);
            	break;
            case 10:
            	this.mc.displayGuiScreen(new CosmeticsGui());
            	break;
            case 11:
                this.mc.displayGuiScreen(Client.getInstance().getGlobalSettings().isLite() ? new ClickGUI() : new ModMenu());
                break;
            case 12:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 13:
                this.mc.displayGuiScreen(new FriendsListOverlay());
                break;
            case 14:
            	this.mc.displayGuiScreen(new AdminRender());
            	break;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        MenuBlurUtils.renderBackground(this);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        new Watermark().render((this.width / 2) - (110 / 2), 40);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void onGuiClosed() {
//    	if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Background Blur").getValBoolean()) {
//			Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
//		}
    	super.onGuiClosed();
    }
}
