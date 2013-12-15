package info.seltenheim.play2.plugins.usertracking.actions;

import info.seltenheim.play2.plugins.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.plugins.usertracking.usertrackingservice.UserTrackingService;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;

public class TrackIgnoreAction extends Action<TrackIgnore> {
    @Override
    public Promise<SimpleResult> call(Context context) throws Throwable {

        final UserTrackingService service = UserTrackingPlugin.getUserTrackingService();
        service.ignoreContext(context);

        return delegate.call(context);
    }
}