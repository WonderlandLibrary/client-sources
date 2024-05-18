package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import optfine.*;
import java.awt.image.*;
import java.awt.*;
import org.apache.commons.io.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private static final String __OBFID;
    private NetworkPlayerInfo playerInfo;
    private static final String[] I;
    private ResourceLocation ofLocationCape;
    
    static void access$1(final AbstractClientPlayer abstractClientPlayer, final ResourceLocation ofLocationCape) {
        abstractClientPlayer.ofLocationCape = ofLocationCape;
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocation, final String s) {
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject texture = textureManager.getTexture(resourceLocation);
        if (texture == null) {
            final File file = null;
            final String s2 = AbstractClientPlayer.I["".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = StringUtils.stripControlCodes(s);
            texture = new ThreadDownloadImageData(file, String.format(s2, array), DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(s)), new ImageBufferDownload());
            textureManager.loadTexture(resourceLocation, texture);
        }
        return (ThreadDownloadImageData)texture;
    }
    
    @Override
    public boolean isSpectator() {
        final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        if (playerInfo != null && playerInfo.getGameType() == WorldSettings.GameType.SPECTATOR) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean hasPlayerInfo() {
        if (this.getPlayerInfo() != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }
    
    public float getFovModifier() {
        float n = 1.0f;
        if (this.capabilities.isFlying) {
            n *= 1.1f;
        }
        float n2 = (float)(n * ((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(n2) || Float.isInfinite(n2)) {
            n2 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final float n3 = this.getItemInUseDuration() / 20.0f;
            float n4;
            if (n3 > 1.0f) {
                n4 = 1.0f;
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                n4 = n3 * n3;
            }
            n2 *= 1.0f - n4 * 0.15f;
        }
        float callFloat;
        if (Reflector.ForgeHooksClient_getOffsetFOV.exists()) {
            final ReflectorMethod forgeHooksClient_getOffsetFOV = Reflector.ForgeHooksClient_getOffsetFOV;
            final Object[] array = new Object["  ".length()];
            array["".length()] = this;
            array[" ".length()] = n2;
            callFloat = Reflector.callFloat(forgeHooksClient_getOffsetFOV, array);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            callFloat = n2;
        }
        return callFloat;
    }
    
    static BufferedImage access$0(final AbstractClientPlayer abstractClientPlayer, final BufferedImage bufferedImage) {
        return abstractClientPlayer.parseCape(bufferedImage);
    }
    
    public AbstractClientPlayer(final World world, final GameProfile gameProfile) {
        super(world, gameProfile);
        this.ofLocationCape = null;
        this.downloadCape(gameProfile.getName());
        PlayerConfigurations.getPlayerConfiguration(this);
    }
    
    private static void I() {
        (I = new String[0x48 ^ 0x4E])["".length()] = I("\u00018\u001c8jFc\u001b#9\u0007?F%9\u0007)\u000b:1\u000f8F&5\u001dc%!>\f/\u001a)6\u001d\u001f\u0003!>\u001acM;~\u0019\"\u000f", "iLhHP");
        AbstractClientPlayer.I[" ".length()] = I(":* 8>f", "IAIVM");
        AbstractClientPlayer.I["  ".length()] = I("\u0005\u00102<lBK5b9\u001d\u0010/*?\u0003\u0001h\"3\u0019K%-&\b\u0017i", "mdFLV");
        AbstractClientPlayer.I["   ".length()] = I("~\u0005\u0002\u0013", "PultT");
        AbstractClientPlayer.I[0x27 ^ 0x23] = I("2\u0000\n=\f7N", "QazXc");
        AbstractClientPlayer.I[0xA6 ^ 0xA3] = I("-&\u001cq@^ZsxC[", "njCAp");
    }
    
    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.ofLocationCape != null) {
            return this.ofLocationCape;
        }
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        ResourceLocation locationCape;
        if (playerInfo == null) {
            locationCape = null;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            locationCape = playerInfo.getLocationCape();
        }
        return locationCape;
    }
    
    public String getSkinType() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        String s;
        if (playerInfo == null) {
            s = DefaultPlayerSkin.getSkinType(this.getUniqueID());
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            s = playerInfo.getSkinType();
        }
        return s;
    }
    
    public static ResourceLocation getLocationSkin(final String s) {
        return new ResourceLocation(AbstractClientPlayer.I[" ".length()] + StringUtils.stripControlCodes(s));
    }
    
    private BufferedImage parseCape(final BufferedImage bufferedImage) {
        int n = 0x85 ^ 0xC5;
        int n2 = 0xD ^ 0x2D;
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (n < width || n2 < height) {
            n *= "  ".length();
            n2 *= "  ".length();
        }
        final BufferedImage bufferedImage2 = new BufferedImage(n, n2, "  ".length());
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, "".length(), "".length(), null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        if (playerInfo != null && playerInfo.hasLocationSkin()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        ResourceLocation resourceLocation;
        if (playerInfo == null) {
            resourceLocation = DefaultPlayerSkin.getDefaultSkin(this.getUniqueID());
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            resourceLocation = playerInfo.getLocationSkin();
        }
        return resourceLocation;
    }
    
    static {
        I();
        __OBFID = AbstractClientPlayer.I[0x52 ^ 0x57];
    }
    
    private void downloadCape(String stripControlCodes) {
        if (stripControlCodes != null && !stripControlCodes.isEmpty()) {
            stripControlCodes = StringUtils.stripControlCodes(stripControlCodes);
            final String string = AbstractClientPlayer.I["  ".length()] + stripControlCodes + AbstractClientPlayer.I["   ".length()];
            final ResourceLocation ofLocationCape = new ResourceLocation(AbstractClientPlayer.I[0x12 ^ 0x16] + FilenameUtils.getBaseName(string));
            final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject texture = textureManager.getTexture(ofLocationCape);
            if (texture != null && texture instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texture;
                if (threadDownloadImageData.imageFound != null) {
                    if (threadDownloadImageData.imageFound) {
                        this.ofLocationCape = ofLocationCape;
                    }
                    return;
                }
            }
            textureManager.loadTexture(ofLocationCape, new ThreadDownloadImageData(null, string, null, new IImageBuffer(this, ofLocationCape) {
                final AbstractClientPlayer this$0;
                ImageBufferDownload ibd = new ImageBufferDownload();
                private final ResourceLocation val$resourcelocation;
                
                @Override
                public void skinAvailable() {
                    AbstractClientPlayer.access$1(this.this$0, this.val$resourcelocation);
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
                        if (0 == 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
                    return AbstractClientPlayer.access$0(this.this$0, bufferedImage);
                }
            }));
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
