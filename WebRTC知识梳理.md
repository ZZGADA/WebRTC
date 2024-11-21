STUN & TURN & ICE
1. STUN（Session Traversal Utilities for NAT）：
  - STUN服务器帮助客户端发现其公共IP地址和端口号，从而使其能够与位于不同网络中的其他客户端进行直接通信。
  - 工作原理：客户端向STUN服务器发送请求，STUN服务器返回客户端的公共IP地址和端口号。客户端使用这些信息与其他客户端进行通信。
2. TURN（Traversal Using Relays around NAT）：
  - TURN服务器充当中继服务器，帮助客户端在无法直接建立点对点连接时进行通信。
  - 工作原理：客户端向TURN服务器发送请求，要求中继其数据。TURN服务器接收到请求后，转发客户端之间的数据。
3. ICE（Interactive Connectivity Establishment）：
  - ICE是一种框架，结合使用STUN和TURN来找到最佳的连接路径。
  - 工作原理：客户端收集多个候选连接（包括直接连接和通过TURN服务器的中继连接），并尝试使用这些候选连接进行通信。ICE选择最优的连接路径。
NAT类型
- 全锥形NAT（Full Cone NAT）：任何外部主机都可以通过NAT设备的公共IP地址和端口号与内部主机通信。
- 受限锥形NAT（Restricted Cone NAT）：只有内部主机之前通信过的外部主机才能通过NAT设备的公共IP地址和端口号与内部主机通信。
- 端口受限锥形NAT（Port Restricted Cone NAT）：只有内部主机之前通信过的外部主机，并且使用相同端口号，才能通过NAT设备的公共IP地址和端口号与内部主机通信。
- 对称NAT（Symmetric NAT）：每个内部主机与外部主机的通信会使用不同的公共IP地址和端口号，只有特定的外部主机才能与内部主机通信。
NAT 对 P2P的影响
- 地址转换：NAT设备会隐藏内部网络的私有IP地址，使得外部主机无法直接与内部主机通信。
- 端口映射：NAT设备会动态分配端口号，使得外部主机无法确定内部主机的实际端口号。
- 对称NAT：对称NAT类型尤其难以穿越，因为每个连接使用不同的公共IP地址和端口号。

防火墙
防火墙对P2P连接的影响：
- 阻止入站连接：防火墙通常会阻止未经授权的入站连接，导致外部主机无法直接与内部主机通信。
- 端口封锁：防火墙可能会封锁特定端口，阻止P2P应用程序使用这些端口进行通信。
- 协议限制：防火墙可能会限制特定协议（如UDP），而P2P应用程序通常依赖这些协议进行通信。

STUN的工作原理
STUN的工作原理
STUN协议的工作原理基于客户端向STUN服务器发送请求，STUN服务器返回客户端的公共IP地址和端口号。具体步骤如下：
1. 客户端发送STUN请求：
  - 客户端向STUN服务器发送一个STUN请求消息。这个请求通常是通过UDP协议发送的，因为UDP是无连接的，适合这种简单的请求-响应模式。
  - 请求消息包含客户端的本地IP地址和端口号。
2. STUN服务器接收请求：
  - STUN服务器接收到请求后，会记录下请求的源IP地址和端口号。这些信息是客户端在NAT设备后的公共IP地址和端口号。
3. STUN服务器发送响应：
  - STUN服务器将记录的源IP地址和端口号封装在响应消息中，并发送回客户端。
  - 响应消息包含客户端的公共IP地址和端口号。
4. 客户端接收响应：
  - 客户端接收到STUN服务器的响应消息后，从中提取出公共IP地址和端口号。
  - 客户端现在知道了其在NAT设备后的公共网络地址，可以使用这些信息与其他客户端进行通信。

STUN的请求和响应示例
以下是一个简单的STUN请求和响应示例：
STUN请求：
复制代码
Client -> STUN Server: STUN Request (from local IP: 192.168.1.2, local port: 54321)
STUN响应：
复制代码
STUN Server -> Client: STUN Response (public IP: 203.0.113.5, public port: 12345)
在这个示例中，客户端的本地IP地址是192.168.1.2，本地端口号是54321。STUN服务器接收到请求后，记录下请求的源IP地址203.0.113.5和端口号12345，并将这些信息返回给客户端。

优势：
1. 简单高效：STUN协议非常简单，只需要发送一个请求并接收一个响应即可完成公共IP地址和端口号的发现。
2. 低延迟：由于STUN使用UDP协议，通信延迟较低，适合实时应用。
3. 广泛支持：STUN协议被广泛支持，许多实时通信应用（如WebRTC）都使用STUN来实现NAT穿越。
局限性：
1. 对称NAT的限制：STUN在对称NAT环境中效果不佳。对称NAT会为每个外部连接分配不同的公共IP地址和端口号，使得STUN无法准确发现公共网络地址。
2. 防火墙的限制：某些防火墙可能会阻止STUN请求，导致STUN无法正常工作。

SDP
- https://segmentfault.com/a/1190000041614675
SDP（Session Description Protocol）指会话描述协议，是一种通用的协议，基于文本，其本身并不属于传输协议，需要依赖其它的传输协议（如 RTP 交换媒体信息。
SDP 主要用来描述多媒体会话，用途包括会话声明、会话邀请、会话初始化等。通俗来讲，它可以表示各端的能力，记录有关于你音频编解码类型、编解码器相关的参数、传输协议等信息。
交换 SDP 时，通信的双方会将接受到的 SDP 和自己的 SDP 进行比较，取出他们之间的交集，这个交集就是协商的结果，也就是最终双方音视频通信时使用的音视频参数及传输协议。

- 发起端发送的 SDP 消息称为 Offer，接收端发送的 SDP 消息称为 Answer

信令与信令服务器
[图片]
- 发起端发送 Offer SDP 和接收端接受 Answer SDP，要怎么发给对方呢？这个过程还需要一种机制来协调通信并发送控制消息，这个过程就称为信令。
信令服务器
信令对应的服务器就叫信令服务器，作为中间人帮助建立连接
1. 交换 SDP：SDP 包含了对等端的媒体能力（如编解码器、分辨率等）和网络信息。：
  1. 信令的处理，如媒体协商消息的传递
2. 交换 ICE 候选者：ICE 候选者包含了对等端的潜在连接地址（如本地 IP、公网 IP、通过 STUN 获取的 IP 等）。
  1. 管理房间信息。比如用户连接时告诉信令服务器自身的房间号，由信令服务器找到也在该房间号的对等端并开始尝试通信，也通知用户谁加入了房间和离开了房间，通知房间人数是否已满等等，所以也叫信令服务器也叫房间服务器。
  
- WebRTC 并没有规定信令必须使用何种实现，目前业界使用较多的是 WebSocket + JSON/SDP 的方案。其中 WebSocket 用来提供信令传输通道，JSON/SDP 用来封装信令的具体内容。
- 信令服务器用于在两个对等端（peers）之间交换连接信息。这些信息包括 SDP（Session Description Protocol）数据和 ICE（Interactive Connectivity Establishment）候选者。
- 信令服务器本身不处理媒体数据，只负责传递连接建立所需的元数据。
- 在两个用户通信前，首先会向公网的 STUN 服务发送请求获取自己的公网地址，然后通过服务器将各自的公网地址转发给对等端，这样双方就知道了对方的公网地址，根据这个公网地址就可以直接点对点通信了。

具体实例
创建连接
1. 双端（两个客户端连接信令服务器，等待交换信令信息）
node signaling-server.js 
Signaling server is running on ws://0.0.0.0:53378
A new client connected
A new client connected

交换信令信息
- 提交自身的可用协议和状态（SDP 绘画描述协议）
``` offer
{
    "type": "offer",
    "offer":
    {
        "sdp": "v=0\r\no=- 5949851614612336510 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE 0 1\r\na=extmap-allow-mixed\r\na=msid-semantic: WMS d15c6979-87f7-4177-b082-e54548148e46\r\nm=audio 9 UDP/TLS/RTP/SAVPF 111 63 9 0 8 13 110 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:JDVd\r\na=ice-pwd:kXT7EgT23rvctdmdZUSU0dT1\r\na=ice-options:trickle\r\na=fingerprint:sha-256 3B:60:C9:3B:27:5F:74:C9:AF:FE:98:15:A5:80:BA:FB:F4:09:8D:2A:EB:8D:13:1D:56:94:C3:5F:6C:D5:B7:B6\r\na=setup:actpass\r\na=mid:0\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 b8e4fae9-2614-4222-8eb2-73c42d5594d6\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:111 opus/48000/2\r\na=rtcp-fb:111 transport-cc\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:63 red/48000/2\r\na=fmtp:63 111/111\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:110 telephone-event/48000\r\na=rtpmap:126 telephone-event/8000\r\na=ssrc:3437501515 cname:MxV7vls3AO3u1lWD\r\na=ssrc:3437501515 msid:d15c6979-87f7-4177-b082-e54548148e46 b8e4fae9-2614-4222-8eb2-73c42d5594d6\r\nm=video 9 UDP/TLS/RTP/SAVPF 96 97 102 103 104 105 106 107 108 109 127 125 39 40 45 46 98 99 100 101 112 113 116 117 118\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:JDVd\r\na=ice-pwd:kXT7EgT23rvctdmdZUSU0dT1\r\na=ice-options:trickle\r\na=fingerprint:sha-256 3B:60:C9:3B:27:5F:74:C9:AF:FE:98:15:A5:80:BA:FB:F4:09:8D:2A:EB:8D:13:1D:56:94:C3:5F:6C:D5:B7:B6\r\na=setup:actpass\r\na=mid:1\r\na=extmap:14 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:13 urn:3gpp:video-orientation\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:5 http://www.webrtc.org/experiments/rtp-hdrext/playout-delay\r\na=extmap:6 http://www.webrtc.org/experiments/rtp-hdrext/video-content-type\r\na=extmap:7 http://www.webrtc.org/experiments/rtp-hdrext/video-timing\r\na=extmap:8 http://www.webrtc.org/experiments/rtp-hdrext/color-space\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=extmap:10 urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id\r\na=extmap:11 urn:ietf:params:rtp-hdrext:sdes:repaired-rtp-stream-id\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:96 VP8/90000\r\na=rtcp-fb:96 goog-remb\r\na=rtcp-fb:96 transport-cc\r\na=rtcp-fb:96 ccm fir\r\na=rtcp-fb:96 nack\r\na=rtcp-fb:96 nack pli\r\na=rtpmap:97 rtx/90000\r\na=fmtp:97 apt=96\r\na=rtpmap:102 H264/90000\r\na=rtcp-fb:102 goog-remb\r\na=rtcp-fb:102 transport-cc\r\na=rtcp-fb:102 ccm fir\r\na=rtcp-fb:102 nack\r\na=rtcp-fb:102 nack pli\r\na=fmtp:102 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42001f\r\na=rtpmap:103 rtx/90000\r\na=fmtp:103 apt=102\r\na=rtpmap:104 H264/90000\r\na=rtcp-fb:104 goog-remb\r\na=rtcp-fb:104 transport-cc\r\na=rtcp-fb:104 ccm fir\r\na=rtcp-fb:104 nack\r\na=rtcp-fb:104 nack pli\r\na=fmtp:104 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42001f\r\na=rtpmap:105 rtx/90000\r\na=fmtp:105 apt=104\r\na=rtpmap:106 H264/90000\r\na=rtcp-fb:106 goog-remb\r\na=rtcp-fb:106 transport-cc\r\na=rtcp-fb:106 ccm fir\r\na=rtcp-fb:106 nack\r\na=rtcp-fb:106 nack pli\r\na=fmtp:106 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\na=rtpmap:107 rtx/90000\r\na=fmtp:107 apt=106\r\na=rtpmap:108 H264/90000\r\na=rtcp-fb:108 goog-remb\r\na=rtcp-fb:108 transport-cc\r\na=rtcp-fb:108 ccm fir\r\na=rtcp-fb:108 nack\r\na=rtcp-fb:108 nack pli\r\na=fmtp:108 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42e01f\r\na=rtpmap:109 rtx/90000\r\na=fmtp:109 apt=108\r\na=rtpmap:127 H264/90000\r\na=rtcp-fb:127 goog-remb\r\na=rtcp-fb:127 transport-cc\r\na=rtcp-fb:127 ccm fir\r\na=rtcp-fb:127 nack\r\na=rtcp-fb:127 nack pli\r\na=fmtp:127 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=4d001f\r\na=rtpmap:125 rtx/90000\r\na=fmtp:125 apt=127\r\na=rtpmap:39 H264/90000\r\na=rtcp-fb:39 goog-remb\r\na=rtcp-fb:39 transport-cc\r\na=rtcp-fb:39 ccm fir\r\na=rtcp-fb:39 nack\r\na=rtcp-fb:39 nack pli\r\na=fmtp:39 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=4d001f\r\na=rtpmap:40 rtx/90000\r\na=fmtp:40 apt=39\r\na=rtpmap:45 AV1/90000\r\na=rtcp-fb:45 goog-remb\r\na=rtcp-fb:45 transport-cc\r\na=rtcp-fb:45 ccm fir\r\na=rtcp-fb:45 nack\r\na=rtcp-fb:45 nack pli\r\na=fmtp:45 level-idx=5;profile=0;tier=0\r\na=rtpmap:46 rtx/90000\r\na=fmtp:46 apt=45\r\na=rtpmap:98 VP9/90000\r\na=rtcp-fb:98 goog-remb\r\na=rtcp-fb:98 transport-cc\r\na=rtcp-fb:98 ccm fir\r\na=rtcp-fb:98 nack\r\na=rtcp-fb:98 nack pli\r\na=fmtp:98 profile-id=0\r\na=rtpmap:99 rtx/90000\r\na=fmtp:99 apt=98\r\na=rtpmap:100 VP9/90000\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=fmtp:100 profile-id=2\r\na=rtpmap:101 rtx/90000\r\na=fmtp:101 apt=100\r\na=rtpmap:112 H264/90000\r\na=rtcp-fb:112 goog-remb\r\na=rtcp-fb:112 transport-cc\r\na=rtcp-fb:112 ccm fir\r\na=rtcp-fb:112 nack\r\na=rtcp-fb:112 nack pli\r\na=fmtp:112 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=64001f\r\na=rtpmap:113 rtx/90000\r\na=fmtp:113 apt=112\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 rtx/90000\r\na=fmtp:117 apt=116\r\na=rtpmap:118 ulpfec/90000\r\na=ssrc-group:FID 583334944 2876421606\r\na=ssrc:583334944 cname:MxV7vls3AO3u1lWD\r\na=ssrc:583334944 msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\na=ssrc:2876421606 cname:MxV7vls3AO3u1lWD\r\na=ssrc:2876421606 msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\n",
        "type": "offer"
    }
}
```

```json- answer
{
    "type": "answer",
    "answer":
    {
        "sdp": "v=0\r\no=- 6625302972082072315 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE 0 1\r\na=extmap-allow-mixed\r\na=msid-semantic: WMS d15c6979-87f7-4177-b082-e54548148e46\r\nm=audio 9 UDP/TLS/RTP/SAVPF 111 63 9 0 8 13 110 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:gZTj\r\na=ice-pwd:G6LOfaoAJg6YaajAUA1WmhkR\r\na=ice-options:trickle\r\na=fingerprint:sha-256 49:11:EC:20:6B:94:DA:60:BC:00:C5:D1:A2:38:AF:7E:E0:19:A5:F3:E5:61:9E:5F:06:5D:FD:41:9A:E5:AE:0D\r\na=setup:active\r\na=mid:0\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 b8e4fae9-2614-4222-8eb2-73c42d5594d6\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:111 opus/48000/2\r\na=rtcp-fb:111 transport-cc\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:63 red/48000/2\r\na=fmtp:63 111/111\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:110 telephone-event/48000\r\na=rtpmap:126 telephone-event/8000\r\na=ssrc:1889325369 cname:j2CRPSmMvx2vVnI/\r\nm=video 9 UDP/TLS/RTP/SAVPF 96 97 102 103 104 105 106 107 108 109 127 125 39 40 45 46 98 99 100 101 112 113 116 117 118\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:gZTj\r\na=ice-pwd:G6LOfaoAJg6YaajAUA1WmhkR\r\na=ice-options:trickle\r\na=fingerprint:sha-256 49:11:EC:20:6B:94:DA:60:BC:00:C5:D1:A2:38:AF:7E:E0:19:A5:F3:E5:61:9E:5F:06:5D:FD:41:9A:E5:AE:0D\r\na=setup:active\r\na=mid:1\r\na=extmap:14 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:13 urn:3gpp:video-orientation\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:5 http://www.webrtc.org/experiments/rtp-hdrext/playout-delay\r\na=extmap:6 http://www.webrtc.org/experiments/rtp-hdrext/video-content-type\r\na=extmap:7 http://www.webrtc.org/experiments/rtp-hdrext/video-timing\r\na=extmap:8 http://www.webrtc.org/experiments/rtp-hdrext/color-space\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=extmap:10 urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id\r\na=extmap:11 urn:ietf:params:rtp-hdrext:sdes:repaired-rtp-stream-id\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:96 VP8/90000\r\na=rtcp-fb:96 goog-remb\r\na=rtcp-fb:96 transport-cc\r\na=rtcp-fb:96 ccm fir\r\na=rtcp-fb:96 nack\r\na=rtcp-fb:96 nack pli\r\na=rtpmap:97 rtx/90000\r\na=fmtp:97 apt=96\r\na=rtpmap:102 H264/90000\r\na=rtcp-fb:102 goog-remb\r\na=rtcp-fb:102 transport-cc\r\na=rtcp-fb:102 ccm fir\r\na=rtcp-fb:102 nack\r\na=rtcp-fb:102 nack pli\r\na=fmtp:102 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42001f\r\na=rtpmap:103 rtx/90000\r\na=fmtp:103 apt=102\r\na=rtpmap:104 H264/90000\r\na=rtcp-fb:104 goog-remb\r\na=rtcp-fb:104 transport-cc\r\na=rtcp-fb:104 ccm fir\r\na=rtcp-fb:104 nack\r\na=rtcp-fb:104 nack pli\r\na=fmtp:104 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42001f\r\na=rtpmap:105 rtx/90000\r\na=fmtp:105 apt=104\r\na=rtpmap:106 H264/90000\r\na=rtcp-fb:106 goog-remb\r\na=rtcp-fb:106 transport-cc\r\na=rtcp-fb:106 ccm fir\r\na=rtcp-fb:106 nack\r\na=rtcp-fb:106 nack pli\r\na=fmtp:106 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\na=rtpmap:107 rtx/90000\r\na=fmtp:107 apt=106\r\na=rtpmap:108 H264/90000\r\na=rtcp-fb:108 goog-remb\r\na=rtcp-fb:108 transport-cc\r\na=rtcp-fb:108 ccm fir\r\na=rtcp-fb:108 nack\r\na=rtcp-fb:108 nack pli\r\na=fmtp:108 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42e01f\r\na=rtpmap:109 rtx/90000\r\na=fmtp:109 apt=108\r\na=rtpmap:127 H264/90000\r\na=rtcp-fb:127 goog-remb\r\na=rtcp-fb:127 transport-cc\r\na=rtcp-fb:127 ccm fir\r\na=rtcp-fb:127 nack\r\na=rtcp-fb:127 nack pli\r\na=fmtp:127 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=4d001f\r\na=rtpmap:125 rtx/90000\r\na=fmtp:125 apt=127\r\na=rtpmap:39 H264/90000\r\na=rtcp-fb:39 goog-remb\r\na=rtcp-fb:39 transport-cc\r\na=rtcp-fb:39 ccm fir\r\na=rtcp-fb:39 nack\r\na=rtcp-fb:39 nack pli\r\na=fmtp:39 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=4d001f\r\na=rtpmap:40 rtx/90000\r\na=fmtp:40 apt=39\r\na=rtpmap:45 AV1/90000\r\na=rtcp-fb:45 goog-remb\r\na=rtcp-fb:45 transport-cc\r\na=rtcp-fb:45 ccm fir\r\na=rtcp-fb:45 nack\r\na=rtcp-fb:45 nack pli\r\na=fmtp:45 level-idx=5;profile=0;tier=0\r\na=rtpmap:46 rtx/90000\r\na=fmtp:46 apt=45\r\na=rtpmap:98 VP9/90000\r\na=rtcp-fb:98 goog-remb\r\na=rtcp-fb:98 transport-cc\r\na=rtcp-fb:98 ccm fir\r\na=rtcp-fb:98 nack\r\na=rtcp-fb:98 nack pli\r\na=fmtp:98 profile-id=0\r\na=rtpmap:99 rtx/90000\r\na=fmtp:99 apt=98\r\na=rtpmap:100 VP9/90000\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=fmtp:100 profile-id=2\r\na=rtpmap:101 rtx/90000\r\na=fmtp:101 apt=100\r\na=rtpmap:112 H264/90000\r\na=rtcp-fb:112 goog-remb\r\na=rtcp-fb:112 transport-cc\r\na=rtcp-fb:112 ccm fir\r\na=rtcp-fb:112 nack\r\na=rtcp-fb:112 nack pli\r\na=fmtp:112 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=64001f\r\na=rtpmap:113 rtx/90000\r\na=fmtp:113 apt=112\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 rtx/90000\r\na=fmtp:117 apt=116\r\na=rtpmap:118 ulpfec/90000\r\na=ssrc-group:FID 1692434634 1983657469\r\na=ssrc:1692434634 cname:j2CRPSmMvx2vVnI/\r\na=ssrc:1983657469 cname:j2CRPSmMvx2vVnI/\r\n",
        "type": "answer"
    }
}
```
- 信令服务器代码
const WebSocket = require('ws');

// 创建 WebSocket 服务器并监听 0.0.0.0:53378
const wss = new WebSocket.Server({ host: '0.0.0.0', port: 53378 });

wss.on('connection', function connection(ws) {
  console.log('A new client connected');

  ws.on('message', function incoming(message) {
    console.log('received: %s', message);

    // 解析接收到的消息
    let parsedMessage;
    try {
      parsedMessage = JSON.parse(message);
    } catch (error) {
      console.error('Error parsing message:', error);
      return;
    }

    // 广播消息给所有连接的客户端
    wss.clients.forEach(function each(client) {
      if (client !== ws && client.readyState === WebSocket.OPEN) {
        client.send(JSON.stringify(parsedMessage));
      }
    });
  });

  ws.on('close', function close() {
    console.log('Client disconnected');
  });
});

console.log('Signaling server is running on ws://0.0.0.0:53378');

交换Candidate （候选者交换）
- Candidate数据（本地IP地址、公网IP地址、Relay服务端分配的地址）
[
    {
        "type": "candidate",
        "candidate":
        {
            "candidate": "candidate:3096071039 1 udp 2122260223 10.25.39.1 60751 typ host generation 0 ufrag N/KY network-id 1 network-cost 10",
            "sdpMid": "0",
            "sdpMLineIndex": 0,
            "usernameFragment": "N/KY"
        }
    },
    {
        "type": "candidate",
        "candidate":
        {
            "candidate": "candidate:3096071039 1 udp 2122260223 10.25.39.1 57043 typ host generation 0 ufrag N/KY network-id 1 network-cost 10",
            "sdpMid": "1",
            "sdpMLineIndex": 1,
            "usernameFragment": "N/KY"
        }
    },
    {
        "type": "candidate",
        "candidate":
        {
            "candidate": "candidate:2649982101 1 udp 1686052607 219.142.117.143 2213 typ srflx raddr 10.25.39.1 rport 57043 generation 0 ufrag N/KY network-id 1 network-cost 10",
            "sdpMid": "1",
            "sdpMLineIndex": 1,
            "usernameFragment": "N/KY"
        }
    },
    {
        "type": "candidate",
        "candidate":
        {
            "candidate": "candidate:2649982101 1 udp 1686052607 219.142.117.143 2212 typ srflx raddr 10.25.39.1 rport 60751 generation 0 ufrag N/KY network-id 1 network-cost 10",
            "sdpMid": "0",
            "sdpMLineIndex": 0,
            "usernameFragment": "N/KY"
        }
    }
]

进行P2P连接
[图片]
双方建立P2P的流程
[图片]
1. 双方先调用 getUserMedia 打开本地摄像头；
2. 向信令服务器发送加入请求；
3. Peer B 接收到 Peer A 发送的 offer SDP 对象，并通过PeerConnection的SetLocalDescription方法保存 Answer SDP 对象并将它通过信令服务器发送给 Peer A。SDP以JSON的形式发送
4. 在 SDP 信息的 offer/answer 流程中，Peer A 和 Peer B 已经根据 SDP 信息创建好相应的音频 Channel 和视频 Channel，并开启Candidate 数据的收集，Candidate数据（本地IP地址、公网IP地址、Relay服务端分配的地址）。
5. 当 Peer A 收集到 Candidate  信息后通过信令服务器发送给 Peer B。同样的过程 Peer B 对 Peer A 也会再发送一次。

流媒体传输机制
流媒体传输的底层机制
WebRTC 使用以下技术和协议来实现流媒体的传输：
1. RTP（实时传输协议）：
RTP 是用于在互联网上传输音频和视频的协议。WebRTC 使用 RTP 来传输媒体流。
2. SRTP（安全实时传输协议）：
为了确保传输的安全性，WebRTC 使用 SRTP 对媒体流进行加密。
3. STUN（会话穿越实用程序）和 TURN（传输中继）：
这些协议用于 NAT（网络地址转换）穿越，帮助对等端找到彼此并建立连接。STUN 服务器用于获取公共 IP 地址和端口，而 TURN 服务器用于在直接连接不可行时中继流量。
4. DTLS（数据报传输层安全协议）：
用于加密 WebRTC 信令和数据通道的传输，确保数据的机密性和完整性。
5. SCTP（流控制传输协议）：
用于 WebRTC 数据通道，支持可靠的数据传输。
通过这些协议和技术，WebRTC 实现了高效、低延迟的端到端流媒体传输。整个过程从获取本地媒体流、创建和交换 SDP、交换 ICE 候选者，到最终建立连接并开始传输，都是为了确保实时通信的质量和可靠性。


WebSocket 和 WebRTC的区别
1. WebSocket是一种网络通信协议，允许在Web服务器和客户端之间建立持久的连接，并进行双向数据传输。而WebRTC，全称为Web实时通信，是一种支持实时音视频通信的开放标准，它允许在无插件的浏览器之间进行点对点（P2P）通信。
2. 通信方式：WebSocket是客户端与服务器之间的双向通信协议。一旦建立连接，服务器和客户端都可以主动发送数据。而WebRTC是基于P2P的实时通信技术，允许浏览器之间直接进行音视频流传输，无需经过服务器转发。

WebRTC在浏览器中的应用场景
WebRTC的API确实是默认嵌入在现代浏览器中的JavaScript环境里，这意味着你可以直接在JavaScript代码中使用WebRTC的相关对象和方法，而无需额外的库或插件。以下是一些关键点，解释为什么你可以直接使用WebRTC的API：
1. 浏览器支持：
现代浏览器（如Google Chrome、Mozilla Firefox、Microsoft Edge和Safari）都内置了对WebRTC的支持。这些浏览器实现了WebRTC的标准API，使得开发者可以直接在JavaScript中调用这些API。
2. 标准化API：
WebRTC是由W3C（World Wide Web Consortium）和IETF（Internet Engineering Task Force）共同制定的标准。标准化的API确保了跨浏览器的一致性，使得开发者可以编写一次代码，在多个浏览器中运行。
3. 内置对象和方法：
WebRTC的API包括一组内置的JavaScript对象和方法，如RTCPeerConnection、getUserMedia、RTCDataChannel等。这些对象和方法直接在浏览器的JavaScript环境中可用，无需额外加载。

逐帧解析SDP交换和IP交换的过程
[图片]
webRTC涉及的关键词
1. offer：一端发起的请求，内含客户端Amy的SDP信息。
2. answer：对端返回的响应，内含对端Bob的SDP信息
3. setLocalDescription：将本地的SDP信息保存
4. setRemoteDescription：将对端发送的offer中的SDP信息保存起来
5. candidate：这本质是一个json，含有当前客户端各自的「公网、内网、本地」ip和port信息。
6. SDP：SDP（Session Description Protocol）指会话描述协议，是一种通用的协议，基于文本，其本身并不属于传输协议。SDP是一个字符串编码，表示各端的能力，包括不限于音视频编码格式，带宽，流控策略等；解决前置思考中，双方能力不匹配问题，通过交换双方 SDP 浏览器会自动选择双方都支持的视频编码格式（取交集）。
1. 建立WebSocket连接 & 点击准备按钮
[图片]
1. 信令事件接收器
signaling.onmessage = e => {
  if (!localStream) {
    console.log('not ready yet');
    return;
  }
  switch (e.data.type) {
    case 'offer':
      handleOffer(e.data);
      break;
    case 'answer':
      handleAnswer(e.data);
      break;
    case 'candidate':
      handleCandidate(e.data);
      break;
    case 'ready':
      // A second tab joined. This tab will initiate a call unless in a call already.
      if (pc) {
        console.log('already in call, ignoring');
        return;
      }
      makeCall();
      break;
    case 'bye':
      if (pc) {
        hangup();
      }
      break;
    default:
      console.log('unhandled', e);
      break;
  }
};
2. start按钮点击事件
startButton.onclick = async () => {
  localStream = await navigator.mediaDevices.getUserMedia({audio: true, video: true});
  localVideo.srcObject = localStream;

  startButton.disabled = true;
  hangupButton.disabled = false;

  signaling.postMessage({type: 'ready'});
};
3. 当用户进入页面之后就先连接WebSocket服务器
4. 当一端点击start之后，向信令服务器发送ready请求。
5. 如果对端的本地流没有建立好，对端就不响应。只有对端也点击start，对端向信令服务器发送ready请求，此时当前客户端接收webSockct的事件执行makeCall()双方才开始准备进行SDP和candidate交换。

2. offer创建并发送
1. offer只负责传SDP，传输本地媒体格式信息
// 创建webRTC连接的实例
function createPeerConnection() {
  pc = new RTCPeerConnection();
  
  // ⚠️最关键的地方！ 给onicecandidate成员变量绑定一个触发事件（函数指针）
  pc.onicecandidate = function iceCallback(e) {
    const message = {
      type: 'candidate',
      candidate: null,
    };
    if (e.candidate) {
      message.candidate = e.candidate.candidate;
      message.sdpMid = e.candidate.sdpMid;
      message.sdpMLineIndex = e.candidate.sdpMLineIndex;
    }
    console.log('onicecandidate的回调函数执行', message);
    signaling.postMessage(message);
  };
  pc.ontrack = e => remoteVideo.srcObject = e.streams[0];
  localStream.getTracks().forEach(track => pc.addTrack(track, localStream));
}

// makeCall sdp和candidate交互的起点
async function makeCall() {
  console.log('start ====> createPeerConnection');
  await createPeerConnection();
  console.log('end <==== createPeerConnection');

  
  const offer = await pc.createOffer();
  signaling.postMessage({type: 'offer', sdp: offer.sdp});
  console.log('发送offer', JSON.stringify(offer));

  await pc.setLocalDescription(offer);
  console.log('将本地的sdp信息保存起来');
}
3. 绑定的事件触发执行回调（发送candidate交换候选人）
1. iceCallback是个回调函数将从STUN获取到的公网IP和Port发送到信令服务器
2. iceCallback的触发时机是在pc.setLocalDescription(offer)中（本地SDP保存之后会触发ice交互的逻辑）
[图片]
4. 回调函数的具体逻辑（触发JS事件，向对端发送candidate）
1. STUN服务器返回公网IP信息
2. event变量负责接收STUN返回的公网IP信息
  1. 然后向信令服务器发送消息（发送candidate候选者，多个ip和port「本地、内网、公网」，存在多个候选者）
pc.onicecandidate = function iceCallback(e) {
    const message = {
      type: 'candidate',
      candidate: null,
    };
    // 封装json消息
    // STUN处理的IP信息传入e这个变量中，这是一个事件
    if (e.candidate) {
      message.candidate = e.candidate.candidate;
      message.sdpMid = e.candidate.sdpMid;
      message.sdpMLineIndex = e.candidate.sdpMLineIndex;
    }
    console.log('onicecandidate的回调函数执行', message);
    signaling.postMessage(message);
  };
  pc.ontrack = e => remoteVideo.srcObject = e.streams[0];
  localStream.getTracks().forEach(track => pc.addTrack(track, localStream));
3. candidate：向信令服务器（webSocket）发送的JSON数据格式（拿了一个内网的IP和PORT作为例子放在这里了）
{
    "type": "candidate",
    "candidate": "candidate:2789144953 1 udp 2122129151 10.18.117.180 51018 typ host generation 0 ufrag ddSd network-id 3 network-cost 50",
    "sdpMid": "0",
    "sdpMLineIndex": 0
}
5. webSocket进行广播📢
1. websocket处理事件。当接收到消息的时候触发事件，然后执行业务逻辑（在incoming函数中），最后将处理后的消息或者是原消息直接广播给其他客户端。
2. 注意⚠️：这里不一定是广播，考虑到我们的业务场景，我们要向指定的ip发送消息，那就在遍历的过程中做一层过滤，向指定的ip发送消息就好了。
// 创建 WebSocket 服务器并监听 0.0.0.0:53378
const wss = new WebSocket.Server({ host: '0.0.0.0', port: 53378 });

wss.on('connection', function connection(ws,req) {
  console.log('A new client connected');
  const ip = req.socket.remoteAddress;
  const port = req.socket.remotePort;
  console.log(`New client connected from IP: ${ip} PORT:${port}`);

  // 当获取到事件之后进行触发操作
  ws.on('message', function incoming(message) {
    console.log('received: %s', message);

    // 解析接收到的消息
    let parsedMessage;
    try {
      parsedMessage = JSON.parse(message);
    } catch (error) {
      console.error('Error parsing message:', error);
      return;
    }

    // 广播消息给所有连接的客户端
    // 向所有"处于准备" 同时 "不是自己" 的其他客户端发送广播消息
    wss.clients.forEach(function each(client) {
      if (client !== ws && client.readyState === WebSocket.OPEN) {
        client.send(JSON.stringify(parsedMessage));
      }
    });
  });

  ws.on('close', function close() {
    console.log('Client disconnected');
  });
});

console.log('Signaling server is running on ws://0.0.0.0:53378');

6. 客户端接受SDP 和 candidate 信息 进行处理
1. 注意⚠️：
  1. candidate信息的发送是setLocalDescription(offer)后，交由回调函数执行的，回调函数由onicecandidate成员变量进行绑定（本质是一个函数指针）。
  2. 发送offer的send操作是早于 candidate 的发送的。
  3. WebSocket中消息的发送和接收是基于TCP的，TCP协议保证了消息的顺序性，所以对端一定先接收到offer再接收到candidate信息
  4. ❗️❗️❗️信令服务器广播出去的ip 和 port 注意。一组ip 和 port 组成的信息称为一个candidate，candadate有多组，包含「本地ip&port、内网ip&port、公网ip&port」。广播出去的candidate有多个，也就是说有多次广播操作。多次广播操作是onicecandidate绑定的回调函数触发的。
    
7. 处理offer 和 candidate
1. signaling.onmessage处理信令服务器（WebSocket）触发的事件。信令服务器会返回一个json。
2. 根据json中的type字段，分别调用 handleOffer 和 handleCandidate 处理SDP信息和candaite信息（对端的ip和port）
signaling.onmessage = e => {
  if (!localStream) {
    console.log('not ready yet');
    return;
  }
  switch (e.data.type) {
    case 'offer':
      handleOffer(e.data);
      break;
    case 'answer':
      handleAnswer(e.data);
      break;
    case 'candidate':
      handleCandidate(e.data);
      break;
    case 'ready':
      // A second tab joined. This tab will initiate a call unless in a call already.
      if (pc) {
        console.log('already in call, ignoring');
        return;
      }
      makeCall();
      break;
    case 'bye':
      if (pc) {
        hangup();
      }
      break;
    default:
      console.log('unhandled', e);
      break;
  }
};

3. WebSocket返回的json示例：
{
    "type": "candidate",
    "candidate": "candidate:2789144953 1 udp 2122129151 10.18.117.180 51018 typ host generation 0 ufrag ddSd network-id 3 network-cost 50",
    "sdpMid": "0",
    "sdpMLineIndex": 0
}

{
    "type": "offer",
    "offer":
    {
        "sdp": "v=0\r\no=- 5949851614612336510 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE 0 1\r\na=extmap-allow-mixed\r\na=msid-semantic: WMS d15c6979-87f7-4177-b082-e54548148e46\r\nm=audio 9 UDP/TLS/RTP/SAVPF 111 63 9 0 8 13 110 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:JDVd\r\na=ice-pwd:kXT7EgT23rvctdmdZUSU0dT1\r\na=ice-options:trickle\r\na=fingerprint:sha-256 3B:60:C9:3B:27:5F:74:C9:AF:FE:98:15:A5:80:BA:FB:F4:09:8D:2A:EB:8D:13:1D:56:94:C3:5F:6C:D5:B7:B6\r\na=setup:actpass\r\na=mid:0\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 b8e4fae9-2614-4222-8eb2-73c42d5594d6\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:111 opus/48000/2\r\na=rtcp-fb:111 transport-cc\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:63 red/48000/2\r\na=fmtp:63 111/111\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:110 telephone-event/48000\r\na=rtpmap:126 telephone-event/8000\r\na=ssrc:3437501515 cname:MxV7vls3AO3u1lWD\r\na=ssrc:3437501515 msid:d15c6979-87f7-4177-b082-e54548148e46 b8e4fae9-2614-4222-8eb2-73c42d5594d6\r\nm=video 9 UDP/TLS/RTP/SAVPF 96 97 102 103 104 105 106 107 108 109 127 125 39 40 45 46 98 99 100 101 112 113 116 117 118\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:JDVd\r\na=ice-pwd:kXT7EgT23rvctdmdZUSU0dT1\r\na=ice-options:trickle\r\na=fingerprint:sha-256 3B:60:C9:3B:27:5F:74:C9:AF:FE:98:15:A5:80:BA:FB:F4:09:8D:2A:EB:8D:13:1D:56:94:C3:5F:6C:D5:B7:B6\r\na=setup:actpass\r\na=mid:1\r\na=extmap:14 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:13 urn:3gpp:video-orientation\r\na=extmap:3 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:5 http://www.webrtc.org/experiments/rtp-hdrext/playout-delay\r\na=extmap:6 http://www.webrtc.org/experiments/rtp-hdrext/video-content-type\r\na=extmap:7 http://www.webrtc.org/experiments/rtp-hdrext/video-timing\r\na=extmap:8 http://www.webrtc.org/experiments/rtp-hdrext/color-space\r\na=extmap:4 urn:ietf:params:rtp-hdrext:sdes:mid\r\na=extmap:10 urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id\r\na=extmap:11 urn:ietf:params:rtp-hdrext:sdes:repaired-rtp-stream-id\r\na=sendrecv\r\na=msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:96 VP8/90000\r\na=rtcp-fb:96 goog-remb\r\na=rtcp-fb:96 transport-cc\r\na=rtcp-fb:96 ccm fir\r\na=rtcp-fb:96 nack\r\na=rtcp-fb:96 nack pli\r\na=rtpmap:97 rtx/90000\r\na=fmtp:97 apt=96\r\na=rtpmap:102 H264/90000\r\na=rtcp-fb:102 goog-remb\r\na=rtcp-fb:102 transport-cc\r\na=rtcp-fb:102 ccm fir\r\na=rtcp-fb:102 nack\r\na=rtcp-fb:102 nack pli\r\na=fmtp:102 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42001f\r\na=rtpmap:103 rtx/90000\r\na=fmtp:103 apt=102\r\na=rtpmap:104 H264/90000\r\na=rtcp-fb:104 goog-remb\r\na=rtcp-fb:104 transport-cc\r\na=rtcp-fb:104 ccm fir\r\na=rtcp-fb:104 nack\r\na=rtcp-fb:104 nack pli\r\na=fmtp:104 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42001f\r\na=rtpmap:105 rtx/90000\r\na=fmtp:105 apt=104\r\na=rtpmap:106 H264/90000\r\na=rtcp-fb:106 goog-remb\r\na=rtcp-fb:106 transport-cc\r\na=rtcp-fb:106 ccm fir\r\na=rtcp-fb:106 nack\r\na=rtcp-fb:106 nack pli\r\na=fmtp:106 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\na=rtpmap:107 rtx/90000\r\na=fmtp:107 apt=106\r\na=rtpmap:108 H264/90000\r\na=rtcp-fb:108 goog-remb\r\na=rtcp-fb:108 transport-cc\r\na=rtcp-fb:108 ccm fir\r\na=rtcp-fb:108 nack\r\na=rtcp-fb:108 nack pli\r\na=fmtp:108 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=42e01f\r\na=rtpmap:109 rtx/90000\r\na=fmtp:109 apt=108\r\na=rtpmap:127 H264/90000\r\na=rtcp-fb:127 goog-remb\r\na=rtcp-fb:127 transport-cc\r\na=rtcp-fb:127 ccm fir\r\na=rtcp-fb:127 nack\r\na=rtcp-fb:127 nack pli\r\na=fmtp:127 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=4d001f\r\na=rtpmap:125 rtx/90000\r\na=fmtp:125 apt=127\r\na=rtpmap:39 H264/90000\r\na=rtcp-fb:39 goog-remb\r\na=rtcp-fb:39 transport-cc\r\na=rtcp-fb:39 ccm fir\r\na=rtcp-fb:39 nack\r\na=rtcp-fb:39 nack pli\r\na=fmtp:39 level-asymmetry-allowed=1;packetization-mode=0;profile-level-id=4d001f\r\na=rtpmap:40 rtx/90000\r\na=fmtp:40 apt=39\r\na=rtpmap:45 AV1/90000\r\na=rtcp-fb:45 goog-remb\r\na=rtcp-fb:45 transport-cc\r\na=rtcp-fb:45 ccm fir\r\na=rtcp-fb:45 nack\r\na=rtcp-fb:45 nack pli\r\na=fmtp:45 level-idx=5;profile=0;tier=0\r\na=rtpmap:46 rtx/90000\r\na=fmtp:46 apt=45\r\na=rtpmap:98 VP9/90000\r\na=rtcp-fb:98 goog-remb\r\na=rtcp-fb:98 transport-cc\r\na=rtcp-fb:98 ccm fir\r\na=rtcp-fb:98 nack\r\na=rtcp-fb:98 nack pli\r\na=fmtp:98 profile-id=0\r\na=rtpmap:99 rtx/90000\r\na=fmtp:99 apt=98\r\na=rtpmap:100 VP9/90000\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=fmtp:100 profile-id=2\r\na=rtpmap:101 rtx/90000\r\na=fmtp:101 apt=100\r\na=rtpmap:112 H264/90000\r\na=rtcp-fb:112 goog-remb\r\na=rtcp-fb:112 transport-cc\r\na=rtcp-fb:112 ccm fir\r\na=rtcp-fb:112 nack\r\na=rtcp-fb:112 nack pli\r\na=fmtp:112 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=64001f\r\na=rtpmap:113 rtx/90000\r\na=fmtp:113 apt=112\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 rtx/90000\r\na=fmtp:117 apt=116\r\na=rtpmap:118 ulpfec/90000\r\na=ssrc-group:FID 583334944 2876421606\r\na=ssrc:583334944 cname:MxV7vls3AO3u1lWD\r\na=ssrc:583334944 msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\na=ssrc:2876421606 cname:MxV7vls3AO3u1lWD\r\na=ssrc:2876421606 msid:d15c6979-87f7-4177-b082-e54548148e46 31e24686-c6d8-4ca8-aab0-12e6f829fe23\r\n",
        "type": "offer"
    }
}


8. handleOffer
async function handleOffer(offer) {
  if (pc) {
    console.error('existing peerconnection');
    return;
  }
  await createPeerConnection();
  await pc.setRemoteDescription(offer);
  console.log('接收offer setRemoteDescription');

  const answer = await pc.createAnswer();
  signaling.postMessage({type: 'answer', sdp: answer.sdp});
  console.log('创建answer, 向对端返回自己的sdp信息');
  await pc.setLocalDescription(answer);
}
1. （由于TCP的顺序性，一定是先处理offer，再处理UDP）
2. 与上面createOffer的流程类似。但是当前客户端使用setRemoteDescription接收对端传输的offer，然后创建answer（与offer的json格式一致，都是传SDP，只是type字段变成了answer ）返回给对端。
3. pc.setLocalDescription(answer)之后依然执行candidate的交换。
[图片]

9. handleAnswer
async function handleAnswer(answer) {
  if (!pc) {
    console.error('no peerconnection');
    return;
  }
  await pc.setRemoteDescription(answer);
}
1. 与handleOffer目的一致，记录对端的SDP信息。但是不需要再执行onicecandidate绑定的回调函数了，因为没有setLocalDescription(),只需要在handleCandidate()中处理对端的candidate信息（ip & port）即可。

10. handleCandidate
async function handleCandidate(candidate) {
  if (!pc) {
    console.error('no peerconnection');
    return;
  }
  if (!candidate.candidate) {
    await pc.addIceCandidate(null);
  } else {
    await pc.addIceCandidate(candidate);
  }
}
1. 这里的逻辑只负责将对端传来的candidate记录下来。
2. 注意⚠️：candidate消息的发送由onicecandidate绑定的回调函数执行，当前客户端会向对端发送多个candidate信息。所有handleCandidate()会将多个candidate都保存下来，然后有ICE框架选择最合适的candidate

11. 多个candidate
[图片]

1. 我们可以有多个STUN/TURN服务器。ICE框架会进行多次调用，并将得到的candidate发送给对端。
2. 字段解释
  1. Time: 生成候选者的时间（以秒为单位）。
  2. Type: 候选者的类型。
    - host: 本地主机候选者，表示直接从本地网络接口获取的候选者。
    - srflx: 服务器反射候选者（Server Reflexive），通过STUN服务器获取的候选者，表示NAT后的公共IP地址。
    - relay: 中继候选者，通过TURN服务器获取的候选者，用于穿越NAT和防火墙。
  3. Foundation: 候选者的基础标识符，用于区分不同的候选者。
  4. Protocol: 使用的协议，通常是udp或tcp。
  5. Address: 候选者的IP地址。
  6. Port: 候选者的端口号。
  7. Priority: 候选者的优先级，格式为<type preference> | <local preference> | <component ID>。
  8. URL (if present): 如果是srflx或relay候选者，这里会显示STUN或TURN服务器的URL。
  9. relayProtocol (if present): 如果是relay候选者，这里会显示中继协议。

12. P2P的Sample文件
暂时无法在飞书文档外展示此内容
暂时无法在飞书文档外展示此内容
暂时无法在飞书文档外展示此内容


注意：
1. navigator.mediaDevices.getUserMedia获取媒体流的时候需要保证是https或者localhost访问，否则不行
2. 然后由于使用了https，那么信令服务器（WebSocket）也要是wss的形式（需要加入SSL认证）
