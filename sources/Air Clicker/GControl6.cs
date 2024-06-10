using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000067 RID: 103
public class GControl6 : ContainerControl
{
	// Token: 0x06000506 RID: 1286 RVA: 0x00018E44 File Offset: 0x00017044
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Invalidate();
	}

	// Token: 0x06000507 RID: 1287 RVA: 0x00018E54 File Offset: 0x00017054
	public GControl6()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(240, 160);
	}

	// Token: 0x06000508 RID: 1288 RVA: 0x00018E90 File Offset: 0x00017090
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		base.OnPaint(e);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.Clear(this.BackColor);
		LinearGradientBrush brush = new LinearGradientBrush(rect, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 90f);
		graphics.FillRectangle(brush, rect);
		graphics.DrawRectangle(new Pen(Brushes.Black, 2f), rect);
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}
}
