package com.jovora.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserDto {

    private String identifier;
    private String username;
}
