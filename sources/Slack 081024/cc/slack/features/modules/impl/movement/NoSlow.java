// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.BlinkUtil;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;

@ModuleInfo(
        name = "NoSlow",
        category = Category.MOVEMENT
)

public class NoSlow extends Module {

    // Slow Modes
    public final ModeValue<String> blockmode = new ModeValue<>("Block", new String[]{"None", "Vanilla", "NCP Latest", "Hypixel", "Hypixel Spoof", "Switch", "Place", "C08 Tick"});
    public final ModeValue<String> eatmode = new ModeValue<>("Eat", new String[]{"None","Vanilla", "NCP Latest", "Hypixel", "Switch", "Place", "C08 Tick", "Blink", "Slowed"});
    public final ModeValue<String> potionmode = new ModeValue<>("Potion", new String[]{"None","Vanilla", "NCP Latest", "Hypixel", "Switch", "Place", "C08 Tick"});
    public final ModeValue<String> bowmode = new ModeValue<>("Bow", new String[]{"None","Vanilla", "NCP Latest", "Hypixel", "Switch", "Place", "C08 Tick"});


    public final NumberValue<Float> forwardMultiplier = new NumberValue<>("Forward Multiplier", 1f, 0.2f,1f, 0.05f);
    public final NumberValue<Float> strafeMultiplier = new NumberValue<>("Strafe Multiplier", 1f, 0.2f,1f, 0.05f);
    private final BooleanValue sprint = new BooleanValue("Sprint", true);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Advanced","Simple", "Off"});


    public float fMultiplier = 0F;
    public float sMultiplier = 0F;
    public boolean sprinting = true;

    private boolean blink = false;

    public NoSlow() {
        addSettings(blockmode, eatmode, potionmode, bowmode
                , forwardMultiplier, strafeMultiplier, sprint);
    }

    private boolean badC07 = false;

    @Override
    public void onDisable() {
        if (blink) {
            blink = false;
            BlinkUtil.disable();
        }
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer == null || mc.thePlayer.getHeldItem() == null || mc.thePlayer.getHeldItem().item == null) return;

        fMultiplier = forwardMultiplier.getValue();
        sMultiplier = strafeMultiplier.getValue();

        boolean usingItem = mc.thePlayer.isUsingItem() || (Slack.getInstance().getModuleManager().getInstance(KillAura.class).isToggle() && Slack.getInstance().getModuleManager().getInstance(KillAura.class).isBlocking);

        if (usingItem && mc.thePlayer.getHeldItem().item instanceof ItemSword) {
            String mode = blockmode.getValue().toLowerCase();
            processModeSword(mode);
        }

        if (mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().item instanceof ItemFood) {
            String mode = eatmode.getValue().toLowerCase();
            processModeEat(mode);
        } else if (!mc.thePlayer.isUsingItem() && blink) {
            blink = false;
            BlinkUtil.disable();
        }

        if (mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().item instanceof ItemPotion) {
            String mode = potionmode.getValue().toLowerCase();
            processModePotion(mode);
        }

        if (mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().item instanceof ItemBow) {
            String mode = bowmode.getValue().toLowerCase();
            processModeBow(mode);
        }

        badC07 = false;
    }

    // onSword (Blocking)
    private void processModeSword(String mode) {
        switch (mode) {
            case "none":
                setMultipliers(0.2F, 0.2F);
                break;
            case "vanilla":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
            case "ncp latest":
            case "switch":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                break;
            case "place":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                break;
            case "c08 tick":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
                break;
            case "hypixel spoof":
                setMultipliers(1, 1);
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                } else {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                break;
            case "hypixel":
                setMultipliers(1, 1);
                if (mc.thePlayer.isSprinting()) {
                    switch (mc.thePlayer.ticksExisted % 4) {
                        case 0:
                            sprinting = false;
                            mc.thePlayer.setSprinting(false);
                            break;
                        case 1:
                            sprinting = true;
                            break;
                    }
                }
                break;
        }
    }


    // onEat (Eating and drinking)
    private void processModeEat(String mode) {
        switch (mode) {
            case "none":
                setMultipliers(0.2F, 0.2F);
                break;
            case "vanilla":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
            case "ncp latest":
            case "switch":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                break;
            case "place":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                break;
            case "c08 tick":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
                break;
            case "hypixel":
                if (mc.thePlayer.ticksExisted % 3 == 0 && !badC07 && !(mc.thePlayer.getHeldItem().item instanceof ItemSword)) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0.0f, 0.0f, 0.0f));
                }
                MovementUtil.spoofNextC03(false);
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
            case "blink":
                if (mc.thePlayer.getItemInUseDuration() == 2) {
                    blink = true;
                    BlinkUtil.enable(false, true);
                } else if (mc.thePlayer.getItemInUseDuration() == 29) {
                    PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0.0f, 0.0f, 0.0f));
                    blink = false;
                    BlinkUtil.disable();
                }
                break;
            case "slowed":
                setMultipliers(0.7f, 0.8f);
                break;

        }
    }

    // onPotion Slow
    private void processModePotion(String mode) {
        switch (mode) {
            case "none":
                setMultipliers(0.2F, 0.2F);
                break;
            case "vanilla":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
            case "ncp latest":
            case "switch":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                break;
            case "place":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                break;
            case "c08 tick":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
                break;
            case "hypixel":
                if (mc.thePlayer.ticksExisted % 3 == 0 && !badC07) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0.0f, 0.0f, 0.0f));
                }
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
        }
    }

    // onBow Slow
    private void processModeBow(String mode) {
        switch (mode) {
            case "none":
                setMultipliers(0.2F, 0.2F);
                break;
            case "vanilla":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
            case "ncp latest":
            case "switch":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                break;
            case "place":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                break;
            case "c08 tick":
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                }
                break;
            case "hypixel":
                if (mc.thePlayer.ticksExisted % 3 == 0 && !badC07 && !(mc.thePlayer.getHeldItem().item instanceof ItemSword)) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0.0f, 0.0f, 0.0f));
                }
                setMultipliers(forwardMultiplier.getValue(), strafeMultiplier.getValue());
                break;
        }
    }

    private void setMultipliers(float forward, float strafe) {
        fMultiplier = forward;
        sMultiplier = strafe;
        sprinting = sprint.getValue();
    }

    @Listen
    public void onPacket(PacketEvent e) {

        if (e.getPacket() instanceof C07PacketPlayerDigging) badC07 = true;
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Advanced":
                return blockmode.getValue() + ", " + eatmode.getValue() + ", " + potionmode.getValue() + ", " + bowmode.getValue();
            case "Simple":
                return blockmode.getValue();
        }
        return null;
    }

}
