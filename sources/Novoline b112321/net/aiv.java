package net;

import com.viaversion.viabackwards.ViaBackwardsConfig;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import java.io.File;
import java.util.logging.Logger;
import net.VV;
import net.aR7;
import net.aR9;
import net.aRC;
import net.aRD;
import net.aRJ;
import net.aRK;
import net.aRM;
import net.aRQ;
import net.aRV;
import net.aRZ;
import net.aRc;
import net.aRg;
import net.aRo;
import net.ay5;
import net.ay_;
import net.ayj;
import net.ayk;
import net.ayx;
import net.hz;
import net.ku;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public interface aiv {
   String a = "3.2.0";

   default void a(File var1) {
      ViaBackwardsConfig var3 = new ViaBackwardsConfig(new File(var1, "config.yml"));
      var3.reloadConfig();
      BackwardsProtocol.a();
      VV.a(this, var3);
      String var4 = VV.class.getPackage().getImplementationVersion();
      Via.b().e().add(var4 != null?var4:"UNKNOWN");
      this.a().info("Loading translations...");
      ku.b();
      this.a().info("Registering protocols...");
      ProtocolManagerImpl.a((ayx)(new Protocol1_9_4To1_10()), (ProtocolVersion)ProtocolVersion.v1_9_3, (ProtocolVersion)ProtocolVersion.v1_10);
      ProtocolManagerImpl.a((ayx)(new Protocol1_10To1_11()), (ProtocolVersion)ProtocolVersion.v1_10, (ProtocolVersion)ProtocolVersion.v1_11);
      ProtocolManagerImpl.a((ayx)(new ayj()), (ProtocolVersion)ProtocolVersion.v1_11, (ProtocolVersion)ProtocolVersion.v1_11_1);
      ProtocolManagerImpl.a((ayx)(new ay_()), (ProtocolVersion)ProtocolVersion.v1_11_1, (ProtocolVersion)ProtocolVersion.v1_12);
      ProtocolManagerImpl.a((ayx)(new aRg()), (ProtocolVersion)ProtocolVersion.v1_12, (ProtocolVersion)ProtocolVersion.v1_12_1);
      ProtocolManagerImpl.a((ayx)(new ay5()), (ProtocolVersion)ProtocolVersion.v1_12_1, (ProtocolVersion)ProtocolVersion.v1_12_2);
      ProtocolManagerImpl.a((ayx)(new ayk()), (ProtocolVersion)ProtocolVersion.v1_12_2, (ProtocolVersion)ProtocolVersion.v1_13);
      ProtocolManagerImpl.a((ayx)(new aRQ()), (ProtocolVersion)ProtocolVersion.v1_13, (ProtocolVersion)ProtocolVersion.v1_13_1);
      ProtocolManagerImpl.a((ayx)(new aRJ()), (ProtocolVersion)ProtocolVersion.v1_13_1, (ProtocolVersion)ProtocolVersion.v1_13_2);
      ProtocolManagerImpl.a((ayx)(new Protocol1_13_2To1_14()), (ProtocolVersion)ProtocolVersion.v1_13_2, (ProtocolVersion)ProtocolVersion.v1_14);
      ProtocolManagerImpl.a((ayx)(new aRD()), (ProtocolVersion)ProtocolVersion.v1_14, (ProtocolVersion)ProtocolVersion.v1_14_1);
      ProtocolManagerImpl.a((ayx)(new aRc()), (ProtocolVersion)ProtocolVersion.v1_14_1, (ProtocolVersion)ProtocolVersion.v1_14_2);
      ProtocolManagerImpl.a((ayx)(new aRC()), (ProtocolVersion)ProtocolVersion.v1_14_2, (ProtocolVersion)ProtocolVersion.v1_14_3);
      ProtocolManagerImpl.a((ayx)(new aRo()), (ProtocolVersion)ProtocolVersion.v1_14_3, (ProtocolVersion)ProtocolVersion.v1_14_4);
      ProtocolManagerImpl.a((ayx)(new Protocol1_14_4To1_15()), (ProtocolVersion)ProtocolVersion.v1_14_4, (ProtocolVersion)ProtocolVersion.v1_15);
      ProtocolManagerImpl.a((ayx)(new aRK()), (ProtocolVersion)ProtocolVersion.v1_15, (ProtocolVersion)ProtocolVersion.v1_15_1);
      ProtocolManagerImpl.a((ayx)(new aR9()), (ProtocolVersion)ProtocolVersion.v1_15_1, (ProtocolVersion)ProtocolVersion.v1_15_2);
      ProtocolManagerImpl.a((ayx)(new aR7()), (ProtocolVersion)ProtocolVersion.v1_15_2, (ProtocolVersion)ProtocolVersion.v1_16);
      ProtocolManagerImpl.a((ayx)(new aRZ()), (ProtocolVersion)ProtocolVersion.v1_16, (ProtocolVersion)ProtocolVersion.v1_16_1);
      ProtocolManagerImpl.a((ayx)(new Protocol1_16_1To1_16_2()), (ProtocolVersion)ProtocolVersion.v1_16_1, (ProtocolVersion)ProtocolVersion.v1_16_2);
      ProtocolManagerImpl.a((ayx)(new aRV()), (ProtocolVersion)ProtocolVersion.v1_16_2, (ProtocolVersion)ProtocolVersion.v1_16_3);
      ProtocolManagerImpl.a((ayx)(new aRM()), (ProtocolVersion)ProtocolVersion.v1_16_3, (ProtocolVersion)ProtocolVersion.u);
   }

   Logger a();

   default boolean c() {
      BackwardsProtocol.a();
      String var2 = Via.d().f();
      if((new hz(var2)).a(new hz("3.2.0--")) < 0) {
         this.a().severe("================================");
         this.a().severe("YOUR VIAVERSION IS OUTDATED");
         this.a().severe("PLEASE USE VIAVERSION 3.2.0 OR NEWER");
         this.a().severe("LINK: https://ci.viaversion.com/");
         this.a().severe("VIABACKWARDS WILL NOW DISABLE");
         this.a().severe("================================");
         this.d();
         return true;
      } else {
         return false;
      }
   }

   void d();

   File b();
}
