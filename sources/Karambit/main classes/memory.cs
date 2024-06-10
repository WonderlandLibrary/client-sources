using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace skidderino
{
    public class memory
    {
		static VAMemory mem = new VAMemory("Minecraft.Windows");

        public static IntPtr FindPointer(IntPtr applicationBaseAddress, int baseAddressOffset, int[] offsets)
        {
            //Get the start address by adding the static offset to the base minecraft memory adress
            var pointer = IntPtr.Add(applicationBaseAddress, baseAddressOffset);

            //Read the first value
            pointer = (IntPtr)mem.ReadInt64(pointer);

            //Itterate through the offsets and add them to the values read to get the next one
            for (int x = 0; x < offsets.Count(); x++)
            {
                var offset = offsets[x];
                pointer = IntPtr.Add(pointer, offset);

                //Only set this if its not the last offset. the last offset is the value we are looking to manipulate
                if (x != offsets.Count() - 1)
                {
                    pointer = (IntPtr)mem.ReadInt64(pointer);
                }

            }
            return pointer;
        }

        public static double NextDouble(double minValue, double maxValue)
        {
            return new Random().NextDouble() * (maxValue - minValue) + minValue;
        }
    }
}
