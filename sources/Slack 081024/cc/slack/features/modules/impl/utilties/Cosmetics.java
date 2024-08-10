package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.util.EnumParticleTypes;

@ModuleInfo(
        name = "Cosmetics",
        category = Category.UTILITIES
)
public class Cosmetics extends Module {

    private final ModeValue<String> cosmeticMode = new ModeValue<>("Cosmetic", new String[]{"Particles"});
    private final ModeValue<String> particlePosMode = new ModeValue<>("Particle Pos", new String[]{"Head", "Foot"});
    private final ModeValue<String> particlemode = new ModeValue<>("Particle", new String[]{"Portal", "Redstone", "Critical", "Heart"});
    private final NumberValue<Integer> particlesSpeed = new NumberValue<>("Particles Speed", 10, 1, 20, 1);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});
    public Cosmetics() {
        super();
        addSettings(cosmeticMode, particlePosMode, particlemode, displayMode);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        switch (cosmeticMode.getValue()) {
            case "Particles":
                switch (particlePosMode.getValue()) {
                    case "Head":
                        switch (particlemode.getValue()) {
                                case "Portal":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.PORTAL, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, 2.5 + mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Critical":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.CRIT, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, 2.5 + mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Redstone":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.REDSTONE, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, 2.5 + mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Heart":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.HEART, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, 2.5 + mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                            }
                        break;
                        case "Foot":
                            switch (particlemode.getValue()) {
                                case "Portal":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.PORTAL, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Critical":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.CRIT, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Redstone":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.REDSTONE, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D,mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                                case "Heart":
                                    for (int k = 0; k < particlesSpeed.getValue(); ++k)
                                        mc.theWorld.spawnParticle(EnumParticleTypes.HEART, mc.thePlayer.posX + mc.thePlayer.motionX * (double) k / 4.0D, mc.thePlayer.posY + mc.thePlayer.motionY * (double) k / 4.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * (double) k / 4.0D, -mc.thePlayer.motionX, -mc.thePlayer.motionY + 0.2D, -mc.thePlayer.motionZ);
                                    break;
                            }
                        break;
                        }
                    case "Palanca":
                    break;
        }
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return cosmeticMode.getValue().toString();
        }
        return null;
    }
}


