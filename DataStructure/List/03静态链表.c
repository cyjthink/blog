#include <stdio.h>

#define MAX_SIZE 100
#define TRUE 1
#define FALSE 0

typedef int Status;
typedef int ElemType;
typedef struct
{
	ElemType data;
	int cursor;
} Component, StaticList[MAX_SIZE];


/* 初始化静态链表 */
void InitList(StaticList L)
{
	int i;

	for (i = 0; i < MAX_SIZE - 1; i++)
	{
		L[i].cursor = i + 1;
	}
	L[MAX_SIZE - 1].cursor = 0;
	return;
}

/* 静态链表长度 */
int ListLength(StaticList L)
{
	int length;
	int i;
	
	length = 0;
	i = L[MAX_SIZE - 1].cursor;
	while (i)
	{
		i = L[i].cursor;
		length++;
	}
	return length;
}

/* 分配空间 */
int MallocComponent(StaticList L)
{
	int i;

	i = L[0].cursor;
	if (i)
	{
		L[0].cursor = L[i].cursor;
	} else {
		printf("空间分配失败\n");
	}
	return i;
}

/* 将数据插入指定位置 */
void InsertList(StaticList L, int pos, ElemType data)
{
	int  j;
	int k;

	k = MAX_SIZE - 1;
	if (pos < 1 || pos > ListLength(L) + 1)
	{
		printf("非法位置\n");
		return;
	}
	j = MallocComponent(L);
	L[j].data = data;
	for (int i = 1; i < pos; i++)
	{
		k = L[k].cursor;
	}
	L[j].cursor = L[k].cursor;
	L[k].cursor = j;
	return;
}

/* 释放空间 */
void FreeComponent(StaticList L, int pos)
{
	L[pos].cursor = L[0].cursor;
	L[0].cursor = pos;
	return;
}

/* 删除指定位置数据 */
void DeleteList(StaticList L, int pos)
{
	int j;
	int k;

	k = MAX_SIZE - 1;
	if (pos < 1 || pos > ListLength(L) + 1)
	{
		printf("非法位置\n");
		return;
	}
	for (int i = 1; i < pos; i++)
	{
		k = L[k].cursor;
	}
	j = L[k].cursor;
	L[k].cursor = L[j].cursor;
	FreeComponent(L, j);
	return;
}

/* 打印静态链表 */
void TraverseList(StaticList L)
{
	int i;

	i = L[MAX_SIZE - 1].cursor;
	printf("data = ");
	while (i)
	{
		printf("%d ", L[i].data);
		i = L[i].cursor;
	}
	printf("\n");
	return;
}

int main()
{
	StaticList L;

	InitList(L);
	printf("初始化成功，此时length = %d\n", ListLength(L));

	for (int i = 1; i < 6; i++)
	{
		InsertList(L, i, i);
	}
	printf("在1~5位置插入1~5后，length = %d\n", ListLength(L));
	TraverseList(L);

	DeleteList(L, 3);
	printf("删除第3个位置数据后，length = %d\n", ListLength(L));
	TraverseList(L);

	return 0;
}