package offline;

import java.util.Map;
import java.util.TreeMap;

public class Dictionary extends TreeMap<String, Integer> {

	public Integer put(String key) {
		Integer v = get(key);
		if(v == null) {
			super.put(key, 1);
			return 0;
		}
		else {
			super.put(key, v+1);
			return v+1;
		}
			
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Integer> map) {
		
		for(java.util.Map.Entry<? extends String, ? extends Integer> e: map.entrySet()) {
			if(this.containsKey(e.getKey()))
				put(e.getKey(), this.get(e.getKey())+e.getValue());
			else put(e.getKey(), e.getValue());
		}
	}
}
