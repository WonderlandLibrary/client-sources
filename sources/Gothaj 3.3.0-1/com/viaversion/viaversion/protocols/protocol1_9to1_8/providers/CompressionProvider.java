package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionProvider implements Provider {
   public void handlePlayCompression(UserConnection user, int threshold) {
      if (!user.isClientSide()) {
         throw new IllegalStateException("PLAY state Compression packet is unsupported");
      } else {
         ChannelPipeline pipe = user.getChannel().pipeline();
         if (threshold < 0) {
            if (pipe.get("compress") != null) {
               pipe.remove("compress");
               pipe.remove("decompress");
            }
         } else if (pipe.get("compress") == null) {
            pipe.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(threshold));
            pipe.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(threshold));
         } else {
            ((CompressionProvider.CompressionHandler)pipe.get("compress")).setCompressionThreshold(threshold);
            ((CompressionProvider.CompressionHandler)pipe.get("decompress")).setCompressionThreshold(threshold);
         }
      }
   }

   protected CompressionProvider.CompressionHandler getEncoder(int threshold) {
      return new CompressionProvider.Compressor(threshold);
   }

   protected CompressionProvider.CompressionHandler getDecoder(int threshold) {
      return new CompressionProvider.Decompressor(threshold);
   }

   public interface CompressionHandler extends ChannelHandler {
      void setCompressionThreshold(int var1);
   }

   private static class Compressor extends MessageToByteEncoder<ByteBuf> implements CompressionProvider.CompressionHandler {
      private final Deflater deflater;
      private int threshold;

      public Compressor(int var1) {
         this.threshold = var1;
         this.deflater = new Deflater();
      }

      protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
         int frameLength = in.readableBytes();
         if (frameLength < this.threshold) {
            out.writeByte(0);
            out.writeBytes(in);
         } else {
            Type.VAR_INT.writePrimitive(out, frameLength);
            ByteBuf temp = in;
            if (!in.hasArray()) {
               temp = ctx.alloc().heapBuffer().writeBytes(in);
            } else {
               in.retain();
            }

            ByteBuf output = ctx.alloc().heapBuffer();

            try {
               this.deflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
               this.deflater.finish();

               while (!this.deflater.finished()) {
                  output.ensureWritable(4096);
                  output.writerIndex(
                     output.writerIndex() + this.deflater.deflate(output.array(), output.arrayOffset() + output.writerIndex(), output.writableBytes())
                  );
               }

               out.writeBytes(output);
            } finally {
               output.release();
               temp.release();
               this.deflater.reset();
            }
         }
      }

      @Override
      public void setCompressionThreshold(int threshold) {
         this.threshold = threshold;
      }
   }

   private static class Decompressor extends MessageToMessageDecoder<ByteBuf> implements CompressionProvider.CompressionHandler {
      private final Inflater inflater;
      private int threshold;

      public Decompressor(int var1) {
         this.threshold = var1;
         this.inflater = new Inflater();
      }

      protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
         if (in.isReadable()) {
            int outLength = Type.VAR_INT.readPrimitive(in);
            if (outLength == 0) {
               out.add(in.readBytes(in.readableBytes()));
            } else if (outLength < this.threshold) {
               throw new DecoderException("Badly compressed packet - size of " + outLength + " is below server threshold of " + this.threshold);
            } else if (outLength > 2097152) {
               throw new DecoderException("Badly compressed packet - size of " + outLength + " is larger than protocol maximum of " + 2097152);
            } else {
               ByteBuf temp = in;
               if (!in.hasArray()) {
                  temp = ctx.alloc().heapBuffer().writeBytes(in);
               } else {
                  in.retain();
               }

               ByteBuf output = ctx.alloc().heapBuffer(outLength, outLength);

               try {
                  this.inflater.setInput(temp.array(), temp.arrayOffset() + temp.readerIndex(), temp.readableBytes());
                  output.writerIndex(output.writerIndex() + this.inflater.inflate(output.array(), output.arrayOffset(), outLength));
                  out.add(output.retain());
               } finally {
                  output.release();
                  temp.release();
                  this.inflater.reset();
               }
            }
         }
      }

      @Override
      public void setCompressionThreshold(int threshold) {
         this.threshold = threshold;
      }
   }
}
