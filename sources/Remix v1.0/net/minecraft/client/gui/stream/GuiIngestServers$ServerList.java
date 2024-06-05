package net.minecraft.client.gui.stream;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import tv.twitch.broadcast.*;
import net.minecraft.client.stream.*;

class ServerList extends GuiSlot
{
    public ServerList(final Minecraft mcIn) {
        super(mcIn, GuiIngestServers.width, GuiIngestServers.height, 32, GuiIngestServers.height - 35, (int)(mcIn.fontRendererObj.FONT_HEIGHT * 3.5));
        this.setShowSelectionBox(false);
    }
    
    @Override
    protected int getSize() {
        return this.mc.getTwitchStream().func_152925_v().length;
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.mc.gameSettings.streamPreferredServer = this.mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl;
        this.mc.gameSettings.saveOptions();
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return this.mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final IngestServer var7 = this.mc.getTwitchStream().func_152925_v()[p_180791_1_];
        String var8 = var7.serverUrl.replaceAll("\\{stream_key\\}", "");
        String var9 = (int)var7.bitrateKbps + " kbps";
        String var10 = null;
        final IngestServerTester var11 = this.mc.getTwitchStream().func_152932_y();
        if (var11 != null) {
            if (var7 == var11.func_153040_c()) {
                var8 = EnumChatFormatting.GREEN + var8;
                var9 = (int)(var11.func_153030_h() * 100.0f) + "%";
            }
            else if (p_180791_1_ < var11.func_153028_p()) {
                if (var7.bitrateKbps == 0.0f) {
                    var9 = EnumChatFormatting.RED + "Down!";
                }
            }
            else {
                var9 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
            }
        }
        else if (var7.bitrateKbps == 0.0f) {
            var9 = EnumChatFormatting.RED + "Down!";
        }
        p_180791_2_ -= 15;
        if (this.isSelected(p_180791_1_)) {
            var10 = EnumChatFormatting.BLUE + "(Preferred)";
        }
        else if (var7.defaultServer) {
            var10 = EnumChatFormatting.GREEN + "(Default)";
        }
        GuiIngestServers.this.drawString(GuiIngestServers.access$000(GuiIngestServers.this), var7.serverName, p_180791_2_ + 2, p_180791_3_ + 5, 16777215);
        GuiIngestServers.this.drawString(GuiIngestServers.access$100(GuiIngestServers.this), var8, p_180791_2_ + 2, p_180791_3_ + GuiIngestServers.access$200(GuiIngestServers.this).FONT_HEIGHT + 5 + 3, 3158064);
        GuiIngestServers.this.drawString(GuiIngestServers.access$300(GuiIngestServers.this), var9, this.getScrollBarX() - 5 - GuiIngestServers.access$400(GuiIngestServers.this).getStringWidth(var9), p_180791_3_ + 5, 8421504);
        if (var10 != null) {
            GuiIngestServers.this.drawString(GuiIngestServers.access$500(GuiIngestServers.this), var10, this.getScrollBarX() - 5 - GuiIngestServers.access$600(GuiIngestServers.this).getStringWidth(var10), p_180791_3_ + 5 + 3 + GuiIngestServers.access$700(GuiIngestServers.this).FONT_HEIGHT, 8421504);
        }
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }
}
