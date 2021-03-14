package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {


    @Insert("INSERT INTO credentials(url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer insertCredential(Credential credential);

    @Update("UPDATE credentials set url = #{url}, username = #{username}, key = #{key}, password = #{password} where credentialid = #{credentialId} and userid = #{userId}")
    public Integer updateCredential(Credential credential);

    @Delete("DELETE credentials where credentialid = #{credentialId} and userid = #{userId}")
    public Integer deleteCredential(Integer credentialId, Integer userId);

    @Select("SELECT credentialid, url, username, key, password, userid FROM credentials where userId = #{userId}")
    public List<Credential> findAllCredentialsByUserId(Integer userId);

}
