/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import net.minecraft.potion.Effects;

@FunctionRegister(name="NoRender", type=Category.Visual)
public class NoRender
extends Function {
    public ModeListSetting element = new ModeListSetting("\u0423\u0434\u0430\u043b\u044f\u0442\u044c", new BooleanSetting("\u041e\u0433\u043e\u043d\u044c \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435", true), new BooleanSetting("\u041b\u0438\u043d\u0438\u044f \u0431\u043e\u0441\u0441\u0430", true), new BooleanSetting("\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f \u0442\u043e\u0442\u0435\u043c\u0430", true), new BooleanSetting("\u0422\u0430\u0439\u0442\u043b\u044b", true), new BooleanSetting("\u0422\u0430\u0431\u043b\u0438\u0446\u0430", true), new BooleanSetting("\u0422\u0443\u043c\u0430\u043d", true), new BooleanSetting("\u0422\u0440\u044f\u0441\u043a\u0443 \u043a\u0430\u043c\u0435\u0440\u044b", true), new BooleanSetting("\u041f\u043b\u043e\u0445\u0438\u0435 \u044d\u0444\u0444\u0435\u043a\u0442\u044b", true), new BooleanSetting("\u0414\u043e\u0436\u0434\u044c", true));

    public NoRender() {
        this.addSettings(this.element);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        this.handleEventUpdate(eventUpdate);
    }

    @Subscribe
    private void onEventCancelOverlay(EventCancelOverlay eventCancelOverlay) {
        this.handleEventOverlaysRender(eventCancelOverlay);
    }

    private void handleEventOverlaysRender(EventCancelOverlay eventCancelOverlay) {
        boolean bl;
        switch (1.$SwitchMap$mpp$venusfr$events$EventCancelOverlay$Overlays[eventCancelOverlay.overlayType.ordinal()]) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case 1: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u041e\u0433\u043e\u043d\u044c \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435").get();
                break;
            }
            case 2: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u041b\u0438\u043d\u0438\u044f \u0431\u043e\u0441\u0441\u0430").get();
                break;
            }
            case 3: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u0422\u0430\u0431\u043b\u0438\u0446\u0430").get();
                break;
            }
            case 4: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u0422\u0430\u0439\u0442\u043b\u044b").get();
                break;
            }
            case 5: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f \u0442\u043e\u0442\u0435\u043c\u0430").get();
                break;
            }
            case 6: {
                boolean bl2 = (Boolean)this.element.getValueByName("\u0422\u0443\u043c\u0430\u043d").get();
                break;
            }
            case 7: {
                boolean bl2 = bl = ((Boolean)this.element.getValueByName("\u0422\u0440\u044f\u0441\u043a\u0443 \u043a\u0430\u043c\u0435\u0440\u044b").get()).booleanValue();
            }
        }
        if (bl) {
            eventCancelOverlay.cancel();
        }
    }

    private void handleEventUpdate(EventUpdate eventUpdate) {
        boolean bl;
        boolean bl2 = NoRender.mc.world.isRaining() && (Boolean)this.element.getValueByName("\u0414\u043e\u0436\u0434\u044c").get() != false;
        boolean bl3 = bl = (NoRender.mc.player.isPotionActive(Effects.BLINDNESS) || NoRender.mc.player.isPotionActive(Effects.NAUSEA)) && (Boolean)this.element.getValueByName("\u041f\u043b\u043e\u0445\u0438\u0435 \u044d\u0444\u0444\u0435\u043a\u0442\u044b").get() != false;
        if (bl2) {
            NoRender.mc.world.setRainStrength(0.0f);
            NoRender.mc.world.setThunderStrength(0.0f);
        }
        if (bl) {
            NoRender.mc.player.removePotionEffect(Effects.NAUSEA);
            NoRender.mc.player.removePotionEffect(Effects.BLINDNESS);
        }
    }
}

