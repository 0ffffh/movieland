package com.movieland.mapper;

import com.movieland.dto.UserDto;
import com.movieland.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

}
