#ifndef DECLARATION_H_INCLUDED
#define DECLARATION_H_INCLUDED

#define FRAMESIZE  4096
#define DEFBUFSIZE 1024
#define MAXPAGES 50000

#include <iostream>

using namespace std;

struct bFrame
{
    char field[FRAMESIZE];
};

struct BCB
{
  BCB();
  int page_id;
  int frame_id;
  int latch;
  int count;
  int dirty;
  double first_time;
  double last_time;
  BCB * next;
};

struct NewPage
{
    int page_id;
    int page_size;
};

struct LRU{
    LRU();
	int frame_id;
	double b2dtime;    //前后两次间隔时间
	LRU *prior;
	LRU *next;
};

#endif // DECLARATION_H_INCLUDED
