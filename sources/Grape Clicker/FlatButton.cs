using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	internal class FlatButton : Control
	{
		private int W;

		private int H;

		private bool _Rounded;

		private MouseState State;

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

		public FlatButton()
		{
			this._Rounded = false;
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(255, 53, 53, 53);
			this._TextColor = Color.FromArgb(255, 255, 255, 255);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new System.Drawing.Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new System.Drawing.Font("Segoe UI", 12f);
			this.Cursor = Cursors.Hand;
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
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			GraphicsPath graphicsPath = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Rectangle rectangle1 = new Rectangle(1, 1, this.W, this.H);
			if (MyProject.Forms.Form1.LightTheme)
			{
				this._BaseColor = Color.FromArgb(255, 220, 220, 220);
				this._TextColor = Color.FromArgb(255, 75, 0, 130);
			}
			else
			{
				this._BaseColor = Color.FromArgb(255, 53, 53, 53);
				this._TextColor = Color.FromArgb(255, 255, 255, 255);
			}
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
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
					}
					else
					{
						graphicsPath = Helpers.RoundRec(rectangle, 6);
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
					}
				}
				case MouseState.Over:
				{
					if (!this.Rounded)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
						g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
					}
					else
					{
						graphicsPath = Helpers.RoundRec(rectangle, 6);
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), graphicsPath);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
					}
				}
				case MouseState.Down:
				{
					if (!this.Rounded)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
						g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
					}
					else
					{
						graphicsPath = Helpers.RoundRec(rectangle, 6);
						g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), graphicsPath);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
						else
						{
							g.DrawString(this.Text, this.Font, Brushes.Black, rectangle1, Helpers.CenterSF);
							g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
							break;
						}
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
	}
}