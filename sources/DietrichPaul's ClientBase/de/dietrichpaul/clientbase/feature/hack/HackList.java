/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack;

import de.dietrichpaul.clientbase.feature.hack.combat.AutoArmorHack;
import de.dietrichpaul.clientbase.feature.hack.combat.KillAuraHack;
import de.dietrichpaul.clientbase.feature.hack.combat.SuperKnockbackHack;
import de.dietrichpaul.clientbase.feature.hack.combat.BacktrackHack;
import de.dietrichpaul.clientbase.feature.hack.movement.FlightHack;
import de.dietrichpaul.clientbase.feature.hack.movement.SpeedHack;
import de.dietrichpaul.clientbase.feature.hack.movement.SprintHack;
import de.dietrichpaul.clientbase.feature.hack.render.HudHack;
import de.dietrichpaul.clientbase.feature.hack.world.ChestStealerHack;
import de.dietrichpaul.clientbase.feature.hack.world.ScaffoldWalkHack;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class HackList {
    private final Map<String, Hack> hacks = new TreeMap<>();

    // combat
    public AutoArmorHack autoArmor = new AutoArmorHack();
    public BacktrackHack backtrack = new BacktrackHack();
    public KillAuraHack killAura = new KillAuraHack();
    public SuperKnockbackHack superKnockback = new SuperKnockbackHack();

    // movement
    public FlightHack flight = new FlightHack();
    public SpeedHack speed = new SpeedHack();
    public SprintHack sprint = new SprintHack();

    // render
    public HudHack hud = new HudHack();

    // world
    public ChestStealerHack chestStealer = new ChestStealerHack();
    public ScaffoldWalkHack scaffoldWalk = new ScaffoldWalkHack();

    public void registerBuiltIn() {
        // combat
        register(autoArmor);
        register(backtrack);
        register(killAura);
        register(superKnockback);

        // movement
        register(flight);
        register(speed);
        register(sprint);

        // render
        register(hud);

        // world
        register(chestStealer);
        register(scaffoldWalk);
    }

    public void register(Hack hack) {
        hacks.put(hack.getName(), hack);
    }

    public Hack getHack(String name) {
        return hacks.get(name);
    }

    public Collection<Hack> getHacks() {
        return hacks.values();
    }
}
