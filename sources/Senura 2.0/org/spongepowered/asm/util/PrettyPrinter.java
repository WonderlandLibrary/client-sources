/*      */ package org.spongepowered.asm.util;
/*      */ 
/*      */ import com.google.common.base.Strings;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PrettyPrinter
/*      */ {
/*      */   public static interface IPrettyPrintable
/*      */   {
/*      */     void print(PrettyPrinter param1PrettyPrinter);
/*      */   }
/*      */   
/*      */   static interface IVariableWidthEntry
/*      */   {
/*      */     int getWidth();
/*      */   }
/*      */   
/*      */   static interface ISpecialEntry {}
/*      */   
/*      */   class KeyValue
/*      */     implements IVariableWidthEntry
/*      */   {
/*      */     private final String key;
/*      */     private final Object value;
/*      */     
/*      */     public KeyValue(String key, Object value) {
/*   86 */       this.key = key;
/*   87 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   92 */       return String.format(PrettyPrinter.this.kvFormat, new Object[] { this.key, this.value });
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*   97 */       return toString().length();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class HorizontalRule
/*      */     implements ISpecialEntry
/*      */   {
/*      */     private final char[] hrChars;
/*      */ 
/*      */     
/*      */     public HorizontalRule(char... hrChars) {
/*  110 */       this.hrChars = hrChars;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  115 */       return Strings.repeat(new String(this.hrChars), PrettyPrinter.this.width + 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class CentredText
/*      */   {
/*      */     private final Object centred;
/*      */ 
/*      */ 
/*      */     
/*      */     public CentredText(Object centred) {
/*  128 */       this.centred = centred;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  133 */       String text = this.centred.toString();
/*  134 */       return String.format("%" + ((PrettyPrinter.this.width - text.length()) / 2 + text.length()) + "s", new Object[] { text });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Alignment
/*      */   {
/*  143 */     LEFT,
/*  144 */     RIGHT;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Table
/*      */     implements IVariableWidthEntry
/*      */   {
/*  152 */     final List<PrettyPrinter.Column> columns = new ArrayList<PrettyPrinter.Column>();
/*      */     
/*  154 */     final List<PrettyPrinter.Row> rows = new ArrayList<PrettyPrinter.Row>();
/*      */     
/*  156 */     String format = "%s";
/*      */     
/*  158 */     int colSpacing = 2;
/*      */     
/*      */     boolean addHeader = true;
/*      */     
/*      */     void headerAdded() {
/*  163 */       this.addHeader = false;
/*      */     }
/*      */     
/*      */     void setColSpacing(int spacing) {
/*  167 */       this.colSpacing = Math.max(0, spacing);
/*  168 */       updateFormat();
/*      */     }
/*      */     
/*      */     Table grow(int size) {
/*  172 */       while (this.columns.size() < size) {
/*  173 */         this.columns.add(new PrettyPrinter.Column(this));
/*      */       }
/*  175 */       updateFormat();
/*  176 */       return this;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column add(PrettyPrinter.Column column) {
/*  180 */       this.columns.add(column);
/*  181 */       return column;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Row add(PrettyPrinter.Row row) {
/*  185 */       this.rows.add(row);
/*  186 */       return row;
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column addColumn(String title) {
/*  190 */       return add(new PrettyPrinter.Column(this, title));
/*      */     }
/*      */     
/*      */     PrettyPrinter.Column addColumn(PrettyPrinter.Alignment align, int size, String title) {
/*  194 */       return add(new PrettyPrinter.Column(this, align, size, title));
/*      */     }
/*      */     
/*      */     PrettyPrinter.Row addRow(Object... args) {
/*  198 */       return add(new PrettyPrinter.Row(this, args));
/*      */     }
/*      */     
/*      */     void updateFormat() {
/*  202 */       String spacing = Strings.repeat(" ", this.colSpacing);
/*  203 */       StringBuilder format = new StringBuilder();
/*  204 */       boolean addSpacing = false;
/*  205 */       for (PrettyPrinter.Column column : this.columns) {
/*  206 */         if (addSpacing) {
/*  207 */           format.append(spacing);
/*      */         }
/*  209 */         addSpacing = true;
/*  210 */         format.append(column.getFormat());
/*      */       } 
/*  212 */       this.format = format.toString();
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  216 */       return this.format;
/*      */     }
/*      */     
/*      */     Object[] getTitles() {
/*  220 */       List<Object> titles = new ArrayList();
/*  221 */       for (PrettyPrinter.Column column : this.columns) {
/*  222 */         titles.add(column.getTitle());
/*      */       }
/*  224 */       return titles.toArray();
/*      */     }
/*      */     
/*      */     public String toString() {
/*      */       int i;
/*  229 */       boolean nonEmpty = false;
/*  230 */       String[] titles = new String[this.columns.size()];
/*  231 */       for (int col = 0; col < this.columns.size(); col++) {
/*  232 */         titles[col] = ((PrettyPrinter.Column)this.columns.get(col)).toString();
/*  233 */         i = nonEmpty | (!titles[col].isEmpty() ? 1 : 0);
/*      */       } 
/*  235 */       return (i != 0) ? String.format(this.format, (Object[])titles) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*  240 */       String str = toString();
/*  241 */       return (str != null) ? str.length() : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Column
/*      */   {
/*      */     private final PrettyPrinter.Table table;
/*      */ 
/*      */     
/*  253 */     private PrettyPrinter.Alignment align = PrettyPrinter.Alignment.LEFT;
/*      */     
/*  255 */     private int minWidth = 1;
/*      */     
/*  257 */     private int maxWidth = Integer.MAX_VALUE;
/*      */     
/*  259 */     private int size = 0;
/*      */     
/*  261 */     private String title = "";
/*      */     
/*  263 */     private String format = "%s";
/*      */     
/*      */     Column(PrettyPrinter.Table table) {
/*  266 */       this.table = table;
/*      */     }
/*      */     
/*      */     Column(PrettyPrinter.Table table, String title) {
/*  270 */       this(table);
/*  271 */       this.title = title;
/*  272 */       this.minWidth = title.length();
/*  273 */       updateFormat();
/*      */     }
/*      */     
/*      */     Column(PrettyPrinter.Table table, PrettyPrinter.Alignment align, int size, String title) {
/*  277 */       this(table, title);
/*  278 */       this.align = align;
/*  279 */       this.size = size;
/*      */     }
/*      */     
/*      */     void setAlignment(PrettyPrinter.Alignment align) {
/*  283 */       this.align = align;
/*  284 */       updateFormat();
/*      */     }
/*      */     
/*      */     void setWidth(int width) {
/*  288 */       if (width > this.size) {
/*  289 */         this.size = width;
/*  290 */         updateFormat();
/*      */       } 
/*      */     }
/*      */     
/*      */     void setMinWidth(int width) {
/*  295 */       if (width > this.minWidth) {
/*  296 */         this.minWidth = width;
/*  297 */         updateFormat();
/*      */       } 
/*      */     }
/*      */     
/*      */     void setMaxWidth(int width) {
/*  302 */       this.size = Math.min(this.size, this.maxWidth);
/*  303 */       this.maxWidth = Math.max(1, width);
/*  304 */       updateFormat();
/*      */     }
/*      */     
/*      */     void setTitle(String title) {
/*  308 */       this.title = title;
/*  309 */       setWidth(title.length());
/*      */     }
/*      */     
/*      */     private void updateFormat() {
/*  313 */       int width = Math.min(this.maxWidth, (this.size == 0) ? this.minWidth : this.size);
/*  314 */       this.format = "%" + ((this.align == PrettyPrinter.Alignment.RIGHT) ? "" : "-") + width + "s";
/*  315 */       this.table.updateFormat();
/*      */     }
/*      */     
/*      */     int getMaxWidth() {
/*  319 */       return this.maxWidth;
/*      */     }
/*      */     
/*      */     String getTitle() {
/*  323 */       return this.title;
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  327 */       return this.format;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  332 */       if (this.title.length() > this.maxWidth) {
/*  333 */         return this.title.substring(0, this.maxWidth);
/*      */       }
/*      */       
/*  336 */       return this.title;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Row
/*      */     implements IVariableWidthEntry
/*      */   {
/*      */     final PrettyPrinter.Table table;
/*      */     
/*      */     final String[] args;
/*      */ 
/*      */     
/*      */     public Row(PrettyPrinter.Table table, Object... args) {
/*  351 */       this.table = table.grow(args.length);
/*  352 */       this.args = new String[args.length];
/*  353 */       for (int i = 0; i < args.length; i++) {
/*  354 */         this.args[i] = args[i].toString();
/*  355 */         ((PrettyPrinter.Column)this.table.columns.get(i)).setMinWidth(this.args[i].length());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  361 */       Object[] args = new Object[this.table.columns.size()];
/*  362 */       for (int col = 0; col < args.length; col++) {
/*  363 */         PrettyPrinter.Column column = this.table.columns.get(col);
/*  364 */         if (col >= this.args.length) {
/*  365 */           args[col] = "";
/*      */         } else {
/*  367 */           args[col] = (this.args[col].length() > column.getMaxWidth()) ? this.args[col].substring(0, column.getMaxWidth()) : this.args[col];
/*      */         } 
/*      */       } 
/*      */       
/*  371 */       return String.format(this.table.format, args);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getWidth() {
/*  376 */       return toString().length();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  384 */   private final HorizontalRule horizontalRule = new HorizontalRule(new char[] { '*' });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  389 */   private final List<Object> lines = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Table table;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean recalcWidth = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  405 */   protected int width = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  410 */   protected int wrapWidth = 80;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  415 */   protected int kvKeyWidth = 10;
/*      */   
/*  417 */   protected String kvFormat = makeKvFormat(this.kvKeyWidth);
/*      */   
/*      */   public PrettyPrinter() {
/*  420 */     this(100);
/*      */   }
/*      */   
/*      */   public PrettyPrinter(int width) {
/*  424 */     this.width = width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter wrapTo(int wrapWidth) {
/*  434 */     this.wrapWidth = wrapWidth;
/*  435 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int wrapTo() {
/*  444 */     return this.wrapWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table() {
/*  453 */     this.table = new Table();
/*  454 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table(String... titles) {
/*  464 */     this.table = new Table();
/*  465 */     for (String title : titles) {
/*  466 */       this.table.addColumn(title);
/*      */     }
/*  468 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter table(Object... format) {
/*  493 */     this.table = new Table();
/*  494 */     Column column = null;
/*  495 */     for (Object entry : format) {
/*  496 */       if (entry instanceof String) {
/*  497 */         column = this.table.addColumn((String)entry);
/*  498 */       } else if (entry instanceof Integer && column != null) {
/*  499 */         int width = ((Integer)entry).intValue();
/*  500 */         if (width > 0) {
/*  501 */           column.setWidth(width);
/*  502 */         } else if (width < 0) {
/*  503 */           column.setMaxWidth(-width);
/*      */         } 
/*  505 */       } else if (entry instanceof Alignment && column != null) {
/*  506 */         column.setAlignment((Alignment)entry);
/*  507 */       } else if (entry != null) {
/*  508 */         column = this.table.addColumn(entry.toString());
/*      */       } 
/*      */     } 
/*  511 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter spacing(int spacing) {
/*  521 */     if (this.table == null) {
/*  522 */       this.table = new Table();
/*      */     }
/*  524 */     this.table.setColSpacing(spacing);
/*  525 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter th() {
/*  535 */     return th(false);
/*      */   }
/*      */   
/*      */   private PrettyPrinter th(boolean onlyIfNeeded) {
/*  539 */     if (this.table == null) {
/*  540 */       this.table = new Table();
/*      */     }
/*  542 */     if (!onlyIfNeeded || this.table.addHeader) {
/*  543 */       this.table.headerAdded();
/*  544 */       addLine(this.table);
/*      */     } 
/*  546 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter tr(Object... args) {
/*  558 */     th(true);
/*  559 */     addLine(this.table.addRow(args));
/*  560 */     this.recalcWidth = true;
/*  561 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add() {
/*  570 */     addLine("");
/*  571 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(String string) {
/*  581 */     addLine(string);
/*  582 */     this.width = Math.max(this.width, string.length());
/*  583 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(String format, Object... args) {
/*  595 */     String line = String.format(format, args);
/*  596 */     addLine(line);
/*  597 */     this.width = Math.max(this.width, line.length());
/*  598 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object[] array) {
/*  608 */     return add(array, "%s");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object[] array, String format) {
/*  619 */     for (Object element : array) {
/*  620 */       add(format, new Object[] { element });
/*      */     } 
/*      */     
/*  623 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addIndexed(Object[] array) {
/*  633 */     int indexWidth = String.valueOf(array.length - 1).length();
/*  634 */     String format = "[%" + indexWidth + "d] %s";
/*  635 */     for (int index = 0; index < array.length; index++) {
/*  636 */       add(format, new Object[] { Integer.valueOf(index), array[index] });
/*      */     } 
/*      */     
/*  639 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWithIndices(Collection<?> c) {
/*  649 */     return addIndexed(c.toArray());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(IPrettyPrintable printable) {
/*  660 */     if (printable != null) {
/*  661 */       printable.print(this);
/*      */     }
/*  663 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Throwable th) {
/*  674 */     return add(th, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Throwable th, int indent) {
/*  686 */     while (th != null) {
/*  687 */       add("%s: %s", new Object[] { th.getClass().getName(), th.getMessage() });
/*  688 */       add(th.getStackTrace(), indent);
/*  689 */       th = th.getCause();
/*      */     } 
/*  691 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(StackTraceElement[] stackTrace, int indent) {
/*  703 */     String margin = Strings.repeat(" ", indent);
/*  704 */     for (StackTraceElement st : stackTrace) {
/*  705 */       add("%s%s", new Object[] { margin, st });
/*      */     } 
/*  707 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object object) {
/*  717 */     return add(object, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Object object, int indent) {
/*  728 */     String margin = Strings.repeat(" ", indent);
/*  729 */     return append(object, indent, margin);
/*      */   }
/*      */   
/*      */   private PrettyPrinter append(Object object, int indent, String margin) {
/*  733 */     if (object instanceof String)
/*  734 */       return add("%s%s", new Object[] { margin, object }); 
/*  735 */     if (object instanceof Iterable) {
/*  736 */       for (Object entry : object) {
/*  737 */         append(entry, indent, margin);
/*      */       }
/*  739 */       return this;
/*  740 */     }  if (object instanceof Map) {
/*  741 */       kvWidth(indent);
/*  742 */       return add((Map<?, ?>)object);
/*  743 */     }  if (object instanceof IPrettyPrintable)
/*  744 */       return add((IPrettyPrintable)object); 
/*  745 */     if (object instanceof Throwable)
/*  746 */       return add((Throwable)object, indent); 
/*  747 */     if (object.getClass().isArray()) {
/*  748 */       return add((Object[])object, indent + "%s");
/*      */     }
/*  750 */     return add("%s%s", new Object[] { margin, object });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWrapped(String format, Object... args) {
/*  763 */     return addWrapped(this.wrapWidth, format, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter addWrapped(int width, String format, Object... args) {
/*  777 */     String indent = "";
/*  778 */     String line = String.format(format, args).replace("\t", "    ");
/*  779 */     Matcher indentMatcher = Pattern.compile("^(\\s+)(.*)$").matcher(line);
/*  780 */     if (indentMatcher.matches()) {
/*  781 */       indent = indentMatcher.group(1);
/*      */     }
/*      */     
/*      */     try {
/*  785 */       for (String wrappedLine : getWrapped(width, line, indent)) {
/*  786 */         addLine(wrappedLine);
/*      */       }
/*  788 */     } catch (Exception ex) {
/*  789 */       add(line);
/*      */     } 
/*  791 */     return this;
/*      */   }
/*      */   
/*      */   private List<String> getWrapped(int width, String line, String indent) {
/*  795 */     List<String> lines = new ArrayList<String>();
/*      */     
/*  797 */     while (line.length() > width) {
/*  798 */       int wrapPoint = line.lastIndexOf(' ', width);
/*  799 */       if (wrapPoint < 10) {
/*  800 */         wrapPoint = width;
/*      */       }
/*  802 */       String head = line.substring(0, wrapPoint);
/*  803 */       lines.add(head);
/*  804 */       line = indent + line.substring(wrapPoint + 1);
/*      */     } 
/*      */     
/*  807 */     if (line.length() > 0) {
/*  808 */       lines.add(line);
/*      */     }
/*      */     
/*  811 */     return lines;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kv(String key, String format, Object... args) {
/*  823 */     return kv(key, String.format(format, args));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kv(String key, Object value) {
/*  834 */     addLine(new KeyValue(key, value));
/*  835 */     return kvWidth(key.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter kvWidth(int width) {
/*  845 */     if (width > this.kvKeyWidth) {
/*  846 */       this.kvKeyWidth = width;
/*  847 */       this.kvFormat = makeKvFormat(width);
/*      */     } 
/*  849 */     this.recalcWidth = true;
/*  850 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter add(Map<?, ?> map) {
/*  860 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/*  861 */       String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();
/*  862 */       kv(key, entry.getValue());
/*      */     } 
/*  864 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter hr() {
/*  873 */     return hr('*');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter hr(char ruleChar) {
/*  884 */     addLine(new HorizontalRule(new char[] { ruleChar }));
/*  885 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter centre() {
/*  894 */     if (!this.lines.isEmpty()) {
/*  895 */       Object lastLine = this.lines.get(this.lines.size() - 1);
/*  896 */       if (lastLine instanceof String) {
/*  897 */         addLine(new CentredText(this.lines.remove(this.lines.size() - 1)));
/*      */       }
/*      */     } 
/*  900 */     return this;
/*      */   }
/*      */   
/*      */   private void addLine(Object line) {
/*  904 */     if (line == null) {
/*      */       return;
/*      */     }
/*  907 */     this.lines.add(line);
/*  908 */     this.recalcWidth |= line instanceof IVariableWidthEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace() {
/*  918 */     return trace(getDefaultLoggerName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Level level) {
/*  929 */     return trace(getDefaultLoggerName(), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(String logger) {
/*  940 */     return trace(System.err, LogManager.getLogger(logger));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(String logger, Level level) {
/*  952 */     return trace(System.err, LogManager.getLogger(logger), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Logger logger) {
/*  963 */     return trace(System.err, logger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(Logger logger, Level level) {
/*  975 */     return trace(System.err, logger, level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream) {
/*  986 */     return trace(stream, getDefaultLoggerName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Level level) {
/*  998 */     return trace(stream, getDefaultLoggerName(), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, String logger) {
/* 1010 */     return trace(stream, LogManager.getLogger(logger));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, String logger, Level level) {
/* 1023 */     return trace(stream, LogManager.getLogger(logger), level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Logger logger) {
/* 1035 */     return trace(stream, logger, Level.DEBUG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter trace(PrintStream stream, Logger logger, Level level) {
/* 1048 */     log(logger, level);
/* 1049 */     print(stream);
/* 1050 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter print() {
/* 1059 */     return print(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter print(PrintStream stream) {
/* 1069 */     updateWidth();
/* 1070 */     printSpecial(stream, this.horizontalRule);
/* 1071 */     for (Object line : this.lines) {
/* 1072 */       if (line instanceof ISpecialEntry) {
/* 1073 */         printSpecial(stream, (ISpecialEntry)line); continue;
/*      */       } 
/* 1075 */       printString(stream, line.toString());
/*      */     } 
/*      */     
/* 1078 */     printSpecial(stream, this.horizontalRule);
/* 1079 */     return this;
/*      */   }
/*      */   
/*      */   private void printSpecial(PrintStream stream, ISpecialEntry line) {
/* 1083 */     stream.printf("/*%s*/\n", new Object[] { line.toString() });
/*      */   }
/*      */   
/*      */   private void printString(PrintStream stream, String string) {
/* 1087 */     if (string != null) {
/* 1088 */       stream.printf("/* %-" + this.width + "s */\n", new Object[] { string });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter log(Logger logger) {
/* 1099 */     return log(logger, Level.INFO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrettyPrinter log(Logger logger, Level level) {
/* 1110 */     updateWidth();
/* 1111 */     logSpecial(logger, level, this.horizontalRule);
/* 1112 */     for (Object line : this.lines) {
/* 1113 */       if (line instanceof ISpecialEntry) {
/* 1114 */         logSpecial(logger, level, (ISpecialEntry)line); continue;
/*      */       } 
/* 1116 */       logString(logger, level, line.toString());
/*      */     } 
/*      */     
/* 1119 */     logSpecial(logger, level, this.horizontalRule);
/* 1120 */     return this;
/*      */   }
/*      */   
/*      */   private void logSpecial(Logger logger, Level level, ISpecialEntry line) {
/* 1124 */     logger.log(level, "/*{}*/", new Object[] { line.toString() });
/*      */   }
/*      */   
/*      */   private void logString(Logger logger, Level level, String line) {
/* 1128 */     if (line != null) {
/* 1129 */       logger.log(level, String.format("/* %-" + this.width + "s */", new Object[] { line }));
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateWidth() {
/* 1134 */     if (this.recalcWidth) {
/* 1135 */       this.recalcWidth = false;
/* 1136 */       for (Object line : this.lines) {
/* 1137 */         if (line instanceof IVariableWidthEntry) {
/* 1138 */           this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry)line).getWidth()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String makeKvFormat(int keyWidth) {
/* 1145 */     return String.format("%%%ds : %%s", new Object[] { Integer.valueOf(keyWidth) });
/*      */   }
/*      */   
/*      */   private static String getDefaultLoggerName() {
/* 1149 */     String name = (new Throwable()).getStackTrace()[2].getClassName();
/* 1150 */     int pos = name.lastIndexOf('.');
/* 1151 */     return (pos == -1) ? name : name.substring(pos + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpStack() {
/* 1159 */     (new PrettyPrinter()).add(new Exception("Stack trace")).print(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void print(Throwable th) {
/* 1168 */     (new PrettyPrinter()).add(th).print(System.err);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\PrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */