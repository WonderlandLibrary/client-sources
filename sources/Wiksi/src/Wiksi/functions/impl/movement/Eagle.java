package src.Wiksi.functions.impl.movement;



import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(
        name = "Eagle",
        type = Category.Movement
)
public class Eagle extends Function {
    public BooleanSetting advancedCheck = new BooleanSetting("OnGround", true);

    public Eagle() {
        this.addSettings(new Setting[]{this.advancedCheck});
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if ((Boolean)this.advancedCheck.get()) {
            if (mc.player.isOnGround()) {
                this.onTick();
            }
        } else {
            this.onTick();
        }

    }

    public void onTick() {
        BlockPos playerPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
        if (mc.world.isAirBlock(playerPos.down())) {
            mc.gameSettings.keyBindSneak.setPressed(true);
        } else {
            mc.gameSettings.keyBindSneak.setPressed(false);
        }

    }

    public void onDisable() {
        super.onDisable();
        mc.gameSettings.keyBindSneak.setPressed(false);
    }
}
