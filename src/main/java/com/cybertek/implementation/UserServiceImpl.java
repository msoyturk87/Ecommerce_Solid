package com.cybertek.implementation;

import com.cybertek.enums.Status;
import com.cybertek.model.User;
import com.cybertek.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(User user) throws Exception {
        Optional<User> foundedUser = userRepository.findByUserNameOrEmail(user.getUserName(),user.getEmail());

        if(foundedUser.isPresent()){

            throw new Exception("This user already exist ");
        }

       return  userRepository.save(user);

    }
    @Transactional
    public void update(User user) throws Exception {
        User foundedUser = userRepository.findByUserNameOrEmail(user.getUserName(),user.getEmail())
                .orElseThrow(()->new Exception("There is no user to update"));

        /* if(!foundedUser.getEmail().equals(user.getEmail())){
            //TODO check for confirmation email
        }*/

        user.setId(foundedUser.getId());

        userRepository.save(user);

    }



    public User readByUsername(String username) throws Exception {

        return userRepository.findByUserName(username).orElseThrow(()->new Exception("User does not exist"));
    }

    public List<User> readAll(){

        return userRepository.findAll(Sort.by("id")); // This part will be only variable name
    }

    @Transactional
    public void deactivateAccount(Long id) throws Exception {

        User foundedUser = userRepository.findById(id).orElseThrow(() -> new Exception("user does not exist"));

        foundedUser.setStatus(Status.SUSPENDED);
        userRepository.save(foundedUser);

    }
    public User readByEmail(String email){
        return userRepository.findByUserName(email)
                .orElseThrow(() -> new NoSuchElementException("This user does not exist"));
    }}
