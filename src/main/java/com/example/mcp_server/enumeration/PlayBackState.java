package com.example.mcp_server.enumeration;

import lombok.Getter;

@Getter
public enum PlayBackState {
    PLAY("play"),
    PAUSE("pause");

    private final String state;

    PlayBackState(String state) {
        this.state = state;
    }
}
