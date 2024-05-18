package pw.latematt.xiv.mod.mods.movement;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.LiquidVelocityEvent;
import pw.latematt.xiv.event.events.ReadPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

/**
 * @author TehNeon
 */
public class Velocity extends Mod implements CommandHandler {
    private final ClampedValue<Float> reduction = new ClampedValue<>("velocity_reduction", 0.0F, -150.0F, 150.0F);
    private final Value<Boolean> liquid = new Value<>("velocity_water", true);
    private final Listener readPacketListener, liquidVelocityListener;

    public Velocity() {
        super("Velocity", ModType.MOVEMENT, Keyboard.KEY_BACKSLASH, 0xFF36454F, true);
        updateTag();
        Command.newCommand().cmd("velocity").aliases("vel").description("Base command for Velocity mod.").arguments("<action>").handler(this).build();

        readPacketListener = new Listener<ReadPacketEvent>() {
            @Override
            public void onEventCalled(ReadPacketEvent event) {
                updateTag();

                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

                    if (mc.thePlayer.getEntityId() == packet.getEntityID()) {
                        event.setCancelled(true);
                        double velX = packet.getVelocityX() * reduction.getValue() / 8000;
                        double velY = packet.getVelocityY() * reduction.getValue() / 8000;
                        double velZ = packet.getVelocityZ() * reduction.getValue() / 8000;

                        mc.thePlayer.motionX += velX;
                        mc.thePlayer.motionY += velY;
                        mc.thePlayer.motionZ += velZ;
                    }
                }
            }
        };

        liquidVelocityListener = new Listener<LiquidVelocityEvent>() {
            @Override
            public void onEventCalled(LiquidVelocityEvent event) {
                if (liquid.getValue()) {
                    Vec3 velocity = event.getVelocity();

                    double velX = velocity.xCoord * reduction.getValue() / 8000;
                    double velY = velocity.yCoord * reduction.getValue() / 8000;
                    double velZ = velocity.zCoord * reduction.getValue() / 8000;

                    event.setVelocity(new Vec3(velX, velY, velZ));
                }
            }
        };
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "liquid":
                case "water":
                case "lava":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            liquid.setValue(liquid.getDefault());
                        } else {
                            liquid.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        liquid.setValue(!liquid.getValue());
                    }
                    ChatLogger.print(String.format("Velocity will %s work in velocity.", liquid.getValue() ? "now" : "no longer"));
                    break;
                case "percent":
                case "perc":
                    if (arguments.length >= 3) {
                        String newVelocityString = arguments[2];
                        try {
                            float newPercent = arguments[2].equalsIgnoreCase("-d") ? reduction.getDefault() : Float.parseFloat(newVelocityString);
                            reduction.setValue((newPercent));
                            if (reduction.getValue() > reduction.getMax())
                                reduction.setValue(reduction.getMax());
                            else if (reduction.getValue() < reduction.getMin())
                                reduction.setValue(reduction.getMin());

                            updateTag();
                            ChatLogger.print(String.format("Velocity Percent set to %s", reduction.getValue() + "%"));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newVelocityString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: velocity percent <number>");
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: percent, liquid");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: velocity <action>");
        }
    }

    public void updateTag() {
        if (reduction.getValue() == 0) {
            setDisplayName("NoVelocity");
            setTag("");
        } else {
            setDisplayName(getName());
            setTag((reduction.getValue()) + "%");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(readPacketListener, liquidVelocityListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(readPacketListener, liquidVelocityListener);
    }
}
