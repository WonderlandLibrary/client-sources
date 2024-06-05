package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.Tired;

@ModuleAnnotation(name = "Paint", category = ModuleCategory.MISC)
public class Paint extends Module {
    @Override
    public void onState() {
        MC.displayGuiScreen(Tired.INSTANCE.guiPainting);
        unableModule();
    }

    @Override
    public void onUndo() {
    }
}
