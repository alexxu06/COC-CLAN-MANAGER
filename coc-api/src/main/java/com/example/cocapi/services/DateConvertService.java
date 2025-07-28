package com.example.cocapi.services;

import com.example.cocapi.models.war.War;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DateConvertService {

    // gets most recent war and converts into suitable Timestamp for storing
    public Timestamp convertDate(List<War> wars) {
        if (wars.isEmpty()) {
            return null;
        }

        String endTime = wars.get(0).getWar_data().getEndTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(endTime, formatter);
        return Timestamp.from(zonedDateTime.toInstant());
    }
}
