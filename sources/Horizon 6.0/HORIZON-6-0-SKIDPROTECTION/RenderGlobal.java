package HORIZON-6-0-SKIDPROTECTION;

import java.util.EnumSet;
import java.util.concurrent.Callable;
import javax.vecmath.Tuple4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Iterators;
import java.util.Random;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import java.util.LinkedHashSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4f;
import java.util.Map;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class RenderGlobal implements IResourceManagerReloadListener, IWorldAccess
{
    private static final Logger Ø;
    private static final ResourceLocation_1975012498 áŒŠÆ;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    private static final ResourceLocation_1975012498 á;
    private static final ResourceLocation_1975012498 ˆÏ­;
    public final Minecraft HorizonCode_Horizon_È;
    private final TextureManager £á;
    private final RenderManager Å;
    private WorldClient £à;
    private Set µà;
    private List ˆà;
    private ViewFrustum ¥Æ;
    private int Ø­à;
    private int µÕ;
    private int Æ;
    private VertexFormat Šáƒ;
    private VertexBuffer Ï­Ðƒà;
    private VertexBuffer áŒŠà;
    private VertexBuffer ŠÄ;
    private int Ñ¢á;
    public final Map Â;
    private final Map ŒÏ;
    private final TextureAtlasSprite[] Çªà¢;
    private Framebuffer Ê;
    private ShaderGroup ÇŽÉ;
    private double ˆá;
    private double ÇŽÕ;
    private double É;
    private int áƒ;
    private int á€;
    private int Õ;
    private double à¢;
    private double ŠÂµà;
    private double ¥à;
    private double Âµà;
    private double Ç;
    private final ChunkRenderDispatcher È;
    private ChunkRenderContainer áŠ;
    private int ˆáŠ;
    private int áŒŠ;
    private int £ÂµÄ;
    private int Ø­Âµ;
    private int Ä;
    private boolean Ñ¢Â;
    private ClippingHelper Ï­à;
    private final Vector4f[] áˆºáˆºÈ;
    private final Vector3d ÇŽá€;
    private boolean Ï;
    IRenderChunkFactory Ý;
    private double Ô;
    private double ÇªÓ;
    private double áˆºÏ;
    public boolean Ø­áŒŠá;
    private static final String ˆáƒ = "CL_00000954";
    private int Œ;
    private boolean £Ï;
    private int Ø­á;
    private double ˆÉ;
    private double Ï­Ï­Ï;
    private double £Â;
    public Entity Âµá€;
    public Set Ó;
    public Set à;
    
    static {
        Ø = LogManager.getLogger();
        áŒŠÆ = new ResourceLocation_1975012498("textures/environment/moon_phases.png");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/environment/sun.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/environment/clouds.png");
        á = new ResourceLocation_1975012498("textures/environment/end_sky.png");
        ˆÏ­ = new ResourceLocation_1975012498("textures/misc/forcefield.png");
    }
    
    public RenderGlobal(final Minecraft mcIn) {
        this.µà = Sets.newLinkedHashSet();
        this.ˆà = Lists.newArrayListWithCapacity(69696);
        this.Ø­à = -1;
        this.µÕ = -1;
        this.Æ = -1;
        this.Â = Maps.newHashMap();
        this.ŒÏ = Maps.newHashMap();
        this.Çªà¢ = new TextureAtlasSprite[10];
        this.ˆá = Double.MIN_VALUE;
        this.ÇŽÕ = Double.MIN_VALUE;
        this.É = Double.MIN_VALUE;
        this.áƒ = Integer.MIN_VALUE;
        this.á€ = Integer.MIN_VALUE;
        this.Õ = Integer.MIN_VALUE;
        this.à¢ = Double.MIN_VALUE;
        this.ŠÂµà = Double.MIN_VALUE;
        this.¥à = Double.MIN_VALUE;
        this.Âµà = Double.MIN_VALUE;
        this.Ç = Double.MIN_VALUE;
        this.È = new ChunkRenderDispatcher();
        this.ˆáŠ = -1;
        this.áŒŠ = 2;
        this.Ñ¢Â = false;
        this.áˆºáˆºÈ = new Vector4f[8];
        this.ÇŽá€ = new Vector3d();
        this.Ï = false;
        this.Ø­áŒŠá = true;
        this.Œ = -1;
        this.£Ï = false;
        this.Ø­á = -99999;
        this.ˆÉ = 0.0;
        this.Ï­Ï­Ï = 0.0;
        this.£Â = 0.0;
        this.Ó = new LinkedHashSet();
        this.à = new LinkedHashSet();
        this.Œ = GLAllocation.HorizonCode_Horizon_È(1);
        this.HorizonCode_Horizon_È = mcIn;
        this.Å = mcIn.ÇªÓ();
        (this.£á = mcIn.¥à()).HorizonCode_Horizon_È(RenderGlobal.ˆÏ­);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GlStateManager.áŒŠÆ(0);
        this.á();
        this.Ï = OpenGlHelper.Ó();
        if (this.Ï) {
            this.áŠ = new VboRenderList();
            this.Ý = new VboChunkFactory();
        }
        else {
            this.áŠ = new RenderList();
            this.Ý = new ListChunkFactory();
        }
        (this.Šáƒ = new VertexFormat()).HorizonCode_Horizon_È(new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.HorizonCode_Horizon_È, VertexFormatElement.Â.HorizonCode_Horizon_È, 3));
        this.Å();
        this.£á();
        this.ˆÏ­();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager resourceManager) {
        this.á();
    }
    
    private void á() {
        final TextureMap var1 = this.HorizonCode_Horizon_È.áŠ();
        for (int var2 = 0; var2 < this.Çªà¢.length; ++var2) {
            this.Çªà¢[var2] = var1.HorizonCode_Horizon_È("minecraft:blocks/destroy_stage_" + var2);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        if (OpenGlHelper.¥à) {
            if (ShaderLinkHelper.Â() == null) {
                ShaderLinkHelper.HorizonCode_Horizon_È();
            }
            final ResourceLocation_1975012498 var1 = new ResourceLocation_1975012498("shaders/post/entity_outline.json");
            try {
                (this.ÇŽÉ = new ShaderGroup(this.HorizonCode_Horizon_È.¥à(), this.HorizonCode_Horizon_È.Âµà(), this.HorizonCode_Horizon_È.Ý(), var1)).HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ó, this.HorizonCode_Horizon_È.à);
                this.Ê = this.ÇŽÉ.HorizonCode_Horizon_È("final");
            }
            catch (IOException var2) {
                RenderGlobal.Ø.warn("Failed to load shader: " + var1, (Throwable)var2);
                this.ÇŽÉ = null;
                this.Ê = null;
            }
            catch (JsonSyntaxException var3) {
                RenderGlobal.Ø.warn("Failed to load shader: " + var1, (Throwable)var3);
                this.ÇŽÉ = null;
                this.Ê = null;
            }
        }
        else {
            this.ÇŽÉ = null;
            this.Ê = null;
        }
    }
    
    public void Â() {
        if (this.Ý()) {
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 0, 1);
            this.Ê.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ó, this.HorizonCode_Horizon_È.à, false);
            GlStateManager.ÂµÈ();
        }
    }
    
    protected boolean Ý() {
        return this.Ê != null && this.ÇŽÉ != null && this.HorizonCode_Horizon_È.á != null && this.HorizonCode_Horizon_È.á.Ø­áŒŠá() && this.HorizonCode_Horizon_È.ŠÄ.ÇªáˆºÕ.Ø­áŒŠá();
    }
    
    private void ˆÏ­() {
        final Tessellator var1 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var2 = var1.Ý();
        if (this.ŠÄ != null) {
            this.ŠÄ.Ý();
        }
        if (this.Æ >= 0) {
            GLAllocation.Â(this.Æ);
            this.Æ = -1;
        }
        if (this.Ï) {
            this.ŠÄ = new VertexBuffer(this.Šáƒ);
            this.HorizonCode_Horizon_È(var2, -16.0f, true);
            var2.Ø­áŒŠá();
            var2.HorizonCode_Horizon_È();
            this.ŠÄ.HorizonCode_Horizon_È(var2.Ó(), var2.Âµá€());
        }
        else {
            GL11.glNewList(this.Æ = GLAllocation.HorizonCode_Horizon_È(1), 4864);
            this.HorizonCode_Horizon_È(var2, -16.0f, true);
            var1.Â();
            GL11.glEndList();
        }
    }
    
    private void £á() {
        final Tessellator var1 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var2 = var1.Ý();
        if (this.áŒŠà != null) {
            this.áŒŠà.Ý();
        }
        if (this.µÕ >= 0) {
            GLAllocation.Â(this.µÕ);
            this.µÕ = -1;
        }
        if (this.Ï) {
            this.áŒŠà = new VertexBuffer(this.Šáƒ);
            this.HorizonCode_Horizon_È(var2, 16.0f, false);
            var2.Ø­áŒŠá();
            var2.HorizonCode_Horizon_È();
            this.áŒŠà.HorizonCode_Horizon_È(var2.Ó(), var2.Âµá€());
        }
        else {
            GL11.glNewList(this.µÕ = GLAllocation.HorizonCode_Horizon_È(1), 4864);
            this.HorizonCode_Horizon_È(var2, 16.0f, false);
            var1.Â();
            GL11.glEndList();
        }
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer worldRendererIn, final float p_174968_2_, final boolean p_174968_3_) {
        final boolean var4 = true;
        final boolean var5 = true;
        worldRendererIn.Â();
        for (int var6 = -384; var6 <= 384; var6 += 64) {
            for (int var7 = -384; var7 <= 384; var7 += 64) {
                float var8 = var6;
                float var9 = var6 + 64;
                if (p_174968_3_) {
                    var9 = var6;
                    var8 = var6 + 64;
                }
                worldRendererIn.Â(var8, p_174968_2_, (double)var7);
                worldRendererIn.Â(var9, p_174968_2_, (double)var7);
                worldRendererIn.Â(var9, p_174968_2_, (double)(var7 + 64));
                worldRendererIn.Â(var8, p_174968_2_, (double)(var7 + 64));
            }
        }
    }
    
    private void Å() {
        final Tessellator var1 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var2 = var1.Ý();
        if (this.Ï­Ðƒà != null) {
            this.Ï­Ðƒà.Ý();
        }
        if (this.Ø­à >= 0) {
            GLAllocation.Â(this.Ø­à);
            this.Ø­à = -1;
        }
        if (this.Ï) {
            this.Ï­Ðƒà = new VertexBuffer(this.Šáƒ);
            this.HorizonCode_Horizon_È(var2);
            var2.Ø­áŒŠá();
            var2.HorizonCode_Horizon_È();
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2.Ó(), var2.Âµá€());
        }
        else {
            this.Ø­à = GLAllocation.HorizonCode_Horizon_È(1);
            GlStateManager.Çªà¢();
            GL11.glNewList(this.Ø­à, 4864);
            this.HorizonCode_Horizon_È(var2);
            var1.Â();
            GL11.glEndList();
            GlStateManager.Ê();
        }
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer worldRendererIn) {
        final Random var2 = new Random(10842L);
        worldRendererIn.Â();
        for (int var3 = 0; var3 < 1500; ++var3) {
            double var4 = var2.nextFloat() * 2.0f - 1.0f;
            double var5 = var2.nextFloat() * 2.0f - 1.0f;
            double var6 = var2.nextFloat() * 2.0f - 1.0f;
            final double var7 = 0.15f + var2.nextFloat() * 0.1f;
            double var8 = var4 * var4 + var5 * var5 + var6 * var6;
            if (var8 < 1.0 && var8 > 0.01) {
                var8 = 1.0 / Math.sqrt(var8);
                var4 *= var8;
                var5 *= var8;
                var6 *= var8;
                final double var9 = var4 * 100.0;
                final double var10 = var5 * 100.0;
                final double var11 = var6 * 100.0;
                final double var12 = Math.atan2(var4, var6);
                final double var13 = Math.sin(var12);
                final double var14 = Math.cos(var12);
                final double var15 = Math.atan2(Math.sqrt(var4 * var4 + var6 * var6), var5);
                final double var16 = Math.sin(var15);
                final double var17 = Math.cos(var15);
                final double var18 = var2.nextDouble() * 3.141592653589793 * 2.0;
                final double var19 = Math.sin(var18);
                final double var20 = Math.cos(var18);
                for (int var21 = 0; var21 < 4; ++var21) {
                    final double var22 = 0.0;
                    final double var23 = ((var21 & 0x2) - 1) * var7;
                    final double var24 = ((var21 + 1 & 0x2) - 1) * var7;
                    final double var25 = 0.0;
                    final double var26 = var23 * var20 - var24 * var19;
                    final double var27 = var24 * var20 + var23 * var19;
                    final double var28 = var26 * var16 + 0.0 * var17;
                    final double var29 = 0.0 * var16 - var26 * var17;
                    final double var30 = var29 * var13 - var27 * var14;
                    final double var31 = var27 * var13 + var29 * var14;
                    worldRendererIn.Â(var9 + var30, var10 + var28, var11 + var31);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final WorldClient worldClientIn) {
        if (this.£à != null) {
            this.£à.Â(this);
        }
        this.ˆá = Double.MIN_VALUE;
        this.ÇŽÕ = Double.MIN_VALUE;
        this.É = Double.MIN_VALUE;
        this.áƒ = Integer.MIN_VALUE;
        this.á€ = Integer.MIN_VALUE;
        this.Õ = Integer.MIN_VALUE;
        this.Å.HorizonCode_Horizon_È(worldClientIn);
        if ((this.£à = worldClientIn) != null) {
            worldClientIn.HorizonCode_Horizon_È(this);
            this.Ø­áŒŠá();
        }
    }
    
    public void Ø­áŒŠá() {
        if (this.£à != null) {
            this.Ø­áŒŠá = true;
            Blocks.µÕ.Â(Config.áŒŠà());
            Blocks.Æ.Â(Config.áŒŠà());
            BlockModelRenderer.HorizonCode_Horizon_È();
            this.ˆáŠ = this.HorizonCode_Horizon_È.ŠÄ.Ý;
            final boolean var1 = this.Ï;
            this.Ï = OpenGlHelper.Ó();
            if (var1 && !this.Ï) {
                this.áŠ = new RenderList();
                this.Ý = new ListChunkFactory();
            }
            else if (!var1 && this.Ï) {
                this.áŠ = new VboRenderList();
                this.Ý = new VboChunkFactory();
            }
            if (var1 != this.Ï) {
                this.Å();
                this.£á();
                this.ˆÏ­();
            }
            if (this.¥Æ != null) {
                this.¥Æ.HorizonCode_Horizon_È();
            }
            this.Âµá€();
            this.¥Æ = new ViewFrustum(this.£à, this.HorizonCode_Horizon_È.ŠÄ.Ý, this, this.Ý);
            if (this.£à != null) {
                final Entity var2 = this.HorizonCode_Horizon_È.ÇŽá€();
                if (var2 != null) {
                    this.¥Æ.HorizonCode_Horizon_È(var2.ŒÏ, var2.Ê);
                }
            }
            this.áŒŠ = 2;
        }
    }
    
    protected void Âµá€() {
        this.µà.clear();
        this.È.Â();
    }
    
    public void HorizonCode_Horizon_È(final int p_72720_1_, final int p_72720_2_) {
        if (OpenGlHelper.¥à && this.ÇŽÉ != null) {
            this.ÇŽÉ.HorizonCode_Horizon_È(p_72720_1_, p_72720_2_);
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_180446_1_, final ICamera p_180446_2_, final float partialTicks) {
        if (this.áŒŠ > 0) {
            --this.áŒŠ;
        }
        else {
            final double var4 = p_180446_1_.áŒŠà + (p_180446_1_.ŒÏ - p_180446_1_.áŒŠà) * partialTicks;
            final double var5 = p_180446_1_.ŠÄ + (p_180446_1_.Çªà¢ - p_180446_1_.ŠÄ) * partialTicks;
            final double var6 = p_180446_1_.Ñ¢á + (p_180446_1_.Ê - p_180446_1_.Ñ¢á) * partialTicks;
            this.£à.Ï­Ðƒà.HorizonCode_Horizon_È("prepare");
            TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È.¥à(), this.HorizonCode_Horizon_È.µà, this.HorizonCode_Horizon_È.ÇŽá€(), partialTicks);
            this.Å.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È.µà, this.HorizonCode_Horizon_È.ÇŽá€(), this.HorizonCode_Horizon_È.£á, this.HorizonCode_Horizon_È.ŠÄ, partialTicks);
            this.£ÂµÄ = 0;
            this.Ø­Âµ = 0;
            this.Ä = 0;
            final Entity var7 = this.HorizonCode_Horizon_È.ÇŽá€();
            final double var8 = var7.áˆºáˆºÈ + (var7.ŒÏ - var7.áˆºáˆºÈ) * partialTicks;
            final double var9 = var7.ÇŽá€ + (var7.Çªà¢ - var7.ÇŽá€) * partialTicks;
            final double var10 = var7.Ï + (var7.Ê - var7.Ï) * partialTicks;
            TileEntityRendererDispatcher.Â = var8;
            TileEntityRendererDispatcher.Ý = var9;
            TileEntityRendererDispatcher.Ø­áŒŠá = var10;
            this.Å.HorizonCode_Horizon_È(var8, var9, var10);
            this.HorizonCode_Horizon_È.µÕ.à();
            this.£à.Ï­Ðƒà.Ý("global");
            final List var11 = this.£à.Ø­à();
            this.£ÂµÄ = var11.size();
            if (Config.£á() && this.HorizonCode_Horizon_È.µÕ.Ø) {
                GlStateManager.£á();
            }
            for (int var12 = 0; var12 < this.£à.à.size(); ++var12) {
                final Entity var13 = this.£à.à.get(var12);
                ++this.Ø­Âµ;
                if (var13.Ø(var4, var5, var6)) {
                    this.Å.HorizonCode_Horizon_È(var13, partialTicks);
                }
            }
            if (this.Ý()) {
                GlStateManager.Ý(519);
                GlStateManager.£á();
                this.Ê.Ó();
                this.Ê.HorizonCode_Horizon_È(false);
                this.£à.Ï­Ðƒà.Ý("entityOutlines");
                RenderHelper.HorizonCode_Horizon_È();
                this.Å.Ý(true);
                for (int var12 = 0; var12 < var11.size(); ++var12) {
                    final Entity var13 = var11.get(var12);
                    final boolean var14 = this.HorizonCode_Horizon_È.ÇŽá€() instanceof EntityLivingBase && ((EntityLivingBase)this.HorizonCode_Horizon_È.ÇŽá€()).Ï­Ó();
                    final boolean var15 = var13.Ø(var4, var5, var6) && (var13.ÇªÂµÕ || p_180446_2_.HorizonCode_Horizon_È(var13.£É()) || var13.µÕ == this.HorizonCode_Horizon_È.á) && var13 instanceof EntityPlayer;
                    if ((var13 != this.HorizonCode_Horizon_È.ÇŽá€() || this.HorizonCode_Horizon_È.ŠÄ.µÏ != 0 || var14) && var15) {
                        this.Å.HorizonCode_Horizon_È(var13, partialTicks);
                    }
                }
                this.Å.Ý(false);
                RenderHelper.Â();
                GlStateManager.HorizonCode_Horizon_È(false);
                this.ÇŽÉ.HorizonCode_Horizon_È(partialTicks);
                GlStateManager.HorizonCode_Horizon_È(true);
                this.HorizonCode_Horizon_È.Ý().HorizonCode_Horizon_È(false);
                GlStateManager.ˆÏ­();
                GlStateManager.Ý(515);
                GlStateManager.áˆºÑ¢Õ();
                GlStateManager.Ø­áŒŠá();
            }
            this.£à.Ï­Ðƒà.Ý("entities");
            Iterator var16 = this.ˆà.iterator();
            final boolean oldFancyGraphics = this.HorizonCode_Horizon_È.ŠÄ.Û;
            this.HorizonCode_Horizon_È.ŠÄ.Û = Config.ŠÄ();
            while (var16.hasNext()) {
                final HorizonCode_Horizon_È var17 = var16.next();
                final Chunk fontRenderer = this.£à.à(var17.HorizonCode_Horizon_È.áŒŠÆ());
                final ClassInheratanceMultiMap var18 = fontRenderer.¥Æ()[var17.HorizonCode_Horizon_È.áŒŠÆ().Â() / 16];
                Object var19 = Iterators.emptyIterator();
                if (var18.size() > 0) {
                    var19 = var18.iterator();
                }
                while (((Iterator)var19).hasNext()) {
                    final Entity var20 = ((Iterator)var19).next();
                    final boolean var21 = this.Å.HorizonCode_Horizon_È(var20, p_180446_2_, var4, var5, var6) || var20.µÕ == this.HorizonCode_Horizon_È.á;
                    if (var21) {
                        final boolean teClass = this.HorizonCode_Horizon_È.ÇŽá€() instanceof EntityLivingBase && ((EntityLivingBase)this.HorizonCode_Horizon_È.ÇŽá€()).Ï­Ó();
                        if (var20 == this.HorizonCode_Horizon_È.ÇŽá€() && this.HorizonCode_Horizon_È.ŠÄ.µÏ == 0 && !teClass) {
                            continue;
                        }
                        if (var20.Çªà¢ >= 0.0 && var20.Çªà¢ < 256.0 && !this.£à.Ó(new BlockPos(var20))) {
                            continue;
                        }
                        ++this.Ø­Âµ;
                        if (var20.getClass() == EntityItemFrame.class) {
                            var20.¥Æ = 0.06;
                        }
                        this.Âµá€ = var20;
                        this.Å.HorizonCode_Horizon_È(var20, partialTicks);
                        this.Âµá€ = null;
                    }
                    if (!var21 && var20 instanceof EntityWitherSkull) {
                        this.HorizonCode_Horizon_È.ÇªÓ().Â(var20, partialTicks);
                    }
                }
            }
            this.HorizonCode_Horizon_È.ŠÄ.Û = oldFancyGraphics;
            final FontRenderer var22 = TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
            this.£à.Ï­Ðƒà.Ý("blockentities");
            RenderHelper.Â();
            var16 = this.ˆà.iterator();
            while (var16.hasNext()) {
                final HorizonCode_Horizon_È var17 = var16.next();
                final List var23 = var17.HorizonCode_Horizon_È.à().Â();
                Object var24 = Iterators.emptyIterator();
                if (var23.size() > 0) {
                    var24 = var23.iterator();
                }
                while (((Iterator)var24).hasNext()) {
                    final TileEntity var25 = ((Iterator)var24).next();
                    final Class var26 = var25.getClass();
                    if (var26 == TileEntitySign.class && !Config.áŒŠÆ) {
                        final EntityPlayerSP pl = this.HorizonCode_Horizon_È.á;
                        final double distSq = var25.HorizonCode_Horizon_È(pl.ŒÏ, pl.Çªà¢, pl.Ê);
                        if (distSq > 256.0) {
                            var22.Ó = false;
                        }
                    }
                    TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var25, partialTicks, -1);
                    var22.Ó = true;
                }
            }
            this.µà();
            var16 = this.Â.values().iterator();
            while (var16.hasNext()) {
                final DestroyBlockProgress var27 = var16.next();
                BlockPos var28 = var27.HorizonCode_Horizon_È();
                TileEntity var25 = this.£à.HorizonCode_Horizon_È(var28);
                if (var25 instanceof TileEntityChest) {
                    final TileEntityChest var29 = (TileEntityChest)var25;
                    if (var29.Ø != null) {
                        var28 = var28.HorizonCode_Horizon_È(EnumFacing.Âµá€);
                        var25 = this.£à.HorizonCode_Horizon_È(var28);
                    }
                    else if (var29.Ó != null) {
                        var28 = var28.HorizonCode_Horizon_È(EnumFacing.Ý);
                        var25 = this.£à.HorizonCode_Horizon_È(var28);
                    }
                }
                final Block var30 = this.£à.Â(var28).Ý();
                if (var25 != null && (var30 instanceof BlockChest || var30 instanceof BlockEnderChest || var30 instanceof BlockSign || var30 instanceof BlockSkull)) {
                    TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var25, partialTicks, var27.Â());
                }
            }
            this.ˆà();
            this.HorizonCode_Horizon_È.µÕ.Ó();
            this.HorizonCode_Horizon_È.ÇŽÕ.Â();
        }
    }
    
    public String Ó() {
        final int var1 = this.¥Æ.Ó.length;
        int var2 = 0;
        for (final HorizonCode_Horizon_È var4 : this.ˆà) {
            final CompiledChunk var5 = var4.HorizonCode_Horizon_È.Â;
            if (var5 != CompiledChunk.HorizonCode_Horizon_È && !var5.HorizonCode_Horizon_È()) {
                ++var2;
            }
        }
        return String.format("C: %d/%d %sD: %d, %s", var2, var1, this.HorizonCode_Horizon_È.¥à ? "(s) " : "", this.ˆáŠ, this.È.HorizonCode_Horizon_È());
    }
    
    public String à() {
        return "E: " + this.Ø­Âµ + "/" + this.£ÂµÄ + ", B: " + this.Ä + ", I: " + (this.£ÂµÄ - this.Ä - this.Ø­Âµ) + ", " + Config.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final Entity viewEntity, final double partialTicks, ICamera camera, final int frameCount, final boolean playerSpectator) {
        if (this.HorizonCode_Horizon_È.ŠÄ.Ý != this.ˆáŠ) {
            this.Ø­áŒŠá();
        }
        this.£à.Ï­Ðƒà.HorizonCode_Horizon_È("camera");
        final double var7 = viewEntity.ŒÏ - this.ˆá;
        final double var8 = viewEntity.Çªà¢ - this.ÇŽÕ;
        final double var9 = viewEntity.Ê - this.É;
        if (this.áƒ != viewEntity.£Õ || this.á€ != viewEntity.Ï­Ô || this.Õ != viewEntity.Œà || var7 * var7 + var8 * var8 + var9 * var9 > 16.0) {
            this.ˆá = viewEntity.ŒÏ;
            this.ÇŽÕ = viewEntity.Çªà¢;
            this.É = viewEntity.Ê;
            this.áƒ = viewEntity.£Õ;
            this.á€ = viewEntity.Ï­Ô;
            this.Õ = viewEntity.Œà;
            this.¥Æ.HorizonCode_Horizon_È(viewEntity.ŒÏ, viewEntity.Ê);
        }
        this.£à.Ï­Ðƒà.Ý("renderlistcamera");
        final double var10 = viewEntity.áˆºáˆºÈ + (viewEntity.ŒÏ - viewEntity.áˆºáˆºÈ) * partialTicks;
        final double var11 = viewEntity.ÇŽá€ + (viewEntity.Çªà¢ - viewEntity.ÇŽá€) * partialTicks;
        final double var12 = viewEntity.Ï + (viewEntity.Ê - viewEntity.Ï) * partialTicks;
        this.áŠ.HorizonCode_Horizon_È(var10, var11, var12);
        this.£à.Ï­Ðƒà.Ý("cull");
        if (this.Ï­à != null) {
            final Frustrum var13 = new Frustrum(this.Ï­à);
            var13.HorizonCode_Horizon_È(this.ÇŽá€.x, this.ÇŽá€.y, this.ÇŽá€.z);
            camera = var13;
        }
        this.HorizonCode_Horizon_È.ÇŽÕ.Ý("culling");
        final BlockPos var14 = new BlockPos(var10, var11 + viewEntity.Ðƒáƒ(), var12);
        final RenderChunk var15 = this.¥Æ.HorizonCode_Horizon_È(var14);
        final BlockPos var16 = new BlockPos(MathHelper.Ý(var10) / 16 * 16, MathHelper.Ý(var11) / 16 * 16, MathHelper.Ý(var12) / 16 * 16);
        this.Ø­áŒŠá = (this.Ø­áŒŠá || !this.µà.isEmpty() || viewEntity.ŒÏ != this.à¢ || viewEntity.Çªà¢ != this.ŠÂµà || viewEntity.Ê != this.¥à || viewEntity.áƒ != this.Âµà || viewEntity.É != this.Ç);
        this.à¢ = viewEntity.ŒÏ;
        this.ŠÂµà = viewEntity.Çªà¢;
        this.¥à = viewEntity.Ê;
        this.Âµà = viewEntity.áƒ;
        this.Ç = viewEntity.É;
        final boolean var17 = this.Ï­à != null;
        Lagometer.Ó.HorizonCode_Horizon_È();
        if (!var17 && this.Ø­áŒŠá) {
            this.Ø­áŒŠá = false;
            this.ˆà = Lists.newArrayList();
            final LinkedList var18 = Lists.newLinkedList();
            boolean var19 = this.HorizonCode_Horizon_È.¥à;
            if (var15 == null) {
                final int var20 = (var14.Â() > 0) ? 248 : 8;
                for (int var21 = -this.ˆáŠ; var21 <= this.ˆáŠ; ++var21) {
                    for (int var22 = -this.ˆáŠ; var22 <= this.ˆáŠ; ++var22) {
                        final RenderChunk var23 = this.¥Æ.HorizonCode_Horizon_È(new BlockPos((var21 << 4) + 8, var20, (var22 << 4) + 8));
                        if (var23 != null && camera.HorizonCode_Horizon_È(var23.Ý)) {
                            var23.HorizonCode_Horizon_È(frameCount);
                            var18.add(new HorizonCode_Horizon_È(var23, null, 0, null));
                        }
                    }
                }
            }
            else {
                boolean var24 = false;
                final HorizonCode_Horizon_È var25 = new HorizonCode_Horizon_È(var15, null, 0, null);
                final Set var26 = this.Ý(var14);
                if (!var26.isEmpty() && var26.size() == 1) {
                    final Vector3f var27 = this.HorizonCode_Horizon_È(viewEntity, partialTicks);
                    final EnumFacing var28 = EnumFacing.HorizonCode_Horizon_È(var27.x, var27.y, var27.z).Âµá€();
                    var26.remove(var28);
                }
                if (var26.isEmpty()) {
                    var24 = true;
                }
                if (var24 && !playerSpectator) {
                    this.ˆà.add(var25);
                }
                else {
                    if (playerSpectator && this.£à.Â(var14).Ý().Å()) {
                        var19 = false;
                    }
                    var15.HorizonCode_Horizon_È(frameCount);
                    var18.add(var25);
                }
            }
            while (!var18.isEmpty()) {
                final HorizonCode_Horizon_È var29 = var18.poll();
                final RenderChunk var30 = var29.HorizonCode_Horizon_È;
                final EnumFacing var31 = var29.Â;
                final BlockPos var32 = var30.áŒŠÆ();
                this.ˆà.add(var29);
                for (final EnumFacing var36 : EnumFacing.à) {
                    final RenderChunk var37 = this.HorizonCode_Horizon_È(var14, var30, var36);
                    if ((!var19 || !var29.Ý.contains(var36.Âµá€())) && (!var19 || var31 == null || var30.à().HorizonCode_Horizon_È(var31.Âµá€(), var36)) && var37 != null && var37.HorizonCode_Horizon_È(frameCount) && camera.HorizonCode_Horizon_È(var37.Ý)) {
                        final HorizonCode_Horizon_È var38 = new HorizonCode_Horizon_È(var37, var36, var29.Ø­áŒŠá + 1, null);
                        var38.Ý.addAll(var29.Ý);
                        var38.Ý.add(var36);
                        var18.add(var38);
                    }
                }
            }
        }
        if (this.Ñ¢Â) {
            this.HorizonCode_Horizon_È(var10, var11, var12);
            this.Ñ¢Â = false;
        }
        Lagometer.Ó.Â();
        this.È.Âµá€();
        final Set var39 = this.µà;
        this.µà = Sets.newLinkedHashSet();
        final Iterator var40 = this.ˆà.iterator();
        Lagometer.Âµá€.HorizonCode_Horizon_È();
        while (var40.hasNext()) {
            final HorizonCode_Horizon_È var29 = var40.next();
            final RenderChunk var30 = var29.HorizonCode_Horizon_È;
            if (var30.ÂµÈ() || var39.contains(var30)) {
                this.Ø­áŒŠá = true;
                if (this.HorizonCode_Horizon_È(var16, var29.HorizonCode_Horizon_È)) {
                    if (!Config.£ÇªÓ()) {
                        this.à.add(var30);
                    }
                    else {
                        this.HorizonCode_Horizon_È.ÇŽÕ.HorizonCode_Horizon_È("build near");
                        this.È.Â(var30);
                        var30.HorizonCode_Horizon_È(false);
                        this.HorizonCode_Horizon_È.ÇŽÕ.Â();
                    }
                }
                else {
                    this.µà.add(var30);
                }
            }
        }
        Lagometer.Âµá€.Â();
        this.µà.addAll(var39);
        this.HorizonCode_Horizon_È.ÇŽÕ.Â();
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_174983_1_, final RenderChunk p_174983_2_) {
        final BlockPos var3 = p_174983_2_.áŒŠÆ();
        return MathHelper.HorizonCode_Horizon_È(p_174983_1_.HorizonCode_Horizon_È() - var3.HorizonCode_Horizon_È()) <= 16 && MathHelper.HorizonCode_Horizon_È(p_174983_1_.Â() - var3.Â()) <= 16 && MathHelper.HorizonCode_Horizon_È(p_174983_1_.Ý() - var3.Ý()) <= 16;
    }
    
    private Set Ý(final BlockPos p_174978_1_) {
        final VisGraph var2 = new VisGraph();
        final BlockPos var3 = new BlockPos(p_174978_1_.HorizonCode_Horizon_È() >> 4 << 4, p_174978_1_.Â() >> 4 << 4, p_174978_1_.Ý() >> 4 << 4);
        final Chunk var4 = this.£à.à(var3);
        for (final BlockPos.HorizonCode_Horizon_È var6 : BlockPos.Ý(var3, var3.Â(15, 15, 15))) {
            if (var4.Ý(var6).Å()) {
                var2.HorizonCode_Horizon_È(var6);
            }
        }
        return var2.Â(p_174978_1_);
    }
    
    private RenderChunk HorizonCode_Horizon_È(final BlockPos p_174973_1_, final RenderChunk renderChunk, final EnumFacing p_174973_3_) {
        final BlockPos var4 = renderChunk.HorizonCode_Horizon_È(p_174973_3_);
        return (MathHelper.HorizonCode_Horizon_È(p_174973_1_.HorizonCode_Horizon_È() - var4.HorizonCode_Horizon_È()) > this.ˆáŠ * 16) ? null : ((var4.Â() >= 0 && var4.Â() < 256) ? ((MathHelper.HorizonCode_Horizon_È(p_174973_1_.Ý() - var4.Ý()) > this.ˆáŠ * 16) ? null : this.¥Æ.HorizonCode_Horizon_È(var4)) : null);
    }
    
    private void HorizonCode_Horizon_È(final double p_174984_1_, final double p_174984_3_, final double p_174984_5_) {
        this.Ï­à = new ClippingHelperImpl();
        ((ClippingHelperImpl)this.Ï­à).Â();
        final Matrix4f var7 = new Matrix4f(this.Ï­à.Ý);
        var7.transpose();
        final Matrix4f var8 = new Matrix4f(this.Ï­à.Â);
        var8.transpose();
        final Matrix4f var9 = new Matrix4f();
        var9.mul(var8, var7);
        var9.invert();
        this.ÇŽá€.x = p_174984_1_;
        this.ÇŽá€.y = p_174984_3_;
        this.ÇŽá€.z = p_174984_5_;
        this.áˆºáˆºÈ[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.áˆºáˆºÈ[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.áˆºáˆºÈ[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.áˆºáˆºÈ[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.áˆºáˆºÈ[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.áˆºáˆºÈ[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.áˆºáˆºÈ[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.áˆºáˆºÈ[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        for (int var10 = 0; var10 < 8; ++var10) {
            var9.transform((Tuple4f)this.áˆºáˆºÈ[var10]);
            final Vector4f vector4f = this.áˆºáˆºÈ[var10];
            vector4f.x /= this.áˆºáˆºÈ[var10].w;
            final Vector4f vector4f2 = this.áˆºáˆºÈ[var10];
            vector4f2.y /= this.áˆºáˆºÈ[var10].w;
            final Vector4f vector4f3 = this.áˆºáˆºÈ[var10];
            vector4f3.z /= this.áˆºáˆºÈ[var10].w;
            this.áˆºáˆºÈ[var10].w = 1.0f;
        }
    }
    
    protected Vector3f HorizonCode_Horizon_È(final Entity entityIn, final double partialTicks) {
        float var4 = (float)(entityIn.Õ + (entityIn.áƒ - entityIn.Õ) * partialTicks);
        final float var5 = (float)(entityIn.á€ + (entityIn.É - entityIn.á€) * partialTicks);
        if (Minecraft.áŒŠà().ŠÄ.µÏ == 2) {
            var4 += 180.0f;
        }
        final float var6 = MathHelper.Â(-var5 * 0.017453292f - 3.1415927f);
        final float var7 = MathHelper.HorizonCode_Horizon_È(-var5 * 0.017453292f - 3.1415927f);
        final float var8 = -MathHelper.Â(-var4 * 0.017453292f);
        final float var9 = MathHelper.HorizonCode_Horizon_È(-var4 * 0.017453292f);
        return new Vector3f(var7 * var8, var9, var6 * var8);
    }
    
    public int HorizonCode_Horizon_È(final EnumWorldBlockLayer blockLayerIn, final double partialTicks, final int pass, final Entity entityIn) {
        RenderHelper.HorizonCode_Horizon_È();
        if (blockLayerIn == EnumWorldBlockLayer.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È.ÇŽÕ.HorizonCode_Horizon_È("translucent_sort");
            final double var15 = entityIn.ŒÏ - this.Ô;
            final double var16 = entityIn.Çªà¢ - this.ÇªÓ;
            final double var17 = entityIn.Ê - this.áˆºÏ;
            if (var15 * var15 + var16 * var16 + var17 * var17 > 1.0) {
                this.Ô = entityIn.ŒÏ;
                this.ÇªÓ = entityIn.Çªà¢;
                this.áˆºÏ = entityIn.Ê;
                int var18 = 0;
                final Iterator var19 = this.ˆà.iterator();
                this.Ó.clear();
                while (var19.hasNext()) {
                    final HorizonCode_Horizon_È var20 = var19.next();
                    if (var20.HorizonCode_Horizon_È.Â.Ø­áŒŠá(blockLayerIn) && var18++ < 15) {
                        this.Ó.add(var20.HorizonCode_Horizon_È);
                    }
                }
            }
            this.HorizonCode_Horizon_È.ÇŽÕ.Â();
        }
        this.HorizonCode_Horizon_È.ÇŽÕ.HorizonCode_Horizon_È("filterempty");
        int var21 = 0;
        final boolean var22 = blockLayerIn == EnumWorldBlockLayer.Ø­áŒŠá;
        final int var23 = var22 ? (this.ˆà.size() - 1) : 0;
        for (int var24 = var22 ? -1 : this.ˆà.size(), var25 = var22 ? -1 : 1, var26 = var23; var26 != var24; var26 += var25) {
            final RenderChunk var27 = this.ˆà.get(var26).HorizonCode_Horizon_È;
            if (!var27.à().Â(blockLayerIn)) {
                ++var21;
                this.áŠ.HorizonCode_Horizon_È(var27, blockLayerIn);
            }
        }
        if (Config.£á() && this.HorizonCode_Horizon_È.µÕ.Ø) {
            GlStateManager.£á();
        }
        this.HorizonCode_Horizon_È.ÇŽÕ.Ý("render_" + blockLayerIn);
        this.HorizonCode_Horizon_È(blockLayerIn);
        this.HorizonCode_Horizon_È.ÇŽÕ.Â();
        return var21;
    }
    
    private void HorizonCode_Horizon_È(final EnumWorldBlockLayer blockLayerIn) {
        this.HorizonCode_Horizon_È.µÕ.à();
        if (OpenGlHelper.Ó()) {
            GL11.glEnableClientState(32884);
            OpenGlHelper.á(OpenGlHelper.£à);
            GL11.glEnableClientState(32888);
            OpenGlHelper.á(OpenGlHelper.µà);
            GL11.glEnableClientState(32888);
            OpenGlHelper.á(OpenGlHelper.£à);
            GL11.glEnableClientState(32886);
        }
        this.áŠ.HorizonCode_Horizon_È(blockLayerIn);
        if (OpenGlHelper.Ó()) {
            final List var2 = DefaultVertexFormats.HorizonCode_Horizon_È.à();
            for (final VertexFormatElement var4 : var2) {
                final VertexFormatElement.Â var5 = var4.Ý();
                final int var6 = var4.Âµá€();
                switch (RenderGlobal.Â.HorizonCode_Horizon_È[var5.ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        GL11.glDisableClientState(32884);
                        continue;
                    }
                    case 2: {
                        OpenGlHelper.á(OpenGlHelper.£à + var6);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.á(OpenGlHelper.£à);
                        continue;
                    }
                    case 3: {
                        GL11.glDisableClientState(32886);
                        GlStateManager.ÇŽÉ();
                        continue;
                    }
                }
            }
        }
        this.HorizonCode_Horizon_È.µÕ.Ó();
    }
    
    private void HorizonCode_Horizon_È(final Iterator p_174965_1_) {
        while (p_174965_1_.hasNext()) {
            final DestroyBlockProgress var2 = p_174965_1_.next();
            final int var3 = var2.Ý();
            if (this.Ñ¢á - var3 > 400) {
                p_174965_1_.remove();
            }
        }
    }
    
    public void Ø() {
        ++this.Ñ¢á;
        if (this.Ñ¢á % 20 == 0) {
            this.HorizonCode_Horizon_È(this.Â.values().iterator());
        }
    }
    
    private void £à() {
        if (Config.Ï()) {
            GlStateManager.£á();
            GlStateManager.Ý();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            RenderHelper.HorizonCode_Horizon_È();
            GlStateManager.HorizonCode_Horizon_È(false);
            this.£á.HorizonCode_Horizon_È(RenderGlobal.á);
            final Tessellator var1 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var2 = var1.Ý();
            for (int var3 = 0; var3 < 6; ++var3) {
                GlStateManager.Çªà¢();
                if (var3 == 1) {
                    GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var3 == 2) {
                    GlStateManager.Â(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var3 == 3) {
                    GlStateManager.Â(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var3 == 4) {
                    GlStateManager.Â(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (var3 == 5) {
                    GlStateManager.Â(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                var2.Â();
                var2.Ý(2631720);
                var2.HorizonCode_Horizon_È(-100.0, -100.0, -100.0, 0.0, 0.0);
                var2.HorizonCode_Horizon_È(-100.0, -100.0, 100.0, 0.0, 16.0);
                var2.HorizonCode_Horizon_È(100.0, -100.0, 100.0, 16.0, 16.0);
                var2.HorizonCode_Horizon_È(100.0, -100.0, -100.0, 16.0, 0.0);
                var1.Â();
                GlStateManager.Ê();
            }
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.µÕ();
            GlStateManager.Ø­áŒŠá();
        }
    }
    
    public void HorizonCode_Horizon_È(final float partialTicks, final int pass) {
        if (Reflector.Ï.Â()) {
            final WorldProvider var3 = this.HorizonCode_Horizon_È.áŒŠÆ.£à;
            final Object var4 = Reflector.Ó(var3, Reflector.Ï, new Object[0]);
            if (var4 != null) {
                Reflector.HorizonCode_Horizon_È(var4, Reflector.£Ï, partialTicks, this.£à, this.HorizonCode_Horizon_È);
                return;
            }
        }
        if (this.HorizonCode_Horizon_È.áŒŠÆ.£à.µà() == 1) {
            this.£à();
        }
        else if (this.HorizonCode_Horizon_È.áŒŠÆ.£à.Ø­áŒŠá()) {
            GlStateManager.Æ();
            Vec3 var5 = this.£à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ÇŽá€(), partialTicks);
            var5 = CustomColorizer.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È.áŒŠÆ, this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ, this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢ + 1.0, this.HorizonCode_Horizon_È.ÇŽá€().Ê);
            float var6 = (float)var5.HorizonCode_Horizon_È;
            float var7 = (float)var5.Â;
            float var8 = (float)var5.Ý;
            if (pass != 2) {
                final float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
                final float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
                final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
                var6 = var9;
                var7 = var10;
                var8 = var11;
            }
            GlStateManager.Ý(var6, var7, var8);
            final Tessellator var12 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var13 = var12.Ý();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.ˆÏ­();
            GlStateManager.Ý(var6, var7, var8);
            if (Config.Ï()) {
                if (this.Ï) {
                    this.áŒŠà.HorizonCode_Horizon_È();
                    GL11.glEnableClientState(32884);
                    GL11.glVertexPointer(3, 5126, 12, 0L);
                    this.áŒŠà.HorizonCode_Horizon_È(7);
                    this.áŒŠà.Â();
                    GL11.glDisableClientState(32884);
                }
                else {
                    GlStateManager.ˆÏ­(this.µÕ);
                }
            }
            GlStateManager.£á();
            GlStateManager.Ý();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            RenderHelper.HorizonCode_Horizon_È();
            final float[] var14 = this.£à.£à.HorizonCode_Horizon_È(this.£à.Ý(partialTicks), partialTicks);
            if (var14 != null && Config.Ô()) {
                GlStateManager.Æ();
                GlStateManager.áˆºÑ¢Õ(7425);
                GlStateManager.Çªà¢();
                GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â((MathHelper.HorizonCode_Horizon_È(this.£à.Ø­áŒŠá(partialTicks)) < 0.0f) ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.Â(90.0f, 0.0f, 0.0f, 1.0f);
                float var15 = var14[0];
                float var16 = var14[1];
                float var17 = var14[2];
                if (pass != 2) {
                    final float var18 = (var15 * 30.0f + var16 * 59.0f + var17 * 11.0f) / 100.0f;
                    final float var19 = (var15 * 30.0f + var16 * 70.0f) / 100.0f;
                    final float var20 = (var15 * 30.0f + var17 * 70.0f) / 100.0f;
                    var15 = var18;
                    var16 = var19;
                    var17 = var20;
                }
                var13.HorizonCode_Horizon_È(6);
                var13.HorizonCode_Horizon_È(var15, var16, var17, var14[3]);
                var13.Â(0.0, 100.0, 0.0);
                final boolean var21 = true;
                var13.HorizonCode_Horizon_È(var14[0], var14[1], var14[2], 0.0f);
                for (int var22 = 0; var22 <= 16; ++var22) {
                    final float var20 = var22 * 3.1415927f * 2.0f / 16.0f;
                    final float var23 = MathHelper.HorizonCode_Horizon_È(var20);
                    final float var24 = MathHelper.Â(var20);
                    var13.Â(var23 * 120.0f, var24 * 120.0f, (double)(-var24 * 40.0f * var14[3]));
                }
                var12.Â();
                GlStateManager.Ê();
                GlStateManager.áˆºÑ¢Õ(7424);
            }
            GlStateManager.µÕ();
            GlStateManager.HorizonCode_Horizon_È(770, 1, 1, 0);
            GlStateManager.Çªà¢();
            float var15 = 1.0f - this.£à.áˆºÑ¢Õ(partialTicks);
            float var16 = 0.0f;
            float var17 = 0.0f;
            float var18 = 0.0f;
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, var15);
            GlStateManager.Â(0.0f, 0.0f, 0.0f);
            GlStateManager.Â(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.HorizonCode_Horizon_È(this.£à, this.£á, this.£à.Ý(partialTicks), var15);
            GlStateManager.Â(this.£à.Ý(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (Config.Ô()) {
                float var19 = 30.0f;
                this.£á.HorizonCode_Horizon_È(RenderGlobal.áˆºÑ¢Õ);
                var13.Â();
                var13.HorizonCode_Horizon_È(-var19, 100.0, -var19, 0.0, 0.0);
                var13.HorizonCode_Horizon_È(var19, 100.0, -var19, 1.0, 0.0);
                var13.HorizonCode_Horizon_È(var19, 100.0, var19, 1.0, 1.0);
                var13.HorizonCode_Horizon_È(-var19, 100.0, var19, 0.0, 1.0);
                var12.Â();
                var19 = 20.0f;
                this.£á.HorizonCode_Horizon_È(RenderGlobal.áŒŠÆ);
                final int var25 = this.£à.á();
                final int var26 = var25 % 4;
                final int var22 = var25 / 4 % 2;
                final float var23 = (var26 + 0) / 4.0f;
                final float var24 = (var22 + 0) / 2.0f;
                final float var27 = (var26 + 1) / 4.0f;
                final float var28 = (var22 + 1) / 2.0f;
                var13.Â();
                var13.HorizonCode_Horizon_È(-var19, -100.0, var19, var27, var28);
                var13.HorizonCode_Horizon_È(var19, -100.0, var19, var23, var28);
                var13.HorizonCode_Horizon_È(var19, -100.0, -var19, var23, var24);
                var13.HorizonCode_Horizon_È(-var19, -100.0, -var19, var27, var24);
                var12.Â();
            }
            GlStateManager.Æ();
            final float var20 = this.£à.à(partialTicks) * var15;
            if (var20 > 0.0f && Config.ÇªÓ() && !CustomSky.HorizonCode_Horizon_È(this.£à)) {
                GlStateManager.Ý(var20, var20, var20, var20);
                if (this.Ï) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È();
                    GL11.glEnableClientState(32884);
                    GL11.glVertexPointer(3, 5126, 12, 0L);
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(7);
                    this.Ï­Ðƒà.Â();
                    GL11.glDisableClientState(32884);
                }
                else {
                    GlStateManager.ˆÏ­(this.Ø­à);
                }
            }
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.ÂµÈ();
            GlStateManager.Ø­áŒŠá();
            GlStateManager.ˆÏ­();
            GlStateManager.Ê();
            GlStateManager.Æ();
            GlStateManager.Ý(0.0f, 0.0f, 0.0f);
            final double var29 = this.HorizonCode_Horizon_È.á.à(partialTicks).Â - this.£à.á€();
            if (var29 < 0.0) {
                GlStateManager.Çªà¢();
                GlStateManager.Â(0.0f, 12.0f, 0.0f);
                if (this.Ï) {
                    this.ŠÄ.HorizonCode_Horizon_È();
                    GL11.glEnableClientState(32884);
                    GL11.glVertexPointer(3, 5126, 12, 0L);
                    this.ŠÄ.HorizonCode_Horizon_È(7);
                    this.ŠÄ.Â();
                    GL11.glDisableClientState(32884);
                }
                else {
                    GlStateManager.ˆÏ­(this.Æ);
                }
                GlStateManager.Ê();
                var17 = 1.0f;
                var18 = -(float)(var29 + 65.0);
                final float var19 = -1.0f;
                var13.Â();
                var13.HorizonCode_Horizon_È(0, 255);
                var13.Â(-1.0, var18, 1.0);
                var13.Â(1.0, var18, 1.0);
                var13.Â(1.0, -1.0, 1.0);
                var13.Â(-1.0, -1.0, 1.0);
                var13.Â(-1.0, -1.0, -1.0);
                var13.Â(1.0, -1.0, -1.0);
                var13.Â(1.0, var18, -1.0);
                var13.Â(-1.0, var18, -1.0);
                var13.Â(1.0, -1.0, -1.0);
                var13.Â(1.0, -1.0, 1.0);
                var13.Â(1.0, var18, 1.0);
                var13.Â(1.0, var18, -1.0);
                var13.Â(-1.0, var18, -1.0);
                var13.Â(-1.0, var18, 1.0);
                var13.Â(-1.0, -1.0, 1.0);
                var13.Â(-1.0, -1.0, -1.0);
                var13.Â(-1.0, -1.0, -1.0);
                var13.Â(-1.0, -1.0, 1.0);
                var13.Â(1.0, -1.0, 1.0);
                var13.Â(1.0, -1.0, -1.0);
                var12.Â();
            }
            if (this.£à.£à.à()) {
                GlStateManager.Ý(var6 * 0.2f + 0.04f, var7 * 0.2f + 0.04f, var8 * 0.6f + 0.1f);
            }
            else {
                GlStateManager.Ý(var6, var7, var8);
            }
            if (this.HorizonCode_Horizon_È.ŠÄ.Ý <= 4) {
                GlStateManager.Ý(this.HorizonCode_Horizon_È.µÕ.Ø­áŒŠá, this.HorizonCode_Horizon_È.µÕ.Âµá€, this.HorizonCode_Horizon_È.µÕ.Ó);
            }
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, -(float)(var29 - 16.0), 0.0f);
            if (Config.Ï()) {
                GlStateManager.ˆÏ­(this.Æ);
            }
            GlStateManager.Ê();
            GlStateManager.µÕ();
            GlStateManager.HorizonCode_Horizon_È(true);
        }
    }
    
    public void Â(float p_180447_1_, final int p_180447_2_) {
        if (!Config.Šáƒ()) {
            if (Reflector.Ô.Â()) {
                final WorldProvider partialTicks = this.HorizonCode_Horizon_È.áŒŠÆ.£à;
                final Object var3 = Reflector.Ó(partialTicks, Reflector.Ô, new Object[0]);
                if (var3 != null) {
                    Reflector.HorizonCode_Horizon_È(var3, Reflector.£Ï, p_180447_1_, this.£à, this.HorizonCode_Horizon_È);
                    return;
                }
            }
            if (this.HorizonCode_Horizon_È.áŒŠÆ.£à.Ø­áŒŠá()) {
                if (Config.Æ()) {
                    this.Ý(p_180447_1_, p_180447_2_);
                }
                else {
                    final float partialTicks2 = p_180447_1_;
                    p_180447_1_ = 0.0f;
                    GlStateManager.£à();
                    final float var4 = (float)(this.HorizonCode_Horizon_È.ÇŽá€().ÇŽá€ + (this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢ - this.HorizonCode_Horizon_È.ÇŽá€().ÇŽá€) * p_180447_1_);
                    final boolean var5 = true;
                    final boolean var6 = true;
                    final Tessellator var7 = Tessellator.HorizonCode_Horizon_È();
                    final WorldRenderer var8 = var7.Ý();
                    this.£á.HorizonCode_Horizon_È(RenderGlobal.ÂµÈ);
                    GlStateManager.á();
                    GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                    if (this.£Ï || this.Ñ¢á >= this.Ø­á + 20) {
                        GL11.glNewList(this.Œ, 4864);
                        final Vec3 entityliving = this.£à.Âµá€(p_180447_1_);
                        float exactPlayerX = (float)entityliving.HorizonCode_Horizon_È;
                        float var9 = (float)entityliving.Â;
                        float exactPlayerY = (float)entityliving.Ý;
                        if (p_180447_2_ != 2) {
                            final float var10 = (exactPlayerX * 30.0f + var9 * 59.0f + exactPlayerY * 11.0f) / 100.0f;
                            final float exactPlayerZ = (exactPlayerX * 30.0f + var9 * 70.0f) / 100.0f;
                            final float var11 = (exactPlayerX * 30.0f + exactPlayerY * 70.0f) / 100.0f;
                            exactPlayerX = var10;
                            var9 = exactPlayerZ;
                            exactPlayerY = var11;
                        }
                        final float var10 = 4.8828125E-4f;
                        final double exactPlayerZ2 = this.Ñ¢á + p_180447_1_;
                        double dc = this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà + (this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ - this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà) * p_180447_1_ + exactPlayerZ2 * 0.029999999329447746;
                        double cdx = this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á + (this.HorizonCode_Horizon_È.ÇŽá€().Ê - this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á) * p_180447_1_;
                        final int cdz = MathHelper.Ý(dc / 2048.0);
                        final int var12 = MathHelper.Ý(cdx / 2048.0);
                        dc -= cdz * 2048;
                        cdx -= var12 * 2048;
                        float var13 = this.£à.£à.Ó() - var4 + 0.33f;
                        var13 += this.HorizonCode_Horizon_È.ŠÄ.¥Æ * 128.0f;
                        final float var14 = (float)(dc * 4.8828125E-4);
                        final float var15 = (float)(cdx * 4.8828125E-4);
                        var8.Â();
                        var8.HorizonCode_Horizon_È(exactPlayerX, var9, exactPlayerY, 0.8f);
                        for (int var16 = -256; var16 < 256; var16 += 32) {
                            for (int var17 = -256; var17 < 256; var17 += 32) {
                                var8.HorizonCode_Horizon_È(var16 + 0, var13, var17 + 32, (var16 + 0) * 4.8828125E-4f + var14, (var17 + 32) * 4.8828125E-4f + var15);
                                var8.HorizonCode_Horizon_È(var16 + 32, var13, var17 + 32, (var16 + 32) * 4.8828125E-4f + var14, (var17 + 32) * 4.8828125E-4f + var15);
                                var8.HorizonCode_Horizon_È(var16 + 32, var13, var17 + 0, (var16 + 32) * 4.8828125E-4f + var14, (var17 + 0) * 4.8828125E-4f + var15);
                                var8.HorizonCode_Horizon_È(var16 + 0, var13, var17 + 0, (var16 + 0) * 4.8828125E-4f + var14, (var17 + 0) * 4.8828125E-4f + var15);
                            }
                        }
                        var7.Â();
                        GL11.glEndList();
                        this.£Ï = false;
                        this.Ø­á = this.Ñ¢á;
                        this.ˆÉ = this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà;
                        this.Ï­Ï­Ï = this.HorizonCode_Horizon_È.ÇŽá€().ŠÄ;
                        this.£Â = this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á;
                    }
                    final Entity entityliving2 = this.HorizonCode_Horizon_È.ÇŽá€();
                    final double exactPlayerX2 = entityliving2.áŒŠà + (entityliving2.ŒÏ - entityliving2.áŒŠà) * partialTicks2;
                    final double exactPlayerY2 = entityliving2.ŠÄ + (entityliving2.Çªà¢ - entityliving2.ŠÄ) * partialTicks2;
                    final double exactPlayerZ2 = entityliving2.Ñ¢á + (entityliving2.Ê - entityliving2.Ñ¢á) * partialTicks2;
                    double dc = this.Ñ¢á - this.Ø­á + partialTicks2;
                    final float cdx2 = (float)(exactPlayerX2 - this.ˆÉ + dc * 0.03);
                    final float cdy = (float)(exactPlayerY2 - this.Ï­Ï­Ï);
                    final float cdz2 = (float)(exactPlayerZ2 - this.£Â);
                    GlStateManager.Â(-cdx2, -cdy, -cdz2);
                    GlStateManager.ˆÏ­(this.Œ);
                    GlStateManager.Â(cdx2, cdy, cdz2);
                    GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.ÂµÈ();
                    GlStateManager.Å();
                }
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final double p_72721_1_, final double p_72721_3_, final double p_72721_5_, final float p_72721_7_) {
        return false;
    }
    
    private void Ý(float p_180445_1_, final int p_180445_2_) {
        final float partialTicks = p_180445_1_;
        p_180445_1_ = 0.0f;
        GlStateManager.£à();
        final float var3 = (float)(this.HorizonCode_Horizon_È.ÇŽá€().ÇŽá€ + (this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢ - this.HorizonCode_Horizon_È.ÇŽá€().ÇŽá€) * p_180445_1_);
        final Tessellator var4 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var5 = var4.Ý();
        final float var6 = 12.0f;
        final float var7 = 4.0f;
        final double var8 = this.Ñ¢á + p_180445_1_;
        double var9 = (this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà + (this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ - this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà) * p_180445_1_ + var8 * 0.029999999329447746) / 12.0;
        double var10 = (this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á + (this.HorizonCode_Horizon_È.ÇŽá€().Ê - this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á) * p_180445_1_) / 12.0 + 0.33000001311302185;
        float var11 = this.£à.£à.Ó() - var3 + 0.33f;
        var11 += this.HorizonCode_Horizon_È.ŠÄ.¥Æ * 128.0f;
        final int var12 = MathHelper.Ý(var9 / 2048.0);
        final int var13 = MathHelper.Ý(var10 / 2048.0);
        var9 -= var12 * 2048;
        var10 -= var13 * 2048;
        this.£á.HorizonCode_Horizon_È(RenderGlobal.ÂµÈ);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        final Vec3 var14 = this.£à.Âµá€(p_180445_1_);
        float var15 = (float)var14.HorizonCode_Horizon_È;
        float var16 = (float)var14.Â;
        float var17 = (float)var14.Ý;
        if (p_180445_2_ != 2) {
            final float var18 = (var15 * 30.0f + var16 * 59.0f + var17 * 11.0f) / 100.0f;
            final float var19 = (var15 * 30.0f + var16 * 70.0f) / 100.0f;
            final float var20 = (var15 * 30.0f + var17 * 70.0f) / 100.0f;
            var15 = var18;
            var16 = var19;
            var17 = var20;
        }
        final float var18 = 0.00390625f;
        final float var19 = MathHelper.Ý(var9) * 0.00390625f;
        final float var20 = MathHelper.Ý(var10) * 0.00390625f;
        final float var21 = (float)(var9 - MathHelper.Ý(var9));
        final float var22 = (float)(var10 - MathHelper.Ý(var10));
        final boolean var23 = true;
        final boolean var24 = true;
        final float var25 = 9.765625E-4f;
        GlStateManager.HorizonCode_Horizon_È(12.0f, 1.0f, 12.0f);
        for (int var26 = 0; var26 < 2; ++var26) {
            if (var26 == 0) {
                GL11.glColorMask(false, false, false, false);
            }
            else {
                switch (p_180445_2_) {
                    case 0: {
                        GL11.glColorMask(false, true, true, true);
                        break;
                    }
                    case 1: {
                        GL11.glColorMask(true, false, false, true);
                        break;
                    }
                    case 2: {
                        GL11.glColorMask(true, true, true, true);
                        break;
                    }
                }
            }
            if (!this.£Ï || this.Ñ¢á >= this.Ø­á + 20) {
                GL11.glNewList(this.Œ, 4864);
                for (int entityliving = -3; entityliving <= 4; ++entityliving) {
                    for (int exactPlayerX = -3; exactPlayerX <= 4; ++exactPlayerX) {
                        var5.Â();
                        final float var27 = entityliving * 8;
                        final float exactPlayerY = exactPlayerX * 8;
                        final float var28 = var27 - var21;
                        final float exactPlayerZ = exactPlayerY - var22;
                        if (var11 > -5.0f) {
                            var5.HorizonCode_Horizon_È(var15 * 0.7f, var16 * 0.7f, var17 * 0.7f, 0.8f);
                            var5.Ý(0.0f, -1.0f, 0.0f);
                            var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 0.0f, exactPlayerZ + 8.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 0.0f, exactPlayerZ + 8.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 0.0f, exactPlayerZ + 0.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 0.0f, exactPlayerZ + 0.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                        }
                        if (var11 <= 5.0f) {
                            var5.HorizonCode_Horizon_È(var15, var16, var17, 0.8f);
                            var5.Ý(0.0f, 1.0f, 0.0f);
                            var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 4.0f - 9.765625E-4f, exactPlayerZ + 8.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 4.0f - 9.765625E-4f, exactPlayerZ + 8.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 4.0f - 9.765625E-4f, exactPlayerZ + 0.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                            var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 4.0f - 9.765625E-4f, exactPlayerZ + 0.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                        }
                        var5.HorizonCode_Horizon_È(var15 * 0.9f, var16 * 0.9f, var17 * 0.9f, 0.8f);
                        if (entityliving > -1) {
                            var5.Ý(-1.0f, 0.0f, 0.0f);
                            for (int var29 = 0; var29 < 8; ++var29) {
                                var5.HorizonCode_Horizon_È(var28 + var29 + 0.0f, var11 + 0.0f, exactPlayerZ + 8.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 0.0f, var11 + 4.0f, exactPlayerZ + 8.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 0.0f, var11 + 4.0f, exactPlayerZ + 0.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 0.0f, var11 + 0.0f, exactPlayerZ + 0.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                            }
                        }
                        if (entityliving <= 1) {
                            var5.Ý(1.0f, 0.0f, 0.0f);
                            for (int var29 = 0; var29 < 8; ++var29) {
                                var5.HorizonCode_Horizon_È(var28 + var29 + 1.0f - 9.765625E-4f, var11 + 0.0f, exactPlayerZ + 8.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 1.0f - 9.765625E-4f, var11 + 4.0f, exactPlayerZ + 8.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 8.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 1.0f - 9.765625E-4f, var11 + 4.0f, exactPlayerZ + 0.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + var29 + 1.0f - 9.765625E-4f, var11 + 0.0f, exactPlayerZ + 0.0f, (var27 + var29 + 0.5f) * 0.00390625f + var19, (exactPlayerY + 0.0f) * 0.00390625f + var20);
                            }
                        }
                        var5.HorizonCode_Horizon_È(var15 * 0.8f, var16 * 0.8f, var17 * 0.8f, 0.8f);
                        if (exactPlayerX > -1) {
                            var5.Ý(0.0f, 0.0f, -1.0f);
                            for (int var29 = 0; var29 < 8; ++var29) {
                                var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 4.0f, exactPlayerZ + var29 + 0.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 4.0f, exactPlayerZ + var29 + 0.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 0.0f, exactPlayerZ + var29 + 0.0f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 0.0f, exactPlayerZ + var29 + 0.0f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                            }
                        }
                        if (exactPlayerX <= 1) {
                            var5.Ý(0.0f, 0.0f, 1.0f);
                            for (int var29 = 0; var29 < 8; ++var29) {
                                var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 4.0f, exactPlayerZ + var29 + 1.0f - 9.765625E-4f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 4.0f, exactPlayerZ + var29 + 1.0f - 9.765625E-4f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 8.0f, var11 + 0.0f, exactPlayerZ + var29 + 1.0f - 9.765625E-4f, (var27 + 8.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                                var5.HorizonCode_Horizon_È(var28 + 0.0f, var11 + 0.0f, exactPlayerZ + var29 + 1.0f - 9.765625E-4f, (var27 + 0.0f) * 0.00390625f + var19, (exactPlayerY + var29 + 0.5f) * 0.00390625f + var20);
                            }
                        }
                        var4.Â();
                    }
                }
                GL11.glEndList();
                this.£Ï = true;
                this.Ø­á = this.Ñ¢á;
                this.ˆÉ = this.HorizonCode_Horizon_È.ÇŽá€().áŒŠà;
                this.Ï­Ï­Ï = this.HorizonCode_Horizon_È.ÇŽá€().ŠÄ;
                this.£Â = this.HorizonCode_Horizon_È.ÇŽá€().Ñ¢á;
            }
            final Entity var30 = this.HorizonCode_Horizon_È.ÇŽá€();
            final double var31 = var30.áŒŠà + (var30.ŒÏ - var30.áŒŠà) * partialTicks;
            final double var32 = var30.ŠÄ + (var30.Çªà¢ - var30.ŠÄ) * partialTicks;
            final double var33 = var30.Ñ¢á + (var30.Ê - var30.Ñ¢á) * partialTicks;
            final double dc = this.Ñ¢á - this.Ø­á + partialTicks;
            final float cdx = (float)(var31 - this.ˆÉ + dc * 0.03);
            final float cdy = (float)(var32 - this.Ï­Ï­Ï);
            final float cdz = (float)(var33 - this.£Â);
            GlStateManager.Â(-cdx / 12.0f, -cdy, -cdz / 12.0f);
            GlStateManager.ˆÏ­(this.Œ);
            GlStateManager.Â(cdx / 12.0f, cdy, cdz / 12.0f);
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
        GlStateManager.Å();
    }
    
    public void HorizonCode_Horizon_È(final long p_174967_1_) {
        this.Ø­áŒŠá |= this.È.HorizonCode_Horizon_È(p_174967_1_);
        if (this.à.size() > 0) {
            final Iterator countUpdated = this.à.iterator();
            while (countUpdated.hasNext()) {
                final RenderChunk updatesPerFrame = countUpdated.next();
                if (!this.È.HorizonCode_Horizon_È(updatesPerFrame)) {
                    break;
                }
                updatesPerFrame.HorizonCode_Horizon_È(false);
                countUpdated.remove();
                this.µà.remove(updatesPerFrame);
                this.Ó.remove(updatesPerFrame);
            }
        }
        if (this.Ó.size() > 0) {
            final Iterator countUpdated = this.Ó.iterator();
            if (countUpdated.hasNext()) {
                final RenderChunk updatesPerFrame = countUpdated.next();
                if (this.È.Ý(updatesPerFrame)) {
                    countUpdated.remove();
                }
            }
        }
        int var8 = 0;
        int var9 = Config.ˆà();
        final int maxUpdatesPerFrame = var9 * 2;
        final Iterator var10 = this.µà.iterator();
        while (var10.hasNext()) {
            final RenderChunk var11 = var10.next();
            if (!this.È.HorizonCode_Horizon_È(var11)) {
                break;
            }
            var11.HorizonCode_Horizon_È(false);
            var10.remove();
            if (var11.à().HorizonCode_Horizon_È() && var9 < maxUpdatesPerFrame) {
                ++var9;
            }
            if (++var8 >= var9) {
                break;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_180449_1_, final float p_180449_2_) {
        final Tessellator var3 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var4 = var3.Ý();
        final WorldBorder var5 = this.£à.áŠ();
        final double var6 = this.HorizonCode_Horizon_È.ŠÄ.Ý * 16;
        if (p_180449_1_.ŒÏ >= var5.Ó() - var6 || p_180449_1_.ŒÏ <= var5.Ø­áŒŠá() + var6 || p_180449_1_.Ê >= var5.à() - var6 || p_180449_1_.Ê <= var5.Âµá€() + var6) {
            double var7 = 1.0 - var5.HorizonCode_Horizon_È(p_180449_1_) / var6;
            var7 = Math.pow(var7, 4.0);
            final double var8 = p_180449_1_.áˆºáˆºÈ + (p_180449_1_.ŒÏ - p_180449_1_.áˆºáˆºÈ) * p_180449_2_;
            final double var9 = p_180449_1_.ÇŽá€ + (p_180449_1_.Çªà¢ - p_180449_1_.ÇŽá€) * p_180449_2_;
            final double var10 = p_180449_1_.Ï + (p_180449_1_.Ê - p_180449_1_.Ï) * p_180449_2_;
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 1, 1, 0);
            this.£á.HorizonCode_Horizon_È(RenderGlobal.ˆÏ­);
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.Çªà¢();
            final int var11 = var5.Ý().HorizonCode_Horizon_È();
            final float var12 = (var11 >> 16 & 0xFF) / 255.0f;
            final float var13 = (var11 >> 8 & 0xFF) / 255.0f;
            final float var14 = (var11 & 0xFF) / 255.0f;
            GlStateManager.Ý(var12, var13, var14, (float)var7);
            GlStateManager.HorizonCode_Horizon_È(-3.0f, -3.0f);
            GlStateManager.µà();
            GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.£à();
            final float var15 = Minecraft.áƒ() % 3000L / 3000.0f;
            final float var16 = 0.0f;
            final float var17 = 0.0f;
            final float var18 = 128.0f;
            var4.Â();
            var4.Ý(-var8, -var9, -var10);
            var4.Ý();
            double var19 = Math.max(MathHelper.Ý(var10 - var6), var5.Âµá€());
            double var20 = Math.min(MathHelper.Ó(var10 + var6), var5.à());
            if (var8 > var5.Ó() - var6) {
                float var21 = 0.0f;
                for (double var22 = var19; var22 < var20; ++var22, var21 += 0.5f) {
                    final double var23 = Math.min(1.0, var20 - var22);
                    final float var24 = (float)var23 * 0.5f;
                    var4.HorizonCode_Horizon_È(var5.Ó(), 256.0, var22, var15 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var5.Ó(), 256.0, var22 + var23, var15 + var24 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var5.Ó(), 0.0, var22 + var23, var15 + var24 + var21, var15 + 128.0f);
                    var4.HorizonCode_Horizon_È(var5.Ó(), 0.0, var22, var15 + var21, var15 + 128.0f);
                }
            }
            if (var8 < var5.Ø­áŒŠá() + var6) {
                float var21 = 0.0f;
                for (double var22 = var19; var22 < var20; ++var22, var21 += 0.5f) {
                    final double var23 = Math.min(1.0, var20 - var22);
                    final float var24 = (float)var23 * 0.5f;
                    var4.HorizonCode_Horizon_È(var5.Ø­áŒŠá(), 256.0, var22, var15 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var5.Ø­áŒŠá(), 256.0, var22 + var23, var15 + var24 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var5.Ø­áŒŠá(), 0.0, var22 + var23, var15 + var24 + var21, var15 + 128.0f);
                    var4.HorizonCode_Horizon_È(var5.Ø­áŒŠá(), 0.0, var22, var15 + var21, var15 + 128.0f);
                }
            }
            var19 = Math.max(MathHelper.Ý(var8 - var6), var5.Ø­áŒŠá());
            var20 = Math.min(MathHelper.Ó(var8 + var6), var5.Ó());
            if (var10 > var5.à() - var6) {
                float var21 = 0.0f;
                for (double var22 = var19; var22 < var20; ++var22, var21 += 0.5f) {
                    final double var23 = Math.min(1.0, var20 - var22);
                    final float var24 = (float)var23 * 0.5f;
                    var4.HorizonCode_Horizon_È(var22, 256.0, var5.à(), var15 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var22 + var23, 256.0, var5.à(), var15 + var24 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var22 + var23, 0.0, var5.à(), var15 + var24 + var21, var15 + 128.0f);
                    var4.HorizonCode_Horizon_È(var22, 0.0, var5.à(), var15 + var21, var15 + 128.0f);
                }
            }
            if (var10 < var5.Âµá€() + var6) {
                float var21 = 0.0f;
                for (double var22 = var19; var22 < var20; ++var22, var21 += 0.5f) {
                    final double var23 = Math.min(1.0, var20 - var22);
                    final float var24 = (float)var23 * 0.5f;
                    var4.HorizonCode_Horizon_È(var22, 256.0, var5.Âµá€(), var15 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var22 + var23, 256.0, var5.Âµá€(), var15 + var24 + var21, var15 + 0.0f);
                    var4.HorizonCode_Horizon_È(var22 + var23, 0.0, var5.Âµá€(), var15 + var24 + var21, var15 + 128.0f);
                    var4.HorizonCode_Horizon_È(var22, 0.0, var5.Âµá€(), var15 + var21, var15 + 128.0f);
                }
            }
            var3.Â();
            var4.Ý(0.0, 0.0, 0.0);
            GlStateManager.Å();
            GlStateManager.Ý();
            GlStateManager.HorizonCode_Horizon_È(0.0f, 0.0f);
            GlStateManager.ˆà();
            GlStateManager.Ø­áŒŠá();
            GlStateManager.ÂµÈ();
            GlStateManager.Ê();
            GlStateManager.HorizonCode_Horizon_È(true);
        }
    }
    
    private void µà() {
        GlStateManager.HorizonCode_Horizon_È(774, 768, 1, 0);
        GlStateManager.á();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.HorizonCode_Horizon_È(-3.0f, -3.0f);
        GlStateManager.µà();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.Ø­áŒŠá();
        GlStateManager.Çªà¢();
    }
    
    private void ˆà() {
        GlStateManager.Ý();
        GlStateManager.HorizonCode_Horizon_È(0.0f, 0.0f);
        GlStateManager.ˆà();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.Ê();
    }
    
    public void HorizonCode_Horizon_È(final Tessellator p_174981_1_, final WorldRenderer p_174981_2_, final Entity p_174981_3_, final float p_174981_4_) {
        final double var5 = p_174981_3_.áˆºáˆºÈ + (p_174981_3_.ŒÏ - p_174981_3_.áˆºáˆºÈ) * p_174981_4_;
        final double var6 = p_174981_3_.ÇŽá€ + (p_174981_3_.Çªà¢ - p_174981_3_.ÇŽá€) * p_174981_4_;
        final double var7 = p_174981_3_.Ï + (p_174981_3_.Ê - p_174981_3_.Ï) * p_174981_4_;
        if (!this.Â.isEmpty()) {
            this.£á.HorizonCode_Horizon_È(TextureMap.à);
            this.µà();
            p_174981_2_.Â();
            p_174981_2_.HorizonCode_Horizon_È(DefaultVertexFormats.HorizonCode_Horizon_È);
            p_174981_2_.Ý(-var5, -var6, -var7);
            p_174981_2_.Ý();
            final Iterator var8 = this.Â.values().iterator();
            while (var8.hasNext()) {
                final DestroyBlockProgress var9 = var8.next();
                final BlockPos var10 = var9.HorizonCode_Horizon_È();
                final double var11 = var10.HorizonCode_Horizon_È() - var5;
                final double var12 = var10.Â() - var6;
                final double var13 = var10.Ý() - var7;
                final Block var14 = this.£à.Â(var10).Ý();
                if (!(var14 instanceof BlockChest) && !(var14 instanceof BlockEnderChest) && !(var14 instanceof BlockSign) && !(var14 instanceof BlockSkull)) {
                    if (var11 * var11 + var12 * var12 + var13 * var13 > 1024.0) {
                        var8.remove();
                    }
                    else {
                        final IBlockState var15 = this.£à.Â(var10);
                        if (var15.Ý().Ó() == Material.HorizonCode_Horizon_È) {
                            continue;
                        }
                        final int var16 = var9.Â();
                        final TextureAtlasSprite var17 = this.Çªà¢[var16];
                        final BlockRendererDispatcher var18 = this.HorizonCode_Horizon_È.Ô();
                        var18.HorizonCode_Horizon_È(var15, var10, var17, this.£à);
                    }
                }
            }
            p_174981_1_.Â();
            p_174981_2_.Ý(0.0, 0.0, 0.0);
            this.ˆà();
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_72731_1_, final MovingObjectPosition p_72731_2_, final int p_72731_3_, final float p_72731_4_) {
        if (p_72731_3_ == 0 && p_72731_2_.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            GlStateManager.Ý(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GlStateManager.Æ();
            GlStateManager.HorizonCode_Horizon_È(false);
            final float var5 = 0.002f;
            final BlockPos var6 = p_72731_2_.HorizonCode_Horizon_È();
            final Block var7 = this.£à.Â(var6).Ý();
            if (var7.Ó() != Material.HorizonCode_Horizon_È && this.£à.áŠ().HorizonCode_Horizon_È(var6)) {
                var7.Ý((IBlockAccess)this.£à, var6);
                final double var8 = p_72731_1_.áˆºáˆºÈ + (p_72731_1_.ŒÏ - p_72731_1_.áˆºáˆºÈ) * p_72731_4_;
                final double var9 = p_72731_1_.ÇŽá€ + (p_72731_1_.Çªà¢ - p_72731_1_.ÇŽá€) * p_72731_4_;
                final double var10 = p_72731_1_.Ï + (p_72731_1_.Ê - p_72731_1_.Ï) * p_72731_4_;
                HorizonCode_Horizon_È(var7.Ý(this.£à, var6).Â(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).Ý(-var8, -var9, -var10), -1);
            }
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.µÕ();
            GlStateManager.ÂµÈ();
        }
    }
    
    public static void HorizonCode_Horizon_È(final AxisAlignedBB p_147590_0_, final int p_147590_1_) {
        final Tessellator var2 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var3 = var2.Ý();
        var3.HorizonCode_Horizon_È(3);
        if (p_147590_1_ != -1) {
            var3.Ý(p_147590_1_);
        }
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Â, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Â, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Â, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Â, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Â, p_147590_0_.Ý);
        var2.Â();
        var3.HorizonCode_Horizon_È(3);
        if (p_147590_1_ != -1) {
            var3.Ý(p_147590_1_);
        }
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Âµá€, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Âµá€, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Âµá€, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Âµá€, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Âµá€, p_147590_0_.Ý);
        var2.Â();
        var3.HorizonCode_Horizon_È(1);
        if (p_147590_1_ != -1) {
            var3.Ý(p_147590_1_);
        }
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Â, p_147590_0_.Ý);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Âµá€, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Â, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Âµá€, p_147590_0_.Ý);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Â, p_147590_0_.Ó);
        var3.Â(p_147590_0_.Ø­áŒŠá, p_147590_0_.Âµá€, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Â, p_147590_0_.Ó);
        var3.Â(p_147590_0_.HorizonCode_Horizon_È, p_147590_0_.Âµá€, p_147590_0_.Ó);
        var2.Â();
    }
    
    private void Â(final int p_72725_1_, final int p_72725_2_, final int p_72725_3_, final int p_72725_4_, final int p_72725_5_, final int p_72725_6_) {
        this.¥Æ.HorizonCode_Horizon_È(p_72725_1_, p_72725_2_, p_72725_3_, p_72725_4_, p_72725_5_, p_72725_6_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos pos) {
        final int var2 = pos.HorizonCode_Horizon_È();
        final int var3 = pos.Â();
        final int var4 = pos.Ý();
        this.Â(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
    }
    
    @Override
    public void Â(final BlockPos pos) {
        final int var2 = pos.HorizonCode_Horizon_È();
        final int var3 = pos.Â();
        final int var4 = pos.Ý();
        this.Â(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.Â(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_174961_1_, final BlockPos p_174961_2_) {
        final ISound var3 = this.ŒÏ.get(p_174961_2_);
        if (var3 != null) {
            this.HorizonCode_Horizon_È.£ÂµÄ().Â(var3);
            this.ŒÏ.remove(p_174961_2_);
        }
        if (p_174961_1_ != null) {
            final ItemRecord var4 = ItemRecord.Ø­áŒŠá(p_174961_1_);
            if (var4 != null) {
                this.HorizonCode_Horizon_È.Šáƒ.HorizonCode_Horizon_È(var4.ˆà());
            }
            ResourceLocation_1975012498 resource = null;
            if (Reflector.£áƒ.Â() && var4 != null) {
                resource = (ResourceLocation_1975012498)Reflector.Ó(var4, Reflector.£áƒ, p_174961_1_);
            }
            if (resource == null) {
                resource = new ResourceLocation_1975012498(p_174961_1_);
            }
            final PositionedSoundRecord var5 = PositionedSoundRecord.HorizonCode_Horizon_È(resource, p_174961_2_.HorizonCode_Horizon_È(), p_174961_2_.Â(), p_174961_2_.Ý());
            this.ŒÏ.put(p_174961_2_, var5);
            this.HorizonCode_Horizon_È.£ÂµÄ().HorizonCode_Horizon_È(var5);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String soundName, final double x, final double y, final double z, final float volume, final float pitch) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer except, final String soundName, final double x, final double y, final double z, final float volume, final float pitch) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_180442_1_, final boolean p_180442_2_, final double p_180442_3_, final double p_180442_5_, final double p_180442_7_, final double p_180442_9_, final double p_180442_11_, final double p_180442_13_, final int... p_180442_15_) {
        try {
            this.Â(p_180442_1_, p_180442_2_, p_180442_3_, p_180442_5_, p_180442_7_, p_180442_9_, p_180442_11_, p_180442_13_, p_180442_15_);
        }
        catch (Throwable var18) {
            final CrashReport var17 = CrashReport.HorizonCode_Horizon_È(var18, "Exception while adding particle");
            final CrashReportCategory var19 = var17.HorizonCode_Horizon_È("Particle being added");
            var19.HorizonCode_Horizon_È("ID", p_180442_1_);
            if (p_180442_15_ != null) {
                var19.HorizonCode_Horizon_È("Parameters", p_180442_15_);
            }
            var19.HorizonCode_Horizon_È("Position", new Callable() {
                private static final String Â = "CL_00000955";
                
                public String HorizonCode_Horizon_È() {
                    return CrashReportCategory.HorizonCode_Horizon_È(p_180442_3_, p_180442_5_, p_180442_7_);
                }
            });
            throw new ReportedException(var17);
        }
    }
    
    private void HorizonCode_Horizon_È(final EnumParticleTypes p_174972_1_, final double p_174972_2_, final double p_174972_4_, final double p_174972_6_, final double p_174972_8_, final double p_174972_10_, final double p_174972_12_, final int... p_174972_14_) {
        this.HorizonCode_Horizon_È(p_174972_1_.Ý(), p_174972_1_.Âµá€(), p_174972_2_, p_174972_4_, p_174972_6_, p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
    }
    
    private EntityFX Â(final int p_174974_1_, final boolean p_174974_2_, final double p_174974_3_, final double p_174974_5_, final double p_174974_7_, final double p_174974_9_, final double p_174974_11_, final double p_174974_13_, final int... p_174974_15_) {
        if (this.HorizonCode_Horizon_È == null || this.HorizonCode_Horizon_È.ÇŽá€() == null || this.HorizonCode_Horizon_È.Å == null) {
            return null;
        }
        int var16 = this.HorizonCode_Horizon_È.ŠÄ.áˆºá;
        if (var16 == 1 && this.£à.Å.nextInt(3) == 0) {
            var16 = 2;
        }
        final double var17 = this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ - p_174974_3_;
        final double var18 = this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢ - p_174974_5_;
        final double var19 = this.HorizonCode_Horizon_È.ÇŽá€().Ê - p_174974_7_;
        if (p_174974_1_ == EnumParticleTypes.Ý.Ý() && !Config.É()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.Â.Ý() && !Config.É()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.HorizonCode_Horizon_È.Ý() && !Config.É()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.Ø.Ý() && !Config.à¢()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.áŒŠÆ.Ý() && !Config.Õ()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.á.Ý() && !Config.á€()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.ˆÏ­.Ý() && !Config.á€()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.£à.Ý() && !Config.Âµà()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.µà.Ý() && !Config.Âµà()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.£á.Ý() && !Config.Âµà()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.Å.Ý() && !Config.Âµà()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.ˆà.Ý() && !Config.Âµà()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.áŒŠà.Ý() && !Config.Çªà¢()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.Ñ¢á.Ý() && !Config.áƒ()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.ÇŽÉ.Ý() && !Config.ÇŽÕ()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.¥Æ.Ý() && !Config.Ï­Ï­Ï()) {
            return null;
        }
        if (p_174974_1_ == EnumParticleTypes.Ø­à.Ý() && !Config.Ï­Ï­Ï()) {
            return null;
        }
        if (p_174974_2_) {
            return this.HorizonCode_Horizon_È.Å.HorizonCode_Horizon_È(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
        }
        final double var20 = 16.0;
        double maxDistSq = 256.0;
        if (p_174974_1_ == EnumParticleTypes.áˆºÑ¢Õ.Ý()) {
            maxDistSq = 38416.0;
        }
        if (var17 * var17 + var18 * var18 + var19 * var19 > maxDistSq) {
            return null;
        }
        if (var16 > 1) {
            return null;
        }
        final EntityFX entityFx = this.HorizonCode_Horizon_È.Å.HorizonCode_Horizon_È(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
        if (p_174974_1_ == EnumParticleTypes.Âµá€.Ý()) {
            CustomColorizer.Â(entityFx, this.£à, p_174974_3_, p_174974_5_, p_174974_7_);
        }
        if (p_174974_1_ == EnumParticleTypes.Ó.Ý()) {
            CustomColorizer.Â(entityFx, this.£à, p_174974_3_, p_174974_5_, p_174974_7_);
        }
        if (p_174974_1_ == EnumParticleTypes.¥à.Ý()) {
            CustomColorizer.Â(entityFx, this.£à, p_174974_3_, p_174974_5_, p_174974_7_);
        }
        if (p_174974_1_ == EnumParticleTypes.Šáƒ.Ý()) {
            CustomColorizer.Â(entityFx);
        }
        if (p_174974_1_ == EnumParticleTypes.áŒŠà.Ý()) {
            CustomColorizer.HorizonCode_Horizon_È(entityFx);
        }
        if (p_174974_1_ == EnumParticleTypes.ÇŽÉ.Ý()) {
            CustomColorizer.HorizonCode_Horizon_È(entityFx, this.£à, p_174974_3_, p_174974_5_, p_174974_7_);
        }
        return entityFx;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        RandomMobs.HorizonCode_Horizon_È(entityIn);
    }
    
    @Override
    public void Â(final Entity entityIn) {
    }
    
    public void áŒŠÆ() {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_180440_1_, final BlockPos p_180440_2_, final int p_180440_3_) {
        switch (p_180440_1_) {
            case 1013:
            case 1018: {
                if (this.HorizonCode_Horizon_È.ÇŽá€() == null) {
                    break;
                }
                final double var4 = p_180440_2_.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ;
                final double var5 = p_180440_2_.Â() - this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢;
                final double var6 = p_180440_2_.Ý() - this.HorizonCode_Horizon_È.ÇŽá€().Ê;
                final double var7 = Math.sqrt(var4 * var4 + var5 * var5 + var6 * var6);
                double var8 = this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ;
                double var9 = this.HorizonCode_Horizon_È.ÇŽá€().Çªà¢;
                double var10 = this.HorizonCode_Horizon_È.ÇŽá€().Ê;
                if (var7 > 0.0) {
                    var8 += var4 / var7 * 2.0;
                    var9 += var5 / var7 * 2.0;
                    var10 += var6 / var7 * 2.0;
                }
                if (p_180440_1_ == 1013) {
                    this.£à.HorizonCode_Horizon_È(var8, var9, var10, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                this.£à.HorizonCode_Horizon_È(var8, var9, var10, "mob.enderdragon.end", 5.0f, 1.0f, false);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer p_180439_1_, final int p_180439_2_, final BlockPos p_180439_3_, final int p_180439_4_) {
        final Random var5 = this.£à.Å;
        switch (p_180439_2_) {
            case 1000: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.door_open", 1.0f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.fizz", 0.5f, 2.6f + (var5.nextFloat() - var5.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item_1028566121.HorizonCode_Horizon_È(p_180439_4_) instanceof ItemRecord) {
                    this.£à.HorizonCode_Horizon_È(p_180439_3_, "records." + ((ItemRecord)Item_1028566121.HorizonCode_Horizon_È(p_180439_4_)).à);
                    break;
                }
                this.£à.HorizonCode_Horizon_È(p_180439_3_, (String)null);
                break;
            }
            case 1006: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.door_close", 1.0f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.ghast.charge", 10.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.ghast.fireball", 10.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.ghast.fireball", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.zombie.wood", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.zombie.metal", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.zombie.woodbreak", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.wither.shoot", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.bat.takeoff", 0.05f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.zombie.infect", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "mob.zombie.unfect", 2.0f, (var5.nextFloat() - var5.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.anvil_break", 1.0f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.anvil_use", 1.0f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "random.anvil_land", 0.3f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                final int var6 = p_180439_4_ % 3 - 1;
                final int var7 = p_180439_4_ / 3 % 3 - 1;
                final double var8 = p_180439_3_.HorizonCode_Horizon_È() + var6 * 0.6 + 0.5;
                final double var9 = p_180439_3_.Â() + 0.5;
                final double var10 = p_180439_3_.Ý() + var7 * 0.6 + 0.5;
                for (int var11 = 0; var11 < 10; ++var11) {
                    final double var12 = var5.nextDouble() * 0.2 + 0.01;
                    final double var13 = var8 + var6 * 0.01 + (var5.nextDouble() - 0.5) * var7 * 0.5;
                    final double var14 = var9 + (var5.nextDouble() - 0.5) * 0.5;
                    final double var15 = var10 + var7 * 0.01 + (var5.nextDouble() - 0.5) * var6 * 0.5;
                    final double var16 = var6 * var12 + var5.nextGaussian() * 0.01;
                    final double var17 = -0.03 + var5.nextGaussian() * 0.01;
                    final double var18 = var7 * var12 + var5.nextGaussian() * 0.01;
                    this.HorizonCode_Horizon_È(EnumParticleTypes.á, var13, var14, var15, var16, var17, var18, new int[0]);
                }
            }
            case 2001: {
                final Block var19 = Block.HorizonCode_Horizon_È(p_180439_4_ & 0xFFF);
                if (var19.Ó() != Material.HorizonCode_Horizon_È) {
                    this.HorizonCode_Horizon_È.£ÂµÄ().HorizonCode_Horizon_È(new PositionedSoundRecord(new ResourceLocation_1975012498(var19.ˆá.HorizonCode_Horizon_È()), (var19.ˆá.Ø­áŒŠá() + 1.0f) / 2.0f, var19.ˆá.Âµá€() * 0.8f, p_180439_3_.HorizonCode_Horizon_È() + 0.5f, p_180439_3_.Â() + 0.5f, p_180439_3_.Ý() + 0.5f));
                }
                this.HorizonCode_Horizon_È.Å.HorizonCode_Horizon_È(p_180439_3_, var19.Ý(p_180439_4_ >> 12 & 0xFF));
                break;
            }
            case 2002: {
                final double var20 = p_180439_3_.HorizonCode_Horizon_È();
                final double var8 = p_180439_3_.Â();
                final double var9 = p_180439_3_.Ý();
                for (int var21 = 0; var21 < 8; ++var21) {
                    this.HorizonCode_Horizon_È(EnumParticleTypes.Õ, var20, var8, var9, var5.nextGaussian() * 0.15, var5.nextDouble() * 0.2, var5.nextGaussian() * 0.15, Item_1028566121.HorizonCode_Horizon_È(Items.µÂ), p_180439_4_);
                }
                int var21 = Items.µÂ.à(p_180439_4_);
                final float var22 = (var21 >> 16 & 0xFF) / 255.0f;
                final float var23 = (var21 >> 8 & 0xFF) / 255.0f;
                final float var24 = (var21 >> 0 & 0xFF) / 255.0f;
                EnumParticleTypes var25 = EnumParticleTypes.£á;
                if (Items.µÂ.Ø(p_180439_4_)) {
                    var25 = EnumParticleTypes.Å;
                }
                for (int var26 = 0; var26 < 100; ++var26) {
                    final double var27 = var5.nextDouble() * 4.0;
                    final double var28 = var5.nextDouble() * 3.141592653589793 * 2.0;
                    final double var29 = Math.cos(var28) * var27;
                    final double var14 = 0.01 + var5.nextDouble() * 0.5;
                    final double var15 = Math.sin(var28) * var27;
                    final EntityFX var30 = this.Â(var25.Ý(), var25.Âµá€(), var20 + var29 * 0.1, var8 + 0.3, var9 + var15 * 0.1, var29, var14, var15, new int[0]);
                    if (var30 != null) {
                        final float var31 = 0.75f + var5.nextFloat() * 0.25f;
                        var30.HorizonCode_Horizon_È(var22 * var31, var23 * var31, var24 * var31);
                        var30.Ý((float)var27);
                    }
                }
                this.£à.HorizonCode_Horizon_È(p_180439_3_, "game.potion.smash", 1.0f, this.£à.Å.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                final double var20 = p_180439_3_.HorizonCode_Horizon_È() + 0.5;
                final double var8 = p_180439_3_.Â();
                final double var9 = p_180439_3_.Ý() + 0.5;
                for (int var21 = 0; var21 < 8; ++var21) {
                    this.HorizonCode_Horizon_È(EnumParticleTypes.Õ, var20, var8, var9, var5.nextGaussian() * 0.15, var5.nextDouble() * 0.2, var5.nextGaussian() * 0.15, Item_1028566121.HorizonCode_Horizon_È(Items.¥áŠ));
                }
                for (double var10 = 0.0; var10 < 6.283185307179586; var10 += 0.15707963267948966) {
                    this.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var20 + Math.cos(var10) * 5.0, var8 - 0.4, var9 + Math.sin(var10) * 5.0, Math.cos(var10) * -5.0, 0.0, Math.sin(var10) * -5.0, new int[0]);
                    this.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var20 + Math.cos(var10) * 5.0, var8 - 0.4, var9 + Math.sin(var10) * 5.0, Math.cos(var10) * -7.0, 0.0, Math.sin(var10) * -7.0, new int[0]);
                }
            }
            case 2004: {
                for (int var26 = 0; var26 < 20; ++var26) {
                    final double var27 = p_180439_3_.HorizonCode_Horizon_È() + 0.5 + (this.£à.Å.nextFloat() - 0.5) * 2.0;
                    final double var28 = p_180439_3_.Â() + 0.5 + (this.£à.Å.nextFloat() - 0.5) * 2.0;
                    final double var29 = p_180439_3_.Ý() + 0.5 + (this.£à.Å.nextFloat() - 0.5) * 2.0;
                    this.£à.HorizonCode_Horizon_È(EnumParticleTypes.á, var27, var28, var29, 0.0, 0.0, 0.0, new int[0]);
                    this.£à.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var27, var28, var29, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            case 2005: {
                ItemDye.HorizonCode_Horizon_È(this.£à, p_180439_3_, p_180439_4_);
                break;
            }
        }
    }
    
    @Override
    public void Â(final int breakerId, final BlockPos pos, final int progress) {
        if (progress >= 0 && progress < 10) {
            DestroyBlockProgress var4 = this.Â.get(breakerId);
            if (var4 == null || var4.HorizonCode_Horizon_È().HorizonCode_Horizon_È() != pos.HorizonCode_Horizon_È() || var4.HorizonCode_Horizon_È().Â() != pos.Â() || var4.HorizonCode_Horizon_È().Ý() != pos.Ý()) {
                var4 = new DestroyBlockProgress(breakerId, pos);
                this.Â.put(breakerId, var4);
            }
            var4.HorizonCode_Horizon_È(progress);
            var4.Â(this.Ñ¢á);
        }
        else {
            this.Â.remove(breakerId);
        }
    }
    
    public void áˆºÑ¢Õ() {
        this.Ø­áŒŠá = true;
    }
    
    public void ÂµÈ() {
        this.Ø­á = -9999;
    }
    
    class HorizonCode_Horizon_È
    {
        final RenderChunk HorizonCode_Horizon_È;
        final EnumFacing Â;
        final Set Ý;
        final int Ø­áŒŠá;
        private static final String Ó = "CL_00002534";
        
        private HorizonCode_Horizon_È(final RenderChunk p_i46248_2_, final EnumFacing p_i46248_3_, final int p_i46248_4_) {
            this.Ý = EnumSet.noneOf(EnumFacing.class);
            this.HorizonCode_Horizon_È = p_i46248_2_;
            this.Â = p_i46248_3_;
            this.Ø­áŒŠá = p_i46248_4_;
        }
        
        HorizonCode_Horizon_È(final RenderGlobal renderGlobal, final RenderChunk p_i46249_2_, final EnumFacing p_i46249_3_, final int p_i46249_4_, final Object p_i46249_5_) {
            this(renderGlobal, p_i46249_2_, p_i46249_3_, p_i46249_4_);
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002535";
        
        static {
            HorizonCode_Horizon_È = new int[VertexFormatElement.Â.values().length];
            try {
                RenderGlobal.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                RenderGlobal.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                RenderGlobal.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
