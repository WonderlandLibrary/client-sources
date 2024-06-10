using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200006A RID: 106
public class GControl9 : Control
{
	// Token: 0x17000180 RID: 384
	// (get) Token: 0x06000516 RID: 1302 RVA: 0x000193C4 File Offset: 0x000175C4
	// (set) Token: 0x06000517 RID: 1303 RVA: 0x000193CC File Offset: 0x000175CC
	public bool Boolean_0
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000518 RID: 1304 RVA: 0x000193DC File Offset: 0x000175DC
	public GControl9()
	{
		this.bool_0 = true;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(150, 30);
	}

	// Token: 0x06000519 RID: 1305 RVA: 0x0001941C File Offset: 0x0001761C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rectangle = checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1));
		base.OnPaint(e);
		graphics.Clear(this.BackColor);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle, 5));
		if (this.Boolean_0)
		{
			graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle, 5));
			graphics.DrawPath(new Pen(Color.FromArgb(21, 21, 21), 2f), Class22.smethod_0(rectangle, 5));
		}
		graphics.DrawString(this.Text, new Font("Segoe UI", 11f, FontStyle.Regular), new SolidBrush(Color.FromArgb(255, 255, 255)), rectangle, new StringFormat
		{
			Alignment = StringAlignment.Center,
			LineAlignment = StringAlignment.Center
		});
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x040002AA RID: 682
	private bool bool_0;
}
