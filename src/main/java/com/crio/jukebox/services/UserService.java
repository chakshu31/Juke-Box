package com.crio.jukebox.services;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepositiory;

public class UserService implements IUserService {
    
    private IUserRepositiory userRepositiory;

    public UserService(IUserRepositiory userRepositiory){
        this.userRepositiory = userRepositiory;
    }

    @Override
    public User createUser(String name) {
        User newUser = new User(name);
        return userRepositiory.save(newUser);
    }

    
    
}
