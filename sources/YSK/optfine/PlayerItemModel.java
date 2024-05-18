package optfine;

import net.minecraft.util.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;

public class PlayerItemModel
{
    private static final String[] I;
    private ResourceLocation locationMissing;
    public static final int ATTACH_HEAD;
    public static final int ATTACH_RIGHT_LEG;
    private BufferedImage textureImage;
    public static final int ATTACH_CAPE;
    private Dimension textureSize;
    private PlayerItemRenderer[] modelRenderers;
    public static final int ATTACH_LEFT_LEG;
    private boolean usePlayerTexture;
    private ResourceLocation textureLocation;
    public static final int ATTACH_BODY;
    public static final int ATTACH_LEFT_ARM;
    public static final int ATTACH_RIGHT_ARM;
    private DynamicTexture texture;
    
    public BufferedImage getTextureImage() {
        return this.textureImage;
    }
    
    public boolean isUsePlayerTexture() {
        return this.usePlayerTexture;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(" (\u001d\u0006\r&(\u0016]\u001a8\"\u0006\u0019\u000b{:\n\u001d\u0014\u000b.\n\u001e\u0017&(\u0001-\n1)K\u0002\u00163", "TMerx");
    }
    
    public DynamicTexture getTexture() {
        return this.texture;
    }
    
    public void setTextureLocation(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
    
    static {
        I();
        ATTACH_LEFT_ARM = "  ".length();
        ATTACH_RIGHT_ARM = "   ".length();
        ATTACH_HEAD = " ".length();
        ATTACH_LEFT_LEG = (0xB7 ^ 0xB3);
        ATTACH_CAPE = (0x51 ^ 0x57);
        ATTACH_RIGHT_LEG = (0x4F ^ 0x4A);
        ATTACH_BODY = "".length();
    }
    
    public void render(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        final TextureManager textureManager = Config.getTextureManager();
        if (this.usePlayerTexture) {
            textureManager.bindTexture(abstractClientPlayer.getLocationSkin());
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (this.textureLocation != null) {
            if (this.texture == null && this.textureImage != null) {
                this.texture = new DynamicTexture(this.textureImage);
                Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, this.texture);
            }
            textureManager.bindTexture(this.textureLocation);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            textureManager.bindTexture(this.locationMissing);
        }
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.modelRenderers.length) {
            final PlayerItemRenderer playerItemRenderer = this.modelRenderers[i];
            GlStateManager.pushMatrix();
            if (abstractClientPlayer.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            playerItemRenderer.render(modelBiped, n);
            GlStateManager.popMatrix();
            ++i;
        }
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PlayerItemModel(final Dimension textureSize, final boolean usePlayerTexture, final PlayerItemRenderer[] modelRenderers) {
        this.textureSize = null;
        this.usePlayerTexture = ("".length() != 0);
        this.modelRenderers = new PlayerItemRenderer["".length()];
        this.textureLocation = null;
        this.textureImage = null;
        this.texture = null;
        this.locationMissing = new ResourceLocation(PlayerItemModel.I["".length()]);
        this.textureSize = textureSize;
        this.usePlayerTexture = usePlayerTexture;
        this.modelRenderers = modelRenderers;
    }
    
    public static ModelRenderer getAttachModel(final ModelBiped modelBiped, final int n) {
        switch (n) {
            case 0: {
                return modelBiped.bipedBody;
            }
            case 1: {
                return modelBiped.bipedHead;
            }
            case 2: {
                return modelBiped.bipedLeftArm;
            }
            case 3: {
                return modelBiped.bipedRightArm;
            }
            case 4: {
                return modelBiped.bipedLeftLeg;
            }
            case 5: {
                return modelBiped.bipedRightLeg;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setTextureImage(final BufferedImage textureImage) {
        this.textureImage = textureImage;
    }
}
