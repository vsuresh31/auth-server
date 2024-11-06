package com.jovora.secret;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Secret {
    private String data;
    private String alias;
    private String iv;
}
