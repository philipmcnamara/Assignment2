package controllers;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

/**
 * link to loginin and signup html pages
 */
public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }

    public static void login()
    {
        render("login.html");
    }

    /**
     * Creates a new user in the system.
     * @param name
     * @param gender
     * @param email
     * @param password
     * @param address
     * @param height
     * @param startingWeight
     */
    public static void register(String name, String gender, String email, String password, String address, float height, float startingWeight)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, gender, email, password, address,  height, startingWeight);
        member.save();
        redirect("/");
    }

    /**
     * checks if user is either a trainer or a members from the database.
     * Sends Trainer to trainer view html
     * Sends member to member view html
     * if no registered member/trainer are found, reloads the login sequence.
     * @param email
     * @param password
     */
    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        Trainer trainer = Trainer.findByEmail(email);

        if ((member != null) && (member.checkMemberPassword(password)))
        {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        }
        else if ((trainer != null) && (trainer.checkPassword(password)))
        {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/dashboard");
        }
        else
        {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    /**
     * clears the session variable and logs out the user.
     */
    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    /**
     * finds the member and creates a session variable for them to navigate through the site to avoid multiple logins
     * @return
     */
    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid"))
        {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        }
        else
        {
            login();
        }
        return member;
    }

    /**
     * Same as above, but for a trainer.
     * @return
     */
    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid"))
        {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        }
        else
        {
            login();
        }
        return trainer;
    }
}