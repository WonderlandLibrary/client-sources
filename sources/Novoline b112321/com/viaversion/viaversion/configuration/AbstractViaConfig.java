package com.viaversion.viaversion.configuration;

import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.util.Config;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.File;
import net.lc;

public abstract class AbstractViaConfig extends Config implements ViaVersionConfig {
   private boolean f;
   private boolean l;
   private boolean M;
   private boolean Q;
   private boolean y;
   private boolean E;
   private boolean F;
   private boolean g;
   private boolean K;
   private boolean x;
   private double hologramOffset;
   private int w;
   private String maxPPSKickMessage;
   private int I;
   private int U;
   private int T;
   private String maxPPSWarningsKickMessage;
   private boolean v;
   private boolean P;
   private boolean i;
   private boolean s;
   private boolean replacePistons;
   private int pistonReplacementId;
   private boolean R;
   private boolean n;
   private boolean q;
   private IntSet r;
   private String blockedDisconnectMessage;
   private String reloadDisconnectMessage;
   private boolean V;
   private boolean G;
   private boolean N;
   private boolean p;
   private boolean C;
   private boolean J;
   private boolean flowerStemWhenBlockAbove;
   private boolean D;
   private boolean m;
   private boolean H;
   private int O;
   private boolean t;
   private boolean k;
   private boolean A;
   private boolean B;
   private boolean W;
   private boolean ignoreLongChannelNames;

   protected AbstractViaConfig(File var1) {
      super(var1);
   }

   public void reloadConfig() {
      super.reloadConfig();
      this.a();
   }

   protected void a() {
      this.f = this.getBoolean("checkforupdates", true);
      this.l = this.getBoolean("prevent-collision", true);
      this.M = this.getBoolean("use-new-effect-indicator", true);
      this.Q = this.getBoolean("use-new-deathmessages", true);
      this.y = this.getBoolean("suppress-metadata-errors", false);
      this.E = this.getBoolean("shield-blocking", true);
      this.F = this.getBoolean("hologram-patch", false);
      this.g = this.getBoolean("piston-animation-patch", false);
      this.K = this.getBoolean("bossbar-patch", true);
      this.x = this.getBoolean("bossbar-anti-flicker", false);
      this.hologramOffset = this.getDouble("hologram-y", -0.96D);
      this.w = this.getInt("max-pps", 800);
      lc.n();
      this.maxPPSKickMessage = this.getString("max-pps-kick-msg", "Sending packets too fast? lag?");
      this.I = this.getInt("tracking-period", 6);
      this.U = this.getInt("tracking-warning-pps", 120);
      this.T = this.getInt("tracking-max-warnings", 3);
      this.maxPPSWarningsKickMessage = this.getString("tracking-max-kick-msg", "You are sending too many packets, :(");
      this.v = this.getBoolean("send-supported-versions", false);
      this.P = this.getBoolean("simulate-pt", true);
      this.i = this.getBoolean("item-cache", true);
      this.s = this.getBoolean("nms-player-ticking", true);
      this.replacePistons = this.getBoolean("replace-pistons", false);
      this.pistonReplacementId = this.getInt("replacement-piston-id", 0);
      this.R = this.getBoolean("auto-team", true);
      this.n = this.getBoolean("force-json-transform", false);
      this.q = this.getBoolean("chat-nbt-fix", true);
      this.r = new IntOpenHashSet(this.getIntegerList("block-protocols"));
      this.blockedDisconnectMessage = this.getString("block-disconnect-msg", "You are using an unsupported Minecraft version!");
      this.reloadDisconnectMessage = this.getString("reload-disconnect-msg", "Server reload, please rejoin!");
      this.N = this.getBoolean("minimize-cooldown", true);
      this.p = this.getBoolean("team-colour-fix", true);
      this.V = this.getBoolean("suppress-conversion-warnings", false);
      this.G = this.getBoolean("disable-1_13-auto-complete", false);
      this.C = this.getBoolean("serverside-blockconnections", true);
      this.J = this.getBoolean("reduce-blockstorage-memory", false);
      this.flowerStemWhenBlockAbove = this.getBoolean("flowerstem-when-block-above", false);
      this.D = this.getBoolean("vine-climb-fix", false);
      this.m = this.getBoolean("fix-low-snow-collision", false);
      this.H = this.getBoolean("fix-infested-block-breaking", true);
      this.O = this.getInt("1_13-tab-complete-delay", 0);
      this.t = this.getBoolean("truncate-1_14-books", false);
      this.k = this.getBoolean("left-handed-handling", true);
      this.A = this.getBoolean("fix-non-full-blocklight", false);
      this.B = this.getBoolean("fix-1_14-health-nan", true);
      this.W = this.getBoolean("use-1_15-instant-respawn", false);
      this.ignoreLongChannelNames = this.getBoolean("ignore-long-1_16-channel-names", true);
   }

   public boolean i() {
      return this.f;
   }

   public void setCheckForUpdates(boolean var1) {
      this.f = var1;
      this.set("checkforupdates", Boolean.valueOf(var1));
   }

   public boolean S() {
      return this.l;
   }

   public boolean ac() {
      return this.M;
   }

   public boolean aa() {
      return this.Q;
   }

   public boolean A() {
      return this.y;
   }

   public boolean K() {
      return this.E;
   }

   public boolean I() {
      return this.F;
   }

   public boolean u() {
      return this.g;
   }

   public boolean D() {
      return this.K;
   }

   public boolean s() {
      return this.x;
   }

   public double getHologramYOffset() {
      return this.hologramOffset;
   }

   public int M() {
      return this.w;
   }

   public String getMaxPPSKickMessage() {
      return this.maxPPSKickMessage;
   }

   public int z() {
      return this.I;
   }

   public int W() {
      return this.U;
   }

   public int r() {
      return this.T;
   }

   public String getMaxWarningsKickMessage() {
      return this.maxPPSWarningsKickMessage;
   }

   public boolean w() {
      return false;
   }

   public boolean N() {
      return this.v;
   }

   public boolean B() {
      return this.P;
   }

   public boolean R() {
      return this.i;
   }

   public boolean X() {
      return this.s;
   }

   public boolean isReplacePistons() {
      return this.replacePistons;
   }

   public int getPistonReplacementId() {
      return this.pistonReplacementId;
   }

   public boolean l() {
      boolean var1 = lc.i();
      return this.l && this.R;
   }

   public boolean J() {
      return this.n;
   }

   public boolean Y() {
      return this.q;
   }

   public boolean U() {
      return false;
   }

   public IntSet a() {
      return this.r;
   }

   public String getBlockedDisconnectMsg() {
      return this.blockedDisconnectMessage;
   }

   public String getReloadDisconnectMsg() {
      return this.reloadDisconnectMessage;
   }

   public boolean g() {
      return this.N;
   }

   public boolean P() {
      return this.p;
   }

   public boolean j() {
      return this.V;
   }

   public boolean ab() {
      return this.G;
   }

   public boolean t() {
      return this.C;
   }

   public String getBlockConnectionMethod() {
      return "packet";
   }

   public boolean E() {
      return this.J;
   }

   public boolean isStemWhenBlockAbove() {
      return this.flowerStemWhenBlockAbove;
   }

   public boolean p() {
      return this.D;
   }

   public boolean v() {
      return this.m;
   }

   public boolean k() {
      return this.H;
   }

   public int C() {
      return this.O;
   }

   public boolean h() {
      return this.t;
   }

   public boolean Z() {
      return this.k;
   }

   public boolean q() {
      return false;
   }

   public boolean L() {
      return false;
   }

   public boolean x() {
      return this.A;
   }

   public boolean m() {
      return this.B;
   }

   public boolean V() {
      return this.W;
   }

   public boolean isIgnoreLong1_16ChannelNames() {
      return this.ignoreLongChannelNames;
   }
}
