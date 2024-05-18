package de.labystudio.utils;

import java.awt.Robot;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ControllerInput
{
    private static Controller controllers;
    private static Robot robot;
    private static Field dxField;
    private static Field dyField;
    private static long delay = 0L;
    private static int mouseX = 0;
    private static int mouseY = 0;
    private static MovementInput input = new MovementInput();
    private static MovementInput oldInput;
    private static boolean pressedAttack = false;
    private static boolean pressedInteract = false;
    private static boolean pressedTab = false;
    private static boolean active = false;
    private static long lastCheck = 0L;

    public static void init()
    {
        try
        {
            Controllers.create();
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
        }

        Controllers.poll();

        try
        {
            dxField = getSetField("dx");
            dyField = getSetField("dy");
            robot = new Robot();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void tick()
    {
        if (lastCheck < System.currentTimeMillis())
        {
            lastCheck = System.currentTimeMillis() + 2000L;

            for (int i = 0; i < Controllers.getControllerCount(); ++i)
            {
                String s = Controllers.getController(i).getName().toLowerCase();

                if (s.contains("xbox") || s.contains("controller") || s.contains("playstation") || s.contains("joystick"))
                {
                    controllers = Controllers.getController(i);
                    break;
                }
            }
        }

        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;

        if (controllers != null && entityplayersp != null)
        {
            controllers.poll();

            if (!active)
            {
                for (int j = 0; j < controllers.getButtonCount(); ++j)
                {
                    if (controllers.isButtonPressed(j))
                    {
                        active = true;
                    }
                }

                if ((double)controllers.getAxisValue(3) > 0.5D || (double)controllers.getAxisValue(2) > 0.5D || (double)controllers.getAxisValue(3) < -0.5D || (double)controllers.getAxisValue(2) < -0.5D || (double)controllers.getAxisValue(0) > 0.5D || (double)controllers.getAxisValue(1) > 0.5D || (double)controllers.getAxisValue(0) < -0.5D || (double)controllers.getAxisValue(1) < -0.5D)
                {
                    active = true;
                }
            }
            else if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != 1)
            {
                active = false;

                if (oldInput != null)
                {
                    entityplayersp.movementInput = oldInput;
                    oldInput = null;
                }
            }

            if (active)
            {
                boolean flag = Minecraft.getMinecraft().currentScreen == null;

                if (delay < System.currentTimeMillis())
                {
                    if (controllers.isButtonPressed(4))
                    {
                        if (flag)
                        {
                            robot.mouseWheel(-1);
                        }

                        delay = System.currentTimeMillis() + 150L;
                    }

                    if (controllers.isButtonPressed(5))
                    {
                        if (flag)
                        {
                            robot.mouseWheel(1);
                        }

                        delay = System.currentTimeMillis() + 150L;
                    }

                    if (controllers.isButtonPressed(7))
                    {
                        robot.keyPress(27);
                        robot.keyRelease(27);
                        delay = System.currentTimeMillis() + 200L;
                    }

                    if (controllers.isButtonPressed(1))
                    {
                        robot.keyPress(9);
                        delay = System.currentTimeMillis() + 200L;
                        pressedTab = true;
                    }

                    if (controllers.isButtonPressed(3))
                    {
                        entityplayersp.dropOneItem(controllers.isButtonPressed(8));
                        delay = System.currentTimeMillis() + 200L;
                    }

                    if ((double)controllers.getZAxisValue() < -0.5D || controllers.isButtonPressed(9) && !flag)
                    {
                        robot.mousePress(16);
                        delay = System.currentTimeMillis() + 200L;
                        pressedAttack = true;
                    }

                    if ((double)controllers.getZAxisValue() > 0.5D)
                    {
                        robot.mousePress(4);
                        delay = System.currentTimeMillis() + 200L;
                        pressedInteract = true;
                    }

                    if (controllers.isButtonPressed(6))
                    {
                        if (flag)
                        {
                            if (Minecraft.getMinecraft().gameSettings.thirdPersonView >= 2)
                            {
                                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
                            }
                            else
                            {
                                ++Minecraft.getMinecraft().gameSettings.thirdPersonView;
                            }
                        }

                        delay = System.currentTimeMillis() + 200L;
                    }
                }

                if ((double)controllers.getZAxisValue() >= -0.5D && pressedAttack)
                {
                    robot.mouseRelease(16);
                    pressedAttack = false;
                }

                if ((double)controllers.getZAxisValue() <= 0.5D && pressedInteract)
                {
                    robot.mouseRelease(4);
                    pressedInteract = false;
                }

                if (!controllers.isButtonPressed(1) && pressedTab)
                {
                    robot.keyRelease(9);
                    pressedTab = false;
                }

                if (flag)
                {
                    input.jump = controllers.isButtonPressed(0) || controllers.isButtonPressed(9);
                    input.moveForward = (float)(((double)controllers.getAxisValue(0) < -0.5D ? 1 : ((double)(0.0F + controllers.getAxisValue(0)) > 0.5D ? -1 : 0)) * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1));
                    input.moveStrafe = (float)(((double)controllers.getAxisValue(1) < -0.5D ? 1 : ((double)(0.0F + controllers.getAxisValue(1)) > 0.5D ? -1 : 0)) * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1));
                    input.sneak = controllers.isButtonPressed(8);

                    if (input.sneak)
                    {
                        input.moveStrafe = (float)((double)input.moveStrafe * 0.3D);
                        input.moveForward = (float)((double)input.moveForward * 0.3D);
                    }
                    else if (input.moveForward > 0.0F)
                    {
                        entityplayersp.setSprinting(true);
                    }
                }
                else
                {
                    input.jump = false;
                    input.moveForward = 0.0F;
                    input.moveStrafe = 0.0F;
                    input.sneak = false;
                }

                if (oldInput == null)
                {
                    oldInput = entityplayersp.movementInput;
                }

                entityplayersp.movementInput = input;
            }
        }
    }

    public static void mouseTick()
    {
        if (active && controllers != null && Minecraft.getMinecraft().thePlayer != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu))
        {
            try
            {
                float f = controllers.getAxisValue(3);
                float f1 = controllers.getAxisValue(2);

                if (Minecraft.getMinecraft().currentScreen == null)
                {
                    float f2 = 4.0F * Minecraft.getMinecraft().gameSettings.mouseSensitivity * (Minecraft.getMinecraft().thePlayer.isSprinting() ? 1.5F : 1.0F);
                    dxField.setInt((Object)null, (int)(f * f2));
                    dyField.setInt((Object)null, (int)(f1 * -f2));
                    mouseX = Mouse.getX();
                    mouseY = Mouse.getY();
                }
                else
                {
                    float f3 = 2.0F * Minecraft.getMinecraft().gameSettings.mouseSensitivity;

                    if (f != 1.0F && f != -1.0F)
                    {
                        f = 0.0F;
                    }

                    if (f1 != 1.0F && f1 != -1.0F)
                    {
                        f1 = 0.0F;
                    }

                    mouseX = (int)((float)mouseX + f * f3);
                    mouseY = (int)((float)mouseY + f1 * f3);
                    robot.mouseMove(mouseX, mouseY);
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private static Field getSetField(String fieldName) throws NoSuchFieldException, SecurityException
    {
        Field field = Mouse.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static void exit()
    {
        Controllers.destroy();
        Controllers.clearEvents();
    }
}
