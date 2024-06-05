package club.mega.module;

import club.mega.Mega;
import club.mega.module.impl.combat.*;
import club.mega.module.impl.hud.*;
import club.mega.module.impl.movement.*;
import club.mega.module.impl.player.*;
import club.mega.module.impl.visual.*;

import java.util.*;

public class ModuleManager {

    private final ArrayList<Module> modules = new ArrayList<>();
    private final ArrayList<Module> mlModules = new ArrayList<>();

    public ModuleManager() {
        add(
                // Combat
                new KillAura(),
                new Criticals(),
                new Velocity(),
                new STap(),
                // Player
                new NoFall(),
                new NoRotate(),
                new AutoRespawn(),
                new Scaffold(),
                new Test(),
                new Disabler(),
                new Range(),
                new BetterHits(),
                // Movement
                new Sprint(),
                new Speed(),
                new Flight(),
                new Step(),
                new InvMove(),
                new NoSlow(),
                // Visual
                new ESP(),
                new NoFov(),
                new NoHurtCam(),
                new Ambiance(),
                new Animations(),
                new NoBob(),
                // Hud
                new Color(),
                new WaterMark(),
                new ModuleList(),
                new ClickGui(),
                new TargetHud(),
                new TabGui(),
                new Sounds()
        );
        mlModules.addAll(modules);
        mlModules.sort(new ModuleComparator());
    }

    private void add(final Module... modules) {
        this.modules.addAll(Arrays.asList(modules));
    }

    public final ArrayList<Module> getModules() {
        return modules;
    }

    public final ArrayList<Module> getMLModules(final boolean sorted) {
        return sorted ? mlModules : modules;
    }

    public final ArrayList<Module> getModules(final Category category) {
        ArrayList<Module> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if (module.getCategory() == category)
                modules.add(module);
        }
        return modules;
    }

    public final Module getModule(final String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public final <T extends Module> T getModule(final Class<T> tClass) {
        return (T) modules.stream().filter(module -> module.getClass().equals(tClass)).findFirst().orElse(null);
    }

    public final boolean isToggled(final Class tClass) {
        return getModule(tClass).isToggled();
    }

    private class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module m1, Module m2) {
            return Float.compare(Mega.INSTANCE.getFontManager().getFont("Arial 22").getWidth(m2.getName()), Mega.INSTANCE.getFontManager().getFont("Arial 22").getWidth(m1.getName()));
        }
    }

}
