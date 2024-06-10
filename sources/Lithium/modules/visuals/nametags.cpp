#include "..\..\klynt.h"

static float defaultnametags1 = 0.02666667f;
static float defaultnametags2 = -0.02666667f;

namespace v
{
    namespace modules
    {
        namespace nametags
        {
            void enable() {
                float newnametags1 = defaultnametags1 * v::modules::nametags::multiplier;
                float newnametags2 = -(defaultnametags1 * v::modules::nametags::multiplier);
                float buffer;
                for (size_t x = 0; x < v::modules::nametags::locations1.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations1[x], &buffer, sizeof(float), 0)) {
                        if (buffer <= 0.09f && buffer >= 0.02666667f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations1[x], &newnametags1, sizeof(float), 0);
                        }
                        else {
                            v::modules::nametags::locations1.erase(v::modules::nametags::locations1.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::nametags::locations1.erase(v::modules::nametags::locations1.begin() + x);
                        x = 0;
                    }
                }

                for (size_t x = 0; x < v::modules::nametags::locations2.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations2[x], &buffer, sizeof(float), 0)) {
                        if (buffer <= -0.02666667f && buffer >= -0.09f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations2[x], &newnametags2, sizeof(float), 0);
                        }
                        else {
                            v::modules::nametags::locations2.erase(v::modules::nametags::locations2.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::nametags::locations2.erase(v::modules::nametags::locations2.begin() + x);
                        x = 0;
                    }
                }

            }

            void disable() {
                float buffer;
                for (size_t x = 0; x < v::modules::nametags::locations1.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations1[x], &buffer, sizeof(float), 0)) {
                        if (buffer <= 0.09f && buffer >= 0.02666667f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations1[x], &defaultnametags1, sizeof(float), 0);
                        }
                        else {
                            v::modules::nametags::locations1.erase(v::modules::nametags::locations1.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::nametags::locations1.erase(v::modules::nametags::locations1.begin() + x);
                        x = 0;
                    }
                }

                for (size_t x = 0; x < v::modules::nametags::locations2.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations2[x], &buffer, sizeof(float), 0)) {
                        if (buffer <= -0.02666667f && buffer >= -0.09f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::nametags::locations2[x], &defaultnametags2, sizeof(float), 0);
                        }
                        else {
                            v::modules::nametags::locations2.erase(v::modules::nametags::locations2.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::nametags::locations2.erase(v::modules::nametags::locations2.begin() + x);
                        x = 0;
                    }
                }
            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::nametags::enabled) {
                        v::modules::nametags::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::nametags::used) {
                            disable();
                            v::modules::nametags::used = false;
                        }

                    }

                    if (v::modules::nametags::enabled) {
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