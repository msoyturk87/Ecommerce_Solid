package com.cybertek.service;


import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;

import java.util.List;

public interface UserService {



     User create(UserDTO userDTO);
     void update(UserDTO userDTO) ;
    UserDTO readByUsername(String username);

     List<UserDTO> readAll();
     void deactivateAccount(Long id);
    UserDTO readByEmail(String email);

}
