package club.strifeclient.module.implementations.movement.speed;

import club.strifeclient.module.implementations.movement.Speed;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.implementations.DoubleSetting;

public final class CustomSpeed extends Mode<Speed.SpeedMode> {

    private final DoubleSetting moveSpeedSetting = new DoubleSetting("Speed", 0.28, 0.01, 5, 0.01);
    private final DoubleSetting frictionDividendSetting = new DoubleSetting("Friction Dividend", 159, 1, 300, 1);

    @Override
    public Speed.SpeedMode getRepresentation() {
        return Speed.SpeedMode.CUSTOM;
    }
}
