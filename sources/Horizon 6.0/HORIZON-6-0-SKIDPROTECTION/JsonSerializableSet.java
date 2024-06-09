package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.common.collect.Sets;
import java.util.Set;
import com.google.common.collect.ForwardingSet;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable
{
    private final Set HorizonCode_Horizon_È;
    private static final String Â = "CL_00001482";
    
    public JsonSerializableSet() {
        this.HorizonCode_Horizon_È = Sets.newHashSet();
    }
    
    public void HorizonCode_Horizon_È(final JsonElement p_152753_1_) {
        if (p_152753_1_.isJsonArray()) {
            for (final JsonElement var3 : p_152753_1_.getAsJsonArray()) {
                this.add((Object)var3.getAsString());
            }
        }
    }
    
    public JsonElement HorizonCode_Horizon_È() {
        final JsonArray var1 = new JsonArray();
        for (final String var3 : this) {
            var1.add((JsonElement)new JsonPrimitive(var3));
        }
        return (JsonElement)var1;
    }
    
    protected Set delegate() {
        return this.delegate();
    }
}
