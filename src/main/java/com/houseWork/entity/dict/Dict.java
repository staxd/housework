package com.houseWork.entity.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "dict")
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "k")
    @ApiModelProperty(value = "k")
    private String k;

    @Column(name = "orderby")
    @ApiModelProperty(value = "orderby")
    private String orderby;

    @Column(name = "type")
    @ApiModelProperty(value = "type")
    private String type;

    @Column(name = "v")
    @ApiModelProperty(value = "v")
    private String v;
}