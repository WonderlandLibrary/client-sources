using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000010 RID: 16
	public static class StickyGround
	{
		// Token: 0x0600006C RID: 108 RVA: 0x00003788 File Offset: 0x00001988
		public static void Tick10()
		{
			float num = StickyGround.val;
			float num2 = -0.1f;
			float num3 = 0.5f;
			if (num <= num2)
			{
				num = num3;
			}
			Memory0.mem.WriteMemory(StickyGround.yvelo, num);
		}

		// Token: 0x0400004F RID: 79
		public static bool ToggleState;

		// Token: 0x04000050 RID: 80
		private static Pointer yvelo = new Pointer("Minecraft.Windows.exe", 57279112, new int[]
		{
			104,
			8,
			24,
			136,
			128,
			3000,
			1176
		});

		// Token: 0x04000051 RID: 81
		private static float val = Memory0.mem.ReadFloat(StickyGround.yvelo);
	}
}
