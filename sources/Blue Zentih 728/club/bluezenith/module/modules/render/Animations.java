package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.*;

public class Animations extends Module {
    public static Animations module;
    public final ModeValue anim = new ModeValue("Animation", "1.7", true, null, "1.7", "Exhibition", "Bruh", "Spin", "Rotating", "Swank").setIndex(1);
    public final FloatValue itemPosX = new FloatValue( "ItemPosX",0, -100, 100, 1, true, null).setIndex(2);
    public final FloatValue itemPosY = new FloatValue( "ItemPosY",0, -100, 100, 1, true, null).setIndex(3);
    public final FloatValue itemPosZ = new FloatValue( "ItemPosZ",0, -100, 100, 1, true, null).setIndex(4);
    public final FloatValue itemScale = new FloatValue( "ItemScale",1, 0.1f, 5, 0.05F, true, null).setIndex(5);
    public final BooleanValue slowSwing = new BooleanValue("SlowSwing", false, true).setIndex(10);
    public final BooleanValue slowSwingBlock = new BooleanValue("SlowSwingBlock", false, false, slowSwing::get).setIndex(11);
    public final IntegerValue slowSwingValue = new IntegerValue("SlowSwingValue", 50, 0,100,1,true, slowSwing::get).setIndex(12);
    @SuppressWarnings("unused")
    private final ActionValue reset = new ActionValue("Reset",() -> {
        this.anim.set("1.7");
        this.itemPosX.set(0f);
        this.itemPosY.set(0f);
        this.itemPosZ.set(0f);
        this.itemScale.set(1F);
        this.slowSwing.set(false);
        this.slowSwingValue.set(0);
    }).setIndex(13);

    public Animations() {
        super("Animations", ModuleCategory.RENDER, "animations", "anim");
        module = this;
    }

    @Override
    public String getTag() {
        return anim.get();
    }
}
