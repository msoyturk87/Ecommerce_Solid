package com.cybertek.implementation;

import com.cybertek.dto.UserDTO;
import com.cybertek.enums.Status;
import com.cybertek.entity.User;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.UserService;
import org.aspectj.weaver.patterns.DeclareTypeErrorOrWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    @Transactional
    public User create(UserDTO userDTO) throws EcommerceException {
        Optional<User> foundedUser = userRepository.findByUserNameOrEmail(userDTO.getUserName(),userDTO.getEmail());

        if(foundedUser.isPresent()){

            throw new EcommerceException("This user already exist ");
        }

        return  userRepository.save(mapperUtil.convert(userDTO,new User()));    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) throws EcommerceException {
        User foundedUser = userRepository.findByUserNameOrEmail(userDTO.getUserName(),userDTO.getEmail())
                .orElseThrow(()->new EcommerceException("There is no user to update"));

         /*if(!foundedUser.getEmail().equals(user.getEmail())){
        //TODO check for confirmation email
         }
            */

        User convertUser = mapperUtil.convert(userDTO, new User());
        convertUser.setId(foundedUser.getId());

        userRepository.save(convertUser);
    }

    @Override
    public UserDTO readByUsername(String username) throws EcommerceException {
        User foundedUser = userRepository.findByUserName(username).orElseThrow(() -> new EcommerceException("User does not exist"));

        return mapperUtil.convert(foundedUser,new UserDTO());
    }

    @Override
    public List<UserDTO> readAll() {

        List<User> list = userRepository.findAll();
        return list.stream().map(obj->{return mapperUtil.convert(obj,new UserDTO());}).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deactivateAccount(Long id) throws EcommerceException {

        User foundedUser = userRepository.findById(id).orElseThrow(() -> new EcommerceException("user does not exist"));

        foundedUser.setStatus(Status.SUSPENDED);
        userRepository.save(foundedUser);

    }

    @Override
    public User readByEmail(String email) throws EcommerceException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EcommerceException("There is no User with this email"));
    }


}
