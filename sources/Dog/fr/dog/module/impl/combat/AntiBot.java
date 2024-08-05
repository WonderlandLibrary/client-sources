package fr.dog.module.impl.combat;

import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", ModuleCategory.COMBAT);
    }

    public boolean isBot(EntityPlayer player) {
        return !player.hasMoved;
    }
}