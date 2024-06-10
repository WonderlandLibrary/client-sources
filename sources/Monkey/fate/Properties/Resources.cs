using System;
using System.ComponentModel;
using System.Globalization;
using System.Resources;

namespace fate.Properties
{
	// Token: 0x0200001E RID: 30
	internal class Resources
	{
		// Token: 0x06000123 RID: 291 RVA: 0x0000BE24 File Offset: 0x00009E24
		internal Resources()
		{
		}

		// Token: 0x1700003F RID: 63
		// (get) Token: 0x06000124 RID: 292 RVA: 0x0000BE30 File Offset: 0x00009E30
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static ResourceManager ResourceManager
		{
			get
			{
			}
		}

		// Token: 0x17000040 RID: 64
		// (get) Token: 0x06000125 RID: 293 RVA: 0x0000BE78 File Offset: 0x00009E78
		// (set) Token: 0x06000126 RID: 294 RVA: 0x0000BE8F File Offset: 0x00009E8F
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static CultureInfo Culture
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x040000DE RID: 222
		private static ResourceManager resourceMan;

		// Token: 0x040000DF RID: 223
		private static CultureInfo resourceCulture;
	}
}
