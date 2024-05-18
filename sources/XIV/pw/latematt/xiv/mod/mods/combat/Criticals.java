package pw.latematt.xiv.mod.mods.combat;

import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.AttackEntityEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.movement.Speed;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 * @author Jack
 */
public class Criticals extends Mod implements Listener<SendPacketEvent>, CommandHandler {
    private final Value<Mode> currentMode = new Value<>("criticals_mode", Mode.MINIJUMPS);
    private final Listener attackEntityListener;
    private boolean nextTick;
    private float fallDist;

    public Criticals() {
        super("Criticals", ModType.COMBAT, Keyboard.KEY_NONE, 0xFFA38EC7);
        Command.newCommand().cmd("criticals").description("Base command for Criticals mod.").arguments("<action>").aliases("crits").handler(this).build();

        attackEntityListener = new Listener<AttackEntityEvent>() {
            @Override
            public void onEventCalled(AttackEntityEvent event) {
                if (currentMode.getValue() == Mode.MINIJUMPS) {
                    if (!BlockUtils.isOnLiquid(mc.thePlayer) && mc.thePlayer.isCollidedVertically && !BlockUtils.isInLiquid(mc.thePlayer)) {
                        if (nextTick = !nextTick) {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.05, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.012511, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        }
                    }
                }else if (currentMode.getValue() == Mode.PACKETS) {
                    if (!BlockUtils.isOnLiquid(mc.thePlayer) && mc.thePlayer.isCollidedVertically && !BlockUtils.isInLiquid(mc.thePlayer)) {
                        Speed speed = (Speed) XIV.getInstance().getModManager().find("speed");

                        if(speed.shouldOffset()) {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0795, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.017, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.017011D, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.017, mc.thePlayer.posZ, false));
                        }else {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.05, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.012511, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onEventCalled(SendPacketEvent event) {
        if (this.currentMode.getValue() != Mode.OFFGROUND)
            return;
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer player = (C03PacketPlayer) event.getPacket();

            if (isSafe())
                fallDist += mc.thePlayer.fallDistance;

            if (!isSafe() || fallDist >= 3.0F) {
                player.setOnGround(true);
                fallDist = 0.0F;
                mc.thePlayer.fallDistance = 0.0F;

                if (mc.thePlayer.onGround && !BlockUtils.isOnLiquid(mc.thePlayer) && !BlockUtils.isInLiquid(mc.thePlayer)) {
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.01, mc.thePlayer.posZ, false));
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    fallDist += 1.01F;
                }
            } else if (fallDist > 0.0F) {
                player.setOnGround(false);
            }
        }
    }

    public float getFallDistance() {
        return fallDist;
    }

    public void setFallDistance(float fallDist) {
        this.fallDist = fallDist;
    }

    private boolean isSafe() {
        return !mc.thePlayer.isInWater() &&
                !mc.thePlayer.isInsideOfMaterial(Material.lava) &&
                !mc.thePlayer.isOnLadder() &&
                !mc.thePlayer.isPotionActive(Potion.BLINDNESS) &&
                mc.thePlayer.ridingEntity == null;
    }

    public boolean isGay() {
        return currentMode.getValue() == Mode.PACKETS;
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, attackEntityListener);
        if (mc.thePlayer != null && currentMode.getValue() == Mode.OFFGROUND) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.01, mc.thePlayer.posZ, false));
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            fallDist += 1.01F;
        }
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, attackEntityListener);
        fallDist = 0.0F;
        nextTick = false;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "mode":
                    if (arguments.length >= 3) {
                        String mode = arguments[2];
                        switch (mode.toLowerCase()) {
                            case "minijumps":
                                currentMode.setValue(Mode.MINIJUMPS);
                                ChatLogger.print(String.format("Criticals Mode set to: %s", currentMode.getValue().getName()));
                                break;
                            case "packets":
                            case "gay":
                                currentMode.setValue(Mode.PACKETS);
                                ChatLogger.print(String.format("Criticals Mode set to: %s", currentMode.getValue().getName()));
                                break;
                            case "old":
                            case "offground":
                                currentMode.setValue(Mode.OFFGROUND);
                                ChatLogger.print(String.format("Criticals Mode set to: %s", currentMode.getValue().getName()));
                                break;
                            case "-d":
                                currentMode.setValue(currentMode.getDefault());
                                ChatLogger.print(String.format("Criticals Mode set to: %s", currentMode.getValue().getName()));
                                break;
                            default:
                                ChatLogger.print("Invalid mode, valid: minijumps, packets, offground");
                                break;
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: criticals mode <mode>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: mode");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: criticals <action>");
        }
    }

    public enum Mode {
        OFFGROUND, MINIJUMPS, PACKETS;

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