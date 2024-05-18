package pw.latematt.xiv.mod.mods.render;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.FovModifierEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

/**
 * @author Matthew
 */
public class FovFixer extends Mod implements Listener<FovModifierEvent>, CommandHandler {
    private final Value<Boolean> sprinting = new Value<>("fov_fixer_sprinting", true);

    public FovFixer() {
        super("FovFixer", ModType.RENDER);
        Command.newCommand().cmd("fovfixer").description("Base command for the FovFixer mod.").arguments("<action>").aliases("ffix", "fov").handler(this).build();
    }

    @Override
    public void onEventCalled(FovModifierEvent event) {
        float var1 = 1.0F;

        if (sprinting.getValue()) {
            if (mc.thePlayer.capabilities.isFlying) {
                var1 *= 1.1F;
            }

            IAttributeInstance var2 = mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            float walkSpeed = (float) var2.getAttributeValue();
            if (walkSpeed > 0.12999481F) {
                walkSpeed = 0.12999481F;
            }
            var1 = (var1 * ((walkSpeed / mc.thePlayer.capabilities.getWalkSpeed() + 1.0F) / 2.0F));

            if (mc.thePlayer.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(var1) || Float.isInfinite(var1)) {
                var1 = 1.0F;
            }
        }

        if (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUse().getItem() == Items.bow) {
            int var3 = mc.thePlayer.getItemInUseDuration();
            float var4 = (float) var3 / 20.0F;

            if (var4 > 1.0F) {
                var4 = 1.0F;
            } else {
                var4 *= var4;
            }

            var1 *= 1.0F - var4 * 0.15F;
        }

        event.setFov(var1);
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "sprinting":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            sprinting.setValue(sprinting.getDefault());
                        } else {
                            sprinting.setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        sprinting.setValue(!sprinting.getValue());
                    }
                    ChatLogger.print(String.format("FovFiver will %s show %s sprinting FOV.", sprinting.getValue() ? "now" : "no longer", !sprinting.getValue() ? "all" : "any"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: sprinting");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: fovfixer <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
