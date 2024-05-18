package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.lwjgl.opengl.Display;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Mouse;
import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;

public class Input
{
    public static final int HorizonCode_Horizon_È = -1;
    private static final int ÇŽØ = 100;
    public static final int Â = 1;
    public static final int Ý = 2;
    public static final int Ø­áŒŠá = 3;
    public static final int Âµá€ = 4;
    public static final int Ó = 5;
    public static final int à = 6;
    public static final int Ø = 7;
    public static final int áŒŠÆ = 8;
    public static final int áˆºÑ¢Õ = 9;
    public static final int ÂµÈ = 10;
    public static final int á = 11;
    public static final int ˆÏ­ = 12;
    public static final int £á = 13;
    public static final int Å = 14;
    public static final int £à = 15;
    public static final int µà = 16;
    public static final int ˆà = 17;
    public static final int ¥Æ = 18;
    public static final int Ø­à = 19;
    public static final int µÕ = 20;
    public static final int Æ = 21;
    public static final int Šáƒ = 22;
    public static final int Ï­Ðƒà = 23;
    public static final int áŒŠà = 24;
    public static final int ŠÄ = 25;
    public static final int Ñ¢á = 26;
    public static final int ŒÏ = 27;
    public static final int Çªà¢ = 28;
    public static final int Ê = 28;
    public static final int ÇŽÉ = 29;
    public static final int ˆá = 30;
    public static final int ÇŽÕ = 31;
    public static final int É = 32;
    public static final int áƒ = 33;
    public static final int á€ = 34;
    public static final int Õ = 35;
    public static final int à¢ = 36;
    public static final int ŠÂµà = 37;
    public static final int ¥à = 38;
    public static final int Âµà = 39;
    public static final int Ç = 40;
    public static final int È = 41;
    public static final int áŠ = 42;
    public static final int ˆáŠ = 43;
    public static final int áŒŠ = 44;
    public static final int £ÂµÄ = 45;
    public static final int Ø­Âµ = 46;
    public static final int Ä = 47;
    public static final int Ñ¢Â = 48;
    public static final int Ï­à = 49;
    public static final int áˆºáˆºÈ = 50;
    public static final int ÇŽá€ = 51;
    public static final int Ï = 52;
    public static final int Ô = 53;
    public static final int ÇªÓ = 54;
    public static final int áˆºÏ = 55;
    public static final int ˆáƒ = 56;
    public static final int Œ = 57;
    public static final int £Ï = 58;
    public static final int Ø­á = 59;
    public static final int ˆÉ = 60;
    public static final int Ï­Ï­Ï = 61;
    public static final int £Â = 62;
    public static final int £Ó = 63;
    public static final int ˆÐƒØ­à = 64;
    public static final int £Õ = 65;
    public static final int Ï­Ô = 66;
    public static final int Œà = 67;
    public static final int Ðƒá = 68;
    public static final int ˆÏ = 69;
    public static final int áˆºÇŽØ = 70;
    public static final int ÇªÂµÕ = 71;
    public static final int áŒŠÏ = 72;
    public static final int áŒŠáŠ = 73;
    public static final int ˆÓ = 74;
    public static final int ¥Ä = 75;
    public static final int ÇªÔ = 76;
    public static final int Û = 77;
    public static final int ŠÓ = 78;
    public static final int ÇŽá = 79;
    public static final int Ñ¢à = 80;
    public static final int ÇªØ­ = 81;
    public static final int £áŒŠá = 82;
    public static final int áˆº = 83;
    public static final int Šà = 87;
    public static final int áŒŠá€ = 88;
    public static final int ¥Ï = 100;
    public static final int ˆà¢ = 101;
    public static final int Ñ¢Ç = 102;
    public static final int £É = 112;
    public static final int Ðƒáƒ = 121;
    public static final int Ðƒà = 123;
    public static final int ¥É = 125;
    public static final int £ÇªÓ = 141;
    public static final int ÂµÕ = 144;
    public static final int Š = 145;
    public static final int Ø­Ñ¢á€ = 146;
    public static final int Ñ¢Ó = 147;
    public static final int Ø­Æ = 148;
    public static final int áŒŠÔ = 149;
    public static final int ŠÕ = 150;
    public static final int £Ø­à = 151;
    public static final int µÐƒáƒ = 156;
    public static final int áŒŠÕ = 157;
    public static final int ÂµÂ = 179;
    public static final int áŒŠá = 181;
    public static final int ˆØ = 183;
    public static final int áˆºà = 184;
    public static final int ÐƒÂ = 197;
    public static final int £áƒ = 199;
    public static final int Ï­áˆºÓ = 200;
    public static final int Çª = 201;
    public static final int ÇŽÄ = 203;
    public static final int ˆÈ = 205;
    public static final int ˆÅ = 207;
    public static final int ÇªÉ = 208;
    public static final int ŠÏ­áˆºá = 209;
    public static final int ÇŽà = 210;
    public static final int ŠáˆºÂ = 211;
    public static final int Ø­Ñ¢Ï­Ø­áˆº = 219;
    public static final int ŒÂ = 220;
    public static final int Ï­Ï = 221;
    public static final int ŠØ = 222;
    public static final int ˆÐƒØ = 223;
    public static final int Çªà = 56;
    public static final int ¥Å = 184;
    private static final int ŒÓ = 0;
    private static final int ÇŽÊ = 1;
    private static final int µ = 2;
    private static final int µÏ = 3;
    private static final int µÐƒÓ = 4;
    private static final int ¥áŒŠà = 5;
    private static final int ˆÂ = 6;
    private static final int áŒŠÈ = 7;
    private static final int ˆØ­áˆº = 8;
    private static final int £Ô = 9;
    private static final int ŠÏ = 10;
    private static final int ˆ = 11;
    private static final int ŠÑ¢Ó = 12;
    private static final int áˆºá = 13;
    public static final int Œáƒ = 0;
    public static final int Œá = 1;
    public static final int µÂ = 2;
    private static boolean Ï­Ó;
    private static ArrayList ŠáŒŠà¢;
    private int Ñ¢È;
    private int Çªáˆºá;
    protected boolean[] Ñ¢ÇŽÏ;
    private boolean[][] ˆÕ;
    protected char[] ÇªÂ;
    protected boolean[] ÂµáˆºÂ;
    protected long[] ¥Âµá€;
    private boolean[][] ÇªÈ;
    protected boolean ÇŽÈ;
    protected HashSet ÇªáˆºÕ;
    protected ArrayList Ï­Ä;
    protected ArrayList ¥áŠ;
    protected ArrayList µÊ;
    protected ArrayList áˆºáˆºáŠ;
    protected ArrayList áŒŠÉ;
    private int ÇªÅ;
    private int ÇŽ;
    private boolean ÇŽÅ;
    private boolean ¥Ðƒá;
    private int ÐƒÇŽà;
    private int ¥Ê;
    private boolean ÐƒÓ;
    private float áˆºÕ;
    private float ŒÐƒà;
    private float ÐƒáˆºÄ;
    private float áˆºÉ;
    private int Ø­È;
    private long Ñ¢Õ;
    private int Ø­à¢;
    private int áŒŠÓ;
    private int Ø­Â;
    private int ¥ÇªÅ;
    private int áˆºÓ;
    private int ÂµÊ;
    
    static {
        Input.Ï­Ó = false;
        Input.ŠáŒŠà¢ = new ArrayList();
    }
    
    public static void HorizonCode_Horizon_È() {
        Input.Ï­Ó = true;
    }
    
    public Input(final int height) {
        this.Ñ¢ÇŽÏ = new boolean[10];
        this.ˆÕ = new boolean[100][100];
        this.ÇªÂ = new char[1024];
        this.ÂµáˆºÂ = new boolean[1024];
        this.¥Âµá€ = new long[1024];
        this.ÇªÈ = new boolean[10][110];
        this.ÇŽÈ = false;
        this.ÇªáˆºÕ = new HashSet();
        this.Ï­Ä = new ArrayList();
        this.¥áŠ = new ArrayList();
        this.µÊ = new ArrayList();
        this.áˆºáˆºáŠ = new ArrayList();
        this.áŒŠÉ = new ArrayList();
        this.ÇŽÅ = true;
        this.áˆºÕ = 1.0f;
        this.ŒÐƒà = 1.0f;
        this.ÐƒáˆºÄ = 0.0f;
        this.áˆºÉ = 0.0f;
        this.Ø­È = 250;
        this.Ñ¢Õ = 0L;
        this.¥ÇªÅ = -1;
        this.áˆºÓ = -1;
        this.ÂµÊ = 5;
        this.Ý(height);
    }
    
    public void HorizonCode_Horizon_È(final int delay) {
        this.Ø­È = delay;
    }
    
    public void Â(final int mouseClickTolerance) {
        this.ÂµÊ = mouseClickTolerance;
    }
    
    public void HorizonCode_Horizon_È(final float scaleX, final float scaleY) {
        this.áˆºÕ = scaleX;
        this.ŒÐƒà = scaleY;
    }
    
    public void Â(final float xoffset, final float yoffset) {
        this.ÐƒáˆºÄ = xoffset;
        this.áˆºÉ = yoffset;
    }
    
    public void Â() {
        this.Â(0.0f, 0.0f);
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
    }
    
    public void HorizonCode_Horizon_È(final InputListener listener) {
        this.HorizonCode_Horizon_È((KeyListener)listener);
        this.HorizonCode_Horizon_È((MouseListener)listener);
        this.HorizonCode_Horizon_È((ControllerListener)listener);
    }
    
    public void HorizonCode_Horizon_È(final KeyListener listener) {
        this.¥áŠ.add(listener);
    }
    
    private void Ý(final KeyListener listener) {
        if (this.Ï­Ä.contains(listener)) {
            return;
        }
        this.Ï­Ä.add(listener);
        this.ÇªáˆºÕ.add(listener);
    }
    
    public void HorizonCode_Horizon_È(final MouseListener listener) {
        this.áˆºáˆºáŠ.add(listener);
    }
    
    private void Ý(final MouseListener listener) {
        if (this.µÊ.contains(listener)) {
            return;
        }
        this.µÊ.add(listener);
        this.ÇªáˆºÕ.add(listener);
    }
    
    public void HorizonCode_Horizon_È(final ControllerListener listener) {
        if (this.áŒŠÉ.contains(listener)) {
            return;
        }
        this.áŒŠÉ.add(listener);
        this.ÇªáˆºÕ.add(listener);
    }
    
    public void Ý() {
        this.Ø­áŒŠá();
        this.Âµá€();
        this.Ó();
    }
    
    public void Ø­áŒŠá() {
        this.ÇªáˆºÕ.removeAll(this.Ï­Ä);
        this.Ï­Ä.clear();
    }
    
    public void Âµá€() {
        this.ÇªáˆºÕ.removeAll(this.µÊ);
        this.µÊ.clear();
    }
    
    public void Ó() {
        this.ÇªáˆºÕ.removeAll(this.áŒŠÉ);
        this.áŒŠÉ.clear();
    }
    
    public void Â(final InputListener listener) {
        this.Ý(listener);
        this.Ï­Ä.add(0, listener);
        this.µÊ.add(0, listener);
        this.áŒŠÉ.add(0, listener);
        this.ÇªáˆºÕ.add(listener);
    }
    
    public void Ý(final InputListener listener) {
        this.Â((KeyListener)listener);
        this.Â((MouseListener)listener);
        this.Â((ControllerListener)listener);
    }
    
    public void Â(final KeyListener listener) {
        this.Ï­Ä.remove(listener);
        if (!this.µÊ.contains(listener) && !this.áŒŠÉ.contains(listener)) {
            this.ÇªáˆºÕ.remove(listener);
        }
    }
    
    public void Â(final ControllerListener listener) {
        this.áŒŠÉ.remove(listener);
        if (!this.µÊ.contains(listener) && !this.Ï­Ä.contains(listener)) {
            this.ÇªáˆºÕ.remove(listener);
        }
    }
    
    public void Â(final MouseListener listener) {
        this.µÊ.remove(listener);
        if (!this.áŒŠÉ.contains(listener) && !this.Ï­Ä.contains(listener)) {
            this.ÇªáˆºÕ.remove(listener);
        }
    }
    
    void Ý(final int height) {
        this.ÇŽ = height;
        this.Ñ¢È = this.á();
        this.Çªáˆºá = this.ˆÏ­();
    }
    
    public static String Ø­áŒŠá(final int code) {
        return Keyboard.getKeyName(code);
    }
    
    public boolean Âµá€(final int code) {
        if (this.ÂµáˆºÂ[code]) {
            this.ÂµáˆºÂ[code] = false;
            return true;
        }
        return false;
    }
    
    public boolean Ó(final int button) {
        if (this.Ñ¢ÇŽÏ[button]) {
            this.Ñ¢ÇŽÏ[button] = false;
            return true;
        }
        return false;
    }
    
    public boolean à(final int button) {
        return this.HorizonCode_Horizon_È(button, 0);
    }
    
    public boolean HorizonCode_Horizon_È(final int button, final int controller) {
        if (this.ˆÕ[controller][button]) {
            this.ˆÕ[controller][button] = false;
            return true;
        }
        return false;
    }
    
    public void à() {
        for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
            Arrays.fill(this.ˆÕ[i], false);
        }
    }
    
    public void Ø() {
        Arrays.fill(this.ÂµáˆºÂ, false);
    }
    
    public void áŒŠÆ() {
        Arrays.fill(this.Ñ¢ÇŽÏ, false);
    }
    
    public boolean Ø(final int code) {
        return Keyboard.isKeyDown(code);
    }
    
    public int áˆºÑ¢Õ() {
        return Mouse.getX();
    }
    
    public int ÂµÈ() {
        return this.ÇŽ - Mouse.getY();
    }
    
    public int á() {
        return (int)(Mouse.getX() * this.áˆºÕ + this.ÐƒáˆºÄ);
    }
    
    public int ˆÏ­() {
        return (int)((this.ÇŽ - Mouse.getY()) * this.ŒÐƒà + this.áˆºÉ);
    }
    
    public boolean áŒŠÆ(final int button) {
        return Mouse.isButtonDown(button);
    }
    
    private boolean Æ() {
        for (int i = 0; i < 3; ++i) {
            if (Mouse.isButtonDown(i)) {
                return true;
            }
        }
        return false;
    }
    
    public int £á() {
        try {
            this.Å();
        }
        catch (SlickException e) {
            throw new RuntimeException("Failed to initialise controllers");
        }
        return Input.ŠáŒŠà¢.size();
    }
    
    public int áˆºÑ¢Õ(final int controller) {
        return Input.ŠáŒŠà¢.get(controller).getAxisCount();
    }
    
    public float Â(final int controller, final int axis) {
        return Input.ŠáŒŠà¢.get(controller).getAxisValue(axis);
    }
    
    public String Ý(final int controller, final int axis) {
        return Input.ŠáŒŠà¢.get(controller).getAxisName(axis);
    }
    
    public boolean ÂµÈ(final int controller) {
        if (controller >= this.£á()) {
            return false;
        }
        if (controller == -1) {
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                if (this.ÂµÈ(i)) {
                    return true;
                }
            }
            return false;
        }
        return Input.ŠáŒŠà¢.get(controller).getXAxisValue() < -0.5f || Input.ŠáŒŠà¢.get(controller).getPovX() < -0.5f;
    }
    
    public boolean á(final int controller) {
        if (controller >= this.£á()) {
            return false;
        }
        if (controller == -1) {
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                if (this.á(i)) {
                    return true;
                }
            }
            return false;
        }
        return Input.ŠáŒŠà¢.get(controller).getXAxisValue() > 0.5f || Input.ŠáŒŠà¢.get(controller).getPovX() > 0.5f;
    }
    
    public boolean ˆÏ­(final int controller) {
        if (controller >= this.£á()) {
            return false;
        }
        if (controller == -1) {
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                if (this.ˆÏ­(i)) {
                    return true;
                }
            }
            return false;
        }
        return Input.ŠáŒŠà¢.get(controller).getYAxisValue() < -0.5f || Input.ŠáŒŠà¢.get(controller).getPovY() < -0.5f;
    }
    
    public boolean £á(final int controller) {
        if (controller >= this.£á()) {
            return false;
        }
        if (controller == -1) {
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                if (this.£á(i)) {
                    return true;
                }
            }
            return false;
        }
        return Input.ŠáŒŠà¢.get(controller).getYAxisValue() > 0.5f || Input.ŠáŒŠà¢.get(controller).getPovY() > 0.5f;
    }
    
    public boolean Ø­áŒŠá(final int index, final int controller) {
        if (controller >= this.£á()) {
            return false;
        }
        if (controller == -1) {
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                if (this.Ø­áŒŠá(index, i)) {
                    return true;
                }
            }
            return false;
        }
        return Input.ŠáŒŠà¢.get(controller).isButtonPressed(index);
    }
    
    public boolean Å(final int controller) {
        return this.Ø­áŒŠá(0, controller);
    }
    
    public boolean £à(final int controller) {
        return this.Ø­áŒŠá(1, controller);
    }
    
    public boolean µà(final int controller) {
        return this.Ø­áŒŠá(2, controller);
    }
    
    public void Å() throws SlickException {
        if (Input.Ï­Ó) {
            return;
        }
        Input.Ï­Ó = true;
        try {
            Controllers.create();
            for (int count = Controllers.getControllerCount(), i = 0; i < count; ++i) {
                final Controller controller = Controllers.getController(i);
                if (controller.getButtonCount() >= 3 && controller.getButtonCount() < 100) {
                    Input.ŠáŒŠà¢.add(controller);
                }
            }
            Log.Ý("Found " + Input.ŠáŒŠà¢.size() + " controllers");
            for (int i = 0; i < Input.ŠáŒŠà¢.size(); ++i) {
                Log.Ý(String.valueOf(i) + " : " + Input.ŠáŒŠà¢.get(i).getName());
            }
        }
        catch (LWJGLException e) {
            if (e.getCause() instanceof ClassNotFoundException) {
                throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
            }
            throw new SlickException("Unable to create controllers");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {}
    }
    
    public void £à() {
        this.ÇŽÈ = true;
    }
    
    private int HorizonCode_Horizon_È(final int key, final char c) {
        if (c == '=' || key == 0) {
            return 13;
        }
        return key;
    }
    
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        if (this.Ñ¢Õ == 0L) {
            this.Ø­à¢ = x;
            this.áŒŠÓ = y;
            this.Ø­Â = button;
            this.Ñ¢Õ = System.currentTimeMillis() + this.Ø­È;
            this.HorizonCode_Horizon_È(button, x, y, 1);
        }
        else if (this.Ø­Â == button && System.currentTimeMillis() < this.Ñ¢Õ) {
            this.HorizonCode_Horizon_È(button, x, y, 2);
            this.Ñ¢Õ = 0L;
        }
    }
    
    public void Âµá€(final int width, final int height) {
        if (this.ÐƒÓ) {
            this.à();
            this.Ø();
            this.áŒŠÆ();
            while (Keyboard.next()) {}
            while (Mouse.next()) {}
            return;
        }
        if (!Display.isActive()) {
            this.à();
            this.Ø();
            this.áŒŠÆ();
        }
        for (int i = 0; i < this.¥áŠ.size(); ++i) {
            this.Ý(this.¥áŠ.get(i));
        }
        this.¥áŠ.clear();
        for (int i = 0; i < this.áˆºáˆºáŠ.size(); ++i) {
            this.Ý(this.áˆºáˆºáŠ.get(i));
        }
        this.áˆºáˆºáŠ.clear();
        if (this.Ñ¢Õ != 0L && System.currentTimeMillis() > this.Ñ¢Õ) {
            this.Ñ¢Õ = 0L;
        }
        this.ÇŽ = height;
        for (final ControlledInputReciever listener : this.ÇªáˆºÕ) {
            listener.Âµá€();
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                final int eventKey = this.HorizonCode_Horizon_È(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                this.ÇªÂ[eventKey] = Keyboard.getEventCharacter();
                this.ÂµáˆºÂ[eventKey] = true;
                this.¥Âµá€[eventKey] = System.currentTimeMillis() + this.ÐƒÇŽà;
                this.ÇŽÈ = false;
                for (int j = 0; j < this.Ï­Ä.size(); ++j) {
                    final KeyListener listener2 = this.Ï­Ä.get(j);
                    if (listener2.Ý()) {
                        listener2.HorizonCode_Horizon_È(eventKey, Keyboard.getEventCharacter());
                        if (this.ÇŽÈ) {
                            break;
                        }
                    }
                }
            }
            else {
                final int eventKey = this.HorizonCode_Horizon_È(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                this.¥Âµá€[eventKey] = 0L;
                this.ÇŽÈ = false;
                for (int j = 0; j < this.Ï­Ä.size(); ++j) {
                    final KeyListener listener2 = this.Ï­Ä.get(j);
                    if (listener2.Ý()) {
                        listener2.Â(eventKey, this.ÇªÂ[eventKey]);
                        if (this.ÇŽÈ) {
                            break;
                        }
                    }
                }
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() >= 0) {
                if (Mouse.getEventButtonState()) {
                    this.ÇŽÈ = false;
                    this.Ñ¢ÇŽÏ[Mouse.getEventButton()] = true;
                    this.¥ÇªÅ = (int)(this.ÐƒáˆºÄ + Mouse.getEventX() * this.áˆºÕ);
                    this.áˆºÓ = (int)(this.áˆºÉ + (height - Mouse.getEventY()) * this.ŒÐƒà);
                    for (int k = 0; k < this.µÊ.size(); ++k) {
                        final MouseListener listener3 = this.µÊ.get(k);
                        if (listener3.Ý()) {
                            listener3.HorizonCode_Horizon_È(Mouse.getEventButton(), this.¥ÇªÅ, this.áˆºÓ);
                            if (this.ÇŽÈ) {
                                break;
                            }
                        }
                    }
                }
                else {
                    this.ÇŽÈ = false;
                    this.Ñ¢ÇŽÏ[Mouse.getEventButton()] = false;
                    final int releasedX = (int)(this.ÐƒáˆºÄ + Mouse.getEventX() * this.áˆºÕ);
                    final int releasedY = (int)(this.áˆºÉ + (height - Mouse.getEventY()) * this.ŒÐƒà);
                    if (this.¥ÇªÅ != -1 && this.áˆºÓ != -1 && Math.abs(this.¥ÇªÅ - releasedX) < this.ÂµÊ && Math.abs(this.áˆºÓ - releasedY) < this.ÂµÊ) {
                        this.HorizonCode_Horizon_È(Mouse.getEventButton(), releasedX, releasedY);
                        final int n = -1;
                        this.áˆºÓ = n;
                        this.¥ÇªÅ = n;
                    }
                    for (int l = 0; l < this.µÊ.size(); ++l) {
                        final MouseListener listener4 = this.µÊ.get(l);
                        if (listener4.Ý()) {
                            listener4.Â(Mouse.getEventButton(), releasedX, releasedY);
                            if (this.ÇŽÈ) {
                                break;
                            }
                        }
                    }
                }
            }
            else {
                if (Mouse.isGrabbed() && this.ÇŽÅ && (Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0)) {
                    this.ÇŽÈ = false;
                    for (int k = 0; k < this.µÊ.size(); ++k) {
                        final MouseListener listener3 = this.µÊ.get(k);
                        if (listener3.Ý()) {
                            if (this.Æ()) {
                                listener3.Â(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                            }
                            else {
                                listener3.HorizonCode_Horizon_È(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                            }
                            if (this.ÇŽÈ) {
                                break;
                            }
                        }
                    }
                }
                final int dwheel = Mouse.getEventDWheel();
                this.ÇªÅ += dwheel;
                if (dwheel == 0) {
                    continue;
                }
                this.ÇŽÈ = false;
                for (int j = 0; j < this.µÊ.size(); ++j) {
                    final MouseListener listener5 = this.µÊ.get(j);
                    if (listener5.Ý()) {
                        listener5.áŒŠÆ(dwheel);
                        if (this.ÇŽÈ) {
                            break;
                        }
                    }
                }
            }
        }
        if (!this.ÇŽÅ || Mouse.isGrabbed()) {
            this.Ñ¢È = this.á();
            this.Çªáˆºá = this.ˆÏ­();
        }
        else if (this.Ñ¢È != this.á() || this.Çªáˆºá != this.ˆÏ­()) {
            this.ÇŽÈ = false;
            for (int k = 0; k < this.µÊ.size(); ++k) {
                final MouseListener listener3 = this.µÊ.get(k);
                if (listener3.Ý()) {
                    if (this.Æ()) {
                        listener3.Â(this.Ñ¢È, this.Çªáˆºá, this.á(), this.ˆÏ­());
                    }
                    else {
                        listener3.HorizonCode_Horizon_È(this.Ñ¢È, this.Çªáˆºá, this.á(), this.ˆÏ­());
                    }
                    if (this.ÇŽÈ) {
                        break;
                    }
                }
            }
            this.Ñ¢È = this.á();
            this.Çªáˆºá = this.ˆÏ­();
        }
        if (Input.Ï­Ó) {
            for (int k = 0; k < this.£á(); ++k) {
                int count = Input.ŠáŒŠà¢.get(k).getButtonCount() + 3;
                count = Math.min(count, 24);
                for (int c = 0; c <= count; ++c) {
                    if (this.ÇªÈ[k][c] && !this.áŒŠÆ(c, k)) {
                        this.ÇªÈ[k][c] = false;
                        this.Ø(c, k);
                    }
                    else if (!this.ÇªÈ[k][c] && this.áŒŠÆ(c, k)) {
                        this.ˆÕ[k][c] = true;
                        this.ÇªÈ[k][c] = true;
                        this.à(c, k);
                    }
                }
            }
        }
        if (this.¥Ðƒá) {
            for (int k = 0; k < 1024; ++k) {
                if (this.ÂµáˆºÂ[k] && this.¥Âµá€[k] != 0L && System.currentTimeMillis() > this.¥Âµá€[k]) {
                    this.¥Âµá€[k] = System.currentTimeMillis() + this.¥Ê;
                    this.ÇŽÈ = false;
                    for (int m = 0; m < this.Ï­Ä.size(); ++m) {
                        final KeyListener listener2 = this.Ï­Ä.get(m);
                        if (listener2.Ý()) {
                            listener2.HorizonCode_Horizon_È(k, this.ÇªÂ[k]);
                            if (this.ÇŽÈ) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (final ControlledInputReciever listener6 : this.ÇªáˆºÕ) {
            listener6.Ø­áŒŠá();
        }
        if (Display.isCreated()) {
            this.ÇŽÅ = Display.isActive();
        }
    }
    
    public void Ó(final int initial, final int interval) {
        Keyboard.enableRepeatEvents(true);
    }
    
    public void µà() {
        Keyboard.enableRepeatEvents(true);
    }
    
    public void ˆà() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public boolean ¥Æ() {
        return Keyboard.areRepeatEventsEnabled();
    }
    
    private void à(final int index, final int controllerIndex) {
        this.ÇŽÈ = false;
        for (int i = 0; i < this.áŒŠÉ.size(); ++i) {
            final ControllerListener listener = this.áŒŠÉ.get(i);
            if (listener.Ý()) {
                switch (index) {
                    case 0: {
                        listener.Ý(controllerIndex);
                        break;
                    }
                    case 1: {
                        listener.Âµá€(controllerIndex);
                        break;
                    }
                    case 2: {
                        listener.à(controllerIndex);
                        break;
                    }
                    case 3: {
                        listener.HorizonCode_Horizon_È(controllerIndex);
                        break;
                    }
                    default: {
                        listener.HorizonCode_Horizon_È(controllerIndex, index - 4 + 1);
                        break;
                    }
                }
                if (this.ÇŽÈ) {
                    break;
                }
            }
        }
    }
    
    private void Ø(final int index, final int controllerIndex) {
        this.ÇŽÈ = false;
        for (int i = 0; i < this.áŒŠÉ.size(); ++i) {
            final ControllerListener listener = this.áŒŠÉ.get(i);
            if (listener.Ý()) {
                switch (index) {
                    case 0: {
                        listener.Ø­áŒŠá(controllerIndex);
                        break;
                    }
                    case 1: {
                        listener.Ó(controllerIndex);
                        break;
                    }
                    case 2: {
                        listener.Ø(controllerIndex);
                        break;
                    }
                    case 3: {
                        listener.Â(controllerIndex);
                        break;
                    }
                    default: {
                        listener.Â(controllerIndex, index - 4 + 1);
                        break;
                    }
                }
                if (this.ÇŽÈ) {
                    break;
                }
            }
        }
    }
    
    private boolean áŒŠÆ(final int index, final int controllerIndex) {
        switch (index) {
            case 0: {
                return this.ÂµÈ(controllerIndex);
            }
            case 1: {
                return this.á(controllerIndex);
            }
            case 2: {
                return this.ˆÏ­(controllerIndex);
            }
            case 3: {
                return this.£á(controllerIndex);
            }
            default: {
                if (index >= 4) {
                    return this.Ø­áŒŠá(index - 4, controllerIndex);
                }
                throw new RuntimeException("Unknown control index");
            }
        }
    }
    
    public void Ø­à() {
        this.ÐƒÓ = true;
        this.Ø();
        this.áŒŠÆ();
        this.à();
    }
    
    public void µÕ() {
        this.ÐƒÓ = false;
    }
    
    private void HorizonCode_Horizon_È(final int button, final int x, final int y, final int clickCount) {
        this.ÇŽÈ = false;
        for (int i = 0; i < this.µÊ.size(); ++i) {
            final MouseListener listener = this.µÊ.get(i);
            if (listener.Ý()) {
                listener.Ý(button, x, y, clickCount);
                if (this.ÇŽÈ) {
                    break;
                }
            }
        }
    }
    
    private class HorizonCode_Horizon_È extends OutputStream
    {
        @Override
        public void write(final int b) throws IOException {
        }
    }
}
