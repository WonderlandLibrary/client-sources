/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import java.util.List;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class StructureProcessorList {
    private final List<StructureProcessor> field_242918_a;

    public StructureProcessorList(List<StructureProcessor> list) {
        this.field_242918_a = list;
    }

    public List<StructureProcessor> func_242919_a() {
        return this.field_242918_a;
    }

    public String toString() {
        return "ProcessorList[" + this.field_242918_a + "]";
    }
}

