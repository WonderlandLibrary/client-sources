package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.CustomCooldown;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.InvUtil;
import dev.excellent.impl.value.impl.KeyValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;

@ModuleInfo(name = "Middle Click Pearl", description = "Кидает эндер жемчуг при нажатии СКМ.", category = Category.PLAYER)
public class MiddleClickPearl extends Module {
    public static Singleton<MiddleClickPearl> singleton = Singleton.create(() -> Module.link(MiddleClickPearl.class));
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(
                    new SubMode("СКМ"),
                    new SubMode("Кнопка")
            );

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final KeyValue key = new KeyValue("Бинд", this, -1, () -> !mode.is("Кнопка"));

    private final ScriptConstructor scriptConstructor = ScriptConstructor.create();
    private final InvUtil.Hand handUtil = new InvUtil.Hand();
    private long delay;

    @Override
    public void onDisable() {
        super.onDisable();
        delay = 0;
    }

    private final Listener<KeyboardPressEvent> onKeyValue = event -> {
        if (mode.is("Кнопка") && event.getKeyCode() == key.getValue() && !(mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY)) {
            handleThrowPearl();
        }
    };
    private final Listener<MouseInputEvent> onMouseInput = event -> {
        if ((mode.is("СКМ") && event.getMouseButton() == 2 || mode.is("Кнопка") && event.getMouseButton() == key.getValue()) && !(mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY)) {
            handleThrowPearl();
        }
    };
    private final Listener<PacketEvent> packet = handUtil::onEventPacket;
    private final Listener<UpdateEvent> update = event -> this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    private final Listener<MotionEvent> motion = event -> scriptConstructor.update();

    private int findPearlAndThrow() {
        int hbSlot = InvUtil.getSlotInInventoryOrHotbar(Items.ENDER_PEARL, true);
        if (hbSlot != -1) {
            //this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            }
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);

            setCooldown();

            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
            this.delay = System.currentTimeMillis();
            return hbSlot;
        }

        int invSlot = InvUtil.getSlotInInventoryOrHotbar(Items.ENDER_PEARL, false);

        if (invSlot != -1) {
            handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);

            setCooldown();
            this.delay = System.currentTimeMillis();
            return invSlot;
        }
        return -1;
    }

    public void handleThrowPearl() {
        scriptConstructor.cleanup().addStep(0, () -> {
            if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL)) {
                boolean isOffhandEnderPearl = mc.player.getHeldItemOffhand().getItem() instanceof EnderPearlItem;
                if (isOffhandEnderPearl) {
                    mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    mc.player.swingArm(Hand.MAIN_HAND);
                } else {
                    int slot = findPearlAndThrow();
                    if (slot > 8) {
                        mc.playerController.pickItem(slot);
                    }
                }
            }
        });
    }

    private static void setCooldown() {
        CustomCooldown cooldown = CustomCooldown.singleton.get();
        CustomCooldown.ItemEnum itemEnum = CustomCooldown.ItemEnum.getItemEnum(Items.ENDER_PEARL);
        if (cooldown.isEnabled() && itemEnum != null && cooldown.isCurrentItem(itemEnum)) {
            cooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
        }
    }
}