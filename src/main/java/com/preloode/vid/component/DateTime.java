package com.preloode.vid.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


@Component
public class DateTime {


    @Autowired
    private Log log;

    private Date timestamp;

    private TimeZone timezone;


    public Date getTimestamp(HttpServletRequest request) {

        Date timestamp = null;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);

        this.setTimezone(simpleDateFormat);

        try {

            timestamp = new Date(simpleDateFormat.parse(format).getTime());

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        this.setTimestamp(timestamp);

        return this.timestamp;

    }


    public void setTimestamp(Date timestamp) {

        this.timestamp = timestamp;

    }


    public TimeZone getTimezone(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.timezone = calendar.getTimeZone();

        return this.timezone;

    }


    public void setTimezone(SimpleDateFormat simpleDateFormat) {

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));

    }


}
