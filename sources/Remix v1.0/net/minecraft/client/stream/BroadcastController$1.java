package net.minecraft.client.stream;

import tv.twitch.*;
import tv.twitch.broadcast.*;

class BroadcastController$1 implements IStreamCallbacks {
    public void requestAuthTokenCallback(final ErrorCode p_requestAuthTokenCallback_1_, final AuthToken p_requestAuthTokenCallback_2_) {
        if (ErrorCode.succeeded(p_requestAuthTokenCallback_1_)) {
            BroadcastController.this.field_152885_u = p_requestAuthTokenCallback_2_;
            BroadcastController.this.func_152827_a(BroadcastState.Authenticated);
        }
        else {
            BroadcastController.this.field_152885_u.data = "";
            BroadcastController.this.func_152827_a(BroadcastState.Initialized);
            final String var3 = ErrorCode.getString(p_requestAuthTokenCallback_1_);
            BroadcastController.this.func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", var3));
        }
        try {
            if (BroadcastController.this.field_152867_c != null) {
                BroadcastController.this.field_152867_c.func_152900_a(p_requestAuthTokenCallback_1_, p_requestAuthTokenCallback_2_);
            }
        }
        catch (Exception var4) {
            BroadcastController.this.func_152820_d(var4.toString());
        }
    }
    
    public void loginCallback(final ErrorCode p_loginCallback_1_, final ChannelInfo p_loginCallback_2_) {
        if (ErrorCode.succeeded(p_loginCallback_1_)) {
            BroadcastController.this.channelInfo = p_loginCallback_2_;
            BroadcastController.this.func_152827_a(BroadcastState.LoggedIn);
            BroadcastController.this.field_152877_m = true;
        }
        else {
            BroadcastController.this.func_152827_a(BroadcastState.Initialized);
            BroadcastController.this.field_152877_m = false;
            final String var3 = ErrorCode.getString(p_loginCallback_1_);
            BroadcastController.this.func_152820_d(String.format("LoginCallback got failure: %s", var3));
        }
        try {
            if (BroadcastController.this.field_152867_c != null) {
                BroadcastController.this.field_152867_c.func_152897_a(p_loginCallback_1_);
            }
        }
        catch (Exception var4) {
            BroadcastController.this.func_152820_d(var4.toString());
        }
    }
    
    public void getIngestServersCallback(final ErrorCode p_getIngestServersCallback_1_, final IngestList p_getIngestServersCallback_2_) {
        if (ErrorCode.succeeded(p_getIngestServersCallback_1_)) {
            BroadcastController.this.field_152883_s = p_getIngestServersCallback_2_;
            BroadcastController.this.field_152884_t = BroadcastController.this.field_152883_s.getDefaultServer();
            BroadcastController.this.func_152827_a(BroadcastState.ReceivedIngestServers);
            try {
                if (BroadcastController.this.field_152867_c != null) {
                    BroadcastController.this.field_152867_c.func_152896_a(p_getIngestServersCallback_2_);
                }
            }
            catch (Exception var4) {
                BroadcastController.this.func_152820_d(var4.toString());
            }
        }
        else {
            final String var5 = ErrorCode.getString(p_getIngestServersCallback_1_);
            BroadcastController.this.func_152820_d(String.format("IngestListCallback got failure: %s", var5));
            BroadcastController.this.func_152827_a(BroadcastState.LoggingIn);
        }
    }
    
    public void getUserInfoCallback(final ErrorCode p_getUserInfoCallback_1_, final UserInfo p_getUserInfoCallback_2_) {
        BroadcastController.this.field_152887_w = p_getUserInfoCallback_2_;
        if (ErrorCode.failed(p_getUserInfoCallback_1_)) {
            final String var3 = ErrorCode.getString(p_getUserInfoCallback_1_);
            BroadcastController.this.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", var3));
        }
    }
    
    public void getStreamInfoCallback(final ErrorCode p_getStreamInfoCallback_1_, final StreamInfo p_getStreamInfoCallback_2_) {
        if (ErrorCode.succeeded(p_getStreamInfoCallback_1_)) {
            BroadcastController.this.field_152888_x = p_getStreamInfoCallback_2_;
            try {
                if (BroadcastController.this.field_152867_c != null) {
                    BroadcastController.this.field_152867_c.func_152894_a(p_getStreamInfoCallback_2_);
                }
            }
            catch (Exception var4) {
                BroadcastController.this.func_152820_d(var4.toString());
            }
        }
        else {
            final String var5 = ErrorCode.getString(p_getStreamInfoCallback_1_);
            BroadcastController.this.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", var5));
        }
    }
    
    public void getArchivingStateCallback(final ErrorCode p_getArchivingStateCallback_1_, final ArchivingState p_getArchivingStateCallback_2_) {
        BroadcastController.this.field_152889_y = p_getArchivingStateCallback_2_;
        if (ErrorCode.failed(p_getArchivingStateCallback_1_)) {}
    }
    
    public void runCommercialCallback(final ErrorCode p_runCommercialCallback_1_) {
        if (ErrorCode.failed(p_runCommercialCallback_1_)) {
            final String var2 = ErrorCode.getString(p_runCommercialCallback_1_);
            BroadcastController.this.func_152832_e(String.format("RunCommercialCallback got failure: %s", var2));
        }
    }
    
    public void setStreamInfoCallback(final ErrorCode p_setStreamInfoCallback_1_) {
        if (ErrorCode.failed(p_setStreamInfoCallback_1_)) {
            final String var2 = ErrorCode.getString(p_setStreamInfoCallback_1_);
            BroadcastController.this.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", var2));
        }
    }
    
    public void getGameNameListCallback(final ErrorCode p_getGameNameListCallback_1_, final GameInfoList p_getGameNameListCallback_2_) {
        if (ErrorCode.failed(p_getGameNameListCallback_1_)) {
            final String var3 = ErrorCode.getString(p_getGameNameListCallback_1_);
            BroadcastController.this.func_152820_d(String.format("GameNameListCallback got failure: %s", var3));
        }
        try {
            if (BroadcastController.this.field_152867_c != null) {
                BroadcastController.this.field_152867_c.func_152898_a(p_getGameNameListCallback_1_, (p_getGameNameListCallback_2_ == null) ? new GameInfo[0] : p_getGameNameListCallback_2_.list);
            }
        }
        catch (Exception var4) {
            BroadcastController.this.func_152820_d(var4.toString());
        }
    }
    
    public void bufferUnlockCallback(final long p_bufferUnlockCallback_1_) {
        final FrameBuffer var3 = FrameBuffer.lookupBuffer(p_bufferUnlockCallback_1_);
        BroadcastController.this.field_152875_k.add(var3);
    }
    
    public void startCallback(final ErrorCode p_startCallback_1_) {
        if (ErrorCode.succeeded(p_startCallback_1_)) {
            try {
                if (BroadcastController.this.field_152867_c != null) {
                    BroadcastController.this.field_152867_c.func_152899_b();
                }
            }
            catch (Exception var4) {
                BroadcastController.this.func_152820_d(var4.toString());
            }
            BroadcastController.this.func_152827_a(BroadcastState.Broadcasting);
        }
        else {
            BroadcastController.this.field_152881_q = null;
            BroadcastController.this.field_152882_r = null;
            BroadcastController.this.func_152827_a(BroadcastState.ReadyToBroadcast);
            try {
                if (BroadcastController.this.field_152867_c != null) {
                    BroadcastController.this.field_152867_c.func_152892_c(p_startCallback_1_);
                }
            }
            catch (Exception var5) {
                BroadcastController.this.func_152820_d(var5.toString());
            }
            final String var6 = ErrorCode.getString(p_startCallback_1_);
            BroadcastController.this.func_152820_d(String.format("startCallback got failure: %s", var6));
        }
    }
    
    public void stopCallback(final ErrorCode p_stopCallback_1_) {
        if (ErrorCode.succeeded(p_stopCallback_1_)) {
            BroadcastController.this.field_152881_q = null;
            BroadcastController.this.field_152882_r = null;
            BroadcastController.this.func_152831_M();
            try {
                if (BroadcastController.this.field_152867_c != null) {
                    BroadcastController.this.field_152867_c.func_152901_c();
                }
            }
            catch (Exception var3) {
                BroadcastController.this.func_152820_d(var3.toString());
            }
            if (BroadcastController.this.field_152877_m) {
                BroadcastController.this.func_152827_a(BroadcastState.ReadyToBroadcast);
            }
            else {
                BroadcastController.this.func_152827_a(BroadcastState.Initialized);
            }
        }
        else {
            BroadcastController.this.func_152827_a(BroadcastState.ReadyToBroadcast);
            final String var4 = ErrorCode.getString(p_stopCallback_1_);
            BroadcastController.this.func_152820_d(String.format("stopCallback got failure: %s", var4));
        }
    }
    
    public void sendActionMetaDataCallback(final ErrorCode p_sendActionMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendActionMetaDataCallback_1_)) {
            final String var2 = ErrorCode.getString(p_sendActionMetaDataCallback_1_);
            BroadcastController.this.func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", var2));
        }
    }
    
    public void sendStartSpanMetaDataCallback(final ErrorCode p_sendStartSpanMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendStartSpanMetaDataCallback_1_)) {
            final String var2 = ErrorCode.getString(p_sendStartSpanMetaDataCallback_1_);
            BroadcastController.this.func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", var2));
        }
    }
    
    public void sendEndSpanMetaDataCallback(final ErrorCode p_sendEndSpanMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendEndSpanMetaDataCallback_1_)) {
            final String var2 = ErrorCode.getString(p_sendEndSpanMetaDataCallback_1_);
            BroadcastController.this.func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", var2));
        }
    }
}