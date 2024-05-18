package wwe.modules.movement;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import wwe.*;

public class PacketFly extends qMIe
{
    public sbfc Xijd;
    public ModeValue mode;
    public NumberValue yDistance;
    public ModeValue yMode;
    public BooleanValue wait;
    public NumberValue waitTime;
    public NumberValue waitOffset;
    public NumberValue speed;
    public int AdRJ;
    public List<CPacketPlayer> NWrw;
    
    public PacketFly() {
        super("Packet Fly", "Allows you to fly via packets", 0, Category.MOVEMENT);
        this.Xijd = new sbfc();
        this.NWrw = new ArrayList<CPacketPlayer>();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Fast");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Fast", list);
        this.yDistance = new NumberValue("Y Distance", this, "Y distance server side", 999.0, 0.0, 999.0);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Y+");
        list2.add("Y-");
        this.yMode = new ModeValue("Y Mode", this, "Send a packet up or down", "Y-", list2);
        this.wait = new BooleanValue("Wait", this, "Waits and sends you down, bypass servers(normal only)", true);
        this.waitTime = new NumberValue("Wait Time", this, "The time before you are sent down(normal only)", 2.5, 0.0, 5.0);
        this.waitOffset = new NumberValue("Wait Offset", this, "How long before you can start moving after being sent down(normal only)", 5.0, 0.0, 10.0);
        this.speed = new NumberValue("Speed", this, "How fast you move", 1.0, 0.05, 1.0);
    }
    
    @oqrr
    public void oHiJ(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Packet Fly " + TextFormatting.DARK_GRAY + "Normal");
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (this.mode.OnGi().equalsIgnoreCase("Fast")) {
            this.owgW("Packet Fly " + TextFormatting.DARK_GRAY + "Fast");
        }
        if (Wrapper.mc.player.isDead) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Fast")) {
            if (this.AdRJ <= 0) {
                final double posX = Wrapper.mc.player.posX;
                final double posY = Wrapper.mc.player.posY;
                double doubleValue;
                if (this.yMode.OnGi().equalsIgnoreCase("Y+")) {
                    doubleValue = this.yDistance.OnGi();
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    doubleValue = -this.yDistance.OnGi();
                }
                final CPacketPlayer.Position position = new CPacketPlayer.Position(posX, posY + doubleValue, Wrapper.mc.player.posZ, Wrapper.mc.player.onGround);
                this.NWrw.add((CPacketPlayer)position);
                Wrapper.mc.player.connection.sendPacket((Packet)position);
                return;
            }
            Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
            double n2;
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                double n;
                if (Wrapper.mc.player.ticksExisted % 20 == 0) {
                    n = -0.03999999910593033;
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    n = 0.06199999898672104;
                }
                n2 = n;
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                n2 = -0.062;
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else {
                double n3;
                if (Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                    if (Wrapper.mc.player.ticksExisted % 20 == 0) {
                        n3 = -0.03999999910593033;
                        if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                            throw null;
                        }
                    }
                    else {
                        n3 = 0.0;
                        if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                            throw null;
                        }
                    }
                }
                else {
                    n3 = 0.0;
                }
                n2 = n3;
            }
            final double[] array = XWMI(this.speed.OnGi());
            if (!Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().grow(0.0625, 0.0, 0.0625)).isEmpty()) {
                if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() || Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
                    this.XNep(0.0, 0.0, 0.0);
                    int i = 0;
                    while (i <= 3) {
                        Wrapper.mc.player.setVelocity(0.0, n2 * i, 0.0);
                        this.XNep(0.0, n2 * i, 0.0);
                        ++i;
                        if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                            throw null;
                        }
                    }
                }
                return;
            }
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() || Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() || Wrapper.mc.gameSettings.keyBindForward.isKeyDown() || Wrapper.mc.gameSettings.keyBindBack.isKeyDown() || Wrapper.mc.gameSettings.keyBindRight.isKeyDown() || Wrapper.mc.gameSettings.keyBindLeft.isKeyDown()) {
                if (array[0] != 0.0 || n2 != 0.0 || array[1] != 0.0) {
                    if ((Wrapper.mc.player.movementInput.jump && (Wrapper.mc.player.moveStrafing != 0.0f || Wrapper.mc.player.moveForward != 0.0f)) || Wrapper.mc.player.movementInput.jump) {
                        Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
                        this.XNep(0.0, 0.0, 0.0);
                        int j = 0;
                        while (j <= 3) {
                            Wrapper.mc.player.setVelocity(0.0, n2 * j, 0.0);
                            this.XNep(0.0, n2 * j, 0.0);
                            ++j;
                            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                                throw null;
                            }
                        }
                        if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                            throw null;
                        }
                    }
                    else {
                        int k = 0;
                        while (k <= 2) {
                            Wrapper.mc.player.setVelocity(array[0] * k, n2 * k, array[1] * k);
                            this.XNep(array[0] * k, n2 * k, array[1] * k);
                            ++k;
                            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                                throw null;
                            }
                        }
                        if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                            throw null;
                        }
                    }
                }
            }
            else if (Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                final EntityPlayerSP player = Wrapper.mc.player;
                final double n4 = 0.0;
                double n5;
                if (Wrapper.mc.player.ticksExisted % 2 == 0) {
                    n5 = 0.03999999910593033;
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    n5 = -0.03999999910593033;
                }
                player.setVelocity(n4, n5, 0.0);
                final double n6 = 0.0;
                double n7;
                if (Wrapper.mc.player.ticksExisted % 2 == 0) {
                    n7 = 0.03999999910593033;
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    n7 = -0.03999999910593033;
                }
                this.XNep(n6, n7, 0.0);
            }
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            final double n8 = XWMI(this.speed.OnGi())[0];
            final double n9 = XWMI(this.speed.OnGi())[1];
            double n10;
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                n10 = 0.0624 * this.speed.OnGi();
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else {
                n10 = 0.0;
            }
            double n11;
            if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                n11 = 0.0624 * this.speed.OnGi();
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else {
                n11 = 0.0;
            }
            final double n12 = n10 - n11 * this.speed.OnGi();
            if (this.wait.OnGi()) {
                this.Xijd.gCgh();
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else {
                this.Xijd.BBwj();
            }
            Wrapper.mc.player.motionY = 0.0;
            Wrapper.mc.player.onGround = true;
            if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.Xijd.BBwj();
            }
            if (this.wait.OnGi() && this.Xijd.QOXG(this.waitTime.OnGi() * 1000.0) && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 0.0624 * this.speed.OnGi(), Wrapper.mc.player.posZ, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, false));
                final NetHandlerPlayClient connection = Wrapper.mc.player.connection;
                final double posX2 = Wrapper.mc.player.posX;
                final double posY2 = Wrapper.mc.player.posY;
                double doubleValue2;
                if (this.yMode.OnGi().equalsIgnoreCase("Y+")) {
                    doubleValue2 = this.yDistance.OnGi();
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    doubleValue2 = -this.yDistance.OnGi();
                }
                connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX2, posY2 + doubleValue2, Wrapper.mc.player.posZ, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, true));
                this.Xijd.BBwj();
                return;
            }
            if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() || Wrapper.mc.gameSettings.keyBindBack.isKeyDown() || Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() || Wrapper.mc.gameSettings.keyBindRight.isKeyDown() || Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() || Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                if (this.wait.OnGi() && this.Xijd.QOXG(this.waitTime.OnGi() * 1000.0 - this.waitOffset.OnGi() * 100.0)) {
                    return;
                }
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Wrapper.mc.player.posX + n8, Wrapper.mc.player.posY + n12, Wrapper.mc.player.posZ + n9, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, false));
                final NetHandlerPlayClient connection2 = Wrapper.mc.player.connection;
                final double n13 = Wrapper.mc.player.posX + n8;
                final double posY3 = Wrapper.mc.player.posY;
                double doubleValue3;
                if (this.yMode.OnGi().equalsIgnoreCase("Y+")) {
                    doubleValue3 = this.yDistance.OnGi();
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    doubleValue3 = -this.yDistance.OnGi();
                }
                connection2.sendPacket((Packet)new CPacketPlayer.PositionRotation(n13, posY3 + doubleValue3, Wrapper.mc.player.posZ + n9, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, true));
            }
        }
    }
    
    @oqrr
    public void ikgW(final uqzS uqzS) {
        if (this.mode.OnGi().equalsIgnoreCase("Fast")) {
            if (uqzS.MYlK() instanceof CPacketPlayer && !(uqzS.MYlK() instanceof CPacketPlayer.Position)) {
                uqzS.Ggez(true);
            }
            if (uqzS.MYlK() instanceof CPacketPlayer) {
                final CPacketPlayer cPacketPlayer = (CPacketPlayer)uqzS.MYlK();
                if (this.NWrw.contains(cPacketPlayer)) {
                    this.NWrw.remove(cPacketPlayer);
                    return;
                }
                uqzS.Ggez(true);
            }
        }
    }
    
    @oqrr
    public void xzcL(final LqJY lqJY) {
        if (this.mode.OnGi().equalsIgnoreCase("Fast") && lqJY.MYlK() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)lqJY.MYlK();
            if (Wrapper.mc.player != null && Wrapper.mc.player.isEntityAlive() && Wrapper.mc.world.isBlockLoaded(new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ)) && !(Wrapper.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.AdRJ <= 0) {
                    this.AdRJ = sPacketPlayerPosLook.getTeleportId();
                    if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    lqJY.Ggez(true);
                }
            }
        }
    }
    
    @oqrr
    public void TOJH(final sFUx sfUx) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            sfUx.VsNw = 0.0;
            sfUx.XNxu = 0.0;
            sfUx.lcNE = 0.0;
        }
    }
    
    @Override
    public void LPxx() {
        this.AdRJ = 0;
        this.NWrw.clear();
        if (Wrapper.mc.world != null && this.mode.OnGi().equalsIgnoreCase("Fast")) {
            final double posX = Wrapper.mc.player.posX;
            final double posY = Wrapper.mc.player.posY;
            double doubleValue;
            if (this.yMode.OnGi().equalsIgnoreCase("Y+")) {
                doubleValue = this.yDistance.OnGi();
                if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                    throw null;
                }
            }
            else {
                doubleValue = -this.yDistance.OnGi();
            }
            final CPacketPlayer.Position position = new CPacketPlayer.Position(posX, posY + doubleValue, Wrapper.mc.player.posZ, Wrapper.mc.player.onGround);
            this.NWrw.add((CPacketPlayer)position);
            Wrapper.mc.player.connection.sendPacket((Packet)position);
        }
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    public void XNep(final double n, final double n2, final double n3) {
        final CPacketPlayer.Position position = new CPacketPlayer.Position(Wrapper.mc.player.posX + n, Wrapper.mc.player.posY + n2, Wrapper.mc.player.posZ + n3, Wrapper.mc.player.onGround);
        this.NWrw.add((CPacketPlayer)position);
        Wrapper.mc.player.connection.sendPacket((Packet)position);
        final double n4 = Wrapper.mc.player.posX + n;
        final double posY = Wrapper.mc.player.posY;
        double doubleValue;
        if (this.yMode.OnGi().equalsIgnoreCase("Y+")) {
            doubleValue = this.yDistance.OnGi();
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else {
            doubleValue = -this.yDistance.OnGi();
        }
        final CPacketPlayer.Position position2 = new CPacketPlayer.Position(n4, posY + doubleValue, Wrapper.mc.player.posZ + n3, Wrapper.mc.player.onGround);
        this.NWrw.add((CPacketPlayer)position2);
        Wrapper.mc.player.connection.sendPacket((Packet)position2);
        ++this.AdRJ;
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ - 1));
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ));
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ + 1));
    }
    
    public static double[] XWMI(final double n) {
        float n2 = 0.0f;
        float n3 = 0.0f;
        final double n4 = 2.7999100260353087 * n;
        final float sin = MathHelper.sin(Wrapper.mc.player.rotationYaw * 3.1415927f / 180.0f);
        final float cos = MathHelper.cos(Wrapper.mc.player.rotationYaw * 3.1415927f / 180.0f);
        if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 += 0.1f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 -= 0.1f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n3 += 0.1f;
        }
        if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n3 -= 0.1f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 += 0.0624f;
            n3 += 0.0624f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 += 0.0624f;
            n3 -= 0.0624f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 -= 0.0624f;
            n3 += 0.0624f;
            if (PacketFly.\u200d\u200a\u2006\u2003\u2007\u2003\u2003\u2007\u2004) {
                throw null;
            }
        }
        else if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindForward) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindBack) && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindLeft) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindRight)) {
            n2 -= 0.0624f;
            n3 -= 0.0624f;
        }
        return new double[] { (n3 * cos - n2 * sin) * n4, (n2 * cos + n3 * sin) * n4 };
    }
}
