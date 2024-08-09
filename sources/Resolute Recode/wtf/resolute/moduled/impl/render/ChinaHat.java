package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.WorldEvent;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@ModuleAnontion(name = "China Hat", type = Categories.Render,server = "")
public class ChinaHat extends Module {
}
