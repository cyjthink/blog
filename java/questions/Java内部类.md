## Overview

- [定义](#定义)
- [分类](#分类)
  - [在类中定义](#在类中定义)
  - [在作用域中定义](#在作用域中定义)
- [使用场景](#使用场景)
- [.this 与 .new 的使用](#.this 与 .new 的使用)

## 定义

将一个类的定义放在另一个类的定义内部

## 分类

### 在类中定义

1. 普通内部类：创建时需要外部类的对象
2. 静态内部类

### 在作用域中定义

1. 匿名内部类：即为没有名字的内部类，只能使用一次
2. 局部内部类：作用域为其代码块或方法内

## 使用场景

1. 使用private内部类可禁止依赖类型编码，并将具体实现隐藏(普通类只有由public或default修饰)
2. 匿名内部类：只使用一次的场景，如设置点击事件

## .this 与 .new 的使用

1. 代码示例

   ```java
   class Outer {
       
       public int i = 0;
       
       public void test() {
           System.out.println("Outer");
       }
       
       class Inner {
           
           public int i = 1;
           
           public void test() {
           	System.out.println("Inner");
       	}
       }
   }
   
   // Inner inner = new Inner(); 错误
   
   // Outer outer = new Outer(); 正确
   // Outer.Inner inner = outer.new Inner();
   ```

2. 创建内部类对象时为什么需要外部类对象？

   1. 内部类拥有其外围类所有元素的访问权：当生成一个内部类对象时会捕获一个指向外围类对象的引用，可以通过该引用来访问外部类成员

3. 如何在内部类中调用外部类的方法？

   1. `Outer.this.test();`

