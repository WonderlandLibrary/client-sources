package wtf.diablo.client.module.impl.misc;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.potion.PotionEffect;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "Fake Player", description = "Spawns a fake player", category = ModuleCategoryEnum.MISC)
public final class FakePlayerModule extends AbstractModule {
    private int ID;


    @Override
    protected void onEnable() {
        super.onEnable();
        EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        entityOtherPlayerMP.clonePlayer(mc.thePlayer, true);
        entityOtherPlayerMP.copyLocationAndAnglesFrom(mc.thePlayer);

        for (final PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
            entityOtherPlayerMP.addPotionEffect(potionEffect);
        }

        entityOtherPlayerMP.rotationYawHead = mc.thePlayer.rotationYawHead;

        if (this.ID != -100) {
            this.ID = -100;
        }
        mc.theWorld.addEntityToWorld(this.ID, entityOtherPlayerMP);
    }

    @Override
    protected void onDisable() {
        super.onDisable();

        if (this.ID != -100) {
            this.ID = -100;
        }
        mc.theWorld.removeEntityFromWorld(this.ID);
    }
}
