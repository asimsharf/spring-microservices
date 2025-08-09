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


    public static LocalizedData of(String ar, String en) {
        return LocalizedData.builder().arabic(ar).english(en).build();
    }

}