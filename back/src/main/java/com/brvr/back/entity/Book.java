package com.brvr.back.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Book extends BaseDateEntity {

    @Id
    private String isbn;
}
