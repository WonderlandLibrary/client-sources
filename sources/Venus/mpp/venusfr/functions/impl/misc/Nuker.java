/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Nuker", type=Category.Misc)
public class Nuker
extends Function {
    private final SliderSetting rangenuking = new SliderSetting("\u0414\u0438\u0430\u043f\u043e\u0437\u043e\u043d", 2.0f, 1.0f, 5.0f, 0.1f);
    private final SliderSetting intervalnuking = new SliderSetting("\u0418\u043d\u0442\u0435\u0440\u0432\u0430\u043b", 700.0f, 50.0f, 1400.0f, 50.0f);
    private final TimerUtil timerUtil = new TimerUtil();
    private long lastBlockBreakTime = 0L;

    public Nuker() {
        this.addSettings(this.rangenuking, this.intervalnuking);
    }

    @Subscribe
    protected float[] rotations(PlayerEntity playerEntity) {
        return new float[0];
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        int n = Math.round(((Float)this.rangenuking.get()).floatValue());
        long l = Math.round(((Float)this.intervalnuking.get()).floatValue());
        Minecraft minecraft = Minecraft.getInstance();
        Vector3d vector3d = minecraft.player.getPositionVec();
        Vector3d vector3d2 = minecraft.player.getLookVec();
        if (System.currentTimeMillis() - this.lastBlockBreakTime >= l) {
            minecraft.player.swing(Hand.MAIN_HAND, true);
            for (int i = -n; i <= n; ++i) {
                for (int j = 0; j <= n; ++j) {
                    for (int k = -n; k <= n; ++k) {
                        BlockPos blockPos = new BlockPos(vector3d.x + (double)i, vector3d.y + (double)j, vector3d.z + (double)k);
                        minecraft.playerController.onPlayerDamageBlock(blockPos, Direction.UP);
                    }
                }
            }
            this.lastBlockBreakTime = System.currentTimeMillis();
        }
    }
}

