package net.optifine;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import net.acE;
import net.optifine.Config;
import net.optifine.HttpPipelineConnection;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpResponse;
import net.optifine.MatchBlock;

public class HttpPipelineReceiver extends Thread {
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final Charset ASCII = Charset.forName("ASCII");
   private static final String a = "Content-Length";
   private static final char CR = '\r';
   private static final char LF = '\n';

   public HttpPipelineReceiver(HttpPipelineConnection var1) {
      super("HttpPipelineReceiver");
      this.httpPipelineConnection = var1;
   }

   public void run() {
      acE[] var1 = MatchBlock.b();
      if(!Thread.interrupted()) {
         HttpPipelineRequest var2 = null;

         try {
            var2 = this.httpPipelineConnection.getNextRequestReceive();
            InputStream var3 = this.httpPipelineConnection.getInputStream();
            HttpResponse var4 = this.readResponse(var3);
            this.httpPipelineConnection.onResponseReceived(var2, var4);
         } catch (InterruptedException var5) {
            return;
         } catch (Exception var6) {
            this.httpPipelineConnection.onExceptionReceive(var2, var6);
         }
      }

   }

   private HttpResponse readResponse(InputStream var1) throws IOException {
      MatchBlock.b();
      String var3 = this.readLine(var1);
      String[] var4 = Config.tokenize(var3, " ");
      if(var4.length < 3) {
         throw new IOException("Invalid status line: " + var3);
      } else {
         String var5 = var4[0];
         int var6 = Config.parseInt(var4[1], 0);
         String var7 = var4[2];
         LinkedHashMap var8 = new LinkedHashMap();

         while(true) {
            String var9 = this.readLine(var1);
            if(var9.length() <= 0) {
               byte[] var13 = null;
               String var14 = (String)var8.get("Content-Length");
               if(var14 != null) {
                  int var15 = Config.parseInt(var14, -1);
                  if(var15 > 0) {
                     var13 = new byte[var15];
                     this.readFull(var13, var1);
                  }
               } else {
                  String var16 = (String)var8.get("Transfer-Encoding");
                  if(Config.equals(var16, "chunked")) {
                     var13 = this.readContentChunked(var1);
                  }
               }

               return new HttpResponse(var6, var3, var8, var13);
            }

            int var10 = var9.indexOf(":");
            String var11 = var9.substring(0, var10).trim();
            String var12 = var9.substring(var10 + 1).trim();
            var8.put(var11, var12);
         }
      }
   }

   private byte[] readContentChunked(InputStream var1) throws IOException {
      MatchBlock.b();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      String var4 = this.readLine(var1);
      String[] var5 = Config.tokenize(var4, "; ");
      int var6 = Integer.parseInt(var5[0], 16);
      byte[] var7 = new byte[var6];
      this.readFull(var7, var1);
      var3.write(var7);
      this.readLine(var1);
      return var3.toByteArray();
   }

   private void readFull(byte[] var1, InputStream var2) throws IOException {
      MatchBlock.b();
      byte var5 = 0;
      if(var5 < var1.length) {
         int var4 = var2.read(var1, var5, var1.length - var5);
         if(var4 < 0) {
            throw new EOFException();
         }

         int var10000 = var5 + var4;
      }

   }

   private String readLine(InputStream var1) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      MatchBlock.b();
      byte var4 = -1;
      boolean var5 = false;
      int var6 = var1.read();
      var3.write(var6);
      if(var4 == 13 && var6 == 10) {
         var5 = true;
      }

      byte[] var9 = var3.toByteArray();
      String var7 = new String(var9, ASCII);
      var7 = var7.substring(0, var7.length() - 2);
      return var7;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
