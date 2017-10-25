#include <stdio.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

typedef int ElemType;
typedef int Status;
typedef struct Node
{
	ElemType data;
	struct Node *next;
} *LinkList, Node;


/* L指向创建的头节点 */
/* 将指针传递给函数时，传递的是值；如果希望修改原指针，就需要传递指针的指针 */
void InitList(LinkList *L)
{
	*L = (Node *) malloc(sizeof(Node));
	if (*L == NULL)
	{
		printf("分配空间出错\n");
		return;
	}
	(*L)->next = NULL;
	return;
}

/* 判断链表是否为空 */
Status IsEmpty(LinkList L)
{
	if (L->next == NULL)
	{
		return TRUE;
	} else {
		return FALSE;
	}
}

/* 获取链表长度 */
int ListLength(LinkList L) {
	int length = 0;
	Node *head = L->next;

	while (head != NULL) {
		length++;
		head = head->next;
	}
	return length;
}

/* 头插法 */
void CreateListHead(LinkList *L, int length)
{
	(*L) = (LinkList) malloc(sizeof(Node));
	if (*L == NULL)
	{
		printf("分配空间出错\n");
		return;
	}
	(*L)->next = NULL;
	for (int i = 0; i < length; i++)
	{
		Node *newNode = (Node *) malloc(sizeof(Node));
		if (newNode == NULL)
		{
			printf("分配空间出错\n");
			return;
		}
		newNode->data = i + 1;
		newNode->next = (*L)->next;
		(*L)->next = newNode;
	}
	return;
}

/* 尾插法 */
void CreateListTail(LinkList *L, int length)
{
	Node *tail;

	(*L) = (LinkList) malloc(sizeof(Node));
	if (*L == NULL)
	{
		printf("分配空间出错\n");
		return;
	}
	(*L)->next = NULL;
	tail = (*L);
	for (int i = 0; i < length; ++i)
	{
		Node *newNode = (Node *) malloc(sizeof(Node));
		newNode->data = i + 1;
		newNode->next = NULL;
		tail->next = newNode;
		tail = newNode;
	}
	return;
}

/* 在指定位置插入数据 */
void InsertList(LinkList L, int pos, ElemType data)
{
	// 操作数据时，头节点也算一个点，因为存在直接向头节点之后操作数据的情况
	int i = 1;
	Node *head = L;

	while (head != NULL && i < pos)
	{
		i++;
		head = head->next;
	}
	// 分别是pos >= 1与pos <= length情况的判断
	if (pos < i || head == NULL)
	{
		printf("非法位置\n");
		return;
	}
	Node *newNode = (Node *) malloc(sizeof(Node));
	if (newNode == NULL)
	{
		printf("分配空间出错\n");
		return;
	}
	newNode->data = data;
	newNode->next = head->next;
	head->next = newNode;
	return;
}

/* 删除指定位置的数据 */
void DeleteList(LinkList *L, int pos)
{
	int i = 1;
	Node *head = *L;

	while (head != NULL && i < pos) 
	{
		i++;
		head = head->next;
	}
	if (head == NULL || i > pos)
	{
		printf("非法位置\n");
		return;
	}
	Node *temp = head->next;
	head->next = head->next->next;
	free(temp);
	return;
}

/* 获取指定位置的值并赋给e */
void GetElem(LinkList L, int pos, ElemType *e) 
{
	int i = 0;
	Node *head = L;

	while (head != NULL && i < pos)
	{
		i++;
		head = head->next;
	}
	if (head == NULL || i > pos)
	{
		printf("非法位置\n");
		return;
	}
	*e = head->data;
}

/* 链表的删除 */
void ClearList(LinkList *L)
{
	Node *temp;
	Node *head = (*L)->next;

	while(head != NULL)
	{
		temp = head->next;
		free(head);
		head = temp;
	}
	(*L)->next = NULL;
	return;
}

/* 打印链表 */
void TraverseList(LinkList L)
{
	printf("链表中的数据为：");
	Node *head = L->next;
	while (head != NULL)
	{
		printf("%d ", head->data);
		head = head->next;
	}
	printf("\n");
}


int main() {
	LinkList L;
	Status isEmpty;
	ElemType e;

	InitList(&L);

	// CreateListHead(&L, 5);
	// printf("使用头插法插入数据为1~5的数据，链表长度：%d\n", ListLength(L));
	// TraverseList(L);

	// CreateListTail(&L, 5);
	// printf("使用尾插法插入数据为1~5的数据，链表长度：%d\n", ListLength(L));
	// TraverseList(L);

	isEmpty = IsEmpty(L);
	if (isEmpty == TRUE)
	{
		printf("此时链表为空\n");
	} else {
		printf("此时链表不为空\n");
	}

	for (int i = 1; i < 6; i++)
	{
		InsertList(L, i, i);
	}
	printf("在1~5处插入1~5后链表长度：%d\n", ListLength(L));
	TraverseList(L);

	isEmpty = IsEmpty(L);
	if (isEmpty == TRUE)
	{
		printf("此时链表为空\n");
	} else {
		printf("此时链表不为空\n");
	}

	GetElem(L, 3, &e);
	printf("获取第3个位置的值为：%d\n", e);

	DeleteList(&L, 3);
	printf("删除第3个位置的数据后链表长度：%d\n", ListLength(L));
	TraverseList(L);

	ClearList(&L);
	printf("清空链表后的长度为：%d\n", ListLength(L));

	return 0;
}