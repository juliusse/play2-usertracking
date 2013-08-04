package info.seltenheim.play2.usertracking.actions;

import info.seltenheim.play2.usertracking.ExpiringMap;
import info.seltenheim.play2.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Scheduler;

public class TrackUserAction extends Action<TrackUser> {
    private final static String CONTROLLER_ARG = "info.seltenheim.play2.usertracking.ctx.controller";
    private final static String ACTION_ARG = "info.seltenheim.play2.usertracking.ctx.action";
    private static Map<Context, Cancellable> contextLoggingMap = new ExpiringMap<Context, Cancellable>();

    @Override
    public Result call(Context context) throws Throwable {
        String controller = configuration.controller();
        // check in context
        if (controller.isEmpty()) {
            String ctxController = context.args.get(CONTROLLER_ARG).toString();
            if (ctxController != null) {
                controller = ctxController;
            }
        } //write in context 
        else {
            context.args.put(CONTROLLER_ARG, controller);
        }
        
        String action = configuration.action();
        // check in context
        if (action.isEmpty()) {
            String ctxAction = context.args.get(ACTION_ARG).toString();
            if (ctxAction != null) {
                action = ctxAction;
            }
        } //write in context 
        else {
            context.args.put(ACTION_ARG, action);
        }
        
        final ExecuteTracking executeTracking = new ExecuteTracking(context, controller, action);

        final ActorSystem system = UserTrackingPlugin.getActorSystem();
        final Scheduler scheduler = system.scheduler();

        Cancellable cancellable = scheduler.scheduleOnce(Duration.apply(2, TimeUnit.SECONDS), executeTracking, system.dispatcher());
        contextLoggingMap.put(context, cancellable);

        return delegate.call(context);
    }

    private static final class ExecuteTracking implements Runnable {
        private final Context context;
        private final String controller;
        private final String action;

        public ExecuteTracking(Context context, String controller, String action) {
            super();
            this.context = context;
            this.controller = controller;
            this.action = action;
        }

        @Override
        public void run() {
            final UserTrackingService service = UserTrackingPlugin.getUserTrackingService();
            if (action == null) {
                service.track(context, controller);
            } else {
                service.track(context, controller, action);
            }
        }
    }
}
