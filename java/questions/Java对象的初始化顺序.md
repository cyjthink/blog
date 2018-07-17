## Overview

- [代码示例与结论](#代码示例与结论)
- [不会触发初始化的操作](#不会触发初始化的操作)
- [测试代码](#测试代码)
- [引用](#引用)

## 代码示例与结论

```java
class A {
    private int a = 1; 			// 1
    public static int b = 2;		// 2
    
    static {				// 3
    }
    
    {					// 4
    }
    
    public A() {}			// 5
}

class B extends A {
    private int c = 3;			// 6
    public static int d = 4; 	// 7
    
    static {				// 8
    }
    
    {					// 9
    }	
    
	public B() {}			// 10
}

new B();

// 2 -> 3 -> 7 -> 8 -> 1 -> 4 -> 5 -> 6 -> 9 -> 10
// 1.父类的静态变量、静态块(按照定义的顺序)
// 2.子类的静态变量、静态块(按照定义的顺序)
// 3.父类的成员变量、代码块(按照定义的顺序)
// 4.父类的构造函数
// 5.子类的成员变量、代码块(按照定义的顺序)
// 6.子类的构造函数
```

## 不会触发初始化的操作

1. 调用由 static final 修饰的变量不会触发初始化
2. 调用数组 (B[] bs = new B[10];) 不会触发初始化
3. 通过子类引用父类的静态字段不会触发初始化

## 测试代码

1. [Log](code/java/init%20order/Log.java)
2. [Base](code/java/init%20order/Base.java)
3. [Extend](code/java/init%20order/Extend.java)

## 引用

1. https://www.zhihu.com/question/49196023
