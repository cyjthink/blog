## Overview

- [传递基本类型时](#传递基本类型时)
- [传递对象时](#传递对象时)
- [交换对象时](#交换对象时)
- [结论](#结论)
- [引用](#引用)

## 传递基本类型时

1. 代码示例

   ```java
   public class PassByTest {
       public static int x = 10;
   
       public static void changeValue(int y) {
           y = y * 10;
       }
       
       public static void main(String[] args) {
        	System.out.println("before change x = " + x);
           changeValue(x);
           System.out.println("after change x = " + x);   
       }
   }
   
   // before change x = 10
   // after change x = 10
   
   // 1.y初始化为x的一个拷贝
   // 2.执行 y = y * 10，但x不变
   // 3.方法结束后y将被回收
   ```

2. 图示：[java_pass_by_value_1](/res/java_pass_by_value_1.jpg)

## 传递对象时

1. 代码示例

   ```java
   public class User{
       public String name;
   }
   
   public class PassByTest {
       
       public static void changeName(User user) {
           user.name = "michael";
       }
       
       public static void main(String[] args) {
        	User student = new User();
           System.out.println("before change name = " + student.name);
           changeName(student);
           System.out.println("after change name = " + student.name);
       }
   }
   
   // before change name = null
   // after change name = michael
   
   // 1.user初始化为student的一个拷贝，指向student对象
   // 2.调用changeName()，user和student同时引用的User对象内部之改变
   // 3.方法结束后user被回收，而student指向的对象已经被改变
   ```

2. 图示：[java_pass_by_value_2](/res/java_pass_by_value_2.jpg)

## 交换对象时

1. 代码示例

   ```java
   public class User{
       public String name;
   }
   
   public class PassByTest {
       
       public static void swapUser(User user1, User user2) {
           User temp = user1;
           user1 = user2;
           user2 = temp;
   	}
       
       public static void main(String[] args) {
           User student = new User();
           student.name = "student";
           User teacher = new User();
           teacher.name = "teacher";
           System.out.println("before change teacher name = " + teacher.name + ", student name = " + student.name);
           swapUser(student, teacher);
           System.out.println("after change teacher name = " + teacher.name + ", student name = " + student.name);
       }
   }
   
   // before change teacher name = teacher, student name = student
   // after change teacher name = teacher, student name = student
   
   // 1.user1初始化为student的一个拷贝，user2初始化为teacher的一个拷贝，两者都指向对象
   // 2.定义一个temp指向user指向的对象
   // 3.更改user1\user2的指向对象，但都不影响student\teacher
   ```

2. 图示：[java_pass_by_value_3](/res/java_pass_by_value_3.jpg)

## 结论

Java是值传递

## 引用

1. https://blog.csdn.net/javazejian/article/details/51192130
