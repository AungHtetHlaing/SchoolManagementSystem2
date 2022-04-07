package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.UserDto;
import main.java.com.ahh.school.entity.User;
import main.java.com.ahh.school.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDto> fetchAll() {
        List<User> users = repository.fetchAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User out : users) {
            userDtos.add(new UserDto(out));
        }
        return userDtos;
    }

    @Override
    public List<UserDto> fetchAllByName(String name) {
        return null;
    }

    @Override
    public boolean save(UserDto entity) {
        return repository.save(entity.getEntity());
    }

    @Override
    public boolean delete(UserDto entity) {
        return repository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return repository.deleteById(Id);
    }

    @Override
    public UserDto update(UserDto entity) {
        User user = repository.update(entity.getEntity());
        return new UserDto(user);
    }

    @Override
    public UserDto findOne(Long aLong) {
        User user = repository.findOne(aLong);
        return new UserDto(user);
    }
}
