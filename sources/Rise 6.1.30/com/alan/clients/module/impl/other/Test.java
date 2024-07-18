package com.alan.clients.module.impl.other;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.ClickEvent;
import com.alan.clients.event.impl.input.GuiClickEvent;
import com.alan.clients.event.impl.input.GuiMouseReleaseEvent;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.*;
import com.alan.clients.event.impl.other.*;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.event.impl.render.MouseOverEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.EvictingList;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.pathfinding.custom.PathFinder;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.impl.CurveValue;
import com.alan.clients.value.impl.DragValue;
import com.alan.clients.value.impl.StringValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Tuple;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.alan.clients.module.impl.other.data.Equations.EUCLIDEAN_DISTANCE;
import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(aliases = {"module.other.test.name"}, description = "module.other.test.description", category = Category.MOVEMENT)
public final class Test extends Module {

    private final ArrayList<Integer> integerArrayList = new ArrayList<>();
    private final ArrayList<Double> doubleArrayList = new ArrayList<>();
    private final ArrayList<Float> floatArrayList = new ArrayList<>();
    private final ArrayList<Packet<?>> packetArrayOutList = new ArrayList<>();
    private final ArrayList<Packet<?>> packetArrayInList = new ArrayList<>();
    private int anInt, anInt2;
    private boolean aBoolean, aBoolean2, delay;
    private double aDouble, aDouble2, serverPosX, serverPosY, serverPosZ;
    private float aFloat, aFloat2;
    private Vec3 lastPosition, currentPosition, lastCurrentPosition;
    private Entity entity;
    private Packet<?> aPacket;
    private EntityOtherPlayerMP otherEntity;

    private final List<Packet<?>> packets = new ArrayList<>();
    private final ConcurrentLinkedQueue<PacketUtil.TimedPacket> timedPackets = new ConcurrentLinkedQueue<>();
    private final StopWatch timerUtil = new StopWatch();
    private double startPosY, distance, moveSpeed, y, lastX, lastY, lastZ;
    private int stage, bestBlockStack;
    public static boolean set, doFly;
    private BlockPos startPos;
    private final DecimalFormat format = new DecimalFormat("0.0");
    private Vec3 targetBlock;
    private BlockPos blockFace;
    private final DragValue positionValue = new DragValue("Position", this, new Vector2d(255, 255));
    public Vec3 position = new Vec3(0, 0, 0);
    private final StringValue runIf = new StringValue("Run If () ->", this, "onGround");
    private final CurveValue curve = new CurveValue("Test Curve", this);
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Vector3d> positions = new ArrayList<>();
    private World world;
    private boolean reset;
    private double speed;
    private boolean placeHolder;

    //    private final List<Double> jumpValues = new ArrayList<Double>() {{
//        add(0.42); +
//        add(0.33319999363422365);
//        add(0.24813599859094576);
//        add(0.16477328182606651);
//        add(0.08307781780646721);
//        add(0.0030162615090425808);
//        add(-0.0784000015258789);
//        add(-0.1552320045166016);
//        add(-0.230527368912964);
//        add(-0.30431682745754424);
//        add(-0.37663049823865513);
//        add(-0.44749789698341763);
//    }};
//
//    private final List<Double> speedValues = new ArrayList<Double>() {{
//        add(0.3023122842180768);
//        add(0.2982909636320639);
//        add(0.2946315653119493);
//        add(0.29130151576857954);
//        add(0.2882711731879136);
//        add(0.285513563574277);
//        add(0.283004140640921);
//        add(0.2807205673107283);
//        add(0.27864251688221625);
//        add(0.2767514920910006);
//        add(0.27503066045614655);
//        add(0.27346470444576965);
//    }};

    //    0.03684800296020513
//    -0.042288957922058
//    -0.11984318109609361
//    0.11760000228881837
//    0.03684800296020513
//    -0.042288957922058
//    -0.11984318109609361
//    0.11760000228881837
//    0.03684800296020513

    //0.40444491418477924

//
//                    case "1":
//    v = -0.0784000015258789;
//                                break;
//                            case "2":
//    v = -0.09800000190734864;
//                                break;
//                            case "3":
//    v = -0.09800000190735147;
//                                break;

//        for (int i = 0; i < 3; i++) {
//        double position = startingLocationY;
//        for (double value : jumpValues) {
//            if (value == 0.42) value = 0.42f;
//            position += value;
//
//            if (position < startingLocationY) position = startingLocationY;
//            PacketUtil.sendPacketWithoutEvent(new C03PacketPlayer.C04PacketPlayerPosition(startingLocationX, position, startingLocationZ, false));
//        }
//    }
//        PacketUtil.sendPacketWithoutEvent(new C03PacketPlayer.C04PacketPlayerPosition(startingLocationX,startingLocationY,startingLocationZ, true));
//

    //    Font font = Fonts.SF_ROUNDED.get(15);
    Executor threadPool = Executors.newFixedThreadPool(1);

    PathFinder pathFinder;
    EntityPlayerSP entityPlayerSP;
    EvictingList<BlockPos> blockPoses = new EvictingList<>(2);

    private final HashMap<Double, HashMap<Integer, Tuple<Double, Boolean>>> strafeMap = new HashMap<>();
    private Runnable success, failure;

    @Override
    public void onDisable() {
        // get the average of doublearraylist
        double average = 0;
        for (double vale : doubleArrayList) average += vale;
        average = average / doubleArrayList.size();

//        ChatUtil.display("Average Damage " + average + " " + doubleArrayList.size());

        placeHolder = true;

//        ChatUtil.display("Received " + anInt);
//        ChatUtil.display("Attacked " + anInt2);

        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
        mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
        mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
        mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
    }

    List<Vector2d> controlPoints = new ArrayList<>();
    Vector2d selected = null;

    @Override
    public void onEnable() {
        doubleArrayList.clear();
        placeHolder = true;
        aBoolean = false;
        blockFace = null;
        blockPoses.clear();
//        mc.thePlayer.setPosition(Math.floor(mc.thePlayer.posX) + 0.3000001, mc.thePlayer.posY, mc.thePlayer.posZ);

        anInt = 0;
        anInt2 = 0;
//        otherEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.gameProfile);
//        otherEntity.setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0));
//        Client.INSTANCE.getBotManager().add(this, otherEntity);
//        mc.theWorld.addEntityToWorld(-9999, otherEntity);
//        otherEntity.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);

//        Class c = Speed.class;
//        String className = c.getName();
//        String classAsPath = className.replace('.', '/') + ".class";
//        InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath);
//
//        try {
//            ArrayList<Byte> bytes = new ArrayList<>();
//            int data = stream.read();
//
//            while (data != -1) {
//                bytes.add((byte) data);
//
//                data = stream.read();
//            }
//
//            byte[] byteArray = new byte[bytes.size()];
//            for (int i = 0; i < bytes.size(); i++) {
//                byteArray[i] = bytes.get(i);
//            }
//
////            Client.INSTANCE.getModuleManager().get(c).getClass().getClassLoader().getClass().getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class)
////                    .invoke(Client.INSTANCE.getModuleManager().get(c).getClass().getClassLoader(), c.getName(), byteArray, 0, byteArray.length);
//
////            ReflectionUtil.accessMethod("defineClass", c.getClassLoader().getClass())
////                    .invoke(Client.INSTANCE.getModuleManager().get(c).getClass().getClassLoader(), c.getName(), byteArray, 0, byteArray.length);
//
//            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
//            defineClass.setAccessible(true);
//            defineClass.invoke(Client.INSTANCE.getModuleManager().get(c).getClass().getClassLoader(), c.getName(), byteArray, 0, byteArray.length);
//            System.out.println(new String(byteArray));
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }

        controlPoints.clear();
        controlPoints.add(new Vector2d(100, 500));
        controlPoints.add(new Vector2d(200, 100));
        controlPoints.add(new Vector2d(600, 100));
        controlPoints.add(new Vector2d(700, 500));
    }

    @EventLink
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        placeHolder = true;
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> receive = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<PacketSendEvent> send = event -> {
        Packet<?> packet = event.getPacket();
        placeHolder = true;
    };

    @EventLink
    public final Listener<TeleportEvent> teleport = event -> {

        placeHolder = true;
    };

    @EventLink
    public final Listener<BlockAABBEvent> blockAABB = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<Render2DEvent> render2D = event -> {
        placeHolder = true;

        if (selected != null) {
            Vector2d mouse = MouseUtil.mouse();
            selected.setX((float) mouse.getX());
            selected.setY((float) mouse.getY());
        }

        renderBezierCurve(controlPoints);
    };

    private void renderBezierCurve(List<Vector2d> controlPoints) {
        for (Vector2d p : controlPoints) {
            RenderUtil.circle(p.x, p.y, 3, getTheme().getFirstColor());
        }

        // Clear buffers and set up OpenGL state
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);

        // Set the color to white directly
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // RGBA for white color

        // Begin drawing
        glLineWidth(2.0f);
        glBegin(GL_LINE_STRIP);

        // Draw your line or curve
        for (float t = 0; t <= 1.0; t += 0.01f) {
            Vector2d p = evaluateBezierCurve(t, controlPoints);
            glVertex2f((float) p.x, (float) p.y);
        }

        // End drawing
        glEnd();

        // Disable unnecessary states after rendering
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);

        RenderUtil.color(Color.WHITE);
    }

    private Vector2d evaluateBezierCurve(float percentage, List<Vector2d> points) {
        float inversePercentage = 1 - percentage;

        float x = (float) (Math.pow(inversePercentage, 3) * points.get(0).x + 3 * Math.pow(inversePercentage, 2) * percentage * points.get(1).x + 3 * (inversePercentage) * Math.pow(percentage, 2) * points.get(2).x + Math.pow(percentage, 3) * points.get(3).x);

        float y = (float) (Math.pow(inversePercentage, 3) * points.get(0).y + 3 * Math.pow(inversePercentage, 2) * percentage * points.get(1).y + 3 * (inversePercentage) * Math.pow(percentage, 2) * points.get(2).y + Math.pow(percentage, 3) * points.get(3).y);

        return new Vector2d(x, y);
    }

    @EventLink
    public final Listener<Render3DEvent> render3D = event -> {
        placeHolder = true;
    };

    private HashMap<Integer, Integer> deltas = new HashMap<>();

    public static double myass;

    @EventLink()
    public final Listener<PreUpdateEvent> preUpdate = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<WaterEvent> water = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<AttackEvent> attack = event -> {
        anInt2++;
        placeHolder = true;
    };

    @EventLink
    public final Listener<WorldChangeEvent> worldChange = event -> {
        blockFace = null;
        placeHolder = true;
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        placeHolder = true;
    };

    public void run() {
        if (mc.gameSettings.keyBindSneak.isKeyDown()) deltas.clear();
        if (RayCastUtil.rayCast(RotationComponent.rotations, 15).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            aFloat = mc.thePlayer.rotationYaw;
            aFloat2 = mc.thePlayer.rotationPitch;
            return;
        }

        double delta = Math.hypot(mc.thePlayer.rotationYaw - aFloat, mc.thePlayer.rotationPitch - aFloat2);
        int rounded = (int) Math.round(delta);

        deltas.putIfAbsent(rounded, 1);
        deltas.put(rounded, deltas.get(rounded) + 1);

        ChatUtil.display("Outputted");
        deltas.forEach((savedDelta, savedOccurrences) -> {
            System.out.println(savedOccurrences + "\t" + savedDelta);
        });

        aFloat = mc.thePlayer.rotationYaw;
        aFloat2 = mc.thePlayer.rotationPitch;
    }

    @EventLink
    public final Listener<GuiMouseReleaseEvent> onMouseRelease = event -> {
        selected = null;
    };

    @EventLink
    public final Listener<GuiClickEvent> onGUIClick = event -> {
        int mouseX = event.getMouseX();
        int mouseY = event.getMouseY();

        ArrayList<Vector2d> sorted = new ArrayList<>(controlPoints);
        sorted.sort((one, two) -> (int) (EUCLIDEAN_DISTANCE.run(one.getX() - mouseX, one.getY() - mouseY) -
                EUCLIDEAN_DISTANCE.run(two.getX() - mouseX, two.getY() - mouseY)));
        selected = sorted.stream().findFirst().get();
    };

    @EventLink
    public final Listener<ClickEvent> onClickEvent = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotionEvent = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        placeHolder = true;
    };

    public final Listener<JumpEvent> onJump = event -> {
        placeHolder = true;
    };

    @EventLink
    public final Listener<MoveInputEvent> onMoveInput = event -> {
        placeHolder = true;
        event.setSneakSlowDownMultiplier(mc.thePlayer.ticksExisted % 2 == 0 ? event.getSneakSlowDownMultiplier() : 1);
    };
}
