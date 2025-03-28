package com.example.fastfoodstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fastfoodstore.dao.CategoryDAO;
import com.example.fastfoodstore.dao.MenuItemDAO;
import com.example.fastfoodstore.dto.MenuItemDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.MenuItem;
import com.example.fastfoodstore.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        return convertToDTO(menuItem);
    }

    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = new MenuItem();
        mapDTOToEntity(menuItemDTO, menuItem);
        MenuItem savedMenuItem = menuItemDAO.save(menuItem);
        return convertToDTO(savedMenuItem);
    }

    @Override
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        if (menuItemDTO.getName() != null) {
            menuItem.setName(menuItemDTO.getName());
        }
        if (menuItemDTO.getPrice() > 0) {
            menuItem.setPrice(menuItemDTO.getPrice());
        }
        if (menuItemDTO.getDescription() != null) {
            menuItem.setDescription(menuItemDTO.getDescription());
        }
        if (menuItemDTO.getImage() != null) {
            menuItem.setImage(menuItemDTO.getImage());
        }
        if (menuItemDTO.getCategoryId() != null) {
            menuItem.setCategory(categoryDAO.findById(menuItemDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + menuItemDTO.getCategoryId())));
        }
        MenuItem updatedMenuItem = menuItemDAO.save(menuItem);
        return convertToDTO(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItemDAO.delete(menuItem);
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getImage(),
                menuItem.getCategory() != null ? menuItem.getCategory().getId() : null
        );
    }

    private void mapDTOToEntity(MenuItemDTO menuItemDTO, MenuItem menuItem) {
        if (menuItemDTO.getName() == null || menuItemDTO.getPrice() <= 0 || menuItemDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("Name, price, and category are required");
        }
        menuItem.setName(menuItemDTO.getName());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setImage(menuItemDTO.getImage());
        menuItem.setCategory(categoryDAO.findById(menuItemDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + menuItemDTO.getCategoryId())));
    }
}