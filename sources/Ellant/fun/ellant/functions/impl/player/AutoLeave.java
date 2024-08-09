package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.PlayerUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "AutoLeave", type = Category.PLAYER, desc = "Автоматически ливает с сервера когда рядом игрок")
public class AutoLeave extends Function {

    final ModeSetting action = new ModeSetting("Действие", "Kick", "Kick", "/hub", "/spawn", "/home");
    final SliderSetting distance = new SliderSetting("Дистанция", 50.0f, 1.0f, 100.0f, 1.0f);

    public AutoLeave() {
        addSettings(action, distance);
    }
    @Subscribe
    private void onUpdate(EventUpdate event) {
        mc.world.getPlayers().stream()
                .filter(this::isValidPlayer)
                .findFirst()
                .ifPresent(this::performAction);
    }

    private boolean isValidPlayer(PlayerEntity player) {
        return player.isAlive()
                && player.getHealth() > 0.0f
                && player.getDistance(mc.player) <= distance.get()
                && player != mc.player
                && PlayerUtils.isNameValid(player.getName().getString());
    }

    private void performAction(PlayerEntity player) {
        if (!action.get().equalsIgnoreCase("Kick")) {
            mc.player.sendChatMessage(action.get());
            mc.ingameGUI.func_238452_a_(new StringTextComponent("[AutoLeave] " + player.getGameProfile().getName()),
                    new StringTextComponent("test"), -1,
                    -1, -1);
        } else {
            mc.player.connection.getNetworkManager().closeChannel(new StringTextComponent("Вы вышли с сервера! \n" + player.getGameProfile().getName()));
        }
        toggle();
    }
}
