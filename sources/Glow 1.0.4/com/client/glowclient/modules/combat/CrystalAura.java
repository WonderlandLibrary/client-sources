package com.client.glowclient.modules.combat;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.client.network.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import com.client.glowclient.modules.*;

public class CrystalAura extends ModuleContainer
{
    public static BooleanValue throughWalls;
    public static nB mode;
    public static BooleanValue friendDetect;
    public static final NumberValue range;
    private Entity L;
    private long A;
    private BlockPos B;
    private long b;
    
    private static void M(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final float n8, final float n9, final float n10, final float n11) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n8, n9, n10, n11);
        GlStateManager.disableLighting();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5 + n7, n6);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        final double n12 = 1.0;
        GL11.glColor3d(n12, n12, n12);
        GlStateManager.enableLighting();
    }
    
    @Override
    public void E() {
        final Entity l = null;
        this.B = null;
        this.L = l;
        y.M(this);
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        if (CrystalAura.mode.e().equals("GlowClient")) {
            try {
                final double n = 4.0;
                final EntityPlayerSP player = Wrapper.mc.player;
                final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
                final ItemStack heldItemOffhand = Wrapper.mc.player.getHeldItemOffhand();
                if ((heldItemMainhand == null || (!(heldItemMainhand.getItem() instanceof ItemFood) && !(heldItemMainhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) && (heldItemOffhand == null || (!(heldItemOffhand.getItem() instanceof ItemFood) && !(heldItemOffhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown())) {
                    final double n2 = 1000.0;
                    this.A = System.nanoTime() / 1000000L;
                    if (this.M((long)(n2 / n))) {
                        final Iterator<Entity> iterator = (Iterator<Entity>)Wrapper.mc.world.loadedEntityList.iterator();
                        while (iterator.hasNext()) {
                            final Entity entity;
                            if ((entity = iterator.next()) != null && player.getDistance(entity) < CrystalAura.range.k() && this.M(entity)) {
                                if (entity instanceof EntityEnderCrystal) {
                                    final EntityPlayerSP entityPlayerSP = player;
                                    y.M(entity, this);
                                    Wrapper.mc.playerController.attackEntity((EntityPlayer)player, entity);
                                    entityPlayerSP.swingArm(EnumHand.MAIN_HAND);
                                    this.b = System.nanoTime() / 1000000L;
                                }
                                else {
                                    y.M(this);
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private static boolean D(final boolean b, final boolean b2, final Entity entity) {
        if (EntityUtils.i(entity)) {
            if (EntityUtils.d(entity)) {
                if (!b) {
                    return false;
                }
            }
            else if (!b2) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (CrystalAura.mode.e().equals("Kami")) {
            try {
                final boolean b = false;
                final boolean b2 = false;
                final boolean b3 = true;
                final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
                final ItemStack heldItemOffhand = Wrapper.mc.player.getHeldItemOffhand();
                if ((heldItemMainhand == null || (!(heldItemMainhand.getItem() instanceof ItemFood) && !(heldItemMainhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) && (heldItemOffhand == null || (!(heldItemOffhand.getItem() instanceof ItemFood) && !(heldItemOffhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown())) {
                    final EntityEnderCrystal entityEnderCrystal;
                    if ((entityEnderCrystal = (EntityEnderCrystal)Wrapper.mc.world.loadedEntityList.stream().filter(Zf::D).map(Zf::M).min(Comparator.comparing((Function<? super T, ? extends Comparable>)Zf::M)).orElse(null)) != null && Wrapper.mc.player.getDistance((Entity)entityEnderCrystal) <= CrystalAura.range.k() && this.M((Entity)entityEnderCrystal)) {
                        final long n = 250L;
                        this.A = System.nanoTime() / 1000000L;
                        if (this.M(n)) {
                            this.M(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, (EntityPlayer)Wrapper.mc.player);
                            Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, (Entity)entityEnderCrystal);
                            Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.b = System.nanoTime() / 1000000L;
                        }
                        return;
                    }
                    y.M(this);
                    int n2 = (Wrapper.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? Wrapper.mc.player.inventory.currentItem : -1;
                    if (n2 == -1) {
                        int n3;
                        int i = n3 = 0;
                        while (i < 9) {
                            if (Wrapper.mc.player.inventory.getStackInSlot(n3).getItem() == Items.END_CRYSTAL) {
                                n2 = -1;
                                break;
                            }
                            i = ++n3;
                        }
                    }
                    boolean b4 = false;
                    CrystalAura crystalAura;
                    if (Wrapper.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                        b4 = true;
                        crystalAura = this;
                    }
                    else {
                        if (n2 == -1) {
                            return;
                        }
                        crystalAura = this;
                    }
                    final List<BlockPos> m = crystalAura.M();
                    final ArrayList<Entity> list = new ArrayList<Entity>();
                    if (CrystalAura.friendDetect.M()) {
                        if (b3) {
                            list.addAll((Collection<?>)Wrapper.mc.world.playerEntities.stream().filter(Zf::M).collect(Collectors.toList()));
                        }
                        list.addAll((Collection<?>)Wrapper.mc.world.loadedEntityList.stream().filter(Zf::M).collect(Collectors.toList()));
                    }
                    else {
                        if (b3) {
                            list.addAll((Collection<?>)Wrapper.mc.world.playerEntities.stream().collect(Collectors.toList()));
                        }
                        list.addAll((Collection<?>)Wrapper.mc.world.loadedEntityList.stream().filter(Zf::D).collect(Collectors.toList()));
                    }
                    BlockPos b5 = null;
                    double n4 = 0.5;
                    final Iterator<Object> iterator = list.iterator();
                Label_0649:
                    while (true) {
                        Iterator<Object> iterator2 = iterator;
                        while (iterator2.hasNext()) {
                            final Entity l;
                            if ((l = iterator.next()) == Wrapper.mc.player) {
                                continue Label_0649;
                            }
                            if (((EntityLivingBase)l).getHealth() <= 0.0f) {
                                iterator2 = iterator;
                            }
                            else {
                                final Iterator<BlockPos> iterator3 = m.iterator();
                            Label_0705:
                                while (true) {
                                    Iterator<BlockPos> iterator4 = iterator3;
                                    while (iterator4.hasNext()) {
                                        final BlockPos blockPos = iterator3.next();
                                        if (l.getDistanceSq(blockPos) >= 169.0) {
                                            iterator4 = iterator3;
                                        }
                                        else {
                                            final double n5;
                                            final double n6;
                                            if ((n5 = M(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, l)) <= n4 || ((n6 = M(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)Wrapper.mc.player)) > n5 && n5 >= ((EntityLivingBase)l).getHealth())) {
                                                continue Label_0705;
                                            }
                                            if (n6 - 0.5 <= Wrapper.mc.player.getHealth()) {
                                                n4 = n5;
                                                b5 = blockPos;
                                                this.L = l;
                                                continue Label_0705;
                                            }
                                            iterator4 = iterator3;
                                        }
                                    }
                                    continue Label_0649;
                                }
                            }
                        }
                        break;
                    }
                    if (n4 == 0.5) {
                        final Entity j = null;
                        this.B = null;
                        this.L = j;
                        y.M(this);
                        return;
                    }
                    this.B = b5;
                    if (!b4 && Wrapper.mc.player.inventory.currentItem != n2) {
                        y.M(this);
                        return;
                    }
                    this.M(b5.getX() + 0.5, b5.getY() - 0.5, b5.getZ() + 0.5, (EntityPlayer)Wrapper.mc.player);
                    final RayTraceResult rayTraceBlocks;
                    EnumFacing enumFacing;
                    if ((rayTraceBlocks = Wrapper.mc.world.rayTraceBlocks(new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ), new Vec3d(b5.getX() + 0.5, b5.getY() - 0.5, b5.getZ() + 0.5))) == null || rayTraceBlocks.sideHit == null) {
                        y.M(this);
                        enumFacing = EnumFacing.UP;
                    }
                    else {
                        enumFacing = rayTraceBlocks.sideHit;
                    }
                    final NetHandlerPlayClient connection = CrystalAura.B.player.connection;
                    final BlockPos blockPos2 = b5;
                    final EnumFacing enumFacing2 = enumFacing;
                    final EnumHand enumHand = b4 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                    final float n7 = 0.0f;
                    connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos2, enumFacing2, enumHand, n7, n7, n7));
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private static Float M(final EntityEnderCrystal entityEnderCrystal) {
        return Wrapper.mc.player.getDistance((Entity)entityEnderCrystal);
    }
    
    private static boolean M(final boolean b, final boolean b2, final Entity entity) {
        if (EntityUtils.i(entity)) {
            if (EntityUtils.d(entity)) {
                if (!b) {
                    return false;
                }
            }
            else if (!b2) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    private List<BlockPos> M() {
        final NonNullList create = NonNullList.create();
        final BlockPos m = M();
        final float n = (float)CrystalAura.range.k();
        final int n2 = (int)CrystalAura.range.k();
        final boolean b = true;
        final int n3 = 0;
        create.addAll((Collection)this.M(m, n, n2, (boolean)(n3 != 0), b, n3).stream().filter((Predicate<? super Object>)this::M).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)create;
    }
    
    public static BlockPos M() {
        return new BlockPos(Math.floor(Wrapper.mc.player.posX), Math.floor(Wrapper.mc.player.posY), Math.floor(Wrapper.mc.player.posZ));
    }
    
    @Override
    public String M() {
        return String.format("%.1f", CrystalAura.range.k());
    }
    
    private static float M(final float n) {
        final int id = Wrapper.mc.world.getDifficulty().getId();
        return n * ((id == 0) ? 0.0f : ((id == 2) ? 1.0f : ((id == 1) ? 0.5f : 1.5f)));
    }
    
    private static boolean M(final EntityPlayer entityPlayer) {
        return !Va.M().M(entityPlayer.getName());
    }
    
    private static boolean D(final Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }
    
    static {
        range = ValueFactory.M("CrystalAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);
        CrystalAura.throughWalls = ValueFactory.M("CrystalAura", "ThroughWalls", "Hit through walls", false);
        CrystalAura.friendDetect = ValueFactory.M("CrystalAura", "FriendDetect", "Only attacks non-friended entities", true);
        CrystalAura.mode = ValueFactory.M("CrystalAura", "Mode", "Mode of CrystalAura - Credits to 086 for Kami source", "GlowClient", "GlowClient", "Kami");
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            if (this.B != null) {
                final BlockPos b = this.B;
                final int n = 255;
                final int n2 = 64;
                final int n3 = 150;
                Ma.M(b, n, n, n, n2, n3, n3, n3, 128, 1);
                if (this.L != null) {
                    final Vec3d m = EntityUtils.M(this.L, Wrapper.mc.getRenderPartialTicks());
                    final double n4 = this.B.getX() - renderPosX + 0.5;
                    final double n5 = this.B.getY() - renderPosY + 1.0;
                    final double n6 = this.B.getZ() - renderPosZ + 0.5;
                    final double x = m.x;
                    final double y = m.y;
                    final double z = m.z;
                    final double n7 = this.L.getEyeHeight();
                    final float n8 = 1.0f;
                    M(n4, n5, n6, x, y, z, n7, n8, n8, n8, n8);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public boolean M(final Entity entity) {
        return CrystalAura.throughWalls.M() || Wrapper.mc.player.canEntityBeSeen(entity);
    }
    
    private static EntityEnderCrystal M(final Entity entity) {
        return (EntityEnderCrystal)entity;
    }
    
    private static float M(final EntityLivingBase entityLivingBase, float n, final Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            final DamageSource causeExplosionDamage = DamageSource.causeExplosionDamage(explosion);
            n = CombatRules.getDamageAfterAbsorb(n, (float)entityPlayer.getTotalArmorValue(), (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            n *= 1.0f - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage(entityPlayer.getArmorInventoryList(), causeExplosionDamage), 0.0f, 20.0f) / 25.0f;
            if (entityLivingBase.isPotionActive(Potion.getPotionById(11))) {
                final float n2 = n;
                n = n2 - n2 / 4.0f;
            }
            return n = Math.max(n - entityPlayer.getAbsorptionAmount(), 0.0f);
        }
        return n = CombatRules.getDamageAfterAbsorb(n, (float)entityLivingBase.getTotalArmorValue(), (float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
    }
    
    private boolean M(final BlockPos blockPos) {
        final int n = 1;
        final int n2 = 0;
        final BlockPos add = blockPos.add(n2, n, n2);
        final int n3 = 2;
        final int n4 = 0;
        final BlockPos add2 = blockPos.add(n4, n3, n4);
        return (Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && Wrapper.mc.world.getBlockState(add).getBlock() == Blocks.AIR && Wrapper.mc.world.getBlockState(add2).getBlock() == Blocks.AIR && Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(add)).isEmpty();
    }
    
    private static float M(final double n, final double n2, final double n3, final Entity entity) {
        final float n4 = 12.0f;
        final double n5 = (1.0 - entity.getDistance(n, n2, n3) / n4) * entity.world.getBlockDensity(new Vec3d(n, n2, n3), entity.getEntityBoundingBox());
        final float n6 = (float)(int)((n5 * n5 + n5) / 2.0 * 7.0 * n4 + 1.0);
        double n7 = 1.0;
        if (entity instanceof EntityLivingBase) {
            n7 = M((EntityLivingBase)entity, M(n6), new Explosion((World)Wrapper.mc.world, (Entity)null, n, n2, n3, 6.0f, false, true));
        }
        return (float)n7;
    }
    
    private boolean M(final long n) {
        return this.A - this.b >= n;
    }
    
    private void M(final double n, final double n2, final double n3, final EntityPlayer entityPlayer) {
        final double[] m = EntityUtils.M(n, n2, n3, entityPlayer);
        y.M((float)m[0], (float)m[1], this);
    }
    
    public CrystalAura() {
        final long b = -1L;
        final long a = 0L;
        super(Category.COMBAT, "CrystalAura", false, -1, "Automatically explodes crystals. Thanks 086 for Kami source");
        this.A = a;
        this.b = b;
    }
    
    private List<BlockPos> M(final BlockPos blockPos, final float n, final int n2, final boolean b, final boolean b2, final int n3) {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        int n5;
        int n4 = n5 = x - (int)n;
        while (n4 <= x + n) {
            int n7;
            int n6 = n7 = z - (int)n;
            while (n6 <= z + n) {
                int n9;
                int n8 = n9 = (b2 ? (y - (int)n) : y);
                while (n9 < (b2 ? (y + n) : ((float)(y + n2)))) {
                    final double n10;
                    if ((n10 = (x - n5) * (x - n5) + (z - n7) * (z - n7) + (b2 ? ((y - n8) * (y - n8)) : 0)) < n * n && (!b || n10 >= (n - 1.0f) * (n - 1.0f))) {
                        list.add(new BlockPos(n5, n8 + n3, n7));
                    }
                    n9 = ++n8;
                }
                n6 = ++n7;
            }
            n4 = ++n5;
        }
        return list;
    }
}
