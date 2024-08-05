package application.organization.services;

import application.organization.persistence.entities.Employee;

public interface IService<T> {
    public Object getAll();
    public Object getById(Long id);
    public Object create(T object);
    public Object update(T object);
    public void delete(Long id);
}
