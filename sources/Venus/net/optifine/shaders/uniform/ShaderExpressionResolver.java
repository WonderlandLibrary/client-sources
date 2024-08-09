/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.uniform.ShaderParameterBool;
import net.optifine.shaders.uniform.ShaderParameterFloat;
import net.optifine.shaders.uniform.ShaderParameterIndexed;
import net.optifine.util.BiomeUtils;

public class ShaderExpressionResolver
implements IExpressionResolver {
    private Map<String, IExpression> mapExpressions = new HashMap<String, IExpression>();

    public ShaderExpressionResolver(Map<String, IExpression> map) {
        this.registerExpressions();
        for (String string : map.keySet()) {
            IExpression iExpression = map.get(string);
            this.registerExpression(string, iExpression);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void registerExpressions() {
        void var4_11;
        Object object;
        Object object2;
        ShaderParameterFloat[] shaderParameterFloatArray = ShaderParameterFloat.values();
        for (int i = 0; i < shaderParameterFloatArray.length; ++i) {
            ShaderParameterFloat shaderParameterFloat = shaderParameterFloatArray[i];
            this.addParameterFloat(this.mapExpressions, shaderParameterFloat);
        }
        ShaderParameterBool[] shaderParameterBoolArray = ShaderParameterBool.values();
        for (int i = 0; i < shaderParameterBoolArray.length; ++i) {
            ShaderParameterBool object3 = shaderParameterBoolArray[i];
            this.mapExpressions.put(object3.getName(), object3);
        }
        for (ResourceLocation rainTypeArray : BiomeUtils.getLocations()) {
            object2 = rainTypeArray.getPath().trim();
            object2 = "BIOME_" + ((String)object2).toUpperCase().replace(' ', '_');
            int n = BiomeUtils.getId(rainTypeArray);
            object = new ConstantFloat(n);
            this.registerExpression((String)object2, (IExpression)object);
        }
        Biome.Category[] categoryArray = Biome.Category.values();
        boolean bl = false;
        while (var4_11 < categoryArray.length) {
            object2 = categoryArray[var4_11];
            String string = "CAT_" + ((Biome.Category)object2).getString().toUpperCase();
            object = new ConstantFloat((float)var4_11);
            this.registerExpression(string, (IExpression)object);
            ++var4_11;
        }
        Biome.RainType[] rainTypeArray = Biome.RainType.values();
        for (int i = 0; i < rainTypeArray.length; ++i) {
            Biome.RainType rainType = rainTypeArray[i];
            object = "PPT_" + rainType.getString().toUpperCase();
            ConstantFloat constantFloat = new ConstantFloat(i);
            this.registerExpression((String)object, constantFloat);
        }
    }

    private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat shaderParameterFloat) {
        String[] stringArray = shaderParameterFloat.getIndexNames1();
        if (stringArray == null) {
            map.put(shaderParameterFloat.getName(), new ShaderParameterIndexed(shaderParameterFloat));
        } else {
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                String[] stringArray2 = shaderParameterFloat.getIndexNames2();
                if (stringArray2 == null) {
                    map.put(shaderParameterFloat.getName() + "." + string, new ShaderParameterIndexed(shaderParameterFloat, i));
                    continue;
                }
                for (int j = 0; j < stringArray2.length; ++j) {
                    String string2 = stringArray2[j];
                    map.put(shaderParameterFloat.getName() + "." + string + "." + string2, new ShaderParameterIndexed(shaderParameterFloat, i, j));
                }
            }
        }
    }

    public boolean registerExpression(String string, IExpression iExpression) {
        if (this.mapExpressions.containsKey(string)) {
            SMCLog.warning("Expression already defined: " + string);
            return true;
        }
        this.mapExpressions.put(string, iExpression);
        return false;
    }

    @Override
    public IExpression getExpression(String string) {
        return this.mapExpressions.get(string);
    }

    public boolean hasExpression(String string) {
        return this.mapExpressions.containsKey(string);
    }
}

