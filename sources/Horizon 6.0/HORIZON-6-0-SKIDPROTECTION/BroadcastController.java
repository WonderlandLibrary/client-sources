package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.StreamInfoForSetting;
import tv.twitch.MessageLevel;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StreamAPI;
import tv.twitch.broadcast.DesktopStreamAPI;
import tv.twitch.CoreAPI;
import tv.twitch.StandardCoreAPI;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.GameInfoList;
import com.google.common.collect.Lists;
import tv.twitch.broadcast.AudioDeviceType;
import org.apache.logging.log4j.LogManager;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.AuthToken;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.VideoParams;
import java.util.List;
import tv.twitch.broadcast.Stream;
import tv.twitch.Core;
import org.apache.logging.log4j.Logger;

public class BroadcastController
{
    private static final Logger Ê;
    protected final int HorizonCode_Horizon_È = 30;
    protected final int Â = 3;
    private static final ThreadSafeBoundList ÇŽÉ;
    private String ˆá;
    protected HorizonCode_Horizon_È Ý;
    protected String Ø­áŒŠá;
    protected String Âµá€;
    protected String Ó;
    protected boolean à;
    protected Core Ø;
    protected Stream áŒŠÆ;
    protected List áˆºÑ¢Õ;
    protected List ÂµÈ;
    protected boolean á;
    protected boolean ˆÏ­;
    protected boolean £á;
    protected Â Å;
    protected String £à;
    protected VideoParams µà;
    protected AudioParams ˆà;
    protected IngestList ¥Æ;
    protected IngestServer Ø­à;
    protected AuthToken µÕ;
    protected ChannelInfo Æ;
    protected UserInfo Šáƒ;
    protected StreamInfo Ï­Ðƒà;
    protected ArchivingState áŒŠà;
    protected long ŠÄ;
    protected IngestServerTester Ñ¢á;
    private ErrorCode ÇŽÕ;
    protected IStreamCallbacks ŒÏ;
    protected IStatCallbacks Çªà¢;
    private static final String É = "CL_00001822";
    
    static {
        Ê = LogManager.getLogger();
        ÇŽÉ = new ThreadSafeBoundList(String.class, 50);
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_152841_1_) {
        this.Ý = p_152841_1_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final String p_152842_1_) {
        this.Ø­áŒŠá = p_152842_1_;
    }
    
    public StreamInfo Â() {
        return this.Ï­Ðƒà;
    }
    
    public ChannelInfo Ý() {
        return this.Æ;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Å == BroadcastController.Â.ÂµÈ || this.Å == BroadcastController.Â.ˆÏ­;
    }
    
    public boolean Âµá€() {
        return this.Å == BroadcastController.Â.áŒŠÆ;
    }
    
    public boolean Ó() {
        return this.Å == BroadcastController.Â.£á;
    }
    
    public boolean à() {
        return this.Å == BroadcastController.Â.ˆÏ­;
    }
    
    public boolean Ø() {
        return this.ˆÏ­;
    }
    
    public IngestServer áŒŠÆ() {
        return this.Ø­à;
    }
    
    public void HorizonCode_Horizon_È(final IngestServer p_152824_1_) {
        this.Ø­à = p_152824_1_;
    }
    
    public IngestList áˆºÑ¢Õ() {
        return this.¥Æ;
    }
    
    public void HorizonCode_Horizon_È(final float p_152829_1_) {
        this.áŒŠÆ.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, p_152829_1_);
    }
    
    public void Â(final float p_152837_1_) {
        this.áŒŠÆ.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, p_152837_1_);
    }
    
    public IngestServerTester ÂµÈ() {
        return this.Ñ¢á;
    }
    
    public long á() {
        return this.áŒŠÆ.getStreamTime();
    }
    
    protected boolean ˆÏ­() {
        return true;
    }
    
    public ErrorCode £á() {
        return this.ÇŽÕ;
    }
    
    public BroadcastController() {
        this.ˆá = null;
        this.Ý = null;
        this.Ø­áŒŠá = "";
        this.Âµá€ = "";
        this.Ó = "";
        this.à = true;
        this.Ø = null;
        this.áŒŠÆ = null;
        this.áˆºÑ¢Õ = Lists.newArrayList();
        this.ÂµÈ = Lists.newArrayList();
        this.á = false;
        this.ˆÏ­ = false;
        this.£á = false;
        this.Å = BroadcastController.Â.HorizonCode_Horizon_È;
        this.£à = null;
        this.µà = null;
        this.ˆà = null;
        this.¥Æ = new IngestList(new IngestServer[0]);
        this.Ø­à = null;
        this.µÕ = new AuthToken();
        this.Æ = new ChannelInfo();
        this.Šáƒ = new UserInfo();
        this.Ï­Ðƒà = new StreamInfo();
        this.áŒŠà = new ArchivingState();
        this.ŠÄ = 0L;
        this.Ñ¢á = null;
        this.ŒÏ = (IStreamCallbacks)new IStreamCallbacks() {
            private static final String Â = "CL_00002375";
            
            public void requestAuthTokenCallback(final ErrorCode p_requestAuthTokenCallback_1_, final AuthToken p_requestAuthTokenCallback_2_) {
                if (ErrorCode.succeeded(p_requestAuthTokenCallback_1_)) {
                    BroadcastController.this.µÕ = p_requestAuthTokenCallback_2_;
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Ø­áŒŠá);
                }
                else {
                    BroadcastController.this.µÕ.data = "";
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Â);
                    final String var3 = ErrorCode.getString(p_requestAuthTokenCallback_1_);
                    BroadcastController.this.Â(String.format("RequestAuthTokenDoneCallback got failure: %s", var3));
                }
                try {
                    if (BroadcastController.this.Ý != null) {
                        BroadcastController.this.Ý.HorizonCode_Horizon_È(p_requestAuthTokenCallback_1_, p_requestAuthTokenCallback_2_);
                    }
                }
                catch (Exception var4) {
                    BroadcastController.this.Â(var4.toString());
                }
            }
            
            public void loginCallback(final ErrorCode p_loginCallback_1_, final ChannelInfo p_loginCallback_2_) {
                if (ErrorCode.succeeded(p_loginCallback_1_)) {
                    BroadcastController.this.Æ = p_loginCallback_2_;
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Ó);
                    BroadcastController.this.ˆÏ­ = true;
                }
                else {
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Â);
                    BroadcastController.this.ˆÏ­ = false;
                    final String var3 = ErrorCode.getString(p_loginCallback_1_);
                    BroadcastController.this.Â(String.format("LoginCallback got failure: %s", var3));
                }
                try {
                    if (BroadcastController.this.Ý != null) {
                        BroadcastController.this.Ý.HorizonCode_Horizon_È(p_loginCallback_1_);
                    }
                }
                catch (Exception var4) {
                    BroadcastController.this.Â(var4.toString());
                }
            }
            
            public void getIngestServersCallback(final ErrorCode p_getIngestServersCallback_1_, final IngestList p_getIngestServersCallback_2_) {
                if (ErrorCode.succeeded(p_getIngestServersCallback_1_)) {
                    BroadcastController.this.¥Æ = p_getIngestServersCallback_2_;
                    BroadcastController.this.Ø­à = BroadcastController.this.¥Æ.getDefaultServer();
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Ø);
                    try {
                        if (BroadcastController.this.Ý != null) {
                            BroadcastController.this.Ý.HorizonCode_Horizon_È(p_getIngestServersCallback_2_);
                        }
                    }
                    catch (Exception var4) {
                        BroadcastController.this.Â(var4.toString());
                    }
                }
                else {
                    final String var5 = ErrorCode.getString(p_getIngestServersCallback_1_);
                    BroadcastController.this.Â(String.format("IngestListCallback got failure: %s", var5));
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Âµá€);
                }
            }
            
            public void getUserInfoCallback(final ErrorCode p_getUserInfoCallback_1_, final UserInfo p_getUserInfoCallback_2_) {
                BroadcastController.this.Šáƒ = p_getUserInfoCallback_2_;
                if (ErrorCode.failed(p_getUserInfoCallback_1_)) {
                    final String var3 = ErrorCode.getString(p_getUserInfoCallback_1_);
                    BroadcastController.this.Â(String.format("UserInfoDoneCallback got failure: %s", var3));
                }
            }
            
            public void getStreamInfoCallback(final ErrorCode p_getStreamInfoCallback_1_, final StreamInfo p_getStreamInfoCallback_2_) {
                if (ErrorCode.succeeded(p_getStreamInfoCallback_1_)) {
                    BroadcastController.this.Ï­Ðƒà = p_getStreamInfoCallback_2_;
                    try {
                        if (BroadcastController.this.Ý != null) {
                            BroadcastController.this.Ý.HorizonCode_Horizon_È(p_getStreamInfoCallback_2_);
                        }
                    }
                    catch (Exception var4) {
                        BroadcastController.this.Â(var4.toString());
                    }
                }
                else {
                    final String var5 = ErrorCode.getString(p_getStreamInfoCallback_1_);
                    BroadcastController.this.Ý(String.format("StreamInfoDoneCallback got failure: %s", var5));
                }
            }
            
            public void getArchivingStateCallback(final ErrorCode p_getArchivingStateCallback_1_, final ArchivingState p_getArchivingStateCallback_2_) {
                BroadcastController.this.áŒŠà = p_getArchivingStateCallback_2_;
                if (ErrorCode.failed(p_getArchivingStateCallback_1_)) {}
            }
            
            public void runCommercialCallback(final ErrorCode p_runCommercialCallback_1_) {
                if (ErrorCode.failed(p_runCommercialCallback_1_)) {
                    final String var2 = ErrorCode.getString(p_runCommercialCallback_1_);
                    BroadcastController.this.Ý(String.format("RunCommercialCallback got failure: %s", var2));
                }
            }
            
            public void setStreamInfoCallback(final ErrorCode p_setStreamInfoCallback_1_) {
                if (ErrorCode.failed(p_setStreamInfoCallback_1_)) {
                    final String var2 = ErrorCode.getString(p_setStreamInfoCallback_1_);
                    BroadcastController.this.Ý(String.format("SetStreamInfoCallback got failure: %s", var2));
                }
            }
            
            public void getGameNameListCallback(final ErrorCode p_getGameNameListCallback_1_, final GameInfoList p_getGameNameListCallback_2_) {
                if (ErrorCode.failed(p_getGameNameListCallback_1_)) {
                    final String var3 = ErrorCode.getString(p_getGameNameListCallback_1_);
                    BroadcastController.this.Â(String.format("GameNameListCallback got failure: %s", var3));
                }
                try {
                    if (BroadcastController.this.Ý != null) {
                        BroadcastController.this.Ý.HorizonCode_Horizon_È(p_getGameNameListCallback_1_, (p_getGameNameListCallback_2_ == null) ? new GameInfo[0] : p_getGameNameListCallback_2_.list);
                    }
                }
                catch (Exception var4) {
                    BroadcastController.this.Â(var4.toString());
                }
            }
            
            public void bufferUnlockCallback(final long p_bufferUnlockCallback_1_) {
                final FrameBuffer var3 = FrameBuffer.lookupBuffer(p_bufferUnlockCallback_1_);
                BroadcastController.this.ÂµÈ.add(var3);
            }
            
            public void startCallback(final ErrorCode p_startCallback_1_) {
                if (ErrorCode.succeeded(p_startCallback_1_)) {
                    try {
                        if (BroadcastController.this.Ý != null) {
                            BroadcastController.this.Ý.Â();
                        }
                    }
                    catch (Exception var4) {
                        BroadcastController.this.Â(var4.toString());
                    }
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.ÂµÈ);
                }
                else {
                    BroadcastController.this.µà = null;
                    BroadcastController.this.ˆà = null;
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.áŒŠÆ);
                    try {
                        if (BroadcastController.this.Ý != null) {
                            BroadcastController.this.Ý.Ý(p_startCallback_1_);
                        }
                    }
                    catch (Exception var5) {
                        BroadcastController.this.Â(var5.toString());
                    }
                    final String var6 = ErrorCode.getString(p_startCallback_1_);
                    BroadcastController.this.Â(String.format("startCallback got failure: %s", var6));
                }
            }
            
            public void stopCallback(final ErrorCode p_stopCallback_1_) {
                if (ErrorCode.succeeded(p_stopCallback_1_)) {
                    BroadcastController.this.µà = null;
                    BroadcastController.this.ˆà = null;
                    BroadcastController.this.ŒÏ();
                    try {
                        if (BroadcastController.this.Ý != null) {
                            BroadcastController.this.Ý.Ý();
                        }
                    }
                    catch (Exception var3) {
                        BroadcastController.this.Â(var3.toString());
                    }
                    if (BroadcastController.this.ˆÏ­) {
                        BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.áŒŠÆ);
                    }
                    else {
                        BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.Â);
                    }
                }
                else {
                    BroadcastController.this.HorizonCode_Horizon_È(BroadcastController.Â.áŒŠÆ);
                    final String var4 = ErrorCode.getString(p_stopCallback_1_);
                    BroadcastController.this.Â(String.format("stopCallback got failure: %s", var4));
                }
            }
            
            public void sendActionMetaDataCallback(final ErrorCode p_sendActionMetaDataCallback_1_) {
                if (ErrorCode.failed(p_sendActionMetaDataCallback_1_)) {
                    final String var2 = ErrorCode.getString(p_sendActionMetaDataCallback_1_);
                    BroadcastController.this.Â(String.format("sendActionMetaDataCallback got failure: %s", var2));
                }
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode p_sendStartSpanMetaDataCallback_1_) {
                if (ErrorCode.failed(p_sendStartSpanMetaDataCallback_1_)) {
                    final String var2 = ErrorCode.getString(p_sendStartSpanMetaDataCallback_1_);
                    BroadcastController.this.Â(String.format("sendStartSpanMetaDataCallback got failure: %s", var2));
                }
            }
            
            public void sendEndSpanMetaDataCallback(final ErrorCode p_sendEndSpanMetaDataCallback_1_) {
                if (ErrorCode.failed(p_sendEndSpanMetaDataCallback_1_)) {
                    final String var2 = ErrorCode.getString(p_sendEndSpanMetaDataCallback_1_);
                    BroadcastController.this.Â(String.format("sendEndSpanMetaDataCallback got failure: %s", var2));
                }
            }
        };
        this.Çªà¢ = (IStatCallbacks)new IStatCallbacks() {
            private static final String Â = "CL_00002374";
            
            public void statCallback(final StatType p_statCallback_1_, final long p_statCallback_2_) {
            }
        };
        this.Ø = Core.getInstance();
        if (Core.getInstance() == null) {
            this.Ø = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.áŒŠÆ = new Stream((StreamAPI)new DesktopStreamAPI());
    }
    
    protected PixelFormat Å() {
        return PixelFormat.TTV_PF_RGBA;
    }
    
    public boolean £à() {
        if (this.á) {
            return false;
        }
        this.áŒŠÆ.setStreamCallbacks(this.ŒÏ);
        ErrorCode var1 = this.Ø.initialize(this.Ø­áŒŠá, System.getProperty("java.library.path"));
        if (!this.HorizonCode_Horizon_È(var1)) {
            this.áŒŠÆ.setStreamCallbacks((IStreamCallbacks)null);
            this.ÇŽÕ = var1;
            return false;
        }
        var1 = this.Ø.setTraceLevel(MessageLevel.TTV_ML_ERROR);
        if (!this.HorizonCode_Horizon_È(var1)) {
            this.áŒŠÆ.setStreamCallbacks((IStreamCallbacks)null);
            this.Ø.shutdown();
            this.ÇŽÕ = var1;
            return false;
        }
        if (ErrorCode.succeeded(var1)) {
            this.á = true;
            this.HorizonCode_Horizon_È(BroadcastController.Â.Â);
            return true;
        }
        this.ÇŽÕ = var1;
        this.Ø.shutdown();
        return false;
    }
    
    public boolean µà() {
        if (!this.á) {
            return true;
        }
        if (this.Ó()) {
            return false;
        }
        this.£á = true;
        this.¥Æ();
        this.áŒŠÆ.setStreamCallbacks((IStreamCallbacks)null);
        this.áŒŠÆ.setStatCallbacks((IStatCallbacks)null);
        final ErrorCode var1 = this.Ø.shutdown();
        this.HorizonCode_Horizon_È(var1);
        this.á = false;
        this.£á = false;
        this.HorizonCode_Horizon_È(BroadcastController.Â.HorizonCode_Horizon_È);
        return true;
    }
    
    public void ˆà() {
        if (this.Å != BroadcastController.Â.HorizonCode_Horizon_È) {
            if (this.Ñ¢á != null) {
                this.Ñ¢á.à();
            }
            while (this.Ñ¢á != null) {
                try {
                    Thread.sleep(200L);
                }
                catch (Exception var2) {
                    this.Â(var2.toString());
                }
                this.Ï­Ðƒà();
            }
            this.µà();
        }
    }
    
    public boolean HorizonCode_Horizon_È(final String p_152818_1_, final AuthToken p_152818_2_) {
        if (this.Ó()) {
            return false;
        }
        this.¥Æ();
        if (p_152818_1_ == null || p_152818_1_.isEmpty()) {
            this.Â("Username must be valid");
            return false;
        }
        if (p_152818_2_ != null && p_152818_2_.data != null && !p_152818_2_.data.isEmpty()) {
            this.£à = p_152818_1_;
            this.µÕ = p_152818_2_;
            if (this.HorizonCode_Horizon_È()) {
                this.HorizonCode_Horizon_È(BroadcastController.Â.Ø­áŒŠá);
            }
            return true;
        }
        this.Â("Auth token must be valid");
        return false;
    }
    
    public boolean ¥Æ() {
        if (this.Ó()) {
            return false;
        }
        if (this.Ø­áŒŠá()) {
            this.áŒŠÆ.stop(false);
        }
        this.£à = "";
        this.µÕ = new AuthToken();
        if (!this.ˆÏ­) {
            return false;
        }
        this.ˆÏ­ = false;
        if (!this.£á) {
            try {
                if (this.Ý != null) {
                    this.Ý.HorizonCode_Horizon_È();
                }
            }
            catch (Exception var2) {
                this.Â(var2.toString());
            }
        }
        this.HorizonCode_Horizon_È(BroadcastController.Â.Â);
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(String p_152828_1_, String p_152828_2_, String p_152828_3_) {
        if (!this.ˆÏ­) {
            return false;
        }
        if (p_152828_1_ == null || p_152828_1_.equals("")) {
            p_152828_1_ = this.£à;
        }
        if (p_152828_2_ == null) {
            p_152828_2_ = "";
        }
        if (p_152828_3_ == null) {
            p_152828_3_ = "";
        }
        final StreamInfoForSetting var4 = new StreamInfoForSetting();
        var4.streamTitle = p_152828_3_;
        var4.gameName = p_152828_2_;
        final ErrorCode var5 = this.áŒŠÆ.setStreamInfo(this.µÕ, p_152828_1_, var4);
        this.HorizonCode_Horizon_È(var5);
        return ErrorCode.succeeded(var5);
    }
    
    public boolean Ø­à() {
        if (!this.Ø­áŒŠá()) {
            return false;
        }
        final ErrorCode var1 = this.áŒŠÆ.runCommercial(this.µÕ);
        this.HorizonCode_Horizon_È(var1);
        return ErrorCode.succeeded(var1);
    }
    
    public VideoParams HorizonCode_Horizon_È(final int p_152834_1_, final int p_152834_2_, final float p_152834_3_, final float p_152834_4_) {
        final int[] var5 = this.áŒŠÆ.getMaxResolution(p_152834_1_, p_152834_2_, p_152834_3_, p_152834_4_);
        final VideoParams var6 = new VideoParams();
        var6.maxKbps = p_152834_1_;
        var6.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        var6.pixelFormat = this.Å();
        var6.targetFps = p_152834_2_;
        var6.outputWidth = var5[0];
        var6.outputHeight = var5[1];
        var6.disableAdaptiveBitrate = false;
        var6.verticalFlip = false;
        return var6;
    }
    
    public boolean HorizonCode_Horizon_È(final VideoParams p_152836_1_) {
        if (p_152836_1_ == null || !this.Âµá€()) {
            return false;
        }
        this.µà = p_152836_1_.clone();
        this.ˆà = new AudioParams();
        this.ˆà.audioEnabled = (this.à && this.ˆÏ­());
        this.ˆà.enableMicCapture = this.ˆà.audioEnabled;
        this.ˆà.enablePlaybackCapture = this.ˆà.audioEnabled;
        this.ˆà.enablePassthroughAudio = false;
        if (!this.Ñ¢á()) {
            this.µà = null;
            this.ˆà = null;
            return false;
        }
        final ErrorCode var2 = this.áŒŠÆ.start(p_152836_1_, this.ˆà, this.Ø­à, StartFlags.None, true);
        if (ErrorCode.failed(var2)) {
            this.ŒÏ();
            final String var3 = ErrorCode.getString(var2);
            this.Â(String.format("Error while starting to broadcast: %s", var3));
            this.µà = null;
            this.ˆà = null;
            return false;
        }
        this.HorizonCode_Horizon_È(BroadcastController.Â.áˆºÑ¢Õ);
        return true;
    }
    
    public boolean µÕ() {
        if (!this.Ø­áŒŠá()) {
            return false;
        }
        final ErrorCode var1 = this.áŒŠÆ.stop(true);
        if (ErrorCode.failed(var1)) {
            final String var2 = ErrorCode.getString(var1);
            this.Â(String.format("Error while stopping the broadcast: %s", var2));
            return false;
        }
        this.HorizonCode_Horizon_È(BroadcastController.Â.á);
        return ErrorCode.succeeded(var1);
    }
    
    public boolean Æ() {
        if (!this.Ø­áŒŠá()) {
            return false;
        }
        final ErrorCode var1 = this.áŒŠÆ.pauseVideo();
        if (ErrorCode.failed(var1)) {
            this.µÕ();
            final String var2 = ErrorCode.getString(var1);
            this.Â(String.format("Error pausing stream: %s\n", var2));
        }
        else {
            this.HorizonCode_Horizon_È(BroadcastController.Â.ˆÏ­);
        }
        return ErrorCode.succeeded(var1);
    }
    
    public boolean Šáƒ() {
        if (!this.à()) {
            return false;
        }
        this.HorizonCode_Horizon_È(BroadcastController.Â.ÂµÈ);
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final String p_152840_1_, final long p_152840_2_, final String p_152840_4_, final String p_152840_5_) {
        final ErrorCode var6 = this.áŒŠÆ.sendActionMetaData(this.µÕ, p_152840_1_, p_152840_2_, p_152840_4_, p_152840_5_);
        if (ErrorCode.failed(var6)) {
            final String var7 = ErrorCode.getString(var6);
            this.Â(String.format("Error while sending meta data: %s\n", var7));
            return false;
        }
        return true;
    }
    
    public long Â(final String p_177946_1_, final long p_177946_2_, final String p_177946_4_, final String p_177946_5_) {
        final long var6 = this.áŒŠÆ.sendStartSpanMetaData(this.µÕ, p_177946_1_, p_177946_2_, p_177946_4_, p_177946_5_);
        if (var6 == -1L) {
            this.Â(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
        }
        return var6;
    }
    
    public boolean HorizonCode_Horizon_È(final String p_177947_1_, final long p_177947_2_, final long p_177947_4_, final String p_177947_6_, final String p_177947_7_) {
        if (p_177947_4_ == -1L) {
            this.Â(String.format("Invalid sequence id: %d\n", p_177947_4_));
            return false;
        }
        final ErrorCode var8 = this.áŒŠÆ.sendEndSpanMetaData(this.µÕ, p_177947_1_, p_177947_2_, p_177947_4_, p_177947_6_, p_177947_7_);
        if (ErrorCode.failed(var8)) {
            final String var9 = ErrorCode.getString(var8);
            this.Â(String.format("Error in SendStopSpanMetaData: %s\n", var9));
            return false;
        }
        return true;
    }
    
    protected void HorizonCode_Horizon_È(final Â p_152827_1_) {
        if (p_152827_1_ != this.Å) {
            this.Å = p_152827_1_;
            try {
                if (this.Ý != null) {
                    this.Ý.HorizonCode_Horizon_È(p_152827_1_);
                }
            }
            catch (Exception var3) {
                this.Â(var3.toString());
            }
        }
    }
    
    public void Ï­Ðƒà() {
        if (this.áŒŠÆ != null && this.á) {
            ErrorCode var1 = this.áŒŠÆ.pollTasks();
            this.HorizonCode_Horizon_È(var1);
            if (this.Ó()) {
                this.Ñ¢á.Ó();
                if (this.Ñ¢á.Ý()) {
                    this.Ñ¢á = null;
                    this.HorizonCode_Horizon_È(BroadcastController.Â.áŒŠÆ);
                }
            }
            switch (BroadcastController.Ý.HorizonCode_Horizon_È[this.Å.ordinal()]) {
                case 1: {
                    this.HorizonCode_Horizon_È(BroadcastController.Â.Âµá€);
                    var1 = this.áŒŠÆ.login(this.µÕ);
                    if (ErrorCode.failed(var1)) {
                        final String var2 = ErrorCode.getString(var1);
                        this.Â(String.format("Error in TTV_Login: %s\n", var2));
                        break;
                    }
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(BroadcastController.Â.à);
                    var1 = this.áŒŠÆ.getIngestServers(this.µÕ);
                    if (ErrorCode.failed(var1)) {
                        this.HorizonCode_Horizon_È(BroadcastController.Â.Ó);
                        final String var2 = ErrorCode.getString(var1);
                        this.Â(String.format("Error in TTV_GetIngestServers: %s\n", var2));
                        break;
                    }
                    break;
                }
                case 3: {
                    this.HorizonCode_Horizon_È(BroadcastController.Â.áŒŠÆ);
                    var1 = this.áŒŠÆ.getUserInfo(this.µÕ);
                    if (ErrorCode.failed(var1)) {
                        final String var2 = ErrorCode.getString(var1);
                        this.Â(String.format("Error in TTV_GetUserInfo: %s\n", var2));
                    }
                    this.áŒŠà();
                    var1 = this.áŒŠÆ.getArchivingState(this.µÕ);
                    if (ErrorCode.failed(var1)) {
                        final String var2 = ErrorCode.getString(var1);
                        this.Â(String.format("Error in TTV_GetArchivingState: %s\n", var2));
                        break;
                    }
                    break;
                }
                case 11:
                case 12: {
                    this.áŒŠà();
                    break;
                }
            }
        }
    }
    
    protected void áŒŠà() {
        final long var1 = System.nanoTime();
        final long var2 = (var1 - this.ŠÄ) / 1000000000L;
        if (var2 >= 30L) {
            this.ŠÄ = var1;
            final ErrorCode var3 = this.áŒŠÆ.getStreamInfo(this.µÕ, this.£à);
            if (ErrorCode.failed(var3)) {
                final String var4 = ErrorCode.getString(var3);
                this.Â(String.format("Error in TTV_GetStreamInfo: %s", var4));
            }
        }
    }
    
    public IngestServerTester ŠÄ() {
        if (!this.Âµá€() || this.¥Æ == null) {
            return null;
        }
        if (this.Ó()) {
            return null;
        }
        (this.Ñ¢á = new IngestServerTester(this.áŒŠÆ, this.¥Æ)).Âµá€();
        this.HorizonCode_Horizon_È(BroadcastController.Â.£á);
        return this.Ñ¢á;
    }
    
    protected boolean Ñ¢á() {
        for (int var1 = 0; var1 < 3; ++var1) {
            final FrameBuffer var2 = this.áŒŠÆ.allocateFrameBuffer(this.µà.outputWidth * this.µà.outputHeight * 4);
            if (!var2.getIsValid()) {
                this.Â(String.format("Error while allocating frame buffer", new Object[0]));
                return false;
            }
            this.áˆºÑ¢Õ.add(var2);
            this.ÂµÈ.add(var2);
        }
        return true;
    }
    
    protected void ŒÏ() {
        for (int var1 = 0; var1 < this.áˆºÑ¢Õ.size(); ++var1) {
            final FrameBuffer var2 = this.áˆºÑ¢Õ.get(var1);
            var2.free();
        }
        this.ÂµÈ.clear();
        this.áˆºÑ¢Õ.clear();
    }
    
    public FrameBuffer Çªà¢() {
        if (this.ÂµÈ.size() == 0) {
            this.Â(String.format("Out of free buffers, this should never happen", new Object[0]));
            return null;
        }
        final FrameBuffer var1 = this.ÂµÈ.get(this.ÂµÈ.size() - 1);
        this.ÂµÈ.remove(this.ÂµÈ.size() - 1);
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final FrameBuffer p_152846_1_) {
        try {
            this.áŒŠÆ.captureFrameBuffer_ReadPixels(p_152846_1_);
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Trying to submit a frame to Twitch");
            final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Broadcast State");
            var5.HorizonCode_Horizon_È("Last reported errors", Arrays.toString(BroadcastController.ÇŽÉ.Â()));
            var5.HorizonCode_Horizon_È("Buffer", p_152846_1_);
            var5.HorizonCode_Horizon_È("Free buffer count", this.ÂµÈ.size());
            var5.HorizonCode_Horizon_È("Capture buffer count", this.áˆºÑ¢Õ.size());
            throw new ReportedException(var3);
        }
    }
    
    public ErrorCode Â(final FrameBuffer p_152859_1_) {
        if (this.à()) {
            this.Šáƒ();
        }
        else if (!this.Ø­áŒŠá()) {
            return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
        }
        final ErrorCode var2 = this.áŒŠÆ.submitVideoFrame(p_152859_1_);
        if (var2 != ErrorCode.TTV_EC_SUCCESS) {
            final String var3 = ErrorCode.getString(var2);
            if (ErrorCode.succeeded(var2)) {
                this.Ý(String.format("Warning in SubmitTexturePointer: %s\n", var3));
            }
            else {
                this.Â(String.format("Error in SubmitTexturePointer: %s\n", var3));
                this.µÕ();
            }
            if (this.Ý != null) {
                this.Ý.Â(var2);
            }
        }
        return var2;
    }
    
    protected boolean HorizonCode_Horizon_È(final ErrorCode p_152853_1_) {
        if (ErrorCode.failed(p_152853_1_)) {
            this.Â(ErrorCode.getString(p_152853_1_));
            return false;
        }
        return true;
    }
    
    protected void Â(final String p_152820_1_) {
        this.ˆá = p_152820_1_;
        BroadcastController.ÇŽÉ.HorizonCode_Horizon_È("<Error> " + p_152820_1_);
        BroadcastController.Ê.error(TwitchStream.HorizonCode_Horizon_È, "[Broadcast controller] {}", new Object[] { p_152820_1_ });
    }
    
    protected void Ý(final String p_152832_1_) {
        BroadcastController.ÇŽÉ.HorizonCode_Horizon_È("<Warning> " + p_152832_1_);
        BroadcastController.Ê.warn(TwitchStream.HorizonCode_Horizon_È, "[Broadcast controller] {}", new Object[] { p_152832_1_ });
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("Uninitialized", 0, "Uninitialized", 0), 
        Â("Initialized", 1, "Initialized", 1), 
        Ý("Authenticating", 2, "Authenticating", 2), 
        Ø­áŒŠá("Authenticated", 3, "Authenticated", 3), 
        Âµá€("LoggingIn", 4, "LoggingIn", 4), 
        Ó("LoggedIn", 5, "LoggedIn", 5), 
        à("FindingIngestServer", 6, "FindingIngestServer", 6), 
        Ø("ReceivedIngestServers", 7, "ReceivedIngestServers", 7), 
        áŒŠÆ("ReadyToBroadcast", 8, "ReadyToBroadcast", 8), 
        áˆºÑ¢Õ("Starting", 9, "Starting", 9), 
        ÂµÈ("Broadcasting", 10, "Broadcasting", 10), 
        á("Stopping", 11, "Stopping", 11), 
        ˆÏ­("Paused", 12, "Paused", 12), 
        £á("IngestTesting", 13, "IngestTesting", 13);
        
        private static final Â[] Å;
        private static final String £à = "CL_00001820";
        
        static {
            µà = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ, Â.áˆºÑ¢Õ, Â.ÂµÈ, Â.á, Â.ˆÏ­, Â.£á };
            Å = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à, Â.Ø, Â.áŒŠÆ, Â.áˆºÑ¢Õ, Â.ÂµÈ, Â.á, Â.ˆÏ­, Â.£á };
        }
        
        private Â(final String s, final int n, final String p_i1025_1_, final int p_i1025_2_) {
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001821";
        
        static {
            HorizonCode_Horizon_È = new int[BroadcastController.Â.values().length];
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.Ø­áŒŠá.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.Ó.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.Ø.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.áˆºÑ¢Õ.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.á.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.à.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.Ý.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.Â.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.HorizonCode_Horizon_È.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.£á.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.ˆÏ­.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                Ý.HorizonCode_Horizon_È[BroadcastController.Â.ÂµÈ.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
        }
    }
    
    public interface HorizonCode_Horizon_È
    {
        void HorizonCode_Horizon_È(final ErrorCode p0, final AuthToken p1);
        
        void HorizonCode_Horizon_È(final ErrorCode p0);
        
        void HorizonCode_Horizon_È(final ErrorCode p0, final GameInfo[] p1);
        
        void HorizonCode_Horizon_È(final Â p0);
        
        void HorizonCode_Horizon_È();
        
        void HorizonCode_Horizon_È(final StreamInfo p0);
        
        void HorizonCode_Horizon_È(final IngestList p0);
        
        void Â(final ErrorCode p0);
        
        void Â();
        
        void Ý();
        
        void Ý(final ErrorCode p0);
    }
}
