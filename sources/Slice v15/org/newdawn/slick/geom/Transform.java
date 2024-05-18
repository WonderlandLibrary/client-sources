package org.newdawn.slick.geom;

import org.newdawn.slick.util.FastTrig;















public class Transform
{
  private float[] matrixPosition;
  
  public Transform()
  {
    matrixPosition = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
  }
  




  public Transform(Transform other)
  {
    matrixPosition = new float[9];
    for (int i = 0; i < 9; i++) {
      matrixPosition[i] = matrixPosition[i];
    }
  }
  





  public Transform(Transform t1, Transform t2)
  {
    this(t1);
    concatenate(t2);
  }
  





  public Transform(float[] matrixPosition)
  {
    if (matrixPosition.length != 6) {
      throw new RuntimeException("The parameter must be a float array of length 6.");
    }
    this.matrixPosition = new float[] { matrixPosition[0], matrixPosition[1], matrixPosition[2], 
      matrixPosition[3], matrixPosition[4], matrixPosition[5], 
      0.0F, 0.0F, 1.0F };
  }
  









  public Transform(float point00, float point01, float point02, float point10, float point11, float point12)
  {
    matrixPosition = new float[] { point00, point01, point02, point10, point11, point12, 0.0F, 0.0F, 1.0F };
  }
  












  public void transform(float[] source, int sourceOffset, float[] destination, int destOffset, int numberOfPoints)
  {
    float[] result = source == destination ? new float[numberOfPoints * 2] : destination;
    
    for (int i = 0; i < numberOfPoints * 2; i += 2) {
      for (int j = 0; j < 6; j += 3) {
        result[(i + j / 3)] = (source[(i + sourceOffset)] * matrixPosition[j] + source[(i + sourceOffset + 1)] * matrixPosition[(j + 1)] + 1.0F * matrixPosition[(j + 2)]);
      }
    }
    
    if (source == destination)
    {
      for (int i = 0; i < numberOfPoints * 2; i += 2) {
        destination[(i + destOffset)] = result[i];
        destination[(i + destOffset + 1)] = result[(i + 1)];
      }
    }
  }
  





  public Transform concatenate(Transform tx)
  {
    float[] mp = new float[9];
    float n00 = matrixPosition[0] * matrixPosition[0] + matrixPosition[1] * matrixPosition[3];
    float n01 = matrixPosition[0] * matrixPosition[1] + matrixPosition[1] * matrixPosition[4];
    float n02 = matrixPosition[0] * matrixPosition[2] + matrixPosition[1] * matrixPosition[5] + matrixPosition[2];
    float n10 = matrixPosition[3] * matrixPosition[0] + matrixPosition[4] * matrixPosition[3];
    float n11 = matrixPosition[3] * matrixPosition[1] + matrixPosition[4] * matrixPosition[4];
    float n12 = matrixPosition[3] * matrixPosition[2] + matrixPosition[4] * matrixPosition[5] + matrixPosition[5];
    mp[0] = n00;
    mp[1] = n01;
    mp[2] = n02;
    mp[3] = n10;
    mp[4] = n11;
    mp[5] = n12;
    







    matrixPosition = mp;
    return this;
  }
  





  public String toString()
  {
    String result = "Transform[[" + matrixPosition[0] + "," + matrixPosition[1] + "," + matrixPosition[2] + 
      "][" + matrixPosition[3] + "," + matrixPosition[4] + "," + matrixPosition[5] + 
      "][" + matrixPosition[6] + "," + matrixPosition[7] + "," + matrixPosition[8] + "]]";
    
    return result.toString();
  }
  




  public float[] getMatrixPosition()
  {
    return matrixPosition;
  }
  





  public static Transform createRotateTransform(float angle)
  {
    return new Transform((float)FastTrig.cos(angle), -(float)FastTrig.sin(angle), 0.0F, (float)FastTrig.sin(angle), (float)FastTrig.cos(angle), 0.0F);
  }
  







  public static Transform createRotateTransform(float angle, float x, float y)
  {
    Transform temp = createRotateTransform(angle);
    float sinAngle = matrixPosition[3];
    float oneMinusCosAngle = 1.0F - matrixPosition[4];
    matrixPosition[2] = (x * oneMinusCosAngle + y * sinAngle);
    matrixPosition[5] = (y * oneMinusCosAngle - x * sinAngle);
    
    return temp;
  }
  






  public static Transform createTranslateTransform(float xOffset, float yOffset)
  {
    return new Transform(1.0F, 0.0F, xOffset, 0.0F, 1.0F, yOffset);
  }
  






  public static Transform createScaleTransform(float xScale, float yScale)
  {
    return new Transform(xScale, 0.0F, 0.0F, 0.0F, yScale, 0.0F);
  }
  





  public Vector2f transform(Vector2f pt)
  {
    float[] in = { x, y };
    float[] out = new float[2];
    
    transform(in, 0, out, 0, 1);
    
    return new Vector2f(out[0], out[1]);
  }
}
