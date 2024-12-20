package net.minecraft.client.resources.data;

import java.lang.reflect.Type;

import com.google.gson.*;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection> implements JsonSerializer<PackMetadataSection> {
	@Override
	public PackMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
		JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
		ITextComponent itextcomponent = (ITextComponent) p_deserialize_3_.deserialize(jsonobject.get("description"), ITextComponent.class);

		if (itextcomponent == null) {
			throw new JsonParseException("Invalid/missing description!");
		} else {
			int i = JsonUtils.getInt(jsonobject, "pack_format");
			return new PackMetadataSection(itextcomponent, i);
		}
	}

	@Override
	public JsonElement serialize(PackMetadataSection p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
		JsonObject jsonobject = new JsonObject();
		jsonobject.addProperty("pack_format", Integer.valueOf(p_serialize_1_.getPackFormat()));
		jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getPackDescription()));
		return jsonobject;
	}

	/**
	 * The name of this section type as it appears in JSON.
	 */
	@Override
	public String getSectionName() {
		return "pack";
	}
}
