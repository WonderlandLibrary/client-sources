/*     */ package org.spongepowered.asm.util.perf;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.TreeMap;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Profiler
/*     */ {
/*     */   public static final int ROOT = 1;
/*     */   public static final int FINE = 2;
/*     */   
/*     */   public class Section
/*     */   {
/*     */     static final String SEPARATOR_ROOT = " -> ";
/*     */     static final String SEPARATOR_CHILD = ".";
/*     */     private final String name;
/*     */     private boolean root;
/*     */     private boolean fine;
/*     */     protected boolean invalidated;
/*     */     private String info;
/*     */     
/*     */     Section(String name) {
/*  89 */       this.name = name;
/*  90 */       this.info = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section getDelegate() {
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     Section invalidate() {
/* 101 */       this.invalidated = true;
/* 102 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setRoot(boolean root) {
/* 111 */       this.root = root;
/* 112 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRoot() {
/* 119 */       return this.root;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setFine(boolean fine) {
/* 128 */       this.fine = fine;
/* 129 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFine() {
/* 136 */       return this.fine;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 143 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getBaseName() {
/* 151 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInfo(String info) {
/* 160 */       this.info = info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getInfo() {
/* 167 */       return this.info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section start() {
/* 176 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Section stop() {
/* 185 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section end() {
/* 194 */       if (!this.invalidated) {
/* 195 */         Profiler.this.end(this);
/*     */       }
/* 197 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section next(String name) {
/* 207 */       end();
/* 208 */       return Profiler.this.begin(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void mark() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 224 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getTotalTime() {
/* 231 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getSeconds() {
/* 238 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getTotalSeconds() {
/* 245 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long[] getTimes() {
/* 253 */       return new long[1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 260 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTotalCount() {
/* 267 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getAverageTime() {
/* 275 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getTotalAverageTime() {
/* 283 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 291 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LiveSection
/*     */     extends Section
/*     */   {
/* 306 */     private int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     private long[] times = new long[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     private long start = 0L;
/*     */ 
/*     */     
/*     */     private long time;
/*     */     
/*     */     private long markedTime;
/*     */     
/*     */     private int count;
/*     */     
/*     */     private int markedCount;
/*     */ 
/*     */     
/*     */     LiveSection(String name, int cursor) {
/* 329 */       super(name);
/* 330 */       this.cursor = cursor;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 335 */       this.start = System.currentTimeMillis();
/* 336 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Profiler.Section stop() {
/* 341 */       if (this.start > 0L) {
/* 342 */         this.time += System.currentTimeMillis() - this.start;
/*     */       }
/* 344 */       this.start = 0L;
/* 345 */       this.count++;
/* 346 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 351 */       stop();
/* 352 */       if (!this.invalidated) {
/* 353 */         Profiler.this.end(this);
/*     */       }
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     void mark() {
/* 360 */       if (this.cursor >= this.times.length) {
/* 361 */         this.times = Arrays.copyOf(this.times, this.cursor + 4);
/*     */       }
/* 363 */       this.times[this.cursor] = this.time;
/* 364 */       this.markedTime += this.time;
/* 365 */       this.markedCount += this.count;
/* 366 */       this.time = 0L;
/* 367 */       this.count = 0;
/* 368 */       this.cursor++;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTime() {
/* 373 */       return this.time;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTotalTime() {
/* 378 */       return this.time + this.markedTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getSeconds() {
/* 383 */       return this.time * 0.001D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getTotalSeconds() {
/* 388 */       return (this.time + this.markedTime) * 0.001D;
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] getTimes() {
/* 393 */       long[] times = new long[this.cursor + 1];
/* 394 */       System.arraycopy(this.times, 0, times, 0, Math.min(this.times.length, this.cursor));
/* 395 */       times[this.cursor] = this.time;
/* 396 */       return times;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 401 */       return this.count;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTotalCount() {
/* 406 */       return this.count + this.markedCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getAverageTime() {
/* 411 */       return (this.count > 0) ? (this.time / this.count) : 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getTotalAverageTime() {
/* 416 */       return (this.count > 0) ? ((this.time + this.markedTime) / (this.count + this.markedCount)) : 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class SubSection
/*     */     extends LiveSection
/*     */   {
/*     */     private final String baseName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Profiler.Section root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SubSection(String name, int cursor, String baseName, Profiler.Section root) {
/* 439 */       super(name, cursor);
/* 440 */       this.baseName = baseName;
/* 441 */       this.root = root;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section invalidate() {
/* 446 */       this.root.invalidate();
/* 447 */       return super.invalidate();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getBaseName() {
/* 452 */       return this.baseName;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setInfo(String info) {
/* 457 */       this.root.setInfo(info);
/* 458 */       super.setInfo(info);
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section getDelegate() {
/* 463 */       return this.root;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 468 */       this.root.start();
/* 469 */       return super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 474 */       this.root.stop();
/* 475 */       return super.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section next(String name) {
/* 480 */       stop();
/* 481 */       return this.root.next(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 489 */   private final Map<String, Section> sections = new TreeMap<String, Section>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 494 */   private final List<String> phases = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 499 */   private final Deque<Section> stack = new LinkedList<Section>();
/*     */ 
/*     */   
/*     */   private boolean active;
/*     */ 
/*     */ 
/*     */   
/*     */   public Profiler() {
/* 507 */     this.phases.add("Initial");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 517 */     if ((!this.active && active) || !active) {
/* 518 */       reset();
/*     */     }
/* 520 */     this.active = active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 527 */     for (Section section : this.sections.values()) {
/* 528 */       section.invalidate();
/*     */     }
/*     */     
/* 531 */     this.sections.clear();
/* 532 */     this.phases.clear();
/* 533 */     this.phases.add("Initial");
/* 534 */     this.stack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section get(String name) {
/* 544 */     Section section = this.sections.get(name);
/* 545 */     if (section == null) {
/* 546 */       section = this.active ? new LiveSection(name, this.phases.size() - 1) : new Section(name);
/* 547 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 550 */     return section;
/*     */   }
/*     */   
/*     */   private Section getSubSection(String name, String baseName, Section root) {
/* 554 */     Section section = this.sections.get(name);
/* 555 */     if (section == null) {
/* 556 */       section = new SubSection(name, this.phases.size() - 1, baseName, root);
/* 557 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 560 */     return section;
/*     */   }
/*     */   
/*     */   boolean isHead(Section section) {
/* 564 */     return (this.stack.peek() == section);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(String... path) {
/* 574 */     return begin(0, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(int flags, String... path) {
/* 585 */     return begin(flags, Joiner.on('.').join((Object[])path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(String name) {
/* 595 */     return begin(0, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(int flags, String name) {
/* 606 */     boolean root = ((flags & 0x1) != 0);
/* 607 */     boolean fine = ((flags & 0x2) != 0);
/*     */     
/* 609 */     String path = name;
/* 610 */     Section head = this.stack.peek();
/* 611 */     if (head != null) {
/* 612 */       path = head.getName() + (root ? " -> " : ".") + path;
/* 613 */       if (head.isRoot() && !root) {
/* 614 */         int pos = head.getName().lastIndexOf(" -> ");
/* 615 */         name = ((pos > -1) ? head.getName().substring(pos + 4) : head.getName()) + "." + name;
/* 616 */         root = true;
/*     */       } 
/*     */     } 
/*     */     
/* 620 */     Section section = get(root ? name : path);
/* 621 */     if (root && head != null && this.active) {
/* 622 */       section = getSubSection(path, head.getName(), section);
/*     */     }
/*     */     
/* 625 */     section.setFine(fine).setRoot(root);
/* 626 */     this.stack.push(section);
/*     */     
/* 628 */     return section.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void end(Section section) {
/*     */     try {
/* 639 */       for (Section head = this.stack.pop(), next = head; next != section; next = this.stack.pop()) {
/* 640 */         if (next == null && this.active) {
/* 641 */           if (head == null) {
/* 642 */             throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */           }
/* 644 */           throw new IllegalStateException("Attempted to pop " + section + " which was not in the stack, head was " + head);
/*     */         } 
/*     */       } 
/* 647 */     } catch (NoSuchElementException ex) {
/* 648 */       if (this.active) {
/* 649 */         throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(String phase) {
/* 662 */     long currentPhaseTime = 0L;
/* 663 */     for (Section section : this.sections.values()) {
/* 664 */       currentPhaseTime += section.getTime();
/*     */     }
/*     */ 
/*     */     
/* 668 */     if (currentPhaseTime == 0L) {
/* 669 */       int size = this.phases.size();
/* 670 */       this.phases.set(size - 1, phase);
/*     */       
/*     */       return;
/*     */     } 
/* 674 */     this.phases.add(phase);
/* 675 */     for (Section section : this.sections.values()) {
/* 676 */       section.mark();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Section> getSections() {
/* 684 */     return Collections.unmodifiableCollection(this.sections.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrettyPrinter printer(boolean includeFine, boolean group) {
/* 696 */     PrettyPrinter printer = new PrettyPrinter();
/*     */ 
/*     */     
/* 699 */     int colCount = this.phases.size() + 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 704 */     int[] columns = { 0, 1, 2, colCount - 2, colCount - 1 };
/*     */     
/* 706 */     Object[] headers = new Object[colCount * 2]; int pos;
/* 707 */     for (int col = 0; col < colCount; pos = ++col * 2) {
/* 708 */       headers[pos + 1] = PrettyPrinter.Alignment.RIGHT;
/* 709 */       if (col == columns[0]) {
/* 710 */         headers[pos] = (group ? "" : "  ") + "Section";
/* 711 */         headers[pos + 1] = PrettyPrinter.Alignment.LEFT;
/* 712 */       } else if (col == columns[1]) {
/* 713 */         headers[pos] = "    TOTAL";
/* 714 */       } else if (col == columns[3]) {
/* 715 */         headers[pos] = "    Count";
/* 716 */       } else if (col == columns[4]) {
/* 717 */         headers[pos] = "Avg. ";
/* 718 */       } else if (col - columns[2] < this.phases.size()) {
/* 719 */         headers[pos] = this.phases.get(col - columns[2]);
/*     */       } else {
/* 721 */         headers[pos] = "";
/*     */       } 
/*     */     } 
/*     */     
/* 725 */     printer.table(headers).th().hr().add();
/*     */     
/* 727 */     for (Section section : this.sections.values()) {
/* 728 */       if ((section.isFine() && !includeFine) || (group && section.getDelegate() != section)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 733 */       printSectionRow(printer, colCount, columns, section, group);
/*     */ 
/*     */       
/* 736 */       if (group) {
/* 737 */         for (Section subSection : this.sections.values()) {
/* 738 */           Section delegate = subSection.getDelegate();
/* 739 */           if ((subSection.isFine() && !includeFine) || delegate != section || delegate == subSection) {
/*     */             continue;
/*     */           }
/*     */           
/* 743 */           printSectionRow(printer, colCount, columns, subSection, group);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 748 */     return printer.add();
/*     */   }
/*     */   
/*     */   private void printSectionRow(PrettyPrinter printer, int colCount, int[] columns, Section section, boolean group) {
/* 752 */     boolean isDelegate = (section.getDelegate() != section);
/* 753 */     Object[] values = new Object[colCount];
/* 754 */     int col = 1;
/* 755 */     if (group) {
/* 756 */       values[0] = isDelegate ? ("  > " + section.getBaseName()) : section.getName();
/*     */     } else {
/* 758 */       values[0] = (isDelegate ? "+ " : "  ") + section.getName();
/*     */     } 
/*     */     
/* 761 */     long[] times = section.getTimes();
/* 762 */     for (long time : times) {
/* 763 */       if (col == columns[1]) {
/* 764 */         values[col++] = section.getTotalTime() + " ms";
/*     */       }
/* 766 */       if (col >= columns[2] && col < values.length) {
/* 767 */         values[col++] = time + " ms";
/*     */       }
/*     */     } 
/*     */     
/* 771 */     values[columns[3]] = Integer.valueOf(section.getTotalCount());
/* 772 */     values[columns[4]] = (new DecimalFormat("   ###0.000 ms")).format(section.getTotalAverageTime());
/*     */     
/* 774 */     for (int i = 0; i < values.length; i++) {
/* 775 */       if (values[i] == null) {
/* 776 */         values[i] = "-";
/*     */       }
/*     */     } 
/*     */     
/* 780 */     printer.tr(values);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\perf\Profiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */