using System;
using System.Drawing;
using System.Drawing.Drawing2D;

namespace Meth
{
	// Token: 0x0200000B RID: 11
	public class Rainbow
	{
		// Token: 0x0600016A RID: 362 RVA: 0x00002A35 File Offset: 0x00000C35
		private Rainbow()
		{
		}

		// Token: 0x0600016B RID: 363 RVA: 0x0000D118 File Offset: 0x0000B318
		public static LinearGradientBrush RainbowBrush(Point point1, Point point2)
		{
			return new LinearGradientBrush(point1, point2, Color.Red, Color.Red)
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

		// Token: 0x0600016C RID: 364 RVA: 0x0000D1BC File Offset: 0x0000B3BC
		public static float ColorToRainbowNumber(Color clr)
		{
			int num = (int)clr.R;
			int num2 = (int)clr.G;
			int num3 = (int)clr.B;
			float result;
			if (num <= num2 && num <= num3)
			{
				num2 -= num;
				num3 -= num;
				if (num2 + num3 == 0)
				{
					result = 0f;
				}
				else
				{
					result = (0.333333343f * (float)num2 + 0.6666667f * (float)num3) / (float)(num2 + num3);
				}
			}
			else if (num2 <= num && num2 <= num3)
			{
				num -= num2;
				num3 -= num2;
				if (num + num3 == 0)
				{
					result = 0f;
				}
				else
				{
					result = (1f * (float)num + 0.6666667f * (float)num3) / (float)(num + num3);
				}
			}
			else
			{
				num -= num3;
				num2 -= num3;
				if (num + num2 == 0)
				{
					result = 0f;
				}
				else
				{
					result = (0f * (float)num + 0.333333343f * (float)num2) / (float)(num + num2);
				}
			}
			return result;
		}

		// Token: 0x0600016D RID: 365 RVA: 0x0000D27C File Offset: 0x0000B47C
		public static Color RainbowNumberToColor(float number)
		{
			byte b = 0;
			byte b2 = 0;
			byte b3 = 0;
			if (number < 0.166666672f)
			{
				b = byte.MaxValue;
				b2 = (byte)Math.Round((double)((float)b * (number - 0f) / (0.333333343f - number)));
			}
			else if (number < 0.333333343f)
			{
				b2 = byte.MaxValue;
				b = (byte)Math.Round((double)((float)b2 * (0.333333343f - number) / (number - 0f)));
			}
			else if (number < 0.5f)
			{
				b2 = byte.MaxValue;
				b3 = (byte)Math.Round((double)((float)b2 * (0.333333343f - number) / (number - 0.6666667f)));
			}
			else if (number < 0.6666667f)
			{
				b3 = byte.MaxValue;
				b2 = (byte)Math.Round((double)((float)b3 * (number - 0.6666667f) / (0.333333343f - number)));
			}
			else if (number < 0.8333333f)
			{
				b3 = byte.MaxValue;
				b = (byte)Math.Round((double)((float)b3 * (0.6666667f - number) / (number - 1f)));
			}
			else
			{
				b = byte.MaxValue;
				b3 = (byte)Math.Round((double)((float)b * (number - 1f) / (0.6666667f - number)));
			}
			return Color.FromArgb((int)b, (int)b2, (int)b3);
		}
	}
}
