package ClassSub;

import cn.Hanabi.*;
import java.util.*;
import java.awt.*;
import cn.Hanabi.utils.fontmanager.*;
import javafx.scene.media.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class Class333 extends GuiScreen
{
    public ArrayList<Class296> allTracks;
    public int x;
    public int y;
    public int x2;
    public int y2;
    public int windowWidth;
    public int windowHeight;
    public boolean drag;
    public Class119 songListID;
    public Class119 trackSearch;
    private Class140 handler;
    public ArrayList<Class240> trackSlots;
    public int wheelStateTrack;
    public static double wheelSmoothTrack;
    public static double wheelSmoothValue;
    
    
    public Class333(final int x, final int y, final int windowWidth, final int windowHeight) {
        this.drag = false;
        this.handler = new Class140(0);
        this.trackSlots = new ArrayList<Class240>();
        this.x = x;
        this.y = y;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
    
    public void initGui() {
        this.allTracks = Hanabi.INSTANCE.musicMgr.allTracks;
        if (!this.trackSlots.isEmpty()) {
            this.trackSlots.clear();
        }
        if (!this.allTracks.isEmpty()) {
            final Iterator<Class296> iterator = this.allTracks.iterator();
            while (iterator.hasNext()) {
                this.trackSlots.add(new Class240(iterator.next(), this.windowWidth, this.windowHeight));
            }
        }
        this.songListID = new Class119(0, this.fontRendererObj, 2, 2, this.windowWidth - 8, 10);
        this.trackSearch = new Class119(1, this.fontRendererObj, 2, 2, this.windowWidth - 8, 10);
        super.initGui();
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        Class246.drawRoundedRect(this.x - 1, this.y - 15, this.x + this.windowWidth + 1, this.y + this.windowHeight + 1, 6.0f, new Color(-14848033).brighter().getRGB());
        Class246.drawRoundedRect(this.x, this.y, this.x + this.windowWidth, this.y + this.windowHeight, 6.0f, -15000805);
        Class246.drawRoundedRect(this.x + 2, this.y + 50, this.x + this.windowWidth - 2, this.y + this.windowHeight - 2, 6.0f, Class15.BLACK.c);
        Hanabi.INSTANCE.fontManager.comfortaa15.drawString("Netease Music Player", this.x + 5.0f, this.y - 12, Class15.WHITE.c);
        Class246.circle(this.x + this.windowWidth - 10, this.y - 7, 3.0f, Class15.RED.c);
        if (this.isHovering(n, n2, this.x + this.windowWidth - 14, this.y - 11, this.x + this.windowWidth - 6, this.y - 3) && this.handler.canExcecute()) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        Class246.drawRoundedRect(this.x + 140, this.y + 30, this.x + 157, this.y + 46, 2.0f, Class15.GREY.c);
        Hanabi.INSTANCE.fontManager.icon25.drawString(HanabiFonts.ICON_PLAYER_REPEAT, this.x + 142.0f, this.y + 32, -1);
        Class246.circle(this.x + 152, this.y + 42, 2.0f, Class286.getInstance().isLoop ? Class15.GREEN.c : Class15.RED.c);
        if (this.isHovering(n, n2, this.x + 140, this.y + 30, this.x + 157, this.y + 46) && this.handler.canExcecute()) {
            Class286.getInstance().isLoop = !Class286.getInstance().isLoop;
            Class200.tellPlayer(EnumChatFormatting.GOLD + "[MusicPlayer] " + EnumChatFormatting.GRAY + "重新播放歌曲后生�?.");
        }
        if (this.isHovering(n, n2, this.x + 58, this.y + 29, this.x + 75, this.y + 46) && this.handler.canExcecute()) {
            if (Class286.getInstance().getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                Class286.getInstance().getMediaPlayer().pause();
            }
            else {
                Class286.getInstance().getMediaPlayer().play();
            }
        }
        Class246.circle(this.x + 170, this.y + 38, 8.0f, Class15.GREY.c);
        Hanabi.INSTANCE.fontManager.comfortaa16.drawString(Class286.lyricCoding ? "G" : "U", this.x + 166.5f, this.y + 34, -1);
        if (this.isHovering(n, n2, this.x + 162, this.y + 29, this.x + 178, this.y + 46) && this.handler.canExcecute()) {
            Class286.lyricCoding = !Class286.lyricCoding;
            Class200.tellPlayer(EnumChatFormatting.GOLD + "[MusicPlayer] " + EnumChatFormatting.GRAY + "当前编码�? " + (Class286.lyricCoding ? "GBK" : "UTF-8") + ", 请重新播放歌曲以生效.");
        }
        this.songListID.xPosition = this.x + 4;
        this.songListID.yPosition = this.y + 52;
        this.songListID.width = 90;
        this.songListID.drawTextBox();
        if (this.songListID.getText().isEmpty()) {
            Hanabi.INSTANCE.fontManager.wqy18.drawString("歌单ID", this.x + 8.0f, this.y + 52, Class15.WHITE.c);
        }
        this.trackSearch.xPosition = this.x + 106;
        this.trackSearch.yPosition = this.y + 52;
        this.trackSearch.width = 90;
        this.trackSearch.drawTextBox();
        if (this.trackSearch.getText().isEmpty()) {
            Hanabi.INSTANCE.fontManager.wqy18.drawString("歌单内搜�?", this.x + 108.0f, this.y + 52, Class15.WHITE.c);
        }
        if (Hanabi.INSTANCE.musicMgr.getCurrentTrack() != null && Class286.getInstance().getMediaPlayer() != null) {
            final Class296 currentTrack = Hanabi.INSTANCE.musicMgr.getCurrentTrack();
            Class246.drawRoundedRect(this.x + 100, this.y + 30, this.x + 115, this.y + 46, 2.0f, Class15.GREY.c);
            Hanabi.INSTANCE.fontManager.comfortaa20.drawString("-", this.x + 105.0f, this.y + 33, -1);
            if (this.isHovering(n, n2, this.x + 100, this.y + 30, this.x + 115, this.y + 46) && this.handler.canExcecute()) {
                Class286.getInstance().getMediaPlayer().setVolume(Math.max(0.1, Class286.getInstance().getMediaPlayer().getVolume() - 0.1));
                Class286.getInstance().setVolume(Class286.getInstance().getMediaPlayer().getVolume());
            }
            Class246.drawRoundedRect(this.x + 120, this.y + 30, this.x + 135, this.y + 46, 2.0f, Class15.GREY.c);
            Hanabi.INSTANCE.fontManager.comfortaa20.drawString("+", this.x + 125.0f, this.y + 33, -1);
            if (this.isHovering(n, n2, this.x + 120, this.y + 30, this.x + 135, this.y + 46) && this.handler.canExcecute()) {
                Class286.getInstance().getMediaPlayer().setVolume(Math.max(0.1, Class286.getInstance().getMediaPlayer().getVolume() + 0.1));
                Class286.getInstance().setVolume(Class286.getInstance().getMediaPlayer().getVolume());
            }
            Class246.circle(this.x + 67, this.y + 38, 8.0f, Class15.GREY.c);
            if (Class286.getInstance().getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                Hanabi.INSTANCE.fontManager.icon40.drawString(HanabiFonts.ICON_PLAYER_PAUSE, this.x + 57.0f, this.y + 28, -1);
            }
            else {
                Hanabi.INSTANCE.fontManager.icon40.drawString(HanabiFonts.ICON_PLAYER_PLAY, this.x + 57.0f, this.y + 28, -1);
            }
            Class246.circle(this.x + 87, this.y + 38, 8.0f, Class15.GREY.c);
            Hanabi.INSTANCE.fontManager.icon20.drawString(HanabiFonts.ICON_PLAYER_NEXT, this.x + 82.0f, this.y + 33, -1);
            if (this.isHovering(n, n2, this.x + 78, this.y + 29, this.x + 95, this.y + 46) && this.handler.canExcecute()) {
                Class286.getInstance().next();
            }
            if (Class286.getInstance().circleLocations.containsKey(Hanabi.INSTANCE.musicMgr.getCurrentTrack().getId())) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture((ResourceLocation)Hanabi.INSTANCE.musicMgr.circleLocations.get(Hanabi.INSTANCE.musicMgr.getCurrentTrack().getId()));
                this.drawScaledTexturedModalRect(this.x + 9, this.y + 4, 0.0, 0.0, 42.0, 42.0, 6.1f);
                GL11.glPopMatrix();
            }
            else {
                Class286.getInstance().getCircle(Hanabi.INSTANCE.musicMgr.getCurrentTrack());
            }
            Class246.drawArc(this.x + 30, this.y + 25, 21.0, Class15.WHITE.c, 0, 360.0, 4);
            try {
                Class246.drawArc(this.x + 30, this.y + 25, 21.0, Class15.BLUE.c, 180, 180.0f + (float)(Class286.getInstance().getMediaPlayer().getCurrentTime().toSeconds() / Math.max(1.0, Class286.getInstance().getMediaPlayer().getStopTime().toSeconds())) * 100.0f * 3.6f, 4);
            }
            catch (Exception ex) {}
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            Class246.doGlScissor(this.x + 60, this.y + 6, this.windowWidth - 64, 25);
            Hanabi.INSTANCE.fontManager.wqy18.drawString(currentTrack.getName(), this.x + 60.0f, this.y + 6, Class15.WHITE.c);
            Hanabi.INSTANCE.fontManager.wqy18.drawString(currentTrack.getArtists(), this.x + 60.0f, this.y + 17, Class15.WHITE.c);
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        else {
            Class246.circle(this.x + 30, this.y + 25, 20.0f, -1);
            Hanabi.INSTANCE.fontManager.wqy18.drawString("未播放任何歌�?", this.x + 55.0f, this.y + 6, Class15.WHITE.c);
        }
        if (Class286.getInstance().loadingThread != null) {
            if (Class344.INSTANCE.downloadProgress != "") {
                Class246.drawArc(this.x + 30, this.y + 25, 21.0, Class15.AQUA.c, 180, 180.0f + Integer.valueOf(Class344.INSTANCE.downloadProgress) * 3.6f, 4);
            }
            else {
                Class246.drawArc(this.x + 30, this.y + 25, 21.0, Class15.AQUA.c, 180, 180.0, 4);
            }
        }
        Class333.wheelSmoothTrack = Class246.getAnimationState(Class333.wheelSmoothTrack, this.wheelStateTrack * 30, 1000.0);
        int n4 = (int)(this.y + Class333.wheelSmoothTrack) + 40 + 25;
        int n5 = 1;
        GL11.glPushMatrix();
        Class246.doGlScissor(this.x + 4, this.y + 65, this.windowWidth - 8, this.windowHeight - 68);
        GL11.glEnable(3089);
        if (!this.trackSlots.isEmpty() && this.trackSlots != null) {
            for (int i = 0; i < this.trackSlots.size(); ++i) {
                final Class240 class240 = this.trackSlots.get(i);
                if (this.trackSearch.getText().isEmpty() || class240.thisTrack.getName().toLowerCase().indexOf(this.trackSearch.getText().toLowerCase()) != -1) {
                    if (n4 < this.y + this.windowHeight - 10 && n4 > this.y + 50) {
                        class240.draw(this.x + 4, n4);
                        Hanabi.INSTANCE.fontManager.comfortaa18.drawString(String.valueOf(n5), this.x + 8.0f, n4 + 3, -1);
                        if (this.isHovering(n, n2, this.x + 2, this.y + 65, this.x + this.windowWidth - 2, this.y + 65 + this.windowHeight - 68)) {
                            class240.onClick(n, n2, this.x, n4);
                        }
                    }
                    ++n5;
                    n4 += 16;
                }
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.processScroll(n, n2);
        this.moveWindow(n, n2);
        super.drawScreen(n, n2, n3);
    }
    
    public void drawScaledTexturedModalRect(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        final float n8 = 0.00390625f * n7;
        final float n9 = 0.00390625f * n7;
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        getWorldRenderer.pos(n + 0.0, n2 + n6, (double)this.zLevel).tex((double)((float)(n3 + 0.0) * n8), (double)((float)(n4 + n6) * n9)).endVertex();
        getWorldRenderer.pos(n + n5, n2 + n6, (double)this.zLevel).tex((double)((float)(n3 + n5) * n8), (double)((float)(n4 + n6) * n9)).endVertex();
        getWorldRenderer.pos(n + n5, n2 + 0.0, (double)this.zLevel).tex((double)((float)(n3 + n5) * n8), (double)((float)(n4 + 0.0) * n9)).endVertex();
        getWorldRenderer.pos(n + 0.0, n2 + 0.0, (double)this.zLevel).tex((double)((float)(n3 + 0.0) * n8), (double)((float)(n4 + 0.0) * n9)).endVertex();
        getInstance.draw();
    }
    
    public void processScroll(final int n, final int n2) {
        final int dWheel = Mouse.getDWheel();
        if (this.isHovering(n, n2, this.x + 2, this.y + 65, this.x + this.windowWidth - 2, this.y + 65 + this.windowHeight - 68)) {
            if (dWheel > 0) {
                if (this.wheelStateTrack < 0) {
                    ++this.wheelStateTrack;
                }
            }
            else if (dWheel < 0 && this.wheelStateTrack * 30 > this.trackSlots.size() * -15.8f) {
                --this.wheelStateTrack;
            }
        }
    }
    
    public void moveWindow(final int n, final int n2) {
        if (this.isHoveringWindow(n, n2) && this.handler.canExcecute()) {
            this.drag = true;
            this.x2 = n - this.x;
            this.y2 = n2 - this.y;
        }
        if (this.drag) {
            this.x = n - this.x2;
            this.y = n2 - this.y2;
        }
        if (!Mouse.isButtonDown(0)) {
            this.drag = false;
        }
    }
    
    private boolean isHoveringWindow(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.windowWidth && n2 >= this.y - 10 && n2 <= this.y - 1;
    }
    
    private boolean isHovering(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.songListID.mouseClicked(n, n2, n3);
        this.trackSearch.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    protected void keyTyped(final char c, final int n) throws IOException {
        switch (n) {
            case 28: {
                if (this.songListID.isFocused && !this.songListID.getText().isEmpty()) {
                    Hanabi.INSTANCE.musicMgr.allTracks = Class164.INSTANCE.getSongs(this.songListID.getText());
                    this.allTracks = Hanabi.INSTANCE.musicMgr.allTracks;
                    if (!this.trackSlots.isEmpty()) {
                        this.trackSlots.clear();
                    }
                    if (!this.allTracks.isEmpty()) {
                        final Iterator<Class296> iterator = this.allTracks.iterator();
                        while (iterator.hasNext()) {
                            this.trackSlots.add(new Class240(iterator.next(), this.windowWidth, this.windowHeight));
                        }
                    }
                    this.wheelStateTrack = 0;
                    this.songListID.setText("");
                    break;
                }
                break;
            }
        }
        this.songListID.textboxKeyTyped(c, n);
        this.trackSearch.textboxKeyTyped(c, n);
        super.keyTyped(c, n);
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);
    }
    
    public void updateScreen() {
        super.updateScreen();
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
}
