/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.Arrays;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.ComputeProgram;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.ProgramStage;
import net.optifine.shaders.config.RenderScale;
import net.optifine.util.DynamicDimension;

public class Program {
    private final int index;
    private final String name;
    private final ProgramStage programStage;
    private final Program programBackup;
    private ComputeProgram[] computePrograms;
    private GlAlphaState alphaState;
    private GlBlendState blendState;
    private GlBlendState[] blendStatesColorIndexed;
    private RenderScale renderScale;
    private final Boolean[] buffersFlip = new Boolean[16];
    private int id;
    private int ref;
    private String[] drawBufSettings;
    private DrawBuffers drawBuffers;
    private DrawBuffers drawBuffersCustom;
    private int compositeMipmapSetting;
    private int countInstances;
    private final boolean[] toggleColorTextures = new boolean[16];
    private DynamicDimension drawSize;
    private GlBlendState[] blendStatesIndexed;

    public Program(int n, String string, ProgramStage programStage, Program program) {
        this.index = n;
        this.name = string;
        this.programStage = programStage;
        this.programBackup = program;
        this.computePrograms = new ComputeProgram[0];
    }

    public Program(int n, String string, ProgramStage programStage, boolean bl) {
        this.index = n;
        this.name = string;
        this.programStage = programStage;
        this.programBackup = bl ? this : null;
        this.computePrograms = new ComputeProgram[0];
    }

    public void resetProperties() {
        this.alphaState = null;
        this.blendState = null;
        this.blendStatesColorIndexed = null;
        this.renderScale = null;
        Arrays.fill((Object[])this.buffersFlip, null);
    }

    public void resetId() {
        this.id = 0;
        this.ref = 0;
    }

    public void resetConfiguration() {
        this.drawBufSettings = null;
        this.compositeMipmapSetting = 0;
        this.countInstances = 0;
        Arrays.fill(this.toggleColorTextures, false);
        this.drawSize = null;
        this.blendStatesIndexed = null;
        if (this.drawBuffersCustom == null) {
            this.drawBuffersCustom = new DrawBuffers(this.name, 16, 8);
        }
    }

    public void copyFrom(Program program) {
        this.id = program.getId();
        this.alphaState = program.getAlphaState();
        this.blendState = program.getBlendState();
        this.blendStatesColorIndexed = program.blendStatesColorIndexed;
        this.renderScale = program.getRenderScale();
        System.arraycopy(program.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
        this.drawBufSettings = program.getDrawBufSettings();
        this.drawBuffers = program.getDrawBuffers();
        this.compositeMipmapSetting = program.getCompositeMipmapSetting();
        this.countInstances = program.getCountInstances();
        System.arraycopy(program.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
        this.blendStatesIndexed = program.blendStatesIndexed;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public ProgramStage getProgramStage() {
        return this.programStage;
    }

    public Program getProgramBackup() {
        return this.programBackup;
    }

    public int getId() {
        return this.id;
    }

    public int getRef() {
        return this.ref;
    }

    public String[] getDrawBufSettings() {
        return this.drawBufSettings;
    }

    public DrawBuffers getDrawBuffers() {
        return this.drawBuffers;
    }

    public DrawBuffers getDrawBuffersCustom() {
        return this.drawBuffersCustom;
    }

    public int getCompositeMipmapSetting() {
        return this.compositeMipmapSetting;
    }

    public int getCountInstances() {
        return this.countInstances;
    }

    public GlAlphaState getAlphaState() {
        return this.alphaState;
    }

    public GlBlendState getBlendState() {
        return this.blendState;
    }

    public GlBlendState[] getBlendStatesColorIndexed() {
        return this.blendStatesColorIndexed;
    }

    public GlBlendState[] getBlendStatesIndexed() {
        return this.blendStatesIndexed;
    }

    public RenderScale getRenderScale() {
        return this.renderScale;
    }

    public Boolean[] getBuffersFlip() {
        return this.buffersFlip;
    }

    public boolean[] getToggleColorTextures() {
        return this.toggleColorTextures;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setRef(int n) {
        this.ref = n;
    }

    public void setDrawBufSettings(String[] stringArray) {
        this.drawBufSettings = stringArray;
    }

    public void setDrawBuffers(DrawBuffers drawBuffers) {
        this.drawBuffers = drawBuffers;
    }

    public void setCompositeMipmapSetting(int n) {
        this.compositeMipmapSetting = n;
    }

    public void setCountInstances(int n) {
        this.countInstances = n;
    }

    public void setAlphaState(GlAlphaState glAlphaState) {
        this.alphaState = glAlphaState;
    }

    public void setBlendState(GlBlendState glBlendState) {
        this.blendState = glBlendState;
    }

    public void setBlendStateColorIndexed(int n, GlBlendState glBlendState) {
        if (this.blendStatesColorIndexed == null) {
            this.blendStatesColorIndexed = new GlBlendState[n + 1];
        }
        if (this.blendStatesColorIndexed.length < n + 1) {
            GlBlendState[] glBlendStateArray = new GlBlendState[n + 1];
            System.arraycopy(this.blendStatesColorIndexed, 0, glBlendStateArray, 0, this.blendStatesColorIndexed.length);
            this.blendStatesColorIndexed = glBlendStateArray;
        }
        this.blendStatesColorIndexed[n] = glBlendState;
    }

    public void setBlendStateIndexed(int n, GlBlendState glBlendState) {
        if (this.blendStatesIndexed == null) {
            this.blendStatesIndexed = new GlBlendState[n + 1];
        }
        if (this.blendStatesIndexed.length < n + 1) {
            GlBlendState[] glBlendStateArray = new GlBlendState[n + 1];
            System.arraycopy(this.blendStatesIndexed, 0, glBlendStateArray, 0, this.blendStatesIndexed.length);
            this.blendStatesIndexed = glBlendStateArray;
        }
        this.blendStatesIndexed[n] = glBlendState;
    }

    public void setRenderScale(RenderScale renderScale) {
        this.renderScale = renderScale;
    }

    public String getRealProgramName() {
        if (this.id == 0) {
            return "none";
        }
        Program program = this;
        while (program.getRef() != this.id) {
            if (program.getProgramBackup() == null || program.getProgramBackup() == program) {
                return "unknown";
            }
            program = program.getProgramBackup();
        }
        return program.getName();
    }

    public boolean hasCompositeMipmaps() {
        return this.compositeMipmapSetting != 0;
    }

    public DynamicDimension getDrawSize() {
        return this.drawSize;
    }

    public void setDrawSize(DynamicDimension dynamicDimension) {
        this.drawSize = dynamicDimension;
    }

    public ComputeProgram[] getComputePrograms() {
        return this.computePrograms;
    }

    public void setComputePrograms(ComputeProgram[] computeProgramArray) {
        this.computePrograms = computeProgramArray;
    }

    public String toString() {
        return "name: " + this.name + ", id: " + this.id + ", ref: " + this.ref + ", real: " + this.getRealProgramName();
    }
}

