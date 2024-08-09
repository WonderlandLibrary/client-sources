package mathax.client.systems.modules.movement.speed;

public enum SpeedModes {
    Hypixel("Hypixel"),
    Inn3rstellarSpeed("StellarSpeed"),
    Vanilla("Vanilla"),
    Strafe("BunnyHop"),
    MineBerry("Mineberry || Old"),
    VelocityHop("Yport"),
    TimerHop("TimerHop"),
    Weird("Please Don't Crash me :("),
    LBL_SlowHop("SlowHop"),
    Vulcan("Vulcan"),
    Negativity("Negativity"),
    Viper("Viper"),
    ViperHigh("ViperHigh"),
    _5b5t("_5b5t"),
    OnGround("RubberBand"),
    LegitHop("LegitHop"),
    EnvyAnarchy("EnvyAnarchy || Legacy"),
    Test3("FastHop"),
    dumbspeed("dumbspeed"),
    NONONOFUCK("GlideHop"),
    Chinese("Chinese"),
    EnvyHop("EnvyHop"),
    EnvyHop2("EnvyHop2"),
    ChonkyChineseSped("Chinese2"),

    Custom("Custom");



    private final String title;

    SpeedModes(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
