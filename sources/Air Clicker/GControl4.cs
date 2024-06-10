using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000065 RID: 101
public class GControl4 : Control
{
	// Token: 0x1700017B RID: 379
	// (get) Token: 0x060004F6 RID: 1270 RVA: 0x000187A0 File Offset: 0x000169A0
	// (set) Token: 0x060004F7 RID: 1271 RVA: 0x000187A8 File Offset: 0x000169A8
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

	// Token: 0x060004F8 RID: 1272 RVA: 0x000187B8 File Offset: 0x000169B8
	public GControl4()
	{
		this.enum8_0 = Enum8.None;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.BackColor = Color.Transparent;
		this.DoubleBuffered = true;
		base.Size = new Size(180, 25);
	}

	// Token: 0x060004F9 RID: 1273 RVA: 0x000187F8 File Offset: 0x000169F8
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060004FA RID: 1274 RVA: 0x00018808 File Offset: 0x00016A08
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

	// Token: 0x060004FB RID: 1275 RVA: 0x0001882C File Offset: 0x00016A2C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum8_0 = Enum8.Down;
		base.Invalidate();
	}

	// Token: 0x060004FC RID: 1276 RVA: 0x00018844 File Offset: 0x00016A44
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004FD RID: 1277 RVA: 0x0001885C File Offset: 0x00016A5C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum8_0 = Enum8.Over;
		base.Invalidate();
	}

	// Token: 0x060004FE RID: 1278 RVA: 0x00018874 File Offset: 0x00016A74
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum8_0 = Enum8.None;
		base.Invalidate();
	}

	// Token: 0x060004FF RID: 1279 RVA: 0x0001888C File Offset: 0x00016A8C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(3, 3, 18, 18);
		Rectangle rect2 = new Rectangle(4, 4, 17, 17);
		Rectangle r = new Rectangle(6, 6, 15, 15);
		base.OnPaint(e);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		graphics.TextRenderingHint = TextRenderingHint.AntiAlias;
		graphics.Clear(this.BackColor);
		Enum8 @enum = this.enum8_0;
		if (@enum != Enum8.Over)
		{
			if (@enum == Enum8.Down)
			{
				rect.Inflate(-1, -1);
			}
		}
		else
		{
			rect.Inflate(1, 1);
		}
		graphics.DrawString(this.Text, new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(25, 4, base.Width, 16), new StringFormat
		{
			Alignment = StringAlignment.Near,
			LineAlignment = StringAlignment.Center
		});
		if (this.Boolean_0)
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(0, 0, 0)), rect);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(40, 37, 33)), rect2);
			graphics.DrawString("b", new Font("Marlett", 15f, FontStyle.Regular), new SolidBrush(Color.FromArgb(245, 245, 245)), r, new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
		}
		else
		{
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(0, 0, 0)), rect);
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(40, 37, 33)), rect2);
		}
		e.Graphics.DrawImage(bitmap, new Point(0, 0));
		graphics.Dispose();
		bitmap.Dispose();
	}

	// Token: 0x040002A3 RID: 675
	private Enum8 enum8_0;

	// Token: 0x040002A4 RID: 676
	private bool bool_0;
}
