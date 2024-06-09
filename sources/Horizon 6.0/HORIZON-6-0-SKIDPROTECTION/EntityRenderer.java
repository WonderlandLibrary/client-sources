package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Calendar;
import org.lwjgl.opengl.GLContext;
import java.util.concurrent.Callable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import java.util.List;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import java.nio.FloatBuffer;
import java.util.Random;
import org.apache.logging.log4j.Logger;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private static final Logger áŒŠÆ;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    private static final ResourceLocation_1975012498 ÂµÈ;
    public static boolean HorizonCode_Horizon_È;
    public static int Â;
    private Minecraft á;
    private final IResourceManager ˆÏ­;
    private Random £á;
    private float Å;
    public ItemRenderer Ý;
    private final MapItemRenderer £à;
    private int µà;
    private Entity ˆà;
    private MouseFilter ¥Æ;
    private MouseFilter Ø­à;
    private float µÕ;
    private float Æ;
    private float Šáƒ;
    private float Ï­Ðƒà;
    private float áŒŠà;
    private float ŠÄ;
    private float Ñ¢á;
    private float ŒÏ;
    private float Çªà¢;
    private float Ê;
    private float ÇŽÉ;
    private boolean ˆá;
    private boolean ÇŽÕ;
    private boolean É;
    private long áƒ;
    private long á€;
    private final DynamicTexture Õ;
    private final int[] à¢;
    private final ResourceLocation_1975012498 ŠÂµà;
    private boolean ¥à;
    private float Âµà;
    private float Ç;
    private int È;
    private float[] áŠ;
    private float[] ˆáŠ;
    private FloatBuffer áŒŠ;
    public float Ø­áŒŠá;
    public float Âµá€;
    public float Ó;
    private float £ÂµÄ;
    private float Ø­Âµ;
    private int Ä;
    private boolean Ñ¢Â;
    private double Ï­à;
    private double áˆºáˆºÈ;
    private double ÇŽá€;
    private ShaderGroup Ï;
    private static final ResourceLocation_1975012498[] Ô;
    public static final int à;
    private int ÇªÓ;
    private boolean áˆºÏ;
    private int ˆáƒ;
    private static final String Œ = "CL_00000947";
    private boolean £Ï;
    private World Ø­á;
    private boolean ˆÉ;
    public boolean Ø;
    private float Ï­Ï­Ï;
    private long £Â;
    private int £Ó;
    private int ˆÐƒØ­à;
    private int £Õ;
    private float Ï­Ô;
    private float Œà;
    
    static {
        áŒŠÆ = LogManager.getLogger();
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/environment/rain.png");
        ÂµÈ = new ResourceLocation_1975012498("textures/environment/snow.png");
        Ô = new ResourceLocation_1975012498[] { new ResourceLocation_1975012498("shaders/post/notch.json"), new ResourceLocation_1975012498("shaders/post/fxaa.json"), new ResourceLocation_1975012498("shaders/post/art.json"), new ResourceLocation_1975012498("shaders/post/bumpy.json"), new ResourceLocation_1975012498("shaders/post/blobs2.json"), new ResourceLocation_1975012498("shaders/post/pencil.json"), new ResourceLocation_1975012498("shaders/post/color_convolve.json"), new ResourceLocation_1975012498("shaders/post/deconverge.json"), new ResourceLocation_1975012498("shaders/post/flip.json"), new ResourceLocation_1975012498("shaders/post/invert.json"), new ResourceLocation_1975012498("shaders/post/ntsc.json"), new ResourceLocation_1975012498("shaders/post/outline.json"), new ResourceLocation_1975012498("shaders/post/phosphor.json"), new ResourceLocation_1975012498("shaders/post/scan_pincushion.json"), new ResourceLocation_1975012498("shaders/post/sobel.json"), new ResourceLocation_1975012498("shaders/post/bits.json"), new ResourceLocation_1975012498("shaders/post/desaturate.json"), new ResourceLocation_1975012498("shaders/post/green.json"), new ResourceLocation_1975012498("shaders/post/blur.json"), new ResourceLocation_1975012498("shaders/post/wobble.json"), new ResourceLocation_1975012498("shaders/post/blobs.json"), new ResourceLocation_1975012498("shaders/post/antialias.json"), new ResourceLocation_1975012498("shaders/post/creeper.json"), new ResourceLocation_1975012498("shaders/post/spider.json") };
        à = EntityRenderer.Ô.length;
    }
    
    public EntityRenderer(final Minecraft á, final IResourceManager ˆï­) {
        this.£á = new Random();
        this.¥Æ = new MouseFilter();
        this.Ø­à = new MouseFilter();
        this.µÕ = 4.0f;
        this.Æ = 4.0f;
        this.ÇŽÕ = true;
        this.É = true;
        this.áƒ = Minecraft.áƒ();
        this.áŠ = new float[1024];
        this.ˆáŠ = new float[1024];
        this.áŒŠ = GLAllocation.Âµá€(16);
        this.Ä = 0;
        this.Ñ¢Â = false;
        this.Ï­à = 1.0;
        this.£Ï = false;
        this.Ø­á = null;
        this.ˆÉ = false;
        this.Ø = false;
        this.Ï­Ï­Ï = 128.0f;
        this.£Â = 0L;
        this.£Ó = 0;
        this.ˆÐƒØ­à = 0;
        this.£Õ = 0;
        this.Ï­Ô = 0.0f;
        this.Œà = 0.0f;
        this.ÇªÓ = EntityRenderer.à;
        this.áˆºÏ = false;
        this.ˆáƒ = 0;
        this.á = á;
        this.ˆÏ­ = ˆï­;
        this.Ý = á.ˆáƒ();
        this.£à = new MapItemRenderer(á.¥à());
        this.Õ = new DynamicTexture(16, 16);
        this.ŠÂµà = á.¥à().HorizonCode_Horizon_È("lightMap", this.Õ);
        this.à¢ = this.Õ.Ý();
        this.Ï = null;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final float n = j - 16;
                final float n2 = i - 16;
                final float ý = MathHelper.Ý(n * n + n2 * n2);
                this.áŠ[i << 5 | j] = -n2 / ý;
                this.ˆáŠ[i << 5 | j] = n / ý;
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        return OpenGlHelper.¥à && this.Ï != null;
    }
    
    public void Â() {
        this.áˆºÏ = !this.áˆºÏ;
    }
    
    public void HorizonCode_Horizon_È(final Entity entity) {
        if (OpenGlHelper.¥à) {
            if (this.Ï != null) {
                this.Ï.HorizonCode_Horizon_È();
            }
            this.Ï = null;
            if (entity instanceof EntityCreeper) {
                this.HorizonCode_Horizon_È(new ResourceLocation_1975012498("shaders/post/creeper.json"));
            }
            else if (entity instanceof EntitySpider) {
                this.HorizonCode_Horizon_È(new ResourceLocation_1975012498("shaders/post/spider.json"));
            }
            else if (entity instanceof EntityEnderman) {
                this.HorizonCode_Horizon_È(new ResourceLocation_1975012498("shaders/post/invert.json"));
            }
        }
    }
    
    public void Ý() {
        if (OpenGlHelper.¥à && this.á.ÇŽá€() instanceof EntityPlayer) {
            if (this.Ï != null) {
                this.Ï.HorizonCode_Horizon_È();
            }
            this.ÇªÓ = (this.ÇªÓ + 1) % (EntityRenderer.Ô.length + 1);
            if (this.ÇªÓ != EntityRenderer.à) {
                this.HorizonCode_Horizon_È(EntityRenderer.Ô[this.ÇªÓ]);
            }
            else {
                this.Ï = null;
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_i1050_4_) {
        try {
            (this.Ï = new ShaderGroup(this.á.¥à(), this.ˆÏ­, this.á.Ý(), p_i1050_4_)).HorizonCode_Horizon_È(this.á.Ó, this.á.à);
            this.áˆºÏ = true;
        }
        catch (IOException ex) {
            EntityRenderer.áŒŠÆ.warn("Failed to load shader: " + p_i1050_4_, (Throwable)ex);
            this.ÇªÓ = EntityRenderer.à;
            this.áˆºÏ = false;
        }
        catch (JsonSyntaxException ex2) {
            EntityRenderer.áŒŠÆ.warn("Failed to load shader: " + p_i1050_4_, (Throwable)ex2);
            this.ÇªÓ = EntityRenderer.à;
            this.áˆºÏ = false;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager resourceManager) {
        if (this.Ï != null) {
            this.Ï.HorizonCode_Horizon_È();
        }
        this.Ï = null;
        if (this.ÇªÓ != EntityRenderer.à) {
            this.HorizonCode_Horizon_È(EntityRenderer.Ô[this.ÇªÓ]);
        }
        else {
            this.HorizonCode_Horizon_È(this.á.ÇŽá€());
        }
    }
    
    public void Ø­áŒŠá() {
        if (OpenGlHelper.¥à && ShaderLinkHelper.Â() == null) {
            ShaderLinkHelper.HorizonCode_Horizon_È();
        }
        this.ÂµÈ();
        this.á();
        this.£ÂµÄ = this.Ø­Âµ;
        this.Æ = this.µÕ;
        if (this.á.ŠÄ.áŒŠÈ) {
            final float n = this.á.ŠÄ.HorizonCode_Horizon_È * 0.6f + 0.2f;
            final float n2 = n * n * n * 8.0f;
            this.áŒŠà = this.¥Æ.HorizonCode_Horizon_È(this.Šáƒ, 0.05f * n2);
            this.ŠÄ = this.Ø­à.HorizonCode_Horizon_È(this.Ï­Ðƒà, 0.05f * n2);
            this.Ñ¢á = 0.0f;
            this.Šáƒ = 0.0f;
            this.Ï­Ðƒà = 0.0f;
        }
        else {
            this.áŒŠà = 0.0f;
            this.ŠÄ = 0.0f;
            this.¥Æ.HorizonCode_Horizon_È();
            this.Ø­à.HorizonCode_Horizon_È();
        }
        if (this.á.ÇŽá€() == null) {
            this.á.HorizonCode_Horizon_È(this.á.á);
        }
        final float £à = this.á.áŒŠÆ.£à(new BlockPos(this.á.ÇŽá€()));
        final float n3 = this.á.ŠÄ.Ý / 32.0f;
        this.Ø­Âµ += (£à * (1.0f - n3) + n3 - this.Ø­Âµ) * 0.1f;
        ++this.µà;
        this.Ý.HorizonCode_Horizon_È();
        this.£á();
        this.ÇŽÉ = this.Ê;
        if (BossStatus.Ø­áŒŠá) {
            this.Ê += 0.05f;
            if (this.Ê > 1.0f) {
                this.Ê = 1.0f;
            }
            BossStatus.Ø­áŒŠá = false;
        }
        else if (this.Ê > 0.0f) {
            this.Ê -= 0.0125f;
        }
    }
    
    public ShaderGroup Âµá€() {
        return this.Ï;
    }
    
    public void HorizonCode_Horizon_È(final int n, final int n2) {
        if (OpenGlHelper.¥à) {
            if (this.Ï != null) {
                this.Ï.HorizonCode_Horizon_È(n, n2);
            }
            this.á.áˆºÑ¢Õ.HorizonCode_Horizon_È(n, n2);
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_70676_1_) {
        final Entity çŽá€ = this.á.ÇŽá€();
        if (çŽá€ != null && this.á.áŒŠÆ != null) {
            this.á.ÇŽÕ.HorizonCode_Horizon_È("pick");
            this.á.£á = null;
            final double p_174822_1_ = this.á.Âµá€.Ø­áŒŠá();
            this.á.áŒŠà = çŽá€.HorizonCode_Horizon_È(p_174822_1_, p_70676_1_);
            double ó = p_174822_1_;
            final Vec3 à = çŽá€.à(p_70676_1_);
            double n;
            if (this.á.Âµá€.áŒŠÆ()) {
                n = 6.0;
                ó = 6.0;
            }
            else {
                if (p_174822_1_ > 3.0) {
                    ó = 3.0;
                }
                n = ó;
            }
            if (this.á.áŒŠà != null) {
                ó = this.á.áŒŠà.Ý.Ó(à);
            }
            final Vec3 ó2 = çŽá€.Ó(p_70676_1_);
            final Vec3 â = à.Â(ó2.HorizonCode_Horizon_È * n, ó2.Â * n, ó2.Ý * n);
            this.ˆà = null;
            Vec3 p_i45482_2_ = null;
            final float n2 = 1.0f;
            final List â2 = this.á.áŒŠÆ.Â(çŽá€, çŽá€.£É().HorizonCode_Horizon_È(ó2.HorizonCode_Horizon_È * n, ó2.Â * n, ó2.Ý * n).Â(n2, n2, n2));
            double n3 = ó;
            for (int i = 0; i < â2.size(); ++i) {
                final Entity ˆà = â2.get(i);
                if (ˆà.Ô()) {
                    final float £ó = ˆà.£Ó();
                    final AxisAlignedBB â3 = ˆà.£É().Â(£ó, £ó, £ó);
                    final MovingObjectPosition horizonCode_Horizon_È = â3.HorizonCode_Horizon_È(à, â);
                    if (â3.HorizonCode_Horizon_È(à)) {
                        if (0.0 < n3 || n3 == 0.0) {
                            this.ˆà = ˆà;
                            p_i45482_2_ = ((horizonCode_Horizon_È == null) ? à : horizonCode_Horizon_È.Ý);
                            n3 = 0.0;
                        }
                    }
                    else if (horizonCode_Horizon_È != null) {
                        final double ó3 = à.Ó(horizonCode_Horizon_È.Ý);
                        if (ó3 < n3 || n3 == 0.0) {
                            boolean â4 = false;
                            if (Reflector.Ø­Æ.Â()) {
                                â4 = Reflector.Â(i, Reflector.Ø­Æ, new Object[0]);
                            }
                            if (ˆà == çŽá€.Æ && !â4) {
                                if (n3 == 0.0) {
                                    this.ˆà = ˆà;
                                    p_i45482_2_ = horizonCode_Horizon_È.Ý;
                                }
                            }
                            else {
                                this.ˆà = ˆà;
                                p_i45482_2_ = horizonCode_Horizon_È.Ý;
                                n3 = ó3;
                            }
                        }
                    }
                }
            }
            if (this.ˆà != null && (n3 < ó || this.á.áŒŠà == null)) {
                this.á.áŒŠà = new MovingObjectPosition(this.ˆà, p_i45482_2_);
                if (this.ˆà instanceof EntityLivingBase || this.ˆà instanceof EntityItemFrame) {
                    this.á.£á = this.ˆà;
                }
            }
            this.á.ÇŽÕ.Â();
        }
    }
    
    private void ÂµÈ() {
        float n;
        if (this.á.ÇŽá€() instanceof AbstractClientPlayer) {
            n = ((AbstractClientPlayer)this.á.ÇŽá€()).f_();
        }
        else {
            n = this.á.á.f_();
        }
        this.Çªà¢ = this.ŒÏ;
        this.ŒÏ += (n - this.ŒÏ) * 0.5f;
        if (this.ŒÏ > 1.5f) {
            this.ŒÏ = 1.5f;
        }
        if (this.ŒÏ < 0.1f) {
            this.ŒÏ = 0.1f;
        }
    }
    
    private float HorizonCode_Horizon_È(final float p_180786_2_, final boolean b) {
        if (this.Ñ¢Â) {
            return 90.0f;
        }
        final Entity çŽá€ = this.á.ÇŽá€();
        float n = 70.0f;
        if (b) {
            n = this.á.ŠÄ.£Ô * (this.Çªà¢ + (this.ŒÏ - this.Çªà¢) * p_180786_2_);
        }
        boolean horizonCode_Horizon_È = false;
        if (this.á.¥Æ == null) {
            final GameSettings šä = this.á.ŠÄ;
            horizonCode_Horizon_È = GameSettings.HorizonCode_Horizon_È(this.á.ŠÄ.¥Ä);
        }
        if (horizonCode_Horizon_È) {
            if (!Config.áŒŠÆ) {
                Config.áŒŠÆ = true;
                this.á.ŠÄ.áŒŠÈ = true;
            }
            if (Config.áŒŠÆ) {
                n /= 4.0f;
            }
        }
        else if (Config.áŒŠÆ) {
            Config.áŒŠÆ = false;
            this.á.ŠÄ.áŒŠÈ = false;
            this.¥Æ = new MouseFilter();
            this.Ø­à = new MouseFilter();
            this.á.áˆºÑ¢Õ.Ø­áŒŠá = true;
        }
        if (çŽá€ instanceof EntityLivingBase && ((EntityLivingBase)çŽá€).Ï­Ä() <= 0.0f) {
            n /= (1.0f - 500.0f / (((EntityLivingBase)çŽá€).ÇªØ­ + p_180786_2_ + 500.0f)) * 2.0f + 1.0f;
        }
        if (ActiveRenderInfo.HorizonCode_Horizon_È(this.á.áŒŠÆ, çŽá€, p_180786_2_).Ó() == Material.Ø) {
            n = n * 60.0f / 70.0f;
        }
        return n;
    }
    
    private void Ó(final float n) {
        if (this.á.ÇŽá€() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)this.á.ÇŽá€();
            final float n2 = entityLivingBase.µà - n;
            if (entityLivingBase.Ï­Ä() <= 0.0f) {
                GlStateManager.Â(40.0f - 8000.0f / (entityLivingBase.ÇªØ­ + n + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (n2 < 0.0f) {
                return;
            }
            final float n3 = n2 / entityLivingBase.ÇŽá;
            final float horizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(n3 * n3 * n3 * n3 * 3.1415927f);
            final float ñ¢à = entityLivingBase.Ñ¢à;
            GlStateManager.Â(-ñ¢à, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-horizonCode_Horizon_È * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(ñ¢à, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void à(final float n) {
        if (this.á.ÇŽá€() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)this.á.ÇŽá€();
            final float n2 = -(entityPlayer.Ä + (entityPlayer.Ä - entityPlayer.Ø­Âµ) * n);
            final float n3 = entityPlayer.Çªà + (entityPlayer.¥Å - entityPlayer.Çªà) * n;
            final float p_179114_0_ = entityPlayer.Ñ¢Ç + (entityPlayer.£É - entityPlayer.Ñ¢Ç) * n;
            GlStateManager.Â(MathHelper.HorizonCode_Horizon_È(n2 * 3.1415927f) * n3 * 0.5f, -Math.abs(MathHelper.Â(n2 * 3.1415927f) * n3), 0.0f);
            GlStateManager.Â(MathHelper.HorizonCode_Horizon_È(n2 * 3.1415927f) * n3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(Math.abs(MathHelper.Â(n2 * 3.1415927f - 0.2f) * n3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(p_179114_0_, 1.0f, 0.0f, 0.0f);
        }
    }
    
    public void Â(final float p_72721_7_) {
        final Entity çŽá€ = this.á.ÇŽá€();
        float ðƒáƒ = çŽá€.Ðƒáƒ();
        final double x = çŽá€.áŒŠà + (çŽá€.ŒÏ - çŽá€.áŒŠà) * p_72721_7_;
        final double y = çŽá€.ŠÄ + (çŽá€.Çªà¢ - çŽá€.ŠÄ) * p_72721_7_ + ðƒáƒ;
        final double z = çŽá€.Ñ¢á + (çŽá€.Ê - çŽá€.Ñ¢á) * p_72721_7_;
        if (çŽá€ instanceof EntityLivingBase && ((EntityLivingBase)çŽá€).Ï­Ó()) {
            ++ðƒáƒ;
            GlStateManager.Â(0.0f, 0.3f, 0.0f);
            if (!this.á.ŠÄ.ˆØ­áˆº) {
                final IBlockState â = this.á.áŒŠÆ.Â(new BlockPos(çŽá€));
                final Block ý = â.Ý();
                if (Reflector.ˆá.Â()) {
                    Reflector.HorizonCode_Horizon_È(Reflector.ˆá, this.á, çŽá€);
                }
                else if (ý == Blocks.Ê) {
                    GlStateManager.Â(((EnumFacing)â.HorizonCode_Horizon_È(BlockBed.ŠÂµà)).Ý() * 90, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.Â(çŽá€.á€ + (çŽá€.É - çŽá€.á€) * p_72721_7_ + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.Â(çŽá€.Õ + (çŽá€.áƒ - çŽá€.Õ) * p_72721_7_, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.á.ŠÄ.µÏ > 0) {
            double n = this.Æ + (this.µÕ - this.Æ) * p_72721_7_;
            if (this.á.ŠÄ.ˆØ­áˆº) {
                GlStateManager.Â(0.0f, 0.0f, (float)(-n));
            }
            else {
                final float é = çŽá€.É;
                float áƒ = çŽá€.áƒ;
                if (this.á.ŠÄ.µÏ == 2) {
                    áƒ += 180.0f;
                }
                final double n2 = -MathHelper.HorizonCode_Horizon_È(é / 180.0f * 3.1415927f) * MathHelper.Â(áƒ / 180.0f * 3.1415927f) * n;
                final double n3 = MathHelper.Â(é / 180.0f * 3.1415927f) * MathHelper.Â(áƒ / 180.0f * 3.1415927f) * n;
                final double n4 = -MathHelper.HorizonCode_Horizon_È(áƒ / 180.0f * 3.1415927f) * n;
                for (int i = 0; i < 8; ++i) {
                    final float n5 = (i & 0x1) * 2 - 1;
                    final float n6 = (i >> 1 & 0x1) * 2 - 1;
                    final float n7 = (i >> 2 & 0x1) * 2 - 1;
                    final float n8 = n5 * 0.1f;
                    final float n9 = n6 * 0.1f;
                    final float n10 = n7 * 0.1f;
                    final MovingObjectPosition horizonCode_Horizon_È = this.á.áŒŠÆ.HorizonCode_Horizon_È(new Vec3(x + n8, y + n9, z + n10), new Vec3(x - n2 + n8 + n10, y - n4 + n9, z - n3 + n10));
                    if (horizonCode_Horizon_È != null) {
                        final double ó = horizonCode_Horizon_È.Ý.Ó(new Vec3(x, y, z));
                        if (ó < n) {
                            n = ó;
                        }
                    }
                }
                if (this.á.ŠÄ.µÏ == 2) {
                    GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.Â(çŽá€.áƒ - áƒ, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(çŽá€.É - é, 0.0f, 1.0f, 0.0f);
                GlStateManager.Â(0.0f, 0.0f, (float)(-n));
                GlStateManager.Â(é - çŽá€.É, 0.0f, 1.0f, 0.0f);
                GlStateManager.Â(áƒ - çŽá€.áƒ, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.Â(0.0f, 0.0f, -0.1f);
        }
        if (!this.á.ŠÄ.ˆØ­áˆº) {
            GlStateManager.Â(çŽá€.Õ + (çŽá€.áƒ - çŽá€.Õ) * p_72721_7_, 1.0f, 0.0f, 0.0f);
            if (çŽá€ instanceof EntityAnimal) {
                final EntityAnimal entityAnimal = (EntityAnimal)çŽá€;
                GlStateManager.Â(entityAnimal.Š + (entityAnimal.ÂµÕ - entityAnimal.Š) * p_72721_7_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.Â(çŽá€.á€ + (çŽá€.É - çŽá€.á€) * p_72721_7_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.Â(0.0f, -ðƒáƒ, 0.0f);
        this.ˆá = this.á.áˆºÑ¢Õ.HorizonCode_Horizon_È(çŽá€.áŒŠà + (çŽá€.ŒÏ - çŽá€.áŒŠà) * p_72721_7_, çŽá€.ŠÄ + (çŽá€.Çªà¢ - çŽá€.ŠÄ) * p_72721_7_ + ðƒáƒ, çŽá€.Ñ¢á + (çŽá€.Ê - çŽá€.Ñ¢á) * p_72721_7_, p_72721_7_);
    }
    
    public void HorizonCode_Horizon_È(final float n, final int n2) {
        this.Å = this.á.ŠÄ.Ý * 16;
        if (Config.á()) {
            this.Å *= 0.95f;
        }
        if (Config.ˆÏ­()) {
            this.Å *= 0.83f;
        }
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        final float n3 = 0.07f;
        if (this.á.ŠÄ.Âµá€) {
            GlStateManager.Â(-(n2 * 2 - 1) * n3, 0.0f, 0.0f);
        }
        this.Ï­Ï­Ï = this.Å * MathHelper.HorizonCode_Horizon_È;
        if (this.Ï­Ï­Ï < 173.0f) {
            this.Ï­Ï­Ï = 173.0f;
        }
        if (this.á.áŒŠÆ.£à.µà() == 1) {
            this.Ï­Ï­Ï = 256.0f;
        }
        if (this.Ï­à != 1.0) {
            GlStateManager.Â((float)this.áˆºáˆºÈ, (float)(-this.ÇŽá€), 0.0f);
            GlStateManager.HorizonCode_Horizon_È(this.Ï­à, this.Ï­à, 1.0);
        }
        Project.gluPerspective(this.HorizonCode_Horizon_È(n, true), this.á.Ó / this.á.à, 0.05f, this.Ï­Ï­Ï);
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        if (this.á.ŠÄ.Âµá€) {
            GlStateManager.Â((n2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.Ó(n);
        if (this.á.ŠÄ.Ø­áŒŠá) {
            this.à(n);
        }
        final float n4 = this.á.á.ˆÏ­ + (this.á.á.á - this.á.á.ˆÏ­) * n;
        if (n4 > 0.0f) {
            int n5 = 20;
            if (this.á.á.HorizonCode_Horizon_È(Potion.ÂµÈ)) {
                n5 = 7;
            }
            final float n6 = 5.0f / (n4 * n4 + 5.0f) - n4 * 0.04f;
            final float n7 = n6 * n6;
            GlStateManager.Â((this.µà + n) * n5, 0.0f, 1.0f, 1.0f);
            GlStateManager.HorizonCode_Horizon_È(1.0f / n7, 1.0f, 1.0f);
            GlStateManager.Â(-(this.µà + n) * n5, 0.0f, 1.0f, 1.0f);
        }
        this.Â(n);
        if (this.Ñ¢Â) {
            switch (this.Ä) {
                case 0: {
                    GlStateManager.Â(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.Â(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.Â(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.Â(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    private void Â(final float n, final int n2) {
        if (!this.Ñ¢Â) {
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            final float n3 = 0.07f;
            if (this.á.ŠÄ.Âµá€) {
                GlStateManager.Â(-(n2 * 2 - 1) * n3, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.HorizonCode_Horizon_È(n, false), this.á.Ó / this.á.à, 0.05f, this.Å * 2.0f);
            GlStateManager.á(5888);
            GlStateManager.ŒÏ();
            if (this.á.ŠÄ.Âµá€) {
                GlStateManager.Â((n2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.Çªà¢();
            this.Ó(n);
            if (this.á.ŠÄ.Ø­áŒŠá) {
                this.à(n);
            }
            final boolean b = this.á.ÇŽá€() instanceof EntityLivingBase && ((EntityLivingBase)this.á.ÇŽá€()).Ï­Ó();
            if (this.á.ŠÄ.µÏ == 0 && !b && !this.á.ŠÄ.µ && !this.á.Âµá€.HorizonCode_Horizon_È()) {
                this.à();
                this.Ý.HorizonCode_Horizon_È(n);
                this.Ó();
            }
            GlStateManager.Ê();
            if (this.á.ŠÄ.µÏ == 0 && !b) {
                this.Ý.Â(n);
                this.Ó(n);
            }
            if (this.á.ŠÄ.Ø­áŒŠá) {
                this.à(n);
            }
        }
    }
    
    public void Ó() {
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.Æ();
        GlStateManager.à(OpenGlHelper.£à);
    }
    
    public void à() {
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.á(5890);
        GlStateManager.ŒÏ();
        final float n = 0.00390625f;
        GlStateManager.HorizonCode_Horizon_È(n, n, n);
        GlStateManager.Â(8.0f, 8.0f, 8.0f);
        GlStateManager.á(5888);
        this.á.¥à().HorizonCode_Horizon_È(this.ŠÂµà);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.µÕ();
        GlStateManager.à(OpenGlHelper.£à);
    }
    
    private void á() {
        this.Ç += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.Ç *= 0.9;
        this.Âµà += (this.Ç - this.Âµà) * 1.0f;
        this.¥à = true;
    }
    
    private void Ø(final float n) {
        if (this.¥à) {
            this.á.ÇŽÕ.HorizonCode_Horizon_È("lightTex");
            final WorldClient áœšæ = this.á.áŒŠÆ;
            if (áœšæ != null) {
                if (CustomColorizer.HorizonCode_Horizon_È(áœšæ, this.Âµà, this.à¢, this.á.á.HorizonCode_Horizon_È(Potion.ˆà))) {
                    this.Õ.Â();
                    this.¥à = false;
                    this.á.ÇŽÕ.Â();
                    return;
                }
                for (int i = 0; i < 256; ++i) {
                    float n2 = áœšæ.£à.£à()[i / 16] * (áœšæ.Â(1.0f) * 0.95f + 0.05f);
                    final float n3 = áœšæ.£à.£à()[i % 16] * (this.Âµà * 0.1f + 1.5f);
                    if (áœšæ.Âµà() > 0) {
                        n2 = áœšæ.£à.£à()[i / 16];
                    }
                    final float n4 = n2 * (áœšæ.Â(1.0f) * 0.65f + 0.35f);
                    final float n5 = n2 * (áœšæ.Â(1.0f) * 0.65f + 0.35f);
                    final float n6 = n3 * ((n3 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    final float n7 = n3 * (n3 * n3 * 0.6f + 0.4f);
                    final float n8 = n4 + n3;
                    final float n9 = n5 + n6;
                    final float n10 = n2 + n7;
                    float n11 = n8 * 0.96f + 0.03f;
                    float n12 = n9 * 0.96f + 0.03f;
                    float n13 = n10 * 0.96f + 0.03f;
                    if (this.Ê > 0.0f) {
                        final float n14 = this.ÇŽÉ + (this.Ê - this.ÇŽÉ) * n;
                        n11 = n11 * (1.0f - n14) + n11 * 0.7f * n14;
                        n12 = n12 * (1.0f - n14) + n12 * 0.6f * n14;
                        n13 = n13 * (1.0f - n14) + n13 * 0.6f * n14;
                    }
                    if (áœšæ.£à.µà() == 1) {
                        n11 = 0.22f + n3 * 0.75f;
                        n12 = 0.28f + n6 * 0.75f;
                        n13 = 0.25f + n7 * 0.75f;
                    }
                    if (this.á.á.HorizonCode_Horizon_È(Potion.ˆà)) {
                        final float horizonCode_Horizon_È = this.HorizonCode_Horizon_È(this.á.á, n);
                        float n15 = 1.0f / n11;
                        if (n15 > 1.0f / n12) {
                            n15 = 1.0f / n12;
                        }
                        if (n15 > 1.0f / n13) {
                            n15 = 1.0f / n13;
                        }
                        n11 = n11 * (1.0f - horizonCode_Horizon_È) + n11 * n15 * horizonCode_Horizon_È;
                        n12 = n12 * (1.0f - horizonCode_Horizon_È) + n12 * n15 * horizonCode_Horizon_È;
                        n13 = n13 * (1.0f - horizonCode_Horizon_È) + n13 * n15 * horizonCode_Horizon_È;
                    }
                    if (n11 > 1.0f) {
                        n11 = 1.0f;
                    }
                    if (n12 > 1.0f) {
                        n12 = 1.0f;
                    }
                    if (n13 > 1.0f) {
                        n13 = 1.0f;
                    }
                    final float šï = this.á.ŠÄ.ŠÏ;
                    final float n16 = 1.0f - n11;
                    final float n17 = 1.0f - n12;
                    final float n18 = 1.0f - n13;
                    final float n19 = 1.0f - n16 * n16 * n16 * n16;
                    final float n20 = 1.0f - n17 * n17 * n17 * n17;
                    final float n21 = 1.0f - n18 * n18 * n18 * n18;
                    final float n22 = n11 * (1.0f - šï) + n19 * šï;
                    final float n23 = n12 * (1.0f - šï) + n20 * šï;
                    final float n24 = n13 * (1.0f - šï) + n21 * šï;
                    float n25 = n22 * 0.96f + 0.03f;
                    float n26 = n23 * 0.96f + 0.03f;
                    float n27 = n24 * 0.96f + 0.03f;
                    if (n25 > 1.0f) {
                        n25 = 1.0f;
                    }
                    if (n26 > 1.0f) {
                        n26 = 1.0f;
                    }
                    if (n27 > 1.0f) {
                        n27 = 1.0f;
                    }
                    if (n25 < 0.0f) {
                        n25 = 0.0f;
                    }
                    if (n26 < 0.0f) {
                        n26 = 0.0f;
                    }
                    if (n27 < 0.0f) {
                        n27 = 0.0f;
                    }
                    this.à¢[i] = (255 << 24 | (int)(n25 * 255.0f) << 16 | (int)(n26 * 255.0f) << 8 | (int)(n27 * 255.0f));
                }
                this.Õ.Â();
                this.¥à = false;
                this.á.ÇŽÕ.Â();
            }
        }
    }
    
    private float HorizonCode_Horizon_È(final EntityLivingBase entityLivingBase, final float n) {
        final int â = entityLivingBase.Â(Potion.ˆà).Â();
        return (â > 200) ? 1.0f : (0.7f + MathHelper.HorizonCode_Horizon_È((â - n) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void Ý(final float p_175180_1_) {
        this.£à();
        final boolean active = Display.isActive();
        if (!active && this.á.ŠÄ.£ÇªÓ && (!this.á.ŠÄ.ÂµÕ || !Mouse.isButtonDown(1))) {
            if (Minecraft.áƒ() - this.áƒ > 500L) {
                this.á.µà();
            }
        }
        else {
            this.áƒ = Minecraft.áƒ();
        }
        this.á.ÇŽÕ.HorizonCode_Horizon_È("mouse");
        if (active && Minecraft.HorizonCode_Horizon_È && this.á.ÇŽÉ && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
            Mouse.setGrabbed(true);
        }
        if (this.á.ÇŽÉ && active) {
            this.á.Ñ¢á.Ý();
            final float n = this.á.ŠÄ.HorizonCode_Horizon_È * 0.6f + 0.2f;
            final float n2 = n * n * n * 8.0f;
            final float yaw = this.á.Ñ¢á.HorizonCode_Horizon_È * n2;
            final float n3 = this.á.Ñ¢á.Â * n2;
            int n4 = 1;
            if (this.á.ŠÄ.Â) {
                n4 = -1;
            }
            if (this.á.ŠÄ.áŒŠÈ) {
                this.Šáƒ += yaw;
                this.Ï­Ðƒà += n3;
                final float n5 = p_175180_1_ - this.Ñ¢á;
                this.Ñ¢á = p_175180_1_;
                this.á.á.Ý(this.áŒŠà * n5, this.ŠÄ * n5 * n4);
            }
            else {
                this.Šáƒ = 0.0f;
                this.Ï­Ðƒà = 0.0f;
                this.á.á.Ý(yaw, n3 * n4);
            }
        }
        this.á.ÇŽÕ.Â();
        if (!this.á.Ï­Ðƒà) {
            EntityRenderer.HorizonCode_Horizon_È = this.á.ŠÄ.Âµá€;
            final ScaledResolution scaledResolution = new ScaledResolution(this.á, this.á.Ó, this.á.à);
            final int horizonCode_Horizon_È = scaledResolution.HorizonCode_Horizon_È();
            final int â = scaledResolution.Â();
            final int n6 = Mouse.getX() * horizonCode_Horizon_È / this.á.Ó;
            final int n7 = â - Mouse.getY() * â / this.á.à - 1;
            final int à = this.á.ŠÄ.à;
            if (this.á.áŒŠÆ != null) {
                this.á.ÇŽÕ.HorizonCode_Horizon_È("level");
                this.HorizonCode_Horizon_È(p_175180_1_, this.á€ + 1000000000 / Math.max(Minecraft.Œ(), 30));
                if (OpenGlHelper.¥à) {
                    this.á.áˆºÑ¢Õ.Â();
                    if (this.Ï != null && this.áˆºÏ) {
                        GlStateManager.á(5890);
                        GlStateManager.Çªà¢();
                        GlStateManager.ŒÏ();
                        this.Ï.HorizonCode_Horizon_È(p_175180_1_);
                        GlStateManager.Ê();
                    }
                    this.á.Ý().HorizonCode_Horizon_È(true);
                }
                this.á€ = System.nanoTime();
                this.á.ÇŽÕ.Ý("gui");
                if (!this.á.ŠÄ.µ || this.á.¥Æ != null) {
                    GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
                    this.á.Šáƒ.HorizonCode_Horizon_È(p_175180_1_);
                }
                this.á.ÇŽÕ.Â();
            }
            else {
                GlStateManager.Â(0, 0, this.á.Ó, this.á.à);
                GlStateManager.á(5889);
                GlStateManager.ŒÏ();
                GlStateManager.á(5888);
                GlStateManager.ŒÏ();
                this.Ø();
                this.á€ = System.nanoTime();
            }
            if (this.á.¥Æ != null) {
                GlStateManager.ÂµÈ(256);
                try {
                    boolean horizonCode_Horizon_È2 = false;
                    if (Reflector.ŠÓ.Â()) {
                        horizonCode_Horizon_È2 = Reflector.HorizonCode_Horizon_È(Reflector.ˆÐƒØ­à, this.á.¥Æ, n6, n7, p_175180_1_);
                    }
                    if (!horizonCode_Horizon_È2) {
                        this.á.¥Æ.HorizonCode_Horizon_È(n6, n7, p_175180_1_);
                    }
                    Reflector.HorizonCode_Horizon_È(Reflector.Ï­Ô, this.á.¥Æ, n6, n7, p_175180_1_);
                }
                catch (Throwable causeIn) {
                    final CrashReport horizonCode_Horizon_È3 = CrashReport.HorizonCode_Horizon_È(causeIn, "Rendering screen");
                    final CrashReportCategory horizonCode_Horizon_È4 = horizonCode_Horizon_È3.HorizonCode_Horizon_È("Screen render details");
                    horizonCode_Horizon_È4.HorizonCode_Horizon_È("Screen name", new Callable() {
                        private static final String Â = "CL_00000948";
                        
                        public String HorizonCode_Horizon_È() {
                            return EntityRenderer.this.á.¥Æ.getClass().getCanonicalName();
                        }
                    });
                    horizonCode_Horizon_È4.HorizonCode_Horizon_È("Mouse location", new Callable() {
                        private static final String Â = "CL_00000950";
                        
                        public String HorizonCode_Horizon_È() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", n6, n7, Mouse.getX(), Mouse.getY());
                        }
                    });
                    horizonCode_Horizon_È4.HorizonCode_Horizon_È("Screen size", new Callable() {
                        private static final String Â = "CL_00000951";
                        
                        public String HorizonCode_Horizon_È() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), EntityRenderer.this.á.Ó, EntityRenderer.this.á.à, scaledResolution.Âµá€());
                        }
                    });
                    throw new ReportedException(horizonCode_Horizon_È3);
                }
            }
        }
        this.Å();
        Lagometer.Â();
        if (this.á.ŠÄ.ŠÄ) {
            this.á.ŠÄ.¥áŒŠà = true;
        }
    }
    
    public void Ø­áŒŠá(final float n) {
        this.Ø();
        this.á.Šáƒ.Ý(new ScaledResolution(this.á, this.á.Ó, this.á.à));
    }
    
    private boolean ˆÏ­() {
        if (!this.É) {
            return false;
        }
        final Entity çŽá€ = this.á.ÇŽá€();
        boolean b = çŽá€ instanceof EntityPlayer && !this.á.ŠÄ.µ;
        if (b && !((EntityPlayer)çŽá€).áˆºáˆºáŠ.Âµá€) {
            final ItemStack áœŠá = ((EntityPlayer)çŽá€).áŒŠá();
            if (this.á.áŒŠà != null && this.á.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
                final BlockPos horizonCode_Horizon_È = this.á.áŒŠà.HorizonCode_Horizon_È();
                final Block ý = this.á.áŒŠÆ.Â(horizonCode_Horizon_È).Ý();
                if (this.á.Âµá€.á() == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                    b = (ý.£á() && this.á.áŒŠÆ.HorizonCode_Horizon_È(horizonCode_Horizon_È) instanceof IInventory);
                }
                else {
                    b = (áœŠá != null && (áœŠá.Ý(ý) || áœŠá.Ø­áŒŠá(ý)));
                }
            }
        }
        return b;
    }
    
    private void áŒŠÆ(final float n) {
        if (this.á.ŠÄ.µÐƒÓ && !this.á.ŠÄ.µ && !this.á.á.¥Ðƒá() && !this.á.ŠÄ.Ðƒáƒ) {
            final Entity çŽá€ = this.á.ÇŽá€();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            GL11.glLineWidth(1.0f);
            GlStateManager.Æ();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.Çªà¢();
            GlStateManager.á(5888);
            GlStateManager.ŒÏ();
            this.Â(n);
            GlStateManager.Â(0.0f, çŽá€.Ðƒáƒ(), 0.0f);
            RenderGlobal.HorizonCode_Horizon_È(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), -65536);
            RenderGlobal.HorizonCode_Horizon_È(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), -16776961);
            RenderGlobal.HorizonCode_Horizon_È(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), -16711936);
            GlStateManager.Ê();
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.µÕ();
            GlStateManager.ÂµÈ();
        }
    }
    
    public void HorizonCode_Horizon_È(final float n, final long n2) {
        this.Ø(n);
        if (this.á.ÇŽá€() == null) {
            this.á.HorizonCode_Horizon_È(this.á.á);
        }
        this.HorizonCode_Horizon_È(n);
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        this.á.ÇŽÕ.HorizonCode_Horizon_È("center");
        if (this.á.ŠÄ.Âµá€) {
            EntityRenderer.Â = 0;
            GlStateManager.HorizonCode_Horizon_È(false, true, true, false);
            this.HorizonCode_Horizon_È(0, n, n2);
            EntityRenderer.Â = 1;
            GlStateManager.HorizonCode_Horizon_È(true, false, false, false);
            this.HorizonCode_Horizon_È(1, n, n2);
            GlStateManager.HorizonCode_Horizon_È(true, true, true, false);
        }
        else {
            this.HorizonCode_Horizon_È(2, n, n2);
        }
        this.á.ÇŽÕ.Â();
    }
    
    private void HorizonCode_Horizon_È(final int n, final float n2, final long p_174967_1_) {
        final RenderGlobal áˆºÑ¢Õ = this.á.áˆºÑ¢Õ;
        final EffectRenderer å = this.á.Å;
        final boolean ˆï­ = this.ˆÏ­();
        GlStateManager.Å();
        this.á.ÇŽÕ.Ý("clear");
        GlStateManager.Â(0, 0, this.á.Ó, this.á.à);
        this.áˆºÑ¢Õ(n2);
        GlStateManager.ÂµÈ(16640);
        this.á.ÇŽÕ.Ý("camera");
        this.HorizonCode_Horizon_È(n2, n);
        ActiveRenderInfo.HorizonCode_Horizon_È(this.á.á, this.á.ŠÄ.µÏ == 2);
        this.á.ÇŽÕ.Ý("frustum");
        ClippingHelperImpl.HorizonCode_Horizon_È();
        this.á.ÇŽÕ.Ý("culling");
        final Frustrum frustrum = new Frustrum();
        final Entity çŽá€ = this.á.ÇŽá€();
        frustrum.HorizonCode_Horizon_È(çŽá€.áˆºáˆºÈ + (çŽá€.ŒÏ - çŽá€.áˆºáˆºÈ) * n2, çŽá€.ÇŽá€ + (çŽá€.Çªà¢ - çŽá€.ÇŽá€) * n2, çŽá€.Ï + (çŽá€.Ê - çŽá€.Ï) * n2);
        if (!Config.Ï() && !Config.Ô() && !Config.ÇªÓ()) {
            GlStateManager.ÂµÈ();
        }
        else {
            this.HorizonCode_Horizon_È(-1, n2);
            this.á.ÇŽÕ.Ý("sky");
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            Project.gluPerspective(this.HorizonCode_Horizon_È(n2, true), this.á.Ó / this.á.à, 0.05f, this.Ï­Ï­Ï);
            GlStateManager.á(5888);
            áˆºÑ¢Õ.HorizonCode_Horizon_È(n2, n);
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            Project.gluPerspective(this.HorizonCode_Horizon_È(n2, true), this.á.Ó / this.á.à, 0.05f, this.Å * MathHelper.HorizonCode_Horizon_È);
            GlStateManager.á(5888);
        }
        this.HorizonCode_Horizon_È(0, n2);
        GlStateManager.áˆºÑ¢Õ(7425);
        if (çŽá€.Çªà¢ + çŽá€.Ðƒáƒ() < 128.0 + this.á.ŠÄ.¥Æ * 128.0f) {
            this.HorizonCode_Horizon_È(áˆºÑ¢Õ, n2, n);
        }
        this.á.ÇŽÕ.Ý("prepareterrain");
        this.HorizonCode_Horizon_È(0, n2);
        this.á.¥à().HorizonCode_Horizon_È(TextureMap.à);
        RenderHelper.HorizonCode_Horizon_È();
        this.á.ÇŽÕ.Ý("terrain_setup");
        áˆºÑ¢Õ.HorizonCode_Horizon_È(çŽá€, n2, frustrum, this.ˆáƒ++, this.á.á.Ø­áŒŠá());
        if (n == 0 || n == 2) {
            this.á.ÇŽÕ.Ý("updatechunks");
            Lagometer.Ø­áŒŠá.HorizonCode_Horizon_È();
            this.á.áˆºÑ¢Õ.HorizonCode_Horizon_È(p_174967_1_);
            Lagometer.Ø­áŒŠá.Â();
        }
        this.á.ÇŽÕ.Ý("terrain");
        Lagometer.à.HorizonCode_Horizon_È();
        GlStateManager.á(5888);
        GlStateManager.Çªà¢();
        GlStateManager.Ý();
        áˆºÑ¢Õ.HorizonCode_Horizon_È(EnumWorldBlockLayer.HorizonCode_Horizon_È, n2, n, çŽá€);
        GlStateManager.Ø­áŒŠá();
        áˆºÑ¢Õ.HorizonCode_Horizon_È(EnumWorldBlockLayer.Â, n2, n, çŽá€);
        this.á.¥à().Â(TextureMap.à).Â(false, false);
        áˆºÑ¢Õ.HorizonCode_Horizon_È(EnumWorldBlockLayer.Ý, n2, n, çŽá€);
        this.á.¥à().Â(TextureMap.à).Ø­áŒŠá();
        Lagometer.à.Â();
        GlStateManager.áˆºÑ¢Õ(7424);
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        if (!this.Ñ¢Â) {
            GlStateManager.á(5888);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            RenderHelper.Â();
            this.á.ÇŽÕ.Ý("entities");
            if (Reflector.á€.Â()) {
                Reflector.HorizonCode_Horizon_È(Reflector.á€, 0);
            }
            áˆºÑ¢Õ.HorizonCode_Horizon_È(çŽá€, frustrum, n2);
            if (Reflector.á€.Â()) {
                Reflector.HorizonCode_Horizon_È(Reflector.á€, -1);
            }
            RenderHelper.HorizonCode_Horizon_È();
            this.Ó();
            GlStateManager.á(5888);
            GlStateManager.Ê();
            GlStateManager.Çªà¢();
            if (this.á.áŒŠà != null && çŽá€.HorizonCode_Horizon_È(Material.Ø) && ˆï­) {
                final EntityPlayer p_72731_1_ = (EntityPlayer)çŽá€;
                GlStateManager.Ý();
                this.á.ÇŽÕ.Ý("outline");
                áˆºÑ¢Õ.HorizonCode_Horizon_È(p_72731_1_, this.á.áŒŠà, 0, n2);
                GlStateManager.Ø­áŒŠá();
            }
        }
        GlStateManager.á(5888);
        GlStateManager.Ê();
        if (ˆï­ && this.á.áŒŠà != null && !çŽá€.HorizonCode_Horizon_È(Material.Ø)) {
            final EntityPlayer p_72731_1_2 = (EntityPlayer)çŽá€;
            GlStateManager.Ý();
            this.á.ÇŽÕ.Ý("outline");
            áˆºÑ¢Õ.HorizonCode_Horizon_È(p_72731_1_2, this.á.áŒŠà, 0, n2);
            GlStateManager.Ø­áŒŠá();
        }
        this.á.ÇŽÕ.Ý("destroyProgress");
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 1, 1, 0);
        áˆºÑ¢Õ.HorizonCode_Horizon_È(Tessellator.HorizonCode_Horizon_È(), Tessellator.HorizonCode_Horizon_È().Ý(), çŽá€, n2);
        GlStateManager.ÂµÈ();
        if (!this.Ñ¢Â) {
            this.à();
            this.á.ÇŽÕ.Ý("litParticles");
            å.Â(çŽá€, n2);
            RenderHelper.HorizonCode_Horizon_È();
            this.HorizonCode_Horizon_È(0, n2);
            this.á.ÇŽÕ.Ý("particles");
            å.HorizonCode_Horizon_È(çŽá€, n2);
            this.Ó();
        }
        OGLRender.HorizonCode_Horizon_È(1.2f);
        new EventRender3D().Â();
        OGLRender.Ý();
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.Å();
        this.á.ÇŽÕ.Ý("weather");
        this.Âµá€(n2);
        GlStateManager.HorizonCode_Horizon_È(true);
        áˆºÑ¢Õ.HorizonCode_Horizon_È(çŽá€, n2);
        GlStateManager.ÂµÈ();
        GlStateManager.Å();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        this.HorizonCode_Horizon_È(0, n2);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(false);
        this.á.¥à().HorizonCode_Horizon_È(TextureMap.à);
        GlStateManager.áˆºÑ¢Õ(7425);
        if (Config.Ñ¢à()) {
            this.á.ÇŽÕ.Ý("translucent");
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            áˆºÑ¢Õ.HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá, n2, n, çŽá€);
            GlStateManager.ÂµÈ();
        }
        else {
            this.á.ÇŽÕ.Ý("translucent");
            áˆºÑ¢Õ.HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá, n2, n, çŽá€);
        }
        GlStateManager.áˆºÑ¢Õ(7424);
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.Å();
        GlStateManager.ÂµÈ();
        GlStateManager.£á();
        if (çŽá€.Çªà¢ + çŽá€.Ðƒáƒ() >= 128.0 + this.á.ŠÄ.¥Æ * 128.0f) {
            this.á.ÇŽÕ.Ý("aboveClouds");
            this.HorizonCode_Horizon_È(áˆºÑ¢Õ, n2, n);
        }
        if (Reflector.É.Â()) {
            this.á.ÇŽÕ.Ý("FRenderLast");
            Reflector.HorizonCode_Horizon_È(Reflector.É, áˆºÑ¢Õ, n2);
        }
        this.á.ÇŽÕ.Ý("hand");
        if (!Reflector.Â(Reflector.ŠÂµà, this.á.áˆºÑ¢Õ, n2, n)) {
            GlStateManager.ÂµÈ(256);
            this.Â(n2, n);
            this.áŒŠÆ(n2);
        }
    }
    
    private void HorizonCode_Horizon_È(final RenderGlobal renderGlobal, final float p_180447_1_, final int p_180447_2_) {
        if (this.á.ŠÄ.Âµá€()) {
            this.á.ÇŽÕ.Ý("clouds");
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            Project.gluPerspective(this.HorizonCode_Horizon_È(p_180447_1_, true), this.á.Ó / this.á.à, 0.05f, this.Ï­Ï­Ï * 4.0f);
            GlStateManager.á(5888);
            GlStateManager.Çªà¢();
            this.HorizonCode_Horizon_È(0, p_180447_1_);
            renderGlobal.Â(p_180447_1_, p_180447_2_);
            GlStateManager.£á();
            GlStateManager.Ê();
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            Project.gluPerspective(this.HorizonCode_Horizon_È(p_180447_1_, true), this.á.Ó / this.á.à, 0.05f, this.Å * MathHelper.HorizonCode_Horizon_È);
            GlStateManager.á(5888);
        }
    }
    
    private void £á() {
        float áˆºÑ¢Õ = this.á.áŒŠÆ.áˆºÑ¢Õ(1.0f);
        if (!Config.Ø­à()) {
            áˆºÑ¢Õ /= 2.0f;
        }
        if (áˆºÑ¢Õ != 0.0f && Config.ŠÂµà()) {
            this.£á.setSeed(this.µà * 312987231L);
            final Entity çŽá€ = this.á.ÇŽá€();
            final WorldClient áœšæ = this.á.áŒŠÆ;
            final BlockPos p_175725_1_ = new BlockPos(çŽá€);
            final int n = 10;
            double n2 = 0.0;
            double n3 = 0.0;
            double n4 = 0.0;
            int n5 = 0;
            int n6 = (int)(100.0f * áˆºÑ¢Õ * áˆºÑ¢Õ);
            if (this.á.ŠÄ.áˆºá == 1) {
                n6 >>= 1;
            }
            else if (this.á.ŠÄ.áˆºá == 2) {
                n6 = 0;
            }
            for (int i = 0; i < n6; ++i) {
                final BlockPos µà = áœšæ.µà(p_175725_1_.Â(this.£á.nextInt(n) - this.£á.nextInt(n), 0, this.£á.nextInt(n) - this.£á.nextInt(n)));
                final BiomeGenBase ý = áœšæ.Ý(µà);
                final BlockPos âµá€ = µà.Âµá€();
                final Block ý2 = áœšæ.Â(âµá€).Ý();
                if (µà.Â() <= p_175725_1_.Â() + n && µà.Â() >= p_175725_1_.Â() - n && ý.Âµá€() && ý.HorizonCode_Horizon_È(µà) >= 0.15f) {
                    final float nextFloat = this.£á.nextFloat();
                    final float nextFloat2 = this.£á.nextFloat();
                    if (ý2.Ó() == Material.áŒŠÆ) {
                        this.á.áŒŠÆ.HorizonCode_Horizon_È(EnumParticleTypes.á, µà.HorizonCode_Horizon_È() + nextFloat, µà.Â() + 0.1f - ý2.Ø­à(), µà.Ý() + nextFloat2, 0.0, 0.0, 0.0, new int[0]);
                    }
                    else if (ý2.Ó() != Material.HorizonCode_Horizon_È) {
                        ý2.Ý((IBlockAccess)áœšæ, âµá€);
                        ++n5;
                        if (this.£á.nextInt(n5) == 0) {
                            n2 = âµá€.HorizonCode_Horizon_È() + nextFloat;
                            n3 = âµá€.Â() + 0.1f + ý2.µÕ() - 1.0;
                            n4 = âµá€.Ý() + nextFloat2;
                        }
                        this.á.áŒŠÆ.HorizonCode_Horizon_È(EnumParticleTypes.¥à, âµá€.HorizonCode_Horizon_È() + nextFloat, âµá€.Â() + 0.1f + ý2.µÕ(), âµá€.Ý() + nextFloat2, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
            }
            if (n5 > 0 && this.£á.nextInt(3) < this.È++) {
                this.È = 0;
                if (n3 > p_175725_1_.Â() + 1 && áœšæ.µà(p_175725_1_).Â() > MathHelper.Ø­áŒŠá((float)p_175725_1_.Â())) {
                    this.á.áŒŠÆ.HorizonCode_Horizon_È(n2, n3, n4, "ambient.weather.rain", 0.1f, 0.5f, false);
                }
                else {
                    this.á.áŒŠÆ.HorizonCode_Horizon_È(n2, n3, n4, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }
    
    protected void Âµá€(final float p_72867_1_) {
        if (Reflector.ÇªÓ.Â()) {
            final Object ó = Reflector.Ó(this.á.áŒŠÆ.£à, Reflector.ÇªÓ, new Object[0]);
            if (ó != null) {
                Reflector.HorizonCode_Horizon_È(ó, Reflector.£Ï, p_72867_1_, this.á.áŒŠÆ, this.á);
                return;
            }
        }
        final float áˆºÑ¢Õ = this.á.áŒŠÆ.áˆºÑ¢Õ(p_72867_1_);
        if (áˆºÑ¢Õ > 0.0f) {
            if (Config.µÕ()) {
                return;
            }
            this.à();
            final Entity çŽá€ = this.á.ÇŽá€();
            final WorldClient áœšæ = this.á.áŒŠÆ;
            final int ý = MathHelper.Ý(çŽá€.ŒÏ);
            final int ý2 = MathHelper.Ý(çŽá€.Çªà¢);
            final int ý3 = MathHelper.Ý(çŽá€.Ê);
            final Tessellator horizonCode_Horizon_È = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer ý4 = horizonCode_Horizon_È.Ý();
            GlStateManager.£à();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
            final double n = çŽá€.áˆºáˆºÈ + (çŽá€.ŒÏ - çŽá€.áˆºáˆºÈ) * p_72867_1_;
            final double p_76128_0_ = çŽá€.ÇŽá€ + (çŽá€.Çªà¢ - çŽá€.ÇŽá€) * p_72867_1_;
            final double n2 = çŽá€.Ï + (çŽá€.Ê - çŽá€.Ï) * p_72867_1_;
            final int ý5 = MathHelper.Ý(p_76128_0_);
            int n3 = 5;
            if (Config.Ø­à()) {
                n3 = 10;
            }
            int n4 = -1;
            final float n5 = this.µà + p_72867_1_;
            if (Config.Ø­à()) {
                n3 = 10;
            }
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            for (int i = ý3 - n3; i <= ý3 + n3; ++i) {
                for (int j = ý - n3; j <= ý + n3; ++j) {
                    final int n6 = (i - ý3 + 16) * 32 + j - ý + 16;
                    final float n7 = this.áŠ[n6] * 0.5f;
                    final float n8 = this.ˆáŠ[n6] * 0.5f;
                    final BlockPos blockPos = new BlockPos(j, 0, i);
                    final BiomeGenBase ý6 = áœšæ.Ý(blockPos);
                    if (ý6.Âµá€() || ý6.Ø­áŒŠá()) {
                        final int â = áœšæ.µà(blockPos).Â();
                        int y = ý2 - n3;
                        int n9 = ý2 + n3;
                        if (y < â) {
                            y = â;
                        }
                        if (n9 < â) {
                            n9 = â;
                        }
                        final float n10 = 1.0f;
                        int n11;
                        if ((n11 = â) < ý5) {
                            n11 = ý5;
                        }
                        if (y != n9) {
                            this.£á.setSeed(j * j * 3121 + j * 45238971 ^ i * i * 418711 + i * 13761);
                            if (áœšæ.áŒŠÆ().HorizonCode_Horizon_È(ý6.HorizonCode_Horizon_È(new BlockPos(j, y, i)), â) >= 0.15f) {
                                if (n4 != 0) {
                                    if (n4 >= 0) {
                                        horizonCode_Horizon_È.Â();
                                    }
                                    n4 = 0;
                                    this.á.¥à().HorizonCode_Horizon_È(EntityRenderer.áˆºÑ¢Õ);
                                    ý4.Â();
                                }
                                final float n12 = ((this.µà + j * j * 3121 + j * 45238971 + i * i * 418711 + i * 13761 & 0x1F) + p_72867_1_) / 32.0f * (3.0f + this.£á.nextFloat());
                                final double n13 = j + 0.5f - çŽá€.ŒÏ;
                                final double n14 = i + 0.5f - çŽá€.Ê;
                                final float n15 = MathHelper.HorizonCode_Horizon_È(n13 * n13 + n14 * n14) / n3;
                                final float n16 = 1.0f;
                                ý4.Â(áœšæ.HorizonCode_Horizon_È(new BlockPos(j, n11, i), 0));
                                ý4.HorizonCode_Horizon_È(n16, n16, n16, ((1.0f - n15 * n15) * 0.5f + 0.5f) * áˆºÑ¢Õ);
                                ý4.Ý(-n * 1.0, -p_76128_0_ * 1.0, -n2 * 1.0);
                                ý4.HorizonCode_Horizon_È(j - n7 + 0.5, y, i - n8 + 0.5, 0.0f * n10, y * n10 / 4.0f + n12 * n10);
                                ý4.HorizonCode_Horizon_È(j + n7 + 0.5, y, i + n8 + 0.5, 1.0f * n10, y * n10 / 4.0f + n12 * n10);
                                ý4.HorizonCode_Horizon_È(j + n7 + 0.5, n9, i + n8 + 0.5, 1.0f * n10, n9 * n10 / 4.0f + n12 * n10);
                                ý4.HorizonCode_Horizon_È(j - n7 + 0.5, n9, i - n8 + 0.5, 0.0f * n10, n9 * n10 / 4.0f + n12 * n10);
                                ý4.Ý(0.0, 0.0, 0.0);
                            }
                            else {
                                if (n4 != 1) {
                                    if (n4 >= 0) {
                                        horizonCode_Horizon_È.Â();
                                    }
                                    n4 = 1;
                                    this.á.¥à().HorizonCode_Horizon_È(EntityRenderer.ÂµÈ);
                                    ý4.Â();
                                }
                                final float n17 = ((this.µà & 0x1FF) + p_72867_1_) / 512.0f;
                                final float n18 = this.£á.nextFloat() + n5 * 0.01f * (float)this.£á.nextGaussian();
                                final float n19 = this.£á.nextFloat() + n5 * (float)this.£á.nextGaussian() * 0.001f;
                                final double n20 = j + 0.5f - çŽá€.ŒÏ;
                                final double n21 = i + 0.5f - çŽá€.Ê;
                                final float n22 = MathHelper.HorizonCode_Horizon_È(n20 * n20 + n21 * n21) / n3;
                                final float n23 = 1.0f;
                                ý4.Â((áœšæ.HorizonCode_Horizon_È(new BlockPos(j, n11, i), 0) * 3 + 15728880) / 4);
                                ý4.HorizonCode_Horizon_È(n23, n23, n23, ((1.0f - n22 * n22) * 0.3f + 0.5f) * áˆºÑ¢Õ);
                                ý4.Ý(-n * 1.0, -p_76128_0_ * 1.0, -n2 * 1.0);
                                ý4.HorizonCode_Horizon_È(j - n7 + 0.5, y, i - n8 + 0.5, 0.0f * n10 + n18, y * n10 / 4.0f + n17 * n10 + n19);
                                ý4.HorizonCode_Horizon_È(j + n7 + 0.5, y, i + n8 + 0.5, 1.0f * n10 + n18, y * n10 / 4.0f + n17 * n10 + n19);
                                ý4.HorizonCode_Horizon_È(j + n7 + 0.5, n9, i + n8 + 0.5, 1.0f * n10 + n18, n9 * n10 / 4.0f + n17 * n10 + n19);
                                ý4.HorizonCode_Horizon_È(j - n7 + 0.5, n9, i - n8 + 0.5, 0.0f * n10 + n18, n9 * n10 / 4.0f + n17 * n10 + n19);
                                ý4.Ý(0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (n4 >= 0) {
                horizonCode_Horizon_È.Â();
            }
            GlStateManager.Å();
            GlStateManager.ÂµÈ();
            GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
            this.Ó();
        }
    }
    
    public void Ø() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.á, this.á.Ó, this.á.à);
        GlStateManager.ÂµÈ(256);
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        GlStateManager.HorizonCode_Horizon_È(0.0, scaledResolution.Ý(), scaledResolution.Ø­áŒŠá(), 0.0, 1000.0, 3000.0);
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        GlStateManager.Â(0.0f, 0.0f, -2000.0f);
    }
    
    private void áˆºÑ¢Õ(final float n) {
        final WorldClient áœšæ = this.á.áŒŠÆ;
        final Entity çŽá€ = this.á.ÇŽá€();
        final float n2 = 1.0f - (float)Math.pow(0.25f + 0.75f * this.á.ŠÄ.Ý / 32.0f, 0.25);
        final Vec3 â = CustomColorizer.Â(áœšæ.HorizonCode_Horizon_È(this.á.ÇŽá€(), n), áœšæ, this.á.ÇŽá€(), n);
        final float n3 = (float)â.HorizonCode_Horizon_È;
        final float n4 = (float)â.Â;
        final float n5 = (float)â.Ý;
        final Vec3 horizonCode_Horizon_È = CustomColorizer.HorizonCode_Horizon_È(áœšæ.Ó(n), áœšæ, this.á.ÇŽá€(), n);
        this.Ø­áŒŠá = (float)horizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.Âµá€ = (float)horizonCode_Horizon_È.Â;
        this.Ó = (float)horizonCode_Horizon_È.Ý;
        if (this.á.ŠÄ.Ý >= 4) {
            final double x = -1.0;
            float n6 = (float)çŽá€.Ó(n).Â((MathHelper.HorizonCode_Horizon_È(áœšæ.Ø­áŒŠá(n)) > 0.0f) ? new Vec3(x, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0));
            if (n6 < 0.0f) {
                n6 = 0.0f;
            }
            if (n6 > 0.0f) {
                final float[] horizonCode_Horizon_È2 = áœšæ.£à.HorizonCode_Horizon_È(áœšæ.Ý(n), n);
                if (horizonCode_Horizon_È2 != null) {
                    final float n7 = n6 * horizonCode_Horizon_È2[3];
                    this.Ø­áŒŠá = this.Ø­áŒŠá * (1.0f - n7) + horizonCode_Horizon_È2[0] * n7;
                    this.Âµá€ = this.Âµá€ * (1.0f - n7) + horizonCode_Horizon_È2[1] * n7;
                    this.Ó = this.Ó * (1.0f - n7) + horizonCode_Horizon_È2[2] * n7;
                }
            }
        }
        this.Ø­áŒŠá += (n3 - this.Ø­áŒŠá) * n2;
        this.Âµá€ += (n4 - this.Âµá€) * n2;
        this.Ó += (n5 - this.Ó) * n2;
        final float áˆºÑ¢Õ = áœšæ.áˆºÑ¢Õ(n);
        if (áˆºÑ¢Õ > 0.0f) {
            final float n8 = 1.0f - áˆºÑ¢Õ * 0.5f;
            final float n9 = 1.0f - áˆºÑ¢Õ * 0.4f;
            this.Ø­áŒŠá *= n8;
            this.Âµá€ *= n8;
            this.Ó *= n9;
        }
        final float ø = áœšæ.Ø(n);
        if (ø > 0.0f) {
            final float n10 = 1.0f - ø * 0.5f;
            this.Ø­áŒŠá *= n10;
            this.Âµá€ *= n10;
            this.Ó *= n10;
        }
        final Block horizonCode_Horizon_È3 = ActiveRenderInfo.HorizonCode_Horizon_È(this.á.áŒŠÆ, çŽá€, n);
        if (this.ˆá) {
            final Vec3 âµá€ = áœšæ.Âµá€(n);
            this.Ø­áŒŠá = (float)âµá€.HorizonCode_Horizon_È;
            this.Âµá€ = (float)âµá€.Â;
            this.Ó = (float)âµá€.Ý;
        }
        else if (horizonCode_Horizon_È3.Ó() == Material.Ø) {
            float n11 = EnchantmentHelper.HorizonCode_Horizon_È(çŽá€) * 0.2f;
            if (çŽá€ instanceof EntityLivingBase && ((EntityLivingBase)çŽá€).HorizonCode_Horizon_È(Potion.Å)) {
                n11 = n11 * 0.3f + 0.6f;
            }
            this.Ø­áŒŠá = 0.02f + n11;
            this.Âµá€ = 0.02f + n11;
            this.Ó = 0.2f + n11;
            final Vec3 horizonCode_Horizon_È4 = CustomColorizer.HorizonCode_Horizon_È(this.á.áŒŠÆ, this.á.ÇŽá€().ŒÏ, this.á.ÇŽá€().Çªà¢ + 1.0, this.á.ÇŽá€().Ê);
            if (horizonCode_Horizon_È4 != null) {
                this.Ø­áŒŠá = (float)horizonCode_Horizon_È4.HorizonCode_Horizon_È;
                this.Âµá€ = (float)horizonCode_Horizon_È4.Â;
                this.Ó = (float)horizonCode_Horizon_È4.Ý;
            }
        }
        else if (horizonCode_Horizon_È3.Ó() == Material.áŒŠÆ) {
            this.Ø­áŒŠá = 0.6f;
            this.Âµá€ = 0.1f;
            this.Ó = 0.0f;
        }
        final float n12 = this.£ÂµÄ + (this.Ø­Âµ - this.£ÂµÄ) * n;
        this.Ø­áŒŠá *= n12;
        this.Âµá€ *= n12;
        this.Ó *= n12;
        double n13 = (çŽá€.ÇŽá€ + (çŽá€.Çªà¢ - çŽá€.ÇŽá€) * n) * áœšæ.£à.áˆºÑ¢Õ();
        if (çŽá€ instanceof EntityLivingBase && ((EntityLivingBase)çŽá€).HorizonCode_Horizon_È(Potion.µà)) {
            final int â2 = ((EntityLivingBase)çŽá€).Â(Potion.µà).Â();
            if (â2 < 20) {
                n13 *= 1.0f - â2 / 20.0f;
            }
            else {
                n13 = 0.0;
            }
        }
        if (n13 < 1.0) {
            if (n13 < 0.0) {
                n13 = 0.0;
            }
            final double n14 = n13 * n13;
            this.Ø­áŒŠá *= (float)n14;
            this.Âµá€ *= (float)n14;
            this.Ó *= (float)n14;
        }
        if (this.Ê > 0.0f) {
            final float n15 = this.ÇŽÉ + (this.Ê - this.ÇŽÉ) * n;
            this.Ø­áŒŠá = this.Ø­áŒŠá * (1.0f - n15) + this.Ø­áŒŠá * 0.7f * n15;
            this.Âµá€ = this.Âµá€ * (1.0f - n15) + this.Âµá€ * 0.6f * n15;
            this.Ó = this.Ó * (1.0f - n15) + this.Ó * 0.6f * n15;
        }
        if (çŽá€ instanceof EntityLivingBase && ((EntityLivingBase)çŽá€).HorizonCode_Horizon_È(Potion.ˆà)) {
            final float horizonCode_Horizon_È5 = this.HorizonCode_Horizon_È((EntityLivingBase)çŽá€, n);
            float n16 = 1.0f / this.Ø­áŒŠá;
            if (n16 > 1.0f / this.Âµá€) {
                n16 = 1.0f / this.Âµá€;
            }
            if (n16 > 1.0f / this.Ó) {
                n16 = 1.0f / this.Ó;
            }
            this.Ø­áŒŠá = this.Ø­áŒŠá * (1.0f - horizonCode_Horizon_È5) + this.Ø­áŒŠá * n16 * horizonCode_Horizon_È5;
            this.Âµá€ = this.Âµá€ * (1.0f - horizonCode_Horizon_È5) + this.Âµá€ * n16 * horizonCode_Horizon_È5;
            this.Ó = this.Ó * (1.0f - horizonCode_Horizon_È5) + this.Ó * n16 * horizonCode_Horizon_È5;
        }
        if (this.á.ŠÄ.Âµá€) {
            final float ø­áŒŠá = (this.Ø­áŒŠá * 30.0f + this.Âµá€ * 59.0f + this.Ó * 11.0f) / 100.0f;
            final float âµá€2 = (this.Ø­áŒŠá * 30.0f + this.Âµá€ * 70.0f) / 100.0f;
            final float ó = (this.Ø­áŒŠá * 30.0f + this.Ó * 70.0f) / 100.0f;
            this.Ø­áŒŠá = ø­áŒŠá;
            this.Âµá€ = âµá€2;
            this.Ó = ó;
        }
        if (Reflector.Ðƒá.Â()) {
            final Object â3 = Reflector.Â(Reflector.Ðƒá, this, çŽá€, horizonCode_Horizon_È3, n, this.Ø­áŒŠá, this.Âµá€, this.Ó);
            Reflector.HorizonCode_Horizon_È(â3);
            this.Ø­áŒŠá = Reflector.HorizonCode_Horizon_È(â3, Reflector.ˆÏ, this.Ø­áŒŠá);
            this.Âµá€ = Reflector.HorizonCode_Horizon_È(â3, Reflector.áˆºÇŽØ, this.Âµá€);
            this.Ó = Reflector.HorizonCode_Horizon_È(â3, Reflector.ÇªÂµÕ, this.Ó);
        }
        GlStateManager.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€, this.Ó, 0.0f);
    }
    
    private void HorizonCode_Horizon_È(final int n, final float p_180786_2_) {
        final Entity çŽá€ = this.á.ÇŽá€();
        this.Ø = false;
        if (çŽá€ instanceof EntityPlayer) {
            final boolean ø­áŒŠá = ((EntityPlayer)çŽá€).áˆºáˆºáŠ.Ø­áŒŠá;
        }
        GL11.glFog(2918, this.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€, this.Ó, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        final Block horizonCode_Horizon_È = ActiveRenderInfo.HorizonCode_Horizon_È(this.á.áŒŠÆ, çŽá€, p_180786_2_);
        final Object â = Reflector.Â(Reflector.áŒŠáŠ, this, çŽá€, horizonCode_Horizon_È, p_180786_2_, 0.1f);
        if (Reflector.HorizonCode_Horizon_È(â)) {
            GL11.glFogf(2914, Reflector.HorizonCode_Horizon_È(â, Reflector.ˆÓ, 0.0f));
        }
        else if (çŽá€ instanceof EntityLivingBase && ((EntityPlayer)çŽá€).HorizonCode_Horizon_È(Potion.µà)) {
            float p_179153_0_ = 5.0f;
            final int â2 = ((EntityPlayer)çŽá€).Â(Potion.µà).Â();
            if (â2 < 20) {
                p_179153_0_ = 5.0f + (this.Å - 5.0f) * (1.0f - â2 / 20.0f);
            }
            GlStateManager.Ø­áŒŠá(9729);
            if (n == -1) {
                GlStateManager.Â(0.0f);
                GlStateManager.Ý(p_179153_0_ * 0.8f);
            }
            else {
                GlStateManager.Â(p_179153_0_ * 0.25f);
                GlStateManager.Ý(p_179153_0_);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.á()) {
                GL11.glFogi(34138, 34139);
            }
        }
        else if (this.ˆá) {
            GlStateManager.Ø­áŒŠá(2048);
            GlStateManager.HorizonCode_Horizon_È(0.1f);
        }
        else if (horizonCode_Horizon_È.Ó() == Material.Ø) {
            GlStateManager.Ø­áŒŠá(2048);
            if (çŽá€ instanceof EntityLivingBase && ((EntityPlayer)çŽá€).HorizonCode_Horizon_È(Potion.Å)) {
                GlStateManager.HorizonCode_Horizon_È(0.01f);
            }
            else {
                GlStateManager.HorizonCode_Horizon_È(0.1f - EnchantmentHelper.HorizonCode_Horizon_È(çŽá€) * 0.03f);
            }
            if (Config.£Ï()) {
                GL11.glFogf(2914, 0.02f);
            }
        }
        else if (horizonCode_Horizon_È.Ó() == Material.áŒŠÆ) {
            GlStateManager.Ø­áŒŠá(2048);
            GlStateManager.HorizonCode_Horizon_È(2.0f);
        }
        else {
            final float å = this.Å;
            this.Ø = true;
            GlStateManager.Ø­áŒŠá(9729);
            if (n == -1) {
                GlStateManager.Â(0.0f);
                GlStateManager.Ý(å);
            }
            else {
                GlStateManager.Â(å * Config.Å());
                GlStateManager.Ý(å);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                if (Config.á()) {
                    GL11.glFogi(34138, 34139);
                }
                if (Config.ˆÏ­()) {
                    GL11.glFogi(34138, 34140);
                }
            }
            if (this.á.áŒŠÆ.£à.Â((int)çŽá€.ŒÏ, (int)çŽá€.Ê)) {
                GlStateManager.Â(å * 0.05f);
                GlStateManager.Ý(å);
            }
            Reflector.HorizonCode_Horizon_È(Reflector.Â(Reflector.ÇªÔ, this, çŽá€, horizonCode_Horizon_È, p_180786_2_, n, å));
        }
        GlStateManager.à();
        GlStateManager.ˆÏ­();
        GlStateManager.HorizonCode_Horizon_È(1028, 4608);
    }
    
    private FloatBuffer HorizonCode_Horizon_È(final float n, final float n2, final float n3, final float n4) {
        this.áŒŠ.clear();
        this.áŒŠ.put(n).put(n2).put(n3).put(n4);
        this.áŒŠ.flip();
        return this.áŒŠ;
    }
    
    public MapItemRenderer áŒŠÆ() {
        return this.£à;
    }
    
    private void Å() {
        this.£Õ = 0;
        if (Config.ˆà¢() && Config.¥Ï()) {
            if (this.á.Ê()) {
                final IntegratedServer ˆá = this.á.ˆá();
                if (ˆá != null) {
                    if (!this.á.áŒŠ() && !(this.á.¥Æ instanceof GuiDownloadTerrain)) {
                        if (this.ˆÐƒØ­à > 0) {
                            Lagometer.Ø.HorizonCode_Horizon_È();
                            Config.HorizonCode_Horizon_È((long)this.ˆÐƒØ­à);
                            Lagometer.Ø.Â();
                            this.£Õ = this.ˆÐƒØ­à;
                        }
                        final long £â = System.nanoTime() / 1000000L;
                        if (this.£Â != 0L && this.£Ó != 0) {
                            long n = £â - this.£Â;
                            if (n < 0L) {
                                this.£Â = £â;
                                n = 0L;
                            }
                            if (n >= 50L) {
                                this.£Â = £â;
                                final int ï­Ï­Ï = ˆá.Ï­Ï­Ï();
                                int n2 = ï­Ï­Ï - this.£Ó;
                                if (n2 < 0) {
                                    this.£Ó = ï­Ï­Ï;
                                    n2 = 0;
                                }
                                if (n2 < 1 && this.ˆÐƒØ­à < 100) {
                                    this.ˆÐƒØ­à += 2;
                                }
                                if (n2 > 1 && this.ˆÐƒØ­à > 0) {
                                    --this.ˆÐƒØ­à;
                                }
                                this.£Ó = ï­Ï­Ï;
                            }
                        }
                        else {
                            this.£Â = £â;
                            this.£Ó = ˆá.Ï­Ï­Ï();
                            this.Œà = 1.0f;
                            this.Ï­Ô = 50.0f;
                        }
                    }
                    else {
                        if (this.á.¥Æ instanceof GuiDownloadTerrain) {
                            Config.HorizonCode_Horizon_È(20L);
                        }
                        this.£Â = 0L;
                        this.£Ó = 0;
                    }
                }
            }
        }
        else {
            this.£Â = 0L;
            this.£Ó = 0;
        }
    }
    
    private void £à() {
        if (!this.£Ï) {
            TextureUtils.Ø­áŒŠá();
            RenderPlayerOF.Ø();
            this.£Ï = true;
        }
        Config.£ÇªÓ();
        Config.Ðƒà();
        final WorldClient áœšæ = this.á.áŒŠÆ;
        if (áœšæ != null && Config.£áŒŠá() != null) {
            this.á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentText("A new §eOptiFine§f version is available: §e" + (String.valueOf("HD_U".replace("HD_U", "HD Ultra").replace("L", "Light")) + " " + Config.£áŒŠá()) + "§f"));
            Config.Ó(null);
        }
        if (this.á.¥Æ instanceof GuiMainMenu) {
            this.HorizonCode_Horizon_È((GuiMainMenu)this.á.¥Æ);
        }
        if (this.Ø­á != áœšæ) {
            RandomMobs.HorizonCode_Horizon_È(this.Ø­á, áœšæ);
            Config.à();
            this.£Â = 0L;
            this.£Ó = 0;
            this.Ø­á = áœšæ;
        }
    }
    
    private void HorizonCode_Horizon_È(final GuiMainMenu guiMainMenu) {
        try {
            Object o = null;
            final Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            final int value = instance.get(5);
            final int n = instance.get(2) + 1;
            if (value == 8 && n == 4) {
                o = "Happy birthday, OptiFine!";
            }
            if (value == 14 && n == 8) {
                o = "Happy birthday, sp614x!";
            }
            if (o == null) {
                return;
            }
            final Field[] declaredFields = GuiMainMenu.class.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; ++i) {
                if (declaredFields[i].getType() == String.class) {
                    declaredFields[i].setAccessible(true);
                    declaredFields[i].set(guiMainMenu, o);
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    public void HorizonCode_Horizon_È(final double n) {
        OpenGlHelper.ÂµÈ(OpenGlHelper.µà);
        GL11.glDisable(3553);
        OpenGlHelper.ÂµÈ(OpenGlHelper.£à);
    }
    
    public static void áˆºÑ¢Õ() {
    }
}
