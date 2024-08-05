package application.organization.services;

import java.util.List;

public interface CommonManagementService<T> {
    List<T> getAll();
    T getById(Long id);
    T create(T object);
    T update(T object);
    void delete(Long id);
}
