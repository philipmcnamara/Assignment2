package controllers;
import java.util.List;
import models.Member;
import models.Stat;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;


public class Dashboard extends Controller
{
  /**
   * Checks session variable for either Trainer or Member.
   * Sends trainer to the trainers dashboard and the member to the members dashboard
   */
  public static void index()
  {
    if (session.contains("logged_in_Trainerid"))
    {
      Trainer trainer = Accounts.getLoggedInTrainer();
      Logger.info("Rendering Trainer Dashboard" + trainer.id);
      List<Member> members = trainer.members;
      render ("dashboard.html", members);
    }
    else
    {
      Logger.info("Rendering Member Dashboard");
      Member member = Accounts.getLoggedInMember();
      List<Stat> stats = member.stats;
      render ("member.html", member);
    }
  }

  /**
   * Allows the trainer to view the members stats (Assessments) withouth being able to delete them but enables the addition of comments.
   * @param id
   */
  public static void viewMember (Long id)
  {
    Member member = Member.findById(id);
    session.put("logged_in_Memberid", member.id);
    Logger.info("Rendering Trainer View Dashboard" );
    List<Stat> stats = member.stats;
    render ("trainermemberview.html", member);
  }
}