package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class RenderVillager extends RenderLiving<EntityVillager>
{
    private static final ResourceLocation librarianVillagerTextures;
    private static final String[] I;
    private static final ResourceLocation smithVillagerTextures;
    private static final ResourceLocation butcherVillagerTextures;
    private static final ResourceLocation villagerTextures;
    private static final ResourceLocation farmerVillagerTextures;
    private static final ResourceLocation priestVillagerTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityVillager entityVillager) {
        switch (entityVillager.getProfession()) {
            case 0: {
                return RenderVillager.farmerVillagerTextures;
            }
            case 1: {
                return RenderVillager.librarianVillagerTextures;
            }
            case 2: {
                return RenderVillager.priestVillagerTextures;
            }
            case 3: {
                return RenderVillager.smithVillagerTextures;
            }
            case 4: {
                return RenderVillager.butcherVillagerTextures;
            }
            default: {
                return RenderVillager.villagerTextures;
            }
        }
    }
    
    static {
        I();
        villagerTextures = new ResourceLocation(RenderVillager.I["".length()]);
        farmerVillagerTextures = new ResourceLocation(RenderVillager.I[" ".length()]);
        librarianVillagerTextures = new ResourceLocation(RenderVillager.I["  ".length()]);
        priestVillagerTextures = new ResourceLocation(RenderVillager.I["   ".length()]);
        smithVillagerTextures = new ResourceLocation(RenderVillager.I[0x9C ^ 0x98]);
        butcherVillagerTextures = new ResourceLocation(RenderVillager.I[0x93 ^ 0x96]);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityVillager)entityLivingBase, n);
    }
    
    public RenderVillager(final RenderManager renderManager) {
        super(renderManager, new ModelVillager(0.0f), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityVillager)entity);
    }
    
    @Override
    protected void preRenderCallback(final EntityVillager entityVillager, final float n) {
        float n2 = 0.9375f;
        if (entityVillager.getGrowingAge() < 0) {
            n2 *= 0.5;
            this.shadowSize = 0.25f;
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            this.shadowSize = 0.5f;
        }
        GlStateManager.scale(n2, n2, n2);
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ModelVillager getMainModel() {
        return (ModelVillager)super.getMainModel();
    }
    
    private static void I() {
        (I = new String[0x43 ^ 0x45])["".length()] = I("!\u000233\r'\u00028h\u001d;\u0013\"3\u0001z\u0011\"+\u00144\u0000.5W#\u000e'+\u00192\u00029i\b;\u0000", "UgKGx");
        RenderVillager.I[" ".length()] = I("=\u000f+7\u0019;\u000f l\t'\u001e:7\u0015f\u001c:/\u0000(\r61C/\u000b!.\t;D#-\u000b", "IjSCl");
        RenderVillager.I["  ".length()] = I("\u0016\b08\u0000\u0010\b;c\u0010\f\u0019!8\fM\u001b! \u0019\u0003\n->Z\u000e\u0004*>\u0014\u0010\u0004)\"[\u0012\u0003/", "bmHLu");
        RenderVillager.I["   ".length()] = I("3\u0012\"\u0012;5\u0012)I+)\u00033\u00127h\u00013\n\"&\u0010?\u0014a7\u00053\u0003=3Y*\b)", "GwZfN");
        RenderVillager.I[0x6 ^ 0x2] = I("%\u000b\u0011\u0012=#\u000b\u001aI-?\u001a\u0000\u00121~\u0018\u0000\n$0\t\f\u0014g\"\u0003\u0000\u0012 \u007f\u001e\u0007\u0001", "QnifH");
        RenderVillager.I[0x9B ^ 0x9E] = I("#\r06\u0013%\r;m\u00039\u001c!6\u001fx\u001e!.\n6\u000f-0I5\u001d<!\u000e2\u001af2\b0", "WhHBf");
    }
}
