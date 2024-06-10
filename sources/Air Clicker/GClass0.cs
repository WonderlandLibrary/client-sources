using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x02000073 RID: 115
public class GClass0 : ComboBox
{
	// Token: 0x1700018D RID: 397
	// (get) Token: 0x06000554 RID: 1364 RVA: 0x0001AE8C File Offset: 0x0001908C
	// (set) Token: 0x06000555 RID: 1365 RVA: 0x0001AEA4 File Offset: 0x000190A4
	private int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			try
			{
				this.SelectedIndex = value;
			}
			catch (Exception ex)
			{
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000556 RID: 1366 RVA: 0x0001AEE4 File Offset: 0x000190E4
	public void GClass0_DrawItem(object sender, DrawItemEventArgs e)
	{
		e.DrawBackground();
		LinearGradientBrush brush = new LinearGradientBrush(new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height), Color.FromArgb(36, 36, 36), Color.FromArgb(36, 36, 36), 90f);
		LinearGradientBrush brush2 = new LinearGradientBrush(new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height), Color.FromArgb(36, 36, 36), Color.FromArgb(36, 36, 36), 90f);
		checked
		{
			try
			{
				if ((e.State & DrawItemState.Selected) != DrawItemState.Selected)
				{
					e.Graphics.FillPath(brush2, Class22.smethod_0(new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height), 2));
					e.Graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height), 2));
				}
				else
				{
					e.Graphics.FillPath(brush, Class22.smethod_0(new Rectangle(e.Bounds.X + 3, e.Bounds.Y, e.Bounds.Width - 7, e.Bounds.Height), 2));
					e.Graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(new Rectangle(e.Bounds.X + 3, e.Bounds.Y, e.Bounds.Width - 7, e.Bounds.Height), 2));
				}
				e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, new SolidBrush(Color.FromArgb(245, 245, 245)), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			catch (Exception ex)
			{
			}
		}
	}

	// Token: 0x06000557 RID: 1367 RVA: 0x0001B1E4 File Offset: 0x000193E4
	public GClass0()
	{
		base.DrawItem += this.GClass0_DrawItem;
		this.int_0 = 0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.DrawMode = DrawMode.OwnerDrawFixed;
		this.BackColor = Color.Transparent;
		base.DropDownStyle = ComboBoxStyle.DropDownList;
		this.Int32_0 = 0;
		base.ItemHeight = 25;
		this.DoubleBuffered = true;
		base.Width = 200;
	}

	// Token: 0x06000558 RID: 1368 RVA: 0x0001B258 File Offset: 0x00019458
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 20;
	}

	// Token: 0x06000559 RID: 1369 RVA: 0x0001B26C File Offset: 0x0001946C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			LinearGradientBrush brush = new LinearGradientBrush(rectangle, Color.FromArgb(36, 36, 36), Color.FromArgb(36, 36, 36), 90f);
			graphics.FillPath(brush, Class22.smethod_0(rectangle, 3));
			graphics.DrawPath(new Pen(Brushes.Black, 2f), Class22.smethod_0(rectangle, 3));
			graphics.SetClip(Class22.smethod_0(rectangle, 3));
			graphics.FillPath(brush, Class22.smethod_0(rectangle, 3));
			graphics.DrawPath(new Pen(Brushes.Black, 2f), Class22.smethod_0(rectangle, 3));
			graphics.ResetClip();
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 10, base.Width - 22, 10);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 11, base.Width - 22, 11);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 15, base.Width - 22, 15);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 16, base.Width - 22, 16);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 20, base.Width - 22, 20);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), base.Width - 9, 21, base.Width - 22, 21);
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), new Point(base.Width - 29, 7), new Point(base.Width - 29, base.Height - 7));
			graphics.DrawLine(new Pen(Color.FromArgb(245, 245, 245)), new Point(base.Width - 30, 7), new Point(base.Width - 30, base.Height - 7));
			try
			{
				graphics.DrawString(this.Text, new Font("Segoe UI", 11f, FontStyle.Bold), new SolidBrush(Color.FromArgb(245, 245, 245)), rectangle, new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			catch (Exception ex)
			{
			}
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x040002B8 RID: 696
	private int int_0;
}
