package com.wonder.bring.dto;

import lombok.Data;

/**
 * Created by bomi on 2019-01-02.
 */

@Data
public class Point {
    private int storeIdx; // 매장 idx
    private double latitude; // 위도
    private double longitude; // 경도
}
