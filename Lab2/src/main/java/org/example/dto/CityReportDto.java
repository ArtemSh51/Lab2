package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityReportDto {
    private Long id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private CityObjectDto cityObject;
    private UserDto author;

    private Long cityObjectId;
    private Long authorId;
}