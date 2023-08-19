package com.blummon.users.vo;

import com.blummon.users.entity.UserEntity;

import java.util.List;

public class VoResultado {
    public List<UserEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserEntity> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UserEntity> usuarios;
}
