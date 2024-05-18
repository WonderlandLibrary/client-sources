package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;
import java.util.Collection;
import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import java.util.Map;

public class ModifiableAttributeInstance implements IAttributeInstance
{
    private final BaseAttributeMap HorizonCode_Horizon_È;
    private final IAttribute Â;
    private final Map Ý;
    private final Map Ø­áŒŠá;
    private final Map Âµá€;
    private double Ó;
    private boolean à;
    private double Ø;
    private static final String áŒŠÆ = "CL_00001567";
    
    public ModifiableAttributeInstance(final BaseAttributeMap p_i1608_1_, final IAttribute p_i1608_2_) {
        this.Ý = Maps.newHashMap();
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Âµá€ = Maps.newHashMap();
        this.à = true;
        this.HorizonCode_Horizon_È = p_i1608_1_;
        this.Â = p_i1608_2_;
        this.Ó = p_i1608_2_.Â();
        for (int var3 = 0; var3 < 3; ++var3) {
            this.Ý.put(var3, Sets.newHashSet());
        }
    }
    
    @Override
    public IAttribute HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public double Â() {
        return this.Ó;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_111128_1_) {
        if (p_111128_1_ != this.Â()) {
            this.Ó = p_111128_1_;
            this.Ó();
        }
    }
    
    @Override
    public Collection HorizonCode_Horizon_È(final int p_111130_1_) {
        return this.Ý.get(p_111130_1_);
    }
    
    @Override
    public Collection Ý() {
        final HashSet var1 = Sets.newHashSet();
        for (int var2 = 0; var2 < 3; ++var2) {
            var1.addAll(this.HorizonCode_Horizon_È(var2));
        }
        return var1;
    }
    
    @Override
    public AttributeModifier HorizonCode_Horizon_È(final UUID p_111127_1_) {
        return this.Âµá€.get(p_111127_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final AttributeModifier p_180374_1_) {
        return this.Âµá€.get(p_180374_1_.HorizonCode_Horizon_È()) != null;
    }
    
    @Override
    public void Â(final AttributeModifier p_111121_1_) {
        if (this.HorizonCode_Horizon_È(p_111121_1_.HorizonCode_Horizon_È()) != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        Object var2 = this.Ø­áŒŠá.get(p_111121_1_.Â());
        if (var2 == null) {
            var2 = Sets.newHashSet();
            this.Ø­áŒŠá.put(p_111121_1_.Â(), var2);
        }
        this.Ý.get(p_111121_1_.Ý()).add(p_111121_1_);
        ((Set)var2).add(p_111121_1_);
        this.Âµá€.put(p_111121_1_.HorizonCode_Horizon_È(), p_111121_1_);
        this.Ó();
    }
    
    protected void Ó() {
        this.à = true;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void Ý(final AttributeModifier p_111124_1_) {
        for (int var2 = 0; var2 < 3; ++var2) {
            final Set var3 = this.Ý.get(var2);
            var3.remove(p_111124_1_);
        }
        final Set var4 = this.Ø­áŒŠá.get(p_111124_1_.Â());
        if (var4 != null) {
            var4.remove(p_111124_1_);
            if (var4.isEmpty()) {
                this.Ø­áŒŠá.remove(p_111124_1_.Â());
            }
        }
        this.Âµá€.remove(p_111124_1_.HorizonCode_Horizon_È());
        this.Ó();
    }
    
    @Override
    public void Ø­áŒŠá() {
        final Collection var1 = this.Ý();
        if (var1 != null) {
            final ArrayList var2 = Lists.newArrayList((Iterable)var1);
            for (final AttributeModifier var4 : var2) {
                this.Ý(var4);
            }
        }
    }
    
    @Override
    public double Âµá€() {
        if (this.à) {
            this.Ø = this.à();
            this.à = false;
        }
        return this.Ø;
    }
    
    private double à() {
        double var1 = this.Â();
        for (final AttributeModifier var3 : this.Â(0)) {
            var1 += var3.Ø­áŒŠá();
        }
        double var4 = var1;
        for (final AttributeModifier var6 : this.Â(1)) {
            var4 += var1 * var6.Ø­áŒŠá();
        }
        for (final AttributeModifier var6 : this.Â(2)) {
            var4 *= 1.0 + var6.Ø­áŒŠá();
        }
        return this.Â.HorizonCode_Horizon_È(var4);
    }
    
    private Collection Â(final int p_180375_1_) {
        final HashSet var2 = Sets.newHashSet((Iterable)this.HorizonCode_Horizon_È(p_180375_1_));
        for (IAttribute var3 = this.Â.Ø­áŒŠá(); var3 != null; var3 = var3.Ø­áŒŠá()) {
            final IAttributeInstance var4 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3);
            if (var4 != null) {
                var2.addAll(var4.HorizonCode_Horizon_È(p_180375_1_));
            }
        }
        return var2;
    }
}
