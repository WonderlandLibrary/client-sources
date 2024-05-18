package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderDragon extends RenderLiving<EntityDragon>
{
    private static final ResourceLocation enderDragonCrystalBeamTextures;
    protected ModelDragon modelDragon;
    private static final ResourceLocation enderDragonExplodingTextures;
    private static final String[] I;
    private static final ResourceLocation enderDragonTextures;
    
    public RenderDragon(final RenderManager renderManager) {
        super(renderManager, new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerEnderDragonEyes(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerEnderDragonDeath());
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityDragon)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void renderModel(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.renderModel((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected void rotateCorpse(final EntityDragon entityDragon, final float n, final float n2, final float n3) {
        final float n4 = (float)entityDragon.getMovementOffsets(0x67 ^ 0x60, n3)["".length()];
        final float n5 = (float)(entityDragon.getMovementOffsets(0x14 ^ 0x11, n3)[" ".length()] - entityDragon.getMovementOffsets(0x61 ^ 0x6B, n3)[" ".length()]);
        GlStateManager.rotate(-n4, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n5 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 1.0f);
        if (entityDragon.deathTime > 0) {
            float sqrt_float = MathHelper.sqrt_float((entityDragon.deathTime + n3 - 1.0f) / 20.0f * 1.6f);
            if (sqrt_float > 1.0f) {
                sqrt_float = 1.0f;
            }
            GlStateManager.rotate(sqrt_float * this.getDeathMaxRotation(entityDragon), 0.0f, 0.0f, 1.0f);
        }
    }
    
    @Override
    public void doRender(final EntityDragon entityDragon, final double n, final double n2, final double n3, final float n4, final float n5) {
        BossStatus.setBossStatus(entityDragon, "".length() != 0);
        super.doRender(entityDragon, n, n2, n3, n4, n5);
        if (entityDragon.healingEnderCrystal != null) {
            this.drawRechargeRay(entityDragon, n, n2, n3, n5);
        }
    }
    
    protected void drawRechargeRay(final EntityDragon entityDragon, final double n, final double n2, final double n3, final float n4) {
        final float n5 = MathHelper.sin((entityDragon.healingEnderCrystal.innerRotation + n4) * 0.2f) / 2.0f + 0.5f;
        final float n6 = (n5 * n5 + n5) * 0.2f;
        final float n7 = (float)(entityDragon.healingEnderCrystal.posX - entityDragon.posX - (entityDragon.prevPosX - entityDragon.posX) * (1.0f - n4));
        final float n8 = (float)(n6 + entityDragon.healingEnderCrystal.posY - 1.0 - entityDragon.posY - (entityDragon.prevPosY - entityDragon.posY) * (1.0f - n4));
        final float n9 = (float)(entityDragon.healingEnderCrystal.posZ - entityDragon.posZ - (entityDragon.prevPosZ - entityDragon.posZ) * (1.0f - n4));
        final float sqrt_float = MathHelper.sqrt_float(n7 * n7 + n9 * n9);
        final float sqrt_float2 = MathHelper.sqrt_float(n7 * n7 + n8 * n8 + n9 * n9);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2 + 2.0f, (float)n3);
        GlStateManager.rotate((float)(-Math.atan2(n9, n7)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((float)(-Math.atan2(sqrt_float, n8)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        this.bindTexture(RenderDragon.enderDragonCrystalBeamTextures);
        GlStateManager.shadeModel(1679 + 395 + 3174 + 2177);
        final float n10 = 0.0f - (entityDragon.ticksExisted + n4) * 0.01f;
        final float n11 = MathHelper.sqrt_float(n7 * n7 + n8 * n8 + n9 * n9) / 32.0f - (entityDragon.ticksExisted + n4) * 0.01f;
        worldRenderer.begin(0x3B ^ 0x3E, DefaultVertexFormats.POSITION_TEX_COLOR);
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i <= (0xA9 ^ 0xA1)) {
            final float n12 = MathHelper.sin(i % (0x6A ^ 0x62) * 3.1415927f * 2.0f / 8.0f) * 0.75f;
            final float n13 = MathHelper.cos(i % (0x98 ^ 0x90) * 3.1415927f * 2.0f / 8.0f) * 0.75f;
            final float n14 = i % (0x55 ^ 0x5D) * 1.0f / 8.0f;
            worldRenderer.pos(n12 * 0.2f, n13 * 0.2f, 0.0).tex(n14, n11).color("".length(), "".length(), "".length(), 132 + 170 - 188 + 141).endVertex();
            worldRenderer.pos(n12, n13, sqrt_float2).tex(n14, n10).color(254 + 248 - 458 + 211, 217 + 115 - 306 + 229, 250 + 102 - 165 + 68, 157 + 253 - 266 + 111).endVertex();
            ++i;
        }
        instance.draw();
        GlStateManager.enableCull();
        GlStateManager.shadeModel(6493 + 4178 - 8554 + 5307);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    @Override
    protected void renderModel(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (entityDragon.deathTicks > 0) {
            final float n7 = entityDragon.deathTicks / 200.0f;
            GlStateManager.depthFunc(225 + 403 - 506 + 393);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(342 + 221 - 105 + 58, n7);
            this.bindTexture(RenderDragon.enderDragonExplodingTextures);
            this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
            GlStateManager.alphaFunc(467 + 255 - 368 + 162, 0.1f);
            GlStateManager.depthFunc(37 + 56 - 5 + 426);
        }
        this.bindEntityTexture(entityDragon);
        this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
        if (entityDragon.hurtTime > 0) {
            GlStateManager.depthFunc(317 + 70 - 39 + 166);
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(595 + 338 - 727 + 564, 246 + 528 - 500 + 497);
            GlStateManager.color(1.0f, 0.0f, 0.0f, 0.5f);
            this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.depthFunc(452 + 223 - 265 + 105);
        }
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(">\u00106'$8\u0010=|4$\u0001''(e\u0010 748\u0016<*\">\u0014\"|4$\u0011+!28\f='0&*,60'[>=6", "JuNSQ");
        RenderDragon.I[" ".length()] = I(":0?9;<04b+ !.97a0))+<15,)!;h)</2(#\u0011+-7!!*<)*`>; ", "NUGMN");
        RenderDragon.I["  ".length()] = I("'\u0002\n\u001e\u0007!\u0002\u0001E\u0017=\u0013\u001b\u001e\u000b|\u0002\u001c\u000e\u0017!\u0003\u0000\u000b\u0015<\t]\u000e\u00002\u0000\u001d\u0004\\#\t\u0015", "Sgrjr");
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityDragon)entity);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityDragon)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityDragon entityDragon) {
        return RenderDragon.enderDragonTextures;
    }
    
    static {
        I();
        enderDragonCrystalBeamTextures = new ResourceLocation(RenderDragon.I["".length()]);
        enderDragonExplodingTextures = new ResourceLocation(RenderDragon.I[" ".length()]);
        enderDragonTextures = new ResourceLocation(RenderDragon.I["  ".length()]);
    }
}
