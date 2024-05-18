package wwe.modules.combat;

import net.minecraft.entity.item.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.*;
import wwe.modules.exploits.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.network.*;
import java.awt.*;
import wwe.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;

public class CrystalAura extends qMIe
{
    private BlockPos mgWw;
    public EntityEnderCrystal INMa;
    public ArrayList<EntityEnderCrystal> umql;
    public sbfc Xijd;
    public sbfc HSgN;
    public ModeValue mode;
    public ModeValue priority;
    public ModeValue eatting;
    public ModeValue event;
    public ModeValue rotationType;
    public NumberValue attackDelay;
    public BooleanValue place;
    public BooleanValue placeBreak;
    public BooleanValue onePlace;
    public NumberValue placeDelay;
    public BooleanValue esp;
    public NumberValue yDifference;
    public NumberValue range;
    public NumberValue hitchance;
    
    public CrystalAura() {
        super("Crystal Aura", "Automatically attacks crystals", 23, Category.COMBAT);
        this.Xijd = new sbfc();
        this.HSgN = new sbfc();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Packet");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Closest");
        list2.add("Random");
        this.priority = new ModeValue("Priority", this, "Which target to hit", "Closest", list2);
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("Stop");
        list3.add("Stop eatting");
        list3.add("Ignore");
        this.eatting = new ModeValue("Eatting", this, "Eatting modes", "Stop", list3);
        final ArrayList<String> list4 = new ArrayList<String>();
        list4.add("Pre");
        list4.add("Post");
        this.event = new ModeValue("Event", this, "What event to attack the crystal on", "Pre", list4);
        final ArrayList<String> list5 = new ArrayList<String>();
        list5.add("Snap");
        list5.add("Stay");
        this.rotationType = new ModeValue("Rotation Type", this, "What type of rotation to do", "Snap", list5);
        this.attackDelay = new NumberValue("Attack Delay", this, "Delay before attack next crystal", 0.1, 0.0, 1.0);
        this.place = new BooleanValue("Place", this, "Place crystals", false);
        this.placeBreak = new BooleanValue("Place Break", this, "Doesnt attempt to attack a crystal on the same tick it placed one", true);
        this.onePlace = new BooleanValue("One Place", this, "Doesnt place a crystal if theres already one", true);
        this.placeDelay = new NumberValue("Place Delay", this, "Delay before you place next crystal", 0.1, 0.0, 2.0);
        this.esp = new BooleanValue("ESP", this, "Outlines crystals your going hit", false);
        this.yDifference = new NumberValue("Y Difference", this, "Y distance to attack the crystal", 0.0, 0.0, 6.0);
        this.range = new NumberValue("Range", this, "Range to attack the crystal", 4.5, 0.0, 6.0);
        this.hitchance = new NumberValue("Hit chance", this, "What percentage of hits actually hit", 100.0, 0.0, 100.0);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Crystal Aura" + TextFormatting.DARK_GRAY + " Normal");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.owgW("Crystal Aura" + TextFormatting.DARK_GRAY + " Packet");
        }
        if (this.eatting.OnGi().equalsIgnoreCase("Stop") && Wrapper.mc.player.isHandActive()) {
            return;
        }
        this.INMa = null;
        if (this.place.OnGi()) {
            this.mgWw = null;
            if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal)) {
                this.HSgN.BBwj();
                if (CrystalAura.\u2001\u200e\u200e\u2003) {
                    throw null;
                }
            }
            else {
                for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        if (CrystalAura.\u2001\u200e\u200e\u2003) {
                            throw null;
                        }
                        continue;
                    }
                    else if (Wrapper.mc.player.getDistance(entity) <= this.range.OnGi()) {
                        if (CrystalAura.\u2001\u200e\u200e\u2003) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (CrystalAura.\u2001\u200e\u200e\u2003) {
                            throw null;
                        }
                        continue;
                    }
                }
                this.HSgN.gCgh();
                final List<BlockPos> gtui = this.gTUI();
                final ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
                for (final EntityPlayer entityPlayer : Wrapper.mc.world.playerEntities) {
                    if (!(list instanceof EntityPlayerSP)) {
                        if (vlwv.Zqnd().UsVJ(entityPlayer.getName())) {
                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            list.add(entityPlayer);
                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                throw null;
                            }
                            continue;
                        }
                    }
                }
                BlockPos mgWw = null;
                double n = 0.5;
                for (final EntityPlayer entityPlayer2 : list) {
                    if (!(entityPlayer2 instanceof EntityPlayerSP)) {
                        if (entityPlayer2.getHealth() <= 0.0f) {
                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            for (final BlockPos blockPos : gtui) {
                                if (entityPlayer2.getDistanceSq(blockPos) >= 169.0) {
                                    if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                        throw null;
                                    }
                                    continue;
                                }
                                else {
                                    final double n2 = tnyA(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)entityPlayer2);
                                    if (n2 > n) {
                                        final double n3 = tnyA(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)Wrapper.mc.player);
                                        if (n3 > n2 && n2 >= ((EntityLivingBase)entityPlayer2).getHealth()) {
                                            continue;
                                        }
                                        if (n3 - 0.5 > Wrapper.mc.player.getHealth()) {
                                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                                throw null;
                                            }
                                            continue;
                                        }
                                        else {
                                            n = n2;
                                            mgWw = blockPos;
                                        }
                                    }
                                    if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                        throw null;
                                    }
                                    continue;
                                }
                            }
                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                throw null;
                            }
                            continue;
                        }
                    }
                }
                if (n == 0.5) {
                    this.mgWw = null;
                    if (CrystalAura.\u2001\u200e\u200e\u2003) {
                        throw null;
                    }
                }
                else {
                    this.mgWw = mgWw;
                    if (this.rotationType.OnGi().equalsIgnoreCase("Stay")) {
                        this.TpPT(this.mgWw.getX() + 0.5, this.mgWw.getY() - 0.5, this.mgWw.getZ() + 0.5, (EntityPlayer)Wrapper.mc.player, omqH);
                    }
                    if (this.HSgN.QOXG(this.placeDelay.OnGi() * 1000.0)) {
                        if (this.rotationType.OnGi().equalsIgnoreCase("Snap")) {
                            this.TpPT(this.mgWw.getX() + 0.5, this.mgWw.getY() - 0.5, this.mgWw.getZ() + 0.5, (EntityPlayer)Wrapper.mc.player, omqH);
                        }
                        if (this.event.OnGi().equalsIgnoreCase("Pre")) {
                            if (this.mgWw != null && this.eatting.OnGi().equalsIgnoreCase("Stop eatting") && Wrapper.mc.player.isHandActive()) {
                                Wrapper.mc.player.stopActiveHand();
                                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                            }
                            final RayTraceResult rayTraceBlocks = Wrapper.mc.world.rayTraceBlocks(new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ), new Vec3d(this.mgWw.getX() + 0.5, this.mgWw.getY() - 0.5, this.mgWw.getZ() + 0.5));
                            final NetHandlerPlayClient connection = Wrapper.mc.player.connection;
                            final BlockPos mgWw2 = this.mgWw;
                            EnumFacing enumFacing;
                            if (rayTraceBlocks == null || rayTraceBlocks.sideHit == null) {
                                enumFacing = EnumFacing.UP;
                                if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                    throw null;
                                }
                            }
                            else {
                                enumFacing = rayTraceBlocks.sideHit;
                            }
                            connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(mgWw2, enumFacing, OffHand.txLa(), 0.0f, 0.0f, 0.0f));
                            OffHand.LInL();
                            this.HSgN.BBwj();
                        }
                        if (this.placeBreak.OnGi()) {
                            return;
                        }
                    }
                }
            }
        }
        if (this.priority.OnGi().equalsIgnoreCase("Closest")) {
            double doubleValue = this.range.OnGi();
            for (final Entity entity2 : Wrapper.mc.world.loadedEntityList) {
                if (entity2 instanceof EntityEnderCrystal && Wrapper.mc.player.getDistance(entity2) < this.range.OnGi() && Math.abs(Wrapper.mc.player.posY - entity2.posY) >= this.yDifference.OnGi()) {
                    final EntityEnderCrystal inMa = (EntityEnderCrystal)entity2;
                    if (Wrapper.mc.player.getDistance((Entity)inMa) < doubleValue) {
                        doubleValue = Wrapper.mc.player.getDistance((Entity)inMa);
                        this.INMa = inMa;
                    }
                }
                if (CrystalAura.\u2001\u200e\u200e\u2003) {
                    throw null;
                }
            }
        }
        if (this.priority.OnGi().equalsIgnoreCase("Random")) {
            this.umql = new ArrayList<EntityEnderCrystal>();
            for (final Entity entity3 : Wrapper.mc.world.loadedEntityList) {
                if (entity3 instanceof EntityEnderCrystal && Wrapper.mc.player.getDistance(entity3) < this.range.OnGi()) {
                    this.umql.add((EntityEnderCrystal)entity3);
                }
                if (CrystalAura.\u2001\u200e\u200e\u2003) {
                    throw null;
                }
            }
            this.INMa = this.umql.get(new Random().nextInt(this.umql.size()));
        }
        if (this.INMa != null && this.eatting.OnGi().equalsIgnoreCase("Stop eatting") && Wrapper.mc.player.isHandActive()) {
            Wrapper.mc.player.stopActiveHand();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.Xijd.gCgh();
            if (this.rotationType.OnGi().equalsIgnoreCase("Stay")) {
                omqH.prFd = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                omqH.OjyD = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[1];
            }
            if (this.Xijd.QOXG(this.attackDelay.OnGi() * 1000.0) && this.INMa != null && Wrapper.mc.player.getDistance((Entity)this.INMa) <= this.range.OnGi() && Math.abs(Wrapper.mc.player.posY - this.INMa.posY) >= this.yDifference.OnGi()) {
                if (this.rotationType.OnGi().equalsIgnoreCase("Snap")) {
                    omqH.prFd = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                    omqH.OjyD = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[1];
                }
                if (this.event.OnGi().equalsIgnoreCase("Pre")) {
                    if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                        Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, (Entity)this.INMa);
                    }
                    OffHand.LInL();
                    Wrapper.mc.player.resetCooldown();
                    this.Xijd.BBwj();
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.Xijd.gCgh();
            if (this.rotationType.OnGi().equalsIgnoreCase("Stay")) {
                omqH.prFd = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                omqH.OjyD = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[1];
            }
            if (this.Xijd.QOXG(this.attackDelay.OnGi() * 1000.0) && this.INMa != null && Wrapper.mc.player.getDistance((Entity)this.INMa) <= this.range.OnGi() && Math.abs(Wrapper.mc.player.posY - this.INMa.posY) >= this.yDifference.OnGi()) {
                if (this.rotationType.OnGi().equalsIgnoreCase("Snap")) {
                    omqH.prFd = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                    omqH.OjyD = this.xIdp((Entity)this.INMa, Float.MAX_VALUE, Float.MAX_VALUE)[1];
                }
                if (this.event.OnGi().equalsIgnoreCase("Pre")) {
                    if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)this.INMa));
                    }
                    OffHand.LInL();
                    this.Xijd.BBwj();
                }
            }
        }
    }
    
    @oqrr
    public void ooHs(final ILBH ilbh) {
        if (this.event.OnGi().equalsIgnoreCase("Post")) {
            if (this.place.OnGi() && this.mgWw != null && this.HSgN.QOXG(this.placeDelay.OnGi() * 1000.0)) {
                if (this.mgWw != null && this.eatting.OnGi().equalsIgnoreCase("Stop eatting") && Wrapper.mc.player.isHandActive()) {
                    Wrapper.mc.player.stopActiveHand();
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                }
                final RayTraceResult rayTraceBlocks = Wrapper.mc.world.rayTraceBlocks(new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ), new Vec3d(this.mgWw.getX() + 0.5, this.mgWw.getY() - 0.5, this.mgWw.getZ() + 0.5));
                final NetHandlerPlayClient connection = Wrapper.mc.player.connection;
                final BlockPos mgWw = this.mgWw;
                EnumFacing enumFacing;
                if (rayTraceBlocks == null || rayTraceBlocks.sideHit == null) {
                    enumFacing = EnumFacing.UP;
                    if (CrystalAura.\u2001\u200e\u200e\u2003) {
                        throw null;
                    }
                }
                else {
                    enumFacing = rayTraceBlocks.sideHit;
                }
                connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(mgWw, enumFacing, OffHand.txLa(), 0.0f, 0.0f, 0.0f));
                OffHand.LInL();
                this.HSgN.BBwj();
                if (this.placeBreak.OnGi()) {
                    return;
                }
            }
            if (this.mode.OnGi().equalsIgnoreCase("Normal") && this.Xijd.QOXG(this.attackDelay.OnGi() * 1000.0) && this.INMa != null && Wrapper.mc.player.getDistance((Entity)this.INMa) <= this.range.OnGi() && Math.abs(Wrapper.mc.player.posY - this.INMa.posY) >= this.yDifference.OnGi()) {
                if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                    Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, (Entity)this.INMa);
                }
                OffHand.LInL();
                Wrapper.mc.player.resetCooldown();
                this.Xijd.BBwj();
            }
            if (this.mode.OnGi().equalsIgnoreCase("Packet") && this.Xijd.QOXG(this.attackDelay.OnGi() * 1000.0) && this.INMa != null && Wrapper.mc.player.getDistance((Entity)this.INMa) <= this.range.OnGi() && Math.abs(Wrapper.mc.player.posY - this.INMa.posY) >= this.yDifference.OnGi()) {
                if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)this.INMa));
                }
                OffHand.LInL();
                this.Xijd.BBwj();
            }
        }
    }
    
    public float[] xIdp(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - Wrapper.mc.player.posX;
        final double n4 = entity.posZ - Wrapper.mc.player.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                throw null;
            }
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final double n8 = n5;
        float n9;
        if (entity instanceof EntityPlayer) {
            n9 = 0.5f;
            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                throw null;
            }
        }
        else {
            n9 = 0.0f;
        }
        final float n10 = (float)(-(Math.atan2(n8 - n9, n6) * 180.0 / 3.141592653589793));
        Wrapper.mc.player.rotationYawHead = qxzm(Wrapper.mc.player.rotationYaw, n7, n);
        return new float[] { qxzm(Wrapper.mc.player.rotationYaw, n7, n), qxzm(Wrapper.mc.player.rotationPitch, n10, n2) };
    }
    
    public static float qxzm(final float n, final float n2, final float n3) {
        float wrapDegrees = MathHelper.wrapDegrees(n2 - n);
        if (wrapDegrees > n3) {
            wrapDegrees = n3;
        }
        if (wrapDegrees < -n3) {
            wrapDegrees = -n3;
        }
        return n + wrapDegrees;
    }
    
    @Override
    public void LPxx() {
        this.Xijd.BBwj();
        this.HSgN.BBwj();
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        this.Xijd.BBwj();
        this.HSgN.BBwj();
        hSNE.kduK(this);
        super.qBmy();
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (this.esp.OnGi()) {
            if (this.place.OnGi() && this.mgWw != null) {
                BIIV.TGTO(this.mgWw, Color.red, 1.0, 1.0);
            }
            if (this.INMa != null) {
                BIIV.Lszk((Entity)this.INMa, Color.red);
            }
        }
    }
    
    private boolean ElSI(final BlockPos blockPos) {
        final BlockPos add = blockPos.add(0, 1, 0);
        final BlockPos add2 = blockPos.add(0, 2, 0);
        return (Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && Wrapper.mc.world.getBlockState(add).getBlock() == Blocks.AIR && Wrapper.mc.world.getBlockState(add2).getBlock() == Blocks.AIR && Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(add)).isEmpty();
    }
    
    public static BlockPos pLkR() {
        return new BlockPos(Math.floor(Wrapper.mc.player.posX), Math.floor(Wrapper.mc.player.posY), Math.floor(Wrapper.mc.player.posZ));
    }
    
    private List<BlockPos> gTUI() {
        final NonNullList create = NonNullList.create();
        create.addAll((Collection)this.Zccq(pLkR(), this.range.OnGi().floatValue(), this.range.OnGi().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::ElSI).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)create;
    }
    
    public List<BlockPos> Zccq(final BlockPos blockPos, final float n, final int n2, final boolean b, final boolean b2, final int n3) {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        int n4 = x - (int)n;
        while (n4 <= x + n) {
            int n5 = z - (int)n;
        Label_0058:
            while (n5 <= z + n) {
                int n6;
                if (b2) {
                    n6 = y - (int)n;
                    if (CrystalAura.\u2001\u200e\u200e\u2003) {
                        throw null;
                    }
                }
                else {
                    n6 = y;
                }
                int n7 = n6;
                do {
                    final float n8 = (float)n7;
                    float n9;
                    if (b2) {
                        n9 = y + n;
                        if (CrystalAura.\u2001\u200e\u200e\u2003) {
                            throw null;
                        }
                    }
                    else {
                        n9 = (float)(y + n2);
                    }
                    if (n8 < n9) {
                        final int n10 = (x - n4) * (x - n4) + (z - n5) * (z - n5);
                        int n11;
                        if (b2) {
                            n11 = (y - n7) * (y - n7);
                            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                                throw null;
                            }
                        }
                        else {
                            n11 = 0;
                        }
                        final double n12 = n10 + n11;
                        if (n12 < n * n && (!b || n12 >= (n - 1.0f) * (n - 1.0f))) {
                            list.add(new BlockPos(n4, n7 + n3, n5));
                        }
                        ++n7;
                    }
                    else {
                        ++n5;
                        if (CrystalAura.\u2001\u200e\u200e\u2003) {
                            throw null;
                        }
                        continue Label_0058;
                    }
                } while (!CrystalAura.\u2001\u200e\u200e\u2003);
                throw null;
            }
            ++n4;
            if (CrystalAura.\u2001\u200e\u200e\u2003) {
                throw null;
            }
        }
        return list;
    }
    
    public static float tnyA(final double n, final double n2, final double n3, final Entity entity) {
        final float n4 = 12.0f;
        final double n5 = (1.0 - entity.getDistance(n, n2, n3) / n4) * entity.world.getBlockDensity(new Vec3d(n, n2, n3), entity.getEntityBoundingBox());
        final float n6 = (float)(int)((n5 * n5 + n5) / 2.0 * 7.0 * n4 + 1.0);
        double n7 = 1.0;
        if (entity instanceof EntityLivingBase) {
            n7 = vOVm((EntityLivingBase)entity, HTgA(n6), new Explosion((World)Wrapper.mc.world, (Entity)null, n, n2, n3, 6.0f, false, true));
        }
        return (float)n7;
    }
    
    public static float vOVm(final EntityLivingBase entityLivingBase, float n, final Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            final DamageSource causeExplosionDamage = DamageSource.causeExplosionDamage(explosion);
            n = CombatRules.getDamageAfterAbsorb(n, (float)entityPlayer.getTotalArmorValue(), (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            n *= 1.0f - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage(entityPlayer.getArmorInventoryList(), causeExplosionDamage), 0.0f, 20.0f) / 25.0f;
            if (entityLivingBase.isPotionActive(Potion.getPotionById(11))) {
                n -= n / 4.0f;
            }
            n = Math.max(n - entityPlayer.getAbsorptionAmount(), 0.0f);
            return n;
        }
        n = CombatRules.getDamageAfterAbsorb(n, (float)entityLivingBase.getTotalArmorValue(), (float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return n;
    }
    
    private static float HTgA(final float n) {
        Wrapper.mc.world.getDifficulty().getId();
        final float n2 = 0.0f;
        if (CrystalAura.\u2001\u200e\u200e\u2003) {
            throw null;
        }
        return n * n2;
    }
    
    public static float kPBN(final EntityEnderCrystal entityEnderCrystal, final Entity entity) {
        return tnyA(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, entity);
    }
    
    private void TpPT(final double n, final double n2, final double n3, final EntityPlayer entityPlayer, final omqH omqH) {
        final double[] array = nYgh(n, n2, n3, entityPlayer);
        omqH.prFd = (float)array[0];
        omqH.OjyD = (float)array[1];
    }
    
    public static double[] nYgh(final double n, final double n2, final double n3, final EntityPlayer entityPlayer) {
        final double n4 = entityPlayer.posX - n;
        final double n5 = entityPlayer.posY - n2;
        final double n6 = entityPlayer.posZ - n3;
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5 + n6 * n6);
        return new double[] { Math.atan2(n6 / sqrt, n4 / sqrt) * 180.0 / 3.141592653589793 + 90.0, Math.asin(n5 / sqrt) * 180.0 / 3.141592653589793 };
    }
}
