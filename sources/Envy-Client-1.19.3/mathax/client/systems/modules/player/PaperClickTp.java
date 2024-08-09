package mathax.client.systems.modules.player;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.KeyBind;
import mathax.client.utils.player.GotoUtil;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.client.render.Camera;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;


public class PaperClickTp extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");


    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders a block overlay where you will be teleported.")
        .defaultValue(true)
        .build()
    );
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color-solid-block")
        .description("The color of the sides of the blocks being rendered.")
        .defaultValue(new SettingColor(255, 0, 255, 15))
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color-solid-block")
        .description("The color of the lines of the blocks being rendered.")
        .defaultValue(new SettingColor(255, 0, 255, 255))
        .visible(render::get)
        .build()
    );
    //cant resolve keybind
    //classes are case-sensitive, Keybind doesn't work, but KeyBind does (notice the capital B)
    @SuppressWarnings("unused")
    private final Setting<KeyBind> cancelBlink = sgGeneral.add(new KeyBindSetting.Builder()
        .name("Keybind to tp")
        .description("Cancels sending packets and sends you back to your original position.")
        .defaultValue(KeyBind.none())
        .action(() -> {
            if (mc.world == null || mc.player == null) return;   // just to fix a waring
            Camera camera = mc.gameRenderer.getCamera();
            Vec3d cameraPos = camera.getPos();
            float pitch = camera.getPitch();
            float yaw = camera.getYaw();
            Vec3d rotationVec = Vec3d.fromPolar(pitch, yaw);
            Vec3d raycastEnd = cameraPos.add(rotationVec.multiply(300.0));
            BlockPos blockpos = mc.world.raycast(new RaycastContext(cameraPos, raycastEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player)).getBlockPos().up();
            Vec3d pos = new Vec3d((blockpos.getX() + .5), blockpos.getY(), (blockpos.getZ() + .5));
            Thread waitForTickEventThread = new Thread(() -> new GotoUtil().moveto(pos.x, pos.y, pos.z));
            waitForTickEventThread.start();
        })
        .build()
    );

    public PaperClickTp() {
        super(Categories.Player, Items.DIAMOND, "PaperClickTp", "Teleports you to the block you are looking at on paper servers.");
    }


    @EventHandler
    private void onRender(Render3DEvent event) {
        assert mc.player != null && mc.world != null;   // to fix Intellij's stupid warnings
        Camera camera = mc.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        float pitch = camera.getPitch();
        float yaw = camera.getYaw();
        Vec3d rotationVec = Vec3d.fromPolar(pitch, yaw);
        Vec3d raycastEnd = cameraPos.add(rotationVec.multiply(300.0));
        BlockHitResult pos1 = mc.world.raycast(new RaycastContext(cameraPos, raycastEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
        BlockPos location = pos1.getBlockPos();
        double x1 = location.getX();
        double y1 = location.getY() + 1;
        double z1 = location.getZ();
        double x2 = x1 + 1;
        double y2 = y1 + 1;
        double z2 = z1 + 1;


        if (render.get()) {
            event.renderer.box(x1, y1, z1, x2, y2, z2, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
        }
    }
}
