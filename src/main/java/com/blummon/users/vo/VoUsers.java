package com.blummon.users.vo;

import com.blummon.users.entity.UserEntity;

import java.util.List;

public class VoUsers {
    public List<UserEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserEntity> usuarios) {
        this.usuarios = usuarios;
    }

    List<UserEntity> usuarios;
}
