package net.augustus.material;

import net.augustus.modules.Categorys;
import net.augustus.utils.skid.tomorrow.AnimationUtils;

public class Category {
    public Categorys moduleType;
    public boolean needRemove;
    public AnimationUtils rollAnim2 = new AnimationUtils();
    float anim;
    public boolean show;
    public float modsY2 = 0;
    public float modsY3 = 0;

    public Category(Categorys mt, int i, boolean b) {
        this.moduleType = mt;
        this.anim = i;
        this.show = b;
    }
}
