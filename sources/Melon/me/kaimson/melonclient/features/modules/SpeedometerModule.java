package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;

public class SpeedometerModule extends DefaultModuleRenderer
{
    private final Setting decimalPoints;
    private final Setting unit;
    
    public SpeedometerModule() {
        super("Speedometer", 18);
        new Setting(this, "Number Options");
        this.decimalPoints = new Setting(this, "Decimal Points").setDefault(2).setRange(0, 8, 1);
        this.unit = new Setting(this, "Unit").setDefault(0).setRange("blocks/sec", "m/s");
    }
    
    @Override
    public Object getValue() {
        final double distX = this.mc.h.s - this.mc.h.p;
        final double distZ = this.mc.h.u - this.mc.h.r;
        return String.format("%." + this.decimalPoints.getInt() + "f", ns.a(distX * distX + distZ * distZ) / 0.05f);
    }
    
    @Override
    public String getFormat() {
        return "[%value% " + this.unit.getValue().get(this.unit.getInt() + 1) + "]";
    }
}
