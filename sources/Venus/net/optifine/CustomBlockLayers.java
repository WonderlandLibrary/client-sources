/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.BlockAliases;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomBlockLayers {
    private static RenderType[] renderLayers = null;
    public static boolean active = false;

    public static RenderType getRenderLayer(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        if (renderLayers == null) {
            return null;
        }
        if (blockState.isOpaqueCube(iBlockReader, blockPos)) {
            return null;
        }
        int n = blockState.getBlockId();
        return n > 0 && n < renderLayers.length ? renderLayers[n] : null;
    }

    public static void update() {
        PropertiesOrdered propertiesOrdered;
        renderLayers = null;
        active = false;
        ArrayList<RenderType> arrayList = new ArrayList<RenderType>();
        String string = "optifine/block.properties";
        Properties properties = ResUtils.readProperties(string, "CustomBlockLayers");
        if (properties != null) {
            CustomBlockLayers.readLayers(string, properties, arrayList);
        }
        if (Config.isShaders() && (propertiesOrdered = BlockAliases.getBlockLayerPropertes()) != null) {
            String string2 = "shaders/block.properties";
            CustomBlockLayers.readLayers(string2, propertiesOrdered, arrayList);
        }
        if (!arrayList.isEmpty()) {
            renderLayers = arrayList.toArray(new RenderType[arrayList.size()]);
            active = true;
        }
    }

    private static void readLayers(String string, Properties properties, List<RenderType> list) {
        Config.dbg("CustomBlockLayers: " + string);
        CustomBlockLayers.readLayer("solid", RenderTypes.SOLID, properties, list);
        CustomBlockLayers.readLayer("cutout", RenderTypes.CUTOUT, properties, list);
        CustomBlockLayers.readLayer("cutout_mipped", RenderTypes.CUTOUT_MIPPED, properties, list);
        CustomBlockLayers.readLayer("translucent", RenderTypes.TRANSLUCENT, properties, list);
    }

    private static void readLayer(String string, RenderType renderType, Properties properties, List<RenderType> list) {
        ConnectedParser connectedParser;
        MatchBlock[] matchBlockArray;
        String string2 = "layer." + string;
        String string3 = properties.getProperty(string2);
        if (string3 != null && (matchBlockArray = (connectedParser = new ConnectedParser("CustomBlockLayers")).parseMatchBlocks(string3)) != null) {
            for (int i = 0; i < matchBlockArray.length; ++i) {
                MatchBlock matchBlock = matchBlockArray[i];
                int n = matchBlock.getBlockId();
                if (n <= 0) continue;
                while (list.size() < n + 1) {
                    list.add(null);
                }
                if (list.get(n) != null) {
                    Config.warn("CustomBlockLayers: Block layer is already set, block: " + n + ", layer: " + string);
                }
                list.set(n, renderType);
            }
        }
    }

    public static boolean isActive() {
        return active;
    }
}

