package br.com.vardeba.learningspring.service.impl;


import br.com.vardeba.learningspring.dto.CreateDepositDto;
import br.com.vardeba.learningspring.dto.UserDto;
import br.com.vardeba.learningspring.exception.AppException;
import br.com.vardeba.learningspring.model.User;
import br.com.vardeba.learningspring.repository.UserRepository;
import br.com.vardeba.learningspring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkEmailAndCpf(final UserDto userData) {
        if (userRepository.existsUserByCpf(userData.getCpf())) {
            throw new AppException("Cpf already in use", HttpStatus.CONFLICT);
        }

        if (userRepository.existsUserByEmail(userData.getEmail())) {
            throw new AppException("Email already in use", HttpStatus.CONFLICT);
        }
    }

    public User createUser(final UserDto userData) {

        checkEmailAndCpf(userData);

        final User newUser = new User(userData.getName(), userData.getCpf(), userData.getEmail(), userData.getPassword(), userData.getType());

        return userRepository.save(newUser);

    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(final long id) {

        return userRepository.findById(id).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));
    }

    public User updateUser(final UserDto userData, final long id) {

        checkEmailAndCpf(userData);
        
        final User foundUser = userRepository.findById(id).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        foundUser.setName(userData.getName());
        foundUser.setCpf(userData.getCpf());
        foundUser.setEmail(userData.getEmail());
        foundUser.setPassword(userData.getPassword());
        foundUser.setType(userData.getType());

        return userRepository.save(foundUser);
    }

    public void deleteUser(final long id) {
        final User foundUser = userRepository.findById(id).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userRepository.delete(foundUser);

    }

    public User createDeposit(final CreateDepositDto depositData, final long id) {
        final User foundUser = userRepository.findById(id).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float currentBalance = foundUser.getBalance();

        foundUser.setBalance(currentBalance + depositData.getValue());

        return userRepository.save((foundUser));

    }
}
