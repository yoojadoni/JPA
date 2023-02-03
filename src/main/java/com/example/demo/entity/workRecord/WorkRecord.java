package com.example.demo.entity.workRecord;

import com.example.demo.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id", "shopId", "startDate", "endDate"})
@Table(name = "work_record")
public class WorkRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="work_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @CreatedDate
    @Column(name = "start_date", insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime startDate;

    @LastModifiedDate
    @Column(name = "end_date", updatable = true, insertable = false)
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDate;

    @PrePersist
    public void setStartDate(){
        this.startDate = LocalDateTime.now();
        this.endDate = null;
    }

    @PreUpdate
    public void setEndDate(){
        this.endDate = LocalDateTime.now();
    }

    @Builder
    public WorkRecord(Long id, Shop shop, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.shop = shop;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void changeEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
}