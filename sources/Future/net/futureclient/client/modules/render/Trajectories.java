package net.futureclient.client.modules.render;

import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.futureclient.client.modules.render.trajectories.Listener1;
import net.futureclient.client.n;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemBow;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.futureclient.client.AE;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import java.util.List;
import net.futureclient.client.Ea;

public class Trajectories extends Ea
{
    private List<Class<? extends Item>> a;
    private float D;
    private float k;
    
    public static Minecraft getMinecraft() {
        return Trajectories.D;
    }
    
    public Trajectories() {
        super("Trajectories", new String[] { AE.M("27\u0007/\u0003&\u0012*\u0014,\u00036"), "Tracelines", AE.M("\u0011\u0014$\f \u00051\t7\u000f "), "Traject" }, true, -10349857, Category.RENDER);
        final int n = 1;
        (this.a = new ArrayList<Class<? extends Item>>()).add((Class<? extends Item>)ItemBow.class);
        this.a.add((Class<? extends Item>)ItemSplashPotion.class);
        this.a.add((Class<? extends Item>)ItemLingeringPotion.class);
        this.a.add((Class<? extends Item>)ItemExpBottle.class);
        this.a.add((Class<? extends Item>)ItemEnderPearl.class);
        this.a.add((Class<? extends Item>)ItemSnowball.class);
        this.a.add((Class<? extends Item>)ItemEgg.class);
        final n[] array = new n[n];
        array[0] = (n)new Listener1(this);
        this.M(array);
    }
    
    public static Minecraft getMinecraft1() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft21() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft22() {
        return Trajectories.D;
    }
    
    public static float e(final Trajectories trajectories, final float d) {
        return trajectories.D = d;
    }
    
    public static Minecraft getMinecraft23() {
        return Trajectories.D;
    }
    
    public static float e(final Trajectories trajectories) {
        return trajectories.D;
    }
    
    public static Minecraft getMinecraft24() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft25() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft27() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft28() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft30() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft33() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft34() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft35() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft36() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft37() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft38() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft39() {
        return Trajectories.D;
    }
    
    public static float M(final Trajectories trajectories) {
        return trajectories.k;
    }
    
    public static float M(final Trajectories trajectories, final float k) {
        return trajectories.k = k;
    }
    
    public static Entity M(final Trajectories trajectories, final Vec3d vec3d, final Vec3d vec3d2) {
        return trajectories.M(vec3d, vec3d2);
    }
    
    private Entity M(final Vec3d vec3d, final Vec3d vec3d2) {
        final Iterator<EntityLivingBase> iterator = this.M().iterator();
        while (iterator.hasNext()) {
            final EntityLivingBase entityLivingBase;
            if ((entityLivingBase = iterator.next()) != Trajectories.D.player) {
                final double n = 1.3262473694E-314;
                final AxisAlignedBB entityBoundingBox = entityLivingBase.getEntityBoundingBox();
                final double n2 = n;
                if (entityBoundingBox.grow(n2, n2, n2).calculateIntercept(vec3d, vec3d2) != null) {
                    return (Entity)entityLivingBase;
                }
                continue;
            }
        }
        return null;
    }
    
    public static List M(final Trajectories trajectories) {
        return trajectories.a;
    }
    
    private ArrayList<EntityLivingBase> M() {
        final ArrayList<EntityLivingBase> list = new ArrayList<EntityLivingBase>();
        final Iterator<Entity> iterator = Trajectories.D.world.loadedEntityList.iterator();
        while (iterator.hasNext()) {
            final Entity entity;
            if ((entity = iterator.next()) != Trajectories.D.player && entity instanceof EntityLivingBase) {
                list.add((EntityLivingBase)entity);
            }
        }
        return list;
    }
    
    public static Minecraft getMinecraft40() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft41() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft42() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft43() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft44() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft45() {
        return Trajectories.D;
    }
    
    public static Minecraft getMinecraft46() {
        return Trajectories.D;
    }
}
