package com.client.glowclient.modules.render;

import net.minecraftforge.client.event.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.client.glowclient.modules.other.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class PlayerList extends ModuleContainer
{
    @Override
    public String M() {
        return "";
    }
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        final ca ca = new ca();
        double n = 0.0;
        final double n2 = 2.0;
        final Iterator<Entity> iterator = Wrapper.mc.world.loadedEntityList.iterator();
    Label_0031:
        while (true) {
            Iterator<Entity> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final Entity entity;
                if (!((entity = iterator.next()) instanceof EntityPlayer) || entity == Wrapper.mc.player) {
                    continue Label_0031;
                }
                n += ((Ha.M((EntityPlayer)entity) || Ha.D((EntityPlayer)entity)) ? 24.0 : 10.0);
                int n3 = 0;
                int n4 = ga.G;
                if (Va.M().M(entity.getName())) {
                    n4 = ga.d;
                }
                if (wa.M().M(entity.getName())) {
                    final int n5 = 0;
                    final int n6 = 255;
                    n4 = ga.M(n6, n5, n5, n6);
                }
                final float n7 = MathHelper.clamp(((EntityPlayer)entity).getHealth(), 0.0f, ((EntityPlayer)entity).getMaxHealth()) / ((EntityPlayer)entity).getMaxHealth();
                final int n8 = (((EntityPlayer)entity).getHealth() + ((EntityPlayer)entity).getAbsorptionAmount() > ((EntityPlayer)entity).getMaxHealth()) ? ga.b : ga.M((int)((255.0f - n7) * 255.0f), (int)(255.0f * n7), 0, 255);
                final String format = String.format("%.1f", ((EntityPlayer)entity).getHealth() + ((EntityPlayer)entity).getAbsorptionAmount());
                final ea f = HUD.F;
                final ca ca2 = ca;
                ca ca3;
                if (f != null) {
                    ca2.K().M(HUD.F).D(n4).M(entity.getName(), n2 + 1.0, n + 1.0, true).D(n4).M(entity.getName(), n2, n).D(n8).M(format, n2 + Ia.M(HUD.F, new StringBuilder().insert(0, entity.getName()).append(" ").toString(), 1.0) + 1.0, n + 1.0, true).D(n8).M(format, n2 + Ia.M(HUD.F, new StringBuilder().insert(0, entity.getName()).append(" ").toString(), 1.0), n);
                    ca3 = ca;
                }
                else {
                    ca2.K().M(HUD.F).D(n4).M(entity.getName(), n2, n, true).D(n8).M(format, n2 + Ia.M(HUD.F, new StringBuilder().insert(0, entity.getName()).append(" ").toString(), 1.0), n, true);
                    ca3 = ca;
                }
                ca3.K().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e).M(((EntityPlayer)entity).getHeldItemMainhand(), n2 + 74.0, n - 16.0).D(((EntityPlayer)entity).getHeldItemMainhand(), n2 + 74.0, n - 16.0).M(((EntityPlayer)entity).getHeldItemOffhand(), n2 + 58.0, n - 16.0).D(((EntityPlayer)entity).getHeldItemOffhand(), n2 + 58.0, n - 16.0).M(com.client.glowclient.ca::M);
                double n9 = n;
                final List<X> m;
                if ((m = Ea.M(((EntityPlayer)entity).getHeldItemMainhand().getEnchantmentTagList())) == null) {
                    iterator2 = iterator;
                }
                else {
                    final Iterator<X> iterator3 = m.iterator();
                Label_0675:
                    while (true) {
                        Iterator<X> iterator4 = iterator3;
                        while (iterator4.hasNext()) {
                            Ia.M(HUD.F, iterator3.next().M(), n2 + 74.0, n9 - 3.0, true, ga.G, 0.5);
                            final double n10 = n9 -= Ia.M(HUD.F, 0.5) + 1.0;
                            if (n10 > n10 - 16.0) {
                                continue Label_0675;
                            }
                            iterator4 = iterator3;
                        }
                        break;
                    }
                    double n11 = n;
                    final List<X> i;
                    if ((i = Ea.M(((EntityPlayer)entity).getHeldItemOffhand().getEnchantmentTagList())) != null) {
                        final Iterator<X> iterator5 = i.iterator();
                        Label_0802:
                        while (true) {
                            Iterator<X> iterator6 = iterator5;
                            while (iterator6.hasNext()) {
                                Ia.M(HUD.F, iterator5.next().M(), n2 + 58.0, n9 - 3.0, true, ga.G, 0.5);
                                final double n12 = n11 -= Ia.M(HUD.F, 0.5) + 1.0;
                                if (n12 > n12 - 16.0) {
                                    continue Label_0802;
                                }
                                iterator6 = iterator5;
                            }
                            break;
                        }
                        int n13;
                        int j = n13 = 0;
                        while (j < 4) {
                            final ItemStack k = Ha.M(n13, (EntityPlayer)entity);
                            final ca l = ca.K().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e);
                            final ItemStack itemStack = k;
                            final double n14 = n2 + 7 * n3;
                            ++n3;
                            final ca m2 = l.M(itemStack, n14, n - 16.0);
                            final ItemStack itemStack2 = k;
                            final double n15 = n2 - 7.0 + 7 * n3;
                            ++n3;
                            m2.D(itemStack2, n15, n - 16.0).M(com.client.glowclient.ca::M);
                            final double n16 = n2 + 2.0 + n13 * 14;
                            double n17 = n - 3.0;
                            final List<X> m3;
                            if ((m3 = Ea.M(k.getEnchantmentTagList())) != null) {
                                final Iterator<X> iterator7 = m3.iterator();
                                Label_1033:
                                while (true) {
                                    Iterator<X> iterator8 = iterator7;
                                    while (iterator8.hasNext()) {
                                        Ia.M(HUD.F, iterator7.next().M(), n16, n17, true, ga.G, 0.5);
                                        final double n18 = n17 -= Ia.M(HUD.F, 0.5) + 1.0;
                                        if (n18 > n18) {
                                            continue Label_1033;
                                        }
                                        iterator8 = iterator7;
                                    }
                                    break;
                                }
                            }
                            j = ++n13;
                        }
                        continue Label_0031;
                    }
                    iterator2 = iterator;
                }
            }
            break;
        }
    }
    
    public PlayerList() {
        super(Category.RENDER, "PlayerList", false, -1, "Renders a list of players and information");
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
