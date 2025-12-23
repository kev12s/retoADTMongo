package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import config.MongoConnectionManager;
import dao.ModelDAO;
import exception.OurException;
import java.util.ArrayList;
import model.Admin;
import model.Gender;
import model.LoggedProfile;
import model.Profile;
import model.User;
import org.bson.Document;

public class MongoImplements implements ModelDAO {

    private final MongoCollection<Document> mongo;

    public MongoImplements() {
        this.mongo = MongoConnectionManager.getDatabase().getCollection("profiles");
    }

    @Override
    public Profile login(String credential, String password) throws OurException {
        String genderStr;
        Gender gender = null;
        Document doc = mongo.find(
                Filters.and(
                        Filters.or(Filters.eq("username", credential), Filters.eq("email", credential)),
                        Filters.eq("password", password)
                )).first();

        if (doc == null) {
            System.out.println("Es null no ha encontrado");
            return null;
        }

        Profile profile;

        if (doc.containsKey("gender")) {
            User u = new User();
            u.setId(doc.getInteger("_id"));
            u.setEmail(doc.getString("email"));
            u.setUsername(doc.getString("username"));
            u.setPassword(doc.getString("password"));
            u.setName(doc.getString("name"));
            u.setLastname(doc.getString("lastname"));
            u.setTelephone(doc.getString("telephone"));

            genderStr = doc.getString("gender");
            u.setGender(Gender.valueOf(genderStr.toUpperCase()));

            u.setCard(doc.getString("card"));

            profile = u;

        } else {

            Admin a = new Admin();
            a.setId(doc.getInteger("_id"));
            a.setEmail(doc.getString("email"));
            a.setUsername(doc.getString("username"));
            a.setPassword(doc.getString("password"));
            a.setName(doc.getString("name"));
            a.setLastname(doc.getString("lastname"));
            a.setTelephone(doc.getString("telephone"));
            a.setCurrent_account(doc.getString("currentAccount"));

            profile = a;
        }

        LoggedProfile.getInstance().setProfile(profile);
        return profile;

    }
    
    @Override
    public ArrayList<User> getUsers() throws OurException {
        ArrayList<User> users = new ArrayList<>();
        String genderStr;
        Gender gender = null;

        for (Document doc : mongo.find(Filters.exists("gender"))) {
            User u = new User();
            u.setId(doc.getInteger("_id"));
            u.setEmail(doc.getString("email"));
            System.out.println(doc.getString("email"));
            u.setUsername(doc.getString("username"));
            System.out.println(doc.getString("username"));
            u.setPassword(doc.getString("password"));
            u.setName(doc.getString("name"));
            u.setLastname(doc.getString("lastname"));
            u.setTelephone(doc.getString("telephone"));

            genderStr = doc.getString("gender");
            if (genderStr.equalsIgnoreCase("MALE")) {
                gender = gender.MALE;
            } else if (genderStr.equalsIgnoreCase("FEMALE")) {
                gender = gender.FEMALE;
            } else {
                gender = gender.OTHER;
            }
            u.setGender(gender);
            u.setCard(doc.getString("card"));

            users.add(u);
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) throws OurException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteUser(int id) throws OurException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User register(User user) throws OurException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
