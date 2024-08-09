/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3i;
import net.optifine.shaders.ProgramStage;

public class ComputeProgram {
    private final String name;
    private final ProgramStage programStage;
    private int id;
    private int ref;
    private Vector3i localSize;
    private Vector3i workGroups;
    private Vector2f workGroupsRender;
    private int compositeMipmapSetting;

    public ComputeProgram(String string, ProgramStage programStage) {
        this.name = string;
        this.programStage = programStage;
    }

    public void resetProperties() {
    }

    public void resetId() {
        this.id = 0;
        this.ref = 0;
    }

    public void resetConfiguration() {
        this.localSize = null;
        this.workGroups = null;
        this.workGroupsRender = null;
    }

    public String getName() {
        return this.name;
    }

    public ProgramStage getProgramStage() {
        return this.programStage;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public int getRef() {
        return this.ref;
    }

    public void setRef(int n) {
        this.ref = n;
    }

    public Vector3i getLocalSize() {
        return this.localSize;
    }

    public void setLocalSize(Vector3i vector3i) {
        this.localSize = vector3i;
    }

    public Vector3i getWorkGroups() {
        return this.workGroups;
    }

    public void setWorkGroups(Vector3i vector3i) {
        this.workGroups = vector3i;
    }

    public Vector2f getWorkGroupsRender() {
        return this.workGroupsRender;
    }

    public void setWorkGroupsRender(Vector2f vector2f) {
        this.workGroupsRender = vector2f;
    }

    public int getCompositeMipmapSetting() {
        return this.compositeMipmapSetting;
    }

    public void setCompositeMipmapSetting(int n) {
        this.compositeMipmapSetting = n;
    }

    public boolean hasCompositeMipmaps() {
        return this.compositeMipmapSetting != 0;
    }

    public String toString() {
        return "name: " + this.name + ", id: " + this.id;
    }
}

