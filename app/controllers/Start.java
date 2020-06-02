package controllers;

import play.Logger;
import play.mvc.Controller;

public class Start extends Controller
{
  /**
   * Stats the site and loads the start html view
   */
  public static void index() {
    Logger.info("Rendering Start");
    render ("start.html");
  }
}
