using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatMini : Control
	{
		private MouseState State;

		private int x;

		private Color _BaseColor;

		private Color _TextColor;

		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		[Category("Colors")]
		public Color TextColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		public FlatMini()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			base.Size = new System.Drawing.Size(18, 18);
			this.Anchor = AnchorStyles.Top | AnchorStyles.Right;
			this.Font = new System.Drawing.Font("Marlett", 12f);
		}

		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			FormWindowState windowState = base.FindForm().WindowState;
			if (windowState == FormWindowState.Normal)
			{
				base.FindForm().WindowState = FormWindowState.Minimized;
				return;
			}
			if (windowState != FormWindowState.Maximized)
			{
				return;
			}
			base.FindForm().WindowState = FormWindowState.Minimized;
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphic = Graphics.FromImage(bitmap);
			Rectangle rectangle = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphic1 = graphic;
			graphic1.SmoothingMode = SmoothingMode.HighQuality;
			graphic1.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphic1.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphic1.Clear(this.BackColor);
			graphic1.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
			graphic1.DrawString("0", this.Font, new SolidBrush(this.TextColor), new Rectangle(2, 1, base.Width, base.Height), Helpers.CenterSF);
			MouseState state = this.State;
			if (state == MouseState.Over)
			{
				graphic1.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.White)), rectangle);
			}
			else if (state == MouseState.Down)
			{
				graphic1.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.Black)), rectangle);
			}
			graphic1 = null;
			base.OnPaint(e);
			graphic.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new System.Drawing.Size(18, 18);
		}
	}
}