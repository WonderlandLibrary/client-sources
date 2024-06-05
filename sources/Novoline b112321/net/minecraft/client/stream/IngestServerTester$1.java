package net.minecraft.client.stream;

import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.client.stream.IngestServerTester$IngestTestState;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;

class IngestServerTester$1 implements IStreamCallbacks {
   final IngestServerTester this$0;

   IngestServerTester$1(IngestServerTester var1) {
      this.this$0 = var1;
   }

   public void requestAuthTokenCallback(ErrorCode var1, AuthToken var2) {
   }

   public void loginCallback(ErrorCode var1, ChannelInfo var2) {
   }

   public void getIngestServersCallback(ErrorCode var1, IngestList var2) {
   }

   public void getUserInfoCallback(ErrorCode var1, UserInfo var2) {
   }

   public void getStreamInfoCallback(ErrorCode var1, StreamInfo var2) {
   }

   public void getArchivingStateCallback(ErrorCode var1, ArchivingState var2) {
   }

   public void runCommercialCallback(ErrorCode var1) {
   }

   public void setStreamInfoCallback(ErrorCode var1) {
   }

   public void getGameNameListCallback(ErrorCode var1, GameInfoList var2) {
   }

   public void bufferUnlockCallback(long var1) {
   }

   public void startCallback(ErrorCode var1) {
      this.this$0.field_176008_y = false;
      if(ErrorCode.succeeded(var1)) {
         this.this$0.field_176009_x = true;
         this.this$0.field_153054_l = System.currentTimeMillis();
         this.this$0.func_153034_a(IngestServerTester$IngestTestState.ConnectingToServer);
      } else {
         this.this$0.field_153056_n = false;
         this.this$0.func_153034_a(IngestServerTester$IngestTestState.DoneTestingServer);
      }

   }

   public void stopCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         System.out.println("IngestTester.stopCallback failed to stop - " + this.this$0.field_153059_q.serverName + ": " + var1.toString());
      }

      this.this$0.field_176007_z = false;
      this.this$0.field_176009_x = false;
      this.this$0.func_153034_a(IngestServerTester$IngestTestState.DoneTestingServer);
      this.this$0.field_153059_q = null;
      if(this.this$0.field_153060_r) {
         this.this$0.func_153034_a(IngestServerTester$IngestTestState.Cancelling);
      }

   }

   public void sendActionMetaDataCallback(ErrorCode var1) {
   }

   public void sendStartSpanMetaDataCallback(ErrorCode var1) {
   }

   public void sendEndSpanMetaDataCallback(ErrorCode var1) {
   }
}
