package com.serrano.app.forum.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


import com.serrano.app.forum.exception.DateGratherException;


public class TimeAgo {
	
	public static String calculateTimeAgo(Instant instant){
        Instant currentDate = Instant.now();
 
        if(!instant.isBefore(currentDate)) throw new DateGratherException(instant.toString(), currentDate.toString());

        Long timeElapsed = (currentDate.toEpochMilli() - instant.toEpochMilli()) / 1000;
        StringBuilder timeAgo = new StringBuilder();

        if(timeElapsed < ChronoUnit.MINUTES.getDuration().getSeconds()){
            timeAgo.append("just now");
        }else if(timeElapsed < ChronoUnit.HOURS.getDuration().getSeconds()){
            timeAgo.append(timeElapsed / ChronoUnit.MINUTES.getDuration().getSeconds() + " min");
        }else if(timeElapsed < ChronoUnit.DAYS.getDuration().getSeconds()){
            timeAgo.append(timeElapsed / ChronoUnit.HOURS.getDuration().getSeconds() + " h");
        }else{
            LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            timeAgo.append(date.getDayOfMonth() + " " + date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            timeAgo.append((instant.until(currentDate, ChronoUnit.DAYS) > 365) ? " " + date.getYear() : "");
        }
        return timeAgo.toString();
    }
}
