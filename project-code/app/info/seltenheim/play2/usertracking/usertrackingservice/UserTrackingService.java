package info.seltenheim.play2.usertracking.usertrackingservice;

import play.mvc.Http.Context;

public interface UserTrackingService {
    
    void track(Context context, String controller);
    void track(Context context, String controller, String action);
}
