#include <vector>
#include <winnt.h>

template<class val>
std::vector<size_t> get_positions(std::vector<val> buffer, val value, size_t times_found) {
    std::vector<size_t> positions;
    size_t pos = std::distance(buffer.begin(), find(buffer.begin(), buffer.end(), value));
    size_t lastpos = pos;

    while (pos < buffer.size()) {
        if (positions.size() >= times_found)
            break;
        positions.push_back(pos);
        size_t calc = pos + 1;
        pos = std::distance(buffer.begin(), find(buffer.begin() + calc, buffer.end(), value));
        if (pos == lastpos)
            break;
    }
    return positions;
}

size_t min_address = 0x02000000;
size_t max_address = 0x08000000;

void calculator() {
    while (v::info::verified) {
        bool found = false;

        size_t address = 0x2000000;

        MEMORY_BASIC_INFORMATION mbi;

        while (NT_SUCCESS(ZwQueryVirtualMemory(, (void*)address, MemoryBasicInformation,
            &mbi, sizeof(mbi), nullptr))) {
            if (mbi.State == MEM_COMMIT && ((mbi.Protect == PAGE_EXECUTE_READWRITE) | (mbi.Protect == PAGE_READWRITE) |
                (mbi.Protect == PAGE_EXECUTE_WRITECOPY) |
                (mbi.Protect == PAGE_WRITECOPY))) {

                if (!found) {
                    min_address = address;
                    found = !found;
                }

            }

            if (address + mbi.RegionSize >= 0x10000000)
                break;
            else
                address += mbi.RegionSize;
        }
        max_address = address;
        std::this_thread::sleep_for(std::chrono::seconds(10));
    }

}

void scanner::thread() {
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&calculator,
        nullptr, 0, nullptr);
    while (v::info::verified) {
        DWORD status = 0;
        if (GetExitCodeProcess(v::info::minecraft_handle, &status)) {
            if (status != STILL_ACTIVE)
                exit(0);
        }

        for (size_t address = min_address; address <= max_address; address += 4096) {
            std::vector<double> memoryd(4096);
            std::vector<float> memoryf(4096);

            bool doubleread = NT_SUCCESS(ZwReadVirtualMemory(v::info::minecraft_handle, (void*)address, &memoryd[0], 4096,
                nullptr));
            bool floatread = false;

            if (doubleread)
                floatread = NT_SUCCESS(ZwReadVirtualMemory(v::info::minecraft_handle, (void*)address, &memoryf[0], 4096, nullptr));

            if (doubleread && floatread) {
                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 4.5)) {
                    for (const size_t location : get_positions(memoryd, 4.5, x)) {
                        v::modules::reach::unlimit.push_back(address + location * sizeof(double));

                        sort(v::modules::reach::unlimit.begin(), v::modules::reach::unlimit.end());
                        v::modules::reach::unlimit.erase(
                            unique(v::modules::reach::unlimit.begin(), v::modules::reach::unlimit.end()),
                            v::modules::reach::unlimit.end());

                    }


                    if (size_t d = std::count(memoryd.begin(), memoryd.end(), 3.0)) {
                        for (const size_t location : get_positions(memoryd, 3.0, d)) {
                            v::modules::reach::doubles.push_back(address + location * sizeof(double));

                            sort(v::modules::reach::doubles.begin(), v::modules::reach::doubles.end());
                            v::modules::reach::doubles.erase(
                                unique(v::modules::reach::doubles.begin(), v::modules::reach::doubles.end()),
                                v::modules::reach::doubles.end());

                        }

                    }

                    if (size_t f = std::count(memoryf.begin(), memoryf.end(), 3.0f)) {
                        for (const size_t location : get_positions(memoryf, 3.0f, f)) {
                            v::modules::reach::floats.push_back(address + location * sizeof(float));

                            sort(v::modules::reach::floats.begin(), v::modules::reach::floats.end());
                            v::modules::reach::floats.erase(
                                unique(v::modules::reach::floats.begin(), v::modules::reach::floats.end()),
                                v::modules::reach::floats.end());

                        }

                    }
                }

                if (address > 0x03000000) {
                    if (size_t x = std::count(memoryf.begin(), memoryf.end(), 2.5f)) {
                        for (const size_t location : get_positions(memoryf, 2.5f, x)) {
                            v::modules::esp::locations.push_back(address + location * sizeof(float));
                            sort(v::modules::esp::locations.begin(), v::modules::esp::locations.end());
                            v::modules::esp::locations.erase(
                                unique(v::modules::esp::locations.begin(), v::modules::esp::locations.end()),
                                v::modules::esp::locations.end());
                        }
                    }
                }

                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 16.0)) {
                    for (const size_t location : get_positions(memoryd, 16.0, x)) {
                        v::modules::cavefinder::locations.push_back(address + location * sizeof(double));
                        sort(v::modules::cavefinder::locations.begin(), v::modules::cavefinder::locations.end());
                        v::modules::cavefinder::locations.erase(unique(v::modules::cavefinder::locations.begin(),
                            v::modules::cavefinder::locations.end()),
                            v::modules::cavefinder::locations.end());
                    }
                }

                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 8000.0)) {
                    for (const size_t location : get_positions(memoryd, 8000.0, x)) {
                        v::modules::velocity::locations.push_back(address + location * sizeof(double));
                        sort(v::modules::velocity::locations.begin(), v::modules::velocity::locations.end());
                        v::modules::velocity::locations.erase(
                            unique(v::modules::velocity::locations.begin(), v::modules::velocity::locations.end()),
                            v::modules::velocity::locations.end());
                    }
                }

                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 1000.0)) {
                    for (const size_t location : get_positions(memoryd, 1000.0, x)) {
                        v::modules::timer::locations.push_back(address + location * sizeof(double));
                        sort(v::modules::timer::locations.begin(), v::modules::timer::locations.end());
                        v::modules::timer::locations.erase(
                            unique(v::modules::timer::locations.begin(), v::modules::timer::locations.end()),
                            v::modules::timer::locations.end());
                    }
                }

                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 0.3)) {
                    for (const size_t location : get_positions(memoryd, 0.3, x)) {
                        v::modules::bhop::locations.push_back(address + location * sizeof(double));
                        sort(v::modules::bhop::locations.begin(), v::modules::bhop::locations.end());
                        v::modules::bhop::locations.erase(
                            unique(v::modules::bhop::locations.begin(), v::modules::bhop::locations.end()),
                            v::modules::bhop::locations.end());
                    }
                }

                if (size_t x = std::count(memoryf.begin(), memoryf.end(), 0.02666667f)) {
                    for (const size_t location : get_positions(memoryf, 0.02666667f, x)) {
                        v::modules::nametags::locations1.push_back(address + location * sizeof(float));
                        sort(v::modules::nametags::locations1.begin(), v::modules::nametags::locations1.end());
                        v::modules::nametags::locations1.erase(unique(v::modules::nametags::locations1.begin(),
                            v::modules::nametags::locations1.end()),
                            v::modules::nametags::locations1.end());
                    }
                }

                if (size_t x = std::count(memoryf.begin(), memoryf.end(), 0.16277136f)) {
                    for (const size_t location : get_positions(memoryf, 0.16277136f, x)) {
                        v::modules::speed::locations.push_back(address + location * sizeof(float));
                        sort(v::modules::speed::locations.begin(), v::modules::speed::locations.end());
                        v::modules::speed::locations.erase(unique(v::modules::speed::locations.begin(),
                            v::modules::speed::locations.end()),
                            v::modules::speed::locations.end());
                    }
                }

                if (size_t x = std::count(memoryf.begin(), memoryf.end(), -0.02666667f)) {
                    for (const size_t location : get_positions(memoryf, -0.02666667f, x)) {
                        v::modules::nametags::locations2.push_back(address + location * sizeof(float));

                        sort(v::modules::nametags::locations2.begin(), v::modules::nametags::locations2.end());
                        v::modules::nametags::locations2.erase(unique(v::modules::nametags::locations2.begin(),
                            v::modules::nametags::locations2.end()),
                            v::modules::nametags::locations2.end());
                    }
                }

                if (size_t x = std::count(memoryd.begin(), memoryd.end(), 0.08)) {
                    if (size_t x = std::count(memoryd.begin(), memoryd.end(), 0.9800000190734863)) {
                        for (const size_t location : get_positions(memoryd, 0.9800000190734863, x)) {
                            v::modules::fly::locationsfly.push_back(address + location * sizeof(double));

                            sort(v::modules::fly::locationsfly.begin(), v::modules::fly::locationsfly.end());
                            v::modules::fly::locationsfly.erase(
                                unique(v::modules::fly::locationsfly.begin(), v::modules::fly::locationsfly.end()),
                                v::modules::fly::locationsfly.end());
                        }
                    }
                }
            }
        }
        std::this_thread::sleep_for(std::chrono::seconds(5));
        
    }
}