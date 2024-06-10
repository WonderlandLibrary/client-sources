using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FlatUI {

    // All of the theme folder was taken from a now deleted repo from years ago.

    [DefaultEvent("Scroll")]
	public class FlatTrackBar : Control
	{
		private int W;
		private int H;
		private int Val;
		private bool Bool;
		private bool filled;
		private bool floatText;
		private double FloatVal;
		private Rectangle Track;
		private int minvalue;


		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			base.OnMouseDown(e);
			Value = minvalue + Convert.ToInt32((float)(_Maximum - minvalue) * ((float)e.X / (float)Width));

			if (e.Button == System.Windows.Forms.MouseButtons.Left || e.Button == System.Windows.Forms.MouseButtons.Right)
			{
				Val = Convert.ToInt32((float)(_Value - minvalue) / (float)(_Maximum - minvalue) * (float)(Width - 10));
				Value = minvalue + Convert.ToInt32((float)(_Maximum - minvalue) * ((float)e.X / (float)Width));
				Track = new Rectangle(Val, 0, 10, 40);

				Bool = Track.Contains(e.Location);
			}

			if (_Value > _Maximum)
			{
				_Value = _Maximum;
			}

			if (_Value < minvalue)
			{
				_Value = minvalue;
			}
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			base.OnMouseDown(e);
			if (Bool && e.X > -10 && e.X < (Width + 10))
			{
				Value = minvalue + Convert.ToInt32((float)(_Maximum - minvalue) * ((float)e.X / (float)Width));
			}

			if (e.Button == System.Windows.Forms.MouseButtons.Left || e.Button == System.Windows.Forms.MouseButtons.Right)
			{
				Val = Convert.ToInt32((float)(_Value - minvalue) / (float)(_Maximum - minvalue) * (float)(Width - 10));
				Value = minvalue + Convert.ToInt32((float)(_Maximum - minvalue) * ((float)e.X / (float)Width));
				Track = new Rectangle(Val, 0, 10, 40);
			}

			if (_Value > _Maximum)
			{
				_Value = _Maximum;
			}

			if (_Value < minvalue)
			{
				_Value = minvalue;
			}
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			Bool = false;
		}

		[Category("Colors")]
		public Color TrackColor
		{
			get { return _TrackColor; }
			set { _TrackColor = value; }
		}

		[Category("Colors")]
		public Color HatchColor
		{
			get { return _HatchColor; }
			set { _HatchColor = value; }
		}

		[Category("Colors")]
		public Color ColorScheme1
		{
			get { return scheme1; }
			set { scheme1 = value; }
		}

		[Category("Colors")]
		public Color ColorScheme2
		{
			get { return scheme2; }
			set { scheme2 = value; }
		}

		[Category("Misc")]
		public int Minimum
		{
			get { return minvalue; }
			set { minvalue = value; }
		}

		[Category("Misc")]
		public bool Full
		{
			get { return filled; }
			set { filled = value; }
		}

		[Category("Misc")]
		public bool Decimal
		{
			get { return floatText; }
			set { floatText = value; }
		}

		[Category("Misc")]
		public double FloatValue
		{
			get { return FloatVal; }
			set { FloatVal = value; }
		}

		public event ScrollEventHandler Scroll;
		public delegate void ScrollEventHandler(object sender);

		/*private int _Minimum;
		public int Minimum
		{
			get
			{
				int functionReturnValue = 0;
				return functionReturnValue;
				return functionReturnValue;
			}
			set
			{
				if (value < 0)
				{
				}

				_Minimum = value;

				if (value > _Value)
					_Value = value;
				if (value > _Maximum)
					_Maximum = value;
				Invalidate();
			}
		}*/

		private int _Maximum = 10;
		public int Maximum
		{
			get { return _Maximum; }
			set
			{
				if (value < 0)
				{
				}

				_Maximum = value;
				if (value < _Value)
					_Value = value;
				if (value < minvalue)
					minvalue = value;
				Invalidate();
			}
		}

		private int _Value;
		public int Value
		{
			get { return _Value; }
			set
			{
				if (value == _Value)
					return;

				if (value > Maximum || value < minvalue)
				{
					return;
				}

				_Value = value;
				Invalidate();
				if (Scroll != null)
				{
					Scroll(this);
				}
			}
		}

		private bool _ShowValue = false;
		public bool ShowValue
		{
			get { return _ShowValue; }
			set { _ShowValue = value; }
		}

		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Subtract)
			{
				if (Value == 0)
					return;
				Value -= 1;
			}
			else if (e.KeyCode == Keys.Add)
			{
				if (Value == _Maximum)
					return;
				Value += 1;
			}
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			Invalidate();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			Height = 12;
		}

		private int stop;
		private int backspace;

		public static Color scheme1 = Color.FromArgb(130, 96, 189);
		public static Color scheme2 = Color.FromArgb(130, 96, 189);
		public static Pen outline_color = new Pen(Color.FromArgb(scheme1.ToArgb()), 1); // outline
		private Color SliderColor = Color.FromArgb(scheme1.ToArgb()); // slider

		private Color BaseColor = Color.FromArgb(73, 74, 82);
		private Color _TrackColor = Color.FromArgb(100, 100, 100);
		private Color _HatchColor = Color.FromArgb(100, 100, 100);

		public FlatTrackBar()
		{
			SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
			DoubleBuffered = true;

			BackColor = Color.FromArgb(38, 38, 38);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Pen culr = new Pen(Color.FromArgb(scheme2.ToArgb()), 1);

			Bitmap B = new Bitmap(Width + 30, Height);
			Graphics G = Graphics.FromImage(B);
			W = Width;
			H = Height - 1;

			Rectangle Base = new Rectangle(5, 10, W, 80);
			GraphicsPath GP = new GraphicsPath();
			GraphicsPath GP2 = new GraphicsPath();

			var _with20 = G;
			_with20.SmoothingMode = SmoothingMode.HighQuality;
			_with20.PixelOffsetMode = PixelOffsetMode.HighQuality;
			_with20.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			_with20.Clear(BackColor);

			_with20.DrawRectangle(culr, 0, 0, W, H + 1);

			//-- Value
			Val = Convert.ToInt32((float)(_Value - minvalue) / (float)(_Maximum - minvalue) * (float)(W - 8));
			if (_Value < Maximum / 2)
				Track = new Rectangle(Val + 2, 2, 6, H - 3);
			else
				Track = new Rectangle(Val, 2, 6, H - 3);

			//-- Base
			GP.AddRectangle(Base);
			_with20.SetClip(GP);
			//_with20.FillRectangle(new SolidBrush(BaseColor), new Rectangle(2, 0, W - 5, H - 7));
			_with20.FillRectangle(new SolidBrush(_TrackColor), new Rectangle(0, 1, Track.X + Track.Width, 9));
			_with20.ResetClip();

			//-- Hatch Brush
			HatchBrush HB = new HatchBrush(HatchStyle.Plaid, HatchColor, _TrackColor);
			HatchBrush HB2 = new HatchBrush(HatchStyle.Plaid, BaseColor, BaseColor);
			if (filled)
				_with20.FillRectangle(HB, new Rectangle(2, 2, Track.X + Track.Width - 4, H - 3));
			else
				_with20.FillRectangle(HB, new Rectangle(2, 2, Track.X + Track.Width - 5, H - 3));

			GP2.AddRectangle(Track);
			_with20.FillPath(new SolidBrush(scheme1), GP2);

			//-- Show the value 
			if (ShowValue)
			{
				if (floatText)
				{
					string finalVal = "";
					double newr;

					double v = Minimum + (double)Value;
					double d = v / 100.0;
					newr = d;
					FloatVal = d;
					finalVal = d.ToString();

					_with20.DrawString(finalVal + Text, new Font("Segoe UI", 9), Brushes.White, new Rectangle(2, 2, Track.X + Track.Width, H - 3), new StringFormat
					{
						Alignment = StringAlignment.Far,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					if (Value <= Value / 2)
					{
						_with20.DrawString(Value.ToString() + Text, new Font("Segoe UI", 8), Brushes.White, new Rectangle(2, 3, Track.X + Track.Width + 7, H - 3), new StringFormat
						{
							Alignment = StringAlignment.Far,
							LineAlignment = StringAlignment.Center
						});
					}
					else
					{
						_with20.DrawString(Value.ToString() + Text, new Font("Segoe UI", 8), Brushes.White, new Rectangle(2, 2, Track.X + Track.Width - 2, H - 3), new StringFormat
						{
							Alignment = StringAlignment.Far,
							LineAlignment = StringAlignment.Center
						});
					}
				}
			}

			base.OnPaint(e);
			G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(B, 0, 0);
			B.Dispose();
		}
	}
}
