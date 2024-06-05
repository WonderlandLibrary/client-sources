// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.notification;

import net.minecraft.client.gui.ScaledResolution;
import gg.childtrafficking.smokex.utils.render.LockedResolution;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.system.TimerUtil;

public final class Notification
{
    private final NotificationType type;
    private final String header;
    private final String content;
    private final long created;
    private final int color;
    private final long time;
    private final float width;
    private final float height;
    private boolean finished;
    private final TimerUtil timer;
    
    public Notification(final NotificationType type, final String header, final String content, final long time) {
        this.type = type;
        this.header = header;
        this.content = content;
        this.created = System.currentTimeMillis();
        this.time = time;
        this.color = type.getColor();
        if (content != null) {
            this.width = Math.max(SmokeXClient.getInstance().fontRenderer.getWidth(header), SmokeXClient.getInstance().fontRenderer.getWidth(content)) + 4.0f;
        }
        else {
            this.width = SmokeXClient.getInstance().fontRenderer.getWidth(header) + 4.0f;
        }
        this.height = 27.0f;
        if (Minecraft.getMinecraft().currentScreen == null) {
            RenderingUtils.getLockedResolution();
        }
        else {
            RenderingUtils.getScaledResolution();
        }
        this.timer = new TimerUtil();
    }
    
    public Notification(final NotificationType type, final String header, final String content) {
        this(type, header, content, 2500L);
    }
    
    public NotificationType getType() {
        return this.type;
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public long getCreated() {
        return this.created;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void render(final LockedResolution lockedResolution, final ScaledResolution scaledResolution, final int index, final int yOffset) {
    }
}
