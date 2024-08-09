package wtf.shiyeno.modules.impl.util;

import java.util.Iterator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "ScannerItems",
        type = Type.Util
)
public class ScannerItems extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private final SliderSetting delay = new SliderSetting("Скорость сообщений", 3000.0F, 0.0F, 20000.0F, 1.0F);
    private final MultiBoxSetting actions = new MultiBoxSetting("Способности сканера", new BooleanOption[]{new BooleanOption("На кристалы", true), new BooleanOption("На шары", true)});

    public ScannerItems() {
        this.addSettings(new Setting[]{this.actions, this.delay});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate && this.timerUtil.hasTimeElapsed((long)this.delay.getValue().intValue())) {
            Iterator var2 = mc.world.getPlayers().iterator();

            while(var2.hasNext()) {
                PlayerEntity playerEntity = (PlayerEntity)var2.next();
                if (playerEntity != mc.player) {
                    String var10000;
                    if (this.actions.get("На кристалы")) {
                        int count;
                        if (playerEntity.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                            count = playerEntity.getHeldItemOffhand().getCount();
                            var10000 = playerEntity.getDisplayName().getString();
                            ClientUtil.sendMesage(var10000 + " Имеет: " + TextFormatting.RED + count + TextFormatting.RESET + " кристалов");
                        }

                        if (playerEntity.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                            count = playerEntity.getHeldItemMainhand().getCount();
                            var10000 = playerEntity.getDisplayName().getString();
                            ClientUtil.sendMesage(var10000 + " Имеет: " + TextFormatting.RED + count + TextFormatting.RESET + " кристалов");
                        }
                    }

                    if (this.actions.get("На шары")) {
                        if (playerEntity.getHeldItemOffhand().getItem() instanceof SkullItem) {
                            var10000 = playerEntity.getDisplayName().getString();
                            ClientUtil.sendMesage(var10000 + TextFormatting.RESET + "Имеет: " + TextFormatting.YELLOW + playerEntity.getHeldItemOffhand().getDisplayName().getString());
                        }

                        if (playerEntity.getHeldItemMainhand().getItem() instanceof SkullItem) {
                            var10000 = playerEntity.getDisplayName().getString();
                            ClientUtil.sendMesage(var10000 + TextFormatting.RESET + "Имеет: " + TextFormatting.YELLOW + playerEntity.getHeldItemMainhand().getDisplayName().getString());
                        }
                    }

                    this.timerUtil.reset();
                }
            }
        }
    }
}