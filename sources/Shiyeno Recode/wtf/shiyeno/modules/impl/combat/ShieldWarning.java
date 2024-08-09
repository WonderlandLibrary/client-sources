package wtf.shiyeno.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "ShieldWarning",
        type = Type.Combat
)
public class ShieldWarning extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private final SliderSetting distance = new SliderSetting("Дистанция проверки", 3.0F, 0.01F, 6.0F, 0.01F);
    private final SliderSetting delay = new SliderSetting("Задержка отжатия", 0.0F, 0.0F, 3000.0F, 0.01F);
    public BooleanOption actions = new BooleanOption("Писать сообщение о топоре", true);
    public BooleanOption nofriend = new BooleanOption("Не проверять друзей", true);

    public ShieldWarning() {
        this.addSettings(new Setting[]{this.distance, this.delay, this.actions, this.nofriend});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.player == null || mc.world == null) {
                return;
            }

            Iterator var2 = mc.world.getPlayers().iterator();

            while(var2.hasNext()) {
                Entity entity = (Entity)var2.next();
                if (entity instanceof PlayerEntity && mc.player.getDistance(entity) < this.distance.getValue().floatValue()) {
                    boolean check = ((PlayerEntity)entity).getHeldItemMainhand().getItem() instanceof AxeItem;
                    if (mc.player.getHeldItemOffhand().getItem() == Items.SHIELD && mc.player.isHandActive() && ((PlayerEntity)entity).getHeldItemMainhand().getItem() instanceof AxeItem) {
                        if (this.nofriend.get() && Managment.FRIEND_MANAGER.isFriend(entity.getScoreboardName())) {
                            return;
                        }

                        if (mc.gameSettings.keyBindUseItem.isKeyDown() && this.timerUtil.hasTimeElapsed((long)this.delay.getValue().intValue())) {
                            mc.gameSettings.keyBindUseItem.setPressed(false);
                        }

                        if (this.actions.get() && this.timerUtil.hasTimeElapsed((long)this.delay.getValue().intValue())) {
                            ClientUtil.sendMesage(TextFormatting.AQUA + "Противник взял " + TextFormatting.RED + "топор! " + TextFormatting.AQUA + "Отжимаю немедленно щит");
                            this.timerUtil.reset();
                        }
                    }
                }
            }
        }
    }

    public void onDisable() {
        if (mc.player.getHeldItemOffhand().getItem() == Items.SHIELD) {
            mc.gameSettings.keyBindUseItem.setPressed(false);
        }
    }
}