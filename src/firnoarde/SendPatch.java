package firnoarde;

import arc.func.Cons2;

@FunctionalInterface
public interface SendPatch {
    void accept(Object packet, Cons2<Object, Boolean> send);
}