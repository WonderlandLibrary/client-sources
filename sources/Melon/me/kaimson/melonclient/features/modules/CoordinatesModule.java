package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.utils.*;

public class CoordinatesModule extends DefaultModuleRenderer
{
    private int width;
    private int height;
    
    public CoordinatesModule() {
        super("Coordinates");
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void render(final float x, final float y) {
        int i = 0;
        this.width = 0;
        for (final String s : (String[])this.getValue()) {
            this.width = Math.max(this.width, this.mc.k.a(s));
            float coordX = x;
            if (ModuleConfig.INSTANCE.getPosition(this).getAnchorPoint().isRightSide()) {
                coordX += this.width - this.mc.k.a(s);
            }
            FontUtils.drawString(s, coordX, y + 1.0f + i * (this.mc.k.a + 1));
            ++i;
        }
        this.height = (this.mc.k.a + 1) * 3;
    }
    
    @Override
    public Object getValue() {
        return new String[] { "X: " + this.mc.h.c().n(), "Y: " + this.mc.h.c().o(), "Z: " + this.mc.h.c().p() };
    }
}
