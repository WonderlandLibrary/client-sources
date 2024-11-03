package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to deal criticals every hit",
   name = "Criticals",
   key = 0
)
public class CriticalsModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Edit", "Ground", "Packet", "Verus", "Vulcan", "NCP"});
   public int ticks;
   public int attacks;
   public boolean attacked;

   public CriticalsModule() {
      this.addSettings(new ModuleSettings[]{this.mode});
   }

   @Override
   public void onEnable() {
      this.setInfo(this.mode.getMode());
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onAttack(EventAttack event) {
      if (event.getEntity() != null && event.getEntity() instanceof EntityLivingBase) {
         EntityLivingBase ent = (EntityLivingBase)event.getEntity();
         this.attacked = true;
         if (ent.hurtTime <= 2 && this.mode.getMode().equalsIgnoreCase("Packet")) {
            this.mc
               .getNetHandler()
               .getNetworkManager()
               .sendPacketNoEvent(
                  new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ, false)
               );
            this.mc
               .getNetHandler()
               .getNetworkManager()
               .sendPacketNoEvent(
                  new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, false)
               );
         }

         this.attacks++;
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion event) {
      if (event.getType() == EventType.PRE) {
         KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
         if (ka.isEnabled() && ka.target != null) {
            String var3;
            switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1237460601:
                  if (var3.equals("ground")) {
                     if (this.mc.thePlayer.onGround && this.attacked) {
                        this.ticks++;
                        switch (this.ticks) {
                           case 1:
                              event.setY(event.getY() + 5.0E-4);
                              break;
                           case 2:
                              event.setY(event.getY() + 1.0E-4);
                              this.attacked = false;
                        }

                        event.setOnGround(false);
                     } else {
                        this.attacked = false;
                        this.ticks = 0;
                     }
                  }
                  break;
               case -995865464:
                  if (var3.equals("packet")) {
                     event.setOnGround(false);
                     if (this.mc.thePlayer.ticksExisted % 20 == 0) {
                        event.setOnGround(true);
                     }
                  }
                  break;
               case -805359837:
                  if (var3.equals("vulcan")) {
                     if (this.attacked) {
                        this.ticks++;
                        switch (this.ticks) {
                           case 1:
                              event.setY(event.getY() + 0.164);
                              event.setOnGround(false);
                              break;
                           case 2:
                              event.setY(event.getY() + 0.083);
                              event.setOnGround(false);
                              break;
                           case 3:
                              event.setY(event.getY() + 0.003);
                              event.setOnGround(false);
                              this.attacked = false;
                        }
                     } else {
                        this.attacked = false;
                        this.ticks = 0;
                     }
                  }
                  break;
               case 108891:
                  if (var3.equals("ncp")) {
                     if (this.attacked) {
                        this.ticks++;
                        switch (this.ticks) {
                           case 1:
                              event.setY(event.getY() + 0.001);
                              event.setOnGround(true);
                              break;
                           case 2:
                              event.setOnGround(false);
                              this.attacked = false;
                        }
                     } else {
                        this.attacked = false;
                        this.ticks = 0;
                     }
                  }
                  break;
               case 3108362:
                  if (var3.equals("edit")) {
                     if (this.mc.thePlayer.onGround && this.attacked) {
                        this.ticks++;
                        switch (this.ticks) {
                           case 1:
                              event.setY(event.getY() + 5.0E-4);
                              break;
                           case 2:
                              event.setY(event.getY() + 1.0E-4);
                              this.attacked = false;
                        }

                        event.setOnGround(false);
                        if (this.mc.thePlayer.ticksExisted % 20 == 0) {
                           event.setOnGround(true);
                        }
                     } else {
                        this.attacked = false;
                        this.ticks = 0;
                     }
                  }
                  break;
               case 112097665:
                  if (var3.equals("verus")) {
                     if (this.attacked) {
                        this.ticks++;
                        switch (this.ticks) {
                           case 1:
                              event.setY(event.getY() + 0.001);
                              event.setOnGround(true);
                              break;
                           case 2:
                              event.setOnGround(false);
                              this.attacked = false;
                        }
                     } else {
                        this.attacked = false;
                        this.ticks = 0;
                     }
                  }
            }
         }
      }
   }
}
