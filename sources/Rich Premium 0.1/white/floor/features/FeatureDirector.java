package white.floor.features;

import white.floor.features.impl.combat.*;
import white.floor.features.impl.display.*;
import white.floor.features.impl.misc.AutoLeave;
import white.floor.features.impl.misc.AutoRespawn;
import white.floor.features.impl.misc.MCF;
import white.floor.features.impl.movement.*;
import white.floor.features.impl.player.GuiMove;
import white.floor.features.impl.player.MiddleClickPearl;
import white.floor.features.impl.player.NoClip;
import white.floor.features.impl.player.NoPush;
import white.floor.features.impl.visuals.*;
import white.floor.font.Fonts;
import white.floor.helpers.render.FullBright;

import java.util.ArrayList;
import java.util.Comparator;

public class FeatureDirector {
    public static ArrayList<Feature> features = new ArrayList<Feature>();

    public FeatureDirector() {

        // Combat.
       features.add(new KillAura());
       features.add(new AntiBot());
       features.add(new Velocity());
       features.add(new NoFriendDamage());
       features.add(new TriggerBot());
       features.add(new KillauraTest());
       features.add(new TestAntiBot());

        // Movement.
        features.add(new Flight());
        features.add(new Speed());
        features.add(new AirJump());
        features.add(new TargetStrafe());
        features.add(new GuiMove());
        features.add(new Eagle());
        features.add(new AutoSprint());
        features.add(new NoSlowDown());
        features.add(new WaterSpeed());

        // Visuals.
        features.add(new OutOfFovArrows());
        features.add(new Breadcrumbs());
        features.add(new FullBright());
        features.add(new JumpCircle());
        features.add(new ScoreBoard());
        features.add(new NameTags());
        features.add(new Cosmetics());
        features.add(new TargetESP());
        features.add(new ViewModel());
        features.add(new EntityESP());
        features.add(new ChinaHat());
        features.add(new NoRender());
        features.add(new Chams());
        features.add(new Tracers());

        // Player.
        features.add(new MiddleClickPearl());
        features.add(new NoPush());
        features.add(new NoClip());

        // Misc.
        features.add(new AutoLeave());
        features.add(new AutoRespawn());
        features.add(new MCF());

        // Display.
        features.add(new Watermark());
        features.add(new CustomFont());
        features.add(new ClickGUI());
        features.add(new Indicators());
        features.add(new Notifications());
        features.add(new Hotbar());
        features.add(new SessionInfo());
        features.add(new TestArray());

        features.sort(Comparator.comparingInt(m -> Fonts.neverlose500_15.getStringWidth(((Feature) m).getName())).reversed());
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public Feature getModule(Class moduleClass) {
        for (Feature module : features) {
            if (module.getClass() != moduleClass) {
                continue;
            }
            return module;
        }
        return null;
    }

    public Feature[] getModulesInCategory(Category category) {
        return this.features.stream().filter(module -> module.getCategory() == category).toArray(Feature[]::new);
    }
}
