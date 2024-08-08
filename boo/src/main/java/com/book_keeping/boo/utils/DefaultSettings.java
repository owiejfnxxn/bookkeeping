package com.book_keeping.boo.utils;

import com.book_keeping.boo.Entity.Settings;

public class DefaultSettings extends Settings {
    public DefaultSettings() {
        this.setPageSize(9);
        this.setDefaultTimeType("week");
    }
}
