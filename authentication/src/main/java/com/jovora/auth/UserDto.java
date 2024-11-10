package com.jovora.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String identifier;
    private String username;
}
