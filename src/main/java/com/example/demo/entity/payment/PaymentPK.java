package com.example.demo.entity.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentPK implements Serializable {

    //PG사별 승인번호
    @Column(name = "payment_number")
    private String payNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "pg_type")
    private PgType pgType;

}
