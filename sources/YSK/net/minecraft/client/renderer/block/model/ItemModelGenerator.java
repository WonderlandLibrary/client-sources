package net.minecraft.client.renderer.block.model;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.util.vector.*;
import net.minecraft.util.*;

public class ItemModelGenerator
{
    private static int[] $SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing;
    public static final List<String> LAYERS;
    private static final String[] I;
    
    static int[] $SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing() {
        final int[] $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing = ItemModelGenerator.$SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing;
        if ($switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing != null) {
            return $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing;
        }
        final int[] $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2 = new int[SpanFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2[SpanFacing.DOWN.ordinal()] = "  ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2[SpanFacing.LEFT.ordinal()] = "   ".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2[SpanFacing.RIGHT.ordinal()] = (0xB2 ^ 0xB6);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2[SpanFacing.UP.ordinal()] = " ".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return ItemModelGenerator.$SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing = $switch_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing2;
    }
    
    private boolean func_178391_a(final int[] array, final int n, final int n2, final int n3, final int n4) {
        int n5;
        if (n >= 0 && n2 >= 0 && n < n3 && n2 < n4) {
            if ((array[n2 * n3 + n] >> (0x79 ^ 0x61) & 102 + 245 - 239 + 147) == 0x0) {
                n5 = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            n5 = " ".length();
        }
        return n5 != 0;
    }
    
    private void func_178395_a(final List<Span> list, final SpanFacing spanFacing, final int n, final int n2) {
        Span span = null;
        final Iterator<Span> iterator = list.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Span span2 = iterator.next();
            if (span2.func_178383_a() == spanFacing) {
                int n3;
                if (SpanFacing.access$2(spanFacing)) {
                    n3 = n2;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n3 = n;
                }
                if (span2.func_178381_d() != n3) {
                    continue;
                }
                span = span2;
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
        }
        int n4;
        if (SpanFacing.access$2(spanFacing)) {
            n4 = n2;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        final int n5 = n4;
        int n6;
        if (SpanFacing.access$2(spanFacing)) {
            n6 = n;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n6 = n2;
        }
        final int n7 = n6;
        if (span == null) {
            list.add(new Span(spanFacing, n7, n5));
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            span.func_178382_a(n7);
        }
    }
    
    public ModelBlock makeItemModel(final TextureMap textureMap, final ModelBlock modelBlock) {
        final HashMap hashMap = Maps.newHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (i < ItemModelGenerator.LAYERS.size()) {
            final String s = ItemModelGenerator.LAYERS.get(i);
            if (!modelBlock.isTexturePresent(s)) {
                "".length();
                if (2 == 0) {
                    throw null;
                }
                break;
            }
            else {
                final String resolveTextureName = modelBlock.resolveTextureName(s);
                hashMap.put(s, resolveTextureName);
                arrayList.addAll(this.func_178394_a(i, s, textureMap.getAtlasSprite(new ResourceLocation(resolveTextureName).toString())));
                ++i;
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        final HashMap<String, String> hashMap2 = (HashMap<String, String>)hashMap;
        final String s2 = ItemModelGenerator.I[0x4C ^ 0x49];
        String resolveTextureName2;
        if (modelBlock.isTexturePresent(ItemModelGenerator.I[0xC7 ^ 0xC1])) {
            resolveTextureName2 = modelBlock.resolveTextureName(ItemModelGenerator.I[0x6B ^ 0x6C]);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            resolveTextureName2 = hashMap.get(ItemModelGenerator.I[0x5C ^ 0x54]);
        }
        hashMap2.put(s2, resolveTextureName2);
        return new ModelBlock(arrayList, hashMap, "".length() != 0, "".length() != 0, modelBlock.func_181682_g());
    }
    
    private List<BlockPart> func_178397_a(final TextureAtlasSprite textureAtlasSprite, final String s, final int n) {
        final float n2 = textureAtlasSprite.getIconWidth();
        final float n3 = textureAtlasSprite.getIconHeight();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Span> iterator = this.func_178393_a(textureAtlasSprite).iterator();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Span span = iterator.next();
            float n4 = 0.0f;
            float n5 = 0.0f;
            float n6 = 0.0f;
            float n7 = 0.0f;
            float n8 = 0.0f;
            float n9 = 0.0f;
            float n10 = 0.0f;
            float n11 = 0.0f;
            float n12 = 0.0f;
            float n13 = 0.0f;
            final float n14 = span.func_178385_b();
            final float n15 = span.func_178384_c();
            final float n16 = span.func_178381_d();
            final SpanFacing func_178383_a = span.func_178383_a();
            switch ($SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing()[func_178383_a.ordinal()]) {
                case 1: {
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n10 = n16;
                    n5 = n16;
                    n11 = n16;
                    n7 = n16;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    n11 = n16;
                    n10 = n16;
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n5 = n16 + 1.0f;
                    n7 = n16 + 1.0f;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    n8 = n16;
                    n4 = n16;
                    n9 = n16;
                    n6 = n16;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    n9 = n16;
                    n8 = n16;
                    n4 = n16 + 1.0f;
                    n6 = n16 + 1.0f;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    break;
                }
            }
            final float n17 = 16.0f / n2;
            final float n18 = 16.0f / n3;
            final float n19 = n4 * n17;
            final float n20 = n6 * n17;
            final float n21 = n5 * n18;
            final float n22 = n7 * n18;
            final float n23 = 16.0f - n21;
            final float n24 = 16.0f - n22;
            final float n25 = n8 * n12;
            final float n26 = n9 * n12;
            final float n27 = n10 * n13;
            final float n28 = n11 * n13;
            final HashMap hashMap2;
            final HashMap hashMap = hashMap2 = Maps.newHashMap();
            final EnumFacing facing = func_178383_a.getFacing();
            final EnumFacing enumFacing = null;
            final float[] array = new float[0x7D ^ 0x79];
            array["".length()] = n25;
            array[" ".length()] = n27;
            array["  ".length()] = n26;
            array["   ".length()] = n28;
            hashMap2.put(facing, new BlockPartFace(enumFacing, n, s, new BlockFaceUV(array, "".length())));
            switch ($SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing()[func_178383_a.ordinal()]) {
                case 1: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n20, n23, 8.5f), hashMap, null, (boolean)(" ".length() != 0)));
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                    continue;
                }
                case 2: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n24, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, (boolean)(" ".length() != 0)));
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                    continue;
                }
                case 3: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n19, n24, 8.5f), hashMap, null, (boolean)(" ".length() != 0)));
                    "".length();
                    if (false) {
                        throw null;
                    }
                    continue;
                }
                case 4: {
                    arrayList.add(new BlockPart(new Vector3f(n20, n23, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, (boolean)(" ".length() != 0)));
                }
                default: {
                    continue;
                }
            }
        }
        return (List<BlockPart>)arrayList;
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private List<BlockPart> func_178394_a(final int n, final String s, final TextureAtlasSprite textureAtlasSprite) {
        final HashMap hashMap2;
        final HashMap hashMap = hashMap2 = Maps.newHashMap();
        final EnumFacing south = EnumFacing.SOUTH;
        final EnumFacing enumFacing = null;
        final float[] array = new float[0xB7 ^ 0xB3];
        array["".length()] = 0.0f;
        array[" ".length()] = 0.0f;
        array["  ".length()] = 16.0f;
        array["   ".length()] = 16.0f;
        hashMap2.put(south, new BlockPartFace(enumFacing, n, s, new BlockFaceUV(array, "".length())));
        final HashMap hashMap3 = hashMap;
        final EnumFacing north = EnumFacing.NORTH;
        final EnumFacing enumFacing2 = null;
        final float[] array2 = new float[0xB1 ^ 0xB5];
        array2["".length()] = 16.0f;
        array2[" ".length()] = 0.0f;
        array2["  ".length()] = 0.0f;
        array2["   ".length()] = 16.0f;
        hashMap3.put(north, new BlockPartFace(enumFacing2, n, s, new BlockFaceUV(array2, "".length())));
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new BlockPart(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), hashMap, null, (boolean)(" ".length() != 0)));
        arrayList.addAll(this.func_178397_a(textureAtlasSprite, s, n));
        return (List<BlockPart>)arrayList;
    }
    
    private List<Span> func_178393_a(final TextureAtlasSprite textureAtlasSprite) {
        final int iconWidth = textureAtlasSprite.getIconWidth();
        final int iconHeight = textureAtlasSprite.getIconHeight();
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < textureAtlasSprite.getFrameCount()) {
            final int[] array = textureAtlasSprite.getFrameTextureData(i)["".length()];
            int j = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (j < iconHeight) {
                int k = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k < iconWidth) {
                    int n;
                    if (this.func_178391_a(array, k, j, iconWidth, iconHeight)) {
                        n = "".length();
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        n = " ".length();
                    }
                    final int n2 = n;
                    this.func_178396_a(SpanFacing.UP, arrayList, array, k, j, iconWidth, iconHeight, n2 != 0);
                    this.func_178396_a(SpanFacing.DOWN, arrayList, array, k, j, iconWidth, iconHeight, n2 != 0);
                    this.func_178396_a(SpanFacing.LEFT, arrayList, array, k, j, iconWidth, iconHeight, n2 != 0);
                    this.func_178396_a(SpanFacing.RIGHT, arrayList, array, k, j, iconWidth, iconHeight, n2 != 0);
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return (List<Span>)arrayList;
    }
    
    static {
        I();
        final String[] array = new String[0x13 ^ 0x16];
        array["".length()] = ItemModelGenerator.I["".length()];
        array[" ".length()] = ItemModelGenerator.I[" ".length()];
        array["  ".length()] = ItemModelGenerator.I["  ".length()];
        array["   ".length()] = ItemModelGenerator.I["   ".length()];
        array[0x99 ^ 0x9D] = ItemModelGenerator.I[0xAD ^ 0xA9];
        LAYERS = Lists.newArrayList((Object[])array);
    }
    
    private void func_178396_a(final SpanFacing spanFacing, final List<Span> list, final int[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        int n5;
        if (this.func_178391_a(array, n + spanFacing.func_178372_b(), n2 + spanFacing.func_178371_c(), n3, n4) && b) {
            n5 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        if (n5 != 0) {
            this.func_178395_a(list, spanFacing, n, n2);
        }
    }
    
    private static void I() {
        (I = new String[0x3B ^ 0x32])["".length()] = I("\u001d1+.+A", "qPRKY");
        ItemModelGenerator.I[" ".length()] = I(":3\u000e\u0017\u0002g", "VRwrp");
        ItemModelGenerator.I["  ".length()] = I("\u0019+\u0000,\u0006G", "uJyIt");
        ItemModelGenerator.I["   ".length()] = I("\u0014\u000b#\n\u000bK", "xjZoy");
        ItemModelGenerator.I[0x4C ^ 0x48] = I("'\u0000(\u0015\u0018\u007f", "KaQpj");
        ItemModelGenerator.I[0x9D ^ 0x98] = I("(\u0016\u0001,\b;\u001b\u0016", "XwsXa");
        ItemModelGenerator.I[0x2 ^ 0x4] = I("#\u0014>\u0005#0\u0019)", "SuLqJ");
        ItemModelGenerator.I[0xB5 ^ 0xB2] = I("7\u001b\u0015\u0018\u0018$\u0016\u0002", "Gzglq");
        ItemModelGenerator.I[0x2B ^ 0x23] = I("\u00051\u000f$\u0018Y", "iPvAj");
    }
    
    enum SpanFacing
    {
        private final EnumFacing facing;
        private static final String[] I;
        
        UP(SpanFacing.I["".length()], "".length(), EnumFacing.UP, "".length(), -" ".length());
        
        private static final SpanFacing[] ENUM$VALUES;
        
        DOWN(SpanFacing.I[" ".length()], " ".length(), EnumFacing.DOWN, "".length(), " ".length());
        
        private final int field_178374_g;
        
        LEFT(SpanFacing.I["  ".length()], "  ".length(), EnumFacing.EAST, -" ".length(), "".length());
        
        private final int field_178373_f;
        
        RIGHT(SpanFacing.I["   ".length()], "   ".length(), EnumFacing.WEST, " ".length(), "".length());
        
        public int func_178372_b() {
            return this.field_178373_f;
        }
        
        private SpanFacing(final String s, final int n, final EnumFacing facing, final int field_178373_f, final int field_178374_g) {
            this.facing = facing;
            this.field_178373_f = field_178373_f;
            this.field_178374_g = field_178374_g;
        }
        
        private boolean func_178369_d() {
            if (this != SpanFacing.DOWN && this != SpanFacing.UP) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        static {
            I();
            final SpanFacing[] enum$VALUES = new SpanFacing[0x1A ^ 0x1E];
            enum$VALUES["".length()] = SpanFacing.UP;
            enum$VALUES[" ".length()] = SpanFacing.DOWN;
            enum$VALUES["  ".length()] = SpanFacing.LEFT;
            enum$VALUES["   ".length()] = SpanFacing.RIGHT;
            ENUM$VALUES = enum$VALUES;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
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
                if (2 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x75 ^ 0x71])["".length()] = I("'\u0013", "rCLwU");
            SpanFacing.I[" ".length()] = I("\f\u0005:\r", "HJmCW");
            SpanFacing.I["  ".length()] = I("(\u0015\";", "dPdoJ");
            SpanFacing.I["   ".length()] = I("\u0003-$*6", "Qdcbb");
        }
        
        public int func_178371_c() {
            return this.field_178374_g;
        }
        
        static boolean access$2(final SpanFacing spanFacing) {
            return spanFacing.func_178369_d();
        }
    }
    
    static class Span
    {
        private final int field_178386_d;
        private int field_178388_c;
        private int field_178387_b;
        private final SpanFacing spanFacing;
        
        public SpanFacing func_178383_a() {
            return this.spanFacing;
        }
        
        public Span(final SpanFacing spanFacing, final int n, final int field_178386_d) {
            this.spanFacing = spanFacing;
            this.field_178387_b = n;
            this.field_178388_c = n;
            this.field_178386_d = field_178386_d;
        }
        
        public void func_178382_a(final int n) {
            if (n < this.field_178387_b) {
                this.field_178387_b = n;
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else if (n > this.field_178388_c) {
                this.field_178388_c = n;
            }
        }
        
        public int func_178384_c() {
            return this.field_178388_c;
        }
        
        public int func_178381_d() {
            return this.field_178386_d;
        }
        
        public int func_178385_b() {
            return this.field_178387_b;
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
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
