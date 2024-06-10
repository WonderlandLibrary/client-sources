using System;

namespace Rainbow
{
	// Token: 0x02000009 RID: 9
	public static class RainbowPanel
	{
		// Token: 0x06000017 RID: 23 RVA: 0x00002748 File Offset: 0x00000B48
		public static void RainbowEffect()
		{
			if (RainbowPanel.runlock == 1)
			{
				if (RainbowPanel.A != 75)
				{
					RainbowPanel.A--;
				}
				if (RainbowPanel.G == 130)
				{
					if (RainbowPanel.A == 75)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.A--;
					}
				}
				else
				{
					RainbowPanel.G--;
				}
			}
			if (RainbowPanel.runlock == 2)
			{
				if (RainbowPanel.G == 254)
				{
					if (RainbowPanel.A == 0)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.A--;
					}
				}
				else
				{
					RainbowPanel.G++;
				}
			}
			if (RainbowPanel.runlock == 3)
			{
				if (RainbowPanel.R == 254)
				{
					if (RainbowPanel.G == 1)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.G--;
					}
				}
				else
				{
					RainbowPanel.R++;
				}
			}
			if (RainbowPanel.runlock == 4)
			{
				if (RainbowPanel.A == 254)
				{
					if (RainbowPanel.R == 254)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.R = 254;
					}
				}
				else
				{
					RainbowPanel.A++;
				}
			}
			if (RainbowPanel.runlock == 5)
			{
				if (RainbowPanel.A == 254)
				{
					if (RainbowPanel.R == 127)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.R--;
					}
				}
				else
				{
					RainbowPanel.A = 254;
				}
			}
			if (RainbowPanel.runlock == 6)
			{
				if (RainbowPanel.R == 1)
				{
					if (RainbowPanel.A == 254)
					{
						RainbowPanel.runlock++;
					}
					else
					{
						RainbowPanel.A = 254;
					}
				}
				else
				{
					RainbowPanel.R--;
				}
			}
			if (RainbowPanel.runlock == 7)
			{
				if (RainbowPanel.A == 148)
				{
					if (RainbowPanel.G == 211)
					{
						RainbowPanel.A = 148;
						RainbowPanel.R = 0;
						RainbowPanel.G = 211;
						RainbowPanel.runlock = 1;
						return;
					}
					RainbowPanel.G++;
					return;
				}
				else
				{
					RainbowPanel.A--;
				}
			}
		}

		// Token: 0x0400003C RID: 60
		public static int A = 148;

		// Token: 0x0400003D RID: 61
		public static int R = 0;

		// Token: 0x0400003E RID: 62
		public static int G = 211;

		// Token: 0x0400003F RID: 63
		public static int runlock = 1;
	}
}
