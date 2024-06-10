using System;

namespace WaveClient.ModuleManagment.ModuleExtensions
{
	// Token: 0x02000013 RID: 19
	public class GUIExtensions
	{
		// Token: 0x06000054 RID: 84 RVA: 0x0000314C File Offset: 0x0000134C
		public static string GetBoolStateText(bool State)
		{
			if (State)
			{
				return "On";
			}
			return "Off";
		}

		// Token: 0x06000055 RID: 85 RVA: 0x0000315C File Offset: 0x0000135C
		public static string GetModuleListString()
		{
			return "";
		}
	}
}
