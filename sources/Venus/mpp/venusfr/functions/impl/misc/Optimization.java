/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import net.minecraft.client.Minecraft;

@FunctionRegister(name="Optimization", type=Category.Misc)
public class Optimization
extends Function {
    public final BooleanSetting autoJump = new BooleanSetting("\u0410\u0432\u0442\u043e \u043f\u0440\u044b\u0436\u043e\u043a", true);
    public final BooleanSetting ofSky = new BooleanSetting("\u041e\u043f\u0442\u0438\u043c\u0438\u0437\u0430\u0446\u0438\u044f \u041e\u0431\u043b\u043e\u043a\u043e\u0432", true);
    public final BooleanSetting ofCustomSky = new BooleanSetting("\u041e\u043f\u0442\u0438\u043c\u0438\u0437\u0430\u0446\u0438\u044f \u043d\u0435\u0431\u0430", true);
    public final BooleanSetting entityShadows = new BooleanSetting("\u041e\u043f\u0442\u0438\u043c\u0438\u0437\u0430\u0446\u0438\u044f \u042d\u043d\u0442\u0438\u0442\u0438", true);
    public final BooleanSetting optimizeLighting = new BooleanSetting("\u041e\u0441\u0432\u0435\u0449\u0435\u043d\u0438\u0435", true);
    public final BooleanSetting optimizeParticles = new BooleanSetting("\u041f\u0430\u0440\u0442\u0438\u043a\u043b\u044b", true);
    public final BooleanSetting optimizeClientHighlight = new BooleanSetting("\u041f\u043e\u0434\u0441\u0432\u0435\u0442\u043a\u0430 \u043a\u043b\u0438\u0435\u043d\u0442\u0430", false);

    public Optimization() {
        this.addSettings(this.autoJump, this.ofSky, this.ofCustomSky, this.entityShadows, this.optimizeLighting, this.optimizeParticles, this.optimizeClientHighlight);
    }

    @Subscribe
    private void onEventUpdate(EventUpdate eventUpdate) {
        if (((Boolean)this.autoJump.get()).booleanValue()) {
            Minecraft.getInstance().gameSettings.autoJump = false;
        }
        if (((Boolean)this.ofSky.get()).booleanValue()) {
            Minecraft.getInstance().gameSettings.ofSky = false;
        }
        if (((Boolean)this.ofCustomSky.get()).booleanValue()) {
            Minecraft.getInstance().gameSettings.ofCustomSky = false;
        }
        if (((Boolean)this.entityShadows.get()).booleanValue()) {
            Minecraft.getInstance().gameSettings.entityShadows = false;
        }
        if (((Boolean)this.optimizeLighting.get()).booleanValue()) {
            // empty if block
        }
        if (((Boolean)this.optimizeParticles.get()).booleanValue()) {
            // empty if block
        }
        if (((Boolean)this.optimizeClientHighlight.get()).booleanValue()) {
            // empty if block
        }
        long l = Runtime.getRuntime().maxMemory();
        long l2 = Runtime.getRuntime().totalMemory();
        long l3 = Runtime.getRuntime().freeMemory();
        long l4 = l2 - l3 - l;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getInstance().gameSettings.autoJump = true;
        Minecraft.getInstance().gameSettings.ofSky = true;
        Minecraft.getInstance().gameSettings.ofCustomSky = true;
        Minecraft.getInstance().gameSettings.entityShadows = true;
    }
}

