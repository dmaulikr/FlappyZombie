package ve.com.edgaralexanderfr.game;

import java.util.Comparator;

public class GameObjectZComparator implements Comparator<GameObject> {
	@Override
	public int compare (GameObject gameObject1, GameObject gameObject2) {
		return gameObject1.getZIndex() - gameObject2.getZIndex();
	}
}
