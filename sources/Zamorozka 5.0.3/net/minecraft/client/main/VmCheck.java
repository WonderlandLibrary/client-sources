package net.minecraft.client.main;


import com.sun.jna.Library;

public interface VmCheck extends Library {
    boolean IsInsideVMWare();
    boolean IsInsideVPC();
}