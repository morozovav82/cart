package ru.morozov.cart.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.morozov.cart.security.UserDto;

public class AuthUtils {

    public static Long getCurrentUserId() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto.getId();
    }
}
