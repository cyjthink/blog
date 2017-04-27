* [HTTPS介绍](#HTTPS)
  * [对称加密](#对称加密)   
  * [非对称加密](#非对称加密)
  * [CA认证](#CA认证)
 * [实际流程](#实际流程)

## HTTPS = HTTP + 加密(对称加密、非对称加密) + CA认证

#### 对称加密

- ##### 在加密和解密时使用相同的密钥，或是使用两个可以简单地相互推算的密钥

- ##### 为什么使用对称加密？

  >  A发送一段文字给B，在这个过程中信息可能会被截获，通过对称加密即使获取内容也无法解密

#### 非对称加密

- ##### 非对称加密(处理速度慢) = 公开密钥(加密报文) + 私有密钥(解密报文)

- ##### 为什么使用非对称加密？

  >  A发送文字时需要同时发送key给B，这一过程key可能被截获

#### CA认证

- ##### 一个专门用来认证网站合法性的组织，服务商可以向CA申请证书，使得在建立安全连接时可以带上CA的签名

- ##### 为什么使用CA认证？

  > 消息传输过程中会通过一个中间人C，C伪装成A并向B发送public-key-C，B接收到public-key-C后将信息加密返还给C，C就可以用private-key-C将消息解密

## 实际流程

1. Client->Server,发送报文开始SSL通信(报文中包含SSL版本、加密组件)

2. Server->Client,返回certificate报文server，其中包含公开密钥
3. Client->Server,发送使用公开密钥加密后的密钥
4. Server->Client,使用私有密钥获得密钥，使用该密钥进行通信
5. 断开连接
