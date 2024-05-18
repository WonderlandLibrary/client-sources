package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderEntity extends Render<Entity>
{
    public RenderEntity(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        Render.renderOffsetAABB(entity.getEntityBoundingBox(), n - entity.lastTickPosX, n2 - entity.lastTickPosY, n3 - entity.lastTickPosZ);
        GlStateManager.popMatrix();
        super.doRender(entity, n, n2, n3, n4, n5);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
}
