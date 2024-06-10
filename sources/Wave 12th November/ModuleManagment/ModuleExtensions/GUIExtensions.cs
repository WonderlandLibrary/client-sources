using System;

namespace WaveClient.ModuleManagment.ModuleExtensions
{
	// Token: 0x0200000C RID: 12
	public class GUIExtensions
	{
		// Token: 0x06000062 RID: 98 RVA: 0x000036C0 File Offset: 0x000018C0
		public static string GetBoolStateText(bool State)
		{
			if (State)
			{
				return "On";
			}
			return "Off";
		}

		// Token: 0x06000063 RID: 99 RVA: 0x000036D0 File Offset: 0x000018D0
		public static string GetModuleListString()
		{
			return "";
		}
	}
}
