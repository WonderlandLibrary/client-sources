package koks.modules.impl.utilities;

import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 19:26
 */
public class Cosmetics extends Module {

    public BooleanValue<Boolean> particle = new BooleanValue<>("Ground Particle", true, this);
    public ModeValue<String> particleMode = new ModeValue<>("Particle", "Portal", new String[]{"Portal", "Critical", "Redstone"}, this);
    public TitleValue particles = new TitleValue("Particles", true, new Value[]{particle, particleMode}, this);

    public Cosmetics() {
        super("Cosmetics", "Your have decoration", Category.UTILITIES);

        addValue(particles);
        addValue(particle);
        addValue(particleMode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(particleMode.getSelectedMode());
            if (particle.isToggled()) {
                switch (particleMode.getSelectedMode()) {
                    case "Portal":
                        for (int k = 0; k < 10; ++k)
                            mc.theWorld.spawnParticle(EnumParticleTypes.PORTAL, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                        break;
                    case "Critical":
                        for (int k = 0; k < 10; ++k)
                            mc.theWorld.spawnParticle(EnumParticleTypes.CRIT, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                        break;
                    case "Redstone":
                        for (int k = 0; k < 10; ++k)
                            mc.theWorld.spawnParticle(EnumParticleTypes.REDSTONE, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                        break;
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}