package net;

import net.VV;
import net.acE;
import net.aqI;
import net.awo;
import net.ayC;
import net.ayd;
import net.azW;
import net.cA;
import net.cQ;
import net.cT;
import net.kS;
import net.q1;
import net.r;
import net.uN;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;

public class ayk extends ayd {
   public static final BackwardsMappings k;
   private aqI j;
   private static String[] l;

   public ayk() {
      super(q1.class, azW.class, uN.class, r.class);
   }

   protected void registerPackets() {
      awo.b();
      this.a(Protocol1_13To1_12_2.class, ayk::lambda$registerPackets$0);
      kS var2 = new kS(this, this, "1.13");
      var2.registerPing();
      var2.b(q1.BOSSBAR);
      var2.registerChatMessage(q1.CHAT_MESSAGE);
      var2.registerLegacyOpenWindow(q1.OPEN_WINDOW);
      var2.registerDisconnect(q1.DISCONNECT);
      var2.c(q1.COMBAT_EVENT);
      var2.d(q1.TITLE);
      var2.registerTabList(q1.TAB_LIST);
      (this.j = new aqI(this)).f();
      (new EntityPackets1_13(this)).f();
      (new PlayerPacket1_13(this)).f();
      (new SoundPackets1_13(this)).f();
      this.cancelOutgoing(q1.DECLARE_COMMANDS);
      this.cancelOutgoing(q1.NBT_QUERY);
      this.cancelOutgoing(q1.CRAFT_RECIPE_RESPONSE);
      this.cancelOutgoing(q1.UNLOCK_RECIPES);
      this.cancelOutgoing(q1.ADVANCEMENTS);
      this.cancelOutgoing(q1.DECLARE_RECIPES);
      this.cancelOutgoing(q1.TAGS);
      this.cancelIncoming(r.CRAFT_RECIPE_REQUEST);
      this.cancelIncoming(r.RECIPE_BOOK_DATA);
   }

   public void init(UserConnection var1) {
      String[] var2 = awo.b();
      if(!var1.has(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.has(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
      if(!var1.has(BackwardsBlockStorage.class)) {
         var1.a((cA)(new BackwardsBlockStorage(var1)));
      }

      if(!var1.has(TabCompleteStorage.class)) {
         var1.a((cA)(new TabCompleteStorage(var1)));
      }

      if(VV.c().isFix1_13FacePlayer() && !var1.has(PlayerPositionStorage1_13.class)) {
         var1.a((cA)(new PlayerPositionStorage1_13(var1)));
      }

      if(acE.b() == null) {
         awo.b(new String[2]);
      }

   }

   public aqI a() {
      return this.j;
   }

   public BackwardsMappings c() {
      return k;
   }

   private static void lambda$registerPackets$0() {
      k.load();
      PaintingMapping.init();
      Via.getManager().f().b(ayC.class, new ayC());
   }

   static {
      if(b() == null) {
         a(new String[4]);
      }

      k = new BackwardsMappings();
   }

   public static void a(String[] var0) {
      l = var0;
   }

   public static String[] b() {
      return l;
   }
}
