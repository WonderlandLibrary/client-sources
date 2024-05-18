package wwe.modules.movement;

import wwe.modules.*;
import java.util.*;
import wwe.utils.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import java.math.*;
import wwe.*;
import net.minecraft.network.play.client.*;

public class Speed extends qMIe
{
    private int mLMb;
    private double BEIG;
    private double dAcM;
    public int AdRJ;
    public List<CPacketPlayer> NWrw;
    public boolean XNTM;
    public ModeValue mode;
    public BooleanValue strafejump;
    public NumberValue timer;
    public NumberValue modifier;
    
    public Speed() {
        super("Speed", "Makes you go faster", 47, Category.MOVEMENT);
        this.NWrw = new ArrayList<CPacketPlayer>();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("NCP");
        list.add("Strafe");
        list.add("Bhop");
        list.add("Fast hop");
        list.add("Hop");
        list.add("Tomato");
        list.add("Packet");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Fast hop", list);
        this.strafejump = new BooleanValue("Strafe jump", this, "Auto jumps with strafe", true);
        this.timer = new NumberValue("Timer", this, "Custom timer if the speed mode uses a timer", 1.08, 1.0, 1.3);
        this.modifier = new NumberValue("Modifier", this, "Modify the speed of each mode, 1 = default", 1.0, 0.5, 1.2);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        final double n = Wrapper.mc.player.posX - Wrapper.mc.player.prevPosX;
        final double n2 = Wrapper.mc.player.posZ - Wrapper.mc.player.prevPosZ;
        this.dAcM = Math.sqrt(n * n + n2 * n2);
        if (this.mode.OnGi().equalsIgnoreCase("NCP")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "NCP");
            if (Wrapper.mc.player.isSneaking() || Wrapper.mc.player.isInWater() || Wrapper.mc.player.isInLava()) {
                return;
            }
            if ((Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) && !Wrapper.mc.player.collidedHorizontally && Wrapper.mc.player.ticksExisted % 2 != 0) {
                omqH.XNxu = Wrapper.mc.player.posY + 0.4;
            }
            vlwv.Axho = this.timer.OnGi().floatValue();
            double n3;
            if (Wrapper.mc.player.ticksExisted % 2 == 0) {
                n3 = 2.1;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else {
                n3 = 1.3;
            }
            this.dyrL(this.BEIG = Math.max(n3, eabb()), 0.15f);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Strafe")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Strafe");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Low hop")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Low hop");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Bhop")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Bhop");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Fast hop")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Fast hop");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Hop")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Hop");
            if ((Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) && Wrapper.mc.player.onGround) {
                Wrapper.mc.player.jump();
                ++this.mLMb;
                if (this.mLMb == 1 || this.mLMb == 2 || this.mLMb == 3 || this.mLMb == 6) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionX *= 1.0;
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionZ *= 1.0;
                }
                if (this.mLMb == 4 || this.mLMb == 5 || this.mLMb == 7) {
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionX *= 1.1;
                    final EntityPlayerSP player4 = Wrapper.mc.player;
                    player4.motionZ *= 1.1;
                }
                if (this.mLMb == 7) {
                    this.mLMb = 0;
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Tomato")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Tomato");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.owgW("Speed " + TextFormatting.DARK_GRAY + "Packet");
            boolean xntm;
            if (!Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().grow(0.0625, 0.0, 0.0625)).isEmpty()) {
                xntm = true;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else {
                xntm = false;
            }
            this.XNTM = xntm;
            if (this.XNTM) {
                vlwv.Axho = 1.0f;
                this.AdRJ = 0;
                return;
            }
            if (this.AdRJ <= 0) {
                vlwv.Axho = 1.0f;
                final CPacketPlayer.Position position = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 999.0, Wrapper.mc.player.posZ, Wrapper.mc.player.onGround);
                this.NWrw.add((CPacketPlayer)position);
                Wrapper.mc.player.connection.sendPacket((Packet)position);
                return;
            }
            Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
            final double[] array = LqJk.XWMI((double)this.modifier.OnGi());
            if ((Wrapper.mc.gameSettings.keyBindForward.isKeyDown() || Wrapper.mc.gameSettings.keyBindBack.isKeyDown() || Wrapper.mc.gameSettings.keyBindRight.isKeyDown() || Wrapper.mc.gameSettings.keyBindLeft.isKeyDown()) && (array[0] != 0.0 || array[1] != 0.0)) {
                int i = 0;
                while (i < 3) {
                    Wrapper.mc.player.setVelocity(array[0] * i, 0.0, array[1] * i);
                    this.XNep(array[0] * i, 0.0, array[1] * i);
                    ++i;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
            }
        }
    }
    
    @oqrr
    public void ffoK(final sFUx sfUx) {
        if (this.mode.OnGi().equalsIgnoreCase("Packet") && !this.XNTM) {
            sfUx.XNxu = 0.0;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Strafe")) {
            if (Wrapper.mc.player.isSneaking() || Wrapper.mc.player.isInWater() || Wrapper.mc.player.isInLava()) {
                return;
            }
            if (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) {
                if (Wrapper.mc.player.onGround && this.strafejump.OnGi()) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    final double n = 0.4;
                    player.motionY = n;
                    sfUx.XNxu = n;
                }
                this.BEIG = eabb();
                this.PQas(sfUx, 1.0f);
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Bhop")) {
            if (rhii(Wrapper.mc.player.posY - (int)Wrapper.mc.player.posY, 3) == rhii(0.138, 3)) {
                final EntityPlayerSP player2 = Wrapper.mc.player;
                player2.motionY -= 0.08;
                sfUx.XNxu -= 0.09316090325960147;
                final EntityPlayerSP player3 = Wrapper.mc.player;
                player3.posY -= 0.09316090325960147;
            }
            if (this.mLMb == 1 && (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f)) {
                this.mLMb = 2;
                this.BEIG = 1.35 * eabb() - 0.01;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else if (this.mLMb == 2 && Wrapper.mc.player.onGround) {
                this.mLMb = 3;
                if (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) {
                    Wrapper.mc.player.motionY = 0.4;
                    sfUx.XNxu = 0.4;
                    this.BEIG *= 2.149;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
            }
            else if (this.mLMb == 3) {
                this.mLMb = 4;
                this.BEIG = this.dAcM - 0.66 * (this.dAcM - eabb());
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else {
                if (Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().offset(0.0, Wrapper.mc.player.motionY, 0.0)).size() > 0 || Wrapper.mc.player.collidedVertically) {
                    this.mLMb = 1;
                }
                this.BEIG = this.dAcM - this.dAcM / 159.0;
            }
            this.BEIG = Math.max(this.BEIG, eabb());
            this.PQas(sfUx, 0.985f);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Fast hop")) {
            if (rhii(Wrapper.mc.player.posY - (int)Wrapper.mc.player.posY, 3) == rhii(0.138, 3)) {
                final EntityPlayerSP player4 = Wrapper.mc.player;
                player4.motionY -= 0.08;
                sfUx.XNxu -= 0.09316090325960147;
                final EntityPlayerSP player5 = Wrapper.mc.player;
                player5.posY -= 0.09316090325960147;
            }
            if (this.mLMb == 1 && (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f)) {
                this.mLMb = 2;
                this.BEIG = 1.35 * eabb() - 0.01;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else if (this.mLMb == 2 && Wrapper.mc.player.onGround) {
                this.mLMb = 3;
                if (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) {
                    Wrapper.mc.player.motionY = 0.4;
                    sfUx.XNxu = 0.4;
                    this.BEIG *= 2.149;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
            }
            else if (this.mLMb == 3) {
                this.mLMb = 4;
                this.BEIG = this.dAcM - 0.66 * (this.dAcM - eabb());
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else {
                if (Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().offset(0.0, Wrapper.mc.player.motionY, 0.0)).size() > 0 || Wrapper.mc.player.collidedVertically) {
                    this.mLMb = 1;
                }
                this.BEIG = this.dAcM - this.dAcM / 159.0;
            }
            this.BEIG = Math.max(this.BEIG, eabb());
            this.PQas(sfUx, 0.973f);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Tomato")) {
            if (Wrapper.mc.player.isSneaking() || Wrapper.mc.player.isInWater() || Wrapper.mc.player.isInLava()) {
                return;
            }
            if (Wrapper.mc.player.movementInput.moveForward != 0.0f || Wrapper.mc.player.movementInput.moveStrafe != 0.0f) {
                vlwv.Axho = 1.0f;
                if (rhii(Wrapper.mc.player.posY - (int)Wrapper.mc.player.posY, 3) == rhii(0.138, 3)) {
                    final EntityPlayerSP player6 = Wrapper.mc.player;
                    player6.motionY -= 0.08;
                    sfUx.XNxu -= 0.09316090325960147;
                    final EntityPlayerSP player7 = Wrapper.mc.player;
                    player7.posY -= 0.09316090325960147;
                }
                if (this.mLMb == 1) {
                    this.mLMb = 2;
                    this.BEIG = 1.38 * eabb() - 0.01;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (this.mLMb == 2 && Wrapper.mc.player.onGround) {
                    this.mLMb = 3;
                    Wrapper.mc.player.motionY = 0.399399995803833;
                    sfUx.XNxu = 0.399399995803833;
                    this.BEIG *= 2.149;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (this.mLMb == 3) {
                    this.mLMb = 4;
                    this.BEIG = this.dAcM - 0.66 * (this.dAcM - eabb());
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else {
                    if (Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().offset(0.0, Wrapper.mc.player.motionY, 0.0)).size() > 0 || Wrapper.mc.player.collidedHorizontally) {
                        this.mLMb = 1;
                    }
                    this.BEIG = this.dAcM - this.dAcM / 159.0;
                }
                this.BEIG = Math.max(this.BEIG, eabb());
                this.PQas(sfUx, 1.0f);
            }
        }
    }
    
    @oqrr
    public void ikgW(final uqzS uqzS) {
        if (!this.XNTM && this.mode.OnGi().equalsIgnoreCase("Packet")) {
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
        if (!this.XNTM && this.mode.OnGi().equalsIgnoreCase("Packet") && lqJY.MYlK() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)lqJY.MYlK();
            if (Wrapper.mc.player != null && Wrapper.mc.player.isEntityAlive() && Wrapper.mc.world.isBlockLoaded(new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ)) && !(Wrapper.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.AdRJ <= 0) {
                    this.AdRJ = sPacketPlayerPosLook.getTeleportId();
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else {
                    lqJY.Ggez(true);
                }
            }
        }
    }
    
    public static double eabb() {
        double n = 0.2873;
        if (Wrapper.mc.player.isPotionActive(Potion.getPotionById(1))) {
            n *= 1.0 + 0.2 * (Wrapper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() + 1);
        }
        return n;
    }
    
    public void PQas(final sFUx sfUx, float n) {
        n *= this.modifier.OnGi().floatValue();
        final MovementInput movementInput = Wrapper.mc.player.movementInput;
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = Wrapper.mc.player.rotationYaw;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            sfUx.VsNw = 0.0;
            sfUx.lcNE = 0.0;
            if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                throw null;
            }
        }
        else {
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    final float n2 = rotationYaw;
                    int n3;
                    if (moveForward > 0.0) {
                        n3 = -45;
                        if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                            throw null;
                        }
                    }
                    else {
                        n3 = 45;
                    }
                    rotationYaw = n2 + n3;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (moveStrafe < 0.0) {
                    final float n4 = rotationYaw;
                    int n5;
                    if (moveForward > 0.0) {
                        n5 = 45;
                        if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                            throw null;
                        }
                    }
                    else {
                        n5 = -45;
                    }
                    rotationYaw = n4 + n5;
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0) {
                    moveForward = n;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (moveForward < 0.0) {
                    moveForward = -n;
                }
            }
            if (moveStrafe > 0.0) {
                moveStrafe = n;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else if (moveStrafe < 0.0) {
                moveStrafe = -n;
            }
            sfUx.VsNw = moveForward * this.BEIG * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * this.BEIG * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            sfUx.lcNE = moveForward * this.BEIG * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * this.BEIG * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    public void dyrL(final double n, float n2) {
        n2 *= (float)(Object)this.modifier.OnGi();
        final MovementInput movementInput = Wrapper.mc.player.movementInput;
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = Wrapper.mc.player.rotationYaw;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            Wrapper.mc.player.motionX = 0.0;
            Wrapper.mc.player.motionZ = 0.0;
            if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                throw null;
            }
        }
        else {
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    final float n3 = rotationYaw;
                    int n4;
                    if (moveForward > 0.0) {
                        n4 = -45;
                        if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                            throw null;
                        }
                    }
                    else {
                        n4 = 45;
                    }
                    rotationYaw = n3 + n4;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (moveStrafe < 0.0) {
                    final float n5 = rotationYaw;
                    int n6;
                    if (moveForward > 0.0) {
                        n6 = 45;
                        if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                            throw null;
                        }
                    }
                    else {
                        n6 = -45;
                    }
                    rotationYaw = n5 + n6;
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0) {
                    moveForward = n2;
                    if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                        throw null;
                    }
                }
                else if (moveForward < 0.0) {
                    moveForward = -n2;
                }
            }
            if (moveStrafe > 0.0) {
                moveStrafe = n2;
                if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                    throw null;
                }
            }
            else if (moveStrafe < 0.0) {
                moveStrafe = -n2;
            }
            Wrapper.mc.player.motionX = moveForward * this.BEIG * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * this.BEIG * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            Wrapper.mc.player.motionZ = moveForward * this.BEIG * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * this.BEIG * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    public static double rhii(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    @Override
    public void LPxx() {
        vlwv.Axho = 1.0f;
        this.mLMb = 1;
        double eabb;
        if (Wrapper.mc.player == null) {
            eabb = 0.2873;
            if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                throw null;
            }
        }
        else {
            eabb = eabb();
        }
        this.BEIG = eabb;
        this.AdRJ = 0;
        this.NWrw.clear();
        if (this.mode.OnGi().equalsIgnoreCase("Packet") && Wrapper.mc.world != null) {
            final CPacketPlayer.Position position = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 999.0, Wrapper.mc.player.posZ, Wrapper.mc.player.onGround);
            this.NWrw.add((CPacketPlayer)position);
            Wrapper.mc.player.connection.sendPacket((Packet)position);
        }
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        vlwv.Axho = 1.0f;
        this.mLMb = 1;
        double eabb;
        if (Wrapper.mc.player == null) {
            eabb = 0.2873;
            if (Speed.\u200f\u2001\u200c\u200b\u200e\u2001\u2006) {
                throw null;
            }
        }
        else {
            eabb = eabb();
        }
        this.BEIG = eabb;
        hSNE.kduK(this);
        super.qBmy();
    }
    
    public void XNep(final double n, final double n2, final double n3) {
        final CPacketPlayer.Position position = new CPacketPlayer.Position(Wrapper.mc.player.posX + n, Wrapper.mc.player.posY + n2, Wrapper.mc.player.posZ + n3, Wrapper.mc.player.onGround);
        this.NWrw.add((CPacketPlayer)position);
        Wrapper.mc.player.connection.sendPacket((Packet)position);
        final CPacketPlayer.Position position2 = new CPacketPlayer.Position(Wrapper.mc.player.posX + n, Wrapper.mc.player.posY - 999.0, Wrapper.mc.player.posZ + n3, Wrapper.mc.player.onGround);
        this.NWrw.add((CPacketPlayer)position2);
        Wrapper.mc.player.connection.sendPacket((Packet)position2);
        ++this.AdRJ;
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ - 1));
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ));
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.AdRJ + 1));
    }
}
