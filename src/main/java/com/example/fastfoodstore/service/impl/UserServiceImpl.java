package com.example.fastfoodstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fastfoodstore.dao.UserDAO;
import com.example.fastfoodstore.dto.UserDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.User;
import com.example.fastfoodstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        User savedUser = userDAO.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (userDTO.getFullName() != null) {
            user.setFullName(userDTO.getFullName());
        }
        User updatedUser = userDAO.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userDAO.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getFullName());
    }
}