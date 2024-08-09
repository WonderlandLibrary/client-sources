package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.TimerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name = "ScannerItems", type = Category.Player)
    public class SkanItems extends Function {
        private final TimerUtil timerUtil = new TimerUtil();

        private final SliderSetting delay = new SliderSetting("Задержка", 3000.0F, 0.0F, 20000.0F, 1.0F);

        private final ModeListSetting actions = new ModeListSetting("Способность сканера", new BooleanSetting("На криссталы", true), new BooleanSetting("На шары", true));

        public SkanItems() {
            addSettings(this.actions, this.delay);
        }

        @Subscribe
        public void onUpdate(EventUpdate e) {
            if (e instanceof EventUpdate &&
                    this.timerUtil.hasTimeElapsed(this.delay.get().intValue()))
                for (PlayerEntity playerEntity : mc.world.getPlayers()) {
                    if (playerEntity != mc.player) {
                        if (actions.getValueByName("На криссталы").get()) {
                            if (playerEntity.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                                int count = playerEntity.getHeldItemOffhand().getCount();
                                print(playerEntity.getDisplayName().getString() + " Имеет: " + playerEntity.getDisplayName().getString() + TextFormatting.RED + count + " криссталов");
                            }
                            if (playerEntity.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                                int count = playerEntity.getHeldItemMainhand().getCount();
                                print(playerEntity.getDisplayName().getString() + " Имеет: " + playerEntity.getDisplayName().getString() + TextFormatting.RED + count + " криссталов");
                            }
                        }
                        if (actions.getValueByName("На шары").get()) {
                            if (playerEntity.getHeldItemOffhand().getItem() instanceof net.minecraft.item.SkullItem)
                                print(playerEntity.getDisplayName().getString() + playerEntity.getDisplayName().getString() + "Имеет >> " + TextFormatting.RESET + TextFormatting.YELLOW);
                            if (playerEntity.getHeldItemMainhand().getItem() instanceof net.minecraft.item.SkullItem)
                                print(playerEntity.getDisplayName().getString() + playerEntity.getDisplayName().getString() + "Имеет >> " + TextFormatting.RESET + TextFormatting.YELLOW);
                        }
                        this.timerUtil.reset();
                    }
                }
        }
    }
