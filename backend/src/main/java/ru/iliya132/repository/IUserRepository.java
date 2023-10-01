package ru.iliya132.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iliya132.model.User;

public interface IUserRepository extends CrudRepository<User, String> {
}
