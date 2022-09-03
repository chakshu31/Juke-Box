package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepositiory {
    private Map<String, User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository() {
        userMap = new HashMap<String, User>();
    }
    
    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
    }
    
    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement), entity.getName(),entity.getPlaylistIds(),null);
            userMap.put(u.getId(), u);
            return u;
        }
        userMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findByName(String name) {
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getName() == name) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean playlistPresent(String userId, String playlistId) {
        List<String> playlistIds = userMap.get(userId).getPlaylistIds();
        
        for (String plId : playlistIds) {
            if (plId.equals(playlistId))
                return true;
        }
        return false;
    }
    
    @Override
    public User getUser(String userId) {
        return userMap.get(userId);
    }

}
