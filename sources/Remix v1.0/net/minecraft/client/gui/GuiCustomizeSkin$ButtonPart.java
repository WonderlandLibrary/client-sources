package net.minecraft.client.gui;

import net.minecraft.entity.player.*;

class ButtonPart extends GuiButton
{
    private final EnumPlayerModelParts field_175234_p;
    
    private ButtonPart(final int p_i45514_2_, final int p_i45514_3_, final int p_i45514_4_, final int p_i45514_5_, final int p_i45514_6_, final EnumPlayerModelParts p_i45514_7_) {
        super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.access$100(GuiCustomizeSkin.this, p_i45514_7_));
        this.field_175234_p = p_i45514_7_;
    }
    
    ButtonPart(final GuiCustomizeSkin this$0, final int p_i45515_2_, final int p_i45515_3_, final int p_i45515_4_, final int p_i45515_5_, final int p_i45515_6_, final EnumPlayerModelParts p_i45515_7_, final Object p_i45515_8_) {
        this(this$0, p_i45515_2_, p_i45515_3_, p_i45515_4_, p_i45515_5_, p_i45515_6_, p_i45515_7_);
    }
}
