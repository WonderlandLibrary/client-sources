package dev.excellent.client.module.impl.combat;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.BooleanValue;
import lombok.Getter;

@Getter
@ModuleInfo(name = "Targets", description = "Выбор целей для Киллауры или других комбат функций.", category = Category.COMBAT, autoEnabled = true, allowDisable = false)
public class Targets extends Module {
    private final BooleanValue players = new BooleanValue("Players", this, true);
    private final BooleanValue naked = new BooleanValue("Naked", this, true);
    private final BooleanValue invisible = new BooleanValue("Invisible", this, true);
    private final BooleanValue animals = new BooleanValue("Animals", this, true);
    private final BooleanValue mobs = new BooleanValue("Mobs", this, true);
    private final BooleanValue teams = new BooleanValue("Teams", this, false);
}