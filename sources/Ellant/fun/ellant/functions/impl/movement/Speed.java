package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.client.TimerUtil;
import fun.ellant.utils.player.MoveUtils;
import java.util.Iterator;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Speed", type=Category.MOVEMENT, desc = "Ускоряет вас")
public class Speed
        extends Function {
    ItemStack currentStack = ItemStack.EMPTY;
    public ModeSetting mod = new ModeSetting("\u041c\u043e\u0434", "Entinity 2",  "Elytra", "Old Sunrise", "Grim Entity", "Entinity 2");
    private final SliderSetting speedGrim = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 1.15f, 1.0f, 1.3f, 0.01f).setVisible(() -> !this.mod.is("Grim Entinity") && !this.mod.is("IceSpoof") && !this.mod.is("") && !this.mod.is("Legit") && !this.mod.is("Legit 2") && !this.mod.is("Timer 2") && !this.mod.is("Vulcan") && !this.mod.is("LongHop") && !this.mod.is("Elytra") && !this.mod.is("Elytra 2") && !this.mod.is("Elytra 3") && !this.mod.is("FunSkyHVH") && !this.mod.is("Old Sunrise") && !this.mod.is("Matrix") && !this.mod.is("Simulations") && !this.mod.is("Old Intave") && !this.mod.is("Elytra Abuse") && !this.mod.is("Low") && !this.mod.is("Grim Old") && !this.mod.is("Boost") && !this.mod.is("FunTime") && !this.mod.is("New"));
    private final SliderSetting distanceGrim = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 2.0f, 0.0f, 5.0f, 0.5f).setVisible(() -> !this.mod.is("Grim Entinity") && !this.mod.is("IceSpoof") && !this.mod.is("") && !this.mod.is("Legit") && !this.mod.is("Legit 2") && !this.mod.is("Timer 2") && !this.mod.is("Vulcan") && !this.mod.is("LongHop") && !this.mod.is("Elytra") && !this.mod.is("Elytra 2") && !this.mod.is("Elytra 3") && !this.mod.is("FunSkyHVH") && !this.mod.is("Old Sunrise") && !this.mod.is("Matrix") && !this.mod.is("Simulations") && !this.mod.is("Old Intave") && !this.mod.is("Elytra Abuse") && !this.mod.is("Low") && !this.mod.is("Grim Old") && !this.mod.is("Boost") && !this.mod.is("FunTime") && !this.mod.is("New"));
    private RemoteClientPlayerEntity fakePlayer;
    private long lastPacketTime = -1L;
    public boolean boosting;
    public TimerUtil timerUtil = new TimerUtil();

    public Speed() {
        this.addSettings(this.mod, this.speedGrim, this.distanceGrim);
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        this.timerUtil.reset();
        this.boosting = false;
        return false;
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        BlockPos blockPos;
        BlockState blockState;
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        float f;
        if (this.mod.is("Entinity 2")) {
            this.ft();
        }
        if (this.mod.is("Matrix Old")) {
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
                f = 10.0f;
                d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
                d5 = Speed.mc.player.getForward().z * (double)f;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    int n = "\u5e9f\u5f4f\u53bb\u533b".length();
                    Speed.mc.player.motion.x *= 2.25;
                    int n2 = "\u5042\u6b4a\u6b17\u5a83".length();
                    int n3 = "\u5a88\u575d".length();
                    Speed.mc.player.motion.z *= 2.25;
                }
            }


            if (Speed.mc.player.fallDistance >= 1.0f) {
                f = 10.0f;
                d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
                d5 = Speed.mc.player.getForward().z * (double)f;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    int n = "\u659d\u6aff".length();
                    Speed.mc.player.motion.x *= 2.25;
                    int n4 = "\u5528\u6e1c\u5564\u5aa6".length();
                    int n5 = "\u6060\u5cd3\u6666\u70cc".length();
                    Speed.mc.player.motion.z *= 2.25;
                }
            }
        }
        if (this.mod.is("Matrix Old 2") && Speed.mc.player.isOnGround()) {
            Speed.mc.player.jump();
            f = 10.0f;
            d6 = Speed.mc.player.getForward().x * (double)(f /= 100.0f);
            d5 = Speed.mc.player.getForward().z * (double)f;
            if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.8f) {
                int n = "\u663f".length();
                int n6 = "\u66b5\u68ea\u65fc\u5975".length();
                int n7 = "\u5c89".length();
                int n8 = "\u569d\u51ad\u5f13".length();
                Speed.mc.player.motion.x *= 1.75;
                int n9 = "\u514c\u6012".length();
                int n10 = "\u6bcb\u6d97\u5033\u615f\u6ba2".length();
                int n11 = "\u53b6\u4ef6".length();
                Speed.mc.player.motion.z *= 1.75;
            }
        }
        if (this.mod.is("OnGround") && Speed.mc.player.isOnGround()) {
            if (MoveUtils.isMoving()) {
                Speed.mc.player.setSprinting(true);
                Speed.mc.player.jump(0.0f, 1.2f);
            } else {
                Speed.mc.player.setSprinting(false);
            }
        }
        if (this.mod.is("Timer 1")) {
            if (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.isOnLadder()) {
                return;
            }
            f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.34f;
            }
            if (Speed.mc.player.fallDistance > 1.0f) {
                f = 0.6f;
            }
            if (MoveUtils.isMoving()) {
                Speed.mc.timer.timerSpeed = 1.0f;
                if (Speed.mc.player.isOnGround()) {
                    if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Speed.mc.player.jump();
                    }
                } else {
                    Speed.mc.timer.timerSpeed = f;
                }
            } else {
                Speed.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (this.mod.is("Timer 2")) {
            if (Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.isOnLadder()) {
                return;
            }
            f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.0f;
            }
            if (Speed.mc.player.fallDistance > 0.6f) {
                f = 1.7f;
            }
            if (MoveUtils.isMoving()) {
                Speed.mc.timer.timerSpeed = 1.0f;
                if (Speed.mc.player.isOnGround()) {
                    if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Speed.mc.player.jump();
                    }
                } else {
                    Speed.mc.timer.timerSpeed = f;
                }
            } else {
                Speed.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (this.mod.is("Vulcan")) {
            Speed.mc.player.jumpMovementFactor = 0.025f;
            if (Speed.mc.player.isOnGround() && MoveUtils.isMoving()) {
                if (Speed.mc.player.collidedHorizontally || Speed.mc.gameSettings.keyBindJump.pressed) {
                    if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                        Speed.mc.player.jump();
                    }
                    return;
                }
                Speed.mc.player.jump();
                Speed.mc.player.motion.y = 0.1;
            }
        }
        if (this.mod.is("LongHop") && Speed.mc.player.fallDistance >= 0.04f && MoveUtils.isMoving()) {
            f = Speed.mc.player.rotationYawHead;
            float f2 = Speed.mc.player.rotationPitch;
            d4 = 1.0;
            d3 = -Math.sin((double)f / 180.0 * Math.PI) * Math.cos((double)f2 / 180.0 * Math.PI) * d4;
            d2 = Math.cos((double)f / 180.0 * Math.PI) * Math.cos((double)f2 / 180.0 * Math.PI) * d4;
            Speed.mc.player.setVelocity(d3, -0.6, d2);
        }
        if (this.mod.is("Elytra")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA && Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
                if (Speed.mc.player.isAirBorne) {
                    Speed.mc.player.startFallFlying();
                    int n = "\u62aa\u6a52\u6d79\u682b\u7090".length();
                    int n12 = "\u64d2\u624c\u6f72\u5d5e\u675d".length();
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    if ((double)Speed.mc.player.fallDistance == 0.1) {
                        // empty if block
                    }
                    Speed.mc.player.jump();
                    Speed.mc.player.jump();
                }
            }
        }
        if (this.mod.is("Bot") && MoveUtils.isMoving()) {
            this.spawnFakePlayer();
            Speed.mc.player.setSprinting(true);
            int n = "\u5434\u5065\u6535\u5042".length();
            int n13 = "\u579e\u5e5f\u6b87\u677b\u5f65".length();
            int n14 = "\u6c59".length();
            Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_SPRINTING));
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
        }
        if (this.mod.is("Elytra 2")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.player.rotationPitchHead = -90.0f;
                } else if (ElytraItem.isUsable(this.currentStack) && !Speed.mc.player.isElytraFlying()) {
                    Speed.mc.player.startFallFlying();
                    int n = "\u5785\u5d08\u6626\u56d6".length();
                    int n15 = "\u6f19\u51f1\u5acb".length();
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    Speed.mc.player.rotationPitchHead = -90.0f;
                }
            }
        }
        if (this.mod.is("Grim")) {
            if (MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
            Speed.mc.timer.timerSpeed = Speed.mc.player.isOnGround() ? 0.9f : (Speed.mc.player.fallDistance >= 0.3f ? 2.0f : 0.8f);
        }
        if (this.mod.is("Legit")) {
            Speed.mc.timer.timerSpeed = 1.0f;
            int n = "\u6293\u6a78\u62b5\u6de8\u52bb".length();
            int n16 = "\u68ab".length();
            int n17 = "\u6097\u604c\u5db3\u5335".length();
            int n18 = "\u6c9d\u54cd\u6f0e\u6c22\u5c8a".length();
            int n19 = "\u6107\u6031\u61c8".length();
            Speed.mc.player.getMotion().y -= 0.00348;
            Speed.mc.player.jumpMovementFactor = 0.026f;
            Speed.mc.gameSettings.keyBindJump.setPressed(Speed.mc.gameSettings.keyBindJump.isKeyDown());
            if (MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
                Speed.mc.gameSettings.keyBindJump.setPressed(false);
                Speed.mc.timer.timerSpeed = 1.35f;
                Speed.mc.player.jump();
            }
        }
        if (this.mod.is("Legit 2")) {
            Speed.mc.gameSettings.keyBindJump.setPressed(Speed.mc.gameSettings.keyBindJump.isKeyDown());
            if (MoveUtils.isMoving()) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.gameSettings.keyBindJump.setPressed(false);
                    Speed.mc.timer.timerSpeed = 1.0f;
                    Speed.mc.player.jump();
                }
                if (Speed.mc.player.getMotion().y > 0.003) {
                    int n = "\u58b8\u678c\u5bd6\u5887\u6161".length();
                    int n20 = "\u50bd\u5b6c\u6c75\u5278\u6563".length();
                    Speed.mc.player.getMotion().x *= 1.0015;
                    int n21 = "\u64ac\u6559\u6fcf\u5d00".length();
                    int n22 = "\u66ed\u6f89\u57cc".length();
                    int n23 = "\u5ec8".length();
                    int n24 = "\u6e0a\u6dd5\u6761".length();
                    Speed.mc.player.getMotion().z *= 1.0015;
                    Speed.mc.timer.timerSpeed = 1.06f;
                }
            }
        }
        if (this.mod.is("FunSkyHVH") && MoveUtils.isMoving()) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.player.rotationPitchHead = -90.0f;
                } else if (ElytraItem.isUsable(this.currentStack) && !Speed.mc.player.isElytraFlying()) {
                    Speed.mc.player.startFallFlying();
                    int n = "\u62ba\u4f7e".length();
                    int n25 = "\u5699\u5951\u6556".length();
                    Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    Speed.mc.player.rotationPitchHead = -90.0f;
                    if (Speed.mc.player.isElytraFlying()) {
                        Speed.mc.player.setSprinting(true);
                        Speed.mc.player.jump(0.0f, 1.2f);
                        f = Speed.mc.player.rotationYawHead;
                        float f3 = Speed.mc.player.rotationPitch;
                        d4 = 1.4;
                        d3 = -Math.sin((double)f / 180.0 * Math.PI) * Math.cos((double)f3 / 180.0 * Math.PI) * d4;
                        d2 = Math.cos((double)f / 180.0 * Math.PI) * Math.cos((double)f3 / 180.0 * Math.PI) * d4;
                        Speed.mc.player.setVelocity(d3, -0.65, d2);
                    }
                }
            }
        }
        if (this.mod.is("OnGround2") && MoveUtils.isMoving() && Speed.mc.player.isOnGround()) {
            f = Speed.mc.player.rotationYawHead;
            float f4 = Speed.mc.player.rotationPitch;
            d4 = 1.3;
            d3 = 15.0;
            d2 = d3 / 180.0 * 3.1415927410125732;
            d = (double)(-MathHelper.sin(f / 180.0f * (float)Math.PI)) * Math.cos(d2) * d4;
            double d7 = (double)MathHelper.cos(f / 180.0f * (float)Math.PI) * Math.cos(d2) * d4;
            Speed.mc.player.setVelocity(d, 0.0, d7);
        }
        if (this.mod.is("Old Sunrise")) {
            this.handleSunriseDamageMode();
        }
        if (this.mod.is("Matrix") && Speed.mc.player.isOnGround() && MoveUtils.isMoving()) {
            Speed.mc.gameSettings.keyBindJump.setPressed(true);
            this.applyMatrixSpeed();
        }
        if (this.mod.is("Simulations")) {
            this.handleRWMode();
        }
        if (this.mod.is("Elytra 3")) {
            this.elytranew();
        }
        if (this.mod.is("Old Intave")) {
            Speed.mc.gameSettings.keyBindJump.setPressed(false);
            if (MoveUtils.isMoving()) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
                if (Speed.mc.player.getMotion().y > 0.003) {
                    int n = "\u60dd\u55ea".length();
                    int n26 = "\u5bce\u6aaf".length();
                    int n27 = "\u58a7\u6569".length();
                    int n28 = "\u5fb4".length();
                    int n29 = "\u6fec".length();
                    Speed.mc.player.getMotion().x *= 1.0015;
                    int n30 = "\u6b7d".length();
                    int n31 = "\u57cf".length();
                    int n32 = "\u6f4d\u5863\u5405\u6c8c\u611f".length();
                    int n33 = "\u532b\u6319\u4f5e".length();
                    int n34 = "\u6584\u6cf1\u553d\u63d6".length();
                    Speed.mc.player.getMotion().z *= 1.0015;
                    Speed.mc.timer.timerSpeed = 1.06f;
                }
            }
        }
        if (this.mod.is("Grim Entity")) {
            Iterator<AbstractClientPlayerEntity> iterator2 = Speed.mc.world.getPlayers().iterator();
            while (true) {
                float f5;
                if (!iterator2.hasNext()) {
                    return;
                }
                PlayerEntity playerEntity = iterator2.next();
                if (Speed.mc.player == playerEntity) continue;
                float f6 = ((Float)this.distanceGrim.get()).floatValue();
                if (!(Speed.mc.player.getDistance(playerEntity) <= f6) || !Speed.mc.gameSettings.keyBindForward.isKeyDown() && !Speed.mc.gameSettings.keyBindRight.isKeyDown() && !Speed.mc.gameSettings.keyBindLeft.isKeyDown() && !Speed.mc.gameSettings.keyBindBack.isKeyDown()) continue;
                float f7 = f5 = ((Float)this.speedGrim.get()).floatValue();
                Vector3d vector3d = Speed.mc.player.getMotion();
                int n = "\u5aff\u6323".length();
                int n35 = "\u65fd\u5e61\u6ac7\u59d3".length();
                vector3d.x *= (double)f7;
                vector3d = Speed.mc.player.getMotion();
                int n36 = "\u60ca\u57a6\u6bdb\u6b30".length();
                int n37 = "\u519d\u687f\u5d09\u5311\u5857".length();
                vector3d.z *= (double)f7;
            }
        }
        if (this.mod.is("Low")) {
            Iterator<AbstractClientPlayerEntity> iterator3 = Speed.mc.world.getPlayers().iterator();
            while (true) {
                if (!iterator3.hasNext()) {
                    return;
                }
                PlayerEntity playerEntity = iterator3.next();
                if (Speed.mc.player == playerEntity || !(Speed.mc.player.getDistance(playerEntity) <= 0.89f) || !Speed.mc.gameSettings.keyBindForward.isKeyDown() && !Speed.mc.gameSettings.keyBindRight.isKeyDown() && !Speed.mc.gameSettings.keyBindLeft.isKeyDown() && !Speed.mc.gameSettings.keyBindBack.isKeyDown()) continue;
                float f8 = 1.3f;
                Vector3d vector3d = Speed.mc.player.getMotion();
                int n = "\u5eae\u56cf\u52f7".length();
                int n38 = "\u56fb\u6722".length();
                vector3d.x *= (double)f8;
                vector3d = Speed.mc.player.getMotion();
                int n39 = "\u7096\u56e4".length();
                int n40 = "\u4ed5\u502b\u6bb1".length();
                vector3d.z *= (double)f8;
            }
        }
        if (this.mod.is("Grim Old")) {
            if (Speed.mc.player.movementInput.moveForward == 0.0f) {
                MovementInput cfr_ignored_0 = Speed.mc.player.movementInput;
                int n = "\u6f29\u5495\u6098\u5962".length();
                int n41 = "\u5a73\u6ae8\u5702\u6ece\u6387".length();
                int n42 = "\u6d00".length();
                int n43 = "\u6b73\u6e26".length();
                if (MovementInput.moveStrafe == 0.0f) {
                    return;
                }
            }
            double d8 = 0.0027;
            float f9 = Speed.mc.player.rotationYaw;
            if (Speed.mc.player.movementInput.moveForward < 0.0f) {
                f9 += 0.4f;
            }
            MovementInput cfr_ignored_1 = Speed.mc.player.movementInput;
            int n = "\u5574\u5f47\u5d0a\u5911".length();
            int n44 = "\u710f\u5886\u6908".length();
            int n45 = "\u604b\u5826\u5b14\u6a3f".length();
            if (MovementInput.moveStrafe > 0.0f) {
                int n46 = "\u5b6c\u53e3\u6e10".length();
                f9 -= 0.4f * (Speed.mc.player.movementInput.moveForward < 0.0f ? -0.5f : (Speed.mc.player.movementInput.moveForward > 0.0f ? 0.5f : 1.0f));
            }
            MovementInput cfr_ignored_2 = Speed.mc.player.movementInput;
            int n47 = "\u58c3\u6ffa\u6490".length();
            int n48 = "\u59b7\u5e7e\u511d".length();
            int n49 = "\u5a38\u5d50\u6acb\u5348\u5d52".length();
            if (MovementInput.moveStrafe < 0.0f) {
                f9 += 0.4f * (Speed.mc.player.movementInput.moveForward < 0.0f ? -0.5f : (Speed.mc.player.movementInput.moveForward > 0.0f ? 0.5f : 1.0f));
            }
            int n50 = "\u528a\u57ff\u508b\u5f4f\u6761".length();
            int n51 = "\u66b0\u5edb\u581e\u6d6a".length();
            int n52 = "\u56fc".length();
            Speed.mc.player.setMotion(Speed.mc.player.getMotion().x - Math.sin(Math.toRadians(f9)) * d8, Speed.mc.player.getMotion().y, Speed.mc.player.getMotion().z + Math.cos(Math.toRadians(f9)) * d8);
        }
        if (this.mod.is("Boost")) {
            ClientPlayerEntity clientPlayerEntity = Speed.mc.player;
            if (clientPlayerEntity.movementInput.moveForward > 0.0f && clientPlayerEntity.isOnGround()) {
                long l = System.currentTimeMillis();
                if (this.lastPacketTime == -1L || l - this.lastPacketTime > 500L) {
                    d5 = 2.0;
                    float f10 = clientPlayerEntity.getYaw(0.0f);
                    d2 = -Math.sin(Math.toRadians(f10)) * d5;
                    d = Math.cos(Math.toRadians(f10)) * d5;
                    clientPlayerEntity.setMotion(d2, clientPlayerEntity.getMotion().y, d);
                    this.lastPacketTime = l;
                }
            }
        }
        if (this.mod.is("Elytra Abuse")) {
            this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (this.currentStack.getItem() == Items.ELYTRA) {
                if (Speed.mc.player.isOnGround()) {
                    Speed.mc.player.jump();
                }
                if (Speed.mc.player.fallDistance >= 0.11f) {
                    Speed.mc.gameSettings.keyBindJump.setPressed(true);
                } else {
                    Speed.mc.gameSettings.keyBindJump.setPressed(false);
                }
                if (Speed.mc.player.isInWater()) {
                    Speed.mc.player.stopFallFlying();
                }
            }
        }
        if (this.mod.is("FunTime") && ((blockState = Speed.mc.world.getBlockState((blockPos = Speed.mc.player.getPosition()).down())).getBlock() instanceof StairsBlock || blockState.getBlock() instanceof SlabBlock || blockState.getBlock() instanceof BarrelBlock || blockState.getBlock() instanceof ScaffoldingBlock || blockState.getBlock() instanceof CarpetBlock || blockState.getBlock() instanceof FlowerPotBlock) && Speed.mc.player.isOnGround() && MoveUtils.isMoving()) {
            Speed.mc.gameSettings.keyBindJump.setPressed(true);
            this.applyMatrixSpeed();
        }

        if (this.mod.is("New")) {
            // empty if block
        }
        if (this.mod.is("IceSpoof")) {
            this.isespoof();
        }
    }

    private void elytranew() {
        this.currentStack = Speed.mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (this.currentStack.getItem() == Items.ELYTRA) {
            if (Speed.mc.player.isOnGround()) {
                Speed.mc.player.jump();
            }
            float f = 1.0f;
            if (Speed.mc.player.fallDistance <= 0.1f) {
                f = 1.0f;
            }
            if (Speed.mc.player.fallDistance > 0.6f) {
                Speed.mc.player.startFallFlying();
                int n = "\u52c8\u6007".length();
                int n2 = "\u6c22".length();
                int n3 = "\u5600".length();
                Speed.mc.player.connection.sendPacket(new CEntityActionPacket(Speed.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                Speed.mc.player.stopFallFlying();
                float f2 = 10.0f;
                double d = Speed.mc.player.getForward().x * (double)(f2 /= 100.0f);
                double d2 = Speed.mc.player.getForward().z * (double)f2;
                if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                    int n4 = "\u6b44\u663f".length();
                    int n5 = "\u56db\u6d35\u5a35\u599b\u50df".length();
                    int n6 = "\u6bd1\u5393\u5230".length();
                    Speed.mc.player.motion.x *= 2.25;
                    int n7 = "\u5c33\u4f17\u5789\u5829\u5257".length();
                    int n8 = "\u65f3".length();
                    Speed.mc.player.motion.z *= 2.25;
                    if (MoveUtils.isMoving()) {
                        Speed.mc.timer.timerSpeed = 1.0f;
                        if (Speed.mc.player.isOnGround()) {
                            if (!Speed.mc.gameSettings.keyBindJump.isKeyDown()) {
                                Speed.mc.player.jump();
                            }
                        } else {
                            Speed.mc.timer.timerSpeed = f;
                        }
                    } else {
                        Speed.mc.timer.timerSpeed = 1.0f;
                    }
                }
            }
        }
    }

    private void ft() {
        AxisAlignedBB aabb = TargetStrafe.mc.player.getBoundingBox().grow(0.1);
        int armorstans = TargetStrafe.mc.world.getEntitiesWithinAABB(ArmorStandEntity.class, aabb).size();
        boolean canBoost = armorstans > 1 || TargetStrafe.mc.world.getEntitiesWithinAABB(LivingEntity.class, aabb).size() > 1;
        if (canBoost && !TargetStrafe.mc.player.isOnGround()) {
            TargetStrafe.mc.player.jumpMovementFactor = armorstans > 1 ? 1.0F / (float)armorstans : 0.16F;
            float var5 = TargetStrafe.mc.player.jumpMovementFactor;
        }

    }

    private void handlePacketEvent(EventPacket eventPacket) {
        if (this.mod.is("Simulations")) {
            SPlayerPositionLookPacket iPacket;
            IPacket<?> iPacket2 = eventPacket.getPacket();
            if (iPacket2 instanceof CConfirmTransactionPacket) {
                iPacket = (SPlayerPositionLookPacket) iPacket2;
                eventPacket.cancel();
            }
            if ((iPacket2 = eventPacket.getPacket()) instanceof SPlayerPositionLookPacket) {
                iPacket = (SPlayerPositionLookPacket)iPacket2;
                int n = "\u63e9".length();
                int n2 = "\u6b09".length();
                int n3 = "\u5df8\u507d".length();
                int n4 = "\u6479\u5d85\u7073\u5c74".length();
                Speed.mc.player.func_242277_a(new Vector3d(((SPlayerPositionLookPacket)iPacket).getX(), ((SPlayerPositionLookPacket)iPacket).getY(), ((SPlayerPositionLookPacket)iPacket).getZ()));
                Speed.mc.player.setRawPosition(((SPlayerPositionLookPacket)iPacket).getX(), ((SPlayerPositionLookPacket)iPacket).getY(), ((SPlayerPositionLookPacket)iPacket).getZ());
                this.toggle();
            }
        }
    }

    private void isespoof() {
        int n = "\u603f\u6d51\u5d10\u66d5".length();
        int n2 = "\u658b\u5a6f\u6eeb\u5f9c\u55d1".length();
        int n3 = "\u5a27".length();
        int n4 = "\u61cb\u52a1\u5462".length();
        int n5 = "\u713f\u52bb\u6c90\u6e1c\u4f85".length();
        BlockPos blockPos = new BlockPos(Speed.mc.player.getPosX(), Speed.mc.player.getPosY() - 1.0, Speed.mc.player.getPosZ());
        if (Speed.mc.world.getBlockState(blockPos).isAir() || !Speed.mc.gameSettings.keyBindJump.isPressed()) {
            return;
        }
        int n6 = "\u6049\u6ac0\u587e\u5ac0\u4ed6".length();
        int n7 = "\u6b28\u5fb9\u5e79\u5bdc".length();
        Speed.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
        Speed.mc.world.setBlockState(blockPos, Blocks.ICE.getDefaultState(), 3);
        int n8 = "\u6c78".length();
    }

    private void handleRWMode() {
        if (this.timerUtil.hasTimeElapsed(1150L)) {
            this.boosting = true;
        }
        if (this.timerUtil.hasTimeElapsed(7000L)) {
            this.boosting = false;
            this.timerUtil.reset();
        }
        if (this.boosting) {
            if (Speed.mc.player.isOnGround() && !Speed.mc.gameSettings.keyBindJump.pressed) {
                Speed.mc.player.jump();
            }
            Speed.mc.timer.timerSpeed = Speed.mc.player.ticksExisted % 2 == 0 ? 1.5f : 1.2f;
        } else {
            Speed.mc.timer.timerSpeed = 0.05f;
        }
    }

    private void spawnFakePlayer() {
        int n = "\u517f\u5ce7\u5690\u613d\u63a8".length();
        int n2 = "\u5337\u5bfd".length();
        this.fakePlayer = new RemoteClientPlayerEntity(Speed.mc.world, Speed.mc.player.getGameProfile());
        this.fakePlayer.copyLocationAndAnglesFrom(Speed.mc.player);
        this.fakePlayer.rotationYawHead = Speed.mc.player.rotationYawHead;
        this.fakePlayer.renderYawOffset = Speed.mc.player.renderYawOffset;
        this.fakePlayer.rotationPitchHead = Speed.mc.player.rotationPitchHead;
        this.fakePlayer.container = Speed.mc.player.container;
        this.fakePlayer.inventory = Speed.mc.player.inventory;
        Speed.mc.world.addEntity(1337, this.fakePlayer);
        int n3 = "\u6f3a".length();
        this.fakePlayer.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        int n4 = "\u587d".length();
        int n5 = "\u69d0\u4e7e\u68c8\u56e2".length();
        this.fakePlayer.setInvisible(true);
    }

    private void handleSunriseDamageMode() {
        double d = MoveUtils.getDirection(true);
        if (MoveUtils.isMoving()) {
            if (Speed.mc.player.isOnGround()) {
                this.applySunriseGroundMotion(d);
            } else if (Speed.mc.player.isInWater()) {
                this.applySunriseWaterMotion(d);
            } else if (!Speed.mc.player.isOnGround()) {
                this.applySunriseAirMotion(d);
            } else {
                this.applySunriseDefaultMotion(d);
            }
        }
    }

    private void applySunriseGroundMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseWaterMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 9.5 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 9.5 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseAirMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 0.11 / 24.5, 0.0, (double)MathHelper.cos((float)d) * 0.11 / 24.5);
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applySunriseDefaultMotion(double d) {
        Speed.mc.player.addVelocity((double)(-MathHelper.sin((float)d)) * 0.005 * MoveUtils.getMotion(), 0.0, (double)MathHelper.cos((float)d) * 0.005 * MoveUtils.getMotion());
        MoveUtils.setMotion(MoveUtils.getMotion());
    }

    private void applyMatrixSpeed() {
        double d = 2.0;
        int n = "\u70f7".length();
        int n2 = "\u5d8a\u6d18\u5088\u5476".length();
        Speed.mc.player.motion.x *= d;
        int n3 = "\u6993\u6fbc\u6192\u6e6d".length();
        int n4 = "\u6960\u4f34\u57df\u6648\u70ce".length();
        Speed.mc.player.motion.z *= d;
        StrafeMovement.oldSpeed *= d;
    }

    private void applyFunTimeSpeed() {
        double d = 2.2;
        int n = "\u6ac9\u6cfa".length();
        Speed.mc.player.motion.x *= d;
        int n2 = "\u57f1\u5973\u56ec\u6cd6".length();
        Speed.mc.player.motion.z *= d;
        StrafeMovement.oldSpeed *= d;
    }

    private void applyNewSpeed() {
        if (Speed.mc.player.isOnGround()) {
            double d = 1.5;
            int n = "\u6117\u6cd1\u55cc\u52cc\u555f".length();
            int n2 = "\u6251\u5e7e\u5a2a".length();
            Speed.mc.player.motion.x *= d;
            int n3 = "\u5104".length();
            int n4 = "\u5c7d\u51de".length();
            int n5 = "\u6cca\u67ed\u6ae7\u622a".length();
            int n6 = "\u6490\u6831\u5779".length();
            Speed.mc.player.motion.z *= d;
            StrafeMovement.oldSpeed *= d;
        }
    }

    private void removeFakePlayer() {
        Speed.mc.world.removeEntityFromWorld(1337);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.removeFakePlayer();
        Speed.mc.timer.timerSpeed = 1.0f;
    }

    public static class StrafeMovement {
        public static double oldSpeed;
        public static double contextFriction;

        public StrafeMovement() {
        }

        public static void postMove(double horizontal) {
            oldSpeed = horizontal * contextFriction;
        }

        public static float getAIMoveSpeed(ClientPlayerEntity contextPlayer) {
            boolean prevSprinting = contextPlayer.isSprinting();
            contextPlayer.setSprinting(false);
            float speed = contextPlayer.getAIMoveSpeed() * 1.3F;
            contextPlayer.setSprinting(prevSprinting);
            return speed;
        }
    }
}