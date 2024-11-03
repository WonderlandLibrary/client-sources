package net.silentclient.client.gui.multiplayer.components;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.ServerDataFeature;
import net.silentclient.client.gui.elements.Tooltip;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.multiplayer.SilentMultiplayerGui;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.FeaturedServers;
import net.silentclient.client.utils.MouseCursorHandler;
import org.apache.commons.lang3.Validate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerComponent {
    private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
    private final SilentMultiplayerGui owner;
    private final Minecraft mc;
    private final ServerData server;
    private final ResourceLocation serverIcon;
    private String field_148299_g;
    private DynamicTexture field_148305_h;
    private long field_148298_f;

    public ServerComponent(SilentMultiplayerGui p_i45048_1_, ServerData serverIn)
    {
        this.owner = p_i45048_1_;
        this.server = serverIn;
        this.mc = Minecraft.getMinecraft();
        this.serverIcon = new ResourceLocation("servers/" + serverIn.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
    }

    public MouseCursorHandler.CursorType draw(int serverIndex, int mouseX, int mouseY, float x, float y, boolean isSelected) {
        MouseCursorHandler.CursorType cursorType = null;
        boolean featured = this.server instanceof ServerDataFeature;
        boolean unsaved = featured && FeaturedServers.findByIPFeaturedServerInfo(this.server.serverIP, Client.getInstance().getGlobalSettings().getSavedFeaturedServers()) == null;
        if (!this.server.field_78841_f)
        {
            this.server.field_78841_f = true;
            this.server.pingToServer = -2L;
            this.server.serverMOTD = "";
            this.server.populationInfo = "";
            field_148302_b.submit(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        ServerComponent.this.owner.getOldServerPinger().ping(ServerComponent.this.server);
                    }
                    catch (UnknownHostException var2)
                    {
                        ServerComponent.this.server.pingToServer = -1L;
                        ServerComponent.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
                    }
                    catch (Exception var3)
                    {
                        ServerComponent.this.server.pingToServer = -1L;
                        ServerComponent.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
                    }
                }
            });
        }
        boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, y, 240, 35);
        RenderUtil.drawRoundedOutline(x, y, 240, 35, 3, isSelected ? 3 : 1, isSelected ? -1 : Theme.borderColor().getRGB());
        if(isHovered) {
            RenderUtil.drawRoundedRect(x, y, 240, 35, 3, new Color(255, 255, 255,  30).getRGB());
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }

        if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.field_148299_g))
        {
            this.field_148299_g = this.server.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.owner.getServerList().saveServerList();
        }

        RenderUtil.drawImage(this.field_148305_h != null ? serverIcon : UNKNOWN_SERVER, x + 2, y + 2, 31, 31, false);

        if(MouseUtils.isInside(mouseX, mouseY, x + 2, y + 2, 31, 31) && !featured) {
            if(this.owner.canUpSwap(serverIndex)) {
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/page-up.png"), x + 2 + (31 / 2) - (15 / 2), y + 2, 15, 15, true, MouseUtils.isInside(mouseX, mouseY, x + 2 + (31 / 2) - (15 / 2), y + 2, 15, 15) ? new Color(255, 255, 255).getRGB() : Theme.borderColor().getRGB());
            }

            if(this.owner.canDownSwap(serverIndex)) {
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/page-down.png"), x + 2 + (31 / 2) - (15 / 2), y + 2 + 31 - 16, 15, 15, true, MouseUtils.isInside(mouseX, mouseY, x + 2 + (31 / 2) - (15 / 2), y + 2 + 31 - 16, 15, 15) ? new Color(255, 255, 255).getRGB() : Theme.borderColor().getRGB());
            }
        }

        mc.fontRendererObj.drawString(server.serverName, x + 2 + 31 + 3, (int) y + 3, -1, true);
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, 240 - 36);

        for (int i = 0; i < Math.min(list.size(), 2); ++i)
        {
            this.mc.fontRendererObj.drawString((String)list.get(i), (int) x + 2 + 31 + 3, (int) y + 5 + this.mc.fontRendererObj.FONT_HEIGHT * (i + 1), -1);
        }

        boolean flag = this.server.version > 47;
        boolean flag1 = this.server.version < 47;
        boolean flag2 = flag || flag1;
        String s2 = flag2 ? EnumChatFormatting.DARK_RED + this.server.gameVersion : this.server.populationInfo;
        int j = this.mc.fontRendererObj.getStringWidth(s2);
        this.mc.fontRendererObj.drawString(s2, (int) x + 240 - j - 15 - 2 - (featured ? 10 : 0) - (unsaved ? 10 : 0), (int) y + 3, -1);
        int k = 0;
        String s = null;
        int l;
        String s1;

        if (flag2)
        {
            l = 5;
            s1 = flag ? "Client out of date!" : "Server out of date!";
            s = this.server.playerList;
        }
        else if (this.server.field_78841_f && this.server.pingToServer != -2L)
        {
            if (this.server.pingToServer < 0L)
            {
                l = 5;
            }
            else if (this.server.pingToServer < 150L)
            {
                l = 0;
            }
            else if (this.server.pingToServer < 300L)
            {
                l = 1;
            }
            else if (this.server.pingToServer < 600L)
            {
                l = 2;
            }
            else if (this.server.pingToServer < 1000L)
            {
                l = 3;
            }
            else
            {
                l = 4;
            }

            if (this.server.pingToServer < 0L)
            {
                s1 = "(no connection)";
            }
            else
            {
                s1 = this.server.pingToServer + "ms";
                s = this.server.playerList;
            }
        }
        else
        {
            k = 1;
            l = (int)(Minecraft.getSystemTime() / 100L + (long)(1 * 2) & 7L);

            if (l > 4)
            {
                l = 8 - l;
            }

            s1 = "Pinging...";
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture((int) x + 240 - 15 - (featured ? 10 : 0) - (unsaved ? 10 : 0), (int) y + 2, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256.0F, 256.0F);
        Tooltip.render(mouseX, mouseY, x + 240 - 15 - (featured ? 10 : 0) - (unsaved ? 10 : 0), y + 2, 10, 8, s1);

        if(featured) {
            RenderUtil.drawImage(new ResourceLocation("silentclient/icons/star.png"), x + 240 - 13 - (unsaved ? 10 : 0), y + 2, 8, 8);
            Tooltip.render(mouseX, mouseY, x + 240 - 13 - (unsaved ? 10 : 0), y + 2, 8, 8, "Featured Server");

            if(unsaved) {
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/save-icon.png"), x + 240 - 13, y + 2, 8, 8);
                Tooltip.render(mouseX, mouseY, x + 240 - 13, y + 2, 8, 8, "Save Server");
            }
        }

        return cursorType;
    }

    public boolean mouseClicked(int serverIndex, int mouseX, int mouseY, float x, float y, boolean isSelected) {
        boolean isHovered = MouseUtils.isInside(mouseX, mouseY, x, y, 240, 35);
        if(isHovered) {
            if(MouseUtils.isInside(mouseX, mouseY, x + 2, y + 2, 31, 31) && !(this.server instanceof ServerDataFeature)) {
                if(this.owner.canUpSwap(serverIndex) && MouseUtils.isInside(mouseX, mouseY, x + 2 + (31 / 2) - (15 / 2), y + 2, 15, 15)) {
                    Client.logger.info("Swapping server " + serverIndex + " to up");
                    this.owner.swapUp(serverIndex, GuiScreen.isShiftKeyDown());
                    return isHovered;
                }

                if(this.owner.canDownSwap(serverIndex) && MouseUtils.isInside(mouseX, mouseY, x + 2 + (31 / 2) - (15 / 2), y + 2 + 31 - 16, 15, 15)) {
                    Client.logger.info("Swapping server " + serverIndex + " to down");
                    this.owner.swapDown(serverIndex, GuiScreen.isShiftKeyDown());
                    return isHovered;
                }
            }
            if(this.server instanceof ServerDataFeature) {
                if(FeaturedServers.findByIPFeaturedServerInfo(this.server.serverIP, Client.getInstance().getGlobalSettings().getSavedFeaturedServers()) == null && MouseUtils.isInside(mouseX, mouseY, x + 240 - 13, y + 2, 8, 8)) {
                    Client.getInstance().getGlobalSettings().getSavedFeaturedServers().add(new FeaturedServers.FeaturedServerInfo(this.server.serverName, this.server.serverIP));
                    Client.getInstance().getGlobalSettings().save();
                    return isHovered;
                }
            }
            if(!isSelected) {
                this.owner.selectServer(serverIndex);
            } else {
                this.owner.connectToSelected();
            }
        }

        return isHovered;
    }

    private void prepareServerIcon()
    {
        if (this.server.getBase64EncodedIconData() == null)
        {
            this.mc.getTextureManager().deleteTexture(this.serverIcon);
            this.field_148305_h = null;
        }
        else
        {
            ByteBuf bytebuf = Unpooled.copiedBuffer((CharSequence)this.server.getBase64EncodedIconData(), Charsets.UTF_8);
            ByteBuf bytebuf1 = Base64.decode(bytebuf);
            BufferedImage bufferedimage;
            label101:
            {
                try
                {
                    bufferedimage = TextureUtil.readBufferedImage(new ByteBufInputStream(bytebuf1));
                    Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break label101;
                }
                catch (Throwable throwable)
                {
                    Client.logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", throwable);
                    this.server.setBase64EncodedIconData((String)null);
                }
                finally
                {
                    bytebuf.release();
                    bytebuf1.release();
                }

                return;
            }

            if (this.field_148305_h == null)
            {
                this.field_148305_h = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
                this.mc.getTextureManager().loadTexture(this.serverIcon, this.field_148305_h);
            }

            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.field_148305_h.getTextureData(), 0, bufferedimage.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }

    public ServerData getServer() {
        return server;
    }
}
