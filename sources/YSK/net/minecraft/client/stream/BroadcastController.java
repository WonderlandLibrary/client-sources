package net.minecraft.client.stream;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import com.google.common.collect.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;
import org.apache.logging.log4j.*;

public class BroadcastController
{
    protected String field_152869_e;
    protected boolean field_152877_m;
    protected AuthToken authenticationToken;
    protected AudioParams audioParamaters;
    protected boolean field_152878_n;
    private static final ThreadSafeBoundList<String> field_152862_C;
    protected Stream field_152873_i;
    protected BroadcastState broadcastState;
    protected VideoParams videoParamaters;
    protected IngestServerTester field_152860_A;
    private static int[] $SWITCH_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState;
    protected BroadcastListener broadcastListener;
    protected Core field_152872_h;
    protected long field_152890_z;
    protected List<FrameBuffer> field_152875_k;
    private static final Logger logger;
    protected final int field_152865_a = 30;
    protected IngestServer field_152884_t;
    protected UserInfo userInfo;
    protected StreamInfo streamInfo;
    protected String field_152880_p;
    private static final String[] I;
    protected final int field_152866_b = 3;
    protected ArchivingState field_152889_y;
    protected String field_152868_d;
    protected List<FrameBuffer> field_152874_j;
    protected IStatCallbacks field_177949_C;
    private String field_152863_D;
    protected ChannelInfo channelInfo;
    protected boolean field_152871_g;
    protected IngestList ingestList;
    protected String field_152870_f;
    private ErrorCode errorCode;
    protected IStreamCallbacks field_177948_B;
    protected boolean field_152876_l;
    
    protected void func_152820_d(final String field_152863_D) {
        this.field_152863_D = field_152863_D;
        BroadcastController.field_152862_C.func_152757_a(BroadcastController.I[0x88 ^ 0xA8] + field_152863_D);
        final Logger logger = BroadcastController.logger;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = BroadcastController.I[0x1 ^ 0x20];
        final Object[] array = new Object[" ".length()];
        array["".length()] = field_152863_D;
        logger.error(stream_MARKER, s, array);
    }
    
    public boolean requestCommercial() {
        if (!this.isBroadcasting()) {
            return "".length() != 0;
        }
        final ErrorCode runCommercial = this.field_152873_i.runCommercial(this.authenticationToken);
        this.func_152853_a(runCommercial);
        return ErrorCode.succeeded(runCommercial);
    }
    
    public void captureFramebuffer(final FrameBuffer frameBuffer) {
        try {
            this.field_152873_i.captureFrameBuffer_ReadPixels(frameBuffer);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, BroadcastController.I[0x7F ^ 0x67]);
            final CrashReportCategory category = crashReport.makeCategory(BroadcastController.I[0x38 ^ 0x21]);
            category.addCrashSection(BroadcastController.I[0x6E ^ 0x74], Arrays.toString(BroadcastController.field_152862_C.func_152756_c()));
            category.addCrashSection(BroadcastController.I[0x61 ^ 0x7A], frameBuffer);
            category.addCrashSection(BroadcastController.I[0x34 ^ 0x28], this.field_152875_k.size());
            category.addCrashSection(BroadcastController.I[0x6D ^ 0x70], this.field_152874_j.size());
            throw new ReportedException(crashReport);
        }
    }
    
    public void func_152821_H() {
        if (this.field_152873_i != null && this.field_152876_l) {
            this.func_152853_a(this.field_152873_i.pollTasks());
            if (this.isIngestTesting()) {
                this.field_152860_A.func_153041_j();
                if (this.field_152860_A.func_153032_e()) {
                    this.field_152860_A = null;
                    this.func_152827_a(BroadcastState.ReadyToBroadcast);
                }
            }
            switch ($SWITCH_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState()[this.broadcastState.ordinal()]) {
                case 4: {
                    this.func_152827_a(BroadcastState.LoggingIn);
                    final ErrorCode login = this.field_152873_i.login(this.authenticationToken);
                    if (!ErrorCode.failed(login)) {
                        return;
                    }
                    final String string = ErrorCode.getString(login);
                    final String s = BroadcastController.I[0x6C ^ 0x7D];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this.func_152820_d(String.format(s, array));
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                    return;
                }
                case 6: {
                    this.func_152827_a(BroadcastState.FindingIngestServer);
                    final ErrorCode ingestServers = this.field_152873_i.getIngestServers(this.authenticationToken);
                    if (!ErrorCode.failed(ingestServers)) {
                        return;
                    }
                    this.func_152827_a(BroadcastState.LoggedIn);
                    final String string2 = ErrorCode.getString(ingestServers);
                    final String s2 = BroadcastController.I[0xAE ^ 0xBC];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = string2;
                    this.func_152820_d(String.format(s2, array2));
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    return;
                }
                case 8: {
                    this.func_152827_a(BroadcastState.ReadyToBroadcast);
                    final ErrorCode userInfo = this.field_152873_i.getUserInfo(this.authenticationToken);
                    if (ErrorCode.failed(userInfo)) {
                        final String string3 = ErrorCode.getString(userInfo);
                        final String s3 = BroadcastController.I[0x9A ^ 0x89];
                        final Object[] array3 = new Object[" ".length()];
                        array3["".length()] = string3;
                        this.func_152820_d(String.format(s3, array3));
                    }
                    this.func_152835_I();
                    final ErrorCode archivingState = this.field_152873_i.getArchivingState(this.authenticationToken);
                    if (ErrorCode.failed(archivingState)) {
                        final String string4 = ErrorCode.getString(archivingState);
                        final String s4 = BroadcastController.I[0x6 ^ 0x12];
                        final Object[] array4 = new Object[" ".length()];
                        array4["".length()] = string4;
                        this.func_152820_d(String.format(s4, array4));
                        break;
                    }
                    return;
                }
                case 11:
                case 13: {
                    this.func_152835_I();
                    return;
                }
            }
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
    }
    
    public boolean func_152836_a(final VideoParams videoParams) {
        if (videoParams == null || !this.isReadyToBroadcast()) {
            return "".length() != 0;
        }
        this.videoParamaters = videoParams.clone();
        this.audioParamaters = new AudioParams();
        final AudioParams audioParamaters = this.audioParamaters;
        int audioEnabled;
        if (this.field_152871_g && this.func_152848_y()) {
            audioEnabled = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            audioEnabled = "".length();
        }
        audioParamaters.audioEnabled = (audioEnabled != 0);
        this.audioParamaters.enableMicCapture = this.audioParamaters.audioEnabled;
        this.audioParamaters.enablePlaybackCapture = this.audioParamaters.audioEnabled;
        this.audioParamaters.enablePassthroughAudio = ("".length() != 0);
        if (!this.func_152823_L()) {
            this.videoParamaters = null;
            this.audioParamaters = null;
            return "".length() != 0;
        }
        final ErrorCode start = this.field_152873_i.start(videoParams, this.audioParamaters, this.field_152884_t, StartFlags.None, (boolean)(" ".length() != 0));
        if (ErrorCode.failed(start)) {
            this.func_152831_M();
            final String string = ErrorCode.getString(start);
            final String s = BroadcastController.I[0x3A ^ 0x30];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152820_d(String.format(s, array));
            this.videoParamaters = null;
            this.audioParamaters = null;
            return "".length() != 0;
        }
        this.func_152827_a(BroadcastState.Starting);
        return " ".length() != 0;
    }
    
    protected PixelFormat func_152826_z() {
        return PixelFormat.TTV_PF_RGBA;
    }
    
    protected boolean func_152848_y() {
        return " ".length() != 0;
    }
    
    protected boolean func_152853_a(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            this.func_152820_d(ErrorCode.getString(errorCode));
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean stopBroadcasting() {
        if (!this.isBroadcasting()) {
            return "".length() != 0;
        }
        final ErrorCode stop = this.field_152873_i.stop((boolean)(" ".length() != 0));
        if (ErrorCode.failed(stop)) {
            final String string = ErrorCode.getString(stop);
            final String s = BroadcastController.I[0x74 ^ 0x7F];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152820_d(String.format(s, array));
            return "".length() != 0;
        }
        this.func_152827_a(BroadcastState.Stopping);
        return ErrorCode.succeeded(stop);
    }
    
    public boolean func_177947_a(final String s, final long n, final long n2, final String s2, final String s3) {
        if (n2 == -1L) {
            final String s4 = BroadcastController.I[0x4A ^ 0x45];
            final Object[] array = new Object[" ".length()];
            array["".length()] = n2;
            this.func_152820_d(String.format(s4, array));
            return "".length() != 0;
        }
        final ErrorCode sendEndSpanMetaData = this.field_152873_i.sendEndSpanMetaData(this.authenticationToken, s, n, n2, s2, s3);
        if (ErrorCode.failed(sendEndSpanMetaData)) {
            final String string = ErrorCode.getString(sendEndSpanMetaData);
            final String s5 = BroadcastController.I[0x7A ^ 0x6A];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = string;
            this.func_152820_d(String.format(s5, array2));
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean func_152847_F() {
        if (!this.isBroadcasting()) {
            return "".length() != 0;
        }
        final ErrorCode pauseVideo = this.field_152873_i.pauseVideo();
        if (ErrorCode.failed(pauseVideo)) {
            this.stopBroadcasting();
            final String string = ErrorCode.getString(pauseVideo);
            final String s = BroadcastController.I[0x65 ^ 0x69];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152820_d(String.format(s, array));
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            this.func_152827_a(BroadcastState.Paused);
        }
        return ErrorCode.succeeded(pauseVideo);
    }
    
    public boolean func_152849_q() {
        return this.field_152877_m;
    }
    
    public void func_152841_a(final BroadcastListener broadcastListener) {
        this.broadcastListener = broadcastListener;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState() {
        final int[] $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState = BroadcastController.$SWITCH_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState;
        if ($switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState != null) {
            return $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState;
        }
        final int[] $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2 = new int[BroadcastState.values().length];
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Authenticated.ordinal()] = (0x52 ^ 0x56);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Authenticating.ordinal()] = "   ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Broadcasting.ordinal()] = (0x39 ^ 0x32);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.FindingIngestServer.ordinal()] = (0x9E ^ 0x99);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.IngestTesting.ordinal()] = (0x69 ^ 0x67);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Initialized.ordinal()] = "  ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.LoggedIn.ordinal()] = (0x9C ^ 0x9A);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.LoggingIn.ordinal()] = (0x19 ^ 0x1C);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Paused.ordinal()] = (0x60 ^ 0x6D);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.ReadyToBroadcast.ordinal()] = (0x36 ^ 0x3F);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.ReceivedIngestServers.ordinal()] = (0xB3 ^ 0xBB);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Starting.ordinal()] = (0x2F ^ 0x25);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Stopping.ordinal()] = (0x5F ^ 0x53);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2[BroadcastState.Uninitialized.ordinal()] = " ".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        return BroadcastController.$SWITCH_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState = $switch_TABLE$net$minecraft$client$stream$BroadcastController$BroadcastState2;
    }
    
    protected void func_152831_M() {
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i < this.field_152874_j.size()) {
            this.field_152874_j.get(i).free();
            ++i;
        }
        this.field_152875_k.clear();
        this.field_152874_j.clear();
    }
    
    public IngestServerTester func_152838_J() {
        if (!this.isReadyToBroadcast() || this.ingestList == null) {
            return null;
        }
        if (this.isIngestTesting()) {
            return null;
        }
        (this.field_152860_A = new IngestServerTester(this.field_152873_i, this.ingestList)).func_176004_j();
        this.func_152827_a(BroadcastState.IngestTesting);
        return this.field_152860_A;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public long func_177946_b(final String s, final long n, final String s2, final String s3) {
        final long sendStartSpanMetaData = this.field_152873_i.sendStartSpanMetaData(this.authenticationToken, s, n, s2, s3);
        if (sendStartSpanMetaData == -1L) {
            this.func_152820_d(String.format(BroadcastController.I[0x75 ^ 0x7B], new Object["".length()]));
        }
        return sendStartSpanMetaData;
    }
    
    public boolean isReadyToBroadcast() {
        if (this.broadcastState == BroadcastState.ReadyToBroadcast) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isBroadcastPaused() {
        if (this.broadcastState == BroadcastState.Paused) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isBroadcasting() {
        if (this.broadcastState != BroadcastState.Broadcasting && this.broadcastState != BroadcastState.Paused) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void func_152842_a(final String field_152868_d) {
        this.field_152868_d = field_152868_d;
    }
    
    public boolean func_152840_a(final String s, final long n, final String s2, final String s3) {
        final ErrorCode sendActionMetaData = this.field_152873_i.sendActionMetaData(this.authenticationToken, s, n, s2, s3);
        if (ErrorCode.failed(sendActionMetaData)) {
            final String string = ErrorCode.getString(sendActionMetaData);
            final String s4 = BroadcastController.I[0x86 ^ 0x8B];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152820_d(String.format(s4, array));
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public ErrorCode submitStreamFrame(final FrameBuffer frameBuffer) {
        if (this.isBroadcastPaused()) {
            this.func_152854_G();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (!this.isBroadcasting()) {
            return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
        }
        final ErrorCode submitVideoFrame = this.field_152873_i.submitVideoFrame(frameBuffer);
        if (submitVideoFrame != ErrorCode.TTV_EC_SUCCESS) {
            final String string = ErrorCode.getString(submitVideoFrame);
            if (ErrorCode.succeeded(submitVideoFrame)) {
                final String s = BroadcastController.I[0x55 ^ 0x4B];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this.func_152832_e(String.format(s, array));
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                final String s2 = BroadcastController.I[0x68 ^ 0x77];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = string;
                this.func_152820_d(String.format(s2, array2));
                this.stopBroadcasting();
            }
            if (this.broadcastListener != null) {
                this.broadcastListener.func_152893_b(submitVideoFrame);
            }
        }
        return submitVideoFrame;
    }
    
    public boolean func_152851_B() {
        if (!this.field_152876_l) {
            return " ".length() != 0;
        }
        if (this.isIngestTesting()) {
            return "".length() != 0;
        }
        this.field_152878_n = (" ".length() != 0);
        this.func_152845_C();
        this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
        this.field_152873_i.setStatCallbacks((IStatCallbacks)null);
        this.func_152853_a(this.field_152872_h.shutdown());
        this.field_152876_l = ("".length() != 0);
        this.field_152878_n = ("".length() != 0);
        this.func_152827_a(BroadcastState.Uninitialized);
        return " ".length() != 0;
    }
    
    public StreamInfo getStreamInfo() {
        return this.streamInfo;
    }
    
    public VideoParams func_152834_a(final int maxKbps, final int targetFps, final float n, final float n2) {
        final int[] maxResolution = this.field_152873_i.getMaxResolution(maxKbps, targetFps, n, n2);
        final VideoParams videoParams = new VideoParams();
        videoParams.maxKbps = maxKbps;
        videoParams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        videoParams.pixelFormat = this.func_152826_z();
        videoParams.targetFps = targetFps;
        videoParams.outputWidth = maxResolution["".length()];
        videoParams.outputHeight = maxResolution[" ".length()];
        videoParams.disableAdaptiveBitrate = ("".length() != 0);
        videoParams.verticalFlip = ("".length() != 0);
        return videoParams;
    }
    
    public boolean isIngestTesting() {
        if (this.broadcastState == BroadcastState.IngestTesting) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean func_152854_G() {
        if (!this.isBroadcastPaused()) {
            return "".length() != 0;
        }
        this.func_152827_a(BroadcastState.Broadcasting);
        return " ".length() != 0;
    }
    
    public void setRecordingDeviceVolume(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, n);
    }
    
    public IngestServerTester isReady() {
        return this.field_152860_A;
    }
    
    public BroadcastController() {
        this.field_152863_D = null;
        this.broadcastListener = null;
        this.field_152868_d = BroadcastController.I["".length()];
        this.field_152869_e = BroadcastController.I[" ".length()];
        this.field_152870_f = BroadcastController.I["  ".length()];
        this.field_152871_g = (" ".length() != 0);
        this.field_152872_h = null;
        this.field_152873_i = null;
        this.field_152874_j = (List<FrameBuffer>)Lists.newArrayList();
        this.field_152875_k = (List<FrameBuffer>)Lists.newArrayList();
        this.field_152876_l = ("".length() != 0);
        this.field_152877_m = ("".length() != 0);
        this.field_152878_n = ("".length() != 0);
        this.broadcastState = BroadcastState.Uninitialized;
        this.field_152880_p = null;
        this.videoParamaters = null;
        this.audioParamaters = null;
        this.ingestList = new IngestList(new IngestServer["".length()]);
        this.field_152884_t = null;
        this.authenticationToken = new AuthToken();
        this.channelInfo = new ChannelInfo();
        this.userInfo = new UserInfo();
        this.streamInfo = new StreamInfo();
        this.field_152889_y = new ArchivingState();
        this.field_152890_z = 0L;
        this.field_152860_A = null;
        this.field_177948_B = (IStreamCallbacks)new IStreamCallbacks() {
            final BroadcastController this$0;
            private static final String[] I;
            
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState field_152889_y) {
                this.this$0.field_152889_y = field_152889_y;
                if (ErrorCode.failed(errorCode)) {}
            }
            
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList ingestList) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.ingestList = ingestList;
                    this.this$0.field_152884_t = this.this$0.ingestList.getDefaultServer();
                    this.this$0.func_152827_a(BroadcastState.ReceivedIngestServers);
                    try {
                        if (this.this$0.broadcastListener == null) {
                            return;
                        }
                        this.this$0.broadcastListener.func_152896_a(ingestList);
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                        return;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152820_d(ex.toString());
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                        return;
                    }
                }
                final String string = ErrorCode.getString(errorCode);
                final BroadcastController this$0 = this.this$0;
                final String s = BroadcastController$1.I["   ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this$0.func_152820_d(String.format(s, array));
                this.this$0.func_152827_a(BroadcastState.LoggingIn);
            }
            
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo streamInfo) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.streamInfo = streamInfo;
                    try {
                        if (this.this$0.broadcastListener == null) {
                            return;
                        }
                        this.this$0.broadcastListener.func_152894_a(streamInfo);
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                        return;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152820_d(ex.toString());
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                        return;
                    }
                }
                final String string = ErrorCode.getString(errorCode);
                final BroadcastController this$0 = this.this$0;
                final String s = BroadcastController$1.I[0x35 ^ 0x30];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this$0.func_152832_e(String.format(s, array));
            }
            
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x96 ^ 0x9B];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            public void startCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    try {
                        if (this.this$0.broadcastListener != null) {
                            this.this$0.broadcastListener.func_152899_b();
                            "".length();
                            if (0 == 2) {
                                throw null;
                            }
                        }
                    }
                    catch (Exception ex) {
                        this.this$0.func_152820_d(ex.toString());
                    }
                    this.this$0.func_152827_a(BroadcastState.Broadcasting);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    this.this$0.videoParamaters = null;
                    this.this$0.audioParamaters = null;
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    try {
                        if (this.this$0.broadcastListener != null) {
                            this.this$0.broadcastListener.func_152892_c(errorCode);
                            "".length();
                            if (4 < 2) {
                                throw null;
                            }
                        }
                    }
                    catch (Exception ex2) {
                        this.this$0.func_152820_d(ex2.toString());
                    }
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x74 ^ 0x7D];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x67 ^ 0x6F];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
                try {
                    if (this.this$0.broadcastListener != null) {
                        final BroadcastListener broadcastListener = this.this$0.broadcastListener;
                        GameInfo[] list2;
                        if (list == null) {
                            list2 = new GameInfo["".length()];
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                        else {
                            list2 = list.list;
                        }
                        broadcastListener.func_152898_a(errorCode, list2);
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    this.this$0.func_152820_d(ex.toString());
                }
            }
            
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.channelInfo = channelInfo;
                    this.this$0.func_152827_a(BroadcastState.LoggedIn);
                    this.this$0.field_152877_m = (" ".length() != 0);
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    this.this$0.field_152877_m = ("".length() != 0);
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I["  ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
                try {
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152897_a(errorCode);
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    this.this$0.func_152820_d(ex.toString());
                }
            }
            
            static {
                I();
            }
            
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.videoParamaters = null;
                    this.this$0.audioParamaters = null;
                    this.this$0.func_152831_M();
                    try {
                        if (this.this$0.broadcastListener != null) {
                            this.this$0.broadcastListener.func_152901_c();
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                    }
                    catch (Exception ex) {
                        this.this$0.func_152820_d(ex.toString());
                    }
                    if (this.this$0.field_152877_m) {
                        this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                    }
                    else {
                        this.this$0.func_152827_a(BroadcastState.Initialized);
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                    }
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0xCE ^ 0xC4];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            public void setStreamInfoCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x30 ^ 0x37];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152832_e(String.format(s, array));
                }
            }
            
            public void bufferUnlockCallback(final long n) {
                this.this$0.field_152875_k.add(FrameBuffer.lookupBuffer(n));
            }
            
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x1F ^ 0x14];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo userInfo) {
                this.this$0.userInfo = userInfo;
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0xB1 ^ 0xB5];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            private static void I() {
                (I = new String[0x31 ^ 0x3F])["".length()] = I("", "MzwOX");
                BroadcastController$1.I[" ".length()] = I("\"\u0002782\u0003\u0013\u00078#\u00183)&2\u001e#)#23\u0006*!5\u0011\u0004-m0\u001f\u0013f+6\u0019\u000b3?2JGc>", "pgFMW");
                BroadcastController$1.I["  ".length()] = I("!\u001a\u000f\u000b\u0003.\u0014\u0004\u000e\u000f\f\u0016\u0003B\n\u0002\u0001H\u0004\f\u0004\u0019\u001d\u0010\bWUM\u0011", "muhbm");
                BroadcastController$1.I["   ".length()] = I("\u001f\u0006\u0014\u0017\u0005\"$\u001a\u0001\u0002\u0015\t\u001f\u001e\u00147\u000b\u0018R\u00119\u001cS\u0014\u0017?\u0004\u0006\u0000\u0013lHV\u0001", "Vhsrv");
                BroadcastController$1.I[0x90 ^ 0x94] = I(":4\u0002\u001e0\u0001!\b(\u0016\u0001\"$\r\u0015\u0003%\u0006\u000f\u0012O \b\u0018Y\t&\u000e\u0000\f\u001d\"]L\\\u001c", "oGgly");
                BroadcastController$1.I[0x87 ^ 0x82] = I("\u0002%\u0018\r\"<\u0018\u0004\u000e,\u0015>\u0004\r\u00000=\u0006\n\"2:J\u000f,%q\f\t*=$\u0018\ryqt\u0019", "QQjhC");
                BroadcastController$1.I[0xC4 ^ 0xC2] = I("?$\u0005\b\u0018\u0000<\u000e9\u0014\u00040\u0007\b\u0016\u0001=\t*\u0014\u0006q\f$\u0003M7\n\"\u001b\u0018#\u000eqWH\"", "mQkKw");
                BroadcastController$1.I[0xF ^ 0x8] = I("\u0007-9=&&-,\u0003\u001b:.\"-38$/\u000f1?h*\u0001&t.,\u0007>!:(Trq;", "THMnR");
                BroadcastController$1.I[0xBC ^ 0xB4] = I("\u0016\t\u000e?<0\u0005\u0006\u0016\u001b\"\u001c ;\u001e=\n\u00029\u0019q\u000f\f.R7\t\n6\u0007#\rYzW\"", "QhcZr");
                BroadcastController$1.I[0x7B ^ 0x72] = I("'6\r:\u0012\u0017#\u0000$\u00045!\u0007h\u0001;6L.\u0007=.\u0019:\u0003nbI;", "TBlHf");
                BroadcastController$1.I[0x51 ^ 0x5B] = I("\u0016\u00118\u0002!\u0004\t;\u0010\u0003\u0006\u000ew\u0015\r\u0011E1\u0013\u000b\t\u0010%\u0017XE@$", "eeWrb");
                BroadcastController$1.I[0x65 ^ 0x6E] = I("\u0018\u001f\u0001)9\b\u000e\u0006\"\u0016&\u001f\u001b,<\n\u000e\u000e\u000e\u0019\u0007\u0016\r,\u001b\u0000Z\b\"\fK\u001c\u000e$\u0014\u001e\b\nwXN\t", "kzoMx");
                BroadcastController$1.I[0x31 ^ 0x3D] = I("\u001d?\u001d!\u001d\u001a;\u00011\u001d\u001e;\u001d\b+\u001a;7$:\u000f\u0019\u0012)\"\f;\u0010.n\t5\u0007e(\u000f3\u001f0<\u000b`S`=", "nZsEN");
                BroadcastController$1.I[0xBD ^ 0xB0] = I("\u0011590\b\f4\u0004$,\f\u001d2 ,&1#5\u000e\u0003<;6,\u0001;w3\"\u0016p15$\u000e%%1wBu$", "bPWTM");
            }
            
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken authenticationToken) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.authenticationToken = authenticationToken;
                    this.this$0.func_152827_a(BroadcastState.Authenticated);
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else {
                    this.this$0.authenticationToken.data = BroadcastController$1.I["".length()];
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[" ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
                try {
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152900_a(errorCode, authenticationToken);
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    this.this$0.func_152820_d(ex.toString());
                }
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x4D ^ 0x41];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152820_d(String.format(s, array));
                }
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void runCommercialCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    final String string = ErrorCode.getString(errorCode);
                    final BroadcastController this$0 = this.this$0;
                    final String s = BroadcastController$1.I[0x94 ^ 0x92];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152832_e(String.format(s, array));
                }
            }
        };
        this.field_177949_C = (IStatCallbacks)new IStatCallbacks() {
            final BroadcastController this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void statCallback(final StatType statType, final long n) {
            }
        };
        this.field_152872_h = Core.getInstance();
        if (Core.getInstance() == null) {
            this.field_152872_h = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.field_152873_i = new Stream((StreamAPI)new DesktopStreamAPI());
    }
    
    public FrameBuffer func_152822_N() {
        if (this.field_152875_k.size() == 0) {
            this.func_152820_d(String.format(BroadcastController.I[0xB2 ^ 0xA5], new Object["".length()]));
            return null;
        }
        final FrameBuffer frameBuffer = this.field_152875_k.get(this.field_152875_k.size() - " ".length());
        this.field_152875_k.remove(this.field_152875_k.size() - " ".length());
        return frameBuffer;
    }
    
    private static void I() {
        (I = new String[0x10 ^ 0x34])["".length()] = I("", "GNAcm");
        BroadcastController.I[" ".length()] = I("", "VcTVH");
        BroadcastController.I["  ".length()] = I("", "URVGu");
        BroadcastController.I["   ".length()] = I("36#\ny5>7\u00196+.{\u001b6-?", "YWUkW");
        BroadcastController.I[0x2A ^ 0x2E] = I("\u00170'?M\"*82\u0003v(&$\u0019v'6w\u001b7):3", "VESWm");
        BroadcastController.I[0x21 ^ 0x24] = I("\u00062 \u0006$2, T'&21T(6a3\u0015&:%", "SAEtJ");
        BroadcastController.I[0x41 ^ 0x47] = I("", "tzAYr");
        BroadcastController.I[0x3E ^ 0x39] = I("", "AdiRk");
        BroadcastController.I[0x6F ^ 0x67] = I("", "erPbz");
        BroadcastController.I[0xAE ^ 0xA7] = I("", "bAELZ");
        BroadcastController.I[0x48 ^ 0x42] = I("\u001d\u001e\u0005\u0006'x\u001b\u001f\u00009=L\u0004\u001d4*\u0018\u001e\u00072x\u0018\u0018I7*\u0003\u0016\r69\u001f\u0003Su}\u001f", "XlwiU");
        BroadcastController.I[0x5E ^ 0x55] = I("\u0015\u001e%\u001f\"p\u001b?\u0019<5L$\u0004? \u001c>\u001e7p\u0018?\u0015p2\u001e8\u001143\r$\u0004jpI$", "PlWpP");
        BroadcastController.I[0x3 ^ 0xF] = I("\u0013\u0011(&=v\u0013;<<?\r=i<\"\u0011?(\"lC\u007f:E", "VcZIO");
        BroadcastController.I[0x6A ^ 0x67] = I(")\u0003\u00056\u001bL\u0006\u001f0\u0005\tQ\u0004<\u0007\b\u0018\u0019>I\u0001\u0014\u00038I\b\u0010\u00038SLT\u0004S", "lqwYi");
        BroadcastController.I[0x91 ^ 0x9F] = I("\u0010%(\u001d=u>4R\u001c09>!;4%.!?49\u0017\u0017;4\u0013;\u0006._", "UWZrO");
        BroadcastController.I[0x47 ^ 0x48] = I("\u0011%7 \u00191/a2\u0010)>$/\u0016=k(%Oxn%K", "XKAAu");
        BroadcastController.I[0x33 ^ 0x23] = I("\u0004\u0000=\u00067a\u001b!I\u0016$\u001c+:1.\u0002\u001c\u0019$/?*\u001d$\u0005\u0013;\b\u007faW<c", "ArOiE");
        BroadcastController.I[0x81 ^ 0x90] = I(")\u0002\"\b!L\u0019>G\u00078&\u000f+<\u000b\u0019>]sI\u0003Z", "lpPgS");
        BroadcastController.I[0x24 ^ 0x36] = I("&\u0015+!*C\u000e7n\f71\u0006\t=\u0017.7)=\u0010\u0013\n+*\u0015\u0002+=bCB*D", "cgYNX");
        BroadcastController.I[0x6C ^ 0x7F] = I("#5\u001a\u0019\u0005F.\u0006V#2\u001171\u0012\u0012\u0012\u001b\u0013\u0005/)\u000e\u0019MFb\u001b|", "fGhvw");
        BroadcastController.I[0x40 ^ 0x54] = I("\u0015 \u0006\u00171p;\u001aX\u0017\u0004\u0004+?&$\u0013\u0006\u001b+9$\u001d\u0016$\u0003&\u0015\f&jrQ\u000bI", "PRtxC");
        BroadcastController.I[0xBB ^ 0xAE] = I("\u0002\u001b\u0018\u000e\u0013g\u0000\u0004A5\u0013?5&\u00043:\u001e\u0013\u0004&\u0004#\u000f\u0007(SJD\u0012", "Gijaa");
        BroadcastController.I[0xA9 ^ 0xBF] = I("\" >\u001e&G%$\u00188\u0002r-\u001d8\b1-\u0005=\t5l\u0017&\u0006?)Q6\u00124*\u0014&", "gRLqT");
        BroadcastController.I[0x78 ^ 0x6F] = I("*&\u001cI-\u0003s\u000e\u001b'\u0000s\n\u001c$\u00036\u001a\u001anE'\u0000\u00001E \u0000\u00067\t7H\u0007'\u00136\u001aI*\u0004#\u0018\f,", "eShiB");
        BroadcastController.I[0x58 ^ 0x40] = I("\"4\u001d/\u001a\u0011f\u0010)T\u00053\u0006+\u001d\u0002f\u0005f\u0012\u0004'\t#T\u0002)D\u0012\u0003\u001f2\u0007.", "vFdFt");
        BroadcastController.I[0x73 ^ 0x6A] = I("\u001b\u0002\t6\u000b:\u0011\u0015#O\n\u0004\u0007#\n", "YpfWo");
        BroadcastController.I[0x6 ^ 0x1C] = I("\r\u0012\"\u0002K3\u0016!\u0019\u00195\u00165V\u000e3\u0001>\u0004\u0018", "AsQvk");
        BroadcastController.I[0xBD ^ 0xA6] = I("\u000b \u0012\u0013#;", "IUtuF");
        BroadcastController.I[0x99 ^ 0x85] = I("*&\u001d&n\u000e!\u001e%+\u001et\u001b,;\u0002 ", "lTxCN");
        BroadcastController.I[0x68 ^ 0x75] = I(".\u0015\u0002\f1\u001f\u0011R\u001a1\u000b\u0012\u0017\nd\u000e\u001b\u0007\u00160", "mtrxD");
        BroadcastController.I[0x4D ^ 0x53] = I("\u000f&\u0018\u000f\u00056 J\b\u0002x\u0014\u001f\u0003\u000113>\u0004\u0014,2\u0018\u0004<7.\u0004\u0015\t*}JD\u001fR", "XGjal");
        BroadcastController.I[0x83 ^ 0x9C] = I("\u001c\n<>%y\u0011 q\u0004,\u001a#8#\r\u001d6%\"+\u001d\u001e>>7\f+#my]=[", "YxNQW");
        BroadcastController.I[0xAD ^ 0x8D] = I("h\u0013\u0019+\u001a&hK", "TVkYu");
        BroadcastController.I[0x2D ^ 0xC] = I("/\u0013\u0000\u001b\u0012\u00102\u0013\u0007\u0007T2\u001d\u001a\u0007\u0006>\u001e\u0018\u0016\u0006\fR\u000f\u000e", "tQrts");
        BroadcastController.I[0x5D ^ 0x7F] = I("z!\u0010\u0007=/\u0018\u0016Ks", "FvquS");
        BroadcastController.I[0xA2 ^ 0x81] = I("(\r5\u0004\u0004\u0017,&\u0018\u0011S,(\u0005\u0011\u0001 +\u0007\u0000\u0001\u0012g\u0010\u0018", "sOGke");
    }
    
    protected void func_152832_e(final String s) {
        BroadcastController.field_152862_C.func_152757_a(BroadcastController.I[0x2B ^ 0x9] + s);
        final Logger logger = BroadcastController.logger;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s2 = BroadcastController.I[0xB ^ 0x28];
        final Object[] array = new Object[" ".length()];
        array["".length()] = s;
        logger.warn(stream_MARKER, s2, array);
    }
    
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
    
    public boolean func_152817_A() {
        if (this.field_152876_l) {
            return "".length() != 0;
        }
        this.field_152873_i.setStreamCallbacks(this.field_177948_B);
        final ErrorCode initialize = this.field_152872_h.initialize(this.field_152868_d, System.getProperty(BroadcastController.I["   ".length()]));
        if (!this.func_152853_a(initialize)) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.errorCode = initialize;
            return "".length() != 0;
        }
        final ErrorCode setTraceLevel = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
        if (!this.func_152853_a(setTraceLevel)) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.field_152872_h.shutdown();
            this.errorCode = setTraceLevel;
            return "".length() != 0;
        }
        if (ErrorCode.succeeded(setTraceLevel)) {
            this.field_152876_l = (" ".length() != 0);
            this.func_152827_a(BroadcastState.Initialized);
            return " ".length() != 0;
        }
        this.errorCode = setTraceLevel;
        this.field_152872_h.shutdown();
        return "".length() != 0;
    }
    
    protected boolean func_152823_L() {
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < "   ".length()) {
            final FrameBuffer allocateFrameBuffer = this.field_152873_i.allocateFrameBuffer(this.videoParamaters.outputWidth * this.videoParamaters.outputHeight * (0x2D ^ 0x29));
            if (!allocateFrameBuffer.getIsValid()) {
                this.func_152820_d(String.format(BroadcastController.I[0x41 ^ 0x57], new Object["".length()]));
                return "".length() != 0;
            }
            this.field_152874_j.add(allocateFrameBuffer);
            this.field_152875_k.add(allocateFrameBuffer);
            ++i;
        }
        return " ".length() != 0;
    }
    
    public boolean func_152818_a(final String field_152880_p, final AuthToken authenticationToken) {
        if (this.isIngestTesting()) {
            return "".length() != 0;
        }
        this.func_152845_C();
        if (field_152880_p == null || field_152880_p.isEmpty()) {
            this.func_152820_d(BroadcastController.I[0x7D ^ 0x78]);
            return "".length() != 0;
        }
        if (authenticationToken != null && authenticationToken.data != null && !authenticationToken.data.isEmpty()) {
            this.field_152880_p = field_152880_p;
            this.authenticationToken = authenticationToken;
            if (this.func_152858_b()) {
                this.func_152827_a(BroadcastState.Authenticated);
            }
            return " ".length() != 0;
        }
        this.func_152820_d(BroadcastController.I[0x8B ^ 0x8F]);
        return "".length() != 0;
    }
    
    public boolean func_152858_b() {
        return this.field_152876_l;
    }
    
    public void statCallback() {
        if (this.broadcastState != BroadcastState.Uninitialized) {
            if (this.field_152860_A != null) {
                this.field_152860_A.func_153039_l();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            while (this.field_152860_A != null) {
                try {
                    Thread.sleep(200L);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    this.func_152820_d(ex.toString());
                }
                this.func_152821_H();
            }
            this.func_152851_B();
        }
    }
    
    public boolean func_152845_C() {
        if (this.isIngestTesting()) {
            return "".length() != 0;
        }
        if (this.isBroadcasting()) {
            this.field_152873_i.stop((boolean)("".length() != 0));
        }
        this.field_152880_p = BroadcastController.I[0x18 ^ 0x1E];
        this.authenticationToken = new AuthToken();
        if (!this.field_152877_m) {
            return "".length() != 0;
        }
        this.field_152877_m = ("".length() != 0);
        if (!this.field_152878_n) {
            try {
                if (this.broadcastListener != null) {
                    this.broadcastListener.func_152895_a();
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.func_152820_d(ex.toString());
            }
        }
        this.func_152827_a(BroadcastState.Initialized);
        return " ".length() != 0;
    }
    
    public boolean func_152828_a(String field_152880_p, String gameName, String streamTitle) {
        if (!this.field_152877_m) {
            return "".length() != 0;
        }
        if (field_152880_p == null || field_152880_p.equals(BroadcastController.I[0x3F ^ 0x38])) {
            field_152880_p = this.field_152880_p;
        }
        if (gameName == null) {
            gameName = BroadcastController.I[0xC ^ 0x4];
        }
        if (streamTitle == null) {
            streamTitle = BroadcastController.I[0x87 ^ 0x8E];
        }
        final StreamInfoForSetting streamInfoForSetting = new StreamInfoForSetting();
        streamInfoForSetting.streamTitle = streamTitle;
        streamInfoForSetting.gameName = gameName;
        final ErrorCode setStreamInfo = this.field_152873_i.setStreamInfo(this.authenticationToken, field_152880_p, streamInfoForSetting);
        this.func_152853_a(setStreamInfo);
        return ErrorCode.succeeded(setStreamInfo);
    }
    
    public void func_152824_a(final IngestServer field_152884_t) {
        this.field_152884_t = field_152884_t;
    }
    
    protected void func_152827_a(final BroadcastState broadcastState) {
        if (broadcastState != this.broadcastState) {
            this.broadcastState = broadcastState;
            try {
                if (this.broadcastListener != null) {
                    this.broadcastListener.func_152891_a(broadcastState);
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.func_152820_d(ex.toString());
            }
        }
    }
    
    public IngestList func_152855_t() {
        return this.ingestList;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        field_152862_C = new ThreadSafeBoundList<String>(String.class, 0x47 ^ 0x75);
    }
    
    public long func_152844_x() {
        return this.field_152873_i.getStreamTime();
    }
    
    public ChannelInfo getChannelInfo() {
        return this.channelInfo;
    }
    
    protected void func_152835_I() {
        final long nanoTime = System.nanoTime();
        if ((nanoTime - this.field_152890_z) / 1000000000L >= 30L) {
            this.field_152890_z = nanoTime;
            final ErrorCode streamInfo = this.field_152873_i.getStreamInfo(this.authenticationToken, this.field_152880_p);
            if (ErrorCode.failed(streamInfo)) {
                final String string = ErrorCode.getString(streamInfo);
                final String s = BroadcastController.I[0x48 ^ 0x5D];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this.func_152820_d(String.format(s, array));
            }
        }
    }
    
    public IngestServer func_152833_s() {
        return this.field_152884_t;
    }
    
    public void setPlaybackDeviceVolume(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, n);
    }
    
    public interface BroadcastListener
    {
        void func_152901_c();
        
        void func_152894_a(final StreamInfo p0);
        
        void func_152891_a(final BroadcastState p0);
        
        void func_152899_b();
        
        void func_152892_c(final ErrorCode p0);
        
        void func_152893_b(final ErrorCode p0);
        
        void func_152900_a(final ErrorCode p0, final AuthToken p1);
        
        void func_152897_a(final ErrorCode p0);
        
        void func_152896_a(final IngestList p0);
        
        void func_152895_a();
        
        void func_152898_a(final ErrorCode p0, final GameInfo[] p1);
    }
    
    public enum BroadcastState
    {
        Paused(BroadcastState.I[0x1B ^ 0x17], 0x2 ^ 0xE);
        
        private static final BroadcastState[] ENUM$VALUES;
        
        Authenticating(BroadcastState.I["  ".length()], "  ".length()), 
        FindingIngestServer(BroadcastState.I[0xBA ^ 0xBC], 0xB0 ^ 0xB6), 
        Authenticated(BroadcastState.I["   ".length()], "   ".length()), 
        Uninitialized(BroadcastState.I["".length()], "".length()), 
        LoggedIn(BroadcastState.I[0xC5 ^ 0xC0], 0xA4 ^ 0xA1), 
        Starting(BroadcastState.I[0x6B ^ 0x62], 0x71 ^ 0x78), 
        IngestTesting(BroadcastState.I[0xA9 ^ 0xA4], 0x79 ^ 0x74);
        
        private static final String[] I;
        
        ReceivedIngestServers(BroadcastState.I[0x8C ^ 0x8B], 0x81 ^ 0x86), 
        LoggingIn(BroadcastState.I[0x26 ^ 0x22], 0x44 ^ 0x40), 
        ReadyToBroadcast(BroadcastState.I[0xB2 ^ 0xBA], 0x1A ^ 0x12), 
        Broadcasting(BroadcastState.I[0x1A ^ 0x10], 0x30 ^ 0x3A), 
        Initialized(BroadcastState.I[" ".length()], " ".length()), 
        Stopping(BroadcastState.I[0x3B ^ 0x30], 0x10 ^ 0x1B);
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final BroadcastState[] enum$VALUES = new BroadcastState[0x0 ^ 0xE];
            enum$VALUES["".length()] = BroadcastState.Uninitialized;
            enum$VALUES[" ".length()] = BroadcastState.Initialized;
            enum$VALUES["  ".length()] = BroadcastState.Authenticating;
            enum$VALUES["   ".length()] = BroadcastState.Authenticated;
            enum$VALUES[0x3C ^ 0x38] = BroadcastState.LoggingIn;
            enum$VALUES[0x2C ^ 0x29] = BroadcastState.LoggedIn;
            enum$VALUES[0x7E ^ 0x78] = BroadcastState.FindingIngestServer;
            enum$VALUES[0x5F ^ 0x58] = BroadcastState.ReceivedIngestServers;
            enum$VALUES[0x45 ^ 0x4D] = BroadcastState.ReadyToBroadcast;
            enum$VALUES[0xF ^ 0x6] = BroadcastState.Starting;
            enum$VALUES[0x81 ^ 0x8B] = BroadcastState.Broadcasting;
            enum$VALUES[0x7D ^ 0x76] = BroadcastState.Stopping;
            enum$VALUES[0x42 ^ 0x4E] = BroadcastState.Paused;
            enum$VALUES[0x81 ^ 0x8C] = BroadcastState.IngestTesting;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x3C ^ 0x32])["".length()] = I("#\"%>*\u0002%-<*\f)(", "vLLPC");
            BroadcastState.I[" ".length()] = I("/)#<\u0011\u0007+#2\u001d\u0002", "fGJHx");
            BroadcastState.I["  ".length()] = I("\u000f\f#?\r \r>4\t:\u001090", "NyWWh");
            BroadcastState.I["   ".length()] = I("\u0015/\u001a2(:.\u00079, ?\n", "TZnZM");
            BroadcastState.I[0x19 ^ 0x1D] = I("\u000b\u00007&!)\b\u0019/", "GoPAH");
            BroadcastState.I[0xA6 ^ 0xA3] = I("(\u000054!\u0000&<", "doRSD");
            BroadcastState.I[0x30 ^ 0x36] = I("\n\r\u001a% \"\u0003=/.)\u0017\u0000\u0012,>\u0012\u00113", "LdtAI");
            BroadcastState.I[0x42 ^ 0x45] = I("?\u0001).\u001c\u001b\u0001.\u0002\u001b\n\u00019?&\b\u0016<.\u0007\u001e", "mdJKu");
            BroadcastState.I[0xCA ^ 0xC2] = I("1\u0015/\u0017\u00127\u001f\f\u0001\u0004\u0002\u0014-\u0012\u0018\u0017", "cpNsk");
            BroadcastState.I[0x24 ^ 0x2D] = I("\u0000\u00195\u0001<:\u00033", "SmTsH");
            BroadcastState.I[0x78 ^ 0x72] = I("$\u0006<\u0019\n\u0005\u0015 \f\u0007\b\u0013", "ftSxn");
            BroadcastState.I[0x36 ^ 0x3D] = I("9\u0002\u000455\u0003\u0018\f", "jvkEE");
            BroadcastState.I[0x6 ^ 0xA] = I("'\u000f6\u000b\u0010\u0013", "wnCxu");
            BroadcastState.I[0x73 ^ 0x7E] = I("\u0003:\u0013\n8>\u0000\u0011\u001c?#:\u0013", "JTtoK");
        }
        
        private BroadcastState(final String s, final int n) {
        }
    }
}
