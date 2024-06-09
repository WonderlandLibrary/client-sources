using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatStickyButton : Control
	{
		private int W;

		private int H;

		private MouseState State;

		private bool _Rounded;

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

		private Rectangle Rect
		{
			get
			{
				return new Rectangle(base.Left, base.Top, base.Width, base.Height);
			}
		}

		[Category("Options")]
		public bool Rounded
		{
			get
			{
				return this._Rounded;
			}
			set
			{
				this._Rounded = value;
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

		public FlatStickyButton()
		{
			this.State = MouseState.None;
			this._Rounded = false;
			this._BaseColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new System.Drawing.Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new System.Drawing.Font("Segoe UI", 12f);
			this.Cursor = Cursors.Hand;
		}

		private bool[] GetConnectedSides()
		{
			IEnumerator enumerator = null;
			bool[] flagArray = new bool[4];
			try
			{
				enumerator = base.Parent.Controls.GetEnumerator();
				while (enumerator.MoveNext())
				{
					Control current = (Control)enumerator.Current;
					if (!(current is FlatStickyButton) || current == this | !this.Rect.IntersectsWith(this.Rect))
					{
						continue;
					}
					double num = Math.Atan2((double)(checked(base.Left - current.Left)), (double)(checked(base.Top - current.Top))) * 2 / 3.14159265358979;
					if ((double)(checked((long)Math.Round(num)) / (long)1) != num)
					{
						continue;
					}
					flagArray[checked((int)Math.Round(num + 1))] = true;
				}
			}
			finally
			{
				if (enumerator is IDisposable)
				{
					(enumerator as IDisposable).Dispose();
				}
			}
			return flagArray;
		}

		protected override void OnCreateControl()
		{
			base.OnCreateControl();
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

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			GraphicsPath graphicsPath = new GraphicsPath();
			bool[] connectedSides = this.GetConnectedSides();
			GraphicsPath graphicsPath1 = Helpers.RoundRect(0f, 0f, (float)this.W, (float)this.H, 0.3f, !(connectedSides[2] | connectedSides[1]), !(connectedSides[1] | connectedSides[0]), !(connectedSides[3] | connectedSides[0]), !(connectedSides[3] | connectedSides[2]));
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.State)
			{
				case MouseState.None:
				{
					if (!this.Rounded)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
					else
					{
						graphicsPath = graphicsPath1;
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
				}
				case MouseState.Over:
				{
					if (!this.Rounded)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
						g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
					else
					{
						graphicsPath = graphicsPath1;
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), graphicsPath);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
				}
				case MouseState.Down:
				{
					if (!this.Rounded)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
						g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
					else
					{
						graphicsPath = graphicsPath1;
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), graphicsPath);
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
						break;
					}
				}
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
		}
	}
}