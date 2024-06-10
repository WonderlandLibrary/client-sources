using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Dope.Theme
{
    [DefaultEvent("Scroll")]
    class Slider : Control
    {
        private int Val;
        private bool HoverTrack;
        private Rectangle Track;
        private int FontH;

        public Slider()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
            DoubleBuffered = true;

            Height = 45;
        }

        protected override void OnMouseDown(MouseEventArgs e)
        {
            base.OnMouseDown(e);
            if (e.Button == MouseButtons.Left)
            {
                Val = Convert.ToInt32((float)(_Value - _Minimum) / (float)(_Maximum - _Minimum) * (float)(Width - 20));
                Track = new Rectangle(10, FontH - 2, Val, 7);
                Rectangle zone = new Rectangle(Track.Width - 10, FontH - 10, 20, 20);

                HoverTrack = zone.Contains(e.Location);
            }
            Invalidate();
        }

        protected override void OnMouseMove(MouseEventArgs e)
        {
            base.OnMouseMove(e);
            if (HoverTrack && e.X > -1 && e.X < (Width + 1))
            {
                Value = _Minimum + (int)((float)(_Maximum - _Minimum) * ((float)e.X / (float)Width));
            }
        }

        protected override void OnMouseUp(MouseEventArgs e)
        {
            base.OnMouseUp(e);
            HoverTrack = false;
            Invalidate();
        }


        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            if( Height < 45)
            {
                Height = 45;
            }
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            base.OnMouseLeave(e);
            HoverTrack = false;
            Invalidate();
        }

        public event ScrollEventHandler Scroll;
        public delegate void ScrollEventHandler(object sender);

        private int _Minimum = 0;
        public int Minimum
        {
            get
            {
                return _Minimum;
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
        }

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
                if (value < _Minimum)
                    _Minimum = value;
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

                if (value > _Maximum || value < _Minimum)
                {
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

        private int _div = 1;
        public int Diviser
        {
            get { return _div; }
            set
            {
                if (value == 0)
                {
                    _div = 1;
                }
                else
                {
                    _div = value;
                }
            }
        }

        public double RawValue()
        {
            return ((double)Value / Diviser);
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            Bitmap B = new Bitmap(Width, Height);
            Graphics G = Graphics.FromImage(B);
            G.SmoothingMode = SmoothingMode.HighQuality;
            G.PixelOffsetMode = PixelOffsetMode.HighQuality;
            G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;

            int W = Width - 20;

            ///Text
            G.DrawString(Text, Font, new SolidBrush(ForeColor), 10, 4);
            FontH = (int)G.MeasureString(Text, Font).Height + 10;

            Val = Convert.ToInt32((float)(_Value - _Minimum) / (float)(_Maximum - _Minimum) * (float)(W));
            Track = new Rectangle(10, FontH - 2, Val, 7);

            ///Slider
            G.FillRectangle(new SolidBrush(Color.FromArgb(255, 150,150,150)), new Rectangle(10, FontH, W, 3));
            G.FillRectangle(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), new Rectangle(10, FontH, Track.Width, 3));
            ///Kob
            if(HoverTrack)
            {
                G.FillEllipse(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), 
                    new Rectangle(Track.Width, FontH - 6, 14, 14));
            }
            else
            {
                G.FillEllipse(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), 
                    new Rectangle(Track.Width, FontH - 4, 10, 10));
            }

            ///Show Value
            string TV = ((double)Value / _div).ToString();
            int TVL = (int)G.MeasureString(TV, Font).Width;
            G.DrawString(TV, Font, new SolidBrush(ForeColor), Width - TVL - 10, 4);

            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}