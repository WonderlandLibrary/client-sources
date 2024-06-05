/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.EntityLivingBase;

public class BowAimbot
extends Module {
    public NumberSetting range = new NumberSetting("Range", 40.0, 10.0, 100.0, 5.0);

    public BowAimbot() {
        super("BowAimbot", "Aims at jews for u", 0, Category.Combat);
        this.addSettings(this.range);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        EntityLivingBase target = null;
        target = KillAuraHelper.getClosestEntity(this.range.getVal());
        float[] rotations = EntityHelper.getAngles(target);
        float rot0 = (float)((double)rotations[0] + MathHelper.randomNumber(1.0, 2.0));
        float rot1 = rotations[1] + MathHelper.getRandom();
        if (target != null) {
            KillAuraHelper.setRotations(e, rot0, rot1);
        }
    }
}

