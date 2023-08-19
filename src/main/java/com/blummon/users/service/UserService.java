package com.blummon.users.service;

import com.blummon.users.dto.UserDto;
import com.blummon.users.entity.UserEntity;
import com.blummon.users.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity save(UserEntity userEntity) throws ServiceException{
        try{
            return userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage());
        }

    }


    public UserEntity createUser(String name,String username,String lastName, String password) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setUsername(username);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }


    public Optional<UserEntity> findById(Long id) throws ServiceException {
        try{
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<UserEntity> findAll() throws ServiceException {
        try {
            List<UserEntity> userEntities = userRepository.findAll();

            return userEntities;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }




    public UserEntity update(UserEntity userEntity) throws ServiceException{
        try{
            UserEntity oUserEntity = this.findById(userEntity.getIdUser()).orElse(null);
            if (!Objects.isNull(oUserEntity)){
                BeanUtils.copyProperties(userEntity,oUserEntity);
                return userRepository.save(oUserEntity);
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



    public Boolean delete(UserEntity userEntity) throws ServiceException {
        try{
            userRepository.delete(userEntity);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



    public UserEntity findUser(String username, String password) throws ServiceException {
        try {
            UserEntity user = userRepository.findByUsername(username);

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }

            return null; // No se encontró el usuario o las contraseñas no coinciden
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



}
