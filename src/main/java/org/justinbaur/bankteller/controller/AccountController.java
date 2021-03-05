package org.justinbaur.bankteller.controller;

import java.util.List;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Profile;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.service.AdminService;
import org.justinbaur.bankteller.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    UserProfileService userService;

    @Autowired
    AdminService adminService;

    /*
    @GetMapping("/api/profile/{id}/accounts")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable String id) throws ProfileNotFound {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<List<Account>>(userService.getAccounts(id), responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/api/profile/{id}/accounts")
    public void newAccount(@PathVariable String id, @RequestBody Account newAccount) throws ProfileNotFound {
        adminService.createAccount(id, newAccount);
    }

    
    @GetMapping("/api/profile/{id}/accounts/{name}")
    public ResponseEntity<Account> getProfile(@PathVariable String id, @PathVariable String name)
            throws ProfileNotFound, AccountNotFound {
        return userService.getAccount(id, name);
    }

    @PutMapping("/api/profile/{id}")
    public void replaceProfile(@RequestBody Profile newProfile, @RequestParam(value = "id") String id) {
        adminService.createProfile(newProfile);
    }

    @DeleteMapping("/api/profile/{id}")
    public void deleteProfile(@RequestParam(value = "id") String id) throws ProfileNotFound {
        adminService.deleteProfile(id);
    }*/

}
