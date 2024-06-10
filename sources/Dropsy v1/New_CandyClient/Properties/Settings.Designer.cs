using System;
using System.CodeDom.Compiler;
using System.Configuration;
using System.Runtime.CompilerServices;

namespace New_CandyClient.Properties
{
	// Token: 0x0200002B RID: 43
	[CompilerGenerated]
	[GeneratedCode("Microsoft.VisualStudio.Editors.SettingsDesigner.SettingsSingleFileGenerator", "16.7.0.0")]
	internal sealed partial class Settings : ApplicationSettingsBase
	{
		// Token: 0x1700002A RID: 42
		// (get) Token: 0x0600015E RID: 350 RVA: 0x000128C1 File Offset: 0x00010CC1
		public static Settings Default
		{
			get
			{
				return Settings.defaultInstance;
			}
		}

		// Token: 0x04000155 RID: 341
		private static Settings defaultInstance = (Settings)SettingsBase.Synchronized(new Settings());
	}
}
