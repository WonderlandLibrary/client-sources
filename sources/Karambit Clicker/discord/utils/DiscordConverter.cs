using System;
using System.Drawing;
using System.Globalization;

namespace Discord.Utils
{
	// Token: 0x02000023 RID: 35
	public class DiscordConverter
	{
		// Token: 0x06000110 RID: 272 RVA: 0x00011C1C File Offset: 0x0001001C
		public static int ColorToHex(Color SourceColor)
		{
			return int.Parse(SourceColor.R.ToString("X2") + SourceColor.G.ToString("X2") + SourceColor.B.ToString("X2"), NumberStyles.HexNumber);
		}
	}
}
