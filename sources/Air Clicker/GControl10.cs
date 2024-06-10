using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x0200006B RID: 107
public class GControl10 : Control
{
	// Token: 0x17000181 RID: 385
	// (get) Token: 0x0600051A RID: 1306 RVA: 0x00019550 File Offset: 0x00017750
	// (set) Token: 0x0600051B RID: 1307 RVA: 0x00019558 File Offset: 0x00017758
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

	// Token: 0x0600051C RID: 1308 RVA: 0x00019568 File Offset: 0x00017768
	public GControl10()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(80, 25);
	}

	// Token: 0x0600051D RID: 1309 RVA: 0x000195A0 File Offset: 0x000177A0
	protected virtual void OnClick(EventArgs e)
	{
		if (this.Boolean_0)
		{
			this.Boolean_0 = false;
		}
		else
		{
			this.Boolean_0 = true;
		}
		base.OnClick(e);
	}

	// Token: 0x0600051E RID: 1310 RVA: 0x000195C4 File Offset: 0x000177C4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle_ = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.FillPath(new SolidBrush(Color.FromArgb(41, 41, 41)), Class22.smethod_0(rectangle_, 5));
			graphics.DrawPath(new Pen(Color.FromArgb(0, 0, 0), 2f), Class22.smethod_0(rectangle_, 5));
			if (this.Boolean_0)
			{
				graphics.FillPath(new SolidBrush(Color.FromArgb(128, 128, 128)), Class22.smethod_0(new Rectangle(3, 3, (int)Math.Round(unchecked((double)base.Width / 2.0 - 3.0)), base.Height - 7), 3));
				graphics.DrawString("ON", new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(0, 0, 0)), new Rectangle(2, 3, (int)Math.Round(unchecked((double)base.Width / 2.0 - 1.0)), base.Height - 5), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			else
			{
				graphics.FillPath(new SolidBrush(Color.FromArgb(128, 128, 128)), Class22.smethod_0(new Rectangle((int)Math.Round(unchecked((double)base.Width / 2.0 - 2.0)), 3, (int)Math.Round(unchecked((double)base.Width / 2.0 - 2.0)), base.Height - 7), 3));
				graphics.DrawString("OFF", new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(0, 0, 0)), new Rectangle((int)Math.Round(unchecked((double)base.Width / 2.0 - 2.0)), 3, (int)Math.Round(unchecked((double)base.Width / 2.0 - 1.0)), base.Height - 5), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002AB RID: 683
	private bool bool_0;
}
