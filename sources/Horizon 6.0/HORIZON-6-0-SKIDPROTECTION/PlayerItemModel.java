package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class PlayerItemModel
{
    private Dimension Ø;
    private boolean áŒŠÆ;
    private PlayerItemRenderer[] áˆºÑ¢Õ;
    private ResourceLocation_1975012498 ÂµÈ;
    private BufferedImage á;
    private DynamicTexture ˆÏ­;
    private ResourceLocation_1975012498 £á;
    public static final int HorizonCode_Horizon_È = 0;
    public static final int Â = 1;
    public static final int Ý = 2;
    public static final int Ø­áŒŠá = 3;
    public static final int Âµá€ = 4;
    public static final int Ó = 5;
    public static final int à = 6;
    
    public PlayerItemModel(final Dimension textureSize, final boolean usePlayerTexture, final PlayerItemRenderer[] modelRenderers) {
        this.Ø = null;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = new PlayerItemRenderer[0];
        this.ÂµÈ = null;
        this.á = null;
        this.ˆÏ­ = null;
        this.£á = new ResourceLocation_1975012498("textures/blocks/wool_colored_red.png");
        this.Ø = textureSize;
        this.áŒŠÆ = usePlayerTexture;
        this.áˆºÑ¢Õ = modelRenderers;
    }
    
    public void HorizonCode_Horizon_È(final ModelBiped modelBiped, final AbstractClientPlayer player, final float scale, final float partialTicks) {
        final TextureManager textureManager = Config.áŠ();
        if (this.áŒŠÆ) {
            textureManager.HorizonCode_Horizon_È(player.Ø());
        }
        else if (this.ÂµÈ != null) {
            if (this.ˆÏ­ == null && this.á != null) {
                this.ˆÏ­ = new DynamicTexture(this.á);
                Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(this.ÂµÈ, this.ˆÏ­);
            }
            textureManager.HorizonCode_Horizon_È(this.ÂµÈ);
        }
        else {
            textureManager.HorizonCode_Horizon_È(this.£á);
        }
        for (int i = 0; i < this.áˆºÑ¢Õ.length; ++i) {
            final PlayerItemRenderer pir = this.áˆºÑ¢Õ[i];
            GlStateManager.Çªà¢();
            if (player.Çªà¢()) {
                GlStateManager.Â(0.0f, 0.2f, 0.0f);
            }
            pir.HorizonCode_Horizon_È(modelBiped, scale);
            GlStateManager.Ê();
        }
    }
    
    public static ModelRenderer HorizonCode_Horizon_È(final ModelBiped modelBiped, final int attachTo) {
        switch (attachTo) {
            case 0: {
                return modelBiped.ˆÏ­;
            }
            case 1: {
                return modelBiped.ÂµÈ;
            }
            case 2: {
                return modelBiped.Å;
            }
            case 3: {
                return modelBiped.£á;
            }
            case 4: {
                return modelBiped.µà;
            }
            case 5: {
                return modelBiped.£à;
            }
            default: {
                return null;
            }
        }
    }
    
    public BufferedImage HorizonCode_Horizon_È() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final BufferedImage textureImage) {
        this.á = textureImage;
    }
    
    public DynamicTexture Â() {
        return this.ˆÏ­;
    }
    
    public ResourceLocation_1975012498 Ý() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final ResourceLocation_1975012498 textureLocation) {
        this.ÂµÈ = textureLocation;
    }
    
    public boolean Ø­áŒŠá() {
        return this.áŒŠÆ;
    }
}
