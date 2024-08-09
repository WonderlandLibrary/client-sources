package wtf.shiyeno.util;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;

public interface IMinecraft {

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    Minecraft mc = Minecraft.getInstance();
    MainWindow sr = mc.getMainWindow();
    FontRenderer fr = mc.fontRenderer;

    List<Runnable> glow = new ArrayList<>();

    static void update() {
        glow.clear();
    }

    default void setGlow(Runnable runnable) {
        glow.add(runnable);
    }


}
