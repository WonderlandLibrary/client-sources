using System;
using System.Drawing;
using System.Windows.Media;

namespace Wave.Cmr.Color
{
	// Token: 0x02000027 RID: 39
	public static class cmr_color
	{
		// Token: 0x06000134 RID: 308 RVA: 0x00004A98 File Offset: 0x00002C98
		public static System.Drawing.Color CounterToColor(double counter)
		{
			counter = Math.Max(counter, 0.0);
			int num = Convert.ToInt32(counter);
			counter %= 1536.0;
			counter = Math.Min(counter, 1536.0);
			System.Drawing.Color result;
			if (counter < 256.0)
			{
				result = System.Drawing.Color.FromArgb(255, 255, num, 0);
			}
			else if (counter < 511.0)
			{
				num %= 256;
				result = System.Drawing.Color.FromArgb(255, 255 - num, 255, 0);
			}
			else if (counter < 768.0)
			{
				num %= 256;
				result = System.Drawing.Color.FromArgb(255, 0, 255, num);
			}
			else if (counter < 1024.0)
			{
				num %= 256;
				result = System.Drawing.Color.FromArgb(255, 0, 255 - num, 255);
			}
			else if (counter < 1280.0)
			{
				num %= 256;
				result = System.Drawing.Color.FromArgb(255, num, 0, 255);
			}
			else if (counter < 1536.0)
			{
				num %= 256;
				result = System.Drawing.Color.FromArgb(255, 255, 0, 255 - num);
			}
			else
			{
				result = System.Drawing.Color.FromArgb(255, 255, 255, 255);
			}
			return result;
		}

		// Token: 0x06000135 RID: 309 RVA: 0x00004BFC File Offset: 0x00002DFC
		public static System.Windows.Media.Color WindowsCounterToColor(double counter)
		{
			System.Drawing.Color color = cmr_color.CounterToColor(counter);
			return System.Windows.Media.Color.FromArgb(byte.MaxValue, color.R, color.G, color.B);
		}
	}
}
