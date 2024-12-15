package com.alan.clients.module.impl.combat;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.combat.velocity.*;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.combat.velocity.name"}, description = "module.combat.velocity.description" /* Sorry, Tecnio. */ /* Sorry Hazsi. */, category = Category.COMBAT)
public final class Velocity extends Module {



    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardVelocity("Standard", this))
            .add(new BufferAbuseVelocity("Buffer Abuse", this))
            .add(new WatchdogVelocity("Delay", this))
            .add(new LegitVelocity("Legit/Jump Reset", this))
            .add(new LegitVelocity("Polar", this))
            .add(new GroundVelocity("Ground", this))
            .add(new IntaveVelocity("Intave", this))
            .add(new MatrixVelocity("Matrix", this))
            .add(new AACVelocity("AAC", this))
            .add(new VulcanVelocity("Vulcan", this))
            .add(new RedeskyVelocity("Redesky", this))
            .add(new TickVelocity("Tick", this))
            .add(new BounceVelocity("Bounce", this))
            .add(new KarhuVelocity("Karhu", this))
            .add(new MMCVelocity("MMC", this))
            .add(new UniversoCraftVelocity("Universocraft", this))
            .add(new GrimReduceVelocity("Grim Reduce", this))
            .add(new GrimVelocity("Grim", this))
            .add(new WatchdogVelocity("Watchdog", this))
            .add(new WatchdogPredictionVelocity("Watchdog Prediction", this))
            .setDefault("Standard");


    public final BooleanValue onSwing = new BooleanValue("On Swing", this, false);
}
