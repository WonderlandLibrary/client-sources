package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.stream.IngestServerTester$1;
import net.minecraft.client.stream.IngestServerTester$2;
import net.minecraft.client.stream.IngestServerTester$3;
import net.minecraft.client.stream.IngestServerTester$IngestTestListener;
import net.minecraft.client.stream.IngestServerTester$IngestTestState;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.VideoParams;

public class IngestServerTester {
   protected IngestServerTester$IngestTestListener field_153044_b = null;
   protected Stream field_153045_c = null;
   protected IngestList field_153046_d = null;
   protected IngestServerTester$IngestTestState field_153047_e = IngestServerTester$IngestTestState.Uninitalized;
   protected long field_153048_f = 8000L;
   protected long field_153049_g = 2000L;
   protected long field_153050_h = 0L;
   protected RTMPState field_153051_i = RTMPState.Invalid;
   protected VideoParams field_153052_j = null;
   protected AudioParams audioParameters = null;
   protected long field_153054_l = 0L;
   protected List field_153055_m = null;
   protected boolean field_153056_n = false;
   protected IStreamCallbacks field_153057_o = null;
   protected IStatCallbacks field_153058_p = null;
   protected IngestServer field_153059_q = null;
   protected boolean field_153060_r = false;
   protected boolean field_153061_s = false;
   protected int field_153062_t = -1;
   protected int field_153063_u = 0;
   protected long field_153064_v = 0L;
   protected float field_153065_w = 0.0F;
   protected float field_153066_x = 0.0F;
   protected boolean field_176009_x = false;
   protected boolean field_176008_y = false;
   protected boolean field_176007_z = false;
   protected IStreamCallbacks field_176005_A = new IngestServerTester$1(this);
   protected IStatCallbacks field_176006_B = new IngestServerTester$2(this);

   public void func_153042_a(IngestServerTester$IngestTestListener var1) {
      this.field_153044_b = var1;
   }

   public IngestServer func_153040_c() {
      return this.field_153059_q;
   }

   public int func_153028_p() {
      return this.field_153062_t;
   }

   public boolean func_153032_e() {
      return this.field_153047_e == IngestServerTester$IngestTestState.Finished || this.field_153047_e == IngestServerTester$IngestTestState.Cancelled || this.field_153047_e == IngestServerTester$IngestTestState.Failed;
   }

   public float func_153030_h() {
      return this.field_153066_x;
   }

   public IngestServerTester(Stream var1, IngestList var2) {
      this.field_153045_c = var1;
      this.field_153046_d = var2;
   }

   public void func_176004_j() {
      if(this.field_153047_e == IngestServerTester$IngestTestState.Uninitalized) {
         this.field_153062_t = 0;
         this.field_153060_r = false;
         this.field_153061_s = false;
         this.field_176009_x = false;
         this.field_176008_y = false;
         this.field_176007_z = false;
         this.field_153058_p = this.field_153045_c.getStatCallbacks();
         this.field_153045_c.setStatCallbacks(this.field_176006_B);
         this.field_153057_o = this.field_153045_c.getStreamCallbacks();
         this.field_153045_c.setStreamCallbacks(this.field_176005_A);
         this.field_153052_j = new VideoParams();
         this.field_153052_j.targetFps = 60;
         this.field_153052_j.maxKbps = 3500;
         this.field_153052_j.outputWidth = 1280;
         this.field_153052_j.outputHeight = 720;
         this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
         this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
         this.field_153052_j.disableAdaptiveBitrate = true;
         this.field_153052_j.verticalFlip = false;
         this.field_153045_c.getDefaultParams(this.field_153052_j);
         this.audioParameters = new AudioParams();
         this.audioParameters.audioEnabled = false;
         this.audioParameters.enableMicCapture = false;
         this.audioParameters.enablePlaybackCapture = false;
         this.audioParameters.enablePassthroughAudio = false;
         this.field_153055_m = Lists.newArrayList();
         byte var1 = 3;

         for(int var2 = 0; var2 < var1; ++var2) {
            FrameBuffer var3 = this.field_153045_c.allocateFrameBuffer(this.field_153052_j.outputWidth * this.field_153052_j.outputHeight * 4);
            if(!var3.getIsValid()) {
               this.func_153031_o();
               this.func_153034_a(IngestServerTester$IngestTestState.Failed);
               return;
            }

            this.field_153055_m.add(var3);
            this.field_153045_c.randomizeFrameBuffer(var3);
         }

         this.func_153034_a(IngestServerTester$IngestTestState.Starting);
         this.field_153054_l = System.currentTimeMillis();
      }

   }

   public void func_153041_j() {
      if(!this.func_153032_e() && this.field_153047_e != IngestServerTester$IngestTestState.Uninitalized && !this.field_176008_y && !this.field_176007_z) {
         switch(IngestServerTester$3.$SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[this.field_153047_e.ordinal()]) {
         case 1:
         case 2:
            if(this.field_153059_q == null) {
               this.field_153054_l = 0L;
               this.field_153061_s = false;
               this.field_153056_n = true;
               if(this.field_153047_e != IngestServerTester$IngestTestState.Starting) {
                  ++this.field_153062_t;
               }

               if(this.field_153062_t < this.field_153046_d.getServers().length) {
                  this.field_153059_q = this.field_153046_d.getServers()[this.field_153062_t];
                  this.func_153036_a(this.field_153059_q);
               } else {
                  this.func_153034_a(IngestServerTester$IngestTestState.Finished);
               }
               break;
            }

            if(this.field_153061_s || !this.field_153056_n) {
               this.field_153059_q.bitrateKbps = 0.0F;
            }

            this.func_153035_b(this.field_153059_q);
            break;
         case 3:
         case 4:
            this.func_153029_c(this.field_153059_q);
            break;
         case 5:
            this.func_153034_a(IngestServerTester$IngestTestState.Cancelled);
         }

         this.func_153038_n();
         if(this.field_153047_e == IngestServerTester$IngestTestState.Cancelled || this.field_153047_e == IngestServerTester$IngestTestState.Finished) {
            this.func_153031_o();
         }
      }

   }

   public void func_153039_l() {
      if(!this.func_153032_e() && !this.field_153060_r) {
         this.field_153060_r = true;
         if(this.field_153059_q != null) {
            this.field_153059_q.bitrateKbps = 0.0F;
         }
      }

   }

   protected boolean func_153036_a(IngestServer var1) {
      this.field_153056_n = true;
      this.field_153050_h = 0L;
      this.field_153051_i = RTMPState.Idle;
      this.field_153059_q = var1;
      this.field_176008_y = true;
      this.func_153034_a(IngestServerTester$IngestTestState.ConnectingToServer);
      ErrorCode var2 = this.field_153045_c.start(this.field_153052_j, this.audioParameters, var1, StartFlags.TTV_Start_BandwidthTest, true);
      if(ErrorCode.failed(var2)) {
         this.field_176008_y = false;
         this.field_153056_n = false;
         this.func_153034_a(IngestServerTester$IngestTestState.DoneTestingServer);
         return false;
      } else {
         this.field_153064_v = this.field_153050_h;
         var1.bitrateKbps = 0.0F;
         this.field_153063_u = 0;
         return true;
      }
   }

   protected void func_153035_b(IngestServer var1) {
      if(this.field_176008_y) {
         this.field_153061_s = true;
      } else if(this.field_176009_x) {
         this.field_176007_z = true;
         ErrorCode var2 = this.field_153045_c.stop(true);
         if(ErrorCode.failed(var2)) {
            this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
            System.out.println("Stop failed: " + var2.toString());
         }

         this.field_153045_c.pollStats();
      } else {
         this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
      }

   }

   protected long func_153037_m() {
      return System.currentTimeMillis() - this.field_153054_l;
   }

   protected void func_153038_n() {
      float var1 = (float)this.func_153037_m();
      switch(IngestServerTester$3.$SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[this.field_153047_e.ordinal()]) {
      case 1:
      case 3:
      case 6:
      case 7:
      case 8:
      case 9:
         this.field_153066_x = 0.0F;
         break;
      case 2:
         this.field_153066_x = 1.0F;
         break;
      case 4:
      case 5:
      default:
         this.field_153066_x = var1 / (float)this.field_153048_f;
      }

      switch(IngestServerTester$3.$SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[this.field_153047_e.ordinal()]) {
      case 7:
      case 8:
      case 9:
         this.field_153065_w = 1.0F;
         break;
      default:
         this.field_153065_w = (float)this.field_153062_t / (float)this.field_153046_d.getServers().length;
         this.field_153065_w += this.field_153066_x / (float)this.field_153046_d.getServers().length;
      }

   }

   protected boolean func_153029_c(IngestServer var1) {
      if(!this.field_153061_s && !this.field_153060_r && this.func_153037_m() < this.field_153048_f) {
         if(!this.field_176008_y && !this.field_176007_z) {
            ErrorCode var2 = this.field_153045_c.submitVideoFrame((FrameBuffer)this.field_153055_m.get(this.field_153063_u));
            if(ErrorCode.failed(var2)) {
               this.field_153056_n = false;
               this.func_153034_a(IngestServerTester$IngestTestState.DoneTestingServer);
               return false;
            } else {
               this.field_153063_u = (this.field_153063_u + 1) % this.field_153055_m.size();
               this.field_153045_c.pollStats();
               if(this.field_153051_i == RTMPState.SendVideo) {
                  this.func_153034_a(IngestServerTester$IngestTestState.TestingServer);
                  long var3 = this.func_153037_m();
                  if(var3 > 0L && this.field_153050_h > this.field_153064_v) {
                     var1.bitrateKbps = (float)(this.field_153050_h * 8L) / (float)this.func_153037_m();
                     this.field_153064_v = this.field_153050_h;
                  }
               }

               return true;
            }
         } else {
            return true;
         }
      } else {
         this.func_153034_a(IngestServerTester$IngestTestState.DoneTestingServer);
         return true;
      }
   }

   protected void func_153031_o() {
      this.field_153059_q = null;
      if(this.field_153055_m != null) {
         for(FrameBuffer var2 : this.field_153055_m) {
            var2.free();
         }

         this.field_153055_m = null;
      }

      if(this.field_153045_c.getStatCallbacks() == this.field_176006_B) {
         this.field_153045_c.setStatCallbacks(this.field_153058_p);
         this.field_153058_p = null;
      }

      if(this.field_153045_c.getStreamCallbacks() == this.field_176005_A) {
         this.field_153045_c.setStreamCallbacks(this.field_153057_o);
         this.field_153057_o = null;
      }

   }

   protected void func_153034_a(IngestServerTester$IngestTestState var1) {
      if(var1 != this.field_153047_e) {
         this.field_153047_e = var1;
         if(this.field_153044_b != null) {
            this.field_153044_b.func_152907_a(this, var1);
         }
      }

   }
}
