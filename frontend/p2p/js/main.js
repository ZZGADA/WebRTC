/*
 *  Copyright (c) 2021 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree.
 */

'use strict';

const startButton = document.getElementById('startButton');
const hangupButton = document.getElementById('hangupButton');
hangupButton.disabled = true;

const localVideo = document.getElementById('localVideo');
const remoteVideo = document.getElementById('remoteVideo');

let pc;
let localStream;

// const signalingInit = new WebSocket('ws://localhost:18081/init');

// signaling.onopen = function() {
//   tellServerClientInformation();
// };

// function getRandomInt(min, max) {
//   return Math.floor(Math.random() * (max - min + 1)) + min;
// }

// function tellServerClientInformation() {
//   const data = {
//     schoolId: 2281,
//     buildingId: 22810,
//     classRoomId: 228100,
//   };

//   const message = {
//     tyep: 'init',
//     data: data
//   };

//   signalingInit.send(JSON.stringify(message));
// }

const signaling = new WebSocket('ws://localhost:18081/broadcast');
signaling.onmessage = e => {
  const data =  JSON.parse(e.data);
  console.log('检查WebSocket返回的Json对象', data);
  if (!localStream) {
    console.log('not ready yet');
    return;
  }
  switch (data.type) {
    case 'offer':
      handleOffer(data);
      break;
    case 'answer':
      handleAnswer(data);
      break;
    case 'candidate':
      handleCandidate(data);
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

startButton.onclick = async () => {
  localStream = await navigator.mediaDevices.getDisplayMedia({ audio: true, video: true });
  localVideo.srcObject = localStream;


  startButton.disabled = true;
  hangupButton.disabled = false;

  const data = {
    schoolId: 2281,
    buildingId: 22810,
    classRoomId: 228100,
  };

  const message = {
    type: 'ready',
    data: data
  };

  signaling.send(JSON.stringify(message));
};

hangupButton.onclick = async () => {
  hangup();
  signaling.send(JSON.stringify({type: 'bye'}));
};

async function hangup() {
  if (pc) {
    pc.close();
    pc = null;
  }
  localStream.getTracks().forEach(track => track.stop());
  localStream = null;
  startButton.disabled = false;
  hangupButton.disabled = true;
};

function createPeerConnection() {
  pc = new RTCPeerConnection();
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
    signaling.send(JSON.stringify(message));
  };
  pc.ontrack = e => remoteVideo.srcObject = e.streams[0];
  localStream.getTracks().forEach(track => pc.addTrack(track, localStream));
}

async function makeCall() {
  console.log('start ====> createPeerConnection');
  createPeerConnection();
  console.log('end <==== createPeerConnection');

  const offer = await pc.createOffer();
  signaling.send(JSON.stringify({ type: 'offer', sdp: offer.sdp }));
  console.log('发送offer', JSON.stringify(offer));


  await pc.setLocalDescription(offer);
  console.log('将本地的sdp信息保存起来');
}

async function handleOffer(offer) {
  if (pc) {
    console.error('existing peerconnection');
    return;
  }
  createPeerConnection();
  await pc.setRemoteDescription(offer);
  console.log('接收offer setRemoteDescription');

  const answer = await pc.createAnswer();
  signaling.send(JSON.stringify({ type: 'answer', sdp: answer.sdp }));
  console.log('创建answer, 向对端返回自己的sdp信息');
  await pc.setLocalDescription(answer);
}

async function handleAnswer(answer) {
  if (!pc) {
    console.error('no peerconnection');
    return;
  }
  await pc.setRemoteDescription(answer);
}

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

