package com.amit.rest.restfulwebservices.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepository {
    private static List<User> users = new ArrayList<>();
    private int count=3;
    static {
       // users.add(new User(1,"Jhon",new Date()));
       // users.add(new User(2,"Marry",new Date()));
       // users.add(new User(3,"Cooper",new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        for(User user:users) {
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    public User save(User user) {
        System.out.println("user..."+user);
        if(user != null) {
            user.setId(++count);
            users.add(user);
        }
        return user;
    }

    public User delete(int id) {
        for (User user: users) {
            if(user.getId() == id) {
                users.remove(user);
                return user;
            }
        }
        return null;
    }
}
