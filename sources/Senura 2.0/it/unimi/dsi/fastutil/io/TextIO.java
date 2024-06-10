/*      */ package it.unimi.dsi.fastutil.io;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterable;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterable;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterable;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterable;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterable;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterable;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterable;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class TextIO
/*      */ {
/*      */   public static final int BUFFER_SIZE = 8192;
/*      */   
/*      */   public static int loadInts(BufferedReader reader, int[] array, int offset, int length) throws IOException {
/*  107 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  108 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  111 */       for (i = 0; i < length && (
/*  112 */         s = reader.readLine()) != null; i++) array[i + offset] = Integer.parseInt(s.trim());
/*      */     
/*      */     }
/*  115 */     catch (EOFException eOFException) {}
/*  116 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(BufferedReader reader, int[] array) throws IOException {
/*  125 */     return loadInts(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array, int offset, int length) throws IOException {
/*  136 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  137 */     int result = loadInts(reader, array, offset, length);
/*  138 */     reader.close();
/*  139 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array, int offset, int length) throws IOException {
/*  150 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array) throws IOException {
/*  159 */     return loadInts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array) throws IOException {
/*  168 */     return loadInts(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, PrintStream stream) {
/*  178 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  179 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, PrintStream stream) {
/*  187 */     storeInts(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
/*  197 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  198 */     storeInts(array, offset, length, stream);
/*  199 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
/*  209 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, File file) throws IOException {
/*  217 */     storeInts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, CharSequence filename) throws IOException {
/*  225 */     storeInts(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, PrintStream stream) {
/*  233 */     for (; i.hasNext(); stream.println(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, File file) throws IOException {
/*  241 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  242 */     storeInts(i, stream);
/*  243 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
/*  251 */     storeInts(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(BufferedReader reader, int[][] array, long offset, long length) throws IOException {
/*  262 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  263 */     long c = 0L;
/*      */     
/*      */     try {
/*  266 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  267 */         int[] t = array[i];
/*  268 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  269 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  270 */           String s; if ((s = reader.readLine()) != null) { t[d] = Integer.parseInt(s.trim()); }
/*  271 */           else { return c; }
/*  272 */            c++;
/*      */         }
/*      */       
/*      */       } 
/*  276 */     } catch (EOFException eOFException) {}
/*  277 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(BufferedReader reader, int[][] array) throws IOException {
/*  286 */     return loadInts(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array, long offset, long length) throws IOException {
/*  297 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  298 */     long result = loadInts(reader, array, offset, length);
/*  299 */     reader.close();
/*  300 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array, long offset, long length) throws IOException {
/*  311 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array) throws IOException {
/*  320 */     return loadInts(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array) throws IOException {
/*  329 */     return loadInts(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, PrintStream stream) {
/*  339 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  340 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  341 */       int[] t = array[i];
/*  342 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  343 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, PrintStream stream) {
/*  352 */     storeInts(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
/*  362 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  363 */     storeInts(array, offset, length, stream);
/*  364 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  374 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, File file) throws IOException {
/*  382 */     storeInts(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, CharSequence filename) throws IOException {
/*  390 */     storeInts(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class IntReaderWrapper implements IntIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private int next;
/*      */     
/*      */     public IntReaderWrapper(BufferedReader reader) {
/*  399 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  403 */       if (!this.toAdvance) return (this.s != null); 
/*  404 */       this.toAdvance = false;
/*      */       
/*  406 */       try { this.s = this.reader.readLine(); }
/*      */       
/*  408 */       catch (EOFException eOFException) {  }
/*  409 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  410 */        if (this.s == null) return false; 
/*  411 */       this.next = Integer.parseInt(this.s.trim());
/*  412 */       return true;
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  416 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  417 */       this.toAdvance = true;
/*  418 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(BufferedReader reader) {
/*  426 */     return new IntReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(File file) throws IOException {
/*  433 */     return new IntReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(CharSequence filename) throws IOException {
/*  440 */     return asIntIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(File file) {
/*  447 */     return () -> { try {
/*      */           return asIntIterator(file);
/*  449 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(CharSequence filename) {
/*  457 */     return () -> { try {
/*      */           return asIntIterator(filename);
/*  459 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadLongs(BufferedReader reader, long[] array, int offset, int length) throws IOException {
/*  509 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  510 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  513 */       for (i = 0; i < length && (
/*  514 */         s = reader.readLine()) != null; i++) array[i + offset] = Long.parseLong(s.trim());
/*      */     
/*      */     }
/*  517 */     catch (EOFException eOFException) {}
/*  518 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(BufferedReader reader, long[] array) throws IOException {
/*  527 */     return loadLongs(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array, int offset, int length) throws IOException {
/*  538 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  539 */     int result = loadLongs(reader, array, offset, length);
/*  540 */     reader.close();
/*  541 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array, int offset, int length) throws IOException {
/*  552 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array) throws IOException {
/*  561 */     return loadLongs(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array) throws IOException {
/*  570 */     return loadLongs(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, PrintStream stream) {
/*  580 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  581 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, PrintStream stream) {
/*  589 */     storeLongs(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
/*  599 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  600 */     storeLongs(array, offset, length, stream);
/*  601 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
/*  611 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, File file) throws IOException {
/*  619 */     storeLongs(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, CharSequence filename) throws IOException {
/*  627 */     storeLongs(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, PrintStream stream) {
/*  635 */     for (; i.hasNext(); stream.println(i.nextLong()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, File file) throws IOException {
/*  643 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  644 */     storeLongs(i, stream);
/*  645 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
/*  653 */     storeLongs(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(BufferedReader reader, long[][] array, long offset, long length) throws IOException {
/*  664 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  665 */     long c = 0L;
/*      */     
/*      */     try {
/*  668 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  669 */         long[] t = array[i];
/*  670 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  671 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  672 */           String s; if ((s = reader.readLine()) != null) { t[d] = Long.parseLong(s.trim()); }
/*  673 */           else { return c; }
/*  674 */            c++;
/*      */         }
/*      */       
/*      */       } 
/*  678 */     } catch (EOFException eOFException) {}
/*  679 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(BufferedReader reader, long[][] array) throws IOException {
/*  688 */     return loadLongs(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array, long offset, long length) throws IOException {
/*  699 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  700 */     long result = loadLongs(reader, array, offset, length);
/*  701 */     reader.close();
/*  702 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array, long offset, long length) throws IOException {
/*  713 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array) throws IOException {
/*  722 */     return loadLongs(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
/*  731 */     return loadLongs(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, PrintStream stream) {
/*  741 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  742 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  743 */       long[] t = array[i];
/*  744 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  745 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, PrintStream stream) {
/*  754 */     storeLongs(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
/*  764 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  765 */     storeLongs(array, offset, length, stream);
/*  766 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  776 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, File file) throws IOException {
/*  784 */     storeLongs(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
/*  792 */     storeLongs(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class LongReaderWrapper implements LongIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private long next;
/*      */     
/*      */     public LongReaderWrapper(BufferedReader reader) {
/*  801 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  805 */       if (!this.toAdvance) return (this.s != null); 
/*  806 */       this.toAdvance = false;
/*      */       
/*  808 */       try { this.s = this.reader.readLine(); }
/*      */       
/*  810 */       catch (EOFException eOFException) {  }
/*  811 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  812 */        if (this.s == null) return false; 
/*  813 */       this.next = Long.parseLong(this.s.trim());
/*  814 */       return true;
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  818 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  819 */       this.toAdvance = true;
/*  820 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(BufferedReader reader) {
/*  828 */     return new LongReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(File file) throws IOException {
/*  835 */     return new LongReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(CharSequence filename) throws IOException {
/*  842 */     return asLongIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(File file) {
/*  849 */     return () -> { try {
/*      */           return asLongIterator(file);
/*  851 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(CharSequence filename) {
/*  859 */     return () -> { try {
/*      */           return asLongIterator(filename);
/*  861 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadDoubles(BufferedReader reader, double[] array, int offset, int length) throws IOException {
/*  911 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/*  912 */     int i = 0;
/*      */     try {
/*      */       String s;
/*  915 */       for (i = 0; i < length && (
/*  916 */         s = reader.readLine()) != null; i++) array[i + offset] = Double.parseDouble(s.trim());
/*      */     
/*      */     }
/*  919 */     catch (EOFException eOFException) {}
/*  920 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(BufferedReader reader, double[] array) throws IOException {
/*  929 */     return loadDoubles(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array, int offset, int length) throws IOException {
/*  940 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/*  941 */     int result = loadDoubles(reader, array, offset, length);
/*  942 */     reader.close();
/*  943 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array, int offset, int length) throws IOException {
/*  954 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array) throws IOException {
/*  963 */     return loadDoubles(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
/*  972 */     return loadDoubles(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, PrintStream stream) {
/*  982 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/*  983 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, PrintStream stream) {
/*  991 */     storeDoubles(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
/* 1001 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1002 */     storeDoubles(array, offset, length, stream);
/* 1003 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1013 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, File file) throws IOException {
/* 1021 */     storeDoubles(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
/* 1029 */     storeDoubles(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, PrintStream stream) {
/* 1037 */     for (; i.hasNext(); stream.println(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, File file) throws IOException {
/* 1045 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1046 */     storeDoubles(i, stream);
/* 1047 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
/* 1055 */     storeDoubles(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(BufferedReader reader, double[][] array, long offset, long length) throws IOException {
/* 1066 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1067 */     long c = 0L;
/*      */     
/*      */     try {
/* 1070 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1071 */         double[] t = array[i];
/* 1072 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1073 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1074 */           String s; if ((s = reader.readLine()) != null) { t[d] = Double.parseDouble(s.trim()); }
/* 1075 */           else { return c; }
/* 1076 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1080 */     } catch (EOFException eOFException) {}
/* 1081 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(BufferedReader reader, double[][] array) throws IOException {
/* 1090 */     return loadDoubles(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array, long offset, long length) throws IOException {
/* 1101 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1102 */     long result = loadDoubles(reader, array, offset, length);
/* 1103 */     reader.close();
/* 1104 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array, long offset, long length) throws IOException {
/* 1115 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array) throws IOException {
/* 1124 */     return loadDoubles(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
/* 1133 */     return loadDoubles(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, PrintStream stream) {
/* 1143 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1144 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1145 */       double[] t = array[i];
/* 1146 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1147 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, PrintStream stream) {
/* 1156 */     storeDoubles(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
/* 1166 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1167 */     storeDoubles(array, offset, length, stream);
/* 1168 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1178 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, File file) throws IOException {
/* 1186 */     storeDoubles(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
/* 1194 */     storeDoubles(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class DoubleReaderWrapper implements DoubleIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private double next;
/*      */     
/*      */     public DoubleReaderWrapper(BufferedReader reader) {
/* 1203 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1207 */       if (!this.toAdvance) return (this.s != null); 
/* 1208 */       this.toAdvance = false;
/*      */       
/* 1210 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 1212 */       catch (EOFException eOFException) {  }
/* 1213 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1214 */        if (this.s == null) return false; 
/* 1215 */       this.next = Double.parseDouble(this.s.trim());
/* 1216 */       return true;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1220 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1221 */       this.toAdvance = true;
/* 1222 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(BufferedReader reader) {
/* 1230 */     return new DoubleReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(File file) throws IOException {
/* 1237 */     return new DoubleReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(CharSequence filename) throws IOException {
/* 1244 */     return asDoubleIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(File file) {
/* 1251 */     return () -> { try {
/*      */           return asDoubleIterator(file);
/* 1253 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(CharSequence filename) {
/* 1261 */     return () -> { try {
/*      */           return asDoubleIterator(filename);
/* 1263 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadBooleans(BufferedReader reader, boolean[] array, int offset, int length) throws IOException {
/* 1313 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 1314 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 1317 */       for (i = 0; i < length && (
/* 1318 */         s = reader.readLine()) != null; i++) array[i + offset] = Boolean.parseBoolean(s.trim());
/*      */     
/*      */     }
/* 1321 */     catch (EOFException eOFException) {}
/* 1322 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(BufferedReader reader, boolean[] array) throws IOException {
/* 1331 */     return loadBooleans(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array, int offset, int length) throws IOException {
/* 1342 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1343 */     int result = loadBooleans(reader, array, offset, length);
/* 1344 */     reader.close();
/* 1345 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array, int offset, int length) throws IOException {
/* 1356 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array) throws IOException {
/* 1365 */     return loadBooleans(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
/* 1374 */     return loadBooleans(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, PrintStream stream) {
/* 1384 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 1385 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, PrintStream stream) {
/* 1393 */     storeBooleans(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
/* 1403 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1404 */     storeBooleans(array, offset, length, stream);
/* 1405 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1415 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, File file) throws IOException {
/* 1423 */     storeBooleans(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
/* 1431 */     storeBooleans(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, PrintStream stream) {
/* 1439 */     for (; i.hasNext(); stream.println(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, File file) throws IOException {
/* 1447 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1448 */     storeBooleans(i, stream);
/* 1449 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
/* 1457 */     storeBooleans(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(BufferedReader reader, boolean[][] array, long offset, long length) throws IOException {
/* 1468 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1469 */     long c = 0L;
/*      */     
/*      */     try {
/* 1472 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1473 */         boolean[] t = array[i];
/* 1474 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1475 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1476 */           String s; if ((s = reader.readLine()) != null) { t[d] = Boolean.parseBoolean(s.trim()); }
/* 1477 */           else { return c; }
/* 1478 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1482 */     } catch (EOFException eOFException) {}
/* 1483 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(BufferedReader reader, boolean[][] array) throws IOException {
/* 1492 */     return loadBooleans(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array, long offset, long length) throws IOException {
/* 1503 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1504 */     long result = loadBooleans(reader, array, offset, length);
/* 1505 */     reader.close();
/* 1506 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array, long offset, long length) throws IOException {
/* 1517 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array) throws IOException {
/* 1526 */     return loadBooleans(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
/* 1535 */     return loadBooleans(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, PrintStream stream) {
/* 1545 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1546 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1547 */       boolean[] t = array[i];
/* 1548 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1549 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, PrintStream stream) {
/* 1558 */     storeBooleans(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
/* 1568 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1569 */     storeBooleans(array, offset, length, stream);
/* 1570 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1580 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, File file) throws IOException {
/* 1588 */     storeBooleans(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
/* 1596 */     storeBooleans(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class BooleanReaderWrapper implements BooleanIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private boolean next;
/*      */     
/*      */     public BooleanReaderWrapper(BufferedReader reader) {
/* 1605 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1609 */       if (!this.toAdvance) return (this.s != null); 
/* 1610 */       this.toAdvance = false;
/*      */       
/* 1612 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 1614 */       catch (EOFException eOFException) {  }
/* 1615 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1616 */        if (this.s == null) return false; 
/* 1617 */       this.next = Boolean.parseBoolean(this.s.trim());
/* 1618 */       return true;
/*      */     }
/*      */     
/*      */     public boolean nextBoolean() {
/* 1622 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1623 */       this.toAdvance = true;
/* 1624 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(BufferedReader reader) {
/* 1632 */     return new BooleanReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(File file) throws IOException {
/* 1639 */     return new BooleanReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(CharSequence filename) throws IOException {
/* 1646 */     return asBooleanIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(File file) {
/* 1653 */     return () -> { try {
/*      */           return asBooleanIterator(file);
/* 1655 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(CharSequence filename) {
/* 1663 */     return () -> { try {
/*      */           return asBooleanIterator(filename);
/* 1665 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadBytes(BufferedReader reader, byte[] array, int offset, int length) throws IOException {
/* 1715 */     ByteArrays.ensureOffsetLength(array, offset, length);
/* 1716 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 1719 */       for (i = 0; i < length && (
/* 1720 */         s = reader.readLine()) != null; i++) array[i + offset] = Byte.parseByte(s.trim());
/*      */     
/*      */     }
/* 1723 */     catch (EOFException eOFException) {}
/* 1724 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(BufferedReader reader, byte[] array) throws IOException {
/* 1733 */     return loadBytes(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array, int offset, int length) throws IOException {
/* 1744 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1745 */     int result = loadBytes(reader, array, offset, length);
/* 1746 */     reader.close();
/* 1747 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array, int offset, int length) throws IOException {
/* 1758 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array) throws IOException {
/* 1767 */     return loadBytes(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
/* 1776 */     return loadBytes(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, PrintStream stream) {
/* 1786 */     ByteArrays.ensureOffsetLength(array, offset, length);
/* 1787 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, PrintStream stream) {
/* 1795 */     storeBytes(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
/* 1805 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1806 */     storeBytes(array, offset, length, stream);
/* 1807 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1817 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, File file) throws IOException {
/* 1825 */     storeBytes(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
/* 1833 */     storeBytes(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, PrintStream stream) {
/* 1841 */     for (; i.hasNext(); stream.println(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, File file) throws IOException {
/* 1849 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1850 */     storeBytes(i, stream);
/* 1851 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
/* 1859 */     storeBytes(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(BufferedReader reader, byte[][] array, long offset, long length) throws IOException {
/* 1870 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1871 */     long c = 0L;
/*      */     
/*      */     try {
/* 1874 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1875 */         byte[] t = array[i];
/* 1876 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1877 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1878 */           String s; if ((s = reader.readLine()) != null) { t[d] = Byte.parseByte(s.trim()); }
/* 1879 */           else { return c; }
/* 1880 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 1884 */     } catch (EOFException eOFException) {}
/* 1885 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(BufferedReader reader, byte[][] array) throws IOException {
/* 1894 */     return loadBytes(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array, long offset, long length) throws IOException {
/* 1905 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 1906 */     long result = loadBytes(reader, array, offset, length);
/* 1907 */     reader.close();
/* 1908 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array, long offset, long length) throws IOException {
/* 1919 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array) throws IOException {
/* 1928 */     return loadBytes(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
/* 1937 */     return loadBytes(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, PrintStream stream) {
/* 1947 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1948 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1949 */       byte[] t = array[i];
/* 1950 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1951 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, PrintStream stream) {
/* 1960 */     storeBytes(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
/* 1970 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1971 */     storeBytes(array, offset, length, stream);
/* 1972 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1982 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, File file) throws IOException {
/* 1990 */     storeBytes(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
/* 1998 */     storeBytes(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class ByteReaderWrapper implements ByteIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private byte next;
/*      */     
/*      */     public ByteReaderWrapper(BufferedReader reader) {
/* 2007 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2011 */       if (!this.toAdvance) return (this.s != null); 
/* 2012 */       this.toAdvance = false;
/*      */       
/* 2014 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2016 */       catch (EOFException eOFException) {  }
/* 2017 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2018 */        if (this.s == null) return false; 
/* 2019 */       this.next = Byte.parseByte(this.s.trim());
/* 2020 */       return true;
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/* 2024 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2025 */       this.toAdvance = true;
/* 2026 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(BufferedReader reader) {
/* 2034 */     return new ByteReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(File file) throws IOException {
/* 2041 */     return new ByteReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(CharSequence filename) throws IOException {
/* 2048 */     return asByteIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(File file) {
/* 2055 */     return () -> { try {
/*      */           return asByteIterator(file);
/* 2057 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(CharSequence filename) {
/* 2065 */     return () -> { try {
/*      */           return asByteIterator(filename);
/* 2067 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadShorts(BufferedReader reader, short[] array, int offset, int length) throws IOException {
/* 2117 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 2118 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 2121 */       for (i = 0; i < length && (
/* 2122 */         s = reader.readLine()) != null; i++) array[i + offset] = Short.parseShort(s.trim());
/*      */     
/*      */     }
/* 2125 */     catch (EOFException eOFException) {}
/* 2126 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(BufferedReader reader, short[] array) throws IOException {
/* 2135 */     return loadShorts(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array, int offset, int length) throws IOException {
/* 2146 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2147 */     int result = loadShorts(reader, array, offset, length);
/* 2148 */     reader.close();
/* 2149 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array, int offset, int length) throws IOException {
/* 2160 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array) throws IOException {
/* 2169 */     return loadShorts(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array) throws IOException {
/* 2178 */     return loadShorts(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, PrintStream stream) {
/* 2188 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 2189 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, PrintStream stream) {
/* 2197 */     storeShorts(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
/* 2207 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2208 */     storeShorts(array, offset, length, stream);
/* 2209 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2219 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, File file) throws IOException {
/* 2227 */     storeShorts(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, CharSequence filename) throws IOException {
/* 2235 */     storeShorts(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, PrintStream stream) {
/* 2243 */     for (; i.hasNext(); stream.println(i.nextShort()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, File file) throws IOException {
/* 2251 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2252 */     storeShorts(i, stream);
/* 2253 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
/* 2261 */     storeShorts(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(BufferedReader reader, short[][] array, long offset, long length) throws IOException {
/* 2272 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2273 */     long c = 0L;
/*      */     
/*      */     try {
/* 2276 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2277 */         short[] t = array[i];
/* 2278 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2279 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2280 */           String s; if ((s = reader.readLine()) != null) { t[d] = Short.parseShort(s.trim()); }
/* 2281 */           else { return c; }
/* 2282 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 2286 */     } catch (EOFException eOFException) {}
/* 2287 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(BufferedReader reader, short[][] array) throws IOException {
/* 2296 */     return loadShorts(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array, long offset, long length) throws IOException {
/* 2307 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2308 */     long result = loadShorts(reader, array, offset, length);
/* 2309 */     reader.close();
/* 2310 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array, long offset, long length) throws IOException {
/* 2321 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array) throws IOException {
/* 2330 */     return loadShorts(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
/* 2339 */     return loadShorts(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, PrintStream stream) {
/* 2349 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2350 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2351 */       short[] t = array[i];
/* 2352 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2353 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, PrintStream stream) {
/* 2362 */     storeShorts(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
/* 2372 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2373 */     storeShorts(array, offset, length, stream);
/* 2374 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2384 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, File file) throws IOException {
/* 2392 */     storeShorts(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
/* 2400 */     storeShorts(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class ShortReaderWrapper implements ShortIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private short next;
/*      */     
/*      */     public ShortReaderWrapper(BufferedReader reader) {
/* 2409 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2413 */       if (!this.toAdvance) return (this.s != null); 
/* 2414 */       this.toAdvance = false;
/*      */       
/* 2416 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2418 */       catch (EOFException eOFException) {  }
/* 2419 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2420 */        if (this.s == null) return false; 
/* 2421 */       this.next = Short.parseShort(this.s.trim());
/* 2422 */       return true;
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 2426 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2427 */       this.toAdvance = true;
/* 2428 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(BufferedReader reader) {
/* 2436 */     return new ShortReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(File file) throws IOException {
/* 2443 */     return new ShortReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(CharSequence filename) throws IOException {
/* 2450 */     return asShortIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(File file) {
/* 2457 */     return () -> { try {
/*      */           return asShortIterator(file);
/* 2459 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(CharSequence filename) {
/* 2467 */     return () -> { try {
/*      */           return asShortIterator(filename);
/* 2469 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
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
/*      */   public static int loadFloats(BufferedReader reader, float[] array, int offset, int length) throws IOException {
/* 2519 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 2520 */     int i = 0;
/*      */     try {
/*      */       String s;
/* 2523 */       for (i = 0; i < length && (
/* 2524 */         s = reader.readLine()) != null; i++) array[i + offset] = Float.parseFloat(s.trim());
/*      */     
/*      */     }
/* 2527 */     catch (EOFException eOFException) {}
/* 2528 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(BufferedReader reader, float[] array) throws IOException {
/* 2537 */     return loadFloats(reader, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array, int offset, int length) throws IOException {
/* 2548 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2549 */     int result = loadFloats(reader, array, offset, length);
/* 2550 */     reader.close();
/* 2551 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array, int offset, int length) throws IOException {
/* 2562 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array) throws IOException {
/* 2571 */     return loadFloats(file, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array) throws IOException {
/* 2580 */     return loadFloats(filename, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, PrintStream stream) {
/* 2590 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 2591 */     for (int i = 0; i < length; ) { stream.println(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, PrintStream stream) {
/* 2599 */     storeFloats(array, 0, array.length, stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
/* 2609 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2610 */     storeFloats(array, offset, length, stream);
/* 2611 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2621 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, File file) throws IOException {
/* 2629 */     storeFloats(array, 0, array.length, file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, CharSequence filename) throws IOException {
/* 2637 */     storeFloats(array, 0, array.length, filename);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, PrintStream stream) {
/* 2645 */     for (; i.hasNext(); stream.println(i.nextFloat()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, File file) throws IOException {
/* 2653 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2654 */     storeFloats(i, stream);
/* 2655 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
/* 2663 */     storeFloats(i, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(BufferedReader reader, float[][] array, long offset, long length) throws IOException {
/* 2674 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2675 */     long c = 0L;
/*      */     
/*      */     try {
/* 2678 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2679 */         float[] t = array[i];
/* 2680 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2681 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2682 */           String s; if ((s = reader.readLine()) != null) { t[d] = Float.parseFloat(s.trim()); }
/* 2683 */           else { return c; }
/* 2684 */            c++;
/*      */         }
/*      */       
/*      */       } 
/* 2688 */     } catch (EOFException eOFException) {}
/* 2689 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(BufferedReader reader, float[][] array) throws IOException {
/* 2698 */     return loadFloats(reader, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array, long offset, long length) throws IOException {
/* 2709 */     BufferedReader reader = new BufferedReader(new FileReader(file));
/* 2710 */     long result = loadFloats(reader, array, offset, length);
/* 2711 */     reader.close();
/* 2712 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array, long offset, long length) throws IOException {
/* 2723 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array) throws IOException {
/* 2732 */     return loadFloats(file, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
/* 2741 */     return loadFloats(filename, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, PrintStream stream) {
/* 2751 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2752 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2753 */       float[] t = array[i];
/* 2754 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2755 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { stream.println(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, PrintStream stream) {
/* 2764 */     storeFloats(array, 0L, BigArrays.length(array), stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
/* 2774 */     PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2775 */     storeFloats(array, offset, length, stream);
/* 2776 */     stream.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2786 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, File file) throws IOException {
/* 2794 */     storeFloats(array, 0L, BigArrays.length(array), file);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
/* 2802 */     storeFloats(array, 0L, BigArrays.length(array), filename);
/*      */   }
/*      */   
/*      */   private static final class FloatReaderWrapper implements FloatIterator { private final BufferedReader reader;
/*      */     private boolean toAdvance = true;
/*      */     private String s;
/*      */     private float next;
/*      */     
/*      */     public FloatReaderWrapper(BufferedReader reader) {
/* 2811 */       this.reader = reader;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2815 */       if (!this.toAdvance) return (this.s != null); 
/* 2816 */       this.toAdvance = false;
/*      */       
/* 2818 */       try { this.s = this.reader.readLine(); }
/*      */       
/* 2820 */       catch (EOFException eOFException) {  }
/* 2821 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2822 */        if (this.s == null) return false; 
/* 2823 */       this.next = Float.parseFloat(this.s.trim());
/* 2824 */       return true;
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 2828 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2829 */       this.toAdvance = true;
/* 2830 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(BufferedReader reader) {
/* 2838 */     return new FloatReaderWrapper(reader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(File file) throws IOException {
/* 2845 */     return new FloatReaderWrapper(new BufferedReader(new FileReader(file)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(CharSequence filename) throws IOException {
/* 2852 */     return asFloatIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(File file) {
/* 2859 */     return () -> { try {
/*      */           return asFloatIterator(file);
/* 2861 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(CharSequence filename) {
/* 2869 */     return () -> { try {
/*      */           return asFloatIterator(filename);
/* 2871 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\TextIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */