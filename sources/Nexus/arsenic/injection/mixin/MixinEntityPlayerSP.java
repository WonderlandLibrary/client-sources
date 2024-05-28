package arsenic.injection.mixin;

import arsenic.event.impl.EventMouse;
import arsenic.module.impl.movement.NoSlow;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import arsenic.event.impl.EventTick;
import arsenic.event.impl.EventUpdate;
import arsenic.main.Nexus;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

import static arsenic.main.MinecraftAPI.mouseDownLastTick;

@Mixin(priority = 1111, value = EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
    private double cachedX;
    private double cachedY;
    private double cachedZ;
    private boolean cachedOnGround;

    private float cachedRotationPitch;
    private float cachedRotationYaw;

    public MixinEntityPlayerSP(World p_i45074_1_, GameProfile p_i45074_2_) {
        super(p_i45074_1_, p_i45074_2_);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    private void onUpdateWalkingPlayerPre(CallbackInfo ci) {

        cachedX = posX;
        cachedY = posY;
        cachedZ = posZ;

        cachedOnGround = onGround;

        cachedRotationYaw = rotationYaw;
        cachedRotationPitch = rotationPitch;

        EventUpdate event = new EventUpdate.Pre(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
        Nexus.getInstance().getEventManager().post(event);
        if (event.isCancelled()) {
            ci.cancel();
            return;
        }

        posX = event.getX();
        posY = event.getY();
        posZ = event.getZ();

        onGround = event.isOnGround();

        rotationYaw = event.getYaw();
        rotationPitch = event.getPitch();
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        Nexus.getInstance().getEventManager().post(new EventTick());
        for (int i = 0; i < 3; i++) {
            if (Mouse.isButtonDown(i) && !mouseDownLastTick[i]) {
                mouseDownLastTick[i] = true;
                Nexus.getNexus().getEventManager().post(new EventMouse.Down(i));
            } else if (!Mouse.isButtonDown(i) && mouseDownLastTick[i]) {
                mouseDownLastTick[i] = false;
                Nexus.getNexus().getEventManager().post(new EventMouse.Up(i));
            }
        }
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isUsingItem()Z"))
    private boolean noSlowMixin(EntityPlayerSP instance) {
        Minecraft mc = Minecraft.getMinecraft();
        NoSlow noSlow = Nexus.getNexus().getModuleManager().getModuleByClass(NoSlow.class);
        if (noSlow.isEnabled()) {
            if (noSlow.slowMode.getValue() == NoSlow.sMode.NONE && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                return instance.isUsingItem();
            }
            if (noSlow.gappleMode.getValue() == NoSlow.gMode.NONE && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood) {
                return instance.isUsingItem();
            }
            if (mc.thePlayer.getCurrentEquippedItem() != null && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow)) {
                return false;
            }
        }
        return instance.isUsingItem();
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
    private void onUpdateWalkingPlayerPost(CallbackInfo ci) {
        posX = cachedX;
        posY = cachedY;
        posZ = cachedZ;
        onGround = cachedOnGround;

        rotationYaw = cachedRotationYaw;
        rotationPitch = cachedRotationPitch;
        Nexus.getInstance().getEventManager().post(new EventUpdate.Post(posX, posY, posZ, rotationYaw, rotationPitch, onGround));
    }
}
