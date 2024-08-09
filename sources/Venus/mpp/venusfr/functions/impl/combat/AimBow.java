/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name="AimBow", type=Category.Combat)
public class AimBow
extends Function {
    private final BooleanSetting smoothness = new BooleanSetting("\u041f\u043b\u0430\u0432\u043d\u0430\u044f \u043d\u0430\u0432\u043e\u0434\u043a\u0430", false);
    private final BooleanSetting targetPlayers = new BooleanSetting("\u0418\u0433\u0440\u043e\u043a\u0438", true);
    private final BooleanSetting targetMobs = new BooleanSetting("\u041c\u043e\u0431\u044b", false);
    private final BooleanSetting useBow = new BooleanSetting("\u041b\u0443\u043a", false);
    private final BooleanSetting trident = new BooleanSetting("\u0422\u0440\u0435\u0437\u0443\u0431\u0435\u0446", true);
    private final BooleanSetting useCrossbow = new BooleanSetting("\u0410\u0440\u0431\u0430\u043b\u0435\u0442", false);
    private final SliderSetting distanceSlider = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 16.0f, 4.0f, 140.0f, 1.0f);
    private final SliderSetting correctionSlider = new SliderSetting("\u041a\u043e\u0440\u0440\u0435\u043a\u0446\u0438\u044f \u043f\u043e Y", 1.5f, 0.0f, 5.0f, 0.1f);

    public AimBow() {
        this.addSettings(this.smoothness, this.trident, this.targetPlayers, this.targetMobs, this.useBow, this.useCrossbow, this.distanceSlider, this.correctionSlider);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        Minecraft minecraft;
        block7: {
            block5: {
                block6: {
                    minecraft = mc;
                    if (!AimBow.mc.player.isHandActive()) break block5;
                    if (!((Boolean)this.useBow.get()).booleanValue()) break block6;
                    minecraft = mc;
                    if (AimBow.mc.player.getActiveItemStack().getItem() instanceof BowItem) break block7;
                }
                if (!((Boolean)this.useCrossbow.get()).booleanValue()) break block5;
                minecraft = mc;
                if (AimBow.mc.player.getActiveItemStack().getItem() instanceof CrossbowItem) break block7;
            }
            minecraft = mc;
            if (!AimBow.mc.player.isHandActive() || !((Boolean)this.trident.get()).booleanValue()) {
                return;
            }
            minecraft = mc;
            if (!(AimBow.mc.player.getActiveItemStack().getItem() instanceof TridentItem)) {
                return;
            }
        }
        minecraft = mc;
        this.aimAtNearestEntity(AimBow.mc.player);
    }

    public void aimAtNearestEntity(LivingEntity livingEntity) {
        double d = Double.MAX_VALUE;
        Entity entity2 = null;
        Iterator<Entity> iterator2 = livingEntity.world.getEntitiesWithinAABBExcludingEntity(livingEntity, livingEntity.getBoundingBox().grow(((Float)this.distanceSlider.get()).floatValue())).iterator();
        while (true) {
            double d2;
            double d3;
            if (!iterator2.hasNext()) {
                if (entity2 != null) {
                    double d4 = entity2.getPosX();
                    double d5 = livingEntity.getPosX();
                    double d6 = d4 - d5;
                    d4 = entity2.getPosY() + (double)entity2.getEyeHeight() + (double)((Float)this.correctionSlider.get()).floatValue() * 1.0;
                    d5 = livingEntity.getPosYEye() + (double)livingEntity.getEyeHeight();
                    d3 = d4 - d5;
                    d4 = entity2.getPosZ();
                    d5 = livingEntity.getPosZ();
                    double d7 = d4 - d5;
                    double d8 = MathHelper.sqrt(d6 * d6 + d7 * d7);
                    float f = (float)(MathHelper.atan2(d7, d6) * 57.29577951308232);
                    float f2 = f - 90.0f;
                    float f3 = (float)(-(MathHelper.atan2(d3, d8) * 57.29577951308232));
                    if (((Boolean)this.smoothness.get()).booleanValue()) {
                        livingEntity.rotationYawHead = MathHelper.lerp(0.32f, livingEntity.rotationYawHead, f2);
                        livingEntity.rotationPitchHead = MathHelper.lerp(0.32f, livingEntity.rotationPitchHead, f3);
                        livingEntity.rotationYaw = MathHelper.lerp(0.32f, livingEntity.rotationYaw, f2);
                        livingEntity.rotationPitch = MathHelper.lerp(0.32f, livingEntity.rotationPitch, f3);
                    } else {
                        livingEntity.rotationYawHead = f2;
                        livingEntity.rotationPitchHead = f3;
                        livingEntity.rotationYaw = f2;
                        livingEntity.rotationPitch = f3;
                    }
                }
                return;
            }
            Entity entity3 = iterator2.next();
            if (!(entity3 instanceof LivingEntity) || (!(entity3 instanceof PlayerEntity) || !((Boolean)this.targetPlayers.get()).booleanValue()) && (entity3 instanceof PlayerEntity || !((Boolean)this.targetMobs.get()).booleanValue())) continue;
            d3 = livingEntity.getDistanceSq(entity3);
            if (!(d2 < d) || !livingEntity.canEntityBeSeen(entity3)) continue;
            d = d3;
            entity2 = entity3;
        }
    }
}

