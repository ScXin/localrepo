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


/************************************************************************************
*OpenFile function is called anytime a file needs to be opened for reading or writing.
*The prototype for this function is OpenFile(String filename) and returns an error code.
*The function opens the file specified by the filename.
*************************************************************************************/
int DSMgr::OpenFile(string filename)
{
	//file.open(filename,ios_base::in|ios_base::out|ios_base::app);
	//return 1;
	file = fopen(filename.c_str(),"r+");
	if(file == NULL)
	{
		return 1;
	}
	//fseek(file, 0, SEEK_END);
	//numPages = (ftell(file) / FRAMESIZE);
	//fseek(file, 0, SEEK_SET);
	return 0;
}

/************************************************************************************
*CloseFile function is called when the data file needs to be closed.  The protoype is
*CloseFile() and returns an error code.  This function closes the file that is in current
*use.  This function should only be called as the database is changed or a the program closes.
*************************************************************************************/
int DSMgr::CloseFile()
{
	//file.close();
	fclose(file);
	file = NULL;

	return 0;
}

/************************************************************************************
*ReadPage function is called by the FixPage function in the buffer manager.  This
*prototype is ReadPage(page_id, bytes) and returns what it has read in.  This function
*calls fseek() and  fread() to gain data from a file.
*************************************************************************************/
bFrame DSMgr::ReadPage(int page_id)
{
	int iread = 0;
	bFrame bf_ds;
	//C
	//SEEK_CUR：Current position of file pointer.
	//SEEK_END:End of file.
	//SEEK_SET:Beginning of file.
	fseek(file,page_id*FRAMESIZE,SEEK_SET);
	iread = (int)fread(&bf_ds,sizeof(bf_ds),1,file);

	return bf_ds;
}


/************************************************************************************
*WritePage function is called whenever a page is taken out of the buffer.
*The prototype is WritePage(frame_id, frm) and returns how many bytes were written.
*This function calls fseek() and fwrite() to save data into a file.
*************************************************************************************/
int DSMgr::WritePage(int page_id, bFrame frm)
{
	int bytesw = 0;
	fseek(file,page_id*FRAMESIZE,SEEK_SET);

	bytesw = (int)fwrite(&frm,sizeof(frm),1,file);
	return bytesw;

	//int page_id = bm->Hash(frame_id);
	//int size= DEFBUFSIZE*FRAMESIZE;
	//file.seekg(page_id,ios_base::cur);//从page_id处开始写
	//file.write(cbuf,size);

}

//文件指针调整//
int DSMgr::Seek(int offset, int pos)
{
	//C++
	//ios_base::beg ——文件开始位置
	//ios_base::cur ——文件当前位置
	//ios_base::end ——文件末尾位置
	//C
	fseek(file,offset,pos);
	//file.seekg(pos,ios_base::beg);
	return 1;
}


void DSMgr::IncNumPages()
{
	numPages++;
}

//returns the page counter.
int DSMgr::GetNumPages()
{
	return numPages;
}


/************************************************************************************
*SetUse function looks sets the bit in the pages array.  This array keeps track of
*the pages that are being used.  If all records in a page are deleted, then that
*page is not really used anymore and can be reused again in the database.  In order
*to know if a page is reusable, the array is checked for any use_bits that are set
*to zero.  The fixNewPage function firsts checks this array for a use_bit of zero.
*If one is found, the page is reused.  If not, a new page is allocated.
*************************************************************************************/
void DSMgr::SetUse(int index, int use_bit)
{
}

//returns the current use_bit for the corresponding page_id.
int DSMgr::GetUse(int index)
{
	return index;
}
