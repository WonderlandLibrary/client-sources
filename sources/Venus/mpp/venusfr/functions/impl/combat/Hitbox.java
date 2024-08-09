/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

@FunctionRegister(name="HitBox", type=Category.Combat)
public class Hitbox
extends Function {
    public final SliderSetting size = new SliderSetting("\u0420\u0430\u0437\u043c\u0435\u0440", 0.2f, 0.0f, 3.0f, 0.05f);
    public final BooleanSetting visible = new BooleanSetting("\u0412\u0438\u0434\u0438\u043c\u044b\u0435", false);

    public Hitbox() {
        this.addSettings(this.size, this.visible);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (!((Boolean)this.visible.get()).booleanValue() || Hitbox.mc.player == null) {
            return;
        }
        float f = ((Float)this.size.get()).floatValue() * 2.5f;
        for (PlayerEntity playerEntity : Hitbox.mc.world.getPlayers()) {
            if (this.isNotValid(playerEntity)) continue;
            playerEntity.setBoundingBox(this.calculateBoundingBox(playerEntity, f));
        }
    }

    private boolean isNotValid(PlayerEntity playerEntity) {
        return playerEntity == Hitbox.mc.player || !playerEntity.isAlive();
    }

    private AxisAlignedBB calculateBoundingBox(Entity entity2, float f) {
        double d = entity2.getPosX() - (double)f;
        double d2 = entity2.getBoundingBox().minY;
        double d3 = entity2.getPosZ() - (double)f;
        double d4 = entity2.getPosX() + (double)f;
        double d5 = entity2.getBoundingBox().maxY;
        double d6 = entity2.getPosZ() + (double)f;
        return new AxisAlignedBB(d, d2, d3, d4, d5, d6);
    }
}

