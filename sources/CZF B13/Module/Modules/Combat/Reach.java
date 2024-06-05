package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventAttack;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.EventUpdate;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.API.eventapi.EventMouse;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.Random;

public class Reach
        extends Module {
    public double currentRange = 3.0;
    public long lastAttack = 0L;
    protected Random rand = new Random();
    protected long lastMS = -1L;
    private final Option<Boolean> combo = new Option<Boolean>("Combo", "combo", false);
    private final Numbers<Double> min = new Numbers<Double>("Min Range", "min", 3.0, 3.0, 8.0, 0.1);
    private final Numbers<Double> max = new Numbers<Double>("Max Range", "max", 4.0, 3.0, 8.0, 0.1);

    public Reach() {
        super("Reach", new String[]{"reachhack", "longarms"}, ModuleType.Combat);
        this.addValues(this.min, this.max, this.combo);
    }

    public boolean hasTimePassedMS(long MS) {
        return this.getCurrentMS() >= this.lastMS + MS;
    }

    public void updatebefore() {
        this.lastMS = this.getCurrentMS();
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    @EventHandler
    public void Attack(EventAttack event) {
        this.lastAttack = System.currentTimeMillis();
    }

    @EventHandler
    public void Update(EventUpdate event) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.hasTimePassedMS(2000L)) {
            double rangeMin = this.min.getValue();
            double rangeMax = this.max.getValue();
            double rangeDiff = rangeMax - rangeMin;
            if (rangeDiff < 0.0) {
                return;
            }
            this.currentRange = rangeMin + this.rand.nextDouble() * rangeDiff;
            this.updatebefore();
        }
    }

    @EventHandler
    public void Click(EventMouse event) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.combo.getValue().booleanValue() && System.currentTimeMillis() - this.lastAttack > 300L) {
            return;
        }
        Object[] objects = Wrapper.getEntity(this.currentRange, 0.0, 0.0f);
        if (objects == null) {
            return;
        }
        Wrapper.mc.objectMouseOver = new MovingObjectPosition((Entity) objects[0], (Vec3) objects[1]);
        Wrapper.mc.pointedEntity = (Entity) objects[0];
    }
}

