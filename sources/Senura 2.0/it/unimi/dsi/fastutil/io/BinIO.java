/*      */ package it.unimi.dsi.fastutil.io;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterable;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterable;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharBigArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterable;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterable;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatBigArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterable;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntBigArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterable;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterable;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterable;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutput;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
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
/*      */ public class BinIO
/*      */ {
/*      */   private static final int MAX_IO_LENGTH = 1048576;
/*      */   
/*      */   public static void storeObject(Object o, File file) throws IOException {
/*   74 */     ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*   75 */     oos.writeObject(o);
/*   76 */     oos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeObject(Object o, CharSequence filename) throws IOException {
/*   85 */     storeObject(o, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object loadObject(File file) throws IOException, ClassNotFoundException {
/*   94 */     ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(new FileInputStream(file)));
/*   95 */     Object result = ois.readObject();
/*   96 */     ois.close();
/*   97 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object loadObject(CharSequence filename) throws IOException, ClassNotFoundException {
/*  106 */     return loadObject(new File(filename.toString()));
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
/*      */   public static void storeObject(Object o, OutputStream s) throws IOException {
/*  119 */     ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(s));
/*  120 */     oos.writeObject(o);
/*  121 */     oos.flush();
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
/*      */   public static Object loadObject(InputStream s) throws IOException, ClassNotFoundException {
/*  137 */     ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(s));
/*  138 */     Object result = ois.readObject();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int read(InputStream is, byte[] a, int offset, int length) throws IOException {
/*  182 */     if (length == 0) return 0; 
/*  183 */     int read = 0;
/*      */     while (true) {
/*  185 */       int result = is.read(a, offset + read, Math.min(length - read, 1048576));
/*  186 */       if (result < 0) return read; 
/*  187 */       read += result;
/*  188 */       if (read >= length)
/*  189 */         return read; 
/*      */     } 
/*      */   } private static void write(OutputStream outputStream, byte[] a, int offset, int length) throws IOException {
/*  192 */     int written = 0;
/*  193 */     while (written < length) {
/*  194 */       outputStream.write(a, offset + written, Math.min(length - written, 1048576));
/*  195 */       written += Math.min(length - written, 1048576);
/*      */     } 
/*      */   }
/*      */   private static void write(DataOutput dataOutput, byte[] a, int offset, int length) throws IOException {
/*  199 */     int written = 0;
/*  200 */     while (written < length) {
/*  201 */       dataOutput.write(a, offset + written, Math.min(length - written, 1048576));
/*  202 */       written += Math.min(length - written, 1048576);
/*      */     } 
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
/*      */   public static int loadBytes(InputStream inputStream, byte[] array, int offset, int length) throws IOException {
/*  218 */     return read(inputStream, array, offset, length);
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
/*      */   public static int loadBytes(InputStream inputStream, byte[] array) throws IOException {
/*  230 */     return read(inputStream, array, 0, array.length);
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
/*      */   public static void storeBytes(byte[] array, int offset, int length, OutputStream outputStream) throws IOException {
/*  243 */     write(outputStream, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, OutputStream outputStream) throws IOException {
/*  254 */     write(outputStream, array, 0, array.length);
/*      */   }
/*      */   private static long read(InputStream is, byte[][] a, long offset, long length) throws IOException {
/*  257 */     if (length == 0L) return 0L; 
/*  258 */     long read = 0L;
/*  259 */     int segment = BigArrays.segment(offset);
/*  260 */     int displacement = BigArrays.displacement(offset);
/*      */     
/*      */     while (true) {
/*  263 */       int result = is.read(a[segment], displacement, (int)Math.min(((a[segment]).length - displacement), Math.min(length - read, 1048576L)));
/*  264 */       if (result < 0) return read; 
/*  265 */       read += result;
/*  266 */       displacement += result;
/*  267 */       if (displacement == (a[segment]).length) {
/*  268 */         segment++;
/*  269 */         displacement = 0;
/*      */       } 
/*  271 */       if (read >= length)
/*  272 */         return read; 
/*      */     } 
/*      */   } private static void write(OutputStream outputStream, byte[][] a, long offset, long length) throws IOException {
/*  275 */     if (length == 0L)
/*  276 */       return;  long written = 0L;
/*      */     
/*  278 */     int segment = BigArrays.segment(offset);
/*  279 */     int displacement = BigArrays.displacement(offset);
/*      */     do {
/*  281 */       int toWrite = (int)Math.min(((a[segment]).length - displacement), Math.min(length - written, 1048576L));
/*  282 */       outputStream.write(a[segment], displacement, toWrite);
/*  283 */       written += toWrite;
/*  284 */       displacement += toWrite;
/*  285 */       if (displacement != (a[segment]).length)
/*  286 */         continue;  segment++;
/*  287 */       displacement = 0;
/*      */     }
/*  289 */     while (written < length);
/*      */   }
/*      */   private static void write(DataOutput dataOutput, byte[][] a, long offset, long length) throws IOException {
/*  292 */     if (length == 0L)
/*  293 */       return;  long written = 0L;
/*      */     
/*  295 */     int segment = BigArrays.segment(offset);
/*  296 */     int displacement = BigArrays.displacement(offset);
/*      */     do {
/*  298 */       int toWrite = (int)Math.min(((a[segment]).length - displacement), Math.min(length - written, 1048576L));
/*  299 */       dataOutput.write(a[segment], displacement, toWrite);
/*  300 */       written += toWrite;
/*  301 */       displacement += toWrite;
/*  302 */       if (displacement != (a[segment]).length)
/*  303 */         continue;  segment++;
/*  304 */       displacement = 0;
/*      */     }
/*  306 */     while (written < length);
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
/*      */   public static long loadBytes(InputStream inputStream, byte[][] array, long offset, long length) throws IOException {
/*  321 */     return read(inputStream, array, offset, length);
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
/*      */   public static long loadBytes(InputStream inputStream, byte[][] array) throws IOException {
/*  333 */     return read(inputStream, array, 0L, BigArrays.length(array));
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
/*      */   public static void storeBytes(byte[][] array, long offset, long length, OutputStream outputStream) throws IOException {
/*  346 */     write(outputStream, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, OutputStream outputStream) throws IOException {
/*  357 */     write(outputStream, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(DataInput dataInput, byte[] array, int offset, int length) throws IOException {
/*  368 */     ByteArrays.ensureOffsetLength(array, offset, length);
/*  369 */     int i = 0;
/*      */     try {
/*  371 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readByte(); i++; }
/*      */     
/*  373 */     } catch (EOFException eOFException) {}
/*  374 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(DataInput dataInput, byte[] array) throws IOException {
/*  383 */     int i = 0;
/*      */     try {
/*  385 */       int length = array.length;
/*  386 */       for (i = 0; i < length; ) { array[i] = dataInput.readByte(); i++; }
/*      */     
/*  388 */     } catch (EOFException eOFException) {}
/*  389 */     return i;
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
/*  400 */     ByteArrays.ensureOffsetLength(array, offset, length);
/*  401 */     FileInputStream fis = new FileInputStream(file);
/*  402 */     int result = read(fis, array, offset, length);
/*  403 */     fis.close();
/*  404 */     return result;
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
/*  415 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(File file, byte[] array) throws IOException {
/*  424 */     FileInputStream fis = new FileInputStream(file);
/*  425 */     int result = read(fis, array, 0, array.length);
/*  426 */     fis.close();
/*  427 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
/*  436 */     return loadBytes(new File(filename.toString()), array);
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
/*      */   public static byte[] loadBytes(File file) throws IOException {
/*  448 */     FileInputStream fis = new FileInputStream(file);
/*  449 */     long length = fis.getChannel().size() / 1L;
/*  450 */     if (length > 2147483647L) {
/*  451 */       fis.close();
/*  452 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/*  454 */     byte[] array = new byte[(int)length];
/*  455 */     if (read(fis, array, 0, (int)length) < length) throw new EOFException(); 
/*  456 */     fis.close();
/*  457 */     return array;
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
/*      */   public static byte[] loadBytes(CharSequence filename) throws IOException {
/*  469 */     return loadBytes(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/*  479 */     ByteArrays.ensureOffsetLength(array, offset, length);
/*  480 */     write(dataOutput, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, DataOutput dataOutput) throws IOException {
/*  488 */     write(dataOutput, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
/*  498 */     ByteArrays.ensureOffsetLength(array, offset, length);
/*  499 */     OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
/*  500 */     write(os, array, offset, length);
/*  501 */     os.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
/*  511 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, File file) throws IOException {
/*  519 */     OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
/*  520 */     write(os, array, 0, array.length);
/*  521 */     os.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
/*  529 */     storeBytes(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(DataInput dataInput, byte[][] array, long offset, long length) throws IOException {
/*  540 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  541 */     long c = 0L;
/*      */     try {
/*  543 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/*  544 */         byte[] t = array[i];
/*  545 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/*  546 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/*  547 */           t[d] = dataInput.readByte();
/*  548 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  552 */     } catch (EOFException eOFException) {}
/*  553 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(DataInput dataInput, byte[][] array) throws IOException {
/*  562 */     long c = 0L;
/*      */     try {
/*  564 */       for (int i = 0; i < array.length; i++) {
/*  565 */         byte[] t = array[i];
/*  566 */         int l = t.length;
/*  567 */         for (int d = 0; d < l; d++) {
/*  568 */           t[d] = dataInput.readByte();
/*  569 */           c++;
/*      */         }
/*      */       
/*      */       } 
/*  573 */     } catch (EOFException eOFException) {}
/*  574 */     return c;
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
/*  585 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  586 */     FileInputStream fis = new FileInputStream(file);
/*  587 */     long result = read(fis, array, offset, length);
/*  588 */     fis.close();
/*  589 */     return result;
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
/*  600 */     return loadBytes(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(File file, byte[][] array) throws IOException {
/*  609 */     FileInputStream fis = new FileInputStream(file);
/*  610 */     long result = read(fis, array, 0L, BigArrays.length(array));
/*  611 */     fis.close();
/*  612 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
/*  621 */     return loadBytes(new File(filename.toString()), array);
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
/*      */   public static byte[][] loadBytesBig(File file) throws IOException {
/*  633 */     FileInputStream fis = new FileInputStream(file);
/*  634 */     long length = fis.getChannel().size() / 1L;
/*  635 */     byte[][] array = ByteBigArrays.newBigArray(length);
/*  636 */     if (read(fis, array, 0L, length) < length) throw new EOFException(); 
/*  637 */     fis.close();
/*  638 */     return array;
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
/*      */   public static byte[][] loadBytesBig(CharSequence filename) throws IOException {
/*  650 */     return loadBytesBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/*  660 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  661 */     write(dataOutput, array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, DataOutput dataOutput) throws IOException {
/*  669 */     write(dataOutput, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
/*  679 */     BigArrays.ensureOffsetLength(array, offset, length);
/*  680 */     OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
/*  681 */     write(os, array, offset, length);
/*  682 */     os.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
/*  692 */     storeBytes(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, File file) throws IOException {
/*  700 */     OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
/*  701 */     write(os, array, 0L, BigArrays.length(array));
/*  702 */     os.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
/*  710 */     storeBytes(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, DataOutput dataOutput) throws IOException {
/*  718 */     for (; i.hasNext(); dataOutput.writeByte(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, File file) throws IOException {
/*  726 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  727 */     for (; i.hasNext(); dos.writeByte(i.nextByte()));
/*  728 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
/*  736 */     storeBytes(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class ByteDataInputWrapper implements ByteIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private byte next;
/*      */     
/*      */     public ByteDataInputWrapper(DataInput dataInput) {
/*  745 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  749 */       if (!this.toAdvance) return !this.endOfProcess; 
/*  750 */       this.toAdvance = false; 
/*  751 */       try { this.next = this.dataInput.readByte(); }
/*  752 */       catch (EOFException eof) { this.endOfProcess = true; }
/*  753 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/*  754 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/*  758 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  759 */       this.toAdvance = true;
/*  760 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(DataInput dataInput) {
/*  768 */     return new ByteDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(File file) throws IOException {
/*  775 */     return new ByteDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator asByteIterator(CharSequence filename) throws IOException {
/*  782 */     return asByteIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(File file) {
/*  789 */     return () -> { try {
/*      */           return asByteIterator(file);
/*  791 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ByteIterable asByteIterable(CharSequence filename) {
/*  799 */     return () -> { try {
/*      */           return asByteIterator(filename);
/*  801 */         } catch (IOException e) {
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
/*      */   public static int loadInts(DataInput dataInput, int[] array, int offset, int length) throws IOException {
/*  851 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  852 */     int i = 0;
/*      */     try {
/*  854 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readInt(); i++; }
/*      */     
/*  856 */     } catch (EOFException eOFException) {}
/*  857 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(DataInput dataInput, int[] array) throws IOException {
/*  866 */     int i = 0;
/*      */     try {
/*  868 */       int length = array.length;
/*  869 */       for (i = 0; i < length; ) { array[i] = dataInput.readInt(); i++; }
/*      */     
/*  871 */     } catch (EOFException eOFException) {}
/*  872 */     return i;
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
/*  883 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  884 */     FileInputStream fis = new FileInputStream(file);
/*  885 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  886 */     int i = 0;
/*      */     try {
/*  888 */       for (i = 0; i < length; ) { array[i + offset] = dis.readInt(); i++; }
/*      */     
/*  890 */     } catch (EOFException eOFException) {}
/*  891 */     dis.close();
/*  892 */     return i;
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
/*  903 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(File file, int[] array) throws IOException {
/*  912 */     FileInputStream fis = new FileInputStream(file);
/*  913 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  914 */     int i = 0;
/*      */     try {
/*  916 */       int length = array.length;
/*  917 */       for (i = 0; i < length; ) { array[i] = dis.readInt(); i++; }
/*      */     
/*  919 */     } catch (EOFException eOFException) {}
/*  920 */     dis.close();
/*  921 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadInts(CharSequence filename, int[] array) throws IOException {
/*  930 */     return loadInts(new File(filename.toString()), array);
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
/*      */   public static int[] loadInts(File file) throws IOException {
/*  942 */     FileInputStream fis = new FileInputStream(file);
/*  943 */     long length = fis.getChannel().size() / 4L;
/*  944 */     if (length > 2147483647L) {
/*  945 */       fis.close();
/*  946 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/*  948 */     int[] array = new int[(int)length];
/*  949 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/*  950 */     for (int i = 0; i < length; ) { array[i] = dis.readInt(); i++; }
/*  951 */      dis.close();
/*  952 */     return array;
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
/*      */   public static int[] loadInts(CharSequence filename) throws IOException {
/*  964 */     return loadInts(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/*  974 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  975 */     for (int i = 0; i < length; ) { dataOutput.writeInt(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, DataOutput dataOutput) throws IOException {
/*  983 */     int length = array.length;
/*  984 */     for (int i = 0; i < length; ) { dataOutput.writeInt(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
/*  994 */     IntArrays.ensureOffsetLength(array, offset, length);
/*  995 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/*  996 */     for (int i = 0; i < length; ) { dos.writeInt(array[offset + i]); i++; }
/*  997 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1007 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, File file) throws IOException {
/* 1015 */     int length = array.length;
/* 1016 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1017 */     for (int i = 0; i < length; ) { dos.writeInt(array[i]); i++; }
/* 1018 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[] array, CharSequence filename) throws IOException {
/* 1026 */     storeInts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(DataInput dataInput, int[][] array, long offset, long length) throws IOException {
/* 1037 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1038 */     long c = 0L;
/*      */     try {
/* 1040 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1041 */         int[] t = array[i];
/* 1042 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1043 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1044 */           t[d] = dataInput.readInt();
/* 1045 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1049 */     } catch (EOFException eOFException) {}
/* 1050 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(DataInput dataInput, int[][] array) throws IOException {
/* 1059 */     long c = 0L;
/*      */     try {
/* 1061 */       for (int i = 0; i < array.length; i++) {
/* 1062 */         int[] t = array[i];
/* 1063 */         int l = t.length;
/* 1064 */         for (int d = 0; d < l; d++) {
/* 1065 */           t[d] = dataInput.readInt();
/* 1066 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1070 */     } catch (EOFException eOFException) {}
/* 1071 */     return c;
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
/* 1082 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1083 */     FileInputStream fis = new FileInputStream(file);
/* 1084 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1085 */     long c = 0L;
/*      */     try {
/* 1087 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1088 */         int[] t = array[i];
/* 1089 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1090 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1091 */           t[d] = dis.readInt();
/* 1092 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1096 */     } catch (EOFException eOFException) {}
/* 1097 */     dis.close();
/* 1098 */     return c;
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
/* 1109 */     return loadInts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(File file, int[][] array) throws IOException {
/* 1118 */     FileInputStream fis = new FileInputStream(file);
/* 1119 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1120 */     long c = 0L;
/*      */     try {
/* 1122 */       for (int i = 0; i < array.length; i++) {
/* 1123 */         int[] t = array[i];
/* 1124 */         int l = t.length;
/* 1125 */         for (int d = 0; d < l; d++) {
/* 1126 */           t[d] = dis.readInt();
/* 1127 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1131 */     } catch (EOFException eOFException) {}
/* 1132 */     dis.close();
/* 1133 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadInts(CharSequence filename, int[][] array) throws IOException {
/* 1142 */     return loadInts(new File(filename.toString()), array);
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
/*      */   public static int[][] loadIntsBig(File file) throws IOException {
/* 1154 */     FileInputStream fis = new FileInputStream(file);
/* 1155 */     long length = fis.getChannel().size() / 4L;
/* 1156 */     int[][] array = IntBigArrays.newBigArray(length);
/* 1157 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1158 */     for (int i = 0; i < array.length; i++) {
/* 1159 */       int[] t = array[i];
/* 1160 */       int l = t.length;
/* 1161 */       for (int d = 0; d < l; ) { t[d] = dis.readInt(); d++; }
/*      */     
/* 1163 */     }  dis.close();
/* 1164 */     return array;
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
/*      */   public static int[][] loadIntsBig(CharSequence filename) throws IOException {
/* 1176 */     return loadIntsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 1186 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1187 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1188 */       int[] t = array[i];
/* 1189 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1190 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeInt(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, DataOutput dataOutput) throws IOException {
/* 1199 */     for (int i = 0; i < array.length; i++) {
/* 1200 */       int[] t = array[i];
/* 1201 */       int l = t.length;
/* 1202 */       for (int d = 0; d < l; ) { dataOutput.writeInt(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
/* 1213 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1214 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1215 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1216 */       int[] t = array[i];
/* 1217 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1218 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeInt(t[d]); d++; }
/*      */     
/* 1220 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1230 */     storeInts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, File file) throws IOException {
/* 1238 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1239 */     for (int i = 0; i < array.length; i++) {
/* 1240 */       int[] t = array[i];
/* 1241 */       int l = t.length;
/* 1242 */       for (int d = 0; d < l; ) { dos.writeInt(t[d]); d++; }
/*      */     
/* 1244 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(int[][] array, CharSequence filename) throws IOException {
/* 1252 */     storeInts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, DataOutput dataOutput) throws IOException {
/* 1260 */     for (; i.hasNext(); dataOutput.writeInt(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, File file) throws IOException {
/* 1268 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1269 */     for (; i.hasNext(); dos.writeInt(i.nextInt()));
/* 1270 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
/* 1278 */     storeInts(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class IntDataInputWrapper implements IntIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private int next;
/*      */     
/*      */     public IntDataInputWrapper(DataInput dataInput) {
/* 1287 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1291 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 1292 */       this.toAdvance = false; 
/* 1293 */       try { this.next = this.dataInput.readInt(); }
/* 1294 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 1295 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1296 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1300 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1301 */       this.toAdvance = true;
/* 1302 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(DataInput dataInput) {
/* 1310 */     return new IntDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(File file) throws IOException {
/* 1317 */     return new IntDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator asIntIterator(CharSequence filename) throws IOException {
/* 1324 */     return asIntIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(File file) {
/* 1331 */     return () -> { try {
/*      */           return asIntIterator(file);
/* 1333 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntIterable asIntIterable(CharSequence filename) {
/* 1341 */     return () -> { try {
/*      */           return asIntIterator(filename);
/* 1343 */         } catch (IOException e) {
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
/*      */   public static int loadLongs(DataInput dataInput, long[] array, int offset, int length) throws IOException {
/* 1393 */     LongArrays.ensureOffsetLength(array, offset, length);
/* 1394 */     int i = 0;
/*      */     try {
/* 1396 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readLong(); i++; }
/*      */     
/* 1398 */     } catch (EOFException eOFException) {}
/* 1399 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(DataInput dataInput, long[] array) throws IOException {
/* 1408 */     int i = 0;
/*      */     try {
/* 1410 */       int length = array.length;
/* 1411 */       for (i = 0; i < length; ) { array[i] = dataInput.readLong(); i++; }
/*      */     
/* 1413 */     } catch (EOFException eOFException) {}
/* 1414 */     return i;
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
/* 1425 */     LongArrays.ensureOffsetLength(array, offset, length);
/* 1426 */     FileInputStream fis = new FileInputStream(file);
/* 1427 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1428 */     int i = 0;
/*      */     try {
/* 1430 */       for (i = 0; i < length; ) { array[i + offset] = dis.readLong(); i++; }
/*      */     
/* 1432 */     } catch (EOFException eOFException) {}
/* 1433 */     dis.close();
/* 1434 */     return i;
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
/* 1445 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(File file, long[] array) throws IOException {
/* 1454 */     FileInputStream fis = new FileInputStream(file);
/* 1455 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1456 */     int i = 0;
/*      */     try {
/* 1458 */       int length = array.length;
/* 1459 */       for (i = 0; i < length; ) { array[i] = dis.readLong(); i++; }
/*      */     
/* 1461 */     } catch (EOFException eOFException) {}
/* 1462 */     dis.close();
/* 1463 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadLongs(CharSequence filename, long[] array) throws IOException {
/* 1472 */     return loadLongs(new File(filename.toString()), array);
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
/*      */   public static long[] loadLongs(File file) throws IOException {
/* 1484 */     FileInputStream fis = new FileInputStream(file);
/* 1485 */     long length = fis.getChannel().size() / 8L;
/* 1486 */     if (length > 2147483647L) {
/* 1487 */       fis.close();
/* 1488 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 1490 */     long[] array = new long[(int)length];
/* 1491 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1492 */     for (int i = 0; i < length; ) { array[i] = dis.readLong(); i++; }
/* 1493 */      dis.close();
/* 1494 */     return array;
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
/*      */   public static long[] loadLongs(CharSequence filename) throws IOException {
/* 1506 */     return loadLongs(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 1516 */     LongArrays.ensureOffsetLength(array, offset, length);
/* 1517 */     for (int i = 0; i < length; ) { dataOutput.writeLong(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, DataOutput dataOutput) throws IOException {
/* 1525 */     int length = array.length;
/* 1526 */     for (int i = 0; i < length; ) { dataOutput.writeLong(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
/* 1536 */     LongArrays.ensureOffsetLength(array, offset, length);
/* 1537 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1538 */     for (int i = 0; i < length; ) { dos.writeLong(array[offset + i]); i++; }
/* 1539 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
/* 1549 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, File file) throws IOException {
/* 1557 */     int length = array.length;
/* 1558 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1559 */     for (int i = 0; i < length; ) { dos.writeLong(array[i]); i++; }
/* 1560 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[] array, CharSequence filename) throws IOException {
/* 1568 */     storeLongs(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(DataInput dataInput, long[][] array, long offset, long length) throws IOException {
/* 1579 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1580 */     long c = 0L;
/*      */     try {
/* 1582 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1583 */         long[] t = array[i];
/* 1584 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1585 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1586 */           t[d] = dataInput.readLong();
/* 1587 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1591 */     } catch (EOFException eOFException) {}
/* 1592 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(DataInput dataInput, long[][] array) throws IOException {
/* 1601 */     long c = 0L;
/*      */     try {
/* 1603 */       for (int i = 0; i < array.length; i++) {
/* 1604 */         long[] t = array[i];
/* 1605 */         int l = t.length;
/* 1606 */         for (int d = 0; d < l; d++) {
/* 1607 */           t[d] = dataInput.readLong();
/* 1608 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1612 */     } catch (EOFException eOFException) {}
/* 1613 */     return c;
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
/* 1624 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1625 */     FileInputStream fis = new FileInputStream(file);
/* 1626 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1627 */     long c = 0L;
/*      */     try {
/* 1629 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1630 */         long[] t = array[i];
/* 1631 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1632 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 1633 */           t[d] = dis.readLong();
/* 1634 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1638 */     } catch (EOFException eOFException) {}
/* 1639 */     dis.close();
/* 1640 */     return c;
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
/* 1651 */     return loadLongs(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(File file, long[][] array) throws IOException {
/* 1660 */     FileInputStream fis = new FileInputStream(file);
/* 1661 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1662 */     long c = 0L;
/*      */     try {
/* 1664 */       for (int i = 0; i < array.length; i++) {
/* 1665 */         long[] t = array[i];
/* 1666 */         int l = t.length;
/* 1667 */         for (int d = 0; d < l; d++) {
/* 1668 */           t[d] = dis.readLong();
/* 1669 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 1673 */     } catch (EOFException eOFException) {}
/* 1674 */     dis.close();
/* 1675 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
/* 1684 */     return loadLongs(new File(filename.toString()), array);
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
/*      */   public static long[][] loadLongsBig(File file) throws IOException {
/* 1696 */     FileInputStream fis = new FileInputStream(file);
/* 1697 */     long length = fis.getChannel().size() / 8L;
/* 1698 */     long[][] array = LongBigArrays.newBigArray(length);
/* 1699 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1700 */     for (int i = 0; i < array.length; i++) {
/* 1701 */       long[] t = array[i];
/* 1702 */       int l = t.length;
/* 1703 */       for (int d = 0; d < l; ) { t[d] = dis.readLong(); d++; }
/*      */     
/* 1705 */     }  dis.close();
/* 1706 */     return array;
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
/*      */   public static long[][] loadLongsBig(CharSequence filename) throws IOException {
/* 1718 */     return loadLongsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 1728 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1729 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1730 */       long[] t = array[i];
/* 1731 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1732 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeLong(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, DataOutput dataOutput) throws IOException {
/* 1741 */     for (int i = 0; i < array.length; i++) {
/* 1742 */       long[] t = array[i];
/* 1743 */       int l = t.length;
/* 1744 */       for (int d = 0; d < l; ) { dataOutput.writeLong(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
/* 1755 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 1756 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1757 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 1758 */       long[] t = array[i];
/* 1759 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 1760 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeLong(t[d]); d++; }
/*      */     
/* 1762 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 1772 */     storeLongs(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, File file) throws IOException {
/* 1780 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1781 */     for (int i = 0; i < array.length; i++) {
/* 1782 */       long[] t = array[i];
/* 1783 */       int l = t.length;
/* 1784 */       for (int d = 0; d < l; ) { dos.writeLong(t[d]); d++; }
/*      */     
/* 1786 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
/* 1794 */     storeLongs(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, DataOutput dataOutput) throws IOException {
/* 1802 */     for (; i.hasNext(); dataOutput.writeLong(i.nextLong()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, File file) throws IOException {
/* 1810 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 1811 */     for (; i.hasNext(); dos.writeLong(i.nextLong()));
/* 1812 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
/* 1820 */     storeLongs(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class LongDataInputWrapper implements LongIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private long next;
/*      */     
/*      */     public LongDataInputWrapper(DataInput dataInput) {
/* 1829 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1833 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 1834 */       this.toAdvance = false; 
/* 1835 */       try { this.next = this.dataInput.readLong(); }
/* 1836 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 1837 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 1838 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 1842 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1843 */       this.toAdvance = true;
/* 1844 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(DataInput dataInput) {
/* 1852 */     return new LongDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(File file) throws IOException {
/* 1859 */     return new LongDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asLongIterator(CharSequence filename) throws IOException {
/* 1866 */     return asLongIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(File file) {
/* 1873 */     return () -> { try {
/*      */           return asLongIterator(file);
/* 1875 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static LongIterable asLongIterable(CharSequence filename) {
/* 1883 */     return () -> { try {
/*      */           return asLongIterator(filename);
/* 1885 */         } catch (IOException e) {
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
/*      */   public static int loadDoubles(DataInput dataInput, double[] array, int offset, int length) throws IOException {
/* 1935 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/* 1936 */     int i = 0;
/*      */     try {
/* 1938 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readDouble(); i++; }
/*      */     
/* 1940 */     } catch (EOFException eOFException) {}
/* 1941 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(DataInput dataInput, double[] array) throws IOException {
/* 1950 */     int i = 0;
/*      */     try {
/* 1952 */       int length = array.length;
/* 1953 */       for (i = 0; i < length; ) { array[i] = dataInput.readDouble(); i++; }
/*      */     
/* 1955 */     } catch (EOFException eOFException) {}
/* 1956 */     return i;
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
/* 1967 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/* 1968 */     FileInputStream fis = new FileInputStream(file);
/* 1969 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1970 */     int i = 0;
/*      */     try {
/* 1972 */       for (i = 0; i < length; ) { array[i + offset] = dis.readDouble(); i++; }
/*      */     
/* 1974 */     } catch (EOFException eOFException) {}
/* 1975 */     dis.close();
/* 1976 */     return i;
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
/* 1987 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(File file, double[] array) throws IOException {
/* 1996 */     FileInputStream fis = new FileInputStream(file);
/* 1997 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 1998 */     int i = 0;
/*      */     try {
/* 2000 */       int length = array.length;
/* 2001 */       for (i = 0; i < length; ) { array[i] = dis.readDouble(); i++; }
/*      */     
/* 2003 */     } catch (EOFException eOFException) {}
/* 2004 */     dis.close();
/* 2005 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
/* 2014 */     return loadDoubles(new File(filename.toString()), array);
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
/*      */   public static double[] loadDoubles(File file) throws IOException {
/* 2026 */     FileInputStream fis = new FileInputStream(file);
/* 2027 */     long length = fis.getChannel().size() / 8L;
/* 2028 */     if (length > 2147483647L) {
/* 2029 */       fis.close();
/* 2030 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 2032 */     double[] array = new double[(int)length];
/* 2033 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2034 */     for (int i = 0; i < length; ) { array[i] = dis.readDouble(); i++; }
/* 2035 */      dis.close();
/* 2036 */     return array;
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
/*      */   public static double[] loadDoubles(CharSequence filename) throws IOException {
/* 2048 */     return loadDoubles(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 2058 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/* 2059 */     for (int i = 0; i < length; ) { dataOutput.writeDouble(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, DataOutput dataOutput) throws IOException {
/* 2067 */     int length = array.length;
/* 2068 */     for (int i = 0; i < length; ) { dataOutput.writeDouble(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
/* 2078 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/* 2079 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2080 */     for (int i = 0; i < length; ) { dos.writeDouble(array[offset + i]); i++; }
/* 2081 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2091 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, File file) throws IOException {
/* 2099 */     int length = array.length;
/* 2100 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2101 */     for (int i = 0; i < length; ) { dos.writeDouble(array[i]); i++; }
/* 2102 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
/* 2110 */     storeDoubles(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(DataInput dataInput, double[][] array, long offset, long length) throws IOException {
/* 2121 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2122 */     long c = 0L;
/*      */     try {
/* 2124 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2125 */         double[] t = array[i];
/* 2126 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2127 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2128 */           t[d] = dataInput.readDouble();
/* 2129 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2133 */     } catch (EOFException eOFException) {}
/* 2134 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(DataInput dataInput, double[][] array) throws IOException {
/* 2143 */     long c = 0L;
/*      */     try {
/* 2145 */       for (int i = 0; i < array.length; i++) {
/* 2146 */         double[] t = array[i];
/* 2147 */         int l = t.length;
/* 2148 */         for (int d = 0; d < l; d++) {
/* 2149 */           t[d] = dataInput.readDouble();
/* 2150 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2154 */     } catch (EOFException eOFException) {}
/* 2155 */     return c;
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
/* 2166 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2167 */     FileInputStream fis = new FileInputStream(file);
/* 2168 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2169 */     long c = 0L;
/*      */     try {
/* 2171 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2172 */         double[] t = array[i];
/* 2173 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2174 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2175 */           t[d] = dis.readDouble();
/* 2176 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2180 */     } catch (EOFException eOFException) {}
/* 2181 */     dis.close();
/* 2182 */     return c;
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
/* 2193 */     return loadDoubles(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(File file, double[][] array) throws IOException {
/* 2202 */     FileInputStream fis = new FileInputStream(file);
/* 2203 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2204 */     long c = 0L;
/*      */     try {
/* 2206 */       for (int i = 0; i < array.length; i++) {
/* 2207 */         double[] t = array[i];
/* 2208 */         int l = t.length;
/* 2209 */         for (int d = 0; d < l; d++) {
/* 2210 */           t[d] = dis.readDouble();
/* 2211 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2215 */     } catch (EOFException eOFException) {}
/* 2216 */     dis.close();
/* 2217 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
/* 2226 */     return loadDoubles(new File(filename.toString()), array);
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
/*      */   public static double[][] loadDoublesBig(File file) throws IOException {
/* 2238 */     FileInputStream fis = new FileInputStream(file);
/* 2239 */     long length = fis.getChannel().size() / 8L;
/* 2240 */     double[][] array = DoubleBigArrays.newBigArray(length);
/* 2241 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2242 */     for (int i = 0; i < array.length; i++) {
/* 2243 */       double[] t = array[i];
/* 2244 */       int l = t.length;
/* 2245 */       for (int d = 0; d < l; ) { t[d] = dis.readDouble(); d++; }
/*      */     
/* 2247 */     }  dis.close();
/* 2248 */     return array;
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
/*      */   public static double[][] loadDoublesBig(CharSequence filename) throws IOException {
/* 2260 */     return loadDoublesBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 2270 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2271 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2272 */       double[] t = array[i];
/* 2273 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2274 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeDouble(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, DataOutput dataOutput) throws IOException {
/* 2283 */     for (int i = 0; i < array.length; i++) {
/* 2284 */       double[] t = array[i];
/* 2285 */       int l = t.length;
/* 2286 */       for (int d = 0; d < l; ) { dataOutput.writeDouble(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
/* 2297 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2298 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2299 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2300 */       double[] t = array[i];
/* 2301 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2302 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeDouble(t[d]); d++; }
/*      */     
/* 2304 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2314 */     storeDoubles(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, File file) throws IOException {
/* 2322 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2323 */     for (int i = 0; i < array.length; i++) {
/* 2324 */       double[] t = array[i];
/* 2325 */       int l = t.length;
/* 2326 */       for (int d = 0; d < l; ) { dos.writeDouble(t[d]); d++; }
/*      */     
/* 2328 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
/* 2336 */     storeDoubles(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, DataOutput dataOutput) throws IOException {
/* 2344 */     for (; i.hasNext(); dataOutput.writeDouble(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, File file) throws IOException {
/* 2352 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2353 */     for (; i.hasNext(); dos.writeDouble(i.nextDouble()));
/* 2354 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
/* 2362 */     storeDoubles(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class DoubleDataInputWrapper implements DoubleIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private double next;
/*      */     
/*      */     public DoubleDataInputWrapper(DataInput dataInput) {
/* 2371 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2375 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 2376 */       this.toAdvance = false; 
/* 2377 */       try { this.next = this.dataInput.readDouble(); }
/* 2378 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 2379 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2380 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 2384 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2385 */       this.toAdvance = true;
/* 2386 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(DataInput dataInput) {
/* 2394 */     return new DoubleDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(File file) throws IOException {
/* 2401 */     return new DoubleDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(CharSequence filename) throws IOException {
/* 2408 */     return asDoubleIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(File file) {
/* 2415 */     return () -> { try {
/*      */           return asDoubleIterator(file);
/* 2417 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static DoubleIterable asDoubleIterable(CharSequence filename) {
/* 2425 */     return () -> { try {
/*      */           return asDoubleIterator(filename);
/* 2427 */         } catch (IOException e) {
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
/*      */   public static int loadBooleans(DataInput dataInput, boolean[] array, int offset, int length) throws IOException {
/* 2477 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 2478 */     int i = 0;
/*      */     try {
/* 2480 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readBoolean(); i++; }
/*      */     
/* 2482 */     } catch (EOFException eOFException) {}
/* 2483 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(DataInput dataInput, boolean[] array) throws IOException {
/* 2492 */     int i = 0;
/*      */     try {
/* 2494 */       int length = array.length;
/* 2495 */       for (i = 0; i < length; ) { array[i] = dataInput.readBoolean(); i++; }
/*      */     
/* 2497 */     } catch (EOFException eOFException) {}
/* 2498 */     return i;
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
/* 2509 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 2510 */     FileInputStream fis = new FileInputStream(file);
/* 2511 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2512 */     int i = 0;
/*      */     try {
/* 2514 */       for (i = 0; i < length; ) { array[i + offset] = dis.readBoolean(); i++; }
/*      */     
/* 2516 */     } catch (EOFException eOFException) {}
/* 2517 */     dis.close();
/* 2518 */     return i;
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
/* 2529 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(File file, boolean[] array) throws IOException {
/* 2538 */     FileInputStream fis = new FileInputStream(file);
/* 2539 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2540 */     int i = 0;
/*      */     try {
/* 2542 */       int length = array.length;
/* 2543 */       for (i = 0; i < length; ) { array[i] = dis.readBoolean(); i++; }
/*      */     
/* 2545 */     } catch (EOFException eOFException) {}
/* 2546 */     dis.close();
/* 2547 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
/* 2556 */     return loadBooleans(new File(filename.toString()), array);
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
/*      */   public static boolean[] loadBooleans(File file) throws IOException {
/* 2568 */     FileInputStream fis = new FileInputStream(file);
/* 2569 */     long length = fis.getChannel().size();
/* 2570 */     if (length > 2147483647L) {
/* 2571 */       fis.close();
/* 2572 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 2574 */     boolean[] array = new boolean[(int)length];
/* 2575 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2576 */     for (int i = 0; i < length; ) { array[i] = dis.readBoolean(); i++; }
/* 2577 */      dis.close();
/* 2578 */     return array;
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
/*      */   public static boolean[] loadBooleans(CharSequence filename) throws IOException {
/* 2590 */     return loadBooleans(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 2600 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 2601 */     for (int i = 0; i < length; ) { dataOutput.writeBoolean(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, DataOutput dataOutput) throws IOException {
/* 2609 */     int length = array.length;
/* 2610 */     for (int i = 0; i < length; ) { dataOutput.writeBoolean(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
/* 2620 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 2621 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2622 */     for (int i = 0; i < length; ) { dos.writeBoolean(array[offset + i]); i++; }
/* 2623 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
/* 2633 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, File file) throws IOException {
/* 2641 */     int length = array.length;
/* 2642 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2643 */     for (int i = 0; i < length; ) { dos.writeBoolean(array[i]); i++; }
/* 2644 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
/* 2652 */     storeBooleans(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(DataInput dataInput, boolean[][] array, long offset, long length) throws IOException {
/* 2663 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2664 */     long c = 0L;
/*      */     try {
/* 2666 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2667 */         boolean[] t = array[i];
/* 2668 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2669 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2670 */           t[d] = dataInput.readBoolean();
/* 2671 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2675 */     } catch (EOFException eOFException) {}
/* 2676 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(DataInput dataInput, boolean[][] array) throws IOException {
/* 2685 */     long c = 0L;
/*      */     try {
/* 2687 */       for (int i = 0; i < array.length; i++) {
/* 2688 */         boolean[] t = array[i];
/* 2689 */         int l = t.length;
/* 2690 */         for (int d = 0; d < l; d++) {
/* 2691 */           t[d] = dataInput.readBoolean();
/* 2692 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2696 */     } catch (EOFException eOFException) {}
/* 2697 */     return c;
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
/* 2708 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2709 */     FileInputStream fis = new FileInputStream(file);
/* 2710 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2711 */     long c = 0L;
/*      */     try {
/* 2713 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2714 */         boolean[] t = array[i];
/* 2715 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2716 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 2717 */           t[d] = dis.readBoolean();
/* 2718 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2722 */     } catch (EOFException eOFException) {}
/* 2723 */     dis.close();
/* 2724 */     return c;
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
/* 2735 */     return loadBooleans(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(File file, boolean[][] array) throws IOException {
/* 2744 */     FileInputStream fis = new FileInputStream(file);
/* 2745 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2746 */     long c = 0L;
/*      */     try {
/* 2748 */       for (int i = 0; i < array.length; i++) {
/* 2749 */         boolean[] t = array[i];
/* 2750 */         int l = t.length;
/* 2751 */         for (int d = 0; d < l; d++) {
/* 2752 */           t[d] = dis.readBoolean();
/* 2753 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 2757 */     } catch (EOFException eOFException) {}
/* 2758 */     dis.close();
/* 2759 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
/* 2768 */     return loadBooleans(new File(filename.toString()), array);
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
/*      */   public static boolean[][] loadBooleansBig(File file) throws IOException {
/* 2780 */     FileInputStream fis = new FileInputStream(file);
/* 2781 */     long length = fis.getChannel().size();
/* 2782 */     boolean[][] array = BooleanBigArrays.newBigArray(length);
/* 2783 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 2784 */     for (int i = 0; i < array.length; i++) {
/* 2785 */       boolean[] t = array[i];
/* 2786 */       int l = t.length;
/* 2787 */       for (int d = 0; d < l; ) { t[d] = dis.readBoolean(); d++; }
/*      */     
/* 2789 */     }  dis.close();
/* 2790 */     return array;
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
/*      */   public static boolean[][] loadBooleansBig(CharSequence filename) throws IOException {
/* 2802 */     return loadBooleansBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 2812 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2813 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2814 */       boolean[] t = array[i];
/* 2815 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2816 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeBoolean(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, DataOutput dataOutput) throws IOException {
/* 2825 */     for (int i = 0; i < array.length; i++) {
/* 2826 */       boolean[] t = array[i];
/* 2827 */       int l = t.length;
/* 2828 */       for (int d = 0; d < l; ) { dataOutput.writeBoolean(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
/* 2839 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 2840 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2841 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 2842 */       boolean[] t = array[i];
/* 2843 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 2844 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeBoolean(t[d]); d++; }
/*      */     
/* 2846 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 2856 */     storeBooleans(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, File file) throws IOException {
/* 2864 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2865 */     for (int i = 0; i < array.length; i++) {
/* 2866 */       boolean[] t = array[i];
/* 2867 */       int l = t.length;
/* 2868 */       for (int d = 0; d < l; ) { dos.writeBoolean(t[d]); d++; }
/*      */     
/* 2870 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
/* 2878 */     storeBooleans(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, DataOutput dataOutput) throws IOException {
/* 2886 */     for (; i.hasNext(); dataOutput.writeBoolean(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, File file) throws IOException {
/* 2894 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 2895 */     for (; i.hasNext(); dos.writeBoolean(i.nextBoolean()));
/* 2896 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
/* 2904 */     storeBooleans(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class BooleanDataInputWrapper implements BooleanIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private boolean next;
/*      */     
/*      */     public BooleanDataInputWrapper(DataInput dataInput) {
/* 2913 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 2917 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 2918 */       this.toAdvance = false; 
/* 2919 */       try { this.next = this.dataInput.readBoolean(); }
/* 2920 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 2921 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 2922 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public boolean nextBoolean() {
/* 2926 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 2927 */       this.toAdvance = true;
/* 2928 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(DataInput dataInput) {
/* 2936 */     return new BooleanDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(File file) throws IOException {
/* 2943 */     return new BooleanDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator asBooleanIterator(CharSequence filename) throws IOException {
/* 2950 */     return asBooleanIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(File file) {
/* 2957 */     return () -> { try {
/*      */           return asBooleanIterator(file);
/* 2959 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static BooleanIterable asBooleanIterable(CharSequence filename) {
/* 2967 */     return () -> { try {
/*      */           return asBooleanIterator(filename);
/* 2969 */         } catch (IOException e) {
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
/*      */   public static int loadShorts(DataInput dataInput, short[] array, int offset, int length) throws IOException {
/* 3019 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 3020 */     int i = 0;
/*      */     try {
/* 3022 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readShort(); i++; }
/*      */     
/* 3024 */     } catch (EOFException eOFException) {}
/* 3025 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(DataInput dataInput, short[] array) throws IOException {
/* 3034 */     int i = 0;
/*      */     try {
/* 3036 */       int length = array.length;
/* 3037 */       for (i = 0; i < length; ) { array[i] = dataInput.readShort(); i++; }
/*      */     
/* 3039 */     } catch (EOFException eOFException) {}
/* 3040 */     return i;
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
/* 3051 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 3052 */     FileInputStream fis = new FileInputStream(file);
/* 3053 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3054 */     int i = 0;
/*      */     try {
/* 3056 */       for (i = 0; i < length; ) { array[i + offset] = dis.readShort(); i++; }
/*      */     
/* 3058 */     } catch (EOFException eOFException) {}
/* 3059 */     dis.close();
/* 3060 */     return i;
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
/* 3071 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(File file, short[] array) throws IOException {
/* 3080 */     FileInputStream fis = new FileInputStream(file);
/* 3081 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3082 */     int i = 0;
/*      */     try {
/* 3084 */       int length = array.length;
/* 3085 */       for (i = 0; i < length; ) { array[i] = dis.readShort(); i++; }
/*      */     
/* 3087 */     } catch (EOFException eOFException) {}
/* 3088 */     dis.close();
/* 3089 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadShorts(CharSequence filename, short[] array) throws IOException {
/* 3098 */     return loadShorts(new File(filename.toString()), array);
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
/*      */   public static short[] loadShorts(File file) throws IOException {
/* 3110 */     FileInputStream fis = new FileInputStream(file);
/* 3111 */     long length = fis.getChannel().size() / 2L;
/* 3112 */     if (length > 2147483647L) {
/* 3113 */       fis.close();
/* 3114 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 3116 */     short[] array = new short[(int)length];
/* 3117 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3118 */     for (int i = 0; i < length; ) { array[i] = dis.readShort(); i++; }
/* 3119 */      dis.close();
/* 3120 */     return array;
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
/*      */   public static short[] loadShorts(CharSequence filename) throws IOException {
/* 3132 */     return loadShorts(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 3142 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 3143 */     for (int i = 0; i < length; ) { dataOutput.writeShort(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, DataOutput dataOutput) throws IOException {
/* 3151 */     int length = array.length;
/* 3152 */     for (int i = 0; i < length; ) { dataOutput.writeShort(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
/* 3162 */     ShortArrays.ensureOffsetLength(array, offset, length);
/* 3163 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3164 */     for (int i = 0; i < length; ) { dos.writeShort(array[offset + i]); i++; }
/* 3165 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
/* 3175 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, File file) throws IOException {
/* 3183 */     int length = array.length;
/* 3184 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3185 */     for (int i = 0; i < length; ) { dos.writeShort(array[i]); i++; }
/* 3186 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[] array, CharSequence filename) throws IOException {
/* 3194 */     storeShorts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(DataInput dataInput, short[][] array, long offset, long length) throws IOException {
/* 3205 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3206 */     long c = 0L;
/*      */     try {
/* 3208 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3209 */         short[] t = array[i];
/* 3210 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3211 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 3212 */           t[d] = dataInput.readShort();
/* 3213 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3217 */     } catch (EOFException eOFException) {}
/* 3218 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(DataInput dataInput, short[][] array) throws IOException {
/* 3227 */     long c = 0L;
/*      */     try {
/* 3229 */       for (int i = 0; i < array.length; i++) {
/* 3230 */         short[] t = array[i];
/* 3231 */         int l = t.length;
/* 3232 */         for (int d = 0; d < l; d++) {
/* 3233 */           t[d] = dataInput.readShort();
/* 3234 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3238 */     } catch (EOFException eOFException) {}
/* 3239 */     return c;
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
/* 3250 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3251 */     FileInputStream fis = new FileInputStream(file);
/* 3252 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3253 */     long c = 0L;
/*      */     try {
/* 3255 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3256 */         short[] t = array[i];
/* 3257 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3258 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 3259 */           t[d] = dis.readShort();
/* 3260 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3264 */     } catch (EOFException eOFException) {}
/* 3265 */     dis.close();
/* 3266 */     return c;
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
/* 3277 */     return loadShorts(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(File file, short[][] array) throws IOException {
/* 3286 */     FileInputStream fis = new FileInputStream(file);
/* 3287 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3288 */     long c = 0L;
/*      */     try {
/* 3290 */       for (int i = 0; i < array.length; i++) {
/* 3291 */         short[] t = array[i];
/* 3292 */         int l = t.length;
/* 3293 */         for (int d = 0; d < l; d++) {
/* 3294 */           t[d] = dis.readShort();
/* 3295 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3299 */     } catch (EOFException eOFException) {}
/* 3300 */     dis.close();
/* 3301 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
/* 3310 */     return loadShorts(new File(filename.toString()), array);
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
/*      */   public static short[][] loadShortsBig(File file) throws IOException {
/* 3322 */     FileInputStream fis = new FileInputStream(file);
/* 3323 */     long length = fis.getChannel().size() / 2L;
/* 3324 */     short[][] array = ShortBigArrays.newBigArray(length);
/* 3325 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3326 */     for (int i = 0; i < array.length; i++) {
/* 3327 */       short[] t = array[i];
/* 3328 */       int l = t.length;
/* 3329 */       for (int d = 0; d < l; ) { t[d] = dis.readShort(); d++; }
/*      */     
/* 3331 */     }  dis.close();
/* 3332 */     return array;
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
/*      */   public static short[][] loadShortsBig(CharSequence filename) throws IOException {
/* 3344 */     return loadShortsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 3354 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3355 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3356 */       short[] t = array[i];
/* 3357 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3358 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeShort(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, DataOutput dataOutput) throws IOException {
/* 3367 */     for (int i = 0; i < array.length; i++) {
/* 3368 */       short[] t = array[i];
/* 3369 */       int l = t.length;
/* 3370 */       for (int d = 0; d < l; ) { dataOutput.writeShort(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
/* 3381 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3382 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3383 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3384 */       short[] t = array[i];
/* 3385 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3386 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeShort(t[d]); d++; }
/*      */     
/* 3388 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 3398 */     storeShorts(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, File file) throws IOException {
/* 3406 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3407 */     for (int i = 0; i < array.length; i++) {
/* 3408 */       short[] t = array[i];
/* 3409 */       int l = t.length;
/* 3410 */       for (int d = 0; d < l; ) { dos.writeShort(t[d]); d++; }
/*      */     
/* 3412 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
/* 3420 */     storeShorts(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, DataOutput dataOutput) throws IOException {
/* 3428 */     for (; i.hasNext(); dataOutput.writeShort(i.nextShort()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, File file) throws IOException {
/* 3436 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3437 */     for (; i.hasNext(); dos.writeShort(i.nextShort()));
/* 3438 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
/* 3446 */     storeShorts(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class ShortDataInputWrapper implements ShortIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private short next;
/*      */     
/*      */     public ShortDataInputWrapper(DataInput dataInput) {
/* 3455 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 3459 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 3460 */       this.toAdvance = false; 
/* 3461 */       try { this.next = this.dataInput.readShort(); }
/* 3462 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 3463 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 3464 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 3468 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 3469 */       this.toAdvance = true;
/* 3470 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(DataInput dataInput) {
/* 3478 */     return new ShortDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(File file) throws IOException {
/* 3485 */     return new ShortDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterator asShortIterator(CharSequence filename) throws IOException {
/* 3492 */     return asShortIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(File file) {
/* 3499 */     return () -> { try {
/*      */           return asShortIterator(file);
/* 3501 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShortIterable asShortIterable(CharSequence filename) {
/* 3509 */     return () -> { try {
/*      */           return asShortIterator(filename);
/* 3511 */         } catch (IOException e) {
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
/*      */   public static int loadChars(DataInput dataInput, char[] array, int offset, int length) throws IOException {
/* 3561 */     CharArrays.ensureOffsetLength(array, offset, length);
/* 3562 */     int i = 0;
/*      */     try {
/* 3564 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readChar(); i++; }
/*      */     
/* 3566 */     } catch (EOFException eOFException) {}
/* 3567 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(DataInput dataInput, char[] array) throws IOException {
/* 3576 */     int i = 0;
/*      */     try {
/* 3578 */       int length = array.length;
/* 3579 */       for (i = 0; i < length; ) { array[i] = dataInput.readChar(); i++; }
/*      */     
/* 3581 */     } catch (EOFException eOFException) {}
/* 3582 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(File file, char[] array, int offset, int length) throws IOException {
/* 3593 */     CharArrays.ensureOffsetLength(array, offset, length);
/* 3594 */     FileInputStream fis = new FileInputStream(file);
/* 3595 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3596 */     int i = 0;
/*      */     try {
/* 3598 */       for (i = 0; i < length; ) { array[i + offset] = dis.readChar(); i++; }
/*      */     
/* 3600 */     } catch (EOFException eOFException) {}
/* 3601 */     dis.close();
/* 3602 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(CharSequence filename, char[] array, int offset, int length) throws IOException {
/* 3613 */     return loadChars(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(File file, char[] array) throws IOException {
/* 3622 */     FileInputStream fis = new FileInputStream(file);
/* 3623 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3624 */     int i = 0;
/*      */     try {
/* 3626 */       int length = array.length;
/* 3627 */       for (i = 0; i < length; ) { array[i] = dis.readChar(); i++; }
/*      */     
/* 3629 */     } catch (EOFException eOFException) {}
/* 3630 */     dis.close();
/* 3631 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadChars(CharSequence filename, char[] array) throws IOException {
/* 3640 */     return loadChars(new File(filename.toString()), array);
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
/*      */   public static char[] loadChars(File file) throws IOException {
/* 3652 */     FileInputStream fis = new FileInputStream(file);
/* 3653 */     long length = fis.getChannel().size() / 2L;
/* 3654 */     if (length > 2147483647L) {
/* 3655 */       fis.close();
/* 3656 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 3658 */     char[] array = new char[(int)length];
/* 3659 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3660 */     for (int i = 0; i < length; ) { array[i] = dis.readChar(); i++; }
/* 3661 */      dis.close();
/* 3662 */     return array;
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
/*      */   public static char[] loadChars(CharSequence filename) throws IOException {
/* 3674 */     return loadChars(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 3684 */     CharArrays.ensureOffsetLength(array, offset, length);
/* 3685 */     for (int i = 0; i < length; ) { dataOutput.writeChar(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, DataOutput dataOutput) throws IOException {
/* 3693 */     int length = array.length;
/* 3694 */     for (int i = 0; i < length; ) { dataOutput.writeChar(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, File file) throws IOException {
/* 3704 */     CharArrays.ensureOffsetLength(array, offset, length);
/* 3705 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3706 */     for (int i = 0; i < length; ) { dos.writeChar(array[offset + i]); i++; }
/* 3707 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, int offset, int length, CharSequence filename) throws IOException {
/* 3717 */     storeChars(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, File file) throws IOException {
/* 3725 */     int length = array.length;
/* 3726 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3727 */     for (int i = 0; i < length; ) { dos.writeChar(array[i]); i++; }
/* 3728 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[] array, CharSequence filename) throws IOException {
/* 3736 */     storeChars(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(DataInput dataInput, char[][] array, long offset, long length) throws IOException {
/* 3747 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3748 */     long c = 0L;
/*      */     try {
/* 3750 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3751 */         char[] t = array[i];
/* 3752 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3753 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 3754 */           t[d] = dataInput.readChar();
/* 3755 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3759 */     } catch (EOFException eOFException) {}
/* 3760 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(DataInput dataInput, char[][] array) throws IOException {
/* 3769 */     long c = 0L;
/*      */     try {
/* 3771 */       for (int i = 0; i < array.length; i++) {
/* 3772 */         char[] t = array[i];
/* 3773 */         int l = t.length;
/* 3774 */         for (int d = 0; d < l; d++) {
/* 3775 */           t[d] = dataInput.readChar();
/* 3776 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3780 */     } catch (EOFException eOFException) {}
/* 3781 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(File file, char[][] array, long offset, long length) throws IOException {
/* 3792 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3793 */     FileInputStream fis = new FileInputStream(file);
/* 3794 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3795 */     long c = 0L;
/*      */     try {
/* 3797 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3798 */         char[] t = array[i];
/* 3799 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3800 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 3801 */           t[d] = dis.readChar();
/* 3802 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3806 */     } catch (EOFException eOFException) {}
/* 3807 */     dis.close();
/* 3808 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(CharSequence filename, char[][] array, long offset, long length) throws IOException {
/* 3819 */     return loadChars(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(File file, char[][] array) throws IOException {
/* 3828 */     FileInputStream fis = new FileInputStream(file);
/* 3829 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3830 */     long c = 0L;
/*      */     try {
/* 3832 */       for (int i = 0; i < array.length; i++) {
/* 3833 */         char[] t = array[i];
/* 3834 */         int l = t.length;
/* 3835 */         for (int d = 0; d < l; d++) {
/* 3836 */           t[d] = dis.readChar();
/* 3837 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 3841 */     } catch (EOFException eOFException) {}
/* 3842 */     dis.close();
/* 3843 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadChars(CharSequence filename, char[][] array) throws IOException {
/* 3852 */     return loadChars(new File(filename.toString()), array);
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
/*      */   public static char[][] loadCharsBig(File file) throws IOException {
/* 3864 */     FileInputStream fis = new FileInputStream(file);
/* 3865 */     long length = fis.getChannel().size() / 2L;
/* 3866 */     char[][] array = CharBigArrays.newBigArray(length);
/* 3867 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 3868 */     for (int i = 0; i < array.length; i++) {
/* 3869 */       char[] t = array[i];
/* 3870 */       int l = t.length;
/* 3871 */       for (int d = 0; d < l; ) { t[d] = dis.readChar(); d++; }
/*      */     
/* 3873 */     }  dis.close();
/* 3874 */     return array;
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
/*      */   public static char[][] loadCharsBig(CharSequence filename) throws IOException {
/* 3886 */     return loadCharsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 3896 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3897 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3898 */       char[] t = array[i];
/* 3899 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3900 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeChar(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, DataOutput dataOutput) throws IOException {
/* 3909 */     for (int i = 0; i < array.length; i++) {
/* 3910 */       char[] t = array[i];
/* 3911 */       int l = t.length;
/* 3912 */       for (int d = 0; d < l; ) { dataOutput.writeChar(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, File file) throws IOException {
/* 3923 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 3924 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3925 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 3926 */       char[] t = array[i];
/* 3927 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 3928 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeChar(t[d]); d++; }
/*      */     
/* 3930 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 3940 */     storeChars(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, File file) throws IOException {
/* 3948 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3949 */     for (int i = 0; i < array.length; i++) {
/* 3950 */       char[] t = array[i];
/* 3951 */       int l = t.length;
/* 3952 */       for (int d = 0; d < l; ) { dos.writeChar(t[d]); d++; }
/*      */     
/* 3954 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(char[][] array, CharSequence filename) throws IOException {
/* 3962 */     storeChars(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, DataOutput dataOutput) throws IOException {
/* 3970 */     for (; i.hasNext(); dataOutput.writeChar(i.nextChar()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, File file) throws IOException {
/* 3978 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 3979 */     for (; i.hasNext(); dos.writeChar(i.nextChar()));
/* 3980 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeChars(CharIterator i, CharSequence filename) throws IOException {
/* 3988 */     storeChars(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class CharDataInputWrapper implements CharIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private char next;
/*      */     
/*      */     public CharDataInputWrapper(DataInput dataInput) {
/* 3997 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 4001 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 4002 */       this.toAdvance = false; 
/* 4003 */       try { this.next = this.dataInput.readChar(); }
/* 4004 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 4005 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 4006 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public char nextChar() {
/* 4010 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 4011 */       this.toAdvance = true;
/* 4012 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(DataInput dataInput) {
/* 4020 */     return new CharDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(File file) throws IOException {
/* 4027 */     return new CharDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator asCharIterator(CharSequence filename) throws IOException {
/* 4034 */     return asCharIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterable asCharIterable(File file) {
/* 4041 */     return () -> { try {
/*      */           return asCharIterator(file);
/* 4043 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static CharIterable asCharIterable(CharSequence filename) {
/* 4051 */     return () -> { try {
/*      */           return asCharIterator(filename);
/* 4053 */         } catch (IOException e) {
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
/*      */   public static int loadFloats(DataInput dataInput, float[] array, int offset, int length) throws IOException {
/* 4103 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 4104 */     int i = 0;
/*      */     try {
/* 4106 */       for (i = 0; i < length; ) { array[i + offset] = dataInput.readFloat(); i++; }
/*      */     
/* 4108 */     } catch (EOFException eOFException) {}
/* 4109 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(DataInput dataInput, float[] array) throws IOException {
/* 4118 */     int i = 0;
/*      */     try {
/* 4120 */       int length = array.length;
/* 4121 */       for (i = 0; i < length; ) { array[i] = dataInput.readFloat(); i++; }
/*      */     
/* 4123 */     } catch (EOFException eOFException) {}
/* 4124 */     return i;
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
/* 4135 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 4136 */     FileInputStream fis = new FileInputStream(file);
/* 4137 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4138 */     int i = 0;
/*      */     try {
/* 4140 */       for (i = 0; i < length; ) { array[i + offset] = dis.readFloat(); i++; }
/*      */     
/* 4142 */     } catch (EOFException eOFException) {}
/* 4143 */     dis.close();
/* 4144 */     return i;
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
/* 4155 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(File file, float[] array) throws IOException {
/* 4164 */     FileInputStream fis = new FileInputStream(file);
/* 4165 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4166 */     int i = 0;
/*      */     try {
/* 4168 */       int length = array.length;
/* 4169 */       for (i = 0; i < length; ) { array[i] = dis.readFloat(); i++; }
/*      */     
/* 4171 */     } catch (EOFException eOFException) {}
/* 4172 */     dis.close();
/* 4173 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int loadFloats(CharSequence filename, float[] array) throws IOException {
/* 4182 */     return loadFloats(new File(filename.toString()), array);
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
/*      */   public static float[] loadFloats(File file) throws IOException {
/* 4194 */     FileInputStream fis = new FileInputStream(file);
/* 4195 */     long length = fis.getChannel().size() / 4L;
/* 4196 */     if (length > 2147483647L) {
/* 4197 */       fis.close();
/* 4198 */       throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
/*      */     } 
/* 4200 */     float[] array = new float[(int)length];
/* 4201 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4202 */     for (int i = 0; i < length; ) { array[i] = dis.readFloat(); i++; }
/* 4203 */      dis.close();
/* 4204 */     return array;
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
/*      */   public static float[] loadFloats(CharSequence filename) throws IOException {
/* 4216 */     return loadFloats(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, DataOutput dataOutput) throws IOException {
/* 4226 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 4227 */     for (int i = 0; i < length; ) { dataOutput.writeFloat(array[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, DataOutput dataOutput) throws IOException {
/* 4235 */     int length = array.length;
/* 4236 */     for (int i = 0; i < length; ) { dataOutput.writeFloat(array[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
/* 4246 */     FloatArrays.ensureOffsetLength(array, offset, length);
/* 4247 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 4248 */     for (int i = 0; i < length; ) { dos.writeFloat(array[offset + i]); i++; }
/* 4249 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
/* 4259 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, File file) throws IOException {
/* 4267 */     int length = array.length;
/* 4268 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 4269 */     for (int i = 0; i < length; ) { dos.writeFloat(array[i]); i++; }
/* 4270 */      dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[] array, CharSequence filename) throws IOException {
/* 4278 */     storeFloats(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(DataInput dataInput, float[][] array, long offset, long length) throws IOException {
/* 4289 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4290 */     long c = 0L;
/*      */     try {
/* 4292 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4293 */         float[] t = array[i];
/* 4294 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4295 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 4296 */           t[d] = dataInput.readFloat();
/* 4297 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4301 */     } catch (EOFException eOFException) {}
/* 4302 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(DataInput dataInput, float[][] array) throws IOException {
/* 4311 */     long c = 0L;
/*      */     try {
/* 4313 */       for (int i = 0; i < array.length; i++) {
/* 4314 */         float[] t = array[i];
/* 4315 */         int l = t.length;
/* 4316 */         for (int d = 0; d < l; d++) {
/* 4317 */           t[d] = dataInput.readFloat();
/* 4318 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4322 */     } catch (EOFException eOFException) {}
/* 4323 */     return c;
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
/* 4334 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4335 */     FileInputStream fis = new FileInputStream(file);
/* 4336 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4337 */     long c = 0L;
/*      */     try {
/* 4339 */       for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4340 */         float[] t = array[i];
/* 4341 */         int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4342 */         for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
/* 4343 */           t[d] = dis.readFloat();
/* 4344 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4348 */     } catch (EOFException eOFException) {}
/* 4349 */     dis.close();
/* 4350 */     return c;
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
/* 4361 */     return loadFloats(new File(filename.toString()), array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(File file, float[][] array) throws IOException {
/* 4370 */     FileInputStream fis = new FileInputStream(file);
/* 4371 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4372 */     long c = 0L;
/*      */     try {
/* 4374 */       for (int i = 0; i < array.length; i++) {
/* 4375 */         float[] t = array[i];
/* 4376 */         int l = t.length;
/* 4377 */         for (int d = 0; d < l; d++) {
/* 4378 */           t[d] = dis.readFloat();
/* 4379 */           c++;
/*      */         }
/*      */       
/*      */       } 
/* 4383 */     } catch (EOFException eOFException) {}
/* 4384 */     dis.close();
/* 4385 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
/* 4394 */     return loadFloats(new File(filename.toString()), array);
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
/*      */   public static float[][] loadFloatsBig(File file) throws IOException {
/* 4406 */     FileInputStream fis = new FileInputStream(file);
/* 4407 */     long length = fis.getChannel().size() / 4L;
/* 4408 */     float[][] array = FloatBigArrays.newBigArray(length);
/* 4409 */     DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
/* 4410 */     for (int i = 0; i < array.length; i++) {
/* 4411 */       float[] t = array[i];
/* 4412 */       int l = t.length;
/* 4413 */       for (int d = 0; d < l; ) { t[d] = dis.readFloat(); d++; }
/*      */     
/* 4415 */     }  dis.close();
/* 4416 */     return array;
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
/*      */   public static float[][] loadFloatsBig(CharSequence filename) throws IOException {
/* 4428 */     return loadFloatsBig(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
/* 4438 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4439 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4440 */       float[] t = array[i];
/* 4441 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4442 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dataOutput.writeFloat(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, DataOutput dataOutput) throws IOException {
/* 4451 */     for (int i = 0; i < array.length; i++) {
/* 4452 */       float[] t = array[i];
/* 4453 */       int l = t.length;
/* 4454 */       for (int d = 0; d < l; ) { dataOutput.writeFloat(t[d]); d++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
/* 4465 */     BigArrays.ensureOffsetLength(array, offset, length);
/* 4466 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 4467 */     for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
/* 4468 */       float[] t = array[i];
/* 4469 */       int l = (int)Math.min(t.length, offset + length - BigArrays.start(i));
/* 4470 */       for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; ) { dos.writeFloat(t[d]); d++; }
/*      */     
/* 4472 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
/* 4482 */     storeFloats(array, offset, length, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, File file) throws IOException {
/* 4490 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 4491 */     for (int i = 0; i < array.length; i++) {
/* 4492 */       float[] t = array[i];
/* 4493 */       int l = t.length;
/* 4494 */       for (int d = 0; d < l; ) { dos.writeFloat(t[d]); d++; }
/*      */     
/* 4496 */     }  dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
/* 4504 */     storeFloats(array, new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, DataOutput dataOutput) throws IOException {
/* 4512 */     for (; i.hasNext(); dataOutput.writeFloat(i.nextFloat()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, File file) throws IOException {
/* 4520 */     DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
/* 4521 */     for (; i.hasNext(); dos.writeFloat(i.nextFloat()));
/* 4522 */     dos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
/* 4530 */     storeFloats(i, new File(filename.toString()));
/*      */   }
/*      */   
/*      */   private static final class FloatDataInputWrapper implements FloatIterator { private final DataInput dataInput;
/*      */     private boolean toAdvance = true;
/*      */     private boolean endOfProcess = false;
/*      */     private float next;
/*      */     
/*      */     public FloatDataInputWrapper(DataInput dataInput) {
/* 4539 */       this.dataInput = dataInput;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 4543 */       if (!this.toAdvance) return !this.endOfProcess; 
/* 4544 */       this.toAdvance = false; 
/* 4545 */       try { this.next = this.dataInput.readFloat(); }
/* 4546 */       catch (EOFException eof) { this.endOfProcess = true; }
/* 4547 */       catch (IOException rethrow) { throw new RuntimeException(rethrow); }
/* 4548 */        return !this.endOfProcess;
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 4552 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 4553 */       this.toAdvance = true;
/* 4554 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(DataInput dataInput) {
/* 4562 */     return new FloatDataInputWrapper(dataInput);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(File file) throws IOException {
/* 4569 */     return new FloatDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator asFloatIterator(CharSequence filename) throws IOException {
/* 4576 */     return asFloatIterator(new File(filename.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(File file) {
/* 4583 */     return () -> { try {
/*      */           return asFloatIterator(file);
/* 4585 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public static FloatIterable asFloatIterable(CharSequence filename) {
/* 4593 */     return () -> { try {
/*      */           return asFloatIterator(filename);
/* 4595 */         } catch (IOException e) {
/*      */           throw new RuntimeException(e);
/*      */         } 
/*      */       };
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\BinIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */