package com.book_keeping.boo.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class Settings {
    @Id
    private Integer userid;

    private Integer pageSize;

    private String defaultTimeType;

}
