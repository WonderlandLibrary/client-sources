package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Map;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.GameProfile;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 Ó;
    private static final ResourceLocation_1975012498 à;
    public static TileEntitySkullRenderer HorizonCode_Horizon_È;
    private final ModelSkeletonHead Ø;
    private final ModelSkeletonHead áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000971";
    
    static {
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/entity/skeleton/skeleton.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/skeleton/wither_skeleton.png");
        Ó = new ResourceLocation_1975012498("textures/entity/zombie/zombie.png");
        à = new ResourceLocation_1975012498("textures/entity/creeper/creeper.png");
    }
    
    public TileEntitySkullRenderer() {
        this.Ø = new ModelSkeletonHead(0, 0, 64, 32);
        this.áŒŠÆ = new ModelHumanoidHead();
    }
    
    public void HorizonCode_Horizon_È(final TileEntitySkull p_180542_1_, final double p_180542_2_, final double p_180542_4_, final double p_180542_6_, final float p_180542_8_, final int p_180542_9_) {
        final EnumFacing var10 = EnumFacing.HorizonCode_Horizon_È(p_180542_1_.áˆºÑ¢Õ() & 0x7);
        this.HorizonCode_Horizon_È((float)p_180542_2_, (float)p_180542_4_, (float)p_180542_6_, var10, p_180542_1_.Ý() * 360 / 16.0f, p_180542_1_.Â(), p_180542_1_.HorizonCode_Horizon_È(), p_180542_9_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntityRendererDispatcher p_147497_1_) {
        super.HorizonCode_Horizon_È(p_147497_1_);
        TileEntitySkullRenderer.HorizonCode_Horizon_È = this;
    }
    
    public void HorizonCode_Horizon_È(final float p_180543_1_, final float p_180543_2_, final float p_180543_3_, final EnumFacing p_180543_4_, float p_180543_5_, final int p_180543_6_, final GameProfile p_180543_7_, final int p_180543_8_) {
        ModelSkeletonHead var9 = this.Ø;
        if (p_180543_8_ >= 0) {
            this.HorizonCode_Horizon_È(TileEntitySkullRenderer.Â[p_180543_8_]);
            GlStateManager.á(5890);
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(4.0f, 2.0f, 1.0f);
            GlStateManager.Â(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.á(5888);
        }
        else {
            switch (p_180543_6_) {
                default: {
                    this.HorizonCode_Horizon_È(TileEntitySkullRenderer.Ø­áŒŠá);
                    break;
                }
                case 1: {
                    this.HorizonCode_Horizon_È(TileEntitySkullRenderer.Âµá€);
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(TileEntitySkullRenderer.Ó);
                    var9 = this.áŒŠÆ;
                    break;
                }
                case 3: {
                    var9 = this.áŒŠÆ;
                    ResourceLocation_1975012498 var10 = DefaultPlayerSkin.HorizonCode_Horizon_È();
                    if (p_180543_7_ != null) {
                        final Minecraft var11 = Minecraft.áŒŠà();
                        final Map var12 = var11.áˆºáˆºÈ().HorizonCode_Horizon_È(p_180543_7_);
                        if (var12.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            var10 = var11.áˆºáˆºÈ().HorizonCode_Horizon_È(var12.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                        }
                        else {
                            final UUID var13 = EntityPlayer.HorizonCode_Horizon_È(p_180543_7_);
                            var10 = DefaultPlayerSkin.HorizonCode_Horizon_È(var13);
                        }
                    }
                    this.HorizonCode_Horizon_È(var10);
                    break;
                }
                case 4: {
                    this.HorizonCode_Horizon_È(TileEntitySkullRenderer.à);
                    break;
                }
            }
        }
        GlStateManager.Çªà¢();
        GlStateManager.£à();
        if (p_180543_4_ != EnumFacing.Â) {
            switch (TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_180543_4_.ordinal()]) {
                case 1: {
                    GlStateManager.Â(p_180543_1_ + 0.5f, p_180543_2_ + 0.25f, p_180543_3_ + 0.74f);
                    break;
                }
                case 2: {
                    GlStateManager.Â(p_180543_1_ + 0.5f, p_180543_2_ + 0.25f, p_180543_3_ + 0.26f);
                    p_180543_5_ = 180.0f;
                    break;
                }
                case 3: {
                    GlStateManager.Â(p_180543_1_ + 0.74f, p_180543_2_ + 0.25f, p_180543_3_ + 0.5f);
                    p_180543_5_ = 270.0f;
                    break;
                }
                default: {
                    GlStateManager.Â(p_180543_1_ + 0.26f, p_180543_2_ + 0.25f, p_180543_3_ + 0.5f);
                    p_180543_5_ = 90.0f;
                    break;
                }
            }
        }
        else {
            GlStateManager.Â(p_180543_1_ + 0.5f, p_180543_2_, p_180543_3_ + 0.5f);
        }
        final float var14 = 0.0625f;
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
        GlStateManager.Ø­áŒŠá();
        var9.HorizonCode_Horizon_È(null, 0.0f, 0.0f, 0.0f, p_180543_5_, 0.0f, var14);
        GlStateManager.Ê();
        if (p_180543_8_ >= 0) {
            GlStateManager.á(5890);
            GlStateManager.Ê();
            GlStateManager.á(5888);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntitySkull)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002468";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
