package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import java.util.*;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner>
{
    private static final Map<String, TimedBannerTexture> DESIGNS;
    private ModelBanner bannerModel;
    private static final ResourceLocation BANNERTEXTURES;
    private static final String[] I;
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityBanner)tileEntity, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        DESIGNS = Maps.newHashMap();
        BANNERTEXTURES = new ResourceLocation(TileEntityBannerRenderer.I["".length()]);
    }
    
    public TileEntityBannerRenderer() {
        this.bannerModel = new ModelBanner();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("<\u0001 !<:\u0001+z,&\u00101!0g\u00069;'-\u0016\u00077(;\u0001v%'/", "HdXUI");
        TileEntityBannerRenderer.I[" ".length()] = I("7\f2\u000331\f9X#-\u001d#\u0003?l\u000b+\u0019(&\u001be", "CiJwF");
        TileEntityBannerRenderer.I["  ".length()] = I("e\u001a\u00161", "KjxVL");
    }
    
    @Override
    public void renderTileEntityAt(final TileEntityBanner tileEntityBanner, final double n, final double n2, final double n3, final float n4, final int n5) {
        int n6;
        if (tileEntityBanner.getWorld() != null) {
            n6 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n6 = "".length();
        }
        final int n7 = n6;
        int n8;
        if (n7 != 0 && tileEntityBanner.getBlockType() != Blocks.standing_banner) {
            n8 = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n8 = " ".length();
        }
        final int n9 = n8;
        int n10;
        if (n7 != 0) {
            n10 = tileEntityBanner.getBlockMetadata();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n10 = "".length();
        }
        final int n11 = n10;
        long totalWorldTime;
        if (n7 != 0) {
            totalWorldTime = tileEntityBanner.getWorld().getTotalWorldTime();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            totalWorldTime = 0L;
        }
        final long n12 = totalWorldTime;
        GlStateManager.pushMatrix();
        final float n13 = 0.6666667f;
        if (n9 != 0) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n13, (float)n3 + 0.5f);
            GlStateManager.rotate(-(n11 * (356 + 122 - 470 + 352) / 16.0f), 0.0f, 1.0f, 0.0f);
            this.bannerModel.bannerStand.showModel = (" ".length() != 0);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            float n14 = 0.0f;
            if (n11 == "  ".length()) {
                n14 = 180.0f;
            }
            if (n11 == (0xA0 ^ 0xA4)) {
                n14 = 90.0f;
            }
            if (n11 == (0x5A ^ 0x5F)) {
                n14 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 - 0.25f * n13, (float)n3 + 0.5f);
            GlStateManager.rotate(-n14, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.bannerModel.bannerStand.showModel = ("".length() != 0);
        }
        final BlockPos pos = tileEntityBanner.getPos();
        this.bannerModel.bannerSlate.rotateAngleX = (-0.0125f + 0.01f * MathHelper.cos((pos.getX() * (0x29 ^ 0x2E) + pos.getY() * (0x97 ^ 0x9E) + pos.getZ() * (0x14 ^ 0x19) + n12 + n4) * 3.1415927f * 0.02f)) * 3.1415927f;
        GlStateManager.enableRescaleNormal();
        final ResourceLocation func_178463_a = this.func_178463_a(tileEntityBanner);
        if (func_178463_a != null) {
            this.bindTexture(func_178463_a);
            GlStateManager.pushMatrix();
            GlStateManager.scale(n13, -n13, -n13);
            this.bannerModel.renderBanner();
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private ResourceLocation func_178463_a(final TileEntityBanner tileEntityBanner) {
        final String func_175116_e = tileEntityBanner.func_175116_e();
        if (func_175116_e.isEmpty()) {
            return null;
        }
        TimedBannerTexture timedBannerTexture = TileEntityBannerRenderer.DESIGNS.get(func_175116_e);
        if (timedBannerTexture == null) {
            if (TileEntityBannerRenderer.DESIGNS.size() >= 138 + 68 - 159 + 209) {
                final long currentTimeMillis = System.currentTimeMillis();
                final Iterator<String> iterator = TileEntityBannerRenderer.DESIGNS.keySet().iterator();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final TimedBannerTexture timedBannerTexture2 = TileEntityBannerRenderer.DESIGNS.get(iterator.next());
                    if (currentTimeMillis - timedBannerTexture2.systemTime > 60000L) {
                        Minecraft.getMinecraft().getTextureManager().deleteTexture(timedBannerTexture2.bannerTexture);
                        iterator.remove();
                    }
                }
                if (TileEntityBannerRenderer.DESIGNS.size() >= 227 + 91 - 193 + 131) {
                    return null;
                }
            }
            final List<TileEntityBanner.EnumBannerPattern> patternList = tileEntityBanner.getPatternList();
            final List<EnumDyeColor> colorList = tileEntityBanner.getColorList();
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator<TileEntityBanner.EnumBannerPattern> iterator2 = patternList.iterator();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                arrayList.add(TileEntityBannerRenderer.I[" ".length()] + iterator2.next().getPatternName() + TileEntityBannerRenderer.I["  ".length()]);
            }
            timedBannerTexture = new TimedBannerTexture(null);
            timedBannerTexture.bannerTexture = new ResourceLocation(func_175116_e);
            Minecraft.getMinecraft().getTextureManager().loadTexture(timedBannerTexture.bannerTexture, new LayeredColorMaskTexture(TileEntityBannerRenderer.BANNERTEXTURES, arrayList, colorList));
            TileEntityBannerRenderer.DESIGNS.put(func_175116_e, timedBannerTexture);
        }
        timedBannerTexture.systemTime = System.currentTimeMillis();
        return timedBannerTexture.bannerTexture;
    }
    
    static class TimedBannerTexture
    {
        public long systemTime;
        public ResourceLocation bannerTexture;
        
        TimedBannerTexture(final TimedBannerTexture timedBannerTexture) {
            this();
        }
        
        private TimedBannerTexture() {
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
