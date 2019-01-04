#include "def.h"
#include "BMgr.h"
#include "DSMgr.h"
#include <cstring>
#include <iostream>
#include <fstream>
#include <ctime>
#include <stdlib.h>

int main()
{
	//��һ�����У�����ȫΪ1��data.dbf�ļ�,֮������*****************//
	FILE *fp=fopen("data.dbf","w+");
	char bu[100000];
	memset(bu,1,sizeof(bu));
	for (int i = 0;i<4096;i++)
	{
		fwrite(bu,sizeof(char),sizeof(bu),fp);
	}
	fclose(fp);

	/*************************************************************
	* data-5w-50w-zipf.txt��һ������ʾ������д���ڶ���Ϊpage_id
	**************************************************************/
	ifstream infile("data-5w-50w-zipf.txt");
	if(!infile)
	{
		cerr<<"open data-5w-50w-zipf.txt error"<<endl;
		exit(1);
	}
	//(int,int\n);
	int temp1,temp2;
	int ct = -1;
	int ICount = 0;int OCount = 0;
	char c1;

	/*********************************************************
	* ��������
	**********************************************************/
	BMgr *bmgr=new BMgr;
	DSMgr *ds=new DSMgr;
	//bFrame ReadPage(int page_id);
	//int WritePage(int frame_id, bFrame frm);
	//int Hash(int page_id);
	bFrame *bf_2=new bFrame[DEFBUFSIZE];
	ds->OpenFile("data.dbf");

	/***************��frame_id����buf*************************/
	for (int i2 = 0;i2<DEFBUFSIZE;i2++)
	{
		bf_2[i2] = ds->ReadPage(i2);
	}

	for (int i3 = 0;i3<DEFBUFSIZE;i3++)
	{
		bmgr->FixPage(i3);
	}
	//cout<<bmgr->ptof[358]->frame_id<<endl;
	//cout<<(char)bmgr->buf[358].field<<endl;

	//��ʱ��ʼ//
	double beginTime = (double)clock()/CLK_TCK;
	/*******************************************************
	* Each trace record has the format as "x, ###",where x is
	* 0(read) or 1(write) and ### is the referenced page number.
	* һ�߶�.txt�ļ���һ�ߴ���
	********************************************************/
	while(infile!=NULL)
	{
		infile>>temp1;//��¼�Ƕ�����д����//
		//cout<<temp1;
		infile>>c1;
		//cout<<c1;
		infile>>temp2;
		//cout<<temp2;
		//cout<<endl;
		ct++;
		if (temp1 == 0)//read
		{
			int p_id_1 = temp2;
			//int f_id_1=bmgr->Hash(p_id_1);
			/*	int f_id_1=bmgr->Hash(p_id_1);
			if (p_id_1==bmgr->ptof[f_id_1]->page_id)
			{
				bf_1 = bmgr->buf[f_id_1];
				//cout<<(char)(bmgr->buf[f_id_1].field)<<endl;
			}
			else
			{
				bf_1 = ds->ReadPage(p_id_1);
				int f_id_2=bmgr->Hash(p_id_1);
				bmgr->buf[f_id_2] = bf_1;
				bmgr->ptof[f_id_2]->page_id=p_id_1;
				//bmgr->FixNewPage(bf_1);
				//fr[p_id_1]=bmgr->FixPage(p_id_1,bcb);
				//bcb[p_id_1]->dirty=0;
				ICount++;
			}
			*/
			if (bmgr->FindFrame(p_id_1))//�������У��ӻ����ȡ//
			{
				//��ȡbmgr->buf[f_id_1];����
			}
			else
			{
				/*����Ӧpage_id��page���뵽buffer�С����buffer����������Ҫѡ�񻻳���frame*/
				bmgr->FixPage(p_id_1);
				ICount++;
				//��ȡbmgr->buf[f_id_1];����

			}
		}
		else			//write
		{
			//cout<<bmgr->ptof[0]->page_id;
			int p_id_2 = temp2;
			if(bmgr->FindFrame(p_id_2))//��������Ӧҳ����д�뻺��
			{
				//bmgr->buf[i]=bf_1;
			}
			else
			{
				//ֱ��д���ļ����޸Ļ���//
				//ds->WritePage(p_id_2,bf_1);
				/*���ƣ���ȡʱ��=д��ʱ�䣬ʡ��ֱ��д���ļ�����,
				  ����ֱ����һ��FixPage����һ��I/O������ʩ����*/
				bmgr->FixPage(p_id_2);

				OCount++;
			}
			/*int f_id_2 = bmgr->Hash(p_id_2);
			if (p_id_2==bmgr->ptof[f_id_2]->page_id)
			{
				//int f_id_2 = bmgr->ptof[f_id_2]->frame_id;
				//bmgr->buf[f_id_2].field = b_2;
			}
			else
			{
				//bf_op=buf[p_id_2];
				//ds->WritePage(p_id_2,bf_op,bcb);
				//fr[p_id_2]=bmgr->FixPage(p_id_2,bcb);
				//bcb[p_id_2]->dirty=0;
				OCount++;
			}*/


			//bmgr->FixNewPage(bf_2);
		}

	}
	//��ʱ����//
	double finishTime = (double)clock()/CLK_TCK;

	double runTime = finishTime - beginTime;

	int totle = ICount+OCount;
	double l = (double(ct-totle)/ct)*100;
	cout<<"DEFBUFSIZE:"<<DEFBUFSIZE<<endl;
	cout<<"Pages:"<<ct<<endl;
	cout<<"I/O:"<<totle<<endl;
	cout<<"HitRate:"<<l<<"%"<<endl;
	cout<<"TotalTime:"<<runTime<<endl;

	//�رճ����ʱ��dirty��bufд���ļ�//
	for (int i = 0;i < DEFBUFSIZE;i++)
	{
		while (bmgr->ptof[i]->dirty == 1)
		{
			bFrame bf = bmgr->buf[bmgr->ptof[i]->page_id];
			ds->WritePage(bmgr->ptof[i]->page_id,bf);
		}
	}

	infile.close();
	return 0;
}
