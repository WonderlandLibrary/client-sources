using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200009F RID: 159
public class GClass6 : ContextMenuStrip
{
	// Token: 0x17000213 RID: 531
	// (get) Token: 0x06000754 RID: 1876 RVA: 0x00022B00 File Offset: 0x00020D00
	// (set) Token: 0x06000755 RID: 1877 RVA: 0x00022B18 File Offset: 0x00020D18
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

	// Token: 0x06000756 RID: 1878 RVA: 0x00022B24 File Offset: 0x00020D24
	public GClass6()
	{
		this.color_0 = Color.FromArgb(255, 255, 255);
		base.Renderer = new ToolStripProfessionalRenderer(new GClass5());
		base.ShowCheckMargin = false;
		base.ShowImageMargin = false;
		base.ForeColor = Color.FromArgb(255, 255, 255);
	}

	// Token: 0x06000757 RID: 1879 RVA: 0x00022B8C File Offset: 0x00020D8C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		base.OnPaint(e);
	}

	// Token: 0x04000375 RID: 885
	private Color color_0;
}
