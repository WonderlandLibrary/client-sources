package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Timer;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

public class ShulkerDupe extends Module {

    private final SettingGroup sgAutoTool = settings.createGroup("AutoTool");

    public ShulkerDupe() {
        super(Categories.Experimental, Items.SHULKER_SHELL, "shulker-dupe", "ShulkerDupe only works in vanilla, forge, and fabric servers version 1.19 and below.");
    }
    private final Setting<Boolean> autoT = sgAutoTool.add(new BoolSetting.Builder()
        .name("UsePickaxeWhenDupe")
        .description("Uses Pickaxe when breaking shulker.")
        .defaultValue(true)
        .build()
    );
    public static boolean shouldDupe;
    public static boolean shouldDupeAll;
    private boolean timerWASon=false;

    @Override
    public boolean onActivate() {
        timerWASon=false;
        shouldDupeAll=false;
        shouldDupe=false;
        return false;
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent event) {
        if (event.screen instanceof ShulkerBoxScreen) {
            shouldDupeAll=false;
            shouldDupe=false;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (shouldDupe|shouldDupeAll==true){
            if (Modules.get().get(Timer.class).isActive()) {
                timerWASon=true;
                Modules.get().get(Timer.class).toggle();
            }
            for (int i = 0; i < 8; i++) {
                if (autoT.get() && (mc.player.getInventory().getStack(0).getItem() instanceof PickaxeItem || mc.player.getInventory().getStack(1).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(2).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(3).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(4).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(5).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(6).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(7).getItem() instanceof PickaxeItem ||mc.player.getInventory().getStack(8).getItem() instanceof PickaxeItem) && !(mc.player.getInventory().getMainHandStack().getItem() instanceof PickaxeItem)){
                    mc.player.getInventory().selectedSlot++;
                    if (mc.player.getInventory().selectedSlot>8) mc.player.getInventory().selectedSlot=0;
                }
            }
        } else if (!shouldDupe|!shouldDupeAll==true){
            if (!Modules.get().get(Timer.class).isActive() && timerWASon==true) {
                timerWASon=false;
                Modules.get().get(Timer.class).toggle();
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.currentScreen instanceof ShulkerBoxScreen && mc.player != null) {
            HitResult wow = mc.crosshairTarget;
            BlockHitResult a = (BlockHitResult) wow;
            if (shouldDupe|shouldDupeAll==true){
                mc.interactionManager.updateBlockBreakingProgress(a.getBlockPos(), Direction.DOWN);
            }
        }
    }

    @EventHandler
    public void onSendPacket(PacketEvent.Sent event) {
        if (event.packet instanceof PlayerActionC2SPacket) {
            if (shouldDupeAll==true){
                if (((PlayerActionC2SPacket) event.packet).getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                    for (int i = 0; i < 27; i++) {
                        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                    }
                    shouldDupeAll=false;
                }
            } else if (shouldDupe==true){
                if (((PlayerActionC2SPacket) event.packet).getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 0, 0, SlotActionType.QUICK_MOVE, mc.player);
                    shouldDupe=false;
                }
            }
        }
    }
}
