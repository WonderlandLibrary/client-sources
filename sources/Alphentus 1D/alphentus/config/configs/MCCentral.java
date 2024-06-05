package alphentus.config.configs;

import alphentus.config.Config;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.combat.AntiBots;
import alphentus.mod.mods.combat.KillAura;
import alphentus.mod.mods.combat.Velocity;
import alphentus.mod.mods.movement.Fly;
import alphentus.mod.mods.movement.LongJump;
import alphentus.mod.mods.movement.Speed;
import alphentus.mod.mods.player.ChestStealer;
import alphentus.mod.mods.player.InventoryManager;
import alphentus.mod.mods.player.Teleport;
import alphentus.mod.mods.world.Scaffold;

/**
 * @author avox | lmao
 * @since on 08.08.2020.
 */
public class MCCentral extends Config {

    public MCCentral() {
        super("MCCentral");
    }

    @Override
    public void loadConfig() {

        try {
            for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
                if (mod instanceof KillAura) {
                    ((KillAura) mod).attackMode.setSelectedCombo("Single");
                    ((KillAura) mod).range.setCurrent(4.8F);
                    ((KillAura) mod).preAim.setState(true);
                    ((KillAura) mod).preAimRange.setCurrent(0.2F);
                    ((KillAura) mod).cps.setCurrent(12);
                    ((KillAura) mod).rotationSpeed.setCurrent(90);
                    ((KillAura) mod).hitChance.setCurrent(100);
                    ((KillAura) mod).throughWalls.setState(true);
                    ((KillAura) mod).checkTabList.setState(true);
                    ((KillAura) mod).slowOnHit.setState(false);
                    ((KillAura) mod).stopSprint.setState(false);
                    ((KillAura) mod).correctMovement.setState(false);
                    ((KillAura) mod).blockHit.setState(true);
                    ((KillAura) mod).smoothRots.setState(false);
                    ((KillAura) mod).clientRots.setState(false);
                }
                if (mod instanceof AntiBots) {
                    ((AntiBots) mod).mineplex.setState(false);
                    ((AntiBots) mod).attackDead.setState(false);
                    ((AntiBots) mod).timolia.setState(false);
                    mod.setState(false);
                }
                if (mod instanceof Velocity) {
                    ((Velocity) mod).setting.setSelectedCombo("Simple");
                }
                if (mod instanceof Fly) {
                    ((Fly) mod).flyMode.setSelectedCombo("MCCentral");
                }
                if (mod instanceof Speed) {
                    ((Speed) mod).speedMode.setSelectedCombo("Bhop");
                    ((Speed) mod).bhopMode.setSelectedCombo("MCCentral");
                    ((Speed) mod).strafe.setState(true);
                }
                if (mod instanceof ChestStealer) {
                    ((ChestStealer) mod).instant.setState(false);
                    ((ChestStealer) mod).intelligent.setState(false);
                    ((ChestStealer) mod).delay.setCurrent(75);
                    ((ChestStealer) mod).delayRandom.setCurrent(25);
                }
                if (mod instanceof Teleport) {
                    ((Teleport) mod).teleportMode.setSelectedCombo("SetPosition");
                }
                if (mod instanceof Scaffold) {
                    ((Scaffold) mod).minDelay.setCurrent(0);
                    ((Scaffold) mod).maxDelay.setCurrent(0);
                    ((Scaffold) mod).sprint.setState(true);
                    ((Scaffold) mod).sneak.setState(false);
                    ((Scaffold) mod).safeWalk.setState(true);
                    ((Scaffold) mod).intave.setState(false);
                    ((Scaffold) mod).otherRots.setState(false);
                }
                if (mod instanceof LongJump) {
                    ((LongJump) mod).longMode.setSelectedCombo("NeruxVace");
                }
                if (mod instanceof InventoryManager) {
                    ((InventoryManager) mod).delay.setCurrent(150);
                    ((InventoryManager) mod).sort.setState(true);
                    ((InventoryManager) mod).clean.setState(true);
                }
            }
        } catch (NullPointerException e) {

        }

        super.loadConfig();
    }
}