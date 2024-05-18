package wwe.modules.player;

import net.minecraft.entity.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import wwe.*;

public class Freecam extends qMIe
{
    private double pecf;
    private double lcOQ;
    private double tGVQ;
    private float TgZu;
    private float iGUX;
    private double PdgN;
    private double sOEX;
    private double ncil;
    private float Dcvz;
    private float tLcg;
    private EntityOtherPlayerMP iAxr;
    private boolean pNWC;
    private Entity vxgH;
    public NumberValue speed;
    public BooleanValue caveRender;
    public BooleanValue cancelPackets;
    
    public Freecam() {
        super("Freecam", "Allows you to fly out of your body", 22, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        this.speed = new NumberValue("Speed", this, "Speed you move when using freecam", 2.0, 0.0, 5.0);
        this.caveRender = new BooleanValue("Cave Render", this, "Renders caves when using freecam", false);
        this.cancelPackets = new BooleanValue("Cancel Packets", this, "Cancel movement packets, wont able to freely move without this", true);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (!Wrapper.mc.player.onGround) {
            Wrapper.mc.player.motionY = -0.2;
        }
        if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
            Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY + this.speed.OnGi(), Wrapper.mc.player.posZ);
        }
        if (Wrapper.mc.player.isSneaking()) {
            Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY - this.speed.OnGi(), Wrapper.mc.player.posZ);
        }
        if (Wrapper.mc.player.moveForward != 0.0f || Wrapper.mc.player.moveStrafing != 0.0f) {
            this.SkaT(this.speed.OnGi());
            if (Freecam.\u200c\u2007) {
                throw null;
            }
        }
        else {
            Wrapper.mc.player.motionX = 0.0;
            Wrapper.mc.player.motionZ = 0.0;
        }
        Wrapper.mc.player.onGround = true;
        Wrapper.mc.player.motionY = 0.0;
        if (Wrapper.mc.player.isRiding()) {
            Wrapper.mc.player.getRidingEntity().motionY = 0.0;
        }
        Wrapper.mc.player.noClip = true;
        Wrapper.mc.player.onGround = false;
        Wrapper.mc.player.fallDistance = 0.0f;
    }
    
    @oqrr
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        if (Wrapper.mc.world != null && Wrapper.mc.player != null) {
            boolean pnwc;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                pnwc = true;
                if (Freecam.\u200c\u2007) {
                    throw null;
                }
            }
            else {
                pnwc = false;
            }
            this.pNWC = pnwc;
            if (Wrapper.mc.player.getRidingEntity() == null) {
                this.pecf = Wrapper.mc.player.posX;
                this.lcOQ = Wrapper.mc.player.posY;
                this.tGVQ = Wrapper.mc.player.posZ;
                if (Freecam.\u200c\u2007) {
                    throw null;
                }
            }
            else {
                this.vxgH = Wrapper.mc.player.getRidingEntity();
                Wrapper.mc.player.dismountRidingEntity();
            }
            this.TgZu = Wrapper.mc.player.rotationPitch;
            this.iGUX = Wrapper.mc.player.rotationYaw;
            (this.iAxr = new EntityOtherPlayerMP((World)Wrapper.mc.world, Wrapper.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Wrapper.mc.player);
            this.iAxr.rotationYawHead = Wrapper.mc.player.rotationYawHead;
            this.iAxr.inventory = Wrapper.mc.player.inventory;
            Wrapper.mc.world.addEntityToWorld(-100, (Entity)this.iAxr);
            Wrapper.mc.player.noClip = true;
        }
        super.LPxx();
    }
    
    @oqrr
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        if (Wrapper.mc.player != null) {
            Wrapper.mc.player.setPositionAndRotation(this.pecf, this.lcOQ, this.tGVQ, this.iGUX, this.TgZu);
            Wrapper.mc.world.removeEntityFromWorld(-100);
            this.iAxr = null;
            final double pecf = 0.0;
            this.tGVQ = pecf;
            this.lcOQ = pecf;
            this.pecf = pecf;
            final float n = 0.0f;
            this.iGUX = n;
            this.TgZu = n;
            Wrapper.mc.player.noClip = false;
            final EntityPlayerSP player = Wrapper.mc.player;
            final EntityPlayerSP player2 = Wrapper.mc.player;
            final EntityPlayerSP player3 = Wrapper.mc.player;
            final double motionX = 0.0;
            player3.motionZ = motionX;
            player2.motionY = motionX;
            player.motionX = motionX;
            if (this.pNWC) {
                Wrapper.mc.player.startRiding(this.vxgH, true);
            }
        }
        Wrapper.mc.renderGlobal.loadRenderers();
        super.qBmy();
    }
    
    @oqrr
    public void gbjy(final uqzS uqzS) {
        if (this.cancelPackets.OnGi() && (uqzS.MYlK() instanceof CPacketPlayer || uqzS.MYlK() instanceof CPacketInput)) {
            uqzS.Ggez(true);
        }
    }
    
    @oqrr
    public void DwMG(final LqJY lqJY) {
        if (lqJY.MYlK() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)lqJY.MYlK();
            this.PdgN = sPacketPlayerPosLook.getX();
            this.sOEX = sPacketPlayerPosLook.getY();
            this.ncil = sPacketPlayerPosLook.getZ();
            this.Dcvz = sPacketPlayerPosLook.getPitch();
            this.tLcg = sPacketPlayerPosLook.getYaw();
        }
    }
    
    private void SkaT(final double n) {
        if (Wrapper.mc.player != null) {
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            double n2 = movementInput.moveForward;
            double n3 = movementInput.moveStrafe;
            float rotationYaw = Wrapper.mc.player.rotationYaw;
            if (n2 == 0.0 && n3 == 0.0) {
                Wrapper.mc.player.motionX = 0.0;
                Wrapper.mc.player.motionZ = 0.0;
                if (Freecam.\u200c\u2007) {
                    throw null;
                }
            }
            else {
                if (n2 != 0.0) {
                    if (n3 > 0.0) {
                        final float n4 = rotationYaw;
                        int n5;
                        if (n2 > 0.0) {
                            n5 = -45;
                            if (Freecam.\u200c\u2007) {
                                throw null;
                            }
                        }
                        else {
                            n5 = 45;
                        }
                        rotationYaw = n4 + n5;
                        if (Freecam.\u200c\u2007) {
                            throw null;
                        }
                    }
                    else if (n3 < 0.0) {
                        final float n6 = rotationYaw;
                        int n7;
                        if (n2 > 0.0) {
                            n7 = 45;
                            if (Freecam.\u200c\u2007) {
                                throw null;
                            }
                        }
                        else {
                            n7 = -45;
                        }
                        rotationYaw = n6 + n7;
                    }
                    n3 = 0.0;
                    if (n2 > 0.0) {
                        n2 = 1.0;
                        if (Freecam.\u200c\u2007) {
                            throw null;
                        }
                    }
                    else if (n2 < 0.0) {
                        n2 = -1.0;
                    }
                }
                Wrapper.mc.player.motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
                Wrapper.mc.player.motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
            }
        }
    }
    
    @oqrr
    public void aHQC(final sFUx sfUx) {
        Wrapper.mc.player.noClip = true;
    }
}
