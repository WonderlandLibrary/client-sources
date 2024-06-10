using System;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x0200000E RID: 14
	internal class HexButton : Control
	{
		// Token: 0x06000041 RID: 65 RVA: 0x00002415 File Offset: 0x00000615
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000042 RID: 66 RVA: 0x0000242B File Offset: 0x0000062B
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000043 RID: 67 RVA: 0x00002441 File Offset: 0x00000641
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00002457 File Offset: 0x00000657
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000045 RID: 69 RVA: 0x0000246D File Offset: 0x0000066D
		public HexButton()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(90, 30);
		}

		// Token: 0x06000046 RID: 70 RVA: 0x00003064 File Offset: 0x00001264
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(30, 33, 40));
			checked
			{
				graphics.DrawPath(new Pen(Color.FromArgb(236, 95, 75)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
				graphics.FillPath(new SolidBrush(Color.FromArgb(236, 95, 75)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
				MouseState state = this._State;
				if (state != MouseState.Over)
				{
					if (state == MouseState.Down)
					{
						graphics.DrawPath(new Pen(Color.FromArgb(25, Color.Black)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
						graphics.FillPath(new SolidBrush(Color.FromArgb(25, Color.Black)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
					}
				}
				else
				{
					graphics.DrawPath(new Pen(Color.FromArgb(20, Color.White)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
					graphics.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), Functions.RoundRec(new Rectangle(0, 0, base.Width - 1, base.Height - 1), 4));
				}
				StringFormat stringFormat = new StringFormat();
				stringFormat.Alignment = StringAlignment.Center;
				stringFormat.LineAlignment = StringAlignment.Center;
				graphics.DrawString(this.Text, new Font("Segoe UI", 9f), Brushes.White, new RectangleF(0f, 0f, (float)(base.Width - 1), (float)(base.Height - 1)), stringFormat);
			}
		}

		// Token: 0x04000023 RID: 35
		private MouseState _State;
	}
}
