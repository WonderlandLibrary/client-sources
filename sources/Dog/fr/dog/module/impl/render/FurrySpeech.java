package fr.dog.module.impl.render;

import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;

public class FurrySpeech extends Module {
    public FurrySpeech() {
        super("FurrySpeech", ModuleCategory.RENDER);
        this.setEnabled(false);
    }
}