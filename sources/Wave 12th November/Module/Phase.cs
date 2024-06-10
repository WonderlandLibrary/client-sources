using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001B RID: 27
	public static class Phase
	{
		// Token: 0x06000083 RID: 131 RVA: 0x00003BC4 File Offset: 0x00001DC4
		public static void Tick10()
		{
			float y = Phase.Y1;
			Memory0.mem.WriteMemory(Phase.ypos2, y);
		}

		// Token: 0x06000084 RID: 132 RVA: 0x00003BEC File Offset: 0x00001DEC
		public static void Disable()
		{
		}

		// Token: 0x04000067 RID: 103
		public static bool ToggleState;

		// Token: 0x04000068 RID: 104
		private static Pointer ypos = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1116
		});

		// Token: 0x04000069 RID: 105
		private static Pointer ypos2 = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1128
		});

		// Token: 0x0400006A RID: 106
		private static float Y1 = Memory0.mem.ReadFloat(Phase.ypos);

		// Token: 0x0400006B RID: 107
		private static float Y2 = Memory0.mem.ReadFloat(Phase.ypos2);
	}
}
