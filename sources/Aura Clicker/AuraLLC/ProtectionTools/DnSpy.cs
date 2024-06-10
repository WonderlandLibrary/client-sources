using System;
using System.IO;

namespace AuraLLC.ProtectionTools
{
	// Token: 0x02000005 RID: 5
	internal static class DnSpy
	{
		// Token: 0x0600000D RID: 13 RVA: 0x000022A0 File Offset: 0x000004A0
		internal static bool ValueType()
		{
			return File.Exists(Environment.ExpandEnvironmentVariables("%appdata%") + "\\dnSpy\\dnSpy.xml");
		}
	}
}
