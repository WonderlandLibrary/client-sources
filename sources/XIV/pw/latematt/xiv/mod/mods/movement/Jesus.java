package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.BlockAddBBEvent;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class Jesus extends Mod implements CommandHandler {
    private final Value<Boolean> dolphin = new Value<>("jesus_dolphin", false);

    private final Listener blockAddBBListener, motionUpdatesListener, sendPacketListener;
    private boolean nextTick, shouldJump;

    public Jesus() {
        super("Jesus", ModType.MOVEMENT, Keyboard.KEY_J, 0xFF56BFE3);
        Command.newCommand().cmd("jesus").description("Base command for the Jesus mod.").arguments("<action>").handler(this).build();

        blockAddBBListener = new Listener<BlockAddBBEvent>() {
            @Override
            public void onEventCalled(BlockAddBBEvent event) {
                if (event.getBlock() instanceof BlockLiquid && event.getBlock() != null && mc.theWorld != null && mc.thePlayer != null && !dolphin.getValue()) {
                    IBlockState state = mc.theWorld.getBlockState(event.getPos());

                    if (state != null) {
                        float blockHeight = BlockLiquid.getLiquidHeightPercent(event.getBlock().getMetaFromState(state));

                        shouldJump = blockHeight < 0.55F;
                    } else {
                        shouldJump = false;
                    }

                    if (shouldJump && event.getEntity() == mc.thePlayer && !BlockUtils.isInLiquid(mc.thePlayer) && mc.thePlayer.fallDistance <= 3.0F && !mc.thePlayer.isSneaking()) {
                        BlockPos pos = event.getPos();
                        event.setAxisAlignedBB(new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
                    }
                }
            }
        };

        motionUpdatesListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (shouldJump && BlockUtils.isInLiquid(mc.thePlayer) && mc.thePlayer.isInsideOfMaterial(Material.air) && !mc.thePlayer.isSneaking()) {
                    mc.thePlayer.motionY = 0.08;
                }
            }
        };

        sendPacketListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
                    if (BlockUtils.isOnLiquid(mc.thePlayer) && shouldJump && !dolphin.getValue()) {
                        nextTick = !nextTick;
                        if (nextTick) {
                            player.setY(player.getY() - 0.01);
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(blockAddBBListener, motionUpdatesListener, sendPacketListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(blockAddBBListener, motionUpdatesListener, sendPacketListener);
        nextTick = false;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "bob":
                case "dolphin":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            dolphin.setValue(dolphin.getDefault());
                        } else {
                            dolphin.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        dolphin.setValue(!dolphin.getValue());
                    }
                    ChatLogger.print(String.format("Jesus will %s bob in water.", (dolphin.getValue() ? "now" : "no longer")));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: dolphin");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: jesus <action>");
        }
    }
}
