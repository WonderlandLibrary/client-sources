using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AeroMC10
{
    public static class IntPtrExtensions
    {
        public static String AsHex(this IntPtr ptr)
        {
            var yourNumber = ptr.ToInt64();
            string hexNumber = string.Format("0x{0:X}", yourNumber);
            return hexNumber;
        }
    }
}
