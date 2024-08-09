/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTEfx {
    public static final int ALC_EFX_MAJOR_VERSION = 131073;
    public static final int ALC_EFX_MINOR_VERSION = 131074;
    public static final int ALC_MAX_AUXILIARY_SENDS = 131075;
    public static final int AL_METERS_PER_UNIT = 131076;
    public static final int AL_DIRECT_FILTER = 131077;
    public static final int AL_AUXILIARY_SEND_FILTER = 131078;
    public static final int AL_AIR_ABSORPTION_FACTOR = 131079;
    public static final int AL_ROOM_ROLLOFF_FACTOR = 131080;
    public static final int AL_CONE_OUTER_GAINHF = 131081;
    public static final int AL_DIRECT_FILTER_GAINHF_AUTO = 131082;
    public static final int AL_AUXILIARY_SEND_FILTER_GAIN_AUTO = 131083;
    public static final int AL_AUXILIARY_SEND_FILTER_GAINHF_AUTO = 131084;
    public static final int AL_EFFECTSLOT_NULL = 0;
    public static final int AL_EFFECTSLOT_EFFECT = 1;
    public static final int AL_EFFECTSLOT_GAIN = 2;
    public static final int AL_EFFECTSLOT_AUXILIARY_SEND_AUTO = 3;
    public static final int AL_REVERB_DENSITY = 1;
    public static final int AL_REVERB_DIFFUSION = 2;
    public static final int AL_REVERB_GAIN = 3;
    public static final int AL_REVERB_GAINHF = 4;
    public static final int AL_REVERB_DECAY_TIME = 5;
    public static final int AL_REVERB_DECAY_HFRATIO = 6;
    public static final int AL_REVERB_REFLECTIONS_GAIN = 7;
    public static final int AL_REVERB_REFLECTIONS_DELAY = 8;
    public static final int AL_REVERB_LATE_REVERB_GAIN = 9;
    public static final int AL_REVERB_LATE_REVERB_DELAY = 10;
    public static final int AL_REVERB_AIR_ABSORPTION_GAINHF = 11;
    public static final int AL_REVERB_ROOM_ROLLOFF_FACTOR = 12;
    public static final int AL_REVERB_DECAY_HFLIMIT = 13;
    public static final int AL_EAXREVERB_DENSITY = 1;
    public static final int AL_EAXREVERB_DIFFUSION = 2;
    public static final int AL_EAXREVERB_GAIN = 3;
    public static final int AL_EAXREVERB_GAINHF = 4;
    public static final int AL_EAXREVERB_GAINLF = 5;
    public static final int AL_EAXREVERB_DECAY_TIME = 6;
    public static final int AL_EAXREVERB_DECAY_HFRATIO = 7;
    public static final int AL_EAXREVERB_DECAY_LFRATIO = 8;
    public static final int AL_EAXREVERB_REFLECTIONS_GAIN = 9;
    public static final int AL_EAXREVERB_REFLECTIONS_DELAY = 10;
    public static final int AL_EAXREVERB_REFLECTIONS_PAN = 11;
    public static final int AL_EAXREVERB_LATE_REVERB_GAIN = 12;
    public static final int AL_EAXREVERB_LATE_REVERB_DELAY = 13;
    public static final int AL_EAXREVERB_LATE_REVERB_PAN = 14;
    public static final int AL_EAXREVERB_ECHO_TIME = 15;
    public static final int AL_EAXREVERB_ECHO_DEPTH = 16;
    public static final int AL_EAXREVERB_MODULATION_TIME = 17;
    public static final int AL_EAXREVERB_MODULATION_DEPTH = 18;
    public static final int AL_EAXREVERB_AIR_ABSORPTION_GAINHF = 19;
    public static final int AL_EAXREVERB_HFREFERENCE = 20;
    public static final int AL_EAXREVERB_LFREFERENCE = 21;
    public static final int AL_EAXREVERB_ROOM_ROLLOFF_FACTOR = 22;
    public static final int AL_EAXREVERB_DECAY_HFLIMIT = 23;
    public static final int AL_CHORUS_WAVEFORM = 1;
    public static final int AL_CHORUS_PHASE = 2;
    public static final int AL_CHORUS_RATE = 3;
    public static final int AL_CHORUS_DEPTH = 4;
    public static final int AL_CHORUS_FEEDBACK = 5;
    public static final int AL_CHORUS_DELAY = 6;
    public static final int AL_DISTORTION_EDGE = 1;
    public static final int AL_DISTORTION_GAIN = 2;
    public static final int AL_DISTORTION_LOWPASS_CUTOFF = 3;
    public static final int AL_DISTORTION_EQCENTER = 4;
    public static final int AL_DISTORTION_EQBANDWIDTH = 5;
    public static final int AL_ECHO_DELAY = 1;
    public static final int AL_ECHO_LRDELAY = 2;
    public static final int AL_ECHO_DAMPING = 3;
    public static final int AL_ECHO_FEEDBACK = 4;
    public static final int AL_ECHO_SPREAD = 5;
    public static final int AL_FLANGER_WAVEFORM = 1;
    public static final int AL_FLANGER_PHASE = 2;
    public static final int AL_FLANGER_RATE = 3;
    public static final int AL_FLANGER_DEPTH = 4;
    public static final int AL_FLANGER_FEEDBACK = 5;
    public static final int AL_FLANGER_DELAY = 6;
    public static final int AL_FREQUENCY_SHIFTER_FREQUENCY = 1;
    public static final int AL_FREQUENCY_SHIFTER_LEFT_DIRECTION = 2;
    public static final int AL_FREQUENCY_SHIFTER_RIGHT_DIRECTION = 3;
    public static final int AL_VOCMORPHER_PHONEMEA = 1;
    public static final int AL_VOCMORPHER_PHONEMEA_COARSE_TUNING = 2;
    public static final int AL_VOCMORPHER_PHONEMEB = 3;
    public static final int AL_VOCMORPHER_PHONEMEB_COARSE_TUNING = 4;
    public static final int AL_VOCMORPHER_WAVEFORM = 5;
    public static final int AL_VOCMORPHER_RATE = 6;
    public static final int AL_PITCH_SHIFTER_COARSE_TUNE = 1;
    public static final int AL_PITCH_SHIFTER_FINE_TUNE = 2;
    public static final int AL_RING_MODULATOR_FREQUENCY = 1;
    public static final int AL_RING_MODULATOR_HIGHPASS_CUTOFF = 2;
    public static final int AL_RING_MODULATOR_WAVEFORM = 3;
    public static final int AL_AUTOWAH_ATTACK_TIME = 1;
    public static final int AL_AUTOWAH_RELEASE_TIME = 2;
    public static final int AL_AUTOWAH_RESONANCE = 3;
    public static final int AL_AUTOWAH_PEAK_GAIN = 4;
    public static final int AL_COMPRESSOR_ONOFF = 1;
    public static final int AL_EQUALIZER_LOW_GAIN = 1;
    public static final int AL_EQUALIZER_LOW_CUTOFF = 2;
    public static final int AL_EQUALIZER_MID1_GAIN = 3;
    public static final int AL_EQUALIZER_MID1_CENTER = 4;
    public static final int AL_EQUALIZER_MID1_WIDTH = 5;
    public static final int AL_EQUALIZER_MID2_GAIN = 6;
    public static final int AL_EQUALIZER_MID2_CENTER = 7;
    public static final int AL_EQUALIZER_MID2_WIDTH = 8;
    public static final int AL_EQUALIZER_HIGH_GAIN = 9;
    public static final int AL_EQUALIZER_HIGH_CUTOFF = 10;
    public static final int AL_EFFECT_FIRST_PARAMETER = 0;
    public static final int AL_EFFECT_LAST_PARAMETER = 32768;
    public static final int AL_EFFECT_TYPE = 32769;
    public static final int AL_EFFECT_NULL = 0;
    public static final int AL_EFFECT_REVERB = 1;
    public static final int AL_EFFECT_CHORUS = 2;
    public static final int AL_EFFECT_DISTORTION = 3;
    public static final int AL_EFFECT_ECHO = 4;
    public static final int AL_EFFECT_FLANGER = 5;
    public static final int AL_EFFECT_FREQUENCY_SHIFTER = 6;
    public static final int AL_EFFECT_VOCAL_MORPHER = 7;
    public static final int AL_EFFECT_PITCH_SHIFTER = 8;
    public static final int AL_EFFECT_RING_MODULATOR = 9;
    public static final int AL_EFFECT_AUTOWAH = 10;
    public static final int AL_EFFECT_COMPRESSOR = 11;
    public static final int AL_EFFECT_EQUALIZER = 12;
    public static final int AL_EFFECT_EAXREVERB = 32768;
    public static final int AL_LOWPASS_GAIN = 1;
    public static final int AL_LOWPASS_GAINHF = 2;
    public static final int AL_HIGHPASS_GAIN = 1;
    public static final int AL_HIGHPASS_GAINLF = 2;
    public static final int AL_BANDPASS_GAIN = 1;
    public static final int AL_BANDPASS_GAINLF = 2;
    public static final int AL_BANDPASS_GAINHF = 3;
    public static final int AL_FILTER_FIRST_PARAMETER = 0;
    public static final int AL_FILTER_LAST_PARAMETER = 32768;
    public static final int AL_FILTER_TYPE = 32769;
    public static final int AL_FILTER_NULL = 0;
    public static final int AL_FILTER_LOWPASS = 1;
    public static final int AL_FILTER_HIGHPASS = 2;
    public static final int AL_FILTER_BANDPASS = 3;
    public static final float AL_MIN_AIR_ABSORPTION_FACTOR = 0.0f;
    public static final float AL_MAX_AIR_ABSORPTION_FACTOR = 10.0f;
    public static final float AL_DEFAULT_AIR_ABSORPTION_FACTOR = 0.0f;
    public static final float AL_MIN_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final float AL_MAX_ROOM_ROLLOFF_FACTOR = 10.0f;
    public static final float AL_DEFAULT_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final float AL_MIN_CONE_OUTER_GAINHF = 0.0f;
    public static final float AL_MAX_CONE_OUTER_GAINHF = 1.0f;
    public static final float AL_DEFAULT_CONE_OUTER_GAINHF = 1.0f;
    public static final int AL_MIN_DIRECT_FILTER_GAINHF_AUTO = 0;
    public static final int AL_MAX_DIRECT_FILTER_GAINHF_AUTO = 1;
    public static final int AL_DEFAULT_DIRECT_FILTER_GAINHF_AUTO = 1;
    public static final int AL_MIN_AUXILIARY_SEND_FILTER_GAIN_AUTO = 0;
    public static final int AL_MAX_AUXILIARY_SEND_FILTER_GAIN_AUTO = 1;
    public static final int AL_DEFAULT_AUXILIARY_SEND_FILTER_GAIN_AUTO = 1;
    public static final int AL_MIN_AUXILIARY_SEND_FILTER_GAINHF_AUTO = 0;
    public static final int AL_MAX_AUXILIARY_SEND_FILTER_GAINHF_AUTO = 1;
    public static final int AL_DEFAULT_AUXILIARY_SEND_FILTER_GAINHF_AUTO = 1;
    public static final float AL_MIN_METERS_PER_UNIT = Float.MIN_VALUE;
    public static final float AL_MAX_METERS_PER_UNIT = Float.MAX_VALUE;
    public static final float AL_DEFAULT_METERS_PER_UNIT = 1.0f;
    public static final float AL_REVERB_MIN_DENSITY = 0.0f;
    public static final float AL_REVERB_MAX_DENSITY = 1.0f;
    public static final float AL_REVERB_DEFAULT_DENSITY = 1.0f;
    public static final float AL_REVERB_MIN_DIFFUSION = 0.0f;
    public static final float AL_REVERB_MAX_DIFFUSION = 1.0f;
    public static final float AL_REVERB_DEFAULT_DIFFUSION = 1.0f;
    public static final float AL_REVERB_MIN_GAIN = 0.0f;
    public static final float AL_REVERB_MAX_GAIN = 1.0f;
    public static final float AL_REVERB_DEFAULT_GAIN = 0.32f;
    public static final float AL_REVERB_MIN_GAINHF = 0.0f;
    public static final float AL_REVERB_MAX_GAINHF = 1.0f;
    public static final float AL_REVERB_DEFAULT_GAINHF = 0.89f;
    public static final float AL_REVERB_MIN_DECAY_TIME = 0.1f;
    public static final float AL_REVERB_MAX_DECAY_TIME = 20.0f;
    public static final float AL_REVERB_DEFAULT_DECAY_TIME = 1.49f;
    public static final float AL_REVERB_MIN_DECAY_HFRATIO = 0.1f;
    public static final float AL_REVERB_MAX_DECAY_HFRATIO = 2.0f;
    public static final float AL_REVERB_DEFAULT_DECAY_HFRATIO = 0.83f;
    public static final float AL_REVERB_MIN_REFLECTIONS_GAIN = 0.0f;
    public static final float AL_REVERB_MAX_REFLECTIONS_GAIN = 3.16f;
    public static final float AL_REVERB_DEFAULT_REFLECTIONS_GAIN = 0.05f;
    public static final float AL_REVERB_MIN_REFLECTIONS_DELAY = 0.0f;
    public static final float AL_REVERB_MAX_REFLECTIONS_DELAY = 0.3f;
    public static final float AL_REVERB_DEFAULT_REFLECTIONS_DELAY = 0.007f;
    public static final float AL_REVERB_MIN_LATE_REVERB_GAIN = 0.0f;
    public static final float AL_REVERB_MAX_LATE_REVERB_GAIN = 10.0f;
    public static final float AL_REVERB_DEFAULT_LATE_REVERB_GAIN = 1.26f;
    public static final float AL_REVERB_MIN_LATE_REVERB_DELAY = 0.0f;
    public static final float AL_REVERB_MAX_LATE_REVERB_DELAY = 0.1f;
    public static final float AL_REVERB_DEFAULT_LATE_REVERB_DELAY = 0.011f;
    public static final float AL_REVERB_MIN_AIR_ABSORPTION_GAINHF = 0.892f;
    public static final float AL_REVERB_MAX_AIR_ABSORPTION_GAINHF = 1.0f;
    public static final float AL_REVERB_DEFAULT_AIR_ABSORPTION_GAINHF = 0.994f;
    public static final float AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final float AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR = 10.0f;
    public static final float AL_REVERB_DEFAULT_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final int AL_REVERB_MIN_DECAY_HFLIMIT = 0;
    public static final int AL_REVERB_MAX_DECAY_HFLIMIT = 1;
    public static final int AL_REVERB_DEFAULT_DECAY_HFLIMIT = 1;
    public static final float AL_EAXREVERB_MIN_DENSITY = 0.0f;
    public static final float AL_EAXREVERB_MAX_DENSITY = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_DENSITY = 1.0f;
    public static final float AL_EAXREVERB_MIN_DIFFUSION = 0.0f;
    public static final float AL_EAXREVERB_MAX_DIFFUSION = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_DIFFUSION = 1.0f;
    public static final float AL_EAXREVERB_MIN_GAIN = 0.0f;
    public static final float AL_EAXREVERB_MAX_GAIN = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_GAIN = 0.32f;
    public static final float AL_EAXREVERB_MIN_GAINHF = 0.0f;
    public static final float AL_EAXREVERB_MAX_GAINHF = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_GAINHF = 0.89f;
    public static final float AL_EAXREVERB_MIN_GAINLF = 0.0f;
    public static final float AL_EAXREVERB_MAX_GAINLF = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_GAINLF = 1.0f;
    public static final float AL_EAXREVERB_MIN_DECAY_TIME = 0.1f;
    public static final float AL_EAXREVERB_MAX_DECAY_TIME = 20.0f;
    public static final float AL_EAXREVERB_DEFAULT_DECAY_TIME = 1.49f;
    public static final float AL_EAXREVERB_MIN_DECAY_HFRATIO = 0.1f;
    public static final float AL_EAXREVERB_MAX_DECAY_HFRATIO = 2.0f;
    public static final float AL_EAXREVERB_DEFAULT_DECAY_HFRATIO = 0.83f;
    public static final float AL_EAXREVERB_MIN_DECAY_LFRATIO = 0.1f;
    public static final float AL_EAXREVERB_MAX_DECAY_LFRATIO = 2.0f;
    public static final float AL_EAXREVERB_DEFAULT_DECAY_LFRATIO = 1.0f;
    public static final float AL_EAXREVERB_MIN_REFLECTIONS_GAIN = 0.0f;
    public static final float AL_EAXREVERB_MAX_REFLECTIONS_GAIN = 3.16f;
    public static final float AL_EAXREVERB_DEFAULT_REFLECTIONS_GAIN = 0.05f;
    public static final float AL_EAXREVERB_MIN_REFLECTIONS_DELAY = 0.0f;
    public static final float AL_EAXREVERB_MAX_REFLECTIONS_DELAY = 0.3f;
    public static final float AL_EAXREVERB_DEFAULT_REFLECTIONS_DELAY = 0.007f;
    public static final float AL_EAXREVERB_DEFAULT_REFLECTIONS_PAN_XYZ = 0.0f;
    public static final float AL_EAXREVERB_MIN_LATE_REVERB_GAIN = 0.0f;
    public static final float AL_EAXREVERB_MAX_LATE_REVERB_GAIN = 10.0f;
    public static final float AL_EAXREVERB_DEFAULT_LATE_REVERB_GAIN = 1.26f;
    public static final float AL_EAXREVERB_MIN_LATE_REVERB_DELAY = 0.0f;
    public static final float AL_EAXREVERB_MAX_LATE_REVERB_DELAY = 0.1f;
    public static final float AL_EAXREVERB_DEFAULT_LATE_REVERB_DELAY = 0.011f;
    public static final float AL_EAXREVERB_DEFAULT_LATE_REVERB_PAN_XYZ = 0.0f;
    public static final float AL_EAXREVERB_MIN_ECHO_TIME = 0.075f;
    public static final float AL_EAXREVERB_MAX_ECHO_TIME = 0.25f;
    public static final float AL_EAXREVERB_DEFAULT_ECHO_TIME = 0.25f;
    public static final float AL_EAXREVERB_MIN_ECHO_DEPTH = 0.0f;
    public static final float AL_EAXREVERB_MAX_ECHO_DEPTH = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_ECHO_DEPTH = 0.0f;
    public static final float AL_EAXREVERB_MIN_MODULATION_TIME = 0.04f;
    public static final float AL_EAXREVERB_MAX_MODULATION_TIME = 4.0f;
    public static final float AL_EAXREVERB_DEFAULT_MODULATION_TIME = 0.25f;
    public static final float AL_EAXREVERB_MIN_MODULATION_DEPTH = 0.0f;
    public static final float AL_EAXREVERB_MAX_MODULATION_DEPTH = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_MODULATION_DEPTH = 0.0f;
    public static final float AL_EAXREVERB_MIN_AIR_ABSORPTION_GAINHF = 0.892f;
    public static final float AL_EAXREVERB_MAX_AIR_ABSORPTION_GAINHF = 1.0f;
    public static final float AL_EAXREVERB_DEFAULT_AIR_ABSORPTION_GAINHF = 0.994f;
    public static final float AL_EAXREVERB_MIN_HFREFERENCE = 1000.0f;
    public static final float AL_EAXREVERB_MAX_HFREFERENCE = 20000.0f;
    public static final float AL_EAXREVERB_DEFAULT_HFREFERENCE = 5000.0f;
    public static final float AL_EAXREVERB_MIN_LFREFERENCE = 20.0f;
    public static final float AL_EAXREVERB_MAX_LFREFERENCE = 1000.0f;
    public static final float AL_EAXREVERB_DEFAULT_LFREFERENCE = 250.0f;
    public static final float AL_EAXREVERB_MIN_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final float AL_EAXREVERB_MAX_ROOM_ROLLOFF_FACTOR = 10.0f;
    public static final float AL_EAXREVERB_DEFAULT_ROOM_ROLLOFF_FACTOR = 0.0f;
    public static final int AL_EAXREVERB_MIN_DECAY_HFLIMIT = 0;
    public static final int AL_EAXREVERB_MAX_DECAY_HFLIMIT = 1;
    public static final int AL_EAXREVERB_DEFAULT_DECAY_HFLIMIT = 1;
    public static final int AL_CHORUS_WAVEFORM_SINUSOID = 0;
    public static final int AL_CHORUS_WAVEFORM_TRIANGLE = 1;
    public static final int AL_CHORUS_MIN_WAVEFORM = 0;
    public static final int AL_CHORUS_MAX_WAVEFORM = 1;
    public static final int AL_CHORUS_DEFAULT_WAVEFORM = 1;
    public static final int AL_CHORUS_MIN_PHASE = -180;
    public static final int AL_CHORUS_MAX_PHASE = 180;
    public static final int AL_CHORUS_DEFAULT_PHASE = 90;
    public static final float AL_CHORUS_MIN_RATE = 0.0f;
    public static final float AL_CHORUS_MAX_RATE = 10.0f;
    public static final float AL_CHORUS_DEFAULT_RATE = 1.1f;
    public static final float AL_CHORUS_MIN_DEPTH = 0.0f;
    public static final float AL_CHORUS_MAX_DEPTH = 1.0f;
    public static final float AL_CHORUS_DEFAULT_DEPTH = 0.1f;
    public static final float AL_CHORUS_MIN_FEEDBACK = -1.0f;
    public static final float AL_CHORUS_MAX_FEEDBACK = 1.0f;
    public static final float AL_CHORUS_DEFAULT_FEEDBACK = 0.25f;
    public static final float AL_CHORUS_MIN_DELAY = 0.0f;
    public static final float AL_CHORUS_MAX_DELAY = 0.016f;
    public static final float AL_CHORUS_DEFAULT_DELAY = 0.016f;
    public static final float AL_DISTORTION_MIN_EDGE = 0.0f;
    public static final float AL_DISTORTION_MAX_EDGE = 1.0f;
    public static final float AL_DISTORTION_DEFAULT_EDGE = 0.2f;
    public static final float AL_DISTORTION_MIN_GAIN = 0.01f;
    public static final float AL_DISTORTION_MAX_GAIN = 1.0f;
    public static final float AL_DISTORTION_DEFAULT_GAIN = 0.05f;
    public static final float AL_DISTORTION_MIN_LOWPASS_CUTOFF = 80.0f;
    public static final float AL_DISTORTION_MAX_LOWPASS_CUTOFF = 24000.0f;
    public static final float AL_DISTORTION_DEFAULT_LOWPASS_CUTOFF = 8000.0f;
    public static final float AL_DISTORTION_MIN_EQCENTER = 80.0f;
    public static final float AL_DISTORTION_MAX_EQCENTER = 24000.0f;
    public static final float AL_DISTORTION_DEFAULT_EQCENTER = 3600.0f;
    public static final float AL_DISTORTION_MIN_EQBANDWIDTH = 80.0f;
    public static final float AL_DISTORTION_MAX_EQBANDWIDTH = 24000.0f;
    public static final float AL_DISTORTION_DEFAULT_EQBANDWIDTH = 3600.0f;
    public static final float AL_ECHO_MIN_DELAY = 0.0f;
    public static final float AL_ECHO_MAX_DELAY = 0.207f;
    public static final float AL_ECHO_DEFAULT_DELAY = 0.1f;
    public static final float AL_ECHO_MIN_LRDELAY = 0.0f;
    public static final float AL_ECHO_MAX_LRDELAY = 0.404f;
    public static final float AL_ECHO_DEFAULT_LRDELAY = 0.1f;
    public static final float AL_ECHO_MIN_DAMPING = 0.0f;
    public static final float AL_ECHO_MAX_DAMPING = 0.99f;
    public static final float AL_ECHO_DEFAULT_DAMPING = 0.5f;
    public static final float AL_ECHO_MIN_FEEDBACK = 0.0f;
    public static final float AL_ECHO_MAX_FEEDBACK = 1.0f;
    public static final float AL_ECHO_DEFAULT_FEEDBACK = 0.5f;
    public static final float AL_ECHO_MIN_SPREAD = -1.0f;
    public static final float AL_ECHO_MAX_SPREAD = 1.0f;
    public static final float AL_ECHO_DEFAULT_SPREAD = -1.0f;
    public static final int AL_FLANGER_WAVEFORM_SINUSOID = 0;
    public static final int AL_FLANGER_WAVEFORM_TRIANGLE = 1;
    public static final int AL_FLANGER_MIN_WAVEFORM = 0;
    public static final int AL_FLANGER_MAX_WAVEFORM = 1;
    public static final int AL_FLANGER_DEFAULT_WAVEFORM = 1;
    public static final int AL_FLANGER_MIN_PHASE = -180;
    public static final int AL_FLANGER_MAX_PHASE = 180;
    public static final int AL_FLANGER_DEFAULT_PHASE = 0;
    public static final float AL_FLANGER_MIN_RATE = 0.0f;
    public static final float AL_FLANGER_MAX_RATE = 10.0f;
    public static final float AL_FLANGER_DEFAULT_RATE = 0.27f;
    public static final float AL_FLANGER_MIN_DEPTH = 0.0f;
    public static final float AL_FLANGER_MAX_DEPTH = 1.0f;
    public static final float AL_FLANGER_DEFAULT_DEPTH = 1.0f;
    public static final float AL_FLANGER_MIN_FEEDBACK = -1.0f;
    public static final float AL_FLANGER_MAX_FEEDBACK = 1.0f;
    public static final float AL_FLANGER_DEFAULT_FEEDBACK = -0.5f;
    public static final float AL_FLANGER_MIN_DELAY = 0.0f;
    public static final float AL_FLANGER_MAX_DELAY = 0.004f;
    public static final float AL_FLANGER_DEFAULT_DELAY = 0.002f;
    public static final float AL_FREQUENCY_SHIFTER_MIN_FREQUENCY = 0.0f;
    public static final float AL_FREQUENCY_SHIFTER_MAX_FREQUENCY = 24000.0f;
    public static final float AL_FREQUENCY_SHIFTER_DEFAULT_FREQUENCY = 0.0f;
    public static final int AL_FREQUENCY_SHIFTER_MIN_LEFT_DIRECTION = 0;
    public static final int AL_FREQUENCY_SHIFTER_MAX_LEFT_DIRECTION = 2;
    public static final int AL_FREQUENCY_SHIFTER_DEFAULT_LEFT_DIRECTION = 0;
    public static final int AL_FREQUENCY_SHIFTER_DIRECTION_DOWN = 0;
    public static final int AL_FREQUENCY_SHIFTER_DIRECTION_UP = 1;
    public static final int AL_FREQUENCY_SHIFTER_DIRECTION_OFF = 2;
    public static final int AL_FREQUENCY_SHIFTER_MIN_RIGHT_DIRECTION = 0;
    public static final int AL_FREQUENCY_SHIFTER_MAX_RIGHT_DIRECTION = 2;
    public static final int AL_FREQUENCY_SHIFTER_DEFAULT_RIGHT_DIRECTION = 0;
    public static final int AL_VOCAL_MORPHER_MIN_PHONEMEA = 0;
    public static final int AL_VOCAL_MORPHER_MAX_PHONEMEA = 29;
    public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEA = 0;
    public static final int AL_VOCAL_MORPHER_MIN_PHONEMEA_COARSE_TUNING = -24;
    public static final int AL_VOCAL_MORPHER_MAX_PHONEMEA_COARSE_TUNING = 24;
    public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEA_COARSE_TUNING = 0;
    public static final int AL_VOCAL_MORPHER_MIN_PHONEMEB = 0;
    public static final int AL_VOCAL_MORPHER_MAX_PHONEMEB = 29;
    public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEB = 10;
    public static final int AL_VOCAL_MORPHER_MIN_PHONEMEB_COARSE_TUNING = -24;
    public static final int AL_VOCAL_MORPHER_MAX_PHONEMEB_COARSE_TUNING = 24;
    public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEB_COARSE_TUNING = 0;
    public static final int AL_VOCAL_MORPHER_PHONEME_A = 0;
    public static final int AL_VOCAL_MORPHER_PHONEME_E = 1;
    public static final int AL_VOCAL_MORPHER_PHONEME_I = 2;
    public static final int AL_VOCAL_MORPHER_PHONEME_O = 3;
    public static final int AL_VOCAL_MORPHER_PHONEME_U = 4;
    public static final int AL_VOCAL_MORPHER_PHONEME_AA = 5;
    public static final int AL_VOCAL_MORPHER_PHONEME_AE = 6;
    public static final int AL_VOCAL_MORPHER_PHONEME_AH = 7;
    public static final int AL_VOCAL_MORPHER_PHONEME_AO = 8;
    public static final int AL_VOCAL_MORPHER_PHONEME_EH = 9;
    public static final int AL_VOCAL_MORPHER_PHONEME_ER = 10;
    public static final int AL_VOCAL_MORPHER_PHONEME_IH = 11;
    public static final int AL_VOCAL_MORPHER_PHONEME_IY = 12;
    public static final int AL_VOCAL_MORPHER_PHONEME_UH = 13;
    public static final int AL_VOCAL_MORPHER_PHONEME_UW = 14;
    public static final int AL_VOCAL_MORPHER_PHONEME_B = 15;
    public static final int AL_VOCAL_MORPHER_PHONEME_D = 16;
    public static final int AL_VOCAL_MORPHER_PHONEME_F = 17;
    public static final int AL_VOCAL_MORPHER_PHONEME_G = 18;
    public static final int AL_VOCAL_MORPHER_PHONEME_J = 19;
    public static final int AL_VOCAL_MORPHER_PHONEME_K = 20;
    public static final int AL_VOCAL_MORPHER_PHONEME_L = 21;
    public static final int AL_VOCAL_MORPHER_PHONEME_M = 22;
    public static final int AL_VOCAL_MORPHER_PHONEME_N = 23;
    public static final int AL_VOCAL_MORPHER_PHONEME_P = 24;
    public static final int AL_VOCAL_MORPHER_PHONEME_R = 25;
    public static final int AL_VOCAL_MORPHER_PHONEME_S = 26;
    public static final int AL_VOCAL_MORPHER_PHONEME_T = 27;
    public static final int AL_VOCAL_MORPHER_PHONEME_V = 28;
    public static final int AL_VOCAL_MORPHER_PHONEME_Z = 29;
    public static final int AL_VOCAL_MORPHER_WAVEFORM_SINUSOID = 0;
    public static final int AL_VOCAL_MORPHER_WAVEFORM_TRIANGLE = 1;
    public static final int AL_VOCAL_MORPHER_WAVEFORM_SAWTOOTH = 2;
    public static final int AL_VOCAL_MORPHER_MIN_WAVEFORM = 0;
    public static final int AL_VOCAL_MORPHER_MAX_WAVEFORM = 2;
    public static final int AL_VOCAL_MORPHER_DEFAULT_WAVEFORM = 0;
    public static final float AL_VOCAL_MORPHER_MIN_RATE = 0.0f;
    public static final float AL_VOCAL_MORPHER_MAX_RATE = 10.0f;
    public static final float AL_VOCAL_MORPHER_DEFAULT_RATE = 1.41f;
    public static final int AL_PITCH_SHIFTER_MIN_COARSE_TUNE = -12;
    public static final int AL_PITCH_SHIFTER_MAX_COARSE_TUNE = 12;
    public static final int AL_PITCH_SHIFTER_DEFAULT_COARSE_TUNE = 12;
    public static final int AL_PITCH_SHIFTER_MIN_FINE_TUNE = -50;
    public static final int AL_PITCH_SHIFTER_MAX_FINE_TUNE = 50;
    public static final int AL_PITCH_SHIFTER_DEFAULT_FINE_TUNE = 0;
    public static final float AL_RING_MODULATOR_MIN_FREQUENCY = 0.0f;
    public static final float AL_RING_MODULATOR_MAX_FREQUENCY = 8000.0f;
    public static final float AL_RING_MODULATOR_DEFAULT_FREQUENCY = 440.0f;
    public static final float AL_RING_MODULATOR_MIN_HIGHPASS_CUTOFF = 0.0f;
    public static final float AL_RING_MODULATOR_MAX_HIGHPASS_CUTOFF = 24000.0f;
    public static final float AL_RING_MODULATOR_DEFAULT_HIGHPASS_CUTOFF = 800.0f;
    public static final int AL_RING_MODULATOR_SINUSOID = 0;
    public static final int AL_RING_MODULATOR_SAWTOOTH = 1;
    public static final int AL_RING_MODULATOR_SQUARE = 2;
    public static final int AL_RING_MODULATOR_MIN_WAVEFORM = 0;
    public static final int AL_RING_MODULATOR_MAX_WAVEFORM = 2;
    public static final int AL_RING_MODULATOR_DEFAULT_WAVEFORM = 0;
    public static final float AL_AUTOWAH_MIN_ATTACK_TIME = 1.0E-4f;
    public static final float AL_AUTOWAH_MAX_ATTACK_TIME = 1.0f;
    public static final float AL_AUTOWAH_DEFAULT_ATTACK_TIME = 0.06f;
    public static final float AL_AUTOWAH_MIN_RELEASE_TIME = 1.0E-4f;
    public static final float AL_AUTOWAH_MAX_RELEASE_TIME = 1.0f;
    public static final float AL_AUTOWAH_DEFAULT_RELEASE_TIME = 0.06f;
    public static final float AL_AUTOWAH_MIN_RESONANCE = 2.0f;
    public static final float AL_AUTOWAH_MAX_RESONANCE = 1000.0f;
    public static final float AL_AUTOWAH_DEFAULT_RESONANCE = 1000.0f;
    public static final float AL_AUTOWAH_MIN_PEAK_GAIN = 3.0E-5f;
    public static final float AL_AUTOWAH_MAX_PEAK_GAIN = 31621.0f;
    public static final float AL_AUTOWAH_DEFAULT_PEAK_GAIN = 11.22f;
    public static final int AL_COMPRESSOR_MIN_ONOFF = 0;
    public static final int AL_COMPRESSOR_MAX_ONOFF = 1;
    public static final int AL_COMPRESSOR_DEFAULT_ONOFF = 1;
    public static final float AL_EQUALIZER_MIN_LOW_GAIN = 0.126f;
    public static final float AL_EQUALIZER_MAX_LOW_GAIN = 7.943f;
    public static final float AL_EQUALIZER_DEFAULT_LOW_GAIN = 1.0f;
    public static final float AL_EQUALIZER_MIN_LOW_CUTOFF = 50.0f;
    public static final float AL_EQUALIZER_MAX_LOW_CUTOFF = 800.0f;
    public static final float AL_EQUALIZER_DEFAULT_LOW_CUTOFF = 200.0f;
    public static final float AL_EQUALIZER_MIN_MID1_GAIN = 0.126f;
    public static final float AL_EQUALIZER_MAX_MID1_GAIN = 7.943f;
    public static final float AL_EQUALIZER_DEFAULT_MID1_GAIN = 1.0f;
    public static final float AL_EQUALIZER_MIN_MID1_CENTER = 200.0f;
    public static final float AL_EQUALIZER_MAX_MID1_CENTER = 3000.0f;
    public static final float AL_EQUALIZER_DEFAULT_MID1_CENTER = 500.0f;
    public static final float AL_EQUALIZER_MIN_MID1_WIDTH = 0.01f;
    public static final float AL_EQUALIZER_MAX_MID1_WIDTH = 1.0f;
    public static final float AL_EQUALIZER_DEFAULT_MID1_WIDTH = 1.0f;
    public static final float AL_EQUALIZER_MIN_MID2_GAIN = 0.126f;
    public static final float AL_EQUALIZER_MAX_MID2_GAIN = 7.943f;
    public static final float AL_EQUALIZER_DEFAULT_MID2_GAIN = 1.0f;
    public static final float AL_EQUALIZER_MIN_MID2_CENTER = 1000.0f;
    public static final float AL_EQUALIZER_MAX_MID2_CENTER = 8000.0f;
    public static final float AL_EQUALIZER_DEFAULT_MID2_CENTER = 3000.0f;
    public static final float AL_EQUALIZER_MIN_MID2_WIDTH = 0.01f;
    public static final float AL_EQUALIZER_MAX_MID2_WIDTH = 1.0f;
    public static final float AL_EQUALIZER_DEFAULT_MID2_WIDTH = 1.0f;
    public static final float AL_EQUALIZER_MIN_HIGH_GAIN = 0.126f;
    public static final float AL_EQUALIZER_MAX_HIGH_GAIN = 7.943f;
    public static final float AL_EQUALIZER_DEFAULT_HIGH_GAIN = 1.0f;
    public static final float AL_EQUALIZER_MIN_HIGH_CUTOFF = 4000.0f;
    public static final float AL_EQUALIZER_MAX_HIGH_CUTOFF = 16000.0f;
    public static final float AL_EQUALIZER_DEFAULT_HIGH_CUTOFF = 6000.0f;
    public static final float AL_LOWPASS_MIN_GAIN = 0.0f;
    public static final float AL_LOWPASS_MAX_GAIN = 1.0f;
    public static final float AL_LOWPASS_DEFAULT_GAIN = 1.0f;
    public static final float AL_LOWPASS_MIN_GAINHF = 0.0f;
    public static final float AL_LOWPASS_MAX_GAINHF = 1.0f;
    public static final float AL_LOWPASS_DEFAULT_GAINHF = 1.0f;
    public static final float AL_HIGHPASS_MIN_GAIN = 0.0f;
    public static final float AL_HIGHPASS_MAX_GAIN = 1.0f;
    public static final float AL_HIGHPASS_DEFAULT_GAIN = 1.0f;
    public static final float AL_HIGHPASS_MIN_GAINLF = 0.0f;
    public static final float AL_HIGHPASS_MAX_GAINLF = 1.0f;
    public static final float AL_HIGHPASS_DEFAULT_GAINLF = 1.0f;
    public static final float AL_BANDPASS_MIN_GAIN = 0.0f;
    public static final float AL_BANDPASS_MAX_GAIN = 1.0f;
    public static final float AL_BANDPASS_DEFAULT_GAIN = 1.0f;
    public static final float AL_BANDPASS_MIN_GAINHF = 0.0f;
    public static final float AL_BANDPASS_MAX_GAINHF = 1.0f;
    public static final float AL_BANDPASS_DEFAULT_GAINHF = 1.0f;
    public static final float AL_BANDPASS_MIN_GAINLF = 0.0f;
    public static final float AL_BANDPASS_MAX_GAINLF = 1.0f;
    public static final float AL_BANDPASS_DEFAULT_GAINLF = 1.0f;

    protected EXTEfx() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alGenEffects, aLCapabilities.alDeleteEffects, aLCapabilities.alIsEffect, aLCapabilities.alEffecti, aLCapabilities.alEffectiv, aLCapabilities.alEffectf, aLCapabilities.alEffectfv, aLCapabilities.alGetEffecti, aLCapabilities.alGetEffectiv, aLCapabilities.alGetEffectf, aLCapabilities.alGetEffectfv, aLCapabilities.alGenFilters, aLCapabilities.alDeleteFilters, aLCapabilities.alIsFilter, aLCapabilities.alFilteri, aLCapabilities.alFilteriv, aLCapabilities.alFilterf, aLCapabilities.alFilterfv, aLCapabilities.alGetFilteri, aLCapabilities.alGetFilteriv, aLCapabilities.alGetFilterf, aLCapabilities.alGetFilterfv, aLCapabilities.alGenAuxiliaryEffectSlots, aLCapabilities.alDeleteAuxiliaryEffectSlots, aLCapabilities.alIsAuxiliaryEffectSlot, aLCapabilities.alAuxiliaryEffectSloti, aLCapabilities.alAuxiliaryEffectSlotiv, aLCapabilities.alAuxiliaryEffectSlotf, aLCapabilities.alAuxiliaryEffectSlotfv, aLCapabilities.alGetAuxiliaryEffectSloti, aLCapabilities.alGetAuxiliaryEffectSlotiv, aLCapabilities.alGetAuxiliaryEffectSlotf, aLCapabilities.alGetAuxiliaryEffectSlotfv);
    }

    public static void nalGenEffects(int n, long l) {
        long l2 = AL.getICD().alGenEffects;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGenEffects(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalGenEffects(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGenEffects() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGenEffects(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nalDeleteEffects(int n, long l) {
        long l2 = AL.getICD().alDeleteEffects;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteEffects(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalDeleteEffects(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alDeleteEffects(@NativeType(value="ALuint *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTEfx.nalDeleteEffects(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALboolean")
    public static boolean alIsEffect(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alIsEffect;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alEffecti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3) {
        long l = AL.getICD().alEffecti;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, l);
    }

    public static void nalEffectiv(int n, int n2, long l) {
        long l2 = AL.getICD().alEffectiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alEffectiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalEffectiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alEffectf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alEffectf;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, f, l);
    }

    public static void nalEffectfv(int n, int n2, long l) {
        long l2 = AL.getICD().alEffectfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alEffectfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalEffectfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetEffecti(int n, int n2, long l) {
        long l2 = AL.getICD().alGetEffecti;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffecti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetEffecti(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetEffecti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGetEffecti(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetEffectiv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetEffectiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetEffectiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetEffectf(int n, int n2, long l) {
        long l2 = AL.getICD().alGetEffectf;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetEffectf(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetEffectf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTEfx.nalGetEffectf(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetEffectfv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetEffectfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetEffectfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGenFilters(int n, long l) {
        long l2 = AL.getICD().alGenFilters;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGenFilters(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalGenFilters(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGenFilters() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGenFilters(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nalDeleteFilters(int n, long l) {
        long l2 = AL.getICD().alDeleteFilters;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteFilters(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalDeleteFilters(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alDeleteFilters(@NativeType(value="ALuint *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTEfx.nalDeleteFilters(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALboolean")
    public static boolean alIsFilter(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alIsFilter;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alFilteri(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3) {
        long l = AL.getICD().alFilteri;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, l);
    }

    public static void nalFilteriv(int n, int n2, long l) {
        long l2 = AL.getICD().alFilteriv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alFilteriv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalFilteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alFilterf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alFilterf;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, f, l);
    }

    public static void nalFilterfv(int n, int n2, long l) {
        long l2 = AL.getICD().alFilterfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alFilterfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalFilterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetFilteri(int n, int n2, long l) {
        long l2 = AL.getICD().alGetFilteri;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilteri(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetFilteri(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetFilteri(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGetFilteri(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetFilteriv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetFilteriv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilteriv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetFilteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetFilterf(int n, int n2, long l) {
        long l2 = AL.getICD().alGetFilterf;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilterf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetFilterf(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetFilterf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTEfx.nalGetFilterf(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetFilterfv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetFilterfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilterfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetFilterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGenAuxiliaryEffectSlots(int n, long l) {
        long l2 = AL.getICD().alGenAuxiliaryEffectSlots;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGenAuxiliaryEffectSlots(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalGenAuxiliaryEffectSlots(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGenAuxiliaryEffectSlots() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGenAuxiliaryEffectSlots(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nalDeleteAuxiliaryEffectSlots(int n, long l) {
        long l2 = AL.getICD().alDeleteAuxiliaryEffectSlots;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteAuxiliaryEffectSlots(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        EXTEfx.nalDeleteAuxiliaryEffectSlots(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alDeleteAuxiliaryEffectSlots(@NativeType(value="ALuint *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTEfx.nalDeleteAuxiliaryEffectSlots(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALboolean")
    public static boolean alIsAuxiliaryEffectSlot(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alIsAuxiliaryEffectSlot;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSloti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3) {
        long l = AL.getICD().alAuxiliaryEffectSloti;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, l);
    }

    public static void nalAuxiliaryEffectSlotiv(int n, int n2, long l) {
        long l2 = AL.getICD().alAuxiliaryEffectSlotiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSlotiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalAuxiliaryEffectSlotiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSlotf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alAuxiliaryEffectSlotf;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, f, l);
    }

    public static void nalAuxiliaryEffectSlotfv(int n, int n2, long l) {
        long l2 = AL.getICD().alAuxiliaryEffectSlotfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSlotfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalAuxiliaryEffectSlotfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetAuxiliaryEffectSloti(int n, int n2, long l) {
        long l2 = AL.getICD().alGetAuxiliaryEffectSloti;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSloti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetAuxiliaryEffectSloti(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetAuxiliaryEffectSloti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTEfx.nalGetAuxiliaryEffectSloti(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetAuxiliaryEffectSlotiv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetAuxiliaryEffectSlotiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTEfx.nalGetAuxiliaryEffectSlotiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetAuxiliaryEffectSlotf(int n, int n2, long l) {
        long l2 = AL.getICD().alGetAuxiliaryEffectSlotf;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetAuxiliaryEffectSlotf(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetAuxiliaryEffectSlotf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTEfx.nalGetAuxiliaryEffectSlotf(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetAuxiliaryEffectSlotfv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetAuxiliaryEffectSlotfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTEfx.nalGetAuxiliaryEffectSlotfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alGenEffects(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alGenEffects;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteEffects(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alDeleteEffects;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alEffectiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alEffectiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alEffectfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alEffectfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffecti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetEffecti;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetEffectiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetEffectf;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetEffectfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetEffectfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGenFilters(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alGenFilters;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteFilters(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alDeleteFilters;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alFilteriv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alFilteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alFilterfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alFilterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilteri(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetFilteri;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilteriv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetFilteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilterf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetFilterf;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetFilterfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetFilterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGenAuxiliaryEffectSlots(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alGenAuxiliaryEffectSlots;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteAuxiliaryEffectSlots(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alDeleteAuxiliaryEffectSlots;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSlotiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alAuxiliaryEffectSlotiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alAuxiliaryEffectSlotfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alAuxiliaryEffectSlotfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSloti(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetAuxiliaryEffectSloti;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetAuxiliaryEffectSlotiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetAuxiliaryEffectSlotf;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetAuxiliaryEffectSlotfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetAuxiliaryEffectSlotfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }
}

