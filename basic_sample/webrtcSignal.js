const localVideo = document.getElementById('localVideo');
const remoteVideo = document.getElementById('remoteVideo');
const connectButton = document.getElementById('connectButton');
const signalingServer = new WebSocket('ws://8.130.14.29:53378');
let localStream;
let peerConnection;
const configuration = {
    iceServers: [
        {
            urls: 'stun:8.130.14.29:53478',
            username: 'ZZGEDA',
            credential: 'zZ-szshyjbz16D',
            realm: 'ZZGEDA.com'
        }
    ]
};

// Handle incoming messages from signaling server
signalingServer.onmessage = async (message) => {
    try {
        console.log('Received message:', message.data);
        const data = JSON.parse(message.data);

        switch (data.type) {
            case "answer":
                await handleAnswer(data.answer);
                break;
            case "offer":
                await handleOffer(data.offer);
                break;
            case "candidate":
                await handleCandidate(data.candidate);
                break;
            default:
                break;
        }
    } catch (error) {
        console.error('Error parsing message data:', error);
    }
};

// 获取本地的流媒体
const createLocalMediaStream = async () => {
    try {
        // getUserMedia 获取摄像头
        // getDisplayMedia 获取屏幕信息
        localStream = await navigator.mediaDevices.getDisplayMedia({
            video: true,
            audio: true,
        });
        localVideo.srcObject = localStream;
    } catch (error) {
        console.error('Error accessing media devices.', error);
    }
};

// Create and send offer
const createOffer = async () => {
    // 创建一个 RTCPeerConnection 对象，用于管理和控制媒体流的传输。
    peerConnection = new RTCPeerConnection(configuration);
    peerConnection.onicecandidate = handleICECandidateEvent;
    peerConnection.ontrack = handleTrackEvent;

    // 将本地媒体流的轨道（tracks）添加到 RTCPeerConnection 对象中。
    localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

    // 创建和交换 SDP
    const offer = await peerConnection.createOffer();
    await peerConnection.setLocalDescription(offer);
    const message = JSON.stringify({ type: 'offer', offer: offer });
    console.log('Sending message:', message);

    // 发送offer
    signalingServer.send(message);
};

// 处理接收到的 offer 并发送 answer
const handleOffer = async (offer) => {

    // 创建 PTCPeerConnection实例
    peerConnection = new RTCPeerConnection(configuration);
    peerConnection.onicecandidate = handleICECandidateEvent;
    peerConnection.ontrack = handleTrackEvent;

    // 获取本地媒体流的轨道（tracks ）并添加到 RTCPeerConnection 中
    localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

    // 接受对端 SDP 并返回 answer
    await peerConnection.setRemoteDescription(new RTCSessionDescription(offer));
    const answer = await peerConnection.createAnswer();
    await peerConnection.setLocalDescription(answer);

    // 发送answer
    console.log("发送answer",answer)
    signalingServer.send(JSON.stringify({ type: 'answer', answer: answer }));
};

// Handle answer
const handleAnswer = async (answer) => {
    // 接受对端 SDP
    console.log("handleAnswer",answer)
    await peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
};

// Handle ICE candidate
const handleICECandidateEvent = (event) => {
    if (event.candidate) {
        signalingServer.send(JSON.stringify({ type: 'candidate', candidate: event.candidate }));
    }
};

// 在远程对等端接收并显示媒体流
const handleTrackEvent = (event) => {
    remoteVideo.srcObject = event.streams[0];
};

// 处理候选者 ICE框架接受
const handleCandidate = async (candidate) => {
    try {
        console.log("handle Candidate",candidate)
        await peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
    } catch (e) {
        console.error('Error adding received ice candidate', e);
    }
};

// Add event listener to the connect button
connectButton.addEventListener('click', async () => {
    await createLocalMediaStream();
    createOffer();
});