using System;
using System.Drawing;
using System.Windows.Media;

namespace Wave.Cmr.Color
{
	// Token: 0x02000025 RID: 37
	public static class cmr_color
	{
		// Token: 0x06000113 RID: 275 RVA: 0x0000418C File Offset: 0x0000238C
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

		// Token: 0x06000114 RID: 276 RVA: 0x000042F0 File Offset: 0x000024F0
		public static System.Windows.Media.Color WindowsCounterToColor(double counter)
		{
			System.Drawing.Color color = cmr_color.CounterToColor(counter);
			return System.Windows.Media.Color.FromArgb(byte.MaxValue, color.R, color.G, color.B);
		}
	}
}
