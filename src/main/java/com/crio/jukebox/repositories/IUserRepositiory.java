package com.crio.jukebox.repositories;

import java.util.Optional;
import com.crio.jukebox.entities.User;

public interface IUserRepositiory extends CRUDRepository<User, String>{
    public Optional<User> findByName(String name);
    
    public boolean playlistPresent(String userId, String playlistId);

    public User getUser(String userId);
}
