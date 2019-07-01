package shop.domain;

import java.util.Date;

public class VideoAuth {
    private Integer id;

    private String token;

    private Integer status;

    private Integer accredit;

    private Integer auth;

    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAccredit() {
        return accredit;
    }

    public void setAccredit(Integer accredit) {
        this.accredit = accredit;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}