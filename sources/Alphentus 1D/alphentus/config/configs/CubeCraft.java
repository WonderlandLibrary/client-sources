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
 * @since on 13.08.2020.
 */
public class CubeCraft extends Config {

    public CubeCraft() {
        super("CubeCraft");
    }

    @Override
    public void loadConfig() {

        try {
            for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
                if (mod instanceof KillAura) {
                    ((KillAura) mod).attackMode.setSelectedCombo("Single");
                    ((KillAura) mod).range.setCurrent(3.8F);
                    ((KillAura) mod).preAim.setState(false);
                    ((KillAura) mod).preAimRange.setCurrent(0.0F);
                    ((KillAura) mod).cps.setCurrent(7);
                    ((KillAura) mod).rotationSpeed.setCurrent(60);
                    ((KillAura) mod).hitChance.setCurrent(100);
                    ((KillAura) mod).throughWalls.setState(false);
                    ((KillAura) mod).checkTabList.setState(true);
                    ((KillAura) mod).slowOnHit.setState(true);
                    ((KillAura) mod).stopSprint.setState(true);
                    ((KillAura) mod).correctMovement.setState(false);
                    ((KillAura) mod).blockHit.setState(false);
                    ((KillAura) mod).smoothRots.setState(true);
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
                    ((Fly) mod).flyMode.setSelectedCombo("CubeCraft");
                    ((Fly) mod).cubecraftTimer.setCurrent(0.3F);
                    ((Fly) mod).cubecraftSpeed.setCurrent(2.8F);
                }
                if (mod instanceof Speed) {
                    ((Speed) mod).speedMode.setSelectedCombo("Bhop");
                    ((Speed) mod).bhopMode.setSelectedCombo("CubeCraft");
                    ((Speed) mod).strafe.setState(true);
                }
                if (mod instanceof ChestStealer) {
                    ((ChestStealer) mod).instant.setState(false);
                    ((ChestStealer) mod).intelligent.setState(false);
                    ((ChestStealer) mod).delay.setCurrent(100);
                    ((ChestStealer) mod).delayRandom.setCurrent(40);
                }
                if (mod instanceof Teleport) {
                    ((Teleport) mod).teleportMode.setSelectedCombo("SetPosition");
                }
                if (mod instanceof Scaffold) {
                    ((Scaffold) mod).minDelay.setCurrent(80);
                    ((Scaffold) mod).maxDelay.setCurrent(100);
                    ((Scaffold) mod).sprint.setState(false);
                    ((Scaffold) mod).sneak.setState(false);
                    ((Scaffold) mod).safeWalk.setState(true);
                    ((Scaffold) mod).intave.setState(false);
                    ((Scaffold) mod).otherRots.setState(false);
                }
                if (mod instanceof LongJump) {
                    ((LongJump) mod).longMode.setSelectedCombo("CubeCraft");
                }
                if (mod instanceof InventoryManager) {
                    ((InventoryManager) mod).delay.setCurrent(125);
                    ((InventoryManager) mod).sort.setState(true);
                    ((InventoryManager) mod).clean.setState(true);
                }
            }
        } catch (NullPointerException e) {

        }

        super.loadConfig();
    }
}