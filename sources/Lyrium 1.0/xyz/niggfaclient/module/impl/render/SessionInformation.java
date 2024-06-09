// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.multiplayer.GuiConnecting;
import java.util.function.Predicate;
import java.util.Arrays;
import net.minecraft.network.play.server.S02PacketChat;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.utils.render.ColorUtil;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.events.impl.TickEvent;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.ShaderEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.draggable.Dragging;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "SessionInformation", description = "SessionInformation", cat = Category.RENDER)
public class SessionInformation extends Module
{
    private final Property<Boolean> font;
    private long currentTime;
    private int playersKilled;
    public int gamesWon;
    private final Dragging dragging;
    @EventLink
    private final Listener<ShaderEvent> shaderEventListener;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<TickEvent> tickEventListener;
    
    public SessionInformation() {
        this.font = new Property<Boolean>("Font", true);
        this.dragging = Client.getInstance().createDraggable(this, "sessioninformation", 40.0f, 100.0f);
        float width;
        float height;
        this.shaderEventListener = (e -> {
            if (Blur.sessionStats.getValue() && !this.mc.gameSettings.showDebugInfo) {
                width = 145.0f;
                height = 85.0f;
                this.dragging.setHeight(height);
                this.dragging.setWidth(width);
                RenderUtils.drawRoundedRect(this.dragging.getX(), this.dragging.getY() + 2.0f, width, height - 1.0f, 14.0, Color.BLACK.getRGB());
            }
            return;
        });
        long time;
        float width2;
        float height2;
        NetworkPlayerInfo you;
        String currentPing;
        long hours;
        long minutes;
        long seconds;
        String playtime;
        float i;
        int color;
        this.render2DEventListener = (e -> {
            if (!this.mc.gameSettings.showDebugInfo) {
                time = System.currentTimeMillis() - this.currentTime;
                width2 = 145.0f;
                height2 = 85.0f;
                this.dragging.setHeight(height2);
                this.dragging.setWidth(width2);
                you = this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID());
                currentPing = ((you == null) ? "0" : String.valueOf(you.getResponseTime())) + " ms";
                hours = time / 3600000L % 24L;
                minutes = time / 60000L % 60L;
                seconds = time / 1000L % 60L;
                playtime = hours + "h " + minutes + "m " + seconds + "s";
                RenderUtils.drawRoundedRect(this.dragging.getX(), this.dragging.getY() + 2.0f, width2, height2 - 1.0f, 14.0, ColorUtil.applyOpacity(Color.BLACK, 0.2f).getRGB());
                for (i = this.dragging.getX() + 5.0f; i < this.dragging.getX() - 5.0f + width2; ++i) {
                    color = ColorUtil.getHUDColor((int)(25.0f - i * ((HUD.colorMode.getValue() == HUD.ColorMode.Dynamic) ? -1 : 10)));
                    Gui.drawRect(i, this.dragging.getY() + 20.0f, i + 1.0f, this.dragging.getY() + 19.0f, color);
                }
                if (this.font.getValue()) {
                    Fonts.sf23.drawStringWithShadow("Session Information", this.dragging.getX() + 22.0f, this.dragging.getY() + 6.0f, -1);
                    Fonts.sf20.drawStringWithShadow("Playtime: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 26.0f, -1);
                    Fonts.sf20.drawStringWithShadow(playtime, this.dragging.getX() - Fonts.sf20.getStringWidth(playtime) + 139.0f, this.dragging.getY() + 26.0f, -1);
                    Fonts.sf20.drawStringWithShadow("Players Killed: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 38.0f, -1);
                    Fonts.sf20.drawStringWithShadow(String.valueOf(this.playersKilled), this.dragging.getX() - Fonts.sf20.getStringWidth(String.valueOf(this.playersKilled)) + 139.0f, this.dragging.getY() + 38.0f, -1);
                    Fonts.sf20.drawStringWithShadow("Games Won: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 50.0f, -1);
                    Fonts.sf20.drawStringWithShadow(String.valueOf(this.gamesWon), this.dragging.getX() - Fonts.sf20.getStringWidth(String.valueOf(this.gamesWon)) + 139.0f, this.dragging.getY() + 50.0f, -1);
                    Fonts.sf20.drawStringWithShadow("User: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 62.0f, -1);
                    Fonts.sf20.drawStringWithShadow(String.valueOf(this.mc.thePlayer.getName()), this.dragging.getX() - Fonts.sf20.getStringWidth(String.valueOf(this.mc.thePlayer.getName())) + 139.0f, this.dragging.getY() + 62.0f, -1);
                    Fonts.sf20.drawStringWithShadow("Ping: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 74.0f, -1);
                    Fonts.sf20.drawStringWithShadow(currentPing, this.dragging.getX() - Fonts.sf20.getStringWidth(currentPing) + 139.0f, this.dragging.getY() + 74.0f, -1);
                }
                else {
                    this.mc.fontRendererObj.drawStringWithShadow("Session Information", this.dragging.getX() + 22.0f, this.dragging.getY() + 6.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow("Playtime: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 26.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow(playtime, this.dragging.getX() - this.mc.fontRendererObj.getStringWidth(playtime) + 139.0f, this.dragging.getY() + 26.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow("Players Killed: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 38.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.playersKilled), this.dragging.getX() - this.mc.fontRendererObj.getStringWidth(String.valueOf(this.playersKilled)) + 139.0f, this.dragging.getY() + 38.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow("Games Won: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 50.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.gamesWon), this.dragging.getX() - this.mc.fontRendererObj.getStringWidth(String.valueOf(this.gamesWon)) + 139.0f, this.dragging.getY() + 50.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow("User: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 62.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.mc.thePlayer.getName()), this.dragging.getX() - this.mc.fontRendererObj.getStringWidth(String.valueOf(this.mc.thePlayer.getName())) + 139.0f, this.dragging.getY() + 62.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow("Ping: ", this.dragging.getX() + 5.0f, this.dragging.getY() + 74.0f, -1);
                    this.mc.fontRendererObj.drawStringWithShadow(currentPing, this.dragging.getX() - this.mc.fontRendererObj.getStringWidth(currentPing) + 139.0f, this.dragging.getY() + 74.0f, -1);
                }
            }
            return;
        });
        S02PacketChat s02;
        String text;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE) {
                try {
                    if (e.getPacket() instanceof S02PacketChat) {
                        s02 = (S02PacketChat)e.getPacket();
                        text = s02.getChatComponent().getUnformattedText();
                        if (!text.contains(":")) {
                            if (Arrays.stream(new String[] { "by *", "para *", "fue destrozado a manos de *" }).anyMatch(text.replace(this.mc.thePlayer.getName(), "*")::contains)) {
                                ++this.playersKilled;
                            }
                        }
                        if (text.contains("You won!")) {
                            ++this.gamesWon;
                        }
                    }
                }
                catch (Exception ex) {}
            }
            return;
        });
        this.tickEventListener = (e -> {
            if (this.mc.getCurrentServerData() == null || this.mc.currentScreen instanceof GuiConnecting) {
                this.currentTime = System.currentTimeMillis();
                this.gamesWon = 0;
                this.playersKilled = 0;
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.currentTime = System.currentTimeMillis();
    }
}
