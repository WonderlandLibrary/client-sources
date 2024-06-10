using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x0200009C RID: 156
public class GClass5 : ProfessionalColorTable
{
	// Token: 0x0600070F RID: 1807 RVA: 0x00021B60 File Offset: 0x0001FD60
	public GClass5()
	{
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.FromArgb(47, 47, 47);
	}

	// Token: 0x170001F1 RID: 497
	// (get) Token: 0x06000710 RID: 1808 RVA: 0x00021B9C File Offset: 0x0001FD9C
	// (set) Token: 0x06000711 RID: 1809 RVA: 0x00021BB4 File Offset: 0x0001FDB4
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x170001F2 RID: 498
	// (get) Token: 0x06000712 RID: 1810 RVA: 0x00021BC0 File Offset: 0x0001FDC0
	// (set) Token: 0x06000713 RID: 1811 RVA: 0x00021BD8 File Offset: 0x0001FDD8
	[Category("Colours")]
	public Color Color_1
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x170001F3 RID: 499
	// (get) Token: 0x06000714 RID: 1812 RVA: 0x00021BE4 File Offset: 0x0001FDE4
	// (set) Token: 0x06000715 RID: 1813 RVA: 0x00021BFC File Offset: 0x0001FDFC
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x170001F4 RID: 500
	// (get) Token: 0x06000716 RID: 1814 RVA: 0x00021C08 File Offset: 0x0001FE08
	public virtual Color ButtonSelectedBorder
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001F5 RID: 501
	// (get) Token: 0x06000717 RID: 1815 RVA: 0x00021C20 File Offset: 0x0001FE20
	public virtual Color CheckBackground
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001F6 RID: 502
	// (get) Token: 0x06000718 RID: 1816 RVA: 0x00021C38 File Offset: 0x0001FE38
	public virtual Color CheckPressedBackground
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001F7 RID: 503
	// (get) Token: 0x06000719 RID: 1817 RVA: 0x00021C50 File Offset: 0x0001FE50
	public virtual Color CheckSelectedBackground
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001F8 RID: 504
	// (get) Token: 0x0600071A RID: 1818 RVA: 0x00021C68 File Offset: 0x0001FE68
	public virtual Color ImageMarginGradientBegin
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001F9 RID: 505
	// (get) Token: 0x0600071B RID: 1819 RVA: 0x00021C80 File Offset: 0x0001FE80
	public virtual Color ImageMarginGradientEnd
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001FA RID: 506
	// (get) Token: 0x0600071C RID: 1820 RVA: 0x00021C98 File Offset: 0x0001FE98
	public virtual Color ImageMarginGradientMiddle
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001FB RID: 507
	// (get) Token: 0x0600071D RID: 1821 RVA: 0x00021CB0 File Offset: 0x0001FEB0
	public virtual Color MenuBorder
	{
		get
		{
			return this.color_1;
		}
	}

	// Token: 0x170001FC RID: 508
	// (get) Token: 0x0600071E RID: 1822 RVA: 0x00021CC8 File Offset: 0x0001FEC8
	public virtual Color MenuItemBorder
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x170001FD RID: 509
	// (get) Token: 0x0600071F RID: 1823 RVA: 0x00021CE0 File Offset: 0x0001FEE0
	public virtual Color MenuItemSelected
	{
		get
		{
			return this.color_2;
		}
	}

	// Token: 0x170001FE RID: 510
	// (get) Token: 0x06000720 RID: 1824 RVA: 0x00021CF8 File Offset: 0x0001FEF8
	public virtual Color SeparatorDark
	{
		get
		{
			return this.color_1;
		}
	}

	// Token: 0x170001FF RID: 511
	// (get) Token: 0x06000721 RID: 1825 RVA: 0x00021D10 File Offset: 0x0001FF10
	public virtual Color ToolStripDropDownBackground
	{
		get
		{
			return this.color_0;
		}
	}

	// Token: 0x04000363 RID: 867
	private Color color_0;

	// Token: 0x04000364 RID: 868
	private Color color_1;

	// Token: 0x04000365 RID: 869
	private Color color_2;
}
