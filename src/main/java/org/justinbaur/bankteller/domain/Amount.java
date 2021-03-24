package org.justinbaur.bankteller.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Amount {
    @NotNull(message = "value can not be null")
    @Size(min = 0, max = 10000, message = "An amount value between 0 and 10000 expected")
    private Integer value;

    public Amount() {

    }

    public Amount(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
