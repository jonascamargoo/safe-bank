package com.safebank.demo.mappers;

public interface GenericMapper<Entity, DataTransferObject> {
    DataTransferObject toDTO(Entity entity);
    Entity toEntity(DataTransferObject dto);
}

