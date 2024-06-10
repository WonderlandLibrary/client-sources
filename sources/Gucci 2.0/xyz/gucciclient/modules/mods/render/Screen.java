package xyz.gucciclient.modules.mods.render;

import xyz.gucciclient.values.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import xyz.gucciclient.utils.*;
import org.lwjgl.opengl.*;
import xyz.gucciclient.modules.*;
import java.awt.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Screen extends Module
{
    private BooleanValue togglesneak;
    
    public Screen() {
        super(Modules.Hud.name(), 0, Category.RENDER);
        this.addBoolean(this.togglesneak = new BooleanValue("ToggleSneak", false));
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post e) {
        if (Wrapper.getMinecraft().currentScreen instanceof GuiMainMenu) {
            return;
        }
        if (e.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        GL11.glPushMatrix();
        int yCount = 2;
        if (this.togglesneak.getState()) {
            yCount += 12;
        }
        for (final Module mod : ModuleManager.getModules()) {
            if (mod != null && mod != this && mod.getState()) {
                Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), 2.0f, (float)yCount, new Color(0, 220, 0).getRGB());
                yCount += 8;
            }
        }
        GL11.glPopMatrix();
    }
}
