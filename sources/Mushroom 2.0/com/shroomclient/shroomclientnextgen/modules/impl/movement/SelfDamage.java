package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;

//@RegisterModule(
//        name = "Kill Yourself",
//        uniqueId = "kys",
//        description = "Self Damages, Dev Feature",
//        category = ModuleCategory.Movement
//)
public class SelfDamage extends Module {

    @Override
    protected void onEnable() {
        //new HealthUpdateS2CPacket(C.p().getHealth()-0.1f, 20,20).apply(C.mc.getNetworkHandler());

        //C.p().swingHand(Hand.MAIN_HAND);
        //C.mc.interactionManager.attackEntity(C.p(), C.p());
        //PacketUtil.sendPacket(new PlayerInteractEntityC2SPacket(C.p().getId(), C.p().isSneaking(), PlayerInteractEntityC2SPacket.ATTACK), false);

        ModuleManager.toggle(SelfDamage.class, false);
    }

    @Override
    protected void onDisable() {}
}
