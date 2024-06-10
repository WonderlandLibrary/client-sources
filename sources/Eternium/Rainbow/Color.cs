using System;
using System.Drawing;
using System.Windows.Media;

namespace Eternium_mcpe_client.Rainbow
{
	// Token: 0x0200000D RID: 13
	public static class Color
	{
		// Token: 0x06000031 RID: 49 RVA: 0x000053A8 File Offset: 0x000035A8
		public static System.Drawing.Color CounterToColor(double counter)
		{
			counter = Math.Max(counter, 0.0);
			int num = Convert.ToInt32(counter);
			counter %= 1536.0;
			counter = Math.Min(counter, 1536.0);
			bool flag = counter < 256.0;
			System.Drawing.Color result;
			if (flag)
			{
				result = System.Drawing.Color.FromArgb(255, 255, num, 0);
			}
			else
			{
				bool flag2 = counter < 511.0;
				if (flag2)
				{
					num %= 256;
					result = System.Drawing.Color.FromArgb(255, 255 - num, 255, 0);
				}
				else
				{
					bool flag3 = counter < 768.0;
					if (flag3)
					{
						num %= 256;
						result = System.Drawing.Color.FromArgb(255, 0, 255, num);
					}
					else
					{
						bool flag4 = counter < 1024.0;
						if (flag4)
						{
							num %= 256;
							result = System.Drawing.Color.FromArgb(255, 0, 255 - num, 255);
						}
						else
						{
							bool flag5 = counter < 1280.0;
							if (flag5)
							{
								num %= 256;
								result = System.Drawing.Color.FromArgb(255, num, 0, 255);
							}
							else
							{
								bool flag6 = counter < 1536.0;
								if (flag6)
								{
									num %= 256;
									result = System.Drawing.Color.FromArgb(255, 255, 0, 255 - num);
								}
								else
								{
									result = System.Drawing.Color.FromArgb(255, 255, 255, 255);
								}
							}
						}
					}
				}
			}
			return result;
		}

		// Token: 0x06000032 RID: 50 RVA: 0x00005540 File Offset: 0x00003740
		public static System.Windows.Media.Color WindowsCounterToColor(double counter)
		{
			System.Drawing.Color color = Eternium_mcpe_client.Rainbow.Color.CounterToColor(counter);
			return System.Windows.Media.Color.FromArgb(byte.MaxValue, color.R, color.G, color.B);
		}
	}
}
