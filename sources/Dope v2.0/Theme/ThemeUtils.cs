using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;

namespace Dope.Theme
{
    public static class ThemeUtils
    {

        public enum MouseState : byte
        {
            None = 0,
            Over = 1,
            Down = 2,
            Block = 3
        }

        public static readonly StringFormat NearSF = new StringFormat
        {
            Alignment = StringAlignment.Near,
            LineAlignment = StringAlignment.Near
        };

        public static readonly StringFormat CenterSF = new StringFormat
        {
            Alignment = StringAlignment.Center,
            LineAlignment = StringAlignment.Center
        };

        public static readonly StringFormat NearCenterSF = new StringFormat
        {
            Alignment = StringAlignment.Near,
            LineAlignment = StringAlignment.Center
        };

        [DllImport("user32.dll", CharSet = CharSet.Unicode)]
        private static extern int ToUnicode(uint virtualKeyCode,uint scanCode,byte[] keyboardState,
            StringBuilder receivingBuffer,int bufferSize,uint flags);

        public static string GetCharsFromKeys(Keys keys, bool shift)
        {
            var buf = new StringBuilder(256);
            var keyboardState = new byte[256];
            if (shift)
            {
                keyboardState[(int)Keys.ShiftKey] = 0xff;
            }
            ToUnicode((uint)keys, 0, keyboardState, buf, 256, 0);
            return buf.ToString();
        }

        public static GraphicsPath RoundRec(Rectangle Rectangle, int Curve)
        {
            GraphicsPath P = new GraphicsPath();
            int ArcRectangleWidth = Curve * 2;
            P.AddArc(new Rectangle(Rectangle.X, Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), -180, 90);
            P.AddArc(new Rectangle(Rectangle.Width - ArcRectangleWidth + Rectangle.X, Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), -90, 90);
            P.AddArc(new Rectangle(Rectangle.Width - ArcRectangleWidth + Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), 0, 90);
            P.AddArc(new Rectangle(Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), 90, 90);
            P.AddLine(new Point(Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y), new Point(Rectangle.X, Curve + Rectangle.Y));
            return P;
        }

        public static GraphicsPath RoundRect(float x, float y, float w, float h, double r = 0.3,
            bool TL = true, bool TR = true, bool BR = true, bool BL = true)
        {
            GraphicsPath functionReturnValue = null;
            float d = Math.Min(w, h) * (float)r;
            float xw = x + w;
            float yh = y + h;
            functionReturnValue = new GraphicsPath();

            var _with1 = functionReturnValue;
            if (TL)
                _with1.AddArc(x, y, d, d, 180, 90);
            else
                _with1.AddLine(x, y, x, y);
            if (TR)
                _with1.AddArc(xw - d, y, d, d, 270, 90);
            else
                _with1.AddLine(xw, y, xw, y);
            if (BR)
                _with1.AddArc(xw - d, yh - d, d, d, 0, 90);
            else
                _with1.AddLine(xw, yh, xw, yh);
            if (BL)
                _with1.AddArc(x, yh - d, d, d, 90, 90);
            else
                _with1.AddLine(x, yh, x, yh);

            _with1.CloseFigure();
            return functionReturnValue;
        }

        public static GraphicsPath DrawCheck(Rectangle R)
        {
            GraphicsPath GP = new GraphicsPath();
            GP.AddLine(R.X + 3, R.Y + 6, R.X + (int)(R.Width/2), R.Y + R.Height - 4);
            GP.AddLine(R.X + (int)(R.Width / 2), R.Y + R.Height - 4, R.X + R.Width - 3, R.Y + 4);
            return GP;
        }

        public static GraphicsPath DrawCollapse(Rectangle R, bool v)
        {
            GraphicsPath GP = new GraphicsPath();
            int XP = (int)(R.Width / 3);
            int YP = (int)(R.Height / 4);

            if (v)
            {
                GP.AddLine(R.X + XP, R.Y + YP, R.Width - XP, (int)(R.Height / 2));
                GP.AddLine(R.Width - XP, (int)(R.Height / 2), R.X + XP, R.Height - YP);
            }
            else
            {
                GP.AddLine(R.Width - XP, R.Y + YP, R.X + XP, (int)(R.Height / 2));
                GP.AddLine(R.X + XP, (int)(R.Height / 2), R.Width - XP, R.Height - YP);
            }
            return GP;
        }
    }
}
