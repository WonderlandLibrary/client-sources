package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.player.MoveUtils;
import java.util.Iterator;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "Fly", type = Category.Movement)
public class Fly extends Function {
    private final ModeSetting mode = new ModeSetting("Мод", "Vanilla", "Vanilla", "Matrix Jump", "Matrix Glide", "GrimAC", "Elytra Vanilla", "Packets", "Storm Glitch");
    private final SliderSetting horizontal = new SliderSetting("По горизонтали", 0.5F, 0.0F, 5.0F, 0.1F);
    private final SliderSetting vertical = new SliderSetting("По вертикали", 0.5F, 0.0F, 5.0F, 0.1F);
    public Entity vehicle;

    public Fly() {
        this.addSettings(new Setting[]{this.mode, this.horizontal, this.vertical});
    }

    @Subscribe
    public void onUpdate(EventUpdate var1) {
        if (mc.player != null && mc.world != null) {
            ClientPlayNetHandler var11;
            switch (this.mode.getIndex()) {
                case 0:
                    this.updatePlayerMotion();
                    break;
                case 1:
                    if (mc.player.isOnGround()) {
                        mc.player.jump();
                    } else {
                        MoveUtils.setMotion((double)Math.min((Float)this.horizontal.get(), 1.97F));
                        mc.player.motion.y = (double)(Float)this.vertical.get();
                    }
                    break;
                case 2:
                    mc.player.motion = Vector3d.ZERO;
                    MoveUtils.setMotion((double)(Float)this.horizontal.get());
                    mc.player.setMotion(mc.player.getMotion().x, -0.003, mc.player.getMotion().z);
                    break;
                case 3:
                    Iterator var7 = mc.world.getAllEntities().iterator();

                    Entity var9;
                    do {
                        if (!var7.hasNext()) {
                            return;
                        }

                        var9 = (Entity)var7.next();
                    } while(!(var9 instanceof BoatEntity) || !(mc.player.getDistance(var9) <= 2.0F));

                    MoveUtils.setMotion(1.2000000476837158);
                    mc.player.motion.y = 1.0;
                    break;
                case 4:
                    if (mc.player.ticksExisted % 2 != 0) {
                        return;
                    }

                    int var6 = -1;
                    Iterator var3 = mc.player.inventory.mainInventory.iterator();

                    while(var3.hasNext()) {
                        ItemStack var10 = (ItemStack)var3.next();
                        if (var10.getItem() instanceof ElytraItem) {
                            var6 = mc.player.inventory.mainInventory.indexOf(var10);
                        }
                    }

                    mc.player.abilities.isFlying = false;
                    if (var6 == -1) {
                        return;
                    }

                    byte var8 = 6;
                    mc.playerController.windowClick(0, var6, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, var8, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, var6, 0, ClickType.PICKUP, mc.player);
                    var11 = mc.player.connection;
                    var11.sendPacket(new CEntityActionPacket(mc.player, Action.START_FALL_FLYING));
                    mc.player.abilities.isFlying = true;
                    mc.playerController.windowClick(0, var6, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, var8, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, var6, 0, ClickType.PICKUP, mc.player);
                    break;
                case 5:
                    this.updatePlayerMotion();
                    this.sendBadPacket();
                    break;
                case 6:
                    MoveUtils.setMotion(0.0);
                    mc.player.setVelocity(0.0, 0.0, 0.0);
                    if (mc.player.ticksExisted % 10 == 1 && mc.player.hurtTime == 0) {
                        mc.player.setOnGround(true);
                        double var2 = -Math.sin(Math.toRadians((double)mc.player.rotationYaw)) * 0.37;
                        double var4 = Math.cos(Math.toRadians((double)mc.player.rotationYaw)) * 0.37;
                        var11 = mc.getConnection();
                        var11.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX() + (!mc.gameSettings.keyBindJump.pressed ? var2 : 0.0), mc.player.getPosY() + (double)randomize(0.15F, 0.19F), mc.player.getPosZ() + (!mc.gameSettings.keyBindJump.pressed ? var4 : 0.0), true));
                        var11 = mc.getConnection();
                        var11.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX() + (!mc.gameSettings.keyBindJump.pressed ? var2 : 0.0), mc.player.getPosY() + 80.0, mc.player.getPosZ() + (!mc.gameSettings.keyBindJump.pressed ? var4 : 0.0), true));
                    }
            }

        }
    }

    public static float randomize(float var0, float var1) {
        double var10000 = (double)var0;
        return (float)(var10000 + (double)(var1 - var0) * Math.random());
    }

    private void sendBadPacket() {
        BlockPos var1 = new BlockPos(mc.player.getPositionVec());
        ClientPlayNetHandler var2 = mc.player.connection;
        double var10003 = mc.player.getPosX();
        double var10004 = mc.player.getPosY();
        var2.sendPacket(new CPlayerPacket.PositionPacket(var10003, var10004 - 1.0E-9, mc.player.getPosZ(), true));
        var2 = mc.player.connection;
        var2.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() + 1.0E-9, mc.player.getPosZ(), false));
        var2 = mc.player.connection;
        var2.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), true));
        var2 = mc.player.connection;
        var2.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
        var2 = mc.player.connection;

        var2.sendPacket(new CPlayerDiggingPacket(net.minecraft.network.play.client.CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, var1, Direction.UP));
        var2 = mc.player.connection;

        var2.sendPacket(new CPlayerDiggingPacket(net.minecraft.network.play.client.CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, var1, Direction.UP));
    }

    @Subscribe
    public void onPacket(EventPacket var1) {

        if (mc.player != null && mc.world != null) {
            SPlayerPositionLookPacket var2;
            IPacket var3;
            switch (this.mode.getIndex()) {
                case 1:
                    var3 = var1.getPacket();
                    if (var3 instanceof SPlayerPositionLookPacket) {
                        var2 = (SPlayerPositionLookPacket)var3;
                        if (mc.player == null) {
                            this.toggle();
                        }

                        mc.player.setPosition(var2.getX(), var2.getY(), var2.getZ());
                        ClientPlayNetHandler var4 = mc.player.connection;

                        var4.sendPacket(new CConfirmTeleportPacket(var2.getTeleportId()));
                        var1.cancel();
                        this.toggle();
                    }
                    break;
                case 3:
                    var3 = var1.getPacket();
                    if (var3 instanceof SPlayerPositionLookPacket) {
                        var2 = (SPlayerPositionLookPacket)var3;
                        this.toggle();
                    }
            }

        }
    }

    private void updatePlayerMotion() {
        double var1 = mc.player.getMotion().x;
        double var3 = this.getMotionY();
        double var5 = mc.player.getMotion().z;
        MoveUtils.setMotion((double)(Float)this.horizontal.get());
        mc.player.motion.y = var3;
    }

    private double getMotionY() {
        return mc.gameSettings.keyBindSneak.pressed ? (double)(-(Float)this.vertical.get()) : (mc.gameSettings.keyBindJump.pressed ? (double)(Float)this.vertical.get() : 0.0);
    }

    public void onDisable() {
        super.onDisable();
        mc.player.abilities.isFlying = false;
    }
}
