package org.spongepowered.asm.util.perf;

import com.google.common.base.Joiner;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.spongepowered.asm.util.PrettyPrinter;

public final class Profiler {
   public static final int ROOT = 1;
   public static final int FINE = 2;
   private final Map sections = new TreeMap();
   private final List phases = new ArrayList();
   private final Deque stack = new LinkedList();
   private boolean active;

   public Profiler() {
      this.phases.add("Initial");
   }

   public void setActive(boolean var1) {
      if (!this.active && var1 || !var1) {
         this.reset();
      }

      this.active = var1;
   }

   public void reset() {
      Iterator var1 = this.sections.values().iterator();

      while(var1.hasNext()) {
         Profiler.Section var2 = (Profiler.Section)var1.next();
         var2.invalidate();
      }

      this.sections.clear();
      this.phases.clear();
      this.phases.add("Initial");
      this.stack.clear();
   }

   public Profiler.Section get(String var1) {
      Object var2 = (Profiler.Section)this.sections.get(var1);
      if (var2 == null) {
         var2 = this.active ? new Profiler.LiveSection(this, var1, this.phases.size() - 1) : new Profiler.Section(this, var1);
         this.sections.put(var1, var2);
      }

      return (Profiler.Section)var2;
   }

   private Profiler.Section getSubSection(String var1, String var2, Profiler.Section var3) {
      Object var4 = (Profiler.Section)this.sections.get(var1);
      if (var4 == null) {
         var4 = new Profiler.SubSection(this, var1, this.phases.size() - 1, var2, var3);
         this.sections.put(var1, var4);
      }

      return (Profiler.Section)var4;
   }

   boolean isHead(Profiler.Section var1) {
      return this.stack.peek() == var1;
   }

   public Profiler.Section begin(String... var1) {
      return this.begin(0, (String[])var1);
   }

   public Profiler.Section begin(int var1, String... var2) {
      return this.begin(var1, Joiner.on('.').join(var2));
   }

   public Profiler.Section begin(String var1) {
      return this.begin(0, (String)var1);
   }

   public Profiler.Section begin(int var1, String var2) {
      boolean var3 = (var1 & 1) != 0;
      boolean var4 = (var1 & 2) != 0;
      String var5 = var2;
      Profiler.Section var6 = (Profiler.Section)this.stack.peek();
      if (var6 != null) {
         var5 = var6.getName() + (var3 ? " -> " : ".") + var2;
         if (var6.isRoot() && !var3) {
            int var7 = var6.getName().lastIndexOf(" -> ");
            var2 = (var7 > -1 ? var6.getName().substring(var7 + 4) : var6.getName()) + "." + var2;
            var3 = true;
         }
      }

      Profiler.Section var8 = this.get(var3 ? var2 : var5);
      if (var3 && var6 != null && this.active) {
         var8 = this.getSubSection(var5, var6.getName(), var8);
      }

      var8.setFine(var4).setRoot(var3);
      this.stack.push(var8);
      return var8.start();
   }

   void end(Profiler.Section var1) {
      try {
         Profiler.Section var2 = (Profiler.Section)this.stack.pop();

         for(Profiler.Section var3 = var2; var3 != var1; var3 = (Profiler.Section)this.stack.pop()) {
            if (var3 == null && this.active) {
               if (var2 == null) {
                  throw new IllegalStateException("Attempted to pop " + var1 + " but the stack is empty");
               }

               throw new IllegalStateException("Attempted to pop " + var1 + " which was not in the stack, head was " + var2);
            }
         }
      } catch (NoSuchElementException var4) {
         if (this.active) {
            throw new IllegalStateException("Attempted to pop " + var1 + " but the stack is empty");
         }
      }

   }

   public void mark(String var1) {
      long var2 = 0L;

      Iterator var4;
      Profiler.Section var5;
      for(var4 = this.sections.values().iterator(); var4.hasNext(); var2 += var5.getTime()) {
         var5 = (Profiler.Section)var4.next();
      }

      if (var2 == 0L) {
         int var6 = this.phases.size();
         this.phases.set(var6 - 1, var1);
      } else {
         this.phases.add(var1);
         var4 = this.sections.values().iterator();

         while(var4.hasNext()) {
            var5 = (Profiler.Section)var4.next();
            var5.mark();
         }

      }
   }

   public Collection getSections() {
      return Collections.unmodifiableCollection(this.sections.values());
   }

   public PrettyPrinter printer(boolean var1, boolean var2) {
      PrettyPrinter var3 = new PrettyPrinter();
      int var4 = this.phases.size() + 4;
      int[] var5 = new int[]{0, 1, 2, var4 - 2, var4 - 1};
      Object[] var6 = new Object[var4 * 2];
      int var7 = 0;

      for(int var8 = 0; var7 < var4; var8 = var7 * 2) {
         var6[var8 + 1] = PrettyPrinter.Alignment.RIGHT;
         if (var7 == var5[0]) {
            var6[var8] = (var2 ? "" : "  ") + "Section";
            var6[var8 + 1] = PrettyPrinter.Alignment.LEFT;
         } else if (var7 == var5[1]) {
            var6[var8] = "    TOTAL";
         } else if (var7 == var5[3]) {
            var6[var8] = "    Count";
         } else if (var7 == var5[4]) {
            var6[var8] = "Avg. ";
         } else if (var7 - var5[2] < this.phases.size()) {
            var6[var8] = this.phases.get(var7 - var5[2]);
         } else {
            var6[var8] = "";
         }

         ++var7;
      }

      var3.table(var6).th().hr().add();
      Iterator var12 = this.sections.values().iterator();

      label103:
      while(true) {
         Profiler.Section var13;
         do {
            do {
               do {
                  if (!var12.hasNext()) {
                     return var3.add();
                  }

                  var13 = (Profiler.Section)var12.next();
               } while(var13.isFine() && !var1);
            } while(var2 && var13.getDelegate() != var13);

            this.printSectionRow(var3, var4, var5, var13, var2);
         } while(!var2);

         Iterator var9 = this.sections.values().iterator();

         while(true) {
            Profiler.Section var10;
            Profiler.Section var11;
            do {
               if (!var9.hasNext()) {
                  continue label103;
               }

               var10 = (Profiler.Section)var9.next();
               var11 = var10.getDelegate();
            } while(var10.isFine() && !var1);

            if (var11 == var13 && var11 != var10) {
               this.printSectionRow(var3, var4, var5, var10, var2);
            }
         }
      }
   }

   private void printSectionRow(PrettyPrinter var1, int var2, int[] var3, Profiler.Section var4, boolean var5) {
      boolean var6 = var4.getDelegate() != var4;
      Object[] var7 = new Object[var2];
      int var8 = 1;
      if (var5) {
         var7[0] = var6 ? "  > " + var4.getBaseName() : var4.getName();
      } else {
         var7[0] = (var6 ? "+ " : "  ") + var4.getName();
      }

      long[] var9 = var4.getTimes();
      long[] var10 = var9;
      int var11 = var9.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         long var13 = var10[var12];
         if (var8 == var3[1]) {
            var7[var8++] = var4.getTotalTime() + " ms";
         }

         if (var8 >= var3[2] && var8 < var7.length) {
            var7[var8++] = var13 + " ms";
         }
      }

      var7[var3[3]] = var4.getTotalCount();
      var7[var3[4]] = (new DecimalFormat("   ###0.000 ms")).format(var4.getTotalAverageTime());

      for(int var15 = 0; var15 < var7.length; ++var15) {
         if (var7[var15] == null) {
            var7[var15] = "-";
         }
      }

      var1.tr(var7);
   }

   class SubSection extends Profiler.LiveSection {
      private final String baseName;
      private final Profiler.Section root;
      final Profiler this$0;

      SubSection(Profiler var1, String var2, int var3, String var4, Profiler.Section var5) {
         super(var1, var2, var3);
         this.this$0 = var1;
         this.baseName = var4;
         this.root = var5;
      }

      Profiler.Section invalidate() {
         this.root.invalidate();
         return super.invalidate();
      }

      public String getBaseName() {
         return this.baseName;
      }

      public void setInfo(String var1) {
         this.root.setInfo(var1);
         super.setInfo(var1);
      }

      Profiler.Section getDelegate() {
         return this.root;
      }

      Profiler.Section start() {
         this.root.start();
         return super.start();
      }

      public Profiler.Section end() {
         this.root.stop();
         return super.end();
      }

      public Profiler.Section next(String var1) {
         super.stop();
         return this.root.next(var1);
      }
   }

   class LiveSection extends Profiler.Section {
      private int cursor;
      private long[] times;
      private long start;
      private long time;
      private long markedTime;
      private int count;
      private int markedCount;
      final Profiler this$0;

      LiveSection(Profiler var1, String var2, int var3) {
         super(var1, var2);
         this.this$0 = var1;
         this.cursor = 0;
         this.times = new long[0];
         this.start = 0L;
         this.cursor = var3;
      }

      Profiler.Section start() {
         this.start = System.currentTimeMillis();
         return this;
      }

      protected Profiler.Section stop() {
         if (this.start > 0L) {
            this.time += System.currentTimeMillis() - this.start;
         }

         this.start = 0L;
         ++this.count;
         return this;
      }

      public Profiler.Section end() {
         this.stop();
         if (!this.invalidated) {
            this.this$0.end(this);
         }

         return this;
      }

      void mark() {
         if (this.cursor >= this.times.length) {
            this.times = Arrays.copyOf(this.times, this.cursor + 4);
         }

         this.times[this.cursor] = this.time;
         this.markedTime += this.time;
         this.markedCount += this.count;
         this.time = 0L;
         this.count = 0;
         ++this.cursor;
      }

      public long getTime() {
         return this.time;
      }

      public long getTotalTime() {
         return this.time + this.markedTime;
      }

      public double getSeconds() {
         return (double)this.time * 0.001D;
      }

      public double getTotalSeconds() {
         return (double)(this.time + this.markedTime) * 0.001D;
      }

      public long[] getTimes() {
         long[] var1 = new long[this.cursor + 1];
         System.arraycopy(this.times, 0, var1, 0, Math.min(this.times.length, this.cursor));
         var1[this.cursor] = this.time;
         return var1;
      }

      public int getCount() {
         return this.count;
      }

      public int getTotalCount() {
         return this.count + this.markedCount;
      }

      public double getAverageTime() {
         return this.count > 0 ? (double)this.time / (double)this.count : 0.0D;
      }

      public double getTotalAverageTime() {
         return this.count > 0 ? (double)(this.time + this.markedTime) / (double)(this.count + this.markedCount) : 0.0D;
      }
   }

   public class Section {
      static final String SEPARATOR_ROOT = " -> ";
      static final String SEPARATOR_CHILD = ".";
      private final String name;
      private boolean root;
      private boolean fine;
      protected boolean invalidated;
      private String info;
      final Profiler this$0;

      Section(Profiler var1, String var2) {
         this.this$0 = var1;
         this.name = var2;
         this.info = var2;
      }

      Profiler.Section getDelegate() {
         return this;
      }

      Profiler.Section invalidate() {
         this.invalidated = true;
         return this;
      }

      Profiler.Section setRoot(boolean var1) {
         this.root = var1;
         return this;
      }

      public boolean isRoot() {
         return this.root;
      }

      Profiler.Section setFine(boolean var1) {
         this.fine = var1;
         return this;
      }

      public boolean isFine() {
         return this.fine;
      }

      public String getName() {
         return this.name;
      }

      public String getBaseName() {
         return this.name;
      }

      public void setInfo(String var1) {
         this.info = var1;
      }

      public String getInfo() {
         return this.info;
      }

      Profiler.Section start() {
         return this;
      }

      protected Profiler.Section stop() {
         return this;
      }

      public Profiler.Section end() {
         if (!this.invalidated) {
            this.this$0.end(this);
         }

         return this;
      }

      public Profiler.Section next(String var1) {
         this.end();
         return this.this$0.begin(var1);
      }

      void mark() {
      }

      public long getTime() {
         return 0L;
      }

      public long getTotalTime() {
         return 0L;
      }

      public double getSeconds() {
         return 0.0D;
      }

      public double getTotalSeconds() {
         return 0.0D;
      }

      public long[] getTimes() {
         return new long[1];
      }

      public int getCount() {
         return 0;
      }

      public int getTotalCount() {
         return 0;
      }

      public double getAverageTime() {
         return 0.0D;
      }

      public double getTotalAverageTime() {
         return 0.0D;
      }

      public final String toString() {
         return this.name;
      }
   }
}
