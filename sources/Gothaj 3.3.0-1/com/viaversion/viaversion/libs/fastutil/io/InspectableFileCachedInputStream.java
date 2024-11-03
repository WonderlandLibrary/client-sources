package com.viaversion.viaversion.libs.fastutil.io;

import com.viaversion.viaversion.libs.fastutil.bytes.ByteArrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class InspectableFileCachedInputStream extends MeasurableInputStream implements RepositionableStream, WritableByteChannel {
   public static final boolean DEBUG = false;
   public static final int DEFAULT_BUFFER_SIZE = 65536;
   public final byte[] buffer;
   public int inspectable;
   private final File overflowFile;
   private final RandomAccessFile randomAccessFile;
   private final FileChannel fileChannel;
   private long position;
   private long mark;
   private long writePosition;

   public InspectableFileCachedInputStream(int bufferSize, File overflowFile) throws IOException {
      if (bufferSize <= 0) {
         throw new IllegalArgumentException("Illegal buffer size " + bufferSize);
      } else {
         if (overflowFile != null) {
            this.overflowFile = overflowFile;
         } else {
            (this.overflowFile = File.createTempFile(this.getClass().getSimpleName(), "overflow")).deleteOnExit();
         }

         this.buffer = new byte[bufferSize];
         this.randomAccessFile = new RandomAccessFile(this.overflowFile, "rw");
         this.fileChannel = this.randomAccessFile.getChannel();
         this.mark = -1L;
      }
   }

   public InspectableFileCachedInputStream(int bufferSize) throws IOException {
      this(bufferSize, null);
   }

   public InspectableFileCachedInputStream() throws IOException {
      this(65536);
   }

   private void ensureOpen() throws IOException {
      if (this.position == -1L) {
         throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
      }
   }

   public void clear() throws IOException {
      if (!this.fileChannel.isOpen()) {
         throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
      } else {
         this.writePosition = this.position = (long)(this.inspectable = 0);
         this.mark = -1L;
      }
   }

   @Override
   public int write(ByteBuffer byteBuffer) throws IOException {
      this.ensureOpen();
      int remaining = byteBuffer.remaining();
      if (this.inspectable < this.buffer.length) {
         int toBuffer = Math.min(this.buffer.length - this.inspectable, remaining);
         byteBuffer.get(this.buffer, this.inspectable, toBuffer);
         this.inspectable += toBuffer;
      }

      if (byteBuffer.hasRemaining()) {
         this.fileChannel.position(this.writePosition);
         this.writePosition = this.writePosition + (long)this.fileChannel.write(byteBuffer);
      }

      return remaining;
   }

   public void truncate(long size) throws FileNotFoundException, IOException {
      this.fileChannel.truncate(Math.max(size, this.writePosition));
   }

   @Override
   public void close() {
      this.position = -1L;
   }

   public void reopen() throws IOException {
      if (!this.fileChannel.isOpen()) {
         throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
      } else {
         this.position = 0L;
      }
   }

   public void dispose() throws IOException {
      this.position = -1L;
      this.randomAccessFile.close();
      this.overflowFile.delete();
   }

   @Override
   protected void finalize() throws Throwable {
      try {
         this.dispose();
      } finally {
         super.finalize();
      }
   }

   @Override
   public int available() throws IOException {
      this.ensureOpen();
      return (int)Math.min(2147483647L, this.length() - this.position);
   }

   @Override
   public int read(byte[] b, int offset, int length) throws IOException {
      this.ensureOpen();
      if (length == 0) {
         return 0;
      } else if (this.position == this.length()) {
         return -1;
      } else {
         ByteArrays.ensureOffsetLength(b, offset, length);
         int read = 0;
         if (this.position < (long)this.inspectable) {
            int toCopy = Math.min(this.inspectable - (int)this.position, length);
            System.arraycopy(this.buffer, (int)this.position, b, offset, toCopy);
            length -= toCopy;
            offset += toCopy;
            this.position += (long)toCopy;
            read = toCopy;
         }

         if (length > 0) {
            if (this.position == this.length()) {
               return read != 0 ? read : -1;
            }

            this.fileChannel.position(this.position - (long)this.inspectable);
            int toRead = (int)Math.min(this.length() - this.position, (long)length);
            int t = this.randomAccessFile.read(b, offset, toRead);
            this.position += (long)t;
            read += t;
         }

         return read;
      }
   }

   @Override
   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   @Override
   public long skip(long n) throws IOException {
      this.ensureOpen();
      long toSkip = Math.min(n, this.length() - this.position);
      this.position += toSkip;
      return toSkip;
   }

   @Override
   public int read() throws IOException {
      this.ensureOpen();
      if (this.position == this.length()) {
         return -1;
      } else if (this.position < (long)this.inspectable) {
         return this.buffer[(int)(this.position++)] & 0xFF;
      } else {
         this.fileChannel.position(this.position - (long)this.inspectable);
         this.position++;
         return this.randomAccessFile.read();
      }
   }

   @Override
   public long length() throws IOException {
      this.ensureOpen();
      return (long)this.inspectable + this.writePosition;
   }

   @Override
   public long position() throws IOException {
      this.ensureOpen();
      return this.position;
   }

   @Override
   public void position(long position) throws IOException {
      this.position = Math.min(position, this.length());
   }

   @Override
   public boolean isOpen() {
      return this.position != -1L;
   }

   @Override
   public void mark(int readlimit) {
      this.mark = this.position;
   }

   @Override
   public void reset() throws IOException {
      this.ensureOpen();
      if (this.mark == -1L) {
         throw new IOException("Mark has not been set");
      } else {
         this.position(this.mark);
      }
   }

   @Override
   public boolean markSupported() {
      return true;
   }
}
