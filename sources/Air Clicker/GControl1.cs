using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000061 RID: 97
public class GControl1 : ContainerControl
{
	// Token: 0x060004D5 RID: 1237 RVA: 0x000176A0 File Offset: 0x000158A0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (e.Button == MouseButtons.Left & new Rectangle(15, 10, checked(base.Width - 31), 45).Contains(e.Location))
		{
			this.bool_0 = true;
			this.point_0 = e.Location;
		}
	}

	// Token: 0x060004D6 RID: 1238 RVA: 0x000176FC File Offset: 0x000158FC
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.bool_0 = false;
	}

	// Token: 0x060004D7 RID: 1239 RVA: 0x0001770C File Offset: 0x0001590C
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if (this.bool_0)
		{
			base.Parent.Location = checked(new Point(Control.MousePosition.X - this.point_0.X, Control.MousePosition.Y - this.point_0.Y));
		}
	}

	// Token: 0x060004D8 RID: 1240 RVA: 0x0001776C File Offset: 0x0001596C
	public GControl1()
	{
		this.point_0 = new Point(0, 0);
		this.bool_0 = false;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
	}

	// Token: 0x060004D9 RID: 1241 RVA: 0x0001779C File Offset: 0x0001599C
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		base.ParentForm.FormBorderStyle = FormBorderStyle.None;
		base.ParentForm.TransparencyKey = Color.Fuchsia;
		this.Dock = DockStyle.Fill;
	}

	// Token: 0x060004DA RID: 1242 RVA: 0x000177C8 File Offset: 0x000159C8
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rect = new Rectangle(15, 10, base.Width - 31, base.Height - 21);
			Rectangle rect2 = new Rectangle(15, 10, base.Width - 31, 15);
			Rectangle rect3 = new Rectangle(15, base.Height - 25, base.Width - 31, 15);
			Rectangle rectangle = new Rectangle(0, 0, 15, base.Height - 1);
			Rectangle rectangle2 = new Rectangle(base.Width - 16, 0, 15, base.Height - 1);
			base.OnPaint(e);
			graphics.Clear(Color.Fuchsia);
			LinearGradientBrush brush = new LinearGradientBrush(rect, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 90f);
			graphics.FillRectangle(brush, rect);
			graphics.DrawRectangle(new Pen(Brushes.Black), rect);
			LinearGradientBrush brush2 = new LinearGradientBrush(rect2, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 90f);
			graphics.FillRectangle(brush2, rect2);
			graphics.DrawRectangle(new Pen(Brushes.Black), rect2);
			LinearGradientBrush brush3 = new LinearGradientBrush(rect3, Color.FromArgb(61, 61, 61), Color.FromArgb(41, 41, 41), 90f);
			graphics.FillRectangle(brush3, rect3);
			graphics.DrawRectangle(new Pen(Brushes.Black), rect3);
			LinearGradientBrush brush4 = new LinearGradientBrush(rectangle, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Horizontal);
			graphics.FillPath(brush4, Class22.smethod_0(rectangle, 7));
			graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(rectangle, 7));
			LinearGradientBrush brush5 = new LinearGradientBrush(rectangle2, Color.FromArgb(53, 53, 53), Color.FromArgb(62, 62, 62), LinearGradientMode.Horizontal);
			graphics.FillPath(brush5, Class22.smethod_0(rectangle2, 7));
			graphics.DrawPath(new Pen(Brushes.Black), Class22.smethod_0(rectangle2, 7));
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x04000293 RID: 659
	private Point point_0;

	// Token: 0x04000294 RID: 660
	private bool bool_0;
}
