using System;
using System.Runtime.InteropServices;

// Token: 0x0200003B RID: 59
[ComVisible(true)]
[AttributeUsage(AttributeTargets.Assembly | AttributeTargets.Class | AttributeTargets.Struct | AttributeTargets.Enum | AttributeTargets.Method | AttributeTargets.Property | AttributeTargets.Field | AttributeTargets.Event | AttributeTargets.Interface | AttributeTargets.Parameter | AttributeTargets.Delegate, AllowMultiple = true, Inherited = false)]
internal sealed class Attribute0 : Attribute
{
	// Token: 0x17000023 RID: 35
	// (get) Token: 0x060001E4 RID: 484 RVA: 0x00002C6D File Offset: 0x00000E6D
	// (set) Token: 0x060001E5 RID: 485 RVA: 0x00002C75 File Offset: 0x00000E75
	public bool ApplyToMembers
	{
		get
		{
			return this.applyToMembers;
		}
		set
		{
			this.applyToMembers = value;
		}
	}

	// Token: 0x17000024 RID: 36
	// (get) Token: 0x060001E6 RID: 486 RVA: 0x00002C7E File Offset: 0x00000E7E
	// (set) Token: 0x060001E7 RID: 487 RVA: 0x00002C86 File Offset: 0x00000E86
	public bool Exclude
	{
		get
		{
			return this.exclude;
		}
		set
		{
			this.exclude = value;
		}
	}

	// Token: 0x17000025 RID: 37
	// (get) Token: 0x060001E8 RID: 488 RVA: 0x00002C8F File Offset: 0x00000E8F
	// (set) Token: 0x060001E9 RID: 489 RVA: 0x00002C97 File Offset: 0x00000E97
	public string Feature
	{
		get
		{
			return this.feature;
		}
		set
		{
			this.feature = value;
		}
	}

	// Token: 0x17000026 RID: 38
	// (get) Token: 0x060001EA RID: 490 RVA: 0x00002CA0 File Offset: 0x00000EA0
	// (set) Token: 0x060001EB RID: 491 RVA: 0x00002CA8 File Offset: 0x00000EA8
	public bool StripAfterObfuscation
	{
		get
		{
			return this.strip;
		}
		set
		{
			this.strip = value;
		}
	}

	// Token: 0x040003AD RID: 941
	private bool applyToMembers = true;

	// Token: 0x040003AE RID: 942
	private bool exclude = true;

	// Token: 0x040003AF RID: 943
	private bool strip = true;

	// Token: 0x040003B0 RID: 944
	private string feature = "";
}
