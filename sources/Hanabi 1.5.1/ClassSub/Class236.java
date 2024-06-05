package ClassSub;

public final class Class236
{
    private static Class311 GL;
    private static Class127 LSR;
    
    
    public static final void draw(final Class186 class186) {
        final Class282 lastBind = Class237.getLastBind();
        Class237.bindNone();
        final float[] array = class186.getPoints();
        Class236.LSR.start();
        for (int i = 0; i < array.length; i += 2) {
            Class236.LSR.vertex(array[i], array[i + 1]);
        }
        if (class186.closed()) {
            Class236.LSR.vertex(array[0], array[1]);
        }
        Class236.LSR.end();
        if (lastBind == null) {
            Class237.bindNone();
        }
        else {
            lastBind.bind();
        }
    }
    
    public static final void draw(final Class186 class186, final Class173 class187) {
        final float[] array = class186.getPoints();
        final Class282 lastBind = Class237.getLastBind();
        Class237.bindNone();
        final float[] array2 = class186.getCenter();
        Class236.GL.glBegin(3);
        for (int i = 0; i < array.length; i += 2) {
            class187.colorAt(class186, array[i], array[i + 1]).bind();
            final Class224 offset = class187.getOffsetAt(class186, array[i], array[i + 1]);
            Class236.GL.glVertex2f(array[i] + offset.x, array[i + 1] + offset.y);
        }
        if (class186.closed()) {
            class187.colorAt(class186, array[0], array[1]).bind();
            final Class224 offset2 = class187.getOffsetAt(class186, array[0], array[1]);
            Class236.GL.glVertex2f(array[0] + offset2.x, array[1] + offset2.y);
        }
        Class236.GL.glEnd();
        if (lastBind == null) {
            Class237.bindNone();
        }
        else {
            lastBind.bind();
        }
    }
    
    public static boolean validFill(final Class186 class186) {
        return class186.getTriangles() != null && class186.getTriangles().getTriangleCount() != 0;
    }
    
    public static final void fill(final Class186 class186) {
        final class Class174 implements Class2
        {
            
            
            @Override
            public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
                return null;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokestatic    ClassSub/Class236.validFill:(LClassSub/Class186;)Z
        //     8: ifne            16
        //    11: return         
        //    12: nop            
        //    13: nop            
        //    14: nop            
        //    15: athrow         
        //    16: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //    19: astore_1       
        //    20: invokestatic    ClassSub/Class237.bindNone:()V
        //    23: aload_0        
        //    24: new             LClassSub/Class174;
        //    27: dup            
        //    28: invokespecial   ClassSub/Class174.<init>:()V
        //    31: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    34: aload_1        
        //    35: ifnonnull       50
        //    38: invokestatic    ClassSub/Class237.bindNone:()V
        //    41: goto            56
        //    44: nop            
        //    45: nop            
        //    46: nop            
        //    47: nop            
        //    48: nop            
        //    49: athrow         
        //    50: aload_1        
        //    51: invokeinterface ClassSub/Class282.bind:()V
        //    56: return         
        //    57: nop            
        //    58: nop            
        //    59: nop            
        //    60: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static final void fill(final Class186 class186, final Class2 class187) {
        final Class92 triangles = class186.getTriangles();
        Class236.GL.glBegin(4);
        for (int i = 0; i < triangles.getTriangleCount(); ++i) {
            for (int j = 0; j < 3; ++j) {
                final float[] array = triangles.getTrianglePoint(i, j);
                final float[] array2 = class187.preRenderPoint(class186, array[0], array[1]);
                if (array2 == null) {
                    Class236.GL.glVertex2f(array[0], array[1]);
                }
                else {
                    Class236.GL.glVertex2f(array2[0], array2[1]);
                }
            }
        }
        Class236.GL.glEnd();
    }
    
    public static final void texture(final Class186 class186, final Class220 class187) {
        texture(class186, class187, 0.01f, 0.01f);
    }
    
    public static final void textureFit(final Class186 class186, final Class220 class187) {
        textureFit(class186, class187, 1.0f, 1.0f);
    }
    
    public static final void texture(final Class186 class186, final Class220 class187, final float n, final float n2) {
        final class Class3 implements Class2
        {
            final float val$scaleX;
            final float val$scaleY;
            final Class220 val$image;
            
            
            Class3(final float val$scaleX, final float val$scaleY, final Class220 val$image) {
                this.val$scaleX = val$scaleX;
                this.val$scaleY = val$scaleY;
                this.val$image = val$image;
            }
            
            @Override
            public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
                Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
                return null;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokestatic    ClassSub/Class236.validFill:(LClassSub/Class186;)Z
        //     8: ifne            17
        //    11: return         
        //    12: nop            
        //    13: nop            
        //    14: nop            
        //    15: nop            
        //    16: athrow         
        //    17: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //    20: astore          4
        //    22: aload_1        
        //    23: invokevirtual   ClassSub/Class220.getTexture:()LClassSub/Class282;
        //    26: invokeinterface ClassSub/Class282.bind:()V
        //    31: aload_0        
        //    32: new             LClassSub/Class3;
        //    35: dup            
        //    36: fload_2        
        //    37: fload_3        
        //    38: aload_1        
        //    39: invokespecial   ClassSub/Class3.<init>:(FFLClassSub/Class220;)V
        //    42: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    45: aload_0        
        //    46: invokevirtual   ClassSub/Class186.getPoints:()[F
        //    49: checkcast       [F
        //    52: astore          5
        //    54: aload           4
        //    56: ifnonnull       72
        //    59: invokestatic    ClassSub/Class237.bindNone:()V
        //    62: goto            79
        //    65: nop            
        //    66: nop            
        //    67: nop            
        //    68: nop            
        //    69: nop            
        //    70: nop            
        //    71: athrow         
        //    72: aload           4
        //    74: invokeinterface ClassSub/Class282.bind:()V
        //    79: return         
        //    80: nop            
        //    81: nop            
        //    82: nop            
        //    83: nop            
        //    84: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static final void textureFit(final Class186 class186, final Class220 class187, final float n, final float n2) {
        final class Class229 implements Class2
        {
            final float val$scaleX;
            final float val$scaleY;
            final Class220 val$image;
            
            
            Class229(final float val$scaleX, final float val$scaleY, final Class220 val$image) {
                this.val$scaleX = val$scaleX;
                this.val$scaleY = val$scaleY;
                this.val$image = val$image;
            }
            
            @Override
            public float[] preRenderPoint(final Class186 class186, float n, float n2) {
                n -= class186.getMinX();
                n2 -= class186.getMinY();
                n /= class186.getMaxX() - class186.getMinX();
                n2 /= class186.getMaxY() - class186.getMinY();
                Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
                return null;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokestatic    ClassSub/Class236.validFill:(LClassSub/Class186;)Z
        //     8: ifne            17
        //    11: return         
        //    12: nop            
        //    13: nop            
        //    14: nop            
        //    15: nop            
        //    16: athrow         
        //    17: aload_0        
        //    18: invokevirtual   ClassSub/Class186.getPoints:()[F
        //    21: checkcast       [F
        //    24: astore          4
        //    26: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //    29: astore          5
        //    31: aload_1        
        //    32: invokevirtual   ClassSub/Class220.getTexture:()LClassSub/Class282;
        //    35: invokeinterface ClassSub/Class282.bind:()V
        //    40: aload_0        
        //    41: invokevirtual   ClassSub/Class186.getX:()F
        //    44: fstore          6
        //    46: aload_0        
        //    47: invokevirtual   ClassSub/Class186.getY:()F
        //    50: fstore          7
        //    52: aload_0        
        //    53: invokevirtual   ClassSub/Class186.getMaxX:()F
        //    56: fload           6
        //    58: fsub           
        //    59: fstore          8
        //    61: aload_0        
        //    62: invokevirtual   ClassSub/Class186.getMaxY:()F
        //    65: fload           7
        //    67: fsub           
        //    68: fstore          9
        //    70: aload_0        
        //    71: new             LClassSub/Class229;
        //    74: dup            
        //    75: fload_2        
        //    76: fload_3        
        //    77: aload_1        
        //    78: invokespecial   ClassSub/Class229.<init>:(FFLClassSub/Class220;)V
        //    81: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    84: aload           5
        //    86: ifnonnull       102
        //    89: invokestatic    ClassSub/Class237.bindNone:()V
        //    92: goto            109
        //    95: nop            
        //    96: nop            
        //    97: nop            
        //    98: nop            
        //    99: nop            
        //   100: nop            
        //   101: athrow         
        //   102: aload           5
        //   104: invokeinterface ClassSub/Class282.bind:()V
        //   109: return         
        //   110: nop            
        //   111: nop            
        //   112: nop            
        //   113: nop            
        //   114: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static final void fill(final Class186 class186, final Class173 class187) {
        final class Class28 implements Class2
        {
            final Class173 val$fill;
            
            
            Class28(final Class173 val$fill) {
                this.val$fill = val$fill;
            }
            
            @Override
            public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
                this.val$fill.colorAt(class186, n, n2).bind();
                final Class224 offset = this.val$fill.getOffsetAt(class186, n, n2);
                return new float[] { offset.x + n, offset.y + n2 };
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokestatic    ClassSub/Class236.validFill:(LClassSub/Class186;)Z
        //     8: ifne            17
        //    11: return         
        //    12: nop            
        //    13: nop            
        //    14: nop            
        //    15: nop            
        //    16: athrow         
        //    17: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //    20: astore_2       
        //    21: invokestatic    ClassSub/Class237.bindNone:()V
        //    24: aload_0        
        //    25: invokevirtual   ClassSub/Class186.getCenter:()[F
        //    28: checkcast       [F
        //    31: astore_3       
        //    32: aload_0        
        //    33: new             LClassSub/Class28;
        //    36: dup            
        //    37: aload_1        
        //    38: invokespecial   ClassSub/Class28.<init>:(LClassSub/Class173;)V
        //    41: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    44: aload_2        
        //    45: ifnonnull       61
        //    48: invokestatic    ClassSub/Class237.bindNone:()V
        //    51: goto            67
        //    54: nop            
        //    55: nop            
        //    56: nop            
        //    57: nop            
        //    58: nop            
        //    59: nop            
        //    60: athrow         
        //    61: aload_2        
        //    62: invokeinterface ClassSub/Class282.bind:()V
        //    67: return         
        //    68: nop            
        //    69: nop            
        //    70: nop            
        //    71: nop            
        //    72: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static final void texture(final Class186 class186, final Class220 class187, final float n, final float n2, final Class173 class188) {
        final class Class213 implements Class2
        {
            final Class173 val$fill;
            final float[] val$center;
            final float val$scaleX;
            final float val$scaleY;
            final Class220 val$image;
            
            
            Class213(final Class173 val$fill, final float[] val$center, final float val$scaleX, final float val$scaleY, final Class220 val$image) {
                this.val$fill = val$fill;
                this.val$center = val$center;
                this.val$scaleX = val$scaleX;
                this.val$scaleY = val$scaleY;
                this.val$image = val$image;
            }
            
            @Override
            public float[] preRenderPoint(final Class186 class186, float n, float n2) {
                this.val$fill.colorAt(class186, n - this.val$center[0], n2 - this.val$center[1]).bind();
                final Class224 offset = this.val$fill.getOffsetAt(class186, n, n2);
                n += offset.x;
                n2 += offset.y;
                Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
                return new float[] { offset.x + n, offset.y + n2 };
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: aload_0        
        //     5: invokestatic    ClassSub/Class236.validFill:(LClassSub/Class186;)Z
        //     8: ifne            17
        //    11: return         
        //    12: nop            
        //    13: nop            
        //    14: nop            
        //    15: nop            
        //    16: athrow         
        //    17: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //    20: astore          5
        //    22: aload_1        
        //    23: invokevirtual   ClassSub/Class220.getTexture:()LClassSub/Class282;
        //    26: invokeinterface ClassSub/Class282.bind:()V
        //    31: aload_0        
        //    32: invokevirtual   ClassSub/Class186.getCenter:()[F
        //    35: checkcast       [F
        //    38: astore          6
        //    40: aload_0        
        //    41: new             LClassSub/Class213;
        //    44: dup            
        //    45: aload           4
        //    47: aload           6
        //    49: fload_2        
        //    50: fload_3        
        //    51: aload_1        
        //    52: invokespecial   ClassSub/Class213.<init>:(LClassSub/Class173;[FFFLClassSub/Class220;)V
        //    55: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    58: aload           5
        //    60: ifnonnull       76
        //    63: invokestatic    ClassSub/Class237.bindNone:()V
        //    66: goto            83
        //    69: nop            
        //    70: nop            
        //    71: nop            
        //    72: nop            
        //    73: nop            
        //    74: nop            
        //    75: athrow         
        //    76: aload           5
        //    78: invokeinterface ClassSub/Class282.bind:()V
        //    83: return         
        //    84: nop            
        //    85: nop            
        //    86: nop            
        //    87: nop            
        //    88: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static final void texture(final Class186 class186, final Class220 class187, final Class316 class188) {
        final class Class299 implements Class2
        {
            final Class316 val$gen;
            
            
            Class299(final Class316 val$gen) {
                this.val$gen = val$gen;
            }
            
            @Override
            public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
                final Class224 coord = this.val$gen.getCoordFor(n, n2);
                Class236.access$000().glTexCoord2f(coord.x, coord.y);
                return new float[] { n, n2 };
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: invokestatic    ClassSub/Class237.getLastBind:()LClassSub/Class282;
        //     7: astore_3       
        //     8: aload_1        
        //     9: invokevirtual   ClassSub/Class220.getTexture:()LClassSub/Class282;
        //    12: invokeinterface ClassSub/Class282.bind:()V
        //    17: aload_0        
        //    18: invokevirtual   ClassSub/Class186.getCenter:()[F
        //    21: checkcast       [F
        //    24: astore          4
        //    26: aload_0        
        //    27: new             LClassSub/Class299;
        //    30: dup            
        //    31: aload_2        
        //    32: invokespecial   ClassSub/Class299.<init>:(LClassSub/Class316;)V
        //    35: invokestatic    ClassSub/Class236.fill:(LClassSub/Class186;LClassSub/Class2;)V
        //    38: aload_3        
        //    39: ifnonnull       55
        //    42: invokestatic    ClassSub/Class237.bindNone:()V
        //    45: goto            61
        //    48: nop            
        //    49: nop            
        //    50: nop            
        //    51: nop            
        //    52: nop            
        //    53: nop            
        //    54: athrow         
        //    55: aload_3        
        //    56: invokeinterface ClassSub/Class282.bind:()V
        //    61: return         
        //    62: nop            
        //    63: nop            
        //    64: nop            
        //    65: nop            
        //    66: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static Class311 access$000() {
        return Class236.GL;
    }
    
    static {
        Class236.GL = Class197.get();
        Class236.LSR = Class197.getLineStripRenderer();
    }
    
    private interface Class2
    {
        public static final boolean 你还在为你仅存的�?点点尊严奋死反抗自不量力不服气是吧你自己蜗牛弟弟啊你告诉我你是不是垃圾啊你永远都在我手心里知道吗我完全可以无视你那垃圾语�?NMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSLNMSL​�?��?��?��??;
        
        float[] preRenderPoint(final Class186 p0, final float p1, final float p2);
    }
}
