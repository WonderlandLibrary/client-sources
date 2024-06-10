using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000077 RID: 119
public class GControl19 : Control
{
	// Token: 0x17000192 RID: 402
	// (get) Token: 0x0600056C RID: 1388 RVA: 0x0001BF98 File Offset: 0x0001A198
	// (set) Token: 0x0600056D RID: 1389 RVA: 0x0001BFA0 File Offset: 0x0001A1A0
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

	// Token: 0x17000193 RID: 403
	// (get) Token: 0x0600056E RID: 1390 RVA: 0x0001BFB0 File Offset: 0x0001A1B0
	// (set) Token: 0x0600056F RID: 1391 RVA: 0x0001BFC8 File Offset: 0x0001A1C8
	public uint UInt32_0
	{
		get
		{
			return this.uint_0;
		}
		set
		{
			this.uint_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x06000570 RID: 1392 RVA: 0x0001BFD8 File Offset: 0x0001A1D8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x06000571 RID: 1393 RVA: 0x0001BFF0 File Offset: 0x0001A1F0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000572 RID: 1394 RVA: 0x0001C008 File Offset: 0x0001A208
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x06000573 RID: 1395 RVA: 0x0001C020 File Offset: 0x0001A220
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x06000574 RID: 1396 RVA: 0x0001C038 File Offset: 0x0001A238
	public GControl19()
	{
		this.bool_0 = true;
		this.uint_0 = 0U;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(160, 40);
		this.enum8_0 = Enum8.None;
	}

	// Token: 0x06000575 RID: 1397 RVA: 0x0001C090 File Offset: 0x0001A290
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle = new Rectangle(0, 10, base.Width - 1, base.Height - 11);
			Font font = new Font("Segoe UI", 11f, FontStyle.Bold);
			base.OnPaint(e);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
			graphics.Clear(this.BackColor);
			LinearGradientBrush brush = new LinearGradientBrush(rectangle, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), LinearGradientMode.Vertical);
			graphics.FillRectangle(brush, rectangle);
			graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
			switch (this.enum8_0)
			{
			case Enum8.None:
			{
				LinearGradientBrush brush2 = new LinearGradientBrush(rectangle, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush2, rectangle);
				graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			case Enum8.Over:
			{
				LinearGradientBrush brush3 = new LinearGradientBrush(rectangle, Color.FromArgb(41, 41, 41), Color.FromArgb(61, 61, 61), LinearGradientMode.Vertical);
				graphics.FillRectangle(brush3, rectangle);
				graphics.DrawRectangle(new Pen(Brushes.Black), rectangle);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			case Enum8.Down:
				graphics.TranslateTransform(1f, 1f);
				graphics.DrawString(this.Text, font, new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			if (this.Boolean_0)
			{
				uint uint32_ = this.UInt32_0;
				if (uint32_ > 9U)
				{
					if (uint32_ >= 10U && uint32_ <= 99U)
					{
						graphics.FillEllipse(new SolidBrush(Color.FromArgb(81, 81, 81)), new Rectangle(base.Width - 45, 0, 40, 20));
						graphics.DrawEllipse(new Pen(Brushes.Black), new Rectangle(base.Width - 45, 0, 40, 20));
						graphics.DrawString(this.UInt32_0.ToString(), font, new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(base.Width - 45, 0, 40, 20), new StringFormat
						{
							Alignment = StringAlignment.Center,
							LineAlignment = StringAlignment.Center
						});
					}
					else if (uint32_ >= 100U && uint32_ <= 999U)
					{
						graphics.FillEllipse(new SolidBrush(Color.FromArgb(81, 81, 81)), new Rectangle(base.Width - 65, 0, 60, 20));
						graphics.DrawEllipse(new Pen(Brushes.Black), new Rectangle(base.Width - 65, 0, 60, 20));
						graphics.DrawString(this.UInt32_0.ToString(), font, new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(base.Width - 65, 0, 60, 20), new StringFormat
						{
							Alignment = StringAlignment.Center,
							LineAlignment = StringAlignment.Center
						});
					}
				}
				else
				{
					graphics.FillEllipse(new SolidBrush(Color.FromArgb(81, 81, 81)), new Rectangle(base.Width - 25, 0, 20, 20));
					graphics.DrawEllipse(new Pen(Brushes.Black), new Rectangle(base.Width - 25, 0, 20, 20));
					graphics.DrawString(this.UInt32_0.ToString(), font, new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(base.Width - 25, 0, 20, 20), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002C0 RID: 704
	private Enum8 enum8_0;

	// Token: 0x040002C1 RID: 705
	private bool bool_0;

	// Token: 0x040002C2 RID: 706
	private uint uint_0;
}
