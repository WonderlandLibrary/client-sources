package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderZombie extends RenderBiped<EntityZombie>
{
    private static final ResourceLocation zombieVillagerTextures;
    private final List<LayerRenderer<EntityZombie>> field_177121_n;
    private final ModelZombieVillager zombieVillagerModel;
    private final List<LayerRenderer<EntityZombie>> field_177122_o;
    private static final ResourceLocation zombieTextures;
    private static final String[] I;
    private final ModelBiped field_82434_o;
    
    @Override
    protected void rotateCorpse(final EntityZombie entityZombie, final float n, float n2, final float n3) {
        if (entityZombie.isConverting()) {
            n2 += (float)(Math.cos(entityZombie.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(entityZombie, n, n2, n3);
    }
    
    public RenderZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        final LayerRenderer<T> layerRenderer = this.layerRenderers.get("".length());
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        final LayerBipedArmor layerBipedArmor = new LayerBipedArmor(this, this) {
            final RenderZombie this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = (T)new ModelZombie(0.5f, " ".length() != 0);
                this.field_177186_d = (T)new ModelZombie(1.0f, " ".length() != 0);
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
        };
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(layerBipedArmor);
        this.field_177122_o = (List<LayerRenderer<EntityZombie>>)Lists.newArrayList((Iterable)this.layerRenderers);
        if (layerRenderer instanceof LayerCustomHead) {
            ((RendererLivingEntity<EntityLivingBase>)this).removeLayer((LayerRenderer<EntityLivingBase>)layerRenderer);
            ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }
        ((RendererLivingEntity<EntityLivingBase>)this).removeLayer(layerBipedArmor);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerVillagerArmor(this));
        this.field_177121_n = (List<LayerRenderer<EntityZombie>>)Lists.newArrayList((Iterable)this.layerRenderers);
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_82427_a(final EntityZombie entityZombie) {
        if (entityZombie.isVillager()) {
            this.mainModel = this.zombieVillagerModel;
            this.layerRenderers = (List<LayerRenderer<T>>)this.field_177121_n;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.mainModel = this.field_82434_o;
            this.layerRenderers = (List<LayerRenderer<T>>)this.field_177122_o;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    @Override
    public void doRender(final EntityZombie entityZombie, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_82427_a(entityZombie);
        super.doRender(entityZombie, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityZombie)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntityZombie)entityLiving);
    }
    
    static {
        I();
        zombieTextures = new ResourceLocation(RenderZombie.I["".length()]);
        zombieVillagerTextures = new ResourceLocation(RenderZombie.I[" ".length()]);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityZombie)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityZombie entityZombie) {
        ResourceLocation resourceLocation;
        if (entityZombie.isVillager()) {
            resourceLocation = RenderZombie.zombieVillagerTextures;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderZombie.zombieTextures;
        }
        return resourceLocation;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(",#\n\u0001\u0014*#\u0001Z\u000462\u001b\u0001\u0018w<\u001d\u0018\u00031#]\u000f\u000e5$\u001b\u0010O((\u0015", "XFrua");
        RenderZombie.I[" ".length()] = I("7\u001c\u0016\u001a\u00021\u001c\u001dA\u0012-\r\u0007\u001a\u000el\u0003\u0001\u0003\u0015*\u001cA\u0014\u0018.\u001b\u0007\u000b(5\u0010\u0002\u0002\u0016$\u001c\u001c@\u0007-\u001e", "Cynnw");
    }
}
