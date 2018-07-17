## Overview

- [关系图示](#关系图示)
- [区别](#区别)
- [注意](#注意)
- [常见题型](#常见题型)

## 关系图示

![](/res/java_CharSequence%20String%20StringBuilder%20StringBuffer%20relate.jpg)

## 区别

|     名称      |                             说明                             |
| :-----------: | :----------------------------------------------------------: |
| CharSequence  |  接口，表示有序的字符集合，可通过toString()转化为String对象  |
|    String     | 常量，不可变，内部封装的是char[]；android下的String类中部分API通过native方法实现，效率相对高一些 |
| StringBuffer  |           可变长度字符序列，线程安全，初始容量为16           |
| StringBuilder |          可变长度字符序列，非线程安全，初始容量为16          |

## 注意

1. String使用'+'进行字符串拼接时，在编译期会转化为StringBuilder#append方式 

   ```java
   String result = r1 + "." + r2;
   String result = new StringBuilder(r1).append(".").append(r2).toString();
   // 在使用"+"进行拼接的时候实际转为了StringBuilder
   
   // 如果在循环中使用"+"进行拼接，那在整个循环过程中会生成很多无用的StringBuilder对象造成浪费，应该使用StringBuilder实现
   ```

2. String在内存中有一个常量池，两个相同的串在池中只有一份实例

## 常见题型

   ```java
   String a = "HelloWorld";
   String b = "Hello" + "World";
   System.out.println((a==b));
   // true 编译时被优化
   
   String a = "HelloWorld";
   String b = "Hello";
   String c = b + "World";
   System.out.println((a==c));
   // false 由于有引用，编译时不会被优化
   
   String a = "HelloWorld";
   final String b = "Hello";
   String c = b + "World";
   System.out.println((a==c));
   // true 用final修饰的变量会在class文件的常量池中保持一个副本，对final变量的访问在编译器就会被替换成常量
   ```
