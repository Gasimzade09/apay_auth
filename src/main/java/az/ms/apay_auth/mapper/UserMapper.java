package az.ms.apay_auth.mapper;

import az.ms.apay_auth.model.dto.UserDto;
import az.ms.apay_auth.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);
}
