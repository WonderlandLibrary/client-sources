using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x020000C3 RID: 195
internal class Control50 : Control
{
	// Token: 0x060008DD RID: 2269 RVA: 0x00029DD4 File Offset: 0x00027FD4
	public Control50()
	{
		base.SetStyle(ControlStyles.ResizeRedraw, true);
		base.Size = (Size)new Point(120, 10);
	}

	// Token: 0x060008DE RID: 2270 RVA: 0x00029DFC File Offset: 0x00027FFC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		e.Graphics.DrawLine(new Pen(Color.FromArgb(45, 57, 68)), 0, 5, base.Width, 5);
	}
}
