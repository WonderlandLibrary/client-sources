using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	[DefaultEvent("Scroll")]
	internal class FlatTrackBar : Control
	{
		private int W;

		private int H;

		private int Val;

		private bool Bool;

		private Rectangle Track;

		private Rectangle Knob;

		private FlatTrackBar._Style Style_;

		private int _Minimum;

		private int _Maximum;

		private int _Value;

		private bool _ShowValue;

		private Color BaseColor;

		private Color _TrackColor;

		private Color SliderColor;

		private Color _HatchColor;

		[Category("Colors")]
		public Color HatchColor
		{
			get
			{
				return this._HatchColor;
			}
			set
			{
				this._HatchColor = value;
			}
		}

		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				this._Maximum = value;
				if (value < this._Value)
				{
					this._Value = value;
				}
				if (value < this._Minimum)
				{
					this._Minimum = value;
				}
				base.Invalidate();
			}
		}

		public int Minimum
		{
			get
			{
				int num = 0;
				num = num;
				return num;
			}
			set
			{
				this._Minimum = value;
				if (value > this._Value)
				{
					this._Value = value;
				}
				if (value > this._Maximum)
				{
					this._Maximum = value;
				}
				base.Invalidate();
			}
		}

		public bool ShowValue
		{
			get
			{
				return this._ShowValue;
			}
			set
			{
				this._ShowValue = value;
			}
		}

		public FlatTrackBar._Style Style
		{
			get
			{
				return this.Style_;
			}
			set
			{
				this.Style_ = value;
			}
		}

		[Category("Colors")]
		public Color TrackColor
		{
			get
			{
				return this._TrackColor;
			}
			set
			{
				this._TrackColor = value;
			}
		}

		public int Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				if (value != this._Value)
				{
					if (value <= this._Maximum)
					{
						int num = this._Minimum;
					}
					this._Value = value;
					base.Invalidate();
					FlatTrackBar.ScrollEventHandler scrollEventHandler = this.Scroll;
					if (scrollEventHandler != null)
					{
						scrollEventHandler(this);
					}
				}
			}
		}

		public FlatTrackBar()
		{
			this._Maximum = 10;
			this._ShowValue = false;
			this.BaseColor = Color.FromArgb(25, 25, 25);
			this._TrackColor = Color.FromArgb(75, 0, 130);
			this.SliderColor = Color.FromArgb(25, 27, 29);
			this._HatchColor = Color.FromArgb(75, 0, 130);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Height = 18;
			this.BackColor = Color.FromArgb(60, 70, 73);
		}

		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Subtract)
			{
				if (this.Value != 0)
				{
					this.Value = checked(this.Value - 1);
					return;
				}
			}
			else if (e.KeyCode == Keys.Add && this.Value != this._Maximum)
			{
				this.Value = checked(this.Value + 1);
			}
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == System.Windows.Forms.MouseButtons.Left)
			{
				this.Val = checked((int)Math.Round((double)(checked(this._Value - this._Minimum)) / (double)(checked(this._Maximum - this._Minimum)) * (double)(checked(base.Width - 11))));
				this.Track = new Rectangle(this.Val, 0, 10, 20);
				this.Bool = this.Track.Contains(e.Location);
			}
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Bool && e.X > -1 && e.X < checked(base.Width + 1))
			{
				this.Value = checked(this._Minimum + checked((int)Math.Round((double)(checked(this._Maximum - this._Minimum)) * ((double)e.X / (double)base.Width))));
			}
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Bool = false;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Rectangle rectangle = new Rectangle(1, 6, checked(this.W - 2), 8);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath1 = new GraphicsPath();
			Graphics g = Helpers.G;
			if (!MyProject.Forms.Form1.LightTheme)
			{
				this.BackColor = Color.FromArgb(42, 42, 42);
				this.BaseColor = Color.FromArgb(25, 25, 25);
				this._TrackColor = Color.FromArgb(75, 0, 130);
				this.SliderColor = Color.FromArgb(25, 27, 29);
				this._HatchColor = Color.FromArgb(75, 0, 130);
			}
			else
			{
				this.BackColor = Color.FromArgb(255, 255, 255);
				this.BaseColor = Color.FromArgb(25, 25, 25);
				this._TrackColor = Color.FromArgb(75, 0, 130);
				this.SliderColor = Color.FromArgb(25, 27, 29);
				this._HatchColor = Color.FromArgb(75, 0, 130);
			}
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			this.Val = checked((int)Math.Round((double)(checked(this._Value - this._Minimum)) / (double)(checked(this._Maximum - this._Minimum)) * (double)(checked(this.W - 10))));
			this.Track = new Rectangle(this.Val, 0, 10, 20);
			this.Knob = new Rectangle(this.Val, 4, 11, 14);
			graphicsPath.AddRectangle(rectangle);
			g.SetClip(graphicsPath);
			g.FillRectangle(new SolidBrush(this.BaseColor), new Rectangle(0, 7, this.W, 8));
			g.FillRectangle(new SolidBrush(this._TrackColor), new Rectangle(0, 7, checked(this.Track.X + this.Track.Width), 8));
			g.ResetClip();
			HatchBrush hatchBrush = new HatchBrush(HatchStyle.Plaid, this.HatchColor, this._TrackColor);
			g.FillRectangle(hatchBrush, new Rectangle(-10, 7, checked(this.Track.X + this.Track.Width), 8));
			FlatTrackBar._Style style = this.Style;
			if (style == FlatTrackBar._Style.Slider)
			{
				graphicsPath1.AddRectangle(this.Track);
				g.FillPath(new SolidBrush(this.SliderColor), graphicsPath1);
			}
			else if (style == FlatTrackBar._Style.Knob)
			{
				graphicsPath1.AddEllipse(this.Knob);
				g.FillPath(new SolidBrush(this.SliderColor), graphicsPath1);
			}
			if (!MyProject.Forms.Form1.LightTheme)
			{
				if (this.ShowValue)
				{
					g.DrawString(Conversions.ToString(this.Value), new System.Drawing.Font("Tahoma", 8f, FontStyle.Bold), Brushes.Black, new Rectangle(1, 10, checked(this.W + 1), checked(this.H + 1)), new StringFormat()
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					g.DrawString(Conversions.ToString(this.Value), new System.Drawing.Font("Tahoma", 8f, FontStyle.Bold), Brushes.White, new Rectangle(1, 10, this.W, this.H), new StringFormat()
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
			}
			else if (this.ShowValue)
			{
				g.DrawString(Conversions.ToString(this.Value), new System.Drawing.Font("Tahoma", 8f, FontStyle.Bold), Brushes.Indigo, new Rectangle(1, 10, this.W, this.H), new StringFormat()
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		public event FlatTrackBar.ScrollEventHandler Scroll;

		[Flags]
		public enum _Style
		{
			Slider,
			Knob
		}

		public delegate void ScrollEventHandler(object sender);
	}
}