package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrettyPrinter {
   private final PrettyPrinter.HorizontalRule horizontalRule;
   private final List lines;
   private PrettyPrinter.Table table;
   private boolean recalcWidth;
   protected int width;
   protected int wrapWidth;
   protected int kvKeyWidth;
   protected String kvFormat;

   public PrettyPrinter() {
      this(100);
   }

   public PrettyPrinter(int var1) {
      this.horizontalRule = new PrettyPrinter.HorizontalRule(this, new char[]{'*'});
      this.lines = new ArrayList();
      this.recalcWidth = false;
      this.width = 100;
      this.wrapWidth = 80;
      this.kvKeyWidth = 10;
      this.kvFormat = makeKvFormat(this.kvKeyWidth);
      this.width = var1;
   }

   public PrettyPrinter wrapTo(int var1) {
      this.wrapWidth = var1;
      return this;
   }

   public int wrapTo() {
      return this.wrapWidth;
   }

   public PrettyPrinter table() {
      this.table = new PrettyPrinter.Table();
      return this;
   }

   public PrettyPrinter table(String... var1) {
      this.table = new PrettyPrinter.Table();
      String[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         this.table.addColumn(var5);
      }

      return this;
   }

   public PrettyPrinter table(Object... var1) {
      this.table = new PrettyPrinter.Table();
      PrettyPrinter.Column var2 = null;
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         if (var6 instanceof String) {
            var2 = this.table.addColumn((String)var6);
         } else if (var6 instanceof Integer && var2 != null) {
            int var7 = (Integer)var6;
            if (var7 > 0) {
               var2.setWidth(var7);
            } else if (var7 < 0) {
               var2.setMaxWidth(-var7);
            }
         } else if (var6 instanceof PrettyPrinter.Alignment && var2 != null) {
            var2.setAlignment((PrettyPrinter.Alignment)var6);
         } else if (var6 != null) {
            var2 = this.table.addColumn(var6.toString());
         }
      }

      return this;
   }

   public PrettyPrinter spacing(int var1) {
      if (this.table == null) {
         this.table = new PrettyPrinter.Table();
      }

      this.table.setColSpacing(var1);
      return this;
   }

   public PrettyPrinter th() {
      return this.th(false);
   }

   private PrettyPrinter th(boolean var1) {
      if (this.table == null) {
         this.table = new PrettyPrinter.Table();
      }

      if (!var1 || this.table.addHeader) {
         this.table.headerAdded();
         this.addLine(this.table);
      }

      return this;
   }

   public PrettyPrinter tr(Object... var1) {
      this.th(true);
      this.addLine(this.table.addRow(var1));
      this.recalcWidth = true;
      return this;
   }

   public PrettyPrinter add() {
      this.addLine("");
      return this;
   }

   public PrettyPrinter add(String var1) {
      this.addLine(var1);
      this.width = Math.max(this.width, var1.length());
      return this;
   }

   public PrettyPrinter add(String var1, Object... var2) {
      String var3 = String.format(var1, var2);
      this.addLine(var3);
      this.width = Math.max(this.width, var3.length());
      return this;
   }

   public PrettyPrinter add(Object[] var1) {
      return this.add(var1, "%s");
   }

   public PrettyPrinter add(Object[] var1, String var2) {
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         this.add(var2, var6);
      }

      return this;
   }

   public PrettyPrinter addIndexed(Object[] var1) {
      int var2 = String.valueOf(var1.length - 1).length();
      String var3 = "[%" + var2 + "d] %s";

      for(int var4 = 0; var4 < var1.length; ++var4) {
         this.add(var3, var4, var1[var4]);
      }

      return this;
   }

   public PrettyPrinter addWithIndices(Collection var1) {
      return this.addIndexed(var1.toArray());
   }

   public PrettyPrinter add(PrettyPrinter.IPrettyPrintable var1) {
      if (var1 != null) {
         var1.print(this);
      }

      return this;
   }

   public PrettyPrinter add(Throwable var1) {
      return this.add((Throwable)var1, 4);
   }

   public PrettyPrinter add(Throwable var1, int var2) {
      while(var1 != null) {
         this.add("%s: %s", var1.getClass().getName(), var1.getMessage());
         this.add(var1.getStackTrace(), var2);
         var1 = var1.getCause();
      }

      return this;
   }

   public PrettyPrinter add(StackTraceElement[] var1, int var2) {
      String var3 = Strings.repeat(" ", var2);
      StackTraceElement[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         StackTraceElement var7 = var4[var6];
         this.add("%s%s", var3, var7);
      }

      return this;
   }

   public PrettyPrinter add(Object var1) {
      return this.add((Object)var1, 0);
   }

   public PrettyPrinter add(Object var1, int var2) {
      String var3 = Strings.repeat(" ", var2);
      return this.append(var1, var2, var3);
   }

   private PrettyPrinter append(Object var1, int var2, String var3) {
      if (var1 instanceof String) {
         return this.add("%s%s", var3, var1);
      } else if (!(var1 instanceof Iterable)) {
         if (var1 instanceof Map) {
            this.kvWidth(var2);
            return this.add((Map)var1);
         } else if (var1 instanceof PrettyPrinter.IPrettyPrintable) {
            return this.add((PrettyPrinter.IPrettyPrintable)var1);
         } else if (var1 instanceof Throwable) {
            return this.add((Throwable)var1, var2);
         } else {
            return var1.getClass().isArray() ? this.add((Object[])((Object[])var1), var2 + "%s") : this.add("%s%s", var3, var1);
         }
      } else {
         Iterator var4 = ((Iterable)var1).iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            this.append(var5, var2, var3);
         }

         return this;
      }
   }

   public PrettyPrinter addWrapped(String var1, Object... var2) {
      return this.addWrapped(this.wrapWidth, var1, var2);
   }

   public PrettyPrinter addWrapped(int var1, String var2, Object... var3) {
      String var4 = "";
      String var5 = String.format(var2, var3).replace("\t", "    ");
      Matcher var6 = Pattern.compile("^(\\s+)(.*)$").matcher(var5);
      if (var6.matches()) {
         var4 = var6.group(1);
      }

      try {
         Iterator var7 = this.getWrapped(var1, var5, var4).iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            this.addLine(var8);
         }
      } catch (Exception var9) {
         this.add(var5);
      }

      return this;
   }

   private List getWrapped(int var1, String var2, String var3) {
      ArrayList var4;
      int var5;
      for(var4 = new ArrayList(); var2.length() > var1; var2 = var3 + var2.substring(var5 + 1)) {
         var5 = var2.lastIndexOf(32, var1);
         if (var5 < 10) {
            var5 = var1;
         }

         String var6 = var2.substring(0, var5);
         var4.add(var6);
      }

      if (var2.length() > 0) {
         var4.add(var2);
      }

      return var4;
   }

   public PrettyPrinter kv(String var1, String var2, Object... var3) {
      return this.kv(var1, String.format(var2, var3));
   }

   public PrettyPrinter kv(String var1, Object var2) {
      this.addLine(new PrettyPrinter.KeyValue(this, var1, var2));
      return this.kvWidth(var1.length());
   }

   public PrettyPrinter kvWidth(int var1) {
      if (var1 > this.kvKeyWidth) {
         this.kvKeyWidth = var1;
         this.kvFormat = makeKvFormat(var1);
      }

      this.recalcWidth = true;
      return this;
   }

   public PrettyPrinter add(Map var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         String var4 = var3.getKey() == null ? "null" : var3.getKey().toString();
         this.kv(var4, var3.getValue());
      }

      return this;
   }

   public PrettyPrinter hr() {
      return this.hr('*');
   }

   public PrettyPrinter hr(char var1) {
      this.addLine(new PrettyPrinter.HorizontalRule(this, new char[]{var1}));
      return this;
   }

   public PrettyPrinter centre() {
      if (!this.lines.isEmpty()) {
         Object var1 = this.lines.get(this.lines.size() - 1);
         if (var1 instanceof String) {
            this.addLine(new PrettyPrinter.CentredText(this, this.lines.remove(this.lines.size() - 1)));
         }
      }

      return this;
   }

   private void addLine(Object var1) {
      if (var1 != null) {
         this.lines.add(var1);
         this.recalcWidth |= var1 instanceof PrettyPrinter.IVariableWidthEntry;
      }
   }

   public PrettyPrinter trace() {
      return this.trace(getDefaultLoggerName());
   }

   public PrettyPrinter trace(Level var1) {
      return this.trace(getDefaultLoggerName(), var1);
   }

   public PrettyPrinter trace(String var1) {
      return this.trace(System.err, LogManager.getLogger(var1));
   }

   public PrettyPrinter trace(String var1, Level var2) {
      return this.trace(System.err, LogManager.getLogger(var1), var2);
   }

   public PrettyPrinter trace(Logger var1) {
      return this.trace(System.err, var1);
   }

   public PrettyPrinter trace(Logger var1, Level var2) {
      return this.trace(System.err, var1, var2);
   }

   public PrettyPrinter trace(PrintStream var1) {
      return this.trace(var1, getDefaultLoggerName());
   }

   public PrettyPrinter trace(PrintStream var1, Level var2) {
      return this.trace(var1, getDefaultLoggerName(), var2);
   }

   public PrettyPrinter trace(PrintStream var1, String var2) {
      return this.trace(var1, LogManager.getLogger(var2));
   }

   public PrettyPrinter trace(PrintStream var1, String var2, Level var3) {
      return this.trace(var1, LogManager.getLogger(var2), var3);
   }

   public PrettyPrinter trace(PrintStream var1, Logger var2) {
      return this.trace(var1, var2, Level.DEBUG);
   }

   public PrettyPrinter trace(PrintStream var1, Logger var2, Level var3) {
      this.log(var2, var3);
      this.print(var1);
      return this;
   }

   public PrettyPrinter print() {
      return this.print(System.err);
   }

   public PrettyPrinter print(PrintStream var1) {
      this.updateWidth();
      this.printSpecial(var1, this.horizontalRule);
      Iterator var2 = this.lines.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof PrettyPrinter.ISpecialEntry) {
            this.printSpecial(var1, (PrettyPrinter.ISpecialEntry)var3);
         } else {
            this.printString(var1, var3.toString());
         }
      }

      this.printSpecial(var1, this.horizontalRule);
      return this;
   }

   private void printSpecial(PrintStream var1, PrettyPrinter.ISpecialEntry var2) {
      var1.printf("/*%s*/\n", var2.toString());
   }

   private void printString(PrintStream var1, String var2) {
      if (var2 != null) {
         var1.printf("/* %-" + this.width + "s */\n", var2);
      }

   }

   public PrettyPrinter log(Logger var1) {
      return this.log(var1, Level.INFO);
   }

   public PrettyPrinter log(Logger var1, Level var2) {
      this.updateWidth();
      this.logSpecial(var1, var2, this.horizontalRule);
      Iterator var3 = this.lines.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4 instanceof PrettyPrinter.ISpecialEntry) {
            this.logSpecial(var1, var2, (PrettyPrinter.ISpecialEntry)var4);
         } else {
            this.logString(var1, var2, var4.toString());
         }
      }

      this.logSpecial(var1, var2, this.horizontalRule);
      return this;
   }

   private void logSpecial(Logger var1, Level var2, PrettyPrinter.ISpecialEntry var3) {
      var1.log(var2, "/*{}*/", new Object[]{var3.toString()});
   }

   private void logString(Logger var1, Level var2, String var3) {
      if (var3 != null) {
         var1.log(var2, String.format("/* %-" + this.width + "s */", var3));
      }

   }

   private void updateWidth() {
      if (this.recalcWidth) {
         this.recalcWidth = false;
         Iterator var1 = this.lines.iterator();

         while(var1.hasNext()) {
            Object var2 = var1.next();
            if (var2 instanceof PrettyPrinter.IVariableWidthEntry) {
               this.width = Math.min(4096, Math.max(this.width, ((PrettyPrinter.IVariableWidthEntry)var2).getWidth()));
            }
         }
      }

   }

   private static String makeKvFormat(int var0) {
      return String.format("%%%ds : %%s", var0);
   }

   private static String getDefaultLoggerName() {
      String var0 = (new Throwable()).getStackTrace()[2].getClassName();
      int var1 = var0.lastIndexOf(46);
      return var1 == -1 ? var0 : var0.substring(var1 + 1);
   }

   public static void dumpStack() {
      (new PrettyPrinter()).add((Throwable)(new Exception("Stack trace"))).print(System.err);
   }

   public static void print(Throwable var0) {
      (new PrettyPrinter()).add(var0).print(System.err);
   }

   static class Row implements PrettyPrinter.IVariableWidthEntry {
      final PrettyPrinter.Table table;
      final String[] args;

      public Row(PrettyPrinter.Table var1, Object... var2) {
         this.table = var1.grow(var2.length);
         this.args = new String[var2.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.args[var3] = var2[var3].toString();
            ((PrettyPrinter.Column)this.table.columns.get(var3)).setMinWidth(this.args[var3].length());
         }

      }

      public String toString() {
         Object[] var1 = new Object[this.table.columns.size()];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            PrettyPrinter.Column var3 = (PrettyPrinter.Column)this.table.columns.get(var2);
            if (var2 >= this.args.length) {
               var1[var2] = "";
            } else {
               var1[var2] = this.args[var2].length() > var3.getMaxWidth() ? this.args[var2].substring(0, var3.getMaxWidth()) : this.args[var2];
            }
         }

         return String.format(this.table.format, var1);
      }

      public int getWidth() {
         return this.toString().length();
      }
   }

   static class Column {
      private final PrettyPrinter.Table table;
      private PrettyPrinter.Alignment align;
      private int minWidth;
      private int maxWidth;
      private int size;
      private String title;
      private String format;

      Column(PrettyPrinter.Table var1) {
         this.align = PrettyPrinter.Alignment.LEFT;
         this.minWidth = 1;
         this.maxWidth = Integer.MAX_VALUE;
         this.size = 0;
         this.title = "";
         this.format = "%s";
         this.table = var1;
      }

      Column(PrettyPrinter.Table var1, String var2) {
         this(var1);
         this.title = var2;
         this.minWidth = var2.length();
         this.updateFormat();
      }

      Column(PrettyPrinter.Table var1, PrettyPrinter.Alignment var2, int var3, String var4) {
         this(var1, var4);
         this.align = var2;
         this.size = var3;
      }

      void setAlignment(PrettyPrinter.Alignment var1) {
         this.align = var1;
         this.updateFormat();
      }

      void setWidth(int var1) {
         if (var1 > this.size) {
            this.size = var1;
            this.updateFormat();
         }

      }

      void setMinWidth(int var1) {
         if (var1 > this.minWidth) {
            this.minWidth = var1;
            this.updateFormat();
         }

      }

      void setMaxWidth(int var1) {
         this.size = Math.min(this.size, this.maxWidth);
         this.maxWidth = Math.max(1, var1);
         this.updateFormat();
      }

      void setTitle(String var1) {
         this.title = var1;
         this.setWidth(var1.length());
      }

      private void updateFormat() {
         int var1 = Math.min(this.maxWidth, this.size == 0 ? this.minWidth : this.size);
         this.format = "%" + (this.align == PrettyPrinter.Alignment.RIGHT ? "" : "-") + var1 + "s";
         this.table.updateFormat();
      }

      int getMaxWidth() {
         return this.maxWidth;
      }

      String getTitle() {
         return this.title;
      }

      String getFormat() {
         return this.format;
      }

      public String toString() {
         return this.title.length() > this.maxWidth ? this.title.substring(0, this.maxWidth) : this.title;
      }
   }

   static class Table implements PrettyPrinter.IVariableWidthEntry {
      final List columns = new ArrayList();
      final List rows = new ArrayList();
      String format = "%s";
      int colSpacing = 2;
      boolean addHeader = true;

      void headerAdded() {
         this.addHeader = false;
      }

      void setColSpacing(int var1) {
         this.colSpacing = Math.max(0, var1);
         this.updateFormat();
      }

      PrettyPrinter.Table grow(int var1) {
         while(this.columns.size() < var1) {
            this.columns.add(new PrettyPrinter.Column(this));
         }

         this.updateFormat();
         return this;
      }

      PrettyPrinter.Column add(PrettyPrinter.Column var1) {
         this.columns.add(var1);
         return var1;
      }

      PrettyPrinter.Row add(PrettyPrinter.Row var1) {
         this.rows.add(var1);
         return var1;
      }

      PrettyPrinter.Column addColumn(String var1) {
         return this.add(new PrettyPrinter.Column(this, var1));
      }

      PrettyPrinter.Column addColumn(PrettyPrinter.Alignment var1, int var2, String var3) {
         return this.add(new PrettyPrinter.Column(this, var1, var2, var3));
      }

      PrettyPrinter.Row addRow(Object... var1) {
         return this.add(new PrettyPrinter.Row(this, var1));
      }

      void updateFormat() {
         String var1 = Strings.repeat(" ", this.colSpacing);
         StringBuilder var2 = new StringBuilder();
         boolean var3 = false;
         Iterator var4 = this.columns.iterator();

         while(var4.hasNext()) {
            PrettyPrinter.Column var5 = (PrettyPrinter.Column)var4.next();
            if (var3) {
               var2.append(var1);
            }

            var3 = true;
            var2.append(var5.getFormat());
         }

         this.format = var2.toString();
      }

      String getFormat() {
         return this.format;
      }

      Object[] getTitles() {
         ArrayList var1 = new ArrayList();
         Iterator var2 = this.columns.iterator();

         while(var2.hasNext()) {
            PrettyPrinter.Column var3 = (PrettyPrinter.Column)var2.next();
            var1.add(var3.getTitle());
         }

         return var1.toArray();
      }

      public String toString() {
         boolean var1 = false;
         String[] var2 = new String[this.columns.size()];

         for(int var3 = 0; var3 < this.columns.size(); ++var3) {
            var2[var3] = ((PrettyPrinter.Column)this.columns.get(var3)).toString();
            var1 |= !var2[var3].isEmpty();
         }

         return var1 ? String.format(this.format, (Object[])var2) : null;
      }

      public int getWidth() {
         String var1 = this.toString();
         return var1 != null ? var1.length() : 0;
      }
   }

   public static enum Alignment {
      LEFT,
      RIGHT;

      private static final PrettyPrinter.Alignment[] $VALUES = new PrettyPrinter.Alignment[]{LEFT, RIGHT};
   }

   class CentredText {
      private final Object centred;
      final PrettyPrinter this$0;

      public CentredText(PrettyPrinter var1, Object var2) {
         this.this$0 = var1;
         this.centred = var2;
      }

      public String toString() {
         String var1 = this.centred.toString();
         return String.format("%" + ((this.this$0.width - var1.length()) / 2 + var1.length()) + "s", var1);
      }
   }

   class HorizontalRule implements PrettyPrinter.ISpecialEntry {
      private final char[] hrChars;
      final PrettyPrinter this$0;

      public HorizontalRule(PrettyPrinter var1, char... var2) {
         this.this$0 = var1;
         this.hrChars = var2;
      }

      public String toString() {
         return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
      }
   }

   class KeyValue implements PrettyPrinter.IVariableWidthEntry {
      private final String key;
      private final Object value;
      final PrettyPrinter this$0;

      public KeyValue(PrettyPrinter var1, String var2, Object var3) {
         this.this$0 = var1;
         this.key = var2;
         this.value = var3;
      }

      public String toString() {
         return String.format(this.this$0.kvFormat, this.key, this.value);
      }

      public int getWidth() {
         return this.toString().length();
      }
   }

   interface ISpecialEntry {
   }

   interface IVariableWidthEntry {
      int getWidth();
   }

   public interface IPrettyPrintable {
      void print(PrettyPrinter var1);
   }
}
