package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;

public class RenderEnderman extends RenderLiving<EntityEnderman>
{
    private static final ResourceLocation endermanTextures;
    private static final String[] I;
    private Random rnd;
    private ModelEnderman endermanModel;
    
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderEnderman(final RenderManager renderManager) {
        super(renderManager, new ModelEnderman(0.0f), 0.5f);
        this.rnd = new Random();
        this.endermanModel = (ModelEnderman)super.mainModel;
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerEndermanEyes(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldBlock(this));
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0017\t\u0015\u001a\u001a\u0011\t\u001eA\n\r\u0018\u0004\u001a\u0016L\t\u0003\n\n\u0011\u0001\f\u0000@\u0006\u0002\t\u000b\u001d\u000e\r\u0003@\u001f\r\u000b", "clmno");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityEnderman)entity);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderman)entityLiving, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        endermanTextures = new ResourceLocation(RenderEnderman.I["".length()]);
    }
    
    @Override
    public void doRender(final EntityEnderman entityEnderman, double n, final double n2, double n3, final float n4, final float n5) {
        final ModelEnderman endermanModel = this.endermanModel;
        int isCarrying;
        if (entityEnderman.getHeldBlockState().getBlock().getMaterial() != Material.air) {
            isCarrying = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            isCarrying = "".length();
        }
        endermanModel.isCarrying = (isCarrying != 0);
        this.endermanModel.isAttacking = entityEnderman.isScreaming();
        if (entityEnderman.isScreaming()) {
            final double n6 = 0.02;
            n += this.rnd.nextGaussian() * n6;
            n3 += this.rnd.nextGaussian() * n6;
        }
        super.doRender(entityEnderman, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityEnderman entityEnderman) {
        return RenderEnderman.endermanTextures;
    }
}
