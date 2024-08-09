package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Nuker", type=Category.Player)
public class Nuker extends Function {
    private final SliderSetting rangenuking = new SliderSetting("Радиус", 2.0f, 1.0f, 5.0f, 0.1f);
    private final SliderSetting intervalnuking = new SliderSetting("Задержка", 700.0f, 50.0f, 1400.0f, 50.0f);
    private final TimerUtil timerUtil = new TimerUtil();
    private long lastBlockBreakTime = 0L;

    public Nuker() {
        this.addSettings(this.rangenuking, this.intervalnuking);
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

