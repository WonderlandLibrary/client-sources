/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.schemas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class Schema {
    protected final Object2IntMap<String> RECURSIVE_TYPES = new Object2IntOpenHashMap<String>();
    private final Map<String, Supplier<TypeTemplate>> TYPE_TEMPLATES = Maps.newHashMap();
    private final Map<String, Type<?>> TYPES;
    private final int versionKey;
    private final String name;
    protected final Schema parent;

    public Schema(int n, Schema schema) {
        this.versionKey = n;
        int n2 = DataFixUtils.getSubVersion(n);
        this.name = "V" + DataFixUtils.getVersion(n) + (n2 == 0 ? "" : "." + n2);
        this.parent = schema;
        this.registerTypes(this, this.registerEntities(this), this.registerBlockEntities(this));
        this.TYPES = this.buildTypes();
    }

    protected Map<String, Type<?>> buildTypes() {
        HashMap<String, Type<?>> hashMap = Maps.newHashMap();
        ArrayList<TypeTemplate> arrayList = Lists.newArrayList();
        for (Object2IntMap.Entry object2 : this.RECURSIVE_TYPES.object2IntEntrySet()) {
            arrayList.add(DSL.check((String)object2.getKey(), object2.getIntValue(), this.getTemplate((String)object2.getKey())));
        }
        TypeTemplate typeTemplate = (TypeTemplate)arrayList.stream().reduce(DSL::or).get();
        RecursiveTypeFamily recursiveTypeFamily = new RecursiveTypeFamily(this.name, typeTemplate);
        for (String string : this.TYPE_TEMPLATES.keySet()) {
            int n = (Integer)this.RECURSIVE_TYPES.getOrDefault((Object)string, (Object)-1);
            Type<?> type = n != -1 ? recursiveTypeFamily.apply(n) : this.getTemplate(string).apply(recursiveTypeFamily).apply(-1);
            hashMap.put(string, type);
        }
        return hashMap;
    }

    public Set<String> types() {
        return this.TYPES.keySet();
    }

    public Type<?> getTypeRaw(DSL.TypeReference typeReference) {
        String string = typeReference.typeName();
        return this.TYPES.computeIfAbsent(string, arg_0 -> Schema.lambda$getTypeRaw$0(string, arg_0));
    }

    public Type<?> getType(DSL.TypeReference typeReference) {
        String string = typeReference.typeName();
        Type type = this.TYPES.computeIfAbsent(string, arg_0 -> Schema.lambda$getType$1(string, arg_0));
        if (type instanceof RecursivePoint.RecursivePointType) {
            return type.findCheckedType(-1).orElseThrow(Schema::lambda$getType$2);
        }
        return type;
    }

    public TypeTemplate resolveTemplate(String string) {
        return this.TYPE_TEMPLATES.getOrDefault(string, () -> Schema.lambda$resolveTemplate$3(string)).get();
    }

    public TypeTemplate id(String string) {
        int n = (Integer)this.RECURSIVE_TYPES.getOrDefault((Object)string, (Object)-1);
        if (n != -1) {
            return DSL.id(n);
        }
        return this.getTemplate(string);
    }

    protected TypeTemplate getTemplate(String string) {
        return DSL.named(string, this.resolveTemplate(string));
    }

    public Type<?> getChoiceType(DSL.TypeReference typeReference, String string) {
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.findChoiceType(typeReference);
        if (!taggedChoiceType.types().containsKey(string)) {
            throw new IllegalArgumentException("Data fixer not registered for: " + string + " in " + typeReference.typeName());
        }
        return taggedChoiceType.types().get(string);
    }

    public TaggedChoice.TaggedChoiceType<?> findChoiceType(DSL.TypeReference typeReference) {
        return this.getType(typeReference).findChoiceType("id", -1).orElseThrow(Schema::lambda$findChoiceType$4);
    }

    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        this.parent.registerTypes(schema, map, map2);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        return this.parent.registerEntities(schema);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        return this.parent.registerBlockEntities(schema);
    }

    public void registerSimple(Map<String, Supplier<TypeTemplate>> map, String string) {
        this.register(map, string, DSL::remainder);
    }

    public void register(Map<String, Supplier<TypeTemplate>> map, String string, Function<String, TypeTemplate> function) {
        this.register(map, string, () -> Schema.lambda$register$5(function, string));
    }

    public void register(Map<String, Supplier<TypeTemplate>> map, String string, Supplier<TypeTemplate> supplier) {
        map.put(string, supplier);
    }

    public void registerType(boolean bl, DSL.TypeReference typeReference, Supplier<TypeTemplate> supplier) {
        this.TYPE_TEMPLATES.put(typeReference.typeName(), supplier);
        if (bl && !this.RECURSIVE_TYPES.containsKey(typeReference.typeName())) {
            this.RECURSIVE_TYPES.put(typeReference.typeName(), this.RECURSIVE_TYPES.size());
        }
    }

    public int getVersionKey() {
        return this.versionKey;
    }

    public Schema getParent() {
        return this.parent;
    }

    private static TypeTemplate lambda$register$5(Function function, String string) {
        return (TypeTemplate)function.apply(string);
    }

    private static IllegalArgumentException lambda$findChoiceType$4() {
        return new IllegalArgumentException("Not a choice type");
    }

    private static TypeTemplate lambda$resolveTemplate$3(String string) {
        throw new IllegalArgumentException("Unknown type: " + string);
    }

    private static IllegalStateException lambda$getType$2() {
        return new IllegalStateException("Could not find choice type in the recursive type");
    }

    private static Type lambda$getType$1(String string, String string2) {
        throw new IllegalArgumentException("Unknown type: " + string);
    }

    private static Type lambda$getTypeRaw$0(String string, String string2) {
        throw new IllegalArgumentException("Unknown type: " + string);
    }
}

