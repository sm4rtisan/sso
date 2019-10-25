package com.j.sso.entity;

import java.io.Serializable;

/**
 * @author yzsn
 */
public class SSOUser implements Serializable {

    private static final long serialVersionUID = -7627538371743609929L;

    private Long id;
    private String username;
    private String extra;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SSOUser [id=" + id + ", username=" + username + ", extra=" + extra + "]";
    }

}
