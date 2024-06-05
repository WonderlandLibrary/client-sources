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
 * @since on 10.08.2020.
 */
public class Timolia extends Config {

    public Timolia() {
        super("Timolia");
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
                    ((KillAura) mod).cps.setCurrent(10);
                    ((KillAura) mod).rotationSpeed.setCurrent(60);
                    ((KillAura) mod).hitChance.setCurrent(90);
                    ((KillAura) mod).throughWalls.setState(false);
                    ((KillAura) mod).checkTabList.setState(true);
                    ((KillAura) mod).slowOnHit.setState(true);
                    ((KillAura) mod).stopSprint.setState(true);
                    ((KillAura) mod).correctMovement.setState(true);
                    ((KillAura) mod).blockHit.setState(false);
                    ((KillAura) mod).smoothRots.setState(true);
                    ((KillAura) mod).clientRots.setState(false);
                }
                if (mod instanceof AntiBots) {
                    ((AntiBots) mod).mineplex.setState(false);
                    ((AntiBots) mod).attackDead.setState(true);
                    ((AntiBots) mod).timolia.setState(true);
                    mod.setState(true);
                }
                if (mod instanceof Velocity) {
                    ((Velocity) mod).setting.setSelectedCombo("Custom");
                    ((Velocity) mod).motionX.setCurrent(1.0F);
                    ((Velocity) mod).motionY.setCurrent(1.0F);
                    ((Velocity) mod).delay.setCurrent(160);
                    ((Velocity) mod).speedAir.setCurrent(1.0F);
                    ((Velocity) mod).jumpFactor.setCurrent(1.0F);
                    ((Velocity) mod).jumpGround.setState(true);
                    ((Velocity) mod).simGround.setState(false);
                    ((Velocity) mod).cancel.setState(true);
                }
                if (mod instanceof Speed) {
                    ((Speed) mod).speedMode.setSelectedCombo("Bhop");
                    ((Speed) mod).bhopMode.setSelectedCombo("Timolia");
                    ((Speed) mod).strafe.setState(true);
                }
                if (mod instanceof ChestStealer) {
                    ((ChestStealer) mod).instant.setState(false);
                    ((ChestStealer) mod).intelligent.setState(false);
                    ((ChestStealer) mod).delay.setCurrent(90);
                    ((ChestStealer) mod).delayRandom.setCurrent(40);
                }
                if (mod instanceof Scaffold) {
                    ((Scaffold) mod).minDelay.setCurrent(60);
                    ((Scaffold) mod).maxDelay.setCurrent(80);
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