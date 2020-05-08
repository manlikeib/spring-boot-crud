package com.ibrahim.springdemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.springdemo.exception.ResourceNotFoundException;
import com.ibrahim.springdemo.model.User;
import com.ibrahim.springdemo.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable(value = "id") Long employeeId)
        throws ResourceNotFoundException {
        User user = userRepository.findById(employeeId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(user);
    }
    
    @PostMapping("/users")
    public User createEmployee(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable(value = "id") Long employeeId,
         @Valid @RequestBody User employeeDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + employeeId));

        user.setEmailId(employeeDetails.getEmailId());
        user.setLastName(employeeDetails.getLastName());
        user.setFirstName(employeeDetails.getFirstName());
        final User updatedEmployee = userRepository.save(user);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
         throws ResourceNotFoundException {
        User user = userRepository.findById(employeeId)
       .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + employeeId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
