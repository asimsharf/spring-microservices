package com.sudagoarth.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LocalizedData {
    private String arabic;
    private String english;


//    public static LocalizedData fromString(String data) {
//        return LocalizedData.builder()
//                .arabic(data)
//                .english(data)
//                .build();
//    }
}