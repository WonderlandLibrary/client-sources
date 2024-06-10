using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

// Token: 0x0200001A RID: 26
[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "15.0.0.0")]
[DebuggerNonUserCode]
[CompilerGenerated]
internal class Class13
{
	// Token: 0x0600007C RID: 124 RVA: 0x00007074 File Offset: 0x00005274
	internal Class13()
	{
	}

	// Token: 0x17000001 RID: 1
	// (get) Token: 0x0600007D RID: 125 RVA: 0x0000B5F4 File Offset: 0x000097F4
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static ResourceManager ResourceManager_0
	{
		get
		{
			if (Class13.resourceManager_0 == null)
			{
				ResourceManager resourceManager = new ResourceManager("5;\\+/\\, uTna%0\\\\Zd\\[T{t|\\*zO:!", typeof(Class13).Assembly);
				Class13.resourceManager_0 = resourceManager;
			}
			return Class13.resourceManager_0;
		}
	}

	// Token: 0x17000002 RID: 2
	// (get) Token: 0x0600007E RID: 126 RVA: 0x0000B634 File Offset: 0x00009834
	// (set) Token: 0x0600007F RID: 127 RVA: 0x0000B648 File Offset: 0x00009848
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static CultureInfo CultureInfo_0
	{
		get
		{
			return Class13.cultureInfo_0;
		}
		set
		{
			Class13.cultureInfo_0 = value;
		}
	}

	// Token: 0x17000003 RID: 3
	// (get) Token: 0x06000080 RID: 128 RVA: 0x0000B65C File Offset: 0x0000985C
	internal static Bitmap Bitmap_0
	{
		get
		{
			object @object = Class13.ResourceManager_0.GetObject("images", Class13.cultureInfo_0);
			return (Bitmap)@object;
		}
	}

	// Token: 0x17000004 RID: 4
	// (get) Token: 0x06000081 RID: 129 RVA: 0x0000B688 File Offset: 0x00009888
	internal static Bitmap Bitmap_1
	{
		get
		{
			object @object = Class13.ResourceManager_0.GetObject("linha-imagem-animada-0429", Class13.cultureInfo_0);
			return (Bitmap)@object;
		}
	}

	// Token: 0x0400007B RID: 123
	private static ResourceManager resourceManager_0;

	// Token: 0x0400007C RID: 124
	private static CultureInfo cultureInfo_0;
}
