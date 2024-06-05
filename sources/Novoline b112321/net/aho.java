package net;

import net.minecraft.client.stream.BroadcastController;
import net.minecraft.client.stream.BroadcastController$BroadcastState;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;

class aho implements IStreamCallbacks {
   final BroadcastController a;

   aho(BroadcastController var1) {
      this.a = var1;
   }

   public void requestAuthTokenCallback(ErrorCode var1, AuthToken var2) {
      if(ErrorCode.succeeded(var1)) {
         this.a.authenticationToken = var2;
         this.a.func_152827_a(BroadcastController$BroadcastState.Authenticated);
      } else {
         this.a.authenticationToken.data = "";
         this.a.func_152827_a(BroadcastController$BroadcastState.Initialized);
         String var3 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", new Object[]{var3}));
      }

      try {
         if(this.a.broadcastListener != null) {
            this.a.broadcastListener.func_152900_a(var1, var2);
         }
      } catch (Exception var4) {
         this.a.func_152820_d(var4.toString());
      }

   }

   public void loginCallback(ErrorCode var1, ChannelInfo var2) {
      if(ErrorCode.succeeded(var1)) {
         this.a.channelInfo = var2;
         this.a.func_152827_a(BroadcastController$BroadcastState.LoggedIn);
         this.a.field_152877_m = true;
      } else {
         this.a.func_152827_a(BroadcastController$BroadcastState.Initialized);
         this.a.field_152877_m = false;
         String var3 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("LoginCallback got failure: %s", new Object[]{var3}));
      }

      try {
         if(this.a.broadcastListener != null) {
            this.a.broadcastListener.func_152897_a(var1);
         }
      } catch (Exception var4) {
         this.a.func_152820_d(var4.toString());
      }

   }

   public void getIngestServersCallback(ErrorCode var1, IngestList var2) {
      if(ErrorCode.succeeded(var1)) {
         this.a.ingestList = var2;
         this.a.field_152884_t = this.a.ingestList.getDefaultServer();
         this.a.func_152827_a(BroadcastController$BroadcastState.ReceivedIngestServers);

         try {
            if(this.a.broadcastListener != null) {
               this.a.broadcastListener.func_152896_a(var2);
            }
         } catch (Exception var4) {
            this.a.func_152820_d(var4.toString());
         }
      } else {
         String var3 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("IngestListCallback got failure: %s", new Object[]{var3}));
         this.a.func_152827_a(BroadcastController$BroadcastState.LoggingIn);
      }

   }

   public void getUserInfoCallback(ErrorCode var1, UserInfo var2) {
      this.a.userInfo = var2;
      if(ErrorCode.failed(var1)) {
         String var3 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", new Object[]{var3}));
      }

   }

   public void getStreamInfoCallback(ErrorCode var1, StreamInfo var2) {
      if(ErrorCode.succeeded(var1)) {
         this.a.streamInfo = var2;

         try {
            if(this.a.broadcastListener != null) {
               this.a.broadcastListener.func_152894_a(var2);
            }
         } catch (Exception var4) {
            this.a.func_152820_d(var4.toString());
         }
      } else {
         String var3 = ErrorCode.getString(var1);
         this.a.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", new Object[]{var3}));
      }

   }

   public void getArchivingStateCallback(ErrorCode var1, ArchivingState var2) {
      this.a.field_152889_y = var2;
      if(ErrorCode.failed(var1)) {
         ;
      }

   }

   public void runCommercialCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         String var2 = ErrorCode.getString(var1);
         this.a.func_152832_e(String.format("RunCommercialCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void setStreamInfoCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         String var2 = ErrorCode.getString(var1);
         this.a.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void getGameNameListCallback(ErrorCode var1, GameInfoList var2) {
      if(ErrorCode.failed(var1)) {
         String var3 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("GameNameListCallback got failure: %s", new Object[]{var3}));
      }

      try {
         if(this.a.broadcastListener != null) {
            this.a.broadcastListener.func_152898_a(var1, new GameInfo[0]);
         }
      } catch (Exception var4) {
         this.a.func_152820_d(var4.toString());
      }

   }

   public void bufferUnlockCallback(long var1) {
      FrameBuffer var3 = FrameBuffer.lookupBuffer(var1);
      this.a.field_152875_k.add(var3);
   }

   public void startCallback(ErrorCode var1) {
      if(ErrorCode.succeeded(var1)) {
         try {
            if(this.a.broadcastListener != null) {
               this.a.broadcastListener.func_152899_b();
            }
         } catch (Exception var4) {
            this.a.func_152820_d(var4.toString());
         }

         this.a.func_152827_a(BroadcastController$BroadcastState.Broadcasting);
      } else {
         this.a.videoParamaters = null;
         this.a.audioParamaters = null;
         this.a.func_152827_a(BroadcastController$BroadcastState.ReadyToBroadcast);

         try {
            if(this.a.broadcastListener != null) {
               this.a.broadcastListener.func_152892_c(var1);
            }
         } catch (Exception var3) {
            this.a.func_152820_d(var3.toString());
         }

         String var2 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("startCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void stopCallback(ErrorCode var1) {
      if(ErrorCode.succeeded(var1)) {
         this.a.videoParamaters = null;
         this.a.audioParamaters = null;
         this.a.func_152831_M();

         try {
            if(this.a.broadcastListener != null) {
               this.a.broadcastListener.func_152901_c();
            }
         } catch (Exception var3) {
            this.a.func_152820_d(var3.toString());
         }

         if(this.a.field_152877_m) {
            this.a.func_152827_a(BroadcastController$BroadcastState.ReadyToBroadcast);
         } else {
            this.a.func_152827_a(BroadcastController$BroadcastState.Initialized);
         }
      } else {
         this.a.func_152827_a(BroadcastController$BroadcastState.ReadyToBroadcast);
         String var2 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("stopCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void sendActionMetaDataCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         String var2 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void sendStartSpanMetaDataCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         String var2 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", new Object[]{var2}));
      }

   }

   public void sendEndSpanMetaDataCallback(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         String var2 = ErrorCode.getString(var1);
         this.a.func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", new Object[]{var2}));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
