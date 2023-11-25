package com.graceman.propertymangement.dto.response;

import lombok.Data;


public record LoginResponse (
    String name,
    String email,
    String jwt) {

}
