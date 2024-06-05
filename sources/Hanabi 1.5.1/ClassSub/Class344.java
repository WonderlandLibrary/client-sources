package ClassSub;

import cn.Hanabi.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import cn.Hanabi.utils.fontmanager.*;

public enum Class344
{
    INSTANCE;
    
    public String ly;
    public String tly;
    public String downloadProgress;
    public long readedSecs;
    public long totalSecs;
    public float animation;
    public Class205 timer;
    public boolean firstTime;
    public Hanabi hanaInstance;
    private static final Class344[] $VALUES;
    
    
    private Class344() {
        this.ly = "";
        this.tly = "";
        this.downloadProgress = "0";
        this.readedSecs = 0L;
        this.totalSecs = 0L;
        this.animation = 0.0f;
        this.timer = new Class205();
        this.firstTime = true;
        this.hanaInstance = Hanabi.INSTANCE;
    }
    
    public void renderOverlay() {
        final int intValue = (int)(Object)Class118.musicPosX.getValueState();
        final int intValue2 = (int)(Object)Class118.musicPosY.getValueState();
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.hanaInstance.musicMgr.getCurrentTrack() != null && Class286.getInstance().getMediaPlayer() != null) {
            this.readedSecs = (int)Class286.getInstance().getMediaPlayer().getCurrentTime().toSeconds();
            this.totalSecs = (int)Class286.getInstance().getMediaPlayer().getStopTime().toSeconds();
        }
        if (this.hanaInstance.musicMgr.getCurrentTrack() != null && Class286.getInstance().getMediaPlayer() != null) {
            this.hanaInstance.fontManager.wqy18.drawString(this.hanaInstance.musicMgr.getCurrentTrack().getName() + " - " + this.hanaInstance.musicMgr.getCurrentTrack().getArtists(), 36.0f + intValue, 10 + intValue2, Class15.WHITE.c);
            this.hanaInstance.fontManager.wqy18.drawString(this.formatSeconds((int)this.readedSecs) + "/" + this.formatSeconds((int)this.totalSecs), 36.0f + intValue, 20 + intValue2, -1);
            if (Class286.getInstance().circleLocations.containsKey(this.hanaInstance.musicMgr.getCurrentTrack().getId())) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Class246.drawImage(this.hanaInstance.musicMgr.circleLocations.get(this.hanaInstance.musicMgr.getCurrentTrack().getId()), 4 + intValue, 6 + intValue2, 28, 28);
                GL11.glPopMatrix();
            }
            else {
                Class286.getInstance().getCircle(this.hanaInstance.musicMgr.getCurrentTrack());
            }
            try {
                final float n = (float)(Class286.getInstance().getMediaPlayer().getCurrentTime().toSeconds() / Math.max(1.0, Class286.getInstance().getMediaPlayer().getStopTime().toSeconds())) * 100.0f;
                Class246.drawArc(18 + intValue, 19 + intValue2, 14.0, Class15.WHITE.c, 0, 360.0, 4);
                Class246.drawArc(18 + intValue, 19 + intValue2, 14.0, Class15.BLUE.c, 180, 180.0f + n * 3.6f, 4);
            }
            catch (Exception ex) {}
        }
        final UnicodeFontRenderer wqy25 = Hanabi.INSTANCE.fontManager.wqy25;
        final int intValue3 = (int)(Object)Class118.musicPosYlyr.getValueState();
        wqy25.drawCenterOutlinedString(this.ly, scaledResolution.getScaledWidth() / 2.0f - 0.5f, scaledResolution.getScaledHeight() - 120 - 80 + intValue3, Class286.instance.tLyric.isEmpty() ? -7520544 : -49273, Class286.instance.tLyric.isEmpty() ? -1515777 : -8481);
        wqy25.drawCenterOutlinedString(this.tly, scaledResolution.getScaledWidth() / 2.0f, scaledResolution.getScaledHeight() - 100 + 0.5f - 80.0f + intValue3, -49273, -8481);
        if (Class286.showMsg) {
            if (this.firstTime) {
                this.timer.reset();
                this.firstTime = false;
            }
            final UnicodeFontRenderer wqy26 = Hanabi.INSTANCE.fontManager.wqy18;
            final UnicodeFontRenderer wqy27 = Hanabi.INSTANCE.fontManager.wqy25;
            final float n2 = wqy26.getStringWidth(this.hanaInstance.musicMgr.getCurrentTrack().getName());
            final float n3 = wqy27.getStringWidth("正在播放");
            final float n4 = (n2 > n3) ? n2 : n3;
            Class246.drawRect(scaledResolution.getScaledWidth() - this.animation - 50.0f, 5.0f, scaledResolution.getScaledWidth(), 40.0f, Class120.reAlpha(Class15.BLACK.c, 0.7f));
            if (Class286.getInstance().circleLocations.containsKey(this.hanaInstance.musicMgr.getCurrentTrack().getId())) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Class246.drawImage(this.hanaInstance.musicMgr.circleLocations.get(this.hanaInstance.musicMgr.getCurrentTrack().getId()), scaledResolution.getScaledWidth() - this.animation - 45.0f, 8.0f, 28.0f, 28.0f);
                GL11.glPopMatrix();
            }
            else {
                Class286.getInstance().getCircle(this.hanaInstance.musicMgr.getCurrentTrack());
            }
            Class246.drawArc(scaledResolution.getScaledWidth() - this.animation - 31.0f, 22.0f, 14.0, Class15.WHITE.c, 0, 360.0, 2);
            wqy27.drawString("正在播放", scaledResolution.getScaledWidth() - this.animation - 12.0f, 8.0f, Class15.WHITE.c);
            wqy26.drawString(this.hanaInstance.musicMgr.getCurrentTrack().getName(), scaledResolution.getScaledWidth() - this.animation - 12.0f, 26.0f, Class15.WHITE.c);
            if (this.timer.isDelayComplete(5000L)) {
                this.animation = (float)Class246.getAnimationState(this.animation, -(n4 + 50.0f), 300.0);
                if (this.animation <= -(n4 + 50.0f)) {
                    Class286.showMsg = false;
                    this.firstTime = true;
                }
            }
            else {
                this.animation = (float)Class246.getAnimationState(this.animation, n4, 300.0);
            }
        }
        GlStateManager.resetColor();
    }
    
    public String formatSeconds(int n) {
        String string = "";
        final int n2 = n / 60;
        if (n2 < 10) {
            string += "0";
        }
        String s = string + n2 + ":";
        n %= 60;
        if (n < 10) {
            s += "0";
        }
        return s + n;
    }
    
    static {
        $VALUES = new Class344[] { Class344.INSTANCE };
    }
}
