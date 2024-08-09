/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVMeshShader {
    public static final int GL_MESH_SHADER_NV = 38233;
    public static final int GL_TASK_SHADER_NV = 38234;
    public static final int GL_MAX_MESH_UNIFORM_BLOCKS_NV = 36448;
    public static final int GL_MAX_MESH_TEXTURE_IMAGE_UNITS_NV = 36449;
    public static final int GL_MAX_MESH_IMAGE_UNIFORMS_NV = 36450;
    public static final int GL_MAX_MESH_UNIFORM_COMPONENTS_NV = 36451;
    public static final int GL_MAX_MESH_ATOMIC_COUNTER_BUFFERS_NV = 36452;
    public static final int GL_MAX_MESH_ATOMIC_COUNTERS_NV = 36453;
    public static final int GL_MAX_MESH_SHADER_STORAGE_BLOCKS_NV = 36454;
    public static final int GL_MAX_COMBINED_MESH_UNIFORM_COMPONENTS_NV = 36455;
    public static final int GL_MAX_TASK_UNIFORM_BLOCKS_NV = 36456;
    public static final int GL_MAX_TASK_TEXTURE_IMAGE_UNITS_NV = 36457;
    public static final int GL_MAX_TASK_IMAGE_UNIFORMS_NV = 36458;
    public static final int GL_MAX_TASK_UNIFORM_COMPONENTS_NV = 36459;
    public static final int GL_MAX_TASK_ATOMIC_COUNTER_BUFFERS_NV = 36460;
    public static final int GL_MAX_TASK_ATOMIC_COUNTERS_NV = 36461;
    public static final int GL_MAX_TASK_SHADER_STORAGE_BLOCKS_NV = 36462;
    public static final int GL_MAX_COMBINED_TASK_UNIFORM_COMPONENTS_NV = 36463;
    public static final int GL_MAX_MESH_WORK_GROUP_INVOCATIONS_NV = 38306;
    public static final int GL_MAX_TASK_WORK_GROUP_INVOCATIONS_NV = 38307;
    public static final int GL_MAX_MESH_TOTAL_MEMORY_SIZE_NV = 38198;
    public static final int GL_MAX_TASK_TOTAL_MEMORY_SIZE_NV = 38199;
    public static final int GL_MAX_MESH_OUTPUT_VERTICES_NV = 38200;
    public static final int GL_MAX_MESH_OUTPUT_PRIMITIVES_NV = 38201;
    public static final int GL_MAX_TASK_OUTPUT_COUNT_NV = 38202;
    public static final int GL_MAX_DRAW_MESH_TASKS_COUNT_NV = 38205;
    public static final int GL_MAX_MESH_VIEWS_NV = 38231;
    public static final int GL_MESH_OUTPUT_PER_VERTEX_GRANULARITY_NV = 37599;
    public static final int GL_MESH_OUTPUT_PER_PRIMITIVE_GRANULARITY_NV = 38211;
    public static final int GL_MAX_MESH_WORK_GROUP_SIZE_NV = 38203;
    public static final int GL_MAX_TASK_WORK_GROUP_SIZE_NV = 38204;
    public static final int GL_MESH_WORK_GROUP_SIZE_NV = 38206;
    public static final int GL_TASK_WORK_GROUP_SIZE_NV = 38207;
    public static final int GL_MESH_VERTICES_OUT_NV = 38265;
    public static final int GL_MESH_PRIMITIVES_OUT_NV = 38266;
    public static final int GL_MESH_OUTPUT_TYPE_NV = 38267;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_MESH_SHADER_NV = 38300;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TASK_SHADER_NV = 38301;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_MESH_SHADER_NV = 38302;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TASK_SHADER_NV = 38303;
    public static final int GL_REFERENCED_BY_MESH_SHADER_NV = 38304;
    public static final int GL_REFERENCED_BY_TASK_SHADER_NV = 38305;
    public static final int GL_MESH_SUBROUTINE_NV = 38268;
    public static final int GL_TASK_SUBROUTINE_NV = 38269;
    public static final int GL_MESH_SUBROUTINE_UNIFORM_NV = 38270;
    public static final int GL_TASK_SUBROUTINE_UNIFORM_NV = 38271;
    public static final int GL_MESH_SHADER_BIT_NV = 64;
    public static final int GL_TASK_SHADER_BIT_NV = 128;

    protected NVMeshShader() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawMeshTasksNV, gLCapabilities.glDrawMeshTasksIndirectNV, gLCapabilities.glMultiDrawMeshTasksIndirectNV, gLCapabilities.glMultiDrawMeshTasksIndirectCountNV);
    }

    public static native void glDrawMeshTasksNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDrawMeshTasksIndirectNV(@NativeType(value="GLintptr") long var0);

    public static native void glMultiDrawMeshTasksIndirectNV(@NativeType(value="GLintptr") long var0, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static native void glMultiDrawMeshTasksIndirectCountNV(@NativeType(value="GLintptr") long var0, @NativeType(value="GLintptr") long var2, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5);

    static {
        GL.initialize();
    }
}

