package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.tileentity.*;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer<TileEntitySkull>
{
    private static final String[] I;
    private static final ResourceLocation ZOMBIE_TEXTURES;
    private static final ResourceLocation CREEPER_TEXTURES;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    private static final ResourceLocation WITHER_SKELETON_TEXTURES;
    public static TileEntitySkullRenderer instance;
    private final ModelSkeletonHead humanoidHead;
    private static final ResourceLocation SKELETON_TEXTURES;
    private final ModelSkeletonHead skeletonHead;
    
    public void renderSkull(final float n, final float n2, final float n3, final EnumFacing enumFacing, float n4, final int n5, final GameProfile gameProfile, final int n6) {
        ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
        if (n6 >= 0) {
            this.bindTexture(TileEntitySkullRenderer.DESTROY_STAGES[n6]);
            GlStateManager.matrixMode(3455 + 1930 - 1245 + 1750);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5101 + 4442 - 4465 + 810);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            switch (n5) {
                default: {
                    this.bindTexture(TileEntitySkullRenderer.SKELETON_TEXTURES);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    this.bindTexture(TileEntitySkullRenderer.WITHER_SKELETON_TEXTURES);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.bindTexture(TileEntitySkullRenderer.ZOMBIE_TEXTURES);
                    modelSkeletonHead = this.humanoidHead;
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    modelSkeletonHead = this.humanoidHead;
                    ResourceLocation resourceLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
                    if (gameProfile != null) {
                        final Minecraft minecraft = Minecraft.getMinecraft();
                        final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
                        if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            resourceLocation = minecraft.getSkinManager().loadSkin(loadSkinFromCache.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            resourceLocation = DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(gameProfile));
                        }
                    }
                    this.bindTexture(resourceLocation);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.bindTexture(TileEntitySkullRenderer.CREEPER_TEXTURES);
                    break;
                }
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        if (enumFacing != EnumFacing.UP) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 3: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.74f);
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.26f);
                    n4 = 180.0f;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    GlStateManager.translate(n + 0.74f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 270.0f;
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                default: {
                    GlStateManager.translate(n + 0.26f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 90.0f;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
            }
        }
        else {
            GlStateManager.translate(n + 0.5f, n2, n3 + 0.5f);
        }
        final float n7 = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        modelSkeletonHead.render(null, 0.0f, 0.0f, 0.0f, n4, 0.0f, n7);
        GlStateManager.popMatrix();
        if (n6 >= 0) {
            GlStateManager.matrixMode(4203 + 4133 - 3744 + 1298);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5232 + 3493 - 3187 + 350);
        }
    }
    
    @Override
    public void setRendererDispatcher(final TileEntityRendererDispatcher rendererDispatcher) {
        super.setRendererDispatcher(rendererDispatcher);
        TileEntitySkullRenderer.instance = this;
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
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        SKELETON_TEXTURES = new ResourceLocation(TileEntitySkullRenderer.I["".length()]);
        WITHER_SKELETON_TEXTURES = new ResourceLocation(TileEntitySkullRenderer.I[" ".length()]);
        ZOMBIE_TEXTURES = new ResourceLocation(TileEntitySkullRenderer.I["  ".length()]);
        CREEPER_TEXTURES = new ResourceLocation(TileEntitySkullRenderer.I["   ".length()]);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntitySkull tileEntitySkull, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderSkull((float)n, (float)n2, (float)n3, EnumFacing.getFront(tileEntitySkull.getBlockMetadata() & (0xB4 ^ 0xB3)), tileEntitySkull.getSkullRotation() * (34 + 141 - 14 + 199) / 16.0f, tileEntitySkull.getSkullType(), tileEntitySkull.getPlayerProfile(), n5);
    }
    
    public TileEntitySkullRenderer() {
        this.skeletonHead = new ModelSkeletonHead("".length(), "".length(), 0x26 ^ 0x66, 0x7F ^ 0x5F);
        this.humanoidHead = new ModelHumanoidHead();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntitySkull)tileEntity, n, n2, n3, n4, n5);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = TileEntitySkullRenderer.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xB4 ^ 0xB2);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x61 ^ 0x65);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xC7 ^ 0xC2);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return TileEntitySkullRenderer.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    private static void I() {
        (I = new String[0xB7 ^ 0xB3])["".length()] = I("\u0006\u0000:19\u0000\u00001j)\u001c\u0011+15]\u0016)  \u0017\u0011-+c\u0001\u000e'))\u0006\n,k<\u001c\u0002", "reBEL");
        TileEntitySkullRenderer.I[" ".length()] = I("\u0017/\u0012\u0019\u0007\u0011/\u0019B\u0017\r>\u0003\u0019\u000bL9\u0001\b\u001e\u0006>\u0005\u0003]\u0014#\u001e\u0005\u0017\u0011\u0015\u0019\u0006\u0017\u000f/\u001e\u0002\u001cM:\u0004\n", "cJjmr");
        TileEntitySkullRenderer.I["  ".length()] = I("\u001c\u0015:\u0019&\u001a\u00151B6\u0006\u0004+\u0019*G\n-\u00001\u0001\u0015m\u0017<\u0005\u0012+\b}\u0018\u001e%", "hpBmS");
        TileEntitySkullRenderer.I["   ".length()] = I("=\b\u001e:\u0016;\b\u0015a\u0006'\u0019\u000f:\u001af\u000e\u0014+\u00069\b\u0014a\u0000;\b\u0003>\u0006;C\u0016 \u0004", "ImfNc");
    }
}
