package controllers;

import info.seltenheim.play2.usertracking.actions.TrackUser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

@TrackUser(controller="App")
public class Application extends Controller {
  
    @TrackUser(action="base")
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
}
