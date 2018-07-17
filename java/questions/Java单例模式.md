## Overview

- [懒汉模式](#懒汉模式)
- [饿汉模式](#饿汉模式)
- [静态内部类](#静态内部类)
- [枚举](#枚举)
- [双重校验锁](#双重校验锁)
- [存在的问题](#存在的问题)
  - [存在多个类装载器](#存在多个类装载器)
  - [实现了Serializable接口](#实现了Serializable接口)
- [引用](#引用)

## 懒汉模式

1. 线程不安全

   ```java
   public class Singleton {
       
       private static Singleton sInstance;
       
       private Singleton() {}
       
       public static Singleton getInstance() {
           if (sIntance == null) {
               sIntance = new Singleton();
           }
           return sInstance;
       }
   }
   ```

2. 线程安全，但效率低

   ```java
   public class Singleton {
       
       private static Singleton sInstance;
       
       private Singleton() {}
       
       public static synchronized Singleton getInstance() {
           if (sIntance == null) {
               sIntance = new Singleton();
           }
           return sInstance;
       }
   }
   ```

## 饿汉模式

1. 饿汉模式

   ```java
   public class Singleton {
       
       private static Singleton sInstance = new Singleton();
       
       private Singleton() {}
       
       public static Singleton getInstance() {
           return sInstance;
       }
   }
   ```

2. 饿汉模式变种

   ```java
   public class Singleton {
       
       private static Singleton sInstance;
       
       static {
           sInstance = new Singleton();
       }
       
       private Singleton() {}
       
       public static Singleton getInstance() {
           return sInstance;
       }
   }
   ```

## 静态内部类

```java
// 利用classloader机制保证初始化时只有一个线程
public class Singleton {
    
    public static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

## 枚举

```java
// 不仅能避免多线程同步问题，还能反序列化重新创建新的对象
public enum Singleton {
    INSTANCE;
}
```

## 双重校验锁

```java
public class Singleton {
    
    private static volatile Singleton sInstance;
    
	private Singleton() {}
    
    public static Singleton getInstance() {
        if (sInstance == null) {
            // 此时可能有两个线程都进入到这里，所以需要第二次为空判断
            synchronized(Singleton.class) {
                if (sInstance == null) {
                    sInstance = new Singleton();
                }
            }
        }
        return sInstance;
    }
}
```

## 存在的问题

### 存在多个类装载器

```java
public static Class getClass(String className) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
            classLoader = Singleton.class.getClassLoader();

        return (classLoader.loadClass(className));
}
```

### 实现了Serializable接口

```java
public static class Singleton implements Serializable {
        public static Singleton INSTANCE = new Singleton();

        protected Singleton() {
        }
        
        private Object readResolve() {
            return INSTANCE;
        }
}
```

## 引用

1. http://www.blogjava.net/kenzhh/archive/2016/03/28/357824.html
