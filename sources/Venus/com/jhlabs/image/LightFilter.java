/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.WholeImageFilter;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.ImageFunction2D;
import com.jhlabs.vecmath.Color4f;
import com.jhlabs.vecmath.Tuple3f;
import com.jhlabs.vecmath.Vector3f;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.util.Vector;

public class LightFilter
extends WholeImageFilter {
    public static final int COLORS_FROM_IMAGE = 0;
    public static final int COLORS_CONSTANT = 1;
    public static final int BUMPS_FROM_IMAGE = 0;
    public static final int BUMPS_FROM_IMAGE_ALPHA = 1;
    public static final int BUMPS_FROM_MAP = 2;
    public static final int BUMPS_FROM_BEVEL = 3;
    private float bumpHeight;
    private float bumpSoftness;
    private int bumpShape;
    private float viewDistance = 10000.0f;
    Material material;
    private Vector lights = new Vector();
    private int colorSource = 0;
    private int bumpSource = 0;
    private Function2D bumpFunction;
    private Image environmentMap;
    private int[] envPixels;
    private int envWidth = 1;
    private int envHeight = 1;
    private Vector3f l;
    private Vector3f v;
    private Vector3f n;
    private Color4f shadedColor;
    private Color4f diffuse_color;
    private Color4f specular_color;
    private Vector3f tmpv;
    private Vector3f tmpv2;
    protected static final float r255 = 0.003921569f;
    public static final int AMBIENT = 0;
    public static final int DISTANT = 1;
    public static final int POINT = 2;
    public static final int SPOT = 3;

    public LightFilter() {
        this.addLight(new DistantLight(this));
        this.bumpHeight = 1.0f;
        this.bumpSoftness = 5.0f;
        this.bumpShape = 0;
        this.material = new Material();
        this.l = new Vector3f();
        this.v = new Vector3f();
        this.n = new Vector3f();
        this.shadedColor = new Color4f();
        this.diffuse_color = new Color4f();
        this.specular_color = new Color4f();
        this.tmpv = new Vector3f();
        this.tmpv2 = new Vector3f();
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setBumpFunction(Function2D function2D) {
        this.bumpFunction = function2D;
    }

    public Function2D getBumpFunction() {
        return this.bumpFunction;
    }

    public void setBumpHeight(float f) {
        this.bumpHeight = f;
    }

    public float getBumpHeight() {
        return this.bumpHeight;
    }

    public void setBumpSoftness(float f) {
        this.bumpSoftness = f;
    }

    public float getBumpSoftness() {
        return this.bumpSoftness;
    }

    public void setBumpShape(int n) {
        this.bumpShape = n;
    }

    public int getBumpShape() {
        return this.bumpShape;
    }

    public void setViewDistance(float f) {
        this.viewDistance = f;
    }

    public float getViewDistance() {
        return this.viewDistance;
    }

    public void setEnvironmentMap(BufferedImage bufferedImage) {
        this.environmentMap = bufferedImage;
        if (bufferedImage != null) {
            this.envWidth = bufferedImage.getWidth();
            this.envHeight = bufferedImage.getHeight();
            this.envPixels = this.getRGB(bufferedImage, 0, 0, this.envWidth, this.envHeight, null);
        } else {
            this.envHeight = 1;
            this.envWidth = 1;
            this.envPixels = null;
        }
    }

    public Image getEnvironmentMap() {
        return this.environmentMap;
    }

    public void setColorSource(int n) {
        this.colorSource = n;
    }

    public int getColorSource() {
        return this.colorSource;
    }

    public void setBumpSource(int n) {
        this.bumpSource = n;
    }

    public int getBumpSource() {
        return this.bumpSource;
    }

    public void setDiffuseColor(int n) {
        this.material.diffuseColor = n;
    }

    public int getDiffuseColor() {
        return this.material.diffuseColor;
    }

    public void addLight(Light light) {
        this.lights.addElement(light);
    }

    public void removeLight(Light light) {
        this.lights.removeElement(light);
    }

    public Vector getLights() {
        return this.lights;
    }

    protected void setFromRGB(Color4f color4f, int n) {
        color4f.set((float)(n >> 16 & 0xFF) * 0.003921569f, (float)(n >> 8 & 0xFF) * 0.003921569f, (float)(n & 0xFF) * 0.003921569f, (float)(n >> 24 & 0xFF) * 0.003921569f);
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        Object[] objectArray;
        Object object;
        Object object2;
        Object object3;
        int n4 = 0;
        int[] nArray2 = new int[n * n2];
        float f = Math.abs(6.0f * this.bumpHeight);
        boolean bl = this.bumpHeight < 0.0f;
        Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f vector3f2 = new Vector3f((float)n / 2.0f, (float)n2 / 2.0f, this.viewDistance);
        Vector3f vector3f3 = new Vector3f();
        Color4f color4f = new Color4f();
        Color4f color4f2 = new Color4f(new Color(this.material.diffuseColor));
        Color4f color4f3 = new Color4f(new Color(this.material.specularColor));
        Function2D function2D = this.bumpFunction;
        if (this.bumpSource == 0 || this.bumpSource == 1 || this.bumpSource == 2 || function2D == null) {
            if (this.bumpSoftness != 0.0f) {
                int n5 = n;
                int n6 = n2;
                object3 = nArray;
                if (this.bumpSource == 2 && this.bumpFunction instanceof ImageFunction2D) {
                    object2 = (ImageFunction2D)this.bumpFunction;
                    n5 = ((ImageFunction2D)object2).getWidth();
                    n6 = ((ImageFunction2D)object2).getHeight();
                    object3 = ((ImageFunction2D)object2).getPixels();
                }
                object2 = new int[n5 * n6];
                object = new int[n5 * n6];
                objectArray = GaussianFilter.makeKernel(this.bumpSoftness);
                GaussianFilter.convolveAndTranspose((Kernel)objectArray, object3, (int[])object2, n5, n6, true, false, false, GaussianFilter.WRAP_EDGES);
                GaussianFilter.convolveAndTranspose((Kernel)objectArray, (int[])object2, (int[])object, n6, n5, true, false, false, GaussianFilter.WRAP_EDGES);
                Function2D function2D2 = function2D = new ImageFunction2D((int[])object, n5, n6, 1, this.bumpSource == 1);
                if (this.bumpShape != 0) {
                    function2D = new Function2D(){
                        private Function2D original;
                        final Function2D val$bbump;
                        final LightFilter this$0;
                        {
                            this.this$0 = lightFilter;
                            this.val$bbump = function2D;
                            this.original = this.val$bbump;
                        }

                        @Override
                        public float evaluate(float f, float f2) {
                            float f3 = this.original.evaluate(f, f2);
                            switch (this.this$0.bumpShape) {
                                case 1: {
                                    f3 *= ImageMath.smoothStep(0.45f, 0.55f, f3);
                                    break;
                                }
                                case 2: {
                                    f3 = f3 < 0.5f ? 0.5f : f3;
                                    break;
                                }
                                case 3: {
                                    f3 = ImageMath.triangle(f3);
                                    break;
                                }
                                case 4: {
                                    f3 = ImageMath.circleDown(f3);
                                    break;
                                }
                                case 5: {
                                    f3 = ImageMath.gain(f3, 0.75f);
                                }
                            }
                            return f3;
                        }
                    };
                }
            } else if (this.bumpSource != 2) {
                function2D = new ImageFunction2D(nArray, n, n2, 1, this.bumpSource == 1);
            }
        }
        float f2 = this.material.reflectivity;
        float f3 = 1.0f - f2;
        object3 = new Vector3f();
        object2 = new Vector3f();
        object = new Vector3f();
        objectArray = new Light[this.lights.size()];
        this.lights.copyInto(objectArray);
        for (int i = 0; i < objectArray.length; ++i) {
            ((Light)objectArray[i]).prepare(n, n2);
        }
        float[][] fArray = new float[3][n];
        for (n3 = 0; n3 < n; ++n3) {
            fArray[0][n3] = f * function2D.evaluate(n3, 0.0f);
        }
        for (n3 = 0; n3 < n2; ++n3) {
            int n7;
            boolean bl2 = n3 > 0;
            boolean bl3 = n3 < n2 - 1;
            vector3f.y = n3;
            for (n7 = 0; n7 < n; ++n7) {
                fArray[5][n7] = f * function2D.evaluate(n7, n3 + 1);
            }
            for (n7 = 0; n7 < n; ++n7) {
                boolean bl4;
                boolean bl5 = n7 > 0;
                boolean bl6 = bl4 = n7 < n - 1;
                if (this.bumpSource != 3) {
                    float f4;
                    int n8 = 0;
                    vector3f3.z = 0.0f;
                    vector3f3.y = 0.0f;
                    vector3f3.x = 0.0f;
                    float f5 = fArray[0][n7];
                    float f6 = bl5 ? fArray[0][n7 - 1] - f5 : 0.0f;
                    float f7 = bl2 ? fArray[5][n7] - f5 : 0.0f;
                    float f8 = bl4 ? fArray[0][n7 + 1] - f5 : 0.0f;
                    float f9 = f4 = bl3 ? fArray[5][n7] - f5 : 0.0f;
                    if (bl5 && bl3) {
                        object3.x = -1.0f;
                        object3.y = 0.0f;
                        object3.z = f6;
                        ((Vector3f)object2).x = 0.0f;
                        ((Vector3f)object2).y = 1.0f;
                        ((Vector3f)object2).z = f4;
                        ((Vector3f)object).cross((Vector3f)object3, (Vector3f)object2);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n8;
                    }
                    if (bl5 && bl2) {
                        object3.x = -1.0f;
                        object3.y = 0.0f;
                        object3.z = f6;
                        ((Vector3f)object2).x = 0.0f;
                        ((Vector3f)object2).y = -1.0f;
                        ((Vector3f)object2).z = f7;
                        ((Vector3f)object).cross((Vector3f)object3, (Vector3f)object2);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n8;
                    }
                    if (bl2 && bl4) {
                        object3.x = 0.0f;
                        object3.y = -1.0f;
                        object3.z = f7;
                        ((Vector3f)object2).x = 1.0f;
                        ((Vector3f)object2).y = 0.0f;
                        ((Vector3f)object2).z = f8;
                        ((Vector3f)object).cross((Vector3f)object3, (Vector3f)object2);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n8;
                    }
                    if (bl4 && bl3) {
                        object3.x = 1.0f;
                        object3.y = 0.0f;
                        object3.z = f8;
                        ((Vector3f)object2).x = 0.0f;
                        ((Vector3f)object2).y = 1.0f;
                        ((Vector3f)object2).z = f4;
                        ((Vector3f)object).cross((Vector3f)object3, (Vector3f)object2);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n8;
                    }
                    vector3f3.x /= (float)n8;
                    vector3f3.y /= (float)n8;
                    vector3f3.z /= (float)n8;
                }
                if (bl) {
                    vector3f3.x = -vector3f3.x;
                    vector3f3.y = -vector3f3.y;
                }
                vector3f.x = n7;
                if (vector3f3.z >= 0.0f) {
                    if (this.colorSource == 0) {
                        this.setFromRGB(color4f2, nArray[n4]);
                    } else {
                        this.setFromRGB(color4f2, this.material.diffuseColor);
                    }
                    if (f2 != 0.0f && this.environmentMap != null) {
                        this.tmpv2.set(vector3f2);
                        this.tmpv2.sub(vector3f);
                        this.tmpv2.normalize();
                        this.tmpv.set(vector3f3);
                        this.tmpv.normalize();
                        this.tmpv.scale(2.0f * this.tmpv.dot(this.tmpv2));
                        this.tmpv.sub(this.v);
                        this.tmpv.normalize();
                        this.setFromRGB(color4f, this.getEnvironmentMap(this.tmpv, nArray, n, n2));
                        color4f2.x = f2 * color4f.x + f3 * color4f2.x;
                        color4f2.y = f2 * color4f.y + f3 * color4f2.y;
                        color4f2.z = f2 * color4f.z + f3 * color4f2.z;
                    }
                    Color4f color4f4 = this.phongShade(vector3f, vector3f2, vector3f3, color4f2, color4f3, this.material, (Light[])objectArray);
                    int n9 = nArray[n4] & 0xFF000000;
                    int n10 = (int)(color4f4.x * 255.0f) << 16 | (int)(color4f4.y * 255.0f) << 8 | (int)(color4f4.z * 255.0f);
                    nArray2[n4++] = n9 | n10;
                    continue;
                }
                nArray2[n4++] = 0;
            }
            float[] fArray2 = fArray[5];
            fArray[0] = fArray[0];
            fArray[1] = fArray[5];
            fArray[2] = fArray2;
        }
        return nArray2;
    }

    protected Color4f phongShade(Vector3f vector3f, Vector3f vector3f2, Vector3f vector3f3, Color4f color4f, Color4f color4f2, Material material, Light[] lightArray) {
        this.shadedColor.set(color4f);
        this.shadedColor.scale(material.ambientIntensity);
        for (int i = 0; i < lightArray.length; ++i) {
            Light light = lightArray[i];
            this.n.set(vector3f3);
            this.l.set(light.position);
            if (light.type != 1) {
                this.l.sub(vector3f);
            }
            this.l.normalize();
            float f = this.n.dot(this.l);
            if (!((double)f >= 0.0)) continue;
            float f2 = 0.0f;
            this.v.set(vector3f2);
            this.v.sub(vector3f);
            this.v.normalize();
            if (light.type == 3 && (f2 = light.direction.dot(this.l)) < light.cosConeAngle) continue;
            this.n.scale(2.0f * f);
            this.n.sub(this.l);
            float f3 = this.n.dot(this.v);
            float f4 = (double)f3 < 0.0 ? 0.0f : f3 / (material.highlight - material.highlight * f3 + f3);
            if (light.type == 3) {
                float f5 = f2 = light.cosConeAngle / f2;
                f5 *= f5;
                f5 *= f5;
                f5 *= f5;
                f5 = (float)Math.pow(f2, light.focus * 10.0f) * (1.0f - f5);
                f4 *= f5;
                f *= f5;
            }
            this.diffuse_color.set(color4f);
            this.diffuse_color.scale(material.diffuseReflectivity);
            this.diffuse_color.x *= light.realColor.x * f;
            this.diffuse_color.y *= light.realColor.y * f;
            this.diffuse_color.z *= light.realColor.z * f;
            this.specular_color.set(color4f2);
            this.specular_color.scale(material.specularReflectivity);
            this.specular_color.x *= light.realColor.x * f4;
            this.specular_color.y *= light.realColor.y * f4;
            this.specular_color.z *= light.realColor.z * f4;
            this.diffuse_color.add(this.specular_color);
            this.diffuse_color.clamp(0.0f, 1.0f);
            this.shadedColor.add(this.diffuse_color);
        }
        this.shadedColor.clamp(0.0f, 1.0f);
        return this.shadedColor;
    }

    private int getEnvironmentMap(Vector3f vector3f, int[] nArray, int n, int n2) {
        if (this.environmentMap != null) {
            float f;
            float f2 = (float)Math.acos(-vector3f.y);
            float f3 = f2 / (float)Math.PI;
            if (f3 == 0.0f || f3 == 1.0f) {
                f = 0.0f;
            } else {
                float f4 = vector3f.x / (float)Math.sin(f2);
                if (f4 > 1.0f) {
                    f4 = 1.0f;
                } else if (f4 < -1.0f) {
                    f4 = -1.0f;
                }
                f = (float)Math.acos(f4) / (float)Math.PI;
            }
            f = ImageMath.clamp(f * (float)this.envWidth, 0.0f, (float)(this.envWidth - 1));
            f3 = ImageMath.clamp(f3 * (float)this.envHeight, 0.0f, (float)(this.envHeight - 1));
            int n3 = (int)f;
            int n4 = (int)f3;
            float f5 = f - (float)n3;
            float f6 = f3 - (float)n4;
            int n5 = this.envWidth * n4 + n3;
            int n6 = n3 == this.envWidth - 1 ? 0 : 1;
            int n7 = n4 == this.envHeight - 1 ? 0 : this.envWidth;
            return ImageMath.bilinearInterpolate(f5, f6, this.envPixels[n5], this.envPixels[n5 + n6], this.envPixels[n5 + n7], this.envPixels[n5 + n6 + n7]);
        }
        return 1;
    }

    public String toString() {
        return "Stylize/Light Effects...";
    }

    public class DistantLight
    extends Light {
        final LightFilter this$0;

        public DistantLight(LightFilter lightFilter) {
            this.this$0 = lightFilter;
            this.type = 1;
        }

        @Override
        public String toString() {
            return "Distant Light";
        }
    }

    public static class Light
    implements Cloneable {
        int type = 0;
        Vector3f position;
        Vector3f direction;
        Color4f realColor = new Color4f();
        int color = -1;
        float intensity;
        float azimuth;
        float elevation;
        float focus = 0.5f;
        float centreX = 0.5f;
        float centreY = 0.5f;
        float coneAngle = 0.5235988f;
        float cosConeAngle;
        float distance = 100.0f;

        public Light() {
            this(4.712389f, 0.5235988f, 1.0f);
        }

        public Light(float f, float f2, float f3) {
            this.azimuth = f;
            this.elevation = f2;
            this.intensity = f3;
        }

        public void setAzimuth(float f) {
            this.azimuth = f;
        }

        public float getAzimuth() {
            return this.azimuth;
        }

        public void setElevation(float f) {
            this.elevation = f;
        }

        public float getElevation() {
            return this.elevation;
        }

        public void setDistance(float f) {
            this.distance = f;
        }

        public float getDistance() {
            return this.distance;
        }

        public void setIntensity(float f) {
            this.intensity = f;
        }

        public float getIntensity() {
            return this.intensity;
        }

        public void setConeAngle(float f) {
            this.coneAngle = f;
        }

        public float getConeAngle() {
            return this.coneAngle;
        }

        public void setFocus(float f) {
            this.focus = f;
        }

        public float getFocus() {
            return this.focus;
        }

        public void setColor(int n) {
            this.color = n;
        }

        public int getColor() {
            return this.color;
        }

        public void setCentreX(float f) {
            this.centreX = f;
        }

        public float getCentreX() {
            return this.centreX;
        }

        public void setCentreY(float f) {
            this.centreY = f;
        }

        public float getCentreY() {
            return this.centreY;
        }

        public void prepare(int n, int n2) {
            float f = (float)(Math.cos(this.azimuth) * Math.cos(this.elevation));
            float f2 = (float)(Math.sin(this.azimuth) * Math.cos(this.elevation));
            float f3 = (float)Math.sin(this.elevation);
            this.direction = new Vector3f(f, f2, f3);
            this.direction.normalize();
            if (this.type != 1) {
                f *= this.distance;
                f2 *= this.distance;
                f3 *= this.distance;
                f += (float)n * this.centreX;
                f2 += (float)n2 * this.centreY;
            }
            this.position = new Vector3f(f, f2, f3);
            this.realColor.set(new Color(this.color));
            this.realColor.scale(this.intensity);
            this.cosConeAngle = (float)Math.cos(this.coneAngle);
        }

        public Object clone() {
            try {
                Light light = (Light)super.clone();
                return light;
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
        }

        public String toString() {
            return "Light";
        }
    }

    public static class Material {
        int diffuseColor = -7829368;
        int specularColor = -1;
        float ambientIntensity = 0.5f;
        float diffuseReflectivity = 1.0f;
        float specularReflectivity = 1.0f;
        float highlight = 3.0f;
        float reflectivity = 0.0f;
        float opacity = 1.0f;

        public void setDiffuseColor(int n) {
            this.diffuseColor = n;
        }

        public int getDiffuseColor() {
            return this.diffuseColor;
        }

        public void setOpacity(float f) {
            this.opacity = f;
        }

        public float getOpacity() {
            return this.opacity;
        }
    }

    public class SpotLight
    extends Light {
        final LightFilter this$0;

        public SpotLight(LightFilter lightFilter) {
            this.this$0 = lightFilter;
            this.type = 3;
        }

        @Override
        public String toString() {
            return "Spotlight";
        }
    }

    public class PointLight
    extends Light {
        final LightFilter this$0;

        public PointLight(LightFilter lightFilter) {
            this.this$0 = lightFilter;
            this.type = 2;
        }

        @Override
        public String toString() {
            return "Point Light";
        }
    }

    public class AmbientLight
    extends Light {
        final LightFilter this$0;

        public AmbientLight(LightFilter lightFilter) {
            this.this$0 = lightFilter;
        }

        @Override
        public String toString() {
            return "Ambient Light";
        }
    }
}

