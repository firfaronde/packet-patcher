package firnoarde;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class Main extends Mod{

    public Main(){
        Log.info("Loaded constructor.");

        Events.on(ClientLoadEvent.class, e -> {
            Patcher.replaceNet();
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading content.");
    }


}
