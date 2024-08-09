/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@FunctionRegister(name="WaterSpeed", type=Category.Movement)
public class WaterSpeed
extends Function {
    ModeSetting type = new ModeSetting("\u0422\u0438\u043f", "\u041e\u0431\u044b\u0447\u043d\u044b\u0435", "Funtime", "Holyworld", "\u041e\u0431\u044b\u0447\u043d\u044b\u0435");

    public WaterSpeed() {
        this.addSettings(this.type);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        String string = (String)this.type.get();
        if (string.equals("\u041e\u0431\u044b\u0447\u043d\u044b\u0435")) {
            this.WATER_DEF();
        } else if (string.equals("Funtime")) {
            this.WATER_FT();
        } else if (string.equals("Holyworld")) {
            this.WATER_HOLY();
        }
    }

    private void WATER_DEF() {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null && clientPlayerEntity.isAlive() && clientPlayerEntity.isInWater()) {
            clientPlayerEntity.setMotion(clientPlayerEntity.getMotion().x * 1.1, clientPlayerEntity.getMotion().y, clientPlayerEntity.getMotion().z * 1.1);
        }
    }

    private void WATER_FT() {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null && clientPlayerEntity.isAlive() && clientPlayerEntity.isInWater()) {
            clientPlayerEntity.setMotion(clientPlayerEntity.getMotion().x * 1.0505, clientPlayerEntity.getMotion().y, clientPlayerEntity.getMotion().z * 1.0505);
        }
    }

    private void WATER_HOLY() {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null && clientPlayerEntity.isAlive() && clientPlayerEntity.isInWater()) {
            clientPlayerEntity.setMotion(clientPlayerEntity.getMotion().x * 1.03, clientPlayerEntity.getMotion().y, clientPlayerEntity.getMotion().z * 1.03);
        }
    }
}

