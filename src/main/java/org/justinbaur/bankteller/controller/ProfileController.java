package org.justinbaur.bankteller.controller;

import java.util.List;

import javax.validation.Valid;

import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.service.AdminService;
import org.justinbaur.bankteller.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
public class ProfileController {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserProfileService userService;

    @Autowired
    AdminService adminService;

    
    @GetMapping("/api/profile")
    public List<Profile> getProfiles() {
        LOG.debug("Getting list of all Profiles..");
        return userService.getProfiles();
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/api/profile")
    public void newProfile(@Valid @RequestBody Profile newProfile) {
        LOG.debug("Creating new Profile object {}", newProfile);
        adminService.createProfile(newProfile);
    }

    @GetMapping("/api/profile/{id}")
    public Profile getProfile(@PathVariable String id) throws ProfileNotFound {
        LOG.debug("Getting Profile with ID: {}", id);
        return userService.getProfile(id);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/api/profile")
    public void replaceProfile(@Valid @RequestBody Profile newProfile) {
        LOG.debug("Updating Profile object {}", newProfile);
        adminService.updateProfile(newProfile);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/api/profile/{id}")
    public void deleteProfile(@PathVariable String id) throws ProfileNotFound {
        LOG.debug("Deleting Profile object with ID: {}", id);
        adminService.deleteProfile(id);
    }

}
