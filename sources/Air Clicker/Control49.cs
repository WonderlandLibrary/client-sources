using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000C2 RID: 194
internal class Control49 : ContainerControl
{
	// Token: 0x060008DA RID: 2266 RVA: 0x00029C0C File Offset: 0x00027E0C
	public Control49()
	{
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		base.SetStyle(ControlStyles.UserPaint, true);
		this.BackColor = Color.FromArgb(39, 51, 63);
		base.Size = new Size(187, 117);
		base.Padding = new Padding(5, 5, 5, 5);
		this.DoubleBuffered = true;
	}

	// Token: 0x060008DB RID: 2267 RVA: 0x00029C6C File Offset: 0x00027E6C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		this.graphicsPath_0 = new GraphicsPath();
		GraphicsPath graphicsPath = this.graphicsPath_0;
		graphicsPath.AddArc(0, 0, 10, 10, 180f, 90f);
		checked
		{
			graphicsPath.AddArc(base.Width - 11, 0, 10, 10, -90f, 90f);
			graphicsPath.AddArc(base.Width - 11, base.Height - 11, 10, 10, 0f, 90f);
			graphicsPath.AddArc(0, base.Height - 11, 10, 10, 90f, 90f);
			graphicsPath.CloseAllFigures();
		}
	}

	// Token: 0x060008DC RID: 2268 RVA: 0x00029D14 File Offset: 0x00027F14
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.Clear(Color.FromArgb(32, 41, 50));
		graphics.FillPath(new SolidBrush(Color.FromArgb(39, 51, 63)), this.graphicsPath_0);
		graphics.DrawPath(new Pen(Color.FromArgb(39, 51, 63)), this.graphicsPath_0);
		graphics.Dispose();
		NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
		{
			bitmap.Clone(),
			0,
			0
		}, null, null, null, true);
		bitmap.Dispose();
	}

	// Token: 0x04000437 RID: 1079
	private GraphicsPath graphicsPath_0;
}
