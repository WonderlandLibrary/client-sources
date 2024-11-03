package com.viaversion.viaversion.libs.fastutil.io;

import com.viaversion.viaversion.libs.fastutil.bytes.ByteArrays;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.EnumSet;

public class FastBufferedInputStream extends MeasurableInputStream implements RepositionableStream {
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   public static final EnumSet<FastBufferedInputStream.LineTerminator> ALL_TERMINATORS = EnumSet.allOf(FastBufferedInputStream.LineTerminator.class);
   protected InputStream is;
   protected byte[] buffer;
   protected int pos;
   protected long readBytes;
   protected int avail;
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

   public FastBufferedInputStream(InputStream is, byte[] buffer) {
      this.is = is;
      ensureBufferSize(buffer.length);
      this.buffer = buffer;
      if (is instanceof RepositionableStream) {
         this.repositionableStream = (RepositionableStream)is;
      }

      if (is instanceof MeasurableStream) {
         this.measurableStream = (MeasurableStream)is;
      }

      if (this.repositionableStream == null) {
         try {
            this.fileChannel = (FileChannel)is.getClass().getMethod("getChannel").invoke(is);
         } catch (IllegalAccessException var4) {
         } catch (IllegalArgumentException var5) {
         } catch (NoSuchMethodException var6) {
         } catch (InvocationTargetException var7) {
         } catch (ClassCastException var8) {
         }
      }
   }

   public FastBufferedInputStream(InputStream is, int bufferSize) {
      this(is, new byte[ensureBufferSize(bufferSize)]);
   }

   public FastBufferedInputStream(InputStream is) {
      this(is, 8192);
   }

   protected boolean noMoreCharacters() throws IOException {
      if (this.avail == 0) {
         this.avail = this.is.read(this.buffer);
         if (this.avail <= 0) {
            this.avail = 0;
            return true;
         }

         this.pos = 0;
      }

      return false;
   }

   @Override
   public int read() throws IOException {
      if (this.noMoreCharacters()) {
         return -1;
      } else {
         this.avail--;
         this.readBytes++;
         return this.buffer[this.pos++] & 0xFF;
      }
   }

   @Override
   public int read(byte[] b, int offset, int length) throws IOException {
      if (length <= this.avail) {
         System.arraycopy(this.buffer, this.pos, b, offset, length);
         this.pos += length;
         this.avail -= length;
         this.readBytes += (long)length;
         return length;
      } else {
         int head = this.avail;
         System.arraycopy(this.buffer, this.pos, b, offset, head);
         this.pos = this.avail = 0;
         this.readBytes += (long)head;
         if (length > this.buffer.length) {
            int result = this.is.read(b, offset + head, length - head);
            if (result > 0) {
               this.readBytes += (long)result;
            }

            return result < 0 ? (head == 0 ? -1 : head) : result + head;
         } else if (this.noMoreCharacters()) {
            return head == 0 ? -1 : head;
         } else {
            int toRead = Math.min(length - head, this.avail);
            this.readBytes += (long)toRead;
            System.arraycopy(this.buffer, 0, b, offset + head, toRead);
            this.pos = toRead;
            this.avail -= toRead;
            return toRead + head;
         }
      }
   }

   public int readLine(byte[] array) throws IOException {
      return this.readLine(array, 0, array.length, ALL_TERMINATORS);
   }

   public int readLine(byte[] array, EnumSet<FastBufferedInputStream.LineTerminator> terminators) throws IOException {
      return this.readLine(array, 0, array.length, terminators);
   }

   public int readLine(byte[] array, int off, int len) throws IOException {
      return this.readLine(array, off, len, ALL_TERMINATORS);
   }

   public int readLine(byte[] array, int off, int len, EnumSet<FastBufferedInputStream.LineTerminator> terminators) throws IOException {
      ByteArrays.ensureOffsetLength(array, off, len);
      if (len == 0) {
         return 0;
      } else if (this.noMoreCharacters()) {
         return -1;
      } else {
         int k = 0;
         int remaining = len;
         int read = 0;

         while (true) {
            int i = 0;

            while (i < this.avail && i < remaining && (k = this.buffer[this.pos + i]) != 10 && k != 13) {
               i++;
            }

            System.arraycopy(this.buffer, this.pos, array, off + read, i);
            this.pos += i;
            this.avail -= i;
            read += i;
            remaining -= i;
            if (remaining == 0) {
               this.readBytes += (long)read;
               return read;
            }

            if (this.avail > 0) {
               if (k == 10) {
                  this.pos++;
                  this.avail--;
                  if (terminators.contains(FastBufferedInputStream.LineTerminator.LF)) {
                     this.readBytes += (long)(read + 1);
                     return read;
                  }

                  array[off + read++] = 10;
                  remaining--;
               } else if (k == 13) {
                  this.pos++;
                  this.avail--;
                  if (terminators.contains(FastBufferedInputStream.LineTerminator.CR_LF)) {
                     if (this.avail > 0) {
                        if (this.buffer[this.pos] == 10) {
                           this.pos++;
                           this.avail--;
                           this.readBytes += (long)(read + 2);
                           return read;
                        }
                     } else {
                        if (this.noMoreCharacters()) {
                           if (!terminators.contains(FastBufferedInputStream.LineTerminator.CR)) {
                              array[off + read++] = 13;
                              remaining--;
                              this.readBytes += (long)read;
                           } else {
                              this.readBytes += (long)(read + 1);
                           }

                           return read;
                        }

                        if (this.buffer[0] == 10) {
                           this.pos++;
                           this.avail--;
                           this.readBytes += (long)(read + 2);
                           return read;
                        }
                     }
                  }

                  if (terminators.contains(FastBufferedInputStream.LineTerminator.CR)) {
                     this.readBytes += (long)(read + 1);
                     return read;
                  }

                  array[off + read++] = 13;
                  remaining--;
               }
            } else if (this.noMoreCharacters()) {
               this.readBytes += (long)read;
               return read;
            }
         }
      }
   }

   @Override
   public void position(long newPosition) throws IOException {
      long position = this.readBytes;
      if (newPosition <= position + (long)this.avail && newPosition >= position - (long)this.pos) {
         this.pos = (int)((long)this.pos + (newPosition - position));
         this.avail = (int)((long)this.avail - (newPosition - position));
         this.readBytes = newPosition;
      } else {
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

         this.readBytes = newPosition;
         this.avail = this.pos = 0;
      }
   }

   @Override
   public long position() throws IOException {
      return this.readBytes;
   }

   @Override
   public long length() throws IOException {
      if (this.measurableStream != null) {
         return this.measurableStream.length();
      } else if (this.fileChannel != null) {
         return this.fileChannel.size();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private long skipByReading(long n) throws IOException {
      long toSkip = n;

      while (toSkip > 0L) {
         int len = this.is.read(this.buffer, 0, (int)Math.min((long)this.buffer.length, toSkip));
         if (len <= 0) {
            break;
         }

         toSkip -= (long)len;
      }

      return n - toSkip;
   }

   @Override
   public long skip(long n) throws IOException {
      if (n <= (long)this.avail) {
         int m = (int)n;
         this.pos += m;
         this.avail -= m;
         this.readBytes += n;
         return n;
      } else {
         long toSkip = n - (long)this.avail;
         long result = 0L;
         this.avail = 0;

         while (toSkip != 0L && (result = this.is == System.in ? this.skipByReading(toSkip) : this.is.skip(toSkip)) < toSkip) {
            if (result == 0L) {
               if (this.is.read() == -1) {
                  break;
               }

               toSkip--;
            } else {
               toSkip -= result;
            }
         }

         long t = n - (toSkip - result);
         this.readBytes += t;
         return t;
      }
   }

   @Override
   public int available() throws IOException {
      return (int)Math.min((long)this.is.available() + (long)this.avail, 2147483647L);
   }

   @Override
   public void close() throws IOException {
      if (this.is != null) {
         if (this.is != System.in) {
            this.is.close();
         }

         this.is = null;
         this.buffer = null;
      }
   }

   public void flush() {
      if (this.is != null) {
         this.readBytes = this.readBytes + (long)this.avail;
         this.avail = this.pos = 0;
      }
   }

   @Deprecated
   @Override
   public void reset() {
      this.flush();
   }

   public static enum LineTerminator {
      CR,
      LF,
      CR_LF;
   }
}
