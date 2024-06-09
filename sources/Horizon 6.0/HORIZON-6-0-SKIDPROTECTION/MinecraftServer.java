package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Executors;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListenableFuture;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.Collections;
import java.util.Arrays;
import com.mojang.authlib.GameProfile;
import java.awt.image.BufferedImage;
import io.netty.buffer.ByteBuf;
import com.google.common.base.Charsets;
import io.netty.handler.codec.base64.Base64;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import io.netty.buffer.ByteBufOutputStream;
import org.apache.commons.lang3.Validate;
import javax.imageio.ImageIO;
import io.netty.buffer.Unpooled;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.UUID;
import com.google.common.collect.Queues;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.Queue;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.security.KeyPair;
import java.net.Proxy;
import java.util.Random;
import java.util.List;
import java.io.File;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements ICommandSender, IThreadListener, IPlayerUsage, Runnable
{
    private static final Logger áˆºÑ¢Õ;
    public static final File HorizonCode_Horizon_È;
    private static MinecraftServer ÂµÈ;
    private final ISaveFormat á;
    private final PlayerUsageSnooper ˆÏ­;
    private final File £á;
    private final List Å;
    private final ICommandManager £à;
    public final Profiler Â;
    private final NetworkSystem µà;
    private final ServerStatusResponse ˆà;
    private final Random ¥Æ;
    private int Ø­à;
    public WorldServer[] Ý;
    private ServerConfigurationManager µÕ;
    private boolean Æ;
    private boolean Šáƒ;
    private int Ï­Ðƒà;
    protected final Proxy Ø­áŒŠá;
    public String Âµá€;
    public int Ó;
    private boolean áŒŠà;
    private boolean ŠÄ;
    private boolean Ñ¢á;
    private boolean ŒÏ;
    private boolean Çªà¢;
    private String Ê;
    private int ÇŽÉ;
    private int ˆá;
    public final long[] à;
    public long[][] Ø;
    private KeyPair ÇŽÕ;
    private String É;
    private String áƒ;
    private String á€;
    private boolean Õ;
    private boolean à¢;
    private boolean ŠÂµà;
    private String ¥à;
    private String Âµà;
    private boolean Ç;
    private long È;
    private String ˆáŠ;
    private boolean áŒŠ;
    private boolean £ÂµÄ;
    private final YggdrasilAuthenticationService Ø­Âµ;
    private final MinecraftSessionService Ä;
    private long Ñ¢Â;
    private final GameProfileRepository Ï­à;
    private final PlayerProfileCache áˆºáˆºÈ;
    protected final Queue áŒŠÆ;
    private Thread ÇŽá€;
    private long Ï;
    private static final String Ô = "CL_00001462";
    
    static {
        áˆºÑ¢Õ = LogManager.getLogger();
        HorizonCode_Horizon_È = new File("usercache.json");
    }
    
    public MinecraftServer(final Proxy p_i46053_1_, final File p_i46053_2_) {
        this.ˆÏ­ = new PlayerUsageSnooper("server", this, Œà());
        this.Å = Lists.newArrayList();
        this.Â = new Profiler();
        this.ˆà = new ServerStatusResponse();
        this.¥Æ = new Random();
        this.Ø­à = -1;
        this.Æ = true;
        this.ˆá = 0;
        this.à = new long[100];
        this.¥à = "";
        this.Âµà = "";
        this.Ñ¢Â = 0L;
        this.áŒŠÆ = Queues.newArrayDeque();
        this.Ï = Œà();
        this.Ø­áŒŠá = p_i46053_1_;
        MinecraftServer.ÂµÈ = this;
        this.£á = null;
        this.µà = null;
        this.áˆºáˆºÈ = new PlayerProfileCache(this, p_i46053_2_);
        this.£à = null;
        this.á = null;
        this.Ø­Âµ = new YggdrasilAuthenticationService(p_i46053_1_, UUID.randomUUID().toString());
        this.Ä = this.Ø­Âµ.createMinecraftSessionService();
        this.Ï­à = this.Ø­Âµ.createProfileRepository();
    }
    
    public MinecraftServer(final File workDir, final Proxy proxy, final File profileCacheDir) {
        this.ˆÏ­ = new PlayerUsageSnooper("server", this, Œà());
        this.Å = Lists.newArrayList();
        this.Â = new Profiler();
        this.ˆà = new ServerStatusResponse();
        this.¥Æ = new Random();
        this.Ø­à = -1;
        this.Æ = true;
        this.ˆá = 0;
        this.à = new long[100];
        this.¥à = "";
        this.Âµà = "";
        this.Ñ¢Â = 0L;
        this.áŒŠÆ = Queues.newArrayDeque();
        this.Ï = Œà();
        this.Ø­áŒŠá = proxy;
        MinecraftServer.ÂµÈ = this;
        this.£á = workDir;
        this.µà = new NetworkSystem(this);
        this.áˆºáˆºÈ = new PlayerProfileCache(this, profileCacheDir);
        this.£à = this.à();
        this.á = new AnvilSaveConverter(workDir);
        this.Ø­Âµ = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.Ä = this.Ø­Âµ.createMinecraftSessionService();
        this.Ï­à = this.Ø­Âµ.createProfileRepository();
    }
    
    protected ServerCommandManager à() {
        return new ServerCommandManager();
    }
    
    protected abstract boolean Ø() throws IOException;
    
    protected void HorizonCode_Horizon_È(final String worldNameIn) {
        if (this.ˆáŠ().Â(worldNameIn)) {
            MinecraftServer.áˆºÑ¢Õ.info("Converting map!");
            this.Â("menu.convertingLevel");
            this.ˆáŠ().HorizonCode_Horizon_È(worldNameIn, new IProgressUpdate() {
                private long Â = System.currentTimeMillis();
                private static final String Ý = "CL_00001417";
                
                @Override
                public void Â(final String message) {
                }
                
                @Override
                public void HorizonCode_Horizon_È(final String p_73721_1_) {
                }
                
                @Override
                public void HorizonCode_Horizon_È(final int progress) {
                    if (System.currentTimeMillis() - this.Â >= 1000L) {
                        this.Â = System.currentTimeMillis();
                        MinecraftServer.áˆºÑ¢Õ.info("Converting... " + progress + "%");
                    }
                }
                
                @Override
                public void p_() {
                }
                
                @Override
                public void Ý(final String message) {
                }
            });
        }
    }
    
    protected synchronized void Â(final String message) {
        this.ˆáŠ = message;
    }
    
    public synchronized String áŒŠÆ() {
        return this.ˆáŠ;
    }
    
    protected void HorizonCode_Horizon_È(final String p_71247_1_, final String p_71247_2_, final long seed, final WorldType type, final String p_71247_6_) {
        this.HorizonCode_Horizon_È(p_71247_1_);
        this.Â("menu.loadingLevel");
        this.Ý = new WorldServer[3];
        this.Ø = new long[this.Ý.length][100];
        final ISaveHandler var7 = this.á.HorizonCode_Horizon_È(p_71247_1_, true);
        this.HorizonCode_Horizon_È(this.Âµà(), var7);
        WorldInfo var8 = var7.Ý();
        WorldSettings var9;
        if (var8 == null) {
            if (this.áŠ()) {
                var9 = DemoWorldServer.áƒ;
            }
            else {
                var9 = new WorldSettings(seed, this.á(), this.ÂµÈ(), this.Å(), type);
                var9.HorizonCode_Horizon_È(p_71247_6_);
                if (this.à¢) {
                    var9.HorizonCode_Horizon_È();
                }
            }
            var8 = new WorldInfo(var9, p_71247_2_);
        }
        else {
            var8.HorizonCode_Horizon_È(p_71247_2_);
            var9 = new WorldSettings(var8);
        }
        for (int var10 = 0; var10 < this.Ý.length; ++var10) {
            byte var11 = 0;
            if (var10 == 1) {
                var11 = -1;
            }
            if (var10 == 2) {
                var11 = 1;
            }
            if (var10 == 0) {
                if (this.áŠ()) {
                    this.Ý[var10] = (WorldServer)new DemoWorldServer(this, var7, var8, var11, this.Â).Ø();
                }
                else {
                    this.Ý[var10] = (WorldServer)new WorldServer(this, var7, var8, var11, this.Â).Ø();
                }
                this.Ý[var10].HorizonCode_Horizon_È(var9);
            }
            else {
                this.Ý[var10] = (WorldServer)new WorldServerMulti(this, var7, var11, this.Ý[0], this.Â).Ø();
            }
            this.Ý[var10].HorizonCode_Horizon_È(new WorldManager(this, this.Ý[var10]));
            if (!this.¥à()) {
                this.Ý[var10].ŒÏ().HorizonCode_Horizon_È(this.á());
            }
        }
        this.µÕ.HorizonCode_Horizon_È(this.Ý);
        this.HorizonCode_Horizon_È(this.ˆÏ­());
        this.áˆºÑ¢Õ();
    }
    
    protected void áˆºÑ¢Õ() {
        final boolean var1 = true;
        final boolean var2 = true;
        final boolean var3 = true;
        final boolean var4 = true;
        int var5 = 0;
        this.Â("menu.generatingTerrain");
        final byte var6 = 0;
        MinecraftServer.áˆºÑ¢Õ.info("Preparing start region for level " + var6);
        final WorldServer var7 = this.Ý[var6];
        final BlockPos var8 = var7.áŒŠà();
        long var9 = Œà();
        for (int var10 = -192; var10 <= 192 && this.¥Æ(); var10 += 16) {
            for (int var11 = -192; var11 <= 192 && this.¥Æ(); var11 += 16) {
                final long var12 = Œà();
                if (var12 - var9 > 1000L) {
                    this.HorizonCode_Horizon_È("Preparing spawn area", var5 * 100 / 625);
                    var9 = var12;
                }
                ++var5;
                var7.ÇŽÉ.Ý(var8.HorizonCode_Horizon_È() + var10 >> 4, var8.Ý() + var11 >> 4);
            }
        }
        this.µà();
    }
    
    protected void HorizonCode_Horizon_È(final String worldNameIn, final ISaveHandler saveHandlerIn) {
        final File var3 = new File(saveHandlerIn.Ó(), "resources.zip");
        if (var3.isFile()) {
            this.HorizonCode_Horizon_È("level://" + worldNameIn + "/" + var3.getName(), "");
        }
    }
    
    public abstract boolean ÂµÈ();
    
    public abstract WorldSettings.HorizonCode_Horizon_È á();
    
    public abstract EnumDifficulty ˆÏ­();
    
    public abstract boolean Å();
    
    public abstract int £à();
    
    protected void HorizonCode_Horizon_È(final String message, final int percent) {
        this.Âµá€ = message;
        this.Ó = percent;
        MinecraftServer.áˆºÑ¢Õ.info(String.valueOf(message) + ": " + percent + "%");
    }
    
    protected void µà() {
        this.Âµá€ = null;
        this.Ó = 0;
    }
    
    protected void HorizonCode_Horizon_È(final boolean dontLog) {
        if (!this.ŠÂµà) {
            for (final WorldServer var5 : this.Ý) {
                if (var5 != null) {
                    if (!dontLog) {
                        MinecraftServer.áˆºÑ¢Õ.info("Saving chunks for level '" + var5.ŒÏ().áˆºÑ¢Õ() + "'/" + var5.£à.ÂµÈ());
                    }
                    try {
                        var5.HorizonCode_Horizon_È(true, null);
                    }
                    catch (MinecraftException var6) {
                        MinecraftServer.áˆºÑ¢Õ.warn(var6.getMessage());
                    }
                }
            }
        }
    }
    
    public void ˆà() {
        if (!this.ŠÂµà) {
            MinecraftServer.áˆºÑ¢Õ.info("Stopping server");
            if (this.£Ï() != null) {
                this.£Ï().Â();
            }
            if (this.µÕ != null) {
                MinecraftServer.áˆºÑ¢Õ.info("Saving players");
                this.µÕ.ÂµÈ();
                this.µÕ.µÕ();
            }
            if (this.Ý != null) {
                MinecraftServer.áˆºÑ¢Õ.info("Saving worlds");
                this.HorizonCode_Horizon_È(false);
                for (int var1 = 0; var1 < this.Ý.length; ++var1) {
                    final WorldServer var2 = this.Ý[var1];
                    var2.Ï­à();
                }
            }
            if (this.ˆÏ­.Ø­áŒŠá()) {
                this.ˆÏ­.Âµá€();
            }
        }
    }
    
    public boolean ¥Æ() {
        return this.Æ;
    }
    
    public void Ø­à() {
        this.Æ = false;
    }
    
    protected void µÕ() {
        MinecraftServer.ÂµÈ = this;
    }
    
    @Override
    public void run() {
        try {
            if (this.Ø()) {
                this.Ï = Œà();
                long var1 = 0L;
                this.ˆà.HorizonCode_Horizon_È(new ChatComponentText(this.Ê));
                this.ˆà.HorizonCode_Horizon_È(new ServerStatusResponse.HorizonCode_Horizon_È("1.8", 47));
                this.HorizonCode_Horizon_È(this.ˆà);
                while (this.Æ) {
                    final long var2 = Œà();
                    long var3 = var2 - this.Ï;
                    if (var3 > 2000L && this.Ï - this.È >= 15000L) {
                        MinecraftServer.áˆºÑ¢Õ.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { var3, var3 / 50L });
                        var3 = 2000L;
                        this.È = this.Ï;
                    }
                    if (var3 < 0L) {
                        MinecraftServer.áˆºÑ¢Õ.warn("Time ran backwards! Did the system time change?");
                        var3 = 0L;
                    }
                    var1 += var3;
                    this.Ï = var2;
                    if (this.Ý[0].ˆáŠ()) {
                        this.Ï­Ðƒà();
                        var1 = 0L;
                    }
                    else {
                        while (var1 > 50L) {
                            var1 -= 50L;
                            this.Ï­Ðƒà();
                        }
                    }
                    Thread.sleep(Math.max(1L, 50L - var1));
                    this.Ç = true;
                }
            }
            else {
                this.HorizonCode_Horizon_È((CrashReport)null);
            }
        }
        catch (Throwable var4) {
            MinecraftServer.áˆºÑ¢Õ.error("Encountered an unexpected exception", var4);
            CrashReport var5 = null;
            if (var4 instanceof ReportedException) {
                var5 = this.Â(((ReportedException)var4).HorizonCode_Horizon_È());
            }
            else {
                var5 = this.Â(new CrashReport("Exception in server tick loop", var4));
            }
            final File var6 = new File(new File(this.Æ(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (var5.HorizonCode_Horizon_È(var6)) {
                MinecraftServer.áˆºÑ¢Õ.error("This crash report has been saved to: " + var6.getAbsolutePath());
            }
            else {
                MinecraftServer.áˆºÑ¢Õ.error("We were unable to save this crash report to disk.");
            }
            this.HorizonCode_Horizon_È(var5);
            return;
        }
        finally {
            Label_0538: {
                try {
                    this.ˆà();
                    this.Šáƒ = true;
                }
                catch (Throwable var7) {
                    MinecraftServer.áˆºÑ¢Õ.error("Exception stopping the server", var7);
                    this.Šáƒ();
                    break Label_0538;
                }
                finally {
                    this.Šáƒ();
                }
                this.Šáƒ();
            }
        }
        try {
            this.ˆà();
            this.Šáƒ = true;
        }
        catch (Throwable var7) {
            MinecraftServer.áˆºÑ¢Õ.error("Exception stopping the server", var7);
            return;
        }
        finally {
            this.Šáƒ();
        }
        this.Šáƒ();
    }
    
    private void HorizonCode_Horizon_È(final ServerStatusResponse response) {
        final File var2 = this.Ý("server-icon.png");
        if (var2.isFile()) {
            final ByteBuf var3 = Unpooled.buffer();
            try {
                final BufferedImage var4 = ImageIO.read(var2);
                Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(var4, "PNG", (OutputStream)new ByteBufOutputStream(var3));
                final ByteBuf var5 = Base64.encode(var3);
                response.HorizonCode_Horizon_È("data:image/png;base64," + var5.toString(Charsets.UTF_8));
            }
            catch (Exception var6) {
                MinecraftServer.áˆºÑ¢Õ.error("Couldn't load server icon", (Throwable)var6);
                return;
            }
            finally {
                var3.release();
            }
            var3.release();
        }
    }
    
    public File Æ() {
        return new File(".");
    }
    
    protected void HorizonCode_Horizon_È(final CrashReport report) {
    }
    
    protected void Šáƒ() {
    }
    
    public void Ï­Ðƒà() {
        final long var1 = System.nanoTime();
        ++this.Ï­Ðƒà;
        if (this.áŒŠ) {
            this.áŒŠ = false;
            this.Â.HorizonCode_Horizon_È = true;
            this.Â.HorizonCode_Horizon_È();
        }
        this.Â.HorizonCode_Horizon_È("root");
        this.áŒŠà();
        if (var1 - this.Ñ¢Â >= 5000000000L) {
            this.Ñ¢Â = var1;
            this.ˆà.HorizonCode_Horizon_È(new ServerStatusResponse.Â(this.ÇŽÉ(), this.Ê()));
            final GameProfile[] var2 = new GameProfile[Math.min(this.Ê(), 12)];
            final int var3 = MathHelper.HorizonCode_Horizon_È(this.¥Æ, 0, this.Ê() - var2.length);
            for (int var4 = 0; var4 < var2.length; ++var4) {
                var2[var4] = this.µÕ.Âµá€.get(var3 + var4).áˆºà();
            }
            Collections.shuffle(Arrays.asList(var2));
            this.ˆà.Â().HorizonCode_Horizon_È(var2);
        }
        if (this.Ï­Ðƒà % 900 == 0) {
            this.Â.HorizonCode_Horizon_È("save");
            this.µÕ.ÂµÈ();
            this.HorizonCode_Horizon_È(true);
            this.Â.Â();
        }
        this.Â.HorizonCode_Horizon_È("tallying");
        this.à[this.Ï­Ðƒà % 100] = System.nanoTime() - var1;
        this.Â.Â();
        this.Â.HorizonCode_Horizon_È("snooper");
        if (!this.ˆÏ­.Ø­áŒŠá() && this.Ï­Ðƒà > 100) {
            this.ˆÏ­.HorizonCode_Horizon_È();
        }
        if (this.Ï­Ðƒà % 6000 == 0) {
            this.ˆÏ­.Â();
        }
        this.Â.Â();
        this.Â.Â();
    }
    
    public void áŒŠà() {
        this.Â.HorizonCode_Horizon_È("jobs");
        final Queue var1 = this.áŒŠÆ;
        synchronized (this.áŒŠÆ) {
            while (!this.áŒŠÆ.isEmpty()) {
                try {
                    this.áŒŠÆ.poll().run();
                }
                catch (Throwable var2) {
                    MinecraftServer.áˆºÑ¢Õ.fatal((Object)var2);
                }
            }
        }
        // monitorexit(this.\u00e1\u0152\u0160\u00c6)
        this.Â.Ý("levels");
        for (int var3 = 0; var3 < this.Ý.length; ++var3) {
            final long var4 = System.nanoTime();
            if (var3 == 0 || this.ŠÄ()) {
                final WorldServer var5 = this.Ý[var3];
                this.Â.HorizonCode_Horizon_È(var5.ŒÏ().áˆºÑ¢Õ());
                if (this.Ï­Ðƒà % 20 == 0) {
                    this.Â.HorizonCode_Horizon_È("timeSync");
                    this.µÕ.HorizonCode_Horizon_È(new S03PacketTimeUpdate(var5.Šáƒ(), var5.Ï­Ðƒà(), var5.Çªà¢().Â("doDaylightCycle")), var5.£à.µà());
                    this.Â.Â();
                }
                this.Â.HorizonCode_Horizon_È("tick");
                try {
                    var5.r_();
                }
                catch (Throwable var7) {
                    final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var7, "Exception ticking world");
                    var5.HorizonCode_Horizon_È(var6);
                    throw new ReportedException(var6);
                }
                try {
                    var5.£á();
                }
                catch (Throwable var8) {
                    final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var8, "Exception ticking world entities");
                    var5.HorizonCode_Horizon_È(var6);
                    throw new ReportedException(var6);
                }
                this.Â.Â();
                this.Â.HorizonCode_Horizon_È("tracker");
                var5.ÇŽá€().HorizonCode_Horizon_È();
                this.Â.Â();
                this.Â.Â();
            }
            this.Ø[var3][this.Ï­Ðƒà % 100] = System.nanoTime() - var4;
        }
        this.Â.Ý("connection");
        this.£Ï().Ý();
        this.Â.Ý("players");
        this.µÕ.Âµá€();
        this.Â.Ý("tickables");
        for (int var3 = 0; var3 < this.Å.size(); ++var3) {
            this.Å.get(var3).HorizonCode_Horizon_È();
        }
        this.Â.Â();
    }
    
    public boolean ŠÄ() {
        return true;
    }
    
    public void Ñ¢á() {
        (this.ÇŽá€ = new Thread(this, "Server thread")).start();
    }
    
    public File Ý(final String fileName) {
        return new File(this.Æ(), fileName);
    }
    
    public void Ø­áŒŠá(final String msg) {
        MinecraftServer.áˆºÑ¢Õ.warn(msg);
    }
    
    public WorldServer HorizonCode_Horizon_È(final int dimension) {
        return (dimension == -1) ? this.Ý[1] : ((dimension == 1) ? this.Ý[2] : this.Ý[0]);
    }
    
    public String Çªà¢() {
        return "1.8";
    }
    
    public int Ê() {
        return this.µÕ.µà();
    }
    
    public int ÇŽÉ() {
        return this.µÕ.ˆà();
    }
    
    public String[] ˆá() {
        return this.µÕ.à();
    }
    
    public GameProfile[] ÇŽÕ() {
        return this.µÕ.Ø();
    }
    
    public String É() {
        return "vanilla";
    }
    
    public CrashReport Â(final CrashReport report) {
        report.Ó().HorizonCode_Horizon_È("Profiler Position", new Callable() {
            private static final String Â = "CL_00001418";
            
            public String HorizonCode_Horizon_È() {
                return MinecraftServer.this.Â.HorizonCode_Horizon_È ? MinecraftServer.this.Â.Ý() : "N/A (disabled)";
            }
            
            @Override
            public Object call() {
                return this.HorizonCode_Horizon_È();
            }
        });
        if (this.µÕ != null) {
            report.Ó().HorizonCode_Horizon_È("Player Count", new Callable() {
                private static final String Â = "CL_00001419";
                
                public String HorizonCode_Horizon_È() {
                    return String.valueOf(MinecraftServer.this.µÕ.µà()) + " / " + MinecraftServer.this.µÕ.ˆà() + "; " + MinecraftServer.this.µÕ.Âµá€;
                }
            });
        }
        return report;
    }
    
    public List HorizonCode_Horizon_È(final ICommandSender p_180506_1_, String p_180506_2_, final BlockPos p_180506_3_) {
        final ArrayList var4 = Lists.newArrayList();
        if (p_180506_2_.startsWith("/")) {
            p_180506_2_ = p_180506_2_.substring(1);
            final boolean var5 = !p_180506_2_.contains(" ");
            final List var6 = this.£à.HorizonCode_Horizon_È(p_180506_1_, p_180506_2_, p_180506_3_);
            if (var6 != null) {
                for (final String var8 : var6) {
                    if (var5) {
                        var4.add("/" + var8);
                    }
                    else {
                        var4.add(var8);
                    }
                }
            }
            return var4;
        }
        final String[] var9 = p_180506_2_.split(" ", -1);
        final String var10 = var9[var9.length - 1];
        for (final String var14 : this.µÕ.à()) {
            if (CommandBase.HorizonCode_Horizon_È(var10, var14)) {
                var4.add(var14);
            }
        }
        return var4;
    }
    
    public static MinecraftServer áƒ() {
        return MinecraftServer.ÂµÈ;
    }
    
    public boolean á€() {
        return this.£á != null;
    }
    
    @Override
    public String v_() {
        return "Server";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent message) {
        MinecraftServer.áˆºÑ¢Õ.info(message.Ø());
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
        return true;
    }
    
    public ICommandManager Õ() {
        return this.£à;
    }
    
    public KeyPair à¢() {
        return this.ÇŽÕ;
    }
    
    public String ŠÂµà() {
        return this.É;
    }
    
    public void Âµá€(final String owner) {
        this.É = owner;
    }
    
    public boolean ¥à() {
        return this.É != null;
    }
    
    public String Âµà() {
        return this.áƒ;
    }
    
    public void Ó(final String name) {
        this.áƒ = name;
    }
    
    public void à(final String p_71246_1_) {
        this.á€ = p_71246_1_;
    }
    
    public String Ç() {
        return this.á€;
    }
    
    public void HorizonCode_Horizon_È(final KeyPair keyPair) {
        this.ÇŽÕ = keyPair;
    }
    
    public void HorizonCode_Horizon_È(final EnumDifficulty difficulty) {
        for (int var2 = 0; var2 < this.Ý.length; ++var2) {
            final WorldServer var3 = this.Ý[var2];
            if (var3 != null) {
                if (var3.ŒÏ().¥Æ()) {
                    var3.ŒÏ().HorizonCode_Horizon_È(EnumDifficulty.Ø­áŒŠá);
                    var3.HorizonCode_Horizon_È(true, true);
                }
                else if (this.¥à()) {
                    var3.ŒÏ().HorizonCode_Horizon_È(difficulty);
                    var3.HorizonCode_Horizon_È(var3.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È, true);
                }
                else {
                    var3.ŒÏ().HorizonCode_Horizon_È(difficulty);
                    var3.HorizonCode_Horizon_È(this.È(), this.ŠÄ);
                }
            }
        }
    }
    
    protected boolean È() {
        return true;
    }
    
    public boolean áŠ() {
        return this.Õ;
    }
    
    public void Â(final boolean demo) {
        this.Õ = demo;
    }
    
    public void Ý(final boolean enable) {
        this.à¢ = enable;
    }
    
    public ISaveFormat ˆáŠ() {
        return this.á;
    }
    
    public void áŒŠ() {
        this.ŠÂµà = true;
        this.ˆáŠ().Ø­áŒŠá();
        for (int var1 = 0; var1 < this.Ý.length; ++var1) {
            final WorldServer var2 = this.Ý[var1];
            if (var2 != null) {
                var2.Ï­à();
            }
        }
        this.ˆáŠ().Âµá€(this.Ý[0].Ñ¢á().à());
        this.Ø­à();
    }
    
    public String £ÂµÄ() {
        return this.¥à;
    }
    
    public String Ø­Âµ() {
        return this.Âµà;
    }
    
    public void HorizonCode_Horizon_È(final String url, final String hash) {
        this.¥à = url;
        this.Âµà = hash;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PlayerUsageSnooper playerSnooper) {
        playerSnooper.HorizonCode_Horizon_È("whitelist_enabled", false);
        playerSnooper.HorizonCode_Horizon_È("whitelist_count", 0);
        if (this.µÕ != null) {
            playerSnooper.HorizonCode_Horizon_È("players_current", this.Ê());
            playerSnooper.HorizonCode_Horizon_È("players_max", this.ÇŽÉ());
            playerSnooper.HorizonCode_Horizon_È("players_seen", this.µÕ.¥Æ().length);
        }
        playerSnooper.HorizonCode_Horizon_È("uses_auth", this.áŒŠà);
        playerSnooper.HorizonCode_Horizon_È("gui_state", this.ˆÉ() ? "enabled" : "disabled");
        playerSnooper.HorizonCode_Horizon_È("run_time", (Œà() - playerSnooper.à()) / 60L * 1000L);
        playerSnooper.HorizonCode_Horizon_È("avg_tick_ms", (int)(MathHelper.HorizonCode_Horizon_È(this.à) * 1.0E-6));
        int var2 = 0;
        if (this.Ý != null) {
            for (int var3 = 0; var3 < this.Ý.length; ++var3) {
                if (this.Ý[var3] != null) {
                    final WorldServer var4 = this.Ý[var3];
                    final WorldInfo var5 = var4.ŒÏ();
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][dimension]", var4.£à.µà());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][mode]", var5.µà());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][difficulty]", var4.ŠÂµà());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][hardcore]", var5.¥Æ());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][generator_name]", var5.Ø­à().HorizonCode_Horizon_È());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][generator_version]", var5.Ø­à().Ø­áŒŠá());
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][height]", this.ÇŽÉ);
                    playerSnooper.HorizonCode_Horizon_È("world[" + var2 + "][chunks_loaded]", var4.ŠÄ().Âµá€());
                    ++var2;
                }
            }
        }
        playerSnooper.HorizonCode_Horizon_È("worlds", var2);
    }
    
    @Override
    public void Â(final PlayerUsageSnooper playerSnooper) {
        playerSnooper.Â("singleplayer", this.¥à());
        playerSnooper.Â("server_brand", this.É());
        playerSnooper.Â("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        playerSnooper.Â("dedicated", this.Ä());
    }
    
    @Override
    public boolean ŒÏ() {
        return true;
    }
    
    public abstract boolean Ä();
    
    public boolean Ñ¢Â() {
        return this.áŒŠà;
    }
    
    public void Ø­áŒŠá(final boolean online) {
        this.áŒŠà = online;
    }
    
    public boolean Ï­à() {
        return this.ŠÄ;
    }
    
    public void Âµá€(final boolean spawnAnimals) {
        this.ŠÄ = spawnAnimals;
    }
    
    public boolean áˆºáˆºÈ() {
        return this.Ñ¢á;
    }
    
    public void Ó(final boolean spawnNpcs) {
        this.Ñ¢á = spawnNpcs;
    }
    
    public boolean ÇŽá€() {
        return this.ŒÏ;
    }
    
    public void à(final boolean allowPvp) {
        this.ŒÏ = allowPvp;
    }
    
    public boolean Ô() {
        return this.Çªà¢;
    }
    
    public void Ø(final boolean allow) {
        this.Çªà¢ = allow;
    }
    
    public abstract boolean ÇªÓ();
    
    public String áˆºÏ() {
        return this.Ê;
    }
    
    public void Ø(final String motdIn) {
        this.Ê = motdIn;
    }
    
    public int ˆáƒ() {
        return this.ÇŽÉ;
    }
    
    public void Â(final int maxBuildHeight) {
        this.ÇŽÉ = maxBuildHeight;
    }
    
    public ServerConfigurationManager Œ() {
        return this.µÕ;
    }
    
    public void HorizonCode_Horizon_È(final ServerConfigurationManager configManager) {
        this.µÕ = configManager;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È gameMode) {
        for (int var2 = 0; var2 < this.Ý.length; ++var2) {
            áƒ().Ý[var2].ŒÏ().HorizonCode_Horizon_È(gameMode);
        }
    }
    
    public NetworkSystem £Ï() {
        return this.µà;
    }
    
    public boolean Ø­á() {
        return this.Ç;
    }
    
    public boolean ˆÉ() {
        return false;
    }
    
    public abstract String HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p0, final boolean p1);
    
    public int Ï­Ï­Ï() {
        return this.Ï­Ðƒà;
    }
    
    public void £Â() {
        this.áŒŠ = true;
    }
    
    public PlayerUsageSnooper £Ó() {
        return this.ˆÏ­;
    }
    
    @Override
    public BlockPos £á() {
        return BlockPos.HorizonCode_Horizon_È;
    }
    
    @Override
    public Vec3 z_() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    @Override
    public World k_() {
        return this.Ý[0];
    }
    
    @Override
    public Entity l_() {
        return null;
    }
    
    public int ˆÐƒØ­à() {
        return 16;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        return false;
    }
    
    public boolean £Õ() {
        return this.£ÂµÄ;
    }
    
    public Proxy Ï­Ô() {
        return this.Ø­áŒŠá;
    }
    
    public static long Œà() {
        return System.currentTimeMillis();
    }
    
    public int Ðƒá() {
        return this.ˆá;
    }
    
    public void Ý(final int idleTimeout) {
        this.ˆá = idleTimeout;
    }
    
    @Override
    public IChatComponent Ý() {
        return new ChatComponentText(this.v_());
    }
    
    public boolean ˆÏ() {
        return true;
    }
    
    public MinecraftSessionService áˆºÇŽØ() {
        return this.Ä;
    }
    
    public GameProfileRepository ÇªÂµÕ() {
        return this.Ï­à;
    }
    
    public PlayerProfileCache áŒŠÏ() {
        return this.áˆºáˆºÈ;
    }
    
    public ServerStatusResponse áŒŠáŠ() {
        return this.ˆà;
    }
    
    public void ˆÓ() {
        this.Ñ¢Â = 0L;
    }
    
    public Entity HorizonCode_Horizon_È(final UUID uuid) {
        for (final WorldServer var5 : this.Ý) {
            if (var5 != null) {
                final Entity var6 = var5.Â(uuid);
                if (var6 != null) {
                    return var6;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean g_() {
        return áƒ().Ý[0].Çªà¢().Â("sendCommandFeedback");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
    }
    
    public int ¥Ä() {
        return 29999984;
    }
    
    public ListenableFuture HorizonCode_Horizon_È(final Callable callable) {
        Validate.notNull((Object)callable);
        if (!this.Ï()) {
            final ListenableFutureTask var2 = ListenableFutureTask.create(callable);
            final Queue var3 = this.áŒŠÆ;
            synchronized (this.áŒŠÆ) {
                this.áŒŠÆ.add(var2);
                final ListenableFutureTask listenableFutureTask = var2;
                // monitorexit(this.\u00e1\u0152\u0160\u00c6)
                return (ListenableFuture)listenableFutureTask;
            }
        }
        try {
            return Futures.immediateFuture(callable.call());
        }
        catch (Exception var4) {
            return (ListenableFuture)Futures.immediateFailedCheckedFuture(var4);
        }
    }
    
    @Override
    public ListenableFuture HorizonCode_Horizon_È(final Runnable runnableToSchedule) {
        Validate.notNull((Object)runnableToSchedule);
        return this.HorizonCode_Horizon_È(Executors.callable(runnableToSchedule));
    }
    
    @Override
    public boolean Ï() {
        return Thread.currentThread() == this.ÇŽá€;
    }
    
    public int ÇªÔ() {
        return 256;
    }
}
