/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import optfine.Config;
/*     */ import optfine.TextureUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldRenderer
/*     */ {
/*     */   private ByteBuffer byteBuffer;
/*     */   private IntBuffer rawIntBuffer;
/*     */   private ShortBuffer field_181676_c;
/*     */   private FloatBuffer rawFloatBuffer;
/*     */   private int vertexCount;
/*     */   private VertexFormatElement field_181677_f;
/*     */   private int field_181678_g;
/*     */   private boolean needsUpdate;
/*     */   private int drawMode;
/*     */   private double xOffset;
/*     */   private double yOffset;
/*     */   private double zOffset;
/*     */   private VertexFormat vertexFormat;
/*     */   private boolean isDrawing;
/*     */   private static final String __OBFID = "CL_00000942";
/*  41 */   private EnumWorldBlockLayer blockLayer = null;
/*  42 */   private boolean[] drawnIcons = new boolean[256];
/*  43 */   private TextureAtlasSprite[] quadSprites = null;
/*  44 */   private TextureAtlasSprite quadSprite = null;
/*     */ 
/*     */   
/*     */   public WorldRenderer(int bufferSizeIn) {
/*  48 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
/*  49 */     this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*  50 */     this.field_181676_c = this.byteBuffer.asShortBuffer();
/*  51 */     this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181670_b(int p_181670_1_) {
/*  56 */     if (p_181670_1_ > this.rawIntBuffer.remaining()) {
/*     */       
/*  58 */       int i = this.byteBuffer.capacity();
/*  59 */       int j = i % 2097152;
/*  60 */       int k = j + (((this.rawIntBuffer.position() + p_181670_1_) * 4 - j) / 2097152 + 1) * 2097152;
/*  61 */       LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
/*  62 */       int l = this.rawIntBuffer.position();
/*  63 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
/*  64 */       this.byteBuffer.position(0);
/*  65 */       bytebuffer.put(this.byteBuffer);
/*  66 */       bytebuffer.rewind();
/*  67 */       this.byteBuffer = bytebuffer;
/*  68 */       this.rawFloatBuffer = this.byteBuffer.asFloatBuffer().asReadOnlyBuffer();
/*  69 */       this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*  70 */       this.rawIntBuffer.position(l);
/*  71 */       this.field_181676_c = this.byteBuffer.asShortBuffer();
/*  72 */       this.field_181676_c.position(l << 1);
/*     */       
/*  74 */       if (this.quadSprites != null) {
/*     */         
/*  76 */         TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
/*  77 */         int i1 = getBufferQuadSize();
/*  78 */         this.quadSprites = new TextureAtlasSprite[i1];
/*  79 */         System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181674_a(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
/*  86 */     int i = this.vertexCount / 4;
/*  87 */     float[] afloat = new float[i];
/*     */     
/*  89 */     for (int j = 0; j < i; j++)
/*     */     {
/*  91 */       afloat[j] = func_181665_a(this.rawFloatBuffer, (float)(p_181674_1_ + this.xOffset), (float)(p_181674_2_ + this.yOffset), (float)(p_181674_3_ + this.zOffset), this.vertexFormat.func_181719_f(), j * this.vertexFormat.getNextOffset());
/*     */     }
/*     */     
/*  94 */     Integer[] ainteger = new Integer[i];
/*     */     
/*  96 */     for (int k = 0; k < ainteger.length; k++)
/*     */     {
/*  98 */       ainteger[k] = Integer.valueOf(k);
/*     */     }
/*     */     
/* 101 */     Arrays.sort(ainteger, new WorldRenderer$1(this, afloat));
/* 102 */     BitSet bitset = new BitSet();
/* 103 */     int l = this.vertexFormat.getNextOffset();
/* 104 */     int[] aint = new int[l];
/*     */     
/* 106 */     for (int l1 = 0; (l1 = bitset.nextClearBit(l1)) < ainteger.length; l1++) {
/*     */       
/* 108 */       int i1 = ainteger[l1].intValue();
/*     */       
/* 110 */       if (i1 != l1) {
/*     */         
/* 112 */         this.rawIntBuffer.limit(i1 * l + l);
/* 113 */         this.rawIntBuffer.position(i1 * l);
/* 114 */         this.rawIntBuffer.get(aint);
/* 115 */         int j1 = i1;
/*     */         
/* 117 */         for (int k1 = ainteger[i1].intValue(); j1 != l1; k1 = ainteger[k1].intValue()) {
/*     */           
/* 119 */           this.rawIntBuffer.limit(k1 * l + l);
/* 120 */           this.rawIntBuffer.position(k1 * l);
/* 121 */           IntBuffer intbuffer = this.rawIntBuffer.slice();
/* 122 */           this.rawIntBuffer.limit(j1 * l + l);
/* 123 */           this.rawIntBuffer.position(j1 * l);
/* 124 */           this.rawIntBuffer.put(intbuffer);
/* 125 */           bitset.set(j1);
/* 126 */           j1 = k1;
/*     */         } 
/*     */         
/* 129 */         this.rawIntBuffer.limit(l1 * l + l);
/* 130 */         this.rawIntBuffer.position(l1 * l);
/* 131 */         this.rawIntBuffer.put(aint);
/*     */       } 
/*     */       
/* 134 */       bitset.set(l1);
/*     */     } 
/*     */     
/* 137 */     if (this.quadSprites != null) {
/*     */       
/* 139 */       TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
/* 140 */       int i2 = this.vertexFormat.func_181719_f() / 4 * 4;
/*     */       
/* 142 */       for (int j2 = 0; j2 < ainteger.length; j2++) {
/*     */         
/* 144 */         int k2 = ainteger[j2].intValue();
/* 145 */         atextureatlassprite[j2] = this.quadSprites[k2];
/*     */       } 
/*     */       
/* 148 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public State func_181672_a() {
/* 154 */     this.rawIntBuffer.rewind();
/* 155 */     int i = func_181664_j();
/* 156 */     this.rawIntBuffer.limit(i);
/* 157 */     int[] aint = new int[i];
/* 158 */     this.rawIntBuffer.get(aint);
/* 159 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/* 160 */     this.rawIntBuffer.position(i);
/* 161 */     TextureAtlasSprite[] atextureatlassprite = null;
/*     */     
/* 163 */     if (this.quadSprites != null) {
/*     */       
/* 165 */       int j = this.vertexCount / 4;
/* 166 */       atextureatlassprite = new TextureAtlasSprite[j];
/* 167 */       System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
/*     */     } 
/*     */     
/* 170 */     return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_181664_j() {
/* 175 */     return this.vertexCount * this.vertexFormat.func_181719_f();
/*     */   }
/*     */ 
/*     */   
/*     */   private static float func_181665_a(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
/* 180 */     float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
/* 181 */     float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
/* 182 */     float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
/* 183 */     float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
/* 184 */     float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
/* 185 */     float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
/* 186 */     float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
/* 187 */     float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
/* 188 */     float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
/* 189 */     float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
/* 190 */     float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
/* 191 */     float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
/* 192 */     float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
/* 193 */     float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
/* 194 */     float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
/* 195 */     return f12 * f12 + f13 * f13 + f14 * f14;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVertexState(State state) {
/* 200 */     this.rawIntBuffer.clear();
/* 201 */     func_181670_b((state.getRawBuffer()).length);
/* 202 */     this.rawIntBuffer.put(state.getRawBuffer());
/* 203 */     this.vertexCount = state.getVertexCount();
/* 204 */     this.vertexFormat = new VertexFormat(state.getVertexFormat());
/*     */     
/* 206 */     if (state.stateQuadSprites != null) {
/*     */       
/* 208 */       if (this.quadSprites == null)
/*     */       {
/* 210 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*     */       }
/*     */       
/* 213 */       TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
/* 214 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*     */     }
/*     */     else {
/*     */       
/* 218 */       this.quadSprites = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 224 */     this.vertexCount = 0;
/* 225 */     this.field_181677_f = null;
/* 226 */     this.field_181678_g = 0;
/* 227 */     this.quadSprite = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin(int p_181668_1_, VertexFormat p_181668_2_) {
/* 232 */     if (this.isDrawing)
/*     */     {
/* 234 */       throw new IllegalStateException("Already building!");
/*     */     }
/*     */ 
/*     */     
/* 238 */     this.isDrawing = true;
/* 239 */     reset();
/* 240 */     this.drawMode = p_181668_1_;
/* 241 */     this.vertexFormat = p_181668_2_;
/* 242 */     this.field_181677_f = p_181668_2_.getElement(this.field_181678_g);
/* 243 */     this.needsUpdate = false;
/* 244 */     this.byteBuffer.limit(this.byteBuffer.capacity());
/*     */     
/* 246 */     if (Config.isMultiTexture()) {
/*     */       
/* 248 */       if (this.blockLayer != null && this.quadSprites == null)
/*     */       {
/* 250 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 255 */       this.quadSprites = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldRenderer tex(double p_181673_1_, double p_181673_3_) {
/* 262 */     if (this.quadSprite != null && this.quadSprites != null) {
/*     */       
/* 264 */       p_181673_1_ = this.quadSprite.toSingleU((float)p_181673_1_);
/* 265 */       p_181673_3_ = this.quadSprite.toSingleV((float)p_181673_3_);
/* 266 */       this.quadSprites[this.vertexCount / 4] = this.quadSprite;
/*     */     } 
/*     */     
/* 269 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*     */     
/* 271 */     switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
/*     */       
/*     */       case 1:
/* 274 */         this.byteBuffer.putFloat(i, (float)p_181673_1_);
/* 275 */         this.byteBuffer.putFloat(i + 4, (float)p_181673_3_);
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 280 */         this.byteBuffer.putInt(i, (int)p_181673_1_);
/* 281 */         this.byteBuffer.putInt(i + 4, (int)p_181673_3_);
/*     */         break;
/*     */       
/*     */       case 4:
/*     */       case 5:
/* 286 */         this.byteBuffer.putShort(i, (short)(int)p_181673_3_);
/* 287 */         this.byteBuffer.putShort(i + 2, (short)(int)p_181673_1_);
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/* 292 */         this.byteBuffer.put(i, (byte)(int)p_181673_3_);
/* 293 */         this.byteBuffer.put(i + 1, (byte)(int)p_181673_1_);
/*     */         break;
/*     */     } 
/* 296 */     func_181667_k();
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer lightmap(int p_181671_1_, int p_181671_2_) {
/* 302 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*     */     
/* 304 */     switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
/*     */       
/*     */       case 1:
/* 307 */         this.byteBuffer.putFloat(i, p_181671_1_);
/* 308 */         this.byteBuffer.putFloat(i + 4, p_181671_2_);
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 313 */         this.byteBuffer.putInt(i, p_181671_1_);
/* 314 */         this.byteBuffer.putInt(i + 4, p_181671_2_);
/*     */         break;
/*     */       
/*     */       case 4:
/*     */       case 5:
/* 319 */         this.byteBuffer.putShort(i, (short)p_181671_2_);
/* 320 */         this.byteBuffer.putShort(i + 2, (short)p_181671_1_);
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/* 325 */         this.byteBuffer.put(i, (byte)p_181671_2_);
/* 326 */         this.byteBuffer.put(i + 1, (byte)p_181671_1_);
/*     */         break;
/*     */     } 
/* 329 */     func_181667_k();
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
/* 335 */     int i = (this.vertexCount - 4) * this.vertexFormat.func_181719_f() + this.vertexFormat.getUvOffsetById(1) / 4;
/* 336 */     int j = this.vertexFormat.getNextOffset() >> 2;
/* 337 */     this.rawIntBuffer.put(i, p_178962_1_);
/* 338 */     this.rawIntBuffer.put(i + j, p_178962_2_);
/* 339 */     this.rawIntBuffer.put(i + j * 2, p_178962_3_);
/* 340 */     this.rawIntBuffer.put(i + j * 3, p_178962_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putPosition(double x, double y, double z) {
/* 345 */     int i = this.vertexFormat.func_181719_f();
/* 346 */     int j = (this.vertexCount - 4) * i;
/*     */     
/* 348 */     for (int k = 0; k < 4; k++) {
/*     */       
/* 350 */       int l = j + k * i;
/* 351 */       int i1 = l + 1;
/* 352 */       int j1 = i1 + 1;
/* 353 */       this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
/* 354 */       this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
/* 355 */       this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColorIndex(int p_78909_1_) {
/* 364 */     return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
/* 369 */     int i = getColorIndex(p_178978_4_);
/* 370 */     int j = -1;
/*     */     
/* 372 */     if (!this.needsUpdate) {
/*     */       
/* 374 */       j = this.rawIntBuffer.get(i);
/*     */       
/* 376 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*     */         
/* 378 */         int k = (int)((j & 0xFF) * red);
/* 379 */         int l = (int)((j >> 8 & 0xFF) * green);
/* 380 */         int i1 = (int)((j >> 16 & 0xFF) * blue);
/* 381 */         j &= 0xFF000000;
/* 382 */         j = j | i1 << 16 | l << 8 | k;
/*     */       }
/*     */       else {
/*     */         
/* 386 */         int j1 = (int)((j >> 24 & 0xFF) * red);
/* 387 */         int k1 = (int)((j >> 16 & 0xFF) * green);
/* 388 */         int l1 = (int)((j >> 8 & 0xFF) * blue);
/* 389 */         j &= 0xFF;
/* 390 */         j = j | j1 << 24 | k1 << 16 | l1 << 8;
/*     */       } 
/*     */     } 
/*     */     
/* 394 */     this.rawIntBuffer.put(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void putColor(int argb, int p_178988_2_) {
/* 399 */     int i = getColorIndex(p_178988_2_);
/* 400 */     int j = argb >> 16 & 0xFF;
/* 401 */     int k = argb >> 8 & 0xFF;
/* 402 */     int l = argb & 0xFF;
/* 403 */     int i1 = argb >> 24 & 0xFF;
/* 404 */     putColorRGBA(i, j, k, l, i1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
/* 409 */     int i = getColorIndex(p_178994_4_);
/* 410 */     int j = MathHelper.clamp_int((int)(red * 255.0F), 0, 255);
/* 411 */     int k = MathHelper.clamp_int((int)(green * 255.0F), 0, 255);
/* 412 */     int l = MathHelper.clamp_int((int)(blue * 255.0F), 0, 255);
/* 413 */     putColorRGBA(i, j, k, l, 255);
/*     */   }
/*     */ 
/*     */   
/*     */   private void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
/* 418 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*     */       
/* 420 */       this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
/*     */     }
/*     */     else {
/*     */       
/* 424 */       this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 433 */     this.needsUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer color(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
/* 438 */     return color((int)(p_181666_1_ * 255.0F), (int)(p_181666_2_ * 255.0F), (int)(p_181666_3_ * 255.0F), (int)(p_181666_4_ * 255.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer color(int p_181669_1_, int p_181669_2_, int p_181669_3_, int p_181669_4_) {
/* 443 */     if (this.needsUpdate)
/*     */     {
/* 445 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 449 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*     */     
/* 451 */     switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
/*     */       
/*     */       case 1:
/* 454 */         this.byteBuffer.putFloat(i, p_181669_1_ / 255.0F);
/* 455 */         this.byteBuffer.putFloat(i + 4, p_181669_2_ / 255.0F);
/* 456 */         this.byteBuffer.putFloat(i + 8, p_181669_3_ / 255.0F);
/* 457 */         this.byteBuffer.putFloat(i + 12, p_181669_4_ / 255.0F);
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 462 */         this.byteBuffer.putFloat(i, p_181669_1_);
/* 463 */         this.byteBuffer.putFloat(i + 4, p_181669_2_);
/* 464 */         this.byteBuffer.putFloat(i + 8, p_181669_3_);
/* 465 */         this.byteBuffer.putFloat(i + 12, p_181669_4_);
/*     */         break;
/*     */       
/*     */       case 4:
/*     */       case 5:
/* 470 */         this.byteBuffer.putShort(i, (short)p_181669_1_);
/* 471 */         this.byteBuffer.putShort(i + 2, (short)p_181669_2_);
/* 472 */         this.byteBuffer.putShort(i + 4, (short)p_181669_3_);
/* 473 */         this.byteBuffer.putShort(i + 6, (short)p_181669_4_);
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/* 478 */         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*     */           
/* 480 */           this.byteBuffer.put(i, (byte)p_181669_1_);
/* 481 */           this.byteBuffer.put(i + 1, (byte)p_181669_2_);
/* 482 */           this.byteBuffer.put(i + 2, (byte)p_181669_3_);
/* 483 */           this.byteBuffer.put(i + 3, (byte)p_181669_4_);
/*     */           
/*     */           break;
/*     */         } 
/* 487 */         this.byteBuffer.put(i, (byte)p_181669_4_);
/* 488 */         this.byteBuffer.put(i + 1, (byte)p_181669_3_);
/* 489 */         this.byteBuffer.put(i + 2, (byte)p_181669_2_);
/* 490 */         this.byteBuffer.put(i + 3, (byte)p_181669_1_);
/*     */         break;
/*     */     } 
/*     */     
/* 494 */     func_181667_k();
/* 495 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVertexData(int[] vertexData) {
/* 501 */     func_181670_b(vertexData.length);
/* 502 */     this.rawIntBuffer.position(func_181664_j());
/* 503 */     this.rawIntBuffer.put(vertexData);
/* 504 */     this.vertexCount += vertexData.length / this.vertexFormat.func_181719_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endVertex() {
/* 509 */     this.vertexCount++;
/* 510 */     func_181670_b(this.vertexFormat.func_181719_f());
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer pos(double p_181662_1_, double p_181662_3_, double p_181662_5_) {
/* 515 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*     */     
/* 517 */     switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
/*     */       
/*     */       case 1:
/* 520 */         this.byteBuffer.putFloat(i, (float)(p_181662_1_ + this.xOffset));
/* 521 */         this.byteBuffer.putFloat(i + 4, (float)(p_181662_3_ + this.yOffset));
/* 522 */         this.byteBuffer.putFloat(i + 8, (float)(p_181662_5_ + this.zOffset));
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 527 */         this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(p_181662_1_ + this.xOffset)));
/* 528 */         this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(p_181662_3_ + this.yOffset)));
/* 529 */         this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(p_181662_5_ + this.zOffset)));
/*     */         break;
/*     */       
/*     */       case 4:
/*     */       case 5:
/* 534 */         this.byteBuffer.putShort(i, (short)(int)(p_181662_1_ + this.xOffset));
/* 535 */         this.byteBuffer.putShort(i + 2, (short)(int)(p_181662_3_ + this.yOffset));
/* 536 */         this.byteBuffer.putShort(i + 4, (short)(int)(p_181662_5_ + this.zOffset));
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/* 541 */         this.byteBuffer.put(i, (byte)(int)(p_181662_1_ + this.xOffset));
/* 542 */         this.byteBuffer.put(i + 1, (byte)(int)(p_181662_3_ + this.yOffset));
/* 543 */         this.byteBuffer.put(i + 2, (byte)(int)(p_181662_5_ + this.zOffset));
/*     */         break;
/*     */     } 
/* 546 */     func_181667_k();
/* 547 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putNormal(float x, float y, float z) {
/* 552 */     int i = (byte)(int)(x * 127.0F) & 0xFF;
/* 553 */     int j = (byte)(int)(y * 127.0F) & 0xFF;
/* 554 */     int k = (byte)(int)(z * 127.0F) & 0xFF;
/* 555 */     int l = i | j << 8 | k << 16;
/* 556 */     int i1 = this.vertexFormat.getNextOffset() >> 2;
/* 557 */     int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
/* 558 */     this.rawIntBuffer.put(j1, l);
/* 559 */     this.rawIntBuffer.put(j1 + i1, l);
/* 560 */     this.rawIntBuffer.put(j1 + i1 * 2, l);
/* 561 */     this.rawIntBuffer.put(j1 + i1 * 3, l);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181667_k() {
/* 566 */     this.field_181678_g++;
/* 567 */     this.field_181678_g %= this.vertexFormat.getElementCount();
/* 568 */     this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
/*     */     
/* 570 */     if (this.field_181677_f.getUsage() == VertexFormatElement.EnumUsage.PADDING)
/*     */     {
/* 572 */       func_181667_k();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer normal(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
/* 578 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
/*     */     
/* 580 */     switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
/*     */       
/*     */       case 1:
/* 583 */         this.byteBuffer.putFloat(i, p_181663_1_);
/* 584 */         this.byteBuffer.putFloat(i + 4, p_181663_2_);
/* 585 */         this.byteBuffer.putFloat(i + 8, p_181663_3_);
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 590 */         this.byteBuffer.putInt(i, (int)p_181663_1_);
/* 591 */         this.byteBuffer.putInt(i + 4, (int)p_181663_2_);
/* 592 */         this.byteBuffer.putInt(i + 8, (int)p_181663_3_);
/*     */         break;
/*     */       
/*     */       case 4:
/*     */       case 5:
/* 597 */         this.byteBuffer.putShort(i, (short)((int)p_181663_1_ * 32767 & 0xFFFF));
/* 598 */         this.byteBuffer.putShort(i + 2, (short)((int)p_181663_2_ * 32767 & 0xFFFF));
/* 599 */         this.byteBuffer.putShort(i + 4, (short)((int)p_181663_3_ * 32767 & 0xFFFF));
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/* 604 */         this.byteBuffer.put(i, (byte)((int)p_181663_1_ * 127 & 0xFF));
/* 605 */         this.byteBuffer.put(i + 1, (byte)((int)p_181663_2_ * 127 & 0xFF));
/* 606 */         this.byteBuffer.put(i + 2, (byte)((int)p_181663_3_ * 127 & 0xFF));
/*     */         break;
/*     */     } 
/* 609 */     func_181667_k();
/* 610 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTranslation(double x, double y, double z) {
/* 615 */     this.xOffset = x;
/* 616 */     this.yOffset = y;
/* 617 */     this.zOffset = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishDrawing() {
/* 622 */     if (!this.isDrawing)
/*     */     {
/* 624 */       throw new IllegalStateException("Not building!");
/*     */     }
/*     */ 
/*     */     
/* 628 */     this.isDrawing = false;
/* 629 */     this.byteBuffer.position(0);
/* 630 */     this.byteBuffer.limit(func_181664_j() * 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getByteBuffer() {
/* 636 */     return this.byteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat getVertexFormat() {
/* 641 */     return this.vertexFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVertexCount() {
/* 646 */     return this.vertexCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDrawMode() {
/* 651 */     return this.drawMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putColor4(int argb) {
/* 656 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 658 */       putColor(argb, i + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void putColorRGB_F4(float red, float green, float blue) {
/* 664 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 666 */       putColorRGB_F(red, green, blue, i + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void putSprite(TextureAtlasSprite p_putSprite_1_) {
/* 672 */     if (this.quadSprites != null) {
/*     */       
/* 674 */       int i = this.vertexCount / 4;
/* 675 */       this.quadSprites[i - 1] = p_putSprite_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSprite(TextureAtlasSprite p_setSprite_1_) {
/* 681 */     if (this.quadSprites != null)
/*     */     {
/* 683 */       this.quadSprite = p_setSprite_1_;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultiTexture() {
/* 689 */     return (this.quadSprites != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawMultiTexture() {
/* 694 */     if (this.quadSprites != null) {
/*     */       
/* 696 */       int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
/*     */       
/* 698 */       if (this.drawnIcons.length <= i)
/*     */       {
/* 700 */         this.drawnIcons = new boolean[i + 1];
/*     */       }
/*     */       
/* 703 */       Arrays.fill(this.drawnIcons, false);
/* 704 */       int j = 0;
/* 705 */       int k = -1;
/* 706 */       int l = this.vertexCount / 4;
/*     */       
/* 708 */       for (int i1 = 0; i1 < l; i1++) {
/*     */         
/* 710 */         TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
/*     */         
/* 712 */         if (textureatlassprite != null) {
/*     */           
/* 714 */           int j1 = textureatlassprite.getIndexInMap();
/*     */           
/* 716 */           if (!this.drawnIcons[j1])
/*     */           {
/* 718 */             if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
/*     */               
/* 720 */               if (k < 0)
/*     */               {
/* 722 */                 k = i1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 727 */               i1 = drawForIcon(textureatlassprite, i1) - 1;
/* 728 */               j++;
/*     */               
/* 730 */               if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT)
/*     */               {
/* 732 */                 this.drawnIcons[j1] = true;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 739 */       if (k >= 0) {
/*     */         
/* 741 */         drawForIcon(TextureUtils.iconGrassSideOverlay, k);
/* 742 */         j++;
/*     */       } 
/*     */       
/* 745 */       if (j > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
/* 754 */     GL11.glBindTexture(3553, p_drawForIcon_1_.glSpriteTextureId);
/* 755 */     int i = -1;
/* 756 */     int j = -1;
/* 757 */     int k = this.vertexCount / 4;
/*     */     
/* 759 */     for (int l = p_drawForIcon_2_; l < k; l++) {
/*     */       
/* 761 */       TextureAtlasSprite textureatlassprite = this.quadSprites[l];
/*     */       
/* 763 */       if (textureatlassprite == p_drawForIcon_1_) {
/*     */         
/* 765 */         if (j < 0)
/*     */         {
/* 767 */           j = l;
/*     */         }
/*     */       }
/* 770 */       else if (j >= 0) {
/*     */         
/* 772 */         draw(j, l);
/*     */         
/* 774 */         if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT)
/*     */         {
/* 776 */           return l;
/*     */         }
/*     */         
/* 779 */         j = -1;
/*     */         
/* 781 */         if (i < 0)
/*     */         {
/* 783 */           i = l;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 788 */     if (j >= 0)
/*     */     {
/* 790 */       draw(j, k);
/*     */     }
/*     */     
/* 793 */     if (i < 0)
/*     */     {
/* 795 */       i = k;
/*     */     }
/*     */     
/* 798 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void draw(int p_draw_1_, int p_draw_2_) {
/* 803 */     int i = p_draw_2_ - p_draw_1_;
/*     */     
/* 805 */     if (i > 0) {
/*     */       
/* 807 */       int j = p_draw_1_ * 4;
/* 808 */       int k = i * 4;
/* 809 */       GL11.glDrawArrays(this.drawMode, j, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
/* 815 */     this.blockLayer = p_setBlockLayer_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getBufferQuadSize() {
/* 820 */     int i = this.rawIntBuffer.capacity() * 4 / this.vertexFormat.func_181719_f() * 4;
/* 821 */     return i;
/*     */   }
/*     */   
/*     */   static final class WorldRenderer$2
/*     */   {
/* 826 */     static final int[] field_181661_a = new int[(VertexFormatElement.EnumType.values()).length];
/*     */     
/*     */     private static final String __OBFID = "CL_00002569";
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 833 */         field_181661_a[VertexFormatElement.EnumType.FLOAT.ordinal()] = 1;
/*     */       }
/* 835 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 842 */         field_181661_a[VertexFormatElement.EnumType.UINT.ordinal()] = 2;
/*     */       }
/* 844 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 851 */         field_181661_a[VertexFormatElement.EnumType.INT.ordinal()] = 3;
/*     */       }
/* 853 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 860 */         field_181661_a[VertexFormatElement.EnumType.USHORT.ordinal()] = 4;
/*     */       }
/* 862 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 869 */         field_181661_a[VertexFormatElement.EnumType.SHORT.ordinal()] = 5;
/*     */       }
/* 871 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 878 */         field_181661_a[VertexFormatElement.EnumType.UBYTE.ordinal()] = 6;
/*     */       }
/* 880 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 887 */         field_181661_a[VertexFormatElement.EnumType.BYTE.ordinal()] = 7;
/*     */       }
/* 889 */       catch (NoSuchFieldError noSuchFieldError) {}
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class State
/*     */   {
/*     */     private final int[] stateRawBuffer;
/*     */     
/*     */     private final VertexFormat stateVertexFormat;
/*     */     
/*     */     private static final String __OBFID = "CL_00002568";
/*     */     
/*     */     private TextureAtlasSprite[] stateQuadSprites;
/*     */     
/*     */     public State(int[] p_i2_2_, VertexFormat p_i2_3_, TextureAtlasSprite[] p_i2_4_) {
/* 905 */       this.stateRawBuffer = p_i2_2_;
/* 906 */       this.stateVertexFormat = p_i2_3_;
/* 907 */       this.stateQuadSprites = p_i2_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public State(int[] p_i46453_2_, VertexFormat p_i46453_3_) {
/* 912 */       this.stateRawBuffer = p_i46453_2_;
/* 913 */       this.stateVertexFormat = p_i46453_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int[] getRawBuffer() {
/* 918 */       return this.stateRawBuffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getVertexCount() {
/* 923 */       return this.stateRawBuffer.length / this.stateVertexFormat.func_181719_f();
/*     */     }
/*     */ 
/*     */     
/*     */     public VertexFormat getVertexFormat() {
/* 928 */       return this.stateVertexFormat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\WorldRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */