package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.io.File;
import com.mojang.authlib.GameProfile;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private NetworkPlayerInfo HorizonCode_Horizon_È;
    private ResourceLocation_1975012498 Â;
    private static final String Ý = "CL_00000935";
    
    public AbstractClientPlayer(final World worldIn, final GameProfile p_i45074_2_) {
        super(worldIn, p_i45074_2_);
        this.Â = null;
        final String username = p_i45074_2_.getName();
        this.Â(username);
        this.Ý(username);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        final NetworkPlayerInfo var1 = Minecraft.áŒŠà().µÕ().HorizonCode_Horizon_È(this.áˆºà().getId());
        return var1 != null && var1.Â() == WorldSettings.HorizonCode_Horizon_È.Âµá€;
    }
    
    public boolean d_() {
        return this.Ó() != null;
    }
    
    protected NetworkPlayerInfo Ó() {
        if (this.HorizonCode_Horizon_È == null) {
            this.HorizonCode_Horizon_È = Minecraft.áŒŠà().µÕ().HorizonCode_Horizon_È(this.£áŒŠá());
        }
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean à() {
        final NetworkPlayerInfo var1 = this.Ó();
        return var1 != null && var1.Ø­áŒŠá();
    }
    
    public ResourceLocation_1975012498 Ø() {
        final NetworkPlayerInfo var1 = this.Ó();
        return (var1 == null) ? DefaultPlayerSkin.HorizonCode_Horizon_È(this.£áŒŠá()) : var1.Ó();
    }
    
    public ResourceLocation_1975012498 áŒŠÆ() {
        if (!Config.¥Ä()) {
            return null;
        }
        if (this.Â != null) {
            return this.Â;
        }
        final NetworkPlayerInfo var1 = this.Ó();
        return (var1 == null) ? null : var1.à();
    }
    
    public static ThreadDownloadImageData HorizonCode_Horizon_È(final ResourceLocation_1975012498 resourceLocationIn, final String username) {
        final TextureManager var2 = Minecraft.áŒŠà().¥à();
        Object var3 = var2.Â(resourceLocationIn);
        if (var3 == null) {
            var3 = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.HorizonCode_Horizon_È(username)), DefaultPlayerSkin.HorizonCode_Horizon_È(EntityPlayer.Ø(username)), new ImageBufferDownload());
            var2.HorizonCode_Horizon_È(resourceLocationIn, (ITextureObject)var3);
        }
        return (ThreadDownloadImageData)var3;
    }
    
    public static ResourceLocation_1975012498 HorizonCode_Horizon_È(final String username) {
        return new ResourceLocation_1975012498("skins/" + StringUtils.HorizonCode_Horizon_È(username));
    }
    
    public String e_() {
        final NetworkPlayerInfo var1 = this.Ó();
        return (var1 == null) ? DefaultPlayerSkin.Â(this.£áŒŠá()) : var1.Âµá€();
    }
    
    public float f_() {
        float var1 = 1.0f;
        if (this.áˆºáˆºáŠ.Â) {
            var1 *= 1.1f;
        }
        final IAttributeInstance var2 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
        var1 *= (float)((var2.Âµá€() / this.áˆºáˆºáŠ.Â() + 1.0) / 2.0);
        if (this.áˆºáˆºáŠ.Â() == 0.0f || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0f;
        }
        if (this.Ñ¢Ó() && this.Š().HorizonCode_Horizon_È() == Items.Ó) {
            final int var3 = this.Ø­Æ();
            float var4 = var3 / 20.0f;
            if (var4 > 1.0f) {
                var4 = 1.0f;
            }
            else {
                var4 *= var4;
            }
            var1 *= 1.0f - var4 * 0.15f;
        }
        return var1;
    }
    
    private void Â(String username) {
        if (username != null && !username.isEmpty()) {
            username = StringUtils.HorizonCode_Horizon_È(username);
            final String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
            final MinecraftProfileTexture mpt = new MinecraftProfileTexture(ofCapeUrl, (Map)new HashMap());
            final ResourceLocation_1975012498 rl = new ResourceLocation_1975012498("skins/" + mpt.getHash());
            final IImageBuffer iib = new IImageBuffer() {
                ImageBufferDownload HorizonCode_Horizon_È = new ImageBufferDownload();
                
                @Override
                public BufferedImage HorizonCode_Horizon_È(final BufferedImage var1) {
                    return AbstractClientPlayer.this.HorizonCode_Horizon_È(var1);
                }
                
                @Override
                public void HorizonCode_Horizon_È() {
                    AbstractClientPlayer.HorizonCode_Horizon_È(AbstractClientPlayer.this, rl);
                }
            };
            final ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null, iib);
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(rl, textureCape);
        }
    }
    
    private void Ý(String username) {
        if (username != null && !username.isEmpty()) {
            username = StringUtils.HorizonCode_Horizon_È(username);
            try {
                if (Utils.HorizonCode_Horizon_È("http://horizonco.de/horizonclient/capes/checkCape.php?user=" + Horizon.É).equalsIgnoreCase("YES") && username.equalsIgnoreCase(Minecraft.áŒŠà().£à.Ý())) {
                    final String ofCapeUrl = "http://horizonco.de/horizonclient/capes/cape.png";
                    final MinecraftProfileTexture mpt = new MinecraftProfileTexture(ofCapeUrl, (Map)new HashMap());
                    final ResourceLocation_1975012498 rl = new ResourceLocation_1975012498("skins/" + mpt.getHash());
                    final IImageBuffer iib = new IImageBuffer() {
                        ImageBufferDownload HorizonCode_Horizon_È = new ImageBufferDownload();
                        
                        @Override
                        public BufferedImage HorizonCode_Horizon_È(final BufferedImage var1) {
                            return AbstractClientPlayer.this.HorizonCode_Horizon_È(var1);
                        }
                        
                        @Override
                        public void HorizonCode_Horizon_È() {
                            AbstractClientPlayer.HorizonCode_Horizon_È(AbstractClientPlayer.this, rl);
                        }
                    };
                    final ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null, iib);
                    Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(rl, textureCape);
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private BufferedImage HorizonCode_Horizon_È(final BufferedImage img) {
        int imageWidth = 64;
        int imageHeight = 32;
        for (int srcWidth = img.getWidth(), srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {}
        final BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        final Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return imgNew;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final AbstractClientPlayer abstractClientPlayer, final ResourceLocation_1975012498 â) {
        abstractClientPlayer.Â = â;
    }
}
