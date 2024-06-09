/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompressedStreamTools
/*     */ {
/*     */   public static NBTTagCompound readCompressed(InputStream is) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  28 */     DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  33 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/*  37 */       datainputstream.close();
/*     */     } 
/*     */     
/*  40 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressed(NBTTagCompound p_74799_0_, OutputStream outputStream) throws IOException {
/*  48 */     DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
/*     */ 
/*     */     
/*     */     try {
/*  52 */       write(p_74799_0_, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  56 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void safeWrite(NBTTagCompound p_74793_0_, File p_74793_1_) throws IOException {
/*  62 */     File file1 = new File(String.valueOf(p_74793_1_.getAbsolutePath()) + "_tmp");
/*     */     
/*  64 */     if (file1.exists())
/*     */     {
/*  66 */       file1.delete();
/*     */     }
/*     */     
/*  69 */     write(p_74793_0_, file1);
/*     */     
/*  71 */     if (p_74793_1_.exists())
/*     */     {
/*  73 */       p_74793_1_.delete();
/*     */     }
/*     */     
/*  76 */     if (p_74793_1_.exists())
/*     */     {
/*  78 */       throw new IOException("Failed to delete " + p_74793_1_);
/*     */     }
/*     */ 
/*     */     
/*  82 */     file1.renameTo(p_74793_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74795_0_, File p_74795_1_) throws IOException {
/*  88 */     DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(p_74795_1_));
/*     */ 
/*     */     
/*     */     try {
/*  92 */       write(p_74795_0_, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  96 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NBTTagCompound read(File p_74797_0_) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/* 102 */     if (!p_74797_0_.exists())
/*     */     {
/* 104 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 108 */     DataInputStream datainputstream = new DataInputStream(new FileInputStream(p_74797_0_));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/* 117 */       datainputstream.close();
/*     */     } 
/*     */     
/* 120 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
/* 129 */     return read(inputStream, NBTSizeTracker.INFINITE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInput p_152456_0_, NBTSizeTracker p_152456_1_) throws IOException {
/* 137 */     NBTBase nbtbase = func_152455_a(p_152456_0_, 0, p_152456_1_);
/*     */     
/* 139 */     if (nbtbase instanceof NBTTagCompound)
/*     */     {
/* 141 */       return (NBTTagCompound)nbtbase;
/*     */     }
/*     */ 
/*     */     
/* 145 */     throw new IOException("Root tag must be a named compound tag");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException {
/* 151 */     writeTag(p_74800_0_, p_74800_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeTag(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
/* 156 */     p_150663_1_.writeByte(p_150663_0_.getId());
/*     */     
/* 158 */     if (p_150663_0_.getId() != 0) {
/*     */       
/* 160 */       p_150663_1_.writeUTF("");
/* 161 */       p_150663_0_.write(p_150663_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase func_152455_a(DataInput p_152455_0_, int p_152455_1_, NBTSizeTracker p_152455_2_) throws IOException {
/* 167 */     byte b0 = p_152455_0_.readByte();
/*     */     
/* 169 */     if (b0 == 0)
/*     */     {
/* 171 */       return new NBTTagEnd();
/*     */     }
/*     */ 
/*     */     
/* 175 */     p_152455_0_.readUTF();
/* 176 */     NBTBase nbtbase = NBTBase.createNewByType(b0);
/*     */ 
/*     */     
/*     */     try {
/* 180 */       nbtbase.read(p_152455_0_, p_152455_1_, p_152455_2_);
/* 181 */       return nbtbase;
/*     */     }
/* 183 */     catch (IOException ioexception) {
/*     */       
/* 185 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 186 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 187 */       crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
/* 188 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
/* 189 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\nbt\CompressedStreamTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */