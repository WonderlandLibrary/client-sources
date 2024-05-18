package HORIZON-6-0-SKIDPROTECTION;

import tv.twitch.chat.ChatUserSubscription;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatTokenizedMessage;
import java.util.Iterator;
import java.util.Set;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import org.lwjgl.opengl.GL11;
import com.google.gson.JsonObject;
import java.io.IOException;
import tv.twitch.AuthToken;
import com.google.gson.JsonParser;
import java.net.URL;
import java.net.URLEncoder;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mojang.authlib.properties.Property;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Logger;

public class TwitchStream implements BroadcastController.HorizonCode_Horizon_È, IngestServerTester.HorizonCode_Horizon_È, IStream, ChatController.Â
{
    private static final Logger Â;
    public static final Marker HorizonCode_Horizon_È;
    private final BroadcastController Ý;
    private final ChatController Ø­áŒŠá;
    private String Âµá€;
    private final Minecraft Ó;
    private final IChatComponent à;
    private final Map Ø;
    private Framebuffer áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private int ÂµÈ;
    private long á;
    private boolean ˆÏ­;
    private boolean £á;
    private boolean Å;
    private boolean £à;
    private IStream.HorizonCode_Horizon_È µà;
    private static boolean ˆà;
    private static final String ¥Æ = "CL_00001812";
    
    static {
        Â = LogManager.getLogger();
        HorizonCode_Horizon_È = MarkerManager.getMarker("STREAM");
        try {
            if (Util_1252169911.HorizonCode_Horizon_È() == Util_1252169911.HorizonCode_Horizon_È.Ý) {
                System.loadLibrary("avutil-ttv-51");
                System.loadLibrary("swresample-ttv-0");
                System.loadLibrary("libmp3lame-ttv");
                if (System.getProperty("os.arch").contains("64")) {
                    System.loadLibrary("libmfxsw64");
                }
                else {
                    System.loadLibrary("libmfxsw32");
                }
            }
            TwitchStream.ˆà = true;
        }
        catch (Throwable var1) {
            TwitchStream.ˆà = false;
        }
    }
    
    public TwitchStream(final Minecraft mcIn, final Property p_i46057_2_) {
        this.à = new ChatComponentText("Twitch");
        this.Ø = Maps.newHashMap();
        this.ÂµÈ = 30;
        this.á = 0L;
        this.ˆÏ­ = false;
        this.µà = IStream.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.Ó = mcIn;
        this.Ý = new BroadcastController();
        this.Ø­áŒŠá = new ChatController();
        this.Ý.HorizonCode_Horizon_È(this);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this);
        this.Ý.HorizonCode_Horizon_È("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.Ø­áŒŠá.HorizonCode_Horizon_È("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.à.à().HorizonCode_Horizon_È(EnumChatFormatting.Ó);
        if (p_i46057_2_ != null && !Strings.isNullOrEmpty(p_i46057_2_.getValue()) && OpenGlHelper.ÂµÈ) {
            final Thread var3 = new Thread("Twitch authenticator") {
                private static final String Â = "CL_00001811";
                
                @Override
                public void run() {
                    try {
                        final URL var1 = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(p_i46057_2_.getValue(), "UTF-8"));
                        final String var2 = HttpUtil.HorizonCode_Horizon_È(var1);
                        final JsonObject var3 = JsonUtils.Âµá€(new JsonParser().parse(var2), "Response");
                        final JsonObject var4 = JsonUtils.áˆºÑ¢Õ(var3, "token");
                        if (JsonUtils.à(var4, "valid")) {
                            final String var5 = JsonUtils.Ó(var4, "user_name");
                            TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Authenticated with twitch; username is {}", new Object[] { var5 });
                            final AuthToken var6 = new AuthToken();
                            var6.data = p_i46057_2_.getValue();
                            TwitchStream.this.Ý.HorizonCode_Horizon_È(var5, var6);
                            TwitchStream.this.Ø­áŒŠá.Â(var5);
                            TwitchStream.this.Ø­áŒŠá.HorizonCode_Horizon_È(var6);
                            Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
                                private static final String Â = "CL_00001810";
                                
                                @Override
                                public void run() {
                                    TwitchStream.this.Ó();
                                }
                            });
                            TwitchStream.this.Ý.£à();
                            TwitchStream.this.Ø­áŒŠá.Â();
                        }
                        else {
                            TwitchStream.HorizonCode_Horizon_È(TwitchStream.this, IStream.HorizonCode_Horizon_È.Â);
                            TwitchStream.Â.error(TwitchStream.HorizonCode_Horizon_È, "Given twitch access token is invalid");
                        }
                    }
                    catch (IOException var7) {
                        TwitchStream.HorizonCode_Horizon_È(TwitchStream.this, IStream.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                        TwitchStream.Â.error(TwitchStream.HorizonCode_Horizon_È, "Could not authenticate with twitch", (Throwable)var7);
                    }
                }
            };
            var3.setDaemon(true);
            var3.start();
        }
    }
    
    @Override
    public void Ó() {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Shutdown streaming");
        this.Ý.ˆà();
        this.Ø­áŒŠá.Ø­áŒŠá();
    }
    
    @Override
    public void à() {
        final int var1 = this.Ó.ŠÄ.ÇŽÄ;
        final boolean var2 = this.Âµá€ != null && this.Ø­áŒŠá.Ý(this.Âµá€);
        final boolean var3 = this.Ø­áŒŠá.HorizonCode_Horizon_È() == ChatController.Ý.Ý && (this.Âµá€ == null || this.Ø­áŒŠá.Ø­áŒŠá(this.Âµá€) == ChatController.Ø­áŒŠá.Âµá€);
        if (var1 == 2) {
            if (var2) {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Disconnecting from twitch chat per user options");
                this.Ø­áŒŠá.Ó(this.Âµá€);
            }
        }
        else if (var1 == 1) {
            if (var3 && this.Ý.Ø()) {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Connecting to twitch chat per user options");
                this.ÇŽÉ();
            }
        }
        else if (var1 == 0) {
            if (var2 && !this.ÂµÈ()) {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Disconnecting from twitch chat as user is no longer streaming");
                this.Ø­áŒŠá.Ó(this.Âµá€);
            }
            else if (var3 && this.ÂµÈ()) {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Connecting to twitch chat as user is streaming");
                this.ÇŽÉ();
            }
        }
        this.Ý.Ï­Ðƒà();
        this.Ø­áŒŠá.Âµá€();
    }
    
    protected void ÇŽÉ() {
        final ChatController.Ý var1 = this.Ø­áŒŠá.HorizonCode_Horizon_È();
        final String var2 = this.Ý.Ý().name;
        this.Âµá€ = var2;
        if (var1 != ChatController.Ý.Ý) {
            TwitchStream.Â.warn("Invalid twitch chat state {}", new Object[] { var1 });
        }
        else if (this.Ø­áŒŠá.Ø­áŒŠá(this.Âµá€) == ChatController.Ø­áŒŠá.Âµá€) {
            this.Ø­áŒŠá.Âµá€(var2);
        }
        else {
            TwitchStream.Â.warn("Invalid twitch chat state {}", new Object[] { var1 });
        }
    }
    
    @Override
    public void Ø() {
        if (this.Ý.Ø­áŒŠá() && !this.Ý.à()) {
            final long var1 = System.nanoTime();
            final long var2 = 1000000000 / this.ÂµÈ;
            final long var3 = var1 - this.á;
            final boolean var4 = var3 >= var2;
            if (var4) {
                final FrameBuffer var5 = this.Ý.Çªà¢();
                final Framebuffer var6 = this.Ó.Ý();
                this.áŒŠÆ.HorizonCode_Horizon_È(true);
                GlStateManager.á(5889);
                GlStateManager.Çªà¢();
                GlStateManager.ŒÏ();
                GlStateManager.HorizonCode_Horizon_È(0.0, this.áŒŠÆ.Ý, this.áŒŠÆ.Ø­áŒŠá, 0.0, 1000.0, 3000.0);
                GlStateManager.á(5888);
                GlStateManager.Çªà¢();
                GlStateManager.ŒÏ();
                GlStateManager.Â(0.0f, 0.0f, -2000.0f);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.Â(0, 0, this.áŒŠÆ.Ý, this.áŒŠÆ.Ø­áŒŠá);
                GlStateManager.µÕ();
                GlStateManager.Ý();
                GlStateManager.ÂµÈ();
                final float var7 = this.áŒŠÆ.Ý;
                final float var8 = this.áŒŠÆ.Ø­áŒŠá;
                final float var9 = var6.Ý / var6.HorizonCode_Horizon_È;
                final float var10 = var6.Ø­áŒŠá / var6.Â;
                var6.Ý();
                GL11.glTexParameterf(3553, 10241, 9729.0f);
                GL11.glTexParameterf(3553, 10240, 9729.0f);
                final Tessellator var11 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var12 = var11.Ý();
                var12.Â();
                var12.HorizonCode_Horizon_È(0.0, var8, 0.0, 0.0, var10);
                var12.HorizonCode_Horizon_È(var7, var8, 0.0, var9, var10);
                var12.HorizonCode_Horizon_È(var7, 0.0, 0.0, var9, 0.0);
                var12.HorizonCode_Horizon_È(0.0, 0.0, 0.0, 0.0, 0.0);
                var11.Â();
                var6.Ø­áŒŠá();
                GlStateManager.Ê();
                GlStateManager.á(5889);
                GlStateManager.Ê();
                GlStateManager.á(5888);
                this.Ý.HorizonCode_Horizon_È(var5);
                this.áŒŠÆ.Âµá€();
                this.Ý.Â(var5);
                this.á = var1;
            }
        }
    }
    
    @Override
    public boolean áŒŠÆ() {
        return this.Ý.Ø();
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return this.Ý.Âµá€();
    }
    
    @Override
    public boolean ÂµÈ() {
        return this.Ý.Ø­áŒŠá();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Metadata p_152911_1_, final long p_152911_2_) {
        if (this.ÂµÈ() && this.áˆºÑ¢Õ) {
            final long var4 = this.Ý.á();
            if (!this.Ý.HorizonCode_Horizon_È(p_152911_1_.Ý(), var4 + p_152911_2_, p_152911_1_.HorizonCode_Horizon_È(), p_152911_1_.Â())) {
                TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Couldn't send stream metadata action at {}: {}", new Object[] { var4 + p_152911_2_, p_152911_1_ });
            }
            else {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Sent stream metadata action at {}: {}", new Object[] { var4 + p_152911_2_, p_152911_1_ });
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Metadata p_176026_1_, final long p_176026_2_, final long p_176026_4_) {
        if (this.ÂµÈ() && this.áˆºÑ¢Õ) {
            final long var6 = this.Ý.á();
            final String var7 = p_176026_1_.HorizonCode_Horizon_È();
            final String var8 = p_176026_1_.Â();
            final long var9 = this.Ý.Â(p_176026_1_.Ý(), var6 + p_176026_2_, var7, var8);
            if (var9 < 0L) {
                TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Could not send stream metadata sequence from {} to {}: {}", new Object[] { var6 + p_176026_2_, var6 + p_176026_4_, p_176026_1_ });
            }
            else if (this.Ý.HorizonCode_Horizon_È(p_176026_1_.Ý(), var6 + p_176026_4_, var9, var7, var8)) {
                TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Sent stream metadata sequence from {} to {}: {}", new Object[] { var6 + p_176026_2_, var6 + p_176026_4_, p_176026_1_ });
            }
            else {
                TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Half-sent stream metadata sequence from {} to {}: {}", new Object[] { var6 + p_176026_2_, var6 + p_176026_4_, p_176026_1_ });
            }
        }
    }
    
    @Override
    public boolean á() {
        return this.Ý.à();
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ý.Ø­à()) {
            TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Requested commercial from Twitch");
        }
        else {
            TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Could not request commercial from Twitch");
        }
    }
    
    @Override
    public void £á() {
        this.Ý.Æ();
        this.Å = true;
        this.£à();
    }
    
    @Override
    public void Å() {
        this.Ý.Šáƒ();
        this.Å = false;
        this.£à();
    }
    
    @Override
    public void £à() {
        if (this.ÂµÈ()) {
            final float var1 = this.Ó.ŠÄ.ˆØ;
            final boolean var2 = this.Å || var1 <= 0.0f;
            this.Ý.Â(var2 ? 0.0f : var1);
            this.Ý.HorizonCode_Horizon_È(this.Çªà¢() ? 0.0f : this.Ó.ŠÄ.áŒŠá);
        }
    }
    
    @Override
    public void µà() {
        final GameSettings var1 = this.Ó.ŠÄ;
        final VideoParams var2 = this.Ý.HorizonCode_Horizon_È(Â(var1.áˆºà), HorizonCode_Horizon_È(var1.ÐƒÂ), Ý(var1.ÂµÂ), this.Ó.Ó / this.Ó.à);
        switch (var1.£áƒ) {
            case 0: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                break;
            }
            case 1: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                break;
            }
            case 2: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
                break;
            }
        }
        if (this.áŒŠÆ == null) {
            this.áŒŠÆ = new Framebuffer(var2.outputWidth, var2.outputHeight, false);
        }
        else {
            this.áŒŠÆ.HorizonCode_Horizon_È(var2.outputWidth, var2.outputHeight);
        }
        if (var1.Çª != null && var1.Çª.length() > 0) {
            for (final IngestServer var6 : this.¥Æ()) {
                if (var6.serverUrl.equals(var1.Çª)) {
                    this.Ý.HorizonCode_Horizon_È(var6);
                    break;
                }
            }
        }
        this.ÂµÈ = var2.targetFps;
        this.áˆºÑ¢Õ = var1.Ï­áˆºÓ;
        this.Ý.HorizonCode_Horizon_È(var2);
        TwitchStream.Â.info(TwitchStream.HorizonCode_Horizon_È, "Streaming at {}/{} at {} kbps to {}", new Object[] { var2.outputWidth, var2.outputHeight, var2.maxKbps, this.Ý.áŒŠÆ().serverUrl });
        this.Ý.HorizonCode_Horizon_È(null, "Minecraft", null);
    }
    
    @Override
    public void ˆà() {
        if (this.Ý.µÕ()) {
            TwitchStream.Â.info(TwitchStream.HorizonCode_Horizon_È, "Stopped streaming to Twitch");
        }
        else {
            TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Could not stop streaming to Twitch");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ErrorCode p_152900_1_, final AuthToken p_152900_2_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ErrorCode p_152897_1_) {
        if (ErrorCode.succeeded(p_152897_1_)) {
            TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Login attempt successful");
            this.£á = true;
        }
        else {
            TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Login attempt unsuccessful: {} (error code {})", new Object[] { ErrorCode.getString(p_152897_1_), p_152897_1_.getValue() });
            this.£á = false;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ErrorCode p_152898_1_, final GameInfo[] p_152898_2_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BroadcastController.Â p_152891_1_) {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Broadcast state changed to {}", new Object[] { p_152891_1_ });
        if (p_152891_1_ == BroadcastController.Â.Â) {
            this.Ý.HorizonCode_Horizon_È(BroadcastController.Â.Ø­áŒŠá);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        TwitchStream.Â.info(TwitchStream.HorizonCode_Horizon_È, "Logged out of twitch");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StreamInfo p_152894_1_) {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Stream info updated; {} viewers on stream ID {}", new Object[] { p_152894_1_.viewers, p_152894_1_.streamId });
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IngestList p_152896_1_) {
    }
    
    @Override
    public void Â(final ErrorCode p_152893_1_) {
        TwitchStream.Â.warn(TwitchStream.HorizonCode_Horizon_È, "Issue submitting frame: {} (Error code {})", new Object[] { ErrorCode.getString(p_152893_1_), p_152893_1_.getValue() });
        this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentText("Issue streaming frame: " + p_152893_1_ + " (" + ErrorCode.getString(p_152893_1_) + ")"), 2);
    }
    
    @Override
    public void Â() {
        this.£à();
        TwitchStream.Â.info(TwitchStream.HorizonCode_Horizon_È, "Broadcast to Twitch has started");
    }
    
    @Override
    public void Ý() {
        TwitchStream.Â.info(TwitchStream.HorizonCode_Horizon_È, "Broadcast to Twitch has stopped");
    }
    
    @Override
    public void Ý(final ErrorCode p_152892_1_) {
        if (p_152892_1_ == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            final ChatComponentTranslation var2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
            var2.à().HorizonCode_Horizon_È(new ClickEvent(ClickEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            var2.à().Ø­áŒŠá(Boolean.valueOf(true));
            final ChatComponentTranslation var3 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] { var2 });
            var3.à().HorizonCode_Horizon_È(EnumChatFormatting.Âµá€);
            this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(var3);
        }
        else {
            final ChatComponentTranslation var2 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] { ErrorCode.getString(p_152892_1_) });
            var2.à().HorizonCode_Horizon_È(EnumChatFormatting.Âµá€);
            this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(var2);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IngestServerTester p_152907_1_, final IngestServerTester.Â p_152907_2_) {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Ingest test state changed to {}", new Object[] { p_152907_2_ });
        if (p_152907_2_ == IngestServerTester.Â.Ó) {
            this.ˆÏ­ = true;
        }
    }
    
    public static int HorizonCode_Horizon_È(final float p_152948_0_) {
        return MathHelper.Ø­áŒŠá(10.0f + p_152948_0_ * 50.0f);
    }
    
    public static int Â(final float p_152946_0_) {
        return MathHelper.Ø­áŒŠá(230.0f + p_152946_0_ * 3270.0f);
    }
    
    public static float Ý(final float p_152947_0_) {
        return 0.1f + p_152947_0_ * 0.1f;
    }
    
    @Override
    public IngestServer[] ¥Æ() {
        return this.Ý.áˆºÑ¢Õ().getServers();
    }
    
    @Override
    public void Ø­à() {
        final IngestServerTester var1 = this.Ý.ŠÄ();
        if (var1 != null) {
            var1.HorizonCode_Horizon_È(this);
        }
    }
    
    @Override
    public IngestServerTester µÕ() {
        return this.Ý.ÂµÈ();
    }
    
    @Override
    public boolean Æ() {
        return this.Ý.Ó();
    }
    
    @Override
    public int Šáƒ() {
        return this.ÂµÈ() ? this.Ý.Â().viewers : 0;
    }
    
    @Override
    public void Ø­áŒŠá(final ErrorCode p_176023_1_) {
        if (ErrorCode.failed(p_176023_1_)) {
            TwitchStream.Â.error(TwitchStream.HorizonCode_Horizon_È, "Chat failed to initialize");
        }
    }
    
    @Override
    public void Âµá€(final ErrorCode p_176022_1_) {
        if (ErrorCode.failed(p_176022_1_)) {
            TwitchStream.Â.error(TwitchStream.HorizonCode_Horizon_È, "Chat failed to shutdown");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ChatController.Ý p_176017_1_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_180605_1_, final ChatRawMessage[] p_180605_2_) {
        for (final ChatRawMessage var6 : p_180605_2_) {
            this.HorizonCode_Horizon_È(var6.userName, var6);
            if (this.HorizonCode_Horizon_È(var6.modes, var6.subscriptions, this.Ó.ŠÄ.ˆÈ)) {
                final ChatComponentText var7 = new ChatComponentText(var6.userName);
                final ChatComponentTranslation var8 = new ChatComponentTranslation("chat.stream." + (var6.action ? "emote" : "text"), new Object[] { this.à, var7, EnumChatFormatting.HorizonCode_Horizon_È(var6.message) });
                if (var6.action) {
                    var8.à().Â(Boolean.valueOf(true));
                }
                final ChatComponentText var9 = new ChatComponentText("");
                var9.HorizonCode_Horizon_È(new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
                for (final IChatComponent var11 : GuiTwitchUserMode.HorizonCode_Horizon_È(var6.modes, var6.subscriptions, null)) {
                    var9.Â("\n");
                    var9.HorizonCode_Horizon_È(var11);
                }
                var7.à().HorizonCode_Horizon_È(new HoverEvent(HoverEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È, var9));
                var7.à().HorizonCode_Horizon_È(new ClickEvent(ClickEvent.HorizonCode_Horizon_È.Ø­áŒŠá, var6.userName));
                this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(var8);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_176025_1_, final ChatTokenizedMessage[] p_176025_2_) {
    }
    
    private void HorizonCode_Horizon_È(final String p_176027_1_, final ChatRawMessage p_176027_2_) {
        ChatUserInfo var3 = this.Ø.get(p_176027_1_);
        if (var3 == null) {
            var3 = new ChatUserInfo();
            var3.displayName = p_176027_1_;
            this.Ø.put(p_176027_1_, var3);
        }
        var3.subscriptions = p_176027_2_.subscriptions;
        var3.modes = p_176027_2_.modes;
        var3.nameColorARGB = p_176027_2_.nameColorARGB;
    }
    
    private boolean HorizonCode_Horizon_È(final Set p_176028_1_, final Set p_176028_2_, final int p_176028_3_) {
        return !p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) && (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) || p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) || p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) || p_176028_3_ == 0 || (p_176028_3_ == 1 && p_176028_2_.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER)));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_176018_1_, final ChatUserInfo[] p_176018_2_, final ChatUserInfo[] p_176018_3_, final ChatUserInfo[] p_176018_4_) {
        for (final ChatUserInfo var8 : p_176018_3_) {
            this.Ø.remove(var8.displayName);
        }
        for (final ChatUserInfo var8 : p_176018_4_) {
            this.Ø.put(var8.displayName, var8);
        }
        for (final ChatUserInfo var8 : p_176018_2_) {
            this.Ø.put(var8.displayName, var8);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_180606_1_) {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Chat connected");
    }
    
    @Override
    public void Â(final String p_180607_1_) {
        TwitchStream.Â.debug(TwitchStream.HorizonCode_Horizon_È, "Chat disconnected");
        this.Ø.clear();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_176019_1_, final String p_176019_2_) {
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public void Âµá€() {
    }
    
    @Override
    public void Ý(final String p_176016_1_) {
    }
    
    @Override
    public void Ø­áŒŠá(final String p_176020_1_) {
    }
    
    @Override
    public boolean Ï­Ðƒà() {
        return this.Âµá€ != null && this.Âµá€.equals(this.Ý.Ý().name);
    }
    
    @Override
    public String áŒŠà() {
        return this.Âµá€;
    }
    
    @Override
    public ChatUserInfo Âµá€(final String p_152926_1_) {
        return this.Ø.get(p_152926_1_);
    }
    
    @Override
    public void Ó(final String p_152917_1_) {
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Âµá€, p_152917_1_);
    }
    
    @Override
    public boolean ŠÄ() {
        return TwitchStream.ˆà && this.Ý.HorizonCode_Horizon_È();
    }
    
    @Override
    public ErrorCode Ñ¢á() {
        return TwitchStream.ˆà ? this.Ý.£á() : ErrorCode.TTV_EC_OS_TOO_OLD;
    }
    
    @Override
    public boolean ŒÏ() {
        return this.£á;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean p_152910_1_) {
        this.£à = p_152910_1_;
        this.£à();
    }
    
    @Override
    public boolean Çªà¢() {
        final boolean var1 = this.Ó.ŠÄ.ˆÅ == 1;
        return this.Å || this.Ó.ŠÄ.áŒŠá <= 0.0f || var1 != this.£à;
    }
    
    @Override
    public IStream.HorizonCode_Horizon_È Ê() {
        return this.µà;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final TwitchStream twitchStream, final IStream.HorizonCode_Horizon_È µà) {
        twitchStream.µà = µà;
    }
}
