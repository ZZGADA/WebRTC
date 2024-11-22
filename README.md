# WebRTC

## Mention
本项目内容-WebRTC是组内项目的重点，目前我将抽出技术重点和技术难点与大家分享。   
需要考虑WebSocket长链接的稳定性，可能会出Go的版本来进行维护。涉及到的核心中间件件有**Redis** 和 **Nacos**。   
尽请期待～ 

## WebRTC基础知识整理 🚀
可以点击链接右边👉优先查看哦～（目前MarkDown的格式还在梳理）：[WebRTC知识梳理](./WebRTC知识梳理.md)

## 开发时间线 📝
1. 2024.11.18 完成第一版java信令服务器开发，并实现前后端联调  
    1. 只支持本地多端投屏（浏览器点开两个tab或者跨浏览器都可以，但是内网和公网状态下多主机投屏互动还没有完成）





## 分布式架构下的WebSocket连接 🔗

1. WebSocket连接： 客户端通过WebSocket连接服务器，服务器根据不同ws路由创建不同的session，哪怕是同一个ip。
2. 分布式架构下，多个ws服务器以多个pod的形式存在。此时ip的连接可能会被service负载均衡到不同的pod中。
	1. 在linux中，一切皆文件。WebSocket的连接都会以文件的形式存在，此时就需要要拿到fd标识就可以找到WebSocket连接的地址。
	   所以回到我们的场景需求中。我们需要进行点对点的投屏。所以客户端连接WebSocket 就需要知道另外一个客户端连接的WebSocket 的 WebSocketSession对象。
	   此时由于我们是多个pod，那么我们就无法在当前服务的room中找到我们需要的WebSocketSession对象。所以此时就需要中间层redis来记录和标识这些fd标识。
	2. 解决思路：   
	   a. socket连接产生的WebSocketSession对象会存在内存中，此时会有fd标识这一个对象。我们将sessionId和fd绑定为key-value。   
	   b. 然后在redis中设置一个room的房间概念。   
	   c. 根据房间id和学生id（或者是客户端ip，一切可以标识客户端设备的id都行）在一个pod中找到对应客户端唯一标识。    
		d. 如果没有挂载，那么可以在每一个pod中写一个http接口，通过接口实现多个pod的通信，进而将信令消息转发给其他pod，然后对应的pod又可以直接获取WebSocketSession对象进行转发，从而实现分布式架构下的signal信令转发。  
3. 核心关键点：
   * pod 服务本身应该是无状态容器，但是WebSocket连接对象是有状态的。所以需要将这些有状态对象进行抽离，从而使得整个pod变成无状态容器。   
   * 将数据中心化，使用redis。或者是将多个容器的fd的存储挂载到同一个容器中。甚至还可以使用进程通信的一系列技术：共享内存等，实现fd的共享。
   * 每次连接 设置ack 返回连接确认状态    
   * service 路由的时候 使用loadbalance。此时WebSocket连接如何合理的分配到不同的pod上是需要优化的。service本身的allocate算法需要进行优化，让每个pod的连接数变得平均 

 	   
