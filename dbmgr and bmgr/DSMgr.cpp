#include "BMgr.h"
#include "DSMgr.h"
#include<fstream>
#include<iostream>

using namespace std;


DSMgr::DSMgr()
{
	file = NULL;
	int i = 0;
	for(i = 0; i < MAXPAGES; i++)
	{
		pages[i] = -1;
	}
}

int DSMgr::OpenFile(string filename)
{
	file = fopen(filename.c_str(),"r+");
	if(file == NULL)
	{
		return 1;
	}
	return 0;
}

int DSMgr::CloseFile()
{
	//file.close();
	fclose(file);
	file = NULL;

	return 0;
}

bFrame DSMgr::ReadPage(int page_id)
{
	int iread = 0;
	bFrame bf_ds;
	fseek(file,page_id*FRAMESIZE,SEEK_SET);
	iread = (int)fread(&bf_ds,sizeof(bf_ds),1,file);

	return bf_ds;
}

int DSMgr::WritePage(int page_id, bFrame frm)
{
	int bytesw = 0;
	fseek(file,page_id*FRAMESIZE,SEEK_SET);

	bytesw = (int)fwrite(&frm,sizeof(frm),1,file);
	return bytesw;

}

//文件指针调整//
int DSMgr::Seek(int offset, int pos)
{
	fseek(file,offset,pos);
	return 1;
}


void DSMgr::IncNumPages()
{
	numPages++;
}

int DSMgr::GetNumPages()
{
	return numPages;
}

void DSMgr::SetUse(int index, int use_bit)
{
}

int DSMgr::GetUse(int index)
{
	return index;
}
