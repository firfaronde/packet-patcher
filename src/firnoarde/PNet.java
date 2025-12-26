package firnoarde;

import arc.func.Cons;
import arc.func.Cons2;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.Vars;
import mindustry.net.Net;
import mindustry.net.Packet;

public class PNet extends Net{
    ObjectMap<Class<? extends Packet>, SendPatch> sendPatches = new ObjectMap<>();
    // final Cons2<Object, Boolean> = (o,r) -> {};

    public PNet(Net.NetProvider provider) {
        super(provider);
    }

    public void setOutboundOverride(Class<? extends Packet> packet, SendPatch patch) {
        Log.debug("New Outbound override set @", packet);
        if(sendPatches.containsKey(packet))
            sendPatches.remove(packet);
        sendPatches.put(packet, patch);
    }

    // TODO
    public <T> void setInboundOverride(Class<T> packet, Cons<T> l) {
        Log.debug("New Inbound override set @", packet);
        Vars.net.handleClient(packet, l);
    }

    @Override
    public void send(Object object, boolean reliable) {
        if (object instanceof Packet packet) {
            SendPatch p = sendPatches.get(packet.getClass());
            if (p != null) {
                p.accept(packet, super::send);
                Log.debug("@ patched!", packet);
            } else {
                super.send(object, reliable);
            }
        } else {
            super.send(object, reliable);
        }
    }
}
