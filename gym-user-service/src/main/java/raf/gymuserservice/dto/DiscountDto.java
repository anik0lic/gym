package raf.gymuserservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountDto {
    private Integer discount;

    public DiscountDto(Integer discount) {
        this.discount = discount;
    }
}
