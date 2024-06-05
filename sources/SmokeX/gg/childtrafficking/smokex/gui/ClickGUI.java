// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.event.events.system.EventKeyPress;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.util.ResourceLocation;

public final class ClickGUI extends BetterGuiScreen
{
    private static volatile ClickGUI INSTANCE;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 370;
    private static final ResourceLocation LEFT_BACKGROUND;
    private boolean dragging;
    private float prevX;
    private float prevY;
    private final EventListener<EventKeyPress> keyPressEventListener;
    
    private ClickGUI() {
        this.keyPressEventListener = (event -> {
            if (event.getKey() == 54) {
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            return;
        });
        if (ClickGUI.INSTANCE != null) {
            throw new RuntimeException("Another instance of this class currently exists!");
        }
    }
    
    public static ClickGUI getInstance() {
        if (ClickGUI.INSTANCE == null) {
            synchronized (ClickGUI.class) {
                if (ClickGUI.INSTANCE == null) {
                    ClickGUI.INSTANCE = new ClickGUI();
                }
            }
        }
        return ClickGUI.INSTANCE;
    }
    
    static {
        LEFT_BACKGROUND = new ResourceLocation("smokex/guiLeftBackground.png");
    }
}
