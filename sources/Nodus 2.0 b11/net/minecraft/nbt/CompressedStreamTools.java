/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.ByteArrayInputStream;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.DataInput;
/*   7:    */ import java.io.DataInputStream;
/*   8:    */ import java.io.DataOutput;
/*   9:    */ import java.io.DataOutputStream;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.FileInputStream;
/*  12:    */ import java.io.FileOutputStream;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.io.InputStream;
/*  15:    */ import java.io.OutputStream;
/*  16:    */ import java.util.zip.GZIPInputStream;
/*  17:    */ import java.util.zip.GZIPOutputStream;
/*  18:    */ import net.minecraft.crash.CrashReport;
/*  19:    */ import net.minecraft.crash.CrashReportCategory;
/*  20:    */ import net.minecraft.util.ReportedException;
/*  21:    */ 
/*  22:    */ public class CompressedStreamTools
/*  23:    */ {
/*  24:    */   private static final String __OBFID = "CL_00001226";
/*  25:    */   
/*  26:    */   public static NBTTagCompound readCompressed(InputStream par0InputStream)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 31 */     DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
/*  30:    */     NBTTagCompound var2;
/*  31:    */     try
/*  32:    */     {
/*  33: 36 */       var2 = read(var1);
/*  34:    */     }
/*  35:    */     finally
/*  36:    */     {
/*  37:    */       NBTTagCompound var2;
/*  38: 40 */       var1.close();
/*  39:    */     }
/*  40: 43 */     return var2;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void writeCompressed(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 51 */     DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));
/*  47:    */     try
/*  48:    */     {
/*  49: 55 */       write(par0NBTTagCompound, var2);
/*  50:    */     }
/*  51:    */     finally
/*  52:    */     {
/*  53: 59 */       var2.close();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static NBTTagCompound decompress(byte[] par0ArrayOfByte)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 65 */     DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
/*  61:    */     NBTTagCompound var2;
/*  62:    */     try
/*  63:    */     {
/*  64: 70 */       var2 = read(var1);
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68:    */       NBTTagCompound var2;
/*  69: 74 */       var1.close();
/*  70:    */     }
/*  71: 77 */     return var2;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static byte[] compress(NBTTagCompound par0NBTTagCompound)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77: 82 */     ByteArrayOutputStream var1 = new ByteArrayOutputStream();
/*  78: 83 */     DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));
/*  79:    */     try
/*  80:    */     {
/*  81: 87 */       write(par0NBTTagCompound, var2);
/*  82:    */     }
/*  83:    */     finally
/*  84:    */     {
/*  85: 91 */       var2.close();
/*  86:    */     }
/*  87: 94 */     return var1.toByteArray();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void safeWrite(NBTTagCompound par0NBTTagCompound, File par1File)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93: 99 */     File var2 = new File(par1File.getAbsolutePath() + "_tmp");
/*  94:101 */     if (var2.exists()) {
/*  95:103 */       var2.delete();
/*  96:    */     }
/*  97:106 */     write(par0NBTTagCompound, var2);
/*  98:108 */     if (par1File.exists()) {
/*  99:110 */       par1File.delete();
/* 100:    */     }
/* 101:113 */     if (par1File.exists()) {
/* 102:115 */       throw new IOException("Failed to delete " + par1File);
/* 103:    */     }
/* 104:119 */     var2.renameTo(par1File);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static void write(NBTTagCompound par0NBTTagCompound, File par1File)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:125 */     DataOutputStream var2 = new DataOutputStream(new FileOutputStream(par1File));
/* 111:    */     try
/* 112:    */     {
/* 113:129 */       write(par0NBTTagCompound, var2);
/* 114:    */     }
/* 115:    */     finally
/* 116:    */     {
/* 117:133 */       var2.close();
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static NBTTagCompound read(File par0File)
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:139 */     if (!par0File.exists()) {
/* 125:141 */       return null;
/* 126:    */     }
/* 127:145 */     DataInputStream var1 = new DataInputStream(new FileInputStream(par0File));
/* 128:    */     NBTTagCompound var2;
/* 129:    */     try
/* 130:    */     {
/* 131:150 */       var2 = read(var1);
/* 132:    */     }
/* 133:    */     finally
/* 134:    */     {
/* 135:    */       NBTTagCompound var2;
/* 136:154 */       var1.close();
/* 137:    */     }
/* 138:157 */     return var2;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static NBTTagCompound read(DataInput par0DataInput)
/* 142:    */     throws IOException
/* 143:    */   {
/* 144:166 */     NBTBase var1 = func_150664_a(par0DataInput, 0);
/* 145:168 */     if ((var1 instanceof NBTTagCompound)) {
/* 146:170 */       return (NBTTagCompound)var1;
/* 147:    */     }
/* 148:174 */     throw new IOException("Root tag must be a named compound tag");
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static void write(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput)
/* 152:    */     throws IOException
/* 153:    */   {
/* 154:180 */     func_150663_a(par0NBTTagCompound, par1DataOutput);
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static void func_150663_a(NBTBase p_150663_0_, DataOutput p_150663_1_)
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:185 */     p_150663_1_.writeByte(p_150663_0_.getId());
/* 161:187 */     if (p_150663_0_.getId() != 0)
/* 162:    */     {
/* 163:189 */       p_150663_1_.writeUTF("");
/* 164:190 */       p_150663_0_.write(p_150663_1_);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private static NBTBase func_150664_a(DataInput p_150664_0_, int p_150664_1_)
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:196 */     byte var2 = p_150664_0_.readByte();
/* 172:198 */     if (var2 == 0) {
/* 173:200 */       return new NBTTagEnd();
/* 174:    */     }
/* 175:204 */     p_150664_0_.readUTF();
/* 176:205 */     NBTBase var3 = NBTBase.func_150284_a(var2);
/* 177:    */     try
/* 178:    */     {
/* 179:209 */       var3.load(p_150664_0_, p_150664_1_);
/* 180:210 */       return var3;
/* 181:    */     }
/* 182:    */     catch (IOException var7)
/* 183:    */     {
/* 184:214 */       CrashReport var5 = CrashReport.makeCrashReport(var7, "Loading NBT data");
/* 185:215 */       CrashReportCategory var6 = var5.makeCategory("NBT Tag");
/* 186:216 */       var6.addCrashSection("Tag name", "[UNNAMED TAG]");
/* 187:217 */       var6.addCrashSection("Tag type", Byte.valueOf(var2));
/* 188:218 */       throw new ReportedException(var5);
/* 189:    */     }
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.CompressedStreamTools
 * JD-Core Version:    0.7.0.1
 */