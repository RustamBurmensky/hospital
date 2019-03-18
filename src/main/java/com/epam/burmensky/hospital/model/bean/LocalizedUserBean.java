package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.User;
import com.epam.burmensky.hospital.model.entity.UserDetails;

import java.util.List;

/**
 * User entity with localized full name.
 *
 * @author Rustam Burmensky
 *
 */
public class LocalizedUserBean extends User {

    private static final long serialVersionUID = 4539792809630112058L;

    public LocalizedUserBean() {}

    public LocalizedUserBean(User user, List<UserDetails> userDetails) {
        this.setId(user.getId());
        this.setRoleId(user.getRoleId());
        this.setSpecializationId(user.getSpecializationId());
        this.setLangId(user.getLangId());
        this.setBirthday(user.getBirthday());
        this.setLogin(user.getLogin());
        this.setPassword(user.getPassword());
        this.setUserDetails(userDetails);
    }

    private List<UserDetails> userDetails;

    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetails> userDetails) {
        this.userDetails = userDetails;
    }

}
