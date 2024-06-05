package alphentus.config.configs;

import alphentus.config.Config;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.combat.AntiBots;
import alphentus.mod.mods.combat.KillAura;
import alphentus.mod.mods.combat.Velocity;
import alphentus.mod.mods.movement.Fly;
import alphentus.mod.mods.movement.Speed;
import alphentus.mod.mods.player.ChestStealer;
import alphentus.mod.mods.player.InventoryManager;
import alphentus.mod.mods.player.Teleport;
import alphentus.mod.mods.world.Scaffold;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class HiveMC extends Config {

    public HiveMC() {
        super("HiveMC");
    }

    @Override
    public void loadConfig() {

        try {
            for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
                if (mod instanceof KillAura) {
                    ((KillAura) mod).attackMode.setSelectedCombo("Single");
                    ((KillAura) mod).range.setCurrent(6.3F);
                    ((KillAura) mod).preAim.setState(true);
                    ((KillAura) mod).preAimRange.setCurrent(0.7F);
                    ((KillAura) mod).cps.setCurrent(15);
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
                    ((Velocity) mod).setting.setSelectedCombo("Reverse");
                    ((Velocity) mod).reverse.setCurrent(0.1F);
                }
                if (mod instanceof Fly) {
                    ((Fly) mod).flyMode.setSelectedCombo("AAC 3.2.2");
                    ((Fly) mod).flyBoost.setCurrent(7.5F);
                }
                if (mod instanceof Speed) {
                    ((Speed) mod).speedMode.setSelectedCombo("LowHop");
                    ((Speed) mod).bhopMode.setSelectedCombo("HiveMC");
                    ((Speed) mod).lowHopMode.setSelectedCombo("HiveMC");
                    ((Speed) mod).strafe.setState(true);
                }
                if (mod instanceof ChestStealer) {
                    ((ChestStealer) mod).instant.setState(true);
                    ((ChestStealer) mod).intelligent.setState(false);
                    ((ChestStealer) mod).delay.setCurrent(0);
                    ((ChestStealer) mod).delayRandom.setCurrent(0);
                }
                if (mod instanceof Teleport) {
                    ((Teleport) mod).teleportMode.setSelectedCombo("SetPosition");
                }
                if (mod instanceof Scaffold) {
                    ((Scaffold) mod).minDelay.setCurrent(70);
                    ((Scaffold) mod).maxDelay.setCurrent(90);
                    ((Scaffold) mod).sprint.setState(false);
                    ((Scaffold) mod).sneak.setState(false);
                    ((Scaffold) mod).safeWalk.setState(true);
                    ((Scaffold) mod).intave.setState(false);
                    ((Scaffold) mod).otherRots.setState(false);
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