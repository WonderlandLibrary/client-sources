package net.augustus;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.security.MessageDigest;

public class Halal {
    public static void main(String[] args) {
        System.out.println("net.augustus.Augustus -> start");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("net.augustus.Augustus");
            CtMethod ctMethod = ctClass.getDeclaredMethod("start");

            // Get the bytecode as bytes
            byte[] bytecode = ctMethod.getMethodInfo().getCodeAttribute().getCode();

            // Calculate the hash of the bytecode
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(bytecode);

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            String calculatedHash = hashBuilder.toString();
            System.out.println(calculatedHash);
            // Compare with the stored hash
            //return calculatedHash.equals(storedHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("net.minecraft.client.Minecraft -> createDisplay");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("net.minecraft.client.Minecraft");
            CtMethod ctMethod = ctClass.getDeclaredMethod("createDisplay");

            // Get the bytecode as bytes
            byte[] bytecode = ctMethod.getMethodInfo().getCodeAttribute().getCode();

            // Calculate the hash of the bytecode
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(bytecode);

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            String calculatedHash = hashBuilder.toString();
            System.out.println(calculatedHash);
            // Compare with the stored hash
            //return calculatedHash.equals(storedHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("net.minecraft.client.gui.GuiMainMenu -> drawScreen");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("net.minecraft.client.gui.GuiMainMenu");
            CtMethod ctMethod = ctClass.getDeclaredMethod("drawScreen");

            // Get the bytecode as bytes
            byte[] bytecode = ctMethod.getMethodInfo().getCodeAttribute().getCode();

            // Calculate the hash of the bytecode
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(bytecode);

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            String calculatedHash = hashBuilder.toString();
            System.out.println(calculatedHash);
            // Compare with the stored hash
            //return calculatedHash.equals(storedHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("net.augustus.ui.GuiIngameHook -> hud");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("net.augustus.ui.GuiIngameHook");
            CtMethod ctMethod = ctClass.getDeclaredMethod("hud");

            // Get the bytecode as bytes
            byte[] bytecode = ctMethod.getMethodInfo().getCodeAttribute().getCode();

            // Calculate the hash of the bytecode
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(bytecode);

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            String calculatedHash = hashBuilder.toString();
            System.out.println(calculatedHash);
            // Compare with the stored hash
            //return calculatedHash.equals(storedHash);
        } catch (Exception e) {
            System.exit("fuck".hashCode());
        }
    }
}
