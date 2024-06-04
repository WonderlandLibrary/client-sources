package com.polarware.module.impl.player;

import com.polarware.Client;
import com.polarware.component.impl.player.PingSpoofComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.BoundsNumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;

/**
 * @author Alan
 * @since 23/10/2021
 */

@ModuleInfo(name = "module.player.blink.name", description = "module.player.blink.description", category = Category.PLAYER)
public class BlinkModule extends Module {

    public BooleanValue pulse = new BooleanValue("Pulse", this, false);
    public BooleanValue incoming = new BooleanValue("Incoming", this, false);
    public BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 2, 2, 2, 40, 1, () -> !pulse.getValue());
    public int next;
    private EntityOtherPlayerMP blinkEntity;

    @Override
    protected void onEnable() {
        getNext();
        spawnEntity();
    }

    @Override
    protected void onDisable() {
        deSpawnEntity();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        PingSpoofComponent.setSpoofing(999999999, incoming.getValue(), incoming.getValue(), incoming.getValue(), incoming.getValue(), incoming.getValue(), true);

        if (mc.thePlayer.ticksExisted > next && pulse.getValue()) {
            getNext();
            PingSpoofComponent.dispatch();

            deSpawnEntity();
            spawnEntity();
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        getNext();
    };

    public void getNext() {
        if (mc.thePlayer == null) return;
        next = mc.thePlayer.ticksExisted + (int) MathUtil.getRandom(delay.getValue().intValue(), delay.getSecondValue().intValue());
    }

    public void deSpawnEntity() {
        if (blinkEntity != null) {
            Client.INSTANCE.getBotComponent().remove(blinkEntity);
            mc.theWorld.removeEntityFromWorld(blinkEntity.getEntityId());
            blinkEntity = null;
        }
    }

    public void spawnEntity() {
        if (blinkEntity == null) {
//            blinkEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
//            blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
//            blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
//            blinkEntity.setSprinting(mc.thePlayer.isSprinting());
//            blinkEntity.setInvisible(mc.thePlayer.isInvisible());
//            blinkEntity.setSneaking(mc.thePlayer.isSneaking());
//            blinkEntity.inventory = mc.thePlayer.inventory;
//            Client.INSTANCE.getBotManager().add(blinkEntity);
//
//            mc.theWorld.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        }
    }
}