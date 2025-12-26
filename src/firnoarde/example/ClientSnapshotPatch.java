package firnoarde.example;

import firnoarde.PNet;
import mindustry.Vars;
import mindustry.gen.ClientSnapshotCallPacket;

/**
 * This patch hides your pointer from other players
 * */
public class ClientSnapshotPatch {
    public static void load() {
        if(Vars.net instanceof PNet patcher) {
            patcher.setOutboundOverride(ClientSnapshotCallPacket.class, (packet, send)->{
                if(packet instanceof ClientSnapshotCallPacket snapshot) {
                    snapshot.pointerX = 0;
                    snapshot.pointerY = 0;
                }
            });
        }
    }
}
