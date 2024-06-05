// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.gui.element.Element;

public final class NotificationElement extends Element
{
    private String title;
    private String message;
    private final long duration;
    private final TimerUtil timerUtil;
    
    public NotificationElement(final String identifier, final float x, final float y, final String title, final String message, final long duration) {
        this(identifier, x, y, 200.0f, 50.0f, title, message, duration);
    }
    
    public NotificationElement(final String identifier, final float x, final float y, final float width, final float height, final String title, final String message, final long duration) {
        super(identifier, x, y, width, height);
        this.timerUtil = new TimerUtil();
        this.title = title;
        this.message = message;
        this.duration = duration;
    }
    
    public Element setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public Element setMessage(final String message) {
        this.message = message;
        return this;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void render(final double partialTicks) {
        RenderingUtils.drawRect(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight(), 1184274);
        RenderingUtils.drawRect(this.getRenderX(), this.getRenderY() + this.getHeight(), this.getWidth() - this.getWidth() * (this.timerUtil.elapsed() / this.duration), 5.0f, 16777215);
        SmokeXClient.getInstance().fontRenderer.drawStringWithShadow(this.title, this.getRenderX(), this.getRenderY(), 16777215);
        SmokeXClient.getInstance().fontRenderer.drawStringWithShadow(this.message, this.getRenderX(), this.getRenderY() + 10.0f, 16777215);
        super.render(partialTicks);
    }
}
