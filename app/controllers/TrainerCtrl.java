package controllers;
import models.Member;
import models.Trainer;
import models.Stat;
import play.Logger;
import play.mvc.Controller;

public class TrainerCtrl extends Controller
{
    /**
     * Checks trainer id and sends them to thier correct dashboard (See the members they are a trainer of but not the ones they arent)
     * @param id
     */
    public static void index(Long id)
    {
        Trainer trainer = Trainer.findById(id);
        Logger.info ("Trainer id = " + id);
        render("dashboard.html", trainer);
    }

    /**
     * Checks member id and statt id
     * Adds a trainer comment onto the specific stat and saves
     * @param id
     * @param statid
     * @param Comment
     */
    public static void addComment (Long id, Long statid, String Comment)
    {
        Member member = Member.findById(id);
        Stat stat = Stat.findById(statid);
        Logger.info ("Adding" + stat.Comment);
        stat.Comment = Comment;
        stat.save();
        member.save();
        render("trainermemberview.html", member);
    }

    /**
     * Checks member id and finds all their stats
     * deletes all members stats then deletes the member.
     * saves the changes
     * @param id
     */
    public void deleteMember(Long id)
    {
        Member member = Member.findById(id);
        Logger.info("Removing Member" + id);
        member.stats.clear();
        member.save();
        Trainer trainer = Accounts.getLoggedInTrainer();
        trainer.members.remove(member);
        trainer.save();
        redirect ("/dashboard");
    }
}