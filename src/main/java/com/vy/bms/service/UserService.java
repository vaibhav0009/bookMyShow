package com.vy.bms.service;


import com.vy.bms.dto.UserDto;
import com.vy.bms.dto.UserRegisterDto;
import com.vy.bms.exception.ResourceNotFoundException;
import com.vy.bms.model.User;
import com.vy.bms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto createUser(UserRegisterDto userRegisterDto)
    {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new IllegalStateException("Email is already registered");
        }
        User user=mapToEntity(userRegisterDto);
        User savedUser=userRepository.save(user);
        return mapToDto(savedUser);
    }

    public UserDto getUserById(Long id)
    {
        User user=userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers()
    {
        List<User> users=userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //update user
    //delete user

    public String forgotPassword(String email)
    {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: "+email));

        String token=UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        return token;
    }

    public void resetPassword(String token, String newPassword)
    {
        User user=userRepository.findByResetToken(token)
                .orElseThrow(()->new IllegalStateException("Invalid or expired reset token"));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Invalid or expired reset token");
        }

        user.setPassword(newPassword);
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    private User mapToEntity(UserRegisterDto userDto) {
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return  user;
    }

    private UserDto mapToDto(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;

    }
}