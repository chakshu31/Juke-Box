package com.crio.jukebox.repositories;

// import com.crio.jukebox.entities.Playlist;
// import com.crio.jukebox.entities.User;

public interface CRUDRepository<T,ID> {
    public T save(T entity);
}
