#include <stdio.h>

#define TRUE 1
#define FALSE 0
#define MAX_SIZE 20

typedef int Status;
typedef int ElemType;
typedef struct
{
	ElemType data[MAX_SIZE];
	int length;
} Sql;


/* 初始化 */
void InitList(Sql *L)
{
	L->length = 0;
	return;
}

/* 是否为空 */
int IsEmpty(Sql L)
{
	if (L.length == 0)
	{
		return TRUE;
	} else {
		return FALSE;
	}
}

/* 获取list长度 */
int ListLength(Sql L)
{
	return L.length;
}

/* 获取指定位置的值 */
void GetElem(Sql L, int pos, ElemType *e)
{
	if (pos < 1 || pos > L.length)
	{
		printf("非法位置\n");
		return;
	}
	*e = L.data[pos - 1];
	return;
}

/* 获取指定数据在list中的位置 */
int LocateElem(Sql L, ElemType data)
{
	int i;

	if (L.length <= 0)
	{
		printf("当前链表为空\n");
		return -1;
	}
	for (i = 0; i < L.length; i++)
	{
		if (L.data[i] == data)
		{
			break;
		}
	}
	if (i >= L.length)
	{
		return -1;
	}
	return i + 1;
}

/* 在指定位置插入数据 */
void InsertList(Sql *L, int pos, ElemType e)
{
	int i = L->length;

	while(pos <= i)
	{
		L->data[i] = L->data[i - 1];
		i--;
	}
	L->data[pos - 1] = e;
	L->length++;
	return;
}

/* 删除指定位置数据 */
void DeleteList(Sql *L, int pos)
{
	if (pos < 1 || pos > L->length)
	{
		printf("非法位置\n");
		return;
	}
	int i = pos;
	while(i < L->length)
	{
		L->data[i - 1] = L->data[i];
		i++;
	}
	L->length--;
	return;
}

/* 清空 */
void ClearList(Sql *L)
{
	L->length = 0;
	return;
}

/* 打印 */
void TraveserList(Sql L)
{
	int i = 0;

	printf("数据为：");
	while(i < L.length)
	{
		printf("%d ", L.data[i]);
		i++;
	}
	printf("\n");
	return;
}

int main()
{
	Sql L;
	int isEmpty;

	InitList(&L);
	printf("初始化list完成，此时长度为：%d\n", ListLength(L));

	for (int i = 1; i < 6; i++)
	{
		InsertList(&L, i, i);
	}
	printf("在1~5处插入1~5后，此时长度为：%d\n", ListLength(L));
	TraveserList(L);

	DeleteList(&L, 2);
	printf("删除第2个位置的数据后，此时长度为：%d\n", ListLength(L));
	TraveserList(L);

	printf("数据4在list中的位置为：%d\n", LocateElem(L, 0));

	ClearList(&L);
	printf("清空list后，");
	isEmpty = IsEmpty(L);
	if (isEmpty == TRUE)
	{
		printf("此时为空\n");
	} else {
		printf("此时不为空\n");
	}

	return 0;
}