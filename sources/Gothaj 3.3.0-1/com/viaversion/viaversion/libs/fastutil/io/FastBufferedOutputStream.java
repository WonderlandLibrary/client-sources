package com.viaversion.viaversion.libs.fastutil.io;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;

public class FastBufferedOutputStream extends MeasurableOutputStream implements RepositionableStream {
   private static final boolean ASSERTS = false;
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   protected byte[] buffer;
   protected int pos;
   protected int avail;
   protected OutputStream os;
   private FileChannel fileChannel;
   private RepositionableStream repositionableStream;
   private MeasurableStream measurableStream;

   private static int ensureBufferSize(int bufferSize) {
      if (bufferSize <= 0) {
         throw new IllegalArgumentException("Illegal buffer size: " + bufferSize);
      } else {
         return bufferSize;
      }
   }

   public FastBufferedOutputStream(OutputStream os, byte[] buffer) {
      this.os = os;
      ensureBufferSize(buffer.length);
      this.buffer = buffer;
      this.avail = buffer.length;
      if (os instanceof RepositionableStream) {
         this.repositionableStream = (RepositionableStream)os;
      }

      if (os instanceof MeasurableStream) {
         this.measurableStream = (MeasurableStream)os;
      }

      if (this.repositionableStream == null) {
         try {
            this.fileChannel = (FileChannel)os.getClass().getMethod("getChannel").invoke(os);
         } catch (IllegalAccessException var4) {
         } catch (IllegalArgumentException var5) {
         } catch (NoSuchMethodException var6) {
         } catch (InvocationTargetException var7) {
         } catch (ClassCastException var8) {
         }
      }
   }

   public FastBufferedOutputStream(OutputStream os, int bufferSize) {
      this(os, new byte[ensureBufferSize(bufferSize)]);
   }

   public FastBufferedOutputStream(OutputStream os) {
      this(os, 8192);
   }

   private void dumpBuffer(boolean ifFull) throws IOException {
      if (this.pos != 0) {
         if (!ifFull || this.avail == 0) {
            this.os.write(this.buffer, 0, this.pos);
            this.pos = 0;
            this.avail = this.buffer.length;
         }
      }
   }

   @Override
   public void write(int b) throws IOException {
      this.avail--;
      this.buffer[this.pos++] = (byte)b;
      this.dumpBuffer(true);
   }

   @Override
   public void write(byte[] b, int offset, int length) throws IOException {
      if (length >= this.buffer.length) {
         this.dumpBuffer(false);
         this.os.write(b, offset, length);
      } else if (length <= this.avail) {
         System.arraycopy(b, offset, this.buffer, this.pos, length);
         this.pos += length;
         this.avail -= length;
         this.dumpBuffer(true);
      } else {
         this.dumpBuffer(false);
         System.arraycopy(b, offset, this.buffer, 0, length);
         this.pos = length;
         this.avail -= length;
      }
   }

   @Override
   public void flush() throws IOException {
      this.dumpBuffer(false);
      this.os.flush();
   }

   @Override
   public void close() throws IOException {
      if (this.os != null) {
         this.flush();
         if (this.os != System.out) {
            this.os.close();
         }

         this.os = null;
         this.buffer = null;
      }
   }

   @Override
   public long position() throws IOException {
      if (this.repositionableStream != null) {
         return this.repositionableStream.position() + (long)this.pos;
      } else if (this.measurableStream != null) {
         return this.measurableStream.position() + (long)this.pos;
      } else if (this.fileChannel != null) {
         return this.fileChannel.position() + (long)this.pos;
      } else {
         throw new UnsupportedOperationException(
            "position() can only be called if the underlying byte stream implements the MeasurableStream or RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel"
         );
      }
   }

   @Override
   public void position(long newPosition) throws IOException {
      this.flush();
      if (this.repositionableStream != null) {
         this.repositionableStream.position(newPosition);
      } else {
         if (this.fileChannel == null) {
            throw new UnsupportedOperationException(
               "position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel"
            );
         }

         this.fileChannel.position(newPosition);
      }
   }

   @Override
   public long length() throws IOException {
      this.flush();
      if (this.measurableStream != null) {
         return this.measurableStream.length();
      } else if (this.fileChannel != null) {
         return this.fileChannel.size();
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
