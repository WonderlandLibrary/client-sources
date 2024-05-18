package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSquid extends RenderLiving<EntitySquid>
{
    private static final ResourceLocation squidTextures;
    private static final String[] I;
    
    public RenderSquid(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySquid entitySquid) {
        return RenderSquid.squidTextures;
    }
    
    @Override
    protected float handleRotationFloat(final EntitySquid entitySquid, final float n) {
        return entitySquid.lastTentacleAngle + (entitySquid.tentacleAngle - entitySquid.lastTentacleAngle) * n;
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntitySquid)entityLivingBase, n);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00003\u0017\u0007\u0000\u00063\u001c\\\u0010\u001a\"\u0006\u0007\f[%\u001e\u0006\u001c\u0010x\u001f\u001d\u0012", "tVosu");
    }
    
    @Override
    protected void rotateCorpse(final EntitySquid entitySquid, final float n, final float n2, final float n3) {
        final float n4 = entitySquid.prevSquidPitch + (entitySquid.squidPitch - entitySquid.prevSquidPitch) * n3;
        final float n5 = entitySquid.prevSquidYaw + (entitySquid.squidYaw - entitySquid.prevSquidYaw) * n3;
        GlStateManager.translate(0.0f, 0.5f, 0.0f);
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, -1.2f, 0.0f);
    }
    
    static {
        I();
        squidTextures = new ResourceLocation(RenderSquid.I["".length()]);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntitySquid)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySquid)entity);
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
