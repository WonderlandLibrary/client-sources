package wtf.diablo.client.module.impl.player.nofall;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;
import wtf.diablo.client.module.impl.movement.FlightModule;
import wtf.diablo.client.module.impl.movement.LongJumpModule;
import wtf.diablo.client.module.impl.player.AntiVoidModule;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.mc.player.block.BlockUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO: Release queue function
@ModuleMetaData(
        name = "No Fall",
        description = "Prevents fall damage",
        category = ModuleCategoryEnum.PLAYER
)
public final class NoFallModule extends AbstractModule {

    private final ModeSetting<NoFallModeEnum> enumNoFallModeModeSetting = new ModeSetting<>("Mode", NoFallModeEnum.VANILLA);

    private final Collection<Packet<?>> packetQueue = new ArrayList<>();

    public NoFallModule() {
        this.registerSettings(enumNoFallModeModeSetting);
    }

    private boolean trigger;

    @Override
    protected void onEnable() {
        super.onEnable();
        trigger = false;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        sendPackets();
    }

    private boolean doWatchdog;

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event ->
    {
        this.setSuffix(enumNoFallModeModeSetting.getValue().getName());

        final ModuleRepository moduleRepository = Diablo.getInstance().getModuleRepository();

        final FlightModule flightModule = moduleRepository.getModuleInstance(FlightModule.class);
        final LongJumpModule longJumpModule = moduleRepository.getModuleInstance(LongJumpModule.class);
        final AntiVoidModule antiVoidModule = moduleRepository.getModuleInstance(AntiVoidModule.class);

        if (flightModule.isEnabled() || longJumpModule.isEnabled() || !antiVoidModule.isQueueEmpty()) {
            this.sendPackets();
            return;
        }

        if (event.getEventType() != EventTypeEnum.PRE) {
            return;
        }

        switch (enumNoFallModeModeSetting.getValue()) {
            /*
            case WATCHDOG:
                this.doWatchdog = false;

                if (mc.thePlayer.fallDistance > 5 && BlockUtils.isBlockUnder()) {
                    event.setOnGround(true);
                }
                break;

             */
            case VANILLA:
                if (mc.thePlayer.fallDistance > 3) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
                break;
            case GROUNDSPOOF:
                if (mc.thePlayer.fallDistance > 3 && !mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isSpectator() && !mc.thePlayer.capabilities.isFlying && mc.thePlayer.lastTickPosY - mc.thePlayer.posY > 0.0D) {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        event.setOnGround(true);
                    }
                }
                break;
            case KARHU:
                if (mc.thePlayer.fallDistance > 3) trigger = true;

                if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir) && trigger) {
                    event.setY(event.getPosY() - 1);
                    event.setOnGround(true);
                    trigger = false;
                }
                return;
            case BLOCKS_MC:
                if (mc.thePlayer.fallDistance >= 2.5f) {
                    if (mc.thePlayer.ticksExisted % 8 == 0)
                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 255, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
                    event.setOnGround(true);
                    mc.thePlayer.fallDistance = 0;
                }
                return;
            case VERUS:
                if (mc.thePlayer.fallDistance > 2) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement
                            (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1,
                                    new ItemStack(Blocks.stone.getItem
                                            (mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
                    event.setOnGround(true);
                    event.setY(Math.round(mc.thePlayer.posY));
                    mc.thePlayer.fallDistance = 0;
                }
                return;
            case AIR:
                event.setOnGround(false);
                return;
            case GROUND:
                event.setOnGround(true);
                return;
            case NCP:
                if (mc.thePlayer.fallDistance > 3) {
                    if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ, false));
                        event.setOnGround(true);
                        mc.thePlayer.fallDistance = 0;
                    }
                }
                return;
            case VULCAN:
                if (mc.thePlayer.fallDistance > 3) {
                    if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                        event.setOnGround(true);
                        event.setY(Math.round(event.getPosY()) - 2.2);

                        mc.thePlayer.fallDistance = 0;
                    }
                }
                break;
        }
    };

    private void sendPackets() {
        packetQueue.forEach(packet -> {
            mc.getNetHandler().addToSendQueue(packet);
        });
        packetQueue.clear();
    }
}