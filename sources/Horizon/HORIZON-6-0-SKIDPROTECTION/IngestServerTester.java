package HORIZON-6-0-SKIDPROTECTION;

import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.FrameBuffer;
import com.google.common.collect.Lists;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import java.util.List;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.Stream;

public class IngestServerTester
{
    protected HorizonCode_Horizon_È HorizonCode_Horizon_È;
    protected Stream Â;
    protected IngestList Ý;
    protected Â Ø­áŒŠá;
    protected long Âµá€;
    protected long Ó;
    protected long à;
    protected RTMPState Ø;
    protected VideoParams áŒŠÆ;
    protected AudioParams áˆºÑ¢Õ;
    protected long ÂµÈ;
    protected List á;
    protected boolean ˆÏ­;
    protected IStreamCallbacks £á;
    protected IStatCallbacks Å;
    protected IngestServer £à;
    protected boolean µà;
    protected boolean ˆà;
    protected int ¥Æ;
    protected int Ø­à;
    protected long µÕ;
    protected float Æ;
    protected float Šáƒ;
    protected boolean Ï­Ðƒà;
    protected boolean áŒŠà;
    protected boolean ŠÄ;
    protected IStreamCallbacks Ñ¢á;
    protected IStatCallbacks ŒÏ;
    private static final String Çªà¢ = "CL_00001816";
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_153042_1_) {
        this.HorizonCode_Horizon_È = p_153042_1_;
    }
    
    public IngestServer HorizonCode_Horizon_È() {
        return this.£à;
    }
    
    public int Â() {
        return this.¥Æ;
    }
    
    public boolean Ý() {
        return this.Ø­áŒŠá == IngestServerTester.Â.Ó || this.Ø­áŒŠá == IngestServerTester.Â.Ø || this.Ø­áŒŠá == IngestServerTester.Â.áŒŠÆ;
    }
    
    public float Ø­áŒŠá() {
        return this.Šáƒ;
    }
    
    public IngestServerTester(final Stream p_i1019_1_, final IngestList p_i1019_2_) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = null;
        this.Ø­áŒŠá = IngestServerTester.Â.HorizonCode_Horizon_È;
        this.Âµá€ = 8000L;
        this.Ó = 2000L;
        this.à = 0L;
        this.Ø = RTMPState.Invalid;
        this.áŒŠÆ = null;
        this.áˆºÑ¢Õ = null;
        this.ÂµÈ = 0L;
        this.á = null;
        this.ˆÏ­ = false;
        this.£á = null;
        this.Å = null;
        this.£à = null;
        this.µà = false;
        this.ˆà = false;
        this.¥Æ = -1;
        this.Ø­à = 0;
        this.µÕ = 0L;
        this.Æ = 0.0f;
        this.Šáƒ = 0.0f;
        this.Ï­Ðƒà = false;
        this.áŒŠà = false;
        this.ŠÄ = false;
        this.Ñ¢á = (IStreamCallbacks)new IStreamCallbacks() {
            private static final String Â = "CL_00002368";
            
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
                IngestServerTester.this.áŒŠà = false;
                if (ErrorCode.succeeded(p_startCallback_1_)) {
                    IngestServerTester.this.Ï­Ðƒà = true;
                    IngestServerTester.this.ÂµÈ = System.currentTimeMillis();
                    IngestServerTester.this.HorizonCode_Horizon_È(IngestServerTester.Â.Ý);
                }
                else {
                    IngestServerTester.this.ˆÏ­ = false;
                    IngestServerTester.this.HorizonCode_Horizon_È(IngestServerTester.Â.Âµá€);
                }
            }
            
            public void stopCallback(final ErrorCode p_stopCallback_1_) {
                if (ErrorCode.failed(p_stopCallback_1_)) {
                    System.out.println("IngestTester.stopCallback failed to stop - " + IngestServerTester.this.£à.serverName + ": " + p_stopCallback_1_.toString());
                }
                IngestServerTester.this.ŠÄ = false;
                IngestServerTester.this.Ï­Ðƒà = false;
                IngestServerTester.this.HorizonCode_Horizon_È(IngestServerTester.Â.Âµá€);
                IngestServerTester.this.£à = null;
                if (IngestServerTester.this.µà) {
                    IngestServerTester.this.HorizonCode_Horizon_È(IngestServerTester.Â.à);
                }
            }
            
            public void sendActionMetaDataCallback(final ErrorCode p_sendActionMetaDataCallback_1_) {
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode p_sendStartSpanMetaDataCallback_1_) {
            }
            
            public void sendEndSpanMetaDataCallback(final ErrorCode p_sendEndSpanMetaDataCallback_1_) {
            }
        };
        this.ŒÏ = (IStatCallbacks)new IStatCallbacks() {
            private static final String Â = "CL_00002367";
            
            public void statCallback(final StatType p_statCallback_1_, final long p_statCallback_2_) {
                switch (IngestServerTester.Ý.HorizonCode_Horizon_È[p_statCallback_1_.ordinal()]) {
                    case 1: {
                        IngestServerTester.this.Ø = RTMPState.lookupValue((int)p_statCallback_2_);
                        break;
                    }
                    case 2: {
                        IngestServerTester.this.à = p_statCallback_2_;
                        break;
                    }
                }
            }
        };
        this.Â = p_i1019_1_;
        this.Ý = p_i1019_2_;
    }
    
    public void Âµá€() {
        if (this.Ø­áŒŠá == IngestServerTester.Â.HorizonCode_Horizon_È) {
            this.¥Æ = 0;
            this.µà = false;
            this.ˆà = false;
            this.Ï­Ðƒà = false;
            this.áŒŠà = false;
            this.ŠÄ = false;
            this.Å = this.Â.getStatCallbacks();
            this.Â.setStatCallbacks(this.ŒÏ);
            this.£á = this.Â.getStreamCallbacks();
            this.Â.setStreamCallbacks(this.Ñ¢á);
            this.áŒŠÆ = new VideoParams();
            this.áŒŠÆ.targetFps = 60;
            this.áŒŠÆ.maxKbps = 3500;
            this.áŒŠÆ.outputWidth = 1280;
            this.áŒŠÆ.outputHeight = 720;
            this.áŒŠÆ.pixelFormat = PixelFormat.TTV_PF_BGRA;
            this.áŒŠÆ.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
            this.áŒŠÆ.disableAdaptiveBitrate = true;
            this.áŒŠÆ.verticalFlip = false;
            this.Â.getDefaultParams(this.áŒŠÆ);
            this.áˆºÑ¢Õ = new AudioParams();
            this.áˆºÑ¢Õ.audioEnabled = false;
            this.áˆºÑ¢Õ.enableMicCapture = false;
            this.áˆºÑ¢Õ.enablePlaybackCapture = false;
            this.áˆºÑ¢Õ.enablePassthroughAudio = false;
            this.á = Lists.newArrayList();
            final byte var1 = 3;
            for (int var2 = 0; var2 < var1; ++var2) {
                final FrameBuffer var3 = this.Â.allocateFrameBuffer(this.áŒŠÆ.outputWidth * this.áŒŠÆ.outputHeight * 4);
                if (!var3.getIsValid()) {
                    this.áˆºÑ¢Õ();
                    this.HorizonCode_Horizon_È(IngestServerTester.Â.áŒŠÆ);
                    return;
                }
                this.á.add(var3);
                this.Â.randomizeFrameBuffer(var3);
            }
            this.HorizonCode_Horizon_È(IngestServerTester.Â.Â);
            this.ÂµÈ = System.currentTimeMillis();
        }
    }
    
    public void Ó() {
        if (!this.Ý() && this.Ø­áŒŠá != IngestServerTester.Â.HorizonCode_Horizon_È && !this.áŒŠà && !this.ŠÄ) {
            switch (IngestServerTester.Ý.Â[this.Ø­áŒŠá.ordinal()]) {
                case 1:
                case 2: {
                    if (this.£à != null) {
                        if (this.ˆà || !this.ˆÏ­) {
                            this.£à.bitrateKbps = 0.0f;
                        }
                        this.Â(this.£à);
                        break;
                    }
                    this.ÂµÈ = 0L;
                    this.ˆà = false;
                    this.ˆÏ­ = true;
                    if (this.Ø­áŒŠá != IngestServerTester.Â.Â) {
                        ++this.¥Æ;
                    }
                    if (this.¥Æ < this.Ý.getServers().length) {
                        this.HorizonCode_Horizon_È(this.£à = this.Ý.getServers()[this.¥Æ]);
                        break;
                    }
                    this.HorizonCode_Horizon_È(IngestServerTester.Â.Ó);
                    break;
                }
                case 3:
                case 4: {
                    this.Ý(this.£à);
                    break;
                }
                case 5: {
                    this.HorizonCode_Horizon_È(IngestServerTester.Â.Ø);
                    break;
                }
            }
            this.áŒŠÆ();
            if (this.Ø­áŒŠá == IngestServerTester.Â.Ø || this.Ø­áŒŠá == IngestServerTester.Â.Ó) {
                this.áˆºÑ¢Õ();
            }
        }
    }
    
    public void à() {
        if (!this.Ý() && !this.µà) {
            this.µà = true;
            if (this.£à != null) {
                this.£à.bitrateKbps = 0.0f;
            }
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final IngestServer p_153036_1_) {
        this.ˆÏ­ = true;
        this.à = 0L;
        this.Ø = RTMPState.Idle;
        this.£à = p_153036_1_;
        this.áŒŠà = true;
        this.HorizonCode_Horizon_È(IngestServerTester.Â.Ý);
        final ErrorCode var2 = this.Â.start(this.áŒŠÆ, this.áˆºÑ¢Õ, p_153036_1_, StartFlags.TTV_Start_BandwidthTest, true);
        if (ErrorCode.failed(var2)) {
            this.áŒŠà = false;
            this.ˆÏ­ = false;
            this.HorizonCode_Horizon_È(IngestServerTester.Â.Âµá€);
            return false;
        }
        this.µÕ = this.à;
        p_153036_1_.bitrateKbps = 0.0f;
        this.Ø­à = 0;
        return true;
    }
    
    protected void Â(final IngestServer p_153035_1_) {
        if (this.áŒŠà) {
            this.ˆà = true;
        }
        else if (this.Ï­Ðƒà) {
            this.ŠÄ = true;
            final ErrorCode var2 = this.Â.stop(true);
            if (ErrorCode.failed(var2)) {
                this.Ñ¢á.stopCallback(ErrorCode.TTV_EC_SUCCESS);
                System.out.println("Stop failed: " + var2.toString());
            }
            this.Â.pollStats();
        }
        else {
            this.Ñ¢á.stopCallback(ErrorCode.TTV_EC_SUCCESS);
        }
    }
    
    protected long Ø() {
        return System.currentTimeMillis() - this.ÂµÈ;
    }
    
    protected void áŒŠÆ() {
        final float var1 = this.Ø();
        switch (IngestServerTester.Ý.Â[this.Ø­áŒŠá.ordinal()]) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9: {
                this.Šáƒ = 0.0f;
                break;
            }
            case 2: {
                this.Šáƒ = 1.0f;
                break;
            }
            default: {
                this.Šáƒ = var1 / this.Âµá€;
                break;
            }
        }
        switch (IngestServerTester.Ý.Â[this.Ø­áŒŠá.ordinal()]) {
            case 7:
            case 8:
            case 9: {
                this.Æ = 1.0f;
                break;
            }
            default: {
                this.Æ = this.¥Æ / this.Ý.getServers().length;
                this.Æ += this.Šáƒ / this.Ý.getServers().length;
                break;
            }
        }
    }
    
    protected boolean Ý(final IngestServer p_153029_1_) {
        if (this.ˆà || this.µà || this.Ø() >= this.Âµá€) {
            this.HorizonCode_Horizon_È(IngestServerTester.Â.Âµá€);
            return true;
        }
        if (this.áŒŠà || this.ŠÄ) {
            return true;
        }
        final ErrorCode var2 = this.Â.submitVideoFrame((FrameBuffer)this.á.get(this.Ø­à));
        if (ErrorCode.failed(var2)) {
            this.ˆÏ­ = false;
            this.HorizonCode_Horizon_È(IngestServerTester.Â.Âµá€);
            return false;
        }
        this.Ø­à = (this.Ø­à + 1) % this.á.size();
        this.Â.pollStats();
        if (this.Ø == RTMPState.SendVideo) {
            this.HorizonCode_Horizon_È(IngestServerTester.Â.Ø­áŒŠá);
            final long var3 = this.Ø();
            if (var3 > 0L && this.à > this.µÕ) {
                p_153029_1_.bitrateKbps = this.à * 8L / this.Ø();
                this.µÕ = this.à;
            }
        }
        return true;
    }
    
    protected void áˆºÑ¢Õ() {
        this.£à = null;
        if (this.á != null) {
            for (int var1 = 0; var1 < this.á.size(); ++var1) {
                this.á.get(var1).free();
            }
            this.á = null;
        }
        if (this.Â.getStatCallbacks() == this.ŒÏ) {
            this.Â.setStatCallbacks(this.Å);
            this.Å = null;
        }
        if (this.Â.getStreamCallbacks() == this.Ñ¢á) {
            this.Â.setStreamCallbacks(this.£á);
            this.£á = null;
        }
    }
    
    protected void HorizonCode_Horizon_È(final Â p_153034_1_) {
        if (p_153034_1_ != this.Ø­áŒŠá) {
            this.Ø­áŒŠá = p_153034_1_;
            if (this.HorizonCode_Horizon_È != null) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this, p_153034_1_);
            }
        }
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("Uninitalized", 0, "Uninitalized", 0), 
        Â("Starting", 1, "Starting", 1), 
        Ý("ConnectingToServer", 2, "ConnectingToServer", 2), 
        Ø­áŒŠá("TestingServer", 3, "TestingServer", 3), 
        Âµá€("DoneTestingServer", 4, "DoneTestingServer", 4), 
        Ó("Finished", 5, "Finished", 5), 
        à("Cancelling", 6, "Cancelling", 6), 
        Ø("Cancelled", 7, "Cancelled", 7), 
        áŒŠÆ("Failed", 8, "Failed", 8);
        
        private static final Â[] áˆºÑ¢Õ;
        private static final String ÂµÈ = "CL_00001814";
        
        static {
            á = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ };
            áˆºÑ¢Õ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ };
        }
        
        private Â(final String s, final int n, final String p_i1016_1_, final int p_i1016_2_) {
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00001815";
        
        static {
            Â = new int[IngestServerTester.Â.values().length];
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Âµá€.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.à.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.HorizonCode_Horizon_È.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Ó.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.Ø.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                IngestServerTester.Ý.Â[IngestServerTester.Â.áŒŠÆ.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            HorizonCode_Horizon_È = new int[StatType.values().length];
            try {
                IngestServerTester.Ý.HorizonCode_Horizon_È[StatType.TTV_ST_RTMPSTATE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                IngestServerTester.Ý.HorizonCode_Horizon_È[StatType.TTV_ST_RTMPDATASENT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
        }
    }
    
    public interface HorizonCode_Horizon_È
    {
        void HorizonCode_Horizon_È(final IngestServerTester p0, final Â p1);
    }
}
