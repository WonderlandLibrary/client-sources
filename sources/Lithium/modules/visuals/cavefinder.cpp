#include "..\..\klynt.h"

static double defaultcavefinder = 16;
static double newcavefinder = 1879048192;

namespace v
{
    namespace modules
    {
        namespace cavefinder
        {
            void enable() {
                double buffer;
                for (size_t x = 0; x < v::modules::cavefinder::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::cavefinder::locations[x], &buffer, sizeof(double), nullptr)) {
                        if (buffer >= 16 && buffer <= 1879048192) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::cavefinder::locations[x], &newcavefinder, sizeof(double), nullptr);
                        }
                        else {
                            v::modules::cavefinder::locations.erase(v::modules::cavefinder::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::cavefinder::locations.erase(v::modules::cavefinder::locations.begin() + x);
                        x = 0;
                    }
                }


            }

            void disable() {
                double buffer;
                for (size_t x = 0; x < v::modules::cavefinder::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::cavefinder::locations[x], &buffer, sizeof(double), nullptr)) {
                        if (buffer >= 16 && buffer <= 1879048192) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::cavefinder::locations[x], &defaultcavefinder, sizeof(double), nullptr);
                        }
                        else {
                            v::modules::cavefinder::locations.erase(v::modules::cavefinder::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::cavefinder::locations.erase(v::modules::cavefinder::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::cavefinder::enabled) {
                        v::modules::cavefinder::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::cavefinder::used) {
                            disable();
                            v::modules::cavefinder::used = false;
                        }

                    }

                    if (v::modules::cavefinder::enabled) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                    }
                    else {
                        std::this_thread::sleep_for(std::chrono::milliseconds(50));
                    }

                }

            }
        }
    }
}