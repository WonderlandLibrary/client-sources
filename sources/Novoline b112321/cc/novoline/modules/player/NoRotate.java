package cc.novoline.modules.player;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import org.jetbrains.annotations.NotNull;

public class NoRotate extends AbstractModule {
   public NoRotate(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "NoRotate", "No Rotate");
   }
}
