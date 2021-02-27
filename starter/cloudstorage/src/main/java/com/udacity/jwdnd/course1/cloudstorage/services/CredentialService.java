package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Credential getCredential(Integer credentialId) {
        return this.credentialMapper.getById(credentialId);
    }

    public List<Credential> getUserCredentials(Integer userId) { return this.credentialMapper.getAllByUserId(userId); }

    public int createCredential(Credential credential) {
        return this.credentialMapper.insert(new Credential(
                null,
                credential.getUrl(),
                credential.getUsername(),
                credential.getKey(),
                credential.getPassword(),
                credential.getUserId()
        ));
    }

    public int updateCredential(Credential credential) {
        return this.credentialMapper.update(credential);
    }

    public int deleteCredential(Credential credential) {
        return this.credentialMapper.delete(credential);
    }
}
