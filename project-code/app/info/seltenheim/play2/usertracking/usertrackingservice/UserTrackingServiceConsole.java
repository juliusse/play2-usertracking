package info.seltenheim.play2.usertracking.usertrackingservice;

import info.seltenheim.play2.usertracking.ExpiringMap;
import info.seltenheim.play2.usertracking.UserTrackingPlugin;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import play.Logger;
import play.mvc.Http;
import play.mvc.Http.Context;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Scheduler;

public class UserTrackingServiceConsole implements UserTrackingService {
    protected final static String TRACKING_ID_NAME = "id";

    private final static String CONTROLLER_ARG = "info.seltenheim.play2.usertracking.ctx.controller";
    private final static String ACTION_ARG = "info.seltenheim.play2.usertracking.ctx.action";
    private static Map<Context, Cancellable> contextLoggingMap = new ExpiringMap<Context, Cancellable>();

    @Override
    public void initTracking(Context context, String controller) {
        initTracking(context, controller,"");
    }

    @Override
    public void initTracking(Context context, String controller, String action) {
        //check that session exists
        session(context);
        
        
        // check in context
        if (controller.isEmpty()) {
            String ctxController = (String) context.args.get(CONTROLLER_ARG);
            if (ctxController != null) {
                controller = ctxController;
            }
        } // write in context
        else {
            context.args.put(CONTROLLER_ARG, controller);
        }

        // check in context
        if (action.isEmpty()) {
            String ctxAction = (String) context.args.get(ACTION_ARG);
            if (ctxAction != null) {
                action = ctxAction;
            }
        } // write in context
        else {
            context.args.put(ACTION_ARG, action);
        }

        final ExecuteTracking executeTracking = new ExecuteTracking(this, context, controller, action);

        final ActorSystem system = UserTrackingPlugin.getActorSystem();
        final Scheduler scheduler = system.scheduler();

        // check if already Cancellable is scheduled for context
        if (contextLoggingMap.containsKey(context)) {
            contextLoggingMap.get(context).cancel();
        }

        Cancellable cancellable = scheduler.scheduleOnce(Duration.apply(2, TimeUnit.SECONDS), executeTracking, system.dispatcher());
        contextLoggingMap.put(context, cancellable);

    }
    
    public void track(Context context, String controller) {
        final String session = session(context);
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' an action.");
    }
    
    public void track(Context context, String controller, String action) {
        final String session = session(context);
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' the action '" + action + "'.");
    }

    public static String session(Context context) {
        final Http.Session session = context.session();
        // Generate a unique id
        String id = session.get(TRACKING_ID_NAME);
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
            session.put(TRACKING_ID_NAME, id);
        }

        return id;
    }

    private static final class ExecuteTracking implements Runnable {
        private final UserTrackingService service;
        private final Context context;
        private final String controller;
        private final String action;

        public ExecuteTracking(UserTrackingService service, Context context, String controller, String action) {
            super();
            this.service = service;
            this.context = context;
            this.controller = controller;
            this.action = action;
        }

        @Override
        public void run() {
            if (action == null) {
                service.track(context, controller);
            } else {
                service.track(context, controller, action);
            }
        }
    }
}
