package com.client.glowclient.modules.jewishtricks;

import net.minecraft.entity.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import java.util.stream.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import java.util.*;
import com.client.glowclient.events.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import com.google.common.util.concurrent.*;
import net.minecraft.util.math.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.*;

public class NametagsImpact extends ModuleContainer
{
    public static final BooleanValue mobs;
    public static final BooleanValue passive;
    public static final BooleanValue players;
    
    private static EntityLivingBase M(final Entity entity) {
        return (EntityLivingBase)entity;
    }
    
    private static boolean M(final Entity entity) {
        return !Objects.equals(Wrapper.mc.player, entity) && !EntityUtils.D(entity);
    }
    
    @SubscribeEvent
    public void M(final RenderLivingEvent$Specials$Pre renderLivingEvent$Specials$Pre) {
        if (EntityUtils.e((Entity)renderLivingEvent$Specials$Pre.getEntity())) {
            renderLivingEvent$Specials$Pre.setCanceled(true);
        }
    }
    
    static {
        players = ValueFactory.M("NametagsImpact", "Players", "Shows Players", true);
        mobs = ValueFactory.M("NametagsImpact", "Mobs", "Shows Mobs", false);
        passive = ValueFactory.M("NametagsImpact", "Passive", "Shows Passive", false);
    }
    
    @Override
    public void D() {
        ModuleManager.M("Nametags").k();
    }
    
    public double M(final ca ca, final EntityLivingBase entityLivingBase, final double n, final double n2) {
        final List<? super Object> list;
        if (!(list = StreamSupport.stream(entityLivingBase.getEquipmentAndArmor().spliterator(), false).filter(Objects::nonNull).filter((Predicate<? super Object>)NE::M).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).isEmpty()) {
            final double n3 = n + 8.0 * list.size() / 3.33;
            final double n4 = n2 - 8.0;
            int n5;
            int i = n5 = 0;
            while (i < list.size()) {
                final double n6 = n3 - n5 * 8.0;
                final ItemStack itemStack = list.get(n5);
                GL11.glPushMatrix();
                final float n7 = 0.5f;
                GL11.glScalef(n7, n7, n7);
                ca.K().d().M(ca::B).M(ca::e).M(itemStack, n6 * 2.0, n4 * 2.0 - 24.0).D(itemStack, n6 * 2.0, n4 * 2.0 - 24.0).M(ca::M).B();
                GL11.glPopMatrix();
                final double n8 = n3 - n5 * 8;
                double n9 = n4 - 15.0;
                final List<X> m;
                if ((m = Ea.M(itemStack.getEnchantmentTagList())) != null) {
                    final Iterator<X> iterator = m.iterator();
                Label_0264:
                    while (true) {
                        Iterator<X> iterator2 = iterator;
                        while (iterator2.hasNext()) {
                            ca.K().d().M(ca::D).M(ca::d).D(0.3).D(ga.G).M(iterator.next().M(), n8 * 3.33333, n9 * 3.33333, true).D(1.0).M(ca::E).M(ca::A).M(ca::a).B();
                            final double n10 = n9 -= Ia.M(HUD.F, 0.4) + 1.0;
                            if (n10 > n10) {
                                continue Label_0264;
                            }
                            iterator2 = iterator;
                        }
                        break;
                    }
                }
                i = ++n5;
            }
        }
        final ea ea = null;
        final String name = entityLivingBase.getName();
        final String format = String.format("%.0f", entityLivingBase.getHealth());
        MathHelper.clamp(entityLivingBase.getHealth(), 0.0f, 20.0f);
        int n11 = ga.H;
        if (entityLivingBase.getHealth() >= 15.0f) {
            n11 = ga.H;
        }
        if (entityLivingBase.getHealth() <= 15.0f && entityLivingBase.getHealth() >= 5.0f) {
            n11 = ga.b;
        }
        if (entityLivingBase.getHealth() <= 5.0f) {
            n11 = ga.M;
        }
        int n13;
        double n14;
        if (!Va.M().M(entityLivingBase.getName())) {
            final int n12 = 175;
            n13 = ga.M(n12, n12, n12, 255);
            n14 = n;
        }
        else {
            n13 = ga.d;
            n14 = n;
        }
        final double n15 = n14 - ca.M(name + " " + format) / 4.0;
        final double n16 = n2 - ca.M() - 1.0;
        final double n17 = n15 - 2.0;
        final double n18 = n16 - 2.0;
        final double n19 = Ia.M(ea, new StringBuilder().insert(0, name).append(" ").append(format).toString(), 1.0) / 2.0 + 4.0;
        final double n20 = Ia.M(ea) / 2.0 + 2.0;
        final int n21 = 175;
        ba.M(n17, n18, n19, n20, ga.M(n21, n21, n21, 200));
        final ca j = ca.K().d().M(ca::D).M(ca::d).M(ca::k).M();
        final int n22 = 0;
        j.D(ga.M(n22, n22, n22, 150)).k(n15 - 2.0, n16 - 2.0, Ia.M(ea, new StringBuilder().insert(0, name).append(" ").append(format).toString(), 1.0) / 2.0 + 4.0, Ia.M(ea) / 2.0 + 2.0).A().M(ca::A).M(ca::E).D(n13).D(0.5).M(name, n15 * 2.0, n16 * 2.0 - 2.0, true).D(n11).M(format, n15 * 2.0 + Ia.M(ea, new StringBuilder().insert(0, name).append(" ").toString(), 1.0), n16 * 2.0 - 2.0, true).D(1.0).M(ca::E).M(ca::A).M(ca::a).B();
        return Wrapper.mc.fontRenderer.FONT_HEIGHT + 1337.0;
    }
    
    @SubscribeEvent
    public void M(final EventRenderScreen eventRenderScreen) {
        Wrapper.mc.world.loadedEntityList.stream().filter(Y::i).filter(NE::M).filter(Y::a).filter(Y::j).map(NE::M).forEach(this::M);
    }
    
    private static boolean M(final ItemStack itemStack) {
        return !itemStack.isEmpty();
    }
    
    private void M(final EntityLivingBase entityLivingBase) {
        final Vec3d d = EntityUtils.D((Entity)entityLivingBase, Wrapper.mc.getRenderPartialTicks());
        final double n = entityLivingBase.getRenderBoundingBox().maxY - entityLivingBase.posY;
        final double n2 = 0.0;
        final Bd m = nC.M(d.add(n2, n, n2));
        final Bd i = nC.M(d);
        if (entityLivingBase == null) {
            return;
        }
        if (entityLivingBase instanceof EntityBat) {
            return;
        }
        if (!NametagsImpact.players.M() && entityLivingBase instanceof EntityPlayer) {
            return;
        }
        if (!NametagsImpact.mobs.M()) {
            if (entityLivingBase instanceof EntityMob) {
                return;
            }
            if (entityLivingBase instanceof EntityGhast) {
                return;
            }
            if (entityLivingBase instanceof EntityMagmaCube) {
                return;
            }
            if (entityLivingBase instanceof EntitySlime) {
                return;
            }
        }
        if (!NametagsImpact.passive.M()) {
            if (entityLivingBase instanceof EntityAnimal) {
                return;
            }
            if (entityLivingBase instanceof EntityArmorStand) {
                return;
            }
            if (entityLivingBase instanceof EntityGolem) {
                return;
            }
            if (entityLivingBase instanceof EntityIronGolem) {
                return;
            }
        }
        if (!m.M() && !i.M()) {
            return;
        }
        final Bd bd = m;
        final double j = bd.M();
        final double n3 = bd.D() + 1.0;
        final AtomicDouble atomicDouble = new AtomicDouble();
        atomicDouble.set(atomicDouble.get() + this.M(ca.m(), entityLivingBase, j, n3));
    }
    
    public NametagsImpact() {
        super(Category.JEWISH TRICKS, "NametagsImpact", false, -1, "Informative nametags");
    }
}
