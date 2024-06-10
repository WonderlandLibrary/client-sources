using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Runtime.CompilerServices;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000008 RID: 8
[CompilerGenerated]
[DebuggerNonUserCode]
[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "15.0.0.0")]
[StandardModule]
[HideModuleName]
internal sealed class Class5
{
	// Token: 0x1700000D RID: 13
	// (get) Token: 0x06000043 RID: 67 RVA: 0x000030EC File Offset: 0x000012EC
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static ResourceManager ResourceManager_0
	{
		get
		{
			if (object.ReferenceEquals(Class5.resourceManager_0, null))
			{
				ResourceManager resourceManager = new ResourceManager("Air.Resources", typeof(Class5).Assembly);
				Class5.resourceManager_0 = resourceManager;
			}
			return Class5.resourceManager_0;
		}
	}

	// Token: 0x1700000E RID: 14
	// (get) Token: 0x06000044 RID: 68 RVA: 0x00003130 File Offset: 0x00001330
	// (set) Token: 0x06000045 RID: 69 RVA: 0x00003144 File Offset: 0x00001344
	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static CultureInfo CultureInfo_0
	{
		get
		{
			return Class5.cultureInfo_0;
		}
		set
		{
			Class5.cultureInfo_0 = value;
		}
	}

	// Token: 0x1700000F RID: 15
	// (get) Token: 0x06000046 RID: 70 RVA: 0x0000314C File Offset: 0x0000134C
	internal static Bitmap Bitmap_0
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("0a74feb686b3100a82295cfe835b3798", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000010 RID: 16
	// (get) Token: 0x06000047 RID: 71 RVA: 0x0000317C File Offset: 0x0000137C
	internal static Bitmap Bitmap_1
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("484613", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000011 RID: 17
	// (get) Token: 0x06000048 RID: 72 RVA: 0x000031AC File Offset: 0x000013AC
	internal static Bitmap Bitmap_2
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f9", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000012 RID: 18
	// (get) Token: 0x06000049 RID: 73 RVA: 0x000031DC File Offset: 0x000013DC
	internal static Bitmap Bitmap_3
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f91", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000013 RID: 19
	// (get) Token: 0x0600004A RID: 74 RVA: 0x0000320C File Offset: 0x0000140C
	internal static Bitmap Bitmap_4
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f92", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000014 RID: 20
	// (get) Token: 0x0600004B RID: 75 RVA: 0x0000323C File Offset: 0x0000143C
	internal static Bitmap Bitmap_5
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f93", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000015 RID: 21
	// (get) Token: 0x0600004C RID: 76 RVA: 0x0000326C File Offset: 0x0000146C
	internal static Bitmap Bitmap_6
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f94", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000016 RID: 22
	// (get) Token: 0x0600004D RID: 77 RVA: 0x0000329C File Offset: 0x0000149C
	internal static Bitmap Bitmap_7
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f95", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000017 RID: 23
	// (get) Token: 0x0600004E RID: 78 RVA: 0x000032CC File Offset: 0x000014CC
	internal static Bitmap Bitmap_8
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("600c2f8fa83ce3f96", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000018 RID: 24
	// (get) Token: 0x0600004F RID: 79 RVA: 0x000032FC File Offset: 0x000014FC
	internal static Bitmap Bitmap_9
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("6lCPkzK", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000019 RID: 25
	// (get) Token: 0x06000050 RID: 80 RVA: 0x0000332C File Offset: 0x0000152C
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_0
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("_single", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700001A RID: 26
	// (get) Token: 0x06000051 RID: 81 RVA: 0x00003350 File Offset: 0x00001550
	internal static Bitmap Bitmap_10
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("add-button", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700001B RID: 27
	// (get) Token: 0x06000052 RID: 82 RVA: 0x00003380 File Offset: 0x00001580
	internal static Bitmap Bitmap_11
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("add-button1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700001C RID: 28
	// (get) Token: 0x06000053 RID: 83 RVA: 0x000033B0 File Offset: 0x000015B0
	internal static Bitmap Bitmap_12
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Artwork_Right_Top", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700001D RID: 29
	// (get) Token: 0x06000054 RID: 84 RVA: 0x000033E0 File Offset: 0x000015E0
	internal static Bitmap Bitmap_13
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("BR5Bx45", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700001E RID: 30
	// (get) Token: 0x06000055 RID: 85 RVA: 0x00003410 File Offset: 0x00001610
	internal static Bitmap Bitmap_14
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("color-spectrum", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700001F RID: 31
	// (get) Token: 0x06000056 RID: 86 RVA: 0x00003440 File Offset: 0x00001640
	internal static Bitmap Bitmap_15
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("cursor", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000020 RID: 32
	// (get) Token: 0x06000057 RID: 87 RVA: 0x00003470 File Offset: 0x00001670
	internal static Bitmap Bitmap_16
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("d2c9b1a3ad1b773f531f39a9eb077856fb8c17ea", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000021 RID: 33
	// (get) Token: 0x06000058 RID: 88 RVA: 0x000034A0 File Offset: 0x000016A0
	internal static Bitmap Bitmap_17
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("download", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000022 RID: 34
	// (get) Token: 0x06000059 RID: 89 RVA: 0x000034D0 File Offset: 0x000016D0
	internal static Bitmap Bitmap_18
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("dsqd", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000023 RID: 35
	// (get) Token: 0x0600005A RID: 90 RVA: 0x00003500 File Offset: 0x00001700
	internal static Bitmap Bitmap_19
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("eaada0803f93c0b392956680fca34019ef982574", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000024 RID: 36
	// (get) Token: 0x0600005B RID: 91 RVA: 0x00003530 File Offset: 0x00001730
	internal static Bitmap Bitmap_20
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("ezgif.com-video-to-gif", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000025 RID: 37
	// (get) Token: 0x0600005C RID: 92 RVA: 0x00003560 File Offset: 0x00001760
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_1
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("ffff", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000026 RID: 38
	// (get) Token: 0x0600005D RID: 93 RVA: 0x00003584 File Offset: 0x00001784
	internal static Bitmap Bitmap_21
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("flesh", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000027 RID: 39
	// (get) Token: 0x0600005E RID: 94 RVA: 0x000035B4 File Offset: 0x000017B4
	internal static Bitmap Bitmap_22
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("flesh1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000028 RID: 40
	// (get) Token: 0x0600005F RID: 95 RVA: 0x000035E4 File Offset: 0x000017E4
	internal static Bitmap Bitmap_23
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("flesh2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000029 RID: 41
	// (get) Token: 0x06000060 RID: 96 RVA: 0x00003614 File Offset: 0x00001814
	internal static Bitmap Bitmap_24
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("flesh3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700002A RID: 42
	// (get) Token: 0x06000061 RID: 97 RVA: 0x00003644 File Offset: 0x00001844
	internal static Bitmap Bitmap_25
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("flesh4", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700002B RID: 43
	// (get) Token: 0x06000062 RID: 98 RVA: 0x00003674 File Offset: 0x00001874
	internal static Bitmap Bitmap_26
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("fnal", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700002C RID: 44
	// (get) Token: 0x06000063 RID: 99 RVA: 0x000036A4 File Offset: 0x000018A4
	internal static Bitmap Bitmap_27
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("frite2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700002D RID: 45
	// (get) Token: 0x06000064 RID: 100 RVA: 0x000036D4 File Offset: 0x000018D4
	internal static Bitmap Bitmap_28
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("frite21", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700002E RID: 46
	// (get) Token: 0x06000065 RID: 101 RVA: 0x00003704 File Offset: 0x00001904
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_2
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("g303", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700002F RID: 47
	// (get) Token: 0x06000066 RID: 102 RVA: 0x00003728 File Offset: 0x00001928
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_3
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("g3032", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000030 RID: 48
	// (get) Token: 0x06000067 RID: 103 RVA: 0x0000374C File Offset: 0x0000194C
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_4
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("g502", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000031 RID: 49
	// (get) Token: 0x06000068 RID: 104 RVA: 0x00003770 File Offset: 0x00001970
	internal static Bitmap Bitmap_29
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000032 RID: 50
	// (get) Token: 0x06000069 RID: 105 RVA: 0x000037A0 File Offset: 0x000019A0
	internal static Bitmap Bitmap_30
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000033 RID: 51
	// (get) Token: 0x0600006A RID: 106 RVA: 0x000037D0 File Offset: 0x000019D0
	internal static Bitmap Bitmap_31
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze - Copie (2)", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000034 RID: 52
	// (get) Token: 0x0600006B RID: 107 RVA: 0x00003800 File Offset: 0x00001A00
	internal static Bitmap Bitmap_32
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze - Copie (2)1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000035 RID: 53
	// (get) Token: 0x0600006C RID: 108 RVA: 0x00003830 File Offset: 0x00001A30
	internal static Bitmap Bitmap_33
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze - Copie (2)2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000036 RID: 54
	// (get) Token: 0x0600006D RID: 109 RVA: 0x00003860 File Offset: 0x00001A60
	internal static Bitmap Bitmap_34
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze - Copie (2)3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000037 RID: 55
	// (get) Token: 0x0600006E RID: 110 RVA: 0x00003890 File Offset: 0x00001A90
	internal static Bitmap Bitmap_35
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000038 RID: 56
	// (get) Token: 0x0600006F RID: 111 RVA: 0x000038C0 File Offset: 0x00001AC0
	internal static Bitmap Bitmap_36
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000039 RID: 57
	// (get) Token: 0x06000070 RID: 112 RVA: 0x000038F0 File Offset: 0x00001AF0
	internal static Bitmap Bitmap_37
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700003A RID: 58
	// (get) Token: 0x06000071 RID: 113 RVA: 0x00003920 File Offset: 0x00001B20
	internal static Bitmap Bitmap_38
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze4", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700003B RID: 59
	// (get) Token: 0x06000072 RID: 114 RVA: 0x00003950 File Offset: 0x00001B50
	internal static Bitmap Bitmap_39
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("getze5", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700003C RID: 60
	// (get) Token: 0x06000073 RID: 115 RVA: 0x00003980 File Offset: 0x00001B80
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_5
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("GPro", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700003D RID: 61
	// (get) Token: 0x06000074 RID: 116 RVA: 0x000039A4 File Offset: 0x00001BA4
	internal static Bitmap Bitmap_40
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Hacking Youtuber Pack - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700003E RID: 62
	// (get) Token: 0x06000075 RID: 117 RVA: 0x000039D4 File Offset: 0x00001BD4
	internal static Bitmap Bitmap_41
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Hacking Youtuber Pack - Copie1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700003F RID: 63
	// (get) Token: 0x06000076 RID: 118 RVA: 0x00003A04 File Offset: 0x00001C04
	internal static Bitmap Bitmap_42
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("hawk-bird-animal-shape", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000040 RID: 64
	// (get) Token: 0x06000077 RID: 119 RVA: 0x00003A34 File Offset: 0x00001C34
	internal static Bitmap Bitmap_43
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("home", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000041 RID: 65
	// (get) Token: 0x06000078 RID: 120 RVA: 0x00003A64 File Offset: 0x00001C64
	internal static Bitmap Bitmap_44
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("home-icon-silhouette", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000042 RID: 66
	// (get) Token: 0x06000079 RID: 121 RVA: 0x00003A94 File Offset: 0x00001C94
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_6
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("hp", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000043 RID: 67
	// (get) Token: 0x0600007A RID: 122 RVA: 0x00003AB8 File Offset: 0x00001CB8
	internal static Bitmap Bitmap_45
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("image-03", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000044 RID: 68
	// (get) Token: 0x0600007B RID: 123 RVA: 0x00003AE8 File Offset: 0x00001CE8
	internal static Bitmap Bitmap_46
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("JEJEa_jr_400x400", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000045 RID: 69
	// (get) Token: 0x0600007C RID: 124 RVA: 0x00003B18 File Offset: 0x00001D18
	internal static Bitmap Bitmap_47
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000046 RID: 70
	// (get) Token: 0x0600007D RID: 125 RVA: 0x00003B48 File Offset: 0x00001D48
	internal static Bitmap Bitmap_48
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000047 RID: 71
	// (get) Token: 0x0600007E RID: 126 RVA: 0x00003B78 File Offset: 0x00001D78
	internal static Bitmap Bitmap_49
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000048 RID: 72
	// (get) Token: 0x0600007F RID: 127 RVA: 0x00003BA8 File Offset: 0x00001DA8
	internal static Bitmap Bitmap_50
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000049 RID: 73
	// (get) Token: 0x06000080 RID: 128 RVA: 0x00003BD8 File Offset: 0x00001DD8
	internal static Bitmap Bitmap_51
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w4", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700004A RID: 74
	// (get) Token: 0x06000081 RID: 129 RVA: 0x00003C08 File Offset: 0x00001E08
	internal static Bitmap Bitmap_52
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("keyboard_key_w5", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700004B RID: 75
	// (get) Token: 0x06000082 RID: 130 RVA: 0x00003C38 File Offset: 0x00001E38
	internal static Bitmap Bitmap_53
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("kingsize", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700004C RID: 76
	// (get) Token: 0x06000083 RID: 131 RVA: 0x00003C68 File Offset: 0x00001E68
	internal static Bitmap Bitmap_54
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("lock", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700004D RID: 77
	// (get) Token: 0x06000084 RID: 132 RVA: 0x00003C98 File Offset: 0x00001E98
	internal static Bitmap Bitmap_55
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("lock1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700004E RID: 78
	// (get) Token: 0x06000085 RID: 133 RVA: 0x00003CC8 File Offset: 0x00001EC8
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_7
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("microsoft", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700004F RID: 79
	// (get) Token: 0x06000086 RID: 134 RVA: 0x00003CEC File Offset: 0x00001EEC
	internal static Bitmap Bitmap_56
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("minisize", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000050 RID: 80
	// (get) Token: 0x06000087 RID: 135 RVA: 0x00003D1C File Offset: 0x00001F1C
	internal static Bitmap Bitmap_57
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("minisize - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000051 RID: 81
	// (get) Token: 0x06000088 RID: 136 RVA: 0x00003D4C File Offset: 0x00001F4C
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_8
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("multiclick", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000052 RID: 82
	// (get) Token: 0x06000089 RID: 137 RVA: 0x00003D70 File Offset: 0x00001F70
	internal static Bitmap Bitmap_58
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("musica-searcher", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000053 RID: 83
	// (get) Token: 0x0600008A RID: 138 RVA: 0x00003DA0 File Offset: 0x00001FA0
	internal static Icon Icon_0
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("normal", Class5.cultureInfo_0));
			return (Icon)objectValue;
		}
	}

	// Token: 0x17000054 RID: 84
	// (get) Token: 0x0600008B RID: 139 RVA: 0x00003DD0 File Offset: 0x00001FD0
	internal static Bitmap Bitmap_59
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("NVwWYLY2_400x400", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000055 RID: 85
	// (get) Token: 0x0600008C RID: 140 RVA: 0x00003E00 File Offset: 0x00002000
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_9
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("oldmouse", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000056 RID: 86
	// (get) Token: 0x0600008D RID: 141 RVA: 0x00003E24 File Offset: 0x00002024
	internal static byte[] Byte_0
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("pddd", Class5.cultureInfo_0));
			return (byte[])objectValue;
		}
	}

	// Token: 0x17000057 RID: 87
	// (get) Token: 0x0600008E RID: 142 RVA: 0x00003E54 File Offset: 0x00002054
	internal static Icon Icon_1
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("pederaste", Class5.cultureInfo_0));
			return (Icon)objectValue;
		}
	}

	// Token: 0x17000058 RID: 88
	// (get) Token: 0x0600008F RID: 143 RVA: 0x00003E84 File Offset: 0x00002084
	internal static Bitmap Bitmap_60
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("pointer", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000059 RID: 89
	// (get) Token: 0x06000090 RID: 144 RVA: 0x00003EB4 File Offset: 0x000020B4
	internal static byte[] Byte_1
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("pointer1", Class5.cultureInfo_0));
			return (byte[])objectValue;
		}
	}

	// Token: 0x1700005A RID: 90
	// (get) Token: 0x06000091 RID: 145 RVA: 0x00003EE4 File Offset: 0x000020E4
	internal static Bitmap Bitmap_61
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("rOgupG5n_400x400", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700005B RID: 91
	// (get) Token: 0x06000092 RID: 146 RVA: 0x00003F14 File Offset: 0x00002114
	internal static Bitmap Bitmap_62
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Sans titre", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700005C RID: 92
	// (get) Token: 0x06000093 RID: 147 RVA: 0x00003F44 File Offset: 0x00002144
	internal static Bitmap Bitmap_63
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Sans titre1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700005D RID: 93
	// (get) Token: 0x06000094 RID: 148 RVA: 0x00003F74 File Offset: 0x00002174
	internal static Bitmap Bitmap_64
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("Sans titre2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700005E RID: 94
	// (get) Token: 0x06000095 RID: 149 RVA: 0x00003FA4 File Offset: 0x000021A4
	internal static Bitmap Bitmap_65
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("setings", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700005F RID: 95
	// (get) Token: 0x06000096 RID: 150 RVA: 0x00003FD4 File Offset: 0x000021D4
	internal static Bitmap Bitmap_66
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("setings - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000060 RID: 96
	// (get) Token: 0x06000097 RID: 151 RVA: 0x00004004 File Offset: 0x00002204
	internal static Bitmap Bitmap_67
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("setings - Copie1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000061 RID: 97
	// (get) Token: 0x06000098 RID: 152 RVA: 0x00004034 File Offset: 0x00002234
	internal static Bitmap Bitmap_68
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("setings1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000062 RID: 98
	// (get) Token: 0x06000099 RID: 153 RVA: 0x00004064 File Offset: 0x00002264
	internal static Bitmap Bitmap_69
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("setings2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000063 RID: 99
	// (get) Token: 0x0600009A RID: 154 RVA: 0x00004094 File Offset: 0x00002294
	internal static Bitmap Bitmap_70
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("settings-24-512", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000064 RID: 100
	// (get) Token: 0x0600009B RID: 155 RVA: 0x000040C4 File Offset: 0x000022C4
	internal static Bitmap Bitmap_71
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("settings-24-5121", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000065 RID: 101
	// (get) Token: 0x0600009C RID: 156 RVA: 0x000040F4 File Offset: 0x000022F4
	internal static Bitmap Bitmap_72
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("settings-24-5122", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000066 RID: 102
	// (get) Token: 0x0600009D RID: 157 RVA: 0x00004124 File Offset: 0x00002324
	internal static Bitmap Bitmap_73
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("settings-24-5123", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000067 RID: 103
	// (get) Token: 0x0600009E RID: 158 RVA: 0x00004154 File Offset: 0x00002354
	internal static Bitmap Bitmap_74
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("settings-24-5124", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000068 RID: 104
	// (get) Token: 0x0600009F RID: 159 RVA: 0x00004184 File Offset: 0x00002384
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_10
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("shaya", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000069 RID: 105
	// (get) Token: 0x060000A0 RID: 160 RVA: 0x000041A8 File Offset: 0x000023A8
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_11
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("Single__1_", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700006A RID: 106
	// (get) Token: 0x060000A1 RID: 161 RVA: 0x000041CC File Offset: 0x000023CC
	internal static Bitmap Bitmap_75
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("slcte", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700006B RID: 107
	// (get) Token: 0x060000A2 RID: 162 RVA: 0x000041FC File Offset: 0x000023FC
	internal static Bitmap Bitmap_76
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("slcte1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700006C RID: 108
	// (get) Token: 0x060000A3 RID: 163 RVA: 0x0000422C File Offset: 0x0000242C
	internal static Bitmap Bitmap_77
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("slcte2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700006D RID: 109
	// (get) Token: 0x060000A4 RID: 164 RVA: 0x0000425C File Offset: 0x0000245C
	internal static Bitmap Bitmap_78
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("slcte3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700006E RID: 110
	// (get) Token: 0x060000A5 RID: 165 RVA: 0x0000428C File Offset: 0x0000248C
	internal static Bitmap Bitmap_79
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("star", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700006F RID: 111
	// (get) Token: 0x060000A6 RID: 166 RVA: 0x000042BC File Offset: 0x000024BC
	internal static Bitmap Bitmap_80
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stick-man-295293_960_720", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000070 RID: 112
	// (get) Token: 0x060000A7 RID: 167 RVA: 0x000042EC File Offset: 0x000024EC
	internal static Bitmap Bitmap_81
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stick-man-295293_960_7201", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000071 RID: 113
	// (get) Token: 0x060000A8 RID: 168 RVA: 0x0000431C File Offset: 0x0000251C
	internal static Bitmap Bitmap_82
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stick-man-295293_960_7202", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000072 RID: 114
	// (get) Token: 0x060000A9 RID: 169 RVA: 0x0000434C File Offset: 0x0000254C
	internal static Bitmap Bitmap_83
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stick-man-295293_960_7203", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000073 RID: 115
	// (get) Token: 0x060000AA RID: 170 RVA: 0x0000437C File Offset: 0x0000257C
	internal static Bitmap Bitmap_84
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stick-man-295293_960_7204", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000074 RID: 116
	// (get) Token: 0x060000AB RID: 171 RVA: 0x000043AC File Offset: 0x000025AC
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_12
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("stimpy", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000075 RID: 117
	// (get) Token: 0x060000AC RID: 172 RVA: 0x000043D0 File Offset: 0x000025D0
	internal static Bitmap Bitmap_85
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stingson", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000076 RID: 118
	// (get) Token: 0x060000AD RID: 173 RVA: 0x00004400 File Offset: 0x00002600
	internal static Bitmap Bitmap_86
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stingson - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000077 RID: 119
	// (get) Token: 0x060000AE RID: 174 RVA: 0x00004430 File Offset: 0x00002630
	internal static Bitmap Bitmap_87
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stingson - Copie1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000078 RID: 120
	// (get) Token: 0x060000AF RID: 175 RVA: 0x00004460 File Offset: 0x00002660
	internal static Bitmap Bitmap_88
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stingson1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000079 RID: 121
	// (get) Token: 0x060000B0 RID: 176 RVA: 0x00004490 File Offset: 0x00002690
	internal static Bitmap Bitmap_89
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("stingson2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700007A RID: 122
	// (get) Token: 0x060000B1 RID: 177 RVA: 0x000044C0 File Offset: 0x000026C0
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_13
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("success", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700007B RID: 123
	// (get) Token: 0x060000B2 RID: 178 RVA: 0x000044E4 File Offset: 0x000026E4
	internal static Bitmap Bitmap_90
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("swrd", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700007C RID: 124
	// (get) Token: 0x060000B3 RID: 179 RVA: 0x00004514 File Offset: 0x00002714
	internal static Bitmap Bitmap_91
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("swrd1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700007D RID: 125
	// (get) Token: 0x060000B4 RID: 180 RVA: 0x00004544 File Offset: 0x00002744
	internal static Bitmap Bitmap_92
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("swrd2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700007E RID: 126
	// (get) Token: 0x060000B5 RID: 181 RVA: 0x00004574 File Offset: 0x00002774
	internal static Bitmap Bitmap_93
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("swrd3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700007F RID: 127
	// (get) Token: 0x060000B6 RID: 182 RVA: 0x000045A4 File Offset: 0x000027A4
	internal static Bitmap Bitmap_94
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("swrd4", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000080 RID: 128
	// (get) Token: 0x060000B7 RID: 183 RVA: 0x000045D4 File Offset: 0x000027D4
	internal static Bitmap Bitmap_95
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapof", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000081 RID: 129
	// (get) Token: 0x060000B8 RID: 184 RVA: 0x00004604 File Offset: 0x00002804
	internal static Bitmap Bitmap_96
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapof1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000082 RID: 130
	// (get) Token: 0x060000B9 RID: 185 RVA: 0x00004634 File Offset: 0x00002834
	internal static Bitmap Bitmap_97
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapof2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000083 RID: 131
	// (get) Token: 0x060000BA RID: 186 RVA: 0x00004664 File Offset: 0x00002864
	internal static Bitmap Bitmap_98
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapof3", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000084 RID: 132
	// (get) Token: 0x060000BB RID: 187 RVA: 0x00004694 File Offset: 0x00002894
	internal static Bitmap Bitmap_99
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapon", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000085 RID: 133
	// (get) Token: 0x060000BC RID: 188 RVA: 0x000046C4 File Offset: 0x000028C4
	internal static Bitmap Bitmap_100
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapon1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000086 RID: 134
	// (get) Token: 0x060000BD RID: 189 RVA: 0x000046F4 File Offset: 0x000028F4
	internal static Bitmap Bitmap_101
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("tapon2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000087 RID: 135
	// (get) Token: 0x060000BE RID: 190 RVA: 0x00004724 File Offset: 0x00002924
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_14
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("test", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000088 RID: 136
	// (get) Token: 0x060000BF RID: 191 RVA: 0x00004748 File Offset: 0x00002948
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_15
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("test2", Class5.cultureInfo_0);
		}
	}

	// Token: 0x17000089 RID: 137
	// (get) Token: 0x060000C0 RID: 192 RVA: 0x0000476C File Offset: 0x0000296C
	internal static Bitmap Bitmap_102
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("trident", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700008A RID: 138
	// (get) Token: 0x060000C1 RID: 193 RVA: 0x0000479C File Offset: 0x0000299C
	internal static Bitmap Bitmap_103
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("trident1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700008B RID: 139
	// (get) Token: 0x060000C2 RID: 194 RVA: 0x000047CC File Offset: 0x000029CC
	internal static UnmanagedMemoryStream UnmanagedMemoryStream_16
	{
		get
		{
			return Class5.ResourceManager_0.GetStream("Untitled", Class5.cultureInfo_0);
		}
	}

	// Token: 0x1700008C RID: 140
	// (get) Token: 0x060000C3 RID: 195 RVA: 0x000047F0 File Offset: 0x000029F0
	internal static Bitmap Bitmap_104
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("v75Wwar", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700008D RID: 141
	// (get) Token: 0x060000C4 RID: 196 RVA: 0x00004820 File Offset: 0x00002A20
	internal static Bitmap Bitmap_105
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("v75Wwar1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700008E RID: 142
	// (get) Token: 0x060000C5 RID: 197 RVA: 0x00004850 File Offset: 0x00002A50
	internal static Bitmap Bitmap_106
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("v75Wwar2", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x1700008F RID: 143
	// (get) Token: 0x060000C6 RID: 198 RVA: 0x00004880 File Offset: 0x00002A80
	internal static Bitmap Bitmap_107
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("warning", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000090 RID: 144
	// (get) Token: 0x060000C7 RID: 199 RVA: 0x000048B0 File Offset: 0x00002AB0
	internal static Bitmap Bitmap_108
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("weather-41-512", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000091 RID: 145
	// (get) Token: 0x060000C8 RID: 200 RVA: 0x000048E0 File Offset: 0x00002AE0
	internal static Bitmap Bitmap_109
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("weather-41-512 - Copie - Copie", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000092 RID: 146
	// (get) Token: 0x060000C9 RID: 201 RVA: 0x00004910 File Offset: 0x00002B10
	internal static Bitmap Bitmap_110
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("weather-41-512 - Copie - Copie1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000093 RID: 147
	// (get) Token: 0x060000CA RID: 202 RVA: 0x00004940 File Offset: 0x00002B40
	internal static Bitmap Bitmap_111
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("weather-41-5121", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000094 RID: 148
	// (get) Token: 0x060000CB RID: 203 RVA: 0x00004970 File Offset: 0x00002B70
	internal static Bitmap Bitmap_112
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("weather-41-5122", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000095 RID: 149
	// (get) Token: 0x060000CC RID: 204 RVA: 0x000049A0 File Offset: 0x00002BA0
	internal static Bitmap Bitmap_113
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("witch", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000096 RID: 150
	// (get) Token: 0x060000CD RID: 205 RVA: 0x000049D0 File Offset: 0x00002BD0
	internal static Bitmap Bitmap_114
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("witch (1)", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x17000097 RID: 151
	// (get) Token: 0x060000CE RID: 206 RVA: 0x00004A00 File Offset: 0x00002C00
	internal static Bitmap Bitmap_115
	{
		get
		{
			object objectValue = RuntimeHelpers.GetObjectValue(Class5.ResourceManager_0.GetObject("witch1", Class5.cultureInfo_0));
			return (Bitmap)objectValue;
		}
	}

	// Token: 0x0400000E RID: 14
	private static ResourceManager resourceManager_0;

	// Token: 0x0400000F RID: 15
	private static CultureInfo cultureInfo_0;
}
