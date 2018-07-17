## Overview

- [概述](#概述)
- [代码示例](#代码示例)
  - [方法调用时的优先级](#方法调用时的优先级)
- [体现在什么地方](#体现在什么地方)
- [作用与缺点](#作用与缺点)
  - [作用](#作用)
  - [缺点](#缺点)
- [引用](#引用)

## 概述

多态：同一消息可以根据发送对象（函数的调用）的不同而具有不同的表现

多态存在的条件：有继承、有重写、有向上转型

## 代码示例

```java
public static class Animal {

    private String i = "i in Animal";
    public static String j = "j in Animal";
    
    public void run() {
     	System.out.println("run() called in Animal");   
    }
    
    public static void eat() {
 		System.out.println("eat() called in Animal");       
    }
    
    public void show(Animal animal) {
        System.out.println("show(Animal animal) called in Animal");
    }
}

public static class Lion extends Animal {
    
    private String i = "i in Lion";
    public static String j = "j in Lion";
    
    public void run() {
        System.out.println("run() called in Lion");
    }
    
    public static void eat() {
        System.out.println("eat() called in Lion");
    }
    
    public void specialMethod() {
        System.out.println("specialMethod() called in Lion");
    }
    
    public void show(Lion lion) {
        System.out.println("show(Lion lion) called in Lion");
    }
    
    public void show(Animal animal) {
        System.out.println("show(Animal animal) called in Lion");
    }
}

public static void main(String[] args) {
    Animal animal = new Lion();
    
    System.out.println(animal.i); // i in Animal
    System.out.println(animal.j); // j in Animal
    
    animal.run(); // run() called in Lion
	animal.eat(); // eat() called in Animal
    
    // System.out.println(animal.k); 无法调用
    // animal.specialMethod(); 无法调用
    
    animal.show(new Lion()); // show(Animal animal) called in Lion
}

// 涉及的知识点有：
	// 被重写的方法（包括静态方法、非静态方法）、属性（静态属性、非静态属性）在使用中被调用的情况
```

### 方法调用时的优先级

(this指Animal) ：this.show(O) -> super.show(O) -> this.show((super)O) -> super.show((super)O)

## 体现在什么地方

父类引用指向子类对象，调用方法时会调用子类的实现；其中涉及动态绑定：将一个方法调用与一个方法主体关联起来称为绑定。在执行期间判断所引用对象的实际类型，并调用对应的方法

##作用与缺点

### 作用

1. 消除类型之间的耦合

### 缺点

1. 无法调用子类特有的成员属性、成员方法

## 引用

1. https://www.cnblogs.com/liujinhong/p/6003144.html