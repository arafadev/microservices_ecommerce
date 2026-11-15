package com.ecommerce.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void createUser(UserRequest request) {
        User user = new User();
        mapToUserEntity(user, request);
        userRepository.save(user);
    }

    public UserResponse updateUser(String id, UserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    mapToUserEntity(user, request);
                    return mapToUserResponse(userRepository.save(user));
                })
                .orElse(null);
    }

    public UserResponse fetchUserById(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse)
                .orElse(null);
    }

    // Request DTO -> Entity
    private void mapToUserEntity(User user, UserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            Address address = user.getAddress() != null ? user.getAddress() : new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipCode(request.getAddress().getZipCode());
            user.setAddress(address);
        }
    }

    // Entity -> Response DTO
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipCode(user.getAddress().getZipCode());
            response.setAddress(addressDto);
        }

        return response;
    }
}
