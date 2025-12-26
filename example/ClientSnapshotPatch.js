Vars.net.setOutboundOverride((packet, send)=>{
  packet.pointerX = 0;
  packet.pointerY = 0;
  send.get(packet, false); // send packet via udp
});
