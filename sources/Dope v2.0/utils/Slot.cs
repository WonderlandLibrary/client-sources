using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dope.utils
{
    public class Slot
    {
        int[,] Slots = new int[,]{ {0,0}, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }};

        public void setKeyIndex(int Index, int Key)
        {
            Slots[Index,0] = Key;
        }

        public int GetKeyIndex(int Index)
        {
            return Slots[Index,0];
        }

        public bool GetBoolIndex(int Index)
        {
            return Convert.ToBoolean(Slots[Index, 1]);
        }

        public void SetBoolIndex(int Index, bool v)
        {
            Slots[Index, 1] = Convert.ToInt32(v);
        }
    }
}
