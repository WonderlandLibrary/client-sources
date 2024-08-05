package fr.dog.module.impl.movement;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module {
    private final ModeProperty sprintMode = ModeProperty.newInstance("Mode", new String[]{"Vanilla", "Legit", "Omni"}, "Legit");
    private final BooleanProperty omni = BooleanProperty.newInstance("Omni", false);

    public Sprint() {
        super("Sprint", ModuleCategory.MOVEMENT);

        this.registerProperties(sprintMode);
    }

    @SubscribeEvent
    private void onPlayerTick(PlayerTickEvent event) {
        this.setSuffix(sprintMode.getValue());
        switch (sprintMode.getValue().toLowerCase()) {
            case "legit" -> KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
            case "vanilla" -> mc.thePlayer.setSprinting(true);
            case "omni" -> {
                if (sprintMode.is("Omni") && omni.getValue()) {
                    mc.thePlayer.setSprinting(true);
                } else {
                    mc.gameSettings.keyBindSprint.pressed = true;
                }

            }
        }
    }
}