package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Future;
import com.google.common.util.concurrent.Futures;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.io.IOException;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger áˆºÑ¢Õ;
    private final Minecraft ÂµÈ;
    private final WorldSettings á;
    private boolean ˆÏ­;
    private boolean £á;
    private ThreadLanServerPing Å;
    private static final String £à = "CL_00001129";
    
    static {
        áˆºÑ¢Õ = LogManager.getLogger();
    }
    
    public IntegratedServer(final Minecraft mcIn) {
        super(mcIn.ŠÂµà(), new File(mcIn.ŒÏ, IntegratedServer.HorizonCode_Horizon_È.getName()));
        this.ÂµÈ = mcIn;
        this.á = null;
    }
    
    public IntegratedServer(final Minecraft mcIn, final String folderName, final String worldName, final WorldSettings settings) {
        super(new File(mcIn.ŒÏ, "saves"), mcIn.ŠÂµà(), new File(mcIn.ŒÏ, IntegratedServer.HorizonCode_Horizon_È.getName()));
        this.Âµá€(mcIn.Õ().Ý());
        this.Ó(folderName);
        this.à(worldName);
        this.Â(mcIn.Ø­à());
        this.Ý(settings.Ý());
        this.Â(256);
        this.HorizonCode_Horizon_È(new IntegratedPlayerList(this));
        this.ÂµÈ = mcIn;
        this.á = (this.áŠ() ? DemoWorldServer.áƒ : settings);
        Reflector.HorizonCode_Horizon_È(Reflector.Âµá€, this);
    }
    
    @Override
    protected ServerCommandManager à() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final String p_71247_1_, final String p_71247_2_, final long seed, final WorldType type, final String p_71247_6_) {
        this.HorizonCode_Horizon_È(p_71247_1_);
        final ISaveHandler var7 = this.ˆáŠ().HorizonCode_Horizon_È(p_71247_1_, true);
        this.Ý = new WorldServer[3];
        this.Ø = new long[this.Ý.length][100];
        this.HorizonCode_Horizon_È(this.Âµà(), var7);
        WorldInfo var8 = var7.Ý();
        if (var8 == null) {
            var8 = new WorldInfo(this.á, p_71247_2_);
        }
        else {
            var8.HorizonCode_Horizon_È(p_71247_2_);
        }
        for (int var9 = 0; var9 < this.Ý.length; ++var9) {
            byte var10 = 0;
            if (var9 == 1) {
                var10 = -1;
            }
            if (var9 == 2) {
                var10 = 1;
            }
            if (var9 == 0) {
                if (this.áŠ()) {
                    this.Ý[var9] = (WorldServer)new DemoWorldServer(this, var7, var8, var10, this.Â).Ø();
                }
                else {
                    this.Ý[var9] = (WorldServer)new WorldServerOF(this, var7, var8, var10, this.Â).Ø();
                }
                this.Ý[var9].HorizonCode_Horizon_È(this.á);
            }
            else {
                this.Ý[var9] = (WorldServer)new WorldServerMulti(this, var7, var10, this.Ý[0], this.Â).Ø();
            }
            this.Ý[var9].HorizonCode_Horizon_È(new WorldManager(this, this.Ý[var9]));
        }
        this.Œ().HorizonCode_Horizon_È(this.Ý);
        if (this.Ý[0].ŒÏ().Ï­Ðƒà() == null) {
            this.HorizonCode_Horizon_È(this.ÂµÈ.ŠÄ.ÇŽÊ);
        }
        this.áˆºÑ¢Õ();
    }
    
    @Override
    protected boolean Ø() throws IOException {
        IntegratedServer.áˆºÑ¢Õ.info("Starting integrated minecraft server version 1.8");
        this.Ø­áŒŠá(true);
        this.Âµá€(true);
        this.Ó(true);
        this.à(true);
        this.Ø(true);
        IntegratedServer.áˆºÑ¢Õ.info("Generating keypair");
        this.HorizonCode_Horizon_È(CryptManager.Â());
        if (Reflector.áŒŠ.Â()) {
            final Object inst = Reflector.Ó(Reflector.áŠ, new Object[0]);
            if (!Reflector.Â(inst, Reflector.áŒŠ, this)) {
                return false;
            }
        }
        this.HorizonCode_Horizon_È(this.Âµà(), this.Ç(), this.á.Ø­áŒŠá(), this.á.Ø(), this.á.áˆºÑ¢Õ());
        this.Ø(String.valueOf(this.ŠÂµà()) + " - " + this.Ý[0].ŒÏ().áˆºÑ¢Õ());
        if (Reflector.ˆáŠ.Â()) {
            final Object inst = Reflector.Ó(Reflector.áŠ, new Object[0]);
            if (Reflector.ˆáŠ.Ý() == Boolean.TYPE) {
                return Reflector.Â(inst, Reflector.ˆáŠ, this);
            }
            Reflector.HorizonCode_Horizon_È(inst, Reflector.ˆáŠ, this);
        }
        return true;
    }
    
    @Override
    public void Ï­Ðƒà() {
        final boolean var1 = this.ˆÏ­;
        this.ˆÏ­ = (Minecraft.áŒŠà().µÕ() != null && Minecraft.áŒŠà().áŒŠ());
        if (!var1 && this.ˆÏ­) {
            IntegratedServer.áˆºÑ¢Õ.info("Saving and pausing game...");
            this.Œ().ÂµÈ();
            this.HorizonCode_Horizon_È(false);
        }
        if (this.ˆÏ­) {
            final Queue var2 = this.áŒŠÆ;
            final Queue var3 = this.áŒŠÆ;
            synchronized (this.áŒŠÆ) {
                while (!this.áŒŠÆ.isEmpty()) {
                    try {
                        this.áŒŠÆ.poll().run();
                    }
                    catch (Throwable var4) {
                        IntegratedServer.áˆºÑ¢Õ.fatal((Object)var4);
                    }
                }
                // monitorexit(this.\u00e1\u0152\u0160\u00c6)
                return;
            }
        }
        super.Ï­Ðƒà();
        if (this.ÂµÈ.ŠÄ.Ý != this.Œ().Ø­à()) {
            IntegratedServer.áˆºÑ¢Õ.info("Changing view distance to {}, from {}", new Object[] { this.ÂµÈ.ŠÄ.Ý, this.Œ().Ø­à() });
            this.Œ().HorizonCode_Horizon_È(this.ÂµÈ.ŠÄ.Ý);
        }
        if (this.ÂµÈ.áŒŠÆ != null) {
            final WorldInfo var5 = this.Ý[0].ŒÏ();
            final WorldInfo var6 = this.ÂµÈ.áŒŠÆ.ŒÏ();
            if (!var5.áŒŠà() && var6.Ï­Ðƒà() != var5.Ï­Ðƒà()) {
                IntegratedServer.áˆºÑ¢Õ.info("Changing difficulty to {}, from {}", new Object[] { var6.Ï­Ðƒà(), var5.Ï­Ðƒà() });
                this.HorizonCode_Horizon_È(var6.Ï­Ðƒà());
            }
            else if (var6.áŒŠà() && !var5.áŒŠà()) {
                IntegratedServer.áˆºÑ¢Õ.info("Locking difficulty to {}", new Object[] { var6.Ï­Ðƒà() });
                for (final WorldServer var10 : this.Ý) {
                    if (var10 != null) {
                        var10.ŒÏ().Âµá€(true);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean ÂµÈ() {
        return false;
    }
    
    @Override
    public WorldSettings.HorizonCode_Horizon_È á() {
        return this.á.Âµá€();
    }
    
    @Override
    public EnumDifficulty ˆÏ­() {
        return this.ÂµÈ.áŒŠÆ.ŒÏ().Ï­Ðƒà();
    }
    
    @Override
    public boolean Å() {
        return this.á.Ó();
    }
    
    @Override
    public File Æ() {
        return this.ÂµÈ.ŒÏ;
    }
    
    @Override
    public boolean Ä() {
        return false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final CrashReport report) {
        this.ÂµÈ.HorizonCode_Horizon_È(report);
    }
    
    @Override
    public CrashReport Â(CrashReport report) {
        report = super.Â(report);
        report.Ó().HorizonCode_Horizon_È("Type", new Callable() {
            private static final String Â = "CL_00001130";
            
            public String HorizonCode_Horizon_È() {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.Ó().HorizonCode_Horizon_È("Is Modded", new Callable() {
            private static final String Â = "CL_00001131";
            
            public String HorizonCode_Horizon_È() {
                String var1 = ClientBrandRetriever.HorizonCode_Horizon_È();
                if (!var1.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + var1 + "'";
                }
                var1 = IntegratedServer.this.É();
                return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + var1 + "'");
            }
        });
        return report;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EnumDifficulty difficulty) {
        super.HorizonCode_Horizon_È(difficulty);
        if (this.ÂµÈ.áŒŠÆ != null) {
            this.ÂµÈ.áŒŠÆ.ŒÏ().HorizonCode_Horizon_È(difficulty);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PlayerUsageSnooper playerSnooper) {
        super.HorizonCode_Horizon_È(playerSnooper);
        playerSnooper.HorizonCode_Horizon_È("snooper_partner", this.ÂµÈ.É().Ó());
    }
    
    @Override
    public boolean ŒÏ() {
        return Minecraft.áŒŠà().ŒÏ();
    }
    
    @Override
    public String HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È type, final boolean allowCheats) {
        try {
            int var6 = -1;
            try {
                var6 = HttpUtil.HorizonCode_Horizon_È();
            }
            catch (IOException ex) {}
            if (var6 <= 0) {
                var6 = 25564;
            }
            this.£Ï().HorizonCode_Horizon_È(null, var6);
            IntegratedServer.áˆºÑ¢Õ.info("Started on " + var6);
            this.£á = true;
            (this.Å = new ThreadLanServerPing(this.áˆºÏ(), new StringBuilder(String.valueOf(var6)).toString())).start();
            this.Œ().HorizonCode_Horizon_È(type);
            this.Œ().Â(allowCheats);
            return new StringBuilder(String.valueOf(var6)).toString();
        }
        catch (IOException var7) {
            return null;
        }
    }
    
    @Override
    public void ˆà() {
        super.ˆà();
        if (this.Å != null) {
            this.Å.interrupt();
            this.Å = null;
        }
    }
    
    @Override
    public void Ø­à() {
        Futures.getUnchecked((Future)this.HorizonCode_Horizon_È(new Runnable() {
            private static final String Â = "CL_00002380";
            
            @Override
            public void run() {
                final ArrayList var1 = Lists.newArrayList((Iterable)IntegratedServer.this.Œ().Âµá€);
                for (final EntityPlayerMP var3 : var1) {
                    IntegratedServer.this.Œ().Âµá€(var3);
                }
            }
        }));
        super.Ø­à();
        if (this.Å != null) {
            this.Å.interrupt();
            this.Å = null;
        }
    }
    
    public void ŠÓ() {
        this.µÕ();
    }
    
    public boolean ÇŽá() {
        return this.£á;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È gameMode) {
        this.Œ().HorizonCode_Horizon_È(gameMode);
    }
    
    @Override
    public boolean ÇªÓ() {
        return true;
    }
    
    @Override
    public int £à() {
        return 4;
    }
}
