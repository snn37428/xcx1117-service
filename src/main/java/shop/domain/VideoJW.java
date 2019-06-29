package shop.domain;

import java.util.Date;

public class VideoJW {
    private Integer id;

    private String token;

    private String jw;

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

    public String getJw() {
        return jw;
    }

    public void setJw(String jw) {
        this.jw = jw == null ? null : jw.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}