package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import wwe.utils.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.player.*;
import wwe.modules.exploits.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import wwe.modules.movement.*;
import wwe.*;
import net.minecraft.util.*;

@Mixin(value = { EntityPlayerSP.class }, priority = 9999)
public class MixinEntityPlayerSP extends MixinEntityLivingBase
{
    omqH preMotion;
    
    public MixinEntityPlayerSP() {
        super();
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") }, cancellable = true)
    private void onUpdatePre(final CallbackInfo callbackInfo) {
        if (Wrapper.mc.world.isBlockLoaded(new BlockPos(Wrapper.mc.player.posX, 0.0, Wrapper.mc.player.posZ))) {
            final Iterator<qMIe> iterator = vlwv.hNxW.ijZl.iterator();
            while (iterator.hasNext()) {
                iterator.next().hPRw();
            }
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("RETURN") }, cancellable = true)
    private void onUpdatePost(final CallbackInfo callbackInfo) {
        if (Wrapper.mc.world.isBlockLoaded(new BlockPos(Wrapper.mc.player.posX, 0.0, Wrapper.mc.player.posZ)) && EntityPlayerSP.class.cast(this).isRiding()) {
            final omqH omqH = new omqH(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
            hSNE.Seqo(omqH);
            if (omqH.CEMd()) {
                callbackInfo.cancel();
            }
            hSNE.Seqo(new ILBH());
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void onUpdateWalkingPlayerPre(final CallbackInfo callbackInfo) {
        hSNE.Seqo(this.preMotion = new omqH(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
        if (this.preMotion.CEMd()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;posX:D"))
    public double onUpdateWalkingPlayerChangeX(final EntityPlayerSP entityPlayerSP) {
        return this.preMotion.VsNw;
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/util/math/AxisAlignedBB;minY:D"))
    public double onUpdateWalkingPlayerChangeY(final AxisAlignedBB axisAlignedBB) {
        return this.preMotion.XNxu;
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;posZ:D"))
    public double onUpdateWalkingPlayerChangeZ(final EntityPlayerSP entityPlayerSP) {
        return this.preMotion.lcNE;
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onGround:Z"))
    public boolean onUpdateWalkingPlayerChangeOnGround(final EntityPlayerSP entityPlayerSP) {
        return this.preMotion.VTLJ;
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationYaw:F"))
    public float onUpdateWalkingPlayerChangeYaw(final EntityPlayerSP entityPlayerSP) {
        return this.preMotion.prFd;
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationPitch:F"))
    public float onUpdateWalkingPlayerChangePitch(final EntityPlayerSP entityPlayerSP) {
        return this.preMotion.OjyD;
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") }, cancellable = true)
    public void onUpdateWalkingPlayerPost(final CallbackInfo callbackInfo) {
        hSNE.Seqo(new ILBH());
    }
    
    @Inject(method = { "sendChatMessage" }, at = { @At("HEAD") }, cancellable = true)
    public void IsendChatMessage(final String s, final CallbackInfo callbackInfo) {
        if (IRC.FclU && s.startsWith("@")) {
            if (vlwv.IJGi.dzdQ()) {
                vlwv.IJGi.FTQd("#WWEHackedClient", s.replace("@", ""));
                vlwv.IJGi.qQcR().add(new Jhbg(vlwv.IJGi.mxjl(true), s.replace("@", ""), Wrapper.mc.getSession().getUsername(), false));
                vlwv.IJGi.zdMA(true);
            }
            else {
                Wrapper.mc.player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.DARK_AQUA + "[IRC] " + TextFormatting.RESET + "Not connected to IRC?"));
            }
            callbackInfo.cancel();
        }
        else if (s.startsWith(DhFe.itOx)) {
            vlwv.YNBy.RRWX(s);
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer abstractClientPlayer, final MoverType moverType, final double n, final double n2, final double n3) {
        final sFUx sfUx = new sFUx(n, n2, n3, Wrapper.mc.player.onGround);
        hSNE.Seqo(sfUx);
        if (sfUx.CEMd()) {}
        super.move(moverType, sfUx.VsNw, sfUx.XNxu, sfUx.lcNE);
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double n, final double n2, final double n3, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<Freecam>)ZTVf.class).IuiN()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void portalChat(final EntityPlayerSP entityPlayerSP) {
        if (!vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).IuiN() || !vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).portalChat.OnGi()) {
            Minecraft.getMinecraft().player.closeScreen();
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void PortalChat(final Minecraft minecraft, final GuiScreen guiScreen) {
        if (!vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).IuiN() || !vlwv.hNxW.qHPF((Class<Portal>)SXfA.class).portalChat.OnGi()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }
    }
    
    @Override
    public boolean func_184613_cA() {
        return (!vlwv.hNxW.qHPF((Class<Elytra>)vkFD.class).IuiN() || !vlwv.hNxW.qHPF((Class<Elytra>)vkFD.class).mode.OnGi().equalsIgnoreCase("Packet")) && super.isElytraFlying();
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal = 2))
    public void onLivingUpdate(final EntityPlayerSP entityPlayerSP, final boolean sprinting) {
        if (Sprint.vqpo() && (EntityPlayerSP.class.cast(this).movementInput.moveForward != 0.0f || EntityPlayerSP.class.cast(this).movementInput.moveStrafe != 0.0f) && vlwv.hNxW.qHPF((Class<Sprint>)dbwp.class).IuiN() && vlwv.hNxW.qHPF((Class<Sprint>)dbwp.class).mode.OnGi().equalsIgnoreCase("Multi-Directional")) {
            entityPlayerSP.setSprinting(true);
        }
        else {
            entityPlayerSP.setSprinting(sprinting);
        }
    }
    
    @Override
    public void func_70664_aZ() {
        final double motionX = EntityPlayerSP.class.cast(this).motionX;
        final double motionZ = EntityPlayerSP.class.cast(this).motionZ;
        super.jump();
        if (vlwv.hNxW.qHPF((Class<Sprint>)dbwp.class).IuiN() && vlwv.hNxW.qHPF((Class<Sprint>)dbwp.class).mode.OnGi().equalsIgnoreCase("Multi-Directional")) {
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            float moveForward = movementInput.moveForward;
            float moveStrafe = movementInput.moveStrafe;
            float rotationYaw = Wrapper.mc.player.rotationYaw;
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    rotationYaw += ((moveForward > 0.0) ? -45 : 45);
                }
                else if (moveStrafe < 0.0) {
                    rotationYaw += ((moveForward > 0.0) ? 45 : -45);
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0) {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0) {
                    moveForward = -1.0f;
                }
            }
            if (moveStrafe > 0.0) {
                moveStrafe = 1.0f;
            }
            else if (moveStrafe < 0.0) {
                moveStrafe = -1.0f;
            }
            EntityPlayerSP.class.cast(this).motionX = motionX + (moveForward * 0.2 * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * 0.2 * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
            EntityPlayerSP.class.cast(this).motionZ = motionZ + (moveForward * 0.2 * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * 0.2 * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
        }
    }
}
