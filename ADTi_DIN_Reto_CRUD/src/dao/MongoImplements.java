package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import config.MongoConnectionManager;
import dao.ModelDAO;
import exception.OurException;
import java.util.ArrayList;
import model.Profile;
import model.User;
import org.bson.Document;

public class MongoImplements implements ModelDAO {

    private final MongoCollection<Document> col;

    public MongoImplements() {
        this.col = MongoConnectionManager.getDatabase().getCollection("profiles");
    }

    /*
    @Override
    public User login(String emailOrUsername, String password) {

        Document Doc = col.find(
                Filters.and(
                    Filters.or(
                        Filters.eq("email", emailOrUsername),
                        Filters.eq("username", emailOrUsername)
                    ),
                    Filters.eq("password", password)
                )
        ).first();

        if (userDoc == null) {
            return null;
        }

        User u = new User();
        u.setId(userDoc.getObjectId("_id").toHexString());
        u.setEmail(userDoc.getString("email"));
        u.setUsername(userDoc.getString("username"));
        u.setPassword(userDoc.getString("password"));
        u.setCard(userDoc.getString("card"));

        return u;
    }*/
    @Override
    public ArrayList<User> getUsers() throws OurException {
        ArrayList<User> users = new ArrayList<>();

        for (Document doc : col.find()) {
            User u = new User();
            u.setId(doc.getInteger("_id"));
            u.setEmail(doc.getString("email"));
            u.setUsername(doc.getString("username"));
            u.setPassword(doc.getString("password"));
            u.setName(doc.getString("name"));
            u.setCard(doc.getString("card"));
        }

        User u = new User();

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

    @Override
    public Profile login(String username, String password) throws OurException {
        Document doc = col.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password)
                )
        ).first();

        User u = new User();
        u.setId(doc.getInteger("_id"));
        u.setEmail(doc.getString("email"));
        u.setUsername(doc.getString("username"));
        u.setPassword(doc.getString("password"));
        u.setCard(doc.getString("card"));

        return u;
    }

}
