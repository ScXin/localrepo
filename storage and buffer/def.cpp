#include "def.h"

BCB::BCB()
{
    page_id = -1;
    frame_id = -1;
    latch = -1;
    count = 0;
    dirty = -1;
    first_time = -1.0;
    last_time = -1.0;
    next = NULL;
}

LRU::LRU()
{
	frame_id = -1;
	b2dtime = -1.0;
	prior = NULL;
	next = NULL;
}
