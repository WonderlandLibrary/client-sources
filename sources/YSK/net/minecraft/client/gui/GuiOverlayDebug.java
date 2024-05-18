package net.minecraft.client.gui;

import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import optfine.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class GuiOverlayDebug extends Gui
{
    private final Minecraft mc;
    private static final String[] I;
    private final FontRenderer fontRenderer;
    private static final String __OBFID;
    
    private boolean isReducedDebug() {
        if (!this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xB3 ^ 0x9E])["".length()] = I("\u0010*-:5", "tOOOR");
        GuiOverlayDebug.I[" ".length()] = I("<9*\u0004,\u00031\"\u0015o@~|OwQx", "qPDaO");
        GuiOverlayDebug.I["  ".length()] = I("e", "JOUaJ");
        GuiOverlayDebug.I["   ".length()] = I("g", "NPGly");
        GuiOverlayDebug.I[0x56 ^ 0x52] = I("\u0007kc", "WQCve");
        GuiOverlayDebug.I[0x6 ^ 0x3] = I("JQ\u0012ph", "dqFJH");
        GuiOverlayDebug.I[0x52 ^ 0x54] = I("", "hHegf");
        GuiOverlayDebug.I[0x63 ^ 0x64] = I("-\n\u001b\u00033C\u0010\u000b\u00019\u001a\u000b\u0018\bbNG\nM}\nBK\t", "nbnmX");
        GuiOverlayDebug.I[0x45 ^ 0x4D] = I("\u0011'\u0000\u0010\u00141-", "XIvqx");
        GuiOverlayDebug.I[0x6A ^ 0x63] = I("\u001f7 *\u0006/+w%\u0011,9#\"\u0002.x\r", "KXWKt");
        GuiOverlayDebug.I[0xB6 ^ 0xBC] = I(" \u00163\u0007\u0010\u0010\nd\u0016\r\u0007\u00100\u000f\u0014\u0011Y\u001e", "tyDfb");
        GuiOverlayDebug.I[0x63 ^ 0x68] = I("?\u0019\u0018\r;\u000f\u0005O\u0002,\f\u0017\u001b\u0005?\u000eV7", "kvolI");
        GuiOverlayDebug.I[0x62 ^ 0x6E] = I("8!.\u00198\b=y\b%\u001f'-\u0011<\tn\u0001", "lNYxJ");
        GuiOverlayDebug.I[0x7A ^ 0x77] = I("\u001b\u0018!3\u0000$\u0010)\"Cg_wx[vY", "VqOVc");
        GuiOverlayDebug.I[0x95 ^ 0x9B] = I("U", "zOWBv");
        GuiOverlayDebug.I[0x3D ^ 0x32] = I("Y", "pFTca");
        GuiOverlayDebug.I[0x73 ^ 0x63] = I("<|O", "lFotr");
        GuiOverlayDebug.I[0x90 ^ 0x81] = I("iy!}X", "GYuGx");
        GuiOverlayDebug.I[0x40 ^ 0x52] = I("", "VtVKW");
        GuiOverlayDebug.I[0x16 ^ 0x5] = I("4<\u0000jeIKi6eCE\u007f~p\nEup`BV<", "leZPE");
        GuiOverlayDebug.I[0xB7 ^ 0xA3] = I("\u0007%=\u0010%\u007fiw\u0017n`-rV*", "EIRsN");
        GuiOverlayDebug.I[0xAB ^ 0xBE] = I(")\n$\u001f%PBt\u0015nO\u0006qT*J\u000b?Qk\u000eBt\u0015nO\u0006", "jbQqN");
        GuiOverlayDebug.I[0x7F ^ 0x69] = I("\u001c\u000b-.?=Pnb\"zBk4xzBki`<Jagtt[(n", "ZjNGQ");
        GuiOverlayDebug.I[0x1C ^ 0xB] = I(":*\f\u000e3Bc", "xCccV");
        GuiOverlayDebug.I[0xB5 ^ 0xAD] = I("\u000f(0+\u001bya", "CAWCo");
        GuiOverlayDebug.I[0xAA ^ 0xB3] = I("DC", "dkTZs");
        GuiOverlayDebug.I[0x6F ^ 0x75] = I("l\u0015*\u000bNl", "LfArb");
        GuiOverlayDebug.I[0xA5 ^ 0xBE] = I("v\u0018%\u001d1=S", "VzIrR");
        GuiOverlayDebug.I[0x33 ^ 0x2F] = I("4\u0001%\u001b6X*/\u001c<\u0011\r3\u0016.\u0001Tf_tJ\bfR\u001e\u0019\u0017f_>Q", "xnFzZ");
        GuiOverlayDebug.I[0x70 ^ 0x6D] = I("0\u001c\u00110\u0010\u0011NP", "ctpTu");
        GuiOverlayDebug.I[0x81 ^ 0x9F] = I("-\u0003\n1\u0011\u000f\u000bE;\f[L@>XD\bE\u007f\u001c", "aleZx");
        GuiOverlayDebug.I[0x62 ^ 0x7D] = I("\"\u000b.\u0013_HO+R@\f\b1\u0006", "hjXre");
        GuiOverlayDebug.I[0xB4 ^ 0x94] = I("\u0003\u0007\u001e\u0015k\u001f\u0003\u001a\u0007,\u0006\b", "ifhtE");
        GuiOverlayDebug.I[0xE0 ^ 0xC1] = I("\u0006\u001f,wMnZs)HnZd}^/Ud}^/7\u0003", "KzAMm");
        GuiOverlayDebug.I[0xAB ^ 0x89] = I("\n\r\u0005);*\u0015\f\"bkDIt<nDIchx\u0005$\u0004", "KaiFX");
        GuiOverlayDebug.I[0x8C ^ 0xAF] = I("", "KkvWG");
        GuiOverlayDebug.I[0x1B ^ 0x3F] = I("\u000b:/hKm\u0019", "HjzRk");
        GuiOverlayDebug.I[0x0 ^ 0x25] = I("", "cBVqT");
        GuiOverlayDebug.I[0x3A ^ 0x1C] = I("\u0002\u001b\u00121?'\u000b[av\"\nD%snW\u0012h", "FraAS");
        GuiOverlayDebug.I[0x58 ^ 0x7F] = I("", "ZJBfT");
        GuiOverlayDebug.I[0x5C ^ 0x74] = I("", "AzkrA");
        GuiOverlayDebug.I[0x98 ^ 0xB1] = I("cB", "YbaMA");
        GuiOverlayDebug.I[0x44 ^ 0x6E] = I("fR", "PbexU");
        GuiOverlayDebug.I[0x48 ^ 0x63] = I("FW", "ugxDz");
        GuiOverlayDebug.I[0xB2 ^ 0x9E] = I("\u0005!={Fv]SrCp", "FmbKv");
    }
    
    protected List getDebugInfoRight() {
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long n = totalMemory - Runtime.getRuntime().freeMemory();
        final String[] array = new String[0xCF ^ 0xC6];
        final int length = "".length();
        final String s = GuiOverlayDebug.I[0x55 ^ 0x4A];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = System.getProperty(GuiOverlayDebug.I[0xAB ^ 0x8B]);
        final int length2 = " ".length();
        int n2;
        if (this.mc.isJava64bit()) {
            n2 = (0xE7 ^ 0xA7);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n2 = (0xAC ^ 0x8C);
        }
        array2[length2] = n2;
        array[length] = String.format(s, array2);
        final int length3 = " ".length();
        final String s2 = GuiOverlayDebug.I[0xAD ^ 0x8C];
        final Object[] array3 = new Object["   ".length()];
        array3["".length()] = n * 100L / maxMemory;
        array3[" ".length()] = bytesToMb(n);
        array3["  ".length()] = bytesToMb(maxMemory);
        array[length3] = String.format(s2, array3);
        final int length4 = "  ".length();
        final String s3 = GuiOverlayDebug.I[0x33 ^ 0x11];
        final Object[] array4 = new Object["  ".length()];
        array4["".length()] = totalMemory * 100L / maxMemory;
        array4[" ".length()] = bytesToMb(totalMemory);
        array[length4] = String.format(s3, array4);
        array["   ".length()] = GuiOverlayDebug.I[0x17 ^ 0x34];
        final int n3 = 0x77 ^ 0x73;
        final String s4 = GuiOverlayDebug.I[0x19 ^ 0x3D];
        final Object[] array5 = new Object[" ".length()];
        array5["".length()] = OpenGlHelper.func_183029_j();
        array[n3] = String.format(s4, array5);
        array[0x89 ^ 0x8C] = GuiOverlayDebug.I[0x1F ^ 0x3A];
        final int n4 = 0xA9 ^ 0xAF;
        final String s5 = GuiOverlayDebug.I[0x95 ^ 0xB3];
        final Object[] array6 = new Object["   ".length()];
        array6["".length()] = Display.getWidth();
        array6[" ".length()] = Display.getHeight();
        array6["  ".length()] = GL11.glGetString(7182 + 2484 - 4582 + 2852);
        array[n4] = String.format(s5, array6);
        array[0xBB ^ 0xBC] = GL11.glGetString(3746 + 5077 - 8222 + 7336);
        array[0x89 ^ 0x81] = GL11.glGetString(7656 + 1182 - 2371 + 1471);
        final ArrayList arrayList = Lists.newArrayList((Object[])array);
        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object["".length()]);
            arrayList.add(GuiOverlayDebug.I[0x45 ^ 0x62]);
            final ArrayList<String> list = (ArrayList<String>)arrayList;
            final Object o = call;
            final ReflectorMethod fmlCommonHandler_getBrandings = Reflector.FMLCommonHandler_getBrandings;
            final Object[] array7 = new Object[" ".length()];
            array7["".length()] = ("".length() != 0);
            list.addAll((Collection<? extends String>)Reflector.call(o, fmlCommonHandler_getBrandings, array7));
        }
        if (this.isReducedDebug()) {
            return arrayList;
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
            IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
                blockState = blockState.getBlock().getActualState(blockState, this.mc.theWorld, blockPos);
            }
            arrayList.add(GuiOverlayDebug.I[0x9C ^ 0xB4]);
            arrayList.add(String.valueOf(Block.blockRegistry.getNameForObject(blockState.getBlock())));
            final UnmodifiableIterator iterator = blockState.getProperties().entrySet().iterator();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (((Iterator)iterator).hasNext()) {
                final Map.Entry<K, Comparable<?>> entry = ((Iterator<Map.Entry<K, Comparable<?>>>)iterator).next();
                String s6 = entry.getValue().toString();
                if (entry.getValue() == Boolean.TRUE) {
                    s6 = EnumChatFormatting.GREEN + s6;
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else if (entry.getValue() == Boolean.FALSE) {
                    s6 = EnumChatFormatting.RED + s6;
                }
                arrayList.add(String.valueOf(entry.getKey().getName()) + GuiOverlayDebug.I[0x8E ^ 0xA7] + s6);
            }
        }
        return arrayList;
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int func_181553_a(final int n, final int n2, final float n3) {
        final int n4 = n >> (0x98 ^ 0x80) & 122 + 45 - 40 + 128;
        final int n5 = n >> (0x20 ^ 0x30) & 16 + 238 - 105 + 106;
        final int n6 = n >> (0x87 ^ 0x8F) & 19 + 137 - 54 + 153;
        final int n7 = n & 33 + 65 - 55 + 212;
        return MathHelper.clamp_int((int)(n4 + ((n2 >> (0xB8 ^ 0xA0) & 116 + 71 - 128 + 196) - n4) * n3), "".length(), 158 + 116 - 270 + 251) << (0xBD ^ 0xA5) | MathHelper.clamp_int((int)(n5 + ((n2 >> (0x82 ^ 0x92) & 228 + 86 - 221 + 162) - n5) * n3), "".length(), 252 + 238 - 412 + 177) << (0x93 ^ 0x83) | MathHelper.clamp_int((int)(n6 + ((n2 >> (0xB3 ^ 0xBB) & 9 + 94 - 85 + 237) - n6) * n3), "".length(), 17 + 126 - 117 + 229) << (0x3F ^ 0x37) | MathHelper.clamp_int((int)(n7 + ((n2 & 157 + 242 - 258 + 114) - n7) * n3), "".length(), 167 + 8 - 29 + 109);
    }
    
    protected void renderDebugInfoRight(final ScaledResolution scaledResolution) {
        final List debugInfoRight = this.getDebugInfoRight();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < debugInfoRight.size()) {
            final String s = debugInfoRight.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                final int n = scaledResolution.getScaledWidth() - "  ".length() - stringWidth;
                final int n2 = "  ".length() + font_HEIGHT * i;
                Gui.drawRect(n - " ".length(), n2 - " ".length(), n + stringWidth + " ".length(), n2 + font_HEIGHT - " ".length(), -(1195003520 + 1253271594 - 1006114902 + 431624540));
                this.fontRenderer.drawString(s, n, n2, 6824782 + 10382165 - 10036068 + 7566753);
            }
            ++i;
        }
    }
    
    private static long bytesToMb(final long n) {
        return n / 1024L / 1024L;
    }
    
    public GuiOverlayDebug(final Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = mc.fontRendererObj;
    }
    
    private int func_181552_c(final int n, final int n2, final int n3, final int n4) {
        int n5;
        if (n < n3) {
            n5 = this.func_181553_a(-(14582680 + 12524035 - 15933077 + 5538298), -(169 + 40 + 29 + 18), n / n3);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n5 = this.func_181553_a(-(171 + 183 - 256 + 158), -(8072 + 22975 - 12470 + 46959), (n - n3) / (n4 - n3));
        }
        return n5;
    }
    
    protected void renderDebugInfoLeft() {
        final List call = this.call();
        int i = "".length();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (i < call.size()) {
            final String s = call.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                " ".length();
                final int n = "  ".length() + font_HEIGHT * i;
                Gui.drawRect(" ".length(), n - " ".length(), "  ".length() + stringWidth + " ".length(), n + font_HEIGHT - " ".length(), -(1513851330 + 298377090 - 779510968 + 841067300));
                this.fontRenderer.drawString(s, "  ".length(), n, 8047130 + 14401618 - 15193272 + 7482156);
            }
            ++i;
        }
    }
    
    private void func_181554_e() {
        GlStateManager.disableDepth();
        final FrameTimer func_181539_aj = this.mc.func_181539_aj();
        final int func_181749_a = func_181539_aj.func_181749_a();
        final int func_181750_b = func_181539_aj.func_181750_b();
        final long[] func_181746_c = func_181539_aj.func_181746_c();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int i = func_181749_a;
        int length = "".length();
        Gui.drawRect("".length(), scaledResolution.getScaledHeight() - (0x5E ^ 0x62), 130 + 102 - 192 + 200, scaledResolution.getScaledHeight(), -(1647812085 + 796086047 + 1882533300 + 1842320616));
        "".length();
        if (true != true) {
            throw null;
        }
        while (i != func_181750_b) {
            final int func_181748_a = func_181539_aj.func_181748_a(func_181746_c[i], 0x50 ^ 0x4E);
            this.drawVerticalLine(length, scaledResolution.getScaledHeight(), scaledResolution.getScaledHeight() - func_181748_a, this.func_181552_c(MathHelper.clamp_int(func_181748_a, "".length(), 0x17 ^ 0x2B), "".length(), 0x8E ^ 0x90, 0x72 ^ 0x4E));
            ++length;
            i = func_181539_aj.func_181751_b(i + " ".length());
        }
        Gui.drawRect(" ".length(), scaledResolution.getScaledHeight() - (0x6D ^ 0x73) + " ".length(), 0x39 ^ 0x37, scaledResolution.getScaledHeight() - (0x45 ^ 0x5B) + (0x8 ^ 0x2), -(497045280 + 501422341 + 841752825 + 33564306));
        this.fontRenderer.drawString(GuiOverlayDebug.I[0x79 ^ 0x53], "  ".length(), scaledResolution.getScaledHeight() - (0x6B ^ 0x75) + "  ".length(), 4350410 + 8694673 - 2906498 + 4599047);
        this.drawHorizontalLine("".length(), 158 + 59 - 91 + 113, scaledResolution.getScaledHeight() - (0x19 ^ 0x7), -" ".length());
        Gui.drawRect(" ".length(), scaledResolution.getScaledHeight() - (0x0 ^ 0x3C) + " ".length(), 0x25 ^ 0x2B, scaledResolution.getScaledHeight() - (0xAB ^ 0x97) + (0x40 ^ 0x4A), -(1172727030 + 1282782263 - 861586663 + 279862122));
        this.fontRenderer.drawString(GuiOverlayDebug.I[0x6A ^ 0x41], "  ".length(), scaledResolution.getScaledHeight() - (0x4A ^ 0x76) + "  ".length(), 13777323 + 2862026 - 8917111 + 7015394);
        this.drawHorizontalLine("".length(), 30 + 63 + 127 + 19, scaledResolution.getScaledHeight() - (0x7D ^ 0x41), -" ".length());
        this.drawHorizontalLine("".length(), 186 + 205 - 252 + 100, scaledResolution.getScaledHeight() - " ".length(), -" ".length());
        this.drawVerticalLine("".length(), scaledResolution.getScaledHeight() - (0xA4 ^ 0x98), scaledResolution.getScaledHeight(), -" ".length());
        this.drawVerticalLine(64 + 160 - 94 + 109, scaledResolution.getScaledHeight() - (0x32 ^ 0xE), scaledResolution.getScaledHeight(), -" ".length());
        if (this.mc.gameSettings.limitFramerate <= (0xCD ^ 0xB5)) {
            this.drawHorizontalLine("".length(), 161 + 1 - 9 + 86, scaledResolution.getScaledHeight() - (0x14 ^ 0x28) + this.mc.gameSettings.limitFramerate / "  ".length(), -(6456682 + 12966882 - 6827024 + 4115141));
        }
        GlStateManager.enableDepth();
    }
    
    public void renderDebugInfo(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection(GuiOverlayDebug.I["".length()]);
        GlStateManager.pushMatrix();
        this.renderDebugInfoLeft();
        this.renderDebugInfoRight(scaledResolution);
        GlStateManager.popMatrix();
        this.mc.mcProfiler.endSection();
    }
    
    protected List call() {
        final BlockPos blockPos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
        if (this.isReducedDebug()) {
            final String[] array = new String[0xB7 ^ 0xBF];
            array["".length()] = GuiOverlayDebug.I[" ".length()] + this.mc.getVersion() + GuiOverlayDebug.I["  ".length()] + ClientBrandRetriever.getClientModName() + GuiOverlayDebug.I["   ".length()];
            array[" ".length()] = this.mc.debug;
            array["  ".length()] = this.mc.renderGlobal.getDebugInfoRenders();
            array["   ".length()] = this.mc.renderGlobal.getDebugInfoEntities();
            array[0x8 ^ 0xC] = GuiOverlayDebug.I[0x43 ^ 0x47] + this.mc.effectRenderer.getStatistics() + GuiOverlayDebug.I[0x34 ^ 0x31] + this.mc.theWorld.getDebugLoadedEntities();
            array[0x9B ^ 0x9E] = this.mc.theWorld.getProviderName();
            array[0x58 ^ 0x5E] = GuiOverlayDebug.I[0x5C ^ 0x5A];
            final int n = 0x1F ^ 0x18;
            final String s = GuiOverlayDebug.I[0x4 ^ 0x3];
            final Object[] array2 = new Object["   ".length()];
            array2["".length()] = (blockPos.getX() & (0x57 ^ 0x58));
            array2[" ".length()] = (blockPos.getY() & (0x78 ^ 0x77));
            array2["  ".length()] = (blockPos.getZ() & (0xBD ^ 0xB2));
            array[n] = String.format(s, array2);
            return Lists.newArrayList((Object[])array);
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final EnumFacing horizontalFacing = renderViewEntity.getHorizontalFacing();
        String s2 = GuiOverlayDebug.I[0x5D ^ 0x55];
        switch (GuiOverlayDebug$1.field_178907_a[horizontalFacing.ordinal()]) {
            case 1: {
                s2 = GuiOverlayDebug.I[0x21 ^ 0x28];
                "".length();
                if (2 == 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                s2 = GuiOverlayDebug.I[0x8B ^ 0x81];
                "".length();
                if (2 < 1) {
                    throw null;
                }
                break;
            }
            case 3: {
                s2 = GuiOverlayDebug.I[0x10 ^ 0x1B];
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                break;
            }
            case 4: {
                s2 = GuiOverlayDebug.I[0x5B ^ 0x57];
                break;
            }
        }
        final String[] array3 = new String[0xBB ^ 0xB0];
        array3["".length()] = GuiOverlayDebug.I[0x16 ^ 0x1B] + this.mc.getVersion() + GuiOverlayDebug.I[0x1F ^ 0x11] + ClientBrandRetriever.getClientModName() + GuiOverlayDebug.I[0x69 ^ 0x66];
        array3[" ".length()] = this.mc.debug;
        array3["  ".length()] = this.mc.renderGlobal.getDebugInfoRenders();
        array3["   ".length()] = this.mc.renderGlobal.getDebugInfoEntities();
        array3[0x98 ^ 0x9C] = GuiOverlayDebug.I[0x98 ^ 0x88] + this.mc.effectRenderer.getStatistics() + GuiOverlayDebug.I[0x99 ^ 0x88] + this.mc.theWorld.getDebugLoadedEntities();
        array3[0x6A ^ 0x6F] = this.mc.theWorld.getProviderName();
        array3[0x2B ^ 0x2D] = GuiOverlayDebug.I[0x2C ^ 0x3E];
        final int n2 = 0x8 ^ 0xF;
        final String s3 = GuiOverlayDebug.I[0x18 ^ 0xB];
        final Object[] array4 = new Object["   ".length()];
        array4["".length()] = this.mc.getRenderViewEntity().posX;
        array4[" ".length()] = this.mc.getRenderViewEntity().getEntityBoundingBox().minY;
        array4["  ".length()] = this.mc.getRenderViewEntity().posZ;
        array3[n2] = String.format(s3, array4);
        final int n3 = 0x64 ^ 0x6C;
        final String s4 = GuiOverlayDebug.I[0x33 ^ 0x27];
        final Object[] array5 = new Object["   ".length()];
        array5["".length()] = blockPos.getX();
        array5[" ".length()] = blockPos.getY();
        array5["  ".length()] = blockPos.getZ();
        array3[n3] = String.format(s4, array5);
        final int n4 = 0x46 ^ 0x4F;
        final String s5 = GuiOverlayDebug.I[0x3C ^ 0x29];
        final Object[] array6 = new Object[0x8C ^ 0x8A];
        array6["".length()] = (blockPos.getX() & (0x99 ^ 0x96));
        array6[" ".length()] = (blockPos.getY() & (0x1B ^ 0x14));
        array6["  ".length()] = (blockPos.getZ() & (0x1 ^ 0xE));
        array6["   ".length()] = blockPos.getX() >> (0x50 ^ 0x54);
        array6[0x37 ^ 0x33] = blockPos.getY() >> (0x88 ^ 0x8C);
        array6[0x2E ^ 0x2B] = blockPos.getZ() >> (0xB8 ^ 0xBC);
        array3[n4] = String.format(s5, array6);
        final int n5 = 0xB9 ^ 0xB3;
        final String s6 = GuiOverlayDebug.I[0x8B ^ 0x9D];
        final Object[] array7 = new Object[0x7E ^ 0x7A];
        array7["".length()] = horizontalFacing;
        array7[" ".length()] = s2;
        array7["  ".length()] = MathHelper.wrapAngleTo180_float(renderViewEntity.rotationYaw);
        array7["   ".length()] = MathHelper.wrapAngleTo180_float(renderViewEntity.rotationPitch);
        array3[n5] = String.format(s6, array7);
        final ArrayList arrayList = Lists.newArrayList((Object[])array3);
        if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockPos)) {
            final Chunk chunkFromBlockCoords = this.mc.theWorld.getChunkFromBlockCoords(blockPos);
            arrayList.add(GuiOverlayDebug.I[0x35 ^ 0x22] + chunkFromBlockCoords.getBiome(blockPos, this.mc.theWorld.getWorldChunkManager()).biomeName);
            arrayList.add(GuiOverlayDebug.I[0x3F ^ 0x27] + chunkFromBlockCoords.getLightSubtracted(blockPos, "".length()) + GuiOverlayDebug.I[0x6A ^ 0x73] + chunkFromBlockCoords.getLightFor(EnumSkyBlock.SKY, blockPos) + GuiOverlayDebug.I[0xBC ^ 0xA6] + chunkFromBlockCoords.getLightFor(EnumSkyBlock.BLOCK, blockPos) + GuiOverlayDebug.I[0x62 ^ 0x79]);
            DifficultyInstance difficultyInstance = this.mc.theWorld.getDifficultyForLocation(blockPos);
            if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
                final EntityPlayerMP playerByUUID = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
                if (playerByUUID != null) {
                    difficultyInstance = playerByUUID.worldObj.getDifficultyForLocation(new BlockPos(playerByUUID));
                }
            }
            final ArrayList<String> list = (ArrayList<String>)arrayList;
            final String s7 = GuiOverlayDebug.I[0x42 ^ 0x5E];
            final Object[] array8 = new Object["  ".length()];
            array8["".length()] = difficultyInstance.getAdditionalDifficulty();
            array8[" ".length()] = this.mc.theWorld.getWorldTime() / 24000L;
            list.add(String.format(s7, array8));
        }
        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            arrayList.add(GuiOverlayDebug.I[0x1A ^ 0x7] + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockPos2 = this.mc.objectMouseOver.getBlockPos();
            final ArrayList<String> list2 = (ArrayList<String>)arrayList;
            final String s8 = GuiOverlayDebug.I[0x29 ^ 0x37];
            final Object[] array9 = new Object["   ".length()];
            array9["".length()] = blockPos2.getX();
            array9[" ".length()] = blockPos2.getY();
            array9["  ".length()] = blockPos2.getZ();
            list2.add(String.format(s8, array9));
        }
        return arrayList;
    }
    
    static {
        I();
        __OBFID = GuiOverlayDebug.I[0x56 ^ 0x7A];
    }
    
    static final class GuiOverlayDebug$1
    {
        private static final String[] I;
        static final int[] field_178907_a;
        private static final String __OBFID;
        
        static {
            I();
            __OBFID = GuiOverlayDebug$1.I["".length()];
            field_178907_a = new int[EnumFacing.values().length];
            try {
                GuiOverlayDebug$1.field_178907_a[EnumFacing.NORTH.ordinal()] = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GuiOverlayDebug$1.field_178907_a[EnumFacing.SOUTH.ordinal()] = "  ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GuiOverlayDebug$1.field_178907_a[EnumFacing.WEST.ordinal()] = "   ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GuiOverlayDebug$1.field_178907_a[EnumFacing.EAST.ordinal()] = (0xC ^ 0x8);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0016#9\u007fhe_Wvm`", "UofOX");
        }
    }
}
