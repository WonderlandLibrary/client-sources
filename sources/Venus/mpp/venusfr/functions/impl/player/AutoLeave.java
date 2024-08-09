/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.player.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

@FunctionRegister(name="AutoLeave", type=Category.Player)
public class AutoLeave
extends Function {
    private final ModeSetting action = new ModeSetting("\u0414\u0435\u0439\u0441\u0442\u0432\u0438\u0435", "Kick", "Kick", "/hub", "/spawn", "/home");
    private final SliderSetting distance = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 50.0f, 1.0f, 100.0f, 1.0f);

    public AutoLeave() {
        this.addSettings(this.action, this.distance);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        AutoLeave.mc.world.getPlayers().stream().filter(this::isValidPlayer).findFirst().ifPresent(this::performAction);
    }

    private boolean isValidPlayer(PlayerEntity playerEntity) {
        return playerEntity.isAlive() && playerEntity.getHealth() > 0.0f && playerEntity.getDistance(AutoLeave.mc.player) <= ((Float)this.distance.get()).floatValue() && playerEntity != AutoLeave.mc.player && PlayerUtils.isNameValid(playerEntity.getName().getString());
    }

    private void performAction(PlayerEntity playerEntity) {
        if (!((String)this.action.get()).equalsIgnoreCase("Kick")) {
            AutoLeave.mc.player.sendChatMessage((String)this.action.get());
            AutoLeave.mc.ingameGUI.func_238452_a_(new StringTextComponent("[AutoLeave] " + playerEntity.getGameProfile().getName()), new StringTextComponent("test"), -1, -1, -1);
        } else {
            AutoLeave.mc.player.connection.getNetworkManager().closeChannel(new StringTextComponent("\u0412\u044b \u0432\u044b\u0448\u043b\u0438 \u0441 \u0441\u0435\u0440\u0432\u0435\u0440\u0430! \n" + playerEntity.getGameProfile().getName()));
        }
        this.toggle();
    }
}

