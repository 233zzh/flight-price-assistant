package com.zihao;

import lombok.Builder;

/**
 * @author zhangzhihao06 <zhangzhihao06@kuaishou.com>
 * Created on 2023-08-01
 */
@Builder
public class Flight {
    // 航空公司
    private String airCom;

    // 航班号
    private String flightNumber;


    private String departTime;

    private String arriveTime;

    private String durationTime;
    private String departAirport;
    private String arriveAirport;
    private String price;

}
