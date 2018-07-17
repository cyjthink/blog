## Overview

- [定义](#定义)
  - [重载 overload](#重载 overload)
  - [重写 override](#重写 override)

## 定义

### 重载 overload

1. 权限修饰符：可以改变
2. 返回类型：可以改变，但不能作为区分重载方法的依据
3. 方法名：必需相同
4. 参数列表（参数顺序、类型）：必需改变，参数的顺序、类型可以作为方法重载的依据
5. 异常：可以声明新的或更广的异常（Checked exception\Runtime exception）

### 重写 override

1. 权限修饰符：不可比原来的权限小
2. 返回类型：必需相同
3. 方法名：必需相同
4. 参数列表：必需相同
5. 异常：可以添加新的或更广的Runtime Exception。如果父类定义了IOException，重写的方法只能抛出IOException及其子类异常。