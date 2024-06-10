using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001A RID: 26
	public static class KillYourSelf
	{
		// Token: 0x06000081 RID: 129 RVA: 0x00003A48 File Offset: 0x00001C48
		public static void Enable()
		{
			float num = Memory0.mem.ReadFloat(KillYourSelf.Y1_Position);
			float num2 = Memory0.mem.ReadFloat(KillYourSelf.Y2_Position);
			float num3 = 30f + num;
			Memory0.mem.WriteMemory(KillYourSelf.Y1_Position, num3);
			Memory0.mem.WriteMemory(KillYourSelf.Y2_Position, num3 + 1.8f);
			Console.WriteLine("The Ypos 1, and 2 is, " + num.ToString() + ": " + num2.ToString());
			KillYourSelf.ToggleState = false;
		}

		// Token: 0x04000060 RID: 96
		public static bool ToggleState;

		// Token: 0x04000061 RID: 97
		private static Pointer X1_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1112
		});

		// Token: 0x04000062 RID: 98
		private static Pointer Y1_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1116
		});

		// Token: 0x04000063 RID: 99
		private static Pointer Z1_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1120
		});

		// Token: 0x04000064 RID: 100
		private static Pointer X2_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1124
		});

		// Token: 0x04000065 RID: 101
		private static Pointer Y2_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1128
		});

		// Token: 0x04000066 RID: 102
		private static Pointer Z2_Position = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			1129
		});
	}
}
