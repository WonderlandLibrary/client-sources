/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class BlockListReport
implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public BlockListReport(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        JsonObject jsonObject = new JsonObject();
        for (Block block : Registry.BLOCK) {
            JsonElement jsonElement;
            JsonElement jsonElement2;
            ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
            JsonObject jsonObject2 = new JsonObject();
            StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
            if (!stateContainer.getProperties().isEmpty()) {
                jsonElement2 = new JsonObject();
                for (Property property : stateContainer.getProperties()) {
                    jsonElement = new JsonArray();
                    for (Object object : property.getAllowedValues()) {
                        ((JsonArray)jsonElement).add(Util.getValueName(property, object));
                    }
                    ((JsonObject)jsonElement2).add(property.getName(), jsonElement);
                }
                jsonObject2.add("properties", jsonElement2);
            }
            jsonElement2 = new JsonArray();
            for (BlockState blockState : stateContainer.getValidStates()) {
                Object object;
                jsonElement = new JsonObject();
                JsonObject jsonObject3 = new JsonObject();
                object = stateContainer.getProperties().iterator();
                while (object.hasNext()) {
                    Property property = (Property)object.next();
                    jsonObject3.addProperty(property.getName(), Util.getValueName(property, blockState.get(property)));
                }
                if (jsonObject3.size() > 0) {
                    ((JsonObject)jsonElement).add("properties", jsonObject3);
                }
                ((JsonObject)jsonElement).addProperty("id", Block.getStateId(blockState));
                if (blockState == block.getDefaultState()) {
                    ((JsonObject)jsonElement).addProperty("default", true);
                }
                ((JsonArray)jsonElement2).add(jsonElement);
            }
            jsonObject2.add("states", jsonElement2);
            jsonObject.add(resourceLocation.toString(), jsonObject2);
        }
        Path path = this.generator.getOutputFolder().resolve("reports/blocks.json");
        IDataProvider.save(GSON, directoryCache, jsonObject, path);
    }

    @Override
    public String getName() {
        return "Block List";
    }
}

