package com.cybertek.service;


import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.exception.EcommerceException;

import java.util.List;

public interface UserService {



     User create(UserDTO userDTO) throws EcommerceException;
     void update(UserDTO userDTO) throws EcommerceException;
     UserDTO readByUsername(String username) throws EcommerceException;

     List<UserDTO> readAll();
     void deactivateAccount(Long id) throws EcommerceException;
     User readByEmail(String email) throws EcommerceException;

}
