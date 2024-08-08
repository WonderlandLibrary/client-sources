package net.futureclient.client.modules.combat;

import java.util.Iterator;
import net.futureclient.client.ZH;
import net.futureclient.client.he;
import net.futureclient.client.eD;
import net.futureclient.client.pg;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.WI;
import net.minecraft.entity.Entity;
import net.futureclient.client.ZG;
import net.futureclient.client.events.EventMotion;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.bowaim.Listener2;
import net.futureclient.client.modules.combat.bowaim.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.minecraft.entity.EntityLivingBase;
import net.futureclient.client.Ea;

public class BowAim extends Ea
{
    public EntityLivingBase l;
    private Value<Boolean> players;
    private Value<Boolean> monsters;
    public float A;
    private Value<Boolean> armorCheck;
    public float K;
    private Value<Boolean> neutrals;
    private Value<Boolean> animals;
    private Value<Boolean> teams;
    private Value<Boolean> invisibles;
    
    public BowAim() {
        super("BowAim", new String[] { "BowAim", "Ba" }, true, -3358823, Category.COMBAT);
        this.players = new Value<Boolean>(true, new String[] { "Players", "player", "p", "player" });
        this.monsters = new Value<Boolean>(false, new String[] { "Monsters", "monster", "mon", "m", "monst" });
        this.neutrals = new Value<Boolean>(false, new String[] { "Neutrals", "Passive", "Passives", "Neutral", "neu", "n" });
        this.animals = new Value<Boolean>(false, new String[] { "Animals", "ani", "animal" });
        this.invisibles = new Value<Boolean>(true, new String[] { "Invisibles", "invis", "inv", "invisible" });
        this.armorCheck = new Value<Boolean>(true, new String[] { "ArmorCheck", "Armored", "Armor" });
        this.teams = new Value<Boolean>(false, new String[] { "Teams", "Team", "tems" });
        this.M(new Value[] { this.players, this.monsters, this.neutrals, this.animals, this.invisibles, this.armorCheck, this.teams });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public void B() {
        final float a = 0.0f;
        final float k = 0.0f;
        super.B();
        this.K = k;
        this.A = a;
    }
    
    public static Minecraft getMinecraft() {
        return BowAim.D;
    }
    
    public static Minecraft getMinecraft1() {
        return BowAim.D;
    }
    
    public static Minecraft getMinecraft2() {
        return BowAim.D;
    }
    
    public static Minecraft getMinecraft3() {
        return BowAim.D;
    }
    
    private float[] M(final EntityLivingBase entityLivingBase) {
        final double n = entityLivingBase.posX - BowAim.D.player.posX;
        final double n2 = entityLivingBase.posY - BowAim.D.player.posY + entityLivingBase.getEyeHeight() / 1.4f;
        final double n3 = entityLivingBase.posZ - BowAim.D.player.posZ;
        final double n4 = n;
        final double n5 = n4 * n4;
        final double n6 = n3;
        return new float[] { (float)(Math.atan2(n3, n) * 0.0 / 6.984873503E-315) - 90.0f, (float)(-(Math.atan2(n2, Math.sqrt(n5 + n6 * n6)) * 0.0 / 6.984873503E-315)) };
    }
    
    private float M(final EntityLivingBase entityLivingBase, final double n, final double n2) {
        final double n3 = entityLivingBase.posY + entityLivingBase.getEyeHeight() / 2.0f - (BowAim.D.player.posY + BowAim.D.player.getEyeHeight());
        final double n4 = entityLivingBase.posX - BowAim.D.player.posX;
        final double n5 = entityLivingBase.posZ - BowAim.D.player.posZ;
        final double n6 = n4;
        final double n7 = n6 * n6;
        final double n8 = n5;
        return this.M(n, n2, Math.sqrt(n7 + n8 * n8), n3);
    }
    
    public static void M(final BowAim bowAim, final EventMotion eventMotion, final double n, final double n2) {
        bowAim.M(eventMotion, n, n2);
    }
    
    public static EntityLivingBase M(final BowAim bowAim, final EventMotion eventMotion, final float n) {
        return bowAim.M(eventMotion, n);
    }
    
    private boolean M(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase == null || !ZG.b((Entity)entityLivingBase) || !BowAim.D.player.canEntityBeSeen((Entity)entityLivingBase)) {
            return false;
        }
        if (WI.C((Entity)entityLivingBase)) {
            return this.monsters.M();
        }
        if (WI.i((Entity)entityLivingBase)) {
            return this.neutrals.M();
        }
        if (WI.g((Entity)entityLivingBase)) {
            return this.animals.M();
        }
        if (!(entityLivingBase instanceof EntityPlayer) || !this.players.M()) {
            return false;
        }
        final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
        final AntiBots antiBots;
        if ((antiBots = (AntiBots)pg.M().M().M((Class)eD.class)).M() && antiBots.K.containsKey(entityLivingBase.getEntityId())) {
            return false;
        }
        if (entityPlayer.getEntityId() == -1337) {
            return false;
        }
        if (entityPlayer.equals((Object)BowAim.D.player) || entityPlayer.capabilities.isCreativeMode) {
            return false;
        }
        if (this.teams.M() && ZG.M((Entity)entityPlayer)) {
            return false;
        }
        if (this.armorCheck.M() && !ZG.M(entityPlayer)) {
            return false;
        }
        if (entityPlayer.isInvisible()) {
            return this.invisibles.M();
        }
        return !pg.M().M().M(entityPlayer.getName());
    }
    
    private EntityLivingBase M(final EventMotion eventMotion, final float n) {
        switch (he.k[eventMotion.M().ordinal()]) {
            case 1: {
                float n2 = n;
                EntityLivingBase entityLivingBase = null;
                final Iterator<Entity> iterator2;
                Iterator<Entity> iterator = iterator2 = BowAim.D.world.loadedEntityList.iterator();
                while (iterator.hasNext()) {
                    final Entity entity;
                    if (!((entity = iterator2.next()) instanceof EntityLivingBase)) {
                        iterator = iterator2;
                    }
                    else {
                        final EntityLivingBase entityLivingBase2 = (EntityLivingBase)entity;
                        if (!this.M(entityLivingBase2)) {
                            iterator = iterator2;
                        }
                        else {
                            final float[] m = this.M(entityLivingBase2);
                            final float e = ZH.e(eventMotion.e(), m[0]);
                            final float e2 = ZH.e(eventMotion.b(), m[1]);
                            if (e > n) {
                                iterator = iterator2;
                            }
                            else if (e2 > n) {
                                iterator = iterator2;
                            }
                            else {
                                final float n3;
                                if ((n3 = (e + e2) / 2.0f) > n2) {
                                    iterator = iterator2;
                                }
                                else {
                                    n2 = n3;
                                    entityLivingBase = entityLivingBase2;
                                    iterator = iterator2;
                                }
                            }
                        }
                    }
                }
                return entityLivingBase;
            }
            default:
                return null;
        }
    }
    
    private float M(double atan2, final double n, final double n2, double n3) {
        final double n4 = 0.0 * n3;
        final double n5 = atan2;
        n3 = n4 * (n5 * n5);
        n3 = n * (n * (n2 * n2) + n3);
        final double n6 = atan2;
        n3 = Math.sqrt(n6 * n6 * atan2 * atan2 - n3);
        final double n7 = atan2;
        final double n8 = n7 * n7 + n3;
        final double n9 = atan2;
        atan2 = n9 * n9 - n3;
        n3 = Math.atan2(n8, n * n2);
        atan2 = Math.atan2(atan2, n * n2);
        return (float)Math.min(n3, atan2);
    }
    
    private void M(final EventMotion eventMotion, double n, double n2) {
        switch (he.k[eventMotion.M().ordinal()]) {
            case 1: {
                final float k;
                if (Double.isNaN(k = (float)(-Math.toDegrees(this.M(this.l, n, n2))))) {
                    return;
                }
                n = this.l.posX - this.l.lastTickPosX;
                n2 = this.l.posZ - this.l.lastTickPosZ;
                final double n3 = BowAim.D.player.getDistance((Entity)this.l);
                final double n4 = n3 - n3 % 0.0;
                final boolean sprinting = this.l.isSprinting();
                n = n4 / 0.0 * n * (sprinting ? 1.697596633E-314 : 1.273197475E-314);
                n2 = n4 / 0.0 * n2 * (sprinting ? 1.697596633E-314 : 1.273197475E-314);
                n = this.l.posX + n - BowAim.D.player.posX;
                eventMotion.M(this.A = (float)Math.toDegrees(Math.atan2(this.l.posZ + n2 - BowAim.D.player.posZ, n)) - 90.0f);
                eventMotion.e(this.K = k);
                break;
            }
        }
    }
}
