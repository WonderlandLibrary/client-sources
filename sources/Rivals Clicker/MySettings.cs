using System;
using System.CodeDom.Compiler;
using System.Collections;
using System.ComponentModel;
using System.Configuration;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost.My
{
	// Token: 0x02000009 RID: 9
	[CompilerGenerated]
	[GeneratedCode("Microsoft.VisualStudio.Editors.SettingsDesigner.SettingsSingleFileGenerator", "16.1.0.0")]
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal sealed partial class MySettings : ApplicationSettingsBase
	{
		// Token: 0x1700000D RID: 13
		// (get) Token: 0x06000027 RID: 39 RVA: 0x00003D00 File Offset: 0x00001F00
		// (set) Token: 0x06000028 RID: 40 RVA: 0x000021BC File Offset: 0x000003BC
		[UserScopedSetting]
		[DebuggerNonUserCode]
		public Color mainColor
		{
			get
			{
				object obj = this["mainColor"];
				return (obj != null) ? ((Color)obj) : default(Color);
			}
			set
			{
				this["mainColor"] = value;
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x0600002D RID: 45 RVA: 0x00003D7C File Offset: 0x00001F7C
		// (set) Token: 0x0600002E RID: 46 RVA: 0x000021FB File Offset: 0x000003FB
		[UserScopedSetting]
		[DebuggerNonUserCode]
		public ArrayList clicksounds
		{
			get
			{
				return (ArrayList)this["clicksounds"];
			}
			set
			{
				this["clicksounds"] = value;
			}
		}
	}
}
