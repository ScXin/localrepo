#include "BMgr.h"
#include <ctime>

BMgr::BMgr()
{
    for(int i = 0;i < DEFBUFSIZE;i++)
    {
        ftop[i] = -1;
        ptof[i] = NULL;
    }
    head = NULL;
    tail = NULL;
}

int BMgr::SelectVictim()
{
	int vFrame = 0,page_id = 0,frame_id = 0;
	BCB *bcb = NULL;
	bool found = false;
	LRU *temp = tail;
	if(temp == NULL)
	{
		return 0;
	}
	for(int i = 0; i < DEFBUFSIZE; i++)
	{
		if(ftop[i] == -1)
		{
			return i;
		}
	}
	vFrame = temp->frame_id;
	page_id = ftop[vFrame];
	frame_id = Hash(page_id);
	bcb = ptof[frame_id];
	while(found != true)
	{
		if(bcb == NULL)
		{
			break;
		}
        while(bcb->frame_id != vFrame)
        {
          bcb = bcb->next;
        }
        if(bcb->count == 0 && bcb->latch == 0)
        {
            found = true;
        }
        else
        {
            temp = temp->prior;
            if(temp == NULL)
            {
                break;
            }
            vFrame = temp->frame_id;
            page_id = ftop[vFrame];
            frame_id = Hash(page_id);
            bcb = ptof[frame_id];
        }
	}
	page_id = ftop[vFrame];
	frame_id = Hash(page_id);
	bcb = ptof[frame_id];
	if(bcb != NULL)
	{
		if(bcb->next != NULL)
		{
			while(bcb != NULL && bcb->page_id != page_id)
			{
				bcb = bcb->next;
			}
			if(bcb != NULL && bcb->dirty == 1)
			{
				ds.WritePage(page_id,buf[vFrame]);
			}
			if(bcb != NULL)
			{
				RemoveLRUEle(vFrame);
				RemoveBCB(bcb,page_id);
			}
		}
	}
	return vFrame;
}

int BMgr::Hash(int page_id)
{
    return page_id % DEFBUFSIZE;
}

void BMgr::LRUProcess(BCB *bcb)
{
    double time = -1.0;
    LRU *p = NULL;
    LRU *temp = NULL;

    if (head == NULL)
    {
        head = new LRU();
        head->frame_id = bcb->frame_id;
        if (bcb->last_time != -1.0)
        {
            head->b2dtime = bcb->last_time - bcb->first_time;
        }
        tail = head;
    }
    else
    {
		p = head;
		if(bcb->last_time != -1.0)
		{
			time = bcb->last_time - bcb->first_time;
		}
		if(time == -1.0)
		{
			while(p->next != NULL)
			{
				if (p->next->b2dtime == -1.0)
                {
                    temp = new LRU();
                    temp->frame_id = bcb->frame_id;
                    temp->b2dtime = time;
                    temp->next = p->next;
                    temp->prior = p;
                    p->next->prior = temp;
                    p->next = temp;
                    break;
                }
				p = p->next;
			}
			if(p->next == NULL)
			{
				p->next = new LRU();
				p->next->prior = p;
				p = p->next;
				p->frame_id = bcb->frame_id;
				p->next = NULL;
				p->b2dtime = time;
				tail = p;
			}
		}
        else
		{
			while(p->next != NULL)
			{
				if(time <= p->next->b2dtime)
                {
                    temp = new LRU();
                    temp->b2dtime = time;
                    temp->frame_id = bcb->frame_id;
                    temp->next = p->next;
                    temp->prior = p;
                    p->next->prior = temp;
                    p->next = temp;
                    break;
                }
				p = p->next;
			}
			if(p->next == NULL)
			{
				p->next = new LRU();
				p->next->prior = p;
				p = p->next;
				p->b2dtime = time;
				p->frame_id = bcb->frame_id;
				p->next = NULL;
				tail = p;
			}
		}
    }
}

int BMgr::FixPage(int page_id)
{

	int fid = -1;
	int frame_id=Hash(page_id);
	BCB * bcb =ptof[frame_id];



	while(bcb != NULL)
	{
		if(bcb->page_id == page_id)
		{
			break;
		}
		bcb = bcb->next;
	}

	if(bcb != NULL)
	{
		if(bcb->last_time != -1)
		{
			bcb->first_time=bcb->last_time;
		}
		LRUProcess(bcb);
		return bcb->frame_id;
	}

	else
	{
		bcb = ptof[frame_id];
		fid = SelectVictim();
		buf[fid] = ds.ReadPage(page_id);
		ftop[fid] = page_id;
		if(bcb != NULL)
		{
			while(bcb->next != NULL)
			{
				bcb = bcb->next;
			}
			bcb->next = new BCB();
			bcb = bcb->next;
		}
		else
		{
			bcb = new BCB();
			ptof[frame_id] = bcb;
		}
		bcb->next = NULL;
		bcb->page_id = page_id;
		bcb->frame_id = fid;
		bcb->latch = 0;
		bcb->count = 0;
		bcb->first_time = static_cast<unsigned>(time(0));
		bcb->last_time = -1;
		LRUProcess(bcb);
	}
	return fid;
}

bool BMgr::FindFrame(int page_id)
{
    bool flag = false;

    for(int i = 0;i < DEFBUFSIZE;i++)
    {
        if (ptof[i]->page_id == page_id)
        {
            flag = true;
            break;
        }
    }

    return flag;
}

int BMgr::NumFreeFrames()
{
	BCB * bcb = NULL;
	int page_id;
	int frame_id;
	int free = 0;

	for(int i=0;i<DEFBUFSIZE;i++)
	{
		page_id = ftop[i];
		if(page_id == -1)
		{
			free++;
		}
		else
		{
			frame_id = Hash(page_id);
			bcb = ptof[frame_id];
			while(bcb != NULL)
			{
                if(bcb->frame_id == page_id)
                {
                    if(bcb->count == 0 && bcb->latch == 0)
                    {
                        free++;
                    }
                    break;
                }
				bcb = bcb->next;
			}

		}
	}
	return free;
}

void BMgr::SetDirty(int frame_id)
{
	int page_id = ftop[frame_id];
	int ptof_id = Hash(page_id);
	BCB * bcb = ptof[ptof_id];

	while(bcb != NULL)
	{
        if(bcb->page_id != page_id)
        {
            bcb->dirty = 1;
            break;
        }
		bcb = bcb->next;
	}
}

void BMgr::UnsetDirty(int frame_id)
{
	int page_id = ftop[frame_id];
	int ptof_id = Hash(page_id);
	BCB * bcb = ptof[ptof_id];
	while(bcb != NULL)
	{
        if(bcb->page_id != page_id)
        {
            bcb->dirty = 0;
            break;
        }
		bcb = bcb->next;
	}
}

void BMgr::WriteDirtys()
{
	BCB *bcb = NULL;
	for(int i=0;i<DEFBUFSIZE;i++)
	{
		bcb = ptof[i];
		while(bcb != NULL)
		{
			if(bcb->dirty == 1)
			{
				ds.WritePage(bcb->page_id, buf[bcb->frame_id]);
			}
			bcb = bcb->next;
		}
	}
}

void BMgr::RemoveBCB(BCB * p, int page_id)
{
    int frame_id = Hash(page_id);
    BCB * bcb = ptof[frame_id];

	if(bcb == p)
	{
		if(bcb->next != NULL)
		{
			bcb = bcb->next;
			delete p;
			ptof[frame_id] = bcb;
			p = bcb = NULL;
		}
		else
		{
			delete p;
			ptof[frame_id] = NULL;
			p = bcb = NULL;
		}
	}
	else
	{
		while(bcb->next != p)
		{
			bcb = bcb->next;
		}
		bcb->next = p->next;
		delete p;
		p = NULL;
	}
}

void BMgr::RemoveLRUEle(int frid)
{
    LRU *p = head;
	LRU *temp = NULL;
	if(p != NULL)
	{
        while(p != NULL && p->frame_id != frid)
        {
            p = p->next;
        }
        if(p != NULL)
        {
            p->next->next->prior = p;
            temp = p->next->next;
            delete p->next;

            p->next = temp;
        }
	}
}

int BMgr::UnfixPage(int page_id)
{
    return 0;
}

/*NewPage BMgr::FixNewPage()
{
    return NULL;
}*/

void BMgr::PrintFrame(int frame_id)
{

}
