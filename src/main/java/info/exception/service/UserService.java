package info.exception.service;

import info.exception.dto.UserRequest;
import info.exception.entity.User;
import info.exception.exhandler.UserNotFoundException;
import info.exception.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest userRequest) {
        User user = User.build(0, userRequest.getName(), userRequest.getEmail(), userRequest.getMobile(), userRequest.getAge(), userRequest.getGender(), userRequest.getNationality());
        return userRepository.save(user);
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public User getUser(int id) throws UserNotFoundException {
        User user = userRepository.findByUserId(id);
        if (user != null) {
            return user;
        }else {
            throw new UserNotFoundException("user id  not found: "+ id);
        }
    }
    public User updateUser(int id, UserRequest userRequest) {
        try {
            User existingUser = getUser(id);
            existingUser.setName(userRequest.getName());
            existingUser.setAge(userRequest.getAge());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setMobile(userRequest.getMobile());
            existingUser.setGender(userRequest.getGender());
            existingUser.setNationality(userRequest.getNationality());
            return userRepository.save(existingUser);
        } catch (UserNotFoundException e) {
            return null;
        }
    }
    public void deleteUser(int id) throws UserNotFoundException {
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new UserNotFoundException("user id not found: " + exception.getMessage());
        }
    }


}
