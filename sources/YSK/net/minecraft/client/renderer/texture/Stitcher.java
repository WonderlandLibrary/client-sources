package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class Stitcher
{
    private final Set setStitchHolders;
    private final int mipmapLevelStitcher;
    private final int maxWidth;
    private final int maxTileDimension;
    private int currentWidth;
    private static final String __OBFID;
    private int currentHeight;
    private final int maxHeight;
    private final boolean forcePowerOf2;
    private final List stitchSlots;
    private static final String[] I;
    
    public void addSprite(final TextureAtlasSprite textureAtlasSprite) {
        final Holder holder = new Holder(textureAtlasSprite, this.mipmapLevelStitcher);
        if (this.maxTileDimension > 0) {
            holder.setNewDimension(this.maxTileDimension);
        }
        this.setStitchHolders.add(holder);
    }
    
    public Stitcher(final int maxWidth, final int maxHeight, final boolean forcePowerOf2, final int maxTileDimension, final int mipmapLevelStitcher) {
        this.setStitchHolders = Sets.newHashSetWithExpectedSize(37 + 196 + 12 + 11);
        this.stitchSlots = Lists.newArrayListWithCapacity(225 + 10 - 61 + 82);
        this.mipmapLevelStitcher = mipmapLevelStitcher;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.forcePowerOf2 = forcePowerOf2;
        this.maxTileDimension = maxTileDimension;
    }
    
    public void doStitch() {
        final Holder[] array = this.setStitchHolders.toArray(new Holder[this.setStitchHolders.size()]);
        Arrays.sort(array);
        final Holder[] array2;
        final int length = (array2 = array).length;
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < length) {
            final Holder holder = array2[i];
            if (!this.allocateSlot(holder)) {
                final String s = Stitcher.I["".length()];
                final Object[] array3 = new Object[0x16 ^ 0x11];
                array3["".length()] = holder.getAtlasSprite().getIconName();
                array3[" ".length()] = holder.getAtlasSprite().getIconWidth();
                array3["  ".length()] = holder.getAtlasSprite().getIconHeight();
                array3["   ".length()] = this.currentWidth;
                array3[0x80 ^ 0x84] = this.currentHeight;
                array3[0x45 ^ 0x40] = this.maxWidth;
                array3[0x24 ^ 0x22] = this.maxHeight;
                throw new StitcherException(holder, String.format(s, array3));
            }
            ++i;
        }
        if (this.forcePowerOf2) {
            this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
        }
    }
    
    static {
        I();
        __OBFID = Stitcher.I[" ".length()];
    }
    
    private boolean allocateSlot(final Holder holder) {
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < this.stitchSlots.size()) {
            if (((Slot)this.stitchSlots.get(i)).addSlot(holder)) {
                return " ".length() != 0;
            }
            holder.rotate();
            if (((Slot)this.stitchSlots.get(i)).addSlot(holder)) {
                return " ".length() != 0;
            }
            holder.rotate();
            ++i;
        }
        return this.expandAndAllocateSlot(holder);
    }
    
    public int getCurrentWidth() {
        return this.currentWidth;
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean expandAndAllocateSlot(final Holder holder) {
        final int min = Math.min(holder.getWidth(), holder.getHeight());
        int n;
        if (this.currentWidth == 0 && this.currentHeight == 0) {
            n = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n11;
        if (this.forcePowerOf2) {
            final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            final int roundUpToPowerOfTwo2 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
            final int roundUpToPowerOfTwo3 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + min);
            final int roundUpToPowerOfTwo4 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + min);
            int n3;
            if (roundUpToPowerOfTwo3 <= this.maxWidth) {
                n3 = " ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            int n5;
            if (roundUpToPowerOfTwo4 <= this.maxHeight) {
                n5 = " ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            final int n6 = n5;
            if (n4 == 0 && n6 == 0) {
                return "".length() != 0;
            }
            int n7;
            if (roundUpToPowerOfTwo != roundUpToPowerOfTwo3) {
                n7 = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n7 = "".length();
            }
            final int n8 = n7;
            int n9;
            if (roundUpToPowerOfTwo2 != roundUpToPowerOfTwo4) {
                n9 = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                n9 = "".length();
            }
            if ((n8 ^ n9) != 0x0) {
                int n10;
                if (n8 != 0) {
                    n10 = "".length();
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                else {
                    n10 = " ".length();
                }
                n11 = n10;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                int n12;
                if (n4 != 0 && roundUpToPowerOfTwo <= roundUpToPowerOfTwo2) {
                    n12 = " ".length();
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
                else {
                    n12 = "".length();
                }
                n11 = n12;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
        }
        else {
            int n13;
            if (this.currentWidth + min <= this.maxWidth) {
                n13 = " ".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                n13 = "".length();
            }
            final int n14 = n13;
            int n15;
            if (this.currentHeight + min <= this.maxHeight) {
                n15 = " ".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                n15 = "".length();
            }
            final int n16 = n15;
            if (n14 == 0 && n16 == 0) {
                return "".length() != 0;
            }
            int n17;
            if (n14 != 0 && (n2 != 0 || this.currentWidth <= this.currentHeight)) {
                n17 = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n17 = "".length();
            }
            n11 = n17;
        }
        final int max = Math.max(holder.getWidth(), holder.getHeight());
        int n18;
        if (n11 == 0) {
            n18 = this.currentHeight;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n18 = this.currentWidth;
        }
        final int roundUpToPowerOfTwo5 = MathHelper.roundUpToPowerOfTwo(n18 + max);
        int n19;
        if (n11 == 0) {
            n19 = this.maxHeight;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n19 = this.maxWidth;
        }
        if (roundUpToPowerOfTwo5 > n19) {
            return "".length() != 0;
        }
        Slot slot;
        if (n11 != 0) {
            if (holder.getWidth() > holder.getHeight()) {
                holder.rotate();
            }
            if (this.currentHeight == 0) {
                this.currentHeight = holder.getHeight();
            }
            slot = new Slot(this.currentWidth, "".length(), holder.getWidth(), this.currentHeight);
            this.currentWidth += holder.getWidth();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            slot = new Slot("".length(), this.currentHeight, this.currentWidth, holder.getHeight());
            this.currentHeight += holder.getHeight();
        }
        slot.addSlot(holder);
        this.stitchSlots.add(slot);
        return " ".length() != 0;
    }
    
    public List getStichSlots() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Slot> iterator = this.stitchSlots.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().getAllStitchSlots(arrayList);
        }
        final ArrayList arrayList2 = Lists.newArrayList();
        final Iterator<Slot> iterator2 = arrayList.iterator();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Slot slot = iterator2.next();
            final Holder stitchHolder = slot.getStitchHolder();
            final TextureAtlasSprite atlasSprite = stitchHolder.getAtlasSprite();
            atlasSprite.initSprite(this.currentWidth, this.currentHeight, slot.getOriginX(), slot.getOriginY(), stitchHolder.isRotated());
            arrayList2.add(atlasSprite);
        }
        return arrayList2;
    }
    
    static int access$0(final int n, final int n2) {
        return getMipmapDimension(n, n2);
    }
    
    private static int getMipmapDimension(final int n, final int n2) {
        final int n3 = n >> n2;
        int n4;
        if ((n & (" ".length() << n2) - " ".length()) == 0x0) {
            n4 = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        return n3 + n4 << n2;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("$/(\u0016\u0000\u0014a=\u001bL\u0017(=NLT2eT\u001f\u0018;,NLT%1Q\b]a(\u0000\u0000\u00102sTI\u00159l\u0010@Q =\u0018\r\u0002\f(\fVQd-\fI\u0015adT!\u00108+\u0011L\u000530T\rQ-&\u0003\t\u0003a;\u0011\u001f\u001e-<\u0000\u0005\u001e/i\u0006\t\u0002.<\u0006\u000f\u00141(\u0017\u0007N", "qAItl");
        Stitcher.I[" ".length()] = I("6<\u001dxsE@sxvA", "upBHC");
    }
    
    public int getCurrentHeight() {
        return this.currentHeight;
    }
    
    public static class Holder implements Comparable
    {
        private final TextureAtlasSprite theTexture;
        private boolean rotated;
        private final int height;
        private final int width;
        private float scaleFactor;
        private final int mipmapLevelHolder;
        private static final String[] I;
        private static final String __OBFID;
        
        static {
            I();
            __OBFID = Holder.I["  ".length()];
        }
        
        public boolean isRotated() {
            return this.rotated;
        }
        
        public int getWidth() {
            int n;
            if (this.rotated) {
                n = Stitcher.access$0((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                n = Stitcher.access$0((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
            }
            return n;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Holder)o);
        }
        
        public int getHeight() {
            int n;
            if (this.rotated) {
                n = Stitcher.access$0((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = Stitcher.access$0((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
            }
            return n;
        }
        
        public void rotate() {
            int rotated;
            if (this.rotated) {
                rotated = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                rotated = " ".length();
            }
            this.rotated = (rotated != 0);
        }
        
        public TextureAtlasSprite getAtlasSprite() {
            return this.theTexture;
        }
        
        public Holder(final TextureAtlasSprite theTexture, final int mipmapLevelHolder) {
            this.scaleFactor = 1.0f;
            this.theTexture = theTexture;
            this.width = theTexture.getIconWidth();
            this.height = theTexture.getIconHeight();
            this.mipmapLevelHolder = mipmapLevelHolder;
            int rotated;
            if (Stitcher.access$0(this.height, mipmapLevelHolder) > Stitcher.access$0(this.width, mipmapLevelHolder)) {
                rotated = " ".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                rotated = "".length();
            }
            this.rotated = (rotated != 0);
        }
        
        public int compareTo(final Holder holder) {
            int n;
            if (this.getHeight() == holder.getHeight()) {
                if (this.getWidth() == holder.getWidth()) {
                    if (this.theTexture.getIconName() == null) {
                        int length;
                        if (holder.theTexture.getIconName() == null) {
                            length = "".length();
                            "".length();
                            if (0 == -1) {
                                throw null;
                            }
                        }
                        else {
                            length = -" ".length();
                        }
                        return length;
                    }
                    return this.theTexture.getIconName().compareTo(holder.theTexture.getIconName());
                }
                else {
                    int length2;
                    if (this.getWidth() < holder.getWidth()) {
                        length2 = " ".length();
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        length2 = -" ".length();
                    }
                    n = length2;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else {
                int length3;
                if (this.getHeight() < holder.getHeight()) {
                    length3 = " ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    length3 = -" ".length();
                }
                n = length3;
            }
            return n;
        }
        
        @Override
        public String toString() {
            return Holder.I["".length()] + this.width + Holder.I[" ".length()] + this.height + (char)(0x49 ^ 0x34);
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u000e>#\u0006\u001d4*8\u000b\u001c29r", "FQObx");
            Holder.I[" ".length()] = I("_I\r!%\u0014\u0001\u0011y", "sieDL");
            Holder.I["  ".length()] = I("\u001b\u00190QXhe^Q]m", "XUoah");
        }
        
        public void setNewDimension(final int n) {
            if (this.width > n && this.height > n) {
                this.scaleFactor = n / Math.min(this.width, this.height);
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
                if (2 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class Slot
    {
        private Holder holder;
        private final int height;
        private static final String __OBFID;
        private final int originY;
        private final int originX;
        private static final String[] I;
        private List subSlots;
        private final int width;
        
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
                if (0 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean addSlot(final Holder holder) {
            if (this.holder != null) {
                return "".length() != 0;
            }
            final int width = holder.getWidth();
            final int height = holder.getHeight();
            if (width > this.width || height > this.height) {
                return "".length() != 0;
            }
            if (width == this.width && height == this.height) {
                this.holder = holder;
                return " ".length() != 0;
            }
            if (this.subSlots == null) {
                (this.subSlots = Lists.newArrayListWithCapacity(" ".length())).add(new Slot(this.originX, this.originY, width, height));
                final int n = this.width - width;
                final int n2 = this.height - height;
                if (n2 > 0 && n > 0) {
                    if (Math.max(this.height, n) >= Math.max(this.width, n2)) {
                        this.subSlots.add(new Slot(this.originX, this.originY + height, width, n2));
                        this.subSlots.add(new Slot(this.originX + width, this.originY, n, this.height));
                        "".length();
                        if (1 == 4) {
                            throw null;
                        }
                    }
                    else {
                        this.subSlots.add(new Slot(this.originX + width, this.originY, n, height));
                        this.subSlots.add(new Slot(this.originX, this.originY + height, this.width, n2));
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                }
                else if (n == 0) {
                    this.subSlots.add(new Slot(this.originX, this.originY + height, width, n2));
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (n2 == 0) {
                    this.subSlots.add(new Slot(this.originX + width, this.originY, n, height));
                }
            }
            final Iterator<Slot> iterator = this.subSlots.iterator();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (iterator.next().addSlot(holder)) {
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
        }
        
        @Override
        public String toString() {
            return Slot.I["".length()] + this.originX + Slot.I[" ".length()] + this.originY + Slot.I["  ".length()] + this.width + Slot.I["   ".length()] + this.height + Slot.I[0x98 ^ 0x9C] + this.holder + Slot.I[0x2F ^ 0x2A] + this.subSlots + (char)(0xFE ^ 0x83);
        }
        
        public int getOriginY() {
            return this.originY;
        }
        
        public void getAllStitchSlots(final List list) {
            if (this.holder != null) {
                list.add(this);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else if (this.subSlots != null) {
                final Iterator<Slot> iterator = (Iterator<Slot>)this.subSlots.iterator();
                "".length();
                if (4 < 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    iterator.next().getAllStitchSlots(list);
                }
            }
        }
        
        private static void I() {
            (I = new String[0x13 ^ 0x14])["".length()] = I("\u0016<%\u00051*\"#\u0016#+\bw", "EPJqJ");
            Slot.I[" ".length()] = I("ta\u000b?0?(\n\u0014d", "XAdMY");
            Slot.I["  ".length()] = I("}P.!+%\u0018d", "QpYHO");
            Slot.I["   ".length()] = I("ut\u0010\u0016?><\fN", "YTxsV");
            Slot.I[0xA6 ^ 0xA2] = I("FX3\u001d>\u001e\r5\u001d{", "jxGxF");
            Slot.I[0x57 ^ 0x52] = I("gv\u0018\u0019\u0006\u0018:\u0004\u0018\u0017v", "KVkld");
            Slot.I[0xB9 ^ 0xBF] = I("\u001a\u0001\u001eTti}pTqo", "YMAdD");
        }
        
        static {
            I();
            __OBFID = Slot.I[0x65 ^ 0x63];
        }
        
        public int getOriginX() {
            return this.originX;
        }
        
        public Holder getStitchHolder() {
            return this.holder;
        }
        
        public Slot(final int originX, final int originY, final int width, final int height) {
            this.originX = originX;
            this.originY = originY;
            this.width = width;
            this.height = height;
        }
    }
}
