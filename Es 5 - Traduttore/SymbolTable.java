import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    Map<String, Integer> OffsetMap = new HashMap<>();
    public void insert(String s, int address) {
        if(!OffsetMap.containsValue(address)){
            OffsetMap.put(s,address);
        } else {
            throw new IllegalArgumentException("Riferimento ad una locazione di memoria gia' occupata da un'altra variabile");
        }
    }

    public int lookupAddress (String s) {
        return OffsetMap.getOrDefault(s, -1);
    }
}
