package ClassSub;

public class Class234 implements Class92
{
    private float[][] triangles;
    
    
    public Class234(final Class92 class92) {
        this.triangles = new float[class92.getTriangleCount() * 6 * 3][2];
        int n = 0;
        for (int i = 0; i < class92.getTriangleCount(); ++i) {
            float n2 = 0.0f;
            float n3 = 0.0f;
            for (int j = 0; j < 3; ++j) {
                final float[] array = class92.getTrianglePoint(i, j);
                n2 += array[0];
                n3 += array[1];
            }
            final float n4 = n2 / 3.0f;
            final float n5 = n3 / 3.0f;
            for (int k = 0; k < 3; ++k) {
                int n6 = k + 1;
                if (n6 > 2) {
                    n6 = 0;
                }
                final float[] array2 = class92.getTrianglePoint(i, k);
                final float[] array3 = class92.getTrianglePoint(i, n6);
                array2[0] = (array2[0] + array3[0]) / 2.0f;
                array2[1] = (array2[1] + array3[1]) / 2.0f;
                this.triangles[n * 3 + 0][0] = n4;
                this.triangles[n * 3 + 0][1] = n5;
                this.triangles[n * 3 + 1][0] = array2[0];
                this.triangles[n * 3 + 1][1] = array2[1];
                this.triangles[n * 3 + 2][0] = array3[0];
                this.triangles[n * 3 + 2][1] = array3[1];
                ++n;
            }
            for (int l = 0; l < 3; ++l) {
                int n7 = l + 1;
                if (n7 > 2) {
                    n7 = 0;
                }
                final float[] array4 = class92.getTrianglePoint(i, l);
                final float[] array5 = class92.getTrianglePoint(i, n7);
                array5[0] = (array4[0] + array5[0]) / 2.0f;
                array5[1] = (array4[1] + array5[1]) / 2.0f;
                this.triangles[n * 3 + 0][0] = n4;
                this.triangles[n * 3 + 0][1] = n5;
                this.triangles[n * 3 + 1][0] = array4[0];
                this.triangles[n * 3 + 1][1] = array4[1];
                this.triangles[n * 3 + 2][0] = array5[0];
                this.triangles[n * 3 + 2][1] = array5[1];
                ++n;
            }
        }
    }
    
    @Override
    public void addPolyPoint(final float n, final float n2) {
    }
    
    @Override
    public int getTriangleCount() {
        return this.triangles.length / 3;
    }
    
    @Override
    public float[] getTrianglePoint(final int n, final int n2) {
        final float[] array = this.triangles[n * 3 + n2];
        return new float[] { array[0], array[1] };
    }
    
    @Override
    public void startHole() {
    }
    
    @Override
    public boolean triangulate() {
        return true;
    }
}
