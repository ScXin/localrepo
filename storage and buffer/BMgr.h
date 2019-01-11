#ifndef BMGR_H_INCLUDED
#define BMGR_H_INCLUDED

#include "def.h"
#include "DSMgr.h"

class BMgr
{
public:
    BMgr();
    int FixPage(int page_id);
    NewPage FixNewPage();
    int UnfixPage(int page_id);
    int NumFreeFrames();
    int SelectVictim();
    int Hash(int page_id);
    void RemoveBCB(BCB * ptr, int page_id);
    void RemoveLRUEle(int frid);
    void SetDirty(int frame_id);
    void UnsetDirty(int frame_id);
    void WriteDirtys();
    bool FindFrame(int page_id);
    void PrintFrame(int frame_id);
    void LRUProcess(BCB *bcb);
    int ftop[DEFBUFSIZE];
    BCB* ptof[DEFBUFSIZE];
    bFrame buf[DEFBUFSIZE];
private:
    LRU* head;
    LRU* tail;
    DSMgr ds;
};

#endif // BMGR_H_INCLUDED
