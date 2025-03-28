package com.example.fastfoodstore.service;

import java.util.List;

import com.example.fastfoodstore.dto.MenuItemDTO;

public interface MenuItemService {
    List<MenuItemDTO> getAllMenuItems();
    MenuItemDTO getMenuItemById(Long id);
    MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO);
    MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO);
    void deleteMenuItem(Long id);
}