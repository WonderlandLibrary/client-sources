package net.minecraft.client.stream;

import tv.twitch.*;
import tv.twitch.broadcast.*;

class IngestServerTester$1 implements IStreamCallbacks {
    public void requestAuthTokenCallback(final ErrorCode p_requestAuthTokenCallback_1_, final AuthToken p_requestAuthTokenCallback_2_) {
    }
    
    public void loginCallback(final ErrorCode p_loginCallback_1_, final ChannelInfo p_loginCallback_2_) {
    }
    
    public void getIngestServersCallback(final ErrorCode p_getIngestServersCallback_1_, final IngestList p_getIngestServersCallback_2_) {
    }
    
    public void getUserInfoCallback(final ErrorCode p_getUserInfoCallback_1_, final UserInfo p_getUserInfoCallback_2_) {
    }
    
    public void getStreamInfoCallback(final ErrorCode p_getStreamInfoCallback_1_, final StreamInfo p_getStreamInfoCallback_2_) {
    }
    
    public void getArchivingStateCallback(final ErrorCode p_getArchivingStateCallback_1_, final ArchivingState p_getArchivingStateCallback_2_) {
    }
    
    public void runCommercialCallback(final ErrorCode p_runCommercialCallback_1_) {
    }
    
    public void setStreamInfoCallback(final ErrorCode p_setStreamInfoCallback_1_) {
    }
    
    public void getGameNameListCallback(final ErrorCode p_getGameNameListCallback_1_, final GameInfoList p_getGameNameListCallback_2_) {
    }
    
    public void bufferUnlockCallback(final long p_bufferUnlockCallback_1_) {
    }
    
    public void startCallback(final ErrorCode p_startCallback_1_) {
        IngestServerTester.this.field_176008_y = false;
        if (ErrorCode.succeeded(p_startCallback_1_)) {
            IngestServerTester.this.field_176009_x = true;
            IngestServerTester.this.field_153054_l = System.currentTimeMillis();
            IngestServerTester.this.func_153034_a(IngestTestState.ConnectingToServer);
        }
        else {
            IngestServerTester.this.field_153056_n = false;
            IngestServerTester.this.func_153034_a(IngestTestState.DoneTestingServer);
        }
    }
    
    public void stopCallback(final ErrorCode p_stopCallback_1_) {
        if (ErrorCode.failed(p_stopCallback_1_)) {
            System.out.println("IngestTester.stopCallback failed to stop - " + IngestServerTester.this.field_153059_q.serverName + ": " + p_stopCallback_1_.toString());
        }
        IngestServerTester.this.field_176007_z = false;
        IngestServerTester.this.field_176009_x = false;
        IngestServerTester.this.func_153034_a(IngestTestState.DoneTestingServer);
        IngestServerTester.this.field_153059_q = null;
        if (IngestServerTester.this.field_153060_r) {
            IngestServerTester.this.func_153034_a(IngestTestState.Cancelling);
        }
    }
    
    public void sendActionMetaDataCallback(final ErrorCode p_sendActionMetaDataCallback_1_) {
    }
    
    public void sendStartSpanMetaDataCallback(final ErrorCode p_sendStartSpanMetaDataCallback_1_) {
    }
    
    public void sendEndSpanMetaDataCallback(final ErrorCode p_sendEndSpanMetaDataCallback_1_) {
    }
}