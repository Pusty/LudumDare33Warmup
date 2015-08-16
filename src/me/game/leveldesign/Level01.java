package me.game.leveldesign;

import me.engine.entity.EntityBloodSlime;
import me.engine.main.MainClass;
import me.engine.world.LevelScript;
import me.engine.world.World;

public class Level01 extends LevelScript{

	public Level01() {
		super(1);
	}
	public void addEnemy(MainClass m ,World w, int metaID, int x, int z) {
		w.addEntity(new EntityBloodSlime(m,x,z));
	}
}
