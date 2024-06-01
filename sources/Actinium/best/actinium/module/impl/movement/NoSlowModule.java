package best.actinium.module.impl.movement;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.move.SlowDownEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.player.MoveUtil;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;

/**
 * @author Nyghtfull
 * @since 12/2/2023
 */
@ModuleInfo(
        name = "No slow",
        description = "Removes SlowDown",
        category = ModuleCategory.MOVEMENT
)
public class NoSlowModule extends Module {
    public NumberProperty swordSlowDown = new NumberProperty("Sword Slowdown", this, 0f, 0.2f, 1f, 0.1f);
    public NumberProperty consumableSlowDown = new NumberProperty("Consumable Slowdown", this, 0f, 0.2f, 1f, 0.1f);
    public NumberProperty bowSlowDown = new NumberProperty("Bow Slowdown", this, 0f, 0.2f, 1f, 0.1f);
    private BooleanProperty packet = new BooleanProperty("Packet",this,false);
    public ModeProperty swordPacketMode = new ModeProperty("Sword Packet", this, new String[] {"NCP","Old NCP","Switch","Intave","Bug","Private"}, "NCP").setHidden(() -> !packet.isEnabled());
    public ModeProperty consumablePacketMode = new ModeProperty("Consumable Packet", this, new String[] {"NCP","Old NCP","Switch","Intave","Bug","Private"}, "NCP").setHidden(() -> !packet.isEnabled());
    public ModeProperty bowPacketMode = new ModeProperty("Bow Packet", this, new String[] {"NCP","Old NCP","Switch","Intave","Bug","Private"}, "NCP").setHidden(() -> !packet.isEnabled());
    private String packetMode;
    private int counter = 0;
    private boolean sent = false;

    @Override
    public void onDisable() {
        sent = false;
        super.onDisable();
    }

    @Callback
    public void onUpdate(MotionEvent event) {
        setSuffix(consumablePacketMode.is("Private") ? consumablePacketMode.getMode() : "");

        if(!packet.isEnabled() || !mc.thePlayer.isUsingItem()) {
            sent = false;
            return;
        }

        if (PlayerUtil.isHoldingBow()) {
            packetMode = bowPacketMode.getMode();
        } else if (PlayerUtil.isHoldingSword()) {
            packetMode = swordPacketMode.getMode();
        } else if (PlayerUtil.isHoldingConsumable()) {
            packetMode = consumablePacketMode.getMode();
        }


        switch (packetMode) {
            case "NCP":

                break;
            case "Old NCP":

                break;
            case "Intave":
                if (event.getType() == EventType.PRE) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                } else if (mc.thePlayer.isUsingItem()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
                break;
            case "Private":
                //or jmust dont check
                //3
                if(MoveUtil.isMoving() && mc.thePlayer.ticksExisted % 5 == 0) {
                    ChatUtil.display("silent");
                    PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0, 0, 0));
                }
               // if (sent && mc.thePlayer.ticksExisted % 3 == 0 && event.getType() == EventType.PRE) {
                 //   ChatUtil.display("nut");
                //    PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0, 0, 0));
               // }
                break;
            case "Switch":
                if (MoveUtil.isMoving()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                break;
        }

        if(swordPacketMode.is("Private") && PlayerUtil.isHoldingSword()) {
           // ChatUtil.display("null");
            //maybe tick 2 stuff or something
            //prob patched
            //PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(null));
        }

    }

    @Callback
    public void onPacket(PacketEvent event) {
        if (packetMode != null) {
            switch (packetMode) {
                case "Private":
                    if (event.getPacket() instanceof C08PacketPlayerBlockPlacement c08) {
                        if (
                                c08.stack != null
                        ) {
                            sent = true;
                        }
                    }
                    break;
            }
        }
    }

    @Callback
    public void onClick(ClickEvent event) {
        if(packetMode == null || !mc.thePlayer.isUsingItem()) {
            counter = 0;
            return;
        }

        if(packetMode.equals("Bug")) {
            event.setShouldRightClick(false);
            if (counter != 1) {
                mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                mc.thePlayer.stopUsingItem();
                mc.thePlayer.closeScreen();
                event.setCancelled(true);
                counter = 1;
            }
        }

        if (event.isCancelled()) {
            mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
        }
    }

    @Callback
    public void onSlow(SlowDownEvent event) {
        event.setCancelled(true);
        event.setSlowdownAmount(PlayerUtil.isHoldingConsumable() ? consumableSlowDown.getValue().floatValue() :
                (PlayerUtil.isHoldingBow() ? bowSlowDown.getValue().floatValue() :
                        (PlayerUtil.isHoldingSword() ? swordSlowDown.getValue().floatValue() : 0.2f)));
    }
}
