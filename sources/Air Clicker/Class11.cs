using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000032 RID: 50
internal class Class11 : ContextMenuStrip
{
	// Token: 0x06000301 RID: 769 RVA: 0x0000EEB0 File Offset: 0x0000D0B0
	public Class11()
	{
		base.Renderer = new ToolStripProfessionalRenderer(new Class12());
		base.ForeColor = Color.FromArgb(235, 235, 235);
	}

	// Token: 0x06000302 RID: 770 RVA: 0x0000EEE4 File Offset: 0x0000D0E4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		base.OnPaint(e);
	}
}
