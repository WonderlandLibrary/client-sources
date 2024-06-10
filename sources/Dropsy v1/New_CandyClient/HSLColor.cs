using System;
using System.Drawing;

namespace New_CandyClient
{
	// Token: 0x02000029 RID: 41
	public class HSLColor
	{
		// Token: 0x1700000A RID: 10
		// (get) Token: 0x0600012C RID: 300 RVA: 0x00012124 File Offset: 0x00010524
		// (set) Token: 0x0600012D RID: 301 RVA: 0x00012136 File Offset: 0x00010536
		public double Hue
		{
			get
			{
				return this.hue * 240.0;
			}
			set
			{
				this.hue = this.CheckRange(value / 240.0);
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600012E RID: 302 RVA: 0x0001214F File Offset: 0x0001054F
		// (set) Token: 0x0600012F RID: 303 RVA: 0x00012161 File Offset: 0x00010561
		public double Saturation
		{
			get
			{
				return this.saturation * 240.0;
			}
			set
			{
				this.saturation = this.CheckRange(value / 240.0);
			}
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000130 RID: 304 RVA: 0x0001217A File Offset: 0x0001057A
		// (set) Token: 0x06000131 RID: 305 RVA: 0x0001218C File Offset: 0x0001058C
		public double Luminosity
		{
			get
			{
				return this.luminosity * 240.0;
			}
			set
			{
				this.luminosity = this.CheckRange(value / 240.0);
			}
		}

		// Token: 0x06000132 RID: 306 RVA: 0x000121A5 File Offset: 0x000105A5
		private double CheckRange(double value)
		{
			if (value < 0.0)
			{
				value = 0.0;
			}
			else if (value > 1.0)
			{
				value = 1.0;
			}
			return value;
		}

		// Token: 0x06000133 RID: 307 RVA: 0x000121D8 File Offset: 0x000105D8
		public override string ToString()
		{
			return string.Format("H: {0:#0.##} S: {1:#0.##} L: {2:#0.##}", this.Hue, this.Saturation, this.Luminosity);
		}

		// Token: 0x06000134 RID: 308 RVA: 0x00012208 File Offset: 0x00010608
		public string ToRGBString()
		{
			Color color = this;
			return string.Format("R: {0:#0.##} G: {1:#0.##} B: {2:#0.##}", color.R, color.G, color.B);
		}

		// Token: 0x06000135 RID: 309 RVA: 0x0001224C File Offset: 0x0001064C
		public static implicit operator Color(HSLColor hslColor)
		{
			double num = 0.0;
			double num2 = 0.0;
			double num3 = 0.0;
			if (hslColor.luminosity != 0.0)
			{
				if (hslColor.saturation == 0.0)
				{
					num2 = (num = (num3 = hslColor.luminosity));
				}
				else
				{
					double temp = HSLColor.GetTemp2(hslColor);
					double temp2 = 2.0 * hslColor.luminosity - temp;
					num = HSLColor.GetColorComponent(temp2, temp, hslColor.hue + 0.3333333333333333);
					num2 = HSLColor.GetColorComponent(temp2, temp, hslColor.hue);
					num3 = HSLColor.GetColorComponent(temp2, temp, hslColor.hue - 0.3333333333333333);
				}
			}
			return Color.FromArgb((int)(255.0 * num), (int)(255.0 * num2), (int)(255.0 * num3));
		}

		// Token: 0x06000136 RID: 310 RVA: 0x00012328 File Offset: 0x00010728
		private static double GetColorComponent(double temp1, double temp2, double temp3)
		{
			temp3 = HSLColor.MoveIntoRange(temp3);
			if (temp3 < 0.16666666666666666)
			{
				return temp1 + (temp2 - temp1) * 6.0 * temp3;
			}
			if (temp3 < 0.5)
			{
				return temp2;
			}
			if (temp3 < 0.6666666666666666)
			{
				return temp1 + (temp2 - temp1) * (0.6666666666666666 - temp3) * 6.0;
			}
			return temp1;
		}

		// Token: 0x06000137 RID: 311 RVA: 0x00012392 File Offset: 0x00010792
		private static double MoveIntoRange(double temp3)
		{
			if (temp3 < 0.0)
			{
				temp3 += 1.0;
			}
			else if (temp3 > 1.0)
			{
				temp3 -= 1.0;
			}
			return temp3;
		}

		// Token: 0x06000138 RID: 312 RVA: 0x000123CC File Offset: 0x000107CC
		private static double GetTemp2(HSLColor hslColor)
		{
			double result;
			if (hslColor.luminosity < 0.5)
			{
				result = hslColor.luminosity * (1.0 + hslColor.saturation);
			}
			else
			{
				result = hslColor.luminosity + hslColor.saturation - hslColor.luminosity * hslColor.saturation;
			}
			return result;
		}

		// Token: 0x06000139 RID: 313 RVA: 0x00012421 File Offset: 0x00010821
		public static implicit operator HSLColor(Color color)
		{
			return new HSLColor
			{
				hue = (double)color.GetHue() / 360.0,
				luminosity = (double)color.GetBrightness(),
				saturation = (double)color.GetSaturation()
			};
		}

		// Token: 0x0600013A RID: 314 RVA: 0x0001245C File Offset: 0x0001085C
		public void SetRGB(int red, int green, int blue)
		{
			HSLColor hslcolor = Color.FromArgb(red, green, blue);
			this.hue = hslcolor.hue;
			this.saturation = hslcolor.saturation;
			this.luminosity = hslcolor.luminosity;
		}

		// Token: 0x0600013B RID: 315 RVA: 0x0001249B File Offset: 0x0001089B
		public HSLColor()
		{
		}

		// Token: 0x0600013C RID: 316 RVA: 0x000124D0 File Offset: 0x000108D0
		public HSLColor(Color color)
		{
			this.SetRGB((int)color.R, (int)color.G, (int)color.B);
		}

		// Token: 0x0600013D RID: 317 RVA: 0x0001252B File Offset: 0x0001092B
		public HSLColor(int red, int green, int blue)
		{
			this.SetRGB(red, green, blue);
		}

		// Token: 0x0600013E RID: 318 RVA: 0x0001256C File Offset: 0x0001096C
		public HSLColor(double hue, double saturation, double luminosity)
		{
			this.Hue = hue;
			this.Saturation = saturation;
			this.Luminosity = luminosity;
		}

		// Token: 0x0400014F RID: 335
		private double hue = 1.0;

		// Token: 0x04000150 RID: 336
		private double saturation = 1.0;

		// Token: 0x04000151 RID: 337
		private double luminosity = 1.0;

		// Token: 0x04000152 RID: 338
		private const double scale = 240.0;
	}
}
