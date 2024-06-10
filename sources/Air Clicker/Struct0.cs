using System;
using System.Drawing;

// Token: 0x02000011 RID: 17
internal struct Struct0
{
	// Token: 0x170000C9 RID: 201
	// (get) Token: 0x060001FF RID: 511 RVA: 0x0000876C File Offset: 0x0000696C
	public string String_0
	{
		get
		{
			return this.string_0;
		}
	}

	// Token: 0x170000CA RID: 202
	// (get) Token: 0x06000200 RID: 512 RVA: 0x00008784 File Offset: 0x00006984
	// (set) Token: 0x06000201 RID: 513 RVA: 0x0000879C File Offset: 0x0000699C
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x170000CB RID: 203
	// (get) Token: 0x06000202 RID: 514 RVA: 0x000087A8 File Offset: 0x000069A8
	// (set) Token: 0x06000203 RID: 515 RVA: 0x0000880C File Offset: 0x00006A0C
	public string String_1
	{
		get
		{
			return "#" + this.color_0.R.ToString("X2", null) + this.color_0.G.ToString("X2", null) + this.color_0.B.ToString("X2", null);
		}
		set
		{
			try
			{
				this.color_0 = ColorTranslator.FromHtml(value);
			}
			catch (Exception ex)
			{
			}
		}
	}

	// Token: 0x06000204 RID: 516 RVA: 0x00008844 File Offset: 0x00006A44
	public Struct0(string string_1, Color color_1)
	{
		this = default(Struct0);
		this.string_0 = string_1;
		this.color_0 = color_1;
	}

	// Token: 0x0400007C RID: 124
	public string string_0;

	// Token: 0x0400007D RID: 125
	private Color color_0;
}
