/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPainting
/*     */   extends EntityHanging
/*     */ {
/*     */   public EnumArt art;
/*     */   
/*     */   public EntityPainting(World worldIn) {
/*  21 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing) {
/*  26 */     super(worldIn, pos);
/*  27 */     List<EnumArt> list = Lists.newArrayList(); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  29 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/*  31 */       this.art = entitypainting$enumart;
/*  32 */       updateFacingWithBoundingBox(facing);
/*     */       
/*  34 */       if (onValidSurface())
/*     */       {
/*  36 */         list.add(entitypainting$enumart);
/*     */       }
/*     */       b++; }
/*     */     
/*  40 */     if (!list.isEmpty())
/*     */     {
/*  42 */       this.art = list.get(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/*  45 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing, String title) {
/*  50 */     this(worldIn, pos, facing); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  52 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/*  54 */       if (entitypainting$enumart.title.equals(title)) {
/*     */         
/*  56 */         this.art = entitypainting$enumart;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/*  61 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  69 */     tagCompound.setString("Motive", this.art.title);
/*  70 */     super.writeEntityToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  78 */     String s = tagCompund.getString("Motive"); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  80 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/*  82 */       if (entitypainting$enumart.title.equals(s))
/*     */       {
/*  84 */         this.art = entitypainting$enumart;
/*     */       }
/*     */       b++; }
/*     */     
/*  88 */     if (this.art == null)
/*     */     {
/*  90 */       this.art = EnumArt.KEBAB;
/*     */     }
/*     */     
/*  93 */     super.readEntityFromNBT(tagCompund);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  98 */     return this.art.sizeX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/* 103 */     return this.art.sizeY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {
/* 111 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*     */       
/* 113 */       if (brokenEntity instanceof EntityPlayer) {
/*     */         
/* 115 */         EntityPlayer entityplayer = (EntityPlayer)brokenEntity;
/*     */         
/* 117 */         if (entityplayer.capabilities.isCreativeMode) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 123 */       entityDropItem(new ItemStack(Items.painting), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 132 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 133 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 138 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 139 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */   
/*     */   public enum EnumArt
/*     */   {
/* 144 */     KEBAB("Kebab", 16, 16, 0, 0),
/* 145 */     AZTEC("Aztec", 16, 16, 16, 0),
/* 146 */     ALBAN("Alban", 16, 16, 32, 0),
/* 147 */     AZTEC_2("Aztec2", 16, 16, 48, 0),
/* 148 */     BOMB("Bomb", 16, 16, 64, 0),
/* 149 */     PLANT("Plant", 16, 16, 80, 0),
/* 150 */     WASTELAND("Wasteland", 16, 16, 96, 0),
/* 151 */     POOL("Pool", 32, 16, 0, 32),
/* 152 */     COURBET("Courbet", 32, 16, 32, 32),
/* 153 */     SEA("Sea", 32, 16, 64, 32),
/* 154 */     SUNSET("Sunset", 32, 16, 96, 32),
/* 155 */     CREEBET("Creebet", 32, 16, 128, 32),
/* 156 */     WANDERER("Wanderer", 16, 32, 0, 64),
/* 157 */     GRAHAM("Graham", 16, 32, 16, 64),
/* 158 */     MATCH("Match", 32, 32, 0, 128),
/* 159 */     BUST("Bust", 32, 32, 32, 128),
/* 160 */     STAGE("Stage", 32, 32, 64, 128),
/* 161 */     VOID("Void", 32, 32, 96, 128),
/* 162 */     SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
/* 163 */     WITHER("Wither", 32, 32, 160, 128),
/* 164 */     FIGHTERS("Fighters", 64, 32, 0, 96),
/* 165 */     POINTER("Pointer", 64, 64, 0, 192),
/* 166 */     PIGSCENE("Pigscene", 64, 64, 64, 192),
/* 167 */     BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
/* 168 */     SKELETON("Skeleton", 64, 48, 192, 64),
/* 169 */     DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);
/*     */     
/* 171 */     public static final int field_180001_A = "SkullAndRoses".length();
/*     */     
/*     */     public final String title;
/*     */     
/*     */     public final int sizeX;
/*     */     
/*     */     public final int sizeY;
/*     */     
/*     */     EnumArt(String titleIn, int width, int height, int textureU, int textureV) {
/* 180 */       this.title = titleIn;
/* 181 */       this.sizeX = width;
/* 182 */       this.sizeY = height;
/* 183 */       this.offsetX = textureU;
/* 184 */       this.offsetY = textureV;
/*     */     }
/*     */     
/*     */     public final int offsetX;
/*     */     public final int offsetY;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\item\EntityPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */