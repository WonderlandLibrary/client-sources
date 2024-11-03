package net.augustus.utils.interfaces;

import net.augustus.Augustus;
import net.augustus.modules.ModuleManager;

public interface MM {
   ModuleManager mm = Augustus.getInstance().getModuleManager();
}
