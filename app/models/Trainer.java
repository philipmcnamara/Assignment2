package models;

import play.db.jpa.Model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Trainer extends Model
{
    public String name;
    public String email;
    public String password;

    @OneToMany(cascade = CascadeType.ALL)
    /**
     * Array list of members for the Trainers to view in their dashboard.html
     */
    public List<Member> members = new ArrayList<Member>();

    /**
     * Constructor for Trainer
     * @param name
     * @param email
     * @param password
     */
    public Trainer(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    /**
     * Finds Trainer email in database
     * @param email
     * @return
     */
    public static Trainer findByEmail(String email)
    {
        return find("email", email).first();
    }

    /**
     * Finds Trainer password in data based and checks match
     * @param password
     * @return
     */
    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }


}