package org.example.manageusers;

import org.example.manageusers.Entity.User;
import org.example.manageusers.repositories.UserRepository;
import org.example.manageusers.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");
        mockUser.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(mockUser);

        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertNotNull(retrievedUsers);
        assertEquals(1, retrievedUsers.size());
        assertEquals("John Doe", retrievedUsers.get(0).getName());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Optional<User> retrievedUser = userService.getUserById(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals("John Doe", retrievedUser.get().getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> retrievedUser = userService.getUserById(2L);

        assertFalse(retrievedUser.isPresent());
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User createdUser = userService.createUser(mockUser);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }

    @Test
    void testUpdateUser_Success() {
        User updatedDetails = new User();
        updatedDetails.setName("Jane Doe");
        updatedDetails.setEmail("jane.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("jane.doe@example.com", updatedUser.getEmail());
    }

    @Test
    void testUpdateUser_NotFound() {
        User updatedDetails = new User();
        updatedDetails.setName("Jane Doe");
        updatedDetails.setEmail("jane.doe@example.com");

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(2L, updatedDetails));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
