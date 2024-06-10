using System;
using System.Drawing;
using System.Drawing.Drawing2D;

// Token: 0x020000E7 RID: 231
public class GClass11
{
	// Token: 0x0600099B RID: 2459 RVA: 0x0002CF6C File Offset: 0x0002B16C
	private GClass11()
	{
	}

	// Token: 0x0600099C RID: 2460 RVA: 0x0002CF74 File Offset: 0x0002B174
	public static LinearGradientBrush smethod_0(Point point_0, Point point_1)
	{
		return new LinearGradientBrush(point_0, point_1, Color.Red, Color.Red)
		{
			InterpolationColors = new ColorBlend
			{
				Colors = new Color[]
				{
					Color.Red,
					Color.Yellow,
					Color.Lime,
					Color.Aqua,
					Color.Blue,
					Color.Fuchsia,
					Color.Red
				},
				Positions = new float[]
				{
					0f,
					0.166666672f,
					0.333333343f,
					0.5f,
					0.6666667f,
					0.8333333f,
					1f
				}
			}
		};
	}

	// Token: 0x0600099D RID: 2461 RVA: 0x0002D01C File Offset: 0x0002B21C
	public static float smethod_1(Color color_0)
	{
		int num = (int)color_0.R;
		int num2 = (int)color_0.G;
		int num3 = (int)color_0.B;
		checked
		{
			float result;
			if (num <= num2 && num <= num3)
			{
				num2 -= num;
				num3 -= num;
				if (num2 + num3 != 0)
				{
					result = unchecked(0.333333343f * (float)num2 + 0.6666667f * (float)num3) / (float)(num2 + num3);
				}
				else
				{
					result = 0f;
				}
			}
			else if (num2 > num || num2 > num3)
			{
				num -= num3;
				num2 -= num3;
				if (num + num2 != 0)
				{
					result = unchecked(0f * (float)num + 0.333333343f * (float)num2) / (float)(num + num2);
				}
				else
				{
					result = 0f;
				}
			}
			else
			{
				num -= num2;
				num3 -= num2;
				if (num + num3 == 0)
				{
					result = 0f;
				}
				else
				{
					result = unchecked(1f * (float)num + 0.6666667f * (float)num3) / (float)(num + num3);
				}
			}
			return result;
		}
	}

	// Token: 0x0600099E RID: 2462 RVA: 0x0002D0F4 File Offset: 0x0002B2F4
	public static Color smethod_2(float float_0)
	{
		byte red = 0;
		byte green = 0;
		byte blue = 0;
		checked
		{
			if (float_0 >= 0.166666672f)
			{
				if (float_0 < 0.333333343f)
				{
					green = byte.MaxValue;
					red = (byte)Math.Round((double)(unchecked(255f * (0.333333343f - float_0) / (float_0 - 0f))));
				}
				else if (float_0 < 0.5f)
				{
					green = byte.MaxValue;
					blue = (byte)Math.Round((double)(unchecked(255f * (0.333333343f - float_0) / (float_0 - 0.6666667f))));
				}
				else if (float_0 >= 0.6666667f)
				{
					if (float_0 >= 0.8333333f)
					{
						red = byte.MaxValue;
						blue = (byte)Math.Round((double)(unchecked(255f * (float_0 - 1f) / (0.6666667f - float_0))));
					}
					else
					{
						blue = byte.MaxValue;
						red = (byte)Math.Round((double)(unchecked(255f * (0.6666667f - float_0) / (float_0 - 1f))));
					}
				}
				else
				{
					blue = byte.MaxValue;
					green = (byte)Math.Round((double)(unchecked(255f * (float_0 - 0.6666667f) / (0.333333343f - float_0))));
				}
			}
			else
			{
				red = byte.MaxValue;
				green = (byte)Math.Round((double)(unchecked(255f * (float_0 - 0f) / (0.333333343f - float_0))));
			}
			return Color.FromArgb((int)red, (int)green, (int)blue);
		}
	}
}
