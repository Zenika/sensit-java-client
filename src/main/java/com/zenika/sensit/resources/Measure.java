package com.zenika.sensit.resources;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Measure {
    public String data;
    public ZonedDateTime date;
    public ZonedDateTime datePeriod;
}
