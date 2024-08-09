package wtf.shiyeno.modules.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "BoatFly",
        type = Type.Movement
)
public class BoatFlyFunction extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    public ModeSetting mode = new ModeSetting("Мод", "Intave", new String[]{"Grim", "Intave"});
    private final SliderSetting height = (new SliderSetting("height", 0.3F, 0.01F, 0.99F, 0.01F)).setVisible(() -> {
        return this.mode.is("Grim");
    });
    boolean restart = false;

    public BoatFlyFunction() {
        this.addSettings(new Setting[]{this.height, this.mode});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            Minecraft var10000;
            if (this.mode.is("Grim")) {
                var10000 = mc;
                if (Minecraft.player.getRidingEntity() != null) {
                    label55: {
                        var10000 = mc;
                        if (Minecraft.player.getHeldItemMainhand().getItem() != Items.FISHING_ROD) {
                            var10000 = mc;
                            if (Minecraft.player.getHeldItemOffhand().getItem() != Items.FISHING_ROD) {
                                break label55;
                            }
                        }

                        var10000 = mc;
                        Minecraft.player.getRidingEntity().motion.y = 0.04;
                        if (mc.gameSettings.keyBindBack.isKeyDown()) {
                            var10000 = mc;
                            Minecraft.player.getRidingEntity().motion.y = -0.12;
                        }

                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            var10000 = mc;
                            Minecraft.player.getRidingEntity().motion.y = (double)this.height.getValue().floatValue();
                        }
                    }
                }
            }

            if (this.mode.is("Intave")) {
                var10000 = mc;
                if (Minecraft.player.getRidingEntity() != null) {
                    var10000 = mc;
                    Minecraft.player.getRidingEntity().motion.y = 0.04;
                    if (mc.gameSettings.keyBindBack.isKeyDown()) {
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            mc.gameSettings.keyBindJump.setPressed(false);
                        }

                        var10000 = mc;
                        Minecraft.player.getRidingEntity().motion.y = -0.12;
                    }

                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (!this.restart && this.timerUtil.hasTimeElapsed(800L)) {
                            this.restart = true;
                            this.timerUtil.reset();
                        }

                        if (this.restart) {
                            var10000 = mc;
                            Minecraft.player.getRidingEntity().motion.y = 0.1;
                            if (this.timerUtil.hasTimeElapsed(50L)) {
                                this.restart = false;
                                this.timerUtil.reset();
                            }
                        }
                    }
                }
            }
        }

    }
    public void onDisable() {
        super.onDisable();
    }
}