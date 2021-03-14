package com.udacity.jwdnd.course1.cloudstorage.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {

    private Integer credentialId;

    private String url;

    private String username;

    private String key;

    private String password;

    private Integer userId;

    private String decryptedPassword;

}
