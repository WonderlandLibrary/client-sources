using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

namespace Client
{
	// Token: 0x02000016 RID: 22
	[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "4.0.0.0")]
	[CompilerGenerated]
	[DebuggerNonUserCode]
	internal class Class9
	{
		// Token: 0x060000E4 RID: 228 RVA: 0x00002071 File Offset: 0x00000271
		internal Class9()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
		}

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x060000E5 RID: 229 RVA: 0x0001F930 File Offset: 0x0001DB30
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static ResourceManager ResourceManager_0
		{
			get
			{
				int num = 0;
				ResourceManager result;
				for (;;)
				{
					if (num == 5)
					{
						ResourceManager resourceManager;
						Class9.resourceManager_0 = resourceManager;
						num = 6;
					}
					bool flag;
					if (num == 2)
					{
						flag = (Class9.resourceManager_0 == null);
						num = 3;
					}
					if (num != 3)
					{
						goto IL_4C;
					}
					if (flag)
					{
						num = 4;
						goto IL_4C;
					}
					goto IL_59;
					IL_6B:
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 4)
					{
						ResourceManager resourceManager = new ResourceManager("Class9", typeof(Class9).Assembly);
						num = 5;
					}
					if (num == 7)
					{
						break;
					}
					if (num == 0)
					{
						num = 1;
					}
					if (num == 8)
					{
						break;
					}
					continue;
					IL_4C:
					if (num != 6)
					{
						goto IL_6B;
					}
					IL_59:
					result = Class9.resourceManager_0;
					num = 7;
					goto IL_6B;
				}
				return result;
			}
		}

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x060000E6 RID: 230 RVA: 0x0001FA58 File Offset: 0x0001DC58
		// (set) Token: 0x060000E7 RID: 231 RVA: 0x0001FAE4 File Offset: 0x0001DCE4
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static CultureInfo CultureInfo_0
		{
			get
			{
				int num = 0;
				CultureInfo result;
				do
				{
					if (num == 2)
					{
						result = Class9.cultureInfo_0;
						num = 3;
					}
					if (num == 3)
					{
						break;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 4);
				return result;
			}
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						Class9.cultureInfo_0 = value;
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x040000A5 RID: 165
		private static ResourceManager resourceManager_0;

		// Token: 0x040000A6 RID: 166
		private static CultureInfo cultureInfo_0;
	}
}
