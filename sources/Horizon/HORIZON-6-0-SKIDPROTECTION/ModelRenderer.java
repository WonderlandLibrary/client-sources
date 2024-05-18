package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class ModelRenderer
{
    public float HorizonCode_Horizon_È;
    public float Â;
    private int Ø­à;
    private int µÕ;
    public float Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    public float Ó;
    public float à;
    public float Ø;
    private boolean Æ;
    private int Šáƒ;
    public boolean áŒŠÆ;
    public boolean áˆºÑ¢Õ;
    public boolean ÂµÈ;
    public List á;
    public List ˆÏ­;
    public final String £á;
    private ModelBase Ï­Ðƒà;
    public float Å;
    public float £à;
    public float µà;
    private static final String áŒŠà = "CL_00000874";
    public List ˆà;
    public boolean ¥Æ;
    
    public ModelRenderer(final ModelBase p_i1172_1_, final String p_i1172_2_) {
        this.ˆà = new ArrayList();
        this.¥Æ = false;
        this.HorizonCode_Horizon_È = 64.0f;
        this.Â = 32.0f;
        this.áˆºÑ¢Õ = true;
        this.á = Lists.newArrayList();
        this.Ï­Ðƒà = p_i1172_1_;
        p_i1172_1_.Ø.add(this);
        this.£á = p_i1172_2_;
        this.Â(p_i1172_1_.áŒŠÆ, p_i1172_1_.áˆºÑ¢Õ);
    }
    
    public ModelRenderer(final ModelBase p_i1173_1_) {
        this(p_i1173_1_, null);
    }
    
    public ModelRenderer(final ModelBase p_i46358_1_, final int p_i46358_2_, final int p_i46358_3_) {
        this(p_i46358_1_);
        this.HorizonCode_Horizon_È(p_i46358_2_, p_i46358_3_);
    }
    
    public void HorizonCode_Horizon_È(final ModelRenderer p_78792_1_) {
        if (this.ˆÏ­ == null) {
            this.ˆÏ­ = Lists.newArrayList();
        }
        this.ˆÏ­.add(p_78792_1_);
    }
    
    public ModelRenderer HorizonCode_Horizon_È(final int p_78784_1_, final int p_78784_2_) {
        this.Ø­à = p_78784_1_;
        this.µÕ = p_78784_2_;
        return this;
    }
    
    public ModelRenderer HorizonCode_Horizon_È(String p_78786_1_, final float p_78786_2_, final float p_78786_3_, final float p_78786_4_, final int p_78786_5_, final int p_78786_6_, final int p_78786_7_) {
        p_78786_1_ = String.valueOf(this.£á) + "." + p_78786_1_;
        final TextureOffset var8 = this.Ï­Ðƒà.HorizonCode_Horizon_È(p_78786_1_);
        this.HorizonCode_Horizon_È(var8.HorizonCode_Horizon_È, var8.Â);
        this.á.add(new ModelBox(this, this.Ø­à, this.µÕ, p_78786_2_, p_78786_3_, p_78786_4_, p_78786_5_, p_78786_6_, p_78786_7_, 0.0f).HorizonCode_Horizon_È(p_78786_1_));
        return this;
    }
    
    public ModelRenderer HorizonCode_Horizon_È(final float p_78789_1_, final float p_78789_2_, final float p_78789_3_, final int p_78789_4_, final int p_78789_5_, final int p_78789_6_) {
        this.á.add(new ModelBox(this, this.Ø­à, this.µÕ, p_78789_1_, p_78789_2_, p_78789_3_, p_78789_4_, p_78789_5_, p_78789_6_, 0.0f));
        return this;
    }
    
    public ModelRenderer HorizonCode_Horizon_È(final float p_178769_1_, final float p_178769_2_, final float p_178769_3_, final int p_178769_4_, final int p_178769_5_, final int p_178769_6_, final boolean p_178769_7_) {
        this.á.add(new ModelBox(this, this.Ø­à, this.µÕ, p_178769_1_, p_178769_2_, p_178769_3_, p_178769_4_, p_178769_5_, p_178769_6_, 0.0f, p_178769_7_));
        return this;
    }
    
    public void HorizonCode_Horizon_È(final float p_78790_1_, final float p_78790_2_, final float p_78790_3_, final int p_78790_4_, final int p_78790_5_, final int p_78790_6_, final float p_78790_7_) {
        this.á.add(new ModelBox(this, this.Ø­à, this.µÕ, p_78790_1_, p_78790_2_, p_78790_3_, p_78790_4_, p_78790_5_, p_78790_6_, p_78790_7_));
    }
    
    public void HorizonCode_Horizon_È(final float p_78793_1_, final float p_78793_2_, final float p_78793_3_) {
        this.Ý = p_78793_1_;
        this.Ø­áŒŠá = p_78793_2_;
        this.Âµá€ = p_78793_3_;
    }
    
    public void HorizonCode_Horizon_È(final float p_78785_1_) {
        if (!this.ÂµÈ && this.áˆºÑ¢Õ) {
            if (!this.Æ) {
                this.Ø­áŒŠá(p_78785_1_);
            }
            GlStateManager.Â(this.Å, this.£à, this.µà);
            if (this.Ó == 0.0f && this.à == 0.0f && this.Ø == 0.0f) {
                if (this.Ý == 0.0f && this.Ø­áŒŠá == 0.0f && this.Âµá€ == 0.0f) {
                    GlStateManager.ˆÏ­(this.Šáƒ);
                    if (this.ˆÏ­ != null) {
                        for (int var2 = 0; var2 < this.ˆÏ­.size(); ++var2) {
                            this.ˆÏ­.get(var2).HorizonCode_Horizon_È(p_78785_1_);
                        }
                    }
                }
                else {
                    GlStateManager.Â(this.Ý * p_78785_1_, this.Ø­áŒŠá * p_78785_1_, this.Âµá€ * p_78785_1_);
                    GlStateManager.ˆÏ­(this.Šáƒ);
                    if (this.ˆÏ­ != null) {
                        for (int var2 = 0; var2 < this.ˆÏ­.size(); ++var2) {
                            this.ˆÏ­.get(var2).HorizonCode_Horizon_È(p_78785_1_);
                        }
                    }
                    GlStateManager.Â(-this.Ý * p_78785_1_, -this.Ø­áŒŠá * p_78785_1_, -this.Âµá€ * p_78785_1_);
                }
            }
            else {
                GlStateManager.Çªà¢();
                GlStateManager.Â(this.Ý * p_78785_1_, this.Ø­áŒŠá * p_78785_1_, this.Âµá€ * p_78785_1_);
                if (this.Ø != 0.0f) {
                    GlStateManager.Â(this.Ø * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.à != 0.0f) {
                    GlStateManager.Â(this.à * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.Ó != 0.0f) {
                    GlStateManager.Â(this.Ó * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GlStateManager.ˆÏ­(this.Šáƒ);
                if (this.ˆÏ­ != null) {
                    for (int var2 = 0; var2 < this.ˆÏ­.size(); ++var2) {
                        this.ˆÏ­.get(var2).HorizonCode_Horizon_È(p_78785_1_);
                    }
                }
                GlStateManager.Ê();
            }
            GlStateManager.Â(-this.Å, -this.£à, -this.µà);
        }
    }
    
    public void Â(final float p_78791_1_) {
        if (!this.ÂµÈ && this.áˆºÑ¢Õ) {
            if (!this.Æ) {
                this.Ø­áŒŠá(p_78791_1_);
            }
            GlStateManager.Çªà¢();
            GlStateManager.Â(this.Ý * p_78791_1_, this.Ø­áŒŠá * p_78791_1_, this.Âµá€ * p_78791_1_);
            if (this.à != 0.0f) {
                GlStateManager.Â(this.à * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.Ó != 0.0f) {
                GlStateManager.Â(this.Ó * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.Ø != 0.0f) {
                GlStateManager.Â(this.Ø * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.ˆÏ­(this.Šáƒ);
            GlStateManager.Ê();
        }
    }
    
    public void Ý(final float p_78794_1_) {
        if (!this.ÂµÈ && this.áˆºÑ¢Õ) {
            if (!this.Æ) {
                this.Ø­áŒŠá(p_78794_1_);
            }
            if (this.Ó == 0.0f && this.à == 0.0f && this.Ø == 0.0f) {
                if (this.Ý != 0.0f || this.Ø­áŒŠá != 0.0f || this.Âµá€ != 0.0f) {
                    GlStateManager.Â(this.Ý * p_78794_1_, this.Ø­áŒŠá * p_78794_1_, this.Âµá€ * p_78794_1_);
                }
            }
            else {
                GlStateManager.Â(this.Ý * p_78794_1_, this.Ø­áŒŠá * p_78794_1_, this.Âµá€ * p_78794_1_);
                if (this.Ø != 0.0f) {
                    GlStateManager.Â(this.Ø * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.à != 0.0f) {
                    GlStateManager.Â(this.à * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.Ó != 0.0f) {
                    GlStateManager.Â(this.Ó * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }
    
    private void Ø­áŒŠá(final float p_78788_1_) {
        GL11.glNewList(this.Šáƒ = GLAllocation.HorizonCode_Horizon_È(1), 4864);
        final WorldRenderer var2 = Tessellator.HorizonCode_Horizon_È().Ý();
        for (int i = 0; i < this.á.size(); ++i) {
            this.á.get(i).HorizonCode_Horizon_È(var2, p_78788_1_);
        }
        for (int i = 0; i < this.ˆà.size(); ++i) {
            final ModelSprite sprite = this.ˆà.get(i);
            sprite.HorizonCode_Horizon_È(Tessellator.HorizonCode_Horizon_È(), p_78788_1_);
        }
        GL11.glEndList();
        this.Æ = true;
    }
    
    public ModelRenderer Â(final int p_78787_1_, final int p_78787_2_) {
        this.HorizonCode_Horizon_È = p_78787_1_;
        this.Â = p_78787_2_;
        return this;
    }
    
    public void Â(final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ, final float sizeAdd) {
        this.ˆà.add(new ModelSprite(this, this.Ø­à, this.µÕ, posX, posY, posZ, sizeX, sizeY, sizeZ, sizeAdd));
    }
}
