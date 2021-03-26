package org.justinbaur.bankteller.controller;

import java.util.List;

import javax.validation.Valid;

import org.justinbaur.bankteller.domain.Account;
import org.justinbaur.bankteller.domain.Amount;
import org.justinbaur.bankteller.exception.AccountNotFound;
import org.justinbaur.bankteller.exception.InsufficientBalance;
import org.justinbaur.bankteller.exception.ProfileNotFound;
import org.justinbaur.bankteller.service.AdminService;
import org.justinbaur.bankteller.service.UserProfileService;
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
public class AccountController {

    @Autowired
    UserProfileService userService;

    @Autowired
    AdminService adminService;

    @GetMapping("/api/profile/{id}/account")
    public List<Account> getAccounts(@PathVariable String id) throws ProfileNotFound {
        return userService.getAccounts(id);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/api/profile/{id}/account")
    public void newAccount(@PathVariable String id, @Valid @RequestBody Account newAccount) throws ProfileNotFound {
        adminService.createAccount(id, newAccount);
    }

    @GetMapping("/api/profile/{id}/account/{name}")
    public Account getAccount(@PathVariable String id, @PathVariable String name)
            throws ProfileNotFound, AccountNotFound {
        return userService.getAccount(id, name);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/api/profile/{id}/account/{name}")
    public void replaceAccount(@PathVariable String id, @PathVariable String name, @Valid @RequestBody Account newAccount) throws ProfileNotFound, AccountNotFound {
        adminService.updateAccount(id, newAccount);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/api/profile/{id}/account/{name}")
    public void deleteAccount(@PathVariable String id, @PathVariable String name) throws ProfileNotFound, AccountNotFound {
        adminService.deleteAccount(id, name);
    }

    @PutMapping("/api/profile/{id}/account/{name}/deposit")
    public void deposit(@PathVariable String id, @PathVariable String name, @Valid @RequestBody Amount amount) throws ProfileNotFound, AccountNotFound {
        userService.addBalance(id, name, amount.getValue());
    }

    @PutMapping("/api/profile/{id}/account/{name}/withdraw")
    public void withdraw(@PathVariable String id, @PathVariable String name, @Valid @RequestBody Amount amount) throws ProfileNotFound, AccountNotFound, InsufficientBalance {
        userService.subtractBalance(id, name, amount.getValue());
    }
}
