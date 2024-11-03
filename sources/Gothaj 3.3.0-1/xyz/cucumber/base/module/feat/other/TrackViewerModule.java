package xyz.cucumber.base.module.feat.other;

import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays music on screen",
   name = "Track Viewer",
   priority = ArrayPriority.LOW
)
public class TrackViewerModule extends Mod {
}
