using System;
using System.Drawing;
using System.Windows.Forms;

// Token: 0x02000026 RID: 38
internal class Control14 : Control
{
	// Token: 0x06000288 RID: 648 RVA: 0x0000BE00 File Offset: 0x0000A000
	public Control14()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.Height = 10;
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(55, 55, 55));
	}

	// Token: 0x06000289 RID: 649 RVA: 0x0000BE60 File Offset: 0x0000A060
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.DrawLine(this.pen_0, 0, 5, base.Width, 5);
		Class9.graphics_0.DrawLine(this.pen_1, 0, 6, base.Width, 6);
	}

	// Token: 0x04000116 RID: 278
	private Pen pen_0;

	// Token: 0x04000117 RID: 279
	private Pen pen_1;
}
