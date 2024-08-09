package dev.luvbeeq.litematica.data;

import dev.luvbeeq.litematica.placement.SchematicPlacementManager;

public class DataManager {
    public static final DataManager INSTANCE = new DataManager();
    private final SchematicPlacementManager schematicPlacementManager = new SchematicPlacementManager();

    private static DataManager getInstance() {
        return INSTANCE;
    }

    public static SchematicPlacementManager getSchematicPlacementManager() {
        return getInstance().schematicPlacementManager;
    }
}