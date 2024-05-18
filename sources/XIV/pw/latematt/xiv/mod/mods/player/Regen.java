package pw.latematt.xiv.mod.mods.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Rederpz
 */
public class Regen extends Mod implements Listener<MotionUpdateEvent>, CommandHandler {
    private final Value<Mode> mode = new Value<>("regen_mode", Mode.POTION);
    private Listener sendPacketListener;

    public Regen() {
        super("Regen", ModType.PLAYER, Keyboard.KEY_P, 0xFF9681D6);
        setTag(mode.getValue().getName());

        Command.newCommand().cmd("regen").description("Base command for the Regen mod.").arguments("<action>").handler(this).build();

        sendPacketListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C03PacketPlayer && mode.getValue() == Mode.BYPASS) {
                    C03PacketPlayer player = (C03PacketPlayer) event.getPacket();

                    if (mc.thePlayer.onGround || BlockUtils.isOnLadder(mc.thePlayer) || BlockUtils.isInLiquid(mc.thePlayer) || BlockUtils.isOnLiquid(mc.thePlayer)) {
                        if (mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth() && mc.thePlayer.ticksExisted % 2 == 0) {
                            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(player.isOnGround()));
                        }
                    }
                }
            }
        };
    }

    public void onEventCalled(MotionUpdateEvent event) {
        if (mc.thePlayer.getActivePotionEffect(Potion.REGENERATION) != null && mode.getValue() == Mode.POTION) {
            if (mc.thePlayer.onGround || BlockUtils.isOnLadder(mc.thePlayer) || BlockUtils.isInLiquid(mc.thePlayer) || BlockUtils.isOnLiquid(mc.thePlayer)) {
                if (mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()) {
                    for (int i = 0; i < mc.thePlayer.getMaxHealth() - mc.thePlayer.getHealth(); i++) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
                    }
                }
            }
        }

        if (mode.getValue() == Mode.OLD) {
            if (mc.thePlayer.onGround || BlockUtils.isOnLadder(mc.thePlayer) || BlockUtils.isInLiquid(mc.thePlayer) || BlockUtils.isOnLiquid(mc.thePlayer)) {
                if (mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth()) {
                    for (int i = 0; i < mc.thePlayer.getMaxHealth() - mc.thePlayer.getHealth(); i++) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
                    }
                }
            }
        }
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "mode":
                    if (arguments.length >= 3) {
                        String newMode = arguments[2];
                        switch (newMode.toLowerCase()) {
                            case "pot":
                            case "potion":
                                mode.setValue(Mode.POTION);
                                ChatLogger.print(String.format("Regen Mode set to: %s", mode.getValue().getName()));
                                break;
                            case "bypass":
                            case "new":
                                mode.setValue(Mode.BYPASS);
                                ChatLogger.print(String.format("Regen Mode set to: %s", mode.getValue().getName()));
                                break;
                            case "packets":
                            case "old":
                                mode.setValue(Mode.OLD);
                                ChatLogger.print(String.format("Regen Mode set to: %s", mode.getValue().getName()));
                                break;
                            default:
                                ChatLogger.print("Invalid mode, valid: potion, bypass");
                                break;
                        }
                        setTag(mode.getValue().getName());
                    } else {
                        ChatLogger.print("Invalid arguments, valid: regen mode <mode>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: mode");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: regen <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, sendPacketListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, sendPacketListener);
    }

    public enum Mode {
        BYPASS, POTION, OLD;

        public String getName() {
            String prettyName = "";
            String[] actualNameSplit = name().split("_");
            if (actualNameSplit.length > 0) {
                for (String arg : actualNameSplit) {
                    arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length()).toLowerCase();
                    prettyName += arg + " ";
                }
            } else {
                prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length()).toLowerCase();
            }
            return prettyName.trim();
        }
    }
}
