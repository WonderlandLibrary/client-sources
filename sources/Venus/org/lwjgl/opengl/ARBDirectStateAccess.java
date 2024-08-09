/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBDirectStateAccess {
    public static final int GL_TEXTURE_TARGET = 4102;
    public static final int GL_QUERY_TARGET = 33514;

    protected ARBDirectStateAccess() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(ARBDirectStateAccess.ARB_transform_feedback2(set) ? gLCapabilities.glCreateTransformFeedbacks : -1L, ARBDirectStateAccess.ARB_uniform_buffer_object(set) ? gLCapabilities.glTransformFeedbackBufferBase : -1L, ARBDirectStateAccess.ARB_uniform_buffer_object(set) ? gLCapabilities.glTransformFeedbackBufferRange : -1L, ARBDirectStateAccess.ARB_transform_feedback2(set) ? gLCapabilities.glGetTransformFeedbackiv : -1L, ARBDirectStateAccess.ARB_transform_feedback2(set) ? gLCapabilities.glGetTransformFeedbacki_v : -1L, ARBDirectStateAccess.ARB_transform_feedback2(set) ? gLCapabilities.glGetTransformFeedbacki64_v : -1L, gLCapabilities.glCreateBuffers, ARBDirectStateAccess.ARB_buffer_storage(set) ? gLCapabilities.glNamedBufferStorage : -1L, gLCapabilities.glNamedBufferData, gLCapabilities.glNamedBufferSubData, ARBDirectStateAccess.ARB_copy_buffer(set) ? gLCapabilities.glCopyNamedBufferSubData : -1L, ARBDirectStateAccess.ARB_clear_texture(set) ? gLCapabilities.glClearNamedBufferData : -1L, ARBDirectStateAccess.ARB_clear_texture(set) ? gLCapabilities.glClearNamedBufferSubData : -1L, gLCapabilities.glMapNamedBuffer, ARBDirectStateAccess.ARB_map_buffer_range(set) ? gLCapabilities.glMapNamedBufferRange : -1L, gLCapabilities.glUnmapNamedBuffer, ARBDirectStateAccess.ARB_map_buffer_range(set) ? gLCapabilities.glFlushMappedNamedBufferRange : -1L, gLCapabilities.glGetNamedBufferParameteriv, gLCapabilities.glGetNamedBufferParameteri64v, gLCapabilities.glGetNamedBufferPointerv, gLCapabilities.glGetNamedBufferSubData, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glCreateFramebuffers : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferRenderbuffer : -1L, ARBDirectStateAccess.ARB_framebuffer_no_attachments(set) ? gLCapabilities.glNamedFramebufferParameteri : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferTexture : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferTextureLayer : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferDrawBuffer : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferDrawBuffers : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedFramebufferReadBuffer : -1L, ARBDirectStateAccess.ARB_invalidate_subdata(set) ? gLCapabilities.glInvalidateNamedFramebufferData : -1L, ARBDirectStateAccess.ARB_invalidate_subdata(set) ? gLCapabilities.glInvalidateNamedFramebufferSubData : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glClearNamedFramebufferiv : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glClearNamedFramebufferuiv : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glClearNamedFramebufferfv : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glClearNamedFramebufferfi : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glBlitNamedFramebuffer : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glCheckNamedFramebufferStatus : -1L, ARBDirectStateAccess.ARB_framebuffer_no_attachments(set) ? gLCapabilities.glGetNamedFramebufferParameteriv : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glGetNamedFramebufferAttachmentParameteriv : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glCreateRenderbuffers : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedRenderbufferStorage : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glNamedRenderbufferStorageMultisample : -1L, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glGetNamedRenderbufferParameteriv : -1L, gLCapabilities.glCreateTextures, ARBDirectStateAccess.ARB_texture_buffer_object(set) ? gLCapabilities.glTextureBuffer : -1L, ARBDirectStateAccess.ARB_texture_buffer_range(set) ? gLCapabilities.glTextureBufferRange : -1L, ARBDirectStateAccess.ARB_texture_storage(set) ? gLCapabilities.glTextureStorage1D : -1L, ARBDirectStateAccess.ARB_texture_storage(set) ? gLCapabilities.glTextureStorage2D : -1L, ARBDirectStateAccess.ARB_texture_storage(set) ? gLCapabilities.glTextureStorage3D : -1L, ARBDirectStateAccess.ARB_texture_storage_multisample(set) ? gLCapabilities.glTextureStorage2DMultisample : -1L, ARBDirectStateAccess.ARB_texture_storage_multisample(set) ? gLCapabilities.glTextureStorage3DMultisample : -1L, gLCapabilities.glTextureSubImage1D, gLCapabilities.glTextureSubImage2D, gLCapabilities.glTextureSubImage3D, gLCapabilities.glCompressedTextureSubImage1D, gLCapabilities.glCompressedTextureSubImage2D, gLCapabilities.glCompressedTextureSubImage3D, gLCapabilities.glCopyTextureSubImage1D, gLCapabilities.glCopyTextureSubImage2D, gLCapabilities.glCopyTextureSubImage3D, gLCapabilities.glTextureParameterf, gLCapabilities.glTextureParameterfv, gLCapabilities.glTextureParameteri, gLCapabilities.glTextureParameterIiv, gLCapabilities.glTextureParameterIuiv, gLCapabilities.glTextureParameteriv, ARBDirectStateAccess.ARB_framebuffer_object(set) ? gLCapabilities.glGenerateTextureMipmap : -1L, gLCapabilities.glBindTextureUnit, gLCapabilities.glGetTextureImage, gLCapabilities.glGetCompressedTextureImage, gLCapabilities.glGetTextureLevelParameterfv, gLCapabilities.glGetTextureLevelParameteriv, gLCapabilities.glGetTextureParameterfv, gLCapabilities.glGetTextureParameterIiv, gLCapabilities.glGetTextureParameterIuiv, gLCapabilities.glGetTextureParameteriv, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glCreateVertexArrays : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glDisableVertexArrayAttrib : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glEnableVertexArrayAttrib : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glVertexArrayElementBuffer : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayVertexBuffer : -1L, ARBDirectStateAccess.ARB_multi_bind(set) ? gLCapabilities.glVertexArrayVertexBuffers : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayAttribFormat : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayAttribIFormat : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayAttribLFormat : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayAttribBinding : -1L, ARBDirectStateAccess.ARB_vertex_attrib_binding(set) ? gLCapabilities.glVertexArrayBindingDivisor : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glGetVertexArrayiv : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glGetVertexArrayIndexediv : -1L, ARBDirectStateAccess.ARB_vertex_array_object(set) ? gLCapabilities.glGetVertexArrayIndexed64iv : -1L, ARBDirectStateAccess.ARB_sampler_objects(set) ? gLCapabilities.glCreateSamplers : -1L, ARBDirectStateAccess.ARB_separate_shader_objects(set) ? gLCapabilities.glCreateProgramPipelines : -1L, gLCapabilities.glCreateQueries, ARBDirectStateAccess.ARB_query_buffer_object(set) ? gLCapabilities.glGetQueryBufferObjecti64v : -1L, ARBDirectStateAccess.ARB_query_buffer_object(set) ? gLCapabilities.glGetQueryBufferObjectiv : -1L, ARBDirectStateAccess.ARB_query_buffer_object(set) ? gLCapabilities.glGetQueryBufferObjectui64v : -1L, ARBDirectStateAccess.ARB_query_buffer_object(set) ? gLCapabilities.glGetQueryBufferObjectuiv : -1L);
    }

    public static void nglCreateTransformFeedbacks(int n, long l) {
        GL45C.nglCreateTransformFeedbacks(n, l);
    }

    public static void glCreateTransformFeedbacks(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateTransformFeedbacks(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateTransformFeedbacks() {
        return GL45C.glCreateTransformFeedbacks();
    }

    public static void glTransformFeedbackBufferBase(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL45C.glTransformFeedbackBufferBase(n, n2, n3);
    }

    public static void glTransformFeedbackBufferRange(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL45C.glTransformFeedbackBufferRange(n, n2, n3, l, l2);
    }

    public static void nglGetTransformFeedbackiv(int n, int n2, long l) {
        GL45C.nglGetTransformFeedbackiv(n, n2, l);
    }

    public static void glGetTransformFeedbackiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetTransformFeedbackiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTransformFeedbacki(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetTransformFeedbacki(n, n2);
    }

    public static void nglGetTransformFeedbacki_v(int n, int n2, int n3, long l) {
        GL45C.nglGetTransformFeedbacki_v(n, n2, n3, l);
    }

    public static void glGetTransformFeedbacki_v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetTransformFeedbacki_v(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTransformFeedbacki(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return GL45C.glGetTransformFeedbacki(n, n2, n3);
    }

    public static void nglGetTransformFeedbacki64_v(int n, int n2, int n3, long l) {
        GL45C.nglGetTransformFeedbacki64_v(n, n2, n3, l);
    }

    public static void glGetTransformFeedbacki64_v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL45C.glGetTransformFeedbacki64_v(n, n2, n3, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetTransformFeedbacki64(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return GL45C.glGetTransformFeedbacki64(n, n2, n3);
    }

    public static void nglCreateBuffers(int n, long l) {
        GL45C.nglCreateBuffers(n, l);
    }

    public static void glCreateBuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateBuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateBuffers() {
        return GL45C.glCreateBuffers();
    }

    public static void nglNamedBufferStorage(int n, long l, long l2, int n2) {
        GL45C.nglNamedBufferStorage(n, l, l2, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, l, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, byteBuffer, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, shortBuffer, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, intBuffer, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, floatBuffer, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, doubleBuffer, n2);
    }

    public static void nglNamedBufferData(int n, long l, long l2, int n2) {
        GL45C.nglNamedBufferData(n, l, l2, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, l, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, byteBuffer, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, shortBuffer, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, intBuffer, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") LongBuffer longBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, longBuffer, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, floatBuffer, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, doubleBuffer, n2);
    }

    public static void nglNamedBufferSubData(int n, long l, long l2, long l3) {
        GL45C.nglNamedBufferSubData(n, l, l2, l3);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glNamedBufferSubData(n, l, byteBuffer);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glNamedBufferSubData(n, l, shortBuffer);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glNamedBufferSubData(n, l, intBuffer);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") LongBuffer longBuffer) {
        GL45C.glNamedBufferSubData(n, l, longBuffer);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glNamedBufferSubData(n, l, floatBuffer);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL45C.glNamedBufferSubData(n, l, doubleBuffer);
    }

    public static void glCopyNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizeiptr") long l3) {
        GL45C.glCopyNamedBufferSubData(n, n2, l, l2, l3);
    }

    public static void nglClearNamedBufferData(int n, int n2, int n3, int n4, long l) {
        GL45C.nglClearNamedBufferData(n, n2, n3, n4, l);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, byteBuffer);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, shortBuffer);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, intBuffer);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, floatBuffer);
    }

    public static void nglClearNamedBufferSubData(int n, int n2, long l, long l2, int n3, int n4, long l3) {
        GL45C.nglClearNamedBufferSubData(n, n2, l, l2, n3, n4, l3);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, byteBuffer);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, shortBuffer);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, intBuffer);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, floatBuffer);
    }

    public static long nglMapNamedBuffer(int n, int n2) {
        return GL45C.nglMapNamedBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glMapNamedBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @Nullable ByteBuffer byteBuffer) {
        return GL45C.glMapNamedBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, long l, @Nullable ByteBuffer byteBuffer) {
        return GL45C.glMapNamedBuffer(n, n2);
    }

    public static long nglMapNamedBufferRange(int n, long l, long l2, int n2) {
        return GL45C.nglMapNamedBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferRange(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2) {
        return GL45C.glMapNamedBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferRange(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2, @Nullable ByteBuffer byteBuffer) {
        return GL45C.glMapNamedBufferRange(n, l, l2, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glUnmapNamedBuffer(@NativeType(value="GLuint") int n) {
        return GL45C.glUnmapNamedBuffer(n);
    }

    public static void glFlushMappedNamedBufferRange(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL45C.glFlushMappedNamedBufferRange(n, l, l2);
    }

    public static void nglGetNamedBufferParameteriv(int n, int n2, long l) {
        GL45C.nglGetNamedBufferParameteriv(n, n2, l);
    }

    public static void glGetNamedBufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetNamedBufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetNamedBufferParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetNamedBufferParameteri(n, n2);
    }

    public static void nglGetNamedBufferParameteri64v(int n, int n2, long l) {
        GL45C.nglGetNamedBufferParameteri64v(n, n2, l);
    }

    public static void glGetNamedBufferParameteri64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL45C.glGetNamedBufferParameteri64v(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetNamedBufferParameteri64(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetNamedBufferParameteri64(n, n2);
    }

    public static void nglGetNamedBufferPointerv(int n, int n2, long l) {
        GL45C.nglGetNamedBufferPointerv(n, n2, l);
    }

    public static void glGetNamedBufferPointerv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        GL45C.glGetNamedBufferPointerv(n, n2, pointerBuffer);
    }

    @NativeType(value="void")
    public static long glGetNamedBufferPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetNamedBufferPointer(n, n2);
    }

    public static void nglGetNamedBufferSubData(int n, long l, long l2, long l3) {
        GL45C.nglGetNamedBufferSubData(n, l, l2, l3);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, byteBuffer);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, shortBuffer);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") IntBuffer intBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, intBuffer);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") LongBuffer longBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, longBuffer);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, floatBuffer);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL45C.glGetNamedBufferSubData(n, l, doubleBuffer);
    }

    public static void nglCreateFramebuffers(int n, long l) {
        GL45C.nglCreateFramebuffers(n, l);
    }

    public static void glCreateFramebuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateFramebuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateFramebuffers() {
        return GL45C.glCreateFramebuffers();
    }

    public static void glNamedFramebufferRenderbuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4) {
        GL45C.glNamedFramebufferRenderbuffer(n, n2, n3, n4);
    }

    public static void glNamedFramebufferParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL45C.glNamedFramebufferParameteri(n, n2, n3);
    }

    public static void glNamedFramebufferTexture(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint") int n4) {
        GL45C.glNamedFramebufferTexture(n, n2, n3, n4);
    }

    public static void glNamedFramebufferTextureLayer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL45C.glNamedFramebufferTextureLayer(n, n2, n3, n4, n5);
    }

    public static void glNamedFramebufferDrawBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedFramebufferDrawBuffer(n, n2);
    }

    public static void nglNamedFramebufferDrawBuffers(int n, int n2, long l) {
        GL45C.nglNamedFramebufferDrawBuffers(n, n2, l);
    }

    public static void glNamedFramebufferDrawBuffers(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer) {
        GL45C.glNamedFramebufferDrawBuffers(n, intBuffer);
    }

    public static void glNamedFramebufferDrawBuffers(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int n2) {
        GL45C.glNamedFramebufferDrawBuffers(n, n2);
    }

    public static void glNamedFramebufferReadBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedFramebufferReadBuffer(n, n2);
    }

    public static void nglInvalidateNamedFramebufferData(int n, int n2, long l) {
        GL45C.nglInvalidateNamedFramebufferData(n, n2, l);
    }

    public static void glInvalidateNamedFramebufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer) {
        GL45C.glInvalidateNamedFramebufferData(n, intBuffer);
    }

    public static void glInvalidateNamedFramebufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int n2) {
        GL45C.glInvalidateNamedFramebufferData(n, n2);
    }

    public static void nglInvalidateNamedFramebufferSubData(int n, int n2, long l, int n3, int n4, int n5, int n6) {
        GL45C.nglInvalidateNamedFramebufferSubData(n, n2, l, n3, n4, n5, n6);
    }

    public static void glInvalidateNamedFramebufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL45C.glInvalidateNamedFramebufferSubData(n, intBuffer, n2, n3, n4, n5);
    }

    public static void glInvalidateNamedFramebufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6) {
        GL45C.glInvalidateNamedFramebufferSubData(n, n2, n3, n4, n5, n6);
    }

    public static void nglClearNamedFramebufferiv(int n, int n2, int n3, long l) {
        GL45C.nglClearNamedFramebufferiv(n, n2, n3, l);
    }

    public static void glClearNamedFramebufferiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glClearNamedFramebufferiv(n, n2, n3, intBuffer);
    }

    public static void nglClearNamedFramebufferuiv(int n, int n2, int n3, long l) {
        GL45C.nglClearNamedFramebufferuiv(n, n2, n3, l);
    }

    public static void glClearNamedFramebufferuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glClearNamedFramebufferuiv(n, n2, n3, intBuffer);
    }

    public static void nglClearNamedFramebufferfv(int n, int n2, int n3, long l) {
        GL45C.nglClearNamedFramebufferfv(n, n2, n3, l);
    }

    public static void glClearNamedFramebufferfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glClearNamedFramebufferfv(n, n2, n3, floatBuffer);
    }

    public static void glClearNamedFramebufferfi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat") float f, @NativeType(value="GLint") int n4) {
        GL45C.glClearNamedFramebufferfi(n, n2, n3, f, n4);
    }

    public static void glBlitNamedFramebuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLint") int n9, @NativeType(value="GLint") int n10, @NativeType(value="GLbitfield") int n11, @NativeType(value="GLenum") int n12) {
        GL45C.glBlitNamedFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
    }

    @NativeType(value="GLenum")
    public static int glCheckNamedFramebufferStatus(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glCheckNamedFramebufferStatus(n, n2);
    }

    public static void nglGetNamedFramebufferParameteriv(int n, int n2, long l) {
        GL45C.nglGetNamedFramebufferParameteriv(n, n2, l);
    }

    public static void glGetNamedFramebufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetNamedFramebufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetNamedFramebufferParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetNamedFramebufferParameteri(n, n2);
    }

    public static void nglGetNamedFramebufferAttachmentParameteriv(int n, int n2, int n3, long l) {
        GL45C.nglGetNamedFramebufferAttachmentParameteriv(n, n2, n3, l);
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetNamedFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetNamedFramebufferAttachmentParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL45C.glGetNamedFramebufferAttachmentParameteri(n, n2, n3);
    }

    public static void nglCreateRenderbuffers(int n, long l) {
        GL45C.nglCreateRenderbuffers(n, l);
    }

    public static void glCreateRenderbuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateRenderbuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateRenderbuffers() {
        return GL45C.glCreateRenderbuffers();
    }

    public static void glNamedRenderbufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL45C.glNamedRenderbufferStorage(n, n2, n3, n4);
    }

    public static void glNamedRenderbufferStorageMultisample(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL45C.glNamedRenderbufferStorageMultisample(n, n2, n3, n4, n5);
    }

    public static void nglGetNamedRenderbufferParameteriv(int n, int n2, long l) {
        GL45C.nglGetNamedRenderbufferParameteriv(n, n2, l);
    }

    public static void glGetNamedRenderbufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetNamedRenderbufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetNamedRenderbufferParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetNamedRenderbufferParameteri(n, n2);
    }

    public static void nglCreateTextures(int n, int n2, long l) {
        GL45C.nglCreateTextures(n, n2, l);
    }

    public static void glCreateTextures(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateTextures(n, intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateTextures(@NativeType(value="GLenum") int n) {
        return GL45C.glCreateTextures(n);
    }

    public static void glTextureBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        GL45C.glTextureBuffer(n, n2, n3);
    }

    public static void glTextureBufferRange(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL45C.glTextureBufferRange(n, n2, n3, l, l2);
    }

    public static void glTextureStorage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4) {
        GL45C.glTextureStorage1D(n, n2, n3, n4);
    }

    public static void glTextureStorage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL45C.glTextureStorage2D(n, n2, n3, n4, n5);
    }

    public static void glTextureStorage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6) {
        GL45C.glTextureStorage3D(n, n2, n3, n4, n5, n6);
    }

    public static void glTextureStorage2DMultisample(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLboolean") boolean bl) {
        GL45C.glTextureStorage2DMultisample(n, n2, n3, n4, n5, bl);
    }

    public static void glTextureStorage3DMultisample(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLboolean") boolean bl) {
        GL45C.glTextureStorage3DMultisample(n, n2, n3, n4, n5, n6, bl);
    }

    public static void nglTextureSubImage1D(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        GL45C.nglTextureSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") long l) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, shortBuffer);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, intBuffer);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, floatBuffer);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, doubleBuffer);
    }

    public static void nglTextureSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
        GL45C.nglTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") long l) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer);
    }

    public static void nglTextureSubImage3D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, long l) {
        GL45C.nglTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") long l) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }

    public static void nglCompressedTextureSubImage1D(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        GL45C.nglCompressedTextureSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glCompressedTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="void const *") long l) {
        GL45C.glCompressedTextureSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glCompressedTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glCompressedTextureSubImage1D(n, n2, n3, n4, n5, byteBuffer);
    }

    public static void nglCompressedTextureSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
        GL45C.nglCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glCompressedTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void const *") long l) {
        GL45C.glCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glCompressedTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glCompressedTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, byteBuffer);
    }

    public static void nglCompressedTextureSubImage3D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, long l) {
        GL45C.nglCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glCompressedTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLsizei") int n10, @NativeType(value="void const *") long l) {
        GL45C.glCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glCompressedTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL45C.glCompressedTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, byteBuffer);
    }

    public static void glCopyTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6) {
        GL45C.glCopyTextureSubImage1D(n, n2, n3, n4, n5, n6);
    }

    public static void glCopyTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8) {
        GL45C.glCopyTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glCopyTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9) {
        GL45C.glCopyTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }

    public static void glTextureParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat") float f) {
        GL45C.glTextureParameterf(n, n2, f);
    }

    public static void nglTextureParameterfv(int n, int n2, long l) {
        GL45C.nglTextureParameterfv(n, n2, l);
    }

    public static void glTextureParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL45C.glTextureParameterfv(n, n2, floatBuffer);
    }

    public static void glTextureParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL45C.glTextureParameteri(n, n2, n3);
    }

    public static void nglTextureParameterIiv(int n, int n2, long l) {
        GL45C.nglTextureParameterIiv(n, n2, l);
    }

    public static void glTextureParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL45C.glTextureParameterIiv(n, n2, intBuffer);
    }

    public static void glTextureParameterIi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int n3) {
        GL45C.glTextureParameterIi(n, n2, n3);
    }

    public static void nglTextureParameterIuiv(int n, int n2, long l) {
        GL45C.nglTextureParameterIuiv(n, n2, l);
    }

    public static void glTextureParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL45C.glTextureParameterIuiv(n, n2, intBuffer);
    }

    public static void glTextureParameterIui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int n3) {
        GL45C.glTextureParameterIui(n, n2, n3);
    }

    public static void nglTextureParameteriv(int n, int n2, long l) {
        GL45C.nglTextureParameteriv(n, n2, l);
    }

    public static void glTextureParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL45C.glTextureParameteriv(n, n2, intBuffer);
    }

    public static void glGenerateTextureMipmap(@NativeType(value="GLuint") int n) {
        GL45C.glGenerateTextureMipmap(n);
    }

    public static void glBindTextureUnit(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL45C.glBindTextureUnit(n, n2);
    }

    public static void nglGetTextureImage(int n, int n2, int n3, int n4, int n5, long l) {
        GL45C.nglGetTextureImage(n, n2, n3, n4, n5, l);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="void *") long l) {
        GL45C.glGetTextureImage(n, n2, n3, n4, n5, l);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glGetTextureImage(n, n2, n3, n4, byteBuffer);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL45C.glGetTextureImage(n, n2, n3, n4, shortBuffer);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") IntBuffer intBuffer) {
        GL45C.glGetTextureImage(n, n2, n3, n4, intBuffer);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL45C.glGetTextureImage(n, n2, n3, n4, floatBuffer);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL45C.glGetTextureImage(n, n2, n3, n4, doubleBuffer);
    }

    public static void nglGetCompressedTextureImage(int n, int n2, int n3, long l) {
        GL45C.nglGetCompressedTextureImage(n, n2, n3, l);
    }

    public static void glGetCompressedTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void *") long l) {
        GL45C.glGetCompressedTextureImage(n, n2, n3, l);
    }

    public static void glGetCompressedTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glGetCompressedTextureImage(n, n2, byteBuffer);
    }

    public static void nglGetTextureLevelParameterfv(int n, int n2, int n3, long l) {
        GL45C.nglGetTextureLevelParameterfv(n, n2, n3, l);
    }

    public static void glGetTextureLevelParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glGetTextureLevelParameterfv(n, n2, n3, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetTextureLevelParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        return GL45C.glGetTextureLevelParameterf(n, n2, n3);
    }

    public static void nglGetTextureLevelParameteriv(int n, int n2, int n3, long l) {
        GL45C.nglGetTextureLevelParameteriv(n, n2, n3, l);
    }

    public static void glGetTextureLevelParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetTextureLevelParameteriv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTextureLevelParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        return GL45C.glGetTextureLevelParameteri(n, n2, n3);
    }

    public static void nglGetTextureParameterfv(int n, int n2, long l) {
        GL45C.nglGetTextureParameterfv(n, n2, l);
    }

    public static void glGetTextureParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glGetTextureParameterfv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetTextureParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetTextureParameterf(n, n2);
    }

    public static void nglGetTextureParameterIiv(int n, int n2, long l) {
        GL45C.nglGetTextureParameterIiv(n, n2, l);
    }

    public static void glGetTextureParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetTextureParameterIiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTextureParameterIi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetTextureParameterIi(n, n2);
    }

    public static void nglGetTextureParameterIuiv(int n, int n2, long l) {
        GL45C.nglGetTextureParameterIuiv(n, n2, l);
    }

    public static void glGetTextureParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glGetTextureParameterIuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTextureParameterIui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetTextureParameterIui(n, n2);
    }

    public static void nglGetTextureParameteriv(int n, int n2, long l) {
        GL45C.nglGetTextureParameteriv(n, n2, l);
    }

    public static void glGetTextureParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetTextureParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTextureParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetTextureParameteri(n, n2);
    }

    public static void nglCreateVertexArrays(int n, long l) {
        GL45C.nglCreateVertexArrays(n, l);
    }

    public static void glCreateVertexArrays(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateVertexArrays(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateVertexArrays() {
        return GL45C.glCreateVertexArrays();
    }

    public static void glDisableVertexArrayAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL45C.glDisableVertexArrayAttrib(n, n2);
    }

    public static void glEnableVertexArrayAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL45C.glEnableVertexArrayAttrib(n, n2);
    }

    public static void glVertexArrayElementBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL45C.glVertexArrayElementBuffer(n, n2);
    }

    public static void glVertexArrayVertexBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n4) {
        GL45C.glVertexArrayVertexBuffer(n, n2, n3, l, n4);
    }

    public static void nglVertexArrayVertexBuffers(int n, int n2, int n3, long l, long l2, long l3) {
        GL45C.nglVertexArrayVertexBuffers(n, n2, n3, l, l2, l3);
    }

    public static void glVertexArrayVertexBuffers(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") IntBuffer intBuffer2) {
        GL45C.glVertexArrayVertexBuffers(n, n2, intBuffer, pointerBuffer, intBuffer2);
    }

    public static void glVertexArrayAttribFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n5) {
        GL45C.glVertexArrayAttribFormat(n, n2, n3, n4, bl, n5);
    }

    public static void glVertexArrayAttribIFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLuint") int n5) {
        GL45C.glVertexArrayAttribIFormat(n, n2, n3, n4, n5);
    }

    public static void glVertexArrayAttribLFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLuint") int n5) {
        GL45C.glVertexArrayAttribLFormat(n, n2, n3, n4, n5);
    }

    public static void glVertexArrayAttribBinding(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL45C.glVertexArrayAttribBinding(n, n2, n3);
    }

    public static void glVertexArrayBindingDivisor(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL45C.glVertexArrayBindingDivisor(n, n2, n3);
    }

    public static void nglGetVertexArrayiv(int n, int n2, long l) {
        GL45C.nglGetVertexArrayiv(n, n2, l);
    }

    public static void glGetVertexArrayiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetVertexArrayiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetVertexArrayi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL45C.glGetVertexArrayi(n, n2);
    }

    public static void nglGetVertexArrayIndexediv(int n, int n2, int n3, long l) {
        GL45C.nglGetVertexArrayIndexediv(n, n2, n3, l);
    }

    public static void glGetVertexArrayIndexediv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL45C.glGetVertexArrayIndexediv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetVertexArrayIndexedi(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        return GL45C.glGetVertexArrayIndexedi(n, n2, n3);
    }

    public static void nglGetVertexArrayIndexed64iv(int n, int n2, int n3, long l) {
        GL45C.nglGetVertexArrayIndexed64iv(n, n2, n3, l);
    }

    public static void glGetVertexArrayIndexed64iv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL45C.glGetVertexArrayIndexed64iv(n, n2, n3, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetVertexArrayIndexed64i(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        return GL45C.glGetVertexArrayIndexed64i(n, n2, n3);
    }

    public static void nglCreateSamplers(int n, long l) {
        GL45C.nglCreateSamplers(n, l);
    }

    public static void glCreateSamplers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateSamplers(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateSamplers() {
        return GL45C.glCreateSamplers();
    }

    public static void nglCreateProgramPipelines(int n, long l) {
        GL45C.nglCreateProgramPipelines(n, l);
    }

    public static void glCreateProgramPipelines(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateProgramPipelines(intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateProgramPipelines() {
        return GL45C.glCreateProgramPipelines();
    }

    public static void nglCreateQueries(int n, int n2, long l) {
        GL45C.nglCreateQueries(n, n2, l);
    }

    public static void glCreateQueries(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL45C.glCreateQueries(n, intBuffer);
    }

    @NativeType(value="void")
    public static int glCreateQueries(@NativeType(value="GLenum") int n) {
        return GL45C.glCreateQueries(n);
    }

    public static void glGetQueryBufferObjecti64v(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLintptr") long l) {
        GL45C.glGetQueryBufferObjecti64v(n, n2, n3, l);
    }

    public static void glGetQueryBufferObjectiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLintptr") long l) {
        GL45C.glGetQueryBufferObjectiv(n, n2, n3, l);
    }

    public static void glGetQueryBufferObjectui64v(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLintptr") long l) {
        GL45C.glGetQueryBufferObjectui64v(n, n2, n3, l);
    }

    public static void glGetQueryBufferObjectuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLintptr") long l) {
        GL45C.glGetQueryBufferObjectuiv(n, n2, n3, l);
    }

    public static void glCreateTransformFeedbacks(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateTransformFeedbacks(nArray);
    }

    public static void glGetTransformFeedbackiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetTransformFeedbackiv(n, n2, nArray);
    }

    public static void glGetTransformFeedbacki_v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetTransformFeedbacki_v(n, n2, n3, nArray);
    }

    public static void glGetTransformFeedbacki64_v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint64 *") long[] lArray) {
        GL45C.glGetTransformFeedbacki64_v(n, n2, n3, lArray);
    }

    public static void glCreateBuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateBuffers(nArray);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, sArray, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, nArray, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, fArray, n2);
    }

    public static void glNamedBufferStorage(@NativeType(value="GLuint") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLbitfield") int n2) {
        GL45C.glNamedBufferStorage(n, dArray, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, sArray, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, nArray, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") long[] lArray, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, lArray, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, fArray, n2);
    }

    public static void glNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLenum") int n2) {
        GL45C.glNamedBufferData(n, dArray, n2);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") short[] sArray) {
        GL45C.glNamedBufferSubData(n, l, sArray);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") int[] nArray) {
        GL45C.glNamedBufferSubData(n, l, nArray);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") long[] lArray) {
        GL45C.glNamedBufferSubData(n, l, lArray);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") float[] fArray) {
        GL45C.glNamedBufferSubData(n, l, fArray);
    }

    public static void glNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") double[] dArray) {
        GL45C.glNamedBufferSubData(n, l, dArray);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, sArray);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, nArray);
    }

    public static void glClearNamedBufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL45C.glClearNamedBufferData(n, n2, n3, n4, fArray);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, sArray);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, nArray);
    }

    public static void glClearNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL45C.glClearNamedBufferSubData(n, n2, l, l2, n3, n4, fArray);
    }

    public static void glGetNamedBufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetNamedBufferParameteriv(n, n2, nArray);
    }

    public static void glGetNamedBufferParameteri64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        GL45C.glGetNamedBufferParameteri64v(n, n2, lArray);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") short[] sArray) {
        GL45C.glGetNamedBufferSubData(n, l, sArray);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") int[] nArray) {
        GL45C.glGetNamedBufferSubData(n, l, nArray);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") long[] lArray) {
        GL45C.glGetNamedBufferSubData(n, l, lArray);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") float[] fArray) {
        GL45C.glGetNamedBufferSubData(n, l, fArray);
    }

    public static void glGetNamedBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") double[] dArray) {
        GL45C.glGetNamedBufferSubData(n, l, dArray);
    }

    public static void glCreateFramebuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateFramebuffers(nArray);
    }

    public static void glNamedFramebufferDrawBuffers(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int[] nArray) {
        GL45C.glNamedFramebufferDrawBuffers(n, nArray);
    }

    public static void glInvalidateNamedFramebufferData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int[] nArray) {
        GL45C.glInvalidateNamedFramebufferData(n, nArray);
    }

    public static void glInvalidateNamedFramebufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int[] nArray, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL45C.glInvalidateNamedFramebufferSubData(n, nArray, n2, n3, n4, n5);
    }

    public static void glClearNamedFramebufferiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glClearNamedFramebufferiv(n, n2, n3, nArray);
    }

    public static void glClearNamedFramebufferuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glClearNamedFramebufferuiv(n, n2, n3, nArray);
    }

    public static void glClearNamedFramebufferfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glClearNamedFramebufferfv(n, n2, n3, fArray);
    }

    public static void glGetNamedFramebufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetNamedFramebufferParameteriv(n, n2, nArray);
    }

    public static void glGetNamedFramebufferAttachmentParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetNamedFramebufferAttachmentParameteriv(n, n2, n3, nArray);
    }

    public static void glCreateRenderbuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateRenderbuffers(nArray);
    }

    public static void glGetNamedRenderbufferParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetNamedRenderbufferParameteriv(n, n2, nArray);
    }

    public static void glCreateTextures(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateTextures(n, nArray);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") short[] sArray) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, sArray);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") int[] nArray) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, nArray);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") float[] fArray) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, fArray);
    }

    public static void glTextureSubImage1D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") double[] dArray) {
        GL45C.glTextureSubImage1D(n, n2, n3, n4, n5, n6, dArray);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") short[] sArray) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, sArray);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") int[] nArray) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, nArray);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") float[] fArray) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, fArray);
    }

    public static void glTextureSubImage2D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") double[] dArray) {
        GL45C.glTextureSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, dArray);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") short[] sArray) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") int[] nArray) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") float[] fArray) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray);
    }

    public static void glTextureSubImage3D(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") double[] dArray) {
        GL45C.glTextureSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray);
    }

    public static void glTextureParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL45C.glTextureParameterfv(n, n2, fArray);
    }

    public static void glTextureParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL45C.glTextureParameterIiv(n, n2, nArray);
    }

    public static void glTextureParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL45C.glTextureParameterIuiv(n, n2, nArray);
    }

    public static void glTextureParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL45C.glTextureParameteriv(n, n2, nArray);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") short[] sArray) {
        GL45C.glGetTextureImage(n, n2, n3, n4, sArray);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") int[] nArray) {
        GL45C.glGetTextureImage(n, n2, n3, n4, nArray);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") float[] fArray) {
        GL45C.glGetTextureImage(n, n2, n3, n4, fArray);
    }

    public static void glGetTextureImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") double[] dArray) {
        GL45C.glGetTextureImage(n, n2, n3, n4, dArray);
    }

    public static void glGetTextureLevelParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glGetTextureLevelParameterfv(n, n2, n3, fArray);
    }

    public static void glGetTextureLevelParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetTextureLevelParameteriv(n, n2, n3, nArray);
    }

    public static void glGetTextureParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glGetTextureParameterfv(n, n2, fArray);
    }

    public static void glGetTextureParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetTextureParameterIiv(n, n2, nArray);
    }

    public static void glGetTextureParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL45C.glGetTextureParameterIuiv(n, n2, nArray);
    }

    public static void glGetTextureParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetTextureParameteriv(n, n2, nArray);
    }

    public static void glCreateVertexArrays(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateVertexArrays(nArray);
    }

    public static void glVertexArrayVertexBuffers(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") int[] nArray2) {
        GL45C.glVertexArrayVertexBuffers(n, n2, nArray, pointerBuffer, nArray2);
    }

    public static void glGetVertexArrayiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetVertexArrayiv(n, n2, nArray);
    }

    public static void glGetVertexArrayIndexediv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL45C.glGetVertexArrayIndexediv(n, n2, n3, nArray);
    }

    public static void glGetVertexArrayIndexed64iv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint64 *") long[] lArray) {
        GL45C.glGetVertexArrayIndexed64iv(n, n2, n3, lArray);
    }

    public static void glCreateSamplers(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateSamplers(nArray);
    }

    public static void glCreateProgramPipelines(@NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateProgramPipelines(nArray);
    }

    public static void glCreateQueries(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") int[] nArray) {
        GL45C.glCreateQueries(n, nArray);
    }

    private static boolean ARB_framebuffer_object(Set<String> set) {
        return set.contains("OpenGL30") || set.contains("GL_ARB_framebuffer_object");
    }

    private static boolean ARB_map_buffer_range(Set<String> set) {
        return set.contains("OpenGL30") || set.contains("GL_ARB_map_buffer_range");
    }

    private static boolean ARB_vertex_array_object(Set<String> set) {
        return set.contains("OpenGL30") || set.contains("GL_ARB_vertex_array_object");
    }

    private static boolean ARB_copy_buffer(Set<String> set) {
        return set.contains("OpenGL31") || set.contains("GL_ARB_copy_buffer");
    }

    private static boolean ARB_texture_buffer_object(Set<String> set) {
        return set.contains("OpenGL31") || set.contains("GL_ARB_texture_buffer_object");
    }

    private static boolean ARB_uniform_buffer_object(Set<String> set) {
        return set.contains("OpenGL31") || set.contains("GL_ARB_uniform_buffer_object");
    }

    private static boolean ARB_instanced_arrays(Set<String> set) {
        return set.contains("OpenGL33") || set.contains("GL_ARB_instanced_arrays");
    }

    private static boolean ARB_sampler_objects(Set<String> set) {
        return set.contains("OpenGL33") || set.contains("GL_ARB_sampler_objects");
    }

    private static boolean ARB_transform_feedback2(Set<String> set) {
        return set.contains("OpenGL40") || set.contains("GL_ARB_transform_feedback2");
    }

    private static boolean ARB_vertex_attrib_64bit(Set<String> set) {
        return set.contains("OpenGL41") || set.contains("GL_ARB_vertex_attrib_64bit");
    }

    private static boolean ARB_separate_shader_objects(Set<String> set) {
        return set.contains("OpenGL41") || set.contains("GL_ARB_separate_shader_objects");
    }

    private static boolean ARB_texture_storage(Set<String> set) {
        return set.contains("OpenGL42") || set.contains("GL_ARB_texture_storage");
    }

    private static boolean ARB_texture_storage_multisample(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_texture_storage_multisample");
    }

    private static boolean ARB_vertex_attrib_binding(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_vertex_attrib_binding");
    }

    private static boolean ARB_invalidate_subdata(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_invalidate_subdata");
    }

    private static boolean ARB_texture_buffer_range(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_texture_buffer_range");
    }

    private static boolean ARB_clear_buffer_object(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_clear_buffer_object");
    }

    private static boolean ARB_framebuffer_no_attachments(Set<String> set) {
        return set.contains("OpenGL43") || set.contains("GL_ARB_framebuffer_no_attachments");
    }

    private static boolean ARB_buffer_storage(Set<String> set) {
        return set.contains("OpenGL44") || set.contains("GL_ARB_buffer_storage");
    }

    private static boolean ARB_clear_texture(Set<String> set) {
        return set.contains("OpenGL44") || set.contains("GL_ARB_clear_texture");
    }

    private static boolean ARB_multi_bind(Set<String> set) {
        return set.contains("OpenGL44") || set.contains("GL_ARB_multi_bind");
    }

    private static boolean ARB_query_buffer_object(Set<String> set) {
        return set.contains("OpenGL44") || set.contains("GL_ARB_query_buffer_object");
    }

    static {
        GL.initialize();
    }
}

