// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import java.time.temporal.Temporal;
import java.time.Duration;
import java.util.StringJoiner;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.network.Packet;
import events.listeners.EventPostRender2D;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import events.listeners.EventRender2D;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S02PacketChat;
import events.listeners.EventPacket;
import events.Event;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.time.Instant;
import java.time.LocalTime;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class SessionInfo extends Module
{
    private int kills;
    private int deaths;
    
    public SessionInfo() {
        super("SessionInfo", Type.Visual, "SessionInfo", 0, Category.Visual);
        this.kills = 0;
        this.deaths = 0;
        Aqua.setmgr.register(new Setting("PosY", this, 25.0, 0.0, 440.0, true));
        Aqua.setmgr.register(new Setting("PosX", this, 4.0, 0.0, 840.0, true));
        Aqua.setmgr.register(new Setting("ConnectedPosX", this, true));
        Aqua.setmgr.register(new Setting("Mode", this, "Glow", new String[] { "Glow", "Shadow" }));
        Aqua.setmgr.register(new Setting("InfoMode", this, "Classic", new String[] { "Classic", "Modern", "NEW", "Aqua" }));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public static LocalTime getLocalTime(final long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId()).toLocalTime();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPacket) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof S02PacketChat) {
                final S02PacketChat chat = (S02PacketChat)packet;
                final String text = chat.getChatComponent().getUnformattedText();
                if (text.contains(SessionInfo.mc.thePlayer.getName())) {
                    for (final Entity entity : SessionInfo.mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP) && text.contains(entity.getName())) {
                            boolean kill = false;
                            final String[] s = text.split(" ");
                            for (int i = 0; i < s.length - 1; ++i) {
                                if (s[i].equals(SessionInfo.mc.thePlayer.getName())) {
                                    kill = false;
                                    break;
                                }
                                if (s[i].equals(entity.getName())) {
                                    kill = true;
                                    break;
                                }
                            }
                            if (kill) {
                                ++this.kills;
                            }
                            boolean death = false;
                            for (int j = 0; j < s.length - 1; ++j) {
                                if (s[j].equals(SessionInfo.mc.thePlayer.getName())) {
                                    death = true;
                                    break;
                                }
                                if (s[j].equals(entity.getName())) {
                                    death = false;
                                    break;
                                }
                            }
                            if (!death) {
                                continue;
                            }
                            ++this.deaths;
                        }
                    }
                }
            }
        }
        if (e instanceof EventRender2D) {
            if (ThemeScreen.themeLoaded && !ThemeScreen.themeAqua2) {
                return;
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("Classic")) {
                this.drawRectShaders();
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("Modern")) {
                this.drawRectShadersModern();
            }
        }
        if (e instanceof EventPostRender2D) {
            if (ThemeScreen.themeLoaded && !ThemeScreen.themeAqua2) {
                return;
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("NEW")) {
                this.drawNEWSession();
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("Aqua")) {
                this.drawAqua();
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("Classic")) {
                this.drawRects();
                this.drawStrings();
            }
            if (Aqua.setmgr.getSetting("SessionInfoInfoMode").getCurrentMode().equalsIgnoreCase("Modern")) {
                this.drawRectsModern();
                this.drawStringsModern();
            }
        }
    }
    
    public void drawNEWSession() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 112;
        final int height = 57;
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(posX - 1.0f, posY, width, height - 15, 1.0, new Color(0, 0, 0, 190)), false);
        }
        RenderUtil.drawRoundedRect2Alpha(posX - 1.0f, posY, width, height - 15, 1.0, new Color(0, 0, 0, 70));
        final StringJoiner joiner = new StringJoiner(", ");
        final Duration duration = Duration.between(getLocalTime(Aqua.INSTANCE.lastConnection), getLocalTime(System.currentTimeMillis()));
        long seconds = duration.getSeconds();
        long nano = 0L;
        if (seconds <= 0L) {
            nano = duration.getNano();
        }
        if (nano <= 0L) {
            long minutes = seconds / 60L;
            final long hours = minutes / 60L;
            if (hours > 0L) {
                joiner.add(hours + "");
            }
            while (minutes > 60L) {
                minutes -= 60L;
            }
            if (minutes > 0L) {
                joiner.add(minutes + "min");
            }
            while (seconds > 60L) {
                seconds -= 60L;
            }
            if (seconds > 0L) {
                joiner.add(seconds + "s");
            }
        }
        else if (nano > 1000000L) {
            joiner.add(nano / 1000000L + "ms");
        }
        else {
            joiner.add(nano + "ns");
        }
        Aqua.INSTANCE.tenacityNormal.drawCenteredString("Session Info", posX + 55.0f, posY + 1.0f, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), 0.0).getRGB());
        Aqua.INSTANCE.tenacitySmall.drawString("Kills : " + this.kills, posX + 2.0f, posY + 15.0f, -1);
        Aqua.INSTANCE.tenacitySmall.drawString("Deaths : " + this.deaths, posX + 35.0f, posY + 15.0f, -1);
        Aqua.INSTANCE.tenacitySmall.drawString("Playtime : " + joiner, posX + 2.0f, posY + 30.0f, -1);
    }
    
    public void drawAqua() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 122;
        final int height = 61;
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(posX - 1.0f, posY, width, height - 15, 3.4, new Color(0, 0, 0, 190)), false);
        }
        RenderUtil.drawRoundedRect2Alpha(posX - 1.0f, posY, width, height - 15, 3.4, new Color(20, 20, 20, 70));
        final StringJoiner joiner = new StringJoiner(", ");
        final Duration duration = Duration.between(getLocalTime(Aqua.INSTANCE.lastConnection), getLocalTime(System.currentTimeMillis()));
        long seconds = duration.getSeconds();
        long nano = 0L;
        if (seconds <= 0L) {
            nano = duration.getNano();
        }
        if (nano <= 0L) {
            long minutes = seconds / 60L;
            final long hours = minutes / 60L;
            if (hours > 0L) {
                joiner.add(hours + "h");
            }
            while (minutes > 60L) {
                minutes -= 60L;
            }
            if (minutes > 0L) {
                joiner.add(minutes + "min");
            }
            while (seconds > 60L) {
                seconds -= 60L;
            }
            if (seconds > 0L) {
                joiner.add(seconds + "s");
            }
        }
        else if (nano > 1000000L) {
            joiner.add(nano / 1000000L + "ms");
        }
        else {
            joiner.add(nano + "ns");
        }
        Aqua.INSTANCE.tenacityNormal.drawString("Session Info", posX + 2.0f, posY + 1.0f, -1);
        RenderUtil.drawRoundedRect2Alpha(posX + 1.0f, posY + 14.0f, width - 5, 1.0, 1.0, new Color(20, 20, 20, 10));
        Aqua.INSTANCE.tenacityNormal.drawString("Kills : " + this.kills, posX + 2.0f, posY + 15.0f, -1);
        Aqua.INSTANCE.tenacityNormal.drawString("Deaths : " + this.deaths, (float)Aqua.INSTANCE.tenacityNormal.getStringWidth("Kills : " + this.kills + 26), posY + 15.0f, -1);
        Aqua.INSTANCE.tenacityNormal.drawString("Playtime : " + joiner, posX + 2.0f, posY + 30.0f, -1);
    }
    
    public void drawStrings() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final StringJoiner joiner = new StringJoiner(", ");
        final Duration duration = Duration.between(getLocalTime(Aqua.INSTANCE.lastConnection), getLocalTime(System.currentTimeMillis()));
        long seconds = duration.getSeconds();
        long nano = 0L;
        if (seconds <= 0L) {
            nano = duration.getNano();
        }
        if (nano <= 0L) {
            long minutes = seconds / 60L;
            final long hours = minutes / 60L;
            if (hours > 0L) {
                joiner.add(hours + "h");
            }
            while (minutes > 60L) {
                minutes -= 60L;
            }
            if (minutes > 0L) {
                joiner.add(minutes + "min");
            }
            while (seconds > 60L) {
                seconds -= 60L;
            }
            if (seconds > 0L) {
                joiner.add(seconds + "s");
            }
        }
        else if (nano > 1000000L) {
            joiner.add(nano / 1000000L + "ms");
        }
        else {
            joiner.add(nano + "ns");
        }
        Aqua.INSTANCE.comfortaa3.drawString("Session Info", posX + 28.0f, posY + 2.0f, Aqua.setmgr.getSetting("HUDColor").getColor());
        Aqua.INSTANCE.comfortaa4.drawString("Kills : " + this.kills, posX + 1.0f, posY + 15.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("Deaths : " + this.deaths, posX + 1.0f, posY + 25.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("Playtime : " + joiner, posX + 1.0f, posY + 35.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("KD : " + this.kills / ((this.deaths == 0) ? 1 : this.deaths), posX + 1.0f, posY + 45.0f, -1);
        Arraylist.drawGlowArray(() -> Aqua.INSTANCE.comfortaa3.drawString("Session Info", posX + 27.0f, posY + 2.0f, Aqua.setmgr.getSetting("HUDColor").getColor()), false);
    }
    
    public void drawRectShaders() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 120;
        final int height = 57;
        final int cornerRadios = 4;
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadios, new Color(0, 0, 0, 80)), false);
        }
        if (Aqua.moduleManager.getModuleByName("Arraylist").isToggled() && Aqua.setmgr.getSetting("SessionInfoMode").getCurrentMode().equalsIgnoreCase("Glow")) {
            Arraylist.drawGlowArray(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadios, Aqua.setmgr.getSetting("HUDColor").getColor()), false);
        }
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && Aqua.setmgr.getSetting("SessionInfoMode").getCurrentMode().equalsIgnoreCase("Shadow") && Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadios, Color.black.getRGB()), false);
        }
    }
    
    public void drawRects() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 120;
        final int height = 57;
        final int cornerRadios = 4;
        RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadios, new Color(0, 0, 0, 60));
    }
    
    public void drawStringsModern() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? 4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final StringJoiner joiner = new StringJoiner(", ");
        final Duration duration = Duration.between(getLocalTime(Aqua.INSTANCE.lastConnection), getLocalTime(System.currentTimeMillis()));
        long seconds = duration.getSeconds();
        long nano = 0L;
        if (seconds <= 0L) {
            nano = duration.getNano();
        }
        if (nano <= 0L) {
            long minutes = seconds / 60L;
            final long hours = minutes / 60L;
            if (hours > 0L) {
                joiner.add(hours + "h");
            }
            while (minutes > 60L) {
                minutes -= 60L;
            }
            if (minutes > 0L) {
                joiner.add(minutes + "min");
            }
            while (seconds > 60L) {
                seconds -= 60L;
            }
            if (seconds > 0L) {
                joiner.add(seconds + "s");
            }
        }
        else if (nano > 1000000L) {
            joiner.add(nano / 1000000L + "ms");
        }
        else {
            joiner.add(nano + "ns");
        }
        Aqua.INSTANCE.comfortaa3.drawString("Session Info", posX + 2.0f, posY + 2.0f, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0).getRGB());
        Aqua.INSTANCE.comfortaa4.drawString("Kills : " + this.kills, posX + 2.0f, posY + 15.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("Deaths : " + this.deaths, (float)(Aqua.INSTANCE.comfortaa3.getStringWidth("Kills : " + this.kills) + 5), posY + 15.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("Playtime : " + joiner, posX + 2.0f, posY + 25.0f, -1);
        Aqua.INSTANCE.comfortaa4.drawString("KD : " + this.kills / ((this.deaths == 0) ? 1 : this.deaths), posX + 2.0f, posY + 35.0f, -1);
        Arraylist.drawGlowArray(() -> Aqua.INSTANCE.comfortaa3.drawString("Session Info", posX + 2.0f, posY + 2.0f, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0).getRGB()), false);
    }
    
    public void drawRectShadersModern() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? -4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 120;
        final int height = 47;
        final int cornerRadios = 4;
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadios, new Color(0, 0, 0, 80)), false);
        }
        if (Aqua.moduleManager.getModuleByName("Arraylist").isToggled() && Aqua.setmgr.getSetting("SessionInfoMode").getCurrentMode().equalsIgnoreCase("Glow")) {
            Arraylist.drawGlowArray(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadios, Aqua.setmgr.getSetting("HUDColor").getColor()), false);
        }
        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && Aqua.setmgr.getSetting("SessionInfoMode").getCurrentMode().equalsIgnoreCase("Shadow") && Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadios, Color.black.getRGB()), false);
        }
    }
    
    public void drawRectsModern() {
        final float posX = Aqua.setmgr.getSetting("SessionInfoConnectedPosX").isState() ? -4.0f : ((float)Aqua.setmgr.getSetting("SessionInfoPosX").getCurrentNumber());
        final float posY = (float)Aqua.setmgr.getSetting("SessionInfoPosY").getCurrentNumber();
        final int width = 120;
        final int height = 47;
        final int cornerRadios = 4;
        RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadios, new Color(0, 0, 0, 90));
        RenderUtil.drawRoundedRect2Alpha(posX, posY, 6.0, height, 0.0, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0));
    }
}
