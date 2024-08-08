package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import net.minecraft.init.Blocks;

public class IceSpeed extends Module {
    public IceSpeed(){
        super("IceSpeed", Category.MOVEMENT);
    }

    @Override
    public void onEnable(){
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        Blocks.ICE.setDefaultSlipperiness(0.4f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.4f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.4f);
    }

    @Override
    public void onDisable() {
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
    }
}
