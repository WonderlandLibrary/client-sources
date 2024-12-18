// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.renderer;

import java.util.List;
import net.minecraft.util.MathHelper;
import java.nio.ByteOrder;
import java.util.Comparator;
import java.util.PriorityQueue;
import net.minecraft.client.util.QuadComparator;
import org.apache.logging.log4j.LogManager;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormat;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class WorldRenderer
{
    private ByteBuffer byteBuffer;
    private IntBuffer rawIntBuffer;
    private FloatBuffer rawFloatBuffer;
    private int vertexCount;
    private double field_178998_e;
    private double field_178995_f;
    private int brightness;
    private int field_179007_h;
    private int rawBufferIndex;
    private boolean needsUpdate;
    private int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int field_179003_o;
    private int field_179012_p;
    private VertexFormat vertexFormat;
    private boolean isDrawing;
    private int bufferSize;
    private static final String __OBFID = "CL_00000942";
    
    public WorldRenderer(final int p_i46275_1_) {
        this.bufferSize = p_i46275_1_;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(p_i46275_1_ * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        (this.vertexFormat = new VertexFormat()).setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
    }
    
    private void growBuffer(final int p_178983_1_) {
        LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + this.bufferSize * 4 + " bytes, new size " + (this.bufferSize * 4 + p_178983_1_) + " bytes.");
        this.bufferSize += p_178983_1_ / 4;
        final ByteBuffer var2 = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
        this.rawIntBuffer.position(0);
        var2.asIntBuffer().put(this.rawIntBuffer);
        this.byteBuffer = var2;
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
    }
    
    public State getVertexState(final float p_178971_1_, final float p_178971_2_, final float p_178971_3_) {
        final int[] var4 = new int[this.rawBufferIndex];
        final PriorityQueue var5 = new PriorityQueue(this.rawBufferIndex, new QuadComparator(this.rawFloatBuffer, (float)(p_178971_1_ + this.xOffset), (float)(p_178971_2_ + this.yOffset), (float)(p_178971_3_ + this.zOffset), this.vertexFormat.func_177338_f() / 4));
        final int var6 = this.vertexFormat.func_177338_f();
        for (int var7 = 0; var7 < this.rawBufferIndex; var7 += var6) {
            var5.add(var7);
        }
        int var7 = 0;
        while (!var5.isEmpty()) {
            final int var8 = (int)var5.remove();
            for (int var9 = 0; var9 < var6; ++var9) {
                var4[var7 + var9] = this.rawIntBuffer.get(var8 + var9);
            }
            var7 += var6;
        }
        this.rawIntBuffer.clear();
        this.rawIntBuffer.put(var4);
        return new State(var4, this.rawBufferIndex, this.vertexCount, new VertexFormat(this.vertexFormat));
    }
    
    public void setVertexState(final State p_178993_1_) {
        if (p_178993_1_.func_179013_a().length > this.rawIntBuffer.capacity()) {
            this.growBuffer(2097152);
        }
        this.rawIntBuffer.clear();
        this.rawIntBuffer.put(p_178993_1_.func_179013_a());
        this.rawBufferIndex = p_178993_1_.getRawBufferIndex();
        this.vertexCount = p_178993_1_.getVertexCount();
        this.vertexFormat = new VertexFormat(p_178993_1_.func_179016_d());
    }
    
    public void reset() {
        this.vertexCount = 0;
        this.rawBufferIndex = 0;
        this.vertexFormat.clear();
        this.vertexFormat.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
    }
    
    public void startDrawingQuads() {
        this.startDrawing(7);
    }
    
    public void startDrawing(final int p_178964_1_) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = p_178964_1_;
        this.needsUpdate = false;
    }
    
    public void setTextureUV(final double p_178992_1_, final double p_178992_3_) {
        if (!this.vertexFormat.hasElementOffset(0) && !this.vertexFormat.hasElementOffset(1)) {
            final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2);
            this.vertexFormat.setElement(var5);
        }
        this.field_178998_e = p_178992_1_;
        this.field_178995_f = p_178992_3_;
    }
    
    public void setBrightness(final int p_178963_1_) {
        if (!this.vertexFormat.hasElementOffset(1)) {
            if (!this.vertexFormat.hasElementOffset(0)) {
                this.vertexFormat.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
            }
            final VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2);
            this.vertexFormat.setElement(var2);
        }
        this.brightness = p_178963_1_;
    }
    
    public void setColorOpaque_F(final float red, final float green, final float blue) {
        this.setPosition((int)(red * 255.0f), (int)(green * 255.0f), (int)(blue * 255.0f));
    }
    
    public void setColorRGBA_F(final float p_178960_1_, final float p_178960_2_, final float p_178960_3_, final float p_178960_4_) {
        this.setColorRGBA((int)(p_178960_1_ * 255.0f), (int)(p_178960_2_ * 255.0f), (int)(p_178960_3_ * 255.0f), (int)(p_178960_4_ * 255.0f));
    }
    
    public void setPosition(final int p_78913_1_, final int p_78913_2_, final int p_78913_3_) {
        this.setColorRGBA(p_78913_1_, p_78913_2_, p_78913_3_, 255);
    }
    
    public void func_178962_a(final int p_178962_1_, final int p_178962_2_, final int p_178962_3_, final int p_178962_4_) {
        final int var5 = (this.vertexCount - 4) * (this.vertexFormat.func_177338_f() / 4) + this.vertexFormat.func_177344_b(1) / 4;
        final int var6 = this.vertexFormat.func_177338_f() >> 2;
        this.rawIntBuffer.put(var5, p_178962_1_);
        this.rawIntBuffer.put(var5 + var6, p_178962_2_);
        this.rawIntBuffer.put(var5 + var6 * 2, p_178962_3_);
        this.rawIntBuffer.put(var5 + var6 * 3, p_178962_4_);
    }
    
    public void func_178987_a(final double p_178987_1_, final double p_178987_3_, final double p_178987_5_) {
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
            this.growBuffer(2097152);
        }
        final int var7 = this.vertexFormat.func_177338_f() / 4;
        final int var8 = (this.vertexCount - 4) * var7;
        for (int var9 = 0; var9 < 4; ++var9) {
            final int var10 = var8 + var9 * var7;
            final int var11 = var10 + 1;
            final int var12 = var11 + 1;
            this.rawIntBuffer.put(var10, Float.floatToRawIntBits((float)(p_178987_1_ + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var10))));
            this.rawIntBuffer.put(var11, Float.floatToRawIntBits((float)(p_178987_3_ + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var11))));
            this.rawIntBuffer.put(var12, Float.floatToRawIntBits((float)(p_178987_5_ + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var12))));
        }
    }
    
    private int getGLCallListForPass(final int p_78909_1_) {
        return ((this.vertexCount - p_78909_1_) * this.vertexFormat.func_177338_f() + this.vertexFormat.func_177340_e()) / 4;
    }
    
    public void func_178978_a(final float p_178978_1_, final float p_178978_2_, final float p_178978_3_, final int p_178978_4_) {
        final int var5 = this.getGLCallListForPass(p_178978_4_);
        int var6 = this.rawIntBuffer.get(var5);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            final int var7 = (int)((var6 & 0xFF) * p_178978_1_);
            final int var8 = (int)((var6 >> 8 & 0xFF) * p_178978_2_);
            final int var9 = (int)((var6 >> 16 & 0xFF) * p_178978_3_);
            var6 &= 0xFF000000;
            var6 |= (var9 << 16 | var8 << 8 | var7);
        }
        else {
            final int var7 = (int)((this.field_179007_h >> 24 & 0xFF) * p_178978_1_);
            final int var8 = (int)((this.field_179007_h >> 16 & 0xFF) * p_178978_2_);
            final int var9 = (int)((this.field_179007_h >> 8 & 0xFF) * p_178978_3_);
            var6 &= 0xFF;
            var6 |= (var7 << 24 | var8 << 16 | var9 << 8);
        }
        if (this.needsUpdate) {
            var6 = -1;
        }
        this.rawIntBuffer.put(var5, var6);
    }
    
    private void func_178988_b(final int p_178988_1_, final int p_178988_2_) {
        final int var3 = this.getGLCallListForPass(p_178988_2_);
        final int var4 = p_178988_1_ >> 16 & 0xFF;
        final int var5 = p_178988_1_ >> 8 & 0xFF;
        final int var6 = p_178988_1_ & 0xFF;
        final int var7 = p_178988_1_ >> 24 & 0xFF;
        this.func_178972_a(var3, var4, var5, var6, var7);
    }
    
    public void func_178994_b(final float p_178994_1_, final float p_178994_2_, final float p_178994_3_, final int p_178994_4_) {
        final int var5 = this.getGLCallListForPass(p_178994_4_);
        final int var6 = MathHelper.clamp_int((int)(p_178994_1_ * 255.0f), 0, 255);
        final int var7 = MathHelper.clamp_int((int)(p_178994_2_ * 255.0f), 0, 255);
        final int var8 = MathHelper.clamp_int((int)(p_178994_3_ * 255.0f), 0, 255);
        this.func_178972_a(var5, var6, var7, var8, 255);
    }
    
    private void func_178972_a(final int p_178972_1_, final int p_178972_2_, final int p_178972_3_, final int p_178972_4_, final int p_178972_5_) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(p_178972_1_, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
        }
        else {
            this.rawIntBuffer.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
        }
    }
    
    public void setColorRGBA(int p_178961_1_, int p_178961_2_, int p_178961_3_, int p_178961_4_) {
        if (!this.needsUpdate) {
            if (p_178961_1_ > 255) {
                p_178961_1_ = 255;
            }
            if (p_178961_2_ > 255) {
                p_178961_2_ = 255;
            }
            if (p_178961_3_ > 255) {
                p_178961_3_ = 255;
            }
            if (p_178961_4_ > 255) {
                p_178961_4_ = 255;
            }
            if (p_178961_1_ < 0) {
                p_178961_1_ = 0;
            }
            if (p_178961_2_ < 0) {
                p_178961_2_ = 0;
            }
            if (p_178961_3_ < 0) {
                p_178961_3_ = 0;
            }
            if (p_178961_4_ < 0) {
                p_178961_4_ = 0;
            }
            if (!this.vertexFormat.func_177346_d()) {
                final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4);
                this.vertexFormat.setElement(var5);
            }
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                this.field_179007_h = (p_178961_4_ << 24 | p_178961_3_ << 16 | p_178961_2_ << 8 | p_178961_1_);
            }
            else {
                this.field_179007_h = (p_178961_1_ << 24 | p_178961_2_ << 16 | p_178961_3_ << 8 | p_178961_4_);
            }
        }
    }
    
    public void func_178982_a(final byte p_178982_1_, final byte p_178982_2_, final byte p_178982_3_) {
        this.setPosition(p_178982_1_ & 0xFF, p_178982_2_ & 0xFF, p_178982_3_ & 0xFF);
    }
    
    public void addVertexWithUV(final double p_178985_1_, final double p_178985_3_, final double p_178985_5_, final double p_178985_7_, final double p_178985_9_) {
        this.setTextureUV(p_178985_7_, p_178985_9_);
        this.addVertex(p_178985_1_, p_178985_3_, p_178985_5_);
    }
    
    public void setVertexFormat(final VertexFormat p_178967_1_) {
        this.vertexFormat = new VertexFormat(p_178967_1_);
    }
    
    public void func_178981_a(final int[] p_178981_1_) {
        final int var2 = this.vertexFormat.func_177338_f() / 4;
        this.vertexCount += p_178981_1_.length / var2;
        this.rawIntBuffer.position(this.rawBufferIndex);
        this.rawIntBuffer.put(p_178981_1_);
        this.rawBufferIndex += p_178981_1_.length;
    }
    
    public void addVertex(final double p_178984_1_, final double p_178984_3_, final double p_178984_5_) {
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
            this.growBuffer(2097152);
        }
        final List var7 = this.vertexFormat.func_177343_g();
        for (int listSize = var7.size(), i = 0; i < listSize; ++i) {
            final VertexFormatElement var8 = var7.get(i);
            final int var9 = var8.func_177373_a() >> 2;
            final int var10 = this.rawBufferIndex + var9;
            switch (SwitchEnumUseage.field_178959_a[var8.func_177375_c().ordinal()]) {
                case 1: {
                    this.rawIntBuffer.put(var10, Float.floatToRawIntBits((float)(p_178984_1_ + this.xOffset)));
                    this.rawIntBuffer.put(var10 + 1, Float.floatToRawIntBits((float)(p_178984_3_ + this.yOffset)));
                    this.rawIntBuffer.put(var10 + 2, Float.floatToRawIntBits((float)(p_178984_5_ + this.zOffset)));
                    break;
                }
                case 2: {
                    this.rawIntBuffer.put(var10, this.field_179007_h);
                    break;
                }
                case 3: {
                    if (var8.func_177369_e() == 0) {
                        this.rawIntBuffer.put(var10, Float.floatToRawIntBits((float)this.field_178998_e));
                        this.rawIntBuffer.put(var10 + 1, Float.floatToRawIntBits((float)this.field_178995_f));
                        break;
                    }
                    this.rawIntBuffer.put(var10, this.brightness);
                    break;
                }
                case 4: {
                    this.rawIntBuffer.put(var10, this.field_179003_o);
                    break;
                }
            }
        }
        this.rawBufferIndex += this.vertexFormat.func_177338_f() >> 2;
        ++this.vertexCount;
    }
    
    public void func_178991_c(final int p_178991_1_) {
        final int var2 = p_178991_1_ >> 16 & 0xFF;
        final int var3 = p_178991_1_ >> 8 & 0xFF;
        final int var4 = p_178991_1_ & 0xFF;
        this.setPosition(var2, var3, var4);
    }
    
    public void func_178974_a(final int p_178974_1_, final int p_178974_2_) {
        final int var3 = p_178974_1_ >> 16 & 0xFF;
        final int var4 = p_178974_1_ >> 8 & 0xFF;
        final int var5 = p_178974_1_ & 0xFF;
        this.setColorRGBA(var3, var4, var5, p_178974_2_);
    }
    
    public void setColorRGBA_I(final int p_178974_1_, final int p_178974_2_) {
        final int var3 = p_178974_1_ >> 16 & 0xFF;
        final int var4 = p_178974_1_ >> 8 & 0xFF;
        final int var5 = p_178974_1_ & 0xFF;
        this.setColorRGBA(var3, var4, var5, p_178974_2_);
    }
    
    public void markDirty() {
        this.needsUpdate = true;
    }
    
    public void func_178980_d(final float p_178980_1_, final float p_178980_2_, final float p_178980_3_) {
        if (!this.vertexFormat.func_177350_b()) {
            final VertexFormatElement var7 = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3);
            this.vertexFormat.setElement(var7);
            this.vertexFormat.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        }
        final byte var8 = (byte)(p_178980_1_ * 127.0f);
        final byte var9 = (byte)(p_178980_2_ * 127.0f);
        final byte var10 = (byte)(p_178980_3_ * 127.0f);
        this.field_179003_o = ((var8 & 0xFF) | (var9 & 0xFF) << 8 | (var10 & 0xFF) << 16);
    }
    
    public void func_178975_e(final float p_178975_1_, final float p_178975_2_, final float p_178975_3_) {
        final byte var4 = (byte)(p_178975_1_ * 127.0f);
        final byte var5 = (byte)(p_178975_2_ * 127.0f);
        final byte var6 = (byte)(p_178975_3_ * 127.0f);
        final int var7 = this.vertexFormat.func_177338_f() >> 2;
        final int var8 = (this.vertexCount - 4) * var7 + this.vertexFormat.func_177342_c() / 4;
        this.field_179003_o = ((var4 & 0xFF) | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
        this.rawIntBuffer.put(var8, this.field_179003_o);
        this.rawIntBuffer.put(var8 + var7, this.field_179003_o);
        this.rawIntBuffer.put(var8 + var7 * 2, this.field_179003_o);
        this.rawIntBuffer.put(var8 + var7 * 3, this.field_179003_o);
    }
    
    public void setTranslation(final double p_178969_1_, final double p_178969_3_, final double p_178969_5_) {
        this.xOffset = p_178969_1_;
        this.yOffset = p_178969_3_;
        this.zOffset = p_178969_5_;
    }
    
    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0) {
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
        }
        return this.field_179012_p = this.rawBufferIndex * 4;
    }
    
    public int func_178976_e() {
        return this.field_179012_p;
    }
    
    public ByteBuffer func_178966_f() {
        return this.byteBuffer;
    }
    
    public VertexFormat func_178973_g() {
        return this.vertexFormat;
    }
    
    public int func_178989_h() {
        return this.vertexCount;
    }
    
    public int getDrawMode() {
        return this.drawMode;
    }
    
    public void func_178968_d(final int p_178968_1_) {
        for (int var2 = 0; var2 < 4; ++var2) {
            this.func_178988_b(p_178968_1_, var2 + 1);
        }
    }
    
    public void func_178990_f(final float p_178990_1_, final float p_178990_2_, final float p_178990_3_) {
        for (int var4 = 0; var4 < 4; ++var4) {
            this.func_178994_b(p_178990_1_, p_178990_2_, p_178990_3_, var4 + 1);
        }
    }
    
    public class State
    {
        private final int[] field_179019_b;
        private final int field_179020_c;
        private final int field_179017_d;
        private final VertexFormat field_179018_e;
        private static final String __OBFID = "CL_00002568";
        
        public State(final int[] p_i46274_2_, final int p_i46274_3_, final int p_i46274_4_, final VertexFormat p_i46274_5_) {
            this.field_179019_b = p_i46274_2_;
            this.field_179020_c = p_i46274_3_;
            this.field_179017_d = p_i46274_4_;
            this.field_179018_e = p_i46274_5_;
        }
        
        public int[] func_179013_a() {
            return this.field_179019_b;
        }
        
        public int getRawBufferIndex() {
            return this.field_179020_c;
        }
        
        public int getVertexCount() {
            return this.field_179017_d;
        }
        
        public VertexFormat func_179016_d() {
            return this.field_179018_e;
        }
    }
    
    static final class SwitchEnumUseage
    {
        static final int[] field_178959_a;
        private static final String __OBFID = "CL_00002569";
        
        static {
            field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
