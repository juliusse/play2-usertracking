package info.seltenheim.play2.usertracking.actions;

import info.seltenheim.play2.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingService;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class TrackIgnoreAction extends Action<TrackIgnore> {
    @Override
    public Result call(Context context) throws Throwable {

        final UserTrackingService service = UserTrackingPlugin.getUserTrackingService();
        service.ignoreContext(context);

        return delegate.call(context);
    }
}
