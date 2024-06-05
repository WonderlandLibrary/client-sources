package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.*;
import java.util.*;
import me.darkmagician6.morbid.util.*;

public final class ChestESP extends ModBase
{
    public ChestESP() {
        super("ChestESP", "U", false, ".t chest");
        this.setDescription("Renders a box arround chests to make them more visable.");
    }
    
    @Override
    public void onRenderHand() {
        GL11.glLineWidth(1.0f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        this.getWrapper();
        for (final Object o : MorbidWrapper.getWorld().g) {
            if (!(o instanceof apy)) {
                continue;
            }
            final apy chest = (apy)o;
            if (chest.l == 0 || chest.m == 0 || chest.n == 0) {
                continue;
            }
            final double n = chest.l;
            this.getWrapper();
            MorbidWrapper.getRenderManager();
            final double x = n - bgy.b;
            final double n2 = chest.m;
            this.getWrapper();
            MorbidWrapper.getRenderManager();
            final double y = n2 - bgy.c;
            final double n3 = chest.n;
            this.getWrapper();
            MorbidWrapper.getRenderManager();
            this.drawChestESP(x, y, n3 - bgy.d);
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
    }
    
    private void drawChestESP(final double x, final double y, final double z) {
        GLHelper.drawOutlinedBoundingBox(new aqx(x + 1.0, y + 1.0, z + 1.0, x, y, z));
        GLHelper.drawLines(new aqx(x + 1.0, y + 1.0, z + 1.0, x, y, z));
    }
}
