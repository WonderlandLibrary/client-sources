package club.mega.module.impl.movement;

import club.mega.Mega;
import club.mega.module.Category;
import club.mega.module.Module;

@Module.ModuleInfo(name = "NoSlow", description = "Removes block and eat slowdown", category = Category.MOVEMENT)
public class NoSlow extends Module {

    public static NoSlow getInstance() {
        return Mega.INSTANCE.getModuleManager().getModule(NoSlow.class);
    }
}
