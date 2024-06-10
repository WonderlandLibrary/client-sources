using System;
using Wave.Cmr.MemoryManagement;

namespace WaveClient.Module
{
	// Token: 0x0200000F RID: 15
	public static class Autoclicker
	{
		// Token: 0x0600006A RID: 106 RVA: 0x0000375D File Offset: 0x0000195D
		public static void Tick10()
		{
		}

		// Token: 0x0400004D RID: 77
		public static bool ToggleState;

		// Token: 0x0400004E RID: 78
		private static Pointer ClickDelay = new Pointer("Minecraft.Windows.exe", 57208472, new int[]
		{
			128,
			0,
			8,
			1088,
			676,
			8,
			80
		});
	}
}
