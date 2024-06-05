package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;
import net.Iw;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.BlockPos;

public class CrashReportCategory {
   private final CrashReport crashReport;
   private final String name;
   private final List children = Lists.newArrayList();
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReportCategory(CrashReport var1, String var2) {
      this.crashReport = var1;
      this.name = var2;
   }

   public static String getCoordinateInfo(double var0, double var2, double var4) {
      return String.format("%.2f,%.2f,%.2f - %s", new Object[]{Double.valueOf(var0), Double.valueOf(var2), Double.valueOf(var4), getCoordinateInfo(new BlockPos(var0, var2, var4))});
   }

   public static String getCoordinateInfo(BlockPos var0) {
      int var1 = var0.getX();
      int var2 = var0.getY();
      int var3 = var0.getZ();
      StringBuilder var4 = new StringBuilder();
      StringBuilder var10000 = var4;
      String var10001 = "World: (%d,%d,%d)";
      byte var10002 = 3;

      try {
         Object[] var31 = new Object[var10002];
         var31[0] = Integer.valueOf(var1);
         var31[1] = Integer.valueOf(var2);
         var31[2] = Integer.valueOf(var3);
         var10000.append(String.format(var10001, var31));
      } catch (Throwable var17) {
         var4.append("(Error finding world loc)");
      }

      var4.append(", ");
      int var5 = var1 >> 4;
      int var6 = var3 >> 4;
      int var7 = var1 & 15;
      int var8 = var2 >> 4;
      int var9 = var3 & 15;
      int var10 = var5 << 4;
      int var11 = var6 << 4;
      int var12 = (var5 + 1 << 4) - 1;
      int var13 = (var6 + 1 << 4) - 1;
      var10000 = var4;
      var10001 = "Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)";
      var10002 = 9;

      try {
         Object[] var33 = new Object[var10002];
         var33[0] = Integer.valueOf(var7);
         var33[1] = Integer.valueOf(var8);
         var33[2] = Integer.valueOf(var9);
         var33[3] = Integer.valueOf(var5);
         var33[4] = Integer.valueOf(var6);
         var33[5] = Integer.valueOf(var10);
         var33[6] = Integer.valueOf(var11);
         var33[7] = Integer.valueOf(var12);
         var33[8] = Integer.valueOf(var13);
         var10000.append(String.format(var10001, var33));
      } catch (Throwable var16) {
         var4.append("(Error finding chunk loc)");
      }

      var4.append(", ");
      var5 = var1 >> 9;
      var6 = var3 >> 9;
      var7 = var5 << 5;
      var8 = var6 << 5;
      var9 = (var5 + 1 << 5) - 1;
      var10 = (var6 + 1 << 5) - 1;
      var11 = var5 << 9;
      var12 = var6 << 9;
      var13 = (var5 + 1 << 9) - 1;
      int var14 = (var6 + 1 << 9) - 1;
      var10000 = var4;
      var10001 = "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)";
      var10002 = 10;

      try {
         Object[] var35 = new Object[var10002];
         var35[0] = Integer.valueOf(var5);
         var35[1] = Integer.valueOf(var6);
         var35[2] = Integer.valueOf(var7);
         var35[3] = Integer.valueOf(var8);
         var35[4] = Integer.valueOf(var9);
         var35[5] = Integer.valueOf(var10);
         var35[6] = Integer.valueOf(var11);
         var35[7] = Integer.valueOf(var12);
         var35[8] = Integer.valueOf(var13);
         var35[9] = Integer.valueOf(var14);
         var10000.append(String.format(var10001, var35));
      } catch (Throwable var15) {
         var4.append("(Error finding world loc)");
      }

      return var4.toString();
   }

   public void addCrashSectionCallable(String var1, Callable var2) {
      CrashReportCategory var10000 = this;
      String var10001 = var1;
      Callable var10002 = var2;

      try {
         var10000.addCrashSection(var10001, var10002.call());
      } catch (Throwable var4) {
         this.addCrashSectionThrowable(var1, var4);
      }

   }

   public void addCrashSection(String var1, Object var2) {
      this.children.add(new Iw(var1, var2));
   }

   public void addCrashSectionThrowable(String var1, Throwable var2) {
      this.addCrashSection(var1, var2);
   }

   public int getPrunedStackTrace(int var1) {
      StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
      if(var2.length <= 0) {
         return 0;
      } else {
         this.stackTrace = new StackTraceElement[var2.length - 3 - var1];
         System.arraycopy(var2, 3 + var1, this.stackTrace, 0, this.stackTrace.length);
         return this.stackTrace.length;
      }
   }

   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement var1, StackTraceElement var2) {
      if(this.stackTrace.length != 0) {
         StackTraceElement var3 = this.stackTrace[0];
         if(var3.isNativeMethod() == var1.isNativeMethod() && var3.getClassName().equals(var1.getClassName()) && var3.getFileName().equals(var1.getFileName()) && var3.getMethodName().equals(var1.getMethodName())) {
            if(this.stackTrace.length > 1) {
               return false;
            } else if(!this.stackTrace[1].equals(var2)) {
               return false;
            } else {
               this.stackTrace[0] = var1;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void trimStackTraceEntriesFromBottom(int var1) {
      StackTraceElement[] var2 = new StackTraceElement[this.stackTrace.length - var1];
      System.arraycopy(this.stackTrace, 0, var2, 0, var2.length);
      this.stackTrace = var2;
   }

   public void appendToStringBuilder(StringBuilder var1) {
      var1.append("-- ").append(this.name).append(" --\n");
      var1.append("Details:");

      for(Iw var3 : this.children) {
         var1.append("\n\t");
         var1.append(var3.b());
         var1.append(": ");
         var1.append(var3.a());
      }

      if(this.stackTrace != null && this.stackTrace.length > 0) {
         var1.append("\nStacktrace:");

         for(StackTraceElement var5 : this.stackTrace) {
            var1.append("\n\tat ");
            var1.append(var5.toString());
         }
      }

   }

   public StackTraceElement[] getStackTrace() {
      return this.stackTrace;
   }

   public static void addBlockInfo(CrashReportCategory var0, BlockPos var1, Block var2, int var3) {
      int var4 = Block.getIdFromBlock(var2);
      var0.addCrashSectionCallable("Block type", CrashReportCategory::lambda$addBlockInfo$0);
      var0.addCrashSectionCallable("Block data value", CrashReportCategory::lambda$addBlockInfo$1);
      var0.addCrashSectionCallable("Block location", CrashReportCategory::lambda$addBlockInfo$2);
   }

   public static void addBlockInfo(CrashReportCategory var0, BlockPos var1, IBlockState var2) {
      var0.addCrashSectionCallable("Block", var2::toString);
      var0.addCrashSectionCallable("Block location", CrashReportCategory::lambda$addBlockInfo$3);
   }

   private static String lambda$addBlockInfo$3(BlockPos var0) throws Exception {
      return getCoordinateInfo(var0);
   }

   private static String lambda$addBlockInfo$2(BlockPos var0) throws Exception {
      return getCoordinateInfo(var0);
   }

   private static String lambda$addBlockInfo$1(int var0) throws Exception {
      return "Unknown? (Got " + var0 + ")";
   }

   private static String lambda$addBlockInfo$0(int var0, Block var1) throws Exception {
      String var10000 = "ID #%d (%s // %s)";
      byte var10001 = 3;

      try {
         Object[] var4 = new Object[var10001];
         var4[0] = Integer.valueOf(var0);
         var4[1] = var1.getUnlocalizedName();
         var4[2] = var1.getClass().getCanonicalName();
         return String.format(var10000, var4);
      } catch (Throwable var3) {
         return "ID #" + var0;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
