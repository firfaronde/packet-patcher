package firnoarde;

import arc.scene.ui.layout.Table;
import arc.struct.IntMap;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.Vars;
import mindustry.net.Net;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Patcher {
    public static void replaceNet() {
        try {
            Net original = Vars.net;
            //PNet patched = new PNet(Reflect.get(original, "provider"));
            PNet patched = new PNet(Vars.platform.getNet());

            for (Field field : Net.class.getDeclaredFields()) {
                field.setAccessible(true);

                int m = field.getModifiers();
                if (Modifier.isStatic(m)) continue;

                Object src = field.get(original);
                Object dst = field.get(patched);

                if (Modifier.isFinal(m)) {
                    copyFinalCollections(src, dst);
                    continue;
                }

                field.set(patched, src);
            }

            Table tb = Vars.ui.hudGroup.find("fps/ping");
            tb.find("ping").visible(()->Vars.net.client());;
            tb.find("tps").visible(()->Vars.net.client());

            Vars.net = patched;
            Log.info("Net replaced!");
        } catch (Exception e) {
            Log.err("Fuck! I cant replace net!");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void copyFinalCollections(Object src, Object dst) {
        if (src instanceof Seq s && dst instanceof Seq d) {
            d.addAll(s);
        }
        else if (src instanceof ObjectMap s && dst instanceof ObjectMap d) {
            d.putAll(s);
        }
        else if (src instanceof IntMap s && dst instanceof IntMap d) {
            d.putAll(s);
        }
    }
}
