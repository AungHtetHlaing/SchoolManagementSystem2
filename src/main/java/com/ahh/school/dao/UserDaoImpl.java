package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.User;
import main.java.com.ahh.school.repository.UserRepository;

import java.util.List;

public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserRepository {
    @Override
    public User findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public List<User> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public User update(User entity) {
        return super.update(entity);
    }

    @Override
    public boolean delete(User entity) {
        return super.delete(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<User> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
