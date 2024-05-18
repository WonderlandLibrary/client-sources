package net.minecraft.client.renderer.vertex;

import java.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;

public class VertexFormat
{
    private static int[] $SWITCH_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage;
    private int colorElementOffset;
    private int nextOffset;
    private static final String[] I;
    private int normalElementOffset;
    private List<Integer> uvOffsetsById;
    private final List<VertexFormatElement> elements;
    private static final Logger LOGGER;
    private final List<Integer> offsets;
    
    public boolean hasUvOffset(final int n) {
        if (this.uvOffsetsById.size() - " ".length() >= n) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int func_181719_f() {
        return this.getNextOffset() / (0x98 ^ 0x9C);
    }
    
    @Override
    public int hashCode() {
        return (0x17 ^ 0x8) * ((0x6E ^ 0x71) * this.elements.hashCode() + this.offsets.hashCode()) + this.nextOffset;
    }
    
    private boolean hasPosition() {
        int i = "".length();
        final int size = this.elements.size();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < size) {
            if (this.elements.get(i).isPositionElement()) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public int getNextOffset() {
        return this.nextOffset;
    }
    
    public int getElementCount() {
        return this.elements.size();
    }
    
    public int func_181720_d(final int n) {
        return this.offsets.get(n);
    }
    
    private static void I() {
        (I = new String[0x37 ^ 0x33])["".length()] = I("7\u0002>\u0012\u001d\u0019!#\u0014\u0015\u0000\u0013l\u0003\n\u0013\b>\\X5\u00155\u000f\u0016\u0006G8\tX\u0000\u0003(F\u0019A\u0017#\u0015\u0011\u0015\u000e#\bX7\u0002>\u0012\u001d\u0019!#\u0014\u0015\u0000\u0013\t\n\u001d\f\u0002\"\u0012X\u0016\u000f)\bX\u000e\t)F\u0019\r\u0015)\u0007\u001c\u0018G)\u001e\u0011\u0012\u0013?JX\b\u0000\"\t\n\b\t+H", "agLfx");
        VertexFormat.I[" ".length()] = I("\b75'\n\u001abg", "nXGJk");
        VertexFormat.I["  ".length()] = I("D\u000b\"6,\u0001\u0000: {D", "dnNSA");
        VertexFormat.I["   ".length()] = I("R", "rWKGO");
    }
    
    public int getNormalOffset() {
        return this.normalElementOffset;
    }
    
    public boolean hasNormal() {
        if (this.normalElementOffset >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public VertexFormat func_181721_a(final VertexFormatElement vertexFormatElement) {
        if (vertexFormatElement.isPositionElement() && this.hasPosition()) {
            VertexFormat.LOGGER.warn(VertexFormat.I["".length()]);
            return this;
        }
        this.elements.add(vertexFormatElement);
        this.offsets.add(this.nextOffset);
        switch ($SWITCH_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage()[vertexFormatElement.getUsage().ordinal()]) {
            case 2: {
                this.normalElementOffset = this.nextOffset;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.colorElementOffset = this.nextOffset;
                "".length();
                if (3 < 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.uvOffsetsById.add(vertexFormatElement.getIndex(), this.nextOffset);
                break;
            }
        }
        this.nextOffset += vertexFormatElement.getSize();
        return this;
    }
    
    @Override
    public String toString() {
        String s = VertexFormat.I[" ".length()] + this.elements.size() + VertexFormat.I["  ".length()];
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < this.elements.size()) {
            s = String.valueOf(s) + this.elements.get(i).toString();
            if (i != this.elements.size() - " ".length()) {
                s = String.valueOf(s) + VertexFormat.I["   ".length()];
            }
            ++i;
        }
        return s;
    }
    
    public VertexFormatElement getElement(final int n) {
        return this.elements.get(n);
    }
    
    public VertexFormat(final VertexFormat vertexFormat) {
        this();
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < vertexFormat.getElementCount()) {
            this.func_181721_a(vertexFormat.getElement(i));
            ++i;
        }
        this.nextOffset = vertexFormat.getNextOffset();
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage() {
        final int[] $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage = VertexFormat.$SWITCH_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage;
        if ($switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage != null) {
            return $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage;
        }
        final int[] $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2 = new int[VertexFormatElement.EnumUsage.values().length];
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.BLEND_WEIGHT.ordinal()] = (0x20 ^ 0x26);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.COLOR.ordinal()] = "   ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.MATRIX.ordinal()] = (0x14 ^ 0x11);
            "".length();
            if (!true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.NORMAL.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.PADDING.ordinal()] = (0x81 ^ 0x86);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.POSITION.ordinal()] = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2[VertexFormatElement.EnumUsage.UV.ordinal()] = (0x9B ^ 0x9F);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        return VertexFormat.$SWITCH_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage = $switch_TABLE$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage2;
    }
    
    public boolean hasColor() {
        if (this.colorElementOffset >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormat vertexFormat = (VertexFormat)o;
            int n;
            if (this.nextOffset != vertexFormat.nextOffset) {
                n = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (!this.elements.equals(vertexFormat.elements)) {
                n = "".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                n = (this.offsets.equals(vertexFormat.offsets) ? 1 : 0);
            }
            return n != 0;
        }
        return "".length() != 0;
    }
    
    public void clear() {
        this.elements.clear();
        this.offsets.clear();
        this.colorElementOffset = -" ".length();
        this.uvOffsetsById.clear();
        this.normalElementOffset = -" ".length();
        this.nextOffset = "".length();
    }
    
    public int getColorOffset() {
        return this.colorElementOffset;
    }
    
    public List<VertexFormatElement> getElements() {
        return this.elements;
    }
    
    public int getUvOffsetById(final int n) {
        return this.uvOffsetsById.get(n);
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    public VertexFormat() {
        this.elements = (List<VertexFormatElement>)Lists.newArrayList();
        this.offsets = (List<Integer>)Lists.newArrayList();
        this.nextOffset = "".length();
        this.colorElementOffset = -" ".length();
        this.uvOffsetsById = (List<Integer>)Lists.newArrayList();
        this.normalElementOffset = -" ".length();
    }
}
