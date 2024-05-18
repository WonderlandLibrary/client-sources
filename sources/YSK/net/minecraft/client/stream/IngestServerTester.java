package net.minecraft.client.stream;

import java.util.*;
import com.google.common.collect.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;

public class IngestServerTester
{
    protected boolean field_153060_r;
    protected long field_153050_h;
    protected long field_153049_g;
    protected IngestTestListener field_153044_b;
    protected long field_153054_l;
    protected VideoParams field_153052_j;
    private static final String[] I;
    protected int field_153063_u;
    private static int[] $SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState;
    protected IngestServer field_153059_q;
    protected float field_153066_x;
    protected long field_153048_f;
    protected long field_153064_v;
    protected boolean field_153056_n;
    protected IStatCallbacks field_176006_B;
    protected IStreamCallbacks field_176005_A;
    protected List<FrameBuffer> field_153055_m;
    protected boolean field_176007_z;
    protected IStreamCallbacks field_153057_o;
    protected int field_153062_t;
    protected boolean field_176009_x;
    protected IngestList field_153046_d;
    protected IStatCallbacks field_153058_p;
    protected boolean field_153061_s;
    protected AudioParams audioParameters;
    protected IngestTestState field_153047_e;
    protected boolean field_176008_y;
    protected float field_153065_w;
    protected Stream field_153045_c;
    protected RTMPState field_153051_i;
    
    public IngestServer func_153040_c() {
        return this.field_153059_q;
    }
    
    protected boolean func_153029_c(final IngestServer ingestServer) {
        if (this.field_153061_s || this.field_153060_r || this.func_153037_m() >= this.field_153048_f) {
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return " ".length() != 0;
        }
        if (this.field_176008_y || this.field_176007_z) {
            return " ".length() != 0;
        }
        if (ErrorCode.failed(this.field_153045_c.submitVideoFrame((FrameBuffer)this.field_153055_m.get(this.field_153063_u)))) {
            this.field_153056_n = ("".length() != 0);
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return "".length() != 0;
        }
        this.field_153063_u = (this.field_153063_u + " ".length()) % this.field_153055_m.size();
        this.field_153045_c.pollStats();
        if (this.field_153051_i == RTMPState.SendVideo) {
            this.func_153034_a(IngestTestState.TestingServer);
            if (this.func_153037_m() > 0L && this.field_153050_h > this.field_153064_v) {
                ingestServer.bitrateKbps = this.field_153050_h * 8L / this.func_153037_m();
                this.field_153064_v = this.field_153050_h;
            }
        }
        return " ".length() != 0;
    }
    
    public boolean func_153032_e() {
        if (this.field_153047_e != IngestTestState.Finished && this.field_153047_e != IngestTestState.Cancelled && this.field_153047_e != IngestTestState.Failed) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    public void func_153042_a(final IngestTestListener field_153044_b) {
        this.field_153044_b = field_153044_b;
    }
    
    protected long func_153037_m() {
        return System.currentTimeMillis() - this.field_153054_l;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("&\u0015\u001b\tN\u0013\u0000\u001d\u0015\u000b\u0011[T", "uatyn");
    }
    
    public void func_176004_j() {
        if (this.field_153047_e == IngestTestState.Uninitalized) {
            this.field_153062_t = "".length();
            this.field_153060_r = ("".length() != 0);
            this.field_153061_s = ("".length() != 0);
            this.field_176009_x = ("".length() != 0);
            this.field_176008_y = ("".length() != 0);
            this.field_176007_z = ("".length() != 0);
            this.field_153058_p = this.field_153045_c.getStatCallbacks();
            this.field_153045_c.setStatCallbacks(this.field_176006_B);
            this.field_153057_o = this.field_153045_c.getStreamCallbacks();
            this.field_153045_c.setStreamCallbacks(this.field_176005_A);
            this.field_153052_j = new VideoParams();
            this.field_153052_j.targetFps = (0xAF ^ 0x93);
            this.field_153052_j.maxKbps = 2157 + 657 - 2482 + 3168;
            this.field_153052_j.outputWidth = 216 + 1005 - 101 + 160;
            this.field_153052_j.outputHeight = 339 + 532 - 377 + 226;
            this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
            this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
            this.field_153052_j.disableAdaptiveBitrate = (" ".length() != 0);
            this.field_153052_j.verticalFlip = ("".length() != 0);
            this.field_153045_c.getDefaultParams(this.field_153052_j);
            this.audioParameters = new AudioParams();
            this.audioParameters.audioEnabled = ("".length() != 0);
            this.audioParameters.enableMicCapture = ("".length() != 0);
            this.audioParameters.enablePlaybackCapture = ("".length() != 0);
            this.audioParameters.enablePassthroughAudio = ("".length() != 0);
            this.field_153055_m = (List<FrameBuffer>)Lists.newArrayList();
            final int length = "   ".length();
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < length) {
                final FrameBuffer allocateFrameBuffer = this.field_153045_c.allocateFrameBuffer(this.field_153052_j.outputWidth * this.field_153052_j.outputHeight * (0x16 ^ 0x12));
                if (!allocateFrameBuffer.getIsValid()) {
                    this.func_153031_o();
                    this.func_153034_a(IngestTestState.Failed);
                    return;
                }
                this.field_153055_m.add(allocateFrameBuffer);
                this.field_153045_c.randomizeFrameBuffer(allocateFrameBuffer);
                ++i;
            }
            this.func_153034_a(IngestTestState.Starting);
            this.field_153054_l = System.currentTimeMillis();
        }
    }
    
    public void func_153041_j() {
        if (!this.func_153032_e() && this.field_153047_e != IngestTestState.Uninitalized && !this.field_176008_y && !this.field_176007_z) {
            switch ($SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState()[this.field_153047_e.ordinal()]) {
                case 2:
                case 5: {
                    if (this.field_153059_q != null) {
                        if (this.field_153061_s || !this.field_153056_n) {
                            this.field_153059_q.bitrateKbps = 0.0f;
                        }
                        this.func_153035_b(this.field_153059_q);
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        this.field_153054_l = 0L;
                        this.field_153061_s = ("".length() != 0);
                        this.field_153056_n = (" ".length() != 0);
                        if (this.field_153047_e != IngestTestState.Starting) {
                            this.field_153062_t += " ".length();
                        }
                        if (this.field_153062_t < this.field_153046_d.getServers().length) {
                            this.func_153036_a(this.field_153059_q = this.field_153046_d.getServers()[this.field_153062_t]);
                            "".length();
                            if (2 <= 1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            this.func_153034_a(IngestTestState.Finished);
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                            break;
                        }
                    }
                    break;
                }
                case 3:
                case 4: {
                    this.func_153029_c(this.field_153059_q);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    break;
                }
                case 7: {
                    this.func_153034_a(IngestTestState.Cancelled);
                    break;
                }
            }
            this.func_153038_n();
            if (this.field_153047_e == IngestTestState.Cancelled || this.field_153047_e == IngestTestState.Finished) {
                this.func_153031_o();
            }
        }
    }
    
    protected boolean func_153036_a(final IngestServer field_153059_q) {
        this.field_153056_n = (" ".length() != 0);
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Idle;
        this.field_153059_q = field_153059_q;
        this.field_176008_y = (" ".length() != 0);
        this.func_153034_a(IngestTestState.ConnectingToServer);
        if (ErrorCode.failed(this.field_153045_c.start(this.field_153052_j, this.audioParameters, field_153059_q, StartFlags.TTV_Start_BandwidthTest, (boolean)(" ".length() != 0)))) {
            this.field_176008_y = ("".length() != 0);
            this.field_153056_n = ("".length() != 0);
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return "".length() != 0;
        }
        this.field_153064_v = this.field_153050_h;
        field_153059_q.bitrateKbps = 0.0f;
        this.field_153063_u = "".length();
        return " ".length() != 0;
    }
    
    public IngestServerTester(final Stream field_153045_c, final IngestList field_153046_d) {
        this.field_153044_b = null;
        this.field_153045_c = null;
        this.field_153046_d = null;
        this.field_153047_e = IngestTestState.Uninitalized;
        this.field_153048_f = 8000L;
        this.field_153049_g = 2000L;
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Invalid;
        this.field_153052_j = null;
        this.audioParameters = null;
        this.field_153054_l = 0L;
        this.field_153055_m = null;
        this.field_153056_n = ("".length() != 0);
        this.field_153057_o = null;
        this.field_153058_p = null;
        this.field_153059_q = null;
        this.field_153060_r = ("".length() != 0);
        this.field_153061_s = ("".length() != 0);
        this.field_153062_t = -" ".length();
        this.field_153063_u = "".length();
        this.field_153064_v = 0L;
        this.field_153065_w = 0.0f;
        this.field_153066_x = 0.0f;
        this.field_176009_x = ("".length() != 0);
        this.field_176008_y = ("".length() != 0);
        this.field_176007_z = ("".length() != 0);
        this.field_176005_A = (IStreamCallbacks)new IStreamCallbacks() {
            final IngestServerTester this$0;
            private static final String[] I;
            
            public void setStreamInfoCallback(final ErrorCode errorCode) {
            }
            
            public void bufferUnlockCallback(final long n) {
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
            }
            
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo userInfo) {
            }
            
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    System.out.println(IngestServerTester$1.I["".length()] + this.this$0.field_153059_q.serverName + IngestServerTester$1.I[" ".length()] + errorCode.toString());
                }
                this.this$0.field_176007_z = ("".length() != 0);
                this.this$0.field_176009_x = ("".length() != 0);
                this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                this.this$0.field_153059_q = null;
                if (this.this$0.field_153060_r) {
                    this.this$0.func_153034_a(IngestTestState.Cancelling);
                }
            }
            
            static {
                I();
            }
            
            public void startCallback(final ErrorCode errorCode) {
                this.this$0.field_176008_y = ("".length() != 0);
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_176009_x = (" ".length() != 0);
                    this.this$0.field_153054_l = System.currentTimeMillis();
                    this.this$0.func_153034_a(IngestTestState.ConnectingToServer);
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    this.this$0.field_153056_n = ("".length() != 0);
                    this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                }
            }
            
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo streamInfo) {
            }
            
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList list) {
            }
            
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken authToken) {
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("#\u000b\u0005\u0002\u0011\u001e1\u0007\u0014\u0016\u000f\u0017L\u0014\u0016\u0005\u0015!\u0006\u000e\u0006\u0007\u0003\u0004\tJ\u0003\u0003\u000e\u000e\u000f\u0001B\u0013\rJ\u0016\u0016\b\u0012JHB", "jebgb");
                IngestServerTester$1.I[" ".length()] = I("}H", "GhbAt");
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
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void runCommercialCallback(final ErrorCode errorCode) {
            }
            
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
            }
            
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
            }
            
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
            }
            
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState archivingState) {
            }
            
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
            }
        };
        this.field_176006_B = (IStatCallbacks)new IStatCallbacks() {
            final IngestServerTester this$0;
            private static int[] $SWITCH_TABLE$tv$twitch$broadcast$StatType;
            
            static int[] $SWITCH_TABLE$tv$twitch$broadcast$StatType() {
                final int[] $switch_TABLE$tv$twitch$broadcast$StatType = IngestServerTester$2.$SWITCH_TABLE$tv$twitch$broadcast$StatType;
                if ($switch_TABLE$tv$twitch$broadcast$StatType != null) {
                    return $switch_TABLE$tv$twitch$broadcast$StatType;
                }
                final int[] $switch_TABLE$tv$twitch$broadcast$StatType2 = new int[StatType.values().length];
                try {
                    $switch_TABLE$tv$twitch$broadcast$StatType2[StatType.TTV_ST_RTMPDATASENT.ordinal()] = "  ".length();
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    $switch_TABLE$tv$twitch$broadcast$StatType2[StatType.TTV_ST_RTMPSTATE.ordinal()] = " ".length();
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError2) {}
                return IngestServerTester$2.$SWITCH_TABLE$tv$twitch$broadcast$StatType = $switch_TABLE$tv$twitch$broadcast$StatType2;
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
                    if (3 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void statCallback(final StatType statType, final long field_153050_h) {
                switch ($SWITCH_TABLE$tv$twitch$broadcast$StatType()[statType.ordinal()]) {
                    case 1: {
                        this.this$0.field_153051_i = RTMPState.lookupValue((int)field_153050_h);
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                        break;
                    }
                    case 2: {
                        this.this$0.field_153050_h = field_153050_h;
                        break;
                    }
                }
            }
        };
        this.field_153045_c = field_153045_c;
        this.field_153046_d = field_153046_d;
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState() {
        final int[] $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState = IngestServerTester.$SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState;
        if ($switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState != null) {
            return $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState;
        }
        final int[] $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2 = new int[IngestTestState.values().length];
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Cancelled.ordinal()] = (0xA6 ^ 0xAE);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Cancelling.ordinal()] = (0x48 ^ 0x4F);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.ConnectingToServer.ordinal()] = "   ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.DoneTestingServer.ordinal()] = (0xBE ^ 0xBB);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Failed.ordinal()] = (0xA4 ^ 0xAD);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Finished.ordinal()] = (0x96 ^ 0x90);
            "".length();
            if (!true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Starting.ordinal()] = "  ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.TestingServer.ordinal()] = (0x85 ^ 0x81);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2[IngestTestState.Uninitalized.ordinal()] = " ".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        return IngestServerTester.$SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState = $switch_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState2;
    }
    
    protected void func_153038_n() {
        final float n = this.func_153037_m();
        switch ($SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState()[this.field_153047_e.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
            case 9: {
                this.field_153066_x = 0.0f;
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.field_153066_x = 1.0f;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
                break;
            }
            default: {
                this.field_153066_x = n / this.field_153048_f;
                break;
            }
        }
        switch ($SWITCH_TABLE$net$minecraft$client$stream$IngestServerTester$IngestTestState()[this.field_153047_e.ordinal()]) {
            case 6:
            case 8:
            case 9: {
                this.field_153065_w = 1.0f;
                "".length();
                if (4 < 2) {
                    throw null;
                }
                break;
            }
            default: {
                this.field_153065_w = this.field_153062_t / this.field_153046_d.getServers().length;
                this.field_153065_w += this.field_153066_x / this.field_153046_d.getServers().length;
                break;
            }
        }
    }
    
    protected void func_153031_o() {
        this.field_153059_q = null;
        if (this.field_153055_m != null) {
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < this.field_153055_m.size()) {
                this.field_153055_m.get(i).free();
                ++i;
            }
            this.field_153055_m = null;
        }
        if (this.field_153045_c.getStatCallbacks() == this.field_176006_B) {
            this.field_153045_c.setStatCallbacks(this.field_153058_p);
            this.field_153058_p = null;
        }
        if (this.field_153045_c.getStreamCallbacks() == this.field_176005_A) {
            this.field_153045_c.setStreamCallbacks(this.field_153057_o);
            this.field_153057_o = null;
        }
    }
    
    public void func_153039_l() {
        if (!this.func_153032_e() && !this.field_153060_r) {
            this.field_153060_r = (" ".length() != 0);
            if (this.field_153059_q != null) {
                this.field_153059_q.bitrateKbps = 0.0f;
            }
        }
    }
    
    public float func_153030_h() {
        return this.field_153066_x;
    }
    
    public int func_153028_p() {
        return this.field_153062_t;
    }
    
    protected void func_153034_a(final IngestTestState field_153047_e) {
        if (field_153047_e != this.field_153047_e) {
            this.field_153047_e = field_153047_e;
            if (this.field_153044_b != null) {
                this.field_153044_b.func_152907_a(this, field_153047_e);
            }
        }
    }
    
    protected void func_153035_b(final IngestServer ingestServer) {
        if (this.field_176008_y) {
            this.field_153061_s = (" ".length() != 0);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.field_176009_x) {
            this.field_176007_z = (" ".length() != 0);
            final ErrorCode stop = this.field_153045_c.stop((boolean)(" ".length() != 0));
            if (ErrorCode.failed(stop)) {
                this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
                System.out.println(IngestServerTester.I["".length()] + stop.toString());
            }
            this.field_153045_c.pollStats();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
        }
    }
    
    public interface IngestTestListener
    {
        void func_152907_a(final IngestServerTester p0, final IngestTestState p1);
    }
    
    public enum IngestTestState
    {
        ConnectingToServer(IngestTestState.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        Failed(IngestTestState.I[0x9A ^ 0x92], 0x82 ^ 0x8A), 
        Finished(IngestTestState.I[0x6C ^ 0x69], 0xA5 ^ 0xA0), 
        Cancelling(IngestTestState.I[0xBA ^ 0xBC], 0x63 ^ 0x65);
        
        private static final IngestTestState[] ENUM$VALUES;
        
        Uninitalized(IngestTestState.I["".length()], "".length()), 
        TestingServer(IngestTestState.I["   ".length()], "   ".length()), 
        Starting(IngestTestState.I[" ".length()], " ".length()), 
        DoneTestingServer(IngestTestState.I[0x36 ^ 0x32], 0x86 ^ 0x82), 
        Cancelled(IngestTestState.I[0x18 ^ 0x1F], 0x96 ^ 0x91);
        
        private IngestTestState(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0x73 ^ 0x7A])["".length()] = I("\u001a-%6\u001c;\" 1\u000f*'", "OCLXu");
            IngestTestState.I[" ".length()] = I("\u00001$\u001c\u0003:+\"", "SEEnw");
            IngestTestState.I["  ".length()] = I("\u0010)-\u001d*02*\u001d(\u0007)\u0010\u0016=%#1", "SFCsO");
            IngestTestState.I["   ".length()] = I("\u0019\u001c\u0000''#\u001e 6<;\u001c\u0001", "MysSN");
            IngestTestState.I[0x30 ^ 0x34] = I(",\b\u0000\u00018\r\u0014\u001a\r\u0002\u000f4\u000b\u0016\u001a\r\u0015", "hgndl");
            IngestTestState.I[0x55 ^ 0x50] = I("6\u001c$.\u001d\u0018\u0010.", "puJGn");
            IngestTestState.I[0x60 ^ 0x66] = I("4\u000b%,\u0013\u001b\u0006\"!\u0011", "wjKOv");
            IngestTestState.I[0x8C ^ 0x8B] = I("\u001b\";\u0007?4/0\u0000", "XCUdZ");
            IngestTestState.I[0x6C ^ 0x64] = I("6 \u0018\u0000$\u0014", "pAqlA");
        }
        
        static {
            I();
            final IngestTestState[] enum$VALUES = new IngestTestState[0x34 ^ 0x3D];
            enum$VALUES["".length()] = IngestTestState.Uninitalized;
            enum$VALUES[" ".length()] = IngestTestState.Starting;
            enum$VALUES["  ".length()] = IngestTestState.ConnectingToServer;
            enum$VALUES["   ".length()] = IngestTestState.TestingServer;
            enum$VALUES[0xBC ^ 0xB8] = IngestTestState.DoneTestingServer;
            enum$VALUES[0x47 ^ 0x42] = IngestTestState.Finished;
            enum$VALUES[0x68 ^ 0x6E] = IngestTestState.Cancelling;
            enum$VALUES[0x23 ^ 0x24] = IngestTestState.Cancelled;
            enum$VALUES[0x49 ^ 0x41] = IngestTestState.Failed;
            ENUM$VALUES = enum$VALUES;
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
