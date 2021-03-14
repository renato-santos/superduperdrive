package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;


@AllArgsConstructor
@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public void submitCredential(Credential credential){
        if(isNull(credential.getCredentialId())) {
            credentialMapper.insertCredential(credential);
        } else {
            credentialMapper.updateCredential(credential);
        }
    }

    public void deleteCredential(Integer credentialId, Integer userId){
        credentialMapper.deleteCredential(credentialId, userId);
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId){


        List<Credential> credentialList = credentialMapper.findAllCredentialsByUserId(userId);

        credentialList.forEach(((l) -> l.setDecryptedPassword(encryptionService.decryptValue(l.getPassword(),l.getKey()))));

        return credentialList;
    }


}
