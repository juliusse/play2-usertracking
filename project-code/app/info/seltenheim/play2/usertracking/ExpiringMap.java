package info.seltenheim.play2.usertracking;

import java.util.HashMap;
import java.util.Map;

public class ExpiringMap<A, B> extends HashMap<A, B> {
    private static final long serialVersionUID = 5240734555987000886L;

    private Map<A, Long> entriesAddedAtMap = new HashMap<A,Long>();
    
    private long millisUntilExpiry = 10000; // 10 seconds default

    public long getMillisUntilExpiry() {
        return millisUntilExpiry;
    }

    public void setMillisUntilExpiry(long millisUntilExpiry) {
        this.millisUntilExpiry = millisUntilExpiry;
    }

    @Override
    public B put(A key, B value) {
        executeExpiry();
        entriesAddedAtMap.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }

    private void executeExpiry() {
        final Map<A, Long> entriesAddedAtMapCopy = new HashMap<A,Long>(entriesAddedAtMap);
        final long currentMillis = System.currentTimeMillis();
        
        for(Map.Entry<A, Long> entry : entriesAddedAtMapCopy.entrySet()) {
            if(currentMillis - entry.getValue() > millisUntilExpiry) { //expired
                entriesAddedAtMap.remove(entry.getKey());
                this.remove(entry.getKey());
            }
        }
    } 
}
